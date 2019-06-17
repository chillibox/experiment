<div class="sidebar" data-background-color="white" data-active-color="danger">
    <div class="sidebar-wrapper">
        <div class="logo">
            <a href="${ctx}/admin" class="simple-text">
                ${appConst.consoleBrand}
            </a>
        </div>

        <ul class="nav">
            <li class="${('dashboard' == active)?then('active','')}">
                <a href="${ctx}/admin/dashboard">
                    <i class="ti-pie-chart"></i>
                    <p>控制台</p>
                </a>
            </li>
            <li class="${('template' == active)?then('active','')}">
                <a href="${ctx}/admin/template">
                    <i class="ti-stamp"></i>
                    <p>优惠券模板</p>
                </a>
            </li>
            <li class="${('coupon' == active)?then('active','')}">
                <a href="${ctx}/admin/coupon">
                    <i class="ti-ticket"></i>
                    <p>优惠券</p>
                </a>
            </li>
            <#if isAdmin>
                <li class="${('report' == active)?then('active','')}">
                    <a href="${ctx}/admin/report">
                        <i class="ti-stats-up"></i>
                        <p>报表</p>
                    </a>
                </li>
                <li class="${('user' == active)?then('active','')}">
                    <a href="${ctx}/admin/user">
                        <i class="ti-user"></i>
                        <p>用户</p>
                    </a>
                </li>
            </#if>
        </ul>
    </div>
</div>