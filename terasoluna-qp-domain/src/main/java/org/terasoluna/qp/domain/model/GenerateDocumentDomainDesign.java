package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.Date;

public class GenerateDocumentDomainDesign implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long domainId;
	
	private String domainName;
	
	private String domainCode;
	
	private int maxLength;
	
	private String minVal;
	
	private String maxVal;
	
	private int precision;
	
	private String defaultValue;
	
	private int baseType;
	
	private String baseTypeName;
	
	private Integer groupBasetypeId = 0;
	
	private String baseTypeAutocomplete;
	
	private String fmtCode;
	
	private String remark;
	
	private Integer mandatoryflg;
	
	private String startDateVal;
	
	private String endDateVal;
	
	private Long projectId;
	
	private String validationName;	
	
	private Long createdBy;
	
	private Date createdDate;
	
	private Long updatedBy;
	
	private String  updatedDate;
	
	private Long datasourceId;
	
	private String majorClassification;
	
	private String subClassification;
	
	private String minorClassification;
	
	private String descriptionFormat;
	
	private Integer datasourceType;
	
	public String getMajorClassification() {
		return majorClassification;
	}

	public void setMajorClassification(String majorClassification) {
		this.majorClassification = majorClassification;
	}

	public String getSubClassification() {
		return subClassification;
	}

	public void setSubClassification(String subClassification) {
		this.subClassification = subClassification;
	}

	public String getMinorClassification() {
		return minorClassification;
	}

	public void setMinorClassification(String minorClassification) {
		this.minorClassification = minorClassification;
	}

	public String getDescriptionFormat() {
		return descriptionFormat;
	}

	public void setDescriptionFormat(String descriptionFormat) {
		this.descriptionFormat = descriptionFormat;
	}


	public Long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
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

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public String getMinVal() {
		return minVal;
	}

	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}

	public String getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getBaseType() {
		return baseType;
	}

	public void setBaseType(int baseType) {
		this.baseType = baseType;
	}

	public String getBaseTypeAutocomplete() {
		return baseTypeAutocomplete;
	}

	public void setBaseTypeAutocomplete(String baseTypeAutocomplete) {
		this.baseTypeAutocomplete = baseTypeAutocomplete;
	}

	public String getFmtCode() {
		return fmtCode;
	}

	public void setFmtCode(String fmtCode) {
		this.fmtCode = fmtCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getMandatoryflg() {
		return mandatoryflg;
	}

	public void setMandatoryflg(Integer mandatoryflg) {
		this.mandatoryflg = mandatoryflg;
	}

	public String getStartDateVal() {
		return startDateVal;
	}

	public void setStartDateVal(String startDateVal) {
		this.startDateVal = startDateVal;
	}

	public String getEndDateVal() {
		return endDateVal;
	}

	public void setEndDateVal(String endDateVal) {
		this.endDateVal = endDateVal;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getValidationName() {
		return validationName;
	}

	public void setValidationName(String validationName) {
		this.validationName = validationName;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(Long datasourceId) {
		this.datasourceId = datasourceId;
	}

	public Integer getGroupBasetypeId() {
		return groupBasetypeId;
	}

	public void setGroupBasetypeId(Integer groupBasetypeId) {
		this.groupBasetypeId = groupBasetypeId;
	}

	/**
	 * @return the datasourceType
	 */
	public Integer getDatasourceType() {
		return datasourceType;
	}

	/**
	 * @param datasourceType the datasourceType to set
	 */
	public void setDatasourceType(Integer datasourceType) {
		this.datasourceType = datasourceType;
	}

	/**
	 * @return the baseTypeName
	 */
	public String getBaseTypeName() {
		return baseTypeName;
	}

	/**
	 * @param baseTypeName the baseTypeName to set
	 */
	public void setBaseTypeName(String baseTypeName) {
		this.baseTypeName = baseTypeName;
	}
}
