package org.terasoluna.qp.domain.repository.functionmaster;

import java.io.Serializable;

public class FunctionMasterCriteria implements Serializable  {

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    private String functionMasterName;
	
	private String functionMasterCode;
	
	private Long projectId;

	private int[] functionMasterType = new int[0];

	public String getFunctionMasterName() {
		return functionMasterName;
	}

	public void setFunctionMasterName(String functionMasterName) {
		this.functionMasterName = functionMasterName;
	}

	public String getFunctionMasterCode() {
		return functionMasterCode;
	}

	public void setFunctionMasterCode(String functionMasterCode) {
		this.functionMasterCode = functionMasterCode;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public int[] getFunctionMasterType() {
		return functionMasterType;
	}

	public void setFunctionMasterType(int[] functionMasterType) {
		this.functionMasterType = functionMasterType;
	}
	
	

}
