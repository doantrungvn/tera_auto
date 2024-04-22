package org.terasoluna.qp.app.sqldesign;

import java.io.Serializable;

public class JoinColumnsForm implements Serializable{

	private static final long serialVersionUID = 7572914502886701382L;
	private Long sqlDesignTableItemId;
	private String tableId;
	private String tableType;
	private String tableIdAutocomplete;
	private String tableMissingFlag;
	private String columnId;
	private String operatorCode;
	private String joinTableId;
	private String joinTableIdAutocomplete;
	private String joinColumnId;
	private String columnIdAutocomplete;
	private String columnMissingFlag;
	private String joinColumnIdAutocomplete;
	private String joinColumnMissingFlag;
	private String itemSeqNo;
	private String logicCode;
	
	public Long getSqlDesignTableItemId() {
		return sqlDesignTableItemId;
	}
	public void setSqlDesignTableItemId(Long sqlDesignTableItemId) {
		this.sqlDesignTableItemId = sqlDesignTableItemId;
	}
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getJoinColumnId() {
		return joinColumnId;
	}
	public void setJoinColumnId(String joinColumnId) {
		this.joinColumnId = joinColumnId;
	}
	public String getColumnIdAutocomplete() {
		return columnIdAutocomplete;
	}
	public void setColumnIdAutocomplete(String columnIdAutocomplete) {
		this.columnIdAutocomplete = columnIdAutocomplete;
	}
	public String getJoinColumnIdAutocomplete() {
		return joinColumnIdAutocomplete;
	}
	public void setJoinColumnIdAutocomplete(String joinColumnIdAutocomplete) {
		this.joinColumnIdAutocomplete = joinColumnIdAutocomplete;
	}
	public String getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(String itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getJoinTableId() {
		return joinTableId;
	}
	public void setJoinTableId(String joinTableId) {
		this.joinTableId = joinTableId;
	}
	public String getTableIdAutocomplete() {
		return tableIdAutocomplete;
	}
	public void setTableIdAutocomplete(String tableIdAutocomplete) {
		this.tableIdAutocomplete = tableIdAutocomplete;
	}
	public String getJoinTableIdAutocomplete() {
		return joinTableIdAutocomplete;
	}
	public void setJoinTableIdAutocomplete(String joinTableIdAutocomplete) {
		this.joinTableIdAutocomplete = joinTableIdAutocomplete;
	}
	public String getLogicCode() {
		return logicCode;
	}
	public void setLogicCode(String logicCode) {
		this.logicCode = logicCode;
	}
	public String getTableMissingFlag() {
		return tableMissingFlag;
	}
	public void setTableMissingFlag(String tableMissingFlag) {
		this.tableMissingFlag = tableMissingFlag;
	}
	public String getColumnMissingFlag() {
		return columnMissingFlag;
	}
	public void setColumnMissingFlag(String columnMissingFlag) {
		this.columnMissingFlag = columnMissingFlag;
	}
	public String getJoinColumnMissingFlag() {
		return joinColumnMissingFlag;
	}
	public void setJoinColumnMissingFlag(String joinColumnMissingFlag) {
		this.joinColumnMissingFlag = joinColumnMissingFlag;
	}
}
