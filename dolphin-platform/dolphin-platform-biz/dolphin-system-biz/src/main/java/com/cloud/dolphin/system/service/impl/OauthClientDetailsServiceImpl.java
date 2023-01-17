package com.cloud.dolphin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.common.core.constant.CacheConstants;
import com.cloud.dolphin.system.api.entity.OauthClientDetails;
import com.cloud.dolphin.system.mapper.OauthClientDetailsMapper;
import com.cloud.dolphin.system.service.OauthClientDetailsService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *<p>
 * 授权客户端管理
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {

	@Override
	@CacheEvict(value = CacheConstants.OAUTH_CLIENT_DETAILS, key = "#ids")
	public Boolean removeClientDetailsByIds(List<String> ids) {
		return this.removeByIds(ids);
	}

	@Override
	@CacheEvict(value = CacheConstants.OAUTH_CLIENT_DETAILS, key = "#clientDetails.clientId")
	public Boolean updateClientDetailsById(OauthClientDetails clientDetails) {
		return this.updateById(clientDetails);
	}

	@Override
	@CacheEvict(value = CacheConstants.OAUTH_CLIENT_DETAILS, allEntries = true)
	public void clearClientCache() {
	}

}
