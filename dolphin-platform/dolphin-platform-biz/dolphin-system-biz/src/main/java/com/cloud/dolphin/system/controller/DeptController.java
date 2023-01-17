package com.cloud.dolphin.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.log.annotation.SysLog;
import com.cloud.dolphin.system.api.entity.Dept;
import com.cloud.dolphin.system.api.entity.User;
import com.cloud.dolphin.system.service.DeptService;
import com.cloud.dolphin.system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *<p>
 * 部门信息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.APP_SYSTEM + "/dept")
public class DeptController {

    private final DeptService deptService;
    private final UserService userService;

    private LambdaQueryWrapper<Dept> getQueryWrapper(Dept dept) {
        return Wrappers.<Dept>lambdaQuery()
                .like(StrUtil.isNotBlank(dept.getName()), Dept::getName, dept.getName())
                .eq(StrUtil.isNotBlank(dept.getStatus()), Dept::getStatus, dept.getStatus())
                .between(StrUtil.isNotBlank(dept.getBeginTime()) && StrUtil.isNotBlank(dept.getEndTime()), Dept::getCreateTime, dept.getBeginTime(), dept.getEndTime())
                .orderByAsc(Dept::getSort);
    }

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('dept_view')")
    public R list(Dept dept) {
        List<Dept> deptList = deptService.list(getQueryWrapper(dept));
        return R.ok(deptList, deptList.size());
    }

    @GetMapping("/{id:\\w+}")
    public R getById(@PathVariable("id") String id) {
        return R.ok(deptService.getById(id));
    }

    @SysLog("机构新增")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('dept_add')")
    public R save(@RequestBody Dept dept) {
        deptService.save(dept);
        return R.ok();
    }

    @SysLog("机构修改")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('dept_edit')")
    public R update(@RequestBody Dept dept) {
        deptService.updateById(dept);
        // 更新用户机构名称
        new Thread(() -> {
            List<User> userList = userService.list(new LambdaQueryWrapper<User>().eq(User::getDeptId, dept.getDeptId()));
            for (User user : userList) {
                user.setDeptName(dept.getName());
                userService.updateById(user);
            }
        }).start();
        return R.ok();
    }

    @SysLog("机构删除")
    @DeleteMapping("/remove/{id:\\w+}")
    @PreAuthorize("@pms.hasPermission('dept_del')")
    public R remove(@PathVariable("id") String id) {
        if (deptService.getOne(Wrappers.<Dept>lambdaQuery().eq(Dept::getParentId, id)) != null) {
            return R.error("存在下级机构,不允许删除");
        }
        if (userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getDeptId, id)) != null) {
            return R.error("机构存在用户,不允许删除");
        }
        deptService.removeById(id);
        return R.ok();
    }

    @SysLog("机构状态更改")
    @GetMapping("/changeStatus")
    @PreAuthorize("@pms.hasPermission('dept_edit')")
    public R changeStatus(Dept dept) {
        deptService.updateById(dept);
        return R.ok();
    }

    /** 加载机构列表树 */
    @GetMapping("/deptTree")
    public R deptTree() {
        List<Dept> deptList = deptService.list(new LambdaQueryWrapper<Dept>().orderByAsc(Dept::getSort));
        return R.ok(deptService.buildTree(deptList, "0"));
    }

}
