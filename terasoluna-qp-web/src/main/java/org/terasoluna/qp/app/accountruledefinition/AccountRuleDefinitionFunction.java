package org.terasoluna.qp.app.accountruledefinition;

import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.domain.model.AccountRuleDefinition;

@Component
public class AccountRuleDefinitionFunction {
	public AccountRuleDefinition mapFromFormToObject(AccountRuleDefinitionForm accountRuleDefinitionForm) {
		AccountRuleDefinition accountRuleDefinition = new AccountRuleDefinition();
		
		accountRuleDefinition.setAccountRuleDefinitionName(accountRuleDefinitionForm.getAccountRuleDefinitionName());
		accountRuleDefinition.setAccountRuleDefinitionCode(accountRuleDefinitionForm.getAccountRuleDefinitionCode());
		
		if (accountRuleDefinitionForm.isRangeOfStringCheckbox()) {
			if (accountRuleDefinitionForm.getRangeOfStringMinimum() != "") {
				accountRuleDefinition.setRangeOfStringMinimum(Integer.parseInt(accountRuleDefinitionForm.getRangeOfStringMinimum()));
			}
			if (accountRuleDefinitionForm.getRangeOfStringMaximum() != "") {
				accountRuleDefinition.setRangeOfStringMaximum(Integer.parseInt(accountRuleDefinitionForm.getRangeOfStringMaximum()));
			}
		}
		
		if (accountRuleDefinitionForm.isCharactersTypeCheckbox()) {
			accountRuleDefinition.setCharactersTypeUpper(accountRuleDefinitionForm.isCharactersTypeUpper());
			accountRuleDefinition.setCharactersTypeLower(accountRuleDefinitionForm.isCharactersTypeLower());
			accountRuleDefinition.setCharactersTypeNumeric(accountRuleDefinitionForm.isCharactersTypeNumeric());
		}
		
		if (accountRuleDefinitionForm.isGenerationsCountCheckbox() && accountRuleDefinitionForm.getGenerationsCount() != "") {
			accountRuleDefinition.setGenerationsCount(Integer.parseInt(accountRuleDefinitionForm.getGenerationsCount()));
		}
		
		if (accountRuleDefinitionForm.isLifeTimeCheckbox() && accountRuleDefinitionForm.getLifeTime() != "") {
			accountRuleDefinition.setLifeTime(Integer.parseInt(accountRuleDefinitionForm.getLifeTime()));
		}
		
		if (accountRuleDefinitionForm.isLoginContinuousFailureCountCheckbox() && accountRuleDefinitionForm.getLoginContinuousFailureCount() != "") {
			accountRuleDefinition.setLoginContinuousFailureCount(Integer.parseInt(accountRuleDefinitionForm.getLoginContinuousFailureCount()));
		}
		
		if (accountRuleDefinitionForm.isAccountLockTimeCheckbox() && accountRuleDefinitionForm.getAccountLockTime() != "") {
			accountRuleDefinition.setAccountLockTime(Integer.parseInt(accountRuleDefinitionForm.getAccountLockTime()));
		}
		
		if (accountRuleDefinitionForm.isInitialPassword()) {
			accountRuleDefinition.setInitialPassword(true);
		}
		
		if (accountRuleDefinitionForm.isInitialPasswordForceChange()) {
			accountRuleDefinition.setInitialPasswordForceChange(true);
		}
		
		accountRuleDefinition.setCreatedBy(SessionUtils.getAccountId());
		accountRuleDefinition.setUpdatedBy(SessionUtils.getAccountId());
		
		if (accountRuleDefinitionForm.getAccountRuleDefinitionId() != null) {
			accountRuleDefinition.setAccountRuleDefinitionId(accountRuleDefinitionForm.getAccountRuleDefinitionId());
		}
		
		if (accountRuleDefinitionForm.getUpdatedDate() != null) {
			accountRuleDefinition.setUpdatedDate(accountRuleDefinitionForm.getUpdatedDate());
		}
		
		return accountRuleDefinition;
	}
	
	public AccountRuleDefinitionForm mapFromObjectToForm(AccountRuleDefinition accountRuleDefinition) {
		AccountRuleDefinitionForm accountRuleDefinitionForm = new AccountRuleDefinitionForm();
		
		accountRuleDefinitionForm.setAccountRuleDefinitionId(accountRuleDefinition.getAccountRuleDefinitionId());
		accountRuleDefinitionForm.setAccountRuleDefinitionName(accountRuleDefinition.getAccountRuleDefinitionName());
		accountRuleDefinitionForm.setAccountRuleDefinitionCode(accountRuleDefinition.getAccountRuleDefinitionCode());
		accountRuleDefinitionForm.setRangeOfStringMinimum(Integer.toString(accountRuleDefinition.getRangeOfStringMinimum()));
		accountRuleDefinitionForm.setRangeOfStringMaximum(Integer.toString(accountRuleDefinition.getRangeOfStringMaximum()));
		accountRuleDefinitionForm.setCharactersTypeUpper(accountRuleDefinition.isCharactersTypeUpper());
		accountRuleDefinitionForm.setCharactersTypeLower(accountRuleDefinition.isCharactersTypeLower());
		accountRuleDefinitionForm.setCharactersTypeNumeric(accountRuleDefinition.isCharactersTypeNumeric());
		accountRuleDefinitionForm.setGenerationsCount(Integer.toString(accountRuleDefinition.getGenerationsCount()));
		accountRuleDefinitionForm.setLifeTime(Integer.toString(accountRuleDefinition.getLifeTime()));
		accountRuleDefinitionForm.setLoginContinuousFailureCount(Integer.toString(accountRuleDefinition.getLoginContinuousFailureCount()));
		accountRuleDefinitionForm.setAccountLockTime(Integer.toString(accountRuleDefinition.getAccountLockTime()));
		accountRuleDefinitionForm.setInitialPassword(accountRuleDefinition.isInitialPassword());
		accountRuleDefinitionForm.setInitialPasswordForceChange(accountRuleDefinition.isInitialPasswordForceChange());
		accountRuleDefinitionForm.setUpdatedDate(accountRuleDefinition.getUpdatedDate());
		
		if (accountRuleDefinition.getRangeOfStringMinimum() == 0 || accountRuleDefinition.getRangeOfStringMaximum() == 0) {
			accountRuleDefinitionForm.setRangeOfStringCheckbox(false);
		} else {
			accountRuleDefinitionForm.setRangeOfStringCheckbox(true);
		}
		
		if (!accountRuleDefinition.isCharactersTypeUpper() && !accountRuleDefinition.isCharactersTypeLower() && !accountRuleDefinition.isCharactersTypeNumeric()) {
			accountRuleDefinitionForm.setCharactersTypeCheckbox(false);
		} else {
			accountRuleDefinitionForm.setCharactersTypeCheckbox(true);
		}
		
		if (accountRuleDefinition.getGenerationsCount() == 0) {
			accountRuleDefinitionForm.setGenerationsCountCheckbox(false);
		} else {
			accountRuleDefinitionForm.setGenerationsCountCheckbox(true);
		}
		
		if (accountRuleDefinition.getLifeTime() == 0) {
			accountRuleDefinitionForm.setLifeTimeCheckbox(false);
		} else {
			accountRuleDefinitionForm.setLifeTimeCheckbox(true);
		}
		
		if (accountRuleDefinition.getLoginContinuousFailureCount() == 0) {
			accountRuleDefinitionForm.setLoginContinuousFailureCountCheckbox(false);
		} else {
			accountRuleDefinitionForm.setLoginContinuousFailureCountCheckbox(true);
		}
		
		if (accountRuleDefinition.getAccountLockTime() == 0) {
			accountRuleDefinitionForm.setAccountLockTimeCheckbox(false);
		} else {
			accountRuleDefinitionForm.setAccountLockTimeCheckbox(true);
		}
		
		return accountRuleDefinitionForm;
	}
	
	public AccountRuleDefinition mapFromFormToObjectViewPage(AccountRuleDefinitionForm accountRuleDefinitionForm) {
		AccountRuleDefinition accountRuleDefinition = new AccountRuleDefinition();
		
		accountRuleDefinition.setAccountRuleDefinitionName(accountRuleDefinitionForm.getAccountRuleDefinitionName());
		accountRuleDefinition.setAccountRuleDefinitionCode(accountRuleDefinitionForm.getAccountRuleDefinitionCode());
		accountRuleDefinition.setRangeOfStringMinimum(Integer.parseInt((accountRuleDefinitionForm.getRangeOfStringMinimum() == null)? "0" : accountRuleDefinitionForm.getRangeOfStringMinimum()));
		accountRuleDefinition.setRangeOfStringMaximum(Integer.parseInt((accountRuleDefinitionForm.getRangeOfStringMaximum() == null)? "0" : accountRuleDefinitionForm.getRangeOfStringMaximum()));
		accountRuleDefinition.setCharactersTypeLower(accountRuleDefinitionForm.isCharactersTypeLower());
		accountRuleDefinition.setCharactersTypeUpper(accountRuleDefinitionForm.isCharactersTypeUpper());
		accountRuleDefinition.setCharactersTypeNumeric(accountRuleDefinitionForm.isCharactersTypeNumeric());
		accountRuleDefinition.setGenerationsCount(Integer.parseInt(accountRuleDefinitionForm.getGenerationsCount()));
		accountRuleDefinition.setLifeTime(Integer.parseInt((accountRuleDefinitionForm.getLifeTime() == null)? "0" : accountRuleDefinitionForm.getLifeTime()));
		accountRuleDefinition.setLoginContinuousFailureCount(Integer.parseInt(accountRuleDefinitionForm.getLoginContinuousFailureCount()));
		accountRuleDefinition.setAccountLockTime(Integer.parseInt((accountRuleDefinitionForm.getAccountLockTime() == null)? "0" : accountRuleDefinitionForm.getAccountLockTime()));
		accountRuleDefinition.setInitialPassword(accountRuleDefinitionForm.isInitialPassword());
		accountRuleDefinition.setInitialPasswordForceChange(accountRuleDefinitionForm.isInitialPasswordForceChange());
		accountRuleDefinition.setCreatedBy(SessionUtils.getAccountId());
		accountRuleDefinition.setUpdatedBy(SessionUtils.getAccountId());
		accountRuleDefinition.setAccountRuleDefinitionId(accountRuleDefinitionForm.getAccountRuleDefinitionId());
		accountRuleDefinition.setUpdatedDate(accountRuleDefinitionForm.getUpdatedDate());
		
		return accountRuleDefinition;
	}
}
