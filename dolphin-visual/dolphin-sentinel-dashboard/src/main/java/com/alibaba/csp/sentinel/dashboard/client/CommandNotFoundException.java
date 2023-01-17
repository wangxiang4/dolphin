package com.alibaba.csp.sentinel.dashboard.client;

/**
 * @author Eric Zhao
 * @since 0.2.1
 */
public class CommandNotFoundException extends Exception {

	public CommandNotFoundException() {
	}

	public CommandNotFoundException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

}
