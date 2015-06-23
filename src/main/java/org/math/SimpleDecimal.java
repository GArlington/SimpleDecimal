package org.math;

/**
 * Simplified implementation of decimal data type with functionality of BigDecimal,
 * but (hopefully) better performance.
 * If you need to handle big values use BigDecimal instead
 * <p>
 * Re: performance -
 * From timing my unit tests it looks like my implementation of
 * multiply(SimpleDecimal multiplicand)
 * and [very noticeably]
 * divide(SimpleDecimal divisor)
 * methods are considerably faster than an equivalent implementation in BigDecimal.
 *
 * @author G.Arlington
 */

import java.text.DecimalFormatSymbols;

public class SimpleDecimal extends Number implements
		BasicNumericalOperations<SimpleDecimal> {
	public static final SimpleDecimal ZERO = new SimpleDecimal(0);
	public static final SimpleDecimal ONE = new SimpleDecimal(1);
	public static final SimpleDecimal TEN = new SimpleDecimal(10);
	public static final SimpleDecimal HUNDRED = new SimpleDecimal(100);
	private static final long serialVersionUID = 4814635367798082279L;
	private static final char decimalSeparator = DecimalFormatSymbols.getInstance().getDecimalSeparator();
	private final long value;
	private final int fractionalPrecision;
	private final boolean debug;

	public SimpleDecimal(final double value) {
		this(String.valueOf(value));
	}

	public SimpleDecimal(final float value) {
		this(String.valueOf(value));
	}

	public SimpleDecimal(final int value) {
		this((long) value);
	}

	public SimpleDecimal(final long value) {
		this(value, 0);
	}

	public SimpleDecimal(final long value, final int fractionalPrecision) {
		this(value, fractionalPrecision, false);
	}

	public SimpleDecimal(final long value, final int fractionalPrecision,
	                     final boolean debug) {
		this.value = value;
		this.fractionalPrecision = fractionalPrecision;
		this.debug = debug;
	}

	/**
	 * This constructor expects standard representation of numerical value as
	 * returned by String.valueOf(...)
	 *
	 * @param value
	 */
	public SimpleDecimal(final String value) {
		this(getValueFromString(value), getScaleFromString(value));
	}

	private static SimpleDecimal add(final SimpleDecimal augend,
	                                 final SimpleDecimal addend) {
		SimpleDecimal lAugend, lAddend;
		int fp = augend.getFractionalPrecision();
		if (fp < addend.getFractionalPrecision()) {
			fp = addend.getFractionalPrecision();
			lAugend = setScale(augend, fp);
			lAddend = addend;
		} else {
			lAugend = augend;
			lAddend = setScale(addend, fp);
		}
		return new SimpleDecimal(lAugend.getValue() + lAddend.getValue(), fp,
				lAugend.isDebug());
	}

	private static SimpleDecimal divide(final SimpleDecimal dividend,
	                                    final SimpleDecimal divisor) {
		SimpleDecimal lDividend, lDivisor;
		int fp = dividend.getFractionalPrecision();
		if (fp < divisor.getFractionalPrecision()) {
			fp = divisor.getFractionalPrecision();
			lDividend = setScale(dividend, fp + fp);
			lDivisor = divisor;
		} else {
			lDividend = setScale(dividend, fp + fp);
			lDivisor = setScale(divisor, fp);
		}
		return new SimpleDecimal(lDividend.getValue() / lDivisor.getValue(),
				fp, lDividend.isDebug());
	}

	private static int getScaleFromString(final String value) {
		int fps = value.lastIndexOf(decimalSeparator);
		if (fps < 0) {
			fps = 0;
		} else {
			fps = value.length() - fps - 1;
		}
		return fps;
	}

	private static long getValueFromString(final String value) {
		int fps = value.lastIndexOf(decimalSeparator);
		String strValue;
		if (fps > 0) {
			strValue = value.substring(0, fps) + value.substring(fps + 1);
		} else {
			strValue = value.substring(fps + 1);
		}
		return (Long.valueOf(strValue).longValue());
	}

	private static SimpleDecimal max(final SimpleDecimal o1,
	                                 final SimpleDecimal o2) {
		if (o1.compareTo(o2) >= 0) {
			return o1;
		}
		return o2;
	}

	private static SimpleDecimal min(final SimpleDecimal o1,
	                                 final SimpleDecimal o2) {
		if (o1.compareTo(o2) <= 0) {
			return o1;
		}
		return o2;
	}

	private static SimpleDecimal multiply(final SimpleDecimal multiplicand,
	                                      final SimpleDecimal multiplier) {
		return new SimpleDecimal(multiplicand.getValue()
				* multiplier.getValue(), multiplicand.getFractionalPrecision()
				+ multiplier.getFractionalPrecision(), multiplicand.isDebug());
	}

	private static SimpleDecimal remainder(final SimpleDecimal dividend,
	                                       final SimpleDecimal divisor) {
		SimpleDecimal lDivisor = setScale(divisor,
				dividend.getFractionalPrecision());
		return new SimpleDecimal(dividend.getValue() % lDivisor.getValue(),
				dividend.getFractionalPrecision(), dividend.isDebug());
	}

	private static SimpleDecimal round(final SimpleDecimal o,
	                                   final int fractionalPrecision) {
		if (o.getFractionalPrecision() <= fractionalPrecision) {
			return o;
		} else {
			return simplify(o, fractionalPrecision, true);
		}
	}

	private static SimpleDecimal setScale(final SimpleDecimal o,
	                                      final int fractionalPrecision) {
		if (o.getFractionalPrecision() == fractionalPrecision) {
			return o;
		}
		if (o.getFractionalPrecision() > fractionalPrecision) {
			return simplify(o, fractionalPrecision);
		}
		return o.incFractionalPrecision(fractionalPrecision
				- o.getFractionalPrecision());
	}

	private static SimpleDecimal simplify(final SimpleDecimal o) {
		return simplify(o, 0);
	}

	private static SimpleDecimal simplify(final SimpleDecimal o,
	                                      final int fractionalPrecision) {
		return simplify(o, fractionalPrecision, false);
	}

	private static SimpleDecimal simplify(final SimpleDecimal o,
	                                      final int fractionalPrecision, final boolean round) {
		long lValue = o.getValue();
		long remainder = lValue % 10;
		if (!round) {
			if (!((o.getFractionalPrecision() > fractionalPrecision) && (remainder == 0))) {
				return o;
			}
		}
		int lFractionalPrecision = o.getFractionalPrecision();
		do {
			lValue /= 10;
			if (remainder >= 5) {
				lValue++;
			}
			remainder = lValue % 10;
			lFractionalPrecision--;
		} while ((lFractionalPrecision > fractionalPrecision)
				&& (round || (remainder == 0)));
		return new SimpleDecimal(lValue, lFractionalPrecision, o.isDebug());
	}

	private static SimpleDecimal subtract(final SimpleDecimal minuend,
	                                      final SimpleDecimal subtrahend) {
		SimpleDecimal lMinuend, lSubtrahend;
		int fp = minuend.getFractionalPrecision();
		if (fp < subtrahend.getFractionalPrecision()) {
			fp = subtrahend.getFractionalPrecision();
			lMinuend = setScale(minuend, fp);
			lSubtrahend = subtrahend;
		} else {
			lMinuend = minuend;
			lSubtrahend = setScale(subtrahend, fp);
		}
		return new SimpleDecimal(lMinuend.getValue() - lSubtrahend.getValue(),
				fp, lMinuend.isDebug());
	}

	public static int compare(final SimpleDecimal o1, final SimpleDecimal o2) {
		int result = 0;
		if (o1.getFractionalPrecision() == o2.getFractionalPrecision()) {
			return (int) (o1.getValue() - o2.getValue());
		}
		SimpleDecimal sd1 = simplify(o1);
		SimpleDecimal sd2 = simplify(o2);
		int maxFractionalPrecision = sd1.getFractionalPrecision();
		if (sd2.getFractionalPrecision() > maxFractionalPrecision) {
			maxFractionalPrecision = sd2.getFractionalPrecision();
		}
		sd1 = setScale(sd1, maxFractionalPrecision);
		sd2 = setScale(sd2, maxFractionalPrecision);
		if (sd1.getFractionalPrecision() == sd2.getFractionalPrecision()) {
			return (int) (sd1.getValue() - sd2.getValue());
		}
		return result;
	}

	private String debugToString() {
		return getClass().getSimpleName() + " [" + "value=" + value
				+ ", fractionalPrecision=" + fractionalPrecision + "]";
	}

	private String stdToString() {
		String strValue = String.valueOf(getValue());
		String str;
		if (getFractionalPrecision() > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = strValue.length(); i < getFractionalPrecision() + 1; i++) {
				sb.append('0');
			}
			sb.append(strValue);
			strValue = sb.toString();

			int dpp = strValue.length() - getFractionalPrecision();
			int p = 0;
			str = ((getValue() >= 0) ? "" : strValue.substring(0, ++p))
					+ ((dpp == p) ? "0" : strValue.substring(p, dpp))
					+ decimalSeparator + strValue.substring(dpp);
		} else {
			str = strValue;
		}
		return str;
	}

	public SimpleDecimal abs() {
		if (this.getValue() >= 0) {
			return this;
		}
		return new SimpleDecimal(-this.getValue(),
				this.getFractionalPrecision(), this.isDebug());
	}

	public SimpleDecimal add(final SimpleDecimal addend) {
		return add(this, addend);
	}

	public int compareTo(final SimpleDecimal o) {
		return compare(this, o);
	}

	public SimpleDecimal decFractionalPrecision() {
		int fractionalPrecision = 1;
		return decFractionalPrecision(fractionalPrecision);
	}

	public SimpleDecimal decFractionalPrecision(final int fractionalPrecision) {
		int lFractionalPrecision;
		if (fractionalPrecision > this.fractionalPrecision) {
			lFractionalPrecision = this.fractionalPrecision;
		} else {
			lFractionalPrecision = fractionalPrecision;
		}
		long lvalue = this.value;
		for (int i = 0; i < fractionalPrecision; i++) {
			lvalue /= 10;
		}
		lFractionalPrecision = this.fractionalPrecision - lFractionalPrecision;
		return new SimpleDecimal(lvalue, lFractionalPrecision, this.isDebug());
	}

	public SimpleDecimal divide(final SimpleDecimal divisor) {
		return divide(this, divisor);
	}

	public SimpleDecimal[] divideAndRemainder(SimpleDecimal divisor) {
		SimpleDecimal[] result = new SimpleDecimal[2];
		result[0] = divide(divisor);
		result[1] = remainder(divisor);
		return result;
	}

	@Override
	public double doubleValue() {
		return Double.parseDouble(toString());
	}

	@Override
	public boolean equals(final Object o) {
		if (null == o)
			return false;
		if (!this.getClass().isInstance(o))
			return false;
		SimpleDecimal sd = (SimpleDecimal) o;
		return equals(sd);
	}

	public boolean equals(SimpleDecimal o) {
		if (null == o)
			return false;
		boolean result = false;
		if (compareTo(o) == 0) {
			result = true;
		}
		return result;
	}

	@Override
	public float floatValue() {
		return (float) doubleValue();
	}

	public int getFractionalPrecision() {
		return fractionalPrecision;
	}

	public long getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		// TODO - Find better way
		return toString().hashCode();
	}

	public SimpleDecimal incFractionalPrecision() {
		int fractionalPrecision = 1;
		return incFractionalPrecision(fractionalPrecision);
	}

	public SimpleDecimal incFractionalPrecision(final int fractionalPrecision) {
		long lvalue = this.value;
		for (int i = 0; i < fractionalPrecision; i++) {
			lvalue *= 10;
		}
		int lfractionalPrecision = this.fractionalPrecision
				+ fractionalPrecision;
		return new SimpleDecimal(lvalue, lfractionalPrecision, this.isDebug());
	}

	@Override
	public int intValue() {
		return (int) longValue();
	}

	public boolean isDebug() {
		return debug;
	}

	@Override
	public long longValue() {
		return (long) doubleValue();
	}

	public SimpleDecimal max(final SimpleDecimal o2) {
		return max(this, o2);
	}

	public SimpleDecimal min(final SimpleDecimal o2) {
		return min(this, o2);
	}

	public SimpleDecimal movePointLeft(final int n) {
		return incFractionalPrecision(n);
	}

	public SimpleDecimal movePointRight(final int n) {
		return decFractionalPrecision(n);
	}

	public SimpleDecimal multiply(final SimpleDecimal multiplicand) {
		return multiply(this, multiplicand);
	}

	public SimpleDecimal negate() {
		if (this.getValue() == 0) {
			return this;
		}
		return new SimpleDecimal(-this.getValue(),
				this.getFractionalPrecision(), this.isDebug());
	}

	public SimpleDecimal plus() {
		if (this.getValue() >= 0) {
			return this;
		}
		return new SimpleDecimal(-this.getValue(),
				this.getFractionalPrecision(), this.isDebug());
	}

	public SimpleDecimal pow(final int power) {
		SimpleDecimal result;
		if (power == 0) {
			return SimpleDecimal.ONE;
		} else if (power == 1) {
			return this;
		} else if (power > 1) {
			result = this.multiply(this);
//			int f = result.getFractionalPrecision();
			for (int i = 2; i < power; i++) {
//				result = result.round(f);
				result = result.multiply(this);
			}
//			result = result.round(f);
		} else { // power < 0
			result = SimpleDecimal.ONE.divide(this.pow(-power));
		}
		return result;
	}

	public int precision() {
		return String.valueOf(getValue()).length();
	}

	public SimpleDecimal remainder(final SimpleDecimal divisor) {
		return remainder(this, divisor);
	}

	public SimpleDecimal round(final int fractionalPrecision) {
		return round(this, fractionalPrecision);
	}

	public int scale() {
		return getFractionalPrecision();
	}

	public SimpleDecimal setScale(final int fractionalPrecision) {
		return setScale(this, fractionalPrecision);
	}

	public int signum() {
		int result;
		if (getValue() > 0) {
			result = 1;
		} else if (getValue() < 0) {
			result = -1;
		} else {
			result = 0;
		}
		return result;
	}

	public SimpleDecimal simplify() {
		return simplify(0);
	}

	public SimpleDecimal simplify(final int fractionalPrecision) {
		return simplify(this, fractionalPrecision);
	}

	public SimpleDecimal stripTrailingZeros() {
		return simplify();
	}

	public SimpleDecimal subtract(final SimpleDecimal subtrahend) {
		return subtract(this, subtrahend);
	}

	@Override
	public String toString() {
		String str;
		if (isDebug()) {
			// Use this for debugging
			str = debugToString();
		} else {
			// Use this for normal output
			str = stdToString();
		}
		return str;
	}
}
