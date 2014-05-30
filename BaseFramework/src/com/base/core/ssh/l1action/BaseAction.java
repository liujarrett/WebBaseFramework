/**
 * 文件名：BaseController.java
 *
 * 版本信息：
 * 日期：2011-3-9
 * Copyright 黑龙江中科方德软件有限公司 2011
 *
 */
package com.base.core.ssh.l1action;

import com.base.core.ssh.l0model.BaseEntity;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 控制器抽象类,提供基本的公共属性以及方法定义
 */
public abstract class BaseAction<T extends BaseEntity> extends ActionSupport
{

	private static final long serialVersionUID=3506353386369287512L;

	public static final String GOTO="goto";
	public static final String LOGOUT="logout";

	public String goTo()
	{
		return GOTO;
	}
}
