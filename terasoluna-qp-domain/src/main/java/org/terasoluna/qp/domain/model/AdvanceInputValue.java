package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdvanceInputValue implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long advanceInputValueId;

	private String inputBeanCode;

	private String inputBeanName;

	private Integer dataType;

	private Boolean arrayFlg = false;

	private String parameterId;

	private String parameterIdAutocomplete;

	private Integer parameterScope;

	private Long parentAdvanceInputValueId;

	private Long advanceComponentId;

	private List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();

	public Long getAdvanceInputValueId() {
		return advanceInputValueId;
	}

	public void setAdvanceInputValueId(Long advanceInputValueId) {
		this.advanceInputValueId = advanceInputValueId;
	}

	public String getInputBeanCode() {
		return inputBeanCode;
	}

	public void setInputBeanCode(String inputBeanCode) {
		this.inputBeanCode = inputBeanCode;
	}

	public String getInputBeanName() {
		return inputBeanName;
	}

	public void setInputBeanName(String inputBeanName) {
		this.inputBeanName = inputBeanName;
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

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public Integer getParameterScope() {
		return parameterScope;
	}

	public void setParameterScope(Integer parameterScope) {
		this.parameterScope = parameterScope;
	}

	public Long getParentAdvanceInputValueId() {
		return parentAdvanceInputValueId;
	}

	public void setParentAdvanceInputValueId(Long parentAdvanceInputValueId) {
		this.parentAdvanceInputValueId = parentAdvanceInputValueId;
	}

	public Long getAdvanceComponentId() {
		return advanceComponentId;
	}

	public void setAdvanceComponentId(Long advanceComponentId) {
		this.advanceComponentId = advanceComponentId;
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

}
