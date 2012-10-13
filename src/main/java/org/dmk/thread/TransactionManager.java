package org.dmk.thread;

import java.util.Random;

public class TransactionManager {

	private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

	public static String getTransactionId() {
		String id = threadLocal.get();
		if (id == null) {
			id = "txId" + new Random().nextInt(10);
			threadLocal.set(id);
		}
		return id;
	}
}
