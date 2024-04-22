package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.DateTimeType;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.TypesValidateDateTime;

public class QpTimeEqualValidator implements ConstraintValidator<QpTimeEqual, String> {
	
	private ValidationCompareDate validation;
	
	private String equalValue;
	
	private String patternFormat;
	
	@Override
	public void initialize(QpTimeEqual constraintAnnotation) {
		this.equalValue = constraintAnnotation.value();
		this.patternFormat = constraintAnnotation.patternFomrat();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(this.patternFormat)){
			return false;
		}
		validation = new ValidationCompareDate(equalValue, patternFormat);
		return this.validation.isValid(value,DateTimeType.TIME, TypesValidateDateTime.EQUAL);
	}

}
