package com.cloud.dolphin.commonbiz.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *<p>
 * SSE 信号量容器
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @since: 2022/9/15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
public class SseSignalContainer {

    private String clientId;

    private String userId;

    private SseEmitter sseEmitter;

    protected String tenantId;

}
