<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar" style="height: auto;">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${sessionScope.userSummaryDTO.photoUrl}" class="img-circle" style="height: 45px;width: 45px;" alt="User Image">
            </div>
            <div class="pull-left info">
                <p>${sessionScope.userSummaryDTO.nickName}</p>
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>
        <!-- search form -->
        <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input name="q" class="form-control" placeholder="Search..." type="text">
                <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
            </div>
        </form>
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">MAIN NAVIGATION</li>
            <li class="treeview">
                <a href="${ctx}/login/home">
                    <i class="fa fa-home"></i> <span>Home</span>
                </a>
            </li>

            <c:forEach items="${sessionScope.modules}" var="module">
                <li class="treeview">
                    <a href="#">
                        <i class="glyphicon ${module.icon}"></i>
                        <span>${module.name}</span>
                        <span class="pull-right-container">
                            <%--<i class="fa fa-angle-left pull-right"></i>--%>
                            <span class="fa fa-angle-left pull-right"></span>
                        </span>
                    </a>
                    <ul class="treeview-menu" style="margin-top: -3px">
                        <c:forEach items="${module.menus}" var="menu">
                            <li><a href="${ctx}${menu.url}"><i class="fa fa-circle-o"></i>${menu.name}</a></li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>