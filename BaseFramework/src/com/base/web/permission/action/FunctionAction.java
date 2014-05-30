package com.base.web.permission.action;

import com.base.core.ssh.l1action.BaseAction;
import com.base.web.permission.Function;
import com.base.web.permission.service.FunctionService;

public class FunctionAction extends BaseAction<Function>
{
	private static final long serialVersionUID=5217656268275826329L;

	private FunctionService functionService;

	public FunctionService getFunctionService()
	{
		return functionService;
	}

	public void setFunctionService(FunctionService functionService)
	{
		this.functionService=functionService;
	}

}
