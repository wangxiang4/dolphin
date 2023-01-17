package com.alibaba.csp.sentinel.dashboard.util;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.SentinelVersion;
import com.alibaba.csp.sentinel.util.StringUtil;

import java.util.Optional;

/**
 * Util class for parsing version.
 *
 * @author Eric Zhao
 * @since 0.2.1
 */
public final class VersionUtils {

	/**
	 * Parse version of Sentinel from raw string.
	 * @param versionFull version string
	 * @return parsed {@link SentinelVersion} if the version is valid; empty if there is
	 * something wrong with the format
	 */
	public static Optional<SentinelVersion> parseVersion(String s) {
		if (StringUtil.isBlank(s)) {
			return Optional.empty();
		}
		try {
			String versionFull = s;
			SentinelVersion version = new SentinelVersion();

			// postfix
			int index = versionFull.indexOf("-");
			if (index == 0) {
				// Start with "-"
				return Optional.empty();
			}
			if (index == versionFull.length() - 1) {
				// End with "-"
			}
			else if (index > 0) {
				version.setPostfix(versionFull.substring(index + 1));
			}

			if (index >= 0) {
				versionFull = versionFull.substring(0, index);
			}

			// x.x.x
			int segment = 0;
			int[] ver = new int[3];
			while (segment < ver.length) {
				index = versionFull.indexOf('.');
				if (index < 0) {
					if (versionFull.length() > 0) {
						ver[segment] = Integer.valueOf(versionFull);
					}
					break;
				}
				ver[segment] = Integer.valueOf(versionFull.substring(0, index));
				versionFull = versionFull.substring(index + 1);
				segment++;
			}

			if (ver[0] < 1) {
				// Wrong format, return empty.
				return Optional.empty();
			}
			else {
				return Optional.of(version.setMajorVersion(ver[0]).setMinorVersion(ver[1]).setFixVersion(ver[2]));
			}
		}
		catch (Exception ex) {
			// Parse fail, return empty.
			return Optional.empty();
		}
	}

	private VersionUtils() {
	}

}
