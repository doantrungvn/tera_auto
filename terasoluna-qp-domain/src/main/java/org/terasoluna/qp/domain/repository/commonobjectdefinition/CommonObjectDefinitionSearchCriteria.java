package org.terasoluna.qp.domain.repository.commonobjectdefinition;

import java.io.Serializable;

public class CommonObjectDefinitionSearchCriteria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String commonObjectDefinitionName;

	private String commonObjectDefinitionCode;

	private Long moduleId;

	private Long projectId;

	private Long languageId;

	public String getCommonObjectDefinitionName() {
		return commonObjectDefinitionName;
	}

	public void setCommonObjectDefinitionName(String commonObjectDefinitionName) {
		this.commonObjectDefinitionName = commonObjectDefinitionName;
	}

	public String getCommonObjectDefinitionCode() {
		return commonObjectDefinitionCode;
	}

	public void setCommonObjectDefinitionCode(String commonObjectDefinitionCode) {
		this.commonObjectDefinitionCode = commonObjectDefinitionCode;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

}
