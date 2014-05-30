package com.base.web.permission;

import com.base.core.ssh.l3dao.BaseDaoImp;

public class FunctionDaoImp extends BaseDaoImp<Function,Integer> implements FunctionDao
{

	public FunctionDaoImp()
	{
		super(Function.class);
	}

}
