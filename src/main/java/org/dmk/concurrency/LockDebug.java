package org.dmk.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDebug {

	private static Lock lock = new ReentrantLock();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		lock.lock();
		try {
			System.out.println("main thread acquired lock");

			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					lock.lock();
					try {
						System.out.println("new thread acquired lock");
					} finally {
						System.out.println("new thread release lock");
						lock.unlock();
					}
				}
			}, "my-new-thread");

			thread.start();
		} finally {
			System.out.println("main thread release lock");
			lock.unlock();
		}
	}

}
