package com.alibaba.csp.sentinel.dashboard.datasource.entity.rule;

import com.alibaba.csp.sentinel.slots.block.AbstractRule;

import java.util.Date;

/**
 * @author Eric Zhao
 * @since 0.2.1
 */
public abstract class AbstractRuleEntity<T extends AbstractRule> implements RuleEntity {

	protected Long id;

	protected String app;

	protected String ip;

	protected Integer port;

	protected T rule;

	private Date gmtCreate;

	private Date gmtModified;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getApp() {
		return app;
	}

	public AbstractRuleEntity<T> setApp(String app) {
		this.app = app;
		return this;
	}

	@Override
	public String getIp() {
		return ip;
	}

	public AbstractRuleEntity<T> setIp(String ip) {
		this.ip = ip;
		return this;
	}

	@Override
	public Integer getPort() {
		return port;
	}

	public AbstractRuleEntity<T> setPort(Integer port) {
		this.port = port;
		return this;
	}

	public T getRule() {
		return rule;
	}

	public AbstractRuleEntity<T> setRule(T rule) {
		this.rule = rule;
		return this;
	}

	@Override
	public Date getGmtCreate() {
		return gmtCreate;
	}

	public AbstractRuleEntity<T> setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
		return this;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public AbstractRuleEntity<T> setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
		return this;
	}

	@Override
	public T toRule() {
		return rule;
	}

}
