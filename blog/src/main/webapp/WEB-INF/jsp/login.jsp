<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>login</title>
    <%@include file="/WEB-INF/jspi/main.jspi"%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="shortcut icon" href="${ctx}/images/js2.png" type="image/x-icon">
  </head>

  <body class="hold-transition login-page register-box">
    <%-- login-box --%>
    <div class="login-box" id="loginbox">
      <div class="login-logo">
        <a href="${ctx}"><b>Jane</b>Scott</a>
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

        <div class="social-auth-links text-center">
          <p>- OR -  Coming Soon</p>
          <a onclick="toggleTo('1')" class="btn btn-block btn-facebook btn-flat text-center"> Sign in</a>
          <a href="${ctx}/login/passerby" class="btn btn-block btn-google btn-flat text-center"> PasserBy</a>
        </div>
      </div>
      <!-- /.login-box-body -->
    </div>
    <!-- /.login-box -->

    <!-- register-box -->
    <div class="register-box hidden" id="registerbox">
      <div class="register-logo">
        <a href="${ctx}"><b>Jane</b>Scott</a>
      </div>

      <div class="register-box-body">
        <p class="login-box-msg">Register a new membership</p>

        <form id="register" method="post">
          <div class="form-group has-feedback">
            <input class="form-control required" placeholder="Nice Name" type="text" name="nickName" title="昵称">
            <span class="fa fa-user-circle-o form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input class="form-control required" placeholder="Account" type="text" name="account" title="账户名">
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input class="form-control required" placeholder="Email" type="email" name="email" title="邮箱">
            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input class="form-control required password" placeholder="Password" type="password" id="set_password" name="password" title="密码">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input class="form-control required password" placeholder="Retype password" type="password" name="retype_password" title="重复密码">
            <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
          </div>
          <div id="register_error" class="form-group has-feedback text-center hidden">
            <span style="color: red" id="error_span"></span>
          </div>
          <div class="row">
            <div class="col-xs-8">
              <div class="form-group has-feedback">
                <label class="">
                    <input type="checkbox" name="terms">
              I agree to the <a href="#myModal" data-toggle="modal" data-target=".bs-example-modal-sm">terms</a>
                </label>
              </div>
            </div>
            <!-- /.col -->
            <div class="col-xs-4">
              <button type="button" onclick="" class="btn btn-primary btn-block btn-flat">Register</button>
            </div>
            <!-- /.col -->
          </div>
        </form>
        <a onclick="toggleTo('2')" class="text-center">I already have a membership</a>
      </div>
      <!-- /.form-box -->
    </div>
    <!-- register-box -->

    <!-- Modal -->

    <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
      <div class="modal-dialog modal-sm" role="document">
          <div class="modal-content">

            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
              <h4 class="modal-title" id="mySmallModalLabel">使用协议</h4>
            </div>
            <div class="modal-body">
              <ul>
                <li style="list-style-type: upper-roman">不得从事任何非法活动</li>
                <li style="list-style-type: upper-roman">不保证用户使用稳定性</li>
                <li style="list-style-type: upper-roman">最终终止权和解释权由开发本人持有.</li>
              </ul>
            </div>
          </div>
      </div>
    </div>

  </body>
<script>
  function toggleTo(flag) {
      if(flag == '1'){
          $("#loginbox").addClass('hidden');
          $("#registerbox").removeClass('hidden');
      } else if(flag == '2'){
          $("#registerbox").addClass('hidden');
          $("#loginbox").removeClass('hidden');
      }
  }

  function register() {
    
  }
  
  $("#set_password").blur(function () {
      if($(this).val().length < 8){
          show_error("密码长度不够！");
      }
  });

  $("input[name=retype_password]").blur(function () {
      if($(this).val != $("#set_password")){
          show_error("两次出入密码不一致！")
      }
  });

  function show_error(msg) {
      $("#error_span").text(msg);
      $("#register_error").removeClass('hidden');
  }
</script>
</html>
