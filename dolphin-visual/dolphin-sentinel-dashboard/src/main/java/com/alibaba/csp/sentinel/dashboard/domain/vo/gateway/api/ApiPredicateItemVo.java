package com.alibaba.csp.sentinel.dashboard.domain.vo.gateway.api;

/**
 * Value Object for add or update gateway api.
 *
 * @author cdfive
 * @since 1.7.0
 */
public class ApiPredicateItemVo {

	private String pattern;

	private Integer matchStrategy;

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Integer getMatchStrategy() {
		return matchStrategy;
	}

	public void setMatchStrategy(Integer matchStrategy) {
		this.matchStrategy = matchStrategy;
	}

}
