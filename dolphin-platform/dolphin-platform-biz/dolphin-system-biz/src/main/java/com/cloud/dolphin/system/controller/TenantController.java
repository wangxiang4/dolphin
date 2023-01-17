package com.cloud.dolphin.system.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.log.annotation.SysLog;
import com.cloud.dolphin.system.api.entity.*;
import com.cloud.dolphin.system.service.RoleTenantService;
import com.cloud.dolphin.system.service.TenantService;
import com.cloud.dolphin.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 *<p>
 * 多租户控制类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/5/9
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.APP_SYSTEM + "/tenant")
public class TenantController {

    private final TenantService tenantService;
    private final UserService userService;
    private final RoleTenantService roleTenantService;

    private LambdaQueryWrapper<Tenant> getQueryWrapper(Tenant tenant) {
        return new LambdaQueryWrapper<Tenant>()
                .like(StrUtil.isNotBlank(tenant.getName()), Tenant::getName, tenant.getName())
                .eq(StrUtil.isNotBlank(tenant.getCode()), Tenant::getCode, tenant.getCode())
                .eq(StrUtil.isNotBlank(tenant.getStatus()), Tenant::getStatus, tenant.getStatus());
    }

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('tenant_view')")
    public R list(Page page, Tenant tenant) {
        IPage<Tenant> tenantPage = tenantService.page(page, getQueryWrapper(tenant));
        return R.ok(tenantPage.getRecords(), tenantPage.getTotal());
    }

    @GetMapping("/currentUserTenantList")
    @PreAuthorize("@pms.hasPermission('tenant_view')")
    public R currentUserTenantList() {
        User user = userService.getCurrentUserInfo();
        List<Tenant> tenantList = tenantService.list(Wrappers.<Tenant>query().lambda().in(Tenant::getCode, StrUtil.split(user.getTenantId(), ",")));
        return R.ok(tenantList);
    }

    @GetMapping("/{id:\\w+}")
    public R getById(@PathVariable("id") String id) {
        return R.ok(tenantService.getById(id));
    }

    @SysLog("多租户新增")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('tenant_add')")
    public R save(@RequestBody Tenant tenant) {
        tenantService.save(tenant);
        return R.ok();
    }

    @SysLog("多租户修改")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('tenant_edit')")
    public R update(@RequestBody Tenant tenant) {
        tenantService.updateById(tenant);
        return R.ok();
    }

    @SysLog("多租户删除")
    @DeleteMapping("/remove/{ids:[\\w,]+}")
    @PreAuthorize("@pms.hasPermission('tenant_del')")
    public R remove(@PathVariable String[] ids) {
        // todo: 防止核心多租户删除,暂时处理手段
        if(ArrayUtil.contains(ids, "1523396238015426562")) {
            return R.error("不允许删除核心多租户数据!");
        }
        if(roleTenantService.count(new LambdaQueryWrapper<RoleTenant>().in(RoleTenant::getMultiTenantId, ids)) > 0) {
            return R.error("多租户已分配,不允许删除!");
        }
        tenantService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
