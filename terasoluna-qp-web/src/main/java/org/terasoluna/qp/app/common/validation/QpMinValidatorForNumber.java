package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QpMinValidatorForNumber extends MinValidateForNumberBase implements ConstraintValidator<QpMin, Number> {

	@Override
	public void initialize(QpMin constraintAnnotation) {
		super.initialize(constraintAnnotation.value());
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		return super.isValid(value);
	}

	@Override
	public boolean validate(Number value) {
		boolean check = super.validateEqual(value);
		if(check)
			return check;
		else
			return super.handlingValidate(value);
	}

	
}