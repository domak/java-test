package org.dmk.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockVsSynchronized {

	public static void main(String[] args) {

		final int count = 100000000;

		launch(count, new LockSharedResource());
		launch(count, new SynchronizedSharedResource());
	}

	private static void launch(final int count, final SharedResource sharedResource) {
		final long start = System.currentTimeMillis();

		final Thread consummer = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < count; i++) {
					String value = sharedResource.get();
					System.out.println("read:" + value);
				}
				System.out.println("consumer shared resource: " + sharedResource);
			}
		});
		consummer.start();

		final Thread producer = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < count; i++) {
					String value = "msg " + i;
					// try {
					// Thread.sleep(3000);
					// } catch (InterruptedException e) {
					// throw new RuntimeException(e);
					// }
					// System.out.println("write:" + value);
					sharedResource.set(value);
				}
				System.out.println("producer shared resource: " + sharedResource);
			}
		});
		producer.start();

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					consummer.join();
					producer.join();
					System.out.println(sharedResource.getClass().getSimpleName() + "->time: "
							+ (System.currentTimeMillis() - start));
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
		thread.start();
	}

	interface SharedResource {

		public abstract String get();

		public abstract void set(String string);

	}

	// -------------------------------------------------------------------------
	private static class SynchronizedSharedResource implements SharedResource {

		private String string;
		private int readCount = 0;
		private int writeCount = 0;

		/*
		 * (non-Javadoc)
		 * @see org.dmk.concurrency.SharedResource#get()
		 */
		@Override
		public synchronized String get() {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			notify();
			readCount++;
			return string;
		}

		/*
		 * (non-Javadoc)
		 * @see org.dmk.concurrency.SharedResource#set(java.lang.String)
		 */
		@Override
		public synchronized void set(String string) {
			writeCount++;
			this.string = string;
			notify();
			try {
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public String toString() {
			return "readCount: " + readCount + " - " + "writeCount: " + writeCount;
		}
	}

	private static class LockSharedResource implements SharedResource {

		private String string;
		private int readCount = 0;
		private int writeCount = 0;

		private Lock lock = new ReentrantLock();
		private Condition valueAvailableForReading = lock.newCondition();
		private Condition valueAvailableForWriting = lock.newCondition();

		/*
		 * (non-Javadoc)
		 * @see org.dmk.concurrency.SharedResource#get()
		 */
		@Override
		public String get() {
			try {
				lock.lock();
				valueAvailableForReading.await();
				valueAvailableForWriting.signal();
				readCount++;
				return string;
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				lock.unlock();
			}
		}

		/*
		 * (non-Javadoc)
		 * @see org.dmk.concurrency.SharedResource#set(java.lang.String)
		 */
		@Override
		public synchronized void set(String string) {
			try {
				lock.lock();
				writeCount++;
				this.string = string;
				valueAvailableForReading.signal();
				valueAvailableForWriting.await();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				lock.unlock();
			}
		}

		@Override
		public String toString() {
			return "readCount: " + readCount + " - " + "writeCount: " + writeCount;
		}
	}
}
