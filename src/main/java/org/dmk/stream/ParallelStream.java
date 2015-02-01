package org.dmk.stream;

import java.util.stream.IntStream;

/**
 *
 */
public class ParallelStream {

    public static void main(String... args) {
        synchronized (System.out) {
            System.out.println("Hello World");
            // does not work because println() is a blocking method
            IntStream.range(0, 4).parallel().
                    forEach(System.out::println);
        }
    }
}

