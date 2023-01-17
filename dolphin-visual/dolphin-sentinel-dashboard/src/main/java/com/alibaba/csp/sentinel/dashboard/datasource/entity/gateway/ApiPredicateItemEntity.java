package com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;

import java.util.Objects;

/**
 * Entity for {@link ApiPredicateItem}.
 *
 * @author cdfive
 * @since 1.7.0
 */
public class ApiPredicateItemEntity {

	private String pattern;

	private Integer matchStrategy;

	public ApiPredicateItemEntity() {
	}

	public ApiPredicateItemEntity(String pattern, int matchStrategy) {
		this.pattern = pattern;
		this.matchStrategy = matchStrategy;
	}

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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ApiPredicateItemEntity that = (ApiPredicateItemEntity) o;
		return Objects.equals(pattern, that.pattern) && Objects.equals(matchStrategy, that.matchStrategy);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pattern, matchStrategy);
	}

	@Override
	public String toString() {
		return "ApiPredicateItemEntity{" + "pattern='" + pattern + '\'' + ", matchStrategy=" + matchStrategy + '}';
	}

}
