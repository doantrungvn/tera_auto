package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DecisionOutputValue implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long decisionOutputValueId;

	private Long decisionComponentId;

	private Long decisionOutputBeanId;

	private String decisionOutputBeanCode;

	private String decisionOutputBeanName;

	private Integer dataType;

	private Integer targetScope;

	private String targetId;

	private String targetIdAutocomplete;

	private String impactStatus;

	private Long decisionOutputBeanIdRefer;

	private String decisionOutputBeanCodeRefer;

	private String decisionOutputBeanNameRefer;

	private Long decisionTableIdRefer;

	private Integer dataTypeRefer;

	private List<BDParameterIndex> lstTargetIndex = new ArrayList<BDParameterIndex>();

	public Long getDecisionOutputValueId() {
		return decisionOutputValueId;
	}

	public void setDecisionOutputValueId(Long decisionOutputValueId) {
		this.decisionOutputValueId = decisionOutputValueId;
	}

	public Long getDecisionComponentId() {
		return decisionComponentId;
	}

	public void setDecisionComponentId(Long decisionComponentId) {
		this.decisionComponentId = decisionComponentId;
	}

	public Long getDecisionOutputBeanId() {
		return decisionOutputBeanId;
	}

	public void setDecisionOutputBeanId(Long decisionOutputBeanId) {
		this.decisionOutputBeanId = decisionOutputBeanId;
	}

	public String getDecisionOutputBeanCode() {
		return decisionOutputBeanCode;
	}

	public void setDecisionOutputBeanCode(String decisionOutputBeanCode) {
		this.decisionOutputBeanCode = decisionOutputBeanCode;
	}

	public String getDecisionOutputBeanName() {
		return decisionOutputBeanName;
	}

	public void setDecisionOutputBeanName(String decisionOutputBeanName) {
		this.decisionOutputBeanName = decisionOutputBeanName;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getTargetScope() {
		return targetScope;
	}

	public void setTargetScope(Integer targetScope) {
		this.targetScope = targetScope;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetIdAutocomplete() {
		return targetIdAutocomplete;
	}

	public void setTargetIdAutocomplete(String targetIdAutocomplete) {
		this.targetIdAutocomplete = targetIdAutocomplete;
	}

	public String getImpactStatus() {
		return impactStatus;
	}

	public void setImpactStatus(String impactStatus) {
		this.impactStatus = impactStatus;
	}

	public Long getDecisionOutputBeanIdRefer() {
		return decisionOutputBeanIdRefer;
	}

	public void setDecisionOutputBeanIdRefer(Long decisionOutputBeanIdRefer) {
		this.decisionOutputBeanIdRefer = decisionOutputBeanIdRefer;
	}

	public String getDecisionOutputBeanCodeRefer() {
		return decisionOutputBeanCodeRefer;
	}

	public void setDecisionOutputBeanCodeRefer(String decisionOutputBeanCodeRefer) {
		this.decisionOutputBeanCodeRefer = decisionOutputBeanCodeRefer;
	}

	public String getDecisionOutputBeanNameRefer() {
		return decisionOutputBeanNameRefer;
	}

	public void setDecisionOutputBeanNameRefer(String decisionOutputBeanNameRefer) {
		this.decisionOutputBeanNameRefer = decisionOutputBeanNameRefer;
	}

	public Long getDecisionTableIdRefer() {
		return decisionTableIdRefer;
	}

	public void setDecisionTableIdRefer(Long decisionTableIdRefer) {
		this.decisionTableIdRefer = decisionTableIdRefer;
	}

	public Integer getDataTypeRefer() {
		return dataTypeRefer;
	}

	public void setDataTypeRefer(Integer dataTypeRefer) {
		this.dataTypeRefer = dataTypeRefer;
	}

	public List<BDParameterIndex> getLstTargetIndex() {
	    return lstTargetIndex;
    }

	public void setLstTargetIndex(List<BDParameterIndex> lstTargetIndex) {
	    this.lstTargetIndex = lstTargetIndex;
    }
}
