package com.lock.unlockInfo.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseService;
import com.lock.unlockInfo.UnlockInfo;

public interface UnlockInfoService extends BaseService<UnlockInfo,Long>
{

	PageBean<UnlockInfo> query(UnlockInfo unlockInfo,PageBean<UnlockInfo> pageBean);
	List<UnlockInfo> query(UnlockInfo unlockInfo);
	
}
