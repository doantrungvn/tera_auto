package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class DecisionTableItemDesignBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String decisionItemDesignId;
	
	/** Input && Output bean */
	private String itemName;
	
	/** Kind of Input bean or Output bean */
	private Integer itemType;
	
	/** Kind of Id from Input or Output */
	private String itemValue;
	
	/** Sort order */
	private Integer itemSequenceNo;
	
	private String decisionTbId;
	
	/** Code of column */
	private String objectCode;
	
	/** Code combine of column */
	private String objectCodeCombine;
	
	/** Name of bean */
	private String objectName;
	
	/** Data type of column */
	private Integer dataType;
	
	/** object code append of column */
	private String objectCodeParent;
    
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

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	 * @return the decisionTbId
	 */
	public String getDecisionTbId() {
		return decisionTbId;
	}

	/**
	 * @param decisionTbId the decisionTbId to set
	 */
	public void setDecisionTbId(String decisionTbId) {
		this.decisionTbId = decisionTbId;
	}

	/**
	 * @return the objectCode
	 */
	public String getObjectCode() {
		return objectCode;
	}

	/**
	 * @param objectCode the objectCode to set
	 */
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	/**
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}

	/**
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	/**
	 * @return the dataType
	 */
	public Integer getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the objectCodeParent
	 */
	public String getObjectCodeParent() {
		return objectCodeParent;
	}

	/**
	 * @param objectCodeParent the objectCodeParent to set
	 */
	public void setObjectCodeParent(String objectCodeParent) {
		this.objectCodeParent = objectCodeParent;
	}

	/**
	 * @return the objectCodeCombine
	 */
	public String getObjectCodeCombine() {
		return objectCodeCombine;
	}

	/**
	 * @param objectCodeCombine the objectCodeCombine to set
	 */
	public void setObjectCodeCombine(String objectCodeCombine) {
		this.objectCodeCombine = objectCodeCombine;
	}

}
