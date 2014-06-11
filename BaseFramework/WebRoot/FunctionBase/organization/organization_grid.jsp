<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
</head>
<body>
<script language="javascript">
	function query(organizationId) {
		parent.ShowIframe("查看-机构信息",
				"<%=contextPath%>/web/organization/queryById?manuType=query&organization.id=" + organizationId + "&companyId="+currentTreeCompanyId,
				350,200);
	}
	function modify(organizationId) {
		parent.ShowIframe("编辑-机构信息",
				"<%=contextPath%>/web/organization/queryById?manuType=edit&organization.id=" + organizationId + "&companyId="+currentTreeCompanyId,
				400,250);
	}
	function delOrganization(organizationId){
			if(confirm("确定将此机构删除?\n请注意:机构删除后，该机构下的所有用户以及子机构都将被自动删除!")){
				$.ajax({
						url			:"<%=contextPath%>/web/organization/json/deleteById",
						type		:"POST",
						dataType	:"json",
						data		:{"organization.id":organizationId},
						success		:function(data){
							if(data == 1) {
								alert("机构删除成功！");
							    loadTree();
							    var currPage= ${pageBean.currentPage};
							    //删除完成后若当前页面为最后一条数据且当前页数大于1，则加载上一页
							    if($(".btnSearch").size()==1 && currPage > 1)
								currPage--;
							    loadData(currPage);
							} else {
								alert("机构删除错误");
							}
						},
						error		:function(){
							alert("机构删除错误");
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
<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tableNoWrap gridtable">
  <tr> 
  	<th   width="40" class="nei_txt_menu">序号</th>
  	<th   width="20%" class="nei_txt_menu">上级机构</th>
    <th   width="20%" class="nei_txt_menu">机构名称</th>
	<th   width="35%" class="nei_txt_menu">机构描述</th>
    <th   width="160" class="nei_txt_menu" style="border-right: none;">操 作</th>
  </tr>
  
  <c:forEach var="organization" items="${pageBean.list}" varStatus="v">
        
 		<tr id="trWithCB" <c:if test="${v.count%2==0}">class="trBg"</c:if> class="treeList">
		    <td style="display: none;">${organization.id }</td>
 			<td class="noWrapTd" align="center">${(pageBean.currentPage-1) * pageBean.pageSize + v.count}</td>
 			<td class="noWrapTd">
 				<c:if test="${organization.parent==null}">
					<c:out value="${organization.company.companyName}"/>
				</c:if>
				<c:if test="${organization.parent!=null}">
					<c:out value="${organization.parent.organizationName}"/>
				</c:if>
 			</td>
 			<td class="noWrapTd"><c:out value="${organization.organizationName }"/></td>
			<td class="noWrapTd"><c:out value="${organization.describes }"/></td>
 			<td align="center">
	 			<c:if test="${fn:contains(sessionScope.resourceIds,31)}">
			       <a href="javascript:void(0);" class="btnSearch" onclick="query(${organization.id});">查看</a>
			       &nbsp;&nbsp;|&nbsp;&nbsp;
	            </c:if>
	 			<c:if test="${fn:contains(sessionScope.resourceIds,33)}">
	 			   <a href="javascript:void(0);" class="btnEdit" onclick="modify(${organization.id});">编辑</a>
	 			   &nbsp;&nbsp;|&nbsp;&nbsp;
	 			</c:if>
	 			<c:if test="${fn:contains(sessionScope.resourceIds,34)}">
	 			   <a href="javascript:void(0);" class="btnDelete" onclick="delOrganization(${organization.id});">删除</a>
	 			</c:if>
 			</td>
	</c:forEach>
</table>
</div>
</body>
</html>