package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;

public class QpZenkakuSymbolValidator implements
		ConstraintValidator<QpZenkakuSymbol, String> {
	
	@Override
	public void initialize(QpZenkakuSymbol constraintAnnotation) {
		//TODO
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		return ValidationUtils.validateRegexForEachCharacter(value, ValidationConst.ZENKAKUSYMBOL_PATTERN);
	}

}
