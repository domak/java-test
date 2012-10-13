package org.dmk.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

/**
 * Hello world!
 */
public class App {
	public static void main(String[] args) {
		Session session = HibernateUtils.getSession();
		for (int i = 0; i < 10; i++) {
			session.persist(new Parent("parent " + i));
			session.persist(new Child("child " + i));
		}
		session.flush();
		session.clear();
		for (int i = 0; i < 10; i++) {
			Parent loadedParent = (Parent) session.get(Parent.class, i);
			System.out.println(loadedParent);
			Child loadedchild = (Child) session.get(Child.class, i);
			System.out.println(loadedchild);
		}

		List<?> counter = session.createSQLQuery("select * from ID_GEN").list();
		for (Iterator<?> iterator = counter.iterator(); iterator.hasNext();) {
			System.out.println(iterator.next());

		}

	}
}
