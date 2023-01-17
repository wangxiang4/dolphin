package com.alibaba.csp.sentinel.dashboard.domain.cluster.state;

/**
 * @author Eric Zhao
 * @since 1.4.1
 */
public class AppClusterServerStateWrapVO {

	/**
	 * {ip}@{transport_command_port}.
	 */
	private String id;

	private String ip;

	private Integer port;

	private Integer connectedCount;

	private Boolean belongToApp;

	private ClusterServerStateVO state;

	public String getId() {
		return id;
	}

	public AppClusterServerStateWrapVO setId(String id) {
		this.id = id;
		return this;
	}

	public String getIp() {
		return ip;
	}

	public AppClusterServerStateWrapVO setIp(String ip) {
		this.ip = ip;
		return this;
	}

	public Integer getPort() {
		return port;
	}

	public AppClusterServerStateWrapVO setPort(Integer port) {
		this.port = port;
		return this;
	}

	public Boolean getBelongToApp() {
		return belongToApp;
	}

	public AppClusterServerStateWrapVO setBelongToApp(Boolean belongToApp) {
		this.belongToApp = belongToApp;
		return this;
	}

	public Integer getConnectedCount() {
		return connectedCount;
	}

	public AppClusterServerStateWrapVO setConnectedCount(Integer connectedCount) {
		this.connectedCount = connectedCount;
		return this;
	}

	public ClusterServerStateVO getState() {
		return state;
	}

	public AppClusterServerStateWrapVO setState(ClusterServerStateVO state) {
		this.state = state;
		return this;
	}

	@Override
	public String toString() {
		return "AppClusterServerStateWrapVO{" + "id='" + id + '\'' + ", ip='" + ip + '\'' + ", port='" + port + '\''
				+ ", belongToApp=" + belongToApp + ", state=" + state + '}';
	}

}
