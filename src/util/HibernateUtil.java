package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

//logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import model.Usuario;


public class HibernateUtil {
	private static SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory()
	{
		try
		{
			if (sessionFactory == null)
			{
				//System.out.println(HibernateUtil.class.getResource("/hibernate.cfg.xml"));
				Configuration configuration = new Configuration().configure("/hibernate.cfg.xml");
				StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
				serviceRegistryBuilder.applySettings(configuration.getProperties());
				ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			}
			return sessionFactory;
		} catch (Throwable ex)
		{
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	public static void shutdown()
	{
		getSessionFactory().close();
	}

	public static Session getSession() {
		if (sessionFactory == null){
			HibernateUtil.buildSessionFactory();
		}
		Session session = sessionFactory.openSession();
		return session;
	}
	
	public static boolean isDbConnected(Connection con) {
		try {
			if (con != null && !con.isClosed()) {
				return true;
			}
		} catch (SQLException e) {
			return false;
		}
		return false;
	}
}
