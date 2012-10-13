package org.dmk.memory;

import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

public class WeakMemoryTest {

	protected static int LEVEL = 1000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Random random = new Random();
				int cpt = 0;
				int lastSize = 0;
				Map<String, Object> strings = new WeakHashMap<String, Object>();
				for (;;) {
					strings.put(String.valueOf(random.nextInt()), null);
					if (cpt++ > LEVEL) {
						int actualSize = strings.size();
						if (lastSize > actualSize) {
							System.out.println("reduced from " + lastSize);
						}
						lastSize = actualSize;
						System.out.println("strings: " + actualSize);
						cpt = 0;
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Thread.yield();
				}
			}
		}, "stringThread").start();

		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// Random random = new Random();
		// int cpt = 0;
		// Map<Integer, Object> integers = new WeakHashMap<Integer, Object>();
		// for (;;) {
		// integers.put(random.nextInt(), null);
		// if (cpt++ > LEVEL) {
		// System.out.println("integers: " + integers.size());
		// cpt = 0;
		// try {
		// Thread.sleep(10);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// Thread.yield();
		// }
		// }
		// }, "integerThread").start();
	}
}
