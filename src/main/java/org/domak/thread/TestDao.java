package org.domak.thread;

public class TestDao {

	public Object find(String key) {
		System.out.println(this.getClass().getSimpleName() + ":" + key + "->" + TransactionManager.getTransactionId());
		return key + " retrieved";
	}
}
