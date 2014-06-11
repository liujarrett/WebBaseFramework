<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/nfpTree.js"></script>
<style>
    html{overflow:hidden;}
</style>
<script language="javascript">
	$(function(){
		//初始化值
		var oldParentId = jQuery.trim($("#oldParentId").val());
		
		if(oldParentId) {
			$("#parentareaId").attr("checkedValue",oldParentId);
		}
		
		$("#parentareaId").nfpCommTree({
			ajaxUrl: "<%=contextPath%>/web/area/json/queryTreeNodeData",
	        themeUrl:"<%=contextPath%>/common/nfpTreeThemes",
	        dropHeight:150,
	        paramsName:"areaParentId"
	    });
		
		$("#btnSave").click(function(){
			var areaId = jQuery.trim($("#areaId").val());
			var areaName = jQuery.trim($("#areaName").val());
			var parentareaId = $("#parentareaId").attr("checkedValue");
			var areaCode = jQuery.trim($("#areaCode").val());
			
			$("#btnSave").attr("disabled","disabled");
			
			if(!areaName){
				alert("区域名称不能为空！");
				$("#areaName").focus();
				$("#btnSave").removeAttr("disabled");
				return;
			}
			if(!checkForm(document.form)){
				$(this).removeAttr("disabled");
				return;
			}
			if(areaId == parentareaId) {
				alert("上级区域不能是自己!");
				return ;
			}
			
			$.ajax({
				url	:"<%=contextPath%>/web/area/json/editArea",
				type : "POST",
				dataType 	: "json",
				data : {
				    "area.id":areaId,
				    "area.areaName":areaName,
				    "area.parent.id":parentareaId,
				    "area.areaCode":areaCode},
				success : function(data) {
						if(data == 1) {
							 alert("修改成功！");
				        	 parent.frames[0].loadTree();
							 parent.frames[0].loadData($(parent.frames[0].document.body).find("#txtCurrentPage").val());
							 parent.ClosePop();
						} else if(data == 2) {
							alert("该上级区域已经存在同名下级区域!");
						    $("#btnSave").removeAttr("disabled");
						} else if(data == 3) {
							alert("不能选择子机构作为父机构！");
				    		$("#btnSave").removeAttr("disabled");
						} 
						else 
						{
							alert("修改失败！");
						}
					},
				error : function() {
							alert("修改失败！");
						}
			});
			
		});
	});
</script>
</head>
<body class="popDiv">
		<form name="form" >
		    <input value="${area.id }" type="hidden" id="areaId"/>
		    <input value="${area.parent.id }" type="hidden" id="oldParentId"/>
			<table width="90%" cellpadding="0" cellspacing="1" bgcolor="#c1dbfc" style="margin-top:1px;margin-left:20px;">
				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						上级区域：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<input id="parentareaId"  value="${area.parent.areaName}" class="editInput" />
					</td>
				</tr>
				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						区域名称：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<input value="${area.areaName }" id="areaName"  class="editInput" maxlength="50"/><span style='color:red'>*</span>
					</td>
				</tr>
				<tr>
					<td width="21%" height="28" align="center" bgcolor="#FFFFFF">
						区域编码：
					</td>
					<td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
						<input id="areaCode"  value="${area.areaCode}" class="editInput" />
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