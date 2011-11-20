package org.domak.memory;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class MemeoryTest {

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
				List<String> strings = Lists.newArrayList();
				for (;;) {
					strings.add(String.valueOf(random.nextInt()));
					if (cpt++ > LEVEL) {
						System.out.println("strings: " + strings.size());
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

		new Thread(new Runnable() {

			@Override
			public void run() {
				Random random = new Random();
				int cpt = 0;
				List<Integer> integers = Lists.newArrayList();
				for (;;) {
					integers.add(Integer.valueOf(random.nextInt()));
					if (cpt++ > LEVEL) {
						System.out.println("integers: " + integers.size());
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
		}, "integerThread").start();
	}
}
