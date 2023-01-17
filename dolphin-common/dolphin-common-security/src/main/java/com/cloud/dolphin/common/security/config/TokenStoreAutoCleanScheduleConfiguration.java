package com.cloud.dolphin.common.security.config;

import com.cloud.dolphin.common.core.util.SpringContextHolderUtil;
import com.cloud.dolphin.common.security.override.DolphinRedisTokenStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;

/**
 *<p>
 * redis token store 自动配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Slf4j
@EnableScheduling
@ConditionalOnBean(AuthorizationServerConfigurerAdapter.class)
public class TokenStoreAutoCleanScheduleConfiguration {

	/**
	 * 每小时执行一致，避免 redis zset 容量问题
	 */
	@Scheduled(cron = "@hourly")
	public void doMaintenance() {
		DolphinRedisTokenStore tokenStore = SpringContextHolderUtil.getBean(DolphinRedisTokenStore.class);
		long maintenance = tokenStore.doMaintenance();
		log.debug("清理Redis ZADD 过期 token 数量: {}", maintenance);
	}

}
