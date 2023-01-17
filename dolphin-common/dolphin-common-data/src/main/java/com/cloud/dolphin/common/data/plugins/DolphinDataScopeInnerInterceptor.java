/*
package com.cloud.dolphin.common.data.plugins;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.token.TokenService;

import java.sql.SQLException;
import java.util.List;

*/
/**
 *<p>
 * 数据过滤拦截器
 * 目前没有数据过滤需求,后面有了在做
 * 这里思路写个数据过滤拦截器,通过在mybatis中注册,跟分页那种直接全局拦截拼接sql、
 * 不过这边拼接部分放在注解切面中做,只有加了注解的才会有拼接过滤的sql
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/4/2
 *//*

public class DolphinDataScopeInnerInterceptor implements InnerInterceptor {

    */
/**
     * 全部数据权限
     *//*

    public static final String DATA_SCOPE_ALL = "1";

    */
/**
     * 自定数据权限
     *//*

    public static final String DATA_SCOPE_CUSTOM = "2";

    */
/**
     * 部门数据权限
     *//*

    public static final String DATA_SCOPE_DEPT = "3";

    */
/**
     * 部门及以下数据权限
     *//*

    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    */
/**
     * 仅本人数据权限
     *//*

    public static final String DATA_SCOPE_SELF = "5";

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {

        @Override
        public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
                ResultHandler resultHandler, BoundSql boundSql) {

            PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
            String originalSql = boundSql.getSql();
            Object parameterObject = boundSql.getParameterObject();

            // 查找参数中包含DataScope类型的参数
            DataScope dataScope = findDataScopeObject(parameterObject);
            if (dataScope == null) {
                return;
            }

            LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
            if (StringUtils.isNotNull(loginUser)) {
                SysUser currentUser = loginUser.getUser();
                // 如果是超级管理员，则不过滤数据
                if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin()) {
                    String scopeName = dataScope.getScopeName();
                    List<String> deptIds = dataScope.getDeptIds();

                    dataScopeFilter(currentUser, deptIds);
                    if (deptIds.isEmpty()) {
                        originalSql = String.format("SELECT %s FROM (%s) temp_data_scope WHERE 1 = 2",
                                dataScope.getFunc().getType(), originalSql);
                    } else {
                        String join = CollectionUtil.join(deptIds, ",");
                        originalSql = String.format("SELECT %s FROM (%s) temp_data_scope WHERE temp_data_scope.%s IN (%s)",
                                dataScope.getFunc().getType(), originalSql, scopeName, join);
                    }
                }
            }
            mpBs.sql(originalSql);
        }

        */
/**
         * 查找参数是否包括DataScope对象
         *
         * @param parameterObj 参数列表
         * @return DataScope
         *//*

        private DataScope findDataScopeObject(Object parameterObj) {
            if (parameterObj instanceof DataScope) {
                return (DataScope) parameterObj;
            } else if (parameterObj instanceof Map) {
                for (Object val : ((Map<?, ?>) parameterObj).values()) {
                    if (val instanceof DataScope) {
                        return (DataScope) val;
                    }
                }
            }
            return null;
        }

        */
/**
         * 数据范围
         *
         * @return
         *//*

        private void dataScopeFilter(SysUser user, List<String> deptList) {

            for (SysRole role : user.getRoles()) {
                String roleScope = role.getDataScope();
                if (DataScopeTypeEnum.ALL.getType().equals(roleScope)) {
                    return;
                }
                if (DataScopeTypeEnum.CUSTOM.getType().equals(roleScope)) {
                    //   获取自定义
                }
                if (DataScopeTypeEnum.OWN_CHILD_LEVEL.getType().equals(roleScope)) {
                    // 获取子集
                }
                if (DataScopeTypeEnum.OWN_LEVEL.getType().equals(roleScope)) {
                    deptList.add(user.getDeptId().toString());
                }
            }
        }
    }

}
*/
