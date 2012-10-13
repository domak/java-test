package org.dmk.reflection;

import java.lang.reflect.Field;

public class IntegerTest {

	public static void main(String[] args) throws SecurityException, NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		Field field = Integer.class.getDeclaredField("value");
		field.setAccessible(true);
		field.set(42, 43);

		for (int i = 0; i < 100; i++) {
			System.out.println(Integer.valueOf(i));

		}
	}

}
