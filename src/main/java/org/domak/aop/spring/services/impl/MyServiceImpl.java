package org.domak.aop.spring.services.impl;

import java.sql.SQLException;

import org.domak.aop.spring.services.MyService;
import org.hibernate.JDBCException;

public class MyServiceImpl implements MyService {

	@Override
	public void doIt() {
		SQLException sqlException = new SQLException("FK violated");
		throw new JDBCException("test exception", sqlException);

	}

}
