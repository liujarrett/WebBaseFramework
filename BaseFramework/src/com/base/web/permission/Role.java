package com.base.web.permission;

import java.util.List;

import com.base.core.ssh.l0model.BaseEntity;
import com.base.web.company.Company;

public class Role extends BaseEntity
{
	private static final long serialVersionUID=-3442497245627129415L;

	// 角色标识
	private int id;

	// 所属的公司
	private Company company;

	// 角色编号
	private String roleCode;

	// 角色类型
	private String roleType;

	// 角色名称（用于前台显示）
	private String name;

	// 角色等级标识（eg:高级，中级，低级）
	private String gradeId;

	// 角色等级名称（eg:高级，中级，低级）
	private String gradeName;

	// 所拥有的权限
	private List<Permission> permissions;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public Company getCompany()
	{
		return company;
	}

	public void setCompany(Company company)
	{
		this.company=company;
	}

	public String getRoleCode()
	{
		return roleCode;
	}

	public void setRoleCode(String roleCode)
	{
		this.roleCode=roleCode;
	}

	public String getRoleType()
	{
		return roleType;
	}

	public void setRoleType(String roleType)
	{
		this.roleType=roleType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public String getGradeId()
	{
		return gradeId;
	}

	public void setGradeId(String gradeId)
	{
		this.gradeId=gradeId;
	}

	public String getGradeName()
	{
		return gradeName;
	}

	public void setGradeName(String gradeName)
	{
		this.gradeName=gradeName;
	}

	public List<Permission> getPermissions()
	{
		return permissions;
	}

	public void setPermissions(List<Permission> permissions)
	{
		this.permissions=permissions;
	}
}
