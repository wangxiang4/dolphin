package com.cloud.dolphin.commonbiz.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.util.StrUtil;
import com.cloud.dolphin.common.core.exception.CommonException;
import com.cloud.dolphin.common.data.entity.DolphinUser;
import com.cloud.dolphin.common.security.util.SecurityUtils;
import com.cloud.dolphin.commonbiz.api.entity.SseSignalContainer;
import com.cloud.dolphin.commonbiz.service.ISseService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * SSE 服务实现类
 * </p>
 *
 * @author entfrm开发团队-王翔
 * @since 2022-07-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SseServiceImpl implements ISseService {

    /** 存储 sse 上下文容器 */
    private static Set<SseSignalContainer> sseSignalContainers  = new ConcurrentHashSet();

    /**
     * 间隔10秒发送心跳包,检查客户端是否关闭
     */
    @XxlJob("doHeartbeat")
    public void doHeartbeat() {
        doMaintenance();
    }

    @Override
    @SneakyThrows
    public SseEmitter SseSubscribe(String clientId) {
        DolphinUser dolphinUser = getUser();
        Optional.ofNullable(clientId).orElseThrow(() -> new CommonException("当前客户端Id为空,请检查后重试!"));
        // 设置超时时间为1小时
        SseEmitter sseEmitter = new SseEmitter(3600_000L);
        SseSignalContainer sseSignalContainer =new SseSignalContainer(
                clientId,
                dolphinUser.getId(),
                sseEmitter,
                dolphinUser.getTenantId()
        );
        // 设置如果网络出错前端请求的重试时间为1s
        sseEmitter.send(SseEmitter.event().data("创建通道连接成功").reconnectTime(1000));
        sseSignalContainers.add(sseSignalContainer);
        log.info("clientId:{},建立的用户Id为:{}", clientId, dolphinUser.getId());
        sseEmitter.onTimeout(() -> {
            log.info("clientId:{},用户Id为:{},的SSE长轮询已经超时,正在删除当前的建立通道对象", clientId, dolphinUser.getId());
            sseEmitter.complete();
            sseSignalContainers.remove(sseSignalContainer);
        });
        sseEmitter.onCompletion(() -> {
            log.info("clientId:{},用户Id为:{}的SSE长轮询已经返回响应关闭,正在删除当前的建立通道对象", clientId, dolphinUser.getId());
            sseSignalContainers.remove(sseSignalContainer);
        });
        sseEmitter.onError(e -> log.info("clientId:{},当前用户Id为:{}的SSE长轮询出现异常,正在删除当前的建立通道对象,错误信息{}", clientId, dolphinUser.getId(), e.getLocalizedMessage()));
        return sseEmitter;
    }

    @Override
    public void sendMessage(String userId, SseEmitterConsumer<SseSignalContainer> consumer) {
        DolphinUser dolphinUser = getUser();
        Set<SseSignalContainer> sendSseSignalContainers = sseSignalContainers.stream()
                .filter(item -> StrUtil.equals(item.getUserId(), userId) && StrUtil.equals(item.getTenantId(), dolphinUser.getTenantId()))
                .collect(Collectors.toSet());
        send(sendSseSignalContainers, consumer);
    }

    @Override
    public void sendMessage(SseEmitterConsumer<SseSignalContainer> consumer) {
        DolphinUser dolphinUser = getUser();
        Set<SseSignalContainer> sendSseSignalContainers = sseSignalContainers.stream()
                .filter(item -> StrUtil.equals(item.getTenantId(), dolphinUser.getTenantId()))
                .collect(Collectors.toSet());
        send(sendSseSignalContainers, consumer);
    }

    @Override
    public void disconnect(String clientId) {
        DolphinUser dolphinUser = getUser();
        Optional.ofNullable(clientId).orElseThrow(() -> new CommonException("当前客户端Id为空,请检查后重试!"));
        Set<SseSignalContainer> sendSseSignalContainers = sseSignalContainers.stream()
                .filter(item -> StrUtil.equals(item.getClientId(), clientId) && StrUtil.equals(item.getTenantId(), dolphinUser.getTenantId()))
                .collect(Collectors.toSet());
        sendSseSignalContainers.forEach(item -> item.getSseEmitter().complete());
        sseSignalContainers.removeAll(sendSseSignalContainers);
    }

    @Override
    public void disconnect() {
        DolphinUser dolphinUser = getUser();
        Set<SseSignalContainer> sendSseSignalContainers = sseSignalContainers.stream()
                .filter(item -> StrUtil.equals(item.getTenantId(), dolphinUser.getTenantId()))
                .collect(Collectors.toSet());
        sendSseSignalContainers.forEach(item -> item.getSseEmitter().complete());
        sseSignalContainers.removeAll(sendSseSignalContainers);
    }

    /**
     * 执行心跳维护,避免 sse 膨胀容量问题
     */
    private void doMaintenance() {
        Iterator<SseSignalContainer> it = sseSignalContainers.iterator();
        while (it.hasNext()) {
            SseSignalContainer item = it.next();
            try {
                item.getSseEmitter().send(SseEmitter.event().comment("保持心跳" + LocalDateTime.now()).reconnectTime(1000));
            } catch (IOException e) {
                item.getSseEmitter().completeWithError(e);
                sseSignalContainers.remove(item);
                log.debug("clientId:{},用户Id为:{}发送心跳包失败", item.getClientId(), item.getUserId());
            }
        }
    }

    /**
     * 发送消息
     * @param sendSseSignalContainers SseEmitter发送集合
     * @param consumer 消费者发送函数
     * @return Set<SseSignalContainer> 失败集合字段
     */
    private Set<SseSignalContainer> send(Set<SseSignalContainer> sendSseSignalContainers, SseEmitterConsumer<SseSignalContainer> consumer) {
        Set<SseSignalContainer> failedEmitters = new ConcurrentHashSet();
        Iterator<SseSignalContainer> it = sendSseSignalContainers.iterator();
        while (it.hasNext()) {
            SseSignalContainer item = it.next();
            try {
                consumer.accept(item);
            } catch (Exception e) {
                item.getSseEmitter().completeWithError(e);
                failedEmitters.add(item);
                log.error("Emitter failed: {}", item.getSseEmitter(), e);
            }
        }
        sseSignalContainers.removeAll(failedEmitters);
        return failedEmitters;
    }

    private DolphinUser getUser() {
        DolphinUser dolphinUser = SecurityUtils.getUser();
        Optional.ofNullable(dolphinUser).orElseThrow(() -> new CommonException("当前用户登录,请先登录后重试!"));
        return dolphinUser;
    }

}
