package org.terasoluna.qp.app.commonobjectdefinition;

import java.io.Serializable;
import java.sql.Timestamp;

import org.terasoluna.qp.app.externalobjectdefinition.ExternalObjectDefinitionForm;

public class CommonObjectAttributeForm implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String commonObjectAttributeId;

	private String commonObjectAttributeCode;

	private String commonObjectAttributeName;

	private Boolean arrayFlg = false;

	private Long commonObjectDefinitionId;

	private String parentCommonObjectAttributeId;

	private Integer itemSeqNo;

	private String tableIndex;

	private Integer dataType;

	private String groupId;

	private Long projectId;

	private Long parentId;

	private Long objectDefinitionId;

	private String objectDefinitionIdAutocomplete;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private CommonObjectDefinitionForm commonObjectDefinition;

	private ExternalObjectDefinitionForm externalObjectDefinition;

	private Boolean saveFlg;
	
	private Long moduleId;

	public String getCommonObjectAttributeId() {
		return commonObjectAttributeId;
	}

	public void setCommonObjectAttributeId(String commonObjectAttributeId) {
		this.commonObjectAttributeId = commonObjectAttributeId;
	}

	public String getCommonObjectAttributeCode() {
		return commonObjectAttributeCode;
	}

	public void setCommonObjectAttributeCode(String commonObjectAttributeCode) {
		this.commonObjectAttributeCode = commonObjectAttributeCode;
	}

	public String getCommonObjectAttributeName() {
		return commonObjectAttributeName;
	}

	public void setCommonObjectAttributeName(String commonObjectAttributeName) {
		this.commonObjectAttributeName = commonObjectAttributeName;
	}

	public Boolean getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Boolean arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public Long getCommonObjectDefinitionId() {
		return commonObjectDefinitionId;
	}

	public void setCommonObjectDefinitionId(Long commonObjectDefinitionId) {
		this.commonObjectDefinitionId = commonObjectDefinitionId;
	}

	public String getParentCommonObjectAttributeId() {
		return parentCommonObjectAttributeId;
	}

	public void setParentCommonObjectAttributeId(String parentCommonObjectAttributeId) {
		this.parentCommonObjectAttributeId = parentCommonObjectAttributeId;
	}

	public Integer getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	public String getTableIndex() {
		return tableIndex;
	}

	public void setTableIndex(String tableIndex) {
		this.tableIndex = tableIndex;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getObjectDefinitionId() {
		return objectDefinitionId;
	}

	public void setObjectDefinitionId(Long objectDefinitionId) {
		this.objectDefinitionId = objectDefinitionId;
	}

	public String getObjectDefinitionIdAutocomplete() {
		return objectDefinitionIdAutocomplete;
	}

	public void setObjectDefinitionIdAutocomplete(String objectDefinitionIdAutocomplete) {
		this.objectDefinitionIdAutocomplete = objectDefinitionIdAutocomplete;
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

	public Boolean getSaveFlg() {
		return saveFlg;
	}

	public void setSaveFlg(Boolean saveFlg) {
		this.saveFlg = saveFlg;
	}

	public CommonObjectDefinitionForm getCommonObjectDefinition() {
	    return commonObjectDefinition;
    }

	public void setCommonObjectDefinition(CommonObjectDefinitionForm commonObjectDefinition) {
	    this.commonObjectDefinition = commonObjectDefinition;
    }

    /**
     * @return the externalObjectDefinition
     */
    public ExternalObjectDefinitionForm getExternalObjectDefinition() {
        return externalObjectDefinition;
    }

    /**
     * @param externalObjectDefinition the externalObjectDefinition to set
     */
    public void setExternalObjectDefinition(ExternalObjectDefinitionForm externalObjectDefinition) {
        this.externalObjectDefinition = externalObjectDefinition;
    }

	public Long getModuleId() {
	    return moduleId;
    }

	public void setModuleId(Long moduleId) {
	    this.moduleId = moduleId;
    }

}
