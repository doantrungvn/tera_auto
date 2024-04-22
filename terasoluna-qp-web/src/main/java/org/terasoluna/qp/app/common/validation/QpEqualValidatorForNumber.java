package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class QpEqualValidatorForNumber extends MinMaxValidateNumber implements ConstraintValidator<QpEqual, Number> {

	@Override
	public void initialize(QpEqual constraintAnnotation) {
		super.initialize(constraintAnnotation.value());
		
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		return super.validateEqual(value);
	}
}

