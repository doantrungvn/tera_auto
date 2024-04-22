package org.terasoluna.qp.domain.repository.language;

import java.io.Serializable;

public class LanguageCriteria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** language Code */
	private String languageCode;
	/** language Name */
	private String languageName;

	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param languageCode
	 *            the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the languageName
	 */
	public String getLanguageName() {
		return languageName;
	}

	/**
	 * @param languageName
	 *            the languageName to set
	 */
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

}
