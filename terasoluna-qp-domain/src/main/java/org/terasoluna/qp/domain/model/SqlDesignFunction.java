package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class SqlDesignFunction implements Serializable {
	
	private static final long serialVersionUID = 1558272554982872858L;
	private Long functionId;
	private String functionName;
	private String dialect;
	private String functionCode;
	private String functionText;
	private String remark;
	private String groupCode;
	public Long getFunctionId() {
		return functionId;
	}
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getDialect() {
		return dialect;
	}
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public String getFunctionText() {
		return functionText;
	}
	public void setFunctionText(String functionText) {
		this.functionText = functionText;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
}
