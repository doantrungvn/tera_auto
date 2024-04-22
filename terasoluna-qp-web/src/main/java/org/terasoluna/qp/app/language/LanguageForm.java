package org.terasoluna.qp.app.language;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.message.LanguageMessageConst;

public class LanguageForm {
	
	public static interface TranslationSystemForm{};
	
	private String languageName;
	
	private String languageCode;
	
	/*@NotEmpty(message = "sc.language.0011")*/
	private String countryCode;
	
	private String countryName;
	
	private String regionCode;
	
	private String toLanguageCode;
	
	private String toCountryCode;
	
	private boolean translateFlg;

	
	@NotEmpty(message = LanguageMessageConst.SC_LANGUAGE_0063, groups = {TranslationSystemForm.class})
	private String toLanguageCodeAutocomplete;
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
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

	public String getToLanguageCode() {
		return toLanguageCode;
	}

	public void setToLanguageCode(String toLanguageCode) {
		this.toLanguageCode = toLanguageCode;
	}

	public String getToLanguageCodeAutocomplete() {
		return toLanguageCodeAutocomplete;
	}

	public void setToLanguageCodeAutocomplete(String toLanguageCodeAutocomplete) {
		this.toLanguageCodeAutocomplete = toLanguageCodeAutocomplete;
	}

	public String getToCountryCode() {
		return toCountryCode;
	}

	public void setToCountryCode(String toCountryCode) {
		this.toCountryCode = toCountryCode;
	}

	public boolean isTranslateFlg() {
		return translateFlg;
	}

	public void setTranslateFlg(boolean translateFlg) {
		this.translateFlg = translateFlg;
	}

	
}
