package org.terasoluna.qp.app.messagedesign;

import java.io.Serializable;
import java.sql.Timestamp;

public class MultipleMessageDesignForm implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Long messageDesignId;
	
	private String messageString; 

	private String remark;
	
	private String messageType;
	
	private Integer messageLevel;
	
	private Long moduleId;
	
	private String moduleCode;
	
	private Long projectId;
	
	private Long languageId;
	
	private String languageCode;
	
	private String languageName;
	
	private String countryCode;
	
	private String messageCode;
	
	private Timestamp updatedDate;
	
	public MultipleMessageDesignForm() {
		
	}
	
	public Long getMessageDesignId() {
		return messageDesignId;
	}

	public void setMessageDesignId(Long messageDesignId) {
		this.messageDesignId = messageDesignId;
	}

	/**
	 * @return the messageString
	 */
	public String getMessageString() {
		return messageString;
	}

	/**
	 * @param messageString the messageString to set
	 */
	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the messageLevel
	 */
	public Integer getMessageLevel() {
		return messageLevel;
	}

	/**
	 * @param messageLevel the messageLevel to set
	 */
	public void setMessageLevel(Integer messageLevel) {
		this.messageLevel = messageLevel;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the moduleId
	 */
	public Long getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

}
