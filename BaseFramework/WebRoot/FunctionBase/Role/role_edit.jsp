<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>编辑-角色信息</title>
<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
<link href="<%=contextPath%>/common/site.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
<script type="text/javascript">
	$(function(){
	$("#roleName").focus();
		initCompanyList();
		$("#btnSubmit").click(function(){
			
			var companyId = jQuery.trim($("#companyId").val());
			var roleId = jQuery.trim($("#roleId").val());
			var roleName = jQuery.trim($("#roleName").val());
			var remarks = jQuery.trim($("#remarks").val());


			if(null==roleName || ""==roleName)
			{
				alert('角色名称不能为空');
				$("#roleName").focus();
				return;
			}
			if(!checkForm(document.form))
				return;

			$.ajax({
				url			:"<%=contextPath%>/web/role/json/isExistRole",
				type		:"POST",
				dataType	:"json",
				data		:{"role.id=":roleId,"role.name":roleName,"company.id":companyId},
				success		:function(data){
						if(data.isDistinctExists){
							alert('角色名称重复，请重新输入！');
							$("#roleName").focus();
							return;
						}else{
							$.ajax({
								url			:"<%=contextPath%>/web/role/json/roleEdit",
								type		:"POST",
								dataType	:"json",
								data		:{"role.id=":roleId,"role.name":roleName,"role.remarks":remarks,"company.id":companyId},
								success		:function(data)
								{
									if(data == 1)
									{
										alert("保存成功！");
										<%-- 
										$.ajax({
											url			:"<%=contextPath%>/web/user/json/getDepAndRoleNamesForIndex.action",
											type 		: "POST",
											dataType 	: "json",
											success 	: function(data) {
												if (data != null) {
													parent.frames[0].setDepRoleNames(data[0], data[1], data[2]);
												}
											},
											error 		: function() {
												alert("查询失败！");
												alert("请检查您的网络环境,确保其连接正常！");
											}
										});
										//初始化角色列表
										parent.frames[0].initRoleListForRefresh();
										parent.ClosePop(); --%>
									}
									else
									{
										alert("保存失败！");
									}

								},
								error		:function()
								{
									alert("保存失败！");
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
	/**
	 * @description:初始化单位下拉框列表
	 */
	function initCompanyList()
	{
			$.ajax({
				url			:"<%=contextPath%>/web/role/json/companyList",
				type		:"POST",
				dataType	:"json",
				success		:function(companyList){
					if(null != companyList)
					{
						var tempStr = '';
						var firstCompanyId = '';//用于暂存第一个角色标识.
						for(var i=0;i<companyList.length;i++)
						{
							tempStr += formatSelect(companyList[i].id,companyList[i].fullName);
						}
						$('#companyId').html(tempStr);
						
					}
					else
					{//边界值情况:数据为空
						$('#companyId').html("");
					}
				},
				error		:function(){
					//alert("获取角色列表错误");
				}
			});
	}
	var cid=parent.frames[0].queryParentCid();
	/**
	 * @description:根据输入的单位标识和单位名称，格式化Select并返回
	 * @param:companyId 单位标识
	 * @param:companyName 单位名称
	 * @return:格式化后的Select标签
	 */
	function formatSelect(companyId,companyName){
		
		if(null != companyId 
				&& '' != companyId 
				&& null != companyName 
				&& '' != companyName){
				if(companyId==cid){
					return '<option selected="selected" value="'+companyId+'">' + companyName + '</option>';
				}else{
					return '<option value="'+companyId+'">' + companyName + '</option>';
				}
			}
		else{
			return '';
			}
	}
</script>
<style>
	table{font-size:12px;}
</style>
</head>
<body class="popDiv">
<form name="form" >
  <table width="90%" cellpadding="0" cellspacing="1" bgcolor="#c1dbfc" style="margin-top:1px;margin-left:20px;">
    <tr>
      <td width="21%" height="28" align="center" bgcolor="#FFFFFF">角色名称</td>
      <td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
      	<input type="hidden" id="roleId" value="${role.id }">
		<input type="text" id="roleName" value="${role.name}" maxlength="50"  class="editInput"/><span style='color:red'>*</span></td>
    </tr>
    <tr>
      <td width="21%" height="28" align="center" bgcolor="#FFFFFF">所属单位名称</td>
      <td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
      		<select id="companyId"></select><span style='color:red'>*</span>
      </td>
    </tr>
    <tr>
      <td width="21%" height="28" align="center" bgcolor="#FFFFFF">备&nbsp;&nbsp;&nbsp;&nbsp;注</td>
      <td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
      		<input id="remarks" maxlength="100"  value="${role.remarks}" class="editInput"/>
      </td>
    </tr>
    <tr>
      
     <td colspan="2" align="center" style="padding-top: 15px;" bgcolor="#FFFFFF">						
		<a href="#" id="btnSubmit" ><img src="<%=contextPath%>/common/blue/Images/save.gif"/></a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" id="btnClose" onclick="javascript:parent.ClosePop();"><img src="<%=contextPath%>/common/blue/Images/close.gif"/></a>
	</td>
    </tr>
  </table>
</form>
</body>
</html>