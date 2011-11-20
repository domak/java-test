package org.domak.concurrency;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedQueueTest {
	public static void main(String[] args) {

		final DelayedQueueTest test = new DelayedQueueTest();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					test.queue.offer(new DelayHandler(3000));
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				int j = 0;
				while (true) {
					try {
						DelayHandler take = test.queue.take();
						i++;
						if (System.currentTimeMillis() - take.triggerTime > 10) {
							j++;
							System.out.println(j + "/" + i + "->" + take);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}

	// -------------------------------------------------------------------------
	DelayQueue<DelayHandler> queue = new DelayQueue<DelayHandler>();

	// -------------------------------------------------------------------------
	private static class DelayHandler implements Delayed {

		private DateFormat dateFormat = new SimpleDateFormat("mm:ss SSS");

		private static int counter;

		private long created = System.currentTimeMillis();
		long triggerTime = 0;

		public DelayHandler(long delay) {
			triggerTime = System.currentTimeMillis() + delay;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(triggerTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}

		@Override
		public int compareTo(Delayed o) {
			DelayHandler handler = (DelayHandler) o;
			return triggerTime > handler.triggerTime ? 1 : triggerTime == handler.triggerTime ? 0 : -1;
		}

		@Override
		public String toString() {
			return counter++ + ": created->" + dateFormat.format(created) + " - trigger->"
					+ dateFormat.format(triggerTime) + " - now->" + dateFormat.format(System.currentTimeMillis())
					+ " - diff->" + (System.currentTimeMillis() - triggerTime);
		}
	}
}
