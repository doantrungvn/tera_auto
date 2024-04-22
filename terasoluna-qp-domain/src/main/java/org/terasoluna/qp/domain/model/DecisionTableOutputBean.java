package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class DecisionTableOutputBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String decisionOutputBeanId;
	
	private String parentDecisionOutputBeanId;
	
	private String decisionOutputBeanCode;
	
	private String decisionOutputBeanName;
	
	private Integer dataType;
	
	private String decisionTbId;
	
	private String itemAutocomplete;
	
	/** Sort order */
	private Integer itemSequenceNo;
	
	private boolean createdMessageFlg = false;
	
	private String tableIndex;
	
	private String groupId;
	
	private List<DecisionTableOutputBean> singleList;
	
	private List<DecisionTableOutputBean> objectList;
    
    private Integer objectType;
    
    private Long objectId;
    
    private Boolean objectFlg = true;

    /* For generate source code */
    private String commonObjDefiCode;
    
    private String externalObjDefiCode;
    
    private String packageNameObjExt;
    
	private String decisionTableCode;
	
	private Long moduleId;
	
	private String moduleCode;
	
	private List<DecisionTableOutputBean> lstChildrens;
	
	/**
	 * @return the decisionOutputBeanId
	 */
	public String getDecisionOutputBeanId() {
		return decisionOutputBeanId;
	}

	/**
	 * @param decisionOutputBeanId the decisionOutputBeanId to set
	 */
	public void setDecisionOutputBeanId(String decisionOutputBeanId) {
		this.decisionOutputBeanId = decisionOutputBeanId;
	}

	/**
	 * @return the parentDecisionOutputBeanId
	 */
	public String getParentDecisionOutputBeanId() {
		return parentDecisionOutputBeanId;
	}

	/**
	 * @param parentDecisionOutputBeanId the parentDecisionOutputBeanId to set
	 */
	public void setParentDecisionOutputBeanId(String parentDecisionOutputBeanId) {
		this.parentDecisionOutputBeanId = parentDecisionOutputBeanId;
	}

	/**
	 * @return the decisionOutputBeanCode
	 */
	public String getDecisionOutputBeanCode() {
		return decisionOutputBeanCode;
	}

	/**
	 * @param decisionOutputBeanCode the decisionOutputBeanCode to set
	 */
	public void setDecisionOutputBeanCode(String decisionOutputBeanCode) {
		this.decisionOutputBeanCode = decisionOutputBeanCode;
	}

	/**
	 * @return the decisionOutputBeanName
	 */
	public String getDecisionOutputBeanName() {
		return decisionOutputBeanName;
	}

	/**
	 * @param decisionOutputBeanName the decisionOutputBeanName to set
	 */
	public void setDecisionOutputBeanName(String decisionOutputBeanName) {
		this.decisionOutputBeanName = decisionOutputBeanName;
	}

	/**
	 * @return the dataType
	 */
	public Integer getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the decisionTbId
	 */
	public String getDecisionTbId() {
		return decisionTbId;
	}

	/**
	 * @param decisionTbId the decisionTbId to set
	 */
	public void setDecisionTbId(String decisionTbId) {
		this.decisionTbId = decisionTbId;
	}

	/**
	 * @return the itemAutocomplete
	 */
	public String getItemAutocomplete() {
		return itemAutocomplete;
	}

	/**
	 * @param itemAutocomplete the itemAutocomplete to set
	 */
	public void setItemAutocomplete(String itemAutocomplete) {
		this.itemAutocomplete = itemAutocomplete;
	}

	/**
	 * @return the createdMessageFlg
	 */
	public boolean isCreatedMessageFlg() {
		return createdMessageFlg;
	}

	/**
	 * @param createdMessageFlg the createdMessageFlg to set
	 */
	public void setCreatedMessageFlg(boolean createdMessageFlg) {
		this.createdMessageFlg = createdMessageFlg;
	}

	/**
	 * @return the itemSequenceNo
	 */
	public Integer getItemSequenceNo() {
		return itemSequenceNo;
	}

	/**
	 * @param itemSequenceNo the itemSequenceNo to set
	 */
	public void setItemSequenceNo(Integer itemSequenceNo) {
		this.itemSequenceNo = itemSequenceNo;
	}

	/**
	 * @return the tableIndex
	 */
	public String getTableIndex() {
		return tableIndex;
	}

	/**
	 * @param tableIndex the tableIndex to set
	 */
	public void setTableIndex(String tableIndex) {
		this.tableIndex = tableIndex;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the singleList
	 */
	public List<DecisionTableOutputBean> getSingleList() {
		return singleList;
	}

	/**
	 * @param singleList the singleList to set
	 */
	public void setSingleList(List<DecisionTableOutputBean> singleList) {
		this.singleList = singleList;
	}

	/**
	 * @return the objectList
	 */
	public List<DecisionTableOutputBean> getObjectList() {
		return objectList;
	}

	/**
	 * @param objectList the objectList to set
	 */
	public void setObjectList(List<DecisionTableOutputBean> objectList) {
		this.objectList = objectList;
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
	 * @return the packageNameObjExt
	 */
	public String getPackageNameObjExt() {
		return packageNameObjExt;
	}

	/**
	 * @param packageNameObjExt the packageNameObjExt to set
	 */
	public void setPackageNameObjExt(String packageNameObjExt) {
		this.packageNameObjExt = packageNameObjExt;
	}

	public String getDecisionTableCode() {
	    return decisionTableCode;
    }

	public void setDecisionTableCode(String decisionTableCode) {
	    this.decisionTableCode = decisionTableCode;
    }

	public Long getModuleId() {
	    return moduleId;
    }

	public void setModuleId(Long moduleId) {
	    this.moduleId = moduleId;
    }

	public List<DecisionTableOutputBean> getLstChildrens() {
	    return lstChildrens;
    }

	public void setLstChildrens(List<DecisionTableOutputBean> lstChildrens) {
	    this.lstChildrens = lstChildrens;
    }

	/**
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
}
