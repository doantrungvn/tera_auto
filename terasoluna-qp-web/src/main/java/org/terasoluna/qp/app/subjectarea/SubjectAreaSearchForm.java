package org.terasoluna.qp.app.subjectarea;

import java.io.Serializable;

public class SubjectAreaSearchForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String areaId;
	private String areaName;
	private String areaCode;
	private String tableName;
	private String tableCode;
	private Long projectId;
	private String projectName;
	private String projectIdAutocomplete;
	private String keyword;
	
	/**
	 * @return the areaId
	 */
	public String getAreaId() {
		return areaId;
	}
	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the tableCode
	 */
	public String getTableCode() {
		return tableCode;
	}
	/**
	 * @param tableCode the tableCode to set
	 */
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
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
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the projectIdAutocomplete
	 */
	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}
	/**
	 * @param projectIdAutocomplete the projectIdAutocomplete to set
	 */
	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
