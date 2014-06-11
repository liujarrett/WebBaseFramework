package com.base.web.log;

import com.base.core.ssh.l0model.BaseEntity;
import com.base.web.user.User;

public class LoginLog extends BaseEntity
{
	private static final long serialVersionUID=5622264164008749758L;
	private long id;
	private User user;
	private String loginType;//[1 手机客户端 ; 2 web浏览器]
	private String loginTime;
	private String logoutTime;
	private String loginIP;
	private String result;
	private String userIMEI;
	private String userIMSI;
	private String userMobilePhone;
	private String userDeviceBrand;
	private String userDeviceModel;
	private String userDeviceVersion;
	private String userDeviceOS;
	private String userDeviceOSVersion;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id=id;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user=user;
	}

	public String getLoginType()
	{
		return loginType;
	}

	public void setLoginType(String loginType)
	{
		this.loginType=loginType;
	}

	public String getLoginTime()
	{
		return loginTime;
	}

	public void setLoginTime(String loginTime)
	{
		this.loginTime=loginTime;
	}

	public String getLogoutTime()
	{
		return logoutTime;
	}

	public void setLogoutTime(String logoutTime)
	{
		this.logoutTime=logoutTime;
	}

	public String getLoginIP()
	{
		return loginIP;
	}

	public void setLoginIP(String loginIP)
	{
		this.loginIP=loginIP;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result=result;
	}

	public String getUserIMEI()
	{
		return userIMEI;
	}

	public void setUserIMEI(String userIMEI)
	{
		this.userIMEI=userIMEI;
	}

	public String getUserIMSI()
	{
		return userIMSI;
	}

	public void setUserIMSI(String userIMSI)
	{
		this.userIMSI=userIMSI;
	}

	public String getUserMobilePhone()
	{
		return userMobilePhone;
	}

	public void setUserMobilePhone(String userMobilePhone)
	{
		this.userMobilePhone=userMobilePhone;
	}

	public String getUserDeviceBrand()
	{
		return userDeviceBrand;
	}

	public void setUserDeviceBrand(String userDeviceBrand)
	{
		this.userDeviceBrand=userDeviceBrand;
	}

	public String getUserDeviceModel()
	{
		return userDeviceModel;
	}

	public void setUserDeviceModel(String userDeviceModel)
	{
		this.userDeviceModel=userDeviceModel;
	}

	public String getUserDeviceVersion()
	{
		return userDeviceVersion;
	}

	public void setUserDeviceVersion(String userDeviceVersion)
	{
		this.userDeviceVersion=userDeviceVersion;
	}

	public String getUserDeviceOS()
	{
		return userDeviceOS;
	}

	public void setUserDeviceOS(String userDeviceOS)
	{
		this.userDeviceOS=userDeviceOS;
	}

	public String getUserDeviceOSVersion()
	{
		return userDeviceOSVersion;
	}

	public void setUserDeviceOSVersion(String userDeviceOSVersion)
	{
		this.userDeviceOSVersion=userDeviceOSVersion;
	}

	// ***********************下面的字段在数据库没有对应列，只是为了查询时，传递参数方便**************************
	private String searchStartTime;
	private String searchEndTime;

	public String getSearchStartTime()
	{
		return searchStartTime;
	}

	public void setSearchStartTime(String searchStartTime)
	{
		this.searchStartTime=searchStartTime;
	}

	public String getSearchEndTime()
	{
		return searchEndTime;
	}

	public void setSearchEndTime(String searchEndTime)
	{
		this.searchEndTime=searchEndTime;
	}

}
