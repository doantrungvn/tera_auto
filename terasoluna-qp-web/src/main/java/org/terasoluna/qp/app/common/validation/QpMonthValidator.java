package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;

public class QpMonthValidator implements ConstraintValidator<QpMonth, String> {
	
	@Override
	public void initialize(QpMonth constraintAnnotation) {
		//TODO
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		
		return ValidationUtils.validateBetweenTwoNumbers(value, TerasolunaQPValidationConst.MIN_VAL_MONTH, TerasolunaQPValidationConst.MAX_VAL_MONTH);
	}

}
