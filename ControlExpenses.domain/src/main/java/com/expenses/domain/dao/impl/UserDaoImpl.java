package com.expenses.domain.dao.impl;

import java.sql.SQLException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.expenses.domain.entities.User;
import com.expenses.domain.dao.IUserDao;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User> implements IUserDao {

	@Override
	public User userExist(final Class clazz, final String userEmail) {
        return (User) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(clazz);
                criteria.add(Restrictions.eq("email", userEmail));
                return criteria.uniqueResult();
            }
        });
    }

    @Override
    public String getPasswordByUserName(final Class clazz, final String userName){
        return (String) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(clazz);
                criteria.add(Restrictions.eq("email", userName));
                criteria.setProjection(Projections.property("password"));
                return criteria.uniqueResult();
            }
        });
    }

    @Override
    public User getUserByUserName(final Class clazz, final String userName){
        return (User) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(clazz);
                criteria.add(Restrictions.eq("email", userName));
                return criteria.uniqueResult();
            }
        });
    }

}
