<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加用户</title>
<style>html{overflow:hidden;}</style>
<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
<link rel="stylesheet" href="<%=contextPath%>/common/site.css"  type="text/css"></link>
<link rel="stylesheet" href="<%=contextPath%>/common/nfpTreeThemes/nfpTreeCss.css" type="text/css"></link>
<script type="text/javascript"	src="<%=contextPath%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/nfpTreeUser.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/validator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/convertChinese.js"></script>
<script language="javascript">
	$(function(){
		$("#userName").focus();
		
		$("#organizationId").nfpCommTree({
			ajaxUrl: "<%=contextPath%>/web/organization/json/queryTreeNodeData?companyId=${company.id}",
	        themeUrl:"<%=contextPath%>/common/nfpTreeThemes",
	        useCache:true,
	        dropHeight:150,
	        paramsName:"organizationParentId"
	    });
		
		$("#btnSave").click(function(){
				$(this).attr("disabled","disabled");
				
				var userName = jQuery.trim($("#userName").val());
				if (!userName) {
					$("#userName").focus();
					$(this).removeAttr("disabled");
					alert("用户名不能为空！");
					return;
				}
				var companyId = jQuery.trim($("#companyId").val());
				var organizationId = $("#organizationId").attr("checkedValue");
				var roleId = $("#roleId").val();
				var IDCard = jQuery.trim($("#IDCard").val());
				var officePhone = jQuery.trim($("#officePhone").val());
				var mobilePhone = jQuery.trim($("#mobilePhone").val());
				var address = jQuery.trim($("#address").val());
				var email = jQuery.trim($("#email").val());
				var status = $("input[name='Status']:checked").val();
				$.ajax({
					url			:"<%=contextPath%>/web/user/json/addUser",
					type 		: "POST",
					dataType 	: "json",
					data		: {
						"user.userName" : userName,
						"user.company.id" : companyId,
						"user.organization.id" : organizationId,
						"user.role.id" : roleId,
						"user.idCard" : IDCard,
						"user.officePhone" : officePhone,
						"user.mobilePhone" : mobilePhone,
						"user.email" : email,
						"user.address" : address,
						"user.currentState" : status,
						"user.isDelete" : "0"
					},
					success 	: function(data) {
						$("#btnSave").removeAttr("disabled");
						if (data == 2) {
							alert("用户名称:\""+userName+"\"已经存在,请重新输入！");
						} else if(data == 1) {
							alert("保存成功！");
							parent.frames[0].loadData($(parent.frames[0].document.body).find("#txtCurrentPage").val())
							parent.ClosePop();
						} else {
							alert("保存失败！");
						}
					},
					error 		: function() {
						$("#btnSave").removeAttr("disabled");
						alert("保存失败！");
					}
				});
		});
	});
</script>
</head>
<body class="popDiv">
	<form name="form">
	<input type="hidden" id="companyId" value="${company.id }"/> 
	<table width="90%" cellpadding="0" cellspacing="1" bgcolor="#c1dbfc" style="margin-top:1px;margin-left:20px;">
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">用户名称：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				<input id="userName" class="editInput" maxlength="50" /><span style='color:red'>*</span>
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
					<input id="organizationId" class="editInput" maxlength="50" />
				</td>
			</tr>
			<tr>
			    <td width="21%" height="28" align="center" bgcolor="#FFFFFF">用户角色：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
					<select name="roleId" id="roleId" class="editInput" style="width:206px;">
					     <option value="-1" selected="selected">请选择角色</option>
						 <c:forEach items="${roleList}" var="role">
							   <option value="${role.id}">${role.name}</option>
					     </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">身份证号：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <input id="IDCard" class="editInput" maxlength="15" dataType="IdCard" msg="身份证号格式不正确！" require="false"/>
				</td>
			</tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">办公电话：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <input id="officePhone" class="editInput" maxlength="8" dataType="Phone" msg="办公电话格式不正确！" require="false"/>
				</td>
			</tr>
			<tr>
			    <td width="21%" height="28" align="center" bgcolor="#FFFFFF">手机号码：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <input id="mobilePhone" class="editInput" maxlength="11" dataType="Mobile" msg="手机号码格式不正确！" require="false"/>
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">email：</td>
			    <td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
			        <input id="email" class="editInput" maxlength="50" dataType="email" msg="email格式不正确！" require="false"/>
			    </td>
			</tr>			
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">家庭住址：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <textarea id="address" class="editInput" rows="2" cols="30" ></textarea>
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">是否审核：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;"><span style="padding-right: 20px;">
						<label>是</label>
						<input type="radio" checked="checked" name="Status" id="StatusYes" value="y"/>
					</span>
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				    	否<input type="radio" name="Status" id="StatusNo" value="n"/>
			    </td>
			</tr>
			<tr>
				<td colspan="2" align="center" style="padding-top: 20px;" bgcolor="#FFFFFF">
					<a href="#" id="btnSave" ><img src="<%=contextPath%>/common/blue/Images/save.gif"/></a>
					    &nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#" id="btnClose" onClick="javascript:parent.ClosePop();">
					   <img src="<%=contextPath%>/common/blue/Images/close.gif"/>
					</a>
				</td>
			</tr>
		</table>
	
	</form>
</body>
</html>