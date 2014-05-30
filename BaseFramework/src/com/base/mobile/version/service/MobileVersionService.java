package com.base.mobile.version.service;

import com.base.core.ssh.l2service.BaseService;
import com.base.mobile.version.MobileVersion;

public interface MobileVersionService extends BaseService<MobileVersion,Long>
{
	public MobileVersion getNewMobileVersion();
}
