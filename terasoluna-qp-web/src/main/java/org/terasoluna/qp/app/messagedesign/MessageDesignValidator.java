package org.terasoluna.qp.app.messagedesign;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.MessageDesignMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;
@Component
public class MessageDesignValidator implements Validator {
	
	private static final String SCREEN_REGISTER = "0";
//	private static final String SCREEN_MODIFY = "1";
	
	@Inject 
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (MessageDesignForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MessageDesignForm messageDesignForm = (MessageDesignForm) target;
		MultipleMessageDesignForm[] multipleMessageDesignForms = messageDesignForm.getMultipleMessageDesignForm();
		AccountProfile accountProfile = systemService.getDefaultProfile();
		if (ArrayUtils.isEmpty(multipleMessageDesignForms)) {
			String[] args =  { MessageUtils.getMessage(CommonMessageConst.TQP_MESSAGEDESIGN)};
			errors.reject(CommonMessageConst.ERR_SYS_0104, args , null);
			
		}else{
			int count = 1;
			if(messageDesignForm.getRemark() != null && messageDesignForm.getRemark().length() > accountProfile.getRemarkMaxLength()){
				errors.reject(MessageDesignMessageConst.ERR_MESSAGEDESIGN_0006, new Object[] { accountProfile.getRemarkMaxLength() }, null);
			}
			
			Set<String> msgStrings = new HashSet<String>();
			
			for (MultipleMessageDesignForm multipleMessageDesignForm : multipleMessageDesignForms) {
				
				if(FunctionCommon.equals(multipleMessageDesignForm.getMessageString(), null)){
					errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0009), count }, null);
				}else if(multipleMessageDesignForm.getMessageString().length() >= DbDomainConst.MAX_VAL_MESSAGE){
					errors.reject(MessageDesignMessageConst.ERR_MESSAGEDESIGN_0004, new Object[] { count, DbDomainConst.MAX_VAL_MESSAGE }, null);
				} else {
					String[] args =  { MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0009), String.valueOf(count) };
					ValidationUtils.validateExistInListIgnoreCase(multipleMessageDesignForm.getMessageString() + multipleMessageDesignForm.getLanguageId(), msgStrings, errors, CommonMessageConst.ERR_SYS_0041, args);
				}
				
				if(messageDesignForm.getScreenType().equals(SCREEN_REGISTER)){
					if(FunctionCommon.equals(multipleMessageDesignForm.getMessageType(), null)){
						errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0006), count }, null);
					}
				}
				
				if(multipleMessageDesignForm.getRemark() != null && multipleMessageDesignForm.getRemark().length() > accountProfile.getRemarkMaxLength()){
					errors.reject(MessageDesignMessageConst.ERR_MESSAGEDESIGN_0005, new Object[] { count, accountProfile.getRemarkMaxLength() }, null);
				}
				count++;
			}
		}
	}
}
