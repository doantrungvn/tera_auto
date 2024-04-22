package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class SubjectAreaTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long tableId;
	private String tableName;
	private String tableCode;
	
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
	 * @return the tableId
	 */
	public Long getTableId() {
		return tableId;
	}

	/**
	 * @param tableId the tableId to set
	 */
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
}
