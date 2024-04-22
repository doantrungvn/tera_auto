package org.terasoluna.qp.app.sqldesign;

public class ValueForm {
	private String sqlDesignValueId;
	private String columnId;
	private String columnIdAutocomplete;
	private String columnMissingFlag;
	private String parameter;
	private String displayParameter;
	private String itemSeqNo;
	private String dataType;
	private String valueType;
	private String patternFormat;
	
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
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getColumnMissingFlag() {
		return columnMissingFlag;
	}
	public void setColumnMissingFlag(String columnMissingFlag) {
		this.columnMissingFlag = columnMissingFlag;
	}
	public String getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(String itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	public String getSqlDesignValueId() {
		return sqlDesignValueId;
	}
	public void setSqlDesignValueId(String sqlDesignValueId) {
		this.sqlDesignValueId = sqlDesignValueId;
	}
	public String getDisplayParameter() {
		return displayParameter;
	}
	public void setDisplayParameter(String displayParameter) {
		this.displayParameter = displayParameter;
	}
	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getPatternFormat() {
		return patternFormat;
	}
	public void setPatternFormat(String patternFormat) {
		this.patternFormat = patternFormat;
	}
	
	
}
