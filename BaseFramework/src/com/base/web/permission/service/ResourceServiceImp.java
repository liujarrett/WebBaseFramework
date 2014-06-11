package com.base.web.permission.service;

import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.web.permission.Resource;
import com.base.web.permission.ResourceDao;

public class ResourceServiceImp extends BaseServiceImp<Resource,Long> implements ResourceService
{

	private static final long serialVersionUID=7516280264561735161L;

	private ResourceDao resourceDao;

	public ResourceServiceImp()
	{
	}

	public ResourceDao getResourceDao()
	{
		return resourceDao;
	}

	public void setResourceDao(ResourceDao resourceDao)
	{
		super.setBaseDao(resourceDao);
		this.resourceDao=resourceDao;
	}

}
