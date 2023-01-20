package com.cloud.dolphin.system.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.log.annotation.SysLog;
import com.cloud.dolphin.common.security.util.SecurityUtils;
import com.cloud.dolphin.system.api.entity.Menu;
import com.cloud.dolphin.system.api.entity.RoleMenu;
import com.cloud.dolphin.system.api.vo.ResultVo;
import com.cloud.dolphin.system.service.MenuService;
import com.cloud.dolphin.system.service.RoleMenuService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 *<p>
 * 菜单信息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.APP_SYSTEM + "/menu")
public class MenuController {

    private final MenuService menuService;
    private final RoleMenuService roleMenuService;

    private LambdaQueryWrapper<Menu> getQueryWrapper(Menu menu) {
        return new LambdaQueryWrapper<Menu>()
                .like(StrUtil.isNotBlank(menu.getName()), Menu::getName, menu.getName())
                .eq(StrUtil.isNotBlank(menu.getHideMenu()), Menu::getHideMenu, menu.getHideMenu())
                .between(StrUtil.isNotBlank(menu.getBeginTime()) && StrUtil.isNotBlank(menu.getEndTime()), Menu::getCreateTime, menu.getBeginTime(), menu.getEndTime())
                .orderByAsc(Menu::getSort);
    }

    /**
     * 获取当前用户菜单路由信息
     * 前端需要根据路由的数据生成菜单
     * @return 前端路由数据
     */
    @GetMapping("/menuRoute")
    @ApiOperation(value = "路由菜单树", notes = "前端需要根据路由的数据生成菜单")
    public R menuRoute() {
        Set<Menu> menuSet = new HashSet();
        SecurityUtils.getRoles().forEach(roleId -> menuSet.addAll(menuService.selectMenuListByRoleId(roleId)));
        List<Menu> menuList = menuSet.stream().sorted(Comparator.comparingInt(Menu::getSort)).collect(Collectors.toList());
        return R.ok(menuService.buildMenuRoute(menuService.buildMenuTree(menuList, "0")));
    }

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('menu_view')")
    public R list(Menu menu) {
        List<Menu> menuList = menuService.list(getQueryWrapper(menu));
        return R.ok(menuList, menuList.size());
    }

    @GetMapping("/{id:\\w+}")
    public R getById(@PathVariable String id) {
        return R.ok(menuService.getById(id));
    }

    @SysLog("菜单新增")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('menu_add')")
    public R save(@RequestBody Menu menu) {
        if (ArrayUtil.isNotEmpty(menu.getParentIds())) {
            roleMenuService.update(Wrappers.<RoleMenu>lambdaUpdate()
                    .in(RoleMenu::getMenuId, menu.getParentIds())
                    .set(RoleMenu::getCheckeType, "2"));
        }
        menuService.save(menu);
        return R.ok();
    }

    @SysLog("菜单修改")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('menu_edit')")
    public R update(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return R.ok();
    }

    @SysLog("菜单删除")
    @DeleteMapping("/remove/{id:\\w+}")
    @PreAuthorize("@pms.hasPermission('menu_del')")
    public R remove(@PathVariable String id) {
        // todo: 防止菜单模块删除,暂时处理手段
        if (ArrayUtil.contains(new String[]{
            "1510519792507658240",
            "1510519775390703616",
            "1510519775680110592",
            "1510519775961128960",
            "1510519776250535936",
            "1521311762108076034",
            "1521312258390708226",
            "1521312739301216257"}, id)) {
            return R.error("不允许删除菜单模块!");
        }
        if (menuService.count(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, id)) > 0) {
            return R.error("存在子菜单,不允许删除");
        }
        if (roleMenuService.count(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getMenuId, id)) > 0) {
            return R.error("菜单已分配,不允许删除");
        }
        menuService.removeById(id);
        return R.ok();
    }

    @GetMapping("/roleMenuTree/{roleId}")
    public R roleMenuTree(@PathVariable String roleId) {
        List<Menu> menuList = menuService.list(new LambdaQueryWrapper<Menu>().eq(Menu::getHideMenu, "0").orderByAsc(Menu::getSort));
        return R.ok(new ResultVo().setResult(menuList).setExtend(menuService.selectMenusByRoleId(roleId)));
    }

}
