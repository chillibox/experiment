<#macro empty></#macro>

<#macro layout active navTitle script=empty>
    <!doctype html>
    <html>
    <head>
        <#include "./head.ftlh"/>
    </head>
    <body>
    <div class="wrapper">
        <#include "./sidebar.ftlh"/>
        <div class="main-panel">
            <#include "./nav.ftlh"/>
            <div class="content">
                <input id="couponTitle" type="hidden" value="${appConst.title!}"/>
                <#nested/>
            </div>
            <#include "./footer.ftlh"/>
        </div>
    </div>
    <#include "./js.ftlh"/>
    <@script/>
    </body>
    </html>
</#macro>