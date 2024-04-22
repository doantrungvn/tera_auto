package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.DateTimeType;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.TypesValidateDateTime;

public class QpTimestampNotEqualValidator  implements ConstraintValidator<QpTimestampNotEqual, String> {
	
	private ValidationCompareDate validation;
	
	private String notEqValue;
	
	private String patternFomrat;
	
	@Override
	public void initialize(QpTimestampNotEqual constraintAnnotation) {
		this.notEqValue = constraintAnnotation.value();
		this.patternFomrat = constraintAnnotation.patternFomrat();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(this.patternFomrat)){
			return false;
		}
		validation = new ValidationCompareDate(notEqValue,this.patternFomrat);
		return this.validation.isValid(value,DateTimeType.DATETIME, TypesValidateDateTime.NOT_EQUAL);
	}
}
	