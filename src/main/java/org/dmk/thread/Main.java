package org.dmk.thread;

public class Main {

	public static void main(String[] args) {

		TestDao dao = new TestDao();
		TestService service = new TestService();
		service.setTestDao(dao);

		Thread thread1 = new Thread(new Servant(service, "test1"));
		Thread thread2 = new Thread(new Servant(service, "test2"));
		thread1.start();
		thread2.start();

	}

	private static class Servant implements Runnable {

		private TestService service;
		private String arg;

		public Servant(TestService service, String arg) {
			this.service = service;
			this.arg = arg;
		}

		@Override
		public void run() {
			service.process(arg);
		}

	}
}
