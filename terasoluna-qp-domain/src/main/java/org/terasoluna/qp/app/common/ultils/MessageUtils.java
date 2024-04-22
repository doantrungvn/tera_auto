package org.terasoluna.qp.app.common.ultils;

import java.util.Locale;

import org.springframework.context.NoSuchMessageException;

public class MessageUtils {

	public static boolean reloadMessage = false;
	
	public static String getMessage(String code, Object... args) {

		InitializableMessageSource s = (InitializableMessageSource) ApplicationContextProvider.getApplicationContext().getBean("messageSource");

		try {
			if (HttpServletRequestUtils.getRequest() != null) {
				return s.getMessage(code, args, LocaleUtils.getRequestLocale());
			} else {
				return getMessageByDefaultLanguage(s, code, args);
			}
		} catch (NoSuchMessageException ex) {
			return getMessageByDefaultLanguage(s, code, args);
		}
	}

	public static String getMessageFromLocale(String code, Locale locale, Object... args) {

		InitializableMessageSource s = (InitializableMessageSource) ApplicationContextProvider.getApplicationContext().getBean("messageSource");

		try {
			return s.getMessage(code, args, locale);
		} catch (NoSuchMessageException ex) {
			return getMessageByDefaultLanguage(s, code, args);
		}
	}

	public static String getMessage(String code) {

		InitializableMessageSource s = (InitializableMessageSource) ApplicationContextProvider.getApplicationContext().getBean("messageSource");

		try {
			if (HttpServletRequestUtils.getRequest() != null) {
				return s.getMessage(code, null, LocaleUtils.getRequestLocale());
			} else {
				return getMessageByDefaultLanguage(s, code, new Object());
			}
		} catch (NoSuchMessageException ex) {
			return getMessageByDefaultLanguage(s, code, new Object());
		}

	}

	public static String getMessageByDefaultLanguage(InitializableMessageSource s, String code, Object... args) {
		try {
			return s.getMessage(code, args, new Locale("en", "US"));
		} catch (NoSuchMessageException ex) {
			return code;
		}
	}
}
