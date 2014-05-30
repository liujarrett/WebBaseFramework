/**
 * 文件名：BaseService.java
 *
 * 版本信息：
 * 日期：2011-3-9
 * Copyright 黑龙江中科方德软件有限公司 2011
 *
 */
package com.base.core.ssh.l2service;

import java.io.Serializable;

import com.base.core.ssh.l0model.BaseEntity;

/**
 * 类名称：BaseService 类描述：基本服务接口，完成CRUD操作
 */
public interface BaseService<T extends BaseEntity,PK extends Serializable> extends Serializable
{

	/**
	 * 根据主键查询实体
	 * 
	 * @param pk
	 * @return
	 */
	T queryByPK(PK pk);

	/**
	 * 保存数据记录,更具id是否为空来判断是否插入新记录还是更新操作
	 * 
	 * @param newObject
	 */
	Serializable insert(T entity);

	/**
	 * 更新数据记录
	 * 
	 * @param Object
	 */
	boolean update(T entity);

	/**
	 * 根据标识物理删除记录
	 * 
	 * @param id
	 */
	boolean delete(PK id);

	/**
	 * 根据实体物理删除记录
	 * 
	 * @param entity 实体Bean
	 */
	boolean delete(T entity);
	
	/**
	 * 根据where物理删除记录
	 */
	int delete(String where);
	
	/**
	 * 根据实体逻辑删除记录
	 * 
	 * @param id
	 */
	boolean deleteForLogic(PK id);

	/**
	 * 根据实体逻辑删除记录
	 * 
	 * @param entity 实体Bean
	 */
	boolean deleteForLogic(T entity);
	
	/**
	 * 根据where物理删除记录
	 */
	int deleteForLogic(String where);

}
