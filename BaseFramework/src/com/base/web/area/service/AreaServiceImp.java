package com.base.web.area.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.web.area.Area;
import com.base.web.area.AreaDao;
import com.base.web.company.Company;
import com.base.web.company.CompanyDao;

public class AreaServiceImp extends BaseServiceImp<Area,Long> implements AreaService
{

	private static final long serialVersionUID=8145001050543056164L;

	private AreaDao areaDao;
	private CompanyDao companyDao;

	public AreaDao getAreaDao()
	{
		return areaDao;
	}

	public void setAreaDao(AreaDao areaDao)
	{
		super.setBaseDao(areaDao);
		this.areaDao=areaDao;
	}

	public CompanyDao getCompanyDao()
	{
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao)
	{
		this.companyDao=companyDao;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	@Override
	public List<Area> queryByParentId(long areaParentId)
	{
		String where="where isDelete='0'";
		if(areaParentId<=0)
		{
			where+=" and parent is null";
		}
		else
		{
			where+=" and parent.id="+areaParentId;
		}
		return areaDao.select(where);
	}

	@Override
	public PageBean<Area> queryAllByParentId(long areaParentId,int currentPage,int pageSize)
	{
		/**/
		List<Area> areaList=queryAllByParentId(areaParentId);
		//设置pageBean的相关属性
		PageBean<Area> pageBean=new PageBean<Area>();
		int fromIndex=(currentPage-1)*pageSize;
		int toIndex=fromIndex+pageSize;
		if(toIndex>areaList.size())
		{
			toIndex=areaList.size();
		}
		pageBean.setList(areaList.subList(fromIndex,toIndex));
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		pageBean.setAllRow(areaList.size());
		int totalPages=(areaList.size()+pageSize-1)/pageSize;
		pageBean.setFirstPage(currentPage==1);
		pageBean.setLastPage(currentPage==totalPages);
		pageBean.setTotalPage(totalPages);
		pageBean.setHasNextPage(currentPage!=totalPages);
		pageBean.setHasPreviousPage(currentPage>1);
		//
		return pageBean;
	}

	@Override
	public List<Area> queryAllByParentId(long areaParentId)
	{
		List<Area> areaList=new ArrayList<Area>();
		queryByParentId(areaList,areaParentId);
		return areaList;
	}

	private void queryByParentId(List<Area> areaList,long areaParentId)
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
			areaList.add(area);
			queryByParentId(areaList,area.getId());
		}
	}

	//如果区域已经存在，返回大于0的值
	//如果不存在，返回0
	@Override
	public int isExist(Area area)
	{
		String where="where isDelete='0'";
		if(area.getId()>0)
		{
			where+=" and id!="+area.getId();
		}
		if(area.getParent()!=null)
		{
			where+=" and parent.id="+area.getParent().getId();
		}
		if(area.getAreaName()!=null&&!area.getAreaName().equals(""))
		{
			where+=" and areaName='"+area.getAreaName()+"'";
		}
		int count=areaDao.count(where);
		return count;
	}

	@Override
	public void batchUpdate(Area area,Area...others)
	{
		areaDao.update(area);
		for(Area a:others)
		{
			areaDao.update(a);
		}
	}

	@Override
	public boolean deleteAreaAndSub(long areaId)
	{
		try
		{
			/*删除子*/
			List<Area> areaList=queryAllByParentId(areaId);
			for(Area areaEntity:areaList)
			{
				areaEntity.setIsDelete("1");
				update(areaEntity);
				//将所属公司的区域置空
				Set<Company> companyList=areaEntity.getCompanyList();
				for(Company company:companyList)
				{
					company.setArea(null);
					companyDao.update(company);
				}
			}
			/*删除父*/
			Area area=queryByPK(areaId);
			area.setIsDelete("1");
			update(area);
			//将所属公司的区域置空
			Set<Company> companyList=area.getCompanyList();
			for(Company company:companyList)
			{
				company.setArea(null);
				companyDao.update(company);
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

}
