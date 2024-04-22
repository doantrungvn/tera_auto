/*
 * @(#)ProjectSearchForm.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.app.project;

import java.io.Serializable;
/**
 * Form bean of search project
 *
 * @author TienND
 * @version 1.0 2015/05/20
 */
public class ProjectSearchForm implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * projectName
	 */
	private String projectName;
	
	/**
	 * projectCode
	 */
	private String projectCode;
	
	private Long createdBy;
	private Long updatedBy;
	private String fromCreatedDate;
	private String toCreatedDate;
	private String fromUpdatedDate;
	private String toUpdatedDate;
	private String createdByAutocomplete;
	private String updatedByAutocomplete;
	
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createBy) {
		this.createdBy = createBy;
	}

	public String getFromCreatedDate() {
		return fromCreatedDate;
	}

	public void setFromCreatedDate(String fromCreatedDate) {
		this.fromCreatedDate = fromCreatedDate;
	}

	public String getToCreatedDate() {
		return toCreatedDate;
	}

	public void setToCreatedDate(String toCreatedDate) {
		this.toCreatedDate = toCreatedDate;
	}
	
	/**
	 * statuses
	 */
	private int[] statuses = new int[0];

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public int[] getStatuses() {
		return statuses;
	}

	public void setStatuses(int[] statuses) {
		this.statuses = statuses;
	}

	public String getCreatedByAutocomplete() {
		return createdByAutocomplete;
	}

	public void setCreatedByAutocomplete(String createdByAutocomplete) {
		this.createdByAutocomplete = createdByAutocomplete;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedByAutocomplete() {
		return updatedByAutocomplete;
	}

	public void setUpdatedByAutocomplete(String updatedByAutocomplete) {
		this.updatedByAutocomplete = updatedByAutocomplete;
	}

	public String getFromUpdatedDate() {
		return fromUpdatedDate;
	}

	public void setFromUpdatedDate(String fromUpdatedDate) {
		this.fromUpdatedDate = fromUpdatedDate;
	}

	public String getToUpdatedDate() {
		return toUpdatedDate;
	}

	public void setToUpdatedDate(String toUpdatedDate) {
		this.toUpdatedDate = toUpdatedDate;
	}
}
