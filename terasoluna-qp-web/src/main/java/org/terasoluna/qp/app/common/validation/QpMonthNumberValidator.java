package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;

public class QpMonthNumberValidator implements ConstraintValidator<QpMonth, Number> {
	
	@Override
	public void initialize(QpMonth constraintAnnotation) {
		//TODO
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if(value == null)
			return true;
		int num = value.intValue();
		int min = TerasolunaQPValidationConst.MIN_VAL_MONTH;
		int max = TerasolunaQPValidationConst.MAX_VAL_MONTH;
		return min <= num && num <= max;
	}

}
