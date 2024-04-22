package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class SqlDesignHaving implements Serializable{
	
	private static final long serialVersionUID = 4649408710874400900L;
	private Long havingId;
	private Long sqlDesignId;
	private String functionCode;
	private Long tableId;
	private String tableName;
	private String updatedTableName;
	private Long columnId;
	private String columnName;
	private String updatedColumnName;
	private String operatorCode;
	private String value;
	private String logicCode;
	private Integer itemSeqNo;
	public Long getHavingId() {
		return havingId;
	}
	public void setHavingId(Long havingId) {
		this.havingId = havingId;
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
	public String getUpdatedTableName() {
		return updatedTableName;
	}
	public void setUpdatedTableName(String updatedTableName) {
		this.updatedTableName = updatedTableName;
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
	public String getUpdatedColumnName() {
		return updatedColumnName;
	}
	public void setUpdatedColumnName(String updatedColumnName) {
		this.updatedColumnName = updatedColumnName;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLogicCode() {
		return logicCode;
	}
	public void setLogicCode(String logicCode) {
		this.logicCode = logicCode;
	}
	public Integer getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	
}
