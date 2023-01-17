package com.cloud.dolphin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.system.api.entity.Role;
import com.cloud.dolphin.system.api.entity.RoleMenu;
import com.cloud.dolphin.system.api.entity.RoleTenant;
import com.cloud.dolphin.system.mapper.RoleMapper;
import com.cloud.dolphin.system.service.RoleMenuService;
import com.cloud.dolphin.system.service.RoleService;
import com.cloud.dolphin.system.service.RoleTenantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *<p>
 * 角色信息表 服务实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMenuService roleMenuService;
    private final RoleTenantService roleTenantService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertRole(Role role) {
        // 新增角色信息
        baseMapper.insert(role);
        insertRoleTenant(role);
        return insertRoleMenu(role);
    }

    @Override
    public boolean updateRoleMenu(Role role) {
        // 修改角色信息
        baseMapper.updateById(role);
        // 删除角色与菜单关联
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, role.getId()));
        // 删除角色与多租户关联
        roleTenantService.remove(new LambdaQueryWrapper<RoleTenant>().eq(RoleTenant::getRoleId, role.getId()));
        insertRoleTenant(role);
        return insertRoleMenu(role);
    }

    public boolean insertRoleMenu(Role role) {
        boolean rows = true;
        // 新增菜单与角色管理
        List<RoleMenu> list = new ArrayList();
        // 新增勾选类型
        for (String menuId : role.getMenuIds().getCheckedKeys()) {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(role.getId());
            rm.setMenuId(menuId);
            rm.setCheckeType("1");
            list.add(rm);
        }
        // 新增半勾选类型
        for (String menuId : role.getMenuIds().getHalfCheckedKeys()) {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(role.getId());
            rm.setMenuId(menuId);
            rm.setCheckeType("2");
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleMenuService.saveBatch(list);
        }
        return rows;
    }

    public boolean insertRoleTenant(Role role) {
        boolean rows = true;
        // 新增用户与多租户管理
        List<RoleTenant> list = new ArrayList();
        for (String tenantId : role.getTenantIds()) {
            RoleTenant rt = new RoleTenant();
            rt.setRoleId(role.getId());
            rt.setMultiTenantId(tenantId);
            list.add(rt);
        }
        if (list.size() > 0) {
            rows = roleTenantService.saveBatch(list);
        }
        return rows;
    }

    @Override
    public List<Role> selectMyRolesByUserId(String userId) {
        return baseMapper.selectRolesByUserId(userId);
    }

}
