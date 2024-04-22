package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class QpHiraganaValidator implements
		ConstraintValidator<QpHiragana, String> {

	@Override
	public void initialize(QpHiragana constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		char [] characters = value.toCharArray();
		for(char ch : characters){
			if(Character.UnicodeBlock.of(ch).equals(Character.UnicodeBlock.KATAKANA)){
				return false;
			}
		}
		return true;
	}

}
