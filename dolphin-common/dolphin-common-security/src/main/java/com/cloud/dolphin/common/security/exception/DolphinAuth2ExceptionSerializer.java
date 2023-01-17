package com.cloud.dolphin.common.security.exception;

import com.cloud.dolphin.common.core.constant.CommonConstants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.SneakyThrows;

/**
 *<p>
 * OAuth2 异常格式化
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
public class DolphinAuth2ExceptionSerializer extends StdSerializer<DolphinAuth2Exception> {

	public DolphinAuth2ExceptionSerializer() {
		super(DolphinAuth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(DolphinAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		gen.writeStartObject();
		gen.writeObjectField("code", CommonConstants.FAIL);
		gen.writeStringField("msg", value.getMessage());
		gen.writeStringField("data", value.getErrorCode());
		// 资源服务器会读取这个字段
		gen.writeStringField("error", value.getMessage());
		gen.writeEndObject();
	}

}
