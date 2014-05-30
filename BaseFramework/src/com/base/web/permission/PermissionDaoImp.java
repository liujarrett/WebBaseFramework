package com.base.web.permission;

import java.util.List;

import com.base.core.ssh.l3dao.BaseDaoImp;

public class PermissionDaoImp extends BaseDaoImp<Permission,Integer> implements PermissionDao
{

	public PermissionDaoImp()
	{
		super(Permission.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Function> queryFunction(String hql)
	{
		return (List<Function>)getHibernateTemplate().find(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> queryResource(String hql)
	{
		return (List<Resource>)getHibernateTemplate().find(hql);
	}

}
