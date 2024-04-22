package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class CommonObjectAttribute implements Serializable {

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

	private String objectDefinitionCode;

	private String objectDefinitionIdAutocomplete;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private CommonObjectDefinition commonObjectDefinition;

	private ExternalObjectDefinition externalObjectDefinition;

	private Boolean saveFlg;
    
    private List<CommonObjectAttribute> singleList;
    
    private List<CommonObjectAttribute> objectList;
    
    private String inputColumnNo;
    
    private String externalPackageName;
    
    private String commonObjectDefinitionCode;
    
    private Long moduleId;
    
    /** module code of common object */
    private String moduleCode;

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

	public Long getCommonObjectDefinitionId() {
		return commonObjectDefinitionId;
	}

	public void setCommonObjectDefinitionId(Long commonObjectDefinitionId) {
		this.commonObjectDefinitionId = commonObjectDefinitionId;
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

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
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

	public String getParentCommonObjectAttributeId() {
		return parentCommonObjectAttributeId;
	}

	public void setParentCommonObjectAttributeId(String parentCommonObjectAttributeId) {
		this.parentCommonObjectAttributeId = parentCommonObjectAttributeId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public CommonObjectDefinition getCommonObjectDefinition() {
		return commonObjectDefinition;
	}

	public void setCommonObjectDefinition(CommonObjectDefinition commonObjectDefinition) {
		this.commonObjectDefinition = commonObjectDefinition;
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

	public Boolean getSaveFlg() {
		return saveFlg;
	}

	public void setSaveFlg(Boolean saveFlg) {
		this.saveFlg = saveFlg;
	}

	public Integer getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	public String getObjectDefinitionIdAutocomplete() {
	    return objectDefinitionIdAutocomplete;
    }

	public void setObjectDefinitionIdAutocomplete(String objectDefinitionIdAutocomplete) {
	    this.objectDefinitionIdAutocomplete = objectDefinitionIdAutocomplete;
    }

    /**
     * @return the singleList
     */
    public List<CommonObjectAttribute> getSingleList() {
        return singleList;
    }

    /**
     * @param singleList the singleList to set
     */
    public void setSingleList(List<CommonObjectAttribute> singleList) {
        this.singleList = singleList;
    }

    /**
     * @return the objectList
     */
    public List<CommonObjectAttribute> getObjectList() {
        return objectList;
    }

    /**
     * @param objectList the objectList to set
     */
    public void setObjectList(List<CommonObjectAttribute> objectList) {
        this.objectList = objectList;
    }

	public String getInputColumnNo() {
		return inputColumnNo;
	}

	public void setInputColumnNo(String inputColumnNo) {
		this.inputColumnNo = inputColumnNo;
	}

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

	public String getObjectDefinitionCode() {
		return objectDefinitionCode;
	}

	public void setObjectDefinitionCode(String objectDefinitionCode) {
		this.objectDefinitionCode = objectDefinitionCode;
	}

	public String getExternalPackageName() {
		return externalPackageName;
	}

	public void setExternalPackageName(String externalPackageName) {
		this.externalPackageName = externalPackageName;
	}

	public String getCommonObjectDefinitionCode() {
	    return commonObjectDefinitionCode;
    }

	public void setCommonObjectDefinitionCode(String commonObjectDefinitionCode) {
	    this.commonObjectDefinitionCode = commonObjectDefinitionCode;
    }

	public Long getModuleId() {
	    return moduleId;
    }

	public void setModuleId(Long moduleId) {
	    this.moduleId = moduleId;
    }

    
}
