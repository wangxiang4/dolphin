package com.cloud.dolphin.common.data.plugins;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.cloud.dolphin.common.data.override.TenantLikeExpression;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *<p>
 * 租户线路内部拦截器
 * 重构租户线路内部拦截器,支持查询多个多租户ID,还支持查询多个租户的共享数据,默认只支持一个多租户id查询
 * 支持多租户ID不存在时,查询所有租户ID数据
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
@NoArgsConstructor
public class DolphinTenantLineInnerInterceptor extends TenantLineInnerInterceptor {

    private TenantLineHandler tenantLineHandler;

    public DolphinTenantLineInnerInterceptor(final TenantLineHandler tenantLineHandler) {
        super(tenantLineHandler);
        this.tenantLineHandler = tenantLineHandler;
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        if (ObjectUtil.isNotEmpty(tenantLineHandler.getTenantId())){
            super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        }
    }

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        if (ObjectUtil.isNotEmpty(tenantLineHandler.getTenantId())) {
            super.beforePrepare(sh, connection, transactionTimeout);
        }
    }

    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        if (!this.tenantLineHandler.ignoreTable(insert.getTable().getName())) {
            List<Column> columns = insert.getColumns();
            if (!CollectionUtils.isEmpty(columns)) {
                String tenantIdColumn = this.tenantLineHandler.getTenantIdColumn();
                if (!this.tenantLineHandler.ignoreInsert(columns, tenantIdColumn)) {
                    columns.add(new Column(tenantIdColumn));

                    List<Expression> duplicateUpdateColumns = insert.getDuplicateUpdateExpressionList();
                    if (CollectionUtils.isNotEmpty(duplicateUpdateColumns)) {
                        // 替换likeExpression支持查询多个租户ID的条件,包括查询数据对应多个多租户ID的数据
                        List<String> tenantIds = StrUtil.split(this.tenantLineHandler.getTenantId().toString(), ",");
                        StringBuilder statementBuilder = new StringBuilder();
                        tenantIds.forEach(tenantId -> {
                            LikeExpression likeExpression = new LikeExpression();
                            likeExpression.setLeftExpression(new Column(tenantIdColumn));
                            likeExpression.setRightExpression(new StringValue("%" + tenantId + "%"));
                            statementBuilder.append(likeExpression + " OR ");
                        });
                        statementBuilder.delete(statementBuilder.length()-4, statementBuilder.length());
                        TenantLikeExpression tenantLikeExpression = new TenantLikeExpression(statementBuilder.toString());
                        Parenthesis parenthesis = new Parenthesis(tenantLikeExpression);
                        duplicateUpdateColumns.add(parenthesis);
                    }

                    Select select = insert.getSelect();
                    if (select != null) {
                        this.processInsertSelect(select.getSelectBody());
                    } else {
                        if (insert.getItemsList() == null) {
                            throw ExceptionUtils.mpe("Failed to process multiple-table update, please exclude the tableName or statementId", new Object[0]);
                        }

                        ItemsList itemsList = insert.getItemsList();
                        if (itemsList instanceof MultiExpressionList) {
                            ((MultiExpressionList)itemsList).getExpressionLists().forEach((el) -> {
                                el.getExpressions().add(new StringValue(this.tenantLineHandler.getTenantId().toString()));
                            });
                        } else {
                            ((ExpressionList)itemsList).getExpressions().add(new StringValue(this.tenantLineHandler.getTenantId().toString()));
                        }
                    }

                }
            }
        }
    }

    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        Table table = update.getTable();
        if (!this.tenantLineHandler.ignoreTable(table.getName())) {
            update.setWhere(this.andLikeExpression(table, update.getWhere()));
        }
    }

    @Override
    protected void processDelete(Delete delete, int index, String sql, Object obj) {
        if (!this.tenantLineHandler.ignoreTable(delete.getTable().getName())) {
            delete.setWhere(this.andLikeExpression(delete.getTable(), delete.getWhere()));
        }
    }

    /** 重写andExpression表达式,支持like查询多个参数 */
    protected Expression andLikeExpression(Table table, Expression where) {
        // 替换likeExpression支持查询多个租户ID的条件,包括查询数据对应多个多租户ID的数据
        List<String> tenantIds = StrUtil.split(this.tenantLineHandler.getTenantId().toString(), ",");
        StringBuilder statementBuilder = new StringBuilder();
        tenantIds.forEach(tenantId -> {
            LikeExpression likeExpression = new LikeExpression();
            likeExpression.setLeftExpression(this.getAliasColumn(table));
            likeExpression.setRightExpression(new StringValue("%" + tenantId + "%"));
            statementBuilder.append(likeExpression + " OR ");
        });
        statementBuilder.delete(statementBuilder.length()-4, statementBuilder.length());
        TenantLikeExpression tenantLikeExpression = new TenantLikeExpression(statementBuilder.toString());
        Parenthesis parenthesis = new Parenthesis(tenantLikeExpression);
        if (null != where) {
            return where instanceof OrExpression ? new AndExpression(parenthesis, new Parenthesis(where)) : new AndExpression(parenthesis, where);
        } else {
            return parenthesis;
        }
    }

    @Override
    protected Expression builderExpression(Expression currentExpression, List<Table> tables) {
        if (CollectionUtils.isEmpty(tables)) {
            return currentExpression;
        } else {
            // 替换likeExpression支持查询多个租户ID的条件,包括查询数据对应多个多租户ID的数据
            List<Parenthesis> likeParenthesisExpressions = tables.stream()
                .filter(x -> !this.tenantLineHandler.ignoreTable(x.getName()))
                .map(item ->  {
                    List<String> tenantIds = StrUtil.split(this.tenantLineHandler.getTenantId().toString(), ",");
                    StringBuilder statementBuilder = new StringBuilder();
                    tenantIds.forEach(tenantId -> {
                        LikeExpression likeExpression = new LikeExpression();
                        likeExpression.setLeftExpression(this.getAliasColumn(item));
                        likeExpression.setRightExpression(new StringValue("%" + tenantId + "%"));
                        statementBuilder.append(likeExpression + " OR ");
                    });
                    statementBuilder.delete(statementBuilder.length()-4, statementBuilder.length());
                    TenantLikeExpression tenantLikeExpression = new TenantLikeExpression(statementBuilder.toString());
                    Parenthesis parenthesis = new Parenthesis(tenantLikeExpression);
                    return parenthesis;
                }).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(likeParenthesisExpressions)) {
                return currentExpression;
            } else {
                Expression injectExpression = likeParenthesisExpressions.get(0);
                if (likeParenthesisExpressions.size() > 1) {
                    for(int i = 1; i < likeParenthesisExpressions.size(); ++i) {
                        injectExpression = new AndExpression(injectExpression, likeParenthesisExpressions.get(i));
                    }
                }

                if (currentExpression == null) {
                    return injectExpression;
                } else {
                    return currentExpression instanceof OrExpression ? new AndExpression(new Parenthesis(currentExpression), injectExpression) : new AndExpression(currentExpression, injectExpression);
                }
            }
        }
    }

}
