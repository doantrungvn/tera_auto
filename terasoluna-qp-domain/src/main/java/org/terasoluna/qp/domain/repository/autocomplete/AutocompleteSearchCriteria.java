package org.terasoluna.qp.domain.repository.autocomplete;

import java.io.Serializable;

public class AutocompleteSearchCriteria implements Serializable {

	private static final long serialVersionUID = -916259283217717465L;

	private String autocompleteCode;
	private String autocompleteName;
	private Long moduleId;
	private Long projectId;
	private String projectIdAutocomplete;
	private String moduleIdAutocomplete;
	private int[] matchingTypes;
	private int[] sqlDesignTypes;
	private Long tableId;
	private Integer[] designStatus;
	private Integer[] autocompleteTypes;

	public Integer[] getAutocompleteTypes() {
		return autocompleteTypes;
	}
	public void setAutocompleteTypes(Integer[] autocompleteTypes) {
		this.autocompleteTypes = autocompleteTypes;
	}
	public String getAutocompleteCode() {
		return autocompleteCode;
	}
	public void setAutocompleteCode(String autocompleteCode) {
		this.autocompleteCode = autocompleteCode;
	}
	public String getAutocompleteName() {
		return autocompleteName;
	}
	public void setAutocompleteName(String autocompleteName) {
		this.autocompleteName = autocompleteName;
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
	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}
	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}
	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}
	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}
	public int[] getMatchingTypes() {
		return matchingTypes;
	}
	public void setMatchingTypes(int[] matchingTypes) {
		this.matchingTypes = matchingTypes;
	}
	public int[] getSqlDesignTypes() {
		return sqlDesignTypes;
	}
	public void setSqlDesignTypes(int[] sqlDesignTypes) {
		this.sqlDesignTypes = sqlDesignTypes;
	}
	public Long getTableId() {
		return tableId;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	public Integer[] getDesignStatus() {
		return designStatus;
	}
	public void setDesignStatus(Integer[] designStatus) {
		this.designStatus = designStatus;
	}

}
