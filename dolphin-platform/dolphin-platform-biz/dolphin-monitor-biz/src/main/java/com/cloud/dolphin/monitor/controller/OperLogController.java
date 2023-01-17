package com.cloud.dolphin.monitor.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.log.annotation.SysLog;
import com.cloud.dolphin.common.security.annotation.Inner;
import com.cloud.dolphin.monitor.api.entity.OperLog;
import com.cloud.dolphin.monitor.service.OperLogService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

/**
 *<p>
 * 系统操作记录
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.APP_MONITOR + "/operLog")
public class OperLogController {

    private final OperLogService operLogService;

    private LambdaQueryWrapper<OperLog> getQueryWrapper(OperLog operLog) {
        return new LambdaQueryWrapper<OperLog>()
                .like(StrUtil.isNotBlank(operLog.getOperName()), OperLog::getOperName, operLog.getOperName())
                .like(StrUtil.isNotBlank(operLog.getTitle()), OperLog::getTitle, operLog.getTitle())
                .eq(ObjectUtil.isNotEmpty(operLog.getStatus()), OperLog::getStatus, operLog.getStatus())
                .between(StrUtil.isNotBlank(operLog.getBeginTime()) && StrUtil.isNotBlank(operLog.getEndTime()), OperLog::getOperTime, operLog.getBeginTime(), operLog.getEndTime())
                .orderByDesc(OperLog::getId);
    }

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('operLog_view')")
    public R list(Page page, OperLog operLog) {
        IPage<OperLog> operLogPage = operLogService.page(page, getQueryWrapper(operLog));
        return R.ok(operLogPage.getRecords(), operLogPage.getTotal());
    }

    @Inner
    @PostMapping("/save")
    public R<Boolean> save(@Valid @RequestBody OperLog operLog) {
        return R.ok(operLogService.save(operLog));
    }

    @SysLog("操作日志删除")
    @DeleteMapping("/remove/{ids:[\\w,]+}")
    @PreAuthorize("@pms.hasPermission('operLog_del')")
    public R remove(@PathVariable String[] ids) {
        operLogService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @SysLog("操作日志清空")
    @DeleteMapping("/clean")
    @PreAuthorize("@pms.hasPermission('operLog_del')")
    public R clean() {
        operLogService.remove(new QueryWrapper());
        return R.ok();
    }

}
