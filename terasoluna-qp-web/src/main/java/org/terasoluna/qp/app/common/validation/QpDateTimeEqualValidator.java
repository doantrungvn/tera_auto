package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.DateTimeType;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.TypesValidateDateTime;

public class QpDateTimeEqualValidator implements ConstraintValidator<QpDateTimeEqual, String> {

	private ValidationCompareDate validation;
	
	private String equalValue;
	
	private String patternFomrat;
	
	@Override
	public void initialize(QpDateTimeEqual constraintAnnotation) {
		this.patternFomrat = constraintAnnotation.patternFomrat();
		this.equalValue = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(this.patternFomrat)){
			return false;
		}
		validation = new ValidationCompareDate(equalValue, this.patternFomrat);
		return this.validation.isValid(value,DateTimeType.DATETIME,TypesValidateDateTime.EQUAL);
	}

}
