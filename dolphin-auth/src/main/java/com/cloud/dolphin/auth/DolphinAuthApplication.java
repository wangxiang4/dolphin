package com.cloud.dolphin.auth;

import com.cloud.dolphin.common.core.annotation.EnableDolphinJacksonAutoConvert;
import com.cloud.dolphin.common.feign.annotation.EnableDolphinFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *<p>
 * 认证授权中心
 *</p>
 *
 * @Author: 开发团队-王翔
 * @Date: 2022/2/16
 */
@EnableDolphinFeignClients
@SpringBootApplication
@EnableDolphinJacksonAutoConvert
public class DolphinAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(DolphinAuthApplication.class, args);
	}

}
