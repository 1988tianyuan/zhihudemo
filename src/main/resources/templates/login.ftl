<!DOCTYPE html>
<html lang="zh-CN" class="is-AppPromotionBarVisible cssanimations csstransforms csstransitions flexbox no-touchevents no-mobile">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="description" content="一个真实的网络问答社区，帮助你寻找答案，分享知识。">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>登录注册</title>
    <link rel="dns-prefetch" href="">
    <link rel="stylesheet" href="static/styles/login.css">
</head>
<body class="zhi  no-auth">
<div class="index-main">
    <div class="index-main-body">
        <div class="index-header">
            <h1 class="logo hide-text"><img src="static/images/res/zhihu.png" alt=""></h1>
            <h2 class="subtitle">
            <#if msg??>
                ${msg}
            <#else>
                与世界分享你的知识、经验和见解
            </#if>
            </h2>
        </div>
        <div class="desk-front sign-flow clearfix sign-flow-simple">
            <div class="view view-signin" data-za-module="SignInForm" style="display: block;">
                <form method="post" id="regloginform">
                    <div class="group-inputs">
                        <div class="email input-wrapper">
                            <input type="text" name="username" aria-label="手机号或邮箱" placeholder="手机号或邮箱" required="">
                        </div>
                        <div class="input-wrapper">
                            <input type="password" name="password" aria-label="密码" placeholder="密码" required="">
                        </div>
                    </div>
                    <input type="hidden" name="next" value=<#if next??>${next}</#if>>
                    <div class="clearfix button-wrapper command">
                        <button class="sign-button submit" type="submit" onclick="form=document.getElementById('regloginform');form.action='login'">登录</button>
                        <button class="sign-button submit" type="submit" onclick="form=document.getElementById('regloginform');form.action='reg'">注册</button>
                    </div>
                    <div class="signin-misc-wrapper clearfix">
                        <label class="remember-me">
                            <input type="checkbox" name="rememberme" value="true">记住我
                        </label>
                        <a class="unable-login" href="#">无法登录？</a>
                    </div>
                </form>

            </div>
        </div>
    </div>

</div>
</body>
</html>