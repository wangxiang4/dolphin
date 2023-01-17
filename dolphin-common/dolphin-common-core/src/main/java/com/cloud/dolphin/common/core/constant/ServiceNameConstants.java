package com.cloud.dolphin.common.core.constant;

/**
 *<p>
 * 服务名称
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
public interface ServiceNameConstants {

	/**
	 * 认证服务的SERVICE_ID
	 */
	String AUTH_SERVICE = "dolphin-auth";

	/**
	 * SYSTEM模块
	 */
	String SYSTEM_SERVICE = "dolphin-system-biz";

	/**
	 * MONITOR模块
	 */
	String MONITOR_SERVICE = "dolphin-monitor-biz";

	/**
	 * COMMON模块
	 */
	String COMMON_SERVICE = "dolphin-common-biz";

	/**
	 * seata分布式事务演示-订单模块
	 */
	String SEATA_ORDER_SERVICE = "dolphin-seata-order";

	/**
	 * seata分布式事务演示-积分模块
	 */
	String SEATA_POINT_SERVICE = "dolphin-seata-point";


}
