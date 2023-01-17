package com.cloud.dolphin.template;

import com.cloud.dolphin.common.core.annotation.EnableDolphinJacksonAutoConvert;
import com.cloud.dolphin.common.feign.annotation.EnableDolphinFeignClients;
import com.cloud.dolphin.common.security.annotation.EnableDolphinResourceServer;
import com.cloud.dolphin.common.swagger.annotation.EnableDolphinSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *<p>
 * 此处填写自己的业务模块名称
 * 规范:【xxx模块】
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/5/4
 */
@EnableDolphinSwagger2
@EnableDolphinResourceServer
@EnableDolphinFeignClients
@SpringBootApplication
@EnableDolphinJacksonAutoConvert
public class DolphinTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(DolphinTemplateApplication.class, args);
	}

}
