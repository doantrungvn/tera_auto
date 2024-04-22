package org.terasoluna.qp.app.common.validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.domain.service.common.SystemService;

public class QpDateTimeRangeValidator implements
		ConstraintValidator<QpDateTimeRange, String> {

	@Inject
	SystemService systemService;
	
	private ValidationDateTimeRange validation;
	
	private String min;
	private String max;
	private String patternFomrat;
	
	@Override
	public void initialize(QpDateTimeRange constraintAnnotation) {
		this.min = constraintAnnotation.min();
		this.max = constraintAnnotation.max();
		this.patternFomrat = constraintAnnotation.patternFomrat();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(this.patternFomrat)){
			return false;
		}
		this.validation = new ValidationDateTimeRange( min,max, this.patternFomrat);
		return validation.isValid(value);
	}

}
