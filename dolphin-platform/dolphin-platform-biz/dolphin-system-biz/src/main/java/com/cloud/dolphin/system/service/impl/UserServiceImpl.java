package com.cloud.dolphin.system.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.cloud.dolphin.common.core.exception.CheckedException;
import com.cloud.dolphin.common.core.exception.CommonException;
import com.cloud.dolphin.common.data.entity.DolphinUser;
import com.cloud.dolphin.common.security.util.SecurityUtils;
import com.cloud.dolphin.system.api.entity.Dept;
import com.cloud.dolphin.system.api.entity.Role;
import com.cloud.dolphin.system.api.entity.User;
import com.cloud.dolphin.system.api.entity.UserRole;
import com.cloud.dolphin.system.mapper.UserMapper;
import com.cloud.dolphin.system.service.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *<p>
 * 用户信息表 服务实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final ConfigService configService;
    private final UserRoleService userRoleService;
    private final DeptService deptService;
    private final RoleService roleService;
    private final MenuService menuService;
    private final TenantService tenantService;
    private final TokenStore tokenStore;
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveUser(User user) {
        if (StrUtil.isEmptyIfStr(user.getId())) {
            // 新增用户信息
            int rows = baseMapper.insert(user);
            // 新增用户与角色管理
            addUserRole(user);
            return rows;
        } else {
            // 修改遵守先删后新增规则
            userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
            // 新增用户与角色管理
            addUserRole(user);
            user.setPassword(null);
            return baseMapper.updateById(user);
        }
    }


    @Override
    @SneakyThrows
    public User getUserAuthority(User user) {
        // 设置角色列表
        List<Role> roleList = roleService.selectMyRolesByUserId(user.getId());
        // 设置角色列表 （ID）
        List<String> roleIds = roleList.stream().map(Role::getId).collect(Collectors.toList());
        user.setRoleIds(ArrayUtil.toArray(roleIds, String.class));
        // 设置权限列表（menu.permission）
        Set<String> permissions = new HashSet();
        // 设置多租户编码列表
        Set<String> tenantCode = new HashSet();
        roleList.forEach(role -> {
            permissions.add(SecurityConstants.ROLE + role.getId());
            List<String> perms = menuService.selectPermsByRoleId(role.getId());
            if(perms != null && perms.size() > 0){
                perms.forEach(item -> {
                    if (StrUtil.isNotBlank(item)) {
                        permissions.add(item);
                    }
                });
            }
            // 设置多租户编码信息
            List<String> codes = tenantService.selectTenantCodeByRoleId(role.getId());
            tenantCode.addAll(codes);
        });
        // 检测多租户信息是否存在,不存在抛出异常
        if (tenantCode.size() == 0) {
           throw new CommonException("请联系管理员检查当前用户是否已分配多租户!");
        }
        user.setTenantId(String.join(",", tenantCode));
        user.setPermissions(ArrayUtil.toArray(permissions, String.class));
        return user;
    }

    @Override
    public User getCurrentUserInfo() {
        User user = super.getOne(Wrappers.<User>query().lambda().eq(User::getId, SecurityUtils.getUser().getId()));
        if (user == null) {
            throw new CommonException("请联系管理员检查多租户下是否有当前用户信息!");
        }
        this.getUserAuthority(user);
        return user;
    }

    @Override
    public void setCurrentUserTenant(String... tenantIds) {
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuthentication == null) {
            new CommonException("当前用户未登录,请登录后重试!");
        }
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) currentAuthentication;
        OAuth2AccessToken accessToken = tokenStore.getAccessToken(oAuth2Authentication);
        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        // 更新当前授权成功用户的信息
        DolphinUser dolphinUser = SecurityUtils.getUser().setTenantId(StrUtil.join(",", tenantIds));
        // 加载用户名密码身份验证令牌
        Authentication userAuthentication = new UsernamePasswordAuthenticationToken(dolphinUser, "N/A", dolphinUser.getAuthorities());
        OAuth2Authentication authentication = new OAuth2Authentication(oAuth2Request, userAuthentication);
        authentication.setAuthenticated(true);
        tokenStore.storeAccessToken(accessToken, authentication);
    }


    /**
     * 新增用户角色信息
     * @param user 用户对象
     */
    public void addUserRole(User user) {
        String[] roles = user.getRoleIds();
        if (roles != null) {
            // 新增用户与角色管理
            for (String roleId : roles) {
                UserRole ur = new UserRole();
                ur.setUserId(user.getId());
                ur.setRoleId(roleId);
                userRoleService.save(ur);
            }
        }
    }

    @Override
    public String importUser(List<User> userList, Boolean isUpdateSupport) {
        if (userList == null || userList.size() == 0) {
            throw new CheckedException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.getValueByKey("user.initPassword");
        for (User user : userList) {
            try {
                // 验证是否存在这个用户
                User u = baseMapper.selectOne(new QueryWrapper<User>().eq("user_name", user.getUserName()));
                if (u == null) {
                    user.setPassword(ENCODER.encode(password));
                    if (StrUtil.isNotBlank(user.getDeptName())) {
                        Dept dept = deptService.getOne(new QueryWrapper<Dept>().eq("name", user.getDeptName()));
                        if (dept != null) {
                            user.setDeptId(dept.getDeptId());
                            user.setDeptName(dept.getName());
                        }
                    }
                    baseMapper.insert(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                } else if (isUpdateSupport) {
                    this.updateById(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CheckedException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

}
