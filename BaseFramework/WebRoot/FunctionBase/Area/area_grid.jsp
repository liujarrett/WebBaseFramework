<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String contextPath = request.getContextPath();
%>
<html>
<head>
<title>区域列表</title>
<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css"
	type="text/css"></link>
</head>
<body>
	<script language="javascript">
	function query(areaId) {
		parent.ShowIframe("查看-区域信息",
				"<%=contextPath%>/web/area/queryById?manuType=query&area.id=" + areaId,
				350,200);
	}
	
	function modify(areaId) {
		parent.ShowIframe("编辑-区域信息",
				"<%=contextPath%>/web/area/queryById?manuType=edit&area.id=" + areaId,
				400,250);
	}
	
	function delarea(areaId){
			if(confirm("删除区域不会删除区域内的公司，\n但会删除下级区域。\n\n确定删除该区域？")){
				$.ajax({
						url			:"<%=contextPath%>/web/area/json/deleteArea",
						type		:"POST",
						dataType	:"json",
						data		:{"area.id":areaId},
						success		:function(data){
							if(data == 1) 
							{
								alert("删除成功！");
							    loadTree();
							    loadData(1);
							} 
							else
							{
								alert("删除失败！");
							}
						},
						error		:function(){
							alert("删除错误");
						}
					});
			}
		}
</script>
	<div class="tit3_background" style="height: 30px;">
		<%-- 分页开始 --%>
		<%@ include file="../../common/pageajax.jsp"%>
		<%-- 分页结束 --%>
	</div>
	<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="1"
			class="tableNoWrap gridtable">
			<tr>
				<th width="40" class="nei_txt_menu">序号</th>
				<th width="20%" class="nei_txt_menu">上级区域</th>
				<th width="20%" class="nei_txt_menu">区域名称</th>
				<th width="20%" class="nei_txt_menu">区域编号</th>
				<th width="160" class="nei_txt_menu" style="border-right: none;">操作</th>
			</tr>

			<c:forEach var="area" items="${pageBean.list}" varStatus="v">
				<tr id="trWithCB" <c:if test="${v.count%2==0}">class="trBg"</c:if>
					class="treeList">
					<td class="noWrapTd">${v.index + 1 }</td>
					<td class="noWrapTd"><c:out value="${area.parent.areaName }" /></td>
					<td class="noWrapTd"><c:out value="${area.areaName }" /></td>
					<td class="noWrapTd"><c:out value="${area.areaCode }" /></td>
					<td align="center">
						<c:if test="${fn:contains(sessionScope.resourceIds,11)}">
							<c:if test="${company.isDelete!=1 }">
								&nbsp;&nbsp;
				      			<a href="javascript:void(0);" class="btnSearch" onclick="query(${area.id});">查看</a>
				   				&nbsp;&nbsp;|
				   			</c:if>
						</c:if>
						<c:if test="${fn:contains(sessionScope.resourceIds,13)}">
				   			<c:if test="${company.isDelete!=1 }">
								&nbsp;&nbsp;
				      			<a href="javascript:void(0);" class="btnEdit" onclick="modify(${area.id});">编辑</a>
				   				&nbsp;&nbsp;|
				   			</c:if>
						</c:if>
						<c:if test="${fn:contains(sessionScope.resourceIds,14)}">
				   			<c:if test="${company.isDelete!=1 }">
								&nbsp;&nbsp;
				      			<a href="javascript:void(0);" class="btnDelete" onclick="delarea(${area.id});">删除</a>
				   				&nbsp;&nbsp;|
				   			</c:if>
						</c:if>
					</td>
			</c:forEach>
		</table>
	</div>
</body>
</html>