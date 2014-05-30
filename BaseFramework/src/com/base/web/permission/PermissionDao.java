package com.base.web.permission;

import java.util.List;

import com.base.core.ssh.l3dao.BaseDao;

public interface PermissionDao extends BaseDao<Permission,Integer>
{

	List<Function> queryFunction(String hql);
	
	List<Resource> queryResource(String hql);

}
