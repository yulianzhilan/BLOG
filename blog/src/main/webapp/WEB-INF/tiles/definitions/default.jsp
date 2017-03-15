<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras" %>

<!DOCTYPE html>
<head>
    <%--<%@include file="/WEB-INF/jspi/minimal.jspi"%>--%>
    <%--<%@include file="/WEB-INF/jspi/AdminLTE.jspi"%>--%>
        <%@include file="/WEB-INF/jspi/test.jspi"%>
    <title><tiles:getAsString name="title" ignore="true"/></title>
</head>
<body class="fixed skin-blue sidebar-mini">
    <div class="wrapper">
        <tiles:insertAttribute name="header"/>

        <tiles:insertAttribute name="sidebar"/>

        <tiles:insertAttribute name="main"/>

        <tiles:insertAttribute name="footer"/>
    </div>
</body>
</html>
