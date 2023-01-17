package com.cloud.dolphin.system.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.core.exception.CheckedException;
import com.cloud.dolphin.common.log.annotation.SysLog;
import com.cloud.dolphin.common.security.annotation.Inner;
import com.cloud.dolphin.common.security.util.SecurityUtils;
import com.cloud.dolphin.system.api.entity.Menu;
import com.cloud.dolphin.system.api.entity.Role;
import com.cloud.dolphin.system.api.entity.User;
import com.cloud.dolphin.system.api.entity.UserRole;
import com.cloud.dolphin.system.api.vo.ResultVo;
import com.cloud.dolphin.system.service.*;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.annotation.Sheet;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 *<p>
 * 用户信息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.APP_SYSTEM + "/user")
public class UserController {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleService roleService;
    private final FileService fileService;
    private final MenuService menuService;
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private LambdaQueryWrapper<User> getQueryWrapper(User user) {
        return new LambdaQueryWrapper<User>()
                .like(StrUtil.isNotBlank(user.getUserName()), User::getUserName, user.getUserName())
                .eq(StrUtil.isNotBlank(user.getDeptId()), User::getDeptId, user.getDeptId())
                .eq(StrUtil.isNotBlank(user.getId()), User::getId, user.getId())
                .between(StrUtil.isNotBlank(user.getBeginTime()) && StrUtil.isNotBlank(user.getEndTime()), User::getCreateTime, user.getBeginTime(), user.getEndTime());
    }

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('user_view')")
    public R list(Page page, User user) {
        IPage<User> userIPage = userService.page(page, getQueryWrapper(user));
        return R.ok(userIPage.getRecords(), userIPage.getTotal());
    }

    @GetMapping("/info")
    public R info() {
        return R.ok(userService.getCurrentUserInfo());
    }

    @GetMapping("/{id:\\w+}")
    public R getById(@PathVariable("id") String id) {
        User user = userService.getById(id);
        List<String> roles;
        // 设置所有角色集合信息
        List<Role> roleList = roleService.list();
        // 设置用户角色信息
        if (user != null) {
            roles = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()))
                    .stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toList());
            user.setRoleIds(ArrayUtil.toArray(roles, String.class));
        }
        return R.ok(new ResultVo().setResult(user).setExtend(roleList));
    }

    @Inner
    @GetMapping("getUser/{id:\\w+}")
    public R getUser(@PathVariable("id") String id) {
        User user = userService.getById(id);
        return R.ok(user);
    }

    @SysLog("用户新增")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('user_add')")
    public R save(@RequestBody User user) {
        user.setPassword(ENCODER.encode(user.getPassword()));
        userService.saveUser(user);
        return R.ok();
    }

    @Inner
    @GetMapping("/selectByUserName/{userName}")
    public R selectByUserName(@PathVariable String userName) {
        User user = userService.getOne(Wrappers.<User>query().lambda().eq(User::getUserName, userName));
        if (user == null) {
            return R.error(String.format("您输入了无效的用户名,请联系管理员检查多租户下是否有当前用户名信息!", userName));
        }
        return R.ok(userService.getUserAuthority(user));
    }

    @SysLog("用户修改")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('user_edit')")
    public R update(@RequestBody User user) {
        userService.saveUser(user);
        return R.ok();
    }

    @SysLog("用户删除")
    @DeleteMapping("/remove/{ids:[\\w,]+}")
    @PreAuthorize("@pms.hasPermission('user_del')")
    public R remove(@PathVariable String[] ids) {
        // todo: 防止管理员用户删除,暂时处理手段
        if (ArrayUtil.contains(ids, "1510539584287346688")) {
            return R.error("不允许删除超级管理员");
        }
        userService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @SysLog("用户信息修改")
    @PutMapping("/updateProfile")
    @PreAuthorize("@pms.hasPermission('user_edit')")
    public R updateProfile(@RequestBody User user) {
        userService.update(Wrappers.<User>update().lambda().eq(User::getId, user.getId())
                .set(User::getNickName, user.getNickName())
                .set(StrUtil.isNotBlank(user.getPhone()), User::getPhone, user.getPhone())
                .set(User::getEmail, user.getEmail())
                .set(User::getSex, user.getSex()));
        return R.ok();
    }

    @SysLog("用户头像修改")
    @PutMapping("/updateAvatar")
    @PreAuthorize("@pms.hasPermission('user_edit')")
    public Map updateAvatar(@RequestParam("avatarFile") MultipartFile file) {
        return fileService.uploadFile(file);
    }

    @SysLog("用户密码修改")
    @PutMapping("/updatePwd")
    @PreAuthorize("@pms.hasPermission('user_edit')")
    public R updatePwd(User user) {
        User originUser = userService.getById(SecurityUtils.getUser().getId());
        if (originUser != null && StrUtil.equals(ENCODER.encode(user.getPassword()), originUser.getPassword())) {
            userService.update(Wrappers.<User>update().lambda().eq(User::getId, originUser.getId()).set(User::getPassword, user.getPassword()));
            return R.ok();
        } else {
            return R.error("原密码有误，请重试");
        }
    }

    @SysLog("用户密码重置")
    @PutMapping("/resetPwd")
    @PreAuthorize("@pms.hasPermission('user_reset')")
    public R resetPwd(@RequestBody User user) {
        userService.update(Wrappers.<User>update().lambda().eq(User::getId, user.getId()).set(User::getPassword, ENCODER.encode(user.getNewPassword())));
        return R.ok();
    }

    @SysLog("用户状态更改")
    @PutMapping("/changeStatus")
    @PreAuthorize("@pms.hasPermission('user_edit')")
    public R changeStatus(@RequestBody User user) {
        userService.update(Wrappers.<User>update().lambda().eq(User::getId, user.getId()).set(User::getStatus, user.getStatus()));
        return R.ok();
    }

    @SysLog("导出用户数据")
    @ResponseExcel(name = "用户列表", sheets = @Sheet(sheetName = "user_list"))
    @GetMapping("/exportUser")
    @PreAuthorize("@pms.hasPermission('user_export')")
    public List<User> exportUserList() {
        return userService.list();
    }

    @SneakyThrows
    @SysLog("用户多租户更改")
    @GetMapping("/changeTenant/{tenantIds:[\\w,]+}")
    @PreAuthorize("@pms.hasPermission('user_edit')")
    public R changeTenant(@PathVariable String[] tenantIds) {
        String originTenantIds = SecurityUtils.getUser().getTenantId();
        userService.setCurrentUserTenant(tenantIds);
        try {
            // 检测切换的多租户下是否已经配置好了基础数据,没配置会导致系统直接404
            //userService.getCurrentUserInfo();
            Set<Menu> menuSet = new HashSet();
            SecurityUtils.getRoles().forEach(roleId -> menuSet.addAll(menuService.selectMenuListByRoleId(roleId)));
            List<Menu> menuList = menuSet.stream().sorted(Comparator.comparingInt(Menu::getSort)).collect(Collectors.toList());
            List<Menu> menus = menuService.buildMenuTree(menuList, "0");
            if (menus.isEmpty()) throw new CheckedException();
        } catch (Exception e) {
            userService.setCurrentUserTenant(originTenantIds);
            return R.error("请联系管理员,配置当前多租户下的基础数据!");
        }
        return R.ok();
    }

    @SysLog("重置当前用户多租户")
    @GetMapping("/resetTenant")
    @PreAuthorize("@pms.hasPermission('user_edit')")
    public R resetTenant() {
        User user =  userService.getCurrentUserInfo();
        userService.setCurrentUserTenant(user.getTenantId());
        return R.ok();
    }

    @GetMapping("/synchronousAuthenticationUser")
    public R synchronousAuthenticationUser() {
        return R.ok(SecurityUtils.getUser());
    }

}
