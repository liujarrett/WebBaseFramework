package com.base.web.log;

import com.base.core.ssh.l0model.BaseEntity;
import com.base.web.user.User;

public class OperateLog extends BaseEntity
{

	private static final long serialVersionUID=-6875105270534817243L;
	private long id;
	private User user;
	private String clientIp;
	private String clientType;//[1 手机客户端;2 web浏览器]
	private String operateType;//[1 增加数据;2 删除数据;3 更新数据]
	private String operateTime;
	private String operateFunction;
	private String operateContents;
	private String tableName;
	private int affectedRows;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id=id;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user=user;
	}

	public String getClientIp()
	{
		return clientIp;
	}

	public void setClientIp(String clientIp)
	{
		this.clientIp=clientIp;
	}

	public String getClientType()
	{
		return clientType;
	}

	public void setClientType(String clientType)
	{
		this.clientType=clientType;
	}

	public String getOperateType()
	{
		return operateType;
	}

	public void setOperateType(String operateType)
	{
		this.operateType=operateType;
	}

	public String getOperateTime()
	{
		return operateTime;
	}

	public void setOperateTime(String operateTime)
	{
		this.operateTime=operateTime;
	}

	public String getOperateFunction()
	{
		return operateFunction;
	}

	public void setOperateFunction(String operateFunction)
	{
		this.operateFunction=operateFunction;
	}

	public String getOperateContents()
	{
		return operateContents;
	}

	public void setOperateContents(String operateContents)
	{
		this.operateContents=operateContents;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName=tableName;
	}

	public int getAffectedRows()
	{
		return affectedRows;
	}

	public void setAffectedRows(int affectedRows)
	{
		this.affectedRows=affectedRows;
	}

	// ***********************下面的字段在数据库没有对应列，只是为了查询时，传递参数方便**************************
	private String searchStartTime;
	private String searchEndTime;

	public String getSearchStartTime()
	{
		return searchStartTime;
	}

	public void setSearchStartTime(String searchStartTime)
	{
		this.searchStartTime=searchStartTime;
	}

	public String getSearchEndTime()
	{
		return searchEndTime;
	}

	public void setSearchEndTime(String searchEndTime)
	{
		this.searchEndTime=searchEndTime;
	}

}
