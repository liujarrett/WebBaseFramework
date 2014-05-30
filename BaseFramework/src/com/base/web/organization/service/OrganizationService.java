package com.base.web.organization.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseService;
import com.base.web.organization.Organization;

public interface OrganizationService extends BaseService<Organization,Integer>
{

	List<Organization> query(int organizationParentId,int companyId);

	int queryCountByCompanyId(boolean isDirect,int companyId);

	PageBean<Organization> query(Organization organization,int companyId,PageBean<Organization> pageBean);

	boolean isExists(Organization organization);

	void batchUpdate(Organization org,Organization...others);

}
