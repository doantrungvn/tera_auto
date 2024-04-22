package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class ImportAssignValue implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long importAssignValueId;
	
	private Long importFileComponentId;

	private Integer targetScope;
	
	private String targetId;

	private String targetIdAutocomplete;

	private String columnNo;
	
	private String dataGroup;
	
	private Integer dataType;

	public Long getImportAssignValueId() {
		return importAssignValueId;
	}

	public void setImportAssignValueId(Long importAssignValueId) {
		this.importAssignValueId = importAssignValueId;
	}

	public Long getImportFileComponentId() {
		return importFileComponentId;
	}

	public void setImportFileComponentId(Long importFileComponentId) {
		this.importFileComponentId = importFileComponentId;
	}

	public Integer getTargetScope() {
		return targetScope;
	}

	public void setTargetScope(Integer targetScope) {
		this.targetScope = targetScope;
	}

	public String getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(String columnNo) {
		this.columnNo = columnNo;
	}

	/**
	 * @return the targetId
	 */
	public String getTargetId() {
		return targetId;
	}

	/**
	 * @param targetId the targetId to set
	 */
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	/**
	 * @return the targetIdAutocomplete
	 */
	public String getTargetIdAutocomplete() {
		return targetIdAutocomplete;
	}

	/**
	 * @param targetIdAutocomplete the targetIdAutocomplete to set
	 */
	public void setTargetIdAutocomplete(String targetIdAutocomplete) {
		this.targetIdAutocomplete = targetIdAutocomplete;
	}

	/**
	 * @return the dataGroup
	 */
	public String getDataGroup() {
		return dataGroup;
	}

	/**
	 * @param dataGroup the dataGroup to set
	 */
	public void setDataGroup(String dataGroup) {
		this.dataGroup = dataGroup;
	}

	/**
	 * @return the dataType
	 */
	public Integer getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
}
