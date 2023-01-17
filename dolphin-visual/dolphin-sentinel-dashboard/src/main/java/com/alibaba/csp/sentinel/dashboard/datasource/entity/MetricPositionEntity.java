package com.alibaba.csp.sentinel.dashboard.datasource.entity;

import java.util.Date;

/**
 * @author leyou
 */
public class MetricPositionEntity {

	private long id;

	private Date gmtCreate;

	private Date gmtModified;

	private String app;

	private String ip;

	/**
	 * Sentinel在该应用上使用的端口
	 */
	private int port;

	/**
	 * 机器名，冗余字段
	 */
	private String hostname;

	/**
	 * 上一次拉取的最晚时间戳
	 */
	private Date lastFetch;

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

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Date getLastFetch() {
		return lastFetch;
	}

	public void setLastFetch(Date lastFetch) {
		this.lastFetch = lastFetch;
	}

	@Override
	public String toString() {
		return "MetricPositionEntity{" + "id=" + id + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ ", app='" + app + '\'' + ", ip='" + ip + '\'' + ", port=" + port + ", hostname='" + hostname + '\''
				+ ", lastFetch=" + lastFetch + '}';
	}

}
