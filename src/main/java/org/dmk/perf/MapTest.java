package org.dmk.perf;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long sum = 0;
		long sum2 = 0;
		int n = 100;
		for (int j = 0; j < n; j++) {
			Map<Integer, Integer> map = new HashMap<>();
			long start = System.currentTimeMillis();
			for (int i = 0; i < 1_000_000; i++) {
				// for (int i = 0; i < 1_000_000; i++) {
				Integer integer = Integer.valueOf(i);

				map.put(integer, integer);
			}
			long end = System.currentTimeMillis();
			long delta = end - start;
			sum += delta;
			sum2 += delta * delta;

			System.out.println(end - start);
		}
		double avg = sum / n;
		double variance = (n * sum2 - sum * sum) / (n * (n - 1));
		double stddev = Math.sqrt(variance);

		System.out.println(avg);
		System.out.println(variance);
		System.out.println(stddev);

	}
}
