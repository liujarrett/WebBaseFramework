package com.base.web.permission.service;

import java.util.List;

import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.web.permission.Role;
import com.base.web.permission.RoleDao;
import com.base.web.user.User;
import com.base.web.user.UserDao;

public class RoleServiceImp extends BaseServiceImp<Role,Integer> implements RoleService
{

	private static final long serialVersionUID=-4896990688134496123L;

	private RoleDao roleDao;
	private UserDao userDao;

	public RoleServiceImp()
	{
	}

	public RoleDao getRoleDao()
	{
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao)
	{
		super.setBaseDao(roleDao);
		this.roleDao=roleDao;
	}

	public UserDao getUserDao()
	{
		return userDao;
	}

	public void setUserDao(UserDao userDao)
	{
		this.userDao=userDao;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */
	/***
	 * 根据公司ID，查询所有角色；
	 * */
	@Override
	public List<Role> queryRoleList(int companyId)
	{
		String where="where id!=1 and isDelete='0' and company.id="+companyId;
		return roleDao.select(where);
	}

	/***
	 * 根据角色ID，查询所有用户；
	 * */
	@Override
	public List<User> queryUserList(int roleId)
	{
		String where="where isDelete='0' and role.id="+roleId;
		return userDao.select(where);
	}

	/***
	 * 是否存在一个名为RoleName的角色
	 * */
	@Override
	public boolean isExistRole(int companyId,int roleId,String roleName)
	{
		String where="where isDelete='0' and company.id="+companyId+" and name='"+roleName+"'";
		List<Role> roleList=roleDao.select(where);
		for(Role role:roleList)
		{
			if(role.getId()!=roleId)
			{
				return true;
			}
		}
		return false;
	}
}
