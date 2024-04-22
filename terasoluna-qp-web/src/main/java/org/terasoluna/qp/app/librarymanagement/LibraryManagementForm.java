package org.terasoluna.qp.app.librarymanagement;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.message.LibraryManagementMessageConst;

public class LibraryManagementForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long libraryId;

	//@NotEmpty(message = LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0005)
	@Size(min = DbDomainConst.MIN_VAL_REMARK,max = DbDomainConst.MAX_VAL_OTHER,message = LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0005 +";"+ DbDomainConst.MIN_VAL_REMARK + ";"+DbDomainConst.MAX_VAL_OTHER)
	private String groupId;
	
	//@NotEmpty(message = LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0006)
	@Size(min = DbDomainConst.MIN_VAL_REMARK,max = DbDomainConst.MAX_VAL_OTHER,message = LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0006 +";"+ DbDomainConst.MIN_VAL_REMARK + ";"+DbDomainConst.MAX_VAL_OTHER)
	private String artifactId;

	@Size(min = DbDomainConst.MIN_VAL_REMARK,max = DbDomainConst.MAX_VAL_OTHER,message = LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0007 +";"+ DbDomainConst.MIN_VAL_REMARK + ";"+DbDomainConst.MAX_VAL_OTHER)
	private String version;

	@Size(min = DbDomainConst.MIN_VAL_REMARK,max = DbDomainConst.MAX_VAL_OTHER,message = LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0008 +";"+ DbDomainConst.MIN_VAL_REMARK + ";"+DbDomainConst.MAX_VAL_OTHER)
	private String classifier;

	@Size(min = DbDomainConst.MIN_VAL_REMARK,max = DbDomainConst.MAX_VAL_OTHER,message = LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0009 +";"+ DbDomainConst.MIN_VAL_REMARK + ";"+DbDomainConst.MAX_VAL_OTHER)
	private String scope;

	@Size(min = DbDomainConst.MIN_VAL_REMARK,max = DbDomainConst.MAX_VAL_OTHER,message = LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0010 +";"+ DbDomainConst.MIN_VAL_REMARK + ";"+DbDomainConst.MAX_VAL_OTHER)
	private String type;

	private Integer optionalFlg;
	
	private Long projectId;
	
	private Timestamp updatedDate;
	
	private String systemPath;
	
	private String systemFlag="1";
	
	private MultipartFile uploadFileContent;
	
	private String uploadFileName;
	
	private boolean uploadFileContentChange;
	
	private int maxSize;

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Integer getOptionalFlg() {
		return optionalFlg;
	}

	public void setOptionalFlg(Integer optionalFlg) {
		this.optionalFlg = optionalFlg;
	}

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

	public MultipartFile getUploadFileContent() {
		return uploadFileContent;
	}

	public void setUploadFileContent(MultipartFile uploadFileContent) {
		this.uploadFileContent = uploadFileContent;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public boolean getUploadFileContentChange() {
		return uploadFileContentChange;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadFileContentChange(boolean uploadFileContentChange) {
		this.uploadFileContentChange = uploadFileContentChange;
	}

	public String getSystemPath() {
		return systemPath;
	}

	public void setSystemPath(String systemPath) {
		this.systemPath = systemPath;
	}

	public String getSystemFlag() {
		return systemFlag;
	}

	public void setSystemFlag(String systemFlag) {
		this.systemFlag = systemFlag;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
