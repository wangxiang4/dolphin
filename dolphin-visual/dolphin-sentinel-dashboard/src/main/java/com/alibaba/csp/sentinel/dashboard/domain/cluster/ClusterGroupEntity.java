package com.alibaba.csp.sentinel.dashboard.domain.cluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Eric Zhao
 * @since 1.4.1
 */
public class ClusterGroupEntity {

	private String machineId;

	private String ip;

	private Integer port;

	private Set<String> clientSet = new HashSet<>();

	private Boolean belongToApp;

	public String getMachineId() {
		return machineId;
	}

	public ClusterGroupEntity setMachineId(String machineId) {
		this.machineId = machineId;
		return this;
	}

	public String getIp() {
		return ip;
	}

	public ClusterGroupEntity setIp(String ip) {
		this.ip = ip;
		return this;
	}

	public Integer getPort() {
		return port;
	}

	public ClusterGroupEntity setPort(Integer port) {
		this.port = port;
		return this;
	}

	public Set<String> getClientSet() {
		return clientSet;
	}

	public ClusterGroupEntity setClientSet(Set<String> clientSet) {
		this.clientSet = clientSet;
		return this;
	}

	public Boolean getBelongToApp() {
		return belongToApp;
	}

	public ClusterGroupEntity setBelongToApp(Boolean belongToApp) {
		this.belongToApp = belongToApp;
		return this;
	}

	@Override
	public String toString() {
		return "ClusterGroupEntity{" + "machineId='" + machineId + '\'' + ", ip='" + ip + '\'' + ", port=" + port
				+ ", clientSet=" + clientSet + ", belongToApp=" + belongToApp + '}';
	}

}
