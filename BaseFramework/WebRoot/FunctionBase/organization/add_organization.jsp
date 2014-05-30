<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
	<title>组织机构添加</title>
	<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
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
	    
		//加载下拉树形控件
		$("#parentOrganizationId").nfpCommTree({
			ajaxUrl: "<%=contextPath%>/web/organization/json/queryTreeNodeData?companyId=${companyId}",
	        themeUrl:"<%=contextPath%>/common/nfpTreeThemes",
	        dropHeight:150,
	        paramsName:"organizationParentId"
	    });
		
		$("#btnAdd").click(function(){
			var organizationName = jQuery.trim($("#organizationName").val());
			//上级机构id  如果没有上级，则为-1
			var parentOrganizationId = $("#parentOrganizationId").attr("checkedValue");
			var descripe = jQuery.trim($("#descripe").val());
			
			$("#btnAdd").attr("disabled","disabled");
			
			if(!organizationName){
				alert("机构名称不能为空！");
				$("#organizationName").focus();
				$("#btnAdd").removeAttr("disabled");
				return;
			}
			if(!checkForm(document.form)){
				$(this).removeAttr("disabled");
				return;
			}
			$.ajax({
				url		: "<%=contextPath%>/web/organization/json/isExists",
				type	: "POST",
				dataType: "json",
				data	: {"organization.fullName":organizationName,
					       "organization.parent.id":parentOrganizationId,
					       "organization.company.id":${companyId}},
			    success : function(data){
						if(data == 1){
							alert("该公司下的该上级机构已经存在此机构名称");
							$("#btnAdd").removeAttr("disabled");
							$("#organizationName").focus();
						}else{
							$.ajax({
								url		: "<%=contextPath%>/web/organization/json/add",
								type	: "POST",
								dataType: "json",
								data	: {   "organization.fullName":organizationName,
									          "organization.parent.id":parentOrganizationId,
									          "organization.describes":descripe,
									          "organization.company.id":${companyId}},
							    success : function(data){
										if(data == 0){
											alert("机构添加失败");
											$("#btnAdd").removeAttr("disabled");
											$("#organizationName").focus();
										}else{
											alert("机构添加成功");
											parent.frames[0].loadTree();
											parent.frames[0].loadData($(parent.frames[0].document.body).find("#txtCurrentPage").val());
											parent.ClosePop();
										}
									},
							    error : function(){
										alert("机构添加失败");
										$("#btnAdd").removeAttr("disabled");
									}
								});
						}
			    }
			});
		});
	});
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
					<input id="organizationName" class="editInput" maxlength="50"/><span style='color:red'>*</span>
				</td>
			</tr>
			
			<tr>
				<td height="28" align="center" bgcolor="#FFFFFF">
					上级机构：
				</td>
				<td bgcolor="#FFFFFF" style="padding-left:10px;">
					<input id="parentOrganizationId" class="editInput"/>
				</td>
			</tr>
			
			<tr>
				<td height="28" align="center" bgcolor="#FFFFFF">
					机构描述：
				</td>
				<td bgcolor="#FFFFFF" style="padding-left:10px;">
					<textarea id="descripe" class="editInput" rows="5"></textarea>
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center" style="padding-top: 15px;" bgcolor="#FFFFFF">
					<a href="#" id="btnAdd" >
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