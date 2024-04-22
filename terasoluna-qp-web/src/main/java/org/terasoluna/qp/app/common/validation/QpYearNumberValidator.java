package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.terasoluna.qp.app.common.ultils.ValidationUtils;

public class QpYearNumberValidator implements ConstraintValidator<QpYear, Number> {
	
	@Override
	public void initialize(QpYear constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if(value == null)
			return true;
		Integer year = value.intValue();
		return ValidationUtils.validateRegex(year.toString(), ValidationConst.YEAR_PATTERN);
	}

}
