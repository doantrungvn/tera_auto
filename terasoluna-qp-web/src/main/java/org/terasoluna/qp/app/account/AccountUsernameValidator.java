package org.terasoluna.qp.app.account;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.AccountMessageConst;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class AccountUsernameValidator implements Validator {
	@Inject
	SystemService systemService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (AccountForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		String username = ((AccountForm) target).getUsername();
		ValidationUtils.validateAccountName(username, errors, AccountMessageConst.SC_ACCOUNT_0002, systemService.getDefaultProfile().getCodePattern());
	}
}
