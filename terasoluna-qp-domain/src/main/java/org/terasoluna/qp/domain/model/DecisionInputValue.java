package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DecisionInputValue implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long decisionInputValueId;

	private Long decisionComponentId;

	private Long decisionInputBeanId;

	private String decisionInputBeanCode;

	private String decisionInputBeanName;

	private Integer dataType;

	private Integer parameterScope;

	private String parameterId;

	private String parameterIdAutocomplete;

	private String impactStatus;

	private Long decisionInputBeanIdRefer;

	private String decisionInputBeanCodeRefer;

	private String decisionInputBeanNameRefer;

	private Long decisionTableIdRefer;

	private Integer dataTypeRefer;

	private List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();

	public Long getDecisionInputValueId() {
		return decisionInputValueId;
	}

	public void setDecisionInputValueId(Long decisionInputValueId) {
		this.decisionInputValueId = decisionInputValueId;
	}

	public Long getDecisionComponentId() {
		return decisionComponentId;
	}

	public void setDecisionComponentId(Long decisionComponentId) {
		this.decisionComponentId = decisionComponentId;
	}

	public Long getDecisionInputBeanId() {
		return decisionInputBeanId;
	}

	public void setDecisionInputBeanId(Long decisionInputBeanId) {
		this.decisionInputBeanId = decisionInputBeanId;
	}

	public String getDecisionInputBeanCode() {
		return decisionInputBeanCode;
	}

	public void setDecisionInputBeanCode(String decisionInputBeanCode) {
		this.decisionInputBeanCode = decisionInputBeanCode;
	}

	public String getDecisionInputBeanName() {
		return decisionInputBeanName;
	}

	public void setDecisionInputBeanName(String decisionInputBeanName) {
		this.decisionInputBeanName = decisionInputBeanName;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
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

	public String getParameterIdAutocomplete() {
		return parameterIdAutocomplete;
	}

	public void setParameterIdAutocomplete(String parameterIdAutocomplete) {
		this.parameterIdAutocomplete = parameterIdAutocomplete;
	}

	public String getImpactStatus() {
		return impactStatus;
	}

	public void setImpactStatus(String impactStatus) {
		this.impactStatus = impactStatus;
	}

	public String getDecisionInputBeanCodeRefer() {
		return decisionInputBeanCodeRefer;
	}

	public void setDecisionInputBeanCodeRefer(String decisionInputBeanCodeRefer) {
		this.decisionInputBeanCodeRefer = decisionInputBeanCodeRefer;
	}

	public String getDecisionInputBeanNameRefer() {
		return decisionInputBeanNameRefer;
	}

	public void setDecisionInputBeanNameRefer(String decisionInputBeanNameRefer) {
		this.decisionInputBeanNameRefer = decisionInputBeanNameRefer;
	}

	public Integer getDataTypeRefer() {
		return dataTypeRefer;
	}

	public void setDataTypeRefer(Integer dataTypeRefer) {
		this.dataTypeRefer = dataTypeRefer;
	}

	public Long getDecisionInputBeanIdRefer() {
		return decisionInputBeanIdRefer;
	}

	public void setDecisionInputBeanIdRefer(Long decisionInputBeanIdRefer) {
		this.decisionInputBeanIdRefer = decisionInputBeanIdRefer;
	}

	public Long getDecisionTableIdRefer() {
		return decisionTableIdRefer;
	}

	public void setDecisionTableIdRefer(Long decisionTableIdRefer) {
		this.decisionTableIdRefer = decisionTableIdRefer;
	}

	public List<BDParameterIndex> getLstParameterIndex() {
	    return lstParameterIndex;
    }

	public void setLstParameterIndex(List<BDParameterIndex> lstParameterIndex) {
	    this.lstParameterIndex = lstParameterIndex;
    }
}
