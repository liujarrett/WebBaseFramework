<%@ page language="java" pageEncoding="UTF-8"%>
<%
   String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>修改密码</title>
		<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
		<link rel="stylesheet" href="<%=contextPath %>/common/site.css" type="text/css"></link>
		<link rel="stylesheet" href="<%=contextPath%>/common/nfpTreeThemes/nfpTreeCss.css" type="text/css"></link>
		<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
		<script type="text/javascript"	src="<%=contextPath%>/common/js/jquery.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
		<script type="text/javascript" src="<%=contextPath %>/common/js/md5_32.js"></script>
		<script language="javascript">
		$(function(){
			$("#oldPwd").focus();
			
			$("#btnSave").click(function(){
					$(this).attr("disabled","disabled");
					if ("${user.id}" <= 0) {
						alert("无效用户不予处理！");
						$(this).removeAttr("disabled");
						return;
					}
					var oldPwd = jQuery.trim($("#oldPwd").val());
					if (!oldPwd) {
						$("#oldPwd").focus();
						$(this).removeAttr("disabled");
						alert("原始密码不能为空！");
						return;
					}
					var newPwd = jQuery.trim($("#newPwd").val());
					if (!newPwd) {
						$("#newPwd").focus();
						$(this).removeAttr("disabled");
						alert("新密码不能为空！");
						return;
					}
					var rePwd = jQuery.trim($("#rePwd").val());
					if (!rePwd) {
						$("#rePwd").focus();
						$(this).removeAttr("disabled");
						alert("确认密码不能为空！");
						return;
					}
					if (calcMD5(oldPwd) != "${user.password}") {
						$("#oldPwd").val("");
						$("#oldPwd").focus();
						$(this).removeAttr("disabled");
						alert("原始密码不正确，请重新输入！");
						return;
					}
					if (newPwd != rePwd) {
						$("#rePwd").focus();
						$(this).removeAttr("disabled");
						alert("两次输入密码不一致，请重新输入！");
						return;
					}
				
					$.ajax({
						url	:"<%=contextPath%>/web/user/json/changePassword",
						type:"POST",
						dataType:"json",
						data: {
							"user.id" : ${user.id},
							"user.password" : calcMD5(newPwd)
						},
						success		:function(data){
							$("#btnSave").removeAttr("disabled");
							if(data == 1) {
							    $("#oldPwd").val("");
							    $("#newPwd").val("");
							    $("#rePwd").val("");
								
								alert("密码修改成功！");
							} else {
								alert("密码修改失败！");
							}
						},
						error		:function(){
							$("#btnSave").removeAttr("disabled");
							alert("密码修改失败！");
						}
					});
			});
		});
	</script>
	</head>
	<body>
		<%@ include file="../../common/pagehead.jsp"%>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		class="nei_box" align="center">
		<tr>
			<td style="padding-left: 10px; height: 34px;" class="tit2_background">
				<div id="btnSave" class="tool_td tool_save"
					style="width: 30px; height: 24px; margin-top: 5px;"></div>
			</td>
		</tr>
		<tr>
			<td align="center">
				<table width="300" cellpadding="0" cellspacing="1" bgcolor="#c1dbfc"
					style="margin-top: 20px; margin-left: 20px;">
					<tr>
						<td width="21%" height="28" align="center" bgcolor="#FFFFFF">用户名称：</td>
						<td width="79%" height="28" bgcolor="#FFFFFF" align="center">
							<div style="width: 90%">${sessionScope.userName }</div>
						</td>
					</tr>
					<tr>
						<td width="21%" height="28" align="center" bgcolor="#FFFFFF">原始密码：</td>
						<td width="79%" height="28" bgcolor="#FFFFFF" align="center">
							<input style="width: 90%" type="password" id="oldPwd" />
						</td>
					</tr>
					<tr>
						<td width="21%" height="28" align="center" bgcolor="#FFFFFF">新&nbsp;密&nbsp;码：</td>
						<td width="79%" height="28" bgcolor="#FFFFFF" align="center">
							<input type="password" id="newPwd" style="width: 90%" />
						</td>
					</tr>
					<tr>
						<td width="21%" height="28" align="center" bgcolor="#FFFFFF">确认密码：</td>
						<td width="79%" height="28" bgcolor="#FFFFFF" align="center">
							<input type="password" id="rePwd" style="width: 90%" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>