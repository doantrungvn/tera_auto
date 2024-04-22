package org.terasoluna.qp.app.module;

import java.io.Serializable;

public class ModuleSearchForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private String moduleCode;
	
	private String moduleName;
	
	private Long projectId;
	
	private Integer projectStatus;
	
	private Long businessTypeId;
	
	private String businessTypeName; 

	private Integer[] confirmationTypes = new Integer[0];

	private Integer[] completionTypes = new Integer[0];
	
	private Integer[] statuses = new Integer[0];	
	
	private Long createdBy;
	
	private String createdByAutocomplete;

	private String fromCreatedDate;
	
	private String toCreatedDate;

	private Long updatedBy;
	
	private String updatedByAutocomplete;

	private String fromUpdatedDate;
	
	private String toUpdatedDate;
	
	private Integer[] moduleTypes;

	/**
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
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
	 * @return the businessTypeId
	 */
	public Long getBusinessTypeId() {
		return businessTypeId;
	}

	/**
	 * @param businessTypeId the businessTypeId to set
	 */
	public void setBusinessTypeId(Long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	/**
	 * @return the businessTypeName
	 */
	public String getBusinessTypeName() {
		return businessTypeName;
	}

	/**
	 * @param businessTypeName the businessTypeName to set
	 */
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	/**
	 * @return the confirmationTypes
	 */
	public Integer[] getConfirmationTypes() {
		return confirmationTypes;
	}

	/**
	 * @param confirmationTypes the confirmationTypes to set
	 */
	public void setConfirmationTypes(Integer[] confirmationTypes) {
		this.confirmationTypes = confirmationTypes;
	}

	/**
	 * @return the completionTypes
	 */
	public Integer[] getCompletionTypes() {
		return completionTypes;
	}

	/**
	 * @param completionTypes the completionTypes to set
	 */
	public void setCompletionTypes(Integer[] completionTypes) {
		this.completionTypes = completionTypes;
	}

	/**
	 * @return the statuses
	 */
	public Integer[] getStatuses() {
		return statuses;
	}

	/**
	 * @param statuses the statuses to set
	 */
	public void setStatuses(Integer[] statuses) {
		this.statuses = statuses;
	}

	public Integer getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Integer projectStatus) {
		this.projectStatus = projectStatus;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdByAutocomplete
	 */
	public String getCreatedByAutocomplete() {
		return createdByAutocomplete;
	}

	/**
	 * @param createdByAutocomplete the createdByAutocomplete to set
	 */
	public void setCreatedByAutocomplete(String createdByAutocomplete) {
		this.createdByAutocomplete = createdByAutocomplete;
	}

	/**
	 * @return the fromCreatedDate
	 */
	public String getFromCreatedDate() {
		return fromCreatedDate;
	}

	/**
	 * @param fromCreatedDate the fromCreatedDate to set
	 */
	public void setFromCreatedDate(String fromCreatedDate) {
		this.fromCreatedDate = fromCreatedDate;
	}

	/**
	 * @return the toCreatedDate
	 */
	public String getToCreatedDate() {
		return toCreatedDate;
	}

	/**
	 * @param toCreatedDate the toCreatedDate to set
	 */
	public void setToCreatedDate(String toCreatedDate) {
		this.toCreatedDate = toCreatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedByAutocomplete
	 */
	public String getUpdatedByAutocomplete() {
		return updatedByAutocomplete;
	}

	/**
	 * @param updatedByAutocomplete the updatedByAutocomplete to set
	 */
	public void setUpdatedByAutocomplete(String updatedByAutocomplete) {
		this.updatedByAutocomplete = updatedByAutocomplete;
	}

	/**
	 * @return the fromUpdatedDate
	 */
	public String getFromUpdatedDate() {
		return fromUpdatedDate;
	}

	/**
	 * @param fromUpdatedDate the fromUpdatedDate to set
	 */
	public void setFromUpdatedDate(String fromUpdatedDate) {
		this.fromUpdatedDate = fromUpdatedDate;
	}

	/**
	 * @return the toUpdatedDate
	 */
	public String getToUpdatedDate() {
		return toUpdatedDate;
	}

	/**
	 * @param toUpdatedDate the toUpdatedDate to set
	 */
	public void setToUpdatedDate(String toUpdatedDate) {
		this.toUpdatedDate = toUpdatedDate;
	}

	public Integer[] getModuleTypes() {
		return moduleTypes;
	}

	public void setModuleTypes(Integer[] moduleTypes) {
		this.moduleTypes = moduleTypes;
	}

	
}
