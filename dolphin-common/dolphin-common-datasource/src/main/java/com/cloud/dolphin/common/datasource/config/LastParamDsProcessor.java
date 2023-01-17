package com.cloud.dolphin.common.datasource.config;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import org.aopalliance.intercept.MethodInvocation;

/**
 *<p>
 * 修复 issues
 * 参数数据源解析 @DS("#last)
 * 查找当前方法中最后一个参数的值当作数据源名称
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/19
 */
public class LastParamDsProcessor extends DsProcessor {

	private static final String LAST_PREFIX = "#last";

	/**
	 * 抽象匹配条件 匹配才会走当前执行器否则走下一级执行器
	 * @param key DS注解里的内容
	 * @return 是否匹配
	 */
	@Override
	public boolean matches(String key) {
		if (key.startsWith(LAST_PREFIX)) {
			// https://github.com/baomidou/dynamic-datasource-spring-boot-starter/issues/213
			DynamicDataSourceContextHolder.clear();
			return true;
		}
		return false;
	}

	/**
	 * 抽象最终决定数据源
	 * @param invocation 方法执行信息
	 * @param key DS注解里的内容
	 * @return 数据源名称
	 */
	@Override
	public String doDetermineDatasource(MethodInvocation invocation, String key) {
		Object[] arguments = invocation.getArguments();
		return String.valueOf(arguments[arguments.length - 1]);
	}

}
