<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String contextPath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>角色权限</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/blue/main.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/Vendor/JQuery_zTree_v3/css/demo.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/Vendor/JQuery_zTree_v3/css/zTreeStyle/zTreeStyle.css" />

<style>
	table{font-size:12px;}
	li {color: #000000;list-style-type: none;}
	.roleActive {cursor: pointer;font-weight:bold;color:#3399FE;}
</style> 
	
	
</style> 

<script type="text/javascript" src="<%=contextPath%>/common/js/nfp.core.util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/ajaxPager.js"></script>
<script type="text/javascript" src="<%=contextPath%>/Vendor/JQuery_zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/Vendor/JQuery_zTree_v3/js/jquery.ztree.excheck-3.5.js"></script> 


<script type="text/javascript">
	
	//当前角色标识(DEBUG时默认值为1)
	var currentRoleId = '';

	//
	var isSelectAll = false;
	
	/**
	 * 以下规则定义:完全控制viewControl
	 */
    var allControl = 'allControl';

    /**
	 * 是否只加载一次树(true:是 false;否)
	 */
    var isOnlyFirstLoad = true;

    /**
	 * 是否更改了复选框的状态/值(true:是 false;否)
	 */
    var isChange = false;
    
    /**
	 * 是否火狐客户端(true:是 false;否):用于控制针对Firefox,IE6,7,8等浏览器进行的页面效果控制
	 */
    var isFireFoxClient = false;

	    
	/**
	 * @description:初始化单位下拉框列表
	 */
	function initCompanyList()
	{
			$.ajax({
				url			:"<%=contextPath%>/web/role/json/companyList",
				type		:"POST",
				dataType	:"json",
				success		:function(companylist)
				{
					if(null != companylist){
						var tempStr = '';
						var firstcompanyId = '';//用于暂存第一个角色标识.
						//tempStr += formatSelect("1","请选择");//选择角色时默认选择
						for(var i=0;i<companylist.length;i++)
						{
							tempStr += formatSelect(companylist[i].id,companylist[i].fullName);
						}
						$('#companyId').html(tempStr);

					}else{//边界值情况:数据为空
						$('#companyId').html("");
					}
				},
				error		:function(){
					//alert("获取角色列表错误");
				}
			});
	}
	/**
	 * @description:根据输入的单位标识和单位名称，格式化Select并返回
	 * @param:companyId 单位标识
	 * @param:companyName 单位名称
	 * @return:格式化后的Select标签
	 */
	function formatSelect(companyId,companyName)
	{
			if(null != companyId && '' != companyId && null != companyName && '' != companyName)
				return '<option value="'+companyId+'">' + companyName + '</option>';
			else
				return '';
	}
	
	/**
	 * @description:初始化角色列表
	 */
	function initRoleList()
	{
		    //var companyIdForRefresh=$("#companyId").val();
			//alert(companyIdForRefresh);
			//当浏览器为IE7以下版本时，建议把以下代码注释打开，以防止闪屏事件发生
		    $("div[class^='layout-button-left']:first").hide();
		    
			var currentCompanyId=${sessionScope.companyId};
		    
			$.ajax({
				url			:"<%=contextPath%>/web/role/json/roleList",
				type		:"POST",
				dataType	:"json",
				data        :{"company.id":currentCompanyId},
				success		:function(roleList)
				{
					hideGridLoading();//隐藏ajax层
					
					if(null != roleList)
					{
						var tempStr = '';
						var firstRoleId = '';//用于暂存第一个角色标识.
						for(var i=0;i<roleList.length;i++)
						{
							if(i==0) firstRoleId = roleList[i].id;
							
							tempStr += formatLi(roleList[i].id,roleList[i].name);
						}
						document.getElementById('ulRoleList').innerHTML = tempStr;

						if(null != firstRoleId && '' != firstRoleId)
						{
							//模拟一下点击
							//clickRole(firstRoleId); 
						}
					}
					else
					{//边界值情况:数据为空
						document.getElementById('ulRoleList').innerHTML = '';
					}
				},
				error		:function()
				{
					hideGridLoading();//隐藏ajax层
					alert("获取角色列表错误");
				}
			});
	}
	/**
	 * @description:角色列表刷新
	 */
	function initRoleListForRefresh()
	{
		//展示背景
		 showGridLoading();
		
		var companyIdForRefresh=$("#companyId").val();
		$.ajax({
				url			:"<%=contextPath%>/web/role/json/roleList",
				type		:"POST",
				dataType	:"json",
				data        :{"company.id":companyIdForRefresh},
				success		:function(roleList)
				{
					//隐藏ajax层
					hideGridLoading();
					
					if(null != roleList)
					{
						var tempStr = '';
						var firstRoleId = '';//用于暂存第一个角色标识.
						for(var i=0;i<roleList.length;i++)
						{
							if(i==0) firstRoleId = roleList[i].id;
							
							tempStr += formatLi(roleList[i].id,roleList[i].name);
						}
						document.getElementById('ulRoleList').innerHTML = tempStr;

						if(null != firstRoleId && '' != firstRoleId)
						{
							//模拟一下点击
							//clickRole(firstRoleId); 
						}
					}
					else
					{//边界值情况:数据为空
						document.getElementById('ulRoleList').innerHTML = '';
					}
					
				},
				error		:function(){
					//隐藏ajax层
					hideGridLoading();
					
					//alert("获取角色列表错误");
				}
			});
	}
	/**
	 * @description:根据输入的角色标识和角色名称，格式化Li并返回
	 * @param:roleId 角色标识
	 * @param:roleName 角色名称
	 * @return:格式化后的Li标签
	 */
	function formatLi(roleId,roleName)
	{
			if(null != roleId && '' != roleId && null != roleName && '' != roleName)
				return '<li style="white-space:nowrap;" title="' + roleName + '" id="' + roleId + '" onClick="clickRole(' + roleId + ');">' + roleName + '</li>';
			else
				return '';
	}
	/**
	 * @description:点击左侧的Role节点
	 * @param:roleId 角色标识
	 */
	function clickRole(roleId)
	{
		if(null!=roleId)
		{
			//展示背景
			showGridLoading();
			
			//初始化当前角色标识
			currentRoleId = roleId;
			
			//初始化菜单树
			if(isOnlyFirstLoad)
			{
				fInitPermissionTree(roleId);
				isOnlyFirstLoad = false;
			}
			else
			{
				fInitPermissionTree(roleId);
			}
			
			//获取角色的所有节点
			var allNodes = $("#ulRoleList");
			
			//设置当前节点为选中状态样式
			allNodes.find("li:[id='"+roleId+"']").attr('className','roleActive');
			
			//设置其它节点为非选中状态样式
			allNodes.find("li:not([id='"+roleId+"'])").attr('className','');
		


		//初始化CheckBox的值（选中与否）
		/* if(!isOnlyFirstLoad)
			initCheckBoxValues(currentRoleId); */
		}
	}
	
	


	/**
	 * @ description:权限树
	 */
	var zNodes =[
		     		{ id:1, pId:0, name:"随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选随意勾选 1", open:true},
		     		{ id:11, pId:1, name:"随意勾选 1-1", open:true},
		     		{ id:111, pId:11, name:"随意勾选 1-1-1"},
		     		{ id:112, pId:11, name:"随意勾选 1-1-2"},
		     		{ id:12, pId:1, name:"随意勾选 1-2", open:true},
		     		{ id:121, pId:12, name:"随意勾选 1-2-1"},
		     		{ id:122, pId:12, name:"随意勾选 1-2-2"},
		     		{ id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
		     		{ id:21, pId:2, name:"随意勾选 2-1"},
		     		{ id:22, pId:2, name:"随意勾选 2-2", open:true},
		     		{ id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
		     		{ id:222, pId:22, name:"随意勾选 2-2-2"},
		     		{ id:23, pId:2, name:"随意勾选 2-3"}

		     	];
	var setting = 
	{
			check: {enable: true},
			data: {simpleData: {enable: true}}
	};

	var code;
	function setCheck() 
	{
		var zTree = $.fn.zTree.getZTreeObj("treeObjId"),
		type = { "Y" : "ps", "N" : "ps" };
		setting.check.chkboxType = type;
		showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
		
		$("#py").bind("change", setCheck);
		$("#sy").bind("change", setCheck);
		$("#pn").bind("change", setCheck);
		$("#sn").bind("change", setCheck);
	}
	
	function showCode(str) 
	{
		if (!code) code = $("#code");
			code.empty();
		code.append("<li>"+str+"</li>");
	}

	/**
	 * @description:初始化权限树
	 */
	function fInitPermissionTree(roleId)
	{
		 if(null != currentRoleId && '' != currentRoleId)
			{
				if(currentRoleId == 1)
				{
					alert("不能修改“超级管理员”！");
					return ;
				}
				
				var currentUserRoleId=${sessionScope.roleId};
				
				//判断公司下是否存在用户
				$.ajax({
					url			:"<%=contextPath%>/web/role/json/rolePermissionTree",
					type		:"POST",
					dataType	:"json",
					data		:{"currentUserRoleId":currentUserRoleId,"currentRoleId":currentRoleId},
					success		:function(permissionTree)
					{
						//隐藏ajax层
						hideGridLoading();
						
						//zNodes
						$.fn.zTree.init($("#treeObjId"), setting, permissionTree);
						setCheck();
					}
				});
			}
			else
			{
				alert('请选择一个角色！');
			}
	}

    /**
	 * @description:添加角色
	 */
    function fRoleAdd()
    {
		parent.ShowIframe('添加-角色信息','<%=contextPath%>/web/role/gotoAdd',400,200);
    }
    
    /**
	 * @description:初始化角色修改页面
	 */
    function fRoleEdit()
    {
		if(null != currentRoleId && '' != currentRoleId)
		{
	    	parent.ShowIframe('编辑-角色信息','<%=contextPath%>/web/role/gotoEdit?role.id='+currentRoleId,400,200);			
		}
		else
		{
			alert('请先选择您要编辑的角色信息！');
		}
    }
    /**
	 * @description:删除角色信息
	 */
    function fRoleDelete()
    {
		if(null != currentRoleId && '' != currentRoleId)
		{
			if(currentRoleId == 1)
			{
				alert("不能删除“超级管理员”！");
				return ;
			}
			
			
			//判断公司下是否存在用户
			$.ajax({
				url			:"<%=contextPath%>/web/role/json/isRoleBindingUsers",
				type		:"POST",
				dataType	:"json",
				data		:{"role.id":currentRoleId},
				success		:function(data){

						if(data==1)
						{
							alert("该角色下存在绑定用户，请先删除其绑定关系后再进行此操作！");
						}
						else
						{

							if(confirm('确定删除该角色？'))
							{
						
							 $.ajax({
									url			:"<%=contextPath%>/web/role/json/roleDelete",
									type		:"POST",
									dataType	:"json",
									data		:{"role.id":currentRoleId},
									success		:function(data){
										
										if(data == 1)
										{
											//刷新角色列表
											initRoleListForRefresh();
											//重置当前角色标识为空
											currentRoleId = '';
											//清楚所有选中的checkBox
											clearAllCheckBox();
											//提示删除成功
											alert("删除成功！");
										}
										else
										{
											alert("删除失败！");
										}
									
									},
									error		:function(){
										alert("删除失败！");
									}
								});
							}
						}
				}
			});
		}
		else
		{
			alert('请选择您要删除的角色！');
		}
    }
    
    
	/**
	 * @description:保存当前角色对应的权限信息
	 */
	function fSelectAll() 
	{
		if(null == currentRoleId || '' == currentRoleId)
		{
				alert('请选择角色！');
				return;
		}
		//
		isSelectAll=!isSelectAll;
		//
		var treeObj = $.fn.zTree.getZTreeObj("treeObjId");
		treeObj.checkAllNodes(isSelectAll);
	}
	
   	function fSaveAll() 
    {
    	if(null == currentRoleId || '' == currentRoleId)
       	{
			alert('请选择角色！');
			return;
		}
    	
		var treeObj = $.fn.zTree.getZTreeObj("treeObjId");
		var changeCheckedNodes = treeObj.getChangeCheckedNodes();

   		if (changeCheckedNodes.length>0)
   		{//只有更改了才执行保存操作，否则什么也不做
   			
   			
   			//展示背景
   			showGridLoading();
   			
			var nodes = treeObj.getCheckedNodes(true);
			var updateSource = '';
			for (var i = 0; i < nodes.length; i++) 
			{
				if(i>0) updateSource+=",";
				updateSource+=nodes[i].id;
			}

          	//alert(updateSource);
          	//return;
          	$.ajax({
          		
				url			:"<%=contextPath%>/web/role/json/rolePermissionSave",
				type		:"POST",
				dataType	:"json",
				data		:{"currentRoleId":currentRoleId,"updateSource":updateSource},
				success		:function(flag){
					//隐藏ajax层
					hideGridLoading();
					
					if(flag=1)
					{
						alert("保存成功！");
					}
					else
					{
						alert("保存失败！");
					}
				},
				error		:function(){
					//隐藏ajax层
					hideGridLoading();
					
					alert("保存失败！");
				}
			});
   		}
   		return false;
	}

	/**
	 * @ description:当页面DOM载入就绪后，初始化角色列表
	 */
	$(document).ready(function(){
		
		//初始化ajax层，loading图片
		initGridLoading("<%=contextPath%>/common/Images/gridloading.gif");
		//展示背景
		showGridLoading();
		
		//
		initCompanyList();
		initRoleList();
		
		//alert(navigator.userAgent.indexOf("Chrome"));
		
		//适应火狐,Chrome和IE
		if(navigator.userAgent.indexOf("Firefox") != -1 || navigator.userAgent.indexOf("Chrome") != -1 || navigator.userAgent.indexOf("Safari") != -1)
			isFireFoxClient = true;
		else
			isFireFoxClient = false;
	});
	

</script>
</head>
<body class="easyui-layout">
	 <div region="north" border="false" style="height:60px;background:#B3DFDA;">
	 <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
	 <tr>
		 <td>
			 <%-- 导入页面标题栏 --%>
			<%@ include file="../../common/pagehead.jsp"%>
		 </td>
	 </tr>
     <tr>
       <td>
       	<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="padding-left:10px;height:34px;" class="tit2_background">
					<c:if test="${fn:contains(sessionScope.resourceIds,2)}">
					   <div class="tool_td tool_add" style="height:24px;margin-top:5px;">
					       <a href="javascript:void(0);" onClick="fRoleAdd();">添加角色</a>
					   </div>            
	                </c:if>
	                <c:if test="${fn:contains(sessionScope.resourceIds,3)}">
					   <div class="tool_td tool_edit" style="height:24px;margin-top:5px;">
					       <a href="javascript:void(0);" onClick="fRoleEdit();">编辑角色</a>
					   </div>           
	                </c:if>
					<c:if test="${fn:contains(sessionScope.resourceIds,4)}">
					   <div class="tool_td tool_del" style="height:24px;margin-top:5px;">
					       <a href="javascript:void(0);" onClick="fRoleDelete();">删除角色</a>
					   </div>
	                </c:if>	
	                <c:if test="${fn:contains(sessionScope.resourceIds,9)}">
	                   <div class="tool_td" style="width:135px;height:24px;margin-top:5px;"></div>
					   <div class="tool_td tool_update" style="height:24px;margin-top:5px;">
					        <a href="javascript:void(0);" onClick="fSelectAll();" >全选</a>
					   </div>
					   <div class="tool_td tool_edit" style="height:24px;margin-top:5px;">
					      <a href="javascript:void(0);" onClick="fSaveAll();" >保存权限设置</a>
					   </div>
	                </c:if>	
		  		</td>
		  	</tr>
		 </table>
        </td>
      </tr>
    </table>
	</div>
	
	<!-- 当浏览器为IE7以下版本时，建议把 split属性值改为false，以防止闪屏事件发生 -->
	<div region="west" id="west" split="false" title="角色列表" style="width:200px;">
		<div align="center">
		<select id="companyId" onchange="initRoleListForRefresh();">
		</select>
		</div>
		<!-- 角色列表 -->
		<ul id="ulRoleList"></ul>
	</div>
	<div region="center" id="pnMain">
		<ul id="treeObjId" class="ztree" ></ul>
	</div>
</body>
</html>