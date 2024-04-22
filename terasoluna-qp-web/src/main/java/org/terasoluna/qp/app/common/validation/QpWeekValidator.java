package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;

public class QpWeekValidator implements ConstraintValidator<QpWeek, Number> {

	@Override
	public void initialize(QpWeek constraintAnnotation) {
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if(value == null)
			return true;
		int num = value.intValue();
		int min = TerasolunaQPValidationConst.MIN_VAL_WEEK;
		int max = TerasolunaQPValidationConst.MAX_VAL_WEEK;
		return min <= num && num <= max;
	}

}
