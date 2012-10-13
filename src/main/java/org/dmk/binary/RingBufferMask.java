package org.dmk.binary;

public class RingBufferMask {

	private static String[] store = new String[] {"tata", "titi", "toto", "tutu"}; // must be a power of 2 length
	private static int indexMask = store.length - 1;

	public static void main(String[] args) {
		int i = 0;
		while (i < 10) {
			int index = index(i);
			System.out.println(" - " + (store[index(i)]));
			i++;
		}
	}

	private static int index(int i) {
		int index = i & indexMask;
		System.out.print("i: " + toLog(i) + " - indexMask: " + toLog(indexMask) + " - index: " + toLog(index));
		return index;
	}

	private static String toLog(int i) {
		return new StringBuilder().append('(').append(Integer.toString(i)).append(", ")
				.append(Integer.toBinaryString(i)).append(')').toString();
	}
}
