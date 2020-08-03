package org.dmk.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 */
public class LambdaTest {
    public static void main(String[] args) {
        Function<Integer, String> intToStr = String::valueOf;
        System.out.println(intToStr.apply(10));

        BiFunction<String, String, Integer> indexOf2 = String::indexOf;

        String tototatatutu = "tototatatutu";
        System.out.println(indexOf2.apply(tototatatutu, "a"));

        Function<String, Integer> integerIntegerFunction = tototatatutu::indexOf;
        System.out.println(integerIntegerFunction.apply("a"));

        List<Pet> pets = Arrays.asList(new Pet("nestor"), new Pet("castor"), new Pet("pollux"));
        List<String> collect = pets.stream().map(Pet::getName).collect(Collectors.toList());
    }

    private static class Pet {
        private final String name;

        public Pet(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
