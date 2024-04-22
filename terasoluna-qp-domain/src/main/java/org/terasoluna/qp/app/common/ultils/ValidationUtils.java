package org.terasoluna.qp.app.common.ultils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.terasoluna.qp.app.message.AccountMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;

public class ValidationUtils {
	/**
	 * Commons Logging instance.
	 */
	private static final Log log = LogFactory.getLog(ValidationUtils.class);

	/*
	 * Type for BEAN FORM
	 */
	private final static int PARENT_BEANFORM = 0;
	private final static int CHILDREN_BEANFORM = 1;
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
	public final static String MESSAGE_SIZE = "err.sys.0064";
	public final static String MESSAGE_VALID = "err.sys.0018";
	/*
	 * Children Message for Hibernate Validator
	 */
	public final static String MESSAGE_CHILDREN_EMAIL = "err.sys.0076";
	public final static String MESSAGE_CHILDREN_NOTEMPTY = "err.sys.0077";
	public final static String MESSAGE_CHILDREN_RANGE = "err.sys.0078 ";
	public final static String MESSAGE_CHILDREN_NOTBLANK = "err.sys.0079";
	public final static String MESSAGE_CHILDREN_URL = "err.sys.0080";
	public final static String MESSAGE_CHILDREN_CREDITCARDNUMBER = "err.sys.0081";
	/*
	 * Children Message for JAVA Validator
	 */
	public final static String MESSAGE_CHILDREN_ASSERTFALSE = "err.sys.0082";
	public final static String MESSAGE_CHILDREN_ASSERTTRUE = "err.sys.0083";
	public final static String MESSAGE_CHILDREN_DECIMALMAX = "err.sys.0115";
	public final static String MESSAGE_CHILDREN_DECIMALMIN = "err.sys.0114";
	public final static String MESSAGE_CHILDREN_DIGITS = "err.sys.0086";
	public final static String MESSAGE_CHILDREN_FUTURE = "err.sys.0087";
	public final static String MESSAGE_CHILDREN_LENGTH = "err.sys.0088";
	public final static String MESSAGE_CHILDREN_MAX = "err.sys.0113";
	public final static String MESSAGE_CHILDREN_MIN = "err.sys.0112";
	public final static String MESSAGE_CHILDREN_NOTNULL = "err.sys.0091";
	public final static String MESSAGE_CHILDREN_NULL = "err.sys.0092";
	public final static String MESSAGE_CHILDREN_PAST = "err.sys.0093";
	public final static String MESSAGE_CHILDREN_PATTERN = "err.sys.0094";
	public final static String MESSAGE_CHILDREN_SIZE = "err.sys.0095";
	public final static String MESSAGE_CHILDREN_VALID = "err.sys.0096";
	/*
	 * list all constraints available in Hibernate Validator
	 */
	public final static String ANNOTATION_EMAIL = "Email";
	public final static String ANNOTATION_NOTEMPTY = "NotEmpty";
	public final static String ANNOTATION_RANGE = "Range";
	public final static String ANNOTATION_NOTBLANK = "NotBlank";
	public final static String ANNOTATION_URL = "URL";
	public final static String ANNOTATION_CREDITCARDNUMBER = "CreditCardNumber";

	/**
	 * Email pattern
	 */
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * ArtifactId pattern
	 */
	public static final String ARTIFACTID_PATTERN = "^\\w([\\.\\w-](?![\\.]{2,}))*[\\w-]$";

	/*
	 * list all constraints available in JAVA Validator
	 */
	public final static String ANNOTATION_ASSERTFALSE = "AssertFalse";
	public final static String ANNOTATION_ASSERTTRUE = "AssertTrue";
	public final static String ANNOTATION_DECIMALMAX = "DecimalMax";
	public final static String ANNOTATION_DECIMALMIN = "DecimalMin";
	public final static String ANNOTATION_DIGITS = "Digits";
	public final static String ANNOTATION_FUTURE = "Future";
	public final static String ANNOTATION_LENGTH = "Length";
	public final static String ANNOTATION_MAX = "Max";
	public final static String ANNOTATION_MIN = "Min";
	public final static String ANNOTATION_NOTNULL = "NotNull";
	public final static String ANNOTATION_NULL = "Null";
	public final static String ANNOTATION_PAST = "Past";
	public final static String ANNOTATION_PATTERN = "Pattern";
	public final static String ANNOTATION_SIZE = "Size";
	public final static String ANNOTATION_VALID = "Valid";
	public static Map<String, String> mapAnnotation = new HashMap<String, String>();
	static {
		mapAnnotation.put(ANNOTATION_ASSERTFALSE, MESSAGE_ASSERTFALSE);
		mapAnnotation.put(ANNOTATION_ASSERTTRUE, MESSAGE_ASSERTTRUE);
		mapAnnotation.put(ANNOTATION_DECIMALMAX, MESSAGE_DECIMALMAX);
		mapAnnotation.put(ANNOTATION_DECIMALMIN, MESSAGE_DECIMALMIN);
		mapAnnotation.put(ANNOTATION_DIGITS, MESSAGE_DIGITS);
		mapAnnotation.put(ANNOTATION_EMAIL, MESSAGE_EMAIL);
		mapAnnotation.put(ANNOTATION_FUTURE, MESSAGE_FUTURE);
		mapAnnotation.put(ANNOTATION_LENGTH, MESSAGE_LENGTH);
		mapAnnotation.put(ANNOTATION_MAX, MESSAGE_MAX);
		mapAnnotation.put(ANNOTATION_MIN, MESSAGE_MIN);
		mapAnnotation.put(ANNOTATION_NOTNULL, MESSAGE_NOTNULL);
		mapAnnotation.put(ANNOTATION_NOTEMPTY, MESSAGE_NOTEMPTY);
		mapAnnotation.put(ANNOTATION_NULL, MESSAGE_NULL);
		mapAnnotation.put(ANNOTATION_PAST, MESSAGE_PAST);
		mapAnnotation.put(ANNOTATION_PATTERN, MESSAGE_PATTERN);
		mapAnnotation.put(ANNOTATION_RANGE, MESSAGE_RANGE);
		mapAnnotation.put(ANNOTATION_SIZE, MESSAGE_SIZE);
		mapAnnotation.put(ANNOTATION_NOTBLANK, MESSAGE_NOTBLANK);
		mapAnnotation.put(ANNOTATION_VALID, MESSAGE_VALID);
		mapAnnotation.put(ANNOTATION_URL, MESSAGE_URL);
		mapAnnotation.put(ANNOTATION_CREDITCARDNUMBER, MESSAGE_CREDITCARDNUMBER);
	}
	public static Map<String, String> mapAnnotationForChildrenForm = new HashMap<String, String>();
	static {
		mapAnnotationForChildrenForm.put(ANNOTATION_ASSERTFALSE, MESSAGE_CHILDREN_ASSERTFALSE);
		mapAnnotationForChildrenForm.put(ANNOTATION_ASSERTTRUE, MESSAGE_CHILDREN_ASSERTTRUE);
		mapAnnotationForChildrenForm.put(ANNOTATION_DECIMALMAX, MESSAGE_CHILDREN_DECIMALMAX);
		mapAnnotationForChildrenForm.put(ANNOTATION_DECIMALMIN, MESSAGE_CHILDREN_DECIMALMIN);
		mapAnnotationForChildrenForm.put(ANNOTATION_DIGITS, MESSAGE_CHILDREN_DIGITS);
		mapAnnotationForChildrenForm.put(ANNOTATION_EMAIL, MESSAGE_CHILDREN_EMAIL);
		mapAnnotationForChildrenForm.put(ANNOTATION_FUTURE, MESSAGE_CHILDREN_FUTURE);
		mapAnnotationForChildrenForm.put(ANNOTATION_LENGTH, MESSAGE_CHILDREN_LENGTH);
		mapAnnotationForChildrenForm.put(ANNOTATION_MAX, MESSAGE_CHILDREN_MAX);
		mapAnnotationForChildrenForm.put(ANNOTATION_MIN, MESSAGE_CHILDREN_MIN);
		mapAnnotationForChildrenForm.put(ANNOTATION_NOTNULL, MESSAGE_CHILDREN_NOTNULL);
		mapAnnotationForChildrenForm.put(ANNOTATION_NOTEMPTY, MESSAGE_CHILDREN_NOTEMPTY);
		mapAnnotationForChildrenForm.put(ANNOTATION_NULL, MESSAGE_CHILDREN_NULL);
		mapAnnotationForChildrenForm.put(ANNOTATION_PAST, MESSAGE_CHILDREN_PAST);
		mapAnnotationForChildrenForm.put(ANNOTATION_PATTERN, MESSAGE_CHILDREN_PATTERN);
		mapAnnotationForChildrenForm.put(ANNOTATION_RANGE, MESSAGE_CHILDREN_RANGE);
		mapAnnotationForChildrenForm.put(ANNOTATION_SIZE, MESSAGE_CHILDREN_SIZE);
		mapAnnotationForChildrenForm.put(ANNOTATION_NOTBLANK, MESSAGE_CHILDREN_NOTBLANK);
		mapAnnotationForChildrenForm.put(ANNOTATION_VALID, MESSAGE_CHILDREN_VALID);
		mapAnnotationForChildrenForm.put(ANNOTATION_URL, MESSAGE_CHILDREN_URL);
		mapAnnotationForChildrenForm.put(ANNOTATION_CREDITCARDNUMBER, MESSAGE_CHILDREN_CREDITCARDNUMBER);
	}
	public static String[] reserved;
	public static char[] number;
	public static String[] qpkeyword;
	static {
		reserved = new String[] { "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while" };
		number = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		qpkeyword = new String[] {"language", "message", "role_permission", "account_permission", "account_profile", "account_role", "account_rule_definition", "account_theme", "permission", "resources", "role", "account", "account_login_attempt"};
		
		java.util.Arrays.sort(reserved);
		java.util.Arrays.sort(number);
		java.util.Arrays.sort(qpkeyword);
	}

	public static void setBindingResult(BindingResult result, Model model) {
		BeanPropertyBindingResult bindingResultTemp = new BeanPropertyBindingResult(result.getTarget(), result.getObjectName());
		// bindingResultTemp.getClass().getDeclaredFie;'lds();
		for (ObjectError objError : result.getAllErrors()) {
			if (objError instanceof FieldError) {
				FieldError fieldError = (FieldError) objError;
				String fieldName = fieldError.getField();
				String[] errorCodes;

				int indexField = getIndexOfObject(fieldName);
				if (mapAnnotation.containsKey(fieldError.getCode())) {
					String[] defaultParamCodeArray = fieldError.getDefaultMessage().split(";");
					String[] defaultParamMessageArray;
					if (indexField >= 0)
						defaultParamMessageArray = new String[defaultParamCodeArray.length + 1];
					else
						defaultParamMessageArray = new String[defaultParamCodeArray.length];
					for (int i = 0; i < defaultParamCodeArray.length; i++) {
						String message = "";
						message = ValidationUtils.getMessageByCode(defaultParamCodeArray[i]);
						defaultParamMessageArray[i] = message;
					}
					if (indexField >= 0) {
						defaultParamMessageArray[defaultParamMessageArray.length - 1] = String.valueOf(indexField + 1);
						errorCodes = ValidationUtils.convertAnnotaionToErrorCode(fieldError.getCode(), CHILDREN_BEANFORM);
					} else {
						errorCodes = ValidationUtils.convertAnnotaionToErrorCode(fieldError.getCode(), PARENT_BEANFORM);
					}
					bindingResultTemp.addError(new FieldError(fieldError.getObjectName().toString(), fieldError.getField(), fieldError.getRejectedValue(), false, errorCodes, defaultParamMessageArray, null));
				} else {
					bindingResultTemp.addError(objError);
				}
			} else {
				bindingResultTemp.addError(objError);
			}
		}

		model.addAttribute(BindingResult.MODEL_KEY_PREFIX + result.getObjectName(), bindingResultTemp);
	}

	public static void setBindingResult(BindingResult result, Model model, String newObjectName) {
		BeanPropertyBindingResult bindingResultTemp = new BeanPropertyBindingResult(result.getTarget(), result.getObjectName());
		for (ObjectError objError : result.getAllErrors()) {
			bindingResultTemp.addError(objError);
		}
		model.addAttribute(BindingResult.MODEL_KEY_PREFIX + newObjectName, bindingResultTemp);
	}

	private static int getIndexOfObject(String fieldName) {
		String strIndex;
		int index = -1;
		int startIndex = fieldName.indexOf('[');
		int endIndex = fieldName.indexOf(']', startIndex);
		try {
			if (startIndex != -1 && endIndex != -1) {
				strIndex = fieldName.substring(startIndex + 1, endIndex);
				index = Integer.valueOf(strIndex);
			}
		} catch (NumberFormatException ex) {
		}
		return index;
	}

	private static String[] convertAnnotaionToErrorCode(String errorCode, int type) {
		String[] arrCode = new String[1];
		arrCode[0] = getErrorCode(errorCode, type);
		return arrCode;
	}

	private static String getErrorCode(String annotaionCode, int type) {
		String errorCode = "";
		switch (type) {
		case 1:
			if (mapAnnotationForChildrenForm.containsKey(annotaionCode)) {
				errorCode = mapAnnotationForChildrenForm.get(annotaionCode);
			} else
				errorCode = annotaionCode;
			break;
		default:
			if (mapAnnotation.containsKey(annotaionCode)) {
				errorCode = mapAnnotation.get(annotaionCode);
			} else
				errorCode = annotaionCode;
			break;
		}
		return errorCode;
	}

	public static String getMessageByCode(String code) {
		String message = "";
		try {
			message = MessageUtils.getMessage(code);
		} catch (Exception ex) {
			message = code;
		}
		return message;
	}

	// KhangTM

	public static boolean validateRequired(Object bean, Errors errors, String messageCode) {
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}
		if (GenericValidator.isBlankOrNull(value)) {
			if (errors != null)
				errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(messageCode) }, null);
			return false;
		}
		return true;
	}

	public static boolean validateMask(Object bean, Errors errors, String mask, String messageCode, String errMessageCode, String msgMask) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}
		try {
			if ((!GenericValidator.isBlankOrNull(value)) && (!Pattern.matches(mask, value))) {
				if (errors != null)
					errors.reject(errMessageCode, new Object[] { MessageUtils.getMessage(messageCode), msgMask }, null);
				return false;
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return true;
	}

	public static boolean validateMaskName(Object bean, Errors errors, String mask, String messageCode) {
		return validateMask(bean, errors, mask, messageCode, CommonMessageConst.ERR_SYS_0126, CommonMessageConst.NAME_INPUTMASK);
	}

	public static boolean validateMaskCode(Object bean, Errors errors, String mask, String messageCode) {
		return validateMask(bean, errors, mask, messageCode, CommonMessageConst.ERR_SYS_0066, StringUtils.EMPTY);
	}

	public static boolean validateByte(Object bean, Errors errors, String messageCode) {

		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}
		if (GenericValidator.isBlankOrNull(value)) {
			return true;
		}
		result = FormatUtils.formatByte(value);
		if (result == null) {
			if (errors != null)
				errors.reject(CommonMessageConst.ERR_SYS_0001, new Object[] { MessageUtils.getMessage(messageCode) }, null);
		}
		return result == null ? false : true;
	}

	public static boolean validateShort(Object bean, Errors errors, String messageCode) {
		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}
		if (GenericValidator.isBlankOrNull(value)) {
			return true;
		}
		result = FormatUtils.formatShort(value);
		if (result == null) {
			if (errors != null)
				errors.reject(CommonMessageConst.ERR_SYS_0027, new Object[] { MessageUtils.getMessage(messageCode) }, null);
		}
		return result == null ? false : true;
	}

	public static boolean validateInteger(Object bean, Errors errors, String messageCode) {
		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}
		if (GenericValidator.isBlankOrNull(value)) {
			return true;
		}
		result = FormatUtils.formatInt(value);
		if (result == null) {
			if (errors != null)
				errors.reject(CommonMessageConst.ERR_SYS_0015, new Object[] { MessageUtils.getMessage(messageCode) }, null);
		}
		return result == null ? false : true;
	}

	public static boolean validateLong(Object bean, Errors errors, String messageCode) {
		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}
		if (GenericValidator.isBlankOrNull(value)) {
			return true;
		}
		result = FormatUtils.formatLong(value);
		if (result == null) {
			if (errors != null)
				errors.reject(CommonMessageConst.ERR_SYS_0019, new Object[] { MessageUtils.getMessage(messageCode) }, null);
		}
		return result == null ? false : true;
	}

	public static boolean validateFloat(Object bean, Errors errors, String messageCode) {
		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}
		if (GenericValidator.isBlankOrNull(value)) {
			return true;
		}

		result = FormatUtils.formatFloat(value);

		if (result == null) {
			if (errors != null)
				errors.reject(CommonMessageConst.ERR_SYS_0013, new Object[] { MessageUtils.getMessage(messageCode) }, null);
		}
		return result == null ? false : true;
	}

	public static boolean validateDouble(Object bean, Errors errors, String messageCode) {
		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}
		if (GenericValidator.isBlankOrNull(value)) {
			return true;
		}
		result = FormatUtils.formatDouble(value);
		if (result == null) {
			if (errors != null)
				errors.reject(CommonMessageConst.ERR_SYS_0009, new Object[] { MessageUtils.getMessage(messageCode) }, null);
		}
		return result == null ? false : true;
	}

	public static boolean validateDate(Object bean, Errors errors, String datePattern, String messageCode) {
		if (bean == null) {
			log.error("bean is null");
			return true;
		}

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}

		if (GenericValidator.isBlankOrNull(value)) {
			return true;
		}
		Date result = null;
		try {
			if (datePattern == null) {
				String getSysFormatDate = FormatUtils.getSysFormatDatePartern();
				result = FormatUtils.formatDate(value, getSysFormatDate, true);
			} else {
				result = FormatUtils.formatDate(value, datePattern, true);
			}
			if (result == null && value.length() == 19) {
				result = FormatUtils.formatDate(value, "yyyy-MM-dd HH:mm:ss", true);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (result == null) {
			if (errors != null)
				errors.reject(CommonMessageConst.ERR_SYS_0005, new Object[] { MessageUtils.getMessage(messageCode) }, null);
			return false;
		}
		return true;
	}

	public static boolean validateDateRange(Object bean, Errors errors, String datePattern, String startDateString, String endDateString, String messageCode) {

		if (bean == null) {
			log.error("bean is null.");
			return true;
		}

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}

		if (GenericValidator.isBlankOrNull(value)) {
			return true;
		}

		Date result = null;
		try {
			if (datePattern == null) {
				String getSysFormatDate = FormatUtils.getSysFormatDatePartern();
				result = FormatUtils.formatDate(value, getSysFormatDate, true);
			} else {
				result = FormatUtils.formatDate(value, datePattern, true);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		if (result == null) {
			if (errors != null)
				errors.reject(CommonMessageConst.ERR_SYS_0005, new Object[] { MessageUtils.getMessage(messageCode) }, null);
			return false;
		}

		if (startDateString == null && endDateString == null) {
			return true;
		} else {
			boolean error = false;
			if (startDateString != null && startDateString.length() > 0) {

				Date startDate = FormatUtils.formatDate(startDateString, FormatUtils.getDefaultSysFormatDatePartern(), false);
				if (startDate == null) {
					if (errors != null)
						errors.reject(CommonMessageConst.ERR_SYS_0005, new Object[] { MessageUtils.getMessage(startDateString) }, null);
					return false;
				}
				if (result.before(startDate)) {
					if (datePattern == null) {
						startDateString = FormatUtils.formatDateToString(startDate, FormatUtils.getSysFormatDatePartern());
					} else {
						startDateString = FormatUtils.formatDateToString(startDate, datePattern);
					}
					error = true;
				}
			}

			if (endDateString != null && endDateString.length() > 0) {
				Date endDate = FormatUtils.formatDate(endDateString, FormatUtils.getDefaultSysFormatDatePartern(), false);
				if (endDate == null) {
					if (errors != null)
						errors.reject(CommonMessageConst.ERR_SYS_0005, new Object[] { MessageUtils.getMessage(endDateString) }, null);
					return false;
				}
				if (result.after(endDate)) {
					if (datePattern == null) {
						endDateString = FormatUtils.formatDateToString(endDate, FormatUtils.getSysFormatDatePartern());
					} else {
						endDateString = FormatUtils.formatDateToString(endDate, datePattern);
					}
					error = true;
				}
			}
			if (error == true) {
				if (errors != null)
					errors.reject(CommonMessageConst.ERR_SYS_0007, new Object[] { MessageUtils.getMessage(messageCode), startDateString, endDateString }, null);
				return false;
			}
		}

		return true;
	}

	public static boolean validateIntRange(Object bean, Errors errors, int min, int max, String messageCode) {

		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				int intValue = Integer.parseInt(value);
				if (!GenericValidator.isInRange(intValue, min, max)) {
					if (errors != null)
						errors.reject(CommonMessageConst.ERR_SYS_0024, new Object[] { MessageUtils.getMessage(messageCode), min, max }, null);
					return false;
				}
			} catch (Exception e) {
				if (errors != null)
					errors.reject(CommonMessageConst.ERR_SYS_0024, new Object[] { MessageUtils.getMessage(messageCode), min, max }, null);
				return false;
			}
		}
		return true;
	}

	public static boolean validateDoubleRange(Object bean, Errors errors, double min, double max, String messageCode) {
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				double doubleValue = Double.parseDouble(value);
				if (!GenericValidator.isInRange(doubleValue, min, max)) {
					if (errors != null)
						errors.reject(CommonMessageConst.ERR_SYS_0024, new Object[] { MessageUtils.getMessage(messageCode), min, max }, null);
					return false;
				}
			} catch (Exception e) {
				if (errors != null)
					errors.reject(CommonMessageConst.ERR_SYS_0024, new Object[] { MessageUtils.getMessage(messageCode), min, max }, null);
				return false;
			}
		}
		return true;
	}

	public static boolean validateFloatRange(Object bean, Errors errors, float min, float max, String messageCode) {
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				float floatValue = Float.parseFloat(value);
				if (!GenericValidator.isInRange(floatValue, min, max)) {
					if (errors != null)
						errors.reject(CommonMessageConst.ERR_SYS_0024, new Object[] { MessageUtils.getMessage(messageCode), min, max }, null);
					return false;
				}
			} catch (Exception e) {
				if (errors != null)
					errors.reject(CommonMessageConst.ERR_SYS_0024, new Object[] { MessageUtils.getMessage(messageCode), min, max }, null);
				return false;
			}
		}
		return true;
	}

	public static Object validateCreditCard(Object bean, Errors errors, String messageCode) {
		Object result = null;
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}
		if (GenericValidator.isBlankOrNull(value)) {
			return true;
		}
		result = FormatUtils.formatCreditCard(value);
		if (result == null) {
			if (errors != null)
				errors.reject(CommonMessageConst.ERR_SYS_0004, new Object[] { MessageUtils.getMessage(messageCode) }, null);
		}
		return result == null ? false : result;
	}

	public static boolean validateMaxLength(Object bean, Errors errors, int max, String messageCode) {
		String value = null;
		int min = 1;
		if (isString(bean)) {
			value = (String) bean;
		}

		if (value != null) {
			try {
				if (!GenericValidator.maxLength(value, max)) {
					if (errors != null)
						errors.reject(CommonMessageConst.ERR_SYS_0067, new Object[] { MessageUtils.getMessage(messageCode), min, max }, null);
					return false;
				}
			} catch (Exception e) {
				if (errors != null)
					errors.reject(CommonMessageConst.ERR_SYS_0067, new Object[] { MessageUtils.getMessage(messageCode), min, max }, null);
				return false;
			}
		}
		return true;
	}

	public static boolean validateMinLength(Object bean, Errors errors, int min, String messageCode) {
		String value = null;
		if (isString(bean)) {
			value = (String) bean;
		}

		if (value != null) {
			try {
				if (!GenericValidator.minLength(value, min)) {
					if (errors != null)
						errors.reject(CommonMessageConst.ERR_SYS_0022, new Object[] { MessageUtils.getMessage(messageCode), min }, null);
					return false;
				}
			} catch (Exception e) {
				if (errors != null)
					errors.reject(CommonMessageConst.ERR_SYS_0022, new Object[] { MessageUtils.getMessage(messageCode), min }, null);
				return false;
			}
		}
		return true;
	}

	public static boolean validateUrl(Object bean, Errors errors) {
		return true;
	}

	public static boolean validateArrays(Object bean, Errors errors) {
		return true;
	}

	// Business validate

	public static Object validateExistence(Object bean, Errors errors) {
		return true;
	}

	public static Object validateDuplicated(Object bean, Errors errors) {
		return true;
	}

	public static Object validateDuplicatedArray(Object bean, Errors errors) {
		return true;
	}

	protected static boolean isString(Object o) {
		return o == null ? true : String.class.isInstance(o);
	}

	public static void validateReservedJava(String input, Errors errors, String errorCode, Object[] params) {
		if (errors != null && StringUtils.isNoneBlank(input) && FunctionCommon.checkExists(reserved, input)) {
			errors.reject(errorCode, params, null);
		}
	}

	public static void validatePackageName(Object bean, Errors errors, String blankMessageCode, String blankErrorCode, String invalidMessageCode, String invalidErrorCode) {
		int[] codePoint;
		int index = 0;
		int dotex = -1;
		boolean needStart = true;
		String packageName = null;

		if (isString(bean)) {
			packageName = (String) bean;
		}

		escape: {
			// If package name is null or empty, not a valid java identifier
			if (packageName == null || packageName.isEmpty()) {
				errors.reject(blankErrorCode, new Object[] { MessageUtils.getMessage(blankMessageCode) }, null);
				break escape;
			}
			// If first character is '.', not a valid java identifier
			if (packageName.codePointAt(0) == '.') {
				errors.reject(invalidErrorCode, new Object[] { MessageUtils.getMessage(invalidMessageCode) }, null);
				break escape;
			}
			codePoint = packageName.codePoints().toArray();
			while (index <= codePoint.length) {
				// End of string
				if (index == codePoint.length) {
					// If last character is '.', not a valid java identifier
					if (codePoint[index - 1] == '.') {
						errors.reject(invalidErrorCode, new Object[] { MessageUtils.getMessage(invalidMessageCode) }, null);
						break escape;
					}
					// Get substring from '.' to end,
					// then compare with reserved java keyword and check if
					// first character is number
					// if true, not a valid java identifier
					int start = dotex + 1;
					int end = index;
					start = packageName.offsetByCodePoints(0, start);
					end = packageName.offsetByCodePoints(0, end);
					String test = packageName.substring(start, end);
					if (!(Arrays.binarySearch(reserved, test) < 0)) {
						errors.reject(invalidErrorCode, new Object[] { MessageUtils.getMessage(invalidMessageCode) }, null);
						break escape;
					}
					if (!(Arrays.binarySearch(number, test.charAt(0)) < 0)) {
						errors.reject(invalidErrorCode, new Object[] { MessageUtils.getMessage(invalidMessageCode) }, null);
						break escape;
					}
					break escape;
				}

				// From start string to '.'
				if (codePoint[index] == '.') {
					// If last character is '.', not a valid java identifier
					if (codePoint[index - 1] == '.') {
						errors.reject(invalidErrorCode, new Object[] { MessageUtils.getMessage(invalidMessageCode) }, null);
						break escape;
					} else {
						// Get substring from start to '.',
						// then compare with reserved java keyword and check if
						// first character is number
						// if true, not a valid java identifier
						needStart = true;
						int start = dotex + 1;
						int end = index;
						start = packageName.offsetByCodePoints(0, start);
						end = packageName.offsetByCodePoints(0, end);
						String test = packageName.substring(start, end);
						if (!(Arrays.binarySearch(reserved, test) < 0)) {
							errors.reject(invalidErrorCode, new Object[] { MessageUtils.getMessage(invalidMessageCode) }, null);
							break escape;
						}
						if (!(Arrays.binarySearch(number, test.charAt(0)) < 0)) {
							errors.reject(invalidErrorCode, new Object[] { MessageUtils.getMessage(invalidMessageCode) }, null);
							break escape;
						}
						dotex = index;
					}
				}
				// Check if first character is valid java identifier
				else if (Character.isJavaIdentifierStart(codePoint[index])) {
					if (needStart) {
						needStart = false;
					}
				}
				// Check if character is valid java identifier
				else if ((!Character.isJavaIdentifierPart(codePoint[index]))) {
					errors.reject(invalidErrorCode, new Object[] { MessageUtils.getMessage(invalidMessageCode) }, null);
					break escape;
				}
				index++;
			}
		}
	}

	/**
	 * Validate for account name
	 * 
	 * @param accountName
	 * @param errors
	 * @param messageCode
	 * @param defaultPattern
	 */
	public static void validateAccountName(String accountName, Errors errors, String messageCode, String defaultCodePattern) {
		String[] args = { MessageUtils.getMessage(messageCode) };
		// Check null
		if (StringUtils.isBlank(accountName)) {
			errors.reject(AccountMessageConst.SC_ACCOUNT_0022, args, null);
			return;
		}

		// Check pattern
		if (!validateRegex(accountName, EMAIL_PATTERN) && !validateRegex(accountName, defaultCodePattern)) {
			errors.reject(AccountMessageConst.SC_ACCOUNT_0022, args, null);
			return;
		}

	}

	/**
	 * Validate for email
	 * 
	 * @param email
	 * @param errors
	 * @param messageCode
	 * @param defaultPattern
	 */
	public static void validateEmail(String email, Errors errors, String messageCode) {
		if (StringUtils.isBlank(email)) {
			return;
		}

		String[] args = { MessageUtils.getMessage(messageCode) };
		// Check pattern
		if (!validateRegex(email, EMAIL_PATTERN)) {
			errors.reject(CommonMessageConst.ERR_SYS_0012, args, null);
			return;
		}

	}

	/**
	 * Validate for ArtifactId
	 * 
	 * @param artifactId
	 * @param errors
	 * @param messageCode
	 */
	public static void validateArtifactId(String artifactId, Errors errors, String messageCode) {
		String[] args = { MessageUtils.getMessage(messageCode) };
		// Check null
		if (StringUtils.isBlank(artifactId)) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, args, null);
			return;
		}

		// Check pattern
		if (!validateRegex(artifactId, ARTIFACTID_PATTERN)) {
			errors.reject(CommonMessageConst.ERR_SYS_0018, args, null);
			return;
		}
	}

	public static boolean validateRegex(String value, String regex) {
		Pattern pattern = Pattern.compile(regex);
		if (pattern.matcher(value).matches()) {
			return true;
		}
		return false;
	}

	public static boolean validateRegexForEachCharacter(String value, String regex) {
		Pattern pattern = Pattern.compile(regex);
		for (char ch : value.toCharArray()) {
			if (!checkCharacter(ch, pattern))
				return false;
		}
		return true;
	}

	public static boolean validateBetweenTwoNumbers(String value, int min, int max) {
		try {
			int number = Integer.parseInt(value);
			if (min <= number && number <= max) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean validateDateTimeFormat(String value, String format) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		// turn off to make date validation more strictly.
		simpleDateFormat.setLenient(false);
		try {
			simpleDateFormat.parse(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean checkCharacter(char ch, Pattern pattern) {
		String str = Character.toString(ch);
		return pattern.matcher(str).matches();
	}

	public static void validateExistInList(String strInput, Collection<String> lstData, Errors errors, String messageCode, String[] args) {
		if (CollectionUtils.isEmpty(lstData)) {
			lstData.add(strInput);
			return;
		}
		if (lstData.contains(strInput)) {
			errors.reject(messageCode, args, null);
		} else {
			lstData.add(strInput);
		}
	}

	public static void validateExistInListIgnoreCase(String strInput, Collection<String> lstData, Errors errors, String messageCode, String[] args) {
		if (CollectionUtils.isEmpty(lstData)) {
			lstData.add(strInput);
			return;
		}
		boolean exist = false;
		for (String str : lstData) {
			if (StringUtils.equalsIgnoreCase(strInput, str)) {
				exist = true;
				break;
			}
		}
		if (exist) {
			errors.reject(messageCode, args, null);
		} else {
			lstData.add(strInput);
		}
	}

	public static Date parseStringToDate(SimpleDateFormat simpleFormat, String value) {
		try {
			Date date = simpleFormat.parse(value);
			return date;
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
