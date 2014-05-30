package com.lock.unlockInfo.service;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.core.utilities.SJDateUtil;
import com.lock.unlockInfo.UnlockInfoDao;
import com.lock.unlockInfo.UnlockInfo;

public class UnlockInfoServiceImp extends BaseServiceImp<UnlockInfo,Integer> implements UnlockInfoService
{

	private static final long serialVersionUID=92909386977444957L;
	private UnlockInfoDao unlockInfoDao;

	public UnlockInfoDao getUnlockInfoDao()
	{
		return unlockInfoDao;
	}

	public void setUnlockInfoDao(UnlockInfoDao unlockInfoDao)
	{
		super.setBaseDao(unlockInfoDao);
		this.unlockInfoDao=unlockInfoDao;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	@Override
	public PageBean<UnlockInfo> query(UnlockInfo unlockInfo,PageBean<UnlockInfo> pageBean)
	{
		String where="";
		if(unlockInfo.getUser()!=null&&unlockInfo.getUser().getUserName()!=null&&!unlockInfo.getUser().getUserName().equals(""))
		{
			where+=" where user.userName like '%"+unlockInfo.getUser().getUserName()+"%'";
		}
		if(unlockInfo.getCustomerName()!=null&&!unlockInfo.getCustomerName().equals(""))
		{
			if(where.equals(""))
			{
				where+=" where customerName like '%"+unlockInfo.getCustomerName()+"%'";
			}
			else
			{
				where+=" and customerName like '%"+unlockInfo.getCustomerName()+"%'";
			}
		}
		if(unlockInfo.getUnlockType()!=null&&Integer.parseInt(unlockInfo.getUnlockType())>0)
		{
			if(where.equals(""))
			{
				where+=" where unlockType='"+unlockInfo.getUnlockType()+"'";
			}
			else
			{
				where+=" and unlockType='"+unlockInfo.getUnlockType()+"'";
			}
		}
		if(unlockInfo.getUnlockStartTime()!=null)
		{
			if(where.equals(""))
			{
				where+=" where unlockTime > '"+SJDateUtil.dateToStr(unlockInfo.getUnlockStartTime(),null)+"'";
			}
			else
			{
				where+=" and unlockTime > '"+SJDateUtil.dateToStr(unlockInfo.getUnlockStartTime(),null)+"'";
			}
		}
		if(unlockInfo.getUnlockEndTime()!=null)
		{
			if(where.equals(""))
			{
				where+=" where unlockTime < '"+SJDateUtil.dateToStr(unlockInfo.getUnlockEndTime(),null)+"'";
			}
			else
			{
				where+=" and unlockTime < '"+SJDateUtil.dateToStr(unlockInfo.getUnlockEndTime(),null)+"'";
			}
		}
		where+=" order by id";
		return unlockInfoDao.selectByPage(where,10,pageBean.getCurrentPage());
	}

}
