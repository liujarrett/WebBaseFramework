package com.lock.unlockInfo.action;

import com.base.core.page.PageBean;
import com.base.core.ssh.l1action.BaseAction;
import com.lock.unlockInfo.UnlockInfo;
import com.lock.unlockInfo.service.UnlockInfoService;

public class UnlockInfoAction extends BaseAction<UnlockInfo>
{

	private static final long serialVersionUID=1904763586093277851L;

	private UnlockInfoService unlockInfoService;
	private UnlockInfo unlockInfo;
	private PageBean<UnlockInfo> pageBean;

	public UnlockInfo getUnlockInfo()
	{
		return unlockInfo;
	}

	public void setUnlockInfo(UnlockInfo unlockInfo)
	{
		this.unlockInfo=unlockInfo;
	}

	public PageBean<UnlockInfo> getPageBean()
	{
		return pageBean;
	}

	public void setPageBean(PageBean<UnlockInfo> pageBean)
	{
		this.pageBean=pageBean;
	}

	public UnlockInfoService getUnlockInfoService()
	{
		return unlockInfoService;
	}

	public void setUnlockInfoService(UnlockInfoService unlockInfoService)
	{
		this.unlockInfoService=unlockInfoService;
	}

	public String queryForGrid()
	{
		pageBean=unlockInfoService.query(unlockInfo,pageBean);
		return SUCCESS;
	}

}
