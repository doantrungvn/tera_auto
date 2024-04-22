package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;

public class QpZenkakuNumericValidator implements
		ConstraintValidator<QpZenkakuNumeric, String> {
	
	
	@Override
	public void initialize(QpZenkakuNumeric constraintAnnotation) {
		//TODO

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		return ValidationUtils.validateRegexForEachCharacter(value, ValidationConst.ZENKAKUNUMERIC_PATTERN);
	}

}
