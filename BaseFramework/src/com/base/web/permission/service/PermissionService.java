package com.base.web.permission.service;

import java.util.List;

import com.base.core.ssh.l2service.BaseService;
import com.base.web.permission.Function;
import com.base.web.permission.Permission;
import com.base.web.permission.Resource;

public interface PermissionService extends BaseService<Permission,Long>
{
	/***
	 * 根据角色ID，查询所有权限
	 * */
	public List<Permission> queryPermissionListByRoleId(long roleId);
	
	
	/***
	 * 根据角色ID，查询所有功能
	 * */
	public List<Function> queryFunctionListByRoleId(long roleId);
	
	/***
	 * 根据角色ID，查询所有资源
	 * */
	public List<Resource> queryResourceListByRoleId(long roleId);
	
	/***
	 * 根据功能父ID和角色ID，查询子功能列表；
	 * */
	public List<Function> queryFunctionListByParentId(long funcParentId,long roleId);

	/***
	 * 根据功能ID和角色ID，查询资源列表；
	 * */
	public List<Resource> queryResourceListByFunctionId(long functionId,long roleId);

	/***
	 * 删除指定角色的权限
	 * */
	public int deleteRolePermission(long currentRoleId);
	
	/***
	 * 保存权限
	 * */
	public boolean savePermission(long currentRoleId,List<Permission> newPermissionList);

}
