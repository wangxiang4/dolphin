package com.cloud.dolphin.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *<p>
 * 监控中心
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@EnableAdminServer
@SpringBootApplication
public class DolphinVisualMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DolphinVisualMonitorApplication.class, args);
	}

}
