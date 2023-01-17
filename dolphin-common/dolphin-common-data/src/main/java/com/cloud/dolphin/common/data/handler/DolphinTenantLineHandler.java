package com.cloud.dolphin.common.data.handler;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.cloud.dolphin.common.data.entity.DolphinUser;
import com.cloud.dolphin.common.data.override.TenantLikeExpression;
import com.cloud.dolphin.common.data.properties.TenantProperties;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

/**
 *<p>
 * 多租户拦截处理
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/4/2
 */
public class DolphinTenantLineHandler implements TenantLineHandler {

    private TenantProperties tenantProperties;

    public DolphinTenantLineHandler(TenantProperties tenantProperties) {
        this.tenantProperties = tenantProperties;
    }

    /**
     * 默认为tenant_id字段,尽量不要去改动,因为会牵扯到实体类中的TenantId字段
     * @return String: 表中的多租户字段
     */
    @Override
    public String getTenantIdColumn() {
        return TenantLineHandler.super.getTenantIdColumn();
    }

    @Override
    public boolean ignoreTable(String tableName) {
        return tenantProperties.getExclusionTable().contains(tableName);
    }

    /**
     * 新增数据字段中存在多租户字段是否忽略此多租户字段拼接新增
     * @Param columns: 当前新增表所有字段
     * @Param tenantIdColumn: 多租户字段
     * @return boolean: 是否忽略此多租户字段拼接新增
     */
    @Override
    public boolean ignoreInsert(List<Column> columns, String tenantIdColumn) {
        return TenantLineHandler.super.ignoreInsert(columns, tenantIdColumn);
    }

    @Override
    public Expression getTenantId() {
        // 返回当前用户所属的多租户ID进行条件拼接
        return ObjectUtil.isNotEmpty(getUser()) ? new TenantLikeExpression(getUser().getTenantId()): null;
    }

    /**
     * 获取用户
     */
    protected DolphinUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Optional.ofNullable(authentication).isPresent()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof DolphinUser) {
                return (DolphinUser) principal;
            }
        }
        return null;
    }

}
