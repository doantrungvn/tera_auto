package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;

public class QpAlphanumericValidator implements ConstraintValidator<QpAlphanumeric, String>{
	
	@Override
	public void initialize(QpAlphanumeric constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		return ValidationUtils.validateRegex(value, ValidationConst.ALPHANUMERIC_PATTERN);
	}
	
}
