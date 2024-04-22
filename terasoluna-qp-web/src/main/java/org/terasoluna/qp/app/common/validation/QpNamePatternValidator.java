package org.terasoluna.qp.app.common.validation;

import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

public class QpNamePatternValidator implements ConstraintValidator<QpNamePattern,String>{

	@Inject
	SystemService systemService;
	
	@Override
	public void initialize(QpNamePattern constraintAnnotation) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		Pattern pattern = Pattern.compile(accountProfile.getNamePattern());
		if(StringUtils.isNotBlank(value)){
			if(!pattern.matcher(value).matches()){
				if(context instanceof HibernateConstraintValidatorContext){
					HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
					hibernateContext.addExpressionVariable("mask", accountProfile.getNameMask());
				}
				return false;
			}
		}
		return true;
	}

}
