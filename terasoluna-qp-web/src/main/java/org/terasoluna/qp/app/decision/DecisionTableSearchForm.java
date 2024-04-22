package org.terasoluna.qp.app.decision;

import java.io.Serializable;

public class DecisionTableSearchForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private String projectId;
	
	private String projectIdAutocomplete;
	
	private String moduleId;
	
	private String moduleName;
	
	private String moduleIdAutocomplete;
	
	private String decisionTbName;
	
	private String decisionTbCode;
	
	private Integer status;
	
	private int[] designStatus = new int[0];

	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(String projectId) {
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

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}