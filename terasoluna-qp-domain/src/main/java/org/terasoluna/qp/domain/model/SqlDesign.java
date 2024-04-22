package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class SqlDesign implements Serializable {
	
	private static final long serialVersionUID = 1558272554982872858L;
	private Long sqlDesignId;
	private String sqlDesignName;
	private String sqlDesignCode;
	private String remark;
	private int designType;
	private int omitOverlap;
	private Long createdBy;
	private Timestamp createdDate;
	private Long updatedBy;
	private Timestamp updatedDate;
	private Timestamp systemDate;
	private Integer sqlPattern;
	private String sqlText;
	private Integer isValid;
	private Timestamp verifiedDate;
	private Long projectId;
	private String projectName;
	private Long moduleId;
	private String moduleName;
	private String moduleCode;
	private Integer moduleStatus;
	private Integer designStatus;
	private Integer returnType;
	private Boolean isConversion;
	private List<SqlDesignInput> allSqlDesignInputs;
	private List<SqlDesignOutput> allSqlDesignOutputs;
	private List<SqlDesignResult> sqlDesignResults;
	private List<SqlDesignTable> sqlDesignTables;
    private String autoCompleteId;
	private Integer pageable;
	private String sqlPatternString;
	private String author;
	
	public Long getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(Long sqlDesignId) {
		this.sqlDesignId = sqlDesignId;
	}
	public String getSqlDesignName() {
		return sqlDesignName;
	}
	public void setSqlDesignName(String sqlDesignName) {
		this.sqlDesignName = sqlDesignName;
	}
	public String getSqlDesignCode() {
		return sqlDesignCode;
	}
	public void setSqlDesignCode(String sqlDesignCode) {
		this.sqlDesignCode = sqlDesignCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getDesignType() {
		return designType;
	}
	public void setDesignType(int designType) {
		this.designType = designType;
	}
	public int getOmitOverlap() {
		return omitOverlap;
	}
	public void setOmitOverlap(int omitOverlap) {
		this.omitOverlap = omitOverlap;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Timestamp getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(Timestamp systemDate) {
		this.systemDate = systemDate;
	}
	public Integer getSqlPattern() {
		return sqlPattern;
	}
	public void setSqlPattern(Integer sqlPattern) {
		this.sqlPattern = sqlPattern;
	}
	public String getSqlText() {
		return sqlText;
	}
	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public Timestamp getVerifiedDate() {
		return verifiedDate;
	}
	public void setVerifiedDate(Timestamp verifiedDate) {
		this.verifiedDate = verifiedDate;
	}
	public Integer getDesignStatus() {
		return designStatus;
	}
	public void setDesignStatus(Integer designStatus) {
		this.designStatus = designStatus;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public Integer getReturnType() {
		return this.returnType;
	}
	public void setReturnType(Integer returnType) {
		this.returnType = returnType;
	}
	public Boolean getIsConversion() {
		return isConversion;
	}
	public void setIsConversion(Boolean isConversion) {
		this.isConversion = isConversion;
	}
	public List<SqlDesignInput> getAllSqlDesignInputs() {
	    return allSqlDesignInputs;
    }
	public void setAllSqlDesignInputs(List<SqlDesignInput> allSqlDesignInputs) {
	    this.allSqlDesignInputs = allSqlDesignInputs;
    }
	public List<SqlDesignOutput> getAllSqlDesignOutputs() {
	    return allSqlDesignOutputs;
    }
	public void setAllSqlDesignOutputs(List<SqlDesignOutput> allSqlDesignOutputs) {
	    this.allSqlDesignOutputs = allSqlDesignOutputs;
    }
	public Integer getModuleStatus() {
		return moduleStatus;
	}
	public void setModuleStatus(Integer moduleStatus) {
		this.moduleStatus = moduleStatus;
	}
	public List<SqlDesignResult> getSqlDesignResults() {
		return sqlDesignResults;
	}
	public void setSqlDesignResults(List<SqlDesignResult> sqlDesignResults) {
		this.sqlDesignResults = sqlDesignResults;
	}
	public List<SqlDesignTable> getSqlDesignTables() {
		return sqlDesignTables;
	}
	public void setSqlDesignTables(List<SqlDesignTable> sqlDesignTables) {
		this.sqlDesignTables = sqlDesignTables;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public Integer getPageable() {
		return pageable;
	}
	public void setPageable(Integer pageable) {
		this.pageable = pageable;
	}
    /**
     * @return the autoCompleteId
     */
    public String getAutoCompleteId() {
        return autoCompleteId;
    }
    /**
     * @param autoCompleteId the autoCompleteId to set
     */
    public void setAutoCompleteId(String autoCompleteId) {
        this.autoCompleteId = autoCompleteId;
    }
	public String getSqlPatternString() {
		return sqlPatternString;
	}
	public void setSqlPatternString(String sqlPatternString) {
		this.sqlPatternString = sqlPatternString;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
    
    
}
