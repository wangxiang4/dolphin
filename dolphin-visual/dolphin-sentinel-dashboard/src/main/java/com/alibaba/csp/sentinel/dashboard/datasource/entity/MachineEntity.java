package com.alibaba.csp.sentinel.dashboard.datasource.entity;

import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;

import java.util.Date;

/**
 * @author leyou
 */
public class MachineEntity {

	private Long id;

	private Date gmtCreate;

	private Date gmtModified;

	private String app;

	private String ip;

	private String hostname;

	private Date timestamp;

	private Integer port;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public MachineInfo toMachineInfo() {
		MachineInfo machineInfo = new MachineInfo();

		machineInfo.setApp(app);
		machineInfo.setHostname(hostname);
		machineInfo.setIp(ip);
		machineInfo.setPort(port);
		machineInfo.setLastHeartbeat(timestamp.getTime());
		machineInfo.setHeartbeatVersion(timestamp.getTime());

		return machineInfo;
	}

	@Override
	public String toString() {
		return "MachineEntity{" + "id=" + id + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", app='"
				+ app + '\'' + ", ip='" + ip + '\'' + ", hostname='" + hostname + '\'' + ", timestamp=" + timestamp
				+ ", port=" + port + '}';
	}

}
