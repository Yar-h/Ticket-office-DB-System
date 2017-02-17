package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import utils.HibernateUtil;
import utils.TicketDAO;
import classes.Ticket;

public class TicketDAOImpl implements TicketDAO {

	public void add(Ticket temp) throws SQLException {
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

	public void update(Ticket temp) throws SQLException {
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
	public List<Ticket> getAll() throws SQLException {
		Session session = null;
		List<Ticket> temps = new ArrayList<Ticket>();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			temps = session.createCriteria(Ticket.class).list();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return temps;
	}

	public void delete(int stID) throws SQLException {
		Session session = null;
		String hql = "delete from Ticket where ticketid=" + stID;
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
	public Ticket getByID(int id) {
		Session session = null;
		String hql = "from Ticket where ticketid="+id;
		Ticket ticket = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			ticket = (Ticket) session.createQuery(hql).setMaxResults(1).uniqueResult();
		} catch (Exception e) {
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return ticket;
	}
}