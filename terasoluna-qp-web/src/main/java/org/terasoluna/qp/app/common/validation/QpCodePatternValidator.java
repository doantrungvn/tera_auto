package org.terasoluna.qp.app.common.validation;

import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

public class QpCodePatternValidator implements ConstraintValidator<QpCodePattern,String>{

	@Inject
	SystemService systemService;
	
	@Override
	public void initialize(QpCodePattern constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		Pattern pattern = Pattern.compile(accountProfile.getCodePattern());
		if(StringUtils.isNotBlank(value)){
			if(!pattern.matcher(value).matches()){
				if(context instanceof HibernateConstraintValidatorContext){
					HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
					hibernateContext.addExpressionVariable("mask", accountProfile.getCodeMask());
				}
				return false;
			}
		}
		return true;
	}

}
