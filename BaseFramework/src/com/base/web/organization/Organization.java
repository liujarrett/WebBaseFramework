package com.base.web.organization;

import java.util.Set;

import com.base.core.ssh.l0model.BaseEntity;
import com.base.web.company.Company;
import com.base.web.user.User;

public class Organization extends BaseEntity
{
	private static final long serialVersionUID=1L;

	// 组织机构标识
	private int id;

	// 上级组织机构
	private Organization parent;

	// 下级组织
	private Set<Organization> childList;

	// 所有员工
	private Set<User> users;

	// 当前组织机构层级
	private int level;

	// 所属公司
	private Company company;

	// 组织机构编号
	private String organizationCode;

	// 组织机构类型
	private String organizationType;

	// 名称
	private String fullName;

	// 昵称
	private String shortName;

	// 全拼
	private String fullSpelling;

	// 简拼
	private String shortSpelling;

	// 完整英文名
	private String fullEnglish;

	// 简写英文名
	private String shortEnglish;

	// 简介，描述
	private String describes;

	// 管理者标识（经理）
	private String managerId;

	// 管理者名称（经理）
	private String managerName;

	// 电话
	private String telephone;

	// 电子邮件
	private String email;

	// 省
	private String province;

	// 市
	private String city;

	// 地址
	private String address;

	// 邮编
	private String postCode;

	public Organization getParent()
	{
		return parent;
	}

	public void setParent(Organization parent)
	{
		this.parent=parent;
	}

	public Set<Organization> getChildList()
	{
		return childList;
	}

	public void setChildList(Set<Organization> childList)
	{
		this.childList=childList;
	}

	public Set<User> getUsers()
	{
		return users;
	}

	public void setUsers(Set<User> users)
	{
		this.users=users;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level=level;
	}

	public Company getCompany()
	{
		return company;
	}

	public void setCompany(Company company)
	{
		this.company=company;
	}

	public String getOrganizationCode()
	{
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode)
	{
		this.organizationCode=organizationCode;
	}

	public String getOrganizationType()
	{
		return organizationType;
	}

	public void setOrganizationType(String organizationType)
	{
		this.organizationType=organizationType;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName=fullName;
	}

	public String getShortName()
	{
		return shortName;
	}

	public void setShortName(String shortName)
	{
		this.shortName=shortName;
	}

	public String getFullSpelling()
	{
		return fullSpelling;
	}

	public void setFullSpelling(String fullSpelling)
	{
		this.fullSpelling=fullSpelling;
	}

	public String getShortSpelling()
	{
		return shortSpelling;
	}

	public void setShortSpelling(String shortSpelling)
	{
		this.shortSpelling=shortSpelling;
	}

	public String getFullEnglish()
	{
		return fullEnglish;
	}

	public void setFullEnglish(String fullEnglish)
	{
		this.fullEnglish=fullEnglish;
	}

	public String getShortEnglish()
	{
		return shortEnglish;
	}

	public void setShortEnglish(String shortEnglish)
	{
		this.shortEnglish=shortEnglish;
	}

	public String getDescribes()
	{
		return describes;
	}

	public void setDescribes(String describes)
	{
		this.describes=describes;
	}

	public String getManagerId()
	{
		return managerId;
	}

	public void setManagerId(String managerId)
	{
		this.managerId=managerId;
	}

	public String getManagerName()
	{
		return managerName;
	}

	public void setManagerName(String managerName)
	{
		this.managerName=managerName;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone=telephone;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email=email;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province=province;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city=city;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address=address;
	}

	public String getPostCode()
	{
		return postCode;
	}

	public void setPostCode(String postCode)
	{
		this.postCode=postCode;
	}
}
