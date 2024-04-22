package org.terasoluna.qp.domain.repository.messagedesign;

import java.io.Serializable;

public class MessageDesignCriteria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String messageString;
	
	private String messageCode;
	
	private Long projectId;
	
	private Long moduleId;
	
	private Long screenId;
	
	private Long businessLogicId;
	
	private String fromLanguageCode;
	
	private Long fromLanguageId;
	
	private String toLanguageCode;
	
	private Long toLanguageId;
	
	private Integer[] messageLevels = new Integer[0];
	
	private String[] messageTypes = new String[0];
	
	private Integer[] generatedStatus = new Integer[0]; 

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
	 * @return the screenId
	 */
	public Long getScreenId() {
		return screenId;
	}

	/**
	 * @param screenId the screenId to set
	 */
	public void setScreenId(Long screenId) {
		this.screenId = screenId;
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

	public String getFromLanguageCode() {
		return fromLanguageCode;
	}

	public void setFromLanguageCode(String fromLanguageCode) {
		this.fromLanguageCode = fromLanguageCode;
	}

	public Long getFromLanguageId() {
		return fromLanguageId;
	}

	public void setFromLanguageId(Long fromLanguageId) {
		this.fromLanguageId = fromLanguageId;
	}

	public String getToLanguageCode() {
		return toLanguageCode;
	}

	public void setToLanguageCode(String toLanguageCode) {
		this.toLanguageCode = toLanguageCode;
	}

	public Long getToLanguageId() {
		return toLanguageId;
	}

	public void setToLanguageId(Long toLanguageId) {
		this.toLanguageId = toLanguageId;
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

	public Integer[] getGeneratedStatus() {
		return generatedStatus;
	}

	public void setGeneratedStatus(Integer[] generatedStatus) {
		this.generatedStatus = generatedStatus;
	}
}
