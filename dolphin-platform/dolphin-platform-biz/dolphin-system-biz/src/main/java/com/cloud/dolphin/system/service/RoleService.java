package com.cloud.dolphin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.dolphin.system.api.entity.Role;

import java.util.List;

/**
 *<p>
 * 角色信息表 服务类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
public interface RoleService extends IService<Role> {

    /**
     * 新增数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    boolean insertRole(Role role);

    /**
     * 修改角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    boolean updateRoleMenu(Role role);

    /**
     * 根据用户ID查询我的角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> selectMyRolesByUserId(String userId);

}
