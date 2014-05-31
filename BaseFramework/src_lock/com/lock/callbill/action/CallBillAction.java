package com.lock.callbill.action;

import java.util.Date;

import com.base.core.page.PageBean;
import com.lock.callbill.Callbill;
import com.lock.callbill.service.CallbillService;
import com.opensymphony.xwork2.ActionSupport;

public class CallBillAction extends ActionSupport
{

	private static final long serialVersionUID=5331708500842070155L;
	private CallbillService callbillService;
	private Callbill callbill;
	private PageBean<Callbill> pageBean;
	private Date hangontime;
	private Date hangofftime;

	public CallbillService getCallbillService()
	{
		return callbillService;
	}

	public void setCallbillService(CallbillService callbillService)
	{
		this.callbillService=callbillService;
	}

	public Callbill getCallbill()
	{
		return callbill;
	}

	public void setCallbill(Callbill callbill)
	{
		this.callbill=callbill;
	}

	public PageBean<Callbill> getPageBean()
	{
		return pageBean;
	}

	public void setPageBean(PageBean<Callbill> pageBean)
	{
		this.pageBean=pageBean;
	}

	public Date getHangontime()
	{
		return hangontime;
	}

	public void setHangontime(Date hangontime)
	{
		this.hangontime=hangontime;
	}

	public Date getHangofftime()
	{
		return hangofftime;
	}

	public void setHangofftime(Date hangofftime)
	{
		this.hangofftime=hangofftime;
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
		callbill.setHangontime(hangontime);
		callbill.setHangofftime(hangofftime);
		callbillService.query(callbill,pageBean);
		return SUCCESS;
	}

}
