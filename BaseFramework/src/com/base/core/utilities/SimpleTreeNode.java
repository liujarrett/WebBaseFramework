/**
 * 文件名：SimpleTreeNode.java
 *
 * 版本信息：
 * 日期：2011-3-22
 * Copyright 黑龙江中科方德软件有限公司 2011
 *
 */
package com.base.core.utilities;

import java.util.HashMap;

/**
 * 基本树实体类
 */
public class SimpleTreeNode
{
	private Object id;
	private String text;
	private Object parentId;

	/**
	 * 仅用于需要异步读取数据的文件夹节点。当节点有子节点时，该属性不起作用。节点永远为文件夹节点
	 */
	private boolean isfolder=false;
	private HashMap<String,Object> attrs=new HashMap<String,Object>();

	public SimpleTreeNode(Object id,String text,Object parentId)
	{
		this.id=id;
		this.text=text;
		this.parentId=parentId;
	}

	public SimpleTreeNode(Object id,String text,Object parentId,boolean isFolder)
	{
		this.id=id;
		this.text=text;
		this.parentId=parentId;
		this.isfolder=isFolder;
	}

	public SimpleTreeNode(Object id,String text,Object parentId,boolean isFolder,HashMap<String,Object> attrs)
	{
		this.id=id;
		this.text=text;
		this.parentId=parentId;
		this.isfolder=isFolder;
		this.attrs=attrs;
	}

	public void addAttribute(String key,Object value)
	{

		this.attrs.put(key,value);
	}

	public Object getId()
	{
		return id;
	}

	public void setId(Object id)
	{
		this.id=id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text=text;
	}

	public Object getParentId()
	{
		return parentId;
	}

	public void setParentId(Object parentId)
	{
		this.parentId=parentId;
	}

	public HashMap<String,Object> getAttrs()
	{
		return attrs;
	}

	public void setAttrs(HashMap<String,Object> attrs)
	{
		this.attrs=attrs;
	}

	public boolean isIsfolder()
	{
		return isfolder;
	}

	public void setIsfolder(boolean isfolder)
	{
		this.isfolder=isfolder;
	}
}
