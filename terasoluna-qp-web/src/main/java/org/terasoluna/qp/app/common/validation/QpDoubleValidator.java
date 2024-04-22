package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class QpDoubleValidator implements ConstraintValidator<QpDouble, String> {
	
	@Override
	public void initialize(QpDouble constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		try{
			Double.parseDouble(value);
			return true;
		}catch(Exception e){
			return false;
		}
	}

}
