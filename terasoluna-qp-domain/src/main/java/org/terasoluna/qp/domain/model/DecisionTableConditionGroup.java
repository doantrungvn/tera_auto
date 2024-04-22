package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class DecisionTableConditionGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String conditionGroupId;
	
	private Integer rowNumber;
	
	private Integer groupType;
	
	private Integer rowSpan;
	
	private String decisionItemDesignId;

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
	 * @return the rowNumber
	 */
	public Integer getRowNumber() {
		return rowNumber;
	}

	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	/**
	 * @return the groupType
	 */
	public Integer getGroupType() {
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	/**
	 * @return the rowSpan
	 */
	public Integer getRowSpan() {
		return rowSpan;
	}

	/**
	 * @param rowSpan the rowSpan to set
	 */
	public void setRowSpan(Integer rowSpan) {
		this.rowSpan = rowSpan;
	}

	/**
	 * @return the decisionItemDesignId
	 */
	public String getDecisionItemDesignId() {
		return decisionItemDesignId;
	}

	/**
	 * @param decisionItemDesignId the decisionItemDesignId to set
	 */
	public void setDecisionItemDesignId(String decisionItemDesignId) {
		this.decisionItemDesignId = decisionItemDesignId;
	}

}
