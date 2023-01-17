package com.alibaba.csp.sentinel.dashboard.auth;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * A fake AuthService implementation, which will pass all user auth checking.
 *
 * @author Carpenter Lee
 * @since 1.5.0
 */
@Component
public class FakeAuthServiceImpl implements AuthService<HttpServletRequest> {

	@Override
	public AuthUser getAuthUser(HttpServletRequest request) {
		return new AuthUserImpl();
	}

	static final class AuthUserImpl implements AuthUser {

		@Override
		public boolean authTarget(String target, PrivilegeType privilegeType) {
			// fake implementation, always return true
			return true;
		}

		@Override
		public boolean isSuperUser() {
			// fake implementation, always return true
			return true;
		}

		@Override
		public String getNickName() {
			return "FAKE_NICK_NAME";
		}

		@Override
		public String getLoginName() {
			return "FAKE_LOGIN_NAME";
		}

		@Override
		public String getId() {
			return "FAKE_EMP_ID";
		}

	}

}
