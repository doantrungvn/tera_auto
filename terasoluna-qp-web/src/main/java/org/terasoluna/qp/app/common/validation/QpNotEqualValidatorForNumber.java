package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class QpNotEqualValidatorForNumber extends MinMaxValidateNumber implements ConstraintValidator<QpNotEqual, Number> {

	@Override
	public void initialize(QpNotEqual constraintAnnotation) {
		super.initialize(constraintAnnotation.value());
		
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		return super.validateNotEqual(value);
	}
}

