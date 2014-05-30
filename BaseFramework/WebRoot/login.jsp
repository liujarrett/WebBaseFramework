<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String contextPath = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<head>
<TITLE>用户登录</TITLE>
<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/ajaxPager.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/md5_32.js"></script>
<style type="text/css">
body {
	background-image: url(<%=contextPath%>/common/Images/Images/topbg1.jpg);
	background-position:inherit;
	background-repeat: repeat-x;
	font-family: Verdana, Geneva, sans-serif;
	font-size: 12px;
}
.login_main {
	height: 358px;
	width: 570px;
	margin-right: auto;
	margin-left: auto;
	background-image: url(<%=contextPath%>/common/Images/login_bg.jpg);
	background-repeat: no-repeat;
	margin-top: 140px;
}
.login_txt {
	height: 19px;
	width: 134px;
	margin-left: 15px;
	background-image: url(<%=contextPath%>/common/Images/user_login_name.gif);
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	background-repeat: no-repeat;
	padding-left: 30px;
}
.login_txt2 {
	height: 19px;
	width: 60px;
	margin-left: 15px;
	background-image: url(<%=contextPath%>/common/Images/user_login_name.gif);
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	background-repeat: no-repeat;
	padding-left: 30px;
}



.STYLE1 {
	font-size: 14px;
	font-weight: bold;
}
.bg {
	font-size: 12px;
	background-attachment: scroll;
	background-image: url(<%=contextPath%>/common/Images/Images/topbg1.jpg);
	background-repeat: no-repeat;
	background-position: center top;
}
.bg1 {
	font-size: 12px;
	background-attachment: scroll;
	background-image: url(<%=contextPath%>/common/Images/Images/lgin_bg1.jpg);
	background-repeat: no-repeat;
	background-position: center top;
}
.bg2 {
	font-size: 12px;
	background-attachment: scroll;
	background-image: url(<%=contextPath%>/common/Images/Images/x1.png);
	background-repeat: no-repeat;
	background-position: center 10px;
}
.input21 {
	font-size: 14px;
	float: left;
	height: 30px;
	width: 180px;
	border: 1px solid #cccccc;
	background-color: #f4f4f4;
}

body,td,th {
	font-size: 12px;
	color: #666;
}

</style>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META content="MSHTML 6.00.6000.16674" name=GENERATOR>
<script language="javascript">
	$(function(){
		$("#companyCode").focus();
		
		$("#companyCode").keydown(function(event){
			if(event.keyCode==13) { //回车键 
				$("#userName").focus();
			}
		});
		
		$("#userName").keydown(function(event){
			if(event.keyCode==13) {
				$("#password").focus();
			}
		});
		
		$("#password").keydown(function(event){
			if(event.keyCode==13){
				$("#btnLogin").triggerHandler("click");
			}
		 });
		
		$("#btnLogin").click(function(){
			//$("#btnLogin").hide();
			//$("#btnLogin").show();
			if (!checkText(document.getElementById("companyCode"))){
				return;
			}
			if (!checkText(document.getElementById("userName"))){
				return;
			}
			if (!checkText(document.getElementById("password"))){
				$("#btnLogin").show();
				return;
			}
			
			var companyCode = jQuery.trim($("#companyCode").val());
			if (companyCode == "") {
				$("#companyCode").focus();
				alert("密码不能为空！");
				$("#btnLogin").show();
				return;
			}
			var userName = jQuery.trim($("#userName").val());
			if (userName == "") {
				$("#userName").focus();
				alert("用户名不能为空！");
				$("#btnLogin").show();
				return;
			}
			var password = jQuery.trim($("#password").val());
			if (password == "") {
				$("#password").focus();
				alert("密码不能为空！");
				$("#btnLogin").show();
				return;
			}
		
           	$.ajax({
				url			:"<%=contextPath%>/web/user/json/login",
				type 		: "POST",
				dataType 	: "json",
				data		: {
					"user.userName" : userName,
					"user.password" : calcMD5(password),
					"company.companyCode" : companyCode
				},
				success 	: function(data) {
					if(data == 0){
						alert("登录失败");
					} else if(data == 1) {
						//登陆成功
						window.location="<%=contextPath%>/web/func/goTo";
					} else if(data == 2) {
						alert("暂未分配任何角色");
					}
				},
				error		:function(){
					alert("登录失败");
				}
			});
		});
		
		//重置按钮
		$("#btnReset").click(function(){
			$("#companyCode").val("");
			$("#userName").val("");
			$("#password").val("");
			
			$("#companyCode").focus();
		});
		
	});
	
</script>
</head>
<body>
<br>
<br>
<br>
<br>
<br>
<br>

<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="350" align="left" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="5"></td>
      </tr>
      <tr>
        <td height="450" align="center" valign="top"><table width="960" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="390" align="right" class="bg1" style="padding-right:80px"><br>
              <br>
              <table width="320" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
                <tr>
                <td height="40" background="<%=contextPath%>/common/Images/Images/loginbgk-3.jpg" class="font14s" style="padding-left:15px"><table width="98%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="85%" align="left">管理登录</td>
                    <td width="15%" align="center"><img src="<%=contextPath%>/common/Images/Images/dian_login.png" width="22" height="22"></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td height="200" align="center" valign="top" bgcolor="#f8f8f8" style="padding-top:15px"><table class=font9 cellspacing=2 cellpadding=0 width=300 
            align=center border=0>
            <tr>
              <td height=25>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr valign="bottom">
              <td width=88 height=35 align="center" valign="middle">单位代码</td>
               
              <td width=206><label for="textfield"></label><input name="companyCode" id="companyCode" class="input21" size=22 tabIndex="1">
              </td>
            </tr>
            <tr valign="bottom">
              <td width=88 height=35 align="center" valign="middle">用户名：</td>
               
              <td width=206><label for="textfield"></label><input name="userName" id="userName" class="input21" size=22 tabIndex="2">
              </td>
            </tr>
            <tr valign="bottom">
              <td width=88 height=35 align="center" valign="middle">密&nbsp;&nbsp;码：</td>
              <td width=206>
              <label for="textfield2"></label>
                  <input name="password" id="password" type=password class="input21" size=22 tabIndex="3">
              </td>
            </tr>
            <tr align="center" valign="bottom">
              <td height=40 align="center">&nbsp;</td>
              <td height=40 align="left">
              
              <img src="<%=contextPath%>/common/Images/Images/login.png" width="77" height="33" id="btnLogin" style="cursor:pointer" >
              <img src="<%=contextPath%>/common/Images/Images/reg.png" width="77" height="33" style="cursor:pointer" id="btnReset">
              
              </td>
            </tr>
        </table></td>
              </tr>
            </table>
              <table width="320" border="0" cellpadding="0" cellspacing="0" class="bg2">
                <tr>
                  <td height="10" colspan="2"></td>
                </tr>
                <tr>
                  <td width="37%" height="31">&nbsp;</td>
                  <td width="63%"><span class="copyright"> 2014 v2.0</span></td>
                </tr>
              </table></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</HTML>
