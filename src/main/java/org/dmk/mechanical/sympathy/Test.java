package org.dmk.mechanical.sympathy;

public class Test {
	public static void main(String[] args) {
		int i = 0;
		long l = 1024;
		while (i++ < 100) {
			long result = l + i;
			long resultShift = l + (i << 0);
			System.out.println(result + " - " + resultShift + " - " + Long.toBinaryString(result) + " - "
					+ Long.toBinaryString(resultShift));
		}
	}
}
