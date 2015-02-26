package com.expenses.domain.dao.impl;

import com.expenses.domain.dao.GenericDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Andres
 * 
 */
@Repository("genericDao")
public class GenericDaoImpl<T> extends HibernateDaoSupport implements
		GenericDao<T> {

	@Autowired(required = true)
	public void anyMethodName(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	// Methods to find.
	@SuppressWarnings("unchecked")
	public T findById(final Serializable id,
			final Class<? extends T> persistentType) {
		// getHibernateTemplate().setAlwaysUseNewSession(useNewSession);
		return (T) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Object result = session.get(persistentType, id);
				if (result != null) {
					getHibernateTemplate().evict(result);
				}
				return result;
			}
		});
	}


	@SuppressWarnings("unchecked")
	public List<T> findAll(final Class clazz) {
		return (List<T>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(clazz);
						return criteria.list();
					}
				});
	}

	// Methods to persist data.

	public void delete(T object) {
		getHibernateTemplate().delete(object);
	}

	public void save(T object) {
		getHibernateTemplate().save(object);
	}

	public void update(T object) {
		getHibernateTemplate().update(object);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllWithId(final String fieldName,
			final Integer fieldValue, final Class clazz) {
		return (List<T>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(clazz);
						criteria.add(Restrictions.like(fieldName, fieldValue));
						return criteria.list();
					}
				});
	}
}
