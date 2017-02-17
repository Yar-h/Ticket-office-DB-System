package utils;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

	private static SessionFactory sessionFactory = createSessionFactory();
	private static ServiceRegistry serviceRegistry;

	public static SessionFactory createSessionFactory() {
		if (sessionFactory != null)
			return sessionFactory;
		Properties prop = new Properties();
		prop.setProperty("hibernate.connection.url",
				"jdbc:postgresql://localhost:5432/postgres");
		prop.setProperty("hibernate.connection.username", "postgres");
		prop.setProperty("hibernate.connection.password", "1234");
		prop.setProperty("hibernate.connection.driver_class",
				"org.postgresql.Driver");
		//prop.setProperty("hibernate.hbm2ddl.auto", "create");

		Configuration configuration = new Configuration();
		configuration.setProperties(prop)
				.addAnnotatedClass(classes.Passenger.class)
				.addAnnotatedClass(classes.Station.class)
				.addAnnotatedClass(classes.Ticket.class)
				.addAnnotatedClass(classes.Trip.class)
				.addAnnotatedClass(classes.Transport.class)
				.addAnnotatedClass(classes.User.class);
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
				configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}