package br.com.rpk.pojory;

import java.util.Random;

public class TraitHelperMethods {

	public static <T> T random(T... values) {
		Random rand = new Random();
		int i = rand.nextInt(values.length);
		return values[i];
	}
	
	public static Integer[] range(int start, int end) {
		if (start > end) {
			throw new IllegalArgumentException("First argument must be less than second one");
		}
		Integer[] range = new Integer[(end - start) + 1];
		int idx = 0;
		for (int i = start; i <= end; i++, idx++) {
			range[idx] = i;
		}
		return range;
	}
}
