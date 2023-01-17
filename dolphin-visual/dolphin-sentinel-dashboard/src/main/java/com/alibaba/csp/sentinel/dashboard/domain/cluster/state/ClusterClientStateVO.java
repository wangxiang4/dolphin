package com.alibaba.csp.sentinel.dashboard.domain.cluster.state;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.ClusterClientInfoVO;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
public class ClusterClientStateVO {

	/**
	 * Cluster token client state.
	 */
	private ClusterClientInfoVO clientConfig;

	public ClusterClientInfoVO getClientConfig() {
		return clientConfig;
	}

	public ClusterClientStateVO setClientConfig(ClusterClientInfoVO clientConfig) {
		this.clientConfig = clientConfig;
		return this;
	}

	@Override
	public String toString() {
		return "ClusterClientStateVO{" + "clientConfig=" + clientConfig + '}';
	}

}
