package org.terasoluna.qp.app.common.validation;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.SizeValidatorForCharSequence;

public class QpSizeValidator implements ConstraintValidator<QpSize, String> {

	private SizeValidatorForCharSequence sizeValidatorForCharSequence;

	@Override
	public void initialize(QpSize qpSize) {
		this.sizeValidatorForCharSequence = new SizeValidatorForCharSequence();
		SizeImplement sizeImplement = new SizeImplement(qpSize);
		this.sizeValidatorForCharSequence.initialize(sizeImplement);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isBlank(value)) {
			return true;
		}
		return this.sizeValidatorForCharSequence.isValid(value, context);
	}

	private class SizeImplement implements Size {
		private QpSize qpSize;

		SizeImplement(QpSize qpSize) {
			this.qpSize = qpSize;
		}

		@Override
		public Class<? extends Annotation> annotationType() {
			return this.qpSize.annotationType();
		}

		@Override
		public String message() {
			return this.qpSize.message();
		}

		@Override
		public Class<?>[] groups() {
			return this.qpSize.groups();
		}

		@Override
		public Class<? extends Payload>[] payload() {
			return this.qpSize.payload();
		}

		@Override
		public int min() {
			return this.qpSize.min();
		}

		@Override
		public int max() {
			return this.qpSize.max();
		}
	}

}
