package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import classes.User;
import utils.HibernateUtil;


public class UserDAOImpl implements UserDAO {

	@SuppressWarnings("unchecked")
	public List<User> getAll() throws SQLException {
		Session session = null;
		List<User> temps = new ArrayList<User>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			temps = session.createCriteria(User.class).list();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return temps;
	}
	
	public User getByName(String name) {
		Session session = null;
		String hql = "from User user where username='" + name+"'";
		User user = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			user = (User) session.createQuery(hql).uniqueResult();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return user;
	}

}