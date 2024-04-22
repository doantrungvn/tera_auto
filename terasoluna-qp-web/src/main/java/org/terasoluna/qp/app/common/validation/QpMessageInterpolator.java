package org.terasoluna.qp.app.common.validation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.MessageInterpolator;
import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.internal.engine.MessageInterpolatorContext;
import org.springframework.context.MessageSource;
import org.terasoluna.qp.app.common.ultils.LocaleUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;

public class QpMessageInterpolator implements MessageInterpolator{
	
	MessageSource messageSource;
	/*
	 * Message for Hibernate Validator
	 */
	public final static String MESSAGE_EMAIL = "err.sys.0012";
	public final static String MESSAGE_NOTEMPTY = "err.sys.0025";
	public final static String MESSAGE_RANGE = "err.sys.0024 ";
	public final static String MESSAGE_NOTBLANK = "err.sys.0061";
	public final static String MESSAGE_URL = "err.sys.0060";
	public final static String MESSAGE_CREDITCARDNUMBER = "err.sys.0004";
	/*
	 * Message for JAVA Validator
	 */
	public final static String MESSAGE_ASSERTFALSE = "err.sys.0070";
	public final static String MESSAGE_ASSERTTRUE = "err.sys.0071";
	public final static String MESSAGE_DECIMALMAX = "err.sys.0113";
	public final static String MESSAGE_DECIMALMIN = "err.sys.0112";
	public final static String MESSAGE_DIGITS = "err.sys.0069";
	public final static String MESSAGE_FUTURE = "err.sys.0068";
	public final static String MESSAGE_LENGTH = "err.sys.0067";
	public final static String MESSAGE_MAX = "err.sys.0050";
	public final static String MESSAGE_MIN = "err.sys.0042";
	public final static String MESSAGE_NOTNULL = "err.sys.0062";
	public final static String MESSAGE_NULL = "err.sys.0063";
	public final static String MESSAGE_PAST = "err.sys.0065";
	public final static String MESSAGE_PATTERN = "err.sys.0129";
	public final static String MESSAGE_PATTERN_NAME = "err.sys.0126";
	public final static String MESSAGE_PATTERN_CODE = "err.sys.0066";
	
	public final static String MESSAGE_SIZE = "err.sys.0064";
	public final static String MESSAGE_VALID = "err.sys.0018";
	
	public final static String MESSAGE_ANPHABET = "err.sys.0157";
	public final static String MESSAGE_ALPHANUMERIC = "err.sys.0159";
	public final static String MESSAGE_BINARY = "err.sys.0161";
	public final static String MESSAGE_CURRENCY = "err.sys.0163";
	public final static String MESSAGE_DECIMAL = "err.sys.0165";
	public final static String MESSAGE_DOUBLE = "err.sys.0167";
	public final static String MESSAGE_EM_CHARACTER = "err.sys.0135";
	public final static String MESSAGE_EN_CHARACTER = "err.sys.0137";
	public final static String MESSAGE_FLOAT = "err.sys.0169";
	public final static String MESSAGE_HIRAGANA = "err.sys.0139";
	public final static String MESSAGE_INTEGER = "err.sys.0171";
	public final static String MESSAGE_KANJI = "err.sys.0143";
	public final static String MESSAGE_KATAKANA = "err.sys.0141";
	public final static String MESSAGE_LONG = "err.sys.0173";
	public final static String MESSAGE_PHONE = "err.sys.0145";
	public final static String MESSAGE_POSTCODE = "err.sys.0147";
	public final static String MESSAGE_TIME = "err.sys.0177";
	public final static String MESSAGE_YEAR = "err.sys.0179";
	public final static String MESSAGE_ZENKAKU_ALPHABET = "err.sys.0151";
	public final static String MESSAGE_ZENKAKU_KATAKANA = "err.sys.0153";
	public final static String MESSAGE_ZENKAKU_NUMERIC = "err.sys.0155";
	public final static String MESSAGE_ZENKAKU_SYMBOL = "err.sys.0181";
	
	public final static String MESSAGE_DATE_TIME = "err.sys.0183";
	public final static String MESSAGE_DAY = "err.sys.0197";
	public final static String MESSAGE_HOUR = "err.sys.0195";
	public final static String MESSAGE_MINUTE = "err.sys.0187";
	public final static String MESSAGE_MONTH = "err.sys.0189";
	public final static String MESSAGE_SECOND = "err.sys.0191";
	public final static String MESSAGE_WEEK = "err.sys.0193";
	public final static String MESSAGE_DATE = "err.sys.0005";
	public final static String MESSAGE_TIMESTAMP = "err.sys.0236";
	
	public final static String MESSAGE_DATE_MAX = "err.sys.0212";
	public final static String MESSAGE_TIME_MAX = "err.sys.0213";
	public final static String MESSAGE_DATE_TIME_MAX = "err.sys.0214";
	public final static String MESSAGE_TIMESTAMP_MAX = "err.sys.0215";
	
	public final static String MESSAGE_DATE_MIN = "err.sys.0216";
	public final static String MESSAGE_TIME_MIN = "err.sys.0217";
	public final static String MESSAGE_DATE_TIME_MIN = "err.sys.0218";
	public final static String MESSAGE_TIMESTAMP_MIN = "err.sys.0219";
	
	public final static String MESSAGE_DATE_RANGE = "err.sys.0220";
	public final static String MESSAGE_TIME_RANGE = "err.sys.0221";
	public final static String MESSAGE_DATE_TIME_RANGE = "err.sys.0222";
	public final static String MESSAGE_TIMESTAMP_RANGE = "err.sys.0223";
	
	public final static String MESSAGE_QP_GREATER_THAN = "err.sys.0238";
	public final static String MESSAGE_QP_LESS_THAN = "err.sys.0239";
	public final static String MESSAGE_QP_EQUAL = "err.sys.0242";
	public final static String MESSAGE_QP_NOT_EQUAL = "err.sys.0244";
	
	public final static String MESSAGE_QP_DATE_EQUAL = "err.sys.0246";
	public final static String MESSAGE_QP_DATETIME_EQUAL = "err.sys.0247";
	public final static String MESSAGE_QP_TIME_EQUAL = "err.sys.0248";
	public final static String MESSAGE_QP_TIMESTAMP_EQUAL = "err.sys.0249";
	
	public final static String MESSAGE_QP_DATE_NOT_EQUAL = "err.sys.0254";
	public final static String MESSAGE_QP_DATETIME_NOT_EQUAL = "err.sys.0255";
	public final static String MESSAGE_QP_TIME_NOT_EQUAL = "err.sys.0256";
	public final static String MESSAGE_QP_TIMESTAMP_NOT_EQUAL = "err.sys.0257";
	
	public final static String MESSAGE_QP_FUTURE_DATE = "err.sys.0262";
	public final static String MESSAGE_QP_PAST_DATE = "err.sys.0264";
	
	public final static String MESSAGE_QP_FUTURE_DATETIME = "err.sys.0266";
	public final static String MESSAGE_QP_PAST_DATETIME = "err.sys.0268";
	
	public final static String MESSAGE_QP_FUTURE_TIMESTAMP = "err.sys.0270";
	public final static String MESSAGE_QP_PAST_TIMESTAMP = "err.sys.0272";
	
	public final static String MESSAGE_QP_SYMBOL = "err.sys.0149";
	public final static String MESSAGE_QP_SPACE = "err.sys.0175";

	public static Map<Class<? extends Annotation>, String> mapAnnotation = new HashMap<Class<? extends Annotation>, String>();
	static {
		mapAnnotation.put(AssertFalse.class, MESSAGE_ASSERTFALSE);
		mapAnnotation.put(AssertTrue.class, MESSAGE_ASSERTTRUE);
		mapAnnotation.put(DecimalMax.class, MESSAGE_DECIMALMAX);
		mapAnnotation.put(DecimalMin.class, MESSAGE_DECIMALMIN);
		mapAnnotation.put(Digits.class, MESSAGE_DIGITS);
		mapAnnotation.put(Email.class, MESSAGE_EMAIL);
		mapAnnotation.put(Future.class, MESSAGE_FUTURE);
		mapAnnotation.put(Length.class, MESSAGE_LENGTH);
		mapAnnotation.put(Min.class, MESSAGE_MIN);
		mapAnnotation.put(Max.class, MESSAGE_MAX);
		mapAnnotation.put(QpMax.class, MESSAGE_MAX);
		mapAnnotation.put(QpMin.class, MESSAGE_MIN);
		mapAnnotation.put(NotNull.class, MESSAGE_NOTNULL);
		mapAnnotation.put(NotEmpty.class, MESSAGE_NOTEMPTY);
		mapAnnotation.put(Null.class, MESSAGE_NULL);
		mapAnnotation.put(Past.class, MESSAGE_PAST);
		mapAnnotation.put(Pattern.class, MESSAGE_PATTERN);
		mapAnnotation.put(Range.class, MESSAGE_RANGE);
		mapAnnotation.put(Size.class, MESSAGE_SIZE);
		mapAnnotation.put(NotBlank.class, MESSAGE_NOTBLANK);
		mapAnnotation.put(Valid.class, MESSAGE_VALID);
		mapAnnotation.put(QpCreaditCard.class, MESSAGE_CREDITCARDNUMBER);
		mapAnnotation.put(QpNamePattern.class, MESSAGE_PATTERN_NAME);
		mapAnnotation.put(QpNameSize.class, MESSAGE_SIZE);
		mapAnnotation.put(QpCodePattern.class, MESSAGE_PATTERN_CODE);
		mapAnnotation.put(QpCodeSize.class, MESSAGE_SIZE);
		mapAnnotation.put(QpSqlCodeSize.class, MESSAGE_SIZE);
		mapAnnotation.put(QpRemarkSize.class, MESSAGE_SIZE);
		mapAnnotation.put(QpAlphabet.class, MESSAGE_ANPHABET);
		mapAnnotation.put(QpAlphanumeric.class, MESSAGE_ALPHANUMERIC);
		mapAnnotation.put(QpBinary.class, MESSAGE_BINARY);
		mapAnnotation.put(QpCurrency.class, MESSAGE_CURRENCY);
		mapAnnotation.put(QpDecimal.class, MESSAGE_DECIMAL);
		mapAnnotation.put(QpDouble.class, MESSAGE_DOUBLE);
		mapAnnotation.put(QpEmCharacter.class, MESSAGE_EM_CHARACTER);
		mapAnnotation.put(QpEnCharacter.class, MESSAGE_EN_CHARACTER);
		mapAnnotation.put(QpFloat.class, MESSAGE_FLOAT);
		mapAnnotation.put(QpHiragana.class, MESSAGE_HIRAGANA);
		mapAnnotation.put(QpInteger.class, MESSAGE_INTEGER);
		mapAnnotation.put(QpKanji.class, MESSAGE_KANJI);
		mapAnnotation.put(QpKatakana.class, MESSAGE_KATAKANA);
		mapAnnotation.put(QpLong.class, MESSAGE_LONG);
		mapAnnotation.put(QpPhone.class, MESSAGE_PHONE);
		mapAnnotation.put(QpPostcode.class, MESSAGE_POSTCODE);
		mapAnnotation.put(QpTime.class, MESSAGE_TIME);
		mapAnnotation.put(QpYear.class, MESSAGE_YEAR);
		mapAnnotation.put(QpZenkakuAlphabet.class, MESSAGE_ZENKAKU_ALPHABET);
		mapAnnotation.put(QpZenkakuKatakana.class, MESSAGE_ZENKAKU_KATAKANA);
		mapAnnotation.put(QpZenkakuNumeric.class, MESSAGE_ZENKAKU_NUMERIC);
		mapAnnotation.put(QpZenkakuSymbol.class, MESSAGE_ZENKAKU_SYMBOL);
		
		mapAnnotation.put(QpDateTime.class, MESSAGE_DATE_TIME);
		mapAnnotation.put(QpDay.class, MESSAGE_DAY);
		mapAnnotation.put(QpHour.class, MESSAGE_HOUR);
		mapAnnotation.put(QpMinute.class, MESSAGE_MINUTE);
		mapAnnotation.put(QpMonth.class, MESSAGE_MONTH);
		mapAnnotation.put(QpSecond.class, MESSAGE_SECOND);
		mapAnnotation.put(QpWeek.class, MESSAGE_WEEK);
		mapAnnotation.put(QpDate.class, MESSAGE_DATE);
		mapAnnotation.put(QpTimestamp.class, MESSAGE_TIMESTAMP);
		
		mapAnnotation.put(QpDateMin.class, MESSAGE_DATE_MIN);
		mapAnnotation.put(QpTimeMin.class, MESSAGE_TIME_MIN);
		mapAnnotation.put(QpDateTimeMin.class, MESSAGE_DATE_TIME_MIN);
		mapAnnotation.put(QpTimestampMin.class, MESSAGE_TIMESTAMP_MIN);
		
		mapAnnotation.put(QpDateMax.class, MESSAGE_DATE_MAX);
		mapAnnotation.put(QpTimeMax.class, MESSAGE_DATE_MAX);
		mapAnnotation.put(QpDateTimeMax.class, MESSAGE_DATE_MAX);
		mapAnnotation.put(QpTimestampMax.class, MESSAGE_DATE_MAX);
		
		mapAnnotation.put(QpDateRange.class, MESSAGE_DATE_RANGE);
		mapAnnotation.put(QpTimeRange.class, MESSAGE_DATE_RANGE);
		mapAnnotation.put(QpDateTimeRange.class, MESSAGE_DATE_RANGE);
		mapAnnotation.put(QpTimestampRange.class, MESSAGE_DATE_RANGE);
		
		mapAnnotation.put(QpGreaterThan.class, MESSAGE_QP_GREATER_THAN);
		mapAnnotation.put(QpLessThan.class, MESSAGE_QP_LESS_THAN);
		mapAnnotation.put(QpEqual.class, MESSAGE_QP_EQUAL);
		mapAnnotation.put(QpNotEqual.class, MESSAGE_QP_NOT_EQUAL);
		
		mapAnnotation.put(QpDateEqual.class, MESSAGE_QP_DATE_EQUAL);
		mapAnnotation.put(QpDateTimeEqual.class, MESSAGE_QP_DATETIME_EQUAL);
		mapAnnotation.put(QpTimeEqual.class, MESSAGE_QP_TIME_EQUAL);
		mapAnnotation.put(QpTimestampEqual.class, MESSAGE_QP_TIMESTAMP_EQUAL);
		
		mapAnnotation.put(QpDateNotEqual.class, MESSAGE_QP_DATE_NOT_EQUAL);
		mapAnnotation.put(QpDateTimeNotEqual.class, MESSAGE_QP_DATETIME_NOT_EQUAL);
		mapAnnotation.put(QpTimeNotEqual.class, MESSAGE_QP_TIME_NOT_EQUAL);
		mapAnnotation.put(QpTimestampNotEqual.class, MESSAGE_QP_TIMESTAMP_NOT_EQUAL);
		
		mapAnnotation.put(QpFutureDate.class, MESSAGE_QP_FUTURE_DATE);
		mapAnnotation.put(QpPastDate.class, MESSAGE_QP_PAST_DATE);
		
		mapAnnotation.put(QpFutureDatetime.class, MESSAGE_QP_FUTURE_DATETIME);
		mapAnnotation.put(QpPastDatetime.class, MESSAGE_QP_PAST_DATETIME);
		
		mapAnnotation.put(QpFutureTimestamp.class, MESSAGE_QP_FUTURE_TIMESTAMP);
		mapAnnotation.put(QpPastTimestamp.class, MESSAGE_QP_PAST_TIMESTAMP);
		
		mapAnnotation.put(QpSize.class, MESSAGE_SIZE);
		mapAnnotation.put(URL.class, MESSAGE_URL);
		mapAnnotation.put(QpSymbol.class, MESSAGE_QP_SYMBOL);
		mapAnnotation.put(QpSpace.class, MESSAGE_QP_SPACE);
		mapAnnotation.put(QpDecimalMin.class, MESSAGE_DECIMALMIN);
		mapAnnotation.put(QpDecimalMax.class, MESSAGE_DECIMALMAX);

		mapAnnotation.put(QpReserved.class, MESSAGE_VALID);
		
	}

	public static final String SEPARATOR_MESSAGE = ";";
	QpMessageInterpolator(MessageSource messageSource){
		this.messageSource = messageSource;
	}
	public String interpolate(String message, Context context) {
		String interpolatedMessage = message;
		interpolatedMessage = interpolateMessage( message, context, LocaleUtils.getRequestLocale());
		return interpolatedMessage;
	}

	public String interpolate(String message, Context context, Locale locale) {
		String interpolatedMessage = message;
		interpolatedMessage = interpolateMessage( message, context, locale );
		return interpolatedMessage;
	}
	
	private String interpolateMessage(String message, Context context,Locale locale) {
		String resolveMessage = message;
		Map<String, Object> messageParametersMap = null;
		if (context instanceof MessageInterpolatorContext ) {
			MessageInterpolatorContext internalContext = (MessageInterpolatorContext) context.unwrap(MessageInterpolator.Context.class);
			messageParametersMap = internalContext.getMessageParameters();
		}
		if(StringUtils.isNotEmpty(message)){
			String[] messageParts = message.split(SEPARATOR_MESSAGE);
			if(ArrayUtils.isNotEmpty(messageParts)){
				int startParamIndex = 0;
				String messageCode = "";
				if(mapAnnotation.containsKey(context.getConstraintDescriptor().getAnnotation().annotationType())){
					startParamIndex = 0;
					messageCode = mapAnnotation.get(context.getConstraintDescriptor().getAnnotation().annotationType());
				} else {
					startParamIndex = 1;
					messageCode = messageParts[0];
				}
				String[] params = ArrayUtils.subarray(messageParts, startParamIndex, messageParts.length);
				for(int i=0;i<params.length;i++){
					params[i] = MessageUtils.getMessageFromLocale(params[i],locale);
				}
				switch(context.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName()){
				case "QpNamePattern":
					params = ArrayUtils.add(params, String.valueOf(messageParametersMap.get("mask")));
					break;
				case "QpCodePattern":
					params = ArrayUtils.add(params, String.valueOf(messageParametersMap.get("mask")));
					break;
				case "QpCodeSize":
					params = ArrayUtils.add(params, String.valueOf(messageParametersMap.get("min")));
					params = ArrayUtils.add(params, String.valueOf(messageParametersMap.get("max")));
					break;
				case "QpSqlCodeSize":
					params = ArrayUtils.add(params, String.valueOf(messageParametersMap.get("min")));
					params = ArrayUtils.add(params, String.valueOf(messageParametersMap.get("max")));
					break;
				case "QpNameSize":
					params = ArrayUtils.add(params, String.valueOf(messageParametersMap.get("min")));
					params = ArrayUtils.add(params, String.valueOf(messageParametersMap.get("max")));
					break;
				case "QpRemarkSize":
					params = ArrayUtils.add(params, String.valueOf(messageParametersMap.get("min")));
					params = ArrayUtils.add(params, String.valueOf(messageParametersMap.get("max")));
					break;
				}
				resolveMessage = MessageUtils.getMessageFromLocale(messageCode,locale,params);
			}
		}
		
		return resolveMessage;
	}
}
