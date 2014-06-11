package com.base.core.ssh.l2service;

import java.io.Serializable;

import com.base.core.ssh.l0model.BaseEntity;
import com.base.core.ssh.l3dao.BaseDao;

import org.springframework.dao.DataAccessException;

public class BaseServiceImp<T extends BaseEntity,PK extends Serializable> implements BaseService<T,PK>
{
	private static final long serialVersionUID=6114565816769264364L;

	private BaseDao<T,PK> baseDao;

	public BaseDao<T,PK> getBaseDao()
	{
		return baseDao;
	}

	public void setBaseDao(BaseDao<T,PK> baseDao)
	{
		this.baseDao=baseDao;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	@Override
	public T queryByPK(PK pk)
	{
		T t=null;
		try
		{
			t=baseDao.select(pk);
		}
		catch(Exception e)
		{
			t=null;
			e.printStackTrace();
		}
		return t;
	}

	@Override
	public Serializable insert(T entity)
	{
		try
		{
			return baseDao.insert(entity);
		}
		catch(DataAccessException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean update(T entity)
	{
		boolean flag=true;
		try
		{
			baseDao.update(entity);
		}
		catch(DataAccessException e)
		{
			flag=false;
			e.printStackTrace();
		}
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
		}
		return flag;

	}

	@Override
	public boolean delete(PK id)
	{
		boolean flag=true;
		try
		{
			baseDao.delete(id);
		}
		catch(DataAccessException e)
		{
			flag=false;
			e.printStackTrace();
		}
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean delete(T entity)
	{
		boolean flag=true;
		try
		{
			baseDao.delete(entity);
		}
		catch(DataAccessException e)
		{
			flag=false;
			e.printStackTrace();
		}
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public int delete(String where)
	{
		try
		{
			return baseDao.delete(where);
		}
		catch(DataAccessException e)
		{
			e.printStackTrace();
			return -1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean deleteForLogic(PK id)
	{
		boolean flag=true;
		try
		{
			baseDao.deleteForLogic(id);
		}
		catch(DataAccessException e)
		{
			flag=false;
			e.printStackTrace();
		}
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deleteForLogic(T entity)
	{
		boolean flag=true;
		try
		{
			baseDao.deleteForLogic(entity);
		}
		catch(DataAccessException e)
		{
			flag=false;
			e.printStackTrace();
		}
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public int deleteForLogic(String where)
	{
		try
		{
			return baseDao.delete(where);
		}
		catch(DataAccessException e)
		{
			e.printStackTrace();
			return -1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
}
