package com.base.core.ssh.l3dao;

import java.io.Serializable;
import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l0model.BaseEntity;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 类名称：BaseDaoImpl 类描述：基本Hibernate实现类
 */
public class BaseDaoImp<T extends BaseEntity,PK extends Serializable> extends HibernateDaoSupport implements BaseDao<T,PK>
{

	private Class<T> clazz;
	private String baseHQL=null;
	private String baseHQL_Count=null;

	public BaseDaoImp(Class<T> clazz)
	{
		this.clazz=clazz;
		baseHQL="FROM "+clazz.getName();
		baseHQL_Count="SELECT count(*) FROM "+clazz.getName();
	}

	@Override
	public T select(PK id) throws DataAccessException
	{
		return (T)getHibernateTemplate().get(clazz,id);
	}

	@Override
	public int count(String where) throws DataAccessException
	{
		String hql=baseHQL_Count;
		if(where!=null&&!where.equals(""))
		{
			hql+=" "+where;
		}
		return ((Long)getHibernateTemplate().find(hql).get(0)).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> select(String where) throws DataAccessException
	{
		String hql=baseHQL;
		if(where!=null&&!where.equals(""))
		{
			hql+=" "+where;
		}
		return getHibernateTemplate().find(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> select(String where,int skip,int max) throws DataAccessException
	{
		Session session=getHibernateTemplate().getSessionFactory().getCurrentSession();
		String hql=baseHQL;
		if(where!=null&&!where.equals(""))
		{
			hql+=" "+where;
		}
		Query query=session.createQuery(hql);
		query.setFirstResult(skip);
		query.setMaxResults(max);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageBean<T> selectByPage(String where,int pageSize,int currentPage) throws DataAccessException
	{
		PageBean<T> pageBean=new PageBean<T>();
		Session session=getHibernateTemplate().getSessionFactory().getCurrentSession();
		String hql=baseHQL;
		if(where!=null&&!where.equals(""))
		{
			hql+=" "+where;
		}
		Query query=session.createQuery(hql);
		query.setFirstResult((currentPage-1)*pageSize);
		query.setMaxResults(pageSize);
		List<T> beans=query.list();

		//设置pageBean的相关属性
		pageBean.setList(beans);
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		int rowCount=count(where);
		pageBean.setAllRow(rowCount);
		int totalPages=(rowCount+pageSize-1)/pageSize;
		pageBean.setTotalPage(totalPages);
		pageBean.setFirstPage(currentPage==1);
		pageBean.setLastPage(currentPage==totalPages);
		pageBean.setHasNextPage(currentPage!=totalPages);
		pageBean.setHasPreviousPage(currentPage>1);

		return pageBean;
	}

	@Override
	public Serializable insert(T entity) throws DataAccessException
	{
		return getHibernateTemplate().save(entity);
	}

	@Override
	public void update(T entity) throws DataAccessException
	{
		getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(PK id) throws DataAccessException
	{
		getHibernateTemplate().delete(getHibernateTemplate().get(clazz,id));
	}

	@Override
	public void delete(T entity) throws DataAccessException
	{
		getHibernateTemplate().delete(entity);
	}
	
	@Override
	public int delete(String where) throws DataAccessException
	{
		String hql="DELETE "+baseHQL;
		if(where!=null&&!where.equals(""))
		{
			hql+=" "+where;
		}
		return getHibernateTemplate().bulkUpdate(hql);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteForLogic(PK id) throws DataAccessException
	{
		Session session=getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		T obj=(T)session.get(clazz,id);
		obj.setIsDelete("1");
		session.getTransaction().commit();
	}

	@Override
	public void deleteForLogic(T entity) throws DataAccessException
	{
		entity.setIsDelete("1");
		getHibernateTemplate().update(entity);
	}
	
	@Override
	public int deleteForLogic(String where) throws DataAccessException
	{
		String hql="UPDATE "+clazz.getName()+" SET isDelete=1";
		if(where!=null&&!where.equals(""))
		{
			hql+=" "+where;
		}
		return getHibernateTemplate().bulkUpdate(hql);
	}

}
