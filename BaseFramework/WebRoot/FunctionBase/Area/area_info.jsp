<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>修改区域信息</title>
	<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
	<link rel="stylesheet" href="<%=contextPath%>/common/site.css" type="text/css" />
	<link rel="stylesheet" href="<%=contextPath%>/common/nfpTreeThemes/nfpTreeCss.css" type="text/css"></link>
</head>
<body class="popDiv">
		<form name="form" >
			<table width="90%" cellpadding="0" cellspacing="1" bgcolor="#c1dbfc" style="margin-top:1px;margin-left:20px;">
				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						上级区域：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<input id="parentareaId"  value='${area.parent.areaName}' class="editInput" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						区域名称：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<input value="${area.areaName }" id="areaName"  class="editInput" maxlength="50" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						区域编码：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
					    <input id="parentareaId"  value='${area.areaCode }' class="editInput" disabled="disabled"/>
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