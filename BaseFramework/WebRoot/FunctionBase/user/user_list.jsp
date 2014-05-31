<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
	<title>用户列表</title>
	<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
	<link rel="stylesheet" href="<%=contextPath%>/common/nfpTreeThemes/nfpTreeCss.css" type="text/css"></link>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/common/js/ajaxPager.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/nfpTree.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/convertChinese.js"></script>

<style type="text/css"> 
	body{ margin:0px; padding:0px;height:100%;} 
	#cover{
	   position:absolute;
	   top:0px;
	   left:0px;
	   width:0%;
	   height:0%;
	   background-color:#000; 
	   display:none; 
	} 
	#button{ margin:auto; width:300px; margin-top:200px;} 
</style> 

<script language="javascript">
    var currentCompanyId = -1;
	$(function(){
		initGridLoading("<%=contextPath%>/common/blue/Images/gridloading.gif");
		
		loadData(1);
		
		$("#btnReset").click(function(){
			$("#search_userName").val("")
			$("#pleaseChooseState").attr("selected","selected");
			loadData(1);
		});
		
		$("#btnSearch").click(function(){
			loadData(1);
		});
		$("#btnOutWord").click(function(){});
		$("#btnOutExcel").click(function(){});
		
		$("#btnAddUser").click(function(){
			parent.ShowIframe("添加-用户信息","<%=contextPath%>/web/user/goToAddUser?company.id=" + currentCompanyId,400,400);
		});
	});
	
	function loadData(pageIndex){
			var gridAction="<%=contextPath%>/web/user/query";
			
			if(!checkText(document.getElementById("search_userName"))) {
				return;
			}
			if(!pageIndex) {
				pageIndex = 1;
			}
			
			//读取数据
			showGridLoading();
			var search_companyId = jQuery.trim($("#search_companyId").val());
			var search_userName = jQuery.trim($("#search_userName").val());
			var search_userState = jQuery.trim($("#search_userState").val());
			
			currentCompanyId = search_companyId;
			
			$("#divGrid").load(gridAction,{
				"user.company.id":search_companyId,
				"user.userName":search_userName,
				"user.currentState":search_userState,
				"pageBean.currentPage":pageIndex
			    },
				function(){
					hideGridLoading();
				}
			);
	}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="nei_box" align="center">
	<%-- 导入页面标题栏 --%>
	 <tr>
		<td>
			<%@ include file="../../common/pagehead.jsp"%>
		 </td>
	 </tr>
	 
	 <tr>
	    <td height="54" >
		    <table id="searchTable" width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="nei_tit tit_background">
		      <tr>
		      	<td width="3%" height="42">&nbsp;</td>
		      	<td width="9%" class="nei_searchTd">公司名称：</td>
				<td width="15%"> 
				    <select name="search_companyId" id="search_companyId"  style="width:150px;">
						   <c:forEach items="${companyList}" var="company">
						          <c:if test="${sessionScope.companyId == company.id}">
						              <option value="${company.id}" selected="selected" id="selectOption${company.id}">${company.fullName}</option>
						          </c:if>
						          <c:if test="${sessionScope.companyId != company.id}">
						              <option value="${company.id}" id="selectOption${company.id}">${company.fullName}</option>
						          </c:if>
						   </c:forEach>
					</select>
			    </td>
	            <td width="9%" class="nei_searchTd">用户名称：</td>
	            <td width="15%">
	                 <input id="search_userName" type="text" class="SEARCH_INPUT"  style="width:150px;"/>
	            </td>
	            <td width="42%" style='padding-left: 20px;'>
	                <input name="button" type="button" class="searchButton" id="btnSearch" />
	            </td>
	          </tr>
	          <tr>
	          	<td width="3%" height="42">&nbsp;</td>
	            <td width="9%" class="nei_searchTd">用户状态：</td>
	            <td width="15%">
					<select id="search_userState" style="width:150px;">
						<option value="2" selected="selected" id="pleaseChooseState">请选择状态</option>
						<option value="0">未审核</option>
						<option value="1">通过审核</option>
					</select>				
			    </td>
	            <td style="padding-left: 45px;">
	                <input name="button2" type="button" class="resetButton" id="btnReset" />
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
					    <c:if test="${fn:contains(sessionScope.resourceIds,32)}">
						    <div class="tool_td tool_add">
								<a href="#" id="btnAddUser" >添加用户</a>
							</div>
                        </c:if>
						<c:if test="${fn:contains(sessionScope.resourceIds,37)}">
							<div class="tool_td tool_sh">
								<a href="#" onClick="javascript:checkStatusAll();" >批量审核</a>
							</div>
						</c:if>
						<c:if test="${fn:contains(sessionScope.resourceIds,38)}">
							<div class="tool_td tool_del">
								<a href="#" onClick="javascript:delAll();" >批量删除</a>
							</div>
						</c:if>
						<!-- 
						<div class="tool_td tool_word">
							<a href="#" id="btnOutWord">Word导出</a>
						</div>
		 				<div class="tool_td tool_excel">
		 					<a href="#" id="btnOutExcel">Excel导出</a>
		 				</div>
		 				 -->
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
</body>
</html>