package com.cloud.dolphin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.system.api.entity.DictData;
import com.cloud.dolphin.system.mapper.DictDataMapper;
import com.cloud.dolphin.system.service.DictDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *<p>
 * 字典数据表 服务实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Service
@AllArgsConstructor
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictDataService {

}
