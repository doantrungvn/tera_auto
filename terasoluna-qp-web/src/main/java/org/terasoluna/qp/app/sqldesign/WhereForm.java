package org.terasoluna.qp.app.sqldesign;

import java.io.Serializable;

public class WhereForm implements Serializable {
	
	private static final long serialVersionUID = 3143829050812484210L;
	
	private String conditionsId;
	private String sqlDesignId;
	private String logicCode;
	private String leftTableId;
	private String leftTableIdAutocomplete;
	private String leftTableMissingFlag;
	private String leftColumnId;
	private String leftColumnIdAutocomplete;
	private String leftColumnMissingFlag;
	private String operatorCode;
	private String value;
	private String value2;
	private String arg;
	private String displayArg;
	private String arg2;
	private String displayArg2;
	private String rightTableId;
	private String rightTableIdAutocomplete;
	private String rightTableMissingFlag;
	private String rightColumnId;
	private String rightColumnIdAutocomplete;
	private String rightColumnMissingFlag;
	private Boolean groupType;
	private String itemSeqNo;
	private String conditionType;
	private String sqlDesignParameterId;
	private String functionCode;
	private String dataType;
	private String patternFormat;
	
	public String getConditionsId() {
		return conditionsId;
	}
	public void setConditionsId(String conditionsId) {
		this.conditionsId = conditionsId;
	}
	public String getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(String sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
	}
	public String getLogicCode() {
		return logicCode;
	}
	public void setLogicCode(String logicCode) {
		this.logicCode = logicCode;
	}
	public String getLeftTableId() {
		return leftTableId;
	}
	public void setLeftTableId(String leftTableId) {
		this.leftTableId = leftTableId;
	}
	public String getLeftTableIdAutocomplete() {
		return leftTableIdAutocomplete;
	}
	public void setLeftTableIdAutocomplete(String leftTableIdAutocomplete) {
		this.leftTableIdAutocomplete = leftTableIdAutocomplete;
	}
	public String getLeftTableMissingFlag() {
		return leftTableMissingFlag;
	}
	public void setLeftTableMissingFlag(String leftTableMissingFlag) {
		this.leftTableMissingFlag = leftTableMissingFlag;
	}
	public String getLeftColumnId() {
		return leftColumnId;
	}
	public void setLeftColumnId(String leftColumnId) {
		this.leftColumnId = leftColumnId;
	}
	public String getLeftColumnIdAutocomplete() {
		return leftColumnIdAutocomplete;
	}
	public void setLeftColumnIdAutocomplete(String leftColumnIdAutocomplete) {
		this.leftColumnIdAutocomplete = leftColumnIdAutocomplete;
	}
	public String getLeftColumnMissingFlag() {
		return leftColumnMissingFlag;
	}
	public void setLeftColumnMissingFlag(String leftColumnMissingFlag) {
		this.leftColumnMissingFlag = leftColumnMissingFlag;
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
	public String getArg() {
		return arg;
	}
	public void setArg(String arg) {
		this.arg = arg;
	}
	public String getArg2() {
		return arg2;
	}
	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}
	public String getRightTableId() {
		return rightTableId;
	}
	public void setRightTableId(String rightTableId) {
		this.rightTableId = rightTableId;
	}
	public String getRightTableIdAutocomplete() {
		return rightTableIdAutocomplete;
	}
	public void setRightTableIdAutocomplete(String rightTableIdAutocomplete) {
		this.rightTableIdAutocomplete = rightTableIdAutocomplete;
	}
	public String getRightTableMissingFlag() {
		return rightTableMissingFlag;
	}
	public void setRightTableMissingFlag(String rightTableMissingFlag) {
		this.rightTableMissingFlag = rightTableMissingFlag;
	}
	public String getRightColumnId() {
		return rightColumnId;
	}
	public void setRightColumnId(String rightColumnId) {
		this.rightColumnId = rightColumnId;
	}
	public String getRightColumnIdAutocomplete() {
		return rightColumnIdAutocomplete;
	}
	public void setRightColumnIdAutocomplete(String rightColumnIdAutocomplete) {
		this.rightColumnIdAutocomplete = rightColumnIdAutocomplete;
	}
	public String getRightColumnMissingFlag() {
		return rightColumnMissingFlag;
	}
	public void setRightColumnMissingFlag(String rightColumnMissingFlag) {
		this.rightColumnMissingFlag = rightColumnMissingFlag;
	}
	public Boolean getGroupType() {
		return groupType;
	}
	public void setGroupType(Boolean groupType) {
		this.groupType = groupType;
	}
	public String getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(String itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	public String getConditionType() {
		return conditionType;
	}
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
	public String getSqlDesignParameterId() {
		return sqlDesignParameterId;
	}
	public void setSqlDesignParameterId(String sqlDesignParameterId) {
		this.sqlDesignParameterId = sqlDesignParameterId;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public String getDisplayArg() {
		return displayArg;
	}
	public void setDisplayArg(String displayArg) {
		this.displayArg = displayArg;
	}
	public String getDisplayArg2() {
		return displayArg2;
	}
	public void setDisplayArg2(String displayArg2) {
		this.displayArg2 = displayArg2;
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
	public String getPatternFormat() {
		return patternFormat;
	}
	public void setPatternFormat(String patternFormat) {
		this.patternFormat = patternFormat;
	}
}
