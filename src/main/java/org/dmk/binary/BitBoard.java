package org.dmk.binary;

/**
 *
 */
public class BitBoard {
    public static void main(String[] args) {
        int i = 255;

        printBinary(i);

        printBinary(1 << 0);
        printBinary(1 << 1);
        printBinary(1 << 4);
        printBinary(1 << 6);
        printBinary(~(1 << 6));

        // set the  bit 6 (7th) to 1
        i |= (1 << 6); // set a bit to 1
        printBinary(i);

        // check if bit 6 is set
        printBinary((i & (1 << 6)));
        System.out.println("bit 6 set: " + ((i & (1 << 6)) == (1 << 6)));

        // set the bit 6 to 0
        i &= ~(1 << 6);
        printBinary(i);
        System.out.println("bit 6 set: " + ((i & (1 << 6)) == (1 << 6)));

        i |= (1 << 6); // set a bit to 1
        printBinary(i);

        System.out.println("------------------------------");
        i = 255;
        printBinary(i);
        i &= ~64;
        printBinary(i);

    }

    private static void printBinary(int i) {
        System.out.println(String.format("i: %s - binary: %s - count: %s", i, Integer.toBinaryString(i), Integer.bitCount(i)));
    }
}
