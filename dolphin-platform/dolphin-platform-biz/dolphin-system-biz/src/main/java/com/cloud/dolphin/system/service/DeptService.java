package com.cloud.dolphin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.dolphin.system.api.entity.Dept;

import java.util.List;

/**
 *<p>
 * 部门表 服务类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
public interface DeptService extends IService<Dept> {

    /**
     * 构建树
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    List<Dept> buildTree(List<Dept> list, String parentId);

}
