package org.terasoluna.qp.app.messagedesign;

import java.io.Serializable;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.message.MessageDesignMessageConst;

public class MessageDesignSearchForm implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static interface SearchMessageDesignForm {};
	
	public static interface ModificationMessageDesignForm {};
	
	private Long messageDesignId;
	
	private String messageString;
	
	private String messageCode;
	
	private Long projectId;
	
	private String projectIdAutocomplete;

	private String moduleId;
	
	private String moduleIdAutocomplete;
	
	private String screenId;
	
	private String screenIdAutocomplete;
	
	private Long businessLogicId;
	
	private String businessLogicIdAutocomplete;
	
	private String businessLogicName;
	
	private String fromLanguageCode;
	
	@NotEmpty(message = MessageDesignMessageConst.SC_MESSAGEDESIGN_0002, groups = { SearchMessageDesignForm.class })
	private String fromLanguageId;
	
	private String fromLanguageIdAutocomplete;
	
	private String toLanguageCode;
	
	private String toLanguageIdAutocomplete;
	
	private Long toLanguageId;
	
	private Integer[] messageLevels = new Integer[0];
	
	private String[] messageTypes = new String[0];
	
	private Integer[] fromGeneratedStatus = new Integer[0]; 
	
	private Integer[] toGeneratedStatus = new Integer[0]; 
	
	private Integer[] generatedStatus = new Integer[0];
	
	@Valid
	private ModifyMessageDesignForm[] modifyMessageDesignForm = new ModifyMessageDesignForm[0];
	
	private Boolean flagAction;
	
	public Integer[] getGeneratedStatus() {
		return generatedStatus;
	}

	public void setGeneratedStatus(Integer[] generatedStatus) {
		this.generatedStatus = generatedStatus;
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
	 * @return the messageCode
	 */
	public String getMessageCode() {
		return messageCode;
	}

	/**
	 * @param messageCode the messageCode to set
	 */
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}


	public Long getProjectId() {
		return projectId;
	}

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
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
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
	 * @return the screenId
	 */
	public String getScreenId() {
		return screenId;
	}

	/**
	 * @param screenId the screenId to set
	 */
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	/**
	 * @return the screenIdAutocomplete
	 */
	public String getScreenIdAutocomplete() {
		return screenIdAutocomplete;
	}

	/**
	 * @param screenIdAutocomplete the screenIdAutocomplete to set
	 */
	public void setScreenIdAutocomplete(String screenIdAutocomplete) {
		this.screenIdAutocomplete = screenIdAutocomplete;
	}

	/**
	 * @return the businessLogicId
	 */
	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	/**
	 * @param businessLogicId the businessLogicId to set
	 */
	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	/**
	 * @return the businessLogicIdAutocomplete
	 */
	public String getBusinessLogicIdAutocomplete() {
		return businessLogicIdAutocomplete;
	}

	/**
	 * @param businessLogicIdAutocomplete the businessLogicIdAutocomplete to set
	 */
	public void setBusinessLogicIdAutocomplete(String businessLogicIdAutocomplete) {
		this.businessLogicIdAutocomplete = businessLogicIdAutocomplete;
	}

	/**
	 * @return the businessLogicName
	 */
	public String getBusinessLogicName() {
		return businessLogicName;
	}

	/**
	 * @param businessLogicName the businessLogicName to set
	 */
	public void setBusinessLogicName(String businessLogicName) {
		this.businessLogicName = businessLogicName;
	}

	/**
	 * @return the fromLanguageCode
	 */
	public String getFromLanguageCode() {
		return fromLanguageCode;
	}

	/**
	 * @param fromLanguageCode the fromLanguageCode to set
	 */
	public void setFromLanguageCode(String fromLanguageCode) {
		this.fromLanguageCode = fromLanguageCode;
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
	 * @return the messageLevels
	 */
	public Integer[] getMessageLevels() {
		return messageLevels;
	}

	/**
	 * @param messageLevels the messageLevels to set
	 */
	public void setMessageLevels(Integer[] messageLevels) {
		this.messageLevels = messageLevels;
	}

	/**
	 * @return the messageTypes
	 */
	public String[] getMessageTypes() {
		return messageTypes;
	}

	/**
	 * @param messageTypes the messageTypes to set
	 */
	public void setMessageTypes(String[] messageTypes) {
		this.messageTypes = messageTypes;
	}
	
	public Integer[] getFromGeneratedStatus() {
		return fromGeneratedStatus;
	}

	public void setFromGeneratedStatus(Integer[] fromGeneratedStatus) {
		this.fromGeneratedStatus = fromGeneratedStatus;
	}

	public Integer[] getToGeneratedStatus() {
		return toGeneratedStatus;
	}

	public void setToGeneratedStatus(Integer[] toGeneratedStatus) {
		this.toGeneratedStatus = toGeneratedStatus;
	}

	public String getFromLanguageIdAutocomplete() {
		return fromLanguageIdAutocomplete;
	}

	public void setFromLanguageIdAutocomplete(String fromLanguageIdAutocomplete) {
		this.fromLanguageIdAutocomplete = fromLanguageIdAutocomplete;
	}

	public String getToLanguageIdAutocomplete() {
		return toLanguageIdAutocomplete;
	}

	public void setToLanguageIdAutocomplete(String toLanguageIdAutocomplete) {
		this.toLanguageIdAutocomplete = toLanguageIdAutocomplete;
	}

	public ModifyMessageDesignForm[] getModifyMessageDesignForm() {
		return modifyMessageDesignForm;
	}

	public void setModifyMessageDesignForm(
			ModifyMessageDesignForm[] modifyMessageDesignForm) {
		this.modifyMessageDesignForm = modifyMessageDesignForm;
	}

	public Boolean getFlagAction() {
		return flagAction;
	}

	public void setFlagAction(Boolean flagAction) {
		this.flagAction = flagAction;
	}

	public String getFromLanguageId() {
		return fromLanguageId;
	}

	public void setFromLanguageId(String fromLanguageId) {
		this.fromLanguageId = fromLanguageId;
	}

	public Long getToLanguageId() {
		return toLanguageId;
	}

	public void setToLanguageId(Long toLanguageId) {
		this.toLanguageId = toLanguageId;
	}
}
