package org.terasoluna.qp.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DecisionTableConditionItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String conditionItemId;
	
	private String conditionGroupId;
	
	private Integer itemSequenceNo;
	
	private Integer opertatorType;
	
	/** Value */
	private Integer itemType;

	private String itemValue;

	private Long formulaDefinitionId;

	private List<FormulaDetail> formulaDefinitionDetails = new ArrayList<FormulaDetail>();
	
	private String formulaDefinitionContent;
	
	private String decisionTbCode;
	
	private Long decisionTbId;
	
	private Long moduleId;
	
	/**
	 * @return the conditionItemId
	 */
	public String getConditionItemId() {
		return conditionItemId;
	}

	/**
	 * @param conditionItemId the conditionItemId to set
	 */
	public void setConditionItemId(String conditionItemId) {
		this.conditionItemId = conditionItemId;
	}

	/**
	 * @return the conditionGroupId
	 */
	public String getConditionGroupId() {
		return conditionGroupId;
	}

	/**
	 * @param conditionGroupId the conditionGroupId to set
	 */
	public void setConditionGroupId(String conditionGroupId) {
		this.conditionGroupId = conditionGroupId;
	}

	/**
	 * @return the itemSequenceNo
	 */
	public Integer getItemSequenceNo() {
		return itemSequenceNo;
	}

	/**
	 * @param itemSequenceNo the itemSequenceNo to set
	 */
	public void setItemSequenceNo(Integer itemSequenceNo) {
		this.itemSequenceNo = itemSequenceNo;
	}

	/**
	 * @return the opertatorType
	 */
	public Integer getOpertatorType() {
		return opertatorType;
	}

	/**
	 * @param opertatorType the opertatorType to set
	 */
	public void setOpertatorType(Integer opertatorType) {
		this.opertatorType = opertatorType;
	}

	/**
	 * @return the itemType
	 */
	public Integer getItemType() {
		return itemType;
	}

	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return the itemValue
	 */
	public String getItemValue() {
		return itemValue;
	}

	/**
	 * @param itemValue the itemValue to set
	 */
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public Long getFormulaDefinitionId() {
		return formulaDefinitionId;
	}

	public void setFormulaDefinitionId(Long formulaDefinitionId) {
		this.formulaDefinitionId = formulaDefinitionId;
	}

	public List<FormulaDetail> getFormulaDefinitionDetails() {
		return formulaDefinitionDetails;
	}

	public void setFormulaDefinitionDetails(List<FormulaDetail> formulaDefinitionDetails) {
		this.formulaDefinitionDetails = formulaDefinitionDetails;
	}

	public String getFormulaDefinitionContent() {
		return formulaDefinitionContent;
	}

	public void setFormulaDefinitionContent(String formulaDefinitionContent) {
		this.formulaDefinitionContent = formulaDefinitionContent;
	}

	public String getDecisionTbCode() {
	    return decisionTbCode;
    }

	public void setDecisionTbCode(String decisionTbCode) {
	    this.decisionTbCode = decisionTbCode;
    }

	public Long getDecisionTbId() {
	    return decisionTbId;
    }

	public void setDecisionTbId(Long decisionTbId) {
	    this.decisionTbId = decisionTbId;
    }

	public Long getModuleId() {
	    return moduleId;
    }

	public void setModuleId(Long moduleId) {
	    this.moduleId = moduleId;
    }

}
