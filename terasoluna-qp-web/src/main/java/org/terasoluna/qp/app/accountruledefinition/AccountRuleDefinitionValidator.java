package org.terasoluna.qp.app.accountruledefinition;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.AccountRuleDefinitionMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;

@Component
public class AccountRuleDefinitionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (AccountRuleDefinitionForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AccountRuleDefinitionForm accountRuleDefinitionForm = (AccountRuleDefinitionForm) target;
		
		if (accountRuleDefinitionForm.isRangeOfStringCheckbox()) {
			if (StringUtils.isNoneBlank(accountRuleDefinitionForm.getRangeOfStringMinimum()) && 
					(Integer.parseInt(accountRuleDefinitionForm.getRangeOfStringMinimum()) < Integer.parseInt(AccountRuleDefinitionMessageConst.VALIDATION_MIN_VALUE) ||
							Integer.parseInt(accountRuleDefinitionForm.getRangeOfStringMinimum()) > Integer.parseInt(AccountRuleDefinitionMessageConst.PASSWORD_LENGTH_MAX_VALUE))) {
				String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0040),
						AccountRuleDefinitionMessageConst.VALIDATION_MIN_VALUE,AccountRuleDefinitionMessageConst.PASSWORD_LENGTH_MAX_VALUE};
				errors.reject(CommonMessageConst.ERR_SYS_0024, args, null);
			}
			
			if (StringUtils.isNoneBlank(accountRuleDefinitionForm.getRangeOfStringMaximum()) && 
					(Integer.parseInt(accountRuleDefinitionForm.getRangeOfStringMaximum()) < Integer.parseInt(AccountRuleDefinitionMessageConst.VALIDATION_MIN_VALUE) || 
							Integer.parseInt(accountRuleDefinitionForm.getRangeOfStringMaximum()) > Integer.parseInt(AccountRuleDefinitionMessageConst.PASSWORD_LENGTH_MAX_VALUE))) {
				String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0041),
						AccountRuleDefinitionMessageConst.VALIDATION_MIN_VALUE,AccountRuleDefinitionMessageConst.PASSWORD_LENGTH_MAX_VALUE};
				errors.reject(CommonMessageConst.ERR_SYS_0024, args, null);
			}
			
			if (StringUtils.isNoneBlank(accountRuleDefinitionForm.getRangeOfStringMinimum()) && StringUtils.isNoneBlank(accountRuleDefinitionForm.getRangeOfStringMaximum()) &&
					Integer.parseInt(accountRuleDefinitionForm.getRangeOfStringMinimum()) > Integer.parseInt(accountRuleDefinitionForm.getRangeOfStringMaximum())) {
				String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0040),
						MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0041)};
				errors.reject(CommonMessageConst.ERR_SYS_0050, args, null);
			}
			
			if (StringUtils.isBlank(accountRuleDefinitionForm.getRangeOfStringMinimum()) || StringUtils.isBlank(accountRuleDefinitionForm.getRangeOfStringMaximum())) {
				String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0037)};
				errors.reject(CommonMessageConst.ERR_SYS_0025, args, null);
			}
		}
		
		if (accountRuleDefinitionForm.isCharactersTypeCheckbox() && !accountRuleDefinitionForm.isCharactersTypeUpper() &&
				!accountRuleDefinitionForm.isCharactersTypeLower() && !accountRuleDefinitionForm.isCharactersTypeNumeric()) {
			String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0013)};
			errors.reject(CommonMessageConst.ERR_SYS_0116, args, null);
		}
		
		if (accountRuleDefinitionForm.isGenerationsCountCheckbox()) {
			if (StringUtils.isBlank(accountRuleDefinitionForm.getGenerationsCount())) {
				String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0014)};
				errors.reject(CommonMessageConst.ERR_SYS_0025, args, null);
			} else {
				if (Integer.parseInt(accountRuleDefinitionForm.getGenerationsCount()) < Integer.parseInt(AccountRuleDefinitionMessageConst.VALIDATION_MIN_VALUE) ||
						Integer.parseInt(accountRuleDefinitionForm.getGenerationsCount()) > Integer.parseInt(AccountRuleDefinitionMessageConst.VALIDATION_MAX_VALUE)) {
					String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0014),
							AccountRuleDefinitionMessageConst.VALIDATION_MIN_VALUE,AccountRuleDefinitionMessageConst.VALIDATION_MAX_VALUE};
					errors.reject(CommonMessageConst.ERR_SYS_0024, args, null);
				}
			}
		}
		
		if (accountRuleDefinitionForm.isLifeTimeCheckbox()) {
			if (StringUtils.isBlank(accountRuleDefinitionForm.getLifeTime())) {
				String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0015)};
				errors.reject(CommonMessageConst.ERR_SYS_0025, args, null);
			} else {
				if (Integer.parseInt(accountRuleDefinitionForm.getLifeTime()) < Integer.parseInt(AccountRuleDefinitionMessageConst.VALIDATION_MIN_VALUE) ||
						Integer.parseInt(accountRuleDefinitionForm.getLifeTime()) > Integer.parseInt(AccountRuleDefinitionMessageConst.VALIDATION_MAX_VALUE)) {
					String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0015),
							AccountRuleDefinitionMessageConst.VALIDATION_MIN_VALUE,AccountRuleDefinitionMessageConst.VALIDATION_MAX_VALUE};
					errors.reject(CommonMessageConst.ERR_SYS_0024, args, null);
				}
			}
		}
		
		if (accountRuleDefinitionForm.isLoginContinuousFailureCountCheckbox()) {
			if (StringUtils.isBlank(accountRuleDefinitionForm.getLoginContinuousFailureCount())) {
				String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0017)};
				errors.reject(CommonMessageConst.ERR_SYS_0025, args, null);
			} else {
				if (Integer.parseInt(accountRuleDefinitionForm.getLoginContinuousFailureCount()) < Integer.parseInt(AccountRuleDefinitionMessageConst.VALIDATION_MIN_VALUE) ||
						Integer.parseInt(accountRuleDefinitionForm.getLoginContinuousFailureCount()) > Integer.parseInt(AccountRuleDefinitionMessageConst.VALIDATION_MAX_VALUE)) {
					String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0017),
							AccountRuleDefinitionMessageConst.VALIDATION_MIN_VALUE,AccountRuleDefinitionMessageConst.VALIDATION_MAX_VALUE};
					errors.reject(CommonMessageConst.ERR_SYS_0024, args, null);
				}
			}
		}
		
		if (accountRuleDefinitionForm.isAccountLockTimeCheckbox()) {
			if (StringUtils.isBlank(accountRuleDefinitionForm.getAccountLockTime())) {
				String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0018)};
				errors.reject(CommonMessageConst.ERR_SYS_0025, args, null);
			} else {
				if (Integer.parseInt(accountRuleDefinitionForm.getAccountLockTime()) < Integer.parseInt(AccountRuleDefinitionMessageConst.VALIDATION_MIN_VALUE) ||
						Integer.parseInt(accountRuleDefinitionForm.getAccountLockTime()) > Integer.parseInt(AccountRuleDefinitionMessageConst.VALIDATION_MAX_VALUE)) {
					String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0018),
							AccountRuleDefinitionMessageConst.VALIDATION_MIN_VALUE,AccountRuleDefinitionMessageConst.VALIDATION_MAX_VALUE};
					errors.reject(CommonMessageConst.ERR_SYS_0024, args, null);
				}
			}
		}
		
		if (accountRuleDefinitionForm.isInitialPassword()) {
			if (!accountRuleDefinitionForm.isRangeOfStringCheckbox()) {
				String[] args = {MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0012)};
				errors.reject(CommonMessageConst.ERR_SYS_0025, args, null);
			}
		}
	}
}