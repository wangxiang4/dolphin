package com.cloud.dolphin.gateway.handler;

import cn.hutool.crypto.SecureUtil;
import com.cloud.dolphin.common.core.constant.CacheConstants;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.pig4cloud.captcha.SpecCaptcha;
import com.pig4cloud.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 *<p>
 * 验证码生成逻辑处理类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Slf4j
@RequiredArgsConstructor
public class ImageCodeHandler implements HandlerFunction<ServerResponse> {

	private static final Integer DEFAULT_IMAGE_WIDTH = 105;

	private static final Integer DEFAULT_IMAGE_HEIGHT = 35;

	private static final Integer DEFAULT_IMAGE_LEN = 4;

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	@SneakyThrows
	public Mono<ServerResponse> handle(ServerRequest serverRequest) {
		SpecCaptcha specCaptcha = new SpecCaptcha(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT, DEFAULT_IMAGE_LEN);
		// 设置内置字体
		specCaptcha.setFont(Captcha.FONT_5);
		// 设置不区分大小写,全部以小写验证
		String code = specCaptcha.text().toLowerCase();

		// 保存验证码信息
		Optional<String> key = serverRequest.queryParam("key");
		String realKey = SecureUtil.md5(code + key);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		key.ifPresent(v -> redisTemplate.opsForValue().set(CacheConstants.VERIFICATION_CODE + realKey, code, SecurityConstants.CODE_TIME, TimeUnit.SECONDS));

		// 转换信息写出
		Map<String, String> result = new HashMap<>();
		result.put("img", specCaptcha.toBase64());
		result.put("realKey", realKey);
		return ServerResponse
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(result));
	}

}
