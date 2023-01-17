package com.cloud.dolphin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.system.api.entity.UserRole;
import com.cloud.dolphin.system.mapper.UserRoleMapper;
import com.cloud.dolphin.system.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 *<p>
 * 用户和角色关联表 服务实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
