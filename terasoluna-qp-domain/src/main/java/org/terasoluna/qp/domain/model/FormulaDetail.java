package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormulaDetail implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long formulaDetailId;

	private Integer itemSequenceNo ;

	private Integer type;

	private String value;

	private String parameterId;

	private String parameterCode;

	private String parameterFullCode;

	private Integer dataType;

	private String parentParameterId;

	private Boolean arrayFlg;

	private Long formulaDefinitionId;

	private String paddingLeft;

	private Long functionMethodId;

	private String functionMethodCode;

	private String functionMasterCode;

	private List<FormulaMethodInput> formulaMethodInputs;

	private List<FormulaMethodOutput> formulaMethodOutputs;

	private List<BDParameterIndex> lstParameterIndex = new ArrayList<BDParameterIndex>();

	private String label;

	private String shortLabel;
	
	//detect impact
	private Long sequenceLogic;

	public Long getFormulaDetailId() {
		return formulaDetailId;
	}

	public void setFormulaDetailId(Long formulaDetailId) {
		this.formulaDetailId = formulaDetailId;
	}

	public Integer getItemSequenceNo() {
		return itemSequenceNo;
	}

	public void setItemSequenceNo(Integer itemSequenceNo) {
		this.itemSequenceNo = itemSequenceNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public Long getFormulaDefinitionId() {
		return formulaDefinitionId;
	}

	public void setFormulaDefinitionId(Long formulaDefinitionId) {
		this.formulaDefinitionId = formulaDefinitionId;
	}

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public String getParameterFullCode() {
		return parameterFullCode;
	}

	public void setParameterFullCode(String parameterFullCode) {
		this.parameterFullCode = parameterFullCode;
	}

	public String getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(String paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	public Long getFunctionMethodId() {
		return functionMethodId;
	}

	public void setFunctionMethodId(Long functionMethodId) {
		this.functionMethodId = functionMethodId;
	}

	public List<FormulaMethodInput> getFormulaMethodInputs() {
		return formulaMethodInputs;
	}

	public void setFormulaMethodInputs(List<FormulaMethodInput> formulaMethodInputs) {
		this.formulaMethodInputs = formulaMethodInputs;
	}

	public List<FormulaMethodOutput> getFormulaMethodOutputs() {
		return formulaMethodOutputs;
	}

	public void setFormulaMethodOutputs(List<FormulaMethodOutput> formulaMethodOutputs) {
		this.formulaMethodOutputs = formulaMethodOutputs;
	}

	public String getFunctionMethodCode() {
		return functionMethodCode;
	}

	public void setFunctionMethodCode(String functionMethodCode) {
		this.functionMethodCode = functionMethodCode;
	}

	public String getFunctionMasterCode() {
		return functionMasterCode;
	}

	public void setFunctionMasterCode(String functionMasterCode) {
		this.functionMasterCode = functionMasterCode;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getShortLabel() {
		return shortLabel;
	}

	public void setShortLabel(String shortLabel) {
		this.shortLabel = shortLabel;
	}

	public Boolean getArrayFlg() {
		return arrayFlg;
	}

	public void setArrayFlg(Boolean arrayFlg) {
		this.arrayFlg = arrayFlg;
	}

	public List<BDParameterIndex> getLstParameterIndex() {
		return lstParameterIndex;
	}

	public void setLstParameterIndex(List<BDParameterIndex> lstParameterIndex) {
		this.lstParameterIndex = lstParameterIndex;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getParentParameterId() {
		return parentParameterId;
	}

	public void setParentParameterId(String parentParameterId) {
		this.parentParameterId = parentParameterId;
	}

	public Long getSequenceLogic() {
	    return sequenceLogic;
    }

	public void setSequenceLogic(Long sequenceLogic) {
	    this.sequenceLogic = sequenceLogic;
    }
}
