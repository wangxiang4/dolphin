package com.cloud.dolphin.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.dolphin.system.api.entity.DictData;
import com.cloud.dolphin.system.api.feign.RemoteDictService;
import com.cloud.dolphin.system.service.DictDataService;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.core.constant.CacheConstants;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.cloud.dolphin.common.log.annotation.SysLog;
import com.cloud.dolphin.common.security.annotation.Inner;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
@RequestMapping(AppConstants.APP_SYSTEM + "/dictData")
public class DictDataController {

    private final DictDataService dictDataService;
    private final RemoteDictService remoteDictService;

    private LambdaQueryWrapper<DictData> getQueryWrapper(DictData dictData) {
        return new LambdaQueryWrapper<DictData>()
                .like(StrUtil.isNotBlank(dictData.getLabel()), DictData::getLabel, dictData.getLabel())
                .eq(StrUtil.isNotBlank(dictData.getDictType()), DictData::getDictType, dictData.getDictType())
                .orderByDesc(DictData::getId);
    }

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('dictData_view')")
    public R list(Page page, DictData dictData) {
        IPage<DictData> dictPage = dictDataService.page(page, getQueryWrapper(dictData));
        return R.ok(dictPage.getRecords(), dictPage.getTotal());
    }

    @GetMapping("/{id:\\w+}")
    public R getById(@PathVariable("id") String id) {
        return R.ok(dictDataService.getById(id));
    }

    @Inner
    @GetMapping("/getDictByType/{type}")
    @Cacheable(value = CacheConstants.DICT_DETAILS, key = "#type")
    public R getDictByType(@PathVariable String type) {
        return R.ok(dictDataService.list(Wrappers.<DictData>query().lambda().eq(DictData::getDictType, type)));
    }

    @GetMapping("/dictType/{dictType}")
    public R dictType(@PathVariable String dictType) {
        return remoteDictService.getDictByType(dictType, SecurityConstants.FROM_IN);
    }

    @SysLog("字典数据新增")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('dictData_add')")
    public R save(@RequestBody DictData dictData) {
        dictDataService.save(dictData);
        return R.ok();
    }

    @SysLog("字典数据修改")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('dictData_edit')")
    public R update(@RequestBody DictData dictData) {
        dictDataService.updateById(dictData);
        return R.ok();
    }

    @SysLog("字典数据删除")
    @DeleteMapping("/remove/{ids:[\\w,]+}")
    @PreAuthorize("@pms.hasPermission('dictData_del')")
    public R remove(@PathVariable String[] ids) {
        dictDataService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
