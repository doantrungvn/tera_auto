package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class SqlDesignResult implements Serializable{
	
	private static final long serialVersionUID = 9050180310268684553L;
	private Long resultId;
	private Long sqlDesignId;
	private String functionCode;
	private Long tableId;
	private String tableName;
	private String tableCode;
	private String tableMissingFlag;
	private Long columnId;
	private String columnName;
	private String columnCode;
	private String columnMissingFlag;
	private Integer itemSeqNo;
	private Integer enabledFlag;
	private Integer dataType;
	private Integer dataTypeBackup;
	private String mappingAlias;
	private Integer tableType;
	
	public Long getResultId() {
		return resultId;
	}
	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}
	public Long getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(Long sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public Long getTableId() {
		return tableId;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableMissingFlag() {
		return tableMissingFlag;
	}
	public void setTableMissingFlag(String tableMissingFlag) {
		this.tableMissingFlag = tableMissingFlag;
	}
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnMissingFlag() {
		return columnMissingFlag;
	}
	public void setColumnMissingFlag(String columnMissingFlag) {
		this.columnMissingFlag = columnMissingFlag;
	}
	public Integer getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(Integer enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public Integer getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public Integer getDataTypeBackup() {
		return dataTypeBackup;
	}
	public void setDataTypeBackup(Integer dataTypeBackup) {
		this.dataTypeBackup = dataTypeBackup;
	}
	public String getTableCode() {
		return tableCode;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	public String getColumnCode() {
		return columnCode;
	}
	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}
	public String getMappingAlias() {
		return mappingAlias;
	}
	public void setMappingAlias(String mappingAlias) {
		this.mappingAlias = mappingAlias;
	}
	public Integer getTableType() {
		return tableType;
	}
	public void setTableType(Integer tableType) {
		this.tableType = tableType;
	}
}
