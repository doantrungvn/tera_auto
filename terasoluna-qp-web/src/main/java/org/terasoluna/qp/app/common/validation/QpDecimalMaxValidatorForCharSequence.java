package org.terasoluna.qp.app.common.validation;

import java.lang.annotation.Annotation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.DecimalMaxValidatorForCharSequence;

import javax.validation.constraints.DecimalMax;

/**
 * Check that the character sequence (e.g. string) being validated represents a
 * number, and has a value less than or equal to the maximum value specified.
 *
 * @author Alaa Nassef
 */
public class QpDecimalMaxValidatorForCharSequence implements
		ConstraintValidator<QpDecimalMax, CharSequence> {

	private DecimalMaxValidatorForCharSequence decimalMaxValidatorForCharSequence;

	public void initialize(QpDecimalMax qpDecimalMax) {
		DecimalMaxImplement decimalMaxImplement = new DecimalMaxImplement(qpDecimalMax);
		this.decimalMaxValidatorForCharSequence = new DecimalMaxValidatorForCharSequence();
		this.decimalMaxValidatorForCharSequence.initialize(decimalMaxImplement);
		
	}

	public boolean isValid(CharSequence value,
			ConstraintValidatorContext constraintValidatorContext) {
		if (StringUtils.isBlank(value)) {
			return true;
		}
		return this.decimalMaxValidatorForCharSequence.isValid(value, constraintValidatorContext);
	}
	
	private class DecimalMaxImplement implements DecimalMax{
		
		private QpDecimalMax qpDecimalMax;
		
		DecimalMaxImplement(QpDecimalMax qpDecimalMax){
			this.qpDecimalMax = qpDecimalMax;
		}

		@Override
		public Class<? extends Annotation> annotationType() {
			return this.qpDecimalMax.annotationType();
		}

		@Override
		public String message() {
			return this.qpDecimalMax.message();
		}

		@Override
		public Class<?>[] groups() {
			return this.qpDecimalMax.groups();
		}

		@Override
		public Class<? extends Payload>[] payload() {
			return this.qpDecimalMax.payload();
		}

		@Override
		public String value() {
			return this.qpDecimalMax.value();
		}

		@Override
		public boolean inclusive() {
			return this.qpDecimalMax.inclusive();
		}
		
	}
	
}
