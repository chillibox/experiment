<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
<#assign ctx="${springMacroRequestContext.contextPath}" />
<#assign param=RequestParameters />
<meta name="ctx" content="${ctx}"/>
<#if appConst??>
    <meta name="version" content="${appConst.appVersion!}"/>
</#if>
