package org.terasoluna.qp.app.languagedesign;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.message.LanguageDesignMessageConst;

public class LanguageDesignForm {
	
	public static interface TranslationForm{};
	
	public static interface RegistrationForm{};
	
	@NotEmpty(message = LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0001, groups = { RegistrationForm.class} )
	private String languageCode;
	
	private Long languageId;
	
	/*@NotEmpty(message = LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0001)*/
	private String languageName;
	
	/*@NotEmpty(message = LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0003,  groups = { RegistrationForm.class})*/
	private String countryCode;
	
	private String regionCode;
	
	private String createdBy;
	
	private Timestamp createdDate;
	
	private String updatedBy;
	
	private Timestamp updatedDate;
	
	private boolean hasErrors = false;
	
	private String toLanguageCode;
	
	private Long toLanguageId;
	
	private String toCountryCode;
	
	private String itemSeqNo;
	
	private String isDefault;
	
	private String currentLanguageProject;
	
	private String mode;
	
	public String getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(String itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	@NotEmpty(message = LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0011, groups = { TranslationForm.class})
	private String toLanguageIdAutocomplete;
	
	@NotNull(message = LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0010, groups = { TranslationForm.class})
	private Integer translateMode;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
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

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
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


	public Long getLanguageId() {
		return languageId;
	}

	public String getToLanguageIdAutocomplete() {
		return toLanguageIdAutocomplete;
	}

	public void setToLanguageIdAutocomplete(String toLanguageIdAutocomplete) {
		this.toLanguageIdAutocomplete = toLanguageIdAutocomplete;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public Integer getTranslateMode() {
		return translateMode;
	}

	public void setTranslateMode(Integer translateMode) {
		this.translateMode = translateMode;
	}

	public Long getToLanguageId() {
		return toLanguageId;
	}

	public void setToLanguageId(Long toLanguageId) {
		this.toLanguageId = toLanguageId;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

    public String getCurrentLanguageProject() {
        return currentLanguageProject;
    }

    public void setCurrentLanguageProject(String currentLanguageProject) {
        this.currentLanguageProject = currentLanguageProject;
    }

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
} 
