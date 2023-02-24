<h1 align="center">
   <b>
       <a href="https://godolphinx.org"><img src="https://godolphinx.org/images/dolphin-platform-logo.svg" /></a><br>
   </b>
</h1>

<p align="center"> 一个快速开发软件的平台 </p>

<p align="center">
    <a href="https://godolphinx.org/"><b>Website</b></a> •
    <a href="https://godolphinx.org/microservice/description.html"><b>Documentation</b></a>
</p>

<div align="center">
  <a href="https://godolphinx.org">
    <img src="https://img.shields.io/npm/l/vue.svg?sanitize=true">
  </a>
  <a href="https://gitpod.io/#https://github.com/wangxiang4/dolphin">
    <img src="https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod&style=flat-square">
  </a>
  <a href="https://discord.gg/DREuQWrRYQ">
    <img src="https://img.shields.io/badge/chat-on%20discord-7289da.svg?sanitize=true"/>
  </a>
</div>

## 🐬 介绍
海豚生态计划-打造一个web端,安卓端,ios端的一个海豚开发平台生态圈,不接收任何商业化,并且完全免费开源(包含高级功能)。

## 💪 愿景
让人人都可以快速高效的开发软件

## ✨ 特性
- 主体框架：采用最新的`Spring Cloud 2021.0.1`, `Spring Boot 2.6.4`, `Spring Cloud Alibaba 2021.1`版本进行系统设计。
- 统一注册：支持`Nacos`作为注册中心，实现多配置、分群组、分命名空间、多业务模块的注册和发现功能。
- 统一认证：统一`Oauth2`认证协议，并支持自定义grant_type实现手机号码登录，第三方登录集成JustAuth实现微信、支付宝等多种登录模式。
- 业务监控：利用`Spring Boot Admin`来监控各个独立微服务运行状态。
- 内部调用：集成了`Feign`与自定义内部注解，支持内部调用。
- 业务熔断：采用`Sentinel`实现业务熔断处理，避免服务之间出现雪崩。
- 在线文档：通过接入`Knife4j`，实现在线API文档的查看与调试。
- 业务分离：采用前后端分离的框架设计，提高开发效率、降低维护成本、增强系统稳定性和灵活性。
- 多租户功能：集成`Mybatis Plus`,自定义sql执行拦截器，实现SAAS多租户。
- 消息中间件：采用`RocketMQ`,实现服务之间消息转发。
- 分布式事物方案：采用`seata`,实现多个微服务分布式事物一致。
- 分布式定时器：采用`XXL-JOB`,实现多个微服务分布式任务调度。
- 微服务网关：采用`Spring Gateway`实现流量配置动态化、API管理和路由、负载均衡和容错、解决跨域问题、鉴权，限流，熔断，防火墙等等。

## <img width="28" style="vertical-align:middle" src="https://godolphinx.org/images/hacktoberfest-logo.svg"> 黑客节
加入[Github HackToberFest](https://hacktoberfest.com/) 开始为此项目做出贡献.

## 🔨 开发目录

```
├─dolphin -- 父项目,各模块分离,方便微服务扩展
│  ├─doc -- 文档数据-包含项目的一些数据资料
│  ├─docker-cloud -- docker-compose容器配置
│  ├─dolphin-auth -- 认证授权中心,基于 spring security oAuth2
│  ├─dolphin-common -- 公共通用模块，主模块
│  │  ├─dolphin-common-bom -- 全局jar BOM标准定义
│  │  ├─dolphin-common-core -- 公共工具类核心包
│  │  ├─dolphin-common-data -- 数据服务核心包
│  │  ├─dolphin-common-datasource -- 动态切换数据源组件
│  │  ├─dolphin-common-feign -- feign-sentinel服务降级熔断、限流组件
│  │  ├─dolphin-common-job -- 定时任务,基于xxl-job
│  │  ├─dolphin-common-log -- 日志服务
│  │  ├─dolphin-common-mock -- 单元模拟测试工具类
│  │  ├─dolphin-common-rocketmq -- 阿里 rocketmq 消息中间件
│  │  ├─dolphin-common-seata -- 阿里巴巴-seata分布式事务解决方案
│  │  ├─dolphin-common-security -- 安全工具类
│  │  ├─dolphin-common-swagger -- 接口文档
│  │─dolphin-common-demo -- 组件使用案列 
│  │  ├─dolphin-common-demo-mq -- 消息中心间演示
│  │  ├─dolphin-common-demo-seata -- 分布式事务解决方案演示
│  │─dolphin-gateway -- 服务网关，基于 spring cloud gateway
│  │─ddolphin-platform -- 微服务平台
│  │  ├─dolphin-platform-api -- 微服务api调用(添加调用的微服务api依赖库,实现调用)
│  │  │  ├─dolphin-common-api -- 通用业务模块公共api模块
│  │  │  ├─dolphin-monitor-api -- 运维监控api模块
│  │  │  ├─dolphin-system-api -- 系统api模块
│  │  │  ├─dolphin-template-api -- 新建api模块模板,只提供基础依赖
│  │  ├─dolphin-platform-biz -- 微服务业务模块
│  │  │  ├─dolphin-common-biz -- 通用业务模块
│  │  │  ├─dolphin-monitor-biz -- 运维监控业务模块
│  │  │  ├─dolphin-system-biz -- 系统业务模块
│  │  │  ├─dolphin-template-biz -- 新建业务模块模板,只提供基础依赖
│  │─dolphin-register -- 注册配置中心
│  │─dolphin-visual 可视化图形界面
│  │  ├─dolphin-rocketmq-dashboard -- RocketMQ可视化监控平台
│  │  ├─dolphin-sentinel-dashboard -- 哨兵流量控制可视化平台
│  │  ├─dolphin-spring-dashboard -- SpringBoot可视化监控平台
│  │  ├─dolphin-xxl-job-admin -- XXL-JOB可视化监控平台
```

## 🤔 一起讨论
加入我们的 [Discord](https://discord.gg/DREuQWrRYQ) 开始与大家交流。

## 🤗 我想成为开发团队的一员！
欢迎😀！我们正在寻找有才华的开发者加入我们，让海豚开发平台变得更好！如果您想加入开发团队，请联系我们，非常欢迎您加入我们！💖

## 在线一键设置
您可以使用 Gitpod，一个在线 IDE（开源免费）来在线贡献或运行示例。

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/wangxiang4/dolphin)

## 📄 执照
[Dolphin Development Platform 是获得MIT许可](https://github.com/wangxiang4/dolphin/blob/master/LICENSE) 的开源软件 。


