package org.domak.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockTestWithConcurrencyLock {

	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();

	private void logLock(int lockId, boolean acquire) {
		System.out.println(Thread.currentThread().getName() + " -> lock" + lockId
				+ (acquire ? " acquired" : " released"));
	}

	public void acquireLock1Lock2() {
		lock1.lock();
		logLock(1, true);
		try {
			Thread.sleep(3000);
			lock2.lock();
			logLock(2, true);
			Thread.sleep(3000);
			lock2.unlock();
			logLock(2, false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lock1.unlock();
		logLock(1, false);
	}

	public void acquireLock2Lock1() {
		lock2.lock();
		logLock(2, true);
		try {
			Thread.sleep(1000);
			lock1.lock();
			logLock(1, true);
			Thread.sleep(3000);
			lock1.unlock();
			logLock(1, false);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lock2.unlock();
		logLock(2, false);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final DeadLockTestWithConcurrencyLock deadLockTest = new DeadLockTestWithConcurrencyLock();
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