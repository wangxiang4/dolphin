package com.cloud.dolphin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.dolphin.system.api.entity.Dept;
import com.cloud.dolphin.system.api.entity.User;

import java.util.List;

/**
 *<p>
 * 用户信息表 Mapper 接口
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据部门名称和部门类型查询用户信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    User selectUseByDept(Dept dept);

}
