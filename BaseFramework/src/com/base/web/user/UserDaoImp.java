package com.base.web.user;

import com.base.core.ssh.l3dao.BaseDaoImp;

public class UserDaoImp extends BaseDaoImp<User,Long> implements UserDao
{

	public UserDaoImp()
	{
		super(User.class);
	}

}
