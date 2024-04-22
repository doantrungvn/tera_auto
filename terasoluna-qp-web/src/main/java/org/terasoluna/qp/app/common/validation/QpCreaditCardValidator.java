package org.terasoluna.qp.app.common.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.ModCheckBase;
import org.hibernate.validator.internal.util.ModUtil;

public class QpCreaditCardValidator extends ModCheckBase implements ConstraintValidator<QpCreaditCard, CharSequence> {

	@Override
	public void initialize(QpCreaditCard constraintAnnotation) {
		super.initialize(
				constraintAnnotation.startIndex(),
				constraintAnnotation.endIndex(),
				constraintAnnotation.checkDigitIndex(),
				constraintAnnotation.ignoreNonDigitCharacters()
		);
	}

	@Override
	public boolean isValid(final CharSequence value, final ConstraintValidatorContext context){
		if(StringUtils.isBlank(value)){
			return true;
		}
		return super.isValid(value, context);
	}
	
	@Override
	public boolean isCheckDigitValid(List<Integer> digits, char checkDigit) {
		if ( !Character.isDigit( checkDigit ) ) {
			return false;
		}
		int modResult = ModUtil.calculateLuhnMod10Check( digits );
		int checkValue = extractDigit( checkDigit );
		return checkValue == modResult;
	}
	
}
