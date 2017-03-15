<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar" style="height: auto;">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p>${sessionUser.username}</p>
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
                <a href="${ctx}">
                    <i class="fa fa-home"></i> <span>Home</span>
                </a>
            </li>

            <c:forEach items="${sessionScope.categories}" var="category">
                <li class="treeview">
                    <a href="#">
                        <i class="fa ${category.categoryIcon}"></i>
                        <span>${category.categoryName}</span>
                        <span class="pull-right-container">
                            <i class="fa fa-angle-left pull-right"></i>
                        </span>
                    </a>
                    <ul class="treeview-menu">
                        <c:forEach items="${category.menus}" var="menu">
                            <li>
                                <a href="${menu.menuUrl}">
                                    <i class="fa fa-circle-o"></i>
                                    ${menu.menuName}
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>

            <%--<li class="treeview">--%>
                <%--<a href="#">--%>
                    <%--<i class="fa fa-th"></i>--%>
                    <%--<span>Photo Album</span>--%>
                    <%--<span class="pull-right-container">--%>
                            <%--&lt;%&ndash;<span class="label label-primary pull-right">4</span>&ndash;%&gt;--%>
                            <%--<i class="fa fa-angle-left pull-right"></i>--%>
                    <%--</span>--%>
                <%--</a>--%>
                <%--<ul class="treeview-menu">--%>
                    <%--<li>--%>
                        <%--<a href="/photo/check">--%>
                            <%--<i class="fa fa-circle-o"></i>--%>
                            <%--check--%>
                        <%--</a>--%>
                    <%--</li>--%>
                <%--</ul>--%>
            <%--</li>--%>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>