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
		<%@ include file="../../common/pageajax.jsp"%>
	</div>
	<div>
	<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tableNoWrap gridtable" >
	  <tr>
	    <th width="150" class="nei_txt_menu">ID</th>
	  	<th width="150" class="nei_txt_menu">主叫号码</th>
	    <th width="150" class="nei_txt_menu">被叫号码</th>
	    <th width="150" class="nei_txt_menu">提机时间</th>
	    <th width="150" class="nei_txt_menu">挂机时间</th>
	    <th width="100" class="nei_txt_menu">开锁公司</th>
	    <th width="80" class="nei_txt_menu">通话时长(秒)</th>
	    <th width="100" class="nei_txt_menu">受理工号</th>
	  </tr>
	  <c:forEach var="bill" items="${pageBean.list}" varStatus="v">
	  
	 		<tr tag="source" id="trWithCB" <c:if test="${v.count%2==0}">class="trBg"</c:if> >
	 			<td class="noWrapTd">
	 			     <c:out value="${bill.id}"/>
	 			</td>
	 			
	 			<td class="noWrapTd" align="center">
	 			   <c:out value="${bill.callerid}"/>
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    <c:out value="${bill.calleeid }"/>
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    <c:out value="${bill.hangofftm }"/>
			    </td>
			    
	 			<td class="noWrapTd">
	 			    <c:out value='${bill.hangontm }'/>
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    <c:out value='${bill.openlockname }'/>
				</td>
				
	 			<td class="noWrapTd">
	 			    <c:out value='${bill.talkingsecond }'/>&nbsp;
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    <c:out value='${bill.staffid}'/>&nbsp;
	 		    </td>
	 		</tr>
		</c:forEach>
	</table>
	</div>
</body>
</html>
