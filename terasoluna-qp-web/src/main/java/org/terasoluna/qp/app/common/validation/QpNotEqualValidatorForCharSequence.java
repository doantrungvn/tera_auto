package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QpNotEqualValidatorForCharSequence extends MinMaxValidateCharSequence implements ConstraintValidator<QpNotEqual, CharSequence> {

	@Override
	public void initialize(QpNotEqual constraintAnnotation) {
		super.initialize(constraintAnnotation.value());
		
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		return super.validateNotEqual(value);
	}
}
