package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class FunctionMethodInput implements Serializable {

	private static final long serialVersionUID = 1L;

	private String methodInputId;

	private String methodInputName;

	private String methodInputCode;

	private Integer dataType;

	private Integer arrayFlg;

	private boolean arrayFlgDisplay;

	private Long functionMethodId;
	
	private String functionMethodName;
	
	private String functionMasterName;
	
	private Integer itemSeqNo;
	
	private Integer oldDataType;

	private Integer oldArrayFlg;
    
    private String parentFunctionMethodInputId;
    
    private Integer objectType;
    
    private Long objectId;
    
    private Boolean objectFlg = true;

    private String tableIndex;

    private String groupId;

	private List<FunctionMethodInput> singleList;

	private List<FunctionMethodInput> objectList;

	/** In the case of object external  */
	private String packageNameObjExt;

	private String commonObjDefiCode;
	
	/** module code of common object */
	private String moduleCode;

	private String externalObjDefiCode;
	
	private String functionMethodCode;
	
	private String functionMasterCode;
	
	private Long functionMasterId;
	
	private List<FunctionMethodInput> lstChildrens;

	public String getMethodInputName() {
		return methodInputName;
	}

	public void setMethodInputName(String methodInputName) {
		this.methodInputName = methodInputName;
	}

	public String getMethodInputCode() {
		return methodInputCode;
	}

	public void setMethodInputCode(String methodInputCode) {
		this.methodInputCode = methodInputCode;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public Integer getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Integer arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public boolean isArrayFlgDisplay() {
		return arrayFlgDisplay;
	}

	public void setArrayFlgDisplay(boolean arrayFlgDisplay) {
		this.arrayFlgDisplay = arrayFlgDisplay;
	}


	public Long getFunctionMethodId() {
		return functionMethodId;
	}
	
	public void setFunctionMethodId(Long functionMethodId) {
		this.functionMethodId = functionMethodId;
	}

	public Integer getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	public String getFunctionMethodName() {
		return functionMethodName;
	}

	public void setFunctionMethodName(String functionMethodName) {
		this.functionMethodName = functionMethodName;
	}

	public String getFunctionMasterName() {
		return functionMasterName;
	}

	public void setFunctionMasterName(String functionMasterName) {
		this.functionMasterName = functionMasterName;
	}

	public Integer getOldDataType() {
		return oldDataType;
	}

	public void setOldDataType(Integer oldDataType) {
		this.oldDataType = oldDataType;
	}

	public Integer getOldArrayFlg() {
		return oldArrayFlg;
	}

	public void setOldArrayFlg(Integer oldArrayFlg) {
		this.oldArrayFlg = oldArrayFlg;
	}

    /**
     * @return the parentFunctionMethodInputId
     */
    public String getParentFunctionMethodInputId() {
        return parentFunctionMethodInputId;
    }

    /**
     * @param parentFunctionMethodInputId the parentFunctionMethodInputId to set
     */
    public void setParentFunctionMethodInputId(
            String parentFunctionMethodInputId) {
        this.parentFunctionMethodInputId = parentFunctionMethodInputId;
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
     * @return the methodInputId
     */
    public String getMethodInputId() {
        return methodInputId;
    }

    /**
     * @param methodInputId the methodInputId to set
     */
    public void setMethodInputId(String methodInputId) {
        this.methodInputId = methodInputId;
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

	public List<FunctionMethodInput> getSingleList() {
		return singleList;
	}

	public void setSingleList(List<FunctionMethodInput> singleList) {
		this.singleList = singleList;
	}

	public List<FunctionMethodInput> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<FunctionMethodInput> objectList) {
		this.objectList = objectList;
	}

	public String getPackageNameObjExt() {
		return packageNameObjExt;
	}

	public void setPackageNameObjExt(String packageNameObjExt) {
		this.packageNameObjExt = packageNameObjExt;
	}

	public String getCommonObjDefiCode() {
		return commonObjDefiCode;
	}

	public void setCommonObjDefiCode(String commonObjDefiCode) {
		this.commonObjDefiCode = commonObjDefiCode;
	}

	public String getExternalObjDefiCode() {
		return externalObjDefiCode;
	}

	public void setExternalObjDefiCode(String externalObjDefiCode) {
		this.externalObjDefiCode = externalObjDefiCode;
	}

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

	public String getFunctionMethodCode() {
	    return functionMethodCode;
    }

	public void setFunctionMethodCode(String functionMethodCode) {
	    this.functionMethodCode = functionMethodCode;
    }

	public String getFunctionMasterCode() {
	    return functionMasterCode;
    }

	public void setFunctionMasterCode(String functionMasterCode) {
	    this.functionMasterCode = functionMasterCode;
    }

	public Long getFunctionMasterId() {
	    return functionMasterId;
    }

	public void setFunctionMasterId(Long functionMasterId) {
	    this.functionMasterId = functionMasterId;
    }

	public List<FunctionMethodInput> getLstChildrens() {
	    return lstChildrens;
    }

	public void setLstChildrens(List<FunctionMethodInput> lstChildrens) {
	    this.lstChildrens = lstChildrens;
    }

}
