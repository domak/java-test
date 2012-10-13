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
		print(SHARED_SHIFT);
		print(SHARED_UNIT);
		print(MAX_COUNT);
		print(EXCLUSIVE_MASK);
		System.out.println("================================================================");
		for (int i = 0; i < SHARED_SHIFT; i++) {
			print((1 << i) - 1);
		}
		print(0x8fff);
		print(0xffff);
	}

	public static void print(int i) {
		System.out.println("int value: " + i + " - binary: " + Integer.toBinaryString(i) + " - hexa: "
				+ Integer.toHexString(i));
	}

}
