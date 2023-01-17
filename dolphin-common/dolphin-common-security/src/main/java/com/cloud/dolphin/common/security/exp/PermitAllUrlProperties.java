package com.cloud.dolphin.common.security.exp;

import cn.hutool.core.util.ReUtil;
import com.cloud.dolphin.common.security.annotation.Inner;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 *<p>
 * 资源服务器对外直接暴露URL,如果设置 contex-path 要特殊处理
 * 主要处理拿取加了Inner注解的方法上的url映射地址,以及配置文件中配置的security.oauth2.ignore.urls,进行对外开放不拦截
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Slf4j
@ConfigurationProperties(prefix = "security.oauth2.ignore")
public class PermitAllUrlProperties implements InitializingBean, ApplicationContextAware {

	private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

	private ApplicationContext applicationContext;

	@Getter
	@Setter
	private List<String> urls = new ArrayList<>();

	/** bean初始化后执行 */
	@Override
	public void afterPropertiesSet() {
		RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

		// 处理所有controller请求映射方法
		map.keySet().forEach(info -> {
			HandlerMethod handlerMethod = map.get(info);

			// 获取方法上边的注解,替代path variable 为 *,避免前端传递过来的参数变量被拦截
			Inner method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Inner.class);
				Optional.ofNullable(method).ifPresent(inner -> info.getPatternsCondition().getPatterns()
					.forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, "*"))));

			// 获取类上边的注解,替代path variable 为 *,避免前端传递过来的参数变量被拦截
			Inner controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Inner.class);
			Optional.ofNullable(controller).ifPresent(inner -> info.getPatternsCondition().getPatterns()
					.forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, "*"))));
		});
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.applicationContext = context;
	}

}
