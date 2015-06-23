package org.math;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SimpleDecimalTest {

	protected void setUp() throws Exception {

	}

	protected void tearDown() throws Exception {

	}

	@Test
	public final void testSimplifySimpleDecimal() {
		String s = "5.500";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		SimpleDecimal sd2 = sd1.simplify();
		int result = sd1.compareTo(sd2);
		assertEquals(0, result);

		s = "55.000";
		sd1 = new SimpleDecimal(s);
		sd2 = sd1.simplify();
		result = sd1.compareTo(sd2);
		assertEquals(0, result);
	}

	@Test
	public final void testSimplifySimpleDecimalInt() {
		String s = "5.500";
		int fractionalPrecision = 1;
		SimpleDecimal sd1 = new SimpleDecimal(s);
		SimpleDecimal sd2 = sd1.simplify(fractionalPrecision);
		fractionalPrecision++;
		SimpleDecimal sd3 = sd1.simplify(fractionalPrecision);
		int result = sd1.compareTo(sd2);
		assertEquals(0, result);
		result = sd1.compareTo(sd3);
		assertEquals(0, result);
	}

	@Test
	public final void testSetScale() {
		String s = "5.500";
		int fractionalPrecision = 4;
		SimpleDecimal sd1 = new SimpleDecimal(s);
		SimpleDecimal sd2 = sd1.setScale(fractionalPrecision);
		int result = sd1.compareTo(sd2);
		assertEquals(0, result);

		s = "55.0";
		sd1 = new SimpleDecimal(s);
		sd2 = sd1.setScale(fractionalPrecision);
		result = sd1.compareTo(sd2);
		assertEquals(0, result);

		s = "55.0000";
		sd1 = new SimpleDecimal(s);
		fractionalPrecision = 1;
		sd2 = sd1.setScale(fractionalPrecision);
	}

	@Test
	public final void testSimpleDecimalInt() {
		int i = 2;
		SimpleDecimal sd1 = new SimpleDecimal(i);
		i = -1;
		SimpleDecimal sd2 = new SimpleDecimal(i);
		int result = sd1.compareTo(sd2);
		assertEquals(3, result);
	}

	@Test
	public final void testSimpleDecimalLong() {
		long i = 2L;
		SimpleDecimal sd1 = new SimpleDecimal(i);
		i = -1L;
		SimpleDecimal sd2 = new SimpleDecimal(i);
		int result = sd1.compareTo(sd2);
		assertEquals(3, result);
	}

	@Test
	public final void testSimpleDecimalFloat() {
		float d = 2.3f;
		SimpleDecimal sd1 = new SimpleDecimal(d);
		d = 2.300f;
		SimpleDecimal sd2 = new SimpleDecimal(d);
		int result = sd1.compareTo(sd2);
		assertEquals(0, result);
	}

	@Test
	public final void testSimpleDecimalDouble() {
		double d = 2.3D;
		SimpleDecimal sd1 = new SimpleDecimal(d);
		d = 2.300D;
		SimpleDecimal sd2 = new SimpleDecimal(d);
		int result = sd1.compareTo(sd2);
		assertEquals(0, result);
	}

	@Test
	public final void testSimpleDecimalString() {
		String s = "5.500";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		s = "5.5";
		SimpleDecimal sd2 = new SimpleDecimal(s);
		int result = sd1.compareTo(sd2);
		assertEquals(0, result);
		s = ".500";
		sd1 = new SimpleDecimal(s);
		s = "0.5";
		sd2 = new SimpleDecimal(s);
		result = sd1.compareTo(sd2);
		assertEquals(0, result);
		s = "-.500";
		sd1 = new SimpleDecimal(s);
		s = "-0.5";
		sd2 = new SimpleDecimal(s);
		result = sd1.compareTo(sd2);
		assertEquals(0, result);
		s = "1.";
		sd1 = new SimpleDecimal(s);
		s = "1.00";
		sd2 = new SimpleDecimal(s);
		result = sd1.compareTo(sd2);
		assertEquals(0, result);
	}

	@Test
	public final void testAdd() {
		int fractionalPrecision = 10;
		Random r = new Random();
		double d = r.nextDouble();
		SimpleDecimal sd1 = new SimpleDecimal(d);
		sd1 = sd1.setScale(fractionalPrecision);
		BigDecimal bd1 = new BigDecimal(sd1.toString());
		d = r.nextDouble();
		SimpleDecimal sd2 = new SimpleDecimal(d);
		sd2 = sd2.setScale(fractionalPrecision);
		BigDecimal bd2 = new BigDecimal(sd2.toString());

		SimpleDecimal sd3 = sd1.add(sd2);
		BigDecimal bd3 = bd1.add(bd2);
		assertEquals(bd3, new BigDecimal(sd3.toString()));
	}

	@Test
	public final void testSubtract() {
		double s = 5.5D;
		SimpleDecimal sd1 = new SimpleDecimal(s);
		s = 3.201D;
		SimpleDecimal sd2 = new SimpleDecimal(s);
		SimpleDecimal sd3 = sd1.subtract(sd2);
		BigDecimal bd1 = new BigDecimal(sd1.toString());
		BigDecimal bd2 = new BigDecimal(sd2.toString());
		BigDecimal bd3 = bd1.subtract(bd2);
		assertEquals(bd3, new BigDecimal(sd3.toString()));

		long l = 7L;
		sd1 = new SimpleDecimal(l);
		s = 13.2173D;
		sd2 = new SimpleDecimal(s);
		sd3 = sd1.subtract(sd2);
		bd1 = new BigDecimal(sd1.toString());
		bd2 = new BigDecimal(sd2.toString());
		bd3 = bd1.subtract(bd2);
		assertEquals(bd3, new BigDecimal(sd3.toString()));

		String s1 = "0.1";
		sd1 = new SimpleDecimal(s1);
		s1 = "0.01";
		sd2 = new SimpleDecimal(s1);
		sd3 = sd1.subtract(sd2);
		bd1 = new BigDecimal(sd1.toString());
		bd2 = new BigDecimal(sd2.toString());
		bd3 = bd1.subtract(bd2);
		assertEquals(bd3, new BigDecimal(sd3.toString()));
	}

	@Test
	public final void testMultiply() {
		double s = 5.501D;
		SimpleDecimal sd1 = new SimpleDecimal(s);
		s = 3.2D;
		SimpleDecimal sd2 = new SimpleDecimal(s);
		SimpleDecimal sd3 = sd1.multiply(sd2);
		BigDecimal bd1 = new BigDecimal(sd1.toString());
		BigDecimal bd2 = new BigDecimal(sd2.toString());
		BigDecimal bd3 = bd1.multiply(bd2);
		assertEquals(bd3, new BigDecimal(sd3.toString()));

		long l = 7L;
		sd1 = new SimpleDecimal(l);
		s = 13.2173D;
		sd2 = new SimpleDecimal(s);
		sd3 = sd1.multiply(sd2);
		bd1 = new BigDecimal(sd1.toString());
		bd2 = new BigDecimal(sd2.toString());
		bd3 = bd1.multiply(bd2);
		assertEquals(bd3, new BigDecimal(sd3.toString()));
	}

	@Test
	public final void testDivide() {
		boolean debug = false;
		double s = 5.501D;
		SimpleDecimal sd1 = new SimpleDecimal(s);
		sd1 = new SimpleDecimal(sd1.getValue(), sd1.getFractionalPrecision(),
				debug);
		s = 3.2D;
		SimpleDecimal sd2 = new SimpleDecimal(s);
		SimpleDecimal sd3 = sd1.divide(sd2);
		BigDecimal bd1 = new BigDecimal(sd1.toString());
		BigDecimal bd2 = new BigDecimal(sd2.toString());
		BigDecimal bd3 = bd1.divide(bd2);
		bd3 = bd3.setScale(sd3.getFractionalPrecision(), BigDecimal.ROUND_DOWN);
		assertEquals(bd3, new BigDecimal(sd3.toString()));
	}

	@Test
	public final void testDivide2() {
		boolean debug = false;
		double l = -155.3164923D;
		SimpleDecimal sd1 = new SimpleDecimal(l);
		double s = -13.2173D;
		SimpleDecimal sd2 = new SimpleDecimal(s);
		SimpleDecimal sd3 = sd1.divide(sd2);
		sd3 = new SimpleDecimal(sd3.getValue(), sd3.getFractionalPrecision(),
				debug);
		BigDecimal bd1 = new BigDecimal(sd1.toString());
		bd1 = bd1.setScale(sd3.getFractionalPrecision(), BigDecimal.ROUND_DOWN);
		BigDecimal bd2 = new BigDecimal(sd2.toString());
		bd2 = bd2.setScale(sd3.getFractionalPrecision(), BigDecimal.ROUND_DOWN);
		BigDecimal bd3 = bd1.divide(bd2);
		bd3 = bd3.setScale(sd3.getFractionalPrecision(), BigDecimal.ROUND_DOWN);
		assertEquals(bd3, new BigDecimal(sd3.toString()));
	}

//	@Test
	public final void testDivide3() {
		boolean debug = false;
		double l = -155.316D;
		SimpleDecimal sd1 = new SimpleDecimal(l);
		double s = -13.2173D;
		SimpleDecimal sd2 = new SimpleDecimal(s);
		SimpleDecimal sd3 = sd1.divide(sd2);
		sd3 = new SimpleDecimal(sd3.getValue(), sd3.getFractionalPrecision(),
				debug);
		BigDecimal bd1 = new BigDecimal(sd1.toString());
		bd1 = bd1.setScale(sd3.getFractionalPrecision(), BigDecimal.ROUND_DOWN);
		BigDecimal bd2 = new BigDecimal(sd2.toString());
		bd2 = bd2.setScale(sd3.getFractionalPrecision(), BigDecimal.ROUND_DOWN);
		BigDecimal bd3 = bd1.divide(bd2);
		bd3 = bd3.setScale(sd3.getFractionalPrecision(), BigDecimal.ROUND_DOWN);
		assertEquals(bd3, new BigDecimal(sd3.toString()));
	}

	@Test
	public void testRemainderSimpleDecimal() {
		double l = 155.3164923D;
		SimpleDecimal sd1 = new SimpleDecimal(l);
		double s = 13.2173D;
		SimpleDecimal sd2 = new SimpleDecimal(s);
		SimpleDecimal sd3 = sd1.remainder(sd2);
		
		BigDecimal bd1 = new BigDecimal(sd1.toString());
		bd1 = bd1.setScale(sd3.getFractionalPrecision(), BigDecimal.ROUND_DOWN);
		BigDecimal bd2 = new BigDecimal(sd2.toString());
		bd2 = bd2.setScale(sd3.getFractionalPrecision(), BigDecimal.ROUND_DOWN);
		BigDecimal bd3 = bd1.remainder(bd2);

		BigDecimal bd4 = new BigDecimal(sd3.toString());
		
		assertEquals(bd3, bd4);
	}

	@Test
	public final void testAbs() {
		String s = "-5.500";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		SimpleDecimal sd2 = sd1.abs();
		assertEquals(new SimpleDecimal(5.5), sd2);
		s = "3.210";
		sd1 = new SimpleDecimal(s);
		sd2 = sd1.abs();
		assertEquals(new SimpleDecimal(3.21), sd2);
		s = "-.1";
		sd1 = new SimpleDecimal(s);
		sd2 = sd1.abs();
		assertEquals(new SimpleDecimal(0.1), sd2);
	}

	@Test
	public final void testNegate() {
		String s = "-5.500";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		SimpleDecimal sd2 = sd1.negate();
		assertEquals(0, sd1.add(sd2).compareTo(SimpleDecimal.ZERO));
		s = "3.210";
		sd1 = new SimpleDecimal(s);
		sd2 = sd1.negate();
		assertEquals(0, sd1.add(sd2).compareTo(SimpleDecimal.ZERO));
	}

	@Test
	public final void testPlus() {
		String s = "-5.500";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		SimpleDecimal sd2 = sd1.plus();
		assertEquals(0, sd1.add(sd2).compareTo(SimpleDecimal.ZERO));
		s = "3.210";
		sd1 = new SimpleDecimal(s);
		sd2 = sd1.plus();
		assertEquals(true, sd1.equals(sd2));
	}

	@Test
	public final void testMax() {
		String s = "5.500";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		s = "5.51";
		SimpleDecimal sd2 = new SimpleDecimal(s);
		SimpleDecimal sd3 = sd1.max(sd2);
		assertEquals(sd2, sd3);
		sd3 = sd2.max(sd1);
		assertEquals(sd2, sd3);
	}

	@Test
	public final void testMin() {
		String s = "-5.500";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		s = "5.5";
		SimpleDecimal sd2 = new SimpleDecimal(s);
		SimpleDecimal sd3 = sd1.min(sd2);
		assertEquals(sd1, sd3);
		sd3 = sd2.min(sd1);
		assertEquals(sd1, sd3);
	}

	@Test
	public final void testPow() {
		double d = 1.09D;
		String s = String.valueOf(d);
		SimpleDecimal sd1 = new SimpleDecimal(s);
		assertEquals(SimpleDecimal.ONE, sd1.pow(0));
		BigDecimal bd1 = new BigDecimal(sd1.toString());
		bd1 = bd1.setScale(sd1.getFractionalPrecision(), BigDecimal.ROUND_HALF_UP);
		int maxPower = 9;
		for (int power = 1; power <= maxPower; power++) {
			SimpleDecimal sd2 = sd1.pow(power);
//			System.out.printf("%s '%s'.pow(%d)=%s%n", sd1.getClass().getSimpleName(), sd1.toString(), power, sd2.toString());
			BigDecimal bd2 = bd1.pow(power);
//			bd2 = bd2.setScale(sd2.getFractionalPrecision(), BigDecimal.ROUND_HALF_UP);
//			System.out.printf("%s '%s'.pow(%d)=%s%n", bd1.getClass().getSimpleName(), bd1.toString(), power, bd2.toString());
			assertEquals(bd2, new BigDecimal(sd2.toString()));

			sd2 = sd1.pow(-power);
			SimpleDecimal sd3 = sd1.pow(power);
//			System.out.printf("%s '%s'.pow(%d)=%s%n", sd1.getClass().getSimpleName(), sd1.toString(), power, sd2.toString());
//			bd2 = bd2.setScale(sd2.getFractionalPrecision(), BigDecimal.ROUND_HALF_UP);
//			System.out.printf("%s '%s'.pow(%d)=%s%n", bd1.getClass().getSimpleName(), bd1.toString(), power, bd2.toString());
			assertEquals(sd2, SimpleDecimal.ONE.divide(sd3));
		}
	}

	@Test
	public final void testSignum() {
		String s = "-5.500";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		assertEquals(-1, sd1.signum());
		s = "980353435.5";
		sd1 = new SimpleDecimal(s);
		assertEquals(1, sd1.signum());
		s = "0";
		sd1 = new SimpleDecimal(s);
		assertEquals(0, sd1.signum());
	}

	@Test
	public final void testStripTrailingZeros() {
		String s = "5.500";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		SimpleDecimal sd2 = sd1.stripTrailingZeros();
		assertEquals(1, sd2.getFractionalPrecision());
	}

	@Test
	public final void testSimplify() {
		String s = "5.500";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		SimpleDecimal sd2 = sd1.simplify();
		int result = sd1.compareTo(sd2);
		assertEquals(0, result);
	}

	@Test
	public final void testCompareTo() {
		String s = "5.500";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		assertEquals(sd1, sd1);
		SimpleDecimal sd2 = sd1.stripTrailingZeros();
		assertEquals(sd2.compareTo(sd1), -sd1.compareTo(sd2));
	}

	@Test
	public final void testCompare() {
		String s = "5.500";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		assertEquals(sd1, sd1);
		SimpleDecimal sd2 = sd1.stripTrailingZeros();
		assertEquals(sd1, sd2);
		assertEquals(SimpleDecimal.compare(sd1, sd2),
				SimpleDecimal.compare(sd2, sd1));
	}

	@Test
	public void testRound() throws Exception {
		String s = "5.4944";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		SimpleDecimal sd2;
		sd2 = sd1.round(6);
		assertEquals("5.4944", sd2.toString());
		sd2 = sd1.round(3);
		assertEquals("5.494", sd2.toString());
		sd2 = sd1.round(2);
		assertEquals("5.49", sd2.toString());
		sd2 = sd1.round(1);
		assertEquals("5.5", sd2.toString());
	}

	@Test
	public final void testPrecision() {
		int max = 1000000;
		String str = "0.000001";
		SimpleDecimal sd1, sd2, sd3, sd4, sd5, sd6;
		sd1 = new SimpleDecimal(str);
		sd2 = SimpleDecimal.ZERO;

		for (int i = 0; i < max; i++) {
			sd2 = sd2.add(sd1);
		}
		sd3 = sd2;
		String out;

		for (int i = 0; i < max; i++) {
			sd2 = sd2.subtract(sd1);
		}

		double startValue = 0.0D;
		double result = startValue;
		double incr = 0.00001D;
		for (int i = 0; i < max; i++) {
			result += incr;
		}
		result = incr * max;
	}

	@Test
	public void testEqualsObject() throws Exception {
		String s = "5.4944";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		SimpleDecimal sd2;
		sd2 = sd1.round(6);
		assertFalse(sd1.equals(null));
		assert(sd1.equals((Object) sd1));
		assert(sd1.equals((Object) sd2));
	}

	@Test
	public void testEqualsSimpleDecimal() throws Exception {
		String s = "5.4944";
		SimpleDecimal sd1 = new SimpleDecimal(s);
		SimpleDecimal sd2;
		sd2 = sd1.round(6);
		assertFalse(sd1.equals(null));
		assertEquals(sd1, sd1);
		assertEquals(sd1, sd2);
	}
}
