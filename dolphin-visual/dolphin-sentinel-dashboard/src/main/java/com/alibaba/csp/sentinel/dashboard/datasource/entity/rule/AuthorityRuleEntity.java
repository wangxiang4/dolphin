package com.alibaba.csp.sentinel.dashboard.datasource.entity.rule;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Eric Zhao
 * @since 0.2.1
 */
public class AuthorityRuleEntity extends AbstractRuleEntity<AuthorityRule> {

	public AuthorityRuleEntity() {
	}

	public AuthorityRuleEntity(AuthorityRule authorityRule) {
		AssertUtil.notNull(authorityRule, "Authority rule should not be null");
		this.rule = authorityRule;
	}

	public static AuthorityRuleEntity fromAuthorityRule(String app, String ip, Integer port, AuthorityRule rule) {
		AuthorityRuleEntity entity = new AuthorityRuleEntity(rule);
		entity.setApp(app);
		entity.setIp(ip);
		entity.setPort(port);
		return entity;
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public String getLimitApp() {
		return rule.getLimitApp();
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public String getResource() {
		return rule.getResource();
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public int getStrategy() {
		return rule.getStrategy();
	}

}
