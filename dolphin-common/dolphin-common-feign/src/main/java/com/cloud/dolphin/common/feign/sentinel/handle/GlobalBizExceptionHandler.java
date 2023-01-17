package com.cloud.dolphin.common.feign.sentinel.handle;

import com.alibaba.csp.sentinel.Tracer;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 *<p>
 * 全局异常处理器结合sentinel 全局异常处理器不能作用在 oauth server
 * 因为授权那边有自己的异常处理不能覆盖授权异常处理
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnExpression("!'${security.oauth2.client.clientId}'.isEmpty()")
public class GlobalBizExceptionHandler {

	/**
	 * 全局异常.
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R handleGlobalException(Exception e) {
		log.error("全局异常信息 ex={}", e.getMessage(), e);

		// 业务异常交由 sentinel 记录
		Tracer.trace(e);
		return R.error(e.getLocalizedMessage());
	}

	/**
	 * 通用前端错误提示自定义全局异常
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(CommonException.class)
	@ResponseStatus(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
	public R handleGlobalCommonException(CommonException e) {
		log.error("自定义异常信息 ex={}", e.getMessage(), e);

		// 业务异常交由 sentinel 记录
		Tracer.trace(e);
		return R.error(e.getLocalizedMessage());
	}

	/**
	 * 处理业务校验过程中碰到的非法参数异常 该异常基本由{@link org.springframework.util.Assert}抛出
	 * @see Assert#hasLength(String, String)
	 * @see Assert#hasText(String, String)
	 * @see Assert#isTrue(boolean, String)
	 * @see Assert#isNull(Object, String)
	 * @see Assert#notNull(Object, String)
	 * @param exception 参数校验异常
	 * @return API返回结果对象包装后的错误输出结果
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.OK)
	public R handleIllegalArgumentException(IllegalArgumentException exception) {
		log.error("非法参数,ex = {}", exception.getMessage(), exception);
		return R.error(exception.getMessage());
	}

	/**
	 * AccessDeniedException
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public R handleAccessDeniedException(AccessDeniedException e) {
		String msg = SpringSecurityMessageSource.getAccessor().getMessage("AbstractAccessDecisionManager.accessDenied",
				e.getMessage());
		log.warn("拒绝授权异常信息 ex={}", msg);
		return R.error(e.getLocalizedMessage());
	}

	/**
	 * validation Exception
	 * @param exception
	 * @return R
	 */
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R handleBodyValidException(MethodArgumentNotValidException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		log.warn("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
		return R.error(fieldErrors.get(0).getDefaultMessage());
	}

	/**
	 * validation Exception (以form-data形式传参)
	 * @param exception
	 * @return R
	 */
	@ExceptionHandler({ BindException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R bindExceptionHandler(BindException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		log.warn("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
		return R.error(fieldErrors.get(0).getDefaultMessage());
	}

}
