package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class QpIntegerValidator implements
		ConstraintValidator<QpInteger, String> {
	
	@Override
	public void initialize(QpInteger constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		try{
			Integer.parseInt(value);
			return true;
		}catch (Exception ex){
			return false;
		}
	}

}
