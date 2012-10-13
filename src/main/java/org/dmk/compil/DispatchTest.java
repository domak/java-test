package org.dmk.compil;

public class DispatchTest {

	public static void main(String[] args) {
		B b = new B();

		DispatchTest test = new DispatchTest();
		test.process(b);
	}

	public void process(B b) {
		System.out.println("process B - arg: " + b);
	}

	public void process(C c) {
		System.out.println("process C - arg: " + c);
	}

	public abstract static class A {
		// void
	}

	public static class B extends A {
		// void
	}

	public static class C extends A {
		// void
	}

}
