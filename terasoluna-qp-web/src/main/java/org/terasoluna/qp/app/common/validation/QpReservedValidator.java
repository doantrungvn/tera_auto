package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;

public class QpReservedValidator implements ConstraintValidator<QpReserved, String>{

	@Override
	public void initialize(QpReserved constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(value)){
			return true;
		}
		return !FunctionCommon.checkExists(ValidationUtils.reserved, value);
	}

}
