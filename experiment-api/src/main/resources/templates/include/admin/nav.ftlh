<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar bar1"></span>
                <span class="icon-bar bar2"></span>
                <span class="icon-bar bar3"></span>
            </button>
            <a class="navbar-brand" href="#">${navTitle}</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a class="btn" href="${ctx}/admin/profile">
                        <i class="ti-user"></i>
                        <p>${username}</p>
                    </a>
                </li>
                <li>
                    <form action="${ctx}/admin/logout" method="post">
                        <button type="submit" class="btn btn-danger">
                            <i class="ti-power-off"></i>
                            <p>登出</p>
                        </button>
                    </form>
                </li>
            </ul>

        </div>
    </div>
</nav>