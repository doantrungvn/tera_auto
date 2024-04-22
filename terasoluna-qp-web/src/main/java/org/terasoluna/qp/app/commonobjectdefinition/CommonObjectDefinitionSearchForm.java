package org.terasoluna.qp.app.commonobjectdefinition;

import java.io.Serializable;

public class CommonObjectDefinitionSearchForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String commonObjectDefinitionName;

	private String commonObjectDefinitionCode;
	
	private Integer moduleId;
	
	private String moduleIdAutocomplete;

	private int[] commonObjectDefinitionType;

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

	public int[] getCommonObjectDefinitionType() {
		return commonObjectDefinitionType;
	}

	public void setCommonObjectDefinitionType(int[] commonObjectDefinitionType) {
		this.commonObjectDefinitionType = commonObjectDefinitionType;
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
