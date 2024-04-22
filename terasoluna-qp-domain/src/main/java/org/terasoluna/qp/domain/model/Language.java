package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class Language implements Serializable {

	private static final long serialVersionUID = 1L;

	private String languageCode;

	private String languageName;

	private String countryCode;

	private String regionCode;
	
	private Long languageId;
	
	private String toLanguageCode;
	
	private String toCountryCode;
	
	private String toLanguageIdAutocomplete;
	
	private Integer translateMode;
	
	public Language() {
		super();
	}
	
	public Language(String languageCode, String languageName, String countryCode) {
		this.languageCode = languageCode;
		this.languageName = languageName;
		this.countryCode = countryCode;
	}

	public Language(String languageCode, String countryCode) {
		super();
		this.languageCode = languageCode;
		this.countryCode = countryCode;
	}
	
	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getToLanguageCode() {
		return toLanguageCode;
	}

	public void setToLanguageCode(String toLanguageCode) {
		this.toLanguageCode = toLanguageCode;
	}

	public String getToCountryCode() {
		return toCountryCode;
	}

	public void setToCountryCode(String toCountryCode) {
		this.toCountryCode = toCountryCode;
	}

	public String getToLanguageIdAutocomplete() {
		return toLanguageIdAutocomplete;
	}

	public void setToLanguageIdAutocomplete(String toLanguageIdAutocomplete) {
		this.toLanguageIdAutocomplete = toLanguageIdAutocomplete;
	}

	public Integer getTranslateMode() {
		return translateMode;
	}

	public void setTranslateMode(Integer translateMode) {
		this.translateMode = translateMode;
	}
}
