package com.cloud.dolphin.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.dolphin.system.api.entity.Dict;
import com.cloud.dolphin.system.service.DictService;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.log.annotation.SysLog;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 *<p>
 * 数据字典信息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.APP_SYSTEM + "/dict")
public class DictController {

    private final DictService dictService;

    private LambdaQueryWrapper<Dict> getQueryWrapper(Dict dict) {
        return new LambdaQueryWrapper<Dict>()
                .like(StrUtil.isNotBlank(dict.getName()), Dict::getName, dict.getName())
                .eq(StrUtil.isNotBlank(dict.getType()), Dict::getType, dict.getType())
                .eq(StrUtil.isNotBlank(dict.getStatus()), Dict::getStatus, dict.getStatus())
                .orderByDesc(Dict::getId);
    }

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('dict_view')")
    public R list(Page page, Dict dict) {
        IPage<Dict> dictPage = dictService.page(page, getQueryWrapper(dict));
        return R.ok(dictPage.getRecords(), dictPage.getTotal());
    }

    @GetMapping("/{id:\\w+}")
    public R getById(@PathVariable("id") String id) {
        return R.ok(dictService.getById(id));
    }

    @SysLog("字典新增")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('dict_add')")
    public R save(@RequestBody Dict dict) {
        dictService.save(dict);
        return R.ok();
    }

    @SysLog("字典修改")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('dict_edit')")
    public R update(@RequestBody Dict dict) {
        dictService.updateById(dict);
        return R.ok();
    }

    @SysLog("字典删除")
    @DeleteMapping("/remove/{types:[\\w,]+}")
    @PreAuthorize("@pms.hasPermission('dict_del')")
    public R remove(@PathVariable String[] types) {
        dictService.deleteByTypes(types);
        return R.ok();
    }

    @SysLog("字典状态更改")
    @PutMapping("/changeStatus")
    @PreAuthorize("@pms.hasPermission('dict_edit')")
    public R changeStatus(@RequestBody Dict dict) {
        dictService.updateById(dict);
        return R.ok();
    }

}
