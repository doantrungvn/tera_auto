package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InputBean implements Serializable, Cloneable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String inputBeanId;

	private String inputBeanCode;

	private String inputBeanName;

	private String messageString;

	private String messageStringAutocomplete;

	private Integer inputBeanType;

	private Integer dataType;

	private Boolean arrayFlg = false;

	private Long screenItemId;

	private Long businessLogicId;

	private Integer itemSequenceNo;

	private String tableIndex;

	private String groupId;

	private Long tblDesignId;

	private String tblDesignName;

	private String tblDesignCode;

	private Long columnId;

	private String columnName;

	private boolean createdMessageFlg = false;

	private List<InputBean> singleList;

	private List<InputBean> objectList;

	private String jsonValidationInputs = "";

	private List<ValidationCheckDetail> lstValidationCheckDetails = new ArrayList<ValidationCheckDetail>();

	private String parentInputBeanId;

	private String parentInputBeanName;

	private String parentInputBeanCode;

	private String parentInputBeanArrayFlag;

	private String grandParentInputBeanId;

	private String grandParentInputBeanName;

	private String grandParentInputBeanCode;

	private String grandParentInputBeanArrayFlag;

	private ScreenItem screenItem;

	private MessageDesign messageDesign;

	private Integer objectType;

	private Long objectId;

	private String commonObjDefiCode;
	
	/** module code of common object */
	private String moduleCode;

	private String externalObjDefiCode;

	private Boolean objectFlg = true;

	/** In the case of object external  */
	private String packageNameObjExt;

	private Integer scopeType = 0;

	private String scopeValue;

	private String scopeValueAutocomplete;

	private Integer displayType = 1;

	private String validateStandar;

	private Integer autoincrementFlg;

	private Integer groupBaseTypeId;

	private String sessionManagementCode;

	private Integer sessionManagementType = 1;

	private String sessionManagementTableDesignCode;

	private Boolean flagUsingTempId;

	private String logicalDataType;

	private Boolean flagUsingDatasource;

	private ScreenItemOutput screenItemMapping;

	private String screenItemIdAutocomplete;

	private Boolean mappingScreenItemFlag = false;
	
	private Integer fromOrTo; // From : 0 ; To : 1

	private String defaultValue;
	
	private String defaultValueTo;

	private Integer dataTypeSession;

	private Boolean arrayFlagSession = false;
	
	private Integer screenItemLogicalDataType;
	
	private Integer blogicRequestMethod;
	
	private String businessLogicCode;
	
	private Long moduleId;
	
	private Long projectId;
	
	private List<InputBean> lstChildrens;
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getInputBeanId() {
		return inputBeanId;
	}

	public void setInputBeanId(String inputBeanId) {
		this.inputBeanId = inputBeanId;
	}

	public String getInputBeanCode() {
		return inputBeanCode;
	}

	public void setInputBeanCode(String inputBeanCode) {
		this.inputBeanCode = inputBeanCode;
	}

	public Integer getInputBeanType() {
		return inputBeanType;
	}

	public void setInputBeanType(Integer inputBeanType) {
		this.inputBeanType = inputBeanType;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Long getScreenItemId() {
		return screenItemId;
	}

	public void setScreenItemId(Long screenItemId) {
		this.screenItemId = screenItemId;
	}

	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	public String getParentInputBeanId() {
		return parentInputBeanId;
	}

	public void setParentInputBeanId(String parentInputBeanId) {
		this.parentInputBeanId = parentInputBeanId;
	}

	public Integer getItemSequenceNo() {
		return itemSequenceNo;
	}

	public void setItemSequenceNo(Integer itemSequenceNo) {
		this.itemSequenceNo = itemSequenceNo;
	}

	public String getTableIndex() {
		return tableIndex;
	}

	public void setTableIndex(String tableIndex) {
		this.tableIndex = tableIndex;
	}

	public String getMessageStringAutocomplete() {
		return messageStringAutocomplete;
	}

	public void setMessageStringAutocomplete(String messageStringAutocomplete) {
		this.messageStringAutocomplete = messageStringAutocomplete;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	public boolean isCreatedMessageFlg() {
		return createdMessageFlg;
	}

	public void setCreatedMessageFlg(boolean createdMessageFlg) {
		this.createdMessageFlg = createdMessageFlg;
	}

	public String getInputBeanName() {
		return inputBeanName;
	}

	public void setInputBeanName(String inputBeanName) {
		this.inputBeanName = inputBeanName;
	}

	public Long getTblDesignId() {
		return tblDesignId;
	}

	public void setTblDesignId(Long tblDesignId) {
		this.tblDesignId = tblDesignId;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public String getTblDesignName() {
		return tblDesignName;
	}

	public void setTblDesignName(String tblDesignName) {
		this.tblDesignName = tblDesignName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Boolean getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Boolean arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public List<ValidationCheckDetail> getLstValidationCheckDetails() {
		return lstValidationCheckDetails;
	}

	public void setLstValidationCheckDetails(List<ValidationCheckDetail> lstValidationCheckDetails) {
		this.lstValidationCheckDetails = lstValidationCheckDetails;
	}

	public String getJsonValidationInputs() {
		return jsonValidationInputs;
	}

	public void setJsonValidationInputs(String jsonValidationInputs) {
		this.jsonValidationInputs = jsonValidationInputs;
	}

	/**
	 * @return the singleList
	 */
	public List<InputBean> getSingleList() {
		return singleList;
	}

	/**
	 * @param singleList the singleList to set
	 */
	public void setSingleList(List<InputBean> singleList) {
		this.singleList = singleList;
	}

	/**
	 * @return the objectList
	 */
	public List<InputBean> getObjectList() {
		return objectList;
	}

	/**
	 * @param objectList the objectList to set
	 */
	public void setObjectList(List<InputBean> objectList) {
		this.objectList = objectList;
	}

	public String getParentInputBeanName() {
		return parentInputBeanName;
	}

	public void setParentInputBeanName(String parentInputBeanName) {
		this.parentInputBeanName = parentInputBeanName;
	}

	public String getParentInputBeanCode() {
		return parentInputBeanCode;
	}

	public void setParentInputBeanCode(String parentInputBeanCode) {
		this.parentInputBeanCode = parentInputBeanCode;
	}

	public String getParentInputBeanArrayFlag() {
		return parentInputBeanArrayFlag;
	}

	public void setParentInputBeanArrayFlag(String parentInputBeanArrayFlag) {
		this.parentInputBeanArrayFlag = parentInputBeanArrayFlag;
	}

	public String getGrandParentInputBeanId() {
		return grandParentInputBeanId;
	}

	public void setGrandParentInputBeanId(String grandParentInputBeanId) {
		this.grandParentInputBeanId = grandParentInputBeanId;
	}

	public String getGrandParentInputBeanName() {
		return grandParentInputBeanName;
	}

	public void setGrandParentInputBeanName(String grandParentInputBeanName) {
		this.grandParentInputBeanName = grandParentInputBeanName;
	}

	public String getGrandParentInputBeanCode() {
		return grandParentInputBeanCode;
	}

	public void setGrandParentInputBeanCode(String grandParentInputBeanCode) {
		this.grandParentInputBeanCode = grandParentInputBeanCode;
	}

	public String getGrandParentInputBeanArrayFlag() {
		return grandParentInputBeanArrayFlag;
	}

	public void setGrandParentInputBeanArrayFlag(
			String grandParentInputBeanArrayFlag) {
		this.grandParentInputBeanArrayFlag = grandParentInputBeanArrayFlag;
	}

	public ScreenItem getScreenItem() {
		return screenItem;
	}

	public void setScreenItem(ScreenItem screenItem) {
		this.screenItem = screenItem;
	}

	public MessageDesign getMessageDesign() {
		return messageDesign;
	}

	public void setMessageDesign(MessageDesign messageDesign) {
		this.messageDesign = messageDesign;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * @return the objectType
	 */
	public Integer getObjectType() {
		return objectType;
	}

	/**
	 * @param objectType the objectType to set
	 */
	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	/**
	 * @return the objectId
	 */
	public Long getObjectId() {
		return objectId;
	}

	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	/**
	 * @return the tblDesignCode
	 */
	public String getTblDesignCode() {
		return tblDesignCode;
	}

	/**
	 * @param tblDesignCode the tblDesignCode to set
	 */
	public void setTblDesignCode(String tblDesignCode) {
		this.tblDesignCode = tblDesignCode;
	}

	/**
	 * @return the objectFlg
	 */
	public Boolean getObjectFlg() {
		return objectFlg;
	}

	/**
	 * @param objectFlg the objectFlg to set
	 */
	public void setObjectFlg(Boolean objectFlg) {
		this.objectFlg = objectFlg;
	}

	/**
	 * @return the commonObjDefiCode
	 */
	public String getCommonObjDefiCode() {
		return commonObjDefiCode;
	}

	/**
	 * @param commonObjDefiCode the commonObjDefiCode to set
	 */
	public void setCommonObjDefiCode(String commonObjDefiCode) {
		this.commonObjDefiCode = commonObjDefiCode;
	}

	/**
	 * @return the externalObjDefiCode
	 */
	public String getExternalObjDefiCode() {
		return externalObjDefiCode;
	}

	/**
	 * @param externalObjDefiCode the externalObjDefiCode to set
	 */
	public void setExternalObjDefiCode(String externalObjDefiCode) {
		this.externalObjDefiCode = externalObjDefiCode;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageNameObjExt() {
		return packageNameObjExt;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageNameObjExt(String packageNameObjExt) {
		this.packageNameObjExt = packageNameObjExt;
	}

	public Integer getScopeType() {
		return scopeType;
	}

	public void setScopeType(Integer scopeType) {
		this.scopeType = scopeType;
	}

	public String getScopeValue() {
		return scopeValue;
	}

	public void setScopeValue(String scopeValue) {
		this.scopeValue = scopeValue;
	}

	public String getScopeValueAutocomplete() {
		return scopeValueAutocomplete;
	}

	public void setScopeValueAutocomplete(String scopeValueAutocomplete) {
		this.scopeValueAutocomplete = scopeValueAutocomplete;
	}

	/**
	 * @return the displayType
	 */
	public Integer getDisplayType() {
		return displayType;
	}

	/**
	 * @param displayType the displayType to set
	 */
	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
	}

	/**
	 * @return the validateStandar
	 */
	public String getValidateStandar() {
		return validateStandar;
	}

	/**
	 * @param validateStandar the validateStandar to set
	 */
	public void setValidateStandar(String validateStandar) {
		this.validateStandar = validateStandar;
	}

	/**
	 * @return the autoincrementFlg
	 */
	public Integer getAutoincrementFlg() {
		return autoincrementFlg;
	}

	/**
	 * @param autoincrementFlg the autoincrementFlg to set
	 */
	public void setAutoincrementFlg(Integer autoincrementFlg) {
		this.autoincrementFlg = autoincrementFlg;
	}

	public Integer getGroupBaseTypeId() {
		return groupBaseTypeId;
	}

	public void setGroupBaseTypeId(Integer groupBaseTypeId) {
		this.groupBaseTypeId = groupBaseTypeId;
	}

	public String getSessionManagementCode() {
		return sessionManagementCode;
	}

	public void setSessionManagementCode(String sessionManagementCode) {
		this.sessionManagementCode = sessionManagementCode;
	}

	public Integer getSessionManagementType() {
		return sessionManagementType;
	}

	public void setSessionManagementType(Integer sessionManagementType) {
		this.sessionManagementType = sessionManagementType;
	}

	public String getSessionManagementTableDesignCode() {
		return sessionManagementTableDesignCode;
	}

	public void setSessionManagementTableDesignCode(
			String sessionManagementTableDesignCode) {
		this.sessionManagementTableDesignCode = sessionManagementTableDesignCode;
	}

	public Boolean getFlagUsingTempId() {
		return flagUsingTempId;
	}

	public void setFlagUsingTempId(Boolean flagUsingTempId) {
		this.flagUsingTempId = flagUsingTempId;
	}

	public String getLogicalDataType() {
		return logicalDataType;
	}

	public void setLogicalDataType(String logicalDataType) {
		this.logicalDataType = logicalDataType;
	}

	public Boolean getFlagUsingDatasource() {
		return flagUsingDatasource;
	}

	public void setFlagUsingDatasource(Boolean flagUsingDatasource) {
		this.flagUsingDatasource = flagUsingDatasource;
	}

	public String getScreenItemIdAutocomplete() {
		return screenItemIdAutocomplete;
	}

	public void setScreenItemIdAutocomplete(String screenItemIdAutocomplete) {
		this.screenItemIdAutocomplete = screenItemIdAutocomplete;
	}

	public ScreenItemOutput getScreenItemMapping() {
		return screenItemMapping;
	}

	public void setScreenItemMapping(ScreenItemOutput screenItemMapping) {
		this.screenItemMapping = screenItemMapping;
	}

	public Boolean getMappingScreenItemFlag() {
	    return mappingScreenItemFlag;
    }

	public void setMappingScreenItemFlag(Boolean mappingScreenItemFlag) {
	    this.mappingScreenItemFlag = mappingScreenItemFlag;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

	public Integer getFromOrTo() {
		return fromOrTo;
	}

	public void setFromOrTo(Integer fromOrTo) {
		this.fromOrTo = fromOrTo;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the defaultValueTo
	 */
	public String getDefaultValueTo() {
		return defaultValueTo;
	}

	/**
	 * @param defaultValueTo the defaultValueTo to set
	 */
	public void setDefaultValueTo(String defaultValueTo) {
		this.defaultValueTo = defaultValueTo;
	}

	public Integer getDataTypeSession() {
		return dataTypeSession;
	}

	public void setDataTypeSession(Integer dataTypeSession) {
		this.dataTypeSession = dataTypeSession;
	}

	public Boolean getArrayFlagSession() {
		return arrayFlagSession;
	}

	public void setArrayFlagSession(Boolean arrayFlagSession) {
		this.arrayFlagSession = arrayFlagSession;
	}

	public Integer getScreenItemLogicalDataType() {
		return screenItemLogicalDataType;
	}

	public void setScreenItemLogicalDataType(Integer screenItemLogicalDataType) {
		this.screenItemLogicalDataType = screenItemLogicalDataType;
	}

	public Integer getBlogicRequestMethod() {
		return blogicRequestMethod;
	}

	public void setBlogicRequestMethod(Integer blogicRequestMethod) {
		this.blogicRequestMethod = blogicRequestMethod;
	}

	public String getBusinessLogicCode() {
	    return businessLogicCode;
    }

	public void setBusinessLogicCode(String businessLogicCode) {
	    this.businessLogicCode = businessLogicCode;
    }

	public Long getModuleId() {
	    return moduleId;
    }

	public void setModuleId(Long moduleId) {
	    this.moduleId = moduleId;
    }

	public Long getProjectId() {
	    return projectId;
    }

	public void setProjectId(Long projectId) {
	    this.projectId = projectId;
    }

	public List<InputBean> getLstChildrens() {
	    return lstChildrens;
    }

	public void setLstChildrens(List<InputBean> lstChildrens) {
	    this.lstChildrens = lstChildrens;
    }
	

}
