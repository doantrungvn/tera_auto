package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;

public class QpKatakanaValidator implements ConstraintValidator<QpKatakana, String> {
	
	@Override
	public void initialize(QpKatakana constraintAnnotation) {
		//TODO
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		return ValidationUtils.validateRegexForEachCharacter(value,ValidationConst.KATAKANA_PATTERN);
	}

}
