package org.terasoluna.qp.domain.repository.decisiontable;

import java.io.Serializable;

public class DecisionTableSearchCriteria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long projectId;
	
	private String projectIdAutocomplete;
	
	private Long moduleId;
	
	private String moduleName;
	
	private String moduleIdAutocomplete;
	
	private String decisionTbName;
	
	private String decisionTbCode;
	
	private int[] designStatus = new int[0];
	
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
	 * @return the decisionTbName
	 */
	public String getDecisionTbName() {
		return decisionTbName;
	}

	/**
	 * @param decisionTbName the decisionTbName to set
	 */
	public void setDecisionTbName(String decisionTbName) {
		this.decisionTbName = decisionTbName;
	}

	/**
	 * @return the decisionTbCode
	 */
	public String getDecisionTbCode() {
		return decisionTbCode;
	}

	/**
	 * @param decisionTbCode the decisionTbCode to set
	 */
	public void setDecisionTbCode(String decisionTbCode) {
		this.decisionTbCode = decisionTbCode;
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
	 * @return the designStatus
	 */
	public int[] getDesignStatus() {
		return designStatus;
	}

	/**
	 * @param designStatus the designStatus to set
	 */
	public void setDesignStatus(int[] designStatus) {
		this.designStatus = designStatus;
	}
	
}
