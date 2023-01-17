package com.cloud.dolphin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.system.api.entity.Tenant;
import com.cloud.dolphin.system.mapper.TenantMapper;
import com.cloud.dolphin.system.service.TenantService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *<p>
 * 多租户服务实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/5/9
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Override
    public List<String> selectTenantCodeByRoleId(String roleId) {
        return baseMapper.selectTenantCodeByRoleId(roleId);
    }

}
