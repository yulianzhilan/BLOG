<header class="main-header">
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <!-- Logo -->
    <a href="${ctx}" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>J</b>S</span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>Jane</b>Scott</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <%--<span class="sr-only">Toggle navigation</span>--%>
            <%--<span class="icon-bar"></span>--%>
            <%--<span class="icon-bar"></span>--%>
            <%--<span class="icon-bar"></span>--%>
        </a>

        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">

                <!-- User Account: style can be found in dropdown.less -->
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="${sessionScope.userSummaryDTO.photoUrl}" class="user-image" alt="User Image">
                        <span class="hidden-xs">Hello, ${sessionScope.userSummaryDTO.nickName}!</span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- User image -->
                        <li class="user-header">
                            <img src="${sessionScope.userSummaryDTO.photoUrl}" class="img-circle" alt="User Image">
                            <p>
                                ${sessionScope.userSummaryDTO.nickName} - ${sessionScope.userSummaryDTO.account}
                                <small>Member since <fmt:formatDate value="${sessionScope.userSummaryDTO.created}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
                            </p>
                        </li>
                        <!-- Menu Body -->
                        <li class="user-body">
                            <div class="row">
                                <div class="col-xs-4 text-center">
                                    <a href="#">Followers</a>
                                </div>
                                <div class="col-xs-4 text-center">
                                    <a href="#">Sales</a>
                                </div>
                                <div class="col-xs-4 text-center">
                                    <a href="#">Friends</a>
                                </div>
                            </div>
                            <!-- /.row -->
                        </li>
                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-left">
                                <a onclick="getInfo();" class="btn btn-default btn-flat">Setting</a>
                            </div>
                            <div class="pull-right">
                                <a onclick="if(confirm('Are You Sure?')){window.location.href='${ctx}/login/logout'}" class="btn btn-default btn-flat">Sign out</a>
                            </div>
                        </li>
                    </ul>
                </li>

            </ul>
        </div>
    </nav>
</header>
<div class="modal fade" id="setting" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h3 class="modal-title text-center" id="title">All attributes are optional</h3>
            </div>
            <div class="modal-body">
                <form id="setForm" method="post">
                    <input type="hidden" name="userId">
                    <span style="font-weight: bold">nickName</span>
                    <div class="form-group has-feedback">
                        <input class="form-control" placeholder="Nice Name" type="text" name="nickName">
                        <span class="fa fa-user-circle-o form-control-feedback"></span>
                    </div>
                    <span style="font-weight: bold; color: red;">account(not available)</span>
                    <div class="form-group has-feedback">
                        <input class="form-control" placeholder="Account" type="text" name="account" readonly>
                        <span class="glyphicon glyphicon-user form-control-feedback"></span>
                    </div>
                    <span style="font-weight: bold">email</span>
                    <div class="form-group has-feedback">
                        <input class="form-control" placeholder="Email" type="email" name="email">
                        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
                    </div>
                    <span style="font-weight: bold">password</span>
                    <div class="form-group has-feedback">
                        <input class="form-control password" placeholder="Password" type="password" name="password">
                        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                    </div>
                    <span style="font-weight: bold">retypePassword</span>
                    <div class="form-group has-feedback">
                        <input class="form-control password" placeholder="Retype password" type="password" name="retypePassword">
                        <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
                    </div>
                    <span style="font-weight: bold">photo</span>
                    <div class="form-group has-feedback">
                        <div class="row">
                            <div class="col-sm-10" style="padding-right: 0;">
                                <input class="form-control" placeholder="Photo Path" type="text" name="photoUrl">
                                <span class="glyphicon glyphicon-picture form-control-feedback"></span>
                            </div>
                            <div class="col-sm-2">
                                <div class="input-group-btn">
                                    <a target="_blank" href="${ctx}/photo/manage" class="btn btn-default"><i class="fa fa-search"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="register_error" class="form-group has-feedback text-center">
                        <span style="color: red" id="error_span"></span>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" onclick="setting();">Confirm</button>
            </div>
        </div>
    </div>
</div>


<script>
    function getInfo() {
        $.ajax({
            url: '${ctx}/login/getInfo.json',
            success: function (data) {
                if(ajaxValidate(data)){
                    bindFormValue($("#setting"), data.infoDTO);
                    $('#setting').modal('show');
                }
            }
        })
    }

    function setting() {
        $.ajax({
            url: '${ctx}/login/setting.json',
            data: $("#setForm").serialize(),
            success: function (data) {
                if(ajaxValidate(data)){
                    alert("setting success, please re-login!");
                    window.location.href = "${ctx}/login/logout";
                }
            }
        })
    }

    function show_error(msg) {
        $("#error_span").text(msg);
        setTimeout('$("#error_span").text("");',1000)
    }

    $("input[name = password]").blur(function () {
        if($("input[name = password]").val().length < 8) {
            show_error("密码长度不够！");
        }
    });

    $("input[name = retypePassword]").blur(function () {
        if($("input[name = password]").val() != $("input[name = retypePassword]").val()) {
            show_error("两次输入密码不一致！");
        }
    });

</script>