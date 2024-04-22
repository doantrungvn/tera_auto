package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QpLessThanValidatorForCharSequence extends MaxValidateForCharSequenceBase implements ConstraintValidator<QpLessThan, CharSequence> {

	@Override
	public void initialize(QpLessThan constraintAnnotation) {
		super.initialize(constraintAnnotation.value());
		
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		return super.isValid(value);
	}

	@Override
	public boolean validate(CharSequence value) {
		return super.handleValidate(value);
	}

	
}
