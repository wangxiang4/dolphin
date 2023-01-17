package com.cloud.dolphin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.system.api.entity.Config;
import com.cloud.dolphin.system.mapper.ConfigMapper;
import com.cloud.dolphin.system.service.ConfigService;
import com.cloud.dolphin.common.core.constant.CacheConstants;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 *<p>
 * 参数配置表 服务实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Service
@AllArgsConstructor
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    private final CacheManager cacheManager;

    /** 获取配置参数缓存 */
    @Override
    public String getValueByKey(String key) {
        Cache cache = cacheManager.getCache(CacheConstants.CONFIG_PARAM);
        if (cache != null && cache.get(key) != null) {
            return (String) cache.get(key).get();
        } else {
            Config config = baseMapper.selectOne(new LambdaQueryWrapper<Config>().eq(Config::getKey, key));
            return config.getValue();
        }
    }
}
