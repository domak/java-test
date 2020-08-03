package org.dmk.binary;

public class ShiftTest {

    /**
     * @param args
     */
    static final int SHARED_SHIFT = 16;
    static final int SHARED_UNIT = (1 << SHARED_SHIFT);
    static final int MAX_COUNT = (1 << SHARED_SHIFT) - 1;
    static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

    public static void main(String[] args) {

        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
        System.out.println(Integer.bitCount(Integer.MAX_VALUE));
        System.out.println(Integer.bitCount(Integer.MAX_VALUE)/3);
        System.out.println(Long.MAX_VALUE);
        System.out.println(Long.toBinaryString(Long.MAX_VALUE));
        System.out.println(Long.bitCount(Long.MAX_VALUE));
        System.out.println(Long.bitCount(Long.MAX_VALUE)/3);

        int value = 0;
        value = add(value , 3 , 0 );
        value = add(value , 4 , 1 );
        value = add(value , 5 , 2  );
        value = add(value , 6 , 3 );

        get(value, 3);
        get(value, 2);
        get(value, 1);
        get(value, 0);

        if (true) {
            return;
        }

        System.out.println("=======================================================");
        System.out.println(Integer.toBinaryString(128));
        System.out.println(Integer.toBinaryString(127));
        System.out.println(Integer.toBinaryString(~127));
        System.out.println(Integer.toBinaryString(127 & ~127));
        System.out.println(Integer.toBinaryString(127 | ~127));

        print(1 << 0);
        print(1 << 1);
        print(1 << 2);
        print(1 << 3);
        print(1 << 4);

        print(Integer.valueOf("1001011111101111001111001010", 2));

        System.out.println("=================================================================" +
                "");
        print(Integer.valueOf("1010", 2));

        print(SHARED_SHIFT);
        print(SHARED_UNIT);
        print(MAX_COUNT);
        print(EXCLUSIVE_MASK);
        System.out.println("================================================================");
        for (int i = 0; i < SHARED_SHIFT; i++) {
            print((1 << i) - 1);
        }
        System.out.println("================================================================");
        print(0x8fff);
        print(0xffff);
    }

    public static int add(int value, int add, int pos) {
        int result = value + (add << (pos * 3));
        System.out.println(String.format("old value: %s - add: %s - new value: %s - old binary: %s  - add: %s - new binary: %s",
                value, add, result, Integer.toBinaryString(value), Integer.toBinaryString(add), Integer.toBinaryString(result)));
        return result;
    }

    public static int get(int value, int pos) {
        int newValue = (value >>(pos * 3)) & 7;
        System.out.println(String.format("value: %s - pos: %s - get: %s - binary value: %s - binary get: %s", value, pos, newValue, Integer.toBinaryString(value), Integer.toBinaryString(newValue)));
        return newValue;
    }

    public static void print(int i) {
        System.out.println("int value: " + i + " - binary: " + Integer.toBinaryString(i) + " - hexa: "
                + Integer.toHexString(i));
    }

}
