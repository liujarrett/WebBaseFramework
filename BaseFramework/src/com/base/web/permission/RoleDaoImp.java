package com.base.web.permission;

import com.base.core.ssh.l3dao.BaseDaoImp;

public class RoleDaoImp extends BaseDaoImp<Role,Long> implements RoleDao
{

	public RoleDaoImp()
	{
		super(Role.class);
	}

}
