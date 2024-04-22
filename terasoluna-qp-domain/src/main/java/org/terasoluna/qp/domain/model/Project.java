/*
 * @(#)ProjectRepository.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.domain.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DateUtils;

public class Project extends CommonModel implements Comparable<Project>{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String projectName;

	private String projectCode;

	private Integer status;

	private String remark;

	private List<Module> modules;

	private Long createdBy;

	private String updatedByName;

	private String createdByName;

	private Long selected;

	private Integer dbType;
	
	private Integer dbTypeOld;

	private String dbName;

	private String dbHostName;

	private String dbPort;

	private String dbUser;

	private String dbPassword;

	private String dbDriver;

	private String sqlScripts;

	private String packageName;

	private int caseSensitivity;
	
	private int codeMaxVal;

	private List<GenerateDocumentItem> documentItemTypeRDLst;

	private List<GenerateDocumentItem> documentItemTypeEDLst;

	private List<GenerateDocumentItem> documentItemTypeIDLst;

	private List<Long> listModuleId;

	private List<TableDesign> listOfTableDesign;

	private List<SqlDesign> listOfSqlDesign;

	private List<AutocompleteDesign> listOfAutocompleteDesign;
	
	private Boolean webserviceFlg;
	
	private String webservicePattern;
	
	private Long defaultLanguageId;
	
	private String defaultLanguageIdAutocomplete;

	private String emailAddress;
	
	private String emailName;
	
	private String smtpHost;
	
	private Integer smtpEncryption;
	
	private String smtpPort;
	
	private String smtpUserName;
	
	private String smtpPassword;
	
	private DataFormat dataFormat;
	
	public String getSqlScripts() {
		return sqlScripts;
	}

	public void setSqlScripts(String sqlScripts) {
		this.sqlScripts = sqlScripts;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public Integer getDbType() {
		return (dbType == null) ? DbDomainConst.DatabaseType.PostgreSQL : dbType;
	}

	public void setDbType(Integer dbType) {
		this.dbType = dbType;
	}

	public Long getSelected() {
		return selected;
	}

	public void setSelected(Long selected) {
		this.selected = selected;
	}

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

	public List<Long> getListModuleId() {
		return listModuleId;
	}

	public void setListModuleId(List<Long> listModuleId) {
		this.listModuleId = listModuleId;
	}

	/**
	 * @return the documentItemTypeRDLst
	 */
	public List<GenerateDocumentItem> getDocumentItemTypeRDLst() {
		return documentItemTypeRDLst;
	}

	/**
	 * @param documentItemTypeRDLst
	 *            the documentItemTypeRDLst to set
	 */
	public void setDocumentItemTypeRDLst(List<GenerateDocumentItem> documentItemTypeRDLst) {
		this.documentItemTypeRDLst = documentItemTypeRDLst;
	}

	/**
	 * @return the documentItemTypeEDLst
	 */
	public List<GenerateDocumentItem> getDocumentItemTypeEDLst() {
		return documentItemTypeEDLst;
	}

	/**
	 * @param documentItemTypeEDLst
	 *            the documentItemTypeEDLst to set
	 */
	public void setDocumentItemTypeEDLst(List<GenerateDocumentItem> documentItemTypeEDLst) {
		this.documentItemTypeEDLst = documentItemTypeEDLst;
	}

	/**
	 * @return the documentItemTypeIDLst
	 */
	public List<GenerateDocumentItem> getDocumentItemTypeIDLst() {
		return documentItemTypeIDLst;
	}

	/**
	 * @param documentItemTypeIDLst
	 *            the documentItemTypeIDLst to set
	 */
	public void setDocumentItemTypeIDLst(List<GenerateDocumentItem> documentItemTypeIDLst) {
		this.documentItemTypeIDLst = documentItemTypeIDLst;
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
		return StringUtils.defaultString(packageName, StringUtils.EMPTY);
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getCaseSensitivity() {
		return caseSensitivity;
	}

	public void setCaseSensitivity(int caseSensitivity) {
		this.caseSensitivity = caseSensitivity;
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

	public void setListOfAutocompleteDesign(List<AutocompleteDesign> listOfAutocompleteDesign) {
		this.listOfAutocompleteDesign = listOfAutocompleteDesign;
	}

	public int getCodeMaxVal() {
		return codeMaxVal;
	}

	public void setCodeMaxVal(int codeMaxVal) {
		this.codeMaxVal = codeMaxVal;
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

	@Override
	public int compareTo(Project p) {
		return DateUtils.compare(p.getUpdatedDate(), getUpdatedDate());
	}

	public Integer getDbTypeOld() {
		return dbTypeOld;
	}

	public void setDbTypeOld(Integer dbTypeOld) {
		this.dbTypeOld = dbTypeOld;
	}
	
	/**
	 * function until determine project is a Oracle
	 * @return
	 */
	@Transient
	public boolean isOracle(){
		return DbDomainConst.DatabaseType.ORACLE.equals(dbType);
	}
	
}
