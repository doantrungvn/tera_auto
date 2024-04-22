package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class LanguageDesign implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long languageId;

	private String languageCode;

	private String languageName;

	private String countryCode;

	private String regionCode;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private Timestamp sysDatetime;

	private String toLanguageCode;

	private Long toLanguageId;

	private String toCountryCode;

	private String toLanguageIdAutocomplete;

	private Integer translateMode;

	private String languageCodeOld;

	private Long projectId;

	private Integer itemSeqNo;

	private Boolean defaultFlg;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public LanguageDesign(String languageCode, String languageName, String countryCode) {
		this.languageCode = languageCode;
		this.languageName = languageName;
		this.countryCode = countryCode;
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

	public Timestamp getSysDatetime() {
		return sysDatetime;
	}

	public void setSysDatetime(Timestamp sysDatetime) {
		this.sysDatetime = sysDatetime;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public LanguageDesign() {
		super();
	}

	public LanguageDesign(String languageCode, String countryCode) {
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

	public String getLanguageCodeOld() {
		return languageCodeOld;
	}

	public void setLanguageCodeOld(String languageCodeOld) {
		this.languageCodeOld = languageCodeOld;
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

	public Integer getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	public Boolean getDefaultFlg() {
		return defaultFlg;
	}

	public void setDefaultFlg(Boolean defaultFlg) {
		this.defaultFlg = defaultFlg;
	}

}
