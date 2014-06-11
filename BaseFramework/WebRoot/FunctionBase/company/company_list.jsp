<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>公司信息列表</title>
	<link rel="stylesheet"  href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
	<script type="text/javascript" src="<%=contextPath%>/common/js/nfp.core.util.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/nfpTree.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/ajaxPager.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/convertChinese.js"></script>
	
<script type="text/javascript">
	$(function(){
		initGridLoading("<%=contextPath%>/common/blue/Images/gridloading.gif");
		loadData(1);
		
		$("#btnSearch").click(function(){
			loadData(1);
		});
		
		$("#btnReset").click(function(){
			//
			$("#search_company_name").val("");
			$("#search_company_address").val("");
			$("#search_company_phone").val("");
			// outExcel
			$("#outExcelCompanyName").val("");
			$("#outExcelCompanyAddress").val("");
			$("#outExcelCompanyPhone").val("");
			
			loadData(1);
		});
		
		
		$("#btnOutExcel").click(function(){

			$("#submitExcel").submit();
			
		});
		
	});
	
	//删除公司信息
 	function deleteCompany(companyID,page){
		if(companyID) 
		{
			if(confirm("警告:公司删除之后，该公司相关数据都将被删除，确定删除?")){
				$.ajax({
						url	:"<%=contextPath%>/web/company/json/deleteCompany",
						type:"POST",
						dataType:"json",
						data	:{"company.id":companyID},
						success	:function(data){
							if(data == 1) {
								alert("删除成功");
								loadData(page);
							} else {
								alert("删除失败");
							}
						},
						error	:function(){
							alert("删除失败");
						}
				});
			}
		}else{
			alert("请选择您要删除的公司!");
		}
    }
	
	function loadData(pageIndex){
			var gridAction="<%=contextPath%>/web/company/query";
			//获取当前页数
			if(!pageIndex)
				pageIndex="${pageBean.currentPage + 1}";
			//展示覆盖
			showGridLoading();
			//查询条件
			var search_name = jQuery.trim($("#search_company_name").val());
			var search_address = jQuery.trim($("#search_company_address").val());
			var search_phone = jQuery.trim($("#search_company_phone").val());
			
			// outExcel
			$("#outExcelCompanyName").val(search_name);
			$("#outExcelCompanyAddress").val(search_address);
			$("#outExcelCompanyPhone").val(search_phone);
			
			
			//执行查询
			$("#divGrid").load(gridAction,{
				"pageBean.currentPage":pageIndex,
				"company.companyName": search_name ? search_name : "",
				"company.address": search_address ? search_address : "",
				"company.telephone": search_phone ? search_phone : ""
	            },
				function(){
					hideGridLoading();//隐藏覆盖层
				}
			);
	}
</script>
<style>
table {
	font-size: 12px;
}
</style>
</head>
<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="nei_box" align="center">
		<tr>
			<td>
				<%@ include file="../../common/pagehead.jsp"%>
			</td>
		</tr>
		<tr>
			<td height="54">
				<!-- SeachPanel Begin -->
				<form name="formCompany" action="<%=contextPath%>/web/company/company!queryForList.action" method="post">
					<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="nei_tit tit_background">
						<tr>
							<td width="3%" height="54">&nbsp;</td>
							<td width="8%" class="nei_searchTd">公司名称</td>
							<td width="15%">
							    <input id="search_company_name" name="search_company_name" maxlength="50" style='width:150px;' />
							</td>
							<td width="8%" class="nei_searchTd">公司地址</td>
							<td width="15%">
							    <input id="search_company_address" name="search_company_address" maxlength="50" style='width:150px;' "/>
							</td>
							<td width="8%" class="nei_searchTd">联系电话</td>
							<td width="15%">
							    <input id="search_company_phone" name="search_company_phone" maxlength="50" style='width:150px;' />
							</td>
							<td style='padding-left: 20px;'>
			                     <input type="button" id="btnSearch" class="searchButton"/>
								 &nbsp;&nbsp;&nbsp;&nbsp; 
								 <input type="button" id="btnReset" class="resetButton" />
							</td>
						</tr>
					</table>
			    </form> 
			    <!-- SeachPanel End -->
		   </td>
		</tr>
		<!-- ButtonTools Begin -->
		<tr>
			<td>
				<table width="100%" height="34" border="0" cellspacing="0" cellpadding="0" class="tit2_background" align="center">
					<tr>
						<td style="padding-left:10px;">
						    <c:if test="${fn:contains(sessionScope.resourceIds,22)}">
			                   <div class="tool_td tool_add">
								   <a href="#" onclick="parent.ShowIframe('添加-公司信息','<%=contextPath%>/web/company/gotoAddCompany',480,320);">添加公司</a>
							   </div>
	                        </c:if>
                            <c:if test="${fn:contains(sessionScope.resourceIds,27)}">
								<div class="tool_td tool_sh">
									<a href="#" onClick="javascript:passAll();">审核</a>
								</div>
						    </c:if>
						    <c:if test="${fn:contains(sessionScope.resourceIds,28)}">
								<div class="tool_td tool_del">
									<a href="#" onClick="javascript:rejectAll();">驳回</a>
								</div>
							</c:if>
							<c:if test="${fn:contains(sessionScope.resourceIds,26)}">
				 				<div class="tool_td tool_excel">
				 					<a href="#" id="btnOutExcel">Excel导出</a>
								</div>
                        	</c:if>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<!-- SeachPanel End -->
		<tr>
			<td align="center">
				<div id="divGrid"></div>
				<form action="<%=contextPath%>/web/company/outExcel" method="post" id="submitExcel">
		 		<input type="hidden" id="outExcelCompanyName" name="outExcelCompanyName" />
			 	<input type="hidden" id="outExcelCompanyAddress" name="outExcelCompanyAddress" />
			 	<input type="hidden" id="outExcelCompanyPhone" name="outExcelCompanyPhone" />
		 	 </form>
			</td>
		</tr>
	</table>
</body>
</html>
