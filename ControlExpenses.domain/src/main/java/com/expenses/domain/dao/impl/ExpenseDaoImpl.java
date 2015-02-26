package com.expenses.domain.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.expenses.domain.entities.Expense;
import com.expenses.domain.dao.IExpenseDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("expenseDao")
public class ExpenseDaoImpl extends GenericDaoImpl<Expense> implements IExpenseDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Expense> findExpensesByUserId(final Integer userId) {
		return (List<Expense>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Criteria criteria = session.createCriteria(Expense.class);
						criteria.addOrder(Order.asc("description"));
						criteria.createAlias("user", "user");
						criteria.add(Restrictions.eq("user.id",
								userId));
						return criteria.list();
					}
				});
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<Expense> getExpensesByDateRange(final Integer userId, final Date firstDate, final Date lastDate) {
        return (List<Expense>) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Criteria criteria = session.createCriteria(Expense.class);
                        criteria.addOrder(Order.asc("description"));
                        criteria.createAlias("user", "user");
                        criteria.add(Restrictions.eq("user.id", userId));
                        criteria.add(Restrictions.between("date", firstDate, lastDate));
                        return criteria.list();
                    }
                });
    }
}
