package com.cloud.dolphin.common.security.config;

import com.cloud.dolphin.common.core.constant.CacheConstants;
import com.cloud.dolphin.common.security.override.DolphinRedisTokenStore;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 *<p>
 * 令牌存储自动配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
public class TokenStoreAutoConfiguration {

	@Bean
	public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
		DolphinRedisTokenStore tokenStore = new DolphinRedisTokenStore(redisConnectionFactory);
		tokenStore.setPrefix(CacheConstants.OAUTH_ACCESS);
		return tokenStore;
	}

}
