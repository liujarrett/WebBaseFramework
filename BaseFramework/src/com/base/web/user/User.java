package com.base.web.user;

import com.base.core.ssh.l0model.BaseEntity;
import com.base.web.company.Company;
import com.base.web.organization.Organization;
import com.base.web.permission.Role;

/**
 * 对于User来说,currentState 0 未审核 1 已审核
 * 
 * @author Administrator
 * 
 */
public class User extends BaseEntity
{

	private static final long serialVersionUID=1L;

	// 用户标识
	private long id;

	// 用户所属公司
	private Company company;

	// 用户所属组织机构
	private Organization organization;

	// 用户角色
	private Role role;

	// 用户编号
	private String userCode;

	// 用户类型
	private String userType;

	// 登录名称
	private String userName;

	// 登录密码
	private String password;

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

	// 移动电话
	private String mobilePhone;

	// 办公电话
	private String officePhone;

	// 电子邮件
	private String email;

	// 身份证
	private String idCard;

	// 性别
	private String sex;

	// 职位
	private String jobTitle;

	// 职务
	private String jobDuty;

	// 学历
	private String degree;

	// 身高
	private String height;

	// 体重
	private String weight;

	// 血型
	private String bloodType;

	// 生日
	private String birthday;

	// 出生地
	private String birthPlace;

	// 家电话
	private String homePhone;

	// 家地址
	private String homeAddress;

	// 家邮编
	private String homePostCode;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
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

	public Organization getOrganization()
	{
		return organization;
	}

	public void setOrganization(Organization organization)
	{
		this.organization=organization;
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role=role;
	}

	public String getUserCode()
	{
		return userCode;
	}

	public void setUserCode(String userCode)
	{
		this.userCode=userCode;
	}

	public String getUserType()
	{
		return userType;
	}

	public void setUserType(String userType)
	{
		this.userType=userType;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName=userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password=password;
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

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone=mobilePhone;
	}

	public String getOfficePhone()
	{
		return officePhone;
	}

	public void setOfficePhone(String officePhone)
	{
		this.officePhone=officePhone;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email=email;
	}

	public String getIdCard()
	{
		return idCard;
	}

	public void setIdCard(String idCard)
	{
		this.idCard=idCard;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex=sex;
	}

	public String getJobTitle()
	{
		return jobTitle;
	}

	public void setJobTitle(String jobTitle)
	{
		this.jobTitle=jobTitle;
	}

	public String getJobDuty()
	{
		return jobDuty;
	}

	public void setJobDuty(String jobDuty)
	{
		this.jobDuty=jobDuty;
	}

	public String getDegree()
	{
		return degree;
	}

	public void setDegree(String degree)
	{
		this.degree=degree;
	}

	public String getHeight()
	{
		return height;
	}

	public void setHeight(String height)
	{
		this.height=height;
	}

	public String getWeight()
	{
		return weight;
	}

	public void setWeight(String weight)
	{
		this.weight=weight;
	}

	public String getBloodType()
	{
		return bloodType;
	}

	public void setBloodType(String bloodType)
	{
		this.bloodType=bloodType;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday=birthday;
	}

	public String getBirthPlace()
	{
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace)
	{
		this.birthPlace=birthPlace;
	}

	public String getHomePhone()
	{
		return homePhone;
	}

	public void setHomePhone(String homePhone)
	{
		this.homePhone=homePhone;
	}

	public String getHomeAddress()
	{
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress)
	{
		this.homeAddress=homeAddress;
	}

	public String getHomePostCode()
	{
		return homePostCode;
	}

	public void setHomePostCode(String homePostCode)
	{
		this.homePostCode=homePostCode;
	}
}