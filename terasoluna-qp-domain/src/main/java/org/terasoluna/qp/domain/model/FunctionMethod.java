/**
 * 
 */
package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author datld
 */
public class FunctionMethod implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long functionMethodId;
	
	private Long functionMasterId;
	
	private String functionMasterName;
	
	private String functionMethodCode;
	
	private String functionMethodName;
	
	private String remark;
	
	private List<FunctionMethodInput> functionMethodInput = new ArrayList<FunctionMethodInput>();
	
	private List<FunctionMethodOutput> functionMethodOutput = new ArrayList<FunctionMethodOutput>();
	
	private String functionMasterCode;

	private String tableIndex;

	private String groupId;

	private Integer itemSeqNo;

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

	public List<FunctionMethodInput> getFunctionMethodInput() {
		return functionMethodInput;
	}

	public void setFunctionMethodInput(List<FunctionMethodInput> functionMethodInput) {
		this.functionMethodInput = functionMethodInput;
	}

	public List<FunctionMethodOutput> getFunctionMethodOutput() {
		return functionMethodOutput;
	}

	public void setFunctionMethodOutput(List<FunctionMethodOutput> functionMethodOutput) {
		this.functionMethodOutput = functionMethodOutput;
	}

	public String getFunctionMasterName() {
		return functionMasterName;
	}

	public void setFunctionMasterName(String functionMasterName) {
		this.functionMasterName = functionMasterName;
	}

	/**
	 * @return the tableIndex
	 */
	public String getTableIndex() {
		return tableIndex;
	}

	/**
	 * @param tableIndex
	 *            the tableIndex to set
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
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	public String getFunctionMasterCode() {
	    return functionMasterCode;
    }

	public void setFunctionMasterCode(String functionMasterCode) {
	    this.functionMasterCode = functionMasterCode;
    }
}
