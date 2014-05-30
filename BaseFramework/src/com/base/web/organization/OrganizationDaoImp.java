package com.base.web.organization;

import com.base.core.ssh.l3dao.BaseDaoImp;

public class OrganizationDaoImp extends BaseDaoImp<Organization,Integer> implements OrganizationDao
{

	public OrganizationDaoImp()
	{
		super(Organization.class);
	}

}
