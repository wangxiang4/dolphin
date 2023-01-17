package com.cloud.dolphin.gateway;

import com.cloud.dolphin.common.swagger.annotation.EnableDolphinSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *<p>
 * 网关应用
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@EnableDolphinSwagger2
@SpringBootApplication
public class DolphinGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DolphinGatewayApplication.class, args);
	}

}
