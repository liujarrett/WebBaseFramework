package com.lock.unlockInfo;

import java.util.Date;

import com.base.core.ssh.l0model.BaseEntity;
import com.base.web.user.User;

public class UnlockInfo extends BaseEntity
{
	private static final long serialVersionUID=-6349382833888266788L;

	// 标识
	private long id;
	/* 开锁人员 */
	private User user; // 查询
	/* 国际移动设备识别码，手机串号，*#06# */
	private String userImei;
	/* 国际移动用户识别码，手机卡串号 */
	private String userImsi;
	/* 手机号 */
	private String userMobilePhone;
	/* 手机品牌 */
	private String userDeviceBrand;
	/* 手机型号 */
	private String userDeviceModel;
	/* 手机版本号 */
	private String userDeviceVersion;
	/* 手机操作系统(android,ios) */
	private String userDeviceOs;
	/* 手机操作系统版本 */
	private String userDeviceOsVersion;
	/* 开锁类型 */
	private String unlockType;// 查询
	/* 开锁时间 */
	private String unlockTime;// 查询
	/* 开锁地点 */
	private String unlockLocation;
	/* 开锁服务单 */
	private String unlockWorkOrderImg;
	/* 客户姓名 */
	private String customerName;// 查询
	/* 客户身份证号 */
	private String customerIdNo;
	/* 客户身份证图片 */
	private String customerIdImg;
	/* 客户驾驶证图片 */
	private String customerDrivingLicenseImg;
	/* 客户行驶证图片 */
	private String customerVehicleLicenseImg;
	/* 客户执照图片 */
	private String customerBusinessLicenseImg;
	/* 客户介绍信图片 */
	private String customerIntroductionLetterImg;

	public UnlockInfo()
	{
	}

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

	public String getUserImei()
	{
		return userImei;
	}

	public void setUserImei(String userImei)
	{
		this.userImei=userImei;
	}

	public String getUserImsi()
	{
		return userImsi;
	}

	public void setUserImsi(String userImsi)
	{
		this.userImsi=userImsi;
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

	public String getUserDeviceOs()
	{
		return userDeviceOs;
	}

	public void setUserDeviceOs(String userDeviceOs)
	{
		this.userDeviceOs=userDeviceOs;
	}

	public String getUserDeviceOsVersion()
	{
		return userDeviceOsVersion;
	}

	public void setUserDeviceOsVersion(String userDeviceOsVersion)
	{
		this.userDeviceOsVersion=userDeviceOsVersion;
	}

	public String getUnlockType()
	{
		return unlockType;
	}

	public void setUnlockType(String unlockType)
	{
		this.unlockType=unlockType;
	}

	public String getUnlockTime()
	{
		return unlockTime;
	}

	public void setUnlockTime(String unlockTime)
	{
		this.unlockTime=unlockTime;
	}

	public String getUnlockLocation()
	{
		return unlockLocation;
	}

	public void setUnlockLocation(String unlockLocation)
	{
		this.unlockLocation=unlockLocation;
	}

	public String getUnlockWorkOrderImg()
	{
		return unlockWorkOrderImg;
	}

	public void setUnlockWorkOrderImg(String unlockWorkOrderImg)
	{
		this.unlockWorkOrderImg=unlockWorkOrderImg;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName=customerName;
	}

	public String getCustomerIdNo()
	{
		return customerIdNo;
	}

	public void setCustomerIdNo(String customerIdNo)
	{
		this.customerIdNo=customerIdNo;
	}

	public String getCustomerIdImg()
	{
		return customerIdImg;
	}

	public void setCustomerIdImg(String customerIdImg)
	{
		this.customerIdImg=customerIdImg;
	}

	public String getCustomerDrivingLicenseImg()
	{
		return customerDrivingLicenseImg;
	}

	public void setCustomerDrivingLicenseImg(String customerDrivingLicenseImg)
	{
		this.customerDrivingLicenseImg=customerDrivingLicenseImg;
	}

	public String getCustomerVehicleLicenseImg()
	{
		return customerVehicleLicenseImg;
	}

	public void setCustomerVehicleLicenseImg(String customerVehicleLicenseImg)
	{
		this.customerVehicleLicenseImg=customerVehicleLicenseImg;
	}

	public String getCustomerBusinessLicenseImg()
	{
		return customerBusinessLicenseImg;
	}

	public void setCustomerBusinessLicenseImg(String customerBusinessLicenseImg)
	{
		this.customerBusinessLicenseImg=customerBusinessLicenseImg;
	}

	public String getCustomerIntroductionLetterImg()
	{
		return customerIntroductionLetterImg;
	}

	public void setCustomerIntroductionLetterImg(String customerIntroductionLetterImg)
	{
		this.customerIntroductionLetterImg=customerIntroductionLetterImg;
	}

	@Override
	public String toString()
	{
		return "UnlockInfo [id="+id+", user="+(user==null?-1:user.getId())+", userImei="+userImei+", userImsi="+userImsi+", userMobilePhone="+userMobilePhone+", userDeviceBrand="+userDeviceBrand+", userDeviceModel="+userDeviceModel+", userDeviceVersion="+userDeviceVersion+", userDeviceOs="+userDeviceOs+", userDeviceOsVersion="+userDeviceOsVersion+", unlockType="+unlockType+", unlockTime="+unlockTime+", unlockLocation="+unlockLocation+", unlockWorkOrderImg="+unlockWorkOrderImg+", customerName="+customerName+", customerIdNo="+customerIdNo+", customerIdImg="+customerIdImg+", customerDrivingLicenseImg="+customerDrivingLicenseImg+", customerVehicleLicenseImg="+customerVehicleLicenseImg+", customerBusinessLicenseImg="+customerBusinessLicenseImg+", customerIntroductionLetterImg="
				+customerIntroductionLetterImg+"]";
	}

	// 数据库没有对应字段 便于前台传值
	private Date unlockStartTime;
	private Date unlockEndTime;

	public Date getUnlockStartTime()
	{
		return unlockStartTime;
	}

	public void setUnlockStartTime(Date unlockStartTime)
	{
		this.unlockStartTime=unlockStartTime;
	}

	public Date getUnlockEndTime()
	{
		return unlockEndTime;
	}

	public void setUnlockEndTime(Date unlockEndTime)
	{
		this.unlockEndTime=unlockEndTime;
	}

}
