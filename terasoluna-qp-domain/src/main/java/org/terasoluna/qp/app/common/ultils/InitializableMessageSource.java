package org.terasoluna.qp.app.common.ultils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.util.Assert;
import org.synyx.messagesource.MessageProvider;
import org.synyx.messagesource.Messages;
import org.terasoluna.gfw.common.codelist.ReloadableCodeList;

public class InitializableMessageSource extends AbstractMessageSource implements InitializingBean {

	//private static final Logger log = LoggerFactory.getLogger(InitializableMessageSource.class);
	
	@Inject
	@Named("CL_LANGUAGE_LIST")
	private ReloadableCodeList languageList;

	protected Map<Locale, List<String>> resolvingPath = new HashMap<Locale, List<String>>();
	protected Map<String, Map<String, MessageFormat>> messages;
	protected Locale defaultLocale;
	protected MessageProvider messageProvider;
	protected Boolean returnUnresolvedCode = false;
	protected List<String> basenames = new ArrayList<String>();

	/**
	 * If this property is set to true this initializes post-construction (spring lifecycle interface).
	 */
	protected boolean autoInitialize = false;

	/**
	 * Initializes messages by retrieving them from the set {@link MessageProvider}. This also leads to a reset of the resolving-paths used to cache lookup-paths for messages
	 */
	public void initialize() {

		// reset the path-cache (default-locale could have been changed)
		resolvingPath = new HashMap<Locale, List<String>>();
		languageList.refresh();

		basenames = new ArrayList<String>();
		for (Entry<String, String> entry : languageList.asMap().entrySet()) {
			basenames.add(entry.getKey());
		}

		messages = new HashMap<String, Map<String, MessageFormat>>();

		for (String basename : basenames) {
			initialize(basename);
		}

	}

	/**
	 * Reads all messages from the {@link MessageProvider} for the given Basename.
	 *
	 * @param basename
	 *            the basename to initialize messages for
	 */
	protected void initialize(String basename) {
		initializeMessages(basename);
	}

	protected void initializeMessages(String basename) throws RuntimeException {

		Messages messagesForBasename = messageProvider.getMessages(basename);

		for (Locale locale : messagesForBasename.getLocales()) {
			Map<String, String> codeToMessage = messagesForBasename.getMessages(locale);

			for (String code : codeToMessage.keySet()) {
				try {
					/*log.info("Message code " + code);*/
					addMessage(basename, locale, code, createMessageFormat(codeToMessage.get(code), locale));
				} catch (RuntimeException e) {
					throw new MessageInitializationException(String.format("Error processing Message code=%s locale=%s basename=%s, %s", code, locale, basename, e.getMessage()), e);
				}
			}
		}
	}

	private void addMessage(String basename, Locale locale, String code, MessageFormat messageFormat) {

		String localeString = basename + "_" + (locale != null ? locale.toString() : "");
		Map<String, MessageFormat> codeMap = messages.get(localeString);

		if (codeMap == null) {
			codeMap = new HashMap<String, MessageFormat>();
			messages.put(localeString, codeMap);
		}

		codeMap.put(code, messageFormat);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.context.support.AbstractMessageSource#resolveCode(java.lang.String, java.util.Locale)
	 */
	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {

		if (messages == null || MessageUtils.reloadMessage) {
			MessageUtils.reloadMessage = false;
			initialize();
		}
		// System.out.println(messages);
		for (String basename : basenames) {
			List<String> paths = getPath(locale);
			for (String loc : paths) {
				Map<String, MessageFormat> formatMap = messages.get(basename + loc);
				if (formatMap != null) {
					MessageFormat format = formatMap.get(code);
					if (format != null) {
						if (format.getLocale() == null) {
							format.setLocale(defaultLocale);
						}
						return format;
					}
				}
			}
		}

		if (getReturnUnresolvedCode()) {
			return createMessageFormat(code, locale);
		} else {
			return null;
		}
	}

	private List<String> getPath(Locale locale) {

		Locale requestLocale = LocaleUtils.getRequestLocale();

		/*
		 * String[] defaultLanguage = null; if(SessionUtils.get(SessionUtils.USERPROFILE) != null) { AccountProfile accountProfile = (AccountProfile) SessionUtils.get(SessionUtils.USERPROFILE);
		 * if(!StringUtils.isEmpty(accountProfile.getDefaultLanguage())){ defaultLanguage = accountProfile.getDefaultLanguage().split("_"); } } if(defaultLanguage != null){ requestLocale =
		 * LocaleUtils.toLocale(defaultLanguage[0], defaultLanguage[1], ""); }
		 */

		if (requestLocale != null) {
			locale = requestLocale;
		}

		/* else { */
		Map<String, String> clLanguage = languageList.asMap();
		if (!clLanguage.containsKey(locale.getLanguage() + "_" + locale.getCountry())) {
			locale = LocaleUtils.toLocale("en", "US", "");
		}
		/* } */
		List<String> path = resolvingPath.get(locale);
		if (path == null) {
			path = new ArrayList<String>();
			List<Locale> localePath = LocaleUtils.getPath(locale, getDefaultLocale());
			for (Locale loc : localePath) {
				if (loc == null) {
					path.add("_");
				} else {
					String language = LocaleUtils.getLanguage(loc);
					String country = LocaleUtils.getCountry(loc);
					String variant = LocaleUtils.getVariant(loc);
					if (!variant.isEmpty()) {
						path.add(String.format("_%s_%s_%s", language, country, variant));
					} else if (!country.isEmpty()) {
						path.add(String.format("_%s_%s", language, country));
					} else if (!language.isEmpty()) {
						path.add(String.format("_%s", language));
					}
				}

			}

			resolvingPath.put(locale, path);
		}
		return path;
	}

	public Locale getDefaultLocale() {

		return defaultLocale;
	}

	/**
	 * Sets the default {@link Locale} used during message-resolving. If for a given Locale the message is not found the message gets looked up for the default-locale. If the message is not found then
	 * the "base-message" is used. This is allowed to be null which then means "no default locale"
	 *
	 * @param defaultLocale
	 *            the Locale to use as default or null if no default-locale should be used
	 */
	public void setDefaultLocale(Locale defaultLocale) {

		this.defaultLocale = defaultLocale;
	}

	/**
	 * Sets the {@link MessageProvider} for this which is asked for all its Messages during initialisation.
	 *
	 * @param messageProvider
	 *            the {@link MessageProvider} to use
	 */
	public void setMessageProvider(MessageProvider messageProvider) {

		Assert.notNull(messageProvider);

		this.messageProvider = messageProvider;
	}

	/**
	 * Callback to call {@link #initialize()} after construction of this using a Spring-Callback.
	 */
	public void afterPropertiesSet() throws Exception {

		if (autoInitialize) {
			initialize();
		}

	}

	/**
	 * Sets the.
	 *
	 * @param autoInitialize
	 */
	public void setAutoInitialize(boolean autoInitialize) {

		this.autoInitialize = autoInitialize;
	}

	/**
	 * @return <br>
	 *         Default value is false.If message could not be resolved returns null<br>
	 *         if set to true- will return message code if the message could not be resolved
	 */
	public Boolean getReturnUnresolvedCode() {

		return this.returnUnresolvedCode;
	}

	/**
	 * @param returnUnresolvedCode
	 *            -<br>
	 *            Default value is false.If message could not be resolved returns null<br>
	 *            if set to true- will return message code if the message could not be resolved
	 */
	public void setReturnUnresolvedCode(Boolean returnUnresolvedCode) {

		this.returnUnresolvedCode = returnUnresolvedCode;
	}
}
