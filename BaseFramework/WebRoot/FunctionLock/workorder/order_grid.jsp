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
	//全选按钮
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
	  	<th width="150" class="nei_txt_menu">客户来电</th>
	    <th width="150" class="nei_txt_menu">受理时间</th>
	    <th width="100" class="nei_txt_menu">开锁公司</th>
	    <th width="60" class="nei_txt_menu">是否回拨</th>
	    <th width="150" class="nei_txt_menu">备注</th>
	    <th width="80" class="nei_txt_menu">满意度</th>
	    <th width="100" class="nei_txt_menu">投诉</th>
	    <th width="60" class="nei_txt_menu">拨打录音</th>
	    <th width="60" class="nei_txt_menu">回访录音</th>
	  </tr>
	  <c:forEach var="order" items="${pageBean.list}" varStatus="v">
	 		<tr tag="source" id="trWithCB" <c:if test="${v.count%2==0}"> class="trBg" </c:if> >

	 			<td class="noWrapTd">
	 			     <c:out value='${order.id }'/>&nbsp;
	 			</td>
	 			
	 			<td class="noWrapTd" align="center">
	 			     <c:out value='${order.callerid }'/>&nbsp;
	 			</td>
	 			<td class="noWrapTd" align="center">
	 			     <c:out value='${order.accepttime }'/>&nbsp;
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    <c:out value='${order.openlockname }'/>&nbsp;
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    <c:choose>
	 			        <c:when test="${order.callback == 0}">
	 			           <span style='color:red'>未回拨</span>
	 			        </c:when>
					    <c:otherwise> <span>已回拨</span></c:otherwise>
				    </c:choose>
		        </td>
		        
	 			<td class="noWrapTd">
	 			    <c:out value='${order.remark }'/>&nbsp;
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    满意度
			    </td>
			    
	 			<td class="noWrapTd">
	 			     投诉
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			     拨打录音
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    回放录音
	 			</td>
	 		</tr>
		</c:forEach>
	</table>
	</div>
</body>
</html>
