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
			var organizationId = jQuery.trim($("#organizationId").val());
			var organizationName = jQuery.trim($("#organizationName").val());
			var parentorganizationId = $("#parentorganizationId").attr("checkedValue");
			var descripes = jQuery.trim($("#descripes").val());
			
			$("#btnSave").attr("disabled","disabled");
			
			if(!organizationName){
				alert("机构名称不能为空！");
				$("#organizationName").focus();
				$("#btnSave").removeAttr("disabled");
				return;
			}
			if(!checkForm(document.form)){
				$(this).removeAttr("disabled");
				return;
			}
			if(organizationId == parentorganizationId) {
				alert("上级部门不能是自己!");
				return ;
			}
		
			$.ajax({
					url : "<%=contextPath%>/web/organization/json/isExists",
					type : "POST",
					dataType : "json",
					data : {
						    "organization.id":organizationId,
						    "organization.fullName":organizationName,
						    "organization.parent.id":parentorganizationId,
						    "organization.company.id":${companyId}
					        },
					success	:function(data){
							if(data == 1){
								alert("该公司下的该上级部门下的该部门名称已被注册！");
							    $("#btnSave").removeAttr("disabled");
								$("#organizationName").focus();
							}else{
								$.ajax({
									url	:"<%=contextPath%>/web/organization/json/modify",
									type : "POST",
									dataType 	: "json",
									data : {
									    "organization.id":organizationId,
									    "organization.fullName":organizationName,
									    "organization.parent.id":parentorganizationId,
									    "organization.descripes":descripes,
									    "organization.company.id":${companyId}
								        },
									success : function(data) {
										        	 alert("修改成功！");
										        	 parent.frames[0].loadTree();
													 parent.frames[0].loadData($(parent.frames[0].document.body).find("#txtCurrentPage").val());
													 parent.ClosePop();
											  },
									error : function() {
												alert("修改失败！");
											}
								});
							}
						},
					error :function(){
							alert("修改出错！");
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
						机构名称：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<input value="<c:out value='${organization.fullName }'/>" id="organizationName"  class="editInput" maxlength="50"/><span style='color:red'>*</span>
					</td>
				</tr>
				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						上级机构：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<input id="parentorganizationId"  value='<c:out value="${organization.parent.fullName}"/>' class="editInput" />
					</td>
				</tr>
				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						机构描述：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<textarea id="descripes" rows="5" value="<c:out value='${organization.describes }'/>" class="editInput" maxlength="100"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" style="padding-top: 15px;" bgcolor="#FFFFFF">
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