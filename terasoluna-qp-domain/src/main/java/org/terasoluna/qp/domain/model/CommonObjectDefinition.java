package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class CommonObjectDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long commonObjectDefinitionId;

	private String commonObjectDefinitionCode;

	private String commonObjectDefinitionName;

	private Long moduleId;
	
	private Long projectId;

	private String remark;
	
	private int itemSeqNo;

	private Long createdBy;

	private String author;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;
	
	private Timestamp systemTime;

	private Module module;
	
	private List<CommonObjectAttribute> commonObjectAttributes;
	
	private String moduleIdAutocomplete;

	private FileFormat fileFormat;
	
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


	public Long getCommonObjectDefinitionId() {
		return commonObjectDefinitionId;
	}

	public void setCommonObjectDefinitionId(Long commonObjectDefinitionId) {
		this.commonObjectDefinitionId = commonObjectDefinitionId;
	}

	public String getCommonObjectDefinitionCode() {
		return commonObjectDefinitionCode;
	}

	public void setCommonObjectDefinitionCode(String commonObjectDefinitionCode) {
		this.commonObjectDefinitionCode = commonObjectDefinitionCode;
	}

	public String getCommonObjectDefinitionName() {
		return commonObjectDefinitionName;
	}

	public void setCommonObjectDefinitionName(String commonObjectDefinitionName) {
		this.commonObjectDefinitionName = commonObjectDefinitionName;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public int getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(int itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	public Timestamp getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Timestamp systemTime) {
		this.systemTime = systemTime;
	}

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	public List<CommonObjectAttribute> getCommonObjectAttributes() {
		return commonObjectAttributes;
	}

	public void setCommonObjectAttributes(List<CommonObjectAttribute> commonObjectAttributes) {
		this.commonObjectAttributes = commonObjectAttributes;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public FileFormat getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(FileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
}
