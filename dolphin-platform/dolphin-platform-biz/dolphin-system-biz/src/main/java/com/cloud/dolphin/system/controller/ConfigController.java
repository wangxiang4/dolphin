package com.cloud.dolphin.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.dolphin.system.api.entity.Config;
import com.cloud.dolphin.system.service.ConfigService;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.core.constant.CacheConstants;
import com.cloud.dolphin.common.log.annotation.SysLog;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 *<p>
 * 参数信息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.APP_SYSTEM + "/config")
public class ConfigController {

    private final ConfigService configService;

    private LambdaQueryWrapper<Config> getQueryWrapper(Config config) {
        return new LambdaQueryWrapper<Config>()
                .like(StrUtil.isNotBlank(config.getName()), Config::getName, config.getName())
                .eq(StrUtil.isNotBlank(config.getKey()), Config::getKey, config.getKey())
                .eq(StrUtil.isNotBlank(config.getIsSys()), Config::getIsSys, config.getIsSys())
                .between(StrUtil.isNotBlank(config.getBeginTime()) && StrUtil.isNotBlank(config.getEndTime()), Config::getCreateTime, config.getBeginTime(), config.getEndTime())
                .orderByDesc(Config::getId);
    }

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('config_view')")
    public R list(Page page, Config config) {
        IPage<Config> configPage = configService.page(page, getQueryWrapper(config));
        return R.ok(configPage.getRecords(), configPage.getTotal());
    }

    @GetMapping("/{id:\\w+}")
    public R getById(@PathVariable("id") String id) {
        return R.ok(configService.getById(id));
    }

    @GetMapping("/getByKey/{key:\\w+}")
    @Cacheable(value = CacheConstants.CONFIG_PARAM, key = "#key")
    public R getByKey(@PathVariable String key) {
        Config config = configService.getOne(new LambdaQueryWrapper<Config>().eq(Config::getKey, key));
        return R.ok(config != null ? config.getValue() : null);
    }

    @SysLog("参数新增")
    @PostMapping("/save")
    @PreAuthorize("@pms.hasPermission('config_add')")
    public R save(@Validated @RequestBody Config config) {
        configService.save(config);
        return R.ok();
    }

    @SysLog("参数修改")
    @PutMapping("/update")
    @PreAuthorize("@pms.hasPermission('config_edit')")
    public R update(@Validated @RequestBody Config config) {
        configService.updateById(config);
        return R.ok();
    }

    @SysLog("参数删除")
    @DeleteMapping("/remove/{id:[\\w,]+}")
    @PreAuthorize("@pms.hasPermission('config_del')")
    public R remove(@PathVariable String[] id) {
        configService.removeByIds(Arrays.asList(id));
        return R.ok();
    }
}
