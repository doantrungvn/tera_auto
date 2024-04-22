package org.terasoluna.qp.domain.repository.accountruledefinition;

import java.io.Serializable;

public class AccountRuleDefinitionSearchCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private String accountRuleDefinitionCode;
	private String accountRuleDefinitionName;
	
	public String getAccountRuleDefinitionCode() {
		return accountRuleDefinitionCode;
	}
	public void setAccountRuleDefinitionCode(String accountRuleDefinitionCode) {
		this.accountRuleDefinitionCode = accountRuleDefinitionCode;
	}
	public String getAccountRuleDefinitionName() {
		return accountRuleDefinitionName;
	}
	public void setAccountRuleDefinitionName(String accountRuleDefinitionName) {
		this.accountRuleDefinitionName = accountRuleDefinitionName;
	}
	
}
