package com.cloud.dolphin.system.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.core.util.PinyinUtil;
import com.cloud.dolphin.common.log.annotation.SysLog;
import com.cloud.dolphin.system.api.entity.*;
import com.cloud.dolphin.system.service.RoleMenuService;
import com.cloud.dolphin.system.service.RoleService;
import com.cloud.dolphin.system.service.RoleTenantService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *<p>
 * 角色信息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.APP_SYSTEM + "/role")
public class RoleController {

    private final RoleService roleService;
    private final RoleMenuService roleMenuService;
    private final RoleTenantService tenantService;

    private LambdaQueryWrapper<Role> getQueryWrapper(Role role) {
        return new LambdaQueryWrapper<Role>()
                .like(StrUtil.isNotBlank(role.getName()), Role::getName, role.getName())
                .eq(StrUtil.isNotBlank(role.getStatus()), Role::getStatus, role.getStatus());
    }

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('role_view')")
    public R list(Page page, Role role) {
        IPage<Role> roleIPage = roleService.page(page, getQueryWrapper(role));
        return R.ok(roleIPage.getRecords(), roleIPage.getTotal());
    }

    @GetMapping("/{id:\\w+}")
    public R getById(@PathVariable("id") String id) {
        Role role = roleService.getById(id);
        List<String> tenantIds;
        // 设置角色多租户信息
        if (role != null) {
            tenantIds = tenantService.list(new LambdaQueryWrapper<RoleTenant>().eq(RoleTenant::getRoleId, role.getId()))
                    .stream().map(roleTenant -> roleTenant.getMultiTenantId()).collect(Collectors.toList());
            role.setTenantIds(ArrayUtil.toArray(tenantIds, String.class));
        }
        return R.ok(role);
    }

    @SysLog("角色新增")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('role_add')")
    public R save(@RequestBody Role role) {
        if (StrUtil.isBlank(role.getCode()) && ObjectUtil.isNull(role.getId()) && StrUtil.isNotBlank(role.getName())) {
            role.setCode(PinyinUtil.getAllFirstLetter(role.getName()));
        }
        roleService.insertRole(role);
        return R.ok();
    }

    @SysLog("角色修改")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('role_edit')")
    public R update(@RequestBody Role role) {
        roleService.updateRoleMenu(role);
        return R.ok();
    }

    @SysLog("角色状态更改")
    @PutMapping("/changeStatus")
    @PreAuthorize("@pms.hasPermission('role_edit')")
    public R changeStatus(@RequestBody Role role) {
        roleService.update(new LambdaUpdateWrapper<Role>()
                .eq(Role::getId, role.getId())
                .set(Role::getStatus, role.getStatus()));
        return R.ok();
    }

    @SysLog("角色删除")
    @DeleteMapping("/remove/{ids:[\\w,]+}")
    @PreAuthorize("@pms.hasPermission('role_del')")
    public R remove(@PathVariable String[] ids) {
        // todo: 防止管理员角色删除,暂时处理手段
        if(ArrayUtil.contains(ids, "1510536230140129280")){
            return R.error("不允许删除管理员角色");
        }
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getRoleId, Arrays.asList(ids)));
        roleService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
