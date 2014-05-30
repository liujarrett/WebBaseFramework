<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>查看机构信息</title>
		<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
		<link rel="stylesheet" href="<%=contextPath%>/common/site.css" type="text/css" />
	<script language="javascript" type="text/javascript">
		window.onload = function() {
			document.getElementById("btnClose").focus();
		}		
	</script>
	</head>
	<body class="popDiv">
		<form name="form" >
			<table width="90%" cellpadding="0" cellspacing="1" bgcolor="#c1dbfc" style="margin-top:1px;margin-left:20px;">
				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						机构名称：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<c:out value="${organization.fullName }"/>&nbsp;
					</td>
				</tr>
				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						上级机构：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<c:out value="${organization.parent.fullName}"/>
					</td>
				</tr>
				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						机构描述：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<c:out value="${organization.describes }"/>&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" style="padding-top: 15px;" bgcolor="#FFFFFF">
						<a href="#" id="btnClose" onclick="parent.ClosePop();">
						   <img src="<%=contextPath%>/common/blue/Images/close.gif"/>
						</a>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
