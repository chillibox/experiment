<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <#include "../include/ctx.ftlh"/>
    <link rel="stylesheet" href="${ctx}/r/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/r/css/app.css">
    <title>Whoops!${msg!}</title>
</head>
<body style="background: url('${ctx}/r/img/404.png');">
<#if msg??>
    <div class="container-fluid coupon-container">
        <div class="row" style="top: 30%; position: relative;">
            <div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
                <div class="coupon">
                    <div class="coupon-body text-center">
                        <div class="row">
                            <h1 class="coupon-gray">${msg!}</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#if>
</body>
</html>