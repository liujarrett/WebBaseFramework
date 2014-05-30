package com.base.core.ssh.l3dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.base.core.page.PageBean;
import com.base.core.ssh.l0model.BaseEntity;

/**
 * 类名称：BaseDao 类描述：所有dao的父接口,用于实现通常的数据库访问,主要运用了方法参数化类型
 */
public interface BaseDao<T extends BaseEntity, PK extends Serializable> {
	/**
	 * 从数据库查询满足条件的对象,这里我们不处理找不到结果的情况,应为存在业务要求数据库中是否已有这条记录
	 */
	T select(PK id) throws DataAccessException;

	/**
	 * 返回某类对象在数据库中的记录数,分页用的比较多
	 */
	int count(String where) throws DataAccessException;

	/**
	 * 返回数据库中此类记录的所有数据,按id排序
	 */
	List<T> select(String where) throws DataAccessException;

	/**
	 * 返回数据库中此类记录的数据,满足查询边界.主要用在iBatis上
	 * 
	 * @param skip
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<T> select(String where,int skip, int max) throws DataAccessException;

	/**
	 * 
	 * 根据当前页码和每页的记录数获取记录列表 selectByPage
	 * 
	 * @param name
	 *            filterMap 查询条件Map
	 * @param name
	 *            pageSize 每页记录数
	 * @param name
	 *            currentPage 当前第几页
	 */
	PageBean<T> selectByPage(String where, int pageSize,int currentPage) throws DataAccessException;

	/**
	 * 保存数据记录,更具id是否为空来判断是否插入新记录还是更新操作
	 * 
	 * @param newObject
	 */
	Serializable insert(T entity) throws DataAccessException;

	/**
	 * 更新数据记录
	 * 
	 * @param Object
	 */
	void update(T entity) throws DataAccessException;

	/**
	 * 根据标识物理删除记录
	 * 
	 * @param id
	 */
	void delete(PK id) throws DataAccessException;

	/**
	 * 根据实体物理删除记录
	 * 
	 * @param entity
	 *            实体Bean
	 */
	void delete(T entity) throws DataAccessException;
	
	/**
	 * 根据where物理删除记录
	 */
	int delete(String where) throws DataAccessException;

	/**
	 * 根据实体逻辑删除记录
	 * 
	 * @param id
	 */
	void deleteForLogic(PK id) throws DataAccessException;

	/**
	 * 根据实体逻辑删除记录
	 * 
	 * @param entity
	 *            实体Bean
	 */
	void deleteForLogic(T entity) throws DataAccessException;
	
	/**
	 * 根据where物理删除记录
	 */
	int deleteForLogic(String where) throws DataAccessException;
	

}
