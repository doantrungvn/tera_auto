package org.terasoluna.qp.app.messagedesign;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
@Component
public class ModifyMessageDesignValidator implements org.springframework.validation.Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (MessageDesignSearchForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MessageDesignSearchForm messageDesignSearchForm = (MessageDesignSearchForm) target;
		// true = search action
		if(messageDesignSearchForm.getFlagAction() == null){
			messageDesignSearchForm.setFlagAction(true);
		}
		if(messageDesignSearchForm.getFlagAction()){
			return;
		}
		else{
			// false = save action
			ModifyMessageDesignForm[] modifyMessageDesignForm = messageDesignSearchForm.getModifyMessageDesignForm();
			
			for (int i = 0; i < modifyMessageDesignForm.length; i++) {
				String fromMessage = modifyMessageDesignForm[i].getFromMessageString();
				String toMessage = modifyMessageDesignForm[i].getToMessageString();
				if(fromMessage == null){
					errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage("sc.messagedesign.0017") , String.valueOf(i+1)}, null);
				}
				
				if(messageDesignSearchForm.getToLanguageCode() != null){
					if(toMessage == null){
						errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage("sc.messagedesign.0014") , String.valueOf(i+1)}, null);
					}
				}
			}
		}
	}
}
