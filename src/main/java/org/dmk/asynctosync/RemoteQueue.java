/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dmk.asynctosync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

/**
 * 
 * @author domak
 */
public class RemoteQueue<T extends Object> {

    private static Logger logger = Logger.getLogger(RemoteQueue.class);

    private BlockingQueue<T> queue = new ArrayBlockingQueue<T>(1000);

    public void publish(T event) {
	logger.debug("publish()->entering: " + event);
	try {
	    queue.put(event);
	} catch (InterruptedException e) {
	    throw new RuntimeException(e);
	}
	logger.debug("published: " + event);
    }

    public T receive() {
	logger.debug("received()->enterring");
	try {
	    T event = queue.take();
	    logger.debug("received()->take: " + event);
	    return event;
	} catch (InterruptedException e) {
	    throw new RuntimeException(e);
	}
    }
}
