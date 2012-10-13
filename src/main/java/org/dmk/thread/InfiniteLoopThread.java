package org.dmk.thread;

public class InfiniteLoopThread {

	/**
     * 
     */
	static final class InfiniteLoopRunnable implements Runnable {
		private volatile boolean stop = false;

		public void tryToStop() {
			System.out.println("STOP!!!!!!!");
			stop = true;
		}

		@Override
		public void run() {
			System.out.println("infinite loop started");
			while (!stop && !Thread.interrupted()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			System.out.println("infinite loop ended");

		}
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		InfiniteLoopRunnable infiniteLoopRunnable = new InfiniteLoopRunnable();
		final Thread loopThread = new Thread(infiniteLoopRunnable, "loopThread");
		loopThread.start();

		Thread.sleep(1000);

		// infiniteLoopRunnable.tryToStop();
		loopThread.interrupt();
		System.out.println("end of main");

	}
}
