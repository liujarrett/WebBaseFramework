<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改用户</title>
<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
<link rel="stylesheet" href="<%=contextPath %>/common/site.css" type="text/css"></link>
<link rel="stylesheet" href="<%=contextPath%>/common/nfpTreeThemes/nfpTreeCss.css" type="text/css"></link>
<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/validator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/nfpTreeUser.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/convertChinese.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
    html{overflow:hidden;}
</style>
<script language="javascript">
	$(function(){
		$("#userName").focus();
		
		var oldOrganizationId = jQuery.trim($("#oldOrganizationId").val());
		
		if(oldOrganizationId) {
			$("#organizationId").attr("checkedValue",oldOrganizationId);
		}
		
		$("#organizationId").nfpCommTree({
			ajaxUrl: "<%=contextPath%>/web/organization/json/queryTreeNodeData?companyId=${user.company.id}",
	        themeUrl:"<%=contextPath%>/common/nfpTreeThemes",
	        useCache:true,
	        dropHeight:150,
	        paramsName:"organizationParentId"
	    });
		
		$("#btnSave").click(function(){
				$(this).attr("disabled","disabled");
				var userName = jQuery.trim($("#userName").val());//用户名
				if (!userName) 
				{
					$("#userName").focus();
					$(this).removeAttr("disabled");
					alert("用户名不能为空！");
					return;
				}
				var mobilePhone = jQuery.trim($("#mobilePhone").val());
				if (!mobilePhone) 
				{
					$("#mobilePhone").focus();
					$(this).removeAttr("disabled");
					alert("手机号不能为空！");
					return;
				}
				var userId = $("#userId").val();//用户id
				var companyId = $("#companyId").val();//公司id
				var organizationId = $("#organizationId").attr("checkedValue");//所属组织
				var roleId = $("#userRoleId").val();//角色id
				var cardId = jQuery.trim($("#IdCard").val());//身份证号码
				var birthday = jQuery.trim($("#birthday").val());//出生日期
				var officePhone = jQuery.trim($("#officePhone").val());//办公电话
				var email = jQuery.trim($("#email").val());//邮箱
				var address =jQuery.trim($("#address").val()); //地址
				var status = $("input[name='Status']:checked").val(); //状态
				$.ajax({
					url			:"<%=contextPath%>/web/user/json/modifyUser",
					type 		: "POST",
					dataType 	: "json",
					data		: {
						"user.id"	: userId,
						"user.userName" : userName,
						"user.company.id" : companyId,
						"user.organization.id" : organizationId,
						"user.role.id" : roleId,
						"user.idCard" : cardId,
						"user.birthday" : birthday,
						"user.officePhone" : officePhone,
						"user.mobilePhone" : mobilePhone,
						"user.email" : email,
						"user.homeAddress" : address,
						"user.currentState" : status,
						"user.isDelete":"${user.isDelete}",
						"user.password":"${user.password}"
					},
					success 	: function(data) {
						$("#btnSave").removeAttr("disabled");
						if (data == 2) 
						{
							alert("用户名称:\""+userName+"\"已经存在,请重新输入！");
						}
						else if (data == 3) 
						{
							alert("手机号:\""+mobilePhone+"\"已经存在,请重新输入！");
						} 
						else if(data == 1) 
						{ //修改成功
							alert("保存成功！");
							parent.frames[0].loadData($(parent.frames[0].document.body).find("#txtCurrentPage").val());
							parent.ClosePop();
						} else { //修改失败
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
	<input type="hidden" id="userId" value="${user.id }">
	<input type="hidden" id="oldOrganizationId" value="${user.organization.id }">
	<input type="hidden" id="companyId" value="${user.company.id }">
	<table width="90%" cellpadding="0" cellspacing="1" bgcolor="#c1dbfc" style="margin-top:1px;margin-left:20px;">
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">用户名称：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <input type="text" id="userName" value="${user.userName }" class="editInput" maxlength="50" /><span style='color:red'>*</span>
				</td>
			</tr>
			<tr>
			    <td width="21%" height="28" align="center" bgcolor="#FFFFFF">手机号码：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <input id="mobilePhone" class="editInput" value="${user.mobilePhone }" maxlength="11" dataType="Mobile" msg="手机号码格式不正确！" require="false"/><span style='color:red'>*</span>
				</td>
			</tr>
			<tr>
			    <td width="21%" height="28" align="center" bgcolor="#FFFFFF">所属机构：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <input type="text" id="organizationId" class="editInput" value="${user.organization.organizationName }"/>
				</td>
			</tr>
			<tr>
			    <td width="21%" height="28" align="center" bgcolor="#FFFFFF">用户角色：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
					<select name="userRoleId" id="userRoleId" class="editInput" style="width:206px;">
						 <c:forEach items="${roleList}" var="role">
						     <c:choose>
							    <c:when test="${role.id == user.role.id}">
							       <option value="${role.id}" selected="selected">${role.name}</option>
							    </c:when>
							    <c:otherwise>
							       <option value="${role.id}">${role.name}</option>
							    </c:otherwise>
							 </c:choose>
					     </c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">身份证号：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <input id="IdCard" class="editInput" value="${user.idCard }" maxlength="15" dataType="IdCard" msg="身份证号格式不正确！" require="false"/>
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">出生日期：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
			        <input id="birthday" class="Wdate editInput" value="${user.birthday }" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">办公电话：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <input id="officePhone" class="editInput" value="${user.officePhone }" maxlength="8" dataType="Phone" msg="办公电话格式不正确！" require="false"/>
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">email：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <input id="email" class="editInput" value="${user.email }" maxlength="50" dataType="email" msg="email格式不正确！" require="false"/>
				</td>
			</tr>			
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">家庭住址：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				   <textarea id="address" class="editInput" rows="2" cols="50" >${user.homeAddress }</textarea>
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">是否审核：</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				  <c:choose>
						<c:when test="${user.currentState==1}">
							<span style="padding-right: 20px;">
								<label>是</label>
								<input type="radio" checked="checked" name="Status" id="StatusYes" value="1"/>
						    </span>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span style="padding-right: 20px;">
								<label>否</label>
								<input type="radio" name="Status" id="StatusNo" value="0"/>
							</span>
						</c:when>
						<c:otherwise>
						    <span style="padding-right: 20px;">
								<label>是</label>
								<input type="radio" name="Status" id="StatusYes" value="1"/>
						    </span>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
							<span style="padding-right: 20px;">
								<label>否</label>
								<input type="radio" checked="checked" name="Status" id="StatusNo" value="0"/>
							</span>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center" style="padding-top: 20px;" bgcolor="#FFFFFF">
					<input type="hidden" id="userId" value="${user.id }" />
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