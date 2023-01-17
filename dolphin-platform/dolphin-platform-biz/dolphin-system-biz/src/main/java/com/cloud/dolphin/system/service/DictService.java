package com.cloud.dolphin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.dolphin.system.api.entity.Dict;

/**
 *<p>
 * 字典类型表 服务类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
public interface DictService extends IService<Dict> {

    /**
     * 删除字典,包括字典数据
     * @param types 多个字典类型
     * @return void
     */
    void deleteByTypes(String[] types);

}
