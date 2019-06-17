<#include "../include/admin/login.ftlh"/>
<@login>
<div class="wrapper">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <span class="navbar-brand">${appConst.consoleBrand}</span>
            </div>
        </div>
    </nav>
    <div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3">
        <div class="card">
            <div class="header">
                <h4 class="title">登录</h4>
            </div>
            <hr>
            <div class="content">
                <form name="f" method="post" action="${ctx}/admin/login">
                    <#if param.error??>
                        <div class="alert alert-warning">
                            无效用户名密码
                        </div>
                    </#if>
                    <#if param.logout??>
                        <div class="alert alert-success">
                            你已登出系统
                        </div>
                    </#if>
                    <div class="form-group">
                        <label for="username">用户名</label>
                        <input class="form-control border-input" type="text" id="username" name="username" autofocus/>
                    </div>
                    <div class="form-group">
                        <label for="password">密码</label>
                        <input class="form-control border-input" type="password" id="password" name="password"/>
                    </div>
                    <div>
                        <label>
                            <input type="checkbox" id="remember-me" name="remember-me"/>记住我
                        </label>
                    </div>
                    <div class="form-actions text-right">
                        <button type="submit" class="btn btn-info btn-fill btn-wd">登录</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</@login>