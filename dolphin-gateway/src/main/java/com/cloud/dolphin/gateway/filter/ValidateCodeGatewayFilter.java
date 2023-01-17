package com.cloud.dolphin.gateway.filter;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.cloud.dolphin.common.core.constant.CacheConstants;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.cloud.dolphin.common.core.exception.ValidateCodeException;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.util.WebUtil;
import com.cloud.dolphin.gateway.config.GatewayConfigProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 *<p>
 * 网关验证验证码是否合法过滤器。
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Slf4j
@RequiredArgsConstructor
public class ValidateCodeGatewayFilter extends AbstractGatewayFilterFactory {

	private final GatewayConfigProperties configProperties;

	private final ObjectMapper objectMapper;

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			boolean isAuthToken = CharSequenceUtil.containsAnyIgnoreCase(request.getURI().getPath(), SecurityConstants.OAUTH_TOKEN_URL);

			// 不是登录请求，直接向下执行
			if (!isAuthToken) {
				return chain.filter(exchange);
			}

			// 刷新token,手机号登录（也可以这里进行校验） 直接向下执行
			String grantType = request.getQueryParams().getFirst("grant_type");
			if (StrUtil.equals(SecurityConstants.REFRESH_TOKEN, grantType)) {
				return chain.filter(exchange);
			}

			boolean isIgnoreClient = configProperties.getIgnoreClients().contains(WebUtil.getClientId(request));
			try {
				// 只有oauth和请求不在忽略客户端需要检查代码。
				if (!isIgnoreClient) {
					checkCode(request);
				}
			}
			catch (Exception e) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
				response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

				final String errMsg = e.getMessage();
				return response.writeWith(Mono.create(monoSink -> {
					try {
						byte[] bytes = objectMapper.writeValueAsBytes(R.error(errMsg));
						DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);

						monoSink.success(dataBuffer);
					}
					catch (JsonProcessingException jsonProcessingException) {
						log.error("对象输出异常", jsonProcessingException);
						monoSink.error(jsonProcessingException);
					}
				}));
			}

			return chain.filter(exchange);
		};
	}

	/** 处理验证码是否合法 */
	@SneakyThrows
	private void checkCode(ServerHttpRequest request) {
		// 设置不区分大小写,全部以小写验证
		String code = request.getQueryParams().getFirst("code").toLowerCase();

		if (CharSequenceUtil.isBlank(code)) {
			throw new ValidateCodeException("验证码不能为空");
		}

		String realKey = request.getQueryParams().getFirst("realKey");

		String key = CacheConstants.VERIFICATION_CODE + realKey;

		Object codeObj = redisTemplate.opsForValue().get(key);

		redisTemplate.delete(key);

		if (ObjectUtil.isEmpty(codeObj) || !code.equals(codeObj)) {
			throw new ValidateCodeException("验证码不合法");
		}
	}

}
