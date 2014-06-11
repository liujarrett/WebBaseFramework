package com.base.web.area.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseService;
import com.base.web.area.Area;

public interface AreaService extends BaseService<Area,Long>
{
	// 通过区域父ID查询子区域
	List<Area> queryByParentId(long areaParentId);

	// 通过区域父ID查询所有子区域（分页）
	public PageBean<Area> queryAllByParentId(long areaParentId,int currentPage,int pageSize);

	// 通过区域父ID查询所有子区域（分页）
	public List<Area> queryAllByParentId(long areaParentId);

	int isExist(Area area);

	void batchUpdate(Area area,Area...others);

	// 删除区域，并且删除子区域
	public boolean deleteAreaAndSub(long areaId);
}
