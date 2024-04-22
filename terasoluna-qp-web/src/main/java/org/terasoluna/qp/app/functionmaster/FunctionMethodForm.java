package org.terasoluna.qp.app.functionmaster;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.terasoluna.qp.app.common.validation.QpCodePattern;
import org.terasoluna.qp.app.common.validation.QpCodeSize;
import org.terasoluna.qp.app.common.validation.QpNamePattern;
import org.terasoluna.qp.app.common.validation.QpNameSize;
import org.terasoluna.qp.app.message.FunctionMasterMessageConst;

public class FunctionMethodForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long functionMethodId;
	private Long functionMasterId;
	
	@NotEmpty(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0028)
	@QpNameSize(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0028)
	@QpNamePattern(message= FunctionMasterMessageConst.SC_FUNCTIONMASTER_0028)
	private String functionMethodCode;
	
	@NotEmpty(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0027)
	@QpCodeSize(message = FunctionMasterMessageConst.SC_FUNCTIONMASTER_0027)
	@QpCodePattern(message= FunctionMasterMessageConst.SC_FUNCTIONMASTER_0027)
	private String functionMethodName;
	
	private String remark;
	private Integer itemSeqNo;

    private String tableIndex;

    private String groupId;
	
	private List<FunctionMethodInputForm> functionMethodInput = new ArrayList<FunctionMethodInputForm>();
	
	private List<FunctionMethodOutputForm> functionMethodOutput = new ArrayList<FunctionMethodOutputForm>();
	
	public Long getFunctionMethodId() {
		return functionMethodId;
	}
	public void setFunctionMethodId(Long functionMethodId) {
		this.functionMethodId = functionMethodId;
	}
	public Long getFunctionMasterId() {
		return functionMasterId;
	}
	public void setFunctionMasterId(Long functionMasterId) {
		this.functionMasterId = functionMasterId;
	}
	public String getFunctionMethodCode() {
		return functionMethodCode;
	}
	public void setFunctionMethodCode(String functionMethodCode) {
		this.functionMethodCode = functionMethodCode;
	}
	public String getFunctionMethodName() {
		return functionMethodName;
	}
	public void setFunctionMethodName(String functionMethodName) {
		this.functionMethodName = functionMethodName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<FunctionMethodInputForm> getFunctionMethodInput() {
		return functionMethodInput;
	}
	public void setFunctionMethodInput(List<FunctionMethodInputForm> functionMethodInput) {
		this.functionMethodInput = functionMethodInput;
	}
	public List<FunctionMethodOutputForm> getFunctionMethodOutput() {
		return functionMethodOutput;
	}
	public void setFunctionMethodOutput(List<FunctionMethodOutputForm> functionMethodOutput) {
		this.functionMethodOutput = functionMethodOutput;
	}
    /**
     * @return the itemSeqNo
     */
    public Integer getItemSeqNo() {
        return itemSeqNo;
    }
    /**
     * @param itemSeqNo the itemSeqNo to set
     */
    public void setItemSeqNo(Integer itemSeqNo) {
        this.itemSeqNo = itemSeqNo;
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
