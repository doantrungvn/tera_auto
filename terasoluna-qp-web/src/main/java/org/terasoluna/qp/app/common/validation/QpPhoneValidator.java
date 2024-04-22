package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;

public class QpPhoneValidator implements ConstraintValidator<QpPhone, String> {
	
	@Override
	public void initialize(QpPhone constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		boolean check = ValidationUtils.validateRegex(value, ValidationConst.PHONE_PATTERN) ||
				ValidationUtils.validateRegex(value, ValidationConst.MOBILE_PHONE_JAPAN_PATTERN_TYPE_1) ||
				ValidationUtils.validateRegex(value, ValidationConst.MOBILE_PHONE_JAPAN_PATTERN_TYPE_2) ||
				ValidationUtils.validateRegex(value, ValidationConst.LINE_PHONE_JAPAN_PATTERN_TYPE_1) ||
				ValidationUtils.validateRegex(value, ValidationConst.LINE_PHONE_JAPAN_PATTERN_TYPE_2);
		return check;
	}

}
