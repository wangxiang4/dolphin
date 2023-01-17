package com.alibaba.csp.sentinel.dashboard.domain.cluster;

/**
 * @author Eric Zhao
 * @since 1.4.1
 */
public class ClusterClientInfoVO {

	private String serverHost;

	private Integer serverPort;

	private Integer clientState;

	private Integer requestTimeout;

	public String getServerHost() {
		return serverHost;
	}

	public ClusterClientInfoVO setServerHost(String serverHost) {
		this.serverHost = serverHost;
		return this;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public ClusterClientInfoVO setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
		return this;
	}

	public Integer getClientState() {
		return clientState;
	}

	public ClusterClientInfoVO setClientState(Integer clientState) {
		this.clientState = clientState;
		return this;
	}

	public Integer getRequestTimeout() {
		return requestTimeout;
	}

	public ClusterClientInfoVO setRequestTimeout(Integer requestTimeout) {
		this.requestTimeout = requestTimeout;
		return this;
	}

	@Override
	public String toString() {
		return "ClusterClientInfoVO{" + "serverHost='" + serverHost + '\'' + ", serverPort=" + serverPort
				+ ", clientState=" + clientState + ", requestTimeout=" + requestTimeout + '}';
	}

}
