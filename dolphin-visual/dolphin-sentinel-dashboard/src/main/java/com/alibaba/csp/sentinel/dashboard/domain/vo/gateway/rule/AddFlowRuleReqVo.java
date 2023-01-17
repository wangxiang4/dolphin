package com.alibaba.csp.sentinel.dashboard.domain.vo.gateway.rule;

/**
 * Value Object for add gateway flow rule.
 *
 * @author cdfive
 * @since 1.7.0
 */
public class AddFlowRuleReqVo {

	private String app;

	private String ip;

	private Integer port;

	private String resource;

	private Integer resourceMode;

	private Integer grade;

	private Double count;

	private Long interval;

	private Integer intervalUnit;

	private Integer controlBehavior;

	private Integer burst;

	private Integer maxQueueingTimeoutMs;

	private GatewayParamFlowItemVo paramItem;

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Integer getResourceMode() {
		return resourceMode;
	}

	public void setResourceMode(Integer resourceMode) {
		this.resourceMode = resourceMode;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public Long getInterval() {
		return interval;
	}

	public void setInterval(Long interval) {
		this.interval = interval;
	}

	public Integer getIntervalUnit() {
		return intervalUnit;
	}

	public void setIntervalUnit(Integer intervalUnit) {
		this.intervalUnit = intervalUnit;
	}

	public Integer getControlBehavior() {
		return controlBehavior;
	}

	public void setControlBehavior(Integer controlBehavior) {
		this.controlBehavior = controlBehavior;
	}

	public Integer getBurst() {
		return burst;
	}

	public void setBurst(Integer burst) {
		this.burst = burst;
	}

	public Integer getMaxQueueingTimeoutMs() {
		return maxQueueingTimeoutMs;
	}

	public void setMaxQueueingTimeoutMs(Integer maxQueueingTimeoutMs) {
		this.maxQueueingTimeoutMs = maxQueueingTimeoutMs;
	}

	public GatewayParamFlowItemVo getParamItem() {
		return paramItem;
	}

	public void setParamItem(GatewayParamFlowItemVo paramItem) {
		this.paramItem = paramItem;
	}

}
