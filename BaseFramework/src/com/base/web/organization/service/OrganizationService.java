package com.base.web.organization.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseService;
import com.base.web.organization.Organization;

public interface OrganizationService extends BaseService<Organization,Long>
{

	List<Organization> query(long companyId,long organizationParentId);
	
	int queryCountByCompanyId(long companyId,boolean isDirect);
	
	PageBean<Organization> query(long companyId,Organization organization,PageBean<Organization> pageBean);

	boolean isExists(long companyId,Organization organization);

	boolean batchUpdate(Organization org,Organization...others);

}
