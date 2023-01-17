package com.cloud.dolphin.system.service.impl;


import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.system.api.entity.Region;
import com.cloud.dolphin.system.mapper.RegionMapper;
import com.cloud.dolphin.system.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *<p>
 * 区域服务实现
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/7/18
 */
@Service
@RequiredArgsConstructor
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {

    @Override
    public List<Region> lazyList(String parentId) {
        List<Region> regionList = this.baseMapper.lazyList(parentId);
        return regionList.stream().filter(item -> item.getParentId().equals(parentId))
                .map(item -> {
                    // 处理存入子数据,处理让树形表格显示图标
                    List<Region> regionChildren = regionList.stream()
                            .filter(e -> e.getParentId().equals(item.getId())).collect(Collectors.toList());
                    if(regionChildren.size() != 0) {
                        item.setChildren(ListUtil.empty());
                    }
                    return item;
                }).collect(Collectors.toList());
    }

}
