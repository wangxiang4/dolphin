package com.cloud.dolphin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.dolphin.system.api.entity.Menu;
import com.cloud.dolphin.system.api.entity.RoleMenu;

import java.util.List;

/**
 *<p>
 * 菜单权限表 Mapper 接口
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过角色编号查询菜单
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<Menu> selectMenuListByRoleId(String roleId);

    /**
     * 通过角色编号查询菜单权限
     *
     * @param roleId 角色ID
     * @return 菜单编号列表
     */
    List<String> selectPermsByRoleId(String roleId);

    /**
     * 通过角色编号查询菜单编号
     *
     * @param roleId 角色ID
     * @return 菜单编号列表
     */
    List<RoleMenu> selectMenusByRoleId(String roleId);

}
