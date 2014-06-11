package com.base.core.ssh.l1action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 控制器抽象类,提供基本的公共属性以及方法定义
 */
public abstract class BaseAction extends ActionSupport
{

	private static final long serialVersionUID=3506353386369287512L;

	public static final String GOTO="goto";
	public static final String LOGOUT="logout";

	public String goTo()
	{
		return GOTO;
	}
}
