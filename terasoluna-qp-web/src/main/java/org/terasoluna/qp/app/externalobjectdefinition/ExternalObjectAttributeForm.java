package org.terasoluna.qp.app.externalobjectdefinition;

import java.io.Serializable;

public class ExternalObjectAttributeForm implements Serializable {
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

	private ExternalObjectDefinitionForm externalObjectDefinition;

	private Boolean saveFlg;

	private Long externalObjectDefinitionIdAC;

	private String externalObjectDefinitionIdACAutocomplete;
	
	private Long moduleId;

	public String getExternalObjectAttributeCode() {
		return externalObjectAttributeCode;
	}

	public void setExternalObjectAttributeCode(String externalObjectAttributeCode) {
		this.externalObjectAttributeCode = externalObjectAttributeCode;
	}

	public String getExternalObjectAttributeName() {
		return externalObjectAttributeName;
	}

	public void setExternalObjectAttributeName(String externalObjectAttributeName) {
		this.externalObjectAttributeName = externalObjectAttributeName;
	}

	public Boolean getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Boolean arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public Long getExternalObjectDefinitionId() {
		return externalObjectDefinitionId;
	}

	public void setExternalObjectDefinitionId(Long externalObjectDefinitionId) {
		this.externalObjectDefinitionId = externalObjectDefinitionId;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

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

	public String getParentExternalObjectAttributeId() {
		return parentExternalObjectAttributeId;
	}

	public void setParentExternalObjectAttributeId(String parentExternalObjectAttributeId) {
		this.parentExternalObjectAttributeId = parentExternalObjectAttributeId;
	}

	public Long getExternalObjectDefinitionIdAC() {
		return externalObjectDefinitionIdAC;
	}

	public void setExternalObjectDefinitionIdAC(Long externalObjectDefinitionIdAC) {
		this.externalObjectDefinitionIdAC = externalObjectDefinitionIdAC;
	}

	public String getExternalObjectDefinitionIdACAutocomplete() {
		return externalObjectDefinitionIdACAutocomplete;
	}

	public void setExternalObjectDefinitionIdACAutocomplete(String externalObjectDefinitionIdACAutocomplete) {
		this.externalObjectDefinitionIdACAutocomplete = externalObjectDefinitionIdACAutocomplete;
	}

	public String getExternalObjectAttributeId() {
		return externalObjectAttributeId;
	}

	public void setExternalObjectAttributeId(String externalObjectAttributeId) {
		this.externalObjectAttributeId = externalObjectAttributeId;
	}

	/**
	 * @return the itemSeqNo
	 */
	public Integer getItemSeqNo() {
		return itemSeqNo;
	}

	/**
	 * @param itemSeqNo
	 *            the itemSeqNo to set
	 */
	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	/**
	 * @return the objectDefinitionId
	 */
	public Long getObjectDefinitionId() {
		return objectDefinitionId;
	}

	/**
	 * @param objectDefinitionId
	 *            the objectDefinitionId to set
	 */
	public void setObjectDefinitionId(Long objectDefinitionId) {
		this.objectDefinitionId = objectDefinitionId;
	}

	/**
	 * @return the objectDefinitionIdAutocomplete
	 */
	public String getObjectDefinitionIdAutocomplete() {
		return objectDefinitionIdAutocomplete;
	}

	/**
	 * @param objectDefinitionIdAutocomplete
	 *            the objectDefinitionIdAutocomplete to set
	 */
	public void setObjectDefinitionIdAutocomplete(String objectDefinitionIdAutocomplete) {
		this.objectDefinitionIdAutocomplete = objectDefinitionIdAutocomplete;
	}

	/**
	 * @return the saveFlg
	 */
	public Boolean getSaveFlg() {
		return saveFlg;
	}

	/**
	 * @param saveFlg
	 *            the saveFlg to set
	 */
	public void setSaveFlg(Boolean saveFlg) {
		this.saveFlg = saveFlg;
	}

	public ExternalObjectDefinitionForm getExternalObjectDefinition() {
	    return externalObjectDefinition;
    }

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
