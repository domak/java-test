package org.dmk.perf;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class MaxThread {
    public static void main(String[] args) {
        AtomicInteger count = new AtomicInteger();
        while (true) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    System.out.println("thread #" + count.getAndIncrement());
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
            thread.start();
        }
    }
}
