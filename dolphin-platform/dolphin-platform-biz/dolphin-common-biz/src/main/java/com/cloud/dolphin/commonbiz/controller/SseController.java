package com.cloud.dolphin.commonbiz.controller;

import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.commonbiz.service.ISseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.ServletResponse;

/**
 *<p>
 * SSE长连接 控制层
 * 长轮询: https://developer.aliyun.com/article/933937#slide-5
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @since: 2022/9/14
 */
@RestController
@RequestMapping(AppConstants.APP_COMMON + "/sse")
@RequiredArgsConstructor
@Api(tags = "SSE控制接口")
public class SseController {

    private final ISseService iSseService;


    @GetMapping("/subscribe")
    public SseEmitter subscribe(String clientId, ServletResponse response) {
        response.setContentType("text/event-stream;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        return iSseService.SseSubscribe(clientId);
    }

    @GetMapping("/sendMessage")
    public R sendMessage(String userId, String json) {
        iSseService.sendMessage(userId, sseSignalContainer -> sseSignalContainer.getSseEmitter().send(json));
        return R.ok();
    }

    @GetMapping("/sendTenantMessage")
    public R sendTenantMessage(String json) {
        iSseService.sendMessage(sseSignalContainer -> sseSignalContainer.getSseEmitter().send(json));
        return R.ok();
    }

    @GetMapping("/disconnect")
    public R disconnect(String clientId) {
        iSseService.disconnect(clientId);
        return R.ok();
    }

    @GetMapping("/disconnectTenant")
    public R disconnectTenant() {
        iSseService.disconnect();
        return R.ok();
    }

}
