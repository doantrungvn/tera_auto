package org.terasoluna.qp.app.common.validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

public class QpRemarkSizeValidator  implements ConstraintValidator<QpRemarkSize,String>{

	@Inject
	SystemService systemService;
	
	@Override
	public void initialize(QpRemarkSize constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		if(StringUtils.isNoneBlank(value)){
			int length = 0;
			if(value != null) {
				length = value.length();
			}
			if(length<accountProfile.getRemarkMinLength() || length > accountProfile.getRemarkMaxLength()) {
				if(context instanceof HibernateConstraintValidatorContext){
					HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
					hibernateContext.addExpressionVariable("min", accountProfile.getRemarkMinLength());
					hibernateContext.addExpressionVariable("max", accountProfile.getRemarkMaxLength());
				}
				return false;
			}
		}
		return true;
	}

	

}
