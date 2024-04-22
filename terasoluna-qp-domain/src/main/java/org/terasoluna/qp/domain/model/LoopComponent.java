package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.terasoluna.qp.app.common.ultils.DataTypeUtils;

public class LoopComponent extends BlogicComponent implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long loopComponentId;

	private Integer loopType;

	private Integer parameterScope;

	private String parameterId;

	private String parameterIdAutocomplete;

	private Integer fromScope;

	private String fromValue;

	private String fromValueAutocomplete = "";

	private List<BDParameterIndex> lstFromIndex = new ArrayList<BDParameterIndex>();

	private Integer toScope;

	private String toValue;
	
	private String toValueAutocomplete = "";

	private List<BDParameterIndex> lstToIndex = new ArrayList<BDParameterIndex>();

	private Long formulaDefinitionId;

	private String formulaDefinitionContent;

	private List<FormulaDetail> formulaDefinitionDetails = new ArrayList<FormulaDetail>();

	private String index;

	private Integer loopStepType = 0;

	private Integer loopStepValue;

	public Long getLoopComponentId() {
		return loopComponentId;
	}

	public void setLoopComponentId(Long loopComponentId) {
		this.loopComponentId = loopComponentId;
	}

	public Integer getLoopType() {
		return loopType;
	}

	public void setLoopType(Integer loopType) {
		this.loopType = loopType;
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

	public Integer getFromScope() {
		return fromScope;
	}

	public void setFromScope(Integer fromScope) {
		this.fromScope = fromScope;
	}

	public String getFromValue() {
		return fromValue;
	}

	public void setFromValue(String fromValue) {
		this.fromValue = fromValue;
	}

	public String getToValue() {
		return toValue;
	}

	public void setToValue(String toValue) {
		this.toValue = toValue;
	}

	public Integer getToScope() {
		return toScope;
	}

	public void setToScope(Integer toScope) {
		this.toScope = toScope;
	}

	public Long getFormulaDefinitionId() {
		return formulaDefinitionId;
	}
	
	public String getFromValueAutocomplete() {
		return fromValueAutocomplete;
	}

	public void setFromValueAutocomplete(String fromValueAutocomplete) {
		this.fromValueAutocomplete = fromValueAutocomplete;
	}

	public List<BDParameterIndex> getLstFromIndex() {
		return lstFromIndex;
	}

	public void setLstFromIndex(List<BDParameterIndex> lstFromIndex) {
		this.lstFromIndex = lstFromIndex;
	}

	public String getToValueAutocomplete() {
		return toValueAutocomplete;
	}

	public void setToValueAutocomplete(String toValueAutocomplete) {
		this.toValueAutocomplete = toValueAutocomplete;
	}

	public List<BDParameterIndex> getLstToIndex() {
		return lstToIndex;
	}

	public void setLstToIndex(List<BDParameterIndex> lstToIndex) {
		this.lstToIndex = lstToIndex;
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

	public String getParameterIdAutocomplete() {
		return parameterIdAutocomplete;
	}

	public void setParameterIdAutocomplete(String parameterIdAutocomplete) {
		this.parameterIdAutocomplete = parameterIdAutocomplete;
	}

	public List<FormulaDetail> getFormulaDefinitionDetails() {
		return formulaDefinitionDetails;
	}

	public void setFormulaDefinitionDetails(List<FormulaDetail> formulaDefinitionDetails) {
		this.formulaDefinitionDetails = formulaDefinitionDetails;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof SequenceLogic){

			SequenceLogic sl = (SequenceLogic) obj;

			if (sl.getComponentType() != null && sl.getComponentType().equals(10)) {
				if (DataTypeUtils.equals(sl.getSequenceLogicId(), this.getSequenceLogicId())) {
					return true;
				}
			} else {
				return false;
			}
		}

		return false;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the loopStepType
	 */
	public Integer getLoopStepType() {
		return loopStepType;
	}

	/**
	 * @param loopStepType the loopStepType to set
	 */
	public void setLoopStepType(Integer loopStepType) {
		this.loopStepType = loopStepType;
	}

	/**
	 * @return the loopStepValue
	 */
	public Integer getLoopStepValue() {
		return loopStepValue;
	}

	/**
	 * @param loopStepValue the loopStepValue to set
	 */
	public void setLoopStepValue(Integer loopStepValue) {
		this.loopStepValue = loopStepValue;
	}
}
