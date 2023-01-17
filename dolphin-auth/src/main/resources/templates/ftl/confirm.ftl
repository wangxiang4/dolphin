<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>海豚微服务开放平台 第三方授权</title>
    <!--使用/oauth/authorize获取返回页面-->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/signin.css"/>
</head>

<body>
<nav class="navbar navbar-default container-fluid">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">快捷登录 海豚微服务开放平台</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-5">
            <p class="navbar-text navbar-right">
                <a href="javascript:void(0)">技术支持</a>
            </p>
            <p class="navbar-text navbar-right">
                当前用户:${user.username}
            </p>
        </div>
    </div>
</nav>
<div style="padding-top: 80px;width: 300px; color: #555; margin:0px auto;">
    <form id='confirmationForm' name='confirmationForm' action="/oauth/authorize" method='post'>
        <input name='user_oauth_approval' value='true' type='hidden'/>
        <p>
            <a href="${app.website!''}" target="_blank">${app.appName!'未定义应用名称'}</a> 将获得以下权限：</p>
            <ul class="list-group">
                <li class="list-group-item"> <span>
                  <#list scopeList as scope>
                      <input type="hidden" name="${scope}" value="true"/>
                      <input type="checkbox" checked="checked"/><label>${scope}</label>
                  </#list>
            </ul>
            <p class="help-block">授权后表明你已同意 <a href="javascript:void(0)">服务协议</a></p>
            <button class="btn btn-success pull-right" type="submit" id="write-email-btn">授权</button>
        </p>
    </form>
</div>
<footer>
    <p>关于: 海豚生态开源社区</p>
    <p>邮箱: <a href="mailto:1827945911@qq.com">联系我们</a>.</p>
</footer>
</body>
</html>
