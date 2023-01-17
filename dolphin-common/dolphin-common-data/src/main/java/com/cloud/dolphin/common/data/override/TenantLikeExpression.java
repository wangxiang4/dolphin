package com.cloud.dolphin.common.data.override;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.parser.ASTNodeAccessImpl;

import java.util.Objects;

/**
 *<p>
 * 重写StringValue,支持多租户like拼接查询
 * 由于内部StringExpression会拼接默认会加''
 * 而多租户like条件经过处理不许需要加'',如果加上会导致数据查不出
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/5/11
 */
public class TenantLikeExpression extends ASTNodeAccessImpl implements Expression {

    private String value = "";

    public TenantLikeExpression() {
    }

    public TenantLikeExpression(String escapedValue) {
        this.value = escapedValue;
    }

    public String getValue() {
        return this.value;
    }


    public void setValue(String string) {
        this.value = string;
    }


    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
    }

    @Override
    public String toString() {
        return this.value;
    }

    public TenantLikeExpression withValue(String value) {
        this.setValue(value);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            TenantLikeExpression that = (TenantLikeExpression)o;
            return Objects.equals(this.value, that.value);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(new Object[]{this.value});
    }
}
