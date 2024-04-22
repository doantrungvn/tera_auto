package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;

public class QpSpaceValidator implements ConstraintValidator<QpSpace, String> {

	@Override
	public void initialize(QpSpace constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isEmpty(value)){
			return true;
		}
		return ValidationUtils.validateRegex(value, ValidationConst.SPACE_PATTERN);
	}

}
