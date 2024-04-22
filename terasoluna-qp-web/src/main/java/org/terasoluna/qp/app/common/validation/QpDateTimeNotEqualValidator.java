package org.terasoluna.qp.app.common.validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.DateTimeType;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.TypesValidateDateTime;
import org.terasoluna.qp.domain.service.common.SystemService;

public class QpDateTimeNotEqualValidator implements ConstraintValidator<QpDateTimeNotEqual, String> {

	private ValidationCompareDate validation;
	
	@Inject
	SystemService systemService;
	
	private String notEqualValue;
	
	private String patternFomrat;
	
	@Override
	public void initialize(QpDateTimeNotEqual constraintAnnotation) {
		this.notEqualValue = constraintAnnotation.value();
		this.patternFomrat = constraintAnnotation.patternFomrat();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(this.patternFomrat)){
			return false;
		}
		validation = new ValidationCompareDate(notEqualValue, this.patternFomrat);
		return this.validation.isValid(value,DateTimeType.DATETIME,TypesValidateDateTime.NOT_EQUAL);
	}

}
