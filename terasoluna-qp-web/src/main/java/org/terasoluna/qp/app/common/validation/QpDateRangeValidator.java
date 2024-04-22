package org.terasoluna.qp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class QpDateRangeValidator implements ConstraintValidator<QpDateRange, String> {
	
	private ValidationDateTimeRange validation;
	
	private String min;
	private String max;
	
	private String patternFomrat;
	
	@Override
	public void initialize(QpDateRange constraintAnnotation) {
		this.min = constraintAnnotation.min();
		this.max = constraintAnnotation.max();
		this.patternFomrat = constraintAnnotation.patternFomrat();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(this.patternFomrat)){
			return false;
		}
		this.validation = new ValidationDateTimeRange(min,max, this.patternFomrat);
		return validation.isValid(value);
	}
}
