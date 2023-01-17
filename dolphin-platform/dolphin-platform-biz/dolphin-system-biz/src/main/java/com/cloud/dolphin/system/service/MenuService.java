package com.cloud.dolphin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.dolphin.system.api.entity.Menu;
import com.cloud.dolphin.system.api.entity.RoleMenu;
import com.cloud.dolphin.system.api.vo.MenuVo;

import java.util.List;

/**
 *<p>
 * 菜单权限表 服务类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
public interface MenuService extends IService<Menu> {

    /**
     * 通过角色编号查询菜单
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<Menu> selectMenuListByRoleId(String roleId);

    /**
     * 通过角色编号查询权限
     * @param roleId 角色ID
     * @return 菜单权限列表
     */
    List<String> selectPermsByRoleId(String roleId);

    /**
     * 通过角色编号查询菜单编号
     *
     * @param roleId 角色ID
     * @return 菜单编号
     */
    List<RoleMenu> selectMenusByRoleId(String roleId);

    /**
     * 构建树
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    List<Menu> buildMenuTree(List<Menu> list, String parentId);

    /**
     * 构建前端路由所需要的字段信息
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<MenuVo> buildMenuRoute(List<Menu> menus);

}
