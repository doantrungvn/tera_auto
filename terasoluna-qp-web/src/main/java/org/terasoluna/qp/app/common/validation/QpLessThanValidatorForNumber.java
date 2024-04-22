package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class QpLessThanValidatorForNumber extends MaxValidateForNumberBase implements ConstraintValidator<QpLessThan, Number> {

	@Override
	public void initialize(QpLessThan constraintAnnotation) {
		super.initialize(constraintAnnotation.value());
		
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		return super.isValid(value);
	}

	@Override
	boolean validate(Number value) {
		return super.handlingValidate(value);
	}

}

