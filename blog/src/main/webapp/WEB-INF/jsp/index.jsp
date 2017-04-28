<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>JaneScott</title>
  <%@include file="/WEB-INF/jspi/main.jspi"%>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <link rel="shortcut icon" href="${ctx}/images/js2.png" type="image/x-icon">
</head>

<body class="hold-transition login-page">
  <div class="login-box">
    <div class="login-logo">
      <a href="#"><b>Jane</b>Scott</a>
    </div>
    <div class="login-box-body">
      <h4 class="login-box-msg">welcome, using this blog system, if you find any inconvenient bugs, please contact us via email. </h4>
      <div class="row">
        <div class="col-sm-8">
          <div class="input-group margin-bottom-sm">
            <span class="input-group-addon"><i class="fa fa-envelope-o fa-fw"></i></span>
            <input value="18516669265@163.com" class="btn-default form-control input-sm" type="text"/>
          </div>
        </div>
        <div class="col-sm-4">
          <button class="btn btn-sm btn-primary" onclick="window.location.href='${ctx}/login'">Entrance</button>
        </div>
      </div>
    </div>
  </div>
</body>
</html>