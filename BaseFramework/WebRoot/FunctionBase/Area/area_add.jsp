<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
	<title>区域添加</title>
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
	    $("#areaName").focus();
	    
		//加载下拉树形控件
		$("#parentId").nfpCommTree({
			ajaxUrl: "<%=contextPath%>/web/area/json/queryTreeNodeData",
	        themeUrl:"<%=contextPath%>/common/nfpTreeThemes",
	        dropHeight:150,
	        paramsName:"areaParentId"
	    });
		
		$("#btnAdd").click(function(){
			
			var parentId = $("#parentId").attr("checkedValue");
			//区域名称
			var areaName = jQuery.trim($("#areaName").val());
			//区域编号
			var areaCode = jQuery.trim($("#areaCode").val());
			
			$("#btnAdd").attr("disabled","disabled");
			
			if(!areaName)
			{
				alert("区域名称不能为空！");
				$("#areaName").focus();
				$("#btnAdd").removeAttr("disabled");
				return;
			}
			if(!checkForm(document.form))
			{
				$(this).removeAttr("disabled");
				return;
			}
			$.ajax({
				url		: "<%=contextPath%>/web/area/json/add",
				type	: "POST",
				dataType: "json",
				data	: { "area.parent.id":parentId,
							"area.areaName":areaName,
							"area.areaCode":areaCode },
			    success : function(data){
						if(data == 2)
						{
							alert("该上级区域已经存在此区域名称");
							$("#btnAdd").removeAttr("disabled");
							$("#areaName").focus();
						}
						else if(data == 1)
						{
							alert("添加成功");
							parent.frames[0].loadTree();
							parent.frames[0].loadData($(parent.frames[0].document.body).find("#txtCurrentPage").val());
							parent.ClosePop();
						}
						else
						{
							alert("添加失败");
							$("#btnAdd").removeAttr("disabled");
							$("#areaName").focus();
						}
			    },
		    	error : function(){
					alert("添加失败，请重试!");
					$("#btnAdd").removeAttr("disabled");
				}
			});
		});
	});
</script>
</head>
<body class="popDiv">
	<form name="form" >
		<table width="90%" cellpadding="0" cellspacing="1" bgcolor="#c1dbfc" style="margin-top:10px;margin-left:20px;">
			<tr>
				<td width="21%" height="30" align="center" bgcolor="#FFFFFF">
					上级区域：
				</td>
				<td width="79%" height="30" bgcolor="#FFFFFF" style="padding-left:10px;">
					<input id="parentId" class="editInput" style="width:90%"/>
				</td>
			</tr>
			
			<tr>
				<td width="21%" height="30"align="center" bgcolor="#FFFFFF">
					区域名称：
				</td>
				<td width="79%" height="30" bgcolor="#FFFFFF" style="padding-left:10px;">
					<input id="areaName" class="editInput" style="width:90%" maxlength="50"/><span style='color:red'>*</span>
				</td>
			</tr>
			
			<tr>
				<td width="21%" height="30" align="center" bgcolor="#FFFFFF">
					区域编号：
				</td>
				<td width="79%" height="30" bgcolor="#FFFFFF" style="padding-left:10px;">
					<input id="areaCode" class="editInput" style="width:90%" maxlength="50"/>
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center" style="padding-top:10px;padding-bottom:10px;" bgcolor="#FFFFFF">
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