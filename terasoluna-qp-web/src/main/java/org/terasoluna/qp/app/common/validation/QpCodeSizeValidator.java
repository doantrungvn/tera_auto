package org.terasoluna.qp.app.common.validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

public class QpCodeSizeValidator implements ConstraintValidator<QpCodeSize,String>{

	@Inject
	SystemService systemService;
	
	@Override
	public void initialize(QpCodeSize constraintAnnotation) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		
		if(StringUtils.isNotBlank(value)){
			int length = 0;
			if(value != null) {
				length = value.length();
			}
			if(length<accountProfile.getCodeMinLength() || length > accountProfile.getCodeMaxLength()) {
				if(context instanceof HibernateConstraintValidatorContext){
					HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
					hibernateContext.addExpressionVariable("min", accountProfile.getCodeMinLength());
					hibernateContext.addExpressionVariable("max", accountProfile.getCodeMaxLength());
				}
				return false;
			}
		}
		return true;
	}

}
