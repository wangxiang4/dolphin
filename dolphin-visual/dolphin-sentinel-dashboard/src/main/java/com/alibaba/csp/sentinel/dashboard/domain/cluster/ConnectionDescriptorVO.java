package com.alibaba.csp.sentinel.dashboard.domain.cluster;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
public class ConnectionDescriptorVO {

	private String address;

	private String host;

	public String getAddress() {
		return address;
	}

	public ConnectionDescriptorVO setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getHost() {
		return host;
	}

	public ConnectionDescriptorVO setHost(String host) {
		this.host = host;
		return this;
	}

	@Override
	public String toString() {
		return "ConnectionDescriptorVO{" + "address='" + address + '\'' + ", host='" + host + '\'' + '}';
	}

}
