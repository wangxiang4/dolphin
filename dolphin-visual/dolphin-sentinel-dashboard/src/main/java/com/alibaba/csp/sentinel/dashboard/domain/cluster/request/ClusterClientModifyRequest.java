package com.alibaba.csp.sentinel.dashboard.domain.cluster.request;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.config.ClusterClientConfig;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
public class ClusterClientModifyRequest implements ClusterModifyRequest {

	private String app;

	private String ip;

	private Integer port;

	private Integer mode;

	private ClusterClientConfig clientConfig;

	@Override
	public String getApp() {
		return app;
	}

	public ClusterClientModifyRequest setApp(String app) {
		this.app = app;
		return this;
	}

	@Override
	public String getIp() {
		return ip;
	}

	public ClusterClientModifyRequest setIp(String ip) {
		this.ip = ip;
		return this;
	}

	@Override
	public Integer getPort() {
		return port;
	}

	public ClusterClientModifyRequest setPort(Integer port) {
		this.port = port;
		return this;
	}

	@Override
	public Integer getMode() {
		return mode;
	}

	public ClusterClientModifyRequest setMode(Integer mode) {
		this.mode = mode;
		return this;
	}

	public ClusterClientConfig getClientConfig() {
		return clientConfig;
	}

	public ClusterClientModifyRequest setClientConfig(ClusterClientConfig clientConfig) {
		this.clientConfig = clientConfig;
		return this;
	}

}
