package com.base.web.area.action;

import java.util.ArrayList;
import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l1action.BaseAction;
import com.base.web.area.Area;
import com.base.web.area.service.AreaService;

public class AreaAction extends BaseAction
{

	private static final long serialVersionUID=-2336082918526391047L;

	private AreaService areaService;
	private Area area;
	private List<Area> areaList;
	private PageBean<Area> pageBean;
	private long areaParentId;
	private int flag;
	private String manuType;
	private List<Object[]> treeList;

	public String getManuType()
	{
		return manuType;
	}

	public void setManuType(String manuType)
	{
		this.manuType=manuType;
	}

	public int getFlag()
	{
		return flag;
	}

	public void setFlag(int flag)
	{
		this.flag=flag;
	}

	public AreaAction()
	{
	}

	public AreaService getAreaService()
	{
		return areaService;
	}

	public void setAreaService(AreaService areaService)
	{
		this.areaService=areaService;
	}

	public Area getArea()
	{
		return area;
	}

	public void setArea(Area area)
	{
		this.area=area;
	}

	public List<Area> getAreaList()
	{
		return areaList;
	}

	public void setAreaList(List<Area> areaList)
	{
		this.areaList=areaList;
	}

	public PageBean<Area> getPageBean()
	{
		return pageBean;
	}

	public void setPageBean(PageBean<Area> pageBean)
	{
		this.pageBean=pageBean;
	}

	public long getAreaParentId()
	{
		return areaParentId;
	}

	public void setAreaParentId(long areaParentId)
	{
		this.areaParentId=areaParentId;
	}

	public List<Object[]> getTreeList()
	{
		return treeList;
	}

	public void setTreeList(List<Object[]> treeList)
	{
		this.treeList=treeList;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	// 区域树状图
	public String queryTreeNodeData()
	{
		List<Area> list=areaService.queryByParentId(areaParentId);
		for(Area area:list)
		{
			if(treeList==null)
			{
				treeList=new ArrayList<Object[]>();
			}
			Object[] obj=new Object[3];
			obj[0]=area.getId(); // id
			obj[1]=area.getAreaName(); // 名称
			obj[2]=areaService.queryByParentId(area.getId()).size(); // 下级机构个数
			treeList.add(obj);
		}
		return SUCCESS;
	}

	//父节点下的所有子区域（分页）
	public String queryForGrid()
	{
		int currentPage=1;
		int pageSize=10;
		if(pageBean!=null)
		{
			currentPage=pageBean.getCurrentPage();
		}
		pageBean=areaService.queryAllByParentId(areaParentId,currentPage,pageSize);
		return SUCCESS;
	}

	public String addArea()
	{
		// flag == 1 添加成功
		// flag == 2 名称存在

		int hasExistedCode=areaService.isExist(area);
		if(hasExistedCode>0)
		{
			flag=2;
			return SUCCESS;
		}
		area.setIsDelete("0");
		areaService.insert(area);
		flag=1;
		return SUCCESS;
	}

	public String editArea()
	{
		// flag == 1 添加成功
		// flag == 2 名称存在
		// flag == 3 不能选择子机构作为父机构！

		int hasExistedCode=areaService.isExist(area);
		if(hasExistedCode>0)
		{
			flag=2;
			return SUCCESS;
		}
		/**/
		Area area_=areaService.queryByPK(area.getId());
		area_.setAreaName(area.getAreaName());
		area_.setAreaCode(area.getAreaCode());
		//
		if(area.getParent()==null||area.getParent().getId()<=0)
		{ // 修改后上级区域为空
			area_.setParent(null);
		}
		else
		{
			List<Area> areaList=areaService.queryAllByParentId(area.getId());
			for(Area areaEntity:areaList)
			{
				if(areaEntity.getId()==area.getParent().getId())
				{
					flag=3;
					return SUCCESS;
				}
			}
			area_.setParent(area.getParent()); // 修改后上级机构为其他
		}
		//
		if(areaService.update(area_))
		{
			flag=1;
		}
		else
		{
			flag=0;
		}
		return SUCCESS;
	}

	public String deleteArea()
	{
		if(areaService.deleteAreaAndSub(area.getId()))
		{
			flag=1;
		}
		else
		{
			flag=0;
		}
		return SUCCESS;
	}

	public String queryById()
	{
		area=areaService.queryByPK(area.getId());
		return manuType;
	}

}
