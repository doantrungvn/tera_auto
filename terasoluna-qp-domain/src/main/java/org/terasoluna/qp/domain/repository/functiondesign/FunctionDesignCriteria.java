package org.terasoluna.qp.domain.repository.functiondesign;

import java.io.Serializable;

public class FunctionDesignCriteria implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
    private String functionName;
	
	private String functionCode;
	
	private Long moduleId;
	
	private int[] functionType;
	
	private Long projectId;

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public int[] getFunctionType() {
		return functionType;
	}

	public void setFunctionType(int[] functionType) {
		this.functionType = functionType;
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
	
}
