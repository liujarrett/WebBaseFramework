<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String contextPath = request.getContextPath();
%>
<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet" href="<%=contextPath%>/common/main.css" type="text/css"></link>
<script language="javascript">
	$(function(){
		
		$(".btnSearch").click(function(){
			var sref=$(this).parent().parent().children("td");
			$.ajax({
						url			:"<%=contextPath%>/web/department/json/queryObject.action",
						type		:"POST",
						dataType	:"json",
						data		:{"departmentId":sref.eq(0).html()},
						success		:function(data){
							if(data == null){
								alert("该机构已被删除，请刷新后再试！");
							}else{
								parent.ShowIframe("查看-机构信息",'<%=contextPath%>/web/department/queryForObject.action?departmentId='+sref.eq(0).html(),350,200)
							}
						},
						error		:function(){
							alert("机构查看错误");
						}
					});
		});
		$(".btnEdit").click(function(){
			var sref=$(this).parent().parent().children("td");
			$.ajax({
						url			:"<%=contextPath%>/web/department/json/queryObject.action",
						type		:"POST",
						dataType	:"json",
						data		:{"departmentId":sref.eq(0).html()},
						success		:function(data){
							if(data == null){
								alert("该机构已被删除，请刷新后再试！");
							}else{
								parent.ShowIframe("编辑-机构信息",'<%=contextPath%>/web/department/queryForModify.action?departmentId='+sref.eq(0).html(),400,250);
							}
						},
						error		:function(){
							alert("机构修改错误");
						}
					});
		});
		$(".btnDelete").click(function(){
			var sref=$(this).parent().parent().children("td");
			if(confirm("是否将此机构删除?")){
				$.ajax({
						url			:"<%=contextPath%>/web/department/json/delete.action",
						type		:"POST",
						dataType	:"json",
						data		:{"department.departmentId":sref.eq(0).html(),currMenuId:$("#hideMenuId").val()},
						success		:function(data){
							if(data == null){
								alert("机构下有子机构或用户,无法删除,请先删除子机构或用户！");
							}else{
								alert("机构删除成功！");
								loadTree1();
								var currPage=parseInt('${pageBean.currentPage}');
								//删除完成后若当前页面为最后一条数据且当前页数大于1，则加载上一页
								if($(".btnSearch").size()==1 && currPage > 1)
									currPage--;
								loadData(currPage);
							}
						},
						error		:function(){
							alert("机构删除错误");
						}
					});
			}
		});
	})
</script>
</head>
<body>
		
<div class="tit3_background" style="height: 30px;">
  		<%-- 分页开始 --%>
	<%@ include file="../../common/pageajax.jsp"%>
    <%-- 分页结束 --%>
</div>
<div>
<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tableNoWrap gridtable">
  <tr> 
  	<th   width="40" class="nei_txt_menu">序号</th>
    <th   width="20%" class="nei_txt_menu">机构名称</th>
	<th   width="20%" class="nei_txt_menu">上级机构</th>
	<th   width="35%" class="nei_txt_menu">机构描述</th>
    <th   width="160" class="nei_txt_menu" style="border-right: none;">操 作</th>
  </tr>
  
  	<c:forEach var="department" items="${pageBean.list}" varStatus="v">
        
 		<tr id="trWithCB" <c:if test="${v.count%2==0}">class="trBg"</c:if> class="treeList">
		    <td style="display: none;">${department.departmentId }</td>
 			<td class="noWrapTd" align="center">${(pageBean.currentPage-1) * pageBean.pageSize + v.count}</td>
 			<td class="noWrapTd"><c:out value='${department.departmentName }'/></td>
 			<td class="noWrapTd"><c:choose>
	 									<c:when test="${department.parentName ==''}"><c:out value="${parentName}"/></c:when>
	 									<c:otherwise><c:out value="${department.parentName }"/></c:otherwise>
 									</c:choose></td>
			<td class="noWrapTd"><c:out value='${department.remarks }'/></td>
 			<td align="center">
 			<a href="#" class="btnSearch">查看</a>
 			&nbsp;&nbsp;|&nbsp;&nbsp;
 			<a href="#" class="btnEdit">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;
 			<a href="#" class="btnDelete">删除</a>
 			</td>
 		
	</c:forEach>
</table>
</div>

</body>
</html>