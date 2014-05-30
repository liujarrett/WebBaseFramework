package com.lock.workorder;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class WorkOrderImp extends HibernateDaoSupport implements WorkOrderDao {

	@SuppressWarnings("unchecked")
	public List<WorkOrder> query(String sql, int pageNo, int pageSize) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(sql);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return (List<WorkOrder>) query.list();
	}

	@SuppressWarnings("unchecked")
	public int count(String sql) throws DataAccessException {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery(sql);
		return ((List<Long>) query.list()).get(0).intValue();
	}

}
