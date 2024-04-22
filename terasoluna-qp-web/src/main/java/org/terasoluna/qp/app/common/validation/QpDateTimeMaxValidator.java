package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.DateTimeType;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.TypesValidateDateTime;

public class QpDateTimeMaxValidator implements ConstraintValidator<QpDateTimeMax, String> {

	private ValidationCompareDate validation;
	
	private String valueMax;
	
	private String patternFomrat;
	
	@Override
	public void initialize(QpDateTimeMax constraintAnnotation) {
		this.patternFomrat = constraintAnnotation.patternFomrat();
		this.valueMax = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(patternFomrat)){
			return false;
		}
		validation = new ValidationCompareDate(valueMax, this.patternFomrat);
		return this.validation.isValid(value,DateTimeType.DATETIME,TypesValidateDateTime.BEFORE);
	}

}
