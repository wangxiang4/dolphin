package com.alibaba.csp.sentinel.dashboard.auth;

/**
 * Interface for authentication and authorization.
 *
 * @author Carpenter Lee
 * @since 1.5.0
 */
public interface AuthService<R> {

	/**
	 * Get the authentication user.
	 * @param request the request contains the user information
	 * @return the auth user represent the current user, when the user is illegal, a null
	 * value will return.
	 */
	AuthUser getAuthUser(R request);

	/**
	 * Privilege type.
	 */
	enum PrivilegeType {

		/**
		 * Read rule
		 */
		READ_RULE,
		/**
		 * Create or modify rule
		 */
		WRITE_RULE,
		/**
		 * Delete rule
		 */
		DELETE_RULE,
		/**
		 * Read metrics
		 */
		READ_METRIC,
		/**
		 * Add machine
		 */
		ADD_MACHINE,
		/**
		 * All privileges above are granted.
		 */
		ALL

	}

	/**
	 * Represents the current user.
	 */
	interface AuthUser {

		/**
		 * Query whether current user has the specific privilege to the target, the target
		 * may be an app name or an ip address, or other destination.
		 * <p>
		 * This method will use return value to represent whether user has the specific
		 * privileges to the target, but to throw a RuntimeException to represent no auth
		 * is also a good way.
		 * </p>
		 * @param target the target to check
		 * @param privilegeType the privilege type to check
		 * @return if current user has the specific privileges to the target, return true,
		 * otherwise return false.
		 */
		boolean authTarget(String target, PrivilegeType privilegeType);

		/**
		 * Check whether current user is a super-user.
		 * @return if current user is super user return true, else return false.
		 */
		boolean isSuperUser();

		/**
		 * Get current user's nick name.
		 * @return current user's nick name.
		 */
		String getNickName();

		/**
		 * Get current user's login name.
		 * @return current user's login name.
		 */
		String getLoginName();

		/**
		 * Get current user's ID.
		 * @return ID of current user
		 */
		String getId();

	}

}
