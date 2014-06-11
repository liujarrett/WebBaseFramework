<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <title>区域管理</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/blue/main.css" ></link>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/nfpTreeThemes/nfpTreeCss.css" ></link>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/ajaxPager.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/nfpTree.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.simple.tree.js"></script>

<script language="javascript">
	var currentAreaId = -1;
	
	$(function(){
	    $("div[class^='layout-button-left']:first").hide();
	    
		initGridLoading("<%=contextPath%>/common/blue/Images/gridloading.gif"); //初始化ajax层，loading图片
		
		//加载树
		loadTree();
		
		
		$("#btnAdd").click(function(){
			parent.ShowIframe("添加-区域信息","<%=contextPath%>/web/area/gotoAdd",400,250);
		});
		
	});
	
	
	function loadTree(){
    	$("#areaTree").nfpTree({
	        ajaxUrl: "<%=contextPath%>/web/area/json/queryTreeNodeData",
	        themeUrl:'<%=contextPath%>/common/nfpTreeThemes',
	        paramsName:"areaParentId",
	 		expandLevel:2,
	 		clickStyle:false,
	        afterClick: function (node, treeObj) 
	      	{
	        	var nodeObj=node.children("[isNodeText]")
				$("span.active",$('#areaTree')).attr("class","text");
				nodeObj.attr("class","active");   	//切换选中节点颜色
				currentAreaId = node.attr("code"); //currentOrganizationId 为 组织id
				loadData(1);
	        }
	    });
    }
	
    function loadData(pageIndex) 
    {
		var gridAction="<%=contextPath%>/web/area/queryForGrid";
		if(!pageIndex) 
		{
			pageIndex="${pageBean.currentPage}";
		}
		showGridLoading();
		$("#divGrid").load(gridAction,{
			"areaParentId":currentAreaId,
			"pageBean.currentPage":pageIndex
		    },
			function(){
				hideGridLoading();
			}
         );
	}
</script>
</head>
<body class="easyui-layout">
	<div region="north" border="false" style="height:34;background:#FFFFFF;" id='cidDiv'>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="nei_box" align="center">
			<%-- 导入页面标题栏 --%>
			<tr>
				<td>
					<%@ include file="../../common/pagehead.jsp"%>
				</td>
			</tr>
		  	<%-- 工具栏 --%>
			<tr>
		     <td height="34" class="tit2_background">
			     <table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td style="padding-left:10px;" >
			                <c:if test="${fn:contains(sessionScope.resourceIds,12)}">
		                        <div class="tool_td tool_add"><a href="#" id="btnAdd">添加区域</a></div>
                            </c:if>
					 		<div class="tool_td tool_word" style="display: none;"><a href="#" id="btnOutWord">Word导出</a></div>
					 		<div class="tool_td tool_excel" style="display: none;"><a href="#" id="btnOutExcel">Excel导出</a></div>
			            </td>
			          </tr>
			      </table>
		      </td>
		  	</tr>
		</table>
	</div>
	<div region="west" id="west" split="false" title="区域" style="width:200px;padding:10px;">
		<ul id="areaTree"></ul>
	</div>
	<div region="center" title="区域列表" style="width:600px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="nei_box" align="center">
		  <tr>
		     <td>
		       <div class="s" id="divGrid"></div>
		     </td>
		  </tr>
		</table>
	</div>
</body>
</html>