package org.terasoluna.qp.app.domaindesign;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.terasoluna.qp.domain.model.Autocomplete;
import org.terasoluna.qp.domain.model.BusinessLogic;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.ValidationRule;

public class DomainDesignForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long domainId;

	private String domainName;
	
	private String domainCode;

	private Integer baseType;

	private Integer mandatoryFlg;

	private Integer maxLength;

	private Integer precision;

	private String defaultValue;

	private String minVal;
	
	private String maxVal;

	private String fmtCode;
	
	private List<ValidationRule> fmtCodelist;
	
	private List<String> fmtCodeByString;
	
	private String remark;
	
	private Integer groupBasetypeId;
	
	private String baseTypeAutocomplete;
	
/*	@NotEmpty(message= DomainDesignMessageConst.SC_DOMAINDESIGN_0026)*/
	private String projectIdAutocomplete;

	private Long createdBy;
	
	private Date createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private String mode;
	
	private Integer constrainsType;
	private Long datasourceId;
	private Integer datasourceType;
	private String operatorCode;
	private String codelistCodeAutocomplete;
	private String codelistDefaultAutocomplete;
	private String sqlCodeAutocomplete;
	private String userDefineValue;
	private Integer supportOptionFlg;
	private Integer defaultType;
	List<SqlDesign> listSqlDesigns;
	List<BusinessLogic> listBusinessLogics;
	List<Autocomplete> listOfTableDesign;
	
	private String majorClassification;
	
	private String subClassification;
	
	private String minorClassification;
	
	private String descriptionFormat;
	
	private Long optionLabel;
	private Long optionValue;
	private String optionLabelAutocomplete;
	private String optionValueAutocomplete;
	
	private Boolean showImpactFlag = false;
	
	public Long getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(Long optionLabel) {
		this.optionLabel = optionLabel;
	}

	public Long getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(Long optionValue) {
		this.optionValue = optionValue;
	}

	public String getOptionLabelAutocomplete() {
		return optionLabelAutocomplete;
	}

	public void setOptionLabelAutocomplete(String optionLabelAutocomplete) {
		this.optionLabelAutocomplete = optionLabelAutocomplete;
	}

	public String getOptionValueAutocomplete() {
		return optionValueAutocomplete;
	}

	public void setOptionValueAutocomplete(String optionValueAutocomplete) {
		this.optionValueAutocomplete = optionValueAutocomplete;
	}

	
	public List<Autocomplete> getListOfTableDesign() {
		return listOfTableDesign;
	}

	public void setListOfTableDesign(List<Autocomplete> listOfTableDesign) {
		this.listOfTableDesign = listOfTableDesign;
	}

	public List<SqlDesign> getListSqlDesigns() {
		return listSqlDesigns;
	}

	public void setListSqlDesigns(List<SqlDesign> listSqlDesigns) {
		this.listSqlDesigns = listSqlDesigns;
	}

	public List<BusinessLogic> getListBusinessLogics() {
		return listBusinessLogics;
	}

	public void setListBusinessLogics(List<BusinessLogic> listBusinessLogics) {
		this.listBusinessLogics = listBusinessLogics;
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
	
	private Long projectId;

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

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
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

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getBaseType() {
		return baseType;
	}

	public void setBaseType(Integer baseType) {
		this.baseType = baseType;
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

	public Integer getGroupBasetypeId() {
		return groupBasetypeId;
	}

	public void setGroupBasetypeId(Integer groupBasetypeId) {
		this.groupBasetypeId = groupBasetypeId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getBaseTypeAutocomplete() {
		return baseTypeAutocomplete;
	}

	public void setBaseTypeAutocomplete(String baseTypeAutocomplete) {
		this.baseTypeAutocomplete = baseTypeAutocomplete;
	}

	public Integer getMandatoryFlg() {
		return mandatoryFlg;
	}

	public void setMandatoryFlg(Integer mandatoryFlg) {
		this.mandatoryFlg = mandatoryFlg;
	}

	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}

	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<ValidationRule> getFmtCodelist() {
		return fmtCodelist;
	}

	public void setFmtCodelist(List<ValidationRule> fmtCodelist) {
		this.fmtCodelist = fmtCodelist;
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

	public String getCodelistCodeAutocomplete() {
		return codelistCodeAutocomplete;
	}

	public void setCodelistCodeAutocomplete(String codelistCodeAutocomplete) {
		this.codelistCodeAutocomplete = codelistCodeAutocomplete;
	}

	public String getCodelistDefaultAutocomplete() {
		return codelistDefaultAutocomplete;
	}

	public void setCodelistDefaultAutocomplete(String codelistDefaultAutocomplete) {
		this.codelistDefaultAutocomplete = codelistDefaultAutocomplete;
	}

	public String getSqlCodeAutocomplete() {
		return sqlCodeAutocomplete;
	}

	public void setSqlCodeAutocomplete(String sqlCodeAutocomplete) {
		this.sqlCodeAutocomplete = sqlCodeAutocomplete;
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

	public Integer getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(Integer defaultType) {
		this.defaultType = defaultType;
	}

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

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<String> getFmtCodeByString() {
		return fmtCodeByString;
	}

	public void setFmtCodeByString(List<String> fmtCodeByString) {
		this.fmtCodeByString = fmtCodeByString;
	}

	public Boolean getShowImpactFlag() {
		return showImpactFlag;
	}

	public void setShowImpactFlag(Boolean showImpactFlag) {
		this.showImpactFlag = showImpactFlag;
	}
}
