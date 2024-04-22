package org.terasoluna.qp.app.common.validation;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.DecimalMax;

import org.hibernate.validator.internal.constraintvalidators.DecimalMaxValidatorForNumber;

/**
 * Check that the number being validated is less than or equal to the maximum
 * value specified.
 *
 * @author Hardy Ferentschik
 * @author Xavier Sosnovsky
 */
public class QpDecimalMaxValidatorForNumber implements
		ConstraintValidator<QpDecimalMax, Number> {

	private DecimalMaxValidatorForNumber decimalMaxValidatorForNumber;

	@Override
	public void initialize(QpDecimalMax constraintAnnotation) {
		this.decimalMaxValidatorForNumber = new DecimalMaxValidatorForNumber();
		DecimalMaxImplement deciDemalMaxImplement = new DecimalMaxImplement(constraintAnnotation);
		this.decimalMaxValidatorForNumber.initialize(deciDemalMaxImplement);
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		return this.decimalMaxValidatorForNumber.isValid(value, context);
	}

	private class DecimalMaxImplement implements DecimalMax {

		private QpDecimalMax qpDecimalMax;

		DecimalMaxImplement(QpDecimalMax qpDecimalMax) {
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
