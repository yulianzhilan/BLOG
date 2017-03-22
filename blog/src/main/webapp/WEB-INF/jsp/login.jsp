<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>login</title>
    <%@include file="/WEB-INF/jspi/main.jspi"%>
  </head>

  <body class="hold-transition login-page">
    <div class="login-box">
      <div class="login-logo">
        <a href="#"><b>Jane</b>Scott</a>
      </div>
      <!-- /.login-logo -->
      <div class="login-box-body">
        <p class="login-box-msg">Log in to start your session</p>
        <form:form modelAttribute="loginDTO" method="post" action="${pageContext.request.contextPath}/login/validate">
          <div class="form-group has-feedback">
            <form:input path="account" class="form-control" placeholder="Account"/>
            <%--<input class="form-control" placeholder="Email" type="email">--%>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <form:password path="password" class="form-control" placeholder="Password"/>
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
        <div class="row">
          <div class="col-xs-8 text-center">
            <h5 style="color: red">${errtx}</h5>
          </div>
          <div class="col-xs-4">
            <button type="submit" class="btn btn-primary btn-block btn-flat">Log In</button>
          </div>
        </div>
        </form:form>

        <a href="#">I forgot my password</a><br>

        <!-- fixme 这里增加一个注册功能 或者提供 一个游客身份进入 -->
        <div class="social-auth-links text-center">
          <p>- OR -  Coming Soon</p>
          <a href="#" class="btn btn-block btn-facebook btn-flat text-center"> Sign in</a>
          <a href="#" class="btn btn-block btn-google btn-flat text-center"> PasserBy</a>
        </div>
      </div>
      <!-- /.login-box-body -->
    </div>
    <!-- /.login-box -->

  </body>
</html>
