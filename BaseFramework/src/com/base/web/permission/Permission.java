package com.base.web.permission;

import com.base.core.ssh.l0model.BaseEntity;

public class Permission extends BaseEntity
{

	private static final long serialVersionUID=8624064333081636887L;

	// 权限标识
	private int id;

	// 角色
	private Role role;

	// 功能标识（外键关联）
	private Function function;

	// 资源标识（外键关联）
	private Resource resource;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role=role;
	}

	public Function getFunction()
	{
		return function;
	}

	public void setFunction(Function function)
	{
		this.function=function;
	}

	public Resource getResource()
	{
		return resource;
	}

	public void setResource(Resource resource)
	{
		this.resource=resource;
	}

	public String getRemarks()
	{
		return remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks=remarks;
	}

}
