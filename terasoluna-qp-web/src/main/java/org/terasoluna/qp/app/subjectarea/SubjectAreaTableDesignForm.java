package org.terasoluna.qp.app.subjectarea;

import java.io.Serializable;

public class SubjectAreaTableDesignForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long tableDesignId;
	
	private Long projectId;
	
	private String tableName;
	
	private String tableDesignIdAutocomplete;

	private String tableCode;
	
	/**
	 * @return the tableDesignId
	 */
	public Long getTableDesignId() {
		return tableDesignId;
	}

	/**
	 * @param tableDesignId the tableDesignId to set
	 */
	public void setTableDesignId(Long tableDesignId) {
		this.tableDesignId = tableDesignId;
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
	 * @return the tableDesignIdAutocomplete
	 */
	public String getTableDesignIdAutocomplete() {
		return tableDesignIdAutocomplete;
	}

	/**
	 * @param tableDesignIdAutocomplete the tableDesignIdAutocomplete to set
	 */
	public void setTableDesignIdAutocomplete(String tableDesignIdAutocomplete) {
		this.tableDesignIdAutocomplete = tableDesignIdAutocomplete;
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
	
}
