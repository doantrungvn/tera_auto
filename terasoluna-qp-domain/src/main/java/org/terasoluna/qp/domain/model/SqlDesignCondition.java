package org.terasoluna.qp.domain.model;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;
import org.terasoluna.qp.app.common.constants.DbDomainConst.BaseType;

public class SqlDesignCondition implements Serializable {
	
	private static final long serialVersionUID = 4067169146875142488L;
	private Long conditionsId;
	private Long sqlDesignId;
	private String logicCode;
	private Long leftTableId;
	private String leftTableName;
	private String leftTableCode;
	private String leftTableMissingFlag;
	private Long leftColumnId;
	private String leftColumnName;
	private String leftColumnCode;
	private String leftColumnMissingFlag;
	private String operatorCode;
	private String value;
	private Long rightTableId;
	private String rightTableName;
	private String rightTableCode;
	private String rightTableMissingFlag;
	private Long rightColumnId;
	private String rightColumnName;
	private String rightColumnCode;
	private String rightColumnMissingFlag;
	private Integer groupType;
	private Integer itemSeqNo;
	private Integer conditionType;
	private Long sqlDesignParameterId;
	private String functionCode;
	private Integer dataType;
	private String patternFormat;
	
	public Long getConditionsId() {
		return conditionsId;
	}
	public void setConditionsId(Long conditionsId) {
		this.conditionsId = conditionsId;
	}
	public Long getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(Long sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
	}
	public String getLogicCode() {
		return logicCode;
	}
	public void setLogicCode(String logicCode) {
		this.logicCode = logicCode;
	}
	public Long getLeftTableId() {
		return leftTableId;
	}
	public void setLeftTableId(Long leftTableId) {
		this.leftTableId = leftTableId;
	}
	public String getLeftTableName() {
		return leftTableName;
	}
	public void setLeftTableName(String leftTableName) {
		this.leftTableName = leftTableName;
	}
	public String getLeftTableMissingFlag() {
		return leftTableMissingFlag;
	}
	public void setLeftTableMissingFlag(String leftTableMissingFlag) {
		this.leftTableMissingFlag = leftTableMissingFlag;
	}
	public Long getLeftColumnId() {
		return leftColumnId;
	}
	public void setLeftColumnId(Long leftColumnId) {
		this.leftColumnId = leftColumnId;
	}
	public String getLeftColumnName() {
		return leftColumnName;
	}
	public void setLeftColumnName(String leftColumnName) {
		this.leftColumnName = leftColumnName;
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
	public Long getRightTableId() {
		return rightTableId;
	}
	public void setRightTableId(Long rightTableId) {
		this.rightTableId = rightTableId;
	}
	public String getRightTableName() {
		return rightTableName;
	}
	public void setRightTableName(String rightTableName) {
		this.rightTableName = rightTableName;
	}
	public String getRightTableMissingFlag() {
		return rightTableMissingFlag;
	}
	public void setRightTableMissingFlag(String rightTableMissingFlag) {
		this.rightTableMissingFlag = rightTableMissingFlag;
	}
	public Long getRightColumnId() {
		return rightColumnId;
	}
	public void setRightColumnId(Long rightColumnId) {
		this.rightColumnId = rightColumnId;
	}
	public String getRightColumnName() {
		return rightColumnName;
	}
	public void setRightColumnName(String rightColumnName) {
		this.rightColumnName = rightColumnName;
	}
	public String getRightColumnMissingFlag() {
		return rightColumnMissingFlag;
	}
	public void setRightColumnMissingFlag(String rightColumnMissingFlag) {
		this.rightColumnMissingFlag = rightColumnMissingFlag;
	}
	public Integer getGroupType() {
		return groupType;
	}
	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}
	public Integer getItemSeqNo() {
		return itemSeqNo;
	}
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}
	public Integer getConditionType() {
		return conditionType;
	}
	public void setConditionType(Integer conditionType) {
		this.conditionType = conditionType;
	}
	public Long getSqlDesignParameterId() {
		return sqlDesignParameterId;
	}
	public void setSqlDesignParameterId(Long sqlDesignParameterId) {
		this.sqlDesignParameterId = sqlDesignParameterId;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public String getLeftTableCode() {
		return leftTableCode;
	}
	public void setLeftTableCode(String leftTableCode) {
		this.leftTableCode = leftTableCode;
	}
	public String getLeftColumnCode() {
		return leftColumnCode;
	}
	public void setLeftColumnCode(String leftColumnCode) {
		this.leftColumnCode = leftColumnCode;
	}
	public String getRightTableCode() {
		return rightTableCode;
	}
	public void setRightTableCode(String rightTableCode) {
		this.rightTableCode = rightTableCode;
	}
	public String getRightColumnCode() {
		return rightColumnCode;
	}
	public void setRightColumnCode(String rightColumnCode) {
		this.rightColumnCode = rightColumnCode;
	}
	
	public String getPatternFormat() {
		return patternFormat;
	}
	public void setPatternFormat(String patternFormat) {
		this.patternFormat = patternFormat;
	}
	/**
	 * until function determine value condition is Date
	 * @return
	 */
	@Transient
	public boolean isValueDate(){
		if(this.dataType == null){
			return false;
		}
		return BaseType.DATE_BASETYPE == dataType;
	}
	
	/**
	 * until function determine value condition is Time
	 * @return
	 */
	@Transient
	public boolean isValueTime(){
		if(this.dataType == null){
			return false;
		}
		return BaseType.TIME_BASETYPE == dataType;
	}
	
	/**
	 * until function determine value condition is Datetime
	 * @return
	 */
	@Transient
	public boolean isValueDateTime(){
		if(this.dataType == null){
			return false;
		}
		return BaseType.DATETIME_BASETYPE == dataType;
	}
	
	/**
	 * until function determine value condition is Timestamp
	 * @return
	 */
	@Transient
	public boolean isValueTimestamp(){
		if(this.dataType == null){
			return false;
		}
		return BaseType.TIMESTAMP_BASETYPE == dataType;
	}
	
}
