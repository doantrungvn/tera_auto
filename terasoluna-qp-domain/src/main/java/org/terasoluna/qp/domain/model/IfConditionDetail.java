package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IfConditionDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long ifConditionId;
	
	private String caption;
	
	private String conditionRemark;
	
	private Long ifComponentId;
	
	private Long formulaDefinitionId;
	
	private String formulaDefinitionContent;
	
	private List<FormulaDetail> formulaDefinitionDetails = new ArrayList<FormulaDetail>();
	
	private Boolean usedConditionFlg = true;

	public Long getIfConditionId() {
		return ifConditionId;
	}

	public void setIfConditionId(Long ifConditionId) {
		this.ifConditionId = ifConditionId;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Long getIfComponentId() {
		return ifComponentId;
	}

	public void setIfComponentId(Long ifComponentId) {
		this.ifComponentId = ifComponentId;
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

	public List<FormulaDetail> getFormulaDefinitionDetails() {
		return formulaDefinitionDetails;
	}

	public void setFormulaDefinitionDetails(List<FormulaDetail> formulaDefinitionDetails) {
		this.formulaDefinitionDetails = formulaDefinitionDetails;
	}

	public String getConditionRemark() {
		return conditionRemark;
	}

	public void setConditionRemark(String conditionRemark) {
		this.conditionRemark = conditionRemark;
	}

	public Boolean getUsedConditionFlg() {
		return usedConditionFlg;
	}

	public void setUsedConditionFlg(Boolean usedConditionFlg) {
		this.usedConditionFlg = usedConditionFlg;
	}
	

}
