package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;

public class QpHourValidator implements ConstraintValidator<QpHour, Number> {

	
	@Override
	public void initialize(QpHour constraintAnnotation) {
		//TODO
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if(value == null){
			return true;
		}
		int num = value.intValue();
		int min = TerasolunaQPValidationConst.MIN_VAL_HOUR;
		int max = TerasolunaQPValidationConst.MAX_VAL_HOUR;
		return min <= num && num <= max;
	}

}
