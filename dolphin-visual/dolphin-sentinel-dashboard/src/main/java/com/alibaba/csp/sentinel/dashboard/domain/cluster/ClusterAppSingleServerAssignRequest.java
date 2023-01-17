package com.alibaba.csp.sentinel.dashboard.domain.cluster;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.request.ClusterAppAssignMap;

import java.util.Set;

/**
 * @author Eric Zhao
 * @since 1.4.1
 */
public class ClusterAppSingleServerAssignRequest {

	private ClusterAppAssignMap clusterMap;

	private Set<String> remainingList;

	public ClusterAppAssignMap getClusterMap() {
		return clusterMap;
	}

	public ClusterAppSingleServerAssignRequest setClusterMap(ClusterAppAssignMap clusterMap) {
		this.clusterMap = clusterMap;
		return this;
	}

	public Set<String> getRemainingList() {
		return remainingList;
	}

	public ClusterAppSingleServerAssignRequest setRemainingList(Set<String> remainingList) {
		this.remainingList = remainingList;
		return this;
	}

	@Override
	public String toString() {
		return "ClusterAppSingleServerAssignRequest{" + "clusterMap=" + clusterMap + ", remainingList=" + remainingList
				+ '}';
	}

}
