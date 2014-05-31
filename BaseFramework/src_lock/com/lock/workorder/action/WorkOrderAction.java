package com.lock.workorder.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import com.base.core.page.PageBean;
import com.lock.workorder.WorkOrder;
import com.lock.workorder.service.WorkOrderService;
import com.opensymphony.xwork2.ActionSupport;

public class WorkOrderAction extends ActionSupport
{
	private static final long serialVersionUID=5331708500842070155L;
	private WorkOrderService workorderService;
	private WorkOrder workOrder;
	private PageBean<WorkOrder> pageBean;
	private Date startTime;
	private Date endTime;

	public WorkOrderService getWorkorderService()
	{
		return workorderService;
	}

	public void setWorkorderService(WorkOrderService workorderService)
	{
		this.workorderService=workorderService;
	}

	public WorkOrder getWorkOrder()
	{
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder)
	{
		this.workOrder=workOrder;
	}

	public PageBean<WorkOrder> getPageBean()
	{
		return pageBean;
	}

	public void setPageBean(PageBean<WorkOrder> pageBean)
	{
		this.pageBean=pageBean;
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime=startTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime=endTime;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	public String goTo()
	{
		return "goto";
	}

	public String queryForGrid()
	{
		try
		{
			workOrder.setOpenlockname(URLDecoder.decode(workOrder.getOpenlockname(),"utf-8"));
			workOrder.setAcceptStartTime(startTime);
			workOrder.setAcceptEndTime(endTime);

			workorderService.query(workOrder,pageBean);
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
