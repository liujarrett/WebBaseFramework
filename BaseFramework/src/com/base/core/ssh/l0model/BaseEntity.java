package com.base.core.ssh.l0model;

import java.io.Serializable;

import com.google.gson.Gson;

/**
 * 类描述：通用抽象实体类，用于所有普通实体类公用信息的定义
 */
public abstract class BaseEntity implements Serializable
{

	private static final long serialVersionUID=-6890651634163617142L;

	// 备注
	protected String remarks;

	// 序号
	protected Integer orderNumber;

	// 当前状态
	protected String currentState;

	// 是否删除标记位
	protected String isDelete;

	// 创建时间
	protected String createTime;

	// 创建时间
	protected String updateTime;

	public BaseEntity()
	{

	}

	public String toJson()
	{
		Gson gson=new Gson();
		return gson.toJson(this);
	}

	public String getRemarks()
	{
		return remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks=remarks;
	}

	public Integer getOrderNumber()
	{
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber)
	{
		this.orderNumber=orderNumber;
	}

	public String getCurrentState()
	{
		return currentState;
	}

	public void setCurrentState(String currentState)
	{
		this.currentState=currentState;
	}

	public String getIsDelete()
	{
		return isDelete;
	}

	public void setIsDelete(String isDelete)
	{
		this.isDelete=isDelete;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime=createTime;
	}

	public String getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(String updateTime)
	{
		this.updateTime=updateTime;
	}

}
