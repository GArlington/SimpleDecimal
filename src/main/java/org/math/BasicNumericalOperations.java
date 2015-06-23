package org.math;

/**
 * This interface was derived from functionality provided by (mainly) `Bigdecimal`
 * and is now implemented in `SimpleDecimal` and `VeryBigdecimal`
 *
 * @param <T>
 * @author G.Arlington
 */
public interface BasicNumericalOperations<T> extends Comparable<T> {
	T abs();

	T add(final T addend);

	T divide(final T divisor);

	T[] divideAndRemainder(final T divisor);

	double doubleValue();

	float floatValue();

	int intValue();

	long longValue();

	T max(final T value);

	T min(final T value);

	T movePointLeft(int n);

	T movePointRight(int n);

	T multiply(T multiplicand);

	T negate();

	T plus();

	T pow(int n);

	int precision();

	T remainder(T divisor);

	int scale();

	T setScale(int newScale);

	int signum();

	T subtract(T subtrahend);

	T stripTrailingZeros();
}
