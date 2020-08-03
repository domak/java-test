package org.dmk.asynctosync;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

/**
 * 
 */
public class PricerService {
	// -------------------------------------------------------------------------
	// dependency injection
	// -------------------------------------------------------------------------
	private RemoteQueue<PriceEvent> remoteQueue;

	void setRemoteQueue(RemoteQueue<PriceEvent> remoteQueue) {
		this.remoteQueue = remoteQueue;
	}

	// -------------------------------------------------------------------------

	private Lock requestIdLock = new ReentrantLock();
	private int requestIdCounter = 0;
	private ExecutorService pricerExecutorService = Executors.newCachedThreadPool();
	private CompletionService<PriceEvent> pricerCompletionService = new ExecutorCompletionService<PriceEvent>(
			pricerExecutorService);
	private ExecutorService pricerListenerExecutor = Executors.newSingleThreadExecutor();

	public void start() {
		pricerListenerExecutor.execute(new PricerListener());
	}

	public void stop() {
		pricerExecutorService.shutdownNow();
		pricerListenerExecutor.shutdownNow();
	}

	/**
	 * Gets a list of products to price and returns a requestId.
	 * @param productId
	 * @return
	 */
	public Integer price(List<Integer> productIds) {
		int requestId = -1;
		requestIdLock.lock();
		try {
			requestId = requestIdCounter++;
		} finally {
			requestIdLock.unlock();
		}
		for (Integer productId : productIds) {
			Pricer pricer = new Pricer(requestId, productId);
			pricerCompletionService.submit(pricer);
		}
		return requestId;
	}

	// -------------------------------------------------------------------------
	// inner class
	// -------------------------------------------------------------------------
	private class PricerListener implements Runnable {

		private Logger logger = Logger.getLogger(PricerListener.class);

		@Override
		public void run() {
			logger.info("run()->entering");
			while (true) {
				try {
					PriceEvent priceEvent = pricerCompletionService.take().get();
					logger.debug("run()->take: " + priceEvent);
					remoteQueue.publish(priceEvent);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} catch (ExecutionException e) {
					throw new RuntimeException(e);
				}
			}
		}

	}

	// -------------------------------------------------------------------------
	// inner class
	// -------------------------------------------------------------------------
	private static class Pricer implements Callable<PriceEvent> {

		private static Logger logger = Logger.getLogger(Pricer.class);
		private static Random random = new Random();
		private static int eventCounter = 0;

		// ----------------------------------------------------------------------

		private int requestId;
		private int productId;

		public Pricer(int requestId, int productId) {
			this.requestId = requestId;
			this.productId = productId;
		}

		@Override
		public PriceEvent call() throws Exception {
			try {
				logger.debug("call()->start pricing [" + requestId + ", " + productId + "]");
				// simulate computing time
				Thread.sleep(random.nextInt(10000));
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}

			float price = random.nextFloat();
			logger.debug("call()->priced [" + requestId + ", " + productId + "]: " + price);
			// create and return priceEvent
			return new PriceEvent(eventCounter++, requestId, productId, price);
		}
	}

}
