/*
 * @(#)ProjectForm.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.app.project;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.common.validation.QpRemarkSize;
import org.terasoluna.qp.app.common.validation.QpSize;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ProjectMessageConst;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.DataFormat;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.TableDesign;

public class ProjectForm implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Long projectId;
	
	private static final int maxSize = 50;
	
	/*@NotEmpty(message = ProjectMessageConst.SC_PROJECT_0005)
	@Size(min = DbDomainConst.MIN_VAL_INPUT, max = DbDomainConst.MAX_LENGTH_OF_NAME, message = ProjectMessageConst.SC_PROJECT_0005 + ";" + DbDomainConst.MIN_VAL_INPUT +";" + DbDomainConst.MAX_LENGTH_OF_NAME)
	@Pattern(regexp = CommonMessageConst.PATTERN_FOR_NAME, message= ProjectMessageConst.SC_PROJECT_0005 + ";" + CommonMessageConst.NAME_INPUTMASK)*/
	
	@NotEmpty(message = ProjectMessageConst.SC_PROJECT_0005)
	@QpNameSize(message = ProjectMessageConst.SC_PROJECT_0005)
	@QpNamePattern(message= ProjectMessageConst.SC_PROJECT_0005)
	private String projectName;

/*	@NotEmpty(message = ProjectMessageConst.SC_PROJECT_0006)
	@Size(min = DbDomainConst.MIN_VAL_INPUT, max = DbDomainConst.MAX_LENGTH_OF_CODE, message = ProjectMessageConst.SC_PROJECT_0006 + ";" + DbDomainConst.MIN_VAL_INPUT + ";" + DbDomainConst.MAX_LENGTH_OF_CODE)
	@Pattern(regexp = CommonMessageConst.PATTERN_FOR_CODE, message= ProjectMessageConst.SC_PROJECT_0006 + ";" + CommonMessageConst.CODE_INPUTMASK)
*/	
	@NotEmpty(message = ProjectMessageConst.SC_PROJECT_0006)
	@QpCodeSize(message = ProjectMessageConst.SC_PROJECT_0006)
	@QpCodePattern(message= ProjectMessageConst.SC_PROJECT_0006)
	private String projectCode;

	@NotEmpty(message = CommonMessageConst.SC_SYS_0027)
	private String status;

/*	@Size(min = DbDomainConst.MIN_VAL_REMARK, max = DbDomainConst.MAX_VAL_REMARK, message = CommonMessageConst.SC_SYS_0028 + ";" + DbDomainConst.MIN_VAL_REMARK +";" + DbDomainConst.MAX_VAL_REMARK)*/
	@QpRemarkSize(message = CommonMessageConst.SC_SYS_0028)
	private String remark;
	
	private List<Module> modules;
	
	private List<Long> listModuleId;
	
	private Long createdBy;
	
	private String updatedByName;
	
	private String createdByName;
	
	private Timestamp createdDate;
	
	private Long updatedBy;
	
	private Timestamp updatedDate;
	
	private String mode;
	
	private String sqlScripts;
	
	private Integer dbType;
	
	private Integer dbTypeOld;
	
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0017 + ";" + "1;" + maxSize)
	private String dbName;
	
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0018 + ";" + "1;" + maxSize)
	private String dbHostName;
	
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0019 + ";" + "1;" + maxSize)
	private String dbPort;
	
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0020 + ";" + "1;" + maxSize)
	private String dbUser;
	
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0021 + ";" + "1;" + maxSize)
	private String dbPassword;
	
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0022 + ";" + "1;" + maxSize)
	private String dbDriver;
	
	private Integer openOwner;
	
	private Boolean actionDelete;
	
	private Boolean caseSensitivity;
	
	private DataFormat dataFormat;
	
//	@NotEmpty(message = ProjectMessageConst.SC_PROJECT_0027)
//	@Pattern(regexp = CommonMessageConst.PATTERN_FOR_PACKAGE_NAME, message= ProjectMessageConst.ERR_PROJECT_0002+";"+ProjectMessageConst.SC_PROJECT_0028 )
	
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0027 + ";" + "1;" + maxSize)
	private String packageName;
	
	private List<TableDesign> listOfTableDesign;
	
	private List<SqlDesign> listOfSqlDesign;
	
	private List<AutocompleteDesign> listOfAutocompleteDesign;
	
	private Boolean webserviceFlg = true;
	
	@NotEmpty(message = ProjectMessageConst.SC_PROJECT_0030)
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0030 + ";" + "1;" + maxSize)
	private String webservicePattern;
	
	private Long defaultLanguageId;
	
	private String defaultLanguageIdAutocomplete;
	
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0039 + ";" + "1;" + maxSize)
	private String emailAddress;
	
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0040 + ";" + "1;" + maxSize)
	private String emailName;
	
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0041 + ";" + "1;" + maxSize)
	private String smtpHost;
	
	private Integer smtpEncryption = 0;
	
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0045 + ";" + "1;" + maxSize)
	private String smtpPort;
	
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0046 + ";" + "1;" + maxSize)
	private String smtpUserName;
	
	@QpSize(max=maxSize, message= ProjectMessageConst.SC_PROJECT_0047 + ";" + "1;" + maxSize)
	private String smtpPassword;

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbHostName() {
		return dbHostName;
	}

	public void setDbHostName(String dbHostName) {
		this.dbHostName = dbHostName;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public Integer getDbType() {
		return dbType;
	}

	public void setDbType(Integer dbType) {
		this.dbType = dbType;
	}

	public String getSqlScripts() {
		return sqlScripts;
	}

	public void setSqlScripts(String sqlScripts) {
		this.sqlScripts = sqlScripts;
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

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Integer getOpenOwner() {
		return openOwner;
	}

	public void setOpenOwner(Integer openOwner) {
		this.openOwner = openOwner;
	}

	public Boolean getActionDelete() {
		return actionDelete;
	}

	public void setActionDelete(Boolean actionDelete) {
		this.actionDelete = actionDelete;
	}

	public List<Long> getListModuleId() {
		return listModuleId;
	}

	public void setListModuleId(List<Long> listModuleId) {
		this.listModuleId = listModuleId;
	}

	public Boolean getCaseSensitivity() {
		return caseSensitivity;
	}

	public void setCaseSensitivity(Boolean caseSensitivity) {
		this.caseSensitivity = caseSensitivity;
	}

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<TableDesign> getListOfTableDesign() {
		return listOfTableDesign;
	}

	public void setListOfTableDesign(List<TableDesign> listOfTableDesign) {
		this.listOfTableDesign = listOfTableDesign;
	}

	public List<SqlDesign> getListOfSqlDesign() {
		return listOfSqlDesign;
	}

	public void setListOfSqlDesign(List<SqlDesign> listOfSqlDesign) {
		this.listOfSqlDesign = listOfSqlDesign;
	}

	public List<AutocompleteDesign> getListOfAutocompleteDesign() {
		return listOfAutocompleteDesign;
	}

	public void setListOfAutocompleteDesign(
			List<AutocompleteDesign> listOfAutocompleteDesign) {
		this.listOfAutocompleteDesign = listOfAutocompleteDesign;
	}

	public Integer getDbTypeOld() {
		return dbTypeOld;
	}

	public void setDbTypeOld(Integer dbTypeOld) {
		this.dbTypeOld = dbTypeOld;
	}

	public Boolean getWebserviceFlg() {
		return webserviceFlg;
	}

	public void setWebserviceFlg(Boolean webserviceFlg) {
		this.webserviceFlg = webserviceFlg;
	}

	public String getWebservicePattern() {
		return webservicePattern;
	}

	public void setWebservicePattern(String webservicePattern) {
		this.webservicePattern = webservicePattern;
	}

	public Long getDefaultLanguageId() {
		return defaultLanguageId;
	}

	public String getDefaultLanguageIdAutocomplete() {
		return defaultLanguageIdAutocomplete;
	}

	public void setDefaultLanguageId(Long defaultLanguageId) {
		this.defaultLanguageId = defaultLanguageId;
	}

	public void setDefaultLanguageIdAutocomplete(
			String defaultLanguageIdAutocomplete) {
		this.defaultLanguageIdAutocomplete = defaultLanguageIdAutocomplete;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public Integer getSmtpEncryption() {
		return smtpEncryption;
	}

	public void setSmtpEncryption(Integer smtpEncryption) {
		this.smtpEncryption = smtpEncryption;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUserName() {
		return smtpUserName;
	}

	public void setSmtpUserName(String smtpUserName) {
		this.smtpUserName = smtpUserName;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public DataFormat getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(DataFormat dataFormat) {
		this.dataFormat = dataFormat;
	}
	
}
