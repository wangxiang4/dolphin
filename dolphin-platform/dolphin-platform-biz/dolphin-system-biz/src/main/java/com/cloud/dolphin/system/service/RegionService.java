package com.cloud.dolphin.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.dolphin.system.api.entity.Region;

import java.util.List;

/**
 *<p>
 * 区域服务
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/7/18
 */
public interface RegionService extends IService<Region> {

    /**
     * 懒加载查询
     * @param parentId 父节点
     * @return List
     */
    List<Region> lazyList(String parentId);

}
