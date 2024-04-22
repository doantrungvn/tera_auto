package org.terasoluna.qp.domain.model;

import java.io.Serializable;

public class ScreenItemStatus implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long itemId;
	
	private String itemName;
	
	private String itemCode;
	
	private Integer itemType;
	
	private Long formulaDefinitionId;
	
	private Long screenItemId;
	
	private Integer status;
	
	private Integer type;
	
	private Long screenId;
	
	private Long screenFormId;
	
	private Boolean enabled;
	
	private String formulaCondition;

	public Long getFormulaDefinitionId() {
		return formulaDefinitionId;
	}

	public void setFormulaDefinitionId(Long formulaDefinitionId) {
		this.formulaDefinitionId = formulaDefinitionId;
	}

	public Long getScreenItemId() {
		return screenItemId;
	}

	public void setScreenItemId(Long screenItemId) {
		this.screenItemId = screenItemId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Long getScreenFormId() {
		return screenFormId;
	}

	public void setScreenFormId(Long screenFormId) {
		this.screenFormId = screenFormId;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getFormulaCondition() {
		return formulaCondition;
	}

	public void setFormulaCondition(String formulaCondition) {
		this.formulaCondition = formulaCondition;
	}
	
}
