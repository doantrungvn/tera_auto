package org.terasoluna.qp.app.account;


import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.AccountMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.AccountRuleDefinition;
import org.terasoluna.qp.domain.service.accountruledefinition.AccountRuleDefinitionService;

@Component
public class AccountPassEqualsValidator implements Validator {
	@Inject
	AccountRuleDefinitionService accountRuleDefinitionService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (AccountForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AccountForm accountForm = (AccountForm) target;
		if((accountForm.getUpdatedDate() != null && accountForm.isSelectedChangePass()) || accountForm.getUpdatedDate() == null) {
			AccountRuleDefinition accountRuleDefinition = accountRuleDefinitionService.getAccountRuleDefinitionWhenAuthentication(accountForm.getAccountRuleDefinitionId());
			
			if((accountRuleDefinition != null && !accountRuleDefinition.isInitialPassword()) || accountRuleDefinition == null) {
				String pass = accountForm.getPassword();
				String passConfirm = accountForm.getConfirmPassword();
				if (pass == null || pass == "") {
					String[] args =  {MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0003)};
					errors.reject(CommonMessageConst.ERR_SYS_0025, args , null);
				} 
//				else if(pass.length() < DbDomainConst.MIN_VAL_INPUT || pass.length() > DbDomainConst.MAX_VAL_OTHER ){
//		   			String[] args =  { MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0003), String.valueOf(DbDomainConst.MIN_VAL_INPUT) , String.valueOf(DbDomainConst.MAX_VAL_OTHER)};
//		   			errors.reject(CommonMessageConst.ERR_SYS_0067, args , null);
//				}
				
				if (passConfirm == null || passConfirm == "") {
					String[] args =  {MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0004)};
					errors.reject(CommonMessageConst.ERR_SYS_0025, args , null);
				} 
//				else if(passConfirm.length() < DbDomainConst.MIN_VAL_INPUT || passConfirm.length() > DbDomainConst.MAX_VAL_OTHER) {
//					String[] args =  { MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0004), String.valueOf(DbDomainConst.MIN_VAL_INPUT) , String.valueOf(DbDomainConst.MAX_VAL_OTHER)};
//					errors.reject(CommonMessageConst.ERR_SYS_0067, args , null);
//				}

				if (passConfirm != null && !pass.equals(passConfirm)) {
					errors.reject(AccountMessageConst.ERR_ACCOUNT_0012);
				}
			}
		}
	}
}