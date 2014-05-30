package com.base.mobile.version.service;

import java.util.List;

import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.mobile.version.MobileVersion;
import com.base.mobile.version.MobileVersionDao;

public class MobileVersionServiceImp extends BaseServiceImp<MobileVersion,Long> implements MobileVersionService
{

	private static final long serialVersionUID=-6838202537534876394L;

	private MobileVersionDao mobileVersionDao;

	public MobileVersionServiceImp()
	{
	}

	public MobileVersionDao getMobileVersionDao()
	{
		return mobileVersionDao;
	}

	public void setMobileVersionDao(MobileVersionDao mobileVersionDao)
	{
		super.setBaseDao(mobileVersionDao);
		this.mobileVersionDao=mobileVersionDao;
	}

	public MobileVersion getNewMobileVersion()
	{
		List<MobileVersion> list=mobileVersionDao.selectAndroidVersion();
		if(list!=null&&list.size()>0)
		{
			return list.get(0);
		}
		else
		{
			return null;
		}
	}
}
