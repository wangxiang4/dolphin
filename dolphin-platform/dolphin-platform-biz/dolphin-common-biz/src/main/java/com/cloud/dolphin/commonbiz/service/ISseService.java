package com.cloud.dolphin.commonbiz.service;


import com.cloud.dolphin.commonbiz.api.entity.SseSignalContainer;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 *<p>
 * SSE长连接 服务层
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @since: 2022/9/14
 */
public interface ISseService {

    /**
     * 采用长轮询订阅
     * @param clientId 客户端唯一Id
     * @return SseEmitter
     */
    SseEmitter SseSubscribe(String clientId);

    /**
     * sse发送消息
     * @param userId 用户Id
     * @param consumer 消费者发送函数
     * @return void
     */
    void sendMessage(String userId, SseEmitterConsumer<SseSignalContainer> consumer);

    /**
     * sse发送当前租户下所有在线客户端消息
     * @param consumer 消费者发送函数
     * @return void
     */
    void sendMessage(SseEmitterConsumer<SseSignalContainer> consumer);

    /**
     * 断开当前用户租户下所有客户端连接
     * @return void
     */
    void disconnect();

    /**
     * 断开当前用户连接
     * @param clientId 客户端唯一Id
     * @return void
     */
    void disconnect(String clientId);

    /**
     * SseEmitter消费函数
     */
    @FunctionalInterface
    interface SseEmitterConsumer<T> {
        void accept(T t) throws IOException;
    }

}
