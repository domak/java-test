/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.domak.thread;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Try to see the cost of context switching.
 */
public class SwitchContextTest {

	private static final int TRY_COUNT = 3;

	public static void main(String[] args) throws InterruptedException {

		int threadCount = 100;
		int count = 2000000;
		List<Integer> numbers = init(count);

		// compute with only one thread - keep the last ref time
		System.out.println("initialized");
		long start = 0;
		long refTime = 0;
		for (int i = 0; i < TRY_COUNT; i++) {
			start = System.currentTimeMillis();
			BigDecimal mean = mean(numbers);
			refTime = System.currentTimeMillis() - start;
			System.out.println("ref - mean: " + mean + " - time: " + refTime);
		}

		// parallelize the computing with multiple thread
		for (int i = 1; i <= threadCount; i++) {
			start = System.currentTimeMillis();

			// parttion the data
			Iterable<List<Integer>> iterablePartitions = Iterables.partition(numbers, count / i);
			List<List<Integer>> partitions = Lists.newArrayList(Iterables.transform(iterablePartitions,
					new Function<Iterable<Integer>, List<Integer>>() {

						@Override
						public List<Integer> apply(Iterable<Integer> from) {
							return Lists.newArrayList(from);
						}
					}));
			long partitionTime = System.currentTimeMillis() - start;
			for (int j = 0; j < TRY_COUNT; j++) {
				CountDownLatch latch = new CountDownLatch(partitions.size());
				start = System.currentTimeMillis();
				for (List<Integer> part : partitions) {
					Thread thread = new Thread(new MeanComputer(part, start, latch));
					thread.start();
				}
				latch.await();
				long threadTime = System.currentTimeMillis() - start;
				System.out.println("thread: " + threadTime + "\t - diff (ref - thread(" + i + ")): \t"
						+ (refTime - threadTime) + "\t - partitiontime: \t" + partitionTime);
			}
		}
	}

	private static List<Integer> init(int count) {
		List<Integer> numbers = Lists.newArrayList();
		for (int i = 0; i < count; i++) {
			numbers.add(new Integer(i));
		}
		return numbers;

	}

	static BigDecimal mean(List<Integer> numbers) {
		BigInteger sum = BigInteger.valueOf(0);
		for (Integer number : numbers) {
			for (int i = 0; i < 80; i++) {
				BigInteger.valueOf(number.longValue()).compareTo(sum);
			}

			sum = sum.add(BigInteger.valueOf(number.intValue()));
			Thread.yield();
		}
		return new BigDecimal(sum).divide(BigDecimal.valueOf(numbers.size()));
	}

	// -------------------------------------------------------------------------
	// inner class
	// -------------------------------------------------------------------------
	static class MeanComputer implements Runnable {

		private BigDecimal mean;
		private List<Integer> numbers;
		private long start;
		private final CountDownLatch latch;

		public MeanComputer(List<Integer> numbers, long start, CountDownLatch latch) {
			this.numbers = numbers;
			this.start = start;
			this.latch = latch;
		}

		@Override
		public void run() {
			mean = mean(numbers);
			latch.countDown();
			System.out.println("end of thread - mean: " + mean + " - time: " + (System.currentTimeMillis() - start));
		}
	}

}
