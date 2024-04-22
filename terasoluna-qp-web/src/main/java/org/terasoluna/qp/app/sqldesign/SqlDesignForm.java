package org.terasoluna.qp.app.sqldesign;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SqlPattern;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.common.validation.QpRemarkSize;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.SqlDesignMessageConst;

public class SqlDesignForm implements Serializable{
	public interface SqlDesignValidationGroup{
		
	}
	public interface ViewDesignValidationGroup{
		
	}
	private static final long serialVersionUID = 2301526014284579648L;
	private String sqlDesignId;
	@NotEmpty(message = SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN_NAME,groups=SqlDesignValidationGroup.class)
	@QpNameSize(message = SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN_NAME,groups=SqlDesignValidationGroup.class)
	@QpNamePattern(message=SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN_NAME,groups=SqlDesignValidationGroup.class)
	private String sqlDesignName;
	@NotEmpty(message = SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN_CODE,groups=SqlDesignValidationGroup.class)
	@QpCodeSize(message=SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN_CODE,groups=SqlDesignValidationGroup.class)
	@QpCodePattern(message=SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN_CODE,groups=SqlDesignValidationGroup.class)
	private String sqlDesignCode;
	@QpRemarkSize(message=CommonMessageConst.SC_SYS_0028)
	private String remark;
	private String designType="4";
	private Boolean omitOverlap;
	private String createdBy;
	private Timestamp createdDate;
	private String updatedBy;
	private Timestamp updatedDate;
	private Timestamp systemDate;
	private String sqlPattern = String.valueOf(SqlPattern.SELECT);
	private String sqlText;
	private Boolean isValid;
	private Timestamp verifiedDate;
	private String projectId;
	private String projectIdAutocomplete;
	private String moduleId;
	private String moduleIdAutocomplete;
	private String returnType;
	private String designStatus="1";
	private String moduleStatus;
	private Boolean isConversion;
	private Integer pageable;
	
	public Boolean getIsConversion() {
		return isConversion;
	}
	public void setIsConversion(Boolean isConversion) {
		this.isConversion = isConversion;
	}
	public String getSqlDesignId() {
		return sqlDesignId;
	}
	public void setSqlDesignId(String sqlDesignId) {
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
	public String getDesignType() {
		return designType;
	}
	public void setDesignType(String designType) {
		this.designType = designType;
	}
	public Boolean getOmitOverlap() {
		return omitOverlap;
	}
	public void setOmitOverlap(Boolean omitOverlap) {
		this.omitOverlap = omitOverlap;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
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
	public String getSqlPattern() {
		return sqlPattern;
	}
	public void setSqlPattern(String sqlPattern) {
		this.sqlPattern = sqlPattern;
	}
	public String getSqlText() {
		return sqlText;
	}
	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	public Timestamp getVerifiedDate() {
		return verifiedDate;
	}
	public void setVerifiedDate(Timestamp verifiedDate) {
		this.verifiedDate = verifiedDate;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectIdAutocomplete() {
		return projectIdAutocomplete;
	}
	public void setProjectIdAutocomplete(String projectIdAutocomplete) {
		this.projectIdAutocomplete = projectIdAutocomplete;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}
	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getDesignStatus() {
		return designStatus;
	}
	public void setDesignStatus(String designStatus) {
		this.designStatus = designStatus;
	}
	public String getModuleStatus() {
		return moduleStatus;
	}
	public void setModuleStatus(String moduleStatus) {
		this.moduleStatus = moduleStatus;
	}
	public Integer getPageable() {
		return pageable;
	}
	public void setPageable(Integer pageable) {
		this.pageable = pageable;
	}
}
