package com.base.core.ssh.l4interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2804255683807656413L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		if(session.getAttribute("userId") == null ||
		   session.getAttribute("userName") == null ||
		   session.getAttribute("roleId") == null ||
		   session.getAttribute("roleName") == null ||
		   session.getAttribute("companyId") == null ||
		   session.getAttribute("companyCode") == null ||
		   session.getAttribute("companyName") == null ||
		   session.getAttribute("resourceIds") == null ) {
			return "logout";
		}
		return invocation.invoke();
	}
}
