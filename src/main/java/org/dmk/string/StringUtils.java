package org.dmk.string;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 */
public class StringUtils {
    public static String sanitze(String string) {
        return string.trim().replaceAll("\\s+", "_");
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.sanitze("    abc def    ghi       "));

        Arrays.asList(42, 7, 11, 89).stream().filter(i -> i > 22).map(i -> i * 2).collect(Collectors.toList());

        Integer collect = Arrays.asList(42, 7, 11, 89).stream()
                .reduce(0, (i1, i2) -> i1 + i2);
        System.out.println(collect);
    }
}
