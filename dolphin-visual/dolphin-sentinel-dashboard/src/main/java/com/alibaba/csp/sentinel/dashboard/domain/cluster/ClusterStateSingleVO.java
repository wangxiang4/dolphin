package com.alibaba.csp.sentinel.dashboard.domain.cluster;

/**
 * @author Eric Zhao
 * @since 1.4.1
 */
public class ClusterStateSingleVO {

	private String address;

	private Integer mode;

	private String target;

	public String getAddress() {
		return address;
	}

	public ClusterStateSingleVO setAddress(String address) {
		this.address = address;
		return this;
	}

	public Integer getMode() {
		return mode;
	}

	public ClusterStateSingleVO setMode(Integer mode) {
		this.mode = mode;
		return this;
	}

	public String getTarget() {
		return target;
	}

	public ClusterStateSingleVO setTarget(String target) {
		this.target = target;
		return this;
	}

	@Override
	public String toString() {
		return "ClusterStateSingleVO{" + "address='" + address + '\'' + ", mode=" + mode + ", target='" + target + '\''
				+ '}';
	}

}
