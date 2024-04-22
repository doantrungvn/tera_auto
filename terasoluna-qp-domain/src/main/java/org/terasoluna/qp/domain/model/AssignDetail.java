package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AssignDetail implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long assignDetailId;

	private Integer assignType;

	private Integer parameterScope;

	private String parameterId;

	private String parameterIdAutocomplete;

	private Integer targetScope;

	private String targetId;

	private String targetIdAutocomplete;

	private Long formulaDefinitionId;

	private String formulaDefinitionContent;

	private List<FormulaDetail> formulaDefinitionDetails = new ArrayList<FormulaDetail>();

	private Long assignComponentId;

	private String dataGroup;

	private Integer dataType;

	private List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();

	private List<BDParameterIndex> lstTargetIndex = new ArrayList<BDParameterIndex>();

	public Long getAssignDetailId() {
		return assignDetailId;
	}

	public void setAssignDetailId(Long assignDetailId) {
		this.assignDetailId = assignDetailId;
	}

	public Integer getAssignType() {
		return assignType;
	}

	public void setAssignType(Integer assignType) {
		this.assignType = assignType;
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

	public Long getFormulaDefinitionId() {
		return formulaDefinitionId;
	}

	public void setFormulaDefinitionId(Long formulaDefinitionId) {
		this.formulaDefinitionId = formulaDefinitionId;
	}

	public String getFormulaDefinitionContent() {
		return formulaDefinitionContent;
	}

	public void setFormulaDefinitionContent(String formulaDefinitionContent) {
		this.formulaDefinitionContent = formulaDefinitionContent;
	}

	public Long getAssignComponentId() {
		return assignComponentId;
	}

	public void setAssignComponentId(Long assignComponentId) {
		this.assignComponentId = assignComponentId;
	}

	public String getParameterIdAutocomplete() {
		return parameterIdAutocomplete;
	}

	public void setParameterIdAutocomplete(String parameterIdAutocomplete) {
		this.parameterIdAutocomplete = parameterIdAutocomplete;
	}

	public String getTargetIdAutocomplete() {
		return targetIdAutocomplete;
	}

	public void setTargetIdAutocomplete(String targetIdAutocomplete) {
		this.targetIdAutocomplete = targetIdAutocomplete;
	}

	public String getDataGroup() {
		return dataGroup;
	}

	public void setDataGroup(String dataGroup) {
		this.dataGroup = dataGroup;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public List<FormulaDetail> getFormulaDefinitionDetails() {
		return formulaDefinitionDetails;
	}

	public void setFormulaDefinitionDetails(List<FormulaDetail> formulaDefinitionDetails) {
		this.formulaDefinitionDetails = formulaDefinitionDetails;
	}

	public List<BDParameterIndex> getLstParameterIndex() {
		return lstParameterIndex;
	}

	public void setLstParameterIndex(List<BDParameterIndex> lstParameterIndex) {
		this.lstParameterIndex = lstParameterIndex;
	}

	public List<BDParameterIndex> getLstTargetIndex() {
		return lstTargetIndex;
	}

	public void setLstTargetIndex(List<BDParameterIndex> lstTargetIndex) {
		this.lstTargetIndex = lstTargetIndex;
	}

}
