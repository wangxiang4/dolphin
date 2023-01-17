package com.alibaba.nacos.config;

/**
 *<p>
 * 覆盖nacos 默认配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
public interface ConfigConstants {

	/**
	 * 服务端口,注意通过阅读官方文档,发现使用docker部署时一定要保持映射端口一致
	 * 不一样会出现本地服务访问不了服务端nacos: https://nacos.io/zh-cn/docs/2.0.0-compatibility.html
	 */
	String PORT = "server.port";

	/**
	 * The System property name of Standalone mode
	 */
	String STANDALONE_MODE = "nacos.standalone";

	/**
	 * 是否开启认证
	 */
	String AUTH_ENABLED = "nacos.core.auth.enabled";

	/**
	 * 日志目录
	 */
	String LOG_BASEDIR = "server.tomcat.basedir";

	/**
	 * access_log日志开关
	 */
	String LOG_ENABLED = "server.tomcat.accesslog.enabled";

}
