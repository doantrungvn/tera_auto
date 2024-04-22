package org.terasoluna.qp.app.messagedesign;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.Size;

import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.message.MessageDesignMessageConst;
import org.terasoluna.qp.app.messagedesign.MessageDesignSearchForm.ModificationMessageDesignForm;

public class ModifyMessageDesignForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String fromLanguageCode;
	
	private Long fromLanguageId;
	
	private Long toLanguageId;
	
	private Long fromMessageDesignId;
	
	/*@NotEmpty(message = MessageDesignMessageConst.SC_MESSAGEDESIGN_0017,  groups = { ModificationMessageDesignForm.class })*/
	@Size(min = DbDomainConst.MIN_VAL_REMARK, max = DbDomainConst.MAX_VAL_MESSAGE, message = MessageDesignMessageConst.SC_MESSAGEDESIGN_0018 + ";" + DbDomainConst.MIN_VAL_INPUT +";" + DbDomainConst.MAX_VAL_MESSAGE ,  groups = { ModificationMessageDesignForm.class })
	private String fromMessageString;
	
	private String toLanguageCode;
	
	private Long toMessageDesignId;
	
	@Size(min = DbDomainConst.MIN_VAL_REMARK, max = DbDomainConst.MAX_VAL_MESSAGE, message = MessageDesignMessageConst.SC_MESSAGEDESIGN_0019 + ";" + DbDomainConst.MIN_VAL_INPUT +";" + DbDomainConst.MAX_VAL_MESSAGE,  groups = { ModificationMessageDesignForm.class })
	private String toMessageString;
	
	private Long fromUpdatedBy;
	
	private Timestamp fromUpdatedDate;

	private Long toUpdatedBy;
	
	private Timestamp toUpdatedDate;

	private String moduleName;
	
	private String screenName;
	
	private String messageCode;
	
	private Boolean selected;
	
	private Boolean fromSelected = false;
	
	private Boolean toSelected = false;
	
	private Integer fromGeneratedStatus;
	
	private Integer toGeneratedStatus;
	
	private Long messageLevel;
	
	public Long getMessageLevel() {
		return messageLevel;
	}

	public void setMessageLevel(Long messageLevel) {
		this.messageLevel = messageLevel;
	}

	public ModifyMessageDesignForm() {
		// Default constructor
	}

	/**
	 * @return the fromLanguageCode
	 */
	
	public String getFromLanguageCode() {
		return fromLanguageCode;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Boolean getFromSelected() {
		return fromSelected;
	}

	public void setFromSelected(Boolean fromSelected) {
		this.fromSelected = fromSelected;
	}

	public Boolean getToSelected() {
		return toSelected;
	}

	public void setToSelected(Boolean toSelected) {
		this.toSelected = toSelected;
	}

	/**
	 * @param fromLanguageCode the fromLanguageCode to set
	 */
	public void setFromLanguageCode(String fromLanguageCode) {
		this.fromLanguageCode = fromLanguageCode;
	}

	/**
	 * @return the fromMessageDesignId
	 */
	public Long getFromMessageDesignId() {
		return fromMessageDesignId;
	}

	/**
	 * @param fromMessageDesignId the fromMessageDesignId to set
	 */
	public void setFromMessageDesignId(Long fromMessageDesignId) {
		this.fromMessageDesignId = fromMessageDesignId;
	}

	/**
	 * @return the fromMessageString
	 */
	public String getFromMessageString() {
		return fromMessageString;
	}

	/**
	 * @param fromMessageString the fromMessageString to set
	 */
	public void setFromMessageString(String fromMessageString) {
		this.fromMessageString = fromMessageString;
	}

	/**
	 * @return the toLanguageCode
	 */
	public String getToLanguageCode() {
		return toLanguageCode;
	}

	/**
	 * @param toLanguageCode the toLanguageCode to set
	 */
	public void setToLanguageCode(String toLanguageCode) {
		this.toLanguageCode = toLanguageCode;
	}

	/**
	 * @return the toMessageDesignId
	 */
	public Long getToMessageDesignId() {
		return toMessageDesignId;
	}

	/**
	 * @param toMessageDesignId the toMessageDesignId to set
	 */
	public void setToMessageDesignId(Long toMessageDesignId) {
		this.toMessageDesignId = toMessageDesignId;
	}

	/**
	 * @return the toMessageString
	 */
	public String getToMessageString() {
		return toMessageString;
	}

	/**
	 * @param toMessageString the toMessageString to set
	 */
	public void setToMessageString(String toMessageString) {
		this.toMessageString = toMessageString;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * @return the fromUpdatedBy
	 */
	public Long getFromUpdatedBy() {
		return fromUpdatedBy;
	}

	/**
	 * @param fromUpdatedBy the fromUpdatedBy to set
	 */
	public void setFromUpdatedBy(Long fromUpdatedBy) {
		this.fromUpdatedBy = fromUpdatedBy;
	}

	/**
	 * @return the fromUpdatedDate
	 */
	public Timestamp getFromUpdatedDate() {
		return fromUpdatedDate;
	}

	/**
	 * @param fromUpdatedDate the fromUpdatedDate to set
	 */
	public void setFromUpdatedDate(Timestamp fromUpdatedDate) {
		this.fromUpdatedDate = fromUpdatedDate;
	}

	/**
	 * @return the toUpdatedBy
	 */
	public Long getToUpdatedBy() {
		return toUpdatedBy;
	}

	/**
	 * @param toUpdatedBy the toUpdatedBy to set
	 */
	public void setToUpdatedBy(Long toUpdatedBy) {
		this.toUpdatedBy = toUpdatedBy;
	}

	/**
	 * @return the toUpdatedDate
	 */
	public Timestamp getToUpdatedDate() {
		return toUpdatedDate;
	}

	/**
	 * @param toUpdatedDate the toUpdatedDate to set
	 */
	public void setToUpdatedDate(Timestamp toUpdatedDate) {
		this.toUpdatedDate = toUpdatedDate;
	}

	public Long getFromLanguageId() {
		return fromLanguageId;
	}

	public void setFromLanguageId(Long fromLanguageId) {
		this.fromLanguageId = fromLanguageId;
	}

	public Long getToLanguageId() {
		return toLanguageId;
	}

	public void setToLanguageId(Long toLanguageId) {
		this.toLanguageId = toLanguageId;
	}

	public Integer getFromGeneratedStatus() {
		return fromGeneratedStatus;
	}

	public void setFromGeneratedStatus(Integer fromGeneratedStatus) {
		this.fromGeneratedStatus = fromGeneratedStatus;
	}

	public Integer getToGeneratedStatus() {
		return toGeneratedStatus;
	}

	public void setToGeneratedStatus(Integer toGeneratedStatus) {
		this.toGeneratedStatus = toGeneratedStatus;
	}
}
