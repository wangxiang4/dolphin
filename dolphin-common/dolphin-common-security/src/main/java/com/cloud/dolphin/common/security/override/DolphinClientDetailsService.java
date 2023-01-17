package com.cloud.dolphin.common.security.override;

import com.cloud.dolphin.common.core.constant.CacheConstants;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 *<p>
 * JdbcClientDetailsService
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
public class DolphinClientDetailsService extends JdbcClientDetailsService {

	public DolphinClientDetailsService(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * 重写原生方法
	 * 加载oauth2.0客户端支持redis缓存
	 * @param clientId
	 * @return
	 */
	@Override
	@SneakyThrows
	@Cacheable(value = CacheConstants.OAUTH_CLIENT_DETAILS, key = "#clientId", unless = "#result == null")
	public ClientDetails loadClientByClientId(String clientId) {
		return super.loadClientByClientId(clientId);
	}

}
