package org.terasoluna.qp.app.common.validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

public class QpNameSizeValidator  implements ConstraintValidator<QpNameSize,String>{

	@Inject
	SystemService systemService;
	
	@Override
	public void initialize(QpNameSize constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		if(StringUtils.isNotBlank(value)){
			int length = 0;
			if(value != null) {
				length = value.length();
			}
			if(length<accountProfile.getNameMinLength() || length > accountProfile.getNameMaxLength()) {
				if(context instanceof HibernateConstraintValidatorContext){
					HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
					hibernateContext.addExpressionVariable("min", accountProfile.getNameMinLength());
					hibernateContext.addExpressionVariable("max", accountProfile.getNameMaxLength());
				}
				return false;
			}
		}
		return true;
	}
}
