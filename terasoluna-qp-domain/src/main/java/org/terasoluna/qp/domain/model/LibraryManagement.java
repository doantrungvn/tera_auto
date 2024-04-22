package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class LibraryManagement implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long libraryId;

	private String groupId;

	private String artifactId;

	private String version;

	private String classifier;

	private String scope;

	private String type;

	private Integer optionalFlg;

	private Long projectId;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private Timestamp systemDate;

	private String systemPath;

	private Integer systemFlag;

	private byte[] uploadFileContent;

	private String uploadFileName;

	private Boolean uploadFileContentChange;

	public Long getLibraryId() {
		return libraryId;
	}

	public void setLibraryId(Long libraryId) {
		this.libraryId = libraryId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getClassifier() {
		return classifier;
	}

	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOptionalFlg() {
		return optionalFlg;
	}

	public void setOptionalFlg(Integer optionalFlg) {
		this.optionalFlg = optionalFlg;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Timestamp getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(Timestamp systemDate) {
		this.systemDate = systemDate;
	}

	public byte[] getUploadFileContent() {
		return uploadFileContent;
	}

	public void setUploadFileContent(byte[] uploadFileContent) {
		this.uploadFileContent = uploadFileContent;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public Boolean getUploadFileContentChange() {
		return uploadFileContentChange;
	}

	public void setUploadFileContentChange(Boolean uploadFileContentChange) {
		this.uploadFileContentChange = uploadFileContentChange;
	}

	public String getSystemPath() {
		return systemPath;
	}

	public void setSystemPath(String systemPath) {
		this.systemPath = systemPath;
	}

	public Integer getSystemFlag() {
		return systemFlag;
	}

	public void setSystemFlag(Integer systemFlag) {
		this.systemFlag = systemFlag;
	}

	public String getFileName() {
		return getArtifactId() + "_" + getUploadFileName();
	}

}
