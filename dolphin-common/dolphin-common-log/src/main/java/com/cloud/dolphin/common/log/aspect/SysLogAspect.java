package com.cloud.dolphin.common.log.aspect;

import com.cloud.dolphin.common.core.util.SpringContextHolderUtil;
import com.cloud.dolphin.common.log.annotation.SysLog;
import com.cloud.dolphin.common.log.event.SysLogEvent;
import com.cloud.dolphin.common.log.menus.LogTypeEnum;
import com.cloud.dolphin.common.log.util.SysLogUtils;
import com.cloud.dolphin.monitor.api.entity.OperLog;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 *<p>
 * 操作日志使用spring event异步入库
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Aspect
@Slf4j
public class SysLogAspect {

	@Around("@annotation(sysLog)")
	@SneakyThrows
	public Object around(ProceedingJoinPoint point, SysLog sysLog) {
		Long startTime = System.currentTimeMillis();
		String strClassName = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);
		OperLog operLog = SysLogUtils.getSysLog();
		operLog.setTitle(sysLog.value());
		Object obj;
		try {
			obj = point.proceed();
		} catch (Exception e) {
			operLog.setType(LogTypeEnum.ERROR.getType());
			operLog.setErrorMsg(e.getMessage());
			throw e;
		} finally {
			Long endTime = System.currentTimeMillis();
			operLog.setExecuteTime((endTime - startTime) + "毫秒");
			// 发送异步日志事件
			SpringContextHolderUtil.publishEvent(new SysLogEvent(operLog));
		}
		return obj;
	}

}
