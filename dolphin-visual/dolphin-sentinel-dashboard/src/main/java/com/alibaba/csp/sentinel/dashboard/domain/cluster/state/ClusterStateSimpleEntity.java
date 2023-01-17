package com.alibaba.csp.sentinel.dashboard.domain.cluster.state;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
public class ClusterStateSimpleEntity {

	private Integer mode;

	private Long lastModified;

	private Boolean clientAvailable;

	private Boolean serverAvailable;

	public Integer getMode() {
		return mode;
	}

	public ClusterStateSimpleEntity setMode(Integer mode) {
		this.mode = mode;
		return this;
	}

	public Long getLastModified() {
		return lastModified;
	}

	public ClusterStateSimpleEntity setLastModified(Long lastModified) {
		this.lastModified = lastModified;
		return this;
	}

	public Boolean getClientAvailable() {
		return clientAvailable;
	}

	public ClusterStateSimpleEntity setClientAvailable(Boolean clientAvailable) {
		this.clientAvailable = clientAvailable;
		return this;
	}

	public Boolean getServerAvailable() {
		return serverAvailable;
	}

	public ClusterStateSimpleEntity setServerAvailable(Boolean serverAvailable) {
		this.serverAvailable = serverAvailable;
		return this;
	}

	@Override
	public String toString() {
		return "ClusterStateSimpleEntity{" + "mode=" + mode + ", lastModified=" + lastModified + ", clientAvailable="
				+ clientAvailable + ", serverAvailable=" + serverAvailable + '}';
	}

}
