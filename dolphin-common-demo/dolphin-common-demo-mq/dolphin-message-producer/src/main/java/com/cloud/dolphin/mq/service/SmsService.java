package com.cloud.dolphin.mq.service;

/**
 *<p>
 * 发送短消息业务类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/9
 */
public interface SmsService {

	/**
	 * 发送短消息
	 *
	 * @param message 　短消息
	 */
	void sendSms(String message);
}
