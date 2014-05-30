<%@ page language="java" import="java.util.*;" pageEncoding="utf-8"%>
<%
	session = request.getSession();
	Enumeration e = session.getAttributeNames();
	while (e.hasMoreElements()) {
		String sessionName = (String) e.nextElement();
		if (sessionName.equals("userId") || sessionName.equals("userName") || sessionName.equals("roleId") 
		   || sessionName.equals("roleName") || sessionName.equals("companyId") || sessionName.equals("companyCode") 
		   || sessionName.equals("companyName")||sessionName.equals("resourceIds") ) {
			session.removeAttribute(sessionName);
		}
	}
	session.invalidate();
%>