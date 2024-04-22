package org.terasoluna.qp.domain.repository.module;

import java.io.Serializable;
import java.sql.Timestamp;;

public class ModuleCriteria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String moduleCode;
	
	private String moduleName;
	
	private Long projectId;
	
	private Long businessTypeId;

	private Integer[] statuses;
	
	private Integer[] confirmationTypes;
	
	private Integer[] completionTypes;

	private Long createdBy;
	
	private Timestamp fromCreatedDate;
	
	private Timestamp toCreatedDate;
	
	private Long updatedBy;
	
	private Timestamp fromUpdatedDate;
	
	private Timestamp toUpdatedDate;
	
	private Integer[] moduleTypes;

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(Long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public Integer[] getStatuses() {
		return statuses;
	}

	public void setStatuses(Integer[] statuses) {
		this.statuses = statuses;
	}

	public Integer[] getConfirmationTypes() {
		return confirmationTypes;
	}

	public void setConfirmationTypes(Integer[] confirmationTypes) {
		this.confirmationTypes = confirmationTypes;
	}

	public Integer[] getCompletionTypes() {
		return completionTypes;
	}

	public void setCompletionTypes(Integer[] completionTypes) {
		this.completionTypes = completionTypes;
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
	 * @return the fromCreatedDate
	 */
	public Timestamp getFromCreatedDate() {
		return fromCreatedDate;
	}

	/**
	 * @param fromCreatedDate the fromCreatedDate to set
	 */
	public void setFromCreatedDate(Timestamp fromCreatedDate) {
		this.fromCreatedDate = fromCreatedDate;
	}

	/**
	 * @return the toCreatedDate
	 */
	public Timestamp getToCreatedDate() {
		return toCreatedDate;
	}

	/**
	 * @param toCreatedDate the toCreatedDate to set
	 */
	public void setToCreatedDate(Timestamp toCreatedDate) {
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
	 * @return the fromUpdatedDate
	 */
	public Timestamp getFromUpdatedDate() {
		return fromUpdatedDate;
	}

	/**
	 * @param fromUpdatedDate the fromUpdatedDate to set
	 */
	public void setFromUpdatedDate(Timestamp fromUpdatedDate) {
		this.fromUpdatedDate = fromUpdatedDate;
	}

	/**
	 * @return the toUpdatedDate
	 */
	public Timestamp getToUpdatedDate() {
		return toUpdatedDate;
	}

	/**
	 * @param toUpdatedDate the toUpdatedDate to set
	 */
	public void setToUpdatedDate(Timestamp toUpdatedDate) {
		this.toUpdatedDate = toUpdatedDate;
	}

	public Integer[] getModuleTypes() {
		return moduleTypes;
	}

	public void setModuleTypes(Integer[] moduleTypes) {
		this.moduleTypes = moduleTypes;
	}
	
	
}
