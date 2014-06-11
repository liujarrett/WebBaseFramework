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
		
		initCompanyList();
		
		$("#roleName").focus();
		
		$("#btnSubmit").click(function(){
			
			var companyId = jQuery.trim($("#companyId").val());
			var roleId = jQuery.trim($("#roleId").val());
			var roleName = jQuery.trim($("#roleName").val());
			var remarks = jQuery.trim($("#remarks").val());

			var currentState=${role.currentState};
			var isDelete=${role.isDelete};
			var createTime="${role.createTime}";

			if(null==roleName || ""==roleName)
			{
				alert('角色名称不能为空');
				$("#roleName").focus();
				return;
			}
			
			if(!checkForm(document.form))
				return;

			$.ajax({
				url			:"<%=contextPath%>/web/role/json/roleEdit",
				type		:"POST",
				dataType	:"json",
				data		:{	"role.id":roleId,
								"role.name":roleName,
								"role.remarks":remarks,
								"role.companyId":companyId,
								"role.currentState":currentState,
								"role.isDelete":isDelete,
								"role.createTime":createTime},
				success		:function(data) {
					
					if(data == 1)
					{
						alert("保存成功！");
						//初始化角色列表
						parent.frames[0].initRoleListForRefresh();
						parent.ClosePop(); 
					}
					else if(data==2)
					{
						alert('角色名称重复，请重新输入！');
						$("#roleName").focus();
						return;
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
		});
	});
	/**
	 * @description:初始化公司下拉框列表
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
							tempStr += formatSelect(companyList[i].id,companyList[i].companyName);
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
	/**
	 * @description:根据输入的公司标识和公司名称，格式化Select并返回
	 * @param:companyId 公司标识
	 * @param:companyName 公司名称
	 * @return:格式化后的Select标签
	 */
	function formatSelect(companyId,companyName)
	{
		if(null != companyId 
				&& '' != companyId 
				&& null != companyName 
				&& '' != companyName)
		{
			if(companyId==${role.companyId})
			{
				return '<option selected="selected" value="'+companyId+'">' + companyName + '</option>';
			}
			else
			{
				return '<option value="'+companyId+'">' + companyName + '</option>';
			}
		}
		else
		{
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
      <td width="21%" height="28" align="center" bgcolor="#FFFFFF">所属公司名称</td>
      <td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
      		<select id="companyId"></select><span style='color:red'>*</span>
      </td>
    </tr>
    
    <tr>
      <td width="21%" height="28" align="center" bgcolor="#FFFFFF">角色名称</td>
      <td width="79%" height="28" bgcolor="#FFFFFF" style="padding-left:10px;">
      	<input type="hidden" id="roleId" value="${role.id }">
		<input type="text" id="roleName" value="${role.name}" maxlength="50"  class="editInput"/><span style='color:red'>*</span></td>
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