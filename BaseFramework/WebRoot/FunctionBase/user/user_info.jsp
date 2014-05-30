<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>查看用户</title>
		<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
		<link rel="stylesheet" href="<%=contextPath%>/common/site.css" type="text/css" />
	<script language="javascript" type="text/javascript">
		window.onload = function() {
			document.getElementById('btnClose').focus();
		}
	</script>
		
	</head>
	<body>
		<table width="90%" id="senfe" class="divtb" cellpadding="0" cellspacing="1"  align="center">
			<tr>
				<td width="21%" height="22" align="center">用户名称：</td>
				<td width="79%" height="22"  style="padding-left:10px;">${user.userName }</td>
			</tr>
			<tr>
				<td width="21%" height="22" align="center" >公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司：</td>
				<td width="79%" height="22"  style="padding-left:10px;">${user.company.fullName }</td>
			</tr>
			<tr>
			    <td width="21%" height="22" align="center" >所属机构：</td>
				<td width="79%" height="22"  style="padding-left:10px;">${user.organization.fullName}</td>
			</tr>
			<tr>
			    <td width="21%" height="22" align="center" >用户角色：</td>
				<td width="79%" height="22"  style="padding-left:10px;">${user.role.name}</td>
			</tr>
			<tr>
				<td width="21%" height="22" align="center" >身份证号：</td>
				<td width="79%" height="22"  style="padding-left:10px;">${user.idCard }</td>
			</tr>
			<tr>
				<td width="21%" height="22" align="center" >办公电话：</td>
				<td width="79%" height="22"  style="padding-left:10px;">${user.officePhone }</td>
			</tr>
			<tr>
			    <td width="21%" height="22" align="center" >手机号码：</td>
				<td width="79%" height="22"  style="padding-left:10px;">${user.mobilePhone }</td>
			</tr>
			<tr>
				<td width="21%" height="22" align="center" >email：</td>
				<td width="79%" height="22"  style="padding-left:10px;">${user.email }</td>
			</tr>			
			<tr>
				<td width="21%" height="22" align="center" >家庭住址：</td>
				<td width="79%" height="22"  style="padding-left:10px;">${user.homeAddress }</td>
			</tr>
			<tr>
				<td width="21%" height="22" align="center" >是否审核：</td>
				<td width="79%" height="22"  style="padding-left:10px;">${user.currentState==1?'已审核':'未审核' }</td>
			</tr>
			<tr>
				<td colspan="2" align="center" style="padding-top: 20px;" >
					<a href="#" id="btnClose" onClick="javascript:parent.ClosePop();"><img src="<%=contextPath%>/common/blue/Images/close.gif"/></a>
				</td>
			</tr>
		</table>
	</body>
</html>