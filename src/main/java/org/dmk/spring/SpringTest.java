package org.dmk.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by domak on 01/02/15.
 */
public class SpringTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-test.xml");
        SpringTestService serviceByClass = applicationContext.getBean(SpringTestService.class);
        System.out.println(serviceByClass.getMessage());

        SpringTestService serviceByName = (SpringTestService) applicationContext.getBean("springTestService");
        System.out.println(serviceByName.getMessage());

        System.out.println(serviceByClass == serviceByName);
    }
}
