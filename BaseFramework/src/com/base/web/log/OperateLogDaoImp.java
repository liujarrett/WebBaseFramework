package com.base.web.log;

import com.base.core.ssh.l3dao.BaseDaoImp;

public class OperateLogDaoImp extends BaseDaoImp<OperateLog,Long> implements OperateLogDao
{

	public OperateLogDaoImp()
	{
		super(OperateLog.class);
	}

}
