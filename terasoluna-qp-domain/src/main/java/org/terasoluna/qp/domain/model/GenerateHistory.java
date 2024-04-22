package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.constants.DbDomainConst.GenerateAppStatus;
import org.terasoluna.qp.app.common.constants.DbDomainConst.GenerateBLogicAppStatus;
import org.terasoluna.qp.app.common.constants.DbDomainConst.GenerateHistoryStatus;

public class GenerateHistory implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String generateId;
	
	private String generateMode;
	
	private Timestamp generateDate;
	
	private String generateBy;
	
	private String generateByName;
	
	private String blogicAppStatus;
	
	private String curAppStatus;
	
	private Integer generateStatus;

	private String fileName;
	
	private String projectId;
	
	private Integer isDownload;
	
	private Timestamp updatedDate;
	
	private String languageId;

	public Integer getGenerateStatus() {
		
		if (generateStatus != null && GenerateHistoryStatus.GENERATE_TIMEOUT == generateStatus.intValue())
			return generateStatus;
		
		if( GenerateAppStatus.INIT.equals(this.getCurAppStatus()) || GenerateAppStatus.GENERATING.equals(this.getCurAppStatus()) ){
			return GenerateHistoryStatus.GENERATING;
		} else if( GenerateAppStatus.GENERATED.equals(this.getCurAppStatus())){
			if( GenerateBLogicAppStatus.SUCCESS.equals(this.getBlogicAppStatus()) ) {
				return GenerateHistoryStatus.GENERATED;
			}
			else {
				return GenerateHistoryStatus.GENERATE_ERROR;
			}
		}
		return generateStatus;
	}

	public void setGenerateStatus(Integer generateStatus) {
		this.generateStatus = generateStatus;
	}

	public String getGenerateId() {
		return StringUtils.isBlank(generateId) ? StringUtils.EMPTY: generateId;
	}

	public void setGenerateId(String generateId) {
		this.generateId = generateId;
	}

	public String getGenerateMode() {
		return generateMode;
	}

	public void setGenerateMode(String generateMode) {
		this.generateMode = generateMode;
	}

	public Timestamp getGenerateDate() {
		return generateDate;
	}

	public void setGenerateDate(Timestamp generateDate) {
		this.generateDate = generateDate;
	}

	public String getGenerateBy() {
		return generateBy;
	}

	public void setGenerateBy(String generateBy) {
		this.generateBy = generateBy;
	}

	public String getGenerateByName() {
		return generateByName;
	}

	public void setGenerateByName(String generateByName) {
		this.generateByName = generateByName;
	}

	public String getBlogicAppStatus() {
		return blogicAppStatus;
	}

	public void setBlogicAppStatus(String blogicAppStatus) {
		this.blogicAppStatus = blogicAppStatus;
	}

	public String getCurAppStatus() {
		return curAppStatus;
	}

	public void setCurAppStatus(String curAppStatus) {
		this.curAppStatus = curAppStatus;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
