package com.alibaba.csp.sentinel.dashboard.discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Component
public class AppManagement implements MachineDiscovery {

	@Autowired
	private ApplicationContext context;

	private MachineDiscovery machineDiscovery;

	@PostConstruct
	public void init() {
		machineDiscovery = context.getBean(SimpleMachineDiscovery.class);
	}

	@Override
	public Set<AppInfo> getBriefApps() {
		return machineDiscovery.getBriefApps();
	}

	@Override
	public long addMachine(MachineInfo machineInfo) {
		return machineDiscovery.addMachine(machineInfo);
	}

	@Override
	public boolean removeMachine(String app, String ip, int port) {
		return machineDiscovery.removeMachine(app, ip, port);
	}

	@Override
	public List<String> getAppNames() {
		return machineDiscovery.getAppNames();
	}

	@Override
	public AppInfo getDetailApp(String app) {
		return machineDiscovery.getDetailApp(app);
	}

	@Override
	public void removeApp(String app) {
		machineDiscovery.removeApp(app);
	}

}
