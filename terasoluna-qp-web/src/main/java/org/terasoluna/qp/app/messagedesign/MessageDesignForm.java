package org.terasoluna.qp.app.messagedesign;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.domain.model.MessageDesign;

public class MessageDesignForm implements Serializable{
	
	public static interface RegistrationForm {};
	
	private static final long serialVersionUID = 1L;
	
	private Long messageDesignId;
	
	private String messageString;
	
	private String messageCode;
	
	private Long projectId;
	
	private String projectIdAutocomplete;
	
	private Long moduleId;
	
	private String moduleCode;
	
	private String moduleIdAutocomplete;
	
	private String moduleName;
	
	private String messageType;
	
	private String remark;
	
	@NotEmpty (message="sc.languagedesign.0002", groups = { RegistrationForm.class })
	private String languageId;

	private String languageCode;
	
	private String languageIdAutocomplete;
	
	private String countryCode;
	
	private Timestamp updatedDate;
	
	private Long updatedBy;
	
	private Integer messageLevel;
	
	@Valid
	private MultipleMessageDesignForm[] multipleMessageDesignForm = new MultipleMessageDesignForm[0];
	
	private List<MessageDesign> messageDesigns;
	
	private boolean hasErrors = false;
	
	private String mode;
	
	private String screenType;
	
	public String getScreenType() {
		return screenType;
	}

	public void setScreenType(String screenType) {
		this.screenType = screenType;
	}
	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	/**
	 * @return the messageDesignId
	 */
	public Long getMessageDesignId() {
		return messageDesignId;
	}

	/**
	 * @param messageDesignId the messageDesignId to set
	 */
	public void setMessageDesignId(Long messageDesignId) {
		this.messageDesignId = messageDesignId;
	}

	/**
	 * @return the projectId
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the projectIdAutocomplete
	 */
	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	/**
	 * @param projectIdAutocomplete the projectIdAutocomplete to set
	 */
	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
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

	/**
	 * @return the moduleIdAutocomplete
	 */
	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	/**
	 * @param moduleIdAutocomplete the moduleIdAutocomplete to set
	 */
	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param languageCode the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public String getLanguageIdAutocomplete() {
		return languageIdAutocomplete;
	}

	public void setLanguageIdAutocomplete(String languageIdAutocomplete) {
		this.languageIdAutocomplete = languageIdAutocomplete;
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

	/**
	 * @return the multipleMessageDesignForm
	 */
	public MultipleMessageDesignForm[] getMultipleMessageDesignForm() {
		return multipleMessageDesignForm;
	}

	/**
	 * @param multipleMessageDesignForm the multipleMessageDesignForm to set
	 */
	public void setMultipleMessageDesignForm(
			MultipleMessageDesignForm[] multipleMessageDesignForm) {
		this.multipleMessageDesignForm = multipleMessageDesignForm;
	}

	public List<MessageDesign> getMessageDesigns() {
		return messageDesigns;
	}

	public void setMessageDesigns(List<MessageDesign> messageDesigns) {
		this.messageDesigns = messageDesigns;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

}
