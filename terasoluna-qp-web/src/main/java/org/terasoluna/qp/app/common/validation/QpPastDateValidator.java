package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.DateTimeType;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.TypesValidateDateTime;

public class QpPastDateValidator implements
		ConstraintValidator<QpPastDate, String> {

	private ValidationCompareDate validation;
	
	private String patternFomrat;

	public void initialize(QpPastDate constraintAnnotation) {
		this.patternFomrat = constraintAnnotation.patternFomrat();
	}

	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if(StringUtils.isBlank(this.patternFomrat)){
			return false;
		}
		this.validation = new ValidationCompareDate(this.patternFomrat);
		return this.validation.isValid(value,DateTimeType.DATE,TypesValidateDateTime.PAST);
	}
}
