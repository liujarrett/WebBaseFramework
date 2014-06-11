<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>修改机构信息</title>
	<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
	<link rel="stylesheet" href="<%=contextPath%>/common/site.css" type="text/css" />
	<link rel="stylesheet" href="<%=contextPath%>/common/nfpTreeThemes/nfpTreeCss.css" type="text/css"></link>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/nfpTree.js"></script>
<style>
    html{overflow:hidden;}
</style>
<script language="javascript">
	$(function(){
	    $("#organizationName").focus();
		//初始化值
		var oldParentId = jQuery.trim($("#oldParentId").val());
		
		if(oldParentId) {
			$("#parentorganizationId").attr("checkedValue",oldParentId);
		}
		
		$("#parentorganizationId").nfpCommTree({
			ajaxUrl: "<%=contextPath%>/web/organization/json/queryTreeNodeData?companyId=${companyId}",
	        themeUrl:"<%=contextPath%>/common/nfpTreeThemes",
	        dropHeight:150,
	        paramsName:"organizationParentId"
	    });
		
		$("#btnSave").click(function(){
			var companyId=${companyId};
			var organizationId = jQuery.trim($("#organizationId").val());
			var organizationName = jQuery.trim($("#organizationName").val());
			var parentorganizationId = $("#parentorganizationId").attr("checkedValue");
			var descripes = jQuery.trim($("#descripes").val());
			
			$("#btnSave").attr("disabled","disabled");
			
			if(organizationId == parentorganizationId) 
			{
				alert("自己不能是上级机构!");
				return ;
			}
			if(!organizationName)
			{
				alert("机构名称不能为空！");
				$("#organizationName").focus();
				$("#btnSave").removeAttr("disabled");
				return;
			}
			if(!checkForm(document.form))
			{
				$(this).removeAttr("disabled");
				return;
			}

			$.ajax({
				url	:"<%=contextPath%>/web/organization/json/modify",
				type : "POST",
				dataType 	: "json",
				data : {
					"organization.company.id":companyId,
				    "organization.id":organizationId,
				    "organization.organizationName":organizationName,
				    "organization.parent.id":parentorganizationId,
				    "organization.describes":descripes},
				success : function(data) {
							if (data==0)
							{
								alert("修改失败！");
								$("#btnSave").removeAttr("disabled");
							}
							else if (data==1)
							{
								alert("不能选择子机构作为父机构！");
								$("#btnSave").removeAttr("disabled");
							}
							else
							{
			        			alert("修改成功！");
			        			parent.frames[0].loadTree();
						 		parent.frames[0].loadData($(parent.frames[0].document.body).find("#txtCurrentPage").val());
						 		parent.ClosePop();
							}
						},
				error : function() {
							alert("修改失败！");
							$("#btnSave").removeAttr("disabled");
						}
			});
		});
	});
</script>
</head>
<body class="popDiv">
		<form name="form" >
		    <input value="${organization.id }" type="hidden" id="organizationId"/>
		    <input value="${organization.parent.id }" type="hidden" id="oldParentId"/>
			<table width="90%" cellpadding="0" cellspacing="1" bgcolor="#c1dbfc" style="margin-top:1px;margin-left:20px;">
				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						上级机构：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<input style="width:90%" id="parentorganizationId"  value='<c:out value="${organization.parent.organizationName}"/>' class="editInput" />
					</td>
				</tr>
				
				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						机构名称：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<input style="width:90%" value="<c:out value='${organization.organizationName }'/>" id="organizationName"  class="editInput" maxlength="50"/><span style='color:red'>*</span>
					</td>
				</tr>

				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						机构描述：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<textarea style="width:90%" id="descripes" rows="5"  class="editInput" maxlength="100"><c:out value="${organization.describes}"/></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" style="padding-top:15px;" bgcolor="#FFFFFF">
					    <a href="#" id="btnSave" >
					       <img src="<%=contextPath%>/common/blue/Images/save.gif"/>
					    </a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#" id="btnClose" onclick="parent.ClosePop();">
						   <img src="<%=contextPath%>/common/blue/Images/close.gif"/>
						</a>
					</td>
				</tr>
			</table>
		</form>
</body>
</html>