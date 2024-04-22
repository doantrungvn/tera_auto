package org.terasoluna.qp.domain.service.graphicdatabasedesign;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "org.terasoluna.qp.domain.service.graphicdatabasedesign.GroupDataTypeDB")
public class DataType {

	private String label;

	private String length;

	private String sql;

	private String realType;

	private String quote;

	private String fk;

	private long basetypeId;

	private String domainName;

	private String domainCode;

	private String basetypeName;

	private String precision;

	private String mandatory;

	private String validationRule;

	private String defaultValue;

	private String maxValue;

	private String minValue;

	private int groupBaseTypeId;
	private int datatypeFlg;

	private String color;
	
	private String remark;

	private Integer constrainsType;
	private Long datasourceId;
	private Integer datasourceType;
	private String operatorCode;
	private String userDefineValue;
	private Integer supportOptionFlg;
	private String codelistCodeAutocomplete;
	private String codelistDefaultAutocomplete;
	private String sqlCodeAutocomplete;
	private Integer primitiveId;

	public DataType() {

	}

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

	public String getBasetypeName() {
		return basetypeName;
	}

	public void setBasetypeName(String basetypeName) {
		this.basetypeName = basetypeName;
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

	@XmlAttribute(name = "label")
	public String getLabel() {
		return label;
	}

	public void setLabel(String lable) {
		this.label = lable;
	}

	@XmlAttribute(name = "length")
	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	@XmlAttribute(name = "sql")
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@XmlAttribute(name = "re")
	public String getRealType() {
		return realType;
	}

	public void setRealType(String realType) {
		this.realType = realType;
	}

	@XmlAttribute(name = "quote")
	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	@XmlAttribute(name = "fk")
	public String getFk() {
		return fk;
	}

	public void setFk(String fk) {
		this.fk = fk;
	}

	@XmlAttribute(name = "basetypeId")
	public long getBasetypeId() {
		return basetypeId;
	}

	public void setBasetypeId(long basetypeId) {
		this.basetypeId = basetypeId;
	}

	@XmlAttribute(name = "color")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getUserDefineValue() {
		return userDefineValue;
	}

	public void setUserDefineValue(String userDefineValue) {
		this.userDefineValue = userDefineValue;
	}

	public Integer getSupportOptionFlg() {
		return supportOptionFlg;
	}

	public void setSupportOptionFlg(Integer supportOptionFlg) {
		this.supportOptionFlg = supportOptionFlg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	@XmlAttribute(name = "group_base_type")
	public int getGroupBaseTypeId() {
		return groupBaseTypeId;
	}

	public void setGroupBaseTypeId(int groupBaseTypeId) {
		this.groupBaseTypeId = groupBaseTypeId;
	}

	@XmlAttribute(name = "datatypeFlg")
	public int getDatatypeFlg() {
		return datatypeFlg;
	}

	public void setDatatypeFlg(int datatypeFlg) {
		this.datatypeFlg = datatypeFlg;
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
