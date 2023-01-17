package com.alibaba.csp.sentinel.dashboard.rule;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
public interface DynamicRuleProvider<T> {

	T getRules(String appName) throws Exception;

}
