package com.alibaba.csp.sentinel.dashboard.datasource.entity.rule;

import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowClusterConfig;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * @author Eric Zhao
 * @since 0.2.1
 */
public class ParamFlowRuleEntity extends AbstractRuleEntity<ParamFlowRule> {

	public ParamFlowRuleEntity() {
	}

	public ParamFlowRuleEntity(ParamFlowRule rule) {
		AssertUtil.notNull(rule, "Authority rule should not be null");
		this.rule = rule;
	}

	public static ParamFlowRuleEntity fromAuthorityRule(String app, String ip, Integer port, ParamFlowRule rule) {
		ParamFlowRuleEntity entity = new ParamFlowRuleEntity(rule);
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
	public int getGrade() {
		return rule.getGrade();
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public Integer getParamIdx() {
		return rule.getParamIdx();
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public double getCount() {
		return rule.getCount();
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public List<ParamFlowItem> getParamFlowItemList() {
		return rule.getParamFlowItemList();
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public int getControlBehavior() {
		return rule.getControlBehavior();
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public int getMaxQueueingTimeMs() {
		return rule.getMaxQueueingTimeMs();
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public int getBurstCount() {
		return rule.getBurstCount();
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public long getDurationInSec() {
		return rule.getDurationInSec();
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public boolean isClusterMode() {
		return rule.isClusterMode();
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public ParamFlowClusterConfig getClusterConfig() {
		return rule.getClusterConfig();
	}

}
