package org.terasoluna.qp.app.functiondesign;

import java.io.Serializable;

public class FunctionDesignSearchForm implements Serializable{
		
	private static final long serialVersionUID = 1L;
	
	private Long functionId;
    
    private String functionName;
	
	private String functionCode;
	
	private Integer moduleId;
	
	private String moduleIdAutocomplete;
	
	private int[] functionType;
	
	private String actor;

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

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

}
