<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>查看公司信息</title>
<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
<link href="<%=contextPath%>/common/site.css" rel="stylesheet" type="text/css" />
</head>
<body class="popDiv">
<table width="90%" cellpadding="0" cellspacing="1" bgcolor="#c1dbfc" style="margin-top:1px;margin-left:20px;">
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					登陆代码:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.companyCode }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					单位名称:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.fullName }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					单位地址:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.address }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					联系电话:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.telephone }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					单位联系人:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.linkmanName }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					平台类型:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.companyType==1?'平台':'企业' }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					单位法人:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.corporationName }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					单位负责人:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.principalName }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					邮件地址:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.email }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					单位描述:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.describes }</td>
			</tr>
			<tr>
				<td colspan="2" align="center" style="padding-top: 15px;" bgcolor="#FFFFFF"><a href="#" id="btnClose" onclick="parent.ClosePop();"><img src="<%=contextPath%>/common/blue/Images/close.gif"/></a>
				</td>
			</tr>
		</table>
</body>
</html>