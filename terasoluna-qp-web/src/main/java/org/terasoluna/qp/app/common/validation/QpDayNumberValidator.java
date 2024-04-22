package org.terasoluna.qp.app.common.validation;

import static org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst.MAX_VAL_DAY;
import static org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst.MIN_VAL_DAY;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QpDayNumberValidator implements
		ConstraintValidator<QpDay, Number> {

	
	@Override
	public void initialize(QpDay constraintAnnotation) {
		//TODO
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if(value == null){
			return true;
		}
		int valueIn = value.intValue();
		return MIN_VAL_DAY <= valueIn && valueIn <= MAX_VAL_DAY;
	}

}
