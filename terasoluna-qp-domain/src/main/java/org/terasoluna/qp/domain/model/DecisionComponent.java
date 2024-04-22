package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.List;

public class DecisionComponent extends BlogicComponent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long decisionComponentId;

	private Long decisionTableId;

	private String decisionTableIdAutocomplete;

	private String decisionTableCode;

	private Long decisionTableIdRefer;

	private String decisionTableCodeRefer;

	private String decisionTableNameRefer;

	private List<DecisionInputValue> parameterInputBeans;

	private List<DecisionOutputValue> parameterOutputBeans;

	private Long moduleId;

	private String moduleIdAutocomplete;

	private String strGenDecisionDetails;
	
	private Long businessLogicId;

	public Long getDecisionComponentId() {
		return decisionComponentId;
	}

	public void setDecisionComponentId(Long decisionComponentId) {
		this.decisionComponentId = decisionComponentId;
	}

	public Long getDecisionTableId() {
		return decisionTableId;
	}

	public void setDecisionTableId(Long decisionTableId) {
		this.decisionTableId = decisionTableId;
	}

	public String getDecisionTableIdAutocomplete() {
		return decisionTableIdAutocomplete;
	}

	public void setDecisionTableIdAutocomplete(String decisionTableIdAutocomplete) {
		this.decisionTableIdAutocomplete = decisionTableIdAutocomplete;
	}

	public String getDecisionTableCode() {
		return decisionTableCode;
	}

	public void setDecisionTableCode(String decisionTableCode) {
		this.decisionTableCode = decisionTableCode;
	}

	public List<DecisionInputValue> getParameterInputBeans() {
		return parameterInputBeans;
	}

	public void setParameterInputBeans(List<DecisionInputValue> parameterInputBeans) {
		this.parameterInputBeans = parameterInputBeans;
	}

	public List<DecisionOutputValue> getParameterOutputBeans() {
		return parameterOutputBeans;
	}

	public void setParameterOutputBeans(List<DecisionOutputValue> parameterOutputBeans) {
		this.parameterOutputBeans = parameterOutputBeans;
	}

	public Long getDecisionTableIdRefer() {
		return decisionTableIdRefer;
	}

	public void setDecisionTableIdRefer(Long decisionTableIdRefer) {
		this.decisionTableIdRefer = decisionTableIdRefer;
	}

	public String getDecisionTableCodeRefer() {
		return decisionTableCodeRefer;
	}

	public void setDecisionTableCodeRefer(String decisionTableCodeRefer) {
		this.decisionTableCodeRefer = decisionTableCodeRefer;
	}

	public String getDecisionTableNameRefer() {
		return decisionTableNameRefer;
	}

	public void setDecisionTableNameRefer(String decisionTableNameRefer) {
		this.decisionTableNameRefer = decisionTableNameRefer;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleIdAutocomplete() {
		return moduleIdAutocomplete;
	}

	public void setModuleIdAutocomplete(String moduleIdAutocomplete) {
		this.moduleIdAutocomplete = moduleIdAutocomplete;
	}

	/**
	 * @return the strGenDecisionDetails
	 */
	public String getStrGenDecisionDetails() {
		return strGenDecisionDetails;
	}

	/**
	 * @param strGenDecisionDetails the strGenDecisionDetails to set
	 */
	public void setStrGenDecisionDetails(String strGenDecisionDetails) {
		this.strGenDecisionDetails = strGenDecisionDetails;
	}

	public Long getBusinessLogicId() {
	    return businessLogicId;
    }

	public void setBusinessLogicId(Long businessLogicId) {
	    this.businessLogicId = businessLogicId;
    }
}
