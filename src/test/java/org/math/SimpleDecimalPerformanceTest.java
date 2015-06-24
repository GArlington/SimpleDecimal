package org.math;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SimpleDecimalPerformanceTest {
	int testLevel, v1Scale, v1Precision, v2Scale, v2Precision, maxIterations,
			maxPower;
	boolean testVeryBigDecimal = false;
	Random r;

	@Before
	public void setUp() {
		testLevel = 5;

		v1Scale = 10_000_000;
		v1Precision = 100;
		v2Scale = 10_000;
		v2Precision = 1_000;

		maxIterations = 1_000_000;
		maxPower = 5;

		r = new Random();
	}

	@After
	public void tearDown() {
//		super.tearDown();
	}

	@Test
	public final void testPerformance() throws NumericStringsPairException {
		for (int level = 0; level <= testLevel; level++) {
			testPerformance(level);
		}
	}

	/*
	 * private final <T> void testPerformance(T instance1, T instance2) throws
	 * NumericStringsPairException { long time1, time2, s1; int nextLevel;
	 * 
	 * time1 = System.nanoTime(); { T sd1 = null, sd2, sd3, sd4, sd5, sd6, sd7;
	 * for (int i = 0; i < maxIterations; i++) { double value1 = ((double)
	 * (r.nextInt(v1Scale * v1Precision) + 1)) / ((double) v1Precision); double
	 * value2 = ((double) (r.nextInt(v2Scale * v2Precision) + 1)) / ((double)
	 * v2Precision);
	 * 
	 * sd1 = instance1.(value1); sd2 = new SimpleDecimal(value2); nextLevel = 1;
	 * if (testLevel >= nextLevel++) { sd3 = sd1.add(sd2); // 1 if (testLevel >=
	 * nextLevel++) { sd4 = sd3.subtract(sd2); // 2 assertEquals(0,
	 * sd1.compareTo(sd4)); if (testLevel >= nextLevel++) { sd5 =
	 * sd1.multiply(sd2); // 3 if (testLevel >= nextLevel++) { sd6 =
	 * sd5.divide(sd2); // 4 // assertEquals("sd1=" + sd1 + ", sd6=" + sd6, //
	 * 0, sd1.compareTo(sd6)); if (testLevel >= nextLevel++) { int power =
	 * r.nextInt(maxPower); sd7 = sd1.pow(power); // 5 } } } } }
	 * 
	 * assertEquals(0, sd1.compareTo(sd6)); assertEquals("sd1=" + sd1 + ", sd6="
	 * + sd6, 0, sd1.compareTo(sd6));
	 * 
	 * } time2 = System.nanoTime(); s1 = (time2 - time1); System.out.printf(
	 * "testPerformance %s %,d iterations in %,d ns [%d]%n", sd1.getClass(),
	 * maxIterations, s1, s1 / maxIterations); System.out.println(); } }
	 */

	private final void testPerformance(int testLevel)
			throws NumericStringsPairException {
		long time1, time2, s1;
		int nextLevel;

		time1 = System.nanoTime();
		{
			SimpleDecimal sd1 = null, sd2, sd3, sd4, sd5, sd6, sd7;
			for (int i = 0; i < maxIterations; i++) {
				double value1 = ((double) (r.nextInt(v1Scale * v1Precision) + 1))
						/ ((double) v1Precision);
				double value2 = ((double) (r.nextInt(v2Scale * v2Precision) + 1))
						/ ((double) v2Precision);

				sd1 = new SimpleDecimal(value1);
				sd2 = new SimpleDecimal(value2);
				nextLevel = 1;
				if (testLevel >= nextLevel++) {
					sd3 = sd1.add(sd2); // 1
					if (testLevel >= nextLevel++) {
						sd4 = sd3.subtract(sd2); // 2
						assertEquals(0, sd1.compareTo(sd4));
						if (testLevel >= nextLevel++) {
							sd5 = sd1.multiply(sd2); // 3
							if (testLevel >= nextLevel++) {
								sd6 = sd5.divide(sd2); // 4
								// assertEquals("sd1=" + sd1 + ", sd6=" + sd6,
								// 0, sd1.compareTo(sd6));
								if (testLevel >= nextLevel++) {
									int power = r.nextInt(maxPower);
									sd7 = sd1.pow(power); // 5
								}
							}
						}
					}
				}
				/*
				 * assertEquals(0, sd1.compareTo(sd6)); assertEquals("sd1=" +
				 * sd1 + ", sd6=" + sd6, 0, sd1.compareTo(sd6));
				 */
			}
			time2 = System.nanoTime();
			s1 = (time2 - time1);
			System.out.println();
			System.out.printf(
					"level %d testPerformance %s %,d iterations in %,d ns [%d]%n",
					testLevel, sd1.getClass(), maxIterations, s1, s1 / maxIterations);
		}

		{
			BigDecimal sd1 = null, sd2, sd3, sd4, sd5, sd6, sd7;
			time1 = System.nanoTime();
			for (int i = 0; i < maxIterations; i++) {
				double value1 = ((double) (r.nextInt(v1Scale * v1Precision) + 1))
						/ ((double) v1Precision);
				double value2 = ((double) (r.nextInt(v2Scale * v2Precision) + 1))
						/ ((double) v2Precision);

				sd1 = new BigDecimal(value1);
				sd2 = new BigDecimal(value2);
				nextLevel = 1;
				if (testLevel >= nextLevel++) {
					sd3 = sd1.add(sd2); // 1
					if (testLevel >= nextLevel++) {
						sd4 = sd3.subtract(sd2); // 2
						assertEquals(0, sd1.compareTo(sd4));
						if (testLevel >= nextLevel++) {
							sd5 = sd1.multiply(sd2); // 3
							if (testLevel >= nextLevel++) {
								sd6 = sd5.divide(sd2); // 4
								// assertEquals("sd1=" + sd1 + ", sd6=" + sd6,
								// 0, sd1.compareTo(sd6));
								if (testLevel >= nextLevel++) {
									int power = r.nextInt(maxPower);
									sd7 = sd1.pow(power); // 5
								}
							}
						}
					}
				}
				/*
				 * assertEquals(0, sd1.compareTo(sd6)); assertEquals("sd1=" +
				 * sd1 + ", sd6=" + sd6, 0, sd1.compareTo(sd6));
				 */
			}
			time2 = System.nanoTime();
			s1 = (time2 - time1);
			System.out.printf(
					"level %d testPerformance %s %,d iterations in %,d ns [%d]%n",
					testLevel, sd1.getClass(), maxIterations, s1, s1 / maxIterations);
		}
	}
}
