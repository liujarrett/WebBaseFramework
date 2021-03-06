package com.base.web.permission.service;

import java.util.List;

import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.web.permission.Function;
import com.base.web.permission.FunctionDao;
import com.base.web.permission.Permission;
import com.base.web.permission.PermissionDao;
import com.base.web.permission.Resource;

public class PermissionServiceImp extends BaseServiceImp<Permission,Long> implements PermissionService
{

	private static final long serialVersionUID=-3482050884146579720L;

	private PermissionDao permissionDao;
	private FunctionDao functionDao;

	public PermissionServiceImp()
	{
	}

	public PermissionDao getPermissionDao()
	{
		return permissionDao;
	}

	public void setPermissionDao(PermissionDao permissionDao)
	{
		super.setBaseDao(permissionDao);
		this.permissionDao=permissionDao;
	}

	public FunctionDao getFunctionDao()
	{
		return functionDao;
	}

	public void setFunctionDao(FunctionDao functionDao)
	{
		this.functionDao=functionDao;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	/***
	 * 根据角色ID，查询所有权限
	 * */
	@Override
	public List<Permission> queryPermissionListByRoleId(long roleId)
	{
		String hql="where role.id = "+roleId+" and isDelete='0'";
		return permissionDao.select(hql);
	}

	/***
	 * 根据角色ID，查询所有功能
	 * */
	@Override
	public List<Function> queryFunctionListByRoleId(long roleId)
	{
		String where  = " where isDelete='0' and id in(select distinct per.function.id from Permission as per where per.role.id="+ roleId +")";
		return functionDao.select(where);
	}

	/***
	 * 根据角色ID，查询所有资源
	 * */
	@Override
	public List<Resource> queryResourceListByRoleId(long roleId)
	{
		String hql="select p.resource from Permission as p where p.role.id = "+roleId+" and p.resource.isDelete='0'";
		return permissionDao.queryResource(hql);
	}

	/***
	 * 根据功能父ID和角色ID，查询子功能列表；
	 * */
	@Override
	public List<Function> queryFunctionListByParentId(long funcParentId,long roleId)
	{
		String hql=null;
		if(funcParentId<=0)
		{
			hql="select p.function from Permission as p where p.role.id = "+roleId+" and p.function.parent is null and p.function.isDelete=0";
			hql+=" order by p.function.orderNumber ";
			return permissionDao.queryFunction(hql);
		}
		else
		{
			String where  = " where parent.id=" + funcParentId + " and isDelete='0' and id in(select distinct per.function.id from Permission as per where per.role.id="+ roleId +")";
			return functionDao.select(where);
		}
	}

	/***
	 * 根据功能ID和角色ID，查询资源列表；
	 * */
	@Override
	public List<Resource> queryResourceListByFunctionId(long functionId,long roleId)
	{
		String hql="select p.resource from Permission as p where p.function.id = "+functionId+" and p.role.id = "+roleId+" and p.resource.isDelete='0'";
		return permissionDao.queryResource(hql);
	}

	/***
	 * 删除指定角色的权限
	 * */
	@Override
	public int deleteRolePermission(long currentRoleId)
	{
		return permissionDao.delete("where role.id="+currentRoleId);
	}

	/***
	 * 保存权限
	 * */
	@Override
	public boolean savePermission(long currentRoleId,List<Permission> newPermissionList)
	{
		try
		{
			//删除
			deleteRolePermission(currentRoleId);

			//新建
			for(Permission permission:newPermissionList)
			{
				insert(permission);
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

}
