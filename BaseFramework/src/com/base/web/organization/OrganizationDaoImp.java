package com.base.web.organization;

import com.base.core.ssh.l3dao.BaseDaoImp;

public class OrganizationDaoImp extends BaseDaoImp<Organization,Long> implements OrganizationDao
{

	public OrganizationDaoImp()
	{
		super(Organization.class);
	}

}
