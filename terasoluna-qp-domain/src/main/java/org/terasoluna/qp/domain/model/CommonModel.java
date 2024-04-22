package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class CommonModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long projectId;

	private Long workingProjectId;

	private Project WorkingProject;

	private Long workingLanguageId;

	private LanguageDesign workingLanguageDesign;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private Timestamp systemTime;

	private Boolean designStatus;
	
	private AccountProfile accountProfile;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getWorkingProjectId() {
		return workingProjectId;
	}

	public void setWorkingProjectId(Long workingProjectId) {
		this.workingProjectId = workingProjectId;
	}

	public Long getWorkingLanguageId() {
		return workingLanguageId;
	}

	public void setWorkingLanguageId(Long workingLanguageId) {
		this.workingLanguageId = workingLanguageId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Timestamp getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Timestamp systemTime) {
		this.systemTime = systemTime;
	}

	public Boolean getDesignStatus() {
		return designStatus == null ? true : designStatus;
	}

	public void setDesignStatus(Boolean designStatus) {
		this.designStatus = designStatus;
	}

	public LanguageDesign getWorkingLanguageDesign() {
		return workingLanguageDesign;
	}

	public void setWorkingLanguageDesign(LanguageDesign workingLanguageDesign) {
		this.workingLanguageDesign = workingLanguageDesign;
	}

	public Project getWorkingProject() {
		return WorkingProject;
	}

	public void setWorkingProject(Project workingProject) {
		WorkingProject = workingProject;
	}

	public AccountProfile getAccountProfile() {
		return accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}
}
