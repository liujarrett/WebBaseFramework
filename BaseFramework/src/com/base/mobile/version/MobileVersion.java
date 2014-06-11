package com.base.mobile.version;

import com.base.core.ssh.l0model.BaseEntity;

public class MobileVersion extends BaseEntity
{

	private static final long serialVersionUID=-9057120290302586175L;

	// 标识
	private long id;

	// 移动设备类型（iOS和Android）
	private String mobile;

	// 版本号(1.0.0.0)
	private String versionName;

	// 版本号(1)
	private Integer versionCode;

	// 版本说明
	private String description;

	// 发布时间
	private String releaseTime;

	// 下载地址
	private String fileUrl;

	// 文件大小
	private String fileSize;

	public MobileVersion()
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

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile=mobile;
	}

	public String getVersionName()
	{
		return versionName;
	}

	public void setVersionName(String versionName)
	{
		this.versionName=versionName;
	}

	public int getVersionCode()
	{
		return versionCode;
	}

	public void setVersionCode(int versionCode)
	{
		this.versionCode=versionCode;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description=description;
	}

	public String getReleaseTime()
	{
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime)
	{
		this.releaseTime=releaseTime;
	}

	public String getFileUrl()
	{
		return fileUrl;
	}

	public void setFileUrl(String fileUrl)
	{
		this.fileUrl=fileUrl;
	}

	public String getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(String fileSize)
	{
		this.fileSize=fileSize;
	}

}
