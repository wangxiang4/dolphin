package com.cloud.dolphin.mq;

import com.cloud.dolphin.common.rocketmq.channel.DolphinSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 *<p>
 * 消息中心生产者启动类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/9
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableBinding({DolphinSource.class})
public class DolphinDemoMessageProducerServer {

	public static void main(String[] args) {
		SpringApplication.run(DolphinDemoMessageProducerServer.class, args);
	}

}
