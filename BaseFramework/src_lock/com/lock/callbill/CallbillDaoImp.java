package com.lock.callbill;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CallbillDaoImp extends HibernateDaoSupport implements CallbillDao {

	@SuppressWarnings("unchecked")
	public List<Callbill> query(String sql, int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(sql);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return (List<Callbill>) query.list();
	}

	@SuppressWarnings("unchecked")
	public int count(String sql) throws DataAccessException {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		Query query = session.createQuery(sql);
		return ((List<Long>) query.list()).get(0).intValue();
	}
}
