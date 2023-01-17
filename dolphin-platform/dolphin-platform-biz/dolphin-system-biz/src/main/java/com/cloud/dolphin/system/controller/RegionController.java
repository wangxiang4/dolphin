package com.cloud.dolphin.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.security.annotation.Inner;
import com.cloud.dolphin.system.api.entity.Region;
import com.cloud.dolphin.system.api.vo.ResultVo;
import com.cloud.dolphin.system.service.RegionService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *<p>
 * 区域管理
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/7/19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.APP_SYSTEM + "/region")
@Api(value = "app", tags = "地址管理模块")
public class RegionController {

    private final RegionService regionService;

    private LambdaQueryWrapper<Region> getQueryWrapper(Region region) {
        return new LambdaQueryWrapper<Region>()
                .eq(StrUtil.isNotBlank(region.getName()), Region::getName, region.getName())
                .eq(StrUtil.isNotBlank(region.getCode()), Region::getCode, region.getCode())
                .between(StrUtil.isNotBlank(region.getBeginTime()) && StrUtil.isNotBlank(region.getEndTime()), Region::getCreateTime, region.getBeginTime(), region.getEndTime())
                .orderByAsc(Region::getSort);
    }

    @GetMapping("/list")
    public R list(Region region) {
        List<Region> result = regionService.list(getQueryWrapper(region));
        return R.ok(result, result.size());
    }

    @GetMapping("/lazyList")
    public R lazyList(String parentId){
        return R.ok(regionService.lazyList(parentId));
    }

    @Inner
    @GetMapping("/selectByRegionIds/{regionIds}")
    public R selectByRegionIds(@PathVariable String[] regionIds) {
        List<Region> result = regionService.list(new LambdaQueryWrapper<Region>().in(Region::getId, regionIds));
        return R.ok(result, result.size());
    }

    @GetMapping("/{id:\\w+}")
    public R getById(@PathVariable String id) {
        ResultVo<Region> resultVo = new ResultVo();
        Region region = regionService.getById(id);
        resultVo.setResult(region);
        if(!"0".equals(region.getParentId())) {
            Region parentRegion = regionService.getById(region.getParentId());
            resultVo.setExtend(parentRegion);
        }
        return R.ok(resultVo);
    }

    @PostMapping("/save")
    public R save(@RequestBody Region region){
        regionService.save(region);
        return R.ok(region);
    }

    @PutMapping("/update")
    public R update(@RequestBody Region region){
        regionService.updateById(region);
        return R.ok(region);
    }

    @DeleteMapping("/remove/{id:\\w+}")
    public R remove(@PathVariable("id") String id){
        if (regionService.count(new LambdaQueryWrapper<Region>().eq(Region::getParentId, id)) > 0) {
            return R.error("存在下级区域,不允许删除");
        }
        regionService.removeById(id);
        return R.ok();
    }

}
