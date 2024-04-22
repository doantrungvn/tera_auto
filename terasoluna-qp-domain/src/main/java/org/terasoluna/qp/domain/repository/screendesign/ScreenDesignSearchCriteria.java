package org.terasoluna.qp.domain.repository.screendesign;

import java.io.Serializable;

import org.terasoluna.qp.domain.model.MessageDesign;

public class ScreenDesignSearchCriteria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long projectId;
	private Long moduleId;
	private int[] templateTypes;
	private String screenName;
	private String screenCode;
	private int[] screenParternTypes;
	private MessageDesign messageDesign;
	private Long languageId;
	private int[] designMode;
	private int[] designStatus;
	private Long functionDesignId;
	

	public int[] getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(int[] designStatus) {
		this.designStatus = designStatus;
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
	 * @return the templateTypes
	 */
	public int[] getTemplateTypes() {
		return templateTypes;
	}
	
	/**
	 * @param templateTypes the templateTypes to set
	 */
	public void setTemplateTypes(int[] templateTypes) {
		this.templateTypes = templateTypes;
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
	 * @return the screenCode
	 */
	public String getScreenCode() {
		return screenCode;
	}
	
	/**
	 * @param screenCode the screenCode to set
	 */
	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}
	
	/**
	 * @return the screenParternTypes
	 */
	public int[] getScreenParternTypes() {
		return screenParternTypes;
	}
	
	/**
	 * @param screenParternTypes the screenParternTypes to set
	 */
	public void setScreenParternTypes(int[] screenParternTypes) {
		this.screenParternTypes = screenParternTypes;
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
	 * @return the messageDesign
	 */
	public MessageDesign getMessageDesign() {
		return messageDesign;
	}

	/**
	 * @param messageDesign the messageDesign to set
	 */
	public void setMessageDesign(MessageDesign messageDesign) {
		this.messageDesign = messageDesign;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public int[] getDesignMode() {
		return designMode;
	}

	public void setDesignMode(int[] designMode) {
		this.designMode = designMode;
	}

	public Long getFunctionDesignId() {
		return functionDesignId;
	}

	public void setFunctionDesignId(Long functionDesignId) {
		this.functionDesignId = functionDesignId;
	}
}
