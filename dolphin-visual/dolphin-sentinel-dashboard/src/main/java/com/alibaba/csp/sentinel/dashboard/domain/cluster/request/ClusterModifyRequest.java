package com.alibaba.csp.sentinel.dashboard.domain.cluster.request;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
public interface ClusterModifyRequest {

	String getApp();

	String getIp();

	Integer getPort();

	Integer getMode();

}
