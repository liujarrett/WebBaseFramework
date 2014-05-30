package com.lock.unlockInfo.service;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseService;
import com.lock.unlockInfo.UnlockInfo;

public interface UnlockInfoService extends BaseService<UnlockInfo,Integer>
{

	PageBean<UnlockInfo> query(UnlockInfo unlockInfo,PageBean<UnlockInfo> pageBean);

}
