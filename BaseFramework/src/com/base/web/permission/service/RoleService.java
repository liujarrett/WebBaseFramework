package com.base.web.permission.service;

import java.util.List;

import com.base.core.ssh.l2service.BaseService;
import com.base.web.permission.Role;
import com.base.web.user.User;

public interface RoleService extends BaseService<Role,Integer>
{
	/***
	 * 根据公司ID，查询所有角色；
	 * */
	public List<Role> queryRoleList(int companyId);

	/***
	 * 根据角色ID，查询所有用户；
	 * */
	public List<User> queryUserList(int roleId);

	/***
	 * 是否存在一个名为RoleName的角色
	 * */
	public boolean isExistRole(int companyId,int roleId,String roleName);

}
