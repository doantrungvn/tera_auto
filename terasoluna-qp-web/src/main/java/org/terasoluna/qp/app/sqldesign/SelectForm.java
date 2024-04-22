package org.terasoluna.qp.app.sqldesign;

import java.io.Serializable;

public class SelectForm implements Serializable {
	
	private static final long serialVersionUID = 5066438734607264886L;
	private Boolean isSelected;
	private String resultId;
	private String sqlDesignId;
	private String functionCode;
	private String tableId;
	private String tableIdAutocomplete;
	private String tableMissingFlag;
	private String columnId;
	private String columnIdAutocomplete;
	private String columnCode;
	private String columnMissingFlag;
	private String itemSeqNo;
	private String dataType;
	private String dataTypeBackup;
	private String tableType;
	
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getResultId() {
		return resultId;
	}
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	public String getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(String sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getTableIdAutocomplete() {
		return tableIdAutocomplete;
	}
	public void setTableIdAutocomplete(String tableIdAutocomplete) {
		this.tableIdAutocomplete = tableIdAutocomplete;
	}
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public String getColumnIdAutocomplete() {
		return columnIdAutocomplete;
	}
	public void setColumnIdAutocomplete(String columnIdAutocomplete) {
		this.columnIdAutocomplete = columnIdAutocomplete;
	}
	public String getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(String itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataTypeBackup() {
		return dataTypeBackup;
	}
	public void setDataTypeBackup(String dataTypeBackup) {
		this.dataTypeBackup = dataTypeBackup;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getColumnCode() {
		return columnCode;
	}
	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}
}
