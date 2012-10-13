package org.dmk.concurrency;

public class DeadLockTest {

	private Object lock1 = new Object();
	private Object lock2 = new Object();

	private void logLock(int lockId, boolean acquire) {
		System.out.println(Thread.currentThread().getName() + " -> lock" + lockId
				+ (acquire ? " acquired" : " released"));
	}

	public void acquireLock1Lock2() {
		synchronized (lock1) {
			logLock(1, true);
			try {
				Thread.sleep(3000);
				synchronized (lock2) {
					logLock(2, true);
					Thread.sleep(3000);
					logLock(2, false);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logLock(1, false);
		}
	}

	public void acquireLock2Lock1() {
		synchronized (lock2) {
			logLock(2, true);
			try {
				Thread.sleep(1000);
				synchronized (lock1) {
					logLock(1, true);
					Thread.sleep(3000);
					logLock(1, false);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logLock(2, false);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final DeadLockTest deadLockTest = new DeadLockTest();
		Thread thread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				deadLockTest.acquireLock1Lock2();
			}
		}, "thread1");
		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				deadLockTest.acquireLock2Lock1();
			}
		}, "thread2");

		thread1.start();
		thread2.start();

		System.out.println(Thread.currentThread().getName() + " -> end of main");

	}
}
