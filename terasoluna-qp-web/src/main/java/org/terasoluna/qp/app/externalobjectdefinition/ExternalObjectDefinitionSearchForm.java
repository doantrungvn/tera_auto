package org.terasoluna.qp.app.externalobjectdefinition;

import java.io.Serializable;

public class ExternalObjectDefinitionSearchForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String externalObjectDefinitionName;

	private String externalObjectDefinitionCode;
	
	private Integer moduleId;
	
	private String moduleIdAutocomplete;
	
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

	public void setExternalObjectDefinitionName(
			String externalObjectDefinitionName) {
		this.externalObjectDefinitionName = externalObjectDefinitionName;
	}

	public String getExternalObjectDefinitionCode() {
		return externalObjectDefinitionCode;
	}

	public void setExternalObjectDefinitionCode(
			String externalObjectDefinitionCode) {
		this.externalObjectDefinitionCode = externalObjectDefinitionCode;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}
}
