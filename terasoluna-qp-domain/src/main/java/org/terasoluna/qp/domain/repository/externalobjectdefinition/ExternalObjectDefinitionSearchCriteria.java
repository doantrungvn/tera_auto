package org.terasoluna.qp.domain.repository.externalobjectdefinition;

import java.io.Serializable;

public class ExternalObjectDefinitionSearchCriteria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String externalObjectDefinitionName;

	private String externalObjectDefinitionCode;
	
	private Long moduleId;
	
	private Long projectId;

	private Long languageId;
	
	private String externalObjectType;
	
	public String getExternalObjectType() {
		return externalObjectType;
	}

	public void setExternalObjectType(String externalObjectType) {
		this.externalObjectType = externalObjectType;
	}

	public String getExternalObjectDefinitionName() {
		return externalObjectDefinitionName;
	}

	public void setExternalObjectDefinitionName(String externalObjectDefinitionName) {
		this.externalObjectDefinitionName = externalObjectDefinitionName;
	}

	public String getExternalObjectDefinitionCode() {
		return externalObjectDefinitionCode;
	}

	public void setExternalObjectDefinitionCode(String externalObjectDefinitionCode) {
		this.externalObjectDefinitionCode = externalObjectDefinitionCode;
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
	 * @return the languageId
	 */
	public Long getLanguageId() {
		return languageId;
	}

	/**
	 * @param languageId the languageId to set
	 */
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
}
