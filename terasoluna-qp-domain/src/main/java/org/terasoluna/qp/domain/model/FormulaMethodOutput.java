package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class FormulaMethodOutput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long formulaMethodOutputId;
	
	private Long formulaDetailId;
	
	private Long methodOutputId;
	
	private FunctionMethodOutput functionMethodOutput;
	
	private Integer dataType;
	
	private Boolean arrayFlg;
	
	private String methodOutputCode;

	public Long getFormulaMethodOutputId() {
		return formulaMethodOutputId;
	}

	public void setFormulaMethodOutputId(Long formulaMethodOutputId) {
		this.formulaMethodOutputId = formulaMethodOutputId;
	}

	public Long getFormulaDetailId() {
		return formulaDetailId;
	}

	public void setFormulaDetailId(Long formulaDetailId) {
		this.formulaDetailId = formulaDetailId;
	}

	public Long getMethodOutputId() {
		return methodOutputId;
	}

	public void setMethodOutputId(Long methodOutputId) {
		this.methodOutputId = methodOutputId;
	}

	public FunctionMethodOutput getFunctionMethodOutput() {
		return functionMethodOutput;
	}

	public void setFunctionMethodOutput(FunctionMethodOutput functionMethodOutput) {
		this.functionMethodOutput = functionMethodOutput;
	}

	public Integer getDataType() {
	    return dataType;
    }

	public void setDataType(Integer dataType) {
	    this.dataType = dataType;
    }

	public Boolean getArrayFlg() {
	    return arrayFlg;
    }

	public void setArrayFlg(Boolean arrayFlg) {
	    this.arrayFlg = arrayFlg;
    }

	public String getMethodOutputCode() {
	    return methodOutputCode;
    }

	public void setMethodOutputCode(String methodOutputCode) {
	    this.methodOutputCode = methodOutputCode;
    }
	
	
}
