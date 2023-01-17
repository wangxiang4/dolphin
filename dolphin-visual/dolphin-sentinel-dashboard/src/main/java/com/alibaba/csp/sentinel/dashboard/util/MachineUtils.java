package com.alibaba.csp.sentinel.dashboard.util;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.csp.sentinel.util.function.Tuple2;

import java.util.Optional;

/**
 * @author Eric Zhao
 */
public final class MachineUtils {

	public static Optional<Integer> parseCommandPort(String machineIp) {
		try {
			if (!machineIp.contains("@")) {
				return Optional.empty();
			}
			String[] str = machineIp.split("@");
			if (str.length <= 1) {
				return Optional.empty();
			}
			return Optional.of(Integer.parseInt(str[1]));
		}
		catch (Exception ex) {
			return Optional.empty();
		}
	}

	public static Optional<Tuple2<String, Integer>> parseCommandIpAndPort(String machineIp) {
		try {
			if (StringUtil.isEmpty(machineIp) || !machineIp.contains("@")) {
				return Optional.empty();
			}
			String[] str = machineIp.split("@");
			if (str.length <= 1) {
				return Optional.empty();
			}
			return Optional.of(Tuple2.of(str[0], Integer.parseInt(str[1])));
		}
		catch (Exception ex) {
			return Optional.empty();
		}
	}

	private MachineUtils() {
	}

}
