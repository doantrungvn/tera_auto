package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class SqlDesignTableItem implements Serializable{
	
	private static final long serialVersionUID = 8100335983677452529L;
	private Long sqlDesignTableItemId;
	private Long sqlDesignTableId;
	private Long tableId;
	private String tableName;
	private String tableCode;
	private Integer tableType;
	private String tableMissingFlag;
	private Long columnId;
	private String columnName;
	private String columnCode;
	private String updatedColumnName;
	private String operatorCode;
	private Long joinTableId;
	private String joinTableName;
	private String joinTableCode;
	private String columnMissingFlag;
	private Long joinColumnId;
	private String joinColumnName;
	private String joinColumnCode;
	private String joinColumnMissingFlag;
	private Integer itemSeqNo;
	private String logicCode;
	
	public Long getSqlDesignTableItemId() {
		return sqlDesignTableItemId;
	}
	public void setSqlDesignTableItemId(Long sqlDesignTableItemId) {
		this.sqlDesignTableItemId = sqlDesignTableItemId;
	}
	public Long getSqlDesignTableId() {
		return sqlDesignTableId;
	}
	public void setSqlDesignTableId(Long sqlDesignTableId) {
		this.sqlDesignTableId = sqlDesignTableId;
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
	public Long getJoinTableId() {
		return joinTableId;
	}
	public void setJoinTableId(Long joinTableId) {
		this.joinTableId = joinTableId;
	}
	public String getColumnMissingFlag() {
		return columnMissingFlag;
	}
	public void setColumnMissingFlag(String columnMissingFlag) {
		this.columnMissingFlag = columnMissingFlag;
	}
	public Long getJoinColumnId() {
		return joinColumnId;
	}
	public void setJoinColumnId(Long joinColumnId) {
		this.joinColumnId = joinColumnId;
	}
	public String getJoinColumnName() {
		return joinColumnName;
	}
	public void setJoinColumnName(String joinColumnName) {
		this.joinColumnName = joinColumnName;
	}
	public String getJoinColumnMissingFlag() {
		return joinColumnMissingFlag;
	}
	public void setJoinColumnMissingFlag(String joinColumnMissingFlag) {
		this.joinColumnMissingFlag = joinColumnMissingFlag;
	}
	public Integer getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	public String getLogicCode() {
		return logicCode;
	}
	public void setLogicCode(String logicCode) {
		this.logicCode = logicCode;
	}
	public String getColumnCode() {
		return columnCode;
	}
	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}
	public String getJoinColumnCode() {
		return joinColumnCode;
	}
	public void setJoinColumnCode(String joinColumnCode) {
		this.joinColumnCode = joinColumnCode;
	}
	public String getTableCode() {
		return tableCode;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	public Integer getTableType() {
		return tableType;
	}
	public void setTableType(Integer tableType) {
		this.tableType = tableType;
	}
	public String getJoinTableName() {
		return joinTableName;
	}
	public void setJoinTableName(String joinTableName) {
		this.joinTableName = joinTableName;
	}
	public String getJoinTableCode() {
		return joinTableCode;
	}
	public void setJoinTableCode(String joinTableCode) {
		this.joinTableCode = joinTableCode;
	}
	
	
}
