package com.alibaba.csp.sentinel.dashboard.discovery;

import com.alibaba.csp.sentinel.util.AssertUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author leyou
 */
@Component
public class SimpleMachineDiscovery implements MachineDiscovery {

	private final ConcurrentMap<String, AppInfo> apps = new ConcurrentHashMap<>();

	@Override
	public long addMachine(MachineInfo machineInfo) {
		AssertUtil.notNull(machineInfo, "machineInfo cannot be null");
		AppInfo appInfo = apps.computeIfAbsent(machineInfo.getApp(),
				o -> new AppInfo(machineInfo.getApp(), machineInfo.getAppType()));
		appInfo.addMachine(machineInfo);
		return 1;
	}

	@Override
	public boolean removeMachine(String app, String ip, int port) {
		AssertUtil.assertNotBlank(app, "app name cannot be blank");
		AppInfo appInfo = apps.get(app);
		if (appInfo != null) {
			return appInfo.removeMachine(ip, port);
		}
		return false;
	}

	@Override
	public List<String> getAppNames() {
		return new ArrayList<>(apps.keySet());
	}

	@Override
	public AppInfo getDetailApp(String app) {
		AssertUtil.assertNotBlank(app, "app name cannot be blank");
		return apps.get(app);
	}

	@Override
	public Set<AppInfo> getBriefApps() {
		return new HashSet<>(apps.values());
	}

	@Override
	public void removeApp(String app) {
		AssertUtil.assertNotBlank(app, "app name cannot be blank");
		apps.remove(app);
	}

}
