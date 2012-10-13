package org.dmk.reflection;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionMain {

	public static void main(String[] args) throws Exception {
		ReflectionMain reflectionMain = new ReflectionMain();
		Thread thread = new Thread(new TimeLog(reflectionMain));
		thread.start();
		reflectionMain.test();
	}

	static class TimeLog implements Runnable {

		private ReflectionMain reflectionMain;

		public TimeLog(ReflectionMain reflectionMain) {
			this.reflectionMain = reflectionMain;
		}

		@Override
		public void run() {
			while (reflectionMain.testCount != reflectionMain.maxTestCount) {
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(reflectionMain.testCount);

			}

		}

	}

	// -------------------------------------------------------------------------

	long maxTestCount = 3;
	long maxCount = 65000;
	long testCount = 0;

	public void test() throws Exception {
		long staticTimeCall = 0;
		long dynamicTimeCall = 0;

		File file = new File("results.csv");
		file.createNewFile();
		FileOutputStream outputStream = new FileOutputStream(file);

		for (testCount = 0; testCount < maxCount; testCount++) {

			for (int i = 0; i < maxTestCount; i++) {
				staticTimeCall += staticCall(testCount);
				dynamicTimeCall += dynamicCall(testCount);
			}

			staticTimeCall = staticTimeCall / maxTestCount;
			dynamicTimeCall = dynamicTimeCall / maxTestCount;

			String result = (testCount + 1) + "," + staticTimeCall + "," + dynamicTimeCall + "\n";
			outputStream.write(result.getBytes());
		}
		System.out.println("end of test");
	}

	private long staticCall(long newMaxCount) {
		long start = System.currentTimeMillis();
		TestBean bean = new TestBean();
		String lastResult = null;
		for (long i = 0; i < newMaxCount; i++) {
			bean.setLongValue(i);
			bean.setStringValue("toto" + i);
			lastResult = bean.getStringValue() + bean.getLongValue();

		}
		assert lastResult != null; // just to be sure that compilo will not optimize the code by removing the call
		return System.currentTimeMillis() - start;
	}

	private long dynamicCall(long newMaxCount) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		long start = System.currentTimeMillis();
		String lastResult = null;
		TestBean bean = new TestBean();

		Method setLongValue = TestBean.class.getMethod("setLongValue", long.class);
		Method setStringValue = TestBean.class.getMethod("setStringValue", String.class);
		Method getLongValue = TestBean.class.getMethod("getLongValue");
		Method getStringValue = TestBean.class.getMethod("getStringValue");

		for (long i = 0; i < newMaxCount; i++) {

			setLongValue.invoke(bean, i);
			setStringValue.invoke(bean, "toto" + i);
			lastResult = (String) getStringValue.invoke(bean) + getLongValue.invoke(bean);
		}

		assert lastResult != null; // just to be sure that compilo will not optimize the code by removing the call
		return System.currentTimeMillis() - start;
	}
}
