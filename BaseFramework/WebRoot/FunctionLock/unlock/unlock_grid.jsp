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
function showPic(url) {
	window.open(url);
}
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
	    <th width="30" class="nei_txt_menu">
		    <input type="checkbox" value="checkbox" id="checkAll" />
		</th>
	  	<th width="70" class="nei_txt_menu">开锁人</th>
	    <th width="70" class="nei_txt_menu">客户姓名</th>
	    <th width="175" class="nei_txt_menu">客户身份证号码</th>
	    <th width="150" class="nei_txt_menu">开锁时间</th>
	    <th width="100" class="nei_txt_menu">开锁类型</th>
	    <th width="100" class="nei_txt_menu">开锁公司</th>
	    <th width="100" class="nei_txt_menu">服务单</th>
		<th width="100" class="nei_txt_menu">客户身份证</th>
	    <th width="100" class="nei_txt_menu">驾驶证</th>
		<th width="100" class="nei_txt_menu">行驶证</th>
		<th width="100" class="nei_txt_menu">执照</th>
		<th width="100" class="nei_txt_menu">介绍信</th>
	  </tr>
	  <c:forEach var="unlock" items="${pageBean.list}" varStatus="v">
	 		<tr tag="source" id="trWithCB" <c:if test="${v.count%2==0}">class="trBg"</c:if> >
	 		    <td class="noWrapTd" align="center">
					<input type="checkbox" value="${user.id }" class="CHECKBOX" />
				</td>
	 			
	 			<td class="noWrapTd" align="center">
	 			   <c:out value="${unlock.user.userName}"/>
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    <c:out value="${unlock.customerName }"/>
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			    <c:out value="${unlock.customerIdNo }"/>
			    </td>
			    
	 			<td class="noWrapTd">
	 			    <c:out value='${unlock.unlockTime }'/>
	 			</td>
	 			
	 			<td class="noWrapTd">
	 			   <c:if test="${unlock.unlockType == 1 }">
	 			              开民用锁
	 			   </c:if>
	 			   <c:if test="${unlock.unlockType == 2 }">
	 			              开汽车锁
	 			   </c:if>
	 			   <c:if test="${unlock.unlockType == 3 }">
	 			              开保险箱
	 			   </c:if>
	 			   <c:if test="${unlock.unlockType == 4 }">
	 			              开ATM取款机
	 			   </c:if>
	 			   <c:if test="${unlock.unlockType == 5 }">
	 			              开金库
	 			   </c:if>
				</td>
				
				<td class="noWrapTd">
	 			    ${unlock.user.company.fullName}
	 			</td>
				
	 			<td class="noWrapTd">
	 			   <c:if test="${!empty unlock.unlockWorkOrderImg}">
	 			       <img src="${unlock.unlockWorkOrderImg}" width="80" height="50" 
	 			            onclick="showPic('${unlock.unlockWorkOrderImg}');"/>
	 			   </c:if>
	 			</td>
	 			
		 		<td class="noWrapTd">
		 		   <c:if test="${!empty unlock.customerIdImg}">
		 		   	 	<img src="${unlock.customerIdImg}" width="80" height="50" 
	 			            onclick="showPic('${unlock.customerIdImg}');"/>
	 			   </c:if>
		 		</td>
		 			
		 		<td class="noWrapTd">
		 		   <c:if test="${!empty unlock.customerDrivingLicenseImg}">
		 		   		<img src="${unlock.customerDrivingLicenseImg}" width="80" height="50" 
	 			            onclick="showPic('${unlock.customerDrivingLicenseImg}');"/>
	 			   </c:if>
		 		</td>
		 			
		 		<td class="noWrapTd">
		 		   <c:if test="${!empty unlock.customerVehicleLicenseImg}">
		 		   		<img src="${unlock.customerVehicleLicenseImg}" width="80" height="50" 
	 			            onclick="showPic('${unlock.customerVehicleLicenseImg}');"/>
	 			   </c:if>
		 		</td>
		 			
		 		<td class="noWrapTd">
		 		   <c:if test="${!empty unlock.customerBusinessLicenseImg}">
		 		   		<img src="${unlock.customerBusinessLicenseImg}" width="80" height="50" 
	 			            onclick="showPic('${unlock.customerBusinessLicenseImg}');"/>
	 			   </c:if>
		 		</td>
		 			
		 		<td class="noWrapTd">
		 		   <c:if test="${!empty unlock.customerIntroductionLetterImg}">
						<img src="${unlock.customerIntroductionLetterImg}" width="80" height="50" 
	 			            onclick="showPic('${unlock.customerIntroductionLetterImg}');"/>
	 			   </c:if>
		 		</td>
	 		</tr>
		</c:forEach>
	</table>
	</div>
</body>
</html>
