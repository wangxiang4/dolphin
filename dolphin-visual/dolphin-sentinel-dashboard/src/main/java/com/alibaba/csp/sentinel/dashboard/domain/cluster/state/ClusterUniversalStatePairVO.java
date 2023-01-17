package com.alibaba.csp.sentinel.dashboard.domain.cluster.state;

/**
 * @author Eric Zhao
 * @since 1.4.1
 */
public class ClusterUniversalStatePairVO {

	private String ip;

	private Integer commandPort;

	private ClusterUniversalStateVO state;

	public ClusterUniversalStatePairVO() {
	}

	public ClusterUniversalStatePairVO(String ip, Integer commandPort, ClusterUniversalStateVO state) {
		this.ip = ip;
		this.commandPort = commandPort;
		this.state = state;
	}

	public String getIp() {
		return ip;
	}

	public ClusterUniversalStatePairVO setIp(String ip) {
		this.ip = ip;
		return this;
	}

	public Integer getCommandPort() {
		return commandPort;
	}

	public ClusterUniversalStatePairVO setCommandPort(Integer commandPort) {
		this.commandPort = commandPort;
		return this;
	}

	public ClusterUniversalStateVO getState() {
		return state;
	}

	public ClusterUniversalStatePairVO setState(ClusterUniversalStateVO state) {
		this.state = state;
		return this;
	}

	@Override
	public String toString() {
		return "ClusterUniversalStatePairVO{" + "ip='" + ip + '\'' + ", commandPort=" + commandPort + ", state=" + state
				+ '}';
	}

}
