<!DOCTYPE html>
<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
%>

<html>
<head>
<title>login.jsp</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/login.css" />
<script src="<%=basePath%>js/jquery-3.2.1.min.js"></script>

</head>

<body>
<center>
		
		<div class="login-form" >
			<form action="<%=basePath%>loginSubmit.action" method="post">
				<div class="head">
					<img alt="默认头像" src="image/user.png" />
				</div>
				<div class="enterSomething">
					<div class="tips">
						
					</div>
					<div class="enter" style="padding-top: 70px;">
						<input class="yhm" type="text" id="username" name="username"/>
					<div class="checkResult">
						<span id="unameTips"></span>
					</div>
					
					</div>
					
				</div>
				<div class="enterSomething clear">
					<div class="tips">
						
					</div>
					<div class="enter">
						<input type="password" id="password" name="password" />
					<div class="checkResult">
						<span id="passwordTips"></span>
					</div>
					
					</div>
					
				</div>
				
				
				<div class="enterSomething clear">
					<div class="tips">
						
					</div>
					<div class="enter">
						<div id="safeCodeDiv">
						<img src="<%=basePath%>getVerify.action" alt="验证码获取失败" id="safeCodeImage" onclick="changeImage()" style="cursor: pointer;" />
						<a onclick="changeImage()"><span style="color: blue;">看不清,换一张?</span></a>
						</div>
						<div class="yanzheng">
						<div class="enter">
							<input class="yz" type="text" id="safeCode" name="safeCode" value=""/>
						
							<div class="checkResult">
							<span id="safeCodeTips"></span>
							</div>
						
						</div>
						
						</div>
					
					</div>
					
				</div>

				<div class="enterSomething clear">
				<div id="submitButton" class="enter2">
					<input type="checkbox" checked="checked" id="autoLogin"
						name="autoLogin" value="on" /><span style="font-size: 14px">七天内自动登录</span>
					<input type="submit" value="登录" id="sBtn" name="sBtn"/>
					<!-- <button type="button" id="sBtn" name="sBtn">登录</button> -->
				</div>
				</div>
				<div class="clear"></div>
			</form>
		</div>
</center>
</body>
</html>
<script type="text/javascript">
function changeImage() {
	var image = document.getElementById("safeCodeImage");
	image.src = "<%=basePath%>getVerify.action?"+ new Date();
}


</script>