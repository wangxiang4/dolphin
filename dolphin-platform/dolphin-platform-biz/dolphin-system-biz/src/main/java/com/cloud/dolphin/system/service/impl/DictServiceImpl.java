package com.cloud.dolphin.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.system.api.entity.Dict;
import com.cloud.dolphin.system.api.entity.DictData;
import com.cloud.dolphin.system.mapper.DictMapper;
import com.cloud.dolphin.system.service.DictDataService;
import com.cloud.dolphin.system.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *<p>
 * 字典类型表 服务实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Service
@RequiredArgsConstructor
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    private final DictDataService dictDataService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTypes(String[] types) {
        for (int i = 0; i < types.length; ++i) {
            this.remove(Wrappers.<Dict>lambdaUpdate().eq(Dict::getType, types[i]));
            dictDataService.remove(Wrappers.<DictData>lambdaUpdate().eq(DictData::getDictType, types[i]));
        }
    }

}
