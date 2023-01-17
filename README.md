<h1 align="center">
   <b>
        <a href="https://axios-http.com"><img src="https://godolphinx.org/images/dolphin-platform-logo.svg" /></a><br>
    </b>
</h1>

<p align="center"> 一个快速开发软件的平台 </p>

<p align="center">
    <a href="https://godolphinx.org/"><b>Website</b></a> •
    <a href="https://godolphinx.org/microservice/description.html"><b>Documentation</b></a>
</p>

<div align="center">
  <a href="https://github.com/wangxiang4/dolphin-ecology-docs/actions">
    <img src="https://github.com/wangxiang4/dolphin-ecology-docs/workflows/Deploy%20Docs/badge.svg">
  </a>
  <a href="https://godolphinx.org">
    <img src="https://img.shields.io/npm/l/vue.svg?sanitize=true">
  </a>
  <a href="https://gitpod.io/#https://github.com/wangxiang4/dolphin-ecology-docs">
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
- 主体框架：采用最新的`Spring Cloud 2021.0.1`, `Spring Boot 2.6.4`, `Spring Cloud Alibaba 2021.1`版本进行系统设计；

- 统一注册：支持`Nacos`作为注册中心，实现多配置、分群组、分命名空间、多业务模块的注册和发现功能；

- 统一认证：统一`Oauth2`认证协议，并支持自定义grant_type实现手机号码登录，第三方登录集成JustAuth实现微信、支付宝等多种登录模式；

- 业务监控：利用`Spring Boot Admin`来监控各个独立Service的运行状态。

- 内部调用：集成了`Feign`与自定义内部注解，支持内部调用。

- 业务熔断：采用`Sentinel`实现业务熔断处理，避免服务之间出现雪崩;

- 在线文档：通过接入`Knife4j`，实现在线API文档的查看与调试;

- 业务分离：采用前后端分离的框架设计，前端采用基于 `vben Admin` 的 `dolphin-ui`

- 多租户功能：集成`Mybatis Plus`,实现SAAS多租户功能

## <img width="28" style="vertical-align:middle" src="https://godolphinx.org/images/hacktoberfest-logo.svg"> 黑客节
加入[Github HackToberFest](https://hacktoberfest.com/) 开始为此项目做出贡献.

## 🔨 开发目录

```
├─dolphin -- 父项目,各模块分离，方便集成和微服务
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
│  │─dolphin-gateway -- 服务网关，基于 spring cloud gateway
│  │─dolphin-register -- 注册配置中心
│  │─dolphin-system -- 通用系统权限管理聚合模块
│  │─dolphin-visual 图形化相关功能
```


## 🤔 一起讨论
加入我们的 [Discord](https://discord.gg/DREuQWrRYQ) 开始与大家交流。

## 🤗 我想成为开发团队的一员！
欢迎😀！我们正在寻找有才华的开发者加入我们，让海豚开发平台变得更好！如果您想加入开发团队，请联系我们，非常欢迎您加入我们！💖

## 在线一键设置
您可以使用 Gitpod，一个在线 IDE（开源免费）来在线贡献或运行示例。

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/wangxiang4/dolphin-ecology-docs)

## 📄 执照
[Dolphin Development Platform 是获得MIT许可](https://github.com/wangxiang4/dolphin-ecology-docs/blob/master/LICENSE) 的开源软件 。


