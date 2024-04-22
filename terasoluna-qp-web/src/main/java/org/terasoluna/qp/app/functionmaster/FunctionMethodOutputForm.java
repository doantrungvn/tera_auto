package org.terasoluna.qp.app.functionmaster;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.message.FunctionMasterMessageConst;

public class FunctionMethodOutputForm implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String methodOutputId;
    
    @NotEmpty(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0021)
	@QpNameSize(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0021)
	@QpNamePattern(message= FunctionMasterMessageConst.SC_FUNCTIONMASTER_0021)
    private String methodOutputName;
    
    @NotEmpty(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0022)
	@QpCodeSize(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0022)
	@QpCodePattern(message= FunctionMasterMessageConst.SC_FUNCTIONMASTER_0022)
    private String methodOutputCode;
    
    @NotEmpty(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0023)
    private String dataType;

	private boolean arrayFlg;
    
    private boolean arrayFlgDisplay;
    
    private Integer itemSeqNo;
    
    private Long functionMethodId;
    
    private String parentFunctionMethodOutputId;
    
    private Integer objectType;
    
    private Long objectId;
    
    private Boolean objectFlg = true;

    private String tableIndex;

    private String groupId;

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

	public boolean isArrayFlg() {
		return arrayFlg;
	}
	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
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

	public Long getFunctionMethodId() {
		return functionMethodId;
	}

	public void setFunctionMethodId(Long functionMethodId) {
		this.functionMethodId = functionMethodId;
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
