package org.dmk.perf;

import java.util.Arrays;
import java.util.Random;

public class SortedArray {

	public static void main(String[] args) {
		// generate data
		int arraySize = 32768;
		int data[] = new int[arraySize];

		Random rnd = new Random(0);
		for (int c = 0; c < arraySize; ++c)
			data[c] = rnd.nextInt() % 256;

		// !!! with this, the next loop runs faster
		Arrays.sort(data);

		// test
		long start = System.nanoTime();
		long sum = 0;

		for (int i = 0; i < 100000; ++i) {
			// primary loop
			for (int c = 0; c < arraySize; ++c) {
				if (data[c] >= 128)
					sum += data[c];
			}
		}

		System.out.println((System.nanoTime() - start) / 1000000000.0);
		System.out.println("sum = " + sum);
	}
}
