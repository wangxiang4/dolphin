package com.cloud.dolphin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.dolphin.system.api.entity.Region;

import java.util.List;

/**
 *<p>
 * 区域 Mapper 接口
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/7/20
 */
public interface RegionMapper extends BaseMapper<Region> {

    /**
     * 懒加载查询
     * @param parentId 父节点
     * @return List
     */
    List<Region> lazyList(String parentId);

}
