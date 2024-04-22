package org.terasoluna.qp.app.languagedesign;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;

@Component
public class LanguageDesignValidator implements Validator {

	@Inject
	Mapper beanMapper;

	@Inject
	LanguageDesignService languageDesignService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return (LanguageDesignForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LanguageDesignForm languageDesign = (LanguageDesignForm) target;
		if (languageDesign.getLanguageCode() == null || languageDesign.getCountryCode() == null) {
			// must be checked by @NotEmpty
			return;
		}
	}
}