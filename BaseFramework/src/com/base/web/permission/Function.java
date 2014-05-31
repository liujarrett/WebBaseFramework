package com.base.web.permission;

import java.util.HashSet;
import java.util.Set;

import com.base.core.ssh.l0model.BaseEntity;

public class Function extends BaseEntity
{
	private static final long serialVersionUID=6142242237313513896L;

	// 功能标识
	private Integer id;

	// 上级功能
	private Function parent;

	// 下级功能
	private Set<Function> childList=new HashSet<Function>();

	// 拥有的资源
	private Set<Resource> resources;

	// 功能层级
	private Integer functionLevel;

	// 功能编码
	private String functionCode;

	// 功能类型(客户端，浏览器)
	private String functionType;

	// 功能显示的名称
	private String name;

	// 功能显示图标
	private String icon;

	// 资源
	private String url;

	public Function()
	{

	}

	public Function(Integer id,String name)
	{
		super();
		this.id=id;
		this.name=name;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id=id;
	}

	public Function getParent()
	{
		return parent;
	}

	public void setParent(Function parent)
	{
		this.parent=parent;
	}

	public Set<Function> getChildList()
	{
		return childList;
	}

	public void setChildList(Set<Function> childList)
	{
		this.childList=childList;
	}

	public Integer getFunctionLevel()
	{
		return functionLevel;
	}

	public void setFunctionLevel(Integer functionLevel)
	{
		this.functionLevel=functionLevel;
	}

	public String getFunctionCode()
	{
		return functionCode;
	}

	public void setFunctionCode(String functionCode)
	{
		this.functionCode=functionCode;
	}

	public String getFunctionType()
	{
		return functionType;
	}

	public void setFunctionType(String functionType)
	{
		this.functionType=functionType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon=icon;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url=url;
	}

	public Set<Resource> getResources()
	{
		return resources;
	}

	public void setResources(Set<Resource> resources)
	{
		this.resources=resources;
	}

	@Override
	public String toString()
	{
		return "Function [id="+id+", functionCode="+functionCode+", name="+name+", icon="+icon+", url="+url+"]";
	}

}
