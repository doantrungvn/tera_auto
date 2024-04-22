package org.terasoluna.qp.app.common.validation;

public class ValidationConst {
	public static final String ALPHABET_PATTERN = "[A-Za-z]*$";
	public static final String ALPHANUMERIC_PATTERN = "[A-Za-z0-9]*$";
	public final static String BINARY_PATTERN = "[01]+$";
	public static final String CURRENCY_PATTERN = "[0-9]+\\.[0-9][0-9](?:[^0-9]|$)";
	public static final String DECIMAL_PATTERN = "(0|[1-9][0-9]*)$";
	public static final String DOUBLE_PATTERN = "^(?=.+)(?:[1-9]\\d*|0)?(?:\\.\\d+)?$";
	public static final String EMCHARACTER_PATTERN = "^[^ -~｡-ﾟ]+$";
	public static final String ENCHARACTER_PATTERN = "[ -~｡-ﾟ]+$";
	public static final String FLOAT_PATTERN = "^(?=.+)(?:[1-9]\\d*|0)?(?:\\.\\d+)?$";
	public static final String INTEGER_PATTERN = "^[0-9]{1,10}$";
	public static final String KANJI_PATTERN = "([一-龯])";
	public static final String KATAKANA_PATTERN = "[ｦ-ﾟ]";
	public static final String LONG_PATTERN = "^[0-9]{1,64}$";
	public static final String PHONE_PATTERN = "^(\\(?\\+?[0-9]*\\)?)?[0-9_\\- \\(\\)]*$";
	public static final String POSTCODE_PATTERN = "^\\d{3}-\\d{4}$|^\\d{3}-\\d{2}$|^\\d{3}$";
	public static final String TIME_PATTERN = "(([0-1][0-9])|([2][0-3])):([0-5][0-9]):([0-5][0-9]))";
	public static final String YEAR_PATTERN = "[0-9]{0,4}$";
	public static final String ZENKAKUALPHABET_PATTERN = "[Ａ-ｚ]";
	public static final String ZENKAKUKATAKANA_PATTERN = "[ァ-ヴ]";
	public static final String ZENKAKUNUMERIC_PATTERN = "[０-９]";
	public final static String ZENKAKUSYMBOL_PATTERN = "[\\x{3000}-\\x{303F}]";
	public static final String SPACE_PATTERN = "[\\x20]";
	public static final String SYMBOL_PATTERN = "[!-/:-@\\[-\\`\\{-\\~]+$";
	public static final String MOBILE_PHONE_JAPAN_PATTERN_TYPE_1 = "^\\d{3}-\\d{4}-\\d{4}$|^\\d{11}$";
	public static final String MOBILE_PHONE_JAPAN_PATTERN_TYPE_2	 = "^0\\d0-\\d{4}-\\d{4}$";
	public static final String LINE_PHONE_JAPAN_PATTERN_TYPE_1 = "^[0-9-]{6,9}$|^[0-9-]{12}$";
	public static final String LINE_PHONE_JAPAN_PATTERN_TYPE_2	 = "^\\d{1,4}-\\d{4}$|^\\d{2,5}-\\d{1,4}-\\d{4}$";
}