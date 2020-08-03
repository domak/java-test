package org.dmk.aop.spring;

public class ExceptionTranslator {

	public void translate(Exception exception) {
		throw new RuntimeException("catched!!!!!! class:" + exception.getClass() + " - message:"
				+ exception.getMessage());
	}

}
