package org.terasoluna.qp.app.languagedesign;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LanguageDesignMessageConst;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;

@Component
public class LanguageDesignDesignFormValidator implements Validator {

	@Inject
	Mapper beanMapper;

	@Inject
	LanguageDesignService languageDesignService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (LanguageDesignDesignForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LanguageDesignDesignForm languageDesignDesignForm = (LanguageDesignDesignForm) target;
		for(int i=0;i<languageDesignDesignForm.getLanguageDesignForms().length;i++){
			if (StringUtils.isEmpty(languageDesignDesignForm.getLanguageDesignForms()[i].getLanguageCode())) {
				errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] {
					String.valueOf(i + 1),
					MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0001)
				}, null);
			}
		}
	}
}