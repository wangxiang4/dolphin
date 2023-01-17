package com.cloud.dolphin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.dolphin.system.api.entity.Tenant;

import java.util.List;

/**
 *<p>
 * 多租户服务类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/5/9
 */
public interface TenantService extends IService<Tenant> {

    /**
     * 通过角色编号查询多租户编码
     * @param roleId 角色ID
     * @return 多租户编码列表
     */
    List<String> selectTenantCodeByRoleId(String roleId);

}
