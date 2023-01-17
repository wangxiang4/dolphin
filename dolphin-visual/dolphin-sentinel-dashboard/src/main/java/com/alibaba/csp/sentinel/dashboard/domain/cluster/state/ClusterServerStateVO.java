package com.alibaba.csp.sentinel.dashboard.domain.cluster.state;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.ConnectionGroupVO;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.config.ServerFlowConfig;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.config.ServerTransportConfig;

import java.util.List;
import java.util.Set;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
public class ClusterServerStateVO {

	private String appName;

	private ServerTransportConfig transport;

	private ServerFlowConfig flow;

	private Set<String> namespaceSet;

	private Integer port;

	private List<ConnectionGroupVO> connection;

	private List<ClusterRequestLimitVO> requestLimitData;

	private Boolean embedded;

	public String getAppName() {
		return appName;
	}

	public ClusterServerStateVO setAppName(String appName) {
		this.appName = appName;
		return this;
	}

	public ServerTransportConfig getTransport() {
		return transport;
	}

	public ClusterServerStateVO setTransport(ServerTransportConfig transport) {
		this.transport = transport;
		return this;
	}

	public ServerFlowConfig getFlow() {
		return flow;
	}

	public ClusterServerStateVO setFlow(ServerFlowConfig flow) {
		this.flow = flow;
		return this;
	}

	public Set<String> getNamespaceSet() {
		return namespaceSet;
	}

	public ClusterServerStateVO setNamespaceSet(Set<String> namespaceSet) {
		this.namespaceSet = namespaceSet;
		return this;
	}

	public Integer getPort() {
		return port;
	}

	public ClusterServerStateVO setPort(Integer port) {
		this.port = port;
		return this;
	}

	public List<ConnectionGroupVO> getConnection() {
		return connection;
	}

	public ClusterServerStateVO setConnection(List<ConnectionGroupVO> connection) {
		this.connection = connection;
		return this;
	}

	public List<ClusterRequestLimitVO> getRequestLimitData() {
		return requestLimitData;
	}

	public ClusterServerStateVO setRequestLimitData(List<ClusterRequestLimitVO> requestLimitData) {
		this.requestLimitData = requestLimitData;
		return this;
	}

	public Boolean getEmbedded() {
		return embedded;
	}

	public ClusterServerStateVO setEmbedded(Boolean embedded) {
		this.embedded = embedded;
		return this;
	}

	@Override
	public String toString() {
		return "ClusterServerStateVO{" + "appName='" + appName + '\'' + ", transport=" + transport + ", flow=" + flow
				+ ", namespaceSet=" + namespaceSet + ", port=" + port + ", connection=" + connection
				+ ", requestLimitData=" + requestLimitData + ", embedded=" + embedded + '}';
	}

}
