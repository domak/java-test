package org.dmk.thread;

import java.util.Random;

public class TestService {

	// -------------------------------------------------------------------------
	// dependencies injection
	// -------------------------------------------------------------------------
	private TestDao testDao;

	public void setTestDao(TestDao testDao) {
		this.testDao = testDao;
	}

	// -------------------------------------------------------------------------

	public void process(String key) {
		System.out.println(this.getClass().getSimpleName() + ":" + key + "->" + TransactionManager.getTransactionId());
		simulateRandomProcess(key);
		System.out.println(testDao.find(key));
	}

	private void simulateRandomProcess(String string) {
		try {
			int randomInt = Math.abs(new Random().nextInt());
			while (randomInt > 10000) {
				randomInt = randomInt / 1000;
			}
			System.out.println(string + "-> sleep(" + randomInt + ")");
			Thread.sleep(randomInt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
