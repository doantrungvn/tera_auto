package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;

public class QpSecondNumberValidator implements ConstraintValidator<QpSecond, Number> {

	
	@Override
	public void initialize(QpSecond constraintAnnotation) {
		//TODO
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if(value == null){
			return true;
		}
		int num = value.intValue();
		int min = TerasolunaQPValidationConst.MIN_VAL_SECOND;
		int max = TerasolunaQPValidationConst.MAX_VAL_SECOND;
		return min <= num && num <= max;
	}

}
