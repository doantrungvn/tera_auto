package org.terasoluna.qp.app.common.ultils;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class GenericValidator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private static final UrlValidator URL_VALIDATOR = new UrlValidator();

	// private static final CreditCardValidator CREDIT_CARD_VALIDATOR = new CreditCardValidator();

	public static boolean isBlankOrNull(String value) {
		return StringUtils.isBlank(value);
	}

	/*
	 * public static boolean matchRegexp(String value, String regexp) { if ((regexp == null) || (regexp.length() <= 0)) { return false; }
	 * 
	 * Perl5Util matcher = new Perl5Util(); return matcher.match("/" + regexp + "/", value); }
	 */

	public static boolean isByte(String value) {
		return FormatUtils.formatByte(value) != null;
	}

	public static boolean isShort(String value) {
		return FormatUtils.formatShort(value) != null;
	}

	public static boolean isInt(String value) {
		return FormatUtils.formatInt(value) != null;
	}

	public static boolean isLong(String value) {
		return FormatUtils.formatLong(value) != null;
	}

	public static boolean isFloat(String value) {
		return FormatUtils.formatFloat(value) != null;
	}

	public static boolean isDouble(String value) {
		return FormatUtils.formatDouble(value) != null;
	}

	/*
	 * public static boolean isDate(String value, Locale locale) { return DateValidator.getInstance().isValid(value, locale); }
	 * 
	 * public static boolean isDate(String value, String datePattern, boolean strict) { return DateValidator.getInstance().isValid(value, datePattern, strict); }
	 */

	public static boolean isInRange(byte value, byte min, byte max) {
		return (value >= min) && (value <= max);
	}

	public static boolean isInRange(int value, int min, int max) {
		return (value >= min) && (value <= max);
	}

	public static boolean isInRange(float value, float min, float max) {
		return (value >= min) && (value <= max);
	}

	public static boolean isInRange(short value, short min, short max) {
		return (value >= min) && (value <= max);
	}

	public static boolean isInRange(long value, long min, long max) {
		return (value >= min) && (value <= max);
	}

	public static boolean isInRange(double value, double min, double max) {
		return (value >= min) && (value <= max);
	}

	/*
	 * public static boolean isCreditCard(String value) { return CREDIT_CARD_VALIDATOR.isValid(value); }
	 * 
	 * public static boolean isEmail(String value) { return EmailValidator.getInstance().isValid(value); }
	 * 
	 * public static boolean isUrl(String value) { return URL_VALIDATOR.isValid(value); }
	 */

	public static boolean maxLength(String value, int max) {
		return value.length() <= max;
	}

	public static boolean maxLength(String value, int max, int lineEndLength) {
		int adjustAmount = adjustForLineEnding(value, lineEndLength);
		return value.length() + adjustAmount <= max;
	}

	public static boolean minLength(String value, int min) {
		return value.length() >= min;
	}

	public static boolean minLength(String value, int min, int lineEndLength) {
		int adjustAmount = adjustForLineEnding(value, lineEndLength);
		return value.length() + adjustAmount >= min;
	}

	private static int adjustForLineEnding(String value, int lineEndLength) {
		int nCount = 0;
		int rCount = 0;
		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == '\n') {
				nCount++;
			}
			if (value.charAt(i) == '\r') {
				rCount++;
			}
		}
		return nCount * lineEndLength - (rCount + nCount);
	}

	public static boolean minValue(int value, int min) {
		return value >= min;
	}

	public static boolean minValue(long value, long min) {
		return value >= min;
	}

	public static boolean minValue(double value, double min) {
		return value >= min;
	}

	public static boolean minValue(float value, float min) {
		return value >= min;
	}

	public static boolean maxValue(int value, int max) {
		return value <= max;
	}

	public static boolean maxValue(long value, long max) {
		return value <= max;
	}

	public static boolean maxValue(double value, double max) {
		return value <= max;
	}

	public static boolean maxValue(float value, float max) {
		return value <= max;
	}
}