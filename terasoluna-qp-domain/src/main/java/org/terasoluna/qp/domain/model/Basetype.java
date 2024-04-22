package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class Basetype implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer primitiveId;
	private Long basetyeId;
	private String basetypeName;
	private int basetypeValue;
	private int groupBaseTypeId;
	private String basetypeGroupName;
	private String groupColor;
	private int itemSeqNo;
	private Integer dataTypeFlg;
	private String domainName;
	private String domainCode;
	private String precision;
	private String mandatory;
	private String validationRule;
	private String defaultValue;
	private String maxValue;
	private String minValue;
	private String length;
	private Integer constrainsType;
	private Long datasourceId;
	private Integer datasourceType;
	private String operatorCode;
	private String supportOptionFlg;
	private String remark;
	private String codelistCodeAutocomplete;
	private String codelistDefaultAutocomplete;
	private String sqlCodeAutocomplete;

	public Integer getConstrainsType() {
		return constrainsType;
	}

	public void setConstrainsType(Integer constrainsType) {
		this.constrainsType = constrainsType;
	}

	public Long getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(Long datasourceId) {
		this.datasourceId = datasourceId;
	}

	public Integer getDatasourceType() {
		return datasourceType;
	}

	public void setDatasourceType(Integer datasourceType) {
		this.datasourceType = datasourceType;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getValidationRule() {
		return validationRule;
	}

	public void setValidationRule(String validationRule) {
		this.validationRule = validationRule;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	/*public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}*/

	public String getBasetypeGroupName() {
		return basetypeGroupName;
	}

	public void setBasetypeGroupName(String basetypeGroupName) {
		this.basetypeGroupName = basetypeGroupName;
	}

	public String getGroupColor() {
		return groupColor;
	}

	public void setGroupColor(String groupColor) {
		this.groupColor = groupColor;
	}

	public Long getBasetyeId() {
		return basetyeId;
	}

	public void setBasetyeId(Long basetyeId) {
		this.basetyeId = basetyeId;
	}

	public String getBasetypeName() {
		return basetypeName;
	}

	public void setBasetypeName(String basetypeName) {
		this.basetypeName = basetypeName;
	}

	public int getBasetypeValue() {
		return basetypeValue;
	}

	public void setBasetypeValue(int basetypeValue) {
		this.basetypeValue = basetypeValue;
	}

	public int getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(int itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	public String getSupportOptionFlg() {
		return supportOptionFlg;
	}

	public void setSupportOptionFlg(String supportOptionFlg) {
		this.supportOptionFlg = supportOptionFlg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getGroupBaseTypeId() {
		return groupBaseTypeId;
	}

	public void setGroupBaseTypeId(int groupBaseTypeId) {
		this.groupBaseTypeId = groupBaseTypeId;
	}

	public Integer getDataTypeFlg() {
		return dataTypeFlg;
	}

	public void setDataTypeFlg(Integer dataTypeFlg) {
		this.dataTypeFlg = dataTypeFlg;
	}

	public String getCodelistCodeAutocomplete() {
		return codelistCodeAutocomplete;
	}

	public void setCodelistCodeAutocomplete(String codelistCodeAutocomplete) {
		this.codelistCodeAutocomplete = codelistCodeAutocomplete;
	}

	public String getCodelistDefaultAutocomplete() {
		return codelistDefaultAutocomplete;
	}

	public void setCodelistDefaultAutocomplete(
			String codelistDefaultAutocomplete) {
		this.codelistDefaultAutocomplete = codelistDefaultAutocomplete;
	}

	public String getSqlCodeAutocomplete() {
		return sqlCodeAutocomplete;
	}

	public void setSqlCodeAutocomplete(String sqlCodeAutocomplete) {
		this.sqlCodeAutocomplete = sqlCodeAutocomplete;
	}

	public Integer getPrimitiveId() {
		return primitiveId;
	}

	public void setPrimitiveId(Integer primitiveId) {
		this.primitiveId = primitiveId;
	}

}
