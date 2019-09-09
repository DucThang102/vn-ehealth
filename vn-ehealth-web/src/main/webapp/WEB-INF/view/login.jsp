<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <title>HỆ THỐNG BỆNH ÁN ĐIỆN TỬ</title>
    <!-- Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/style.css" rel="stylesheet" type="text/css">
    <link href="/css/fontawesome/all.css" rel="stylesheet" type="text/css">  
</head>
<body class="bg-login">
	<div class="bg1">
  		<ul class="text-menu">
  			<li><a href="">Giới thiệu</a></li>
  			<li><a class="linenone" href="">Liên hệ</a></li>
  		</ul>  		
  	</div>
  	<div class="">
  		<p class="text-center pd20"><img src="images/logo.png" alt=""></p>
  		<h3 class="text-center text1">Bệnh viện Trung ương</h3>
  		<h1 class="text-center text2 MT0">Hệ thống Bệnh án điện tử</h1>
  	</div>
	<div class="login-form">
	    <form id="fmt" method="POST" action="/login" onsubmit='logIn()' class="form-signin">
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <!-- <h2 class="text-center">Đăng nhập</h2>         -->
	        <div class="form-group mb-3">
	            <input id="username" name="username" type="text" class="form-control" placeholder="Tên tài khoản"
	                   autofocus="true"/>            
	        </div>
	        <div class="form-group mb-3">            
	            <input id="password" name="password" type="password" class="form-control" placeholder="Mật khẩu"/>
	        </div>
	        <div class="form-group">
	            <span id="message" style="color:blue"><spring:message code="${message}" text=''/></span>
	            <span id="error" style="color:red"><spring:message code="${error}" text='' /></span>
	        </div>
	        <div class="form-group mb-3">
	            <button type="submit" class="btn btn-primary btn-block">Đăng nhập</button>
	        </div>
	        <div class="clearfix">
	            <label class="pull-left checkbox-inline text-blue"><input type="checkbox">Nhớ tài khoản</label>
	            <a href="#" class="float-right">Quên mật khẩu?</a>
	        </div>        
	    </form>
	    <p class="text-center"><a href="/registration">Đăng ký tài khoản</a></p>
	</div>
  	<footer class="container-fluid fixF">
  		<div class="container">
  			<div class="row mt-3">
				
				<div class="col-4">
					<p><i class="LineH fa fa-phone pull-left" aria-hidden="true"></i> Hỗ trợ: 0999 999 999</p>
					<p><i class="LineH fa fa-envelope pull-left" aria-hidden="true"></i> info@veig.vn</p>
				</div>
				<div class="col-4 text-center">
					<p class="MT10">Copyright © 2019 Công ty CP VEIG</p>
				</div>
			</div>
  		</div>
  	</footer>
  	
	<script>
	    function logIn() {
	        var username = document.getElementById("username").value;
	        var password = document.getElementById("password").value;
	        var data = {username: username, password: password};
	        
	        fetch("http://localhost:8000/api/auth/signin", {
	            method: "POST",
	            headers: {
	            'Content-Type': 'application/json'                
	            },
	            body: JSON.stringify(data)
	        })
	        .then(response => response.json())
	        .then(result => {
	            var token = result.accessToken;
	            if(token) {
	                localStorage.setItem('token', token);
	                document.getElementById("fmt").submit();
	            }else {
	                document.getElementById("message").innerHTML = '';
	                document.getElementById("error").innerHTML = '<spring:message code="Wrong.username.password"/>';
	            }
	        });        
	    }
	</script>
</body>
</html>                                                                    
