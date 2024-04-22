package org.terasoluna.qp.app.sessionmanagement;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.SessionManagementMessageConst;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst;

@Component
public class SessionManagementValidator implements Validator{
	
	@Inject
	SystemService systemService;

	@Override
	public boolean supports(Class<?> clazz) {
		return SessionManagementForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SessionManagementForm sessionManagementForm = (SessionManagementForm) target;
		
		if (sessionManagementForm.getDataType() == null) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, 
					new Object[] {
						MessageUtils.getMessage(SessionManagementMessageConst.SC_SESSIONMANAGEMENT_0008)
					}, null);
		} else if (sessionManagementForm.getDataType().intValue() == GenerateSourceCodeConst.DataType.ENTITY || 
				sessionManagementForm.getDataType().intValue() == GenerateSourceCodeConst.DataType.COMMON_OBJECT || 
				sessionManagementForm.getDataType().intValue() == GenerateSourceCodeConst.DataType.EXTERNAL_OBJECT) {
			if (sessionManagementForm.getObjectId() == null || sessionManagementForm.getObjectIdAutocomplete() == null ) {
				errors.reject(CommonMessageConst.ERR_SYS_0025, 
						new Object[] {
							MessageUtils.getMessage(SessionManagementMessageConst.SC_SESSIONMANAGEMENT_0009)
						}, null);
			}
		}
	}
}
