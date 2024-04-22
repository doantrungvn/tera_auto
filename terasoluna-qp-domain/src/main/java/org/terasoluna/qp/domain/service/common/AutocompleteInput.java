package org.terasoluna.qp.domain.service.common;

import org.terasoluna.qp.app.common.ultils.LocaleUtils;

public class AutocompleteInput {
	private String autocompleteTerm;
	private String sourceType;
	private String searchKey;
	
	private String arg01;
	private String arg02;
	private String arg03;
	private String arg04;
	private String arg05;
	
	private String arg06;
	private String arg07;
	private String arg08;
	private String arg09;
	private String arg10;
	private String arg11;
	private String arg12;
	private String arg13;
	private String arg14;
	private String arg15;
	
	private String arg16;
	private String arg17;
	private String arg18;
	private String arg19;
	private String arg20;
	private String languageCode;
	private String countryCode;
	private Long languageId;
	private String paternDate;
	public String getAutocompleteTerm() {
		return autocompleteTerm;
	}
	public void setAutocompleteTerm(String autocompleteTerm) {
		this.autocompleteTerm = autocompleteTerm;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getArg01() {
		return arg01;
	}
	public void setArg01(String arg01) {
		this.arg01 = arg01;
	}
	public String getArg02() {
		return arg02;
	}
	public void setArg02(String arg02) {
		this.arg02 = arg02;
	}
	public String getArg03() {
		return arg03;
	}
	public void setArg03(String arg03) {
		this.arg03 = arg03;
	}
	public String getArg04() {
		return arg04;
	}
	public void setArg04(String arg04) {
		this.arg04 = arg04;
	}
	public String getArg05() {
		return arg05;
	}
	public void setArg05(String arg05) {
		this.arg05 = arg05;
	}
	public String getArg06() {
		return arg06;
	}
	public void setArg06(String arg06) {
		this.arg06 = arg06;
	}
	public String getArg07() {
		return arg07;
	}
	public void setArg07(String arg07) {
		this.arg07 = arg07;
	}
	public String getArg08() {
		return arg08;
	}
	public void setArg08(String arg08) {
		this.arg08 = arg08;
	}
	public String getArg09() {
		return arg09;
	}
	public void setArg09(String arg09) {
		this.arg09 = arg09;
	}
	public String getArg10() {
		return arg10;
	}
	public void setArg10(String arg10) {
		this.arg10 = arg10;
	}
	public String getArg11() {
		return arg11;
	}
	public void setArg11(String arg11) {
		this.arg11 = arg11;
	}
	public String getArg12() {
		return arg12;
	}
	public void setArg12(String arg12) {
		this.arg12 = arg12;
	}
	public String getArg13() {
		return arg13;
	}
	public void setArg13(String arg13) {
		this.arg13 = arg13;
	}
	public String getArg14() {
		return arg14;
	}
	public void setArg14(String arg14) {
		this.arg14 = arg14;
	}
	public String getArg15() {
		return arg15;
	}
	public void setArg15(String arg15) {
		this.arg15 = arg15;
	}
	public String getArg16() {
		return arg16;
	}
	public void setArg16(String arg16) {
		this.arg16 = arg16;
	}
	public String getArg17() {
		return arg17;
	}
	public void setArg17(String arg17) {
		this.arg17 = arg17;
	}
	public String getArg18() {
		return arg18;
	}
	public void setArg18(String arg18) {
		this.arg18 = arg18;
	}
	public String getArg19() {
		return arg19;
	}
	public void setArg19(String arg19) {
		this.arg19 = arg19;
	}
	public String getArg20() {
		return arg20;
	}
	public void setArg20(String arg20) {
		this.arg20 = arg20;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getLanguageCode() {
		this.languageCode = LocaleUtils.getLanguage(LocaleUtils.getRequestLocale());
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	public String getCountryCode() {
		this.countryCode = LocaleUtils.getCountry(LocaleUtils.getRequestLocale());
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Long getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}


}
