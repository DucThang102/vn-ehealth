<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Đăng ký tài khoản</title>

      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
      <style>
          .registration-form {
            width: 400px;
            margin: 50px auto;
        }
        .registration-form form {
            margin-bottom: 15px;
            background: #f7f7f7;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            padding: 30px;
        }
      </style>
  </head>

  <body>

    <div class="registration-form">
        <form:form method="POST" modelAttribute="userForm" class="form-signin">
            <h2 class="form-signin-heading">Đăng ký tài khoản</h2>
            <spring:bind path="username">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="username" class="form-control" placeholder="Tên tài khoản"
                                autofocus="true"></form:input>
                    <span style="color:red"><form:errors path="username"/></span>
                </div>
            </spring:bind>

            <spring:bind path="password">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="password" path="password" class="form-control" placeholder="Mật khẩu"></form:input>
                    <span style="color:red"><form:errors path="password"/></span>
                </div>
            </spring:bind>

            <spring:bind path="passwordConfirm">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="password" path="passwordConfirm" class="form-control"
                                placeholder="Mật khẩu xác thực"></form:input>
                    <span style="color:red"><form:errors path="passwordConfirm"/></span>
                </div>
            </spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Đăng ký</button>
        </form:form>        
    </div>
  </body>
</html>