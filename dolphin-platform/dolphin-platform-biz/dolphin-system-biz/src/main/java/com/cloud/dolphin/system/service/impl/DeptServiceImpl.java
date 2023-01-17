package com.cloud.dolphin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.system.api.entity.Dept;
import com.cloud.dolphin.system.mapper.DeptMapper;
import com.cloud.dolphin.system.service.DeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *<p>
 * 部门表 服务实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Override
    public List<Dept> buildTree(List<Dept> list, String parentId){
        List<Dept> deptList = new ArrayList();
        for (Iterator<Dept> iterator = list.iterator(); iterator.hasNext(); ) {
            Dept t = iterator.next();
            if (StrUtil.equals(t.getParentId(), parentId)) {
                recursion(list, t);
                deptList.add(t);
            }
        }
        return deptList;
    }

    /** 递归列表 */
    private void recursion(List<Dept> list, Dept dept) {
        // 得到子节点列表
        List<Dept> childList = getChildList(list, dept);
        dept.setChildren(childList);
        for (Dept tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<Dept> it = childList.iterator();
                while (it.hasNext()) {
                    Dept n = (Dept) it.next();
                    recursion(list, n);
                }
            }
        }
    }

    /** 得到子节点列表 */
    private List<Dept> getChildList(List<Dept> list, Dept dept) {
        List<Dept> deptList = new ArrayList() ;
        Iterator<Dept> it = list.iterator();
        while (it.hasNext()) {
            Dept n = it.next();
            if (StrUtil.equals(n.getParentId(), dept.getDeptId())) {
                deptList.add(n);
            }
        }
        return deptList;
    }

    /** 判断是否有子节点 */
    private boolean hasChild(List<Dept> list, Dept t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

}
