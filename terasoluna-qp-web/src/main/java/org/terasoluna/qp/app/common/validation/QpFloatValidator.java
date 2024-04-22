package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class QpFloatValidator implements ConstraintValidator<QpFloat, String> {
	
	@Override
	public void initialize(QpFloat constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		try{
			Float.parseFloat(value);
			return true;
		}catch(Exception e){
			return false;
		}
	}

}
