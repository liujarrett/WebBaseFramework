package com.lock.unlockInfo.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.core.utilities.SJDateUtil;
import com.base.web.area.Area;
import com.base.web.area.AreaDao;
import com.lock.unlockInfo.UnlockInfoDao;
import com.lock.unlockInfo.UnlockInfo;

public class UnlockInfoServiceImp extends BaseServiceImp<UnlockInfo,Long> implements UnlockInfoService
{

	private static final long serialVersionUID=92909386977444957L;
	private UnlockInfoDao unlockInfoDao;
	private AreaDao areaDao;

	public UnlockInfoDao getUnlockInfoDao()
	{
		return unlockInfoDao;
	}

	public void setUnlockInfoDao(UnlockInfoDao unlockInfoDao)
	{
		super.setBaseDao(unlockInfoDao);
		this.unlockInfoDao=unlockInfoDao;
	}

	public AreaDao getAreaDao()
	{
		return areaDao;
	}

	public void setAreaDao(AreaDao areaDao)
	{
		this.areaDao=areaDao;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	@Override
	public PageBean<UnlockInfo> query(UnlockInfo unlockInfo,PageBean<UnlockInfo> pageBean)
	{
		String where="";

		if(unlockInfo.getUser()!=null&&unlockInfo.getUser().getCompany()!=null&&unlockInfo.getUser().getCompany().getArea()!=null&&unlockInfo.getUser().getCompany().getArea().getId()>0)
		{
			StringBuffer sb=new StringBuffer();
			sb.append("'"+unlockInfo.getUser().getCompany().getArea().getId()+"'");
			queryByAreaParentId(sb,unlockInfo.getUser().getCompany().getArea().getId());
			where+=" where user.company.area.id in ("+sb.toString()+")";
		}
		if(unlockInfo.getUser()!=null&&unlockInfo.getUser().getCompany()!=null&&unlockInfo.getUser().getCompany().getId()>0)
		{
			if(where.equals(""))
			{
				where+=" where user.company.id="+unlockInfo.getUser().getCompany().getId();
			}
			else
			{
				where+=" and user.company.id="+unlockInfo.getUser().getCompany().getId();
			}
		}
		if(unlockInfo.getUser()!=null&&unlockInfo.getUser().getUserName()!=null&&!unlockInfo.getUser().getUserName().equals(""))
		{
			if(where.equals(""))
			{
				where+=" where user.userName like '%"+unlockInfo.getUser().getUserName()+"%'";
			}
			else
			{
				where+=" and user.userName like '%"+unlockInfo.getUser().getUserName()+"%'";
			}
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

	@Override
	public List<UnlockInfo> query(UnlockInfo unlockInfo)
	{
		String where="";

		if(unlockInfo.getUser()!=null&&unlockInfo.getUser().getCompany()!=null&&unlockInfo.getUser().getCompany().getArea()!=null&&unlockInfo.getUser().getCompany().getArea().getId()>0)
		{
			StringBuffer sb=new StringBuffer();
			sb.append("'"+unlockInfo.getUser().getCompany().getArea().getId()+"'");
			queryByAreaParentId(sb,unlockInfo.getUser().getCompany().getArea().getId());
			where+=" where user.company.area.id in ("+sb.toString()+")";
		}
		if(unlockInfo.getUser()!=null&&unlockInfo.getUser().getCompany()!=null&&unlockInfo.getUser().getCompany().getId()>0)
		{
			if(where.equals(""))
			{
				where+=" where user.company.id="+unlockInfo.getUser().getCompany().getId();
			}
			else
			{
				where+=" and user.company.id="+unlockInfo.getUser().getCompany().getId();
			}
		}
		if(unlockInfo.getUser()!=null&&unlockInfo.getUser().getUserName()!=null&&!unlockInfo.getUser().getUserName().equals(""))
		{
			if(where.equals(""))
			{
				where+=" where user.userName like '%"+unlockInfo.getUser().getUserName()+"%'";
			}
			else
			{
				where+=" and user.userName like '%"+unlockInfo.getUser().getUserName()+"%'";
			}
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
		return unlockInfoDao.select(where);
	}

	/***
	 * 查询所有子区域
	 * */
	private void queryByAreaParentId(StringBuffer sb,long areaParentId)
	{
		String where=" where isDelete='0'";
		if(areaParentId<=0)
		{
			where+=" and parent is null";
		}
		else
		{
			where+=" and parent.id="+areaParentId;
		}
		//
		List<Area> lsit=areaDao.select(where);
		for(Area area:lsit)
		{
			sb.append(",'"+area.getId()+"'");
			queryByAreaParentId(sb,area.getId());
		}

	}

}
