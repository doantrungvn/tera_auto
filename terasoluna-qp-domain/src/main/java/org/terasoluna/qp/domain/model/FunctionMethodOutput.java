package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class FunctionMethodOutput implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String methodOutputId;

	private String methodOutputName;

	private String methodOutputCode;

	private Integer dataType;

	private Integer arrayFlg;
	
	private boolean arrayFlgDisplay;
	
	private Long functionMethodId;
	
	private String functionMethodName;
	
	private String functionMasterName;

	private Integer itemSeqNo;
	
	private Integer oldDataType;

	private Integer oldArrayFlg;
    
    private String parentFunctionMethodOutputId;
    
    private Integer objectType;
    
    private Long objectId;
    
    private Boolean objectFlg = true;

    private String tableIndex;

    private String groupId;

	private List<FunctionMethodOutput> singleList;

	private List<FunctionMethodOutput> objectList;

	private String commonObjDefiCode;
	
	/** module code of common object */
	private String moduleCode;
	
	private String externalObjDefiCode;
    
    /** In the case of object external  */
    private String packageNameObjExt;
    
    private String functionMethodCode;
	
	private String functionMasterCode;
	
	private Long functionMasterId;

	private List<FunctionMethodOutput> lstChildrens;
	
	public String getMethodOutputName() {
		return methodOutputName;
	}

	public void setMethodOutputName(String methodOutputName) {
		this.methodOutputName = methodOutputName;
	}

	public String getMethodOutputCode() {
		return methodOutputCode;
	}

	public void setMethodOutputCode(String methodOutputCode) {
		this.methodOutputCode = methodOutputCode;
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

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
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
     * @return the methodOutputId
     */
    public String getMethodOutputId() {
        return methodOutputId;
    }

    /**
     * @param methodOutputId the methodOutputId to set
     */
    public void setMethodOutputId(String methodOutputId) {
        this.methodOutputId = methodOutputId;
    }

    /**
     * @return the parentFunctionMethodOutputId
     */
    public String getParentFunctionMethodOutputId() {
        return parentFunctionMethodOutputId;
    }

    /**
     * @param parentFunctionMethodOutputId the parentFunctionMethodOutputId to set
     */
    public void setParentFunctionMethodOutputId(
            String parentFunctionMethodOutputId) {
        this.parentFunctionMethodOutputId = parentFunctionMethodOutputId;
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

	public List<FunctionMethodOutput> getSingleList() {
		return singleList;
	}

	public void setSingleList(List<FunctionMethodOutput> singleList) {
		this.singleList = singleList;
	}

	public List<FunctionMethodOutput> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<FunctionMethodOutput> objectList) {
		this.objectList = objectList;
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

	public String getPackageNameObjExt() {
		return packageNameObjExt;
	}

	public void setPackageNameObjExt(String packageNameObjExt) {
		this.packageNameObjExt = packageNameObjExt;
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

	public List<FunctionMethodOutput> getLstChildrens() {
	    return lstChildrens;
    }

	public void setLstChildrens(List<FunctionMethodOutput> lstChildrens) {
	    this.lstChildrens = lstChildrens;
    }
}
