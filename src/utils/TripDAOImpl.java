package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import utils.HibernateUtil;
import utils.TripDAO;
import classes.Trip;

public class TripDAOImpl implements TripDAO {

	public void add(Trip temp) throws SQLException {
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

	public void update(Trip temp) throws SQLException {
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
	public List<Trip> getAll() throws SQLException {
		Session session = null;
		List<Trip> temps = new ArrayList<Trip>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			temps = session.createCriteria(Trip.class).list();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return temps;
	}
	
	public Trip getByID(int id) {
		Session session = null;
		String hql = "from Trip where tripid="+id;
		Trip trip = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			trip = (Trip) session.createQuery(hql).setMaxResults(1).uniqueResult();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return trip;
	}


	public void delete(int stID) throws SQLException {
		Session session = null;
		String hql = "delete from Trip where tripid=" + stID;
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