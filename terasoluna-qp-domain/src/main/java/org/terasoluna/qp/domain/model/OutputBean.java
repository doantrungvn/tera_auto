package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OutputBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String outputBeanId;

	private String outputBeanCode;

	private String outputBeanName;

	private String messageString;

	private String messageStringAutocomplete;

	private Integer dataType;

	private Boolean arrayFlg = false;

	private Long businessLogicId;
	
	private String businessLogicCode;

	private String parentOutputBeanId;
	
	private String parentOutputBeanName;
	
	private String parentOutputBeanCode;
	
	private String parentOutputBeanArrayFlag;
	
	private String grandParentOutputBeanId;
	
	private String grandParentOutputBeanName;
	
	private String grandParentOutputBeanCode;
	
	private String grandParentOutputBeanArrayFlag;

	private String tableIndex;

	private Integer itemSequenceNo;

	private String groupId;

	private Long tblDesignId;
	
	private String tblDesignCode;

	private Long columnId;

	private Integer groupBaseTypeId;

	private Integer impactStatus = 1;

	private Long screenItemId;

	private List<ScreenItemOutput> lstScreenItemMapping = new ArrayList<ScreenItemOutput>();

	private String screenItemIdAutocomplete;

	private boolean createdMessageFlg = false;

	private String tblDesignName;

	private String columnName;

	private String columnCode;
	
	private Integer objectType;
	
	private Long objectId;
	
	private List<OutputBean> singleList;
	
	private List<OutputBean> objectList;
    
    private Boolean objectFlg = true;

	private String commonObjDefiCode;
	
	/** module code of common object */
    private String moduleCode;
	
	private String externalObjDefiCode;
    
    /** In the case of object external  */
    private String packageNameObjExt;
    
    private Integer scopeType = 0;
    
    private String scopeValue;
    
    private String scopeValueAutocomplete;
    
    private Boolean flagTotalCount;
    
    private Integer mappingType;
    
    private Integer enabledFlag;
    
    private Integer baseType;
    
    private Integer designType;
    
    private String sessionManagementCode;
    
    private String logicalDataType;
    
    private MessageDesign messageDesign;
    
    private Integer outputBeanType;
    
    private String itemCodeMapping;
    
    private String areaCodeMapping;
    
    private String formCodeMapping;

	private Integer dataTypeSession;

	private Boolean arrayFlagSession = false;
	
	private Long moduleId;
	
	private Long projectId;
	
	private List<OutputBean> lstChildrens;
    
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getOutputBeanId() {
		return outputBeanId;
	}

	public void setOutputBeanId(String outputBeanId) {
		this.outputBeanId = outputBeanId;
	}

	public String getOutputBeanCode() {
		return outputBeanCode;
	}

	public void setOutputBeanCode(String outputBeanCode) {
		this.outputBeanCode = outputBeanCode;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Long getBusinessLogicId() {
		return businessLogicId;
	}

	public void setBusinessLogicId(Long businessLogicId) {
		this.businessLogicId = businessLogicId;
	}

	public String getParentOutputBeanId() {
		return parentOutputBeanId;
	}

	public void setParentOutputBeanId(String parentOutputBeanId) {
		this.parentOutputBeanId = parentOutputBeanId;
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

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	public String getMessageStringAutocomplete() {
		return messageStringAutocomplete;
	}

	public void setMessageStringAutocomplete(String messageStringAutocomplete) {
		this.messageStringAutocomplete = messageStringAutocomplete;
	}

	public boolean isCreatedMessageFlg() {
		return createdMessageFlg;
	}

	public void setCreatedMessageFlg(boolean createdMessageFlg) {
		this.createdMessageFlg = createdMessageFlg;
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

	public Integer getGroupBaseTypeId() {
		return groupBaseTypeId;
	}

	public void setGroupBaseTypeId(Integer groupType) {
		this.groupBaseTypeId = groupType;
	}

	public Integer getImpactStatus() {
		return impactStatus;
	}

	public void setImpactStatus(Integer impactStatus) {
		this.impactStatus = impactStatus;
	}

	public Long getScreenItemId() {
		return screenItemId;
	}

	public void setScreenItemId(Long screenItemId) {
		this.screenItemId = screenItemId;
	}

	public String getOutputBeanName() {
		return outputBeanName;
	}

	public void setOutputBeanName(String outputBeanName) {
		this.outputBeanName = outputBeanName;
	}

	public String getScreenItemIdAutocomplete() {
		return screenItemIdAutocomplete;
	}

	public void setScreenItemIdAutocomplete(String screenItemIdAutocomplete) {
		this.screenItemIdAutocomplete = screenItemIdAutocomplete;
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

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public Boolean getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Boolean arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	/**
	 * @return the singleList
	 */
	public List<OutputBean> getSingleList() {
		return singleList;
	}

	/**
	 * @param singleList the singleList to set
	 */
	public void setSingleList(List<OutputBean> singleList) {
		this.singleList = singleList;
	}

	/**
	 * @return the objectList
	 */
	public List<OutputBean> getObjectList() {
		return objectList;
	}

	/**
	 * @param objectList the objectList to set
	 */
	public void setObjectList(List<OutputBean> objectList) {
		this.objectList = objectList;
	}

	public String getParentOutputBeanName() {
		return parentOutputBeanName;
	}

	public void setParentOutputBeanName(String parentOutputBeanName) {
		this.parentOutputBeanName = parentOutputBeanName;
	}

	public String getParentOutputBeanCode() {
		return parentOutputBeanCode;
	}

	public void setParentOutputBeanCode(String parentOutputBeanCode) {
		this.parentOutputBeanCode = parentOutputBeanCode;
	}

	public String getGrandParentOutputBeanId() {
		return grandParentOutputBeanId;
	}

	public void setGrandParentOutputBeanId(String grandParentOutputBeanId) {
		this.grandParentOutputBeanId = grandParentOutputBeanId;
	}

	public String getGrandParentOutputBeanName() {
		return grandParentOutputBeanName;
	}

	public void setGrandParentOutputBeanName(String grandParentOutputBeanName) {
		this.grandParentOutputBeanName = grandParentOutputBeanName;
	}

	public String getGrandParentOutputBeanCode() {
		return grandParentOutputBeanCode;
	}

	public void setGrandParentOutputBeanCode(String grandParentOutputBeanCode) {
		this.grandParentOutputBeanCode = grandParentOutputBeanCode;
	}

	public String getParentOutputBeanArrayFlag() {
		return parentOutputBeanArrayFlag;
	}

	public void setParentOutputBeanArrayFlag(String parentOutputBeanArrayFlag) {
		this.parentOutputBeanArrayFlag = parentOutputBeanArrayFlag;
	}

	public String getGrandParentOutputBeanArrayFlag() {
		return grandParentOutputBeanArrayFlag;
	}

	public void setGrandParentOutputBeanArrayFlag(
			String grandParentOutputBeanArrayFlag) {
		this.grandParentOutputBeanArrayFlag = grandParentOutputBeanArrayFlag;
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

	public Boolean getFlagTotalCount() {
	    return flagTotalCount;
    }

	public void setFlagTotalCount(Boolean flagTotalCount) {
	    this.flagTotalCount = flagTotalCount;
    }

	public List<ScreenItemOutput> getLstScreenItemMapping() {
		return lstScreenItemMapping;
	}

	public void setLstScreenItemMapping(List<ScreenItemOutput> lstScreenItemMapping) {
		this.lstScreenItemMapping = lstScreenItemMapping;
	}

	public Integer getMappingType() {
		return mappingType;
	}

	public void setMappingType(Integer mappingType) {
		this.mappingType = mappingType;
	}

	public Integer getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(Integer enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public Integer getBaseType() {
		return baseType;
	}

	public void setBaseType(Integer baseType) {
		this.baseType = baseType;
	}

	public Integer getDesignType() {
		return designType;
	}

	public void setDesignType(Integer designType) {
		this.designType = designType;
	}

	public String getSessionManagementCode() {
		return sessionManagementCode;
	}

	public void setSessionManagementCode(String sessionManagementCode) {
		this.sessionManagementCode = sessionManagementCode;
	}

	public String getLogicalDataType() {
		return logicalDataType;
	}

	public void setLogicalDataType(String logicalDataType) {
		this.logicalDataType = logicalDataType;
	}

	public MessageDesign getMessageDesign() {
		return messageDesign;
	}

	public void setMessageDesign(MessageDesign messageDesign) {
		this.messageDesign = messageDesign;
	}

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

	public Integer getOutputBeanType() {
	    return outputBeanType;
    }

	public void setOutputBeanType(Integer outputBeanType) {
	    this.outputBeanType = outputBeanType;
    }

	public String getBusinessLogicCode() {
		return businessLogicCode;
	}

	public void setBusinessLogicCode(String businessLogicCode) {
		this.businessLogicCode = businessLogicCode;
	}

	public String getItemCodeMapping() {
		return itemCodeMapping;
	}

	public void setItemCodeMapping(String itemCodeMapping) {
		this.itemCodeMapping = itemCodeMapping;
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

	public String getAreaCodeMapping() {
		return areaCodeMapping;
	}

	public void setAreaCodeMapping(String areaCodeMapping) {
		this.areaCodeMapping = areaCodeMapping;
	}

	public String getFormCodeMapping() {
		return formCodeMapping;
	}

	public void setFormCodeMapping(String formCodeMapping) {
		this.formCodeMapping = formCodeMapping;
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

	public List<OutputBean> getLstChildrens() {
	    return lstChildrens;
    }

	public void setLstChildrens(List<OutputBean> lstChildrens) {
	    this.lstChildrens = lstChildrens;
    }

	
}
