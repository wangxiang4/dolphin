package com.alibaba.csp.sentinel.dashboard.domain.cluster;

import java.util.List;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
public class ConnectionGroupVO {

	private String namespace;

	private List<ConnectionDescriptorVO> connectionSet;

	private Integer connectedCount;

	public String getNamespace() {
		return namespace;
	}

	public ConnectionGroupVO setNamespace(String namespace) {
		this.namespace = namespace;
		return this;
	}

	public List<ConnectionDescriptorVO> getConnectionSet() {
		return connectionSet;
	}

	public ConnectionGroupVO setConnectionSet(List<ConnectionDescriptorVO> connectionSet) {
		this.connectionSet = connectionSet;
		return this;
	}

	public Integer getConnectedCount() {
		return connectedCount;
	}

	public ConnectionGroupVO setConnectedCount(Integer connectedCount) {
		this.connectedCount = connectedCount;
		return this;
	}

	@Override
	public String toString() {
		return "ConnectionGroupVO{" + "namespace='" + namespace + '\'' + ", connectionSet=" + connectionSet
				+ ", connectedCount=" + connectedCount + '}';
	}

}
