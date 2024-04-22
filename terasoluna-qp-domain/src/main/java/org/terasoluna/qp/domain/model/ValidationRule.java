package org.terasoluna.qp.domain.model;

import java.io.Serializable;

	
public class ValidationRule implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String validationRuleCode;
	
	private String validationRuleName;
	
	private String include;
	
	private String baseTypeGroup;
	
	private Integer itemType;
	
	private String remark;
	
	public String getValidationRuleCode() {
		return validationRuleCode;
	}

	public void setValidationRuleCode(String validationRuleCode) {
		this.validationRuleCode = validationRuleCode;
	}

	public String getValidationRuleName() {
		return validationRuleName;
	}

	public void setValidationRuleName(String validationRuleName) {
		this.validationRuleName = validationRuleName;
	}

	public String getInclude() {
		return include;
	}

	public void setInclude(String include) {
		this.include = include;
	}

	public String getBaseTypeGroup() {
		return baseTypeGroup;
	}

	public void setBaseTypeGroup(String baseTypeGroup) {
		this.baseTypeGroup = baseTypeGroup;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}
