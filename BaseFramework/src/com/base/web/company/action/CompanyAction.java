package com.base.web.company.action;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.base.core.page.PageBean;
import com.base.core.ssh.l1action.BaseAction;
import com.base.web.company.Company;
import com.base.web.company.service.CompanyService;

public class CompanyAction extends BaseAction<Company>
{

	private static final long serialVersionUID=5709646655608951202L;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private CompanyService companyService;
	private Company company;
	private PageBean<Company> pageBean;
	private int flag;
	private String manuType;
	private String ids;

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids=ids;
	}

	public String getManuType()
	{
		return manuType;
	}

	public void setManuType(String manuType)
	{
		this.manuType=manuType;
	}

	public int getFlag()
	{
		return flag;
	}

	public void setFlag(int flag)
	{
		this.flag=flag;
	}

	public Company getCompany()
	{
		return company;
	}

	public void setCompany(Company company)
	{
		this.company=company;
	}

	public PageBean<Company> getPageBean()
	{
		return pageBean;
	}

	public void setPageBean(PageBean<Company> pageBean)
	{
		this.pageBean=pageBean;
	}

	public CompanyService getCompanyService()
	{
		return companyService;
	}

	public void setCompanyService(CompanyService companyService)
	{
		this.companyService=companyService;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	public String query()
	{
		pageBean=companyService.query(company,pageBean);
		return SUCCESS;
	}

	public String companyIsExists()
	{
		// flag等于1 ，登录代码已经存在 flag等于2，公司名存在
		flag=companyService.checkIsExists(company);
		return SUCCESS;
	}

	public String addCompany()
	{
		System.out.println("company.getFullName()="+company.getFullName());
		//
		company.setCurrentState("0");
		company.setIsDelete("0");
		String currentTime=sdf.format(new Date());
		company.setCreateTime(currentTime);
		company.setUpdateTime(currentTime);
		//
		Serializable serializable=companyService.insert(company);
		if(serializable==null)
		{
			flag=0;
		}
		else
		{
			flag=1;
		}
		return SUCCESS;
	}

	public String queryCompany()
	{
		company=companyService.query(company.getId());
		return manuType;
	}

	public String modifyCompany()
	{
		flag=companyService.update(company)?1:0;
		return SUCCESS;
	}

	public String deleteCompany()
	{
		flag=companyService.delete(company.getId())?1:0;
		return SUCCESS;
	}

	public String passCompany()
	{
		String[] idArray=ids.split(";");
		for(String id:idArray)
		{
			Company company=companyService.query(Integer.parseInt(id));
			if(!company.getCurrentState().equals("1"))
			{
				company.setCurrentState("1");
			}
			companyService.update(company);
		}
		return SUCCESS;
	}

	public String rejectCompany()
	{
		String[] idArray=ids.split(";");
		for(String id:idArray)
		{
			Company company=companyService.query(Integer.parseInt(id));
			if(!company.getCurrentState().equals("2"))
			{
				company.setCurrentState("2");
			}
			companyService.update(company);
		}
		return SUCCESS;
	}

}
