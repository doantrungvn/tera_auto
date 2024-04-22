package org.terasoluna.qp.app.account;

import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.AccountMessageConst;
import org.terasoluna.qp.app.message.AccountRuleDefinitionMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountLoginAttempt;
import org.terasoluna.qp.domain.model.AccountRuleDefinition;
import org.terasoluna.qp.domain.service.account.AccountService;
import org.terasoluna.qp.domain.service.accountloginattempt.AccountLoginAttemptService;
import org.terasoluna.qp.domain.service.accountruledefinition.AccountRuleDefinitionService;

@Component
public class PasswordRuleValidator implements Validator {
	private final Pattern hasUpperCasePattern = Pattern.compile("[A-Z]");
	private final Pattern hasLowerCasePattern = Pattern.compile("[a-z]");
	private final Pattern hasNumericPattern = Pattern.compile("\\d");
	
	@Inject
	AccountRuleDefinitionService accountRuleDefinitionService;
	
	@Inject
	AccountService accountService;
	
	@Inject
	PasswordEncoder passwordEncoder;
	
	@Inject
	AccountLoginAttemptService accountLoginAttemptService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (AccountForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AccountForm accountForm = (AccountForm) target;
		AccountRuleDefinition accountRuleDefinition = null;
		String inputtedPassword = null;
		if (accountForm.getAccountRuleDefinitionId() != null) {
			accountRuleDefinition = accountRuleDefinitionService.getAccountRuleDefinitionWhenAuthentication(accountForm.getAccountRuleDefinitionId());
		}
		if ((accountForm.getUpdatedDate() != null && accountForm.isSelectedChangePass()) || accountForm.getUpdatedDate() == null) {
			inputtedPassword = accountForm.getPassword();
		}
		Account account = null;
		if (accountForm.getAccountId() != null) {
			//QuyND: add try-catch when using account service
			try{
				account = accountService.findOneByAccountId(accountForm.getAccountId());
			} catch (BusinessException ex){
				return;
			}
		}
		
		if (inputtedPassword != null && accountRuleDefinition != null && !accountRuleDefinition.isInitialPassword()) {
			// Check range of string (only when rule has range of string)
			if(accountRuleDefinition.getRangeOfStringMinimum() != 0 && accountRuleDefinition.getRangeOfStringMaximum() != 0){
				if (inputtedPassword.length() < accountRuleDefinition.getRangeOfStringMinimum() || inputtedPassword.length() > accountRuleDefinition.getRangeOfStringMaximum()) {
					String[] args = {MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0003),
							Integer.toString(accountRuleDefinition.getRangeOfStringMinimum()),Integer.toString(accountRuleDefinition.getRangeOfStringMaximum())};
					errors.reject(CommonMessageConst.ERR_SYS_0024, args, null);
				}
			}
			
			// Check characters type
			if (accountRuleDefinition.isCharactersTypeUpper()) {
				if (!hasUpperCasePattern.matcher(inputtedPassword).find()) {
					errors.reject(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0042, null, null);
				}
			}
			
			if (accountRuleDefinition.isCharactersTypeLower()) {
				if (!hasLowerCasePattern.matcher(inputtedPassword).find()) {
					errors.reject(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0043, null, null);
				}
			}
			
			if (accountRuleDefinition.isCharactersTypeNumeric()) {
				if (!hasNumericPattern.matcher(inputtedPassword).find()) {
					errors.reject(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0044, null, null);
				}
			}
			
			// Check generations count
			if (accountRuleDefinition.getGenerationsCount() > 0 && account != null) {
				if (passwordEncoder.matches(inputtedPassword, account.getPassword())) {
					AccountLoginAttempt accountLoginAttempt = accountLoginAttemptService.getAccountLoginAttemptByAccountId(account.getAccountId());
					
					// If there is not account, insert new data record
					// Else update current data record
					if (accountLoginAttempt == null) {
						AccountLoginAttempt newAccountLoginAttempt = new AccountLoginAttempt();
						
						newAccountLoginAttempt.setAccountId(account.getAccountId());
						newAccountLoginAttempt.setGenerationsCount(1);
						newAccountLoginAttempt.setLoginContinuousFailureCount(0);
						newAccountLoginAttempt.setCreatedBy(SessionUtils.getAccountId());
						newAccountLoginAttempt.setUpdatedBy(SessionUtils.getAccountId());
						
						accountLoginAttemptService.register(newAccountLoginAttempt);
						
						errors.reject(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0045, null, null);
					} else if (accountLoginAttempt.getGenerationsCount() < accountRuleDefinition.getGenerationsCount()) {
						accountLoginAttempt.setGenerationsCount(accountLoginAttempt.getGenerationsCount() + 1);
						accountLoginAttempt.setLoginContinuousFailureCount(accountLoginAttempt.getLoginContinuousFailureCount());
						accountLoginAttempt.setUpdatedBy(SessionUtils.getAccountId());
						accountLoginAttempt.setAccountId(account.getAccountId());
						
						accountLoginAttemptService.modify(accountLoginAttempt);
						
						errors.reject(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0045, null, null);
					}
					
					if (accountLoginAttempt != null && accountLoginAttempt.getGenerationsCount() != 0 && accountLoginAttempt.getGenerationsCount() >= accountRuleDefinition.getGenerationsCount()) {
						errors.reject(AccountRuleDefinitionMessageConst.ERR_ACCOUNTRULEDEFINITION_0046, null, null);
					}
				}
			}
		}
	}
}
