package com.base.web.permission;

import com.base.core.ssh.l3dao.BaseDaoImp;

public class ResourceDaoImp extends BaseDaoImp<Resource,Long> implements ResourceDao
{

	public ResourceDaoImp()
	{
		super(Resource.class);
	}

}
