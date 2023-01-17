package com.cloud.dolphin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.dolphin.system.api.entity.Config;

/**
 *<p>
 * 参数配置表 服务类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
public interface ConfigService extends IService<Config> {

    String getValueByKey(String key);

}
