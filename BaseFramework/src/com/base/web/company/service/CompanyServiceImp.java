package com.base.web.company.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.core.utilities.SJDateUtil;
import com.base.web.company.Company;
import com.base.web.company.CompanyDao;
import com.base.web.permission.RoleDao;
import com.base.web.user.User;
import com.base.web.user.UserDao;

public class CompanyServiceImp extends BaseServiceImp<Company,Long> implements CompanyService
{

	private static final long serialVersionUID=944133844945607686L;

	private CompanyDao companyDao;
	private UserDao userDao;
	private RoleDao roleDao;

	public CompanyDao getCompanyDao()
	{
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao)
	{
		super.setBaseDao(companyDao);
		this.companyDao=companyDao;
	}

	public UserDao getUserDao()
	{
		return userDao;
	}

	public void setUserDao(UserDao userDao)
	{
		this.userDao=userDao;
	}

	public RoleDao getRoleDao()
	{
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao)
	{
		this.roleDao=roleDao;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	@Override
	public int checkIsExists(Company company)
	{
		if(company.getId()<=0)
		{ //添加公司时的检查
			if(company.getCompanyCode()!=null&&!company.getCompanyCode().equals(""))
			{
				String where="where isDelete='0' and companyCode='"+company.getCompanyCode()+"'";
				List<Company> companys=companyDao.select(where);
				if(companys!=null&&companys.size()>0)
				{
					return 1;
				}
			}

			if(company.getCompanyName()!=null&&!company.getCompanyName().equals(""))
			{
				String where="where isDelete='0' and companyName='"+company.getCompanyName()+"'";
				List<Company> companys=companyDao.select(where);
				if(companys!=null&&companys.size()>0)
				{
					return 2;
				}
			}

			return 0;
		}
		else
		{
			//修改公司的时候的检查
			if(company.getCompanyName()!=null&&!company.getCompanyName().equals(""))
			{
				String where="where isDelete='0' and companyName='"+company.getCompanyName()+"' and id !="+company.getId();
				List<Company> companys=companyDao.select(where);
				if(companys!=null&&companys.size()>0)
				{
					return 2;
				}
			}
			return 0;
		}
	}

	@Override
	public PageBean<Company> query(Company company,PageBean<Company> pageBean)
	{
		String where="where id != 1 and isDelete='0'";
		if(company.getCompanyName()!=null&&!company.getCompanyName().equals(""))
		{
			where+=" and companyName like '%"+company.getCompanyName()+"%'";
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
	public List<Company> query(Company company)
	{
		String where="where id != 1 and isDelete='0'";
		if(company.getCompanyName()!=null&&!company.getCompanyName().equals(""))
		{
			where+=" and companyName like '%"+company.getCompanyName()+"%'";
		}
		if(company.getAddress()!=null&&!company.getAddress().equals(""))
		{
			where+=" and address like '%"+company.getAddress()+"%'";
		}
		if(company.getTelephone()!=null&&!company.getTelephone().equals(""))
		{
			where+=" and telephone='"+company.getTelephone()+"'";
		}
		return companyDao.select(where);
	}

	@Override
	public Company query(long id)
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
	public List<Company> queryIdAndName(long cid)
	{
		return companyDao.queryIdAndName(cid);
	}

	@Override
	public boolean addCompany(Company company)
	{
		try
		{
			Long companyId=(Long)companyDao.insert(company);
			company.setId(companyId);
			//
			User user=new User();
			user.setCompany(company);
			user.setOrganization(null);
			user.setRole(roleDao.select((long)2));
			user.setUserName("admin");
			user.setPassword("c4ca4238a0b923820dcc509a6f75849b");

			//
			user.setCurrentState("1");
			user.setIsDelete("0");
			String currentTime=SJDateUtil.DEFAULT_FORMAT.format(new Date());
			user.setCreateTime(currentTime);
			user.setUpdateTime(currentTime);

			//
			Serializable userSerializable=userDao.insert(user);
			if(userSerializable==null)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteCompany(long companyId)
	{
		try
		{
			Company company=query(companyId);
			deleteForLogic(company);
			Set<User> userList=company.getUsers();
			if(userList!=null)
			{
				for(User user:userList)
				{
					userDao.deleteForLogic(user);
				}
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

}
