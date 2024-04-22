package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.DateTimeType;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.TypesValidateDateTime;

public class QpDateNotEqualValidator  implements ConstraintValidator<QpDateNotEqual, String> {
	
	private ValidationCompareDate validation;
	
	private String valueDate;
	
	private String patternFomrat;
	
	@Override
	public void initialize(QpDateNotEqual constraintAnnotation) {
		this.valueDate = constraintAnnotation.value();
		this.patternFomrat = constraintAnnotation.patternFomrat();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(this.patternFomrat)){
			return false;
		}
		validation = new ValidationCompareDate(valueDate, this.patternFomrat);
		return this.validation.isValid(value,DateTimeType.DATE, TypesValidateDateTime.NOT_EQUAL);
	}
}
