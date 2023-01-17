package com.alibaba.csp.sentinel.dashboard.domain.cluster.config;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
public class ClusterClientConfig {

	private String serverHost;

	private Integer serverPort;

	private Integer requestTimeout;

	private Integer connectTimeout;

	public String getServerHost() {
		return serverHost;
	}

	public ClusterClientConfig setServerHost(String serverHost) {
		this.serverHost = serverHost;
		return this;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public ClusterClientConfig setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
		return this;
	}

	public Integer getRequestTimeout() {
		return requestTimeout;
	}

	public ClusterClientConfig setRequestTimeout(Integer requestTimeout) {
		this.requestTimeout = requestTimeout;
		return this;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public ClusterClientConfig setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
		return this;
	}

	@Override
	public String toString() {
		return "ClusterClientConfig{" + "serverHost='" + serverHost + '\'' + ", serverPort=" + serverPort
				+ ", requestTimeout=" + requestTimeout + ", connectTimeout=" + connectTimeout + '}';
	}

}
