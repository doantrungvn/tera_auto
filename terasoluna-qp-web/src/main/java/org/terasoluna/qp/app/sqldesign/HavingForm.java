package org.terasoluna.qp.app.sqldesign;

import java.io.Serializable;

public class HavingForm implements Serializable {
	
	private static final long serialVersionUID = 2779536601673993827L;
	private String havingId;
	private String sqlDesignId;
	private String functionCode;
	private String tableId;
	private String tableIdAutocomplete;
	private String columnId;
	private String columnIdAutocomplete;
	private String columnType;
	private String operatorCode;
	private String value;
	private String value2;
	private String logicCode;
	private String itemSeqNo;
	
	public String getHavingId() {
		return havingId;
	}
	public void setHavingId(String havingId) {
		this.havingId = havingId;
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
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
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
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getLogicCode() {
		return logicCode;
	}
	public void setLogicCode(String logicCode) {
		this.logicCode = logicCode;
	}
	public String getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(String itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	
	
}
