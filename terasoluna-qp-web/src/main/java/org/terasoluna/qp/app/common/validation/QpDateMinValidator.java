package org.terasoluna.qp.app.common.validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.DateTimeType;
import org.terasoluna.qp.app.common.validation.ValidationCompareDate.TypesValidateDateTime;
import org.terasoluna.qp.domain.service.common.SystemService;

public class QpDateMinValidator  implements ConstraintValidator<QpDateMin, String> {
	
	private ValidationCompareDate validation;
	
	private String minValue;
	
	private String patternFomrat;
	
	@Inject
	SystemService systemService;
	
	@Override
	public void initialize(QpDateMin constraintAnnotation) {
		this.minValue = constraintAnnotation.value();
		this.patternFomrat = constraintAnnotation.patternFomrat();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(this.patternFomrat)){
			return false;
		}
		validation = new ValidationCompareDate(this.minValue, this.patternFomrat);
		return this.validation.isValid(value,DateTimeType.DATE, TypesValidateDateTime.AFTER);
	}
}
