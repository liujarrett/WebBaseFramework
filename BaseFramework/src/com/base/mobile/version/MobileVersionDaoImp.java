package com.base.mobile.version;

import java.util.List;

import com.base.core.ssh.l3dao.BaseDaoImp;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

public class MobileVersionDaoImp extends BaseDaoImp<MobileVersion,Long> implements MobileVersionDao
{

	public MobileVersionDaoImp()
	{
		super(MobileVersion.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MobileVersion> selectAndroidVersion() throws DataAccessException
	{
		String hql="FROM "+MobileVersion.class.getName()+" WHERE mobile = 'Android' ORDER BY versionCode DESC ";
		//
		Session session=getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query=session.createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(1);
		return query.list();
	}
}
