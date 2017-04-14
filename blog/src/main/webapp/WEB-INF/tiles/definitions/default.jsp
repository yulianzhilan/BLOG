<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>

<!DOCTYPE html>
<head>
    <%--<%@include file="/WEB-INF/jspi/minimal.jspi"%>--%>
    <%--<%@include file="/WEB-INF/jspi/AdminLTE.jspi"%>--%>
        <%@include file="/WEB-INF/jspi/main.jspi"%>
    <title><tiles:getAsString name="title" ignore="true"/></title>
    <%--<link rel="BookMark" href="http://dev.infoccsp.com/staticNG/favicon-eal.ico" type="image/x-icon">--%>
        <%-- fixme 这里添加网页名称前面的小图标 --%>
        <link rel="shortcut icon" href="" type="image/x-icon">
        <script>
            $(document).ready(function () {
                var pgurl = window.location.href;
                // active：ul.sidebar-menu -> li -> a
                $("ul.sidebar-menu>li>a").each(function () {
                    if ($(this).attr("href") != "#" && pgurl.indexOf($(this).attr("href").split("?")[0]) >= 0) {
                        $(this).parent().addClass("active");
                    }
                });
                // active：ul.treeview-menu -> li -> a
                $("ul.treeview-menu>li>a").each(function () {
                    if ($(this).attr("href") != "#" && pgurl.indexOf($(this).attr("href").split("?")[0]) >= 0) {
                        $(this).parent().addClass("active");
                        $(this).parent().parent().parent().addClass("active");
                    }
                });
            });
        </script>

</head>
<%--<body class="fixed skin-blue sidebar-mini">--%>
<body class="skin-blue sidebar-mini wysihtml5-supported" style="height: auto;">
    <div class="wrapper">
        <tiles:insertAttribute name="header"/>

        <tiles:insertAttribute name="sidebar"/>

        <tiles:insertAttribute name="main"/>

        <tiles:insertAttribute name="footer"/>
    </div>
</body>
</html>
