package org.dmk.aop.spring;

import org.dmk.aop.spring.services.MyService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-aop-beans.xml");

		try {
			MyService myService = (MyService) applicationContext.getBean("myService");
			myService.doIt();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}
}