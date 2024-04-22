package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "externalObjAttr")
public class ExternalObjectAttribute implements Serializable {
	private static final long serialVersionUID = 1L;

	private String externalObjectAttributeId;

	private String externalObjectAttributeCode;

	private String externalObjectAttributeName;

	private Boolean arrayFlg = false;

	private Long externalObjectDefinitionId;

	private String parentExternalObjectAttributeId;

	private Integer itemSeqNo;

	private String tableIndex;

	private Integer dataType;

	private String groupId;

	private Long objectDefinitionId;

	private String objectDefinitionIdAutocomplete;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private ExternalObjectDefinition externalObjectDefinition;

	private Boolean saveFlg;
	
	private String externalObjectDefinitionCode;
	
	private Long moduleId;

	public String getTableIndex() {
		return tableIndex;
	}

	public void setTableIndex(String tableIndex) {
		this.tableIndex = tableIndex;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Long getExternalObjectDefinitionACId() {
		return externalObjectDefinitionACId;
	}

	public void setExternalObjectDefinitionACId(Long externalObjectDefinitionACId) {
		this.externalObjectDefinitionACId = externalObjectDefinitionACId;
	}

	public String getExternalObjectDefinitionACIdAutocomplete() {
		return externalObjectDefinitionACIdAutocomplete;
	}

	public void setExternalObjectDefinitionACIdAutocomplete(
			String externalObjectDefinitionACIdAutocomplete) {
		this.externalObjectDefinitionACIdAutocomplete = externalObjectDefinitionACIdAutocomplete;
	}

	private Long externalObjectDefinitionACId;

	private String externalObjectDefinitionACIdAutocomplete;

	@XmlElement(name = "externalObjAttrCode")
	public String getExternalObjectAttributeCode() {
		return externalObjectAttributeCode;
	}

	public void setExternalObjectAttributeCode(String externalObjectAttributeCode) {
		this.externalObjectAttributeCode = externalObjectAttributeCode;
	}

	public Long getExternalObjectDefinitionId() {
		return externalObjectDefinitionId;
	}

	public void setExternalObjectDefinitionId(Long externalObjectDefinitionId) {
		this.externalObjectDefinitionId = externalObjectDefinitionId;
	}

	@XmlElement(name = "externalObjAttrName")
	public String getExternalObjectAttributeName() {
		return externalObjectAttributeName;
	}

	public void setExternalObjectAttributeName(String externalObjectAttributeName) {
		this.externalObjectAttributeName = externalObjectAttributeName;
	}

	@XmlElement(name = "arrayFlg")
	public Boolean getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Boolean arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	@XmlElement(name = "dataType")
	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public ExternalObjectDefinition getExternalObjectDefinition() {
		return externalObjectDefinition;
	}

	public void setExternalObjectDefinition(ExternalObjectDefinition externalObjectDefinition) {
		this.externalObjectDefinition = externalObjectDefinition;
	}

	public Long getObjectDefinitionId() {
		return objectDefinitionId;
	}

	public void setObjectDefinitionId(Long objectDefinitionId) {
		this.objectDefinitionId = objectDefinitionId;
	}

	/**
	 * @return the externalObjectAttributeId
	 */
	public String getExternalObjectAttributeId() {
		return externalObjectAttributeId;
	}

	/**
	 * @param externalObjectAttributeId the externalObjectAttributeId to set
	 */
	public void setExternalObjectAttributeId(String externalObjectAttributeId) {
		this.externalObjectAttributeId = externalObjectAttributeId;
	}

	/**
	 * @return the parentExternalObjectAttributeId
	 */
	public String getParentExternalObjectAttributeId() {
		return parentExternalObjectAttributeId;
	}

	/**
	 * @param parentExternalObjectAttributeId the parentExternalObjectAttributeId to set
	 */
	public void setParentExternalObjectAttributeId(
			String parentExternalObjectAttributeId) {
		this.parentExternalObjectAttributeId = parentExternalObjectAttributeId;
	}

	/**
	 * @return the saveFlg
	 */
	@XmlElement(name = "saveFlg")
	public Boolean getSaveFlg() {
		return saveFlg;
	}

	/**
	 * @param saveFlg the saveFlg to set
	 */
	public void setSaveFlg(Boolean saveFlg) {
		this.saveFlg = saveFlg;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the itemSeqNo
	 */
	@XmlElement(name = "itemSeqNo")
	public Integer getItemSeqNo() {
		return itemSeqNo;
	}

	/**
	 * @param itemSeqNo the itemSeqNo to set
	 */
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	/**
	 * @return the objectDefinitionIdAutocomplete
	 */
	public String getObjectDefinitionIdAutocomplete() {
		return objectDefinitionIdAutocomplete;
	}

	/**
	 * @param objectDefinitionIdAutocomplete the objectDefinitionIdAutocomplete to set
	 */
	public void setObjectDefinitionIdAutocomplete(
			String objectDefinitionIdAutocomplete) {
		this.objectDefinitionIdAutocomplete = objectDefinitionIdAutocomplete;
	}

	public String getExternalObjectDefinitionCode() {
	    return externalObjectDefinitionCode;
    }

	public void setExternalObjectDefinitionCode(String externalObjectDefinitionCode) {
	    this.externalObjectDefinitionCode = externalObjectDefinitionCode;
    }

	public Long getModuleId() {
	    return moduleId;
    }

	public void setModuleId(Long moduleId) {
	    this.moduleId = moduleId;
    }
}
