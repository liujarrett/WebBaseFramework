<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
%>

<div id="divMainTop"
	 style="height:26px;background-image:url(<%=path%>/common/Images/mainTop.jpg);font-size:12px; vertical-align: middle; padding-left:5px; overflow:hidden;">
	
	<img src="<%=path%>/common/Images/arrows.gif"
		 style=" vertical-align: middle;float:left;" />
    <%--
	<div title="主页面 "
		 style="width:150px;  height:21px; overflow:hidden; float:left; padding-top:6px;">
		 主页面
    </div>
    --%>
	<img src="<%=path%>/common/Images/person.jpg"
		 style=" vertical-align: middle;float:left;" />
	<div id="divLoginMsg" title=""
		 style="margin-right: 40px; width:230px; height:21px; overflow:hidden; float:left; padding-top:6px;">
		 <div style="margin-left: 5px;">
		      ${sessionScope.userName}
		 </div>
	</div>
	<div id="divRoles" title=""
		style="width:330px;height:21px; overflow:hidden; float:left; padding-top:6px;">
		角色:${sessionScope.roleName}
    </div>
</div>