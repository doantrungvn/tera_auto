package org.terasoluna.qp.app.language;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LanguageMessageConst;
import org.terasoluna.qp.domain.service.language.LanguageService;

@Component
public class LanguageValidator implements Validator {

	@Inject
	Mapper beanMapper;

	@Inject
	LanguageService languageService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return (LanguageForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LanguageForm languageForm = (LanguageForm) target;
		
		// Validate required
		if (StringUtils.isBlank(languageForm.getLanguageCode())) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(LanguageMessageConst.LANGUAGE_NAME)}, null);
			return;
		}
	}

}