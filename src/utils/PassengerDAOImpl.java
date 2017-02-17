package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import utils.HibernateUtil;
import utils.PassengerDAO;
import classes.Passenger;

public class PassengerDAOImpl implements PassengerDAO {

	public void add(Passenger temp) throws SQLException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(temp);
			session.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public void update(Passenger temp) throws SQLException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.update(temp);
			session.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Passenger> getAll() throws SQLException {
		Session session = null;
		List<Passenger> temps = new ArrayList<Passenger>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			temps = session.createCriteria(Passenger.class).list();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return temps;
	}
	
	public Passenger getByID(int id) {
		Session session = null;
		String hql = "from Passenger where passengerid="+id;
		Passenger passenger = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			passenger = (Passenger) session.createQuery(hql).setMaxResults(1).uniqueResult();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return passenger;
	}

	public void delete(int stID) throws SQLException {
		Session session = null;
		String hql = "delete from Passenger where passengerid=" + stID;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.createQuery(hql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}