package com.cloud.dolphin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.dolphin.system.api.entity.User;

import java.util.List;

/**
 *<p>
 * 用户信息表 服务类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
public interface UserService extends IService<User> {

    /**
     * 保存用户信息
     * @param user 用户信息
     * @return 结果
     */
    int saveUser(User user);

    /**
     * 导入用户数据
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    String importUser(List<User> userList, Boolean isUpdateSupport);

    /**
     * 获取用户权限
     * @param user 用户信息
     * @return 结果
     */
    User getUserAuthority(User user);

    /**
     * 设置当前用户多租户信息
     * 切换当前多租户的用户下必须存在以下基础表数据,否则系统无法正常使用
     * sys_menu
     * sys_role
     * sys_user
     * sys_dept
     * @param tenantIds 多租户ids
     * @return void
     */
    void setCurrentUserTenant(String... tenantIds);

    /**
     * 获取当前用户信息
     * @return User
     */
    User getCurrentUserInfo();

}
