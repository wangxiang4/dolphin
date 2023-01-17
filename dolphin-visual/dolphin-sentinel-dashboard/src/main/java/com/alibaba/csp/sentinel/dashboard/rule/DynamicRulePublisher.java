package com.alibaba.csp.sentinel.dashboard.rule;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
public interface DynamicRulePublisher<T> {

	/**
	 * Publish rules to remote rule configuration center for given application name.
	 * @param app app name
	 * @param rules list of rules to push
	 * @throws Exception if some error occurs
	 */
	void publish(String app, T rules) throws Exception;

}
