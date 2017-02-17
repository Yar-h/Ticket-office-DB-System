package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import utils.HibernateUtil;
import utils.StationDAO;
import classes.Station;

public class StationDAOImpl implements StationDAO {

	public void add(Station temp) throws SQLException {
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

	public void update(Station temp) throws SQLException {
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
	public List<Station> getAll() throws SQLException {
		Session session = null;
		List<Station> temps = new ArrayList<Station>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			temps = session.createCriteria(Station.class).list();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return temps;
	}
	
	public Station getByID(int id) {
		Session session = null;
		String hql = "from Station where stationid="+id;
		Station station = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			station = (Station) session.createQuery(hql).setMaxResults(1).uniqueResult();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return station;
	}

	public void delete(int stID) throws SQLException {
		Session session = null;
		String hql = "delete from Station where stationid=" + stID;
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