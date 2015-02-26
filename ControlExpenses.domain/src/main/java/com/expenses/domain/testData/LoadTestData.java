package com.expenses.domain.testData;

import com.expenses.commons.PasswordHashHelper;
import com.expenses.domain.entities.Expense;
import com.expenses.domain.entities.User;
import com.expenses.domain.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

public class LoadTestData {

	@Autowired
	private static GenericDao<User> userDAO;

	public static void setUserDAO(GenericDao<User> userDAO) {
		LoadTestData.userDAO = userDAO;
	}

	@Autowired
	private static GenericDao<Expense> expenseDAO;

	public static void setExpenseDAO(GenericDao<Expense> expenseDAO) {
		LoadTestData.expenseDAO = expenseDAO;
	}

    public static void main(String[] args) {
        LoadTestData bd = new LoadTestData();
        bd.load();

        System.out.println("load data: completed");
    }

	public LoadTestData() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:persistence-beans.xml");

		setUserDAO((GenericDao<User>) context.getBean("userDao"));
		setExpenseDAO((GenericDao<Expense>) context.getBean("expenseDao"));
	}

	public void load() {
		// Users
		User u1 = loadUser("andres@email.com", "Gomez Coronel", "Andrés");
		userDAO.save(u1);
		User u2 = loadUser("sebastina@email.com", "Reynoso", "Sebastián");
		userDAO.save(u2);
		User u3 = loadUser("florencia@email.com", "Castro", "Florencia");
		userDAO.save(u3);

		// Expenses
		Expense e1 = loadExpense();
        e1.setUser(u1);
        expenseDAO.save(e1);
        Expense e2 = loadExpense();
        e2.setUser(u1);
		expenseDAO.save(e2);
        Expense e3 = loadExpense();
        e3.setUser(u2);
		expenseDAO.save(e3);
        Expense e4 = loadExpense();
        e4.setUser(u2);
		expenseDAO.save(e4);
        Expense e5 = loadExpense();
        e5.setUser(u3);
		expenseDAO.save(e5);
        Expense e6 = loadExpense();
        e6.setUser(u3);
		expenseDAO.save(e6);
	}

	// ------------ METHODS
	public User loadUser(String email, String lastName, String name) {
        User u = new User();
        u.setEmail(email);
        u.setLastName(lastName);
        u.setName(name);

        //create hash password.
        String hashedPassword = null;
        try {
            hashedPassword = PasswordHashHelper.createHash("password");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        u.setPassword(hashedPassword);

        return u;
    }

    public Expense loadExpense(){
        Expense exp = new Expense();

        exp.setDescription("TV Service");
        exp.setComment("TV Invoice");
        exp.setDate(new Date());
//        exp.setTime(new Date());
        exp.setAmount(250.00);

        return exp;
    }
}
