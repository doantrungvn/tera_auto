package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class QpMaxValidatorForNumber extends MaxValidateForNumberBase implements ConstraintValidator<QpMax, Number> {

	@Override
	public void initialize(QpMax constraintAnnotation) {
		super.initialize(constraintAnnotation.value());
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		return super.isValid(value);
	}

	@Override
	boolean validate(Number value) {
		boolean check = super.validateEqual(value);
		if(check)
			return check;
		else
			return super.handlingValidate(value);
	}
}

