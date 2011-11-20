package org.domak.aop.proxy;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import com.google.common.collect.Lists;

public class ProxyTest {

	public static void main(String[] args) {
		ClassToProxify target = new ClassToProxify();
		ClassToProxify proxy = createJavassistProxy(target);
		proxy.m1("arg1");
		proxy.m2("arg1");
	}

	// -------------------------------------------------------------------------
	// inner class
	// -------------------------------------------------------------------------

	/**
	 * The class to proxify.
	 */
	public static class ClassToProxify {
		public void m1(String arg) {
			System.out.println("m1 called - arg: " + arg);
		}

		public void m2(String arg) {
			System.out.println("m2 called - arg: " + arg);
		}
	}

	/**
	 * Creates a proxy on concrete class
	 */
	private static ClassToProxify createJavassistProxy(ClassToProxify target) {
		try {
			ProxyFactory proxyFactory = new ProxyFactory();
			proxyFactory.setSuperclass(ClassToProxify.class);

			Class<?> proxyClass = proxyFactory.createClass();
			ClassToProxify proxy = (ClassToProxify) proxyClass.newInstance();
			((ProxyObject) proxy).setHandler(new JavassistMethodInterceptor(target));
			return proxy;

		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		}
	}

	// -------------------------------------------------------------------------
	// inner class
	// -------------------------------------------------------------------------

	private static class JavassistMethodInterceptor implements MethodHandler {

		private ClassToProxify delegate;

		public JavassistMethodInterceptor(ClassToProxify delegate) {
			this.delegate = delegate;
		}

		@Override
		public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
			System.out.println("intercept - thisMethod: " + thisMethod + " - proceed: " + proceed + "- args: "
					+ Lists.newArrayList(args));

			if (thisMethod.getName() == "m1") {
				System.out.println("no delegation for " + thisMethod.getName());
				return null;
			}

			return thisMethod.invoke(delegate, args);
		}
	}

}