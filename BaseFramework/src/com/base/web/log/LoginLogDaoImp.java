package com.base.web.log;

import com.base.core.ssh.l3dao.BaseDaoImp;

public class LoginLogDaoImp extends BaseDaoImp<LoginLog,Long> implements LoginLogDao
{

	public LoginLogDaoImp()
	{
		super(LoginLog.class);
	}

}
