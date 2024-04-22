package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class ProjectTheme implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long projectThemeId;
	private Long projectId;
	private int projectStatus;
	private String code;
	private String value;
	private Long accountId;
	

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getProjectThemeId() {
		return projectThemeId;
	}
	public void setProjectThemeId(Long projectThemeId) {
		this.projectThemeId = projectThemeId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public int getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	

}
