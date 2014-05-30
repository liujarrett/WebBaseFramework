package com.base.web.company.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.web.company.Company;
import com.base.web.company.CompanyDao;

public class CompanyServiceImp extends BaseServiceImp<Company,Integer> implements CompanyService
{

	private static final long serialVersionUID=944133844945607686L;

	private CompanyDao companyDao;

	public CompanyDao getCompanyDao()
	{
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao)
	{
		super.setBaseDao(companyDao);
		this.companyDao=companyDao;
	}

	@Override
	public PageBean<Company> query(Company company,PageBean<Company> pageBean)
	{
		String where="where id != 1 and isDelete='0'";
		if(company.getFullName()!=null&&!company.getFullName().equals(""))
		{
			where+=" and fullName like '%"+company.getFullName()+"%'";
		}
		if(company.getAddress()!=null&&!company.getAddress().equals(""))
		{
			where+=" and address like '%"+company.getAddress()+"%'";
		}
		if(company.getTelephone()!=null&&!company.getTelephone().equals(""))
		{
			where+=" and telephone='"+company.getTelephone()+"'";
		}
		return companyDao.selectByPage(where,10,pageBean.getCurrentPage());
	}

	@Override
	public int checkIsExists(Company company)
	{
		if(company.getId()<=0)
		{ //添加公司时的检查
			if(company.getCompanyCode()!=null&&!company.getCompanyCode().equals(""))
			{
				String where="where companyCode='"+company.getCompanyCode()+"'";
				List<Company> companys=companyDao.select(where);
				if(companys!=null&&companys.size()>0)
				{
					return 1;
				}
			}

			if(company.getFullName()!=null&&!company.getFullName().equals(""))
			{
				String where="where fullName='"+company.getFullName()+"'";
				List<Company> companys=companyDao.select(where);
				return companys!=null&&companys.size()>0?2:0;
			}
			return 0;
		}
		else
		{ //修改公司的时候的检查
			if(company.getFullName()!=null&&!company.getFullName().equals(""))
			{
				String where="where fullName='"+company.getFullName()+"' and id !="+company.getId();
				List<Company> companys=companyDao.select(where);
				return companys!=null&&companys.size()>0?1:0;
			}
			return 0;
		}
	}

	@Override
	public Company query(int id)
	{
		Company company=null;
		try
		{
			company=companyDao.select(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return company;
	}

	@Override
	public List<Company> queryNameAndId(int cid)
	{
		return companyDao.queryNameAndId(cid);
	}

}
