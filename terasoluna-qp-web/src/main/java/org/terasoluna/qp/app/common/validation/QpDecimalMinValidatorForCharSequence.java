package org.terasoluna.qp.app.common.validation;

import java.lang.annotation.Annotation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.DecimalMinValidatorForCharSequence;
import javax.validation.constraints.DecimalMin;

;

/**
 * @author Hardy Ferentschik
 */
public class QpDecimalMinValidatorForCharSequence implements
		ConstraintValidator<QpDecimalMin, CharSequence> {

	private DecimalMinValidatorForCharSequence decimalMinValidatorForCharSequence;

	public void initialize(QpDecimalMin minValue) {
		this.decimalMinValidatorForCharSequence = new DecimalMinValidatorForCharSequence();
		DecimalMinImplement decimalMinImplement = new DecimalMinImplement(
				minValue);
		this.decimalMinValidatorForCharSequence.initialize(decimalMinImplement);
	}

	public boolean isValid(CharSequence value,
			ConstraintValidatorContext constraintValidatorContext) {
		// null or empty values are valid
		if (StringUtils.isBlank(value)) {
			return true;
		}
		return this.decimalMinValidatorForCharSequence.isValid(value,
				constraintValidatorContext);
	}

	private class DecimalMinImplement implements DecimalMin {

		private QpDecimalMin qpDecimalMin;

		DecimalMinImplement(QpDecimalMin qpDecimalMin) {
			this.qpDecimalMin = qpDecimalMin;
		}

		@Override
		public Class<? extends Annotation> annotationType() {
			return this.qpDecimalMin.annotationType();
		}

		@Override
		public String message() {
			return this.qpDecimalMin.message();
		}

		@Override
		public Class<?>[] groups() {
			return this.qpDecimalMin.groups();
		}

		@Override
		public Class<? extends Payload>[] payload() {
			return this.qpDecimalMin.payload();
		}

		@Override
		public String value() {
			return this.qpDecimalMin.value();
		}

		@Override
		public boolean inclusive() {
			return this.qpDecimalMin.inclusive();
		}

	}
}
