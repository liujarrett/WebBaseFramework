package com.base.web.organization.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.web.organization.Organization;
import com.base.web.organization.OrganizationDao;

public class OrganizationServiceImp extends BaseServiceImp<Organization,Integer> implements OrganizationService
{

	private static final long serialVersionUID=4032032169278651346L;

	private OrganizationDao organizationDao;

	public OrganizationDao getOrganizationDao()
	{
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao)
	{
		super.setBaseDao(organizationDao);
		this.organizationDao=organizationDao;
	}

	@Override
	public List<Organization> query(int organizationParentId,int companyId)
	{
		String where="where company.id="+companyId+" and isDelete='0'";
		if(organizationParentId<=0)
		{
			where+=" and parent is null";
		}
		else
		{
			where+=" and parent.id="+organizationParentId;
		}
		return organizationDao.select(where);
	}

	@Override
	public int queryCountByCompanyId(boolean isDirect,int companyId)
	{
		String where="where company.id="+companyId;
		if(isDirect)
		{
			where+=" and parent is null";
		}
		return organizationDao.count(where);
	}

	@Override
	public PageBean<Organization> query(Organization organization,int companyId,PageBean<Organization> pageBean)
	{
		String where=" where company.id="+companyId+" and isDelete='0'";
		if(organization.getId()>0)
		{
			where+=" and parent.id="+organization.getId();
		}
		//		else {
		//			where += " and parent is null";
		//		}
		if(organization.getFullName()!=null&&!organization.getFullName().equals(""))
		{
			where+=" and fullName like '%"+organization.getFullName()+"%'";
		}
		return organizationDao.selectByPage(where,10,pageBean.getCurrentPage());
	}

	@Override
	public boolean isExists(Organization organization)
	{
		String where=" where company.id="+organization.getCompany().getId()+" and isDelete='0'";
		if(organization.getParent()!=null&&organization.getParent().getId()>0)
		{
			where+=" and parent.id="+organization.getParent().getId();
		}
		else
		{
			where+=" and parent is null";
		}
		if(organization.getId()>0)
		{
			where+=" and id!="+organization.getId();
		}
		if(organization.getFullName()!=null&&!organization.getFullName().equals(""))
		{
			where+=" and fullName='"+organization.getFullName()+"'";
		}
		return organizationDao.count(where)>0;
	}

	@Override
	public void batchUpdate(Organization org,Organization...others)
	{
		organizationDao.update(org);
		for(Organization o:others)
		{
			organizationDao.update(o);
		}
	}

}
