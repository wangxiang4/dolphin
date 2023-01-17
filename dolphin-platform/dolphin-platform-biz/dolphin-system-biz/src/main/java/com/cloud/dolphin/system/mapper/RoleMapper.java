package com.cloud.dolphin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.dolphin.system.api.entity.Role;

import java.util.List;

/**
 *<p>
 * 角色信息表 Mapper 接口
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> selectRolesByUserId(String userId);

}
