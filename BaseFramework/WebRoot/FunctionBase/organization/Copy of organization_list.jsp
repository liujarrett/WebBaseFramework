<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String contextPath = request.getContextPath();
%>
<html> 
<head>
    <title>组织机构列表</title>
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
	var currentOrganizationId = -1;
	var currentTreeCompanyId = -1; //组织机构树状图当前的CompanyId
	$(function(){
	    $("div[class^='layout-button-left']:first").hide();
	    
		var gridAction="<%=contextPath%>/web/department/queryForList.action";
		initGridLoading("<%=contextPath%>/common/blue/Images/gridloading.gif"); //初始化ajax层，loading图片
		//加载树
		loadTree();
		loadData(1);
		
		$("#btnReset").click(function(){
			$("#search_name").val("")
			loadData(1);
		})
		$("#btnSearch").click(function(){
			if(currentTreeCompanyId != $("#companyId").val()) {
				loadTree();
				currentOrganizationId = -1;
			}
			var likeDepartmentName=jQuery.trim($("#search_name").val());
			$("#departmentName").val(jQuery.trim($("#search_name").val()));
			$("#departmentName1").val(jQuery.trim($("#search_name").val()));
			if(!checkText(document.getElementById("search_name")))
				return;
			loadData(1);
		});
		$("#btnAdd").click(function(){
			parent.ShowIframe("添加-机构信息","<%=contextPath%>/web/organization/gotoAdd?companyId="+currentTreeCompanyId,400,250);
		});
		$("#btnDeleteAll").click(function(){
			var str="";
			$("#typeTable .CHECKBOX").each(function(){
				if($(this).attr("checked")==true){
					str+=$(this).val()+",";
				}
			});	
			if(str == ""){
				alert("请选择要删除的机构！");
				return false;
			}
			if(confirm("是否将所选机构删除?")){
				$.ajax({
						url			:"<%=contextPath%>/web/department/json/deleteAll.action?idStr="+str,
						type		:"POST",
						dataType	:"json",
						success		:function(data){
						var strs ;
						if(data[1] != null && data[0] == null){
							alert("以下机构下有子机构或用户,无法删除,请先删除子机构或用户：\n"+data[1].substring(0, data[1].length-1));
						}else if(data[1] != null && data[0] != null){
							alert("以下机构已经被删除：\n"+data[0].substring(0, data[0].length-1)+"\n\n以下机构下有子机构或用户,无法删除,请先删除子机构或用户：\n"+data[1].substring(0, data[1].length-1));
							$("#searchDepartmentList").submit();
						}else{
							$("#searchDepartmentList").submit();
							alert("机构删除成功！");
						}
						},
						error		:function(){
							alert("机构删除错误");
						}
					});
			}
		});
		$("#btnOutWord").click(function(){});
		$("#btnOutExcel").click(function(){});
	});
	
	function loadTree(){
    	$("#treeDerpartment").nfpTree({
	        ajaxUrl: "<%=contextPath%>/web/organization/json/queryTreeNodeData?companyId=" + $("#companyId").val(),
	        themeUrl:'<%=contextPath%>/common/nfpTreeThemes',
	        paramsName:"organizationParentId",
	 		expandLevel:2,
	 		clickStyle:false,
	        afterClick: function (node, treeObj) {
	        	var nodeObj=node.children("[isNodeText]")
				$("span.active",$('#treeDerpartment')).attr("class","text");
				nodeObj.attr("class","active");   	//切换选中节点颜色
				currentOrganizationId = node.attr("code"); //currentOrganizationId 为 组织id
				loadData(1);
	        }
	    });
    }
    function loadData(pageIndex) {
    	currentTreeCompanyId = $("#companyId").val();
		var gridAction="<%=contextPath%>/web/organization/queryForGrid";
		if(!pageIndex) {
			pageIndex="${pageBean.currentPage}";
		}
		var search_name = jQuery.trim($("#search_name").val());
		showGridLoading();
		$("#divGrid").load(gridAction,{
			"companyId":currentTreeCompanyId,
			"organization.id":currentOrganizationId,
			"organization.fullName":search_name,
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
	<div region="north" border="false" style="height:27px;background:#B3DFDA;" id='cidDiv'>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="nei_box" align="center">
			<%-- 导入页面标题栏 --%>
			<tr>
				<td>
					<%@ include file="../../common/pagehead.jsp"%>
				</td>
			</tr>
		</table>
	</div>
	<div region="west" id="west" split="false" title="组织机构" style="width:200px;padding:10px;">
		<ul id="treeDerpartment"></ul>
	</div>
	<div region="center" title="机构列表" style="width: 600px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="nei_box" align="center">
		  <tr>
		    <td height="54" >
			    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="nei_tit tit_background">
			       <tr>
						<td width="10%"  height="54"></td>
						<td width="30%" align="left">
						        公司名称：<select name="companyId" id="companyId">
						              <c:forEach items="${companyList}" var="company">
						                 <c:if test="${sessionScope.companyId == company.id}">
						                    <option value="${company.id}" selected="selected">${company.fullName}</option>
						                 </c:if>
						                 <c:if test="${sessionScope.companyId != company.id}">
						                   <option value="${company.id}">${company.fullName}</option>
						                 </c:if>
						              </c:forEach>
						           </select>
						</td>
						<td width="30%" align="left">
						         机构名称：<input type="text" id="search_name" class="likeDepartmentName" />
						</td>
						<td width="30%">
							<input type="button" class="searchButton" id="btnSearch" />
							  &nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="resetButton" id="btnReset" />
						</td>
				   </tr>
			    </table>
		    </td>
		  </tr>
		  <tr>
		     <td height="34" class="tit2_background">
			     <table width="100%" border="0" cellspacing="0" cellpadding="0">
			          <tr>
			            <td style="padding-left:10px;" >
			            	<div class="tool_td tool_add"><a href="#" id="btnAdd">添加机构</a></div>
					 		<div class="tool_td tool_word"><a href="#" id="btnOutWord">Word导出</a></div>
					 		<div class="tool_td tool_excel"><a href="#" id="btnOutExcel">Excel导出</a></div>
			            </td>
			          </tr>
			      </table>
		      </td>
		  </tr>
		  <tr>
		     <td>
		       <div class="s" id="divGrid"></div>
		     </td>
		  </tr>
		</table>
	</div>
</body>
</html>