<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>添加-公司信息</title>
<link rel="stylesheet"  href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
<link href="<%=contextPath%>/common/site.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/themes/default/easyui.css">
<link rel="stylesheet" href="<%=contextPath%>/common/nfpTreeThemes/nfpTreeCss.css" type="text/css"></link>

<script type="text/javascript"	src="<%=contextPath%>/common/js/jquery.js"></script>
<script type="text/javascript"	src="<%=contextPath%>/common/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/ajaxPager.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/nfpTree.js"></script>
	
	
<script type="text/javascript">
	$(function(){
	    
		//加载下拉树形控件
		$("#areaId").nfpCommTree({
			ajaxUrl: "<%=contextPath%>/web/area/json/queryTreeNodeData",
	        themeUrl:"<%=contextPath%>/common/nfpTreeThemes",
	        dropHeight:150,
	        paramsName:"areaParentId"
	    });
    	
		$("#companyCode").focus();
		
		$("#btnSubmit").click(function(){
			//上级机构id  如果没有上级，则为-1
			var areaId = $("#areaId").attr("checkedValue");
		    var companyCode = jQuery.trim($("#companyCode").val());
			var companyName = jQuery.trim($("#companyName").val());
			var companyType=$("input[name='companyType']:checked").val();
			var address = jQuery.trim($("#address").val());
			var phone = jQuery.trim($("#phone").val());
			var corporationId = jQuery.trim($("#corporationId").val());
			var corporation = jQuery.trim($("#corporation").val());
			var principal = jQuery.trim($("#principal").val());
			var linkman = jQuery.trim($("#linkman").val());
			var email = jQuery.trim($("#email").val());
			var companyDesc=jQuery.trim($("#companyDesc").val());
			var remarks=jQuery.trim($("#remarks").val());
			
			
			if(!companyCode)
			{
				alert("登录代码不能为空");
				$("#companyCode").focus();
				return;
			}
			if(!companyName)
			{
				alert("公司名称不能为空");
				$("#companyCode").focus();
				return;
			}
			if(!checkForm(document.form)) 
			{
				return;
			}
			$.ajax({
				url			:"<%=contextPath%>/web/company/json/addCompany",
				type		:"POST",
				dataType	:"json",
				data		:{"company.area.id":areaId,
							  "company.companyCode":companyCode,
							  "company.companyName":companyName,
							  "company.address":address,
							  "company.telephone":phone,
							  "company.corporationId":corporationId,
							  "company.corporationName":corporation,
							  "company.principalName":principal,
							  "company.linkmanName":linkman,
							  "company.companyType":companyType,
							  "company.email":email,
							  "company.describes":companyDesc,
							  "company.remarks":remarks
							  },
				success		:function(data){
					if(data == 0)
					{
						 alert("保存失败！");
						 return;
					}
					else if(data == 1)
					{
						alert("登录代码重复，请重新输入!");
						$("#companyCode").focus();
						return;
					}else if(data == 2)
					{
						alert("公司名称重复，请重新输入!");
						$("#companyName").focus();
						return;
					}
					else
					{
						alert("保存成功！");
						parent.frames[0].loadData($(parent.frames[0].document.body).find("#txtCurrentPage").val());
						parent.ClosePop();
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
<body class="popDiv" >
 <form name="form" >
	 <table width="90%" cellpadding="0" cellspacing="1" bgcolor="#c1dbfc" style="margin-top:1px;margin-left:20px;">
	    <tr>
			<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
				所属区域:
			</td>
			<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				<input id="areaId" class="editInput" style="width:90%"><span style='color:red'>*</span>
			</td>
		</tr>
	    <tr>
			<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
				公司代码:
			</td>
			<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				<input type="text" id="companyCode" maxlength="100" class="editInput" style="width:90%"><span style='color:red'>*</span>
			</td>
		</tr>
		<tr>
			<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
				公司名称:
			</td>
			<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				<input type="text" id="companyName" maxlength="100" class="editInput" style="width:90%"><span style='color:red'>*</span>
			</td>
		</tr>
		<tr>
			<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
				公司地址:
			</td>
			<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				<input type="text" id="address" maxlength="200" class="editInput" style="width:90%">
			</td>
		</tr>
		<tr>
			<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
				联系电话:
			</td>
			<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;" style="width:90%">
				<input type="text" id="phone" maxlength="40" class="editInput" style="width:90%">
			</td>
		</tr>
		<tr>
			<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
				公司法人:
			</td>
			<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				<input type="text" id="corporation" maxlength="100" class="editInput" style="width:90%">
			</td>
		</tr>
		<tr>
			<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
				工商注册号:
			</td>
			<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				<input type="text" id="corporationId" maxlength="100" class="editInput" style="width:90%">
			</td>
		</tr>
		<tr>
			<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
				所属派出所:
			</td>
			<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				<input type="text" id="remarks" maxlength="100" class="editInput" style="width:90%">
			</td>
		</tr>
		<tr style="display: none">
			<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
				公司负责人：
			</td>
			<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				<input type="text" id="principal" maxlength="200" class="editInput" style="width:90%">
			</td>
		</tr>
		<tr style="display: none">
			<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
				公司联系人：
			</td>
			<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				<input type="text" id="linkman" maxlength="40" class="editInput" style="width:90%">
			</td>
		</tr>
		<tr style="display: none">
			<td  width="21%" height="28" align="center" bgcolor="#FFFFFF">
				公司类型:
			</td>
			<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				<span style="padding-right: 20px;">
					<input type="radio" name="companyType" value="1" /> 
					<label>平台</label> 
				</span>
				<input type="radio" name="companyType" value="0" checked="true"/>
					<label>企业</label>
			</td>
		</tr>
		<tr style="display: none">
			<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
				邮件地址：
			</td>
			<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				<input type="text" id="email" maxlength="200" class="editInput" style="width:90%">
			</td>
		</tr>
		<tr style="display: none">
			<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
				公司描述：
			</td>
			<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
				<textarea id="companyDesc" cols="47" rows="4" class="editInput" style="width:90%"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" style="padding-top:15px;padding-bottom:5px" bgcolor="#FFFFFF">						
				<a href="#" id="btnSubmit" >
				   <img src="<%=contextPath%>/common/Images/save.gif"/>
				</a>
			    &nbsp;&nbsp;&nbsp;&nbsp;
			    <a href="#" id="btnCancel" onclick="parent.ClosePop();">
			       <img src="<%=contextPath%>/common/Images/close.gif"/>
			    </a>
			</td>
		</tr>
	</table>
</form>
</body>
</html>