package com.cloud.dolphin.system;

import com.cloud.dolphin.common.core.annotation.EnableDolphinJacksonAutoConvert;
import com.cloud.dolphin.common.feign.annotation.EnableDolphinFeignClients;
import com.cloud.dolphin.common.security.annotation.EnableDolphinResourceServer;
import com.cloud.dolphin.common.swagger.annotation.EnableDolphinSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *<p>
 * 管理系统模块
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@EnableDolphinSwagger2
@EnableDolphinResourceServer
@EnableDolphinFeignClients
@SpringBootApplication
@EnableDolphinJacksonAutoConvert
public class DolphinSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DolphinSystemApplication.class, args);
	}

}
