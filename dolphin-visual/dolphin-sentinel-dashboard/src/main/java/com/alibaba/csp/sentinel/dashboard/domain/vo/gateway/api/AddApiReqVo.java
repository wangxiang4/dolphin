package com.alibaba.csp.sentinel.dashboard.domain.vo.gateway.api;

import java.util.List;

/**
 * Value Object for add gateway api.
 *
 * @author cdfive
 * @since 1.7.0
 */
public class AddApiReqVo {

	private String app;

	private String ip;

	private Integer port;

	private String apiName;

	private List<ApiPredicateItemVo> predicateItems;

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

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public List<ApiPredicateItemVo> getPredicateItems() {
		return predicateItems;
	}

	public void setPredicateItems(List<ApiPredicateItemVo> predicateItems) {
		this.predicateItems = predicateItems;
	}

}
