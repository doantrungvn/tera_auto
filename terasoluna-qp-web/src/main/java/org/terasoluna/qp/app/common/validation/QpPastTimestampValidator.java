package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.DateTimeType;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.TypesValidateDateTime;

public class QpPastTimestampValidator implements
		ConstraintValidator<QpPastTimestamp, String> {
	
	ValidationCompareDate validate;
	
	private String patternFomrat;

	public void initialize(QpPastTimestamp constraintAnnotation) {
		this.patternFomrat = constraintAnnotation.patternFomrat();
	}

	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if(StringUtils.isBlank(this.patternFomrat)){
			return false;
		}
		this.validate = new ValidationCompareDate(this.patternFomrat);
		return validate.isValid(value,DateTimeType.DATETIME,TypesValidateDateTime.PAST);
	}
}
