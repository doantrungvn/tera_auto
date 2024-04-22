package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QpGreaterThanValidatorForNumber extends MinValidateForNumberBase implements ConstraintValidator<QpGreaterThan, Number> {

	@Override
	public void initialize(QpGreaterThan constraintAnnotation) {
		super.initialize(constraintAnnotation.value());
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		return super.isValid(value);
	}

	@Override
	public boolean validate(Number value) {
		return super.handlingValidate(value);
	}

	
}