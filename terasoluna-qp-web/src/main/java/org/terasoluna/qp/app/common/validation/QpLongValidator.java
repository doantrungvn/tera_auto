package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class QpLongValidator implements ConstraintValidator<QpLong, String> {
	
	@Override
	public void initialize(QpLong constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		try{
			Long.parseLong(value);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

}
