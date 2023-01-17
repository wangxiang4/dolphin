package com.alibaba.csp.sentinel.dashboard.datasource.entity;

import com.alibaba.csp.sentinel.dashboard.discovery.AppInfo;

import java.util.Date;

/**
 * @author leyou
 */
public class ApplicationEntity {

	private Long id;

	private Date gmtCreate;

	private Date gmtModified;

	private String app;

	private Integer appType;

	private String activeConsole;

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

	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}

	public String getActiveConsole() {
		return activeConsole;
	}

	public Date getLastFetch() {
		return lastFetch;
	}

	public void setLastFetch(Date lastFetch) {
		this.lastFetch = lastFetch;
	}

	public void setActiveConsole(String activeConsole) {
		this.activeConsole = activeConsole;
	}

	public AppInfo toAppInfo() {
		return new AppInfo(app, appType);
	}

	@Override
	public String toString() {
		return "ApplicationEntity{" + "id=" + id + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ ", app='" + app + '\'' + ", activeConsole='" + activeConsole + '\'' + ", lastFetch=" + lastFetch
				+ '}';
	}

}
