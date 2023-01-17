package com.alibaba.csp.sentinel.dashboard.domain.vo.gateway.api;

import java.util.List;

/**
 * Value Object for update gateway api.
 *
 * @author cdfive
 * @since 1.7.0
 */
public class UpdateApiReqVo {

	private Long id;

	private String app;

	private List<ApiPredicateItemVo> predicateItems;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public List<ApiPredicateItemVo> getPredicateItems() {
		return predicateItems;
	}

	public void setPredicateItems(List<ApiPredicateItemVo> predicateItems) {
		this.predicateItems = predicateItems;
	}

}
