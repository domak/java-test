/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dmk.asynctosync;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 
 */
public class Main {

    private static final int CLIENT_COUNT = 200;
    private static final int PRODUCTS_TO_PRICE_MAX = 1000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	BasicConfigurator.configure();
	Logger.getRootLogger().setLevel(Level.INFO);
	final Logger logger = Logger.getLogger(Main.class);

	RemoteQueue<PriceEvent> remoteQueue = new RemoteQueue<PriceEvent>();

	PricerService pricerService = new PricerService();
	pricerService.setRemoteQueue(remoteQueue);
	pricerService.start();

	final PricerProxy pricerProxy = new PricerProxy();
	pricerProxy.setPricerService(pricerService);
	pricerProxy.setRemoteQueue(remoteQueue);
	pricerProxy.start();

	ExecutorService executorService = Executors.newFixedThreadPool(CLIENT_COUNT);
	CompletionService<Map<Integer, Float>> completionService = new ExecutorCompletionService<Map<Integer, Float>>(
		executorService);

	for (int i = 0; i < CLIENT_COUNT; i++) {
	    completionService.submit(new Callable<Map<Integer, Float>>() {
		@Override
		public Map<Integer, Float> call() throws Exception {
		    List<Integer> productIds = getProductIds();
		    // logger.info("main()->added productIds: " + productIds);
		    Map<Integer, Float> productPrices = pricerProxy.price(productIds);
		    logger.info("computed " + productPrices.size() + " prices");
		    return productPrices;
		}
	    });
	}

	int pricedProductCount = 0;
	for (int i = 0; i < CLIENT_COUNT; i++) {
	    try {
		pricedProductCount += completionService.take().get().size();
	    } catch (InterruptedException e) {
		throw new RuntimeException(e);
	    } catch (ExecutionException e) {
		throw new RuntimeException(e);
	    }
	}
	logger.info("total prices computed: " + pricedProductCount);

	executorService.shutdownNow();
	pricerProxy.stop();
	pricerService.stop();
	logger.info("finished");

    }

    /**
     * @return a list of productIds to price
     */
    private static List<Integer> getProductIds() {
	Random random = new Random();
	int size = random.nextInt(PRODUCTS_TO_PRICE_MAX);
	// use a set to enforce unicity
	Set<Integer> productIds = new HashSet<Integer>();
	for (int i = 0; i < size; i++) {
	    Integer productId = random.nextInt(PRODUCTS_TO_PRICE_MAX * 10); // to avoid to many collision
	    productIds.add(productId);
	}
	return new ArrayList<Integer>(productIds);
    }
}
