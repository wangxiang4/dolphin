package com.cloud.dolphin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.dolphin.system.api.entity.Tenant;

import java.util.List;

/**
 *<p>
 * 多租户 Mapper 接口
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/5/9
 */
public interface TenantMapper extends BaseMapper<Tenant> {

    /**
     * 通过角色编号查询多租户编码
     * @param roleId 角色ID
     * @return 多租户编码列表
     */
    List<String> selectTenantCodeByRoleId(String roleId);

}
