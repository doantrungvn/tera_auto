package org.terasoluna.qp.app.common.validation;

import static org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst.MAX_VAL_DAY;
import static org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst.MIN_VAL_DAY;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;

public class QpDayValidator implements
		ConstraintValidator<QpDay, String> {

	
	@Override
	public void initialize(QpDay constraintAnnotation) {
		//TODO
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		return ValidationUtils.validateBetweenTwoNumbers(value,MIN_VAL_DAY, MAX_VAL_DAY);
	}

}
