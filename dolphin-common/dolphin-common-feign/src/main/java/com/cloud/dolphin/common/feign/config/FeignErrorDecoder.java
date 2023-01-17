package com.cloud.dolphin.common.feign.config;

import com.alibaba.fastjson.JSON;
import com.cloud.dolphin.common.core.api.R;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *<p>
 * 自定义feign错误响应数据
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/5/16
 */
@Slf4j
@Configuration
public class FeignErrorDecoder extends ErrorDecoder.Default {

    @Override
    @SneakyThrows
    public Exception decode(String methodKey, Response response) {
        Exception exception = super.decode(methodKey, response);
        // 如果是RetryableException，则返回继续重试
        if (exception instanceof RetryableException) {
            return exception;
        }
        try {
            // 如果是FeignException,则对其进行处理,并抛出Exception
            if (exception instanceof FeignException && ((FeignException) exception).responseBody().isPresent()) {
                ByteBuffer responseBody = ((FeignException) exception).responseBody().get();
                String bodyText = StandardCharsets.UTF_8.newDecoder().decode(responseBody.asReadOnlyBuffer()).toString();
                R result = JSON.parseObject(bodyText, R.class);
                return new Exception(result.getMsg());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return exception;
    }

}
