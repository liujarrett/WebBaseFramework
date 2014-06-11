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
					所属区域:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.area.areaName }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					登录代码:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.companyCode }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					公司名称:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.companyName }&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					平台类型:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.companyType==1?'平台':'企业' }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					公司地址:
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
					公司法人:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.corporationName }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					工商注册号:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.corporationId }&nbsp;</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					所属派出所:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.remarks }&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					邮件地址:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.email }&nbsp;</td>
			</tr>
			<tr style="display: none">
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					公司描述:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">${company.describes }</td>
			</tr>
			<tr>
				<td colspan="2" align="center" style="padding-top:15px;padding-bottom:5px;" bgcolor="#FFFFFF"><a href="#" id="btnClose" onclick="parent.ClosePop();"><img src="<%=contextPath%>/common/blue/Images/close.gif"/></a>
				</td>
			</tr>
		</table>
</body>
</html>