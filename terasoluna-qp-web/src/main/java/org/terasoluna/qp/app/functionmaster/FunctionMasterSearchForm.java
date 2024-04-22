package org.terasoluna.qp.app.functionmaster;

import java.io.Serializable;

public class FunctionMasterSearchForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String functionMasterName;
	
	private String functionMasterCode;
	
	private Long projectId;

	private int[] functionMasterType = new int[0];

	/**
	 * @return the functionMasterName
	 */
	public String getFunctionMasterName() {
		return functionMasterName;
	}

	/**
	 * @param functionMasterName the functionMasterName to set
	 */
	public void setFunctionMasterName(String functionMasterName) {
		this.functionMasterName = functionMasterName;
	}

	/**
	 * @return the functionMasterCode
	 */
	public String getFunctionMasterCode() {
		return functionMasterCode;
	}

	/**
	 * @param functionMasterCode the functionMasterCode to set
	 */
	public void setFunctionMasterCode(String functionMasterCode) {
		this.functionMasterCode = functionMasterCode;
	}

	/**
	 * @return the projectId
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return the functionMasterType
	 */
	public int[] getFunctionMasterType() {
		return functionMasterType;
	}

	/**
	 * @param functionMasterType the functionMasterType to set
	 */
	public void setFunctionMasterType(int[] functionMasterType) {
		this.functionMasterType = functionMasterType;
	}
	
}
