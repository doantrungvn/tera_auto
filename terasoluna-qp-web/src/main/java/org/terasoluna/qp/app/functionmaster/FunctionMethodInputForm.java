package org.terasoluna.qp.app.functionmaster;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.message.FunctionMasterMessageConst;

public class FunctionMethodInputForm implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private String methodInputId;
    
    @NotEmpty(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0018)
	@QpNameSize(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0018)
	@QpNamePattern(message= FunctionMasterMessageConst.SC_FUNCTIONMASTER_0018)
    private String methodInputName;
    
    @NotEmpty(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0019)
	@QpCodeSize(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0019)
	@QpCodePattern(message= FunctionMasterMessageConst.SC_FUNCTIONMASTER_0019)
    private String methodInputCode;
    
    @NotEmpty(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0020)
    private String dataType;
    
    private boolean arrayFlg;
    
    private boolean arrayFlgDisplay;
    
    private Integer itemSeqNo;
    
    private int arrIndexIn;
    
    private Long functionMethodId;
    
    private String parentFunctionMethodInputId;
    
    private Integer objectType;
    
    private Long objectId;
    
    private Boolean objectFlg = true;

    private String tableIndex;

    private String groupId;

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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public boolean isArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(boolean arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public boolean isArrayFlgDisplay() {
		return arrayFlgDisplay;
	}

	public void setArrayFlgDisplay(boolean arrayFlgDisplay) {
		this.arrayFlgDisplay = arrayFlgDisplay;
	}

	public Integer getItemSeqNo() {
		return itemSeqNo;
	}

	public void setItemSeqNo(Integer itemSeqNo) {
		this.itemSeqNo = itemSeqNo;
	}

	public int getArrIndexIn() {
		return arrIndexIn;
	}

	public void setArrIndexIn(int arrIndexIn) {
		this.arrIndexIn = arrIndexIn;
	}

	public Long getFunctionMethodId() {
		return functionMethodId;
	}

	public void setFunctionMethodId(Long functionMethodId) {
		this.functionMethodId = functionMethodId;
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

}
