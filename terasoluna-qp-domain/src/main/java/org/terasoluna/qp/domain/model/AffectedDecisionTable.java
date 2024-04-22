package org.terasoluna.qp.domain.model;

public class AffectedDecisionTable extends DecisionTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long functionMethodId;
	
	private String functionMethodName;
	
	private Long methodInputId;
	
	private String methodInputName;
	
	private Long methodOutputId;
	
	private String methodOutputName;

	public Long getFunctionMethodId() {
		return functionMethodId;
	}

	public void setFunctionMethodId(Long functionMethodId) {
		this.functionMethodId = functionMethodId;
	}

	public String getFunctionMethodName() {
		return functionMethodName;
	}

	public void setFunctionMethodName(String functionMethodName) {
		this.functionMethodName = functionMethodName;
	}

	public Long getMethodInputId() {
		return methodInputId;
	}

	public void setMethodInputId(Long methodInputId) {
		this.methodInputId = methodInputId;
	}

	public String getMethodInputName() {
		return methodInputName;
	}

	public void setMethodInputName(String methodInputName) {
		this.methodInputName = methodInputName;
	}

	public Long getMethodOutputId() {
		return methodOutputId;
	}

	public void setMethodOutputId(Long methodOutputId) {
		this.methodOutputId = methodOutputId;
	}

	public String getMethodOutputName() {
		return methodOutputName;
	}

	public void setMethodOutputName(String methodOutputName) {
		this.methodOutputName = methodOutputName;
	}
}
