/*
 * @(#)ProjectSearchForm.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.domain.repository.project;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Search criteria of project
 *
 * @author TienND
 * @version 1.0 2015/05/20
 */
public class ProjectCriteria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;
	private String projectName;
	private String projectCode;
	private Long createdBy;
	private Long updatedBy;
	private Timestamp fromCreatedDate;
	private Timestamp toCreatedDate;
	private Timestamp fromUpdatedDate;
	private Timestamp toUpdatedDate;
	
	/**
	 * statuses
	 */
	private int[] statuses = new int[0];

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createBy) {
		this.createdBy = createBy;
	}

	public Timestamp getFromCreatedDate() {
		return fromCreatedDate;
	}

	public void setFromCreatedDate(Timestamp fromCreatedDate) {
		this.fromCreatedDate = fromCreatedDate;
	}

	public Timestamp getToCreatedDate() {
		return toCreatedDate;
	}

	public void setToCreatedDate(Timestamp toCreatedDate) {
		this.toCreatedDate = toCreatedDate;
	}


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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getFromUpdatedDate() {
		return fromUpdatedDate;
	}

	public void setFromUpdatedDate(Timestamp fromUpdatedDate) {
		this.fromUpdatedDate = fromUpdatedDate;
	}

	public Timestamp getToUpdatedDate() {
		return toUpdatedDate;
	}

	public void setToUpdatedDate(Timestamp toUpdatedDate) {
		this.toUpdatedDate = toUpdatedDate;
	}
}
