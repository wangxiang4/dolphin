package com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;

import java.util.Objects;

/**
 * Entity for {@link GatewayParamFlowItem}.
 *
 * @author cdfive
 * @since 1.7.0
 */
public class GatewayParamFlowItemEntity {

	private Integer parseStrategy;

	private String fieldName;

	private String pattern;

	private Integer matchStrategy;

	public Integer getParseStrategy() {
		return parseStrategy;
	}

	public void setParseStrategy(Integer parseStrategy) {
		this.parseStrategy = parseStrategy;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
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
		GatewayParamFlowItemEntity that = (GatewayParamFlowItemEntity) o;
		return Objects.equals(parseStrategy, that.parseStrategy) && Objects.equals(fieldName, that.fieldName)
				&& Objects.equals(pattern, that.pattern) && Objects.equals(matchStrategy, that.matchStrategy);
	}

	@Override
	public int hashCode() {
		return Objects.hash(parseStrategy, fieldName, pattern, matchStrategy);
	}

	@Override
	public String toString() {
		return "GatewayParamFlowItemEntity{" + "parseStrategy=" + parseStrategy + ", fieldName='" + fieldName + '\''
				+ ", pattern='" + pattern + '\'' + ", matchStrategy=" + matchStrategy + '}';
	}

}
