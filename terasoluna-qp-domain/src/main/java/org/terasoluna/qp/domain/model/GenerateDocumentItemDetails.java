package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class GenerateDocumentItemDetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Project or Module type */
	private String documentItemParenItemType;
	/** Item had is checked */
	private Boolean isChecked;
	/** Item had is checked */
	private String itemName;
	/** Data of document */
	private String jsonData;

	/**
	 * @return the documentItemParenItemType
	 */
	public String getDocumentItemParenItemType() {
		return documentItemParenItemType;
	}

	/**
	 * @param documentItemParenItemType the documentItemParenItemType to set
	 */
	public void setDocumentItemParenItemType(String documentItemParenItemType) {
		this.documentItemParenItemType = documentItemParenItemType;
	}

	/**
	 * @return the isChecked
	 */
	public Boolean getIsChecked() {
		return isChecked;
	}

	/**
	 * @param isChecked the isChecked to set
	 */
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	/**
	 * @return the jsonData
	 */
	public String getJsonData() {
		return jsonData;
	}

	/**
	 * @param jsonData the jsonData to set
	 */
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
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
	
}
