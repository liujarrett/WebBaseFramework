package com.base.web.company;

import java.util.Set;

import com.base.core.ssh.l0model.BaseEntity;
import com.base.web.organization.Organization;
import com.base.web.permission.Role;
import com.base.web.user.User;

/**
 * 对于Company来说， currentState分为以下几种: 0:待审核 1:审核通过 2:审核不通过
 * 
 * @author Administrator
 * 
 */
public class Company extends BaseEntity
{

	private static final long serialVersionUID=-8570547096420067296L;

	// 公司标识
	private int id;

	// 下属组织机构
	private Set<Organization> organizations;

	// 所有员工
	private Set<User> users;

	// 所拥有的角色
	private Set<Role> roles;

	// 公司编号
	private String companyCode;

	// 公司类型
	private String companyType;

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

	// 法人标识
	private String corporationId;

	// 法人名称
	private String corporationName;

	// 负责人标识
	private String principalId;

	// 负责人名称
	private String principalName;

	// 联系人标识
	private String linkmanId;

	// 联系人姓名
	private String linkmanName;

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

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public Set<Organization> getOrganizations()
	{
		return organizations;
	}

	public void setOrganizations(Set<Organization> organizations)
	{
		this.organizations=organizations;
	}

	public Set<User> getUsers()
	{
		return users;
	}

	public void setUsers(Set<User> users)
	{
		this.users=users;
	}

	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles=roles;
	}

	public String getCompanyCode()
	{
		return companyCode;
	}

	public void setCompanyCode(String companyCode)
	{
		this.companyCode=companyCode;
	}

	public String getCompanyType()
	{
		return companyType;
	}

	public void setCompanyType(String companyType)
	{
		this.companyType=companyType;
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

	public String getCorporationId()
	{
		return corporationId;
	}

	public void setCorporationId(String corporationId)
	{
		this.corporationId=corporationId;
	}

	public String getCorporationName()
	{
		return corporationName;
	}

	public void setCorporationName(String corporationName)
	{
		this.corporationName=corporationName;
	}

	public String getPrincipalId()
	{
		return principalId;
	}

	public void setPrincipalId(String principalId)
	{
		this.principalId=principalId;
	}

	public String getPrincipalName()
	{
		return principalName;
	}

	public void setPrincipalName(String principalName)
	{
		this.principalName=principalName;
	}

	public String getLinkmanId()
	{
		return linkmanId;
	}

	public void setLinkmanId(String linkmanId)
	{
		this.linkmanId=linkmanId;
	}

	public String getLinkmanName()
	{
		return linkmanName;
	}

	public void setLinkmanName(String linkmanName)
	{
		this.linkmanName=linkmanName;
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

	// 构造函数
	public Company()
	{
	}

	public Company(int id,String fullName)
	{
		super();
		this.id=id;
		this.fullName=fullName;
	}
}