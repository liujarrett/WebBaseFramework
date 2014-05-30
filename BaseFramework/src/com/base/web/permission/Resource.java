package com.base.web.permission;

import com.base.core.ssh.l0model.BaseEntity;

public class Resource extends BaseEntity
{
	private static final long serialVersionUID=-6704890747858915827L;

	// 资源标识
	private int id;

	// 功能标识
	private Function function;

	// 资源编码
	private String resourceCode;

	// 资源类型(客户端，浏览器)
	private String resourceType;

	// 资源显示的名称
	private String name;

	// 资源
	private String url;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public Function getFunction()
	{
		return function;
	}

	public void setFunction(Function function)
	{
		this.function=function;
	}

	public String getResourceCode()
	{
		return resourceCode;
	}

	public void setResourceCode(String resourceCode)
	{
		this.resourceCode=resourceCode;
	}

	public String getResourceType()
	{
		return resourceType;
	}

	public void setResourceType(String resourceType)
	{
		this.resourceType=resourceType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url=url;
	}

}