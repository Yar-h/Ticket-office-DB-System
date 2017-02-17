package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import utils.HibernateUtil;
import utils.TransportDAO;
import classes.Transport;

public class TransportDAOImpl implements TransportDAO {

	public void add(Transport temp) throws SQLException {
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

	public void update(Transport temp) throws SQLException {
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
	public List<Transport> getAll() throws SQLException {
		Session session = null;
		List<Transport> temps = new ArrayList<Transport>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			temps = session.createCriteria(Transport.class).list();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return temps;
	}

	public Transport getByID(int id) {
		Session session = null;
		String hql = "from Transport where transid="+id;
		Transport transport = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transport = (Transport) session.createQuery(hql).setMaxResults(1).uniqueResult();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return transport;
	}
	
	public void delete(int stID) throws SQLException {
		Session session = null;
		String hql = "delete from Transport where transid=" + stID;
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