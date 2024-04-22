package org.terasoluna.qp.app.generatemanagement;

import java.io.Serializable;
import java.sql.Timestamp;

public class GenerateManagementForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long generateId;
	
	private Integer generateMode;
	
	private Timestamp generateDate;
	
	private Long generateBy;
	
	private String generateByName;
	
	private Integer generateStatus;

	private String fileName;
	
	private String filePath;
	
	private Long projectId;
	
	private String projectName;
	
	private String projectCode;
	
	private Integer designStatus;
	
	private Integer databaseType;
	
	private Integer isDownload;
	
	private Timestamp updatedDate;
	
	private Long languageId;
	
	private String languageIdAutocomplete;
	
	public Long getGenerateId() {
		return generateId;
	}

	public void setGenerateId(Long generateId) {
		this.generateId = generateId;
	}

	public Integer getGenerateMode() {
		return generateMode;
	}

	public void setGenerateMode(Integer generateMode) {
		this.generateMode = generateMode;
	}

	public Timestamp getGenerateDate() {
		return generateDate;
	}

	public void setGenerateDate(Timestamp generateDate) {
		this.generateDate = generateDate;
	}

	public Long getGenerateBy() {
		return generateBy;
	}

	public void setGenerateBy(Long generateBy) {
		this.generateBy = generateBy;
	}

	public Integer getGenerateStatus() {
		return generateStatus;
	}

	public void setGenerateStatus(Integer generateStatus) {
		this.generateStatus = generateStatus;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public Integer getDesignStatus() {
		return designStatus;
	}

	public void setDesignStatus(Integer designStatus) {
		this.designStatus = designStatus;
	}

	public Integer getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(Integer databaseType) {
		this.databaseType = databaseType;
	}

	public String getGenerateByName() {
		return generateByName;
	}

	public void setGenerateByName(String generateByName) {
		this.generateByName = generateByName;
	}

	public Integer getIsDownload() {
		return isDownload;
	}

	public void setIsDownload(Integer isDownload) {
		this.isDownload = isDownload;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getLanguageIdAutocomplete() {
		return languageIdAutocomplete;
	}

	public void setLanguageIdAutocomplete(String languageIdAutocomplete) {
		this.languageIdAutocomplete = languageIdAutocomplete;
	}
	
	
}
