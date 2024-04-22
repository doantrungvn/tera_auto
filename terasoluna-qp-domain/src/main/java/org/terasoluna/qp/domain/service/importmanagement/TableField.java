package org.terasoluna.qp.domain.service.importmanagement;

public class TableField {
	private int index;
	private String columnName;
	private String dataType;
	private String isMandatory;
	private String maxLength;
	private String minValue;
	private String maxValue;
	private String singleUnique;
	private String multiUnique;
	private String columnMessage;
	private String codeList;
	private String dataSource;
	private String patternCode;
	private String patternName;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String isSingleUnique() {
		return singleUnique;
	}
	public void setSingleUnique(String singleUnique) {
		this.singleUnique = singleUnique;
	}
	public String isMultiUnique() {
		return multiUnique;
	}
	public void setMultiUnique(String multiUnique) {
		this.multiUnique = multiUnique;
	}
	public String getCodeList() {
		return codeList;
	}
	public void setCodeList(String codeList) {
		this.codeList = codeList;
	}
	public String getIsMandatory() {
		return isMandatory;
	}
	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}
	public String getColumnMessage() {
		return columnMessage;
	}
	public void setColumnMessage(String columnMessage) {
		this.columnMessage = columnMessage;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getPatternName() {
		return patternName;
	}
	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}
	public String getPatternCode() {
		return patternCode;
	}
	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}
}
