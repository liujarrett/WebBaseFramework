<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>编辑公司信息</title>
<link href="<%=contextPath%>/common/site.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
<script type="text/javascript">
	$(function(){
		if(${company.companyType == 1})
		{
			$("#plat").attr("checked", true);
			$("#com").attr("checked", false);
		}
		else
		{
			$("#plat").attr("checked", false);
			$("#com").attr("checked", true);
		}
	    
		$("#companyName").focus();
		
	    $("#btnSubmit").click(function(){
			var companyName = jQuery.trim($("#companyName").val());
			var address = jQuery.trim($("#address").val());
			var phone = jQuery.trim($("#phone").val());
			var corporation = jQuery.trim($("#corporation").val());
			var principal = jQuery.trim($("#principal").val());
			var linkman = jQuery.trim($("#linkman").val());
			var email = jQuery.trim($("#email").val());
			var companyType=$("input[name='companyType']:checked").val();
			var companyDesc = jQuery.trim($("#companyDesc").val());
			
		
			
			if(!companyName){
				alert("单位名称不能为空");
				$("#companyName").focus();
				return;
			}
			if(!checkForm(document.form)) {
				return ;
			}
			
			$.ajax({
				url			:"<%=contextPath%>/web/company/json/companyIsExists",
				type		:"POST",
				dataType	:"json",
				data		:{"company.fullName":companyName,"company.id":${company.id}},
				success		:function(data){
						if(data==1){
							alert("单位名称已存在，请重新输入!");
							$("#companyName").focus();
							return;
						}else{
							$.ajax({
								url			:   "<%=contextPath%>/web/company/json/modifyCompany.action",
								type		:"POST",
								dataType	:"json",
								data		:{
												"company.id":${company.id},
												"company.companyCode":"${company.companyCode}",
												"company.companyType":companyType,
												"company.fullName":companyName,
												"company.corporationName":corporation,
												"company.principalName":principal,
												"company.linkmanName":linkman,
												"company.address":address,
												"company.telephone":phone,
												"company.email":email,
												"company.describes":companyDesc,
												"company.currentState":${company.currentState},
												"company.isDelete":${company.isDelete}
											},
								success		:function(data){
												if(data == 1) {
													alert("保存成功！");
													parent.frames[0].loadData($(parent.frames[0].document.body).find("#txtCurrentPage").val());
													parent.ClosePop();
												} else {
													alert("保存失败!");
												}
								},
								error		:function(){
									alert("保存失败!");
								}
							});
						}
				},
				error		:function(){
					alert("保存失败！");
				}
			});
	
		});
	});
	
</script>

</head>
<body class="popDiv" style='overflow: hidden;'>
<form name="form" >
    <table width="90%" cellpadding="0" cellspacing="1" bgcolor="#c1dbfc" style="margin-top:1px;margin-left:20px;">
    		
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					单位名称:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
			        <input type="text" id="companyName" value="${company.fullName }" maxlength="100" class="editInput"><span style='color:red'>*</span>
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					单位地址:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <input type="text" id="address" value="${company.address }" maxlength="200" class="editInput">
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					联系电话:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
					<input type="text" id="phone" value="${company.telephone }" maxlength="40" class="editInput">
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					单位法人:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <input type="text" id="corporation" value="${company.corporationName }" maxlength="200" class="editInput">
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					单位负责人:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
					<input type="text" id="principal" value="${company.principalName }" maxlength="40" class="editInput">
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					单位联系人:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <input type="text" id="linkman" value="${company.linkmanName }" maxlength="200" class="editInput">
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					邮箱地址:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				    <input type="text" id="email" value="${company.email }" maxlength="200" class="editInput">
				</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					平台类型:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
					<span style="padding-right: 20px;">
					     <input type="radio" name="companyType" id="plat" value="1"/>
					     <label>平台</label> 
				    </span>
						<input type="radio" name="companyType" id="com" value="0"/>
						<label>企业</label>
					</td>
			</tr>
			<tr>
				<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
					单位描述:
				</td>
				<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				   <textarea id="companyDesc" cols="47" rows="4" class="editInput">${company.describes }</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center" style="padding-top: 15px;" bgcolor="#FFFFFF">						
					<a href="#" id="btnSubmit" >
					   <img src="<%=contextPath%>/common/blue/Images/save.gif"/>
					</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#" id="btnCancel" onclick="javascript:parent.ClosePop();">
					   <img src="<%=contextPath%>/common/blue/Images/close.gif"/>
					</a>
				</td>
			</tr>
		</table>
</form>

</body>
</html>