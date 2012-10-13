package org.dmk.perf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class AsyncTest {

	private static final int COUNT = 100_000_000;

	// private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(100_000_000);
	// private static BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
	private static TransferQueue<Integer> queue = new LinkedTransferQueue<>();

	private static long start;

	public static void main(String[] args) {
		start = System.currentTimeMillis();
		for (int i = 0; i < COUNT; i++) {
			process(i);
		}
		start = System.currentTimeMillis();
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Integer i = queue.take();
						if (process(i)) {
							System.exit(0);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		for (int i = 0; i < COUNT; i++) {
			try {
				queue.put(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean process(Integer i) {
		if (i == COUNT - 1) {
			System.out.println("final: " + (System.currentTimeMillis() - start));
			return true;
		}
		return false;
	}
}
