package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QpEqualValidatorForCharSequence extends MinMaxValidateCharSequence implements ConstraintValidator<QpEqual, CharSequence> {

	@Override
	public void initialize(QpEqual constraintAnnotation) {
		super.initialize(constraintAnnotation.value());
		
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		return super.validateEqual(value);
	}
}
