package com.base.web.company;

import java.util.List;

import com.base.core.ssh.l3dao.BaseDaoImp;

public class CompanyDaoImp extends BaseDaoImp<Company,Integer> implements CompanyDao
{

	public CompanyDaoImp()
	{
		super(Company.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> queryNameAndId(int cid)
	{
		String hql="";
		if(cid<=0)
		{
			hql="select new Company(id,fullName) from Company where isDelete='0' order by orderNumber";
		}
		else
		{
			hql="select new Company(id,fullName) from Company where isDelete='0' and id="+cid;
		}
		return (List<Company>)getHibernateTemplate().find(hql);
	}

}
