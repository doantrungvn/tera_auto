package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.DateTimeType;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.TypesValidateDateTime;

public class QpTimeMaxValidator implements ConstraintValidator<QpTimeMax, String> {
	
	private ValidationCompareDate validation;
	
	private String maxValue;
	
	String patternFomrat;
	
	@Override
	public void initialize(QpTimeMax constraintAnnotation) {
		this.maxValue = constraintAnnotation.value();
		this.patternFomrat = constraintAnnotation.patternFomrat();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(this.patternFomrat)){
			return false;
		}
		validation = new ValidationCompareDate(maxValue, this.patternFomrat);
		return this.validation.isValid(value,DateTimeType.TIME ,TypesValidateDateTime.BEFORE);
	}

}
