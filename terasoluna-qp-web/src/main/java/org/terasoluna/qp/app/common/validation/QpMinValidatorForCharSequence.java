package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QpMinValidatorForCharSequence extends MinValidateForCharSequenceBase implements ConstraintValidator<QpMin, CharSequence> {

	@Override
	public void initialize(QpMin constraintAnnotation) {
		super.initialize(constraintAnnotation.value());
	}

	@Override
	public boolean validate(CharSequence value) {
		boolean check = super.validateEqual(value);
		if(check)
			return check;
		else
		return super.handleValidate(value);
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		return super.isValid(value);
	}
}
