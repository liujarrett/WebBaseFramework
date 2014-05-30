package com.base.web.permission.service;

import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.web.permission.Function;
import com.base.web.permission.FunctionDao;

public class FunctionServiceImp extends BaseServiceImp<Function,Integer> implements FunctionService
{
	private static final long serialVersionUID=-652269982562840710L;

	private FunctionDao functionDao;

	public FunctionDao getFunctionDao()
	{
		return functionDao;
	}

	public void setFunctionDao(FunctionDao functionDao)
	{
		super.setBaseDao(functionDao);
		this.functionDao=functionDao;
	}

}
