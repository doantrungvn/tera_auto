package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.constants.DbDomainConst;

@XmlRootElement(name = "externalObjDef")
public class ExternalObjectDefinition implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long externalObjectDefinitionId;

	private String externalObjectDefinitionCode;

	private String externalObjectDefinitionName;

	private Long moduleId;
	
	private Long projectId;
	
	private String moduleIdAutocomplete;

	private String remark;
	
	private String className;
	
	private String packageName;
	
	private int itemSeqNo;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;
	
	private Timestamp systemTime;
	
	private String externalObjectType;

	private List<ExternalObjectAttribute> externalObjectAttributes;
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
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

	public Long getExternalObjectDefinitionId() {
		return externalObjectDefinitionId;
	}

	public void setExternalObjectDefinitionId(Long externalObjectDefinitionId) {
		this.externalObjectDefinitionId = externalObjectDefinitionId;
	}

	@XmlElement(name = "externalObjDefCode")
	public String getExternalObjectDefinitionCode() {
		return externalObjectDefinitionCode;
	}

	public void setExternalObjectDefinitionCode(String externalObjectDefinitionCode) {
		this.externalObjectDefinitionCode = externalObjectDefinitionCode;
	}

	@XmlElement(name = "externalObjDefName")
	public String getExternalObjectDefinitionName() {
		return externalObjectDefinitionName;
	}

	public void setExternalObjectDefinitionName(String externalObjectDefinitionName) {
		this.externalObjectDefinitionName = externalObjectDefinitionName;
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

	@XmlElement(name = "externalObjAttr")
	public List<ExternalObjectAttribute> getExternalObjectAttributes() {
		return externalObjectAttributes;
	}

	public void setExternalObjectAttributes(List<ExternalObjectAttribute> externalObjectAttributes) {
		this.externalObjectAttributes = externalObjectAttributes;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	@XmlElement(name = "externalObjectType")
	public String getExternalObjectType() {
		return StringUtils.isBlank(externalObjectType) ? DbDomainConst.QPCommomFlg.NO : externalObjectType;
	}

	public void setExternalObjectType(String externalObjectType) {
		this.externalObjectType = externalObjectType;
	}

}
