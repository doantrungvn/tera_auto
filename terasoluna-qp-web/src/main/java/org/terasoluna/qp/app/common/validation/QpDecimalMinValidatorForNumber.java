package org.terasoluna.qp.app.common.validation;

import java.lang.annotation.Annotation;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.DecimalMin;
import org.hibernate.validator.internal.constraintvalidators.DecimalMinValidatorForNumber;

/**
 * Check that the number being validated is less than or equal to the maximum
 * value specified.
 *
 * @author Hardy Ferentschik
 * @author Xavier Sosnovsky
 */
public class QpDecimalMinValidatorForNumber implements
		ConstraintValidator<QpDecimalMin, Number> {

	private DecimalMinValidatorForNumber decimalMinValidatorForNumber;

	@Override
	public void initialize(QpDecimalMin constraintAnnotation) {
		this.decimalMinValidatorForNumber = new DecimalMinValidatorForNumber();
		DecimalMinImplement decimalMinImplement = new DecimalMinImplement(
				constraintAnnotation);
		this.decimalMinValidatorForNumber.initialize(decimalMinImplement);
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		return this.decimalMinValidatorForNumber.isValid(value, context);
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
