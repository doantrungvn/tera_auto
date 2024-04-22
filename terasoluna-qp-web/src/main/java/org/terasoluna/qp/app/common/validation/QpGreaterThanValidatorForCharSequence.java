package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QpGreaterThanValidatorForCharSequence extends MinValidateForCharSequenceBase implements ConstraintValidator<QpGreaterThan, CharSequence> {

	@Override
	public void initialize(QpGreaterThan constraintAnnotation) {
		super.initialize(constraintAnnotation.value());
		
	}

	@Override
	public boolean validate(CharSequence value) {
		return super.handleValidate(value);
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		return super.isValid(value);
	}
}
