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
		<%@ include file="page_loginLog.jsp"%>
	</div>
	<div>
	<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tableNoWrap gridtable" >
	  <tr>
	    <th width="50" class="nei_txt_menu">编号</th>
	  	<th width="150" class="nei_txt_menu">登录用户</th>
	  	<th width="150" class="nei_txt_menu">登录IP地址</th>
	    <th width="150" class="nei_txt_menu">登录类型</th>
	    <th width="150" class="nei_txt_menu">登录时间</th>
	    <th width="150" class="nei_txt_menu">退出时间</th>
	    <th width="100" class="nei_txt_menu">登录手机号码</th>
	  </tr>
	  <c:forEach var="log" items="${loginLogPageBean.list}" varStatus="v">
	  
	 		<tr tag="source" id="trWithCB" <c:if test="${v.count%2==0}">class="trBg"</c:if> >
	 			<td class="noWrapTd">
	 			     <c:out value="${v.index + 1}"/>
	 			</td>
	 			
	 			<td class="noWrapTd" align="center">
	 			   <c:out value="${log.user.userName}"/>
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    <c:out value="${log.loginIP }"/>
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    <c:if test="${log.loginType == 1 }">手机客户端登录</c:if>
		 			<c:if test="${log.loginType == 2 }">web浏览器登录</c:if>
			    </td>
			    
	 			<td class="noWrapTd">
	 			    <c:out value='${log.loginTime }'/>
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    <c:out value='${log.logoutTime }'/>
				</td>
				
	 			<td class="noWrapTd">
	 			    <c:out value='${log.userMobilePhone }'/>&nbsp;
	 			</td>
	 			
	 		</tr>
		</c:forEach>
	</table>
	</div>
</body>
</html>
