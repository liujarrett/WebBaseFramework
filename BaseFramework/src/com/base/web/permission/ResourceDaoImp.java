package com.base.web.permission;

import com.base.core.ssh.l3dao.BaseDaoImp;

public class ResourceDaoImp extends BaseDaoImp<Resource,Integer> implements ResourceDao
{

	public ResourceDaoImp()
	{
		super(Resource.class);
	}


}
