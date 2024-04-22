package org.terasoluna.qp.app.accountproject;

import java.io.Serializable;

public class AccountProjectForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long accountId;
	private Long projectId;
	private String projectName;
	private String projectCode;
	private int status;
	private boolean selected;
	private boolean owerProject;
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isOwerProject() {
		return owerProject;
	}

	public void setOwerProject(boolean owerProject) {
		this.owerProject = owerProject;
	}

}
