package org.terasoluna.qp.app.common.validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.domain.service.common.SystemService;

public class QpTimeValidator implements ConstraintValidator<QpTime, String> {
	
	@Inject
	SystemService systemService;
	
	private String patternFomrat;
	
	@Override
	public void initialize(QpTime constraintAnnotation) {
		this.patternFomrat = constraintAnnotation.patternFomrat();

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			return true;
		}
		if(StringUtils.isBlank(this.patternFomrat)){
			return false;
		}
		return ValidationUtils.validateDateTimeFormat(value, this.patternFomrat);
	}

}
