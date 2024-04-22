package org.terasoluna.qp.app.autocomplete;

import java.io.Serializable;

public class AutocompleteSearchForm implements Serializable {

	private static final long serialVersionUID = -9170873584006168492L;
	private String autocompleteId;
	private String autocompleteName;
	private String autocompleteCode;
	private String projectId;
	private String projectIdAutocomplete;
	private String moduleId;
	private String moduleIdAutocomplete;
	private String moduleName;
	private String matchingType="0";
	private String minLength;
	private Boolean omitOverlap;
	private String[] matchingTypes;
	private String[] autocompleteTypes;
	private String init;
	private String tableId;
	private String tableIdAutocomplete;

	public String[] getAutocompleteTypes() {
		return autocompleteTypes;
	}
	public void setAutocompleteTypes(String[] autocompleteTypes) {
		this.autocompleteTypes = autocompleteTypes;
	}
	private Integer[] designStatus;
	
	public String getInit() {
		return init;
	}
	public void setInit(String init) {
		this.init = init;
	}
	public String getAutocompleteId() {
		return autocompleteId;
	}
	public void setAutocompleteId(String autocompleteId) {
		this.autocompleteId = autocompleteId;
	}
	public String getAutocompleteName() {
		return autocompleteName;
	}
	public void setAutocompleteName(String autocompleteName) {
		this.autocompleteName = autocompleteName;
	}
	public String getAutocompleteCode() {
		return autocompleteCode;
	}
	public void setAutocompleteCode(String autocompleteCode) {
		this.autocompleteCode = autocompleteCode;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}
	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}
	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getMatchingType() {
		return matchingType;
	}
	public void setMatchingType(String matchingType) {
		this.matchingType = matchingType;
	}
	public String getMinLength() {
		return minLength;
	}
	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}
	public Boolean getOmitOverlap() {
		return omitOverlap;
	}
	public void setOmitOverlap(Boolean omitOverlap) {
		this.omitOverlap = omitOverlap;
	}
	public String[] getMatchingTypes() {
		return matchingTypes;
	}
	public void setMatchingTypes(String[] matchingTypes) {
		this.matchingTypes = matchingTypes;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getTableIdAutocomplete() {
		return tableIdAutocomplete;
	}
	public void setTableIdAutocomplete(String tableIdAutocomplete) {
		this.tableIdAutocomplete = tableIdAutocomplete;
	}
	public Integer[] getDesignStatus() {
		return designStatus;
	}
	public void setDesignStatus(Integer[] designStatus) {
		this.designStatus = designStatus;
	}
}
