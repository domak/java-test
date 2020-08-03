package org.dmk.hibernate;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtils {

	private static final SessionFactory sessionFactory;

	static {
		BasicConfigurator.configure();

		try {
			sessionFactory = new AnnotationConfiguration().addAnnotatedClass(Master.class)
					.addAnnotatedClass(Detail.class).addAnnotatedClass(Parent.class).addAnnotatedClass(Child.class)
					.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
					.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver")
					.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:baseball")
					.setProperty("hibernate.connection.username", "sa")
					.setProperty("hibernate.connection.password", "")
					.setProperty("hibernate.connection.pool_size", "1")
					.setProperty("hibernate.connection.autocommit", "true")
					.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider")
					.setProperty("hibernate.hbm2ddl.auto", "create-drop").setProperty("hibernate.show_sql", "true")
					.setProperty("org.hibernate", "debug").buildSessionFactory();
		} catch (Throwable ex) {
			// Log exception!
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Session getSession() throws HibernateException {
		return sessionFactory.openSession();
	}
}
