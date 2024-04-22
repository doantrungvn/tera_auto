package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormulaMethodInput implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long formulaMethodInputId;

	private Long methodInputId;
	
	private Integer parameterType;
	
	private Integer parameterScope;
	
	private String parameterId;
	
	private String parameterValue;

	private String parameterIdAutocomplete = "";

	private List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();
	
	private Long formulaDetailId;
	
	private Long functionMethodId;
	
	private Integer dataType;
	
	private Boolean arrayFlg;

	public Long getMethodInputId() {
		return methodInputId;
	}

	public void setMethodInputId(Long methodInputId) {
		this.methodInputId = methodInputId;
	}

	public Integer getParameterScope() {
		return parameterScope;
	}

	public void setParameterScope(Integer parameterScope) {
		this.parameterScope = parameterScope;
	}

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public Long getFormulaDetailId() {
		return formulaDetailId;
	}

	public void setFormulaDetailId(Long formulaDetailId) {
		this.formulaDetailId = formulaDetailId;
	}

	public Long getFormulaMethodInputId() {
		return formulaMethodInputId;
	}

	public void setFormulaMethodInputId(Long formulaMethodInputId) {
		this.formulaMethodInputId = formulaMethodInputId;
	}

	public Long getFunctionMethodId() {
		return functionMethodId;
	}

	public void setFunctionMethodId(Long functionMethodId) {
		this.functionMethodId = functionMethodId;
	}

	public String getParameterIdAutocomplete() {
		return parameterIdAutocomplete;
	}

	public void setParameterIdAutocomplete(String parameterIdAutocomplete) {
		this.parameterIdAutocomplete = parameterIdAutocomplete;
	}

	public List<BDParameterIndex> getLstParameterIndex() {
		return lstParameterIndex;
	}

	public void setLstParameterIndex(List<BDParameterIndex> lstParameterIndex) {
		this.lstParameterIndex = lstParameterIndex;
	}

	public Integer getParameterType() {
		return parameterType;
	}

	public void setParameterType(Integer parameterType) {
		this.parameterType = parameterType;
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
	
	
}
