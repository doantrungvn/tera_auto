package org.terasoluna.qp.domain.model;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;
import org.terasoluna.qp.app.common.constants.DbDomainConst.BaseType;
import org.terasoluna.qp.app.common.ultils.DateUtils;

public class SqlDesignValue implements Serializable{
	private static final long serialVersionUID = 2842736960303643446L;
	private Long sqlDesignValueId;
	private Long sqlDesignId;
	private Long columnId;
	private String columnName;
	private String columnCode;
	private String columnMissingFlag;
	private String parameter;
	private Integer itemSeqNo;
	private Integer dataType;
	private Integer valueType;
	private String patternFormat;
	
	public Long getSqlDesignValueId() {
		return sqlDesignValueId;
	}
	public void setSqlDesignValueId(Long sqlDesignValueId) {
		this.sqlDesignValueId = sqlDesignValueId;
	}
	public Long getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(Long sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
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
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
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
	public String getColumnCode() {
		return columnCode;
	}
	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}
	public Integer getValueType() {
		return valueType;
	}
	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}
	public String getPatternFormat() {
		return patternFormat;
	}
	public void setPatternFormat(String patternFormat) {
		this.patternFormat = patternFormat;
	}
	
	/**
	 * get pattern format of data type date time by pattern format
	 * @param profile
	 * @return
	 */
	@Transient
	public String getDateTimeFormatByPatternFormat(){
		switch (this.dataType) {
			case BaseType.DATE_BASETYPE:
				return DateUtils.getPatternDateSql(this.patternFormat);
			case BaseType.DATETIME_BASETYPE:
			case BaseType.TIMESTAMP_BASETYPE:
				return DateUtils.getPatternDateTimeSql(this.patternFormat);
			case BaseType.TIME_BASETYPE:
				return DateUtils.getPatternTimeSql(this.patternFormat);
			default:
				return "";
		}
	}
	
}
