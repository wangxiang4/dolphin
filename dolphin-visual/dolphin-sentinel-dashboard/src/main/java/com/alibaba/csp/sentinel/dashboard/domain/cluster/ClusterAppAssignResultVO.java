package com.alibaba.csp.sentinel.dashboard.domain.cluster;

import java.util.Set;

/**
 * @author Eric Zhao
 * @since 1.4.1
 */
public class ClusterAppAssignResultVO {

	private Set<String> failedServerSet;

	private Set<String> failedClientSet;

	private Integer totalCount;

	public Set<String> getFailedServerSet() {
		return failedServerSet;
	}

	public ClusterAppAssignResultVO setFailedServerSet(Set<String> failedServerSet) {
		this.failedServerSet = failedServerSet;
		return this;
	}

	public Set<String> getFailedClientSet() {
		return failedClientSet;
	}

	public ClusterAppAssignResultVO setFailedClientSet(Set<String> failedClientSet) {
		this.failedClientSet = failedClientSet;
		return this;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public ClusterAppAssignResultVO setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
		return this;
	}

	@Override
	public String toString() {
		return "ClusterAppAssignResultVO{" + "failedServerSet=" + failedServerSet + ", failedClientSet="
				+ failedClientSet + ", totalCount=" + totalCount + '}';
	}

}
