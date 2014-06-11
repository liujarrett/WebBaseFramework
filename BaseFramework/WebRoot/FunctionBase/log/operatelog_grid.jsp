<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
   String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript"  type="text/javascript">
$(function(){
	//全选
	$("#checkAll").click(function(){
		$("tr[tag='source'] :checkbox").attr("checked",$(this).attr("checked"));
	});
	//单选
	$("tr[tag='source'] :checkbox").click(function(){
		if($(this).attr("checked") && $("tr[tag='source'] :checkbox:not(:checked)").size()==0)
			$("#chkAll").attr("checked",true);
		else if($("#chkAll").attr("checked"))
			$("#chkAll").removeAttr("checked");
	});
});
</script>
</head>
<body>
	<div class="tit3_background" style="height: 30px;">
		<%-- 分页 --%>
		<%@ include file="page_operateLog.jsp"%>
	</div>
	<div>
	<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tableNoWrap gridtable" >
	  <tr>
	    <th width="50" class="nei_txt_menu">编号</th>
	  	<th width="150" class="nei_txt_menu">操作用户</th>
	    <th width="150" class="nei_txt_menu">用户IP地址</th>
	    <th width="150" class="nei_txt_menu">客户端类型</th>
	    <th width="150" class="nei_txt_menu">操作类型</th>
	    <th width="150" class="nei_txt_menu">操作时间</th>
	    <th width="200" class="nei_txt_menu">操作描述</th>
	  </tr>
	  <c:forEach var="log" items="${operateLogPageBean.list}" varStatus="v">
	  
	 		<tr tag="source" id="trWithCB" <c:if test="${v.count%2==0}">class="trBg"</c:if> >
	 			<td class="noWrapTd">
	 			     <c:out value="${v.index + 1}"/>
	 			</td>
	 			
	 			<td class="noWrapTd" align="center">
	 			   <c:out value="${log.user.userName}"/>
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    <c:out value="${log.clientIp }"/>
	 			</td>
	 			
	 			<td class="noWrapTd">
		 			<c:if test="${log.clientType == 1 }">手机客户端</c:if>
		 			<c:if test="${log.clientType == 2 }">web浏览器</c:if>
			    </td>
			    
	 			<td class="noWrapTd">
	 			    <c:if test="${log.operateType == 1 }">增加数据</c:if>
		 			<c:if test="${log.operateType == 2 }">删除数据</c:if>
	 			    <c:if test="${log.operateType == 3 }">更新数据</c:if>
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    <c:out value='${log.operateTime }'/>
				</td>
				
	 			<td class="noWrapTd">
	 			    <c:out value='${log.operateContents }'/>&nbsp;
	 			</td>
	 		</tr>
		</c:forEach>
	</table>
	</div>
</body>
</html>
