package org.dmk.exception;

public class ExceptionTest {
	public static void main(String[] args) {
		try {
			throw new NullPointerException();
			// } catch (Exception e) {
			// System.out.println("An ordinary exception was caught");
		} catch (RuntimeException e) { // this will fail to compile
			System.out.println("A runtime exception was caught");
		}
	}
}
