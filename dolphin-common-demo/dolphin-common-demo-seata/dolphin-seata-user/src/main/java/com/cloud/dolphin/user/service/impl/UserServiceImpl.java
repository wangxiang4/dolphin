package com.cloud.dolphin.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.user.entity.User;
import com.cloud.dolphin.user.mapper.UserMapper;
import com.cloud.dolphin.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 *<p>
 * 用户业务实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
