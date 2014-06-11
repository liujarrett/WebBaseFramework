package com.base.web.organization.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.web.organization.Organization;
import com.base.web.organization.OrganizationDao;

public class OrganizationServiceImp extends BaseServiceImp<Organization,Long> implements OrganizationService
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

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	@Override
	public List<Organization> query(long companyId,long organizationParentId)
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
	public int queryCountByCompanyId(long companyId,boolean isDirect)
	{
		String where="where company.id="+companyId;
		if(isDirect)
		{
			where+=" and parent is null";
		}
		return organizationDao.count(where);
	}

	@Override
	public PageBean<Organization> query(long companyId,Organization organization,PageBean<Organization> pageBean)
	{
		String where=" where company.id="+companyId+" and isDelete='0'";
		if(organization.getId()>0)
		{
			where+=" and parent.id="+organization.getId();
		}
		//		else {
		//			where += " and parent is null";
		//		}
		if(organization.getOrganizationName()!=null&&!organization.getOrganizationName().equals(""))
		{
			where+=" and organizationName like '%"+organization.getOrganizationName()+"%'";
		}
		return organizationDao.selectByPage(where,10,pageBean.getCurrentPage());
	}

	@Override
	public boolean isExists(long companyId,Organization organization)
	{
		String where=" where company.id="+companyId+" and isDelete='0'";
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
		if(organization.getOrganizationName()!=null&&!organization.getOrganizationName().equals(""))
		{
			where+=" and organizationName='"+organization.getOrganizationName()+"'";
		}
		return organizationDao.count(where)>0;
	}

	@Override
	public boolean batchUpdate(Organization org,Organization...others)
	{
		try
		{
			organizationDao.update(org);
			for(Organization o:others)
			{
				organizationDao.update(o);
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

}
