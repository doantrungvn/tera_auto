package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;

public class QpMinuteNumberValidator implements ConstraintValidator<QpMinute, Number> {
	
	@Override
	public void initialize(QpMinute constraintAnnotation) {
		//TODO
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if(value == null){
			return true;
		}
		int num = value.intValue();
		int min =  TerasolunaQPValidationConst.MIN_VAL_MINUTE;
		int max = TerasolunaQPValidationConst.MAX_VAL_MINUTE;
		return min <= num && num <= max;
	}

}
