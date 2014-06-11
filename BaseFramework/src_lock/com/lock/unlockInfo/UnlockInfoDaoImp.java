package com.lock.unlockInfo;

import com.base.core.ssh.l3dao.BaseDaoImp;

public class UnlockInfoDaoImp extends BaseDaoImp<UnlockInfo,Long> implements UnlockInfoDao
{

	public UnlockInfoDaoImp()
	{
		super(UnlockInfo.class);
	}

}
