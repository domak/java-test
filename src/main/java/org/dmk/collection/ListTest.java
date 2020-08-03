package org.dmk.collection;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ListTest {

    public static void main(String[] args) {

        List<Integer> values = getValues();
        for (Integer value : values) {
            for (Integer value2 : values) {
                System.out.println(String.format("value 1: %s - value 2: %s", value, value2));
            }
        }

    }

    private static List<Integer> getValues() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            values.add(i);
        }
        return values;
    }
}
