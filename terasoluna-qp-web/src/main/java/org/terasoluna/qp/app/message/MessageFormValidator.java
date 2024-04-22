package org.terasoluna.qp.app.message;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.domain.model.Message;

@Component
public class MessageFormValidator implements Validator {
	
	private static final Integer MIN_LENGTH = 1;
	private static final Integer MAX_LENGTH = 400;

	@Override
	public boolean supports(Class<?> clazz) {
		return MessageForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MessageForm messageForm = (MessageForm) target;

		if (messageForm != null) {
			validateModifyMessageForm(messageForm.getListMessage(), errors);
		}
	}

	private void validateModifyMessageForm(List<Message> lstMessages, Errors errors) {
		if (lstMessages != null) {
			int messageStringIndex = 1;
			for (Message message : lstMessages) {
				if (message.getMessageString() == null || StringUtils.isBlank(message.getMessageString().trim())) {
					errors.reject(CommonMessageConst.ERR_SYS_0077,new Object[] {MessageUtils.getMessage(MessageManagementMessageConst.SC_MESSAGE_0007), messageStringIndex},null);
				} else {
					if (message.getMessageString().length() < MIN_LENGTH || message.getMessageString().length() > MAX_LENGTH){
						errors.reject(CommonMessageConst.ERR_SYS_0095,
								new Object[] {MessageUtils.getMessage(MessageManagementMessageConst.SC_MESSAGE_0007),messageStringIndex, MIN_LENGTH, MAX_LENGTH }, null);
					}
				}
				messageStringIndex++;
			}
		}
	}
}
