package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class ValidationCheckDetail implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long validationCheckDetailId;

	private String inputBeanId;

	private String inputBeanCode;

	private Integer dataType;

	private Boolean arrayFlg;

	private Integer validationType;

	private List<MessageParameter> parameters;

	public Long getValidationCheckDetailId() {
		return validationCheckDetailId;
	}

	public void setValidationCheckDetailId(Long validationCheckDetailId) {
		this.validationCheckDetailId = validationCheckDetailId;
	}

	public String getInputBeanId() {
		return inputBeanId;
	}

	public void setInputBeanId(String inputBeanId) {
		this.inputBeanId = inputBeanId;
	}

	public Integer getValidationType() {
		return validationType;
	}

	public void setValidationType(Integer validationType) {
		this.validationType = validationType;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getInputBeanCode() {
		return inputBeanCode;
	}

	public void setInputBeanCode(String inputBeanCode) {
		this.inputBeanCode = inputBeanCode;
	}

	public Boolean getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Boolean arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public List<MessageParameter> getParameters() {
	    return parameters;
    }

	public void setParameters(List<MessageParameter> parameters) {
	    this.parameters = parameters;
    }
}
