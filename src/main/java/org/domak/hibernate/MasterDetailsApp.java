package org.domak.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;

public class MasterDetailsApp {
	public static void main(String[] args) {

		Logger logger = Logger.getLogger(MasterDetailsApp.class.getName());

		Session session = HibernateUtils.getSession();

		Master master = new Master();
		master.setName("master");

		List<Detail> details = new ArrayList<Detail>();
		for (int i = 0; i < 10; i++) {
			Detail detail = new Detail("detail" + i);
			details.add(detail);
			detail.setMaster(master);
		}

		master.setDetails(details);

		Serializable id = session.save(master);
		System.out.println("id:" + id);

		session.flush();
		session.clear();

		master = (Master) session.get(Master.class, id);
		logger.info("details size:" + master.getDetails().size());

		master.getDetails().clear();
		// session.flush();
		Detail detail = new Detail("detail3");
		detail.setMaster(master);
		master.getDetails().add(detail);

		session.update(master);

		session.flush();
		session.clear();

		master = (Master) session.get(Master.class, id);

		session.flush();

	}
}
