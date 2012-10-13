package org.dmk.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ScheduleTest {

	private static long last;

	public static void main(String[] args) {

		boolean flag = true;
		while (flag) {
			long start = System.currentTimeMillis();
			long startNanos = System.nanoTime();
			LockSupport.parkNanos(Thread.currentThread(), 1000000000);
			System.out.println("after: " + (System.currentTimeMillis() - start) + " - nanos: "
					+ (System.nanoTime() - startNanos) + " - nanos converted: "
					+ TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
		}

		last = System.currentTimeMillis();

		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

		last = System.currentTimeMillis();
		scheduledThreadPool.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {

				Long current = System.currentTimeMillis();
				System.out.println(current - last);
				last = current;

			}
		}, 0, 1000, TimeUnit.MILLISECONDS);
	}

}
