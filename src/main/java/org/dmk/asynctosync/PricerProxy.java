package org.dmk.asynctosync;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

/**
 * @author domak
 */
public class PricerProxy {

	private static Logger logger = Logger.getLogger(PricerProxy.class);

	// -------------------------------------------------------------------------
	// dependency injection
	// -------------------------------------------------------------------------
	private PricerService pricerService;
	private RemoteQueue<PriceEvent> remoteQueue;

	public void setPricerService(PricerService pricerService) {
		this.pricerService = pricerService;
	}

	public void setRemoteQueue(RemoteQueue<PriceEvent> remoteQueue) {
		this.remoteQueue = remoteQueue;
	}

	// -------------------------------------------------------------------------
	private Map<Integer, PricingResultAssemblor> requestIdToPricingResultMap = new ConcurrentHashMap<Integer, PricingResultAssemblor>();
	private ExecutorService remoteQueueListenerExecutor;

	/**
	 * Start listening the queue.
	 */
	public void start() {
		logger.info("init()->start remoteQueueListener");
		remoteQueueListenerExecutor = Executors.newSingleThreadExecutor();
		remoteQueueListenerExecutor.execute(new RemoteQueueListener());
	}

	/**
	 * Stop listening the queue.
	 */
	public void stop() {
		remoteQueueListenerExecutor.shutdownNow();
	}

	/**
	 * Gets a list of products to price and returns a requestId.
	 * @param productId
	 * @return
	 */
	public Map<Integer, Float> price(List<Integer> productIds) {
		if ((productIds == null) || (productIds.size() == 0)) {
			return new HashMap<Integer, Float>();
		}
		PricingResultAssemblor pricingResultAssemblor = new PricingResultAssemblor(productIds);

		int requestId = pricerService.price(productIds);
		logger.debug("price()->get requestId " + requestId + " for products: " + productIds);
		requestIdToPricingResultMap.put(requestId, pricingResultAssemblor);

		// block until all prices were received
		return pricingResultAssemblor.getPrices();
	}

	// -------------------------------------------------------------------------
	// inner class
	// -------------------------------------------------------------------------
	private class RemoteQueueListener implements Runnable {

		@Override
		public void run() {
			while (true) {
				PriceEvent priceEvent = remoteQueue.receive();
				logger.debug("run()->received: " + priceEvent);
				int requestId = priceEvent.getRequestId();

				// get the pricerAssemblor
				PricingResultAssemblor pricingResultAssemblor = null;
				while ((pricingResultAssemblor = requestIdToPricingResultMap.get(requestId)) == null) {
					logger.debug("run()->received unknonw requestId: " + priceEvent);
					// wait for object construction
					Thread.yield();
				}
				pricingResultAssemblor.add(priceEvent);
			}

		}
	}

	// -------------------------------------------------------------------------
	// inner class
	// -------------------------------------------------------------------------
	private static class PricingResultAssemblor {

		@SuppressWarnings("hiding")
		private static Logger logger = Logger.getLogger(PricingResultAssemblor.class);

		private Map<Integer, Float> productIdToPriceMap = new HashMap<Integer, Float>();
		private Set<Integer> remainingProductIds = new HashSet<Integer>(); // use a set for retrieval performance
		private Lock lock = new ReentrantLock();
		private Condition remainingProductidsIsEmpty = lock.newCondition();

		/**
		 * Constructor.
		 * @param productIds
		 */
		public PricingResultAssemblor(List<Integer> productIds) {
			remainingProductIds.addAll(productIds);
		}

		/**
		 * Adds a priceEent and returns true it was the last element to proceed.
		 * @param priceEvent
		 * @return true if it was the last element to proceed
		 */
		public void add(PriceEvent priceEvent) {
			logger.debug("add()-> param: " + priceEvent);
			lock.lock();
			boolean removed = remainingProductIds.remove(priceEvent.getProductId());
			if (!removed) {
				logger.error("productId not found in remaining list: " + priceEvent);
			}
			productIdToPriceMap.put(priceEvent.getProductId(), priceEvent.getPrice());

			if (remainingProductIds.size() == 0) {
				logger.debug("add()->no more product to price");
				logger.debug("add()->remainingProductidsIsEmpty.signal()");
				remainingProductidsIsEmpty.signal();
			}
			logger.debug("add()->unlock");
			lock.unlock();
		}

		/**
		 * Gets the computed prices.
		 * @return
		 */
		public Map<Integer, Float> getPrices() {
			logger.debug("getPrices()->entering");
			lock.lock();
			logger.debug("getPrices()->locked");
			try {
				remainingProductidsIsEmpty.await();
				logger.debug("getPrices()->end of remainingProductidsIsEmpty.await()");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				lock.unlock();
			}
			logger.debug("add()->end of assembly for request");
			return productIdToPriceMap;
		}
	}
}
