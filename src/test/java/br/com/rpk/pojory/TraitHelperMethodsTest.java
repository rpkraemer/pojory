package br.com.rpk.pojory;

import org.junit.Assert;
import org.junit.Test;

public class TraitHelperMethodsTest {

	@Test
	public void shouldReturnRandomString() {
		String ret = TraitHelperMethods.random("String 1", "String 2");
		Assert.assertNotNull(ret);
		Assert.assertTrue(ret.equals("String 1") || ret.equals("String 2"));
	}

	@Test
	public void shouldReturnRandomString2() {
		String ret = TraitHelperMethods.random("String 1", "String 2",
				"Other String");
		Assert.assertNotNull(ret);
		Assert.assertTrue(ret.equals("String 1") || ret.equals("String 2")
				|| ret.equals("Other String"));
	}
	
	@Test
	public void shouldReturnRandomInt() {
		int ret = TraitHelperMethods.random(1, 2);
		Assert.assertTrue(ret >= 1 && ret <= 2);
	}
	
	@Test
	public void shouldReturnRandomInt2() {
		int ret = TraitHelperMethods.random(1, 2, -5);
		Assert.assertTrue(ret >= -5 && ret <= 2);
	}
	
	@Test
	public void shouldReturnRandomLong() {
		long ret = TraitHelperMethods.random(1l, 2l);
		Assert.assertTrue(ret >= 1l && ret <= 2l);
	}
	
	@Test
	public void shouldReturnRandomLong2() {
		long ret = TraitHelperMethods.random(1l, 2l, -5l);
		Assert.assertTrue(ret >= -5l && ret <= 2l);
	}
	
	@Test
	public void shouldReturnRandomFloat() {
		float ret = TraitHelperMethods.random(1.01f, 1.02f);
		Assert.assertTrue(ret >= 1.01f && ret <= 1.02f);
	}
	
	@Test
	public void shouldReturnRandomFloat2() {
		float ret = TraitHelperMethods.random(1.01f, 1.02f, 2f);
		Assert.assertTrue(ret >= 1.01f && ret <= 2f);
	}
	
	@Test
	public void shouldReturnRandomDouble() {
		double ret = TraitHelperMethods.random(1.01, 1.02);
		Assert.assertTrue(ret >= 1.01 && ret <= 1.02);
	}
	
	@Test
	public void shouldReturnRandomDouble2() {
		double ret = TraitHelperMethods.random(1.01, 1.02, 2d);
		Assert.assertTrue(ret >= 1.01 && ret <= 2);
	}
	
	@Test
	public void shouldReturnRandomByte() {
		byte ret = TraitHelperMethods.random((byte) 10, (byte) 12);
		Assert.assertTrue(ret >= 10 && ret <= 12);
	}
	
	@Test
	public void shouldReturnRandomChar() {
		char ret = TraitHelperMethods.random((char) 1, (char) 3);
		Assert.assertTrue(ret >= 1 && ret <= 3);
	}
	
	@Test
	public void shouldReturnRandomWrapperObjects() {
		Byte b = TraitHelperMethods.random(Byte.valueOf((byte) 0), Byte.valueOf((byte) 1));
		Assert.assertTrue(b.byteValue() == 0 || b.byteValue() == 1);
		
		Character c = TraitHelperMethods.random(Character.valueOf('c'), Character.valueOf('3'));
		Assert.assertTrue(c.charValue() == 'c' || c.charValue() == '3');
		
		Short s = TraitHelperMethods.random(Short.valueOf((short) 0), Short.valueOf((short) 1));
		Assert.assertTrue(s.shortValue() == 0 || s.shortValue() == 1);
		
		Integer i = TraitHelperMethods.random(0, 1);
		Assert.assertTrue(i.intValue() == 0 || i.intValue() == 1);
				
		Float f = TraitHelperMethods.random(0f, 1f);
		Assert.assertTrue(f.floatValue() == 0 || f.floatValue() == 1);
		
		Double d = TraitHelperMethods.random(0d, 1d);
		Assert.assertTrue(d.doubleValue() == 0 || d.doubleValue() == 1);
		
		Long l = TraitHelperMethods.random(0l, 1l);
		Assert.assertTrue(l.longValue() == 0 || l.longValue() == 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenRangeFirstArgumentLessThanSecondOne() {
		TraitHelperMethods.range(-1, -2);
	}
	
	@Test
	public void shouldReturnRangeInt() {
		Integer[] range = TraitHelperMethods.range(1, 4);
		Assert.assertEquals(4, range.length);
	}
	
	@Test
	public void shouldReturnRangeInt2() {
		Integer[] range = TraitHelperMethods.range(-5, 5000);
		Assert.assertEquals(5006, range.length);
	}
	
	@Test
	public void shouldReturnRandomIntBasedOnARange() {
		int ret = TraitHelperMethods.random(TraitHelperMethods.range(1, 5));
		Assert.assertTrue(ret >= 1 && ret <= 5);
	}
	
	@Test
	public void shouldReturnRandomLongBasedOnARange() {
		long ret = TraitHelperMethods.random(TraitHelperMethods.range(1, 5));
		Assert.assertTrue(ret >= 1l && ret <= 5l);
	}

}
