package com.cloud.dolphin.system.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.log.annotation.SysLog;
import com.cloud.dolphin.system.api.entity.OauthClientDetails;
import com.cloud.dolphin.system.service.OauthClientDetailsService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

/**
 *<p>
 * oauth2客户端授权列表管理
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.APP_SYSTEM + "/client")
@Api(value = "client", tags = "客户端管理模块")
public class OauthClientDetailsController {

	private final OauthClientDetailsService oauthClientDetailsService;

	private LambdaQueryWrapper<OauthClientDetails> getQueryWrapper(OauthClientDetails oauthClientDetails) {
		return new LambdaQueryWrapper<OauthClientDetails>()
				.eq(StrUtil.isNotBlank(oauthClientDetails.getClientId()), OauthClientDetails::getClientId, oauthClientDetails.getClientId())
				.between(StrUtil.isNotBlank(oauthClientDetails.getBeginTime()) && StrUtil.isNotBlank(oauthClientDetails.getEndTime()),
						OauthClientDetails::getCreateTime, oauthClientDetails.getBeginTime(), oauthClientDetails.getEndTime());
	}

	@GetMapping("/list")
	public R list(Page page, OauthClientDetails oauthClientDetails) {
		IPage<OauthClientDetails> list = oauthClientDetailsService.page(page, getQueryWrapper(oauthClientDetails));
		return R.ok(list.getRecords(), list.getTotal());
	}

	@GetMapping("/{id:\\w+}")
	public R getById(@PathVariable("id") String id) {
		return R.ok(oauthClientDetailsService.getById(id));
	}

	@SysLog("添加终端")
	@PostMapping("/save")
	@PreAuthorize("@pms.hasPermission('client_add')")
	public R save(@Valid @RequestBody OauthClientDetails oauthClientDetails) {
		oauthClientDetailsService.save(oauthClientDetails);
		return R.ok();
	}

	@SysLog("编辑终端")
	@PutMapping("/update")
	@PreAuthorize("@pms.hasPermission('client_edit')")
	public R update(@Valid @RequestBody OauthClientDetails oauthClientDetails) {
		oauthClientDetailsService.updateClientDetailsById(oauthClientDetails);
		return R.ok();
	}

	@SysLog("删除终端")
	@DeleteMapping("/remove/{ids:[\\w,]+}")
	@PreAuthorize("@pms.hasPermission('client_del')")
	public R<Boolean> removeById(@PathVariable String[] ids) {
		// todo: 防止客户端删除,暂时处理手段
		if(ArrayUtil.containsAny(ids, "dolphin", "dolphin_lock", "test")){
			return R.error("不允许删除核心客户端");
		}
		oauthClientDetailsService.removeClientDetailsByIds(Arrays.asList(ids));
		return R.ok();
	}

	@SysLog("清除终端缓存")
	@DeleteMapping("/cache")
	@PreAuthorize("@pms.hasPermission('sys_client_del')")
	public R clearClientCache() {
		oauthClientDetailsService.clearClientCache();
		return R.ok();
	}

}
