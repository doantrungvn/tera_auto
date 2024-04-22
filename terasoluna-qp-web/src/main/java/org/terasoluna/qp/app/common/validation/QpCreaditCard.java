package org.terasoluna.qp.app.common.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = {QpCreaditCardValidator.class })
public @interface QpCreaditCard {
	String message() default "{org.hibernate.validator.constraints.CreditCardNumber.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	/**
	 * @return the start index (inclusive) for calculating the checksum. If not specified 0 is assumed.
	 */
	int startIndex() default 0;

	/**
	 * @return the end index (inclusive) for calculating the checksum. If not specified the whole value is considered.
	 */
	int endIndex() default Integer.MAX_VALUE;

	/**
	 * @return The index of the check digit in the input. Per default it is assumed that the check digit is the last
	 * digit of the specified range. If set, the digit at the specified index is used. If set
	 * the following must hold true:
	 * {@code checkDigitIndex > 0 && (checkDigitIndex < startIndex || checkDigitIndex >= endIndex}.
	 */
	int checkDigitIndex() default -1;

	/**
	 * @return Whether non-digit characters in the validated input should be ignored ({@code true}) or result in a
	 * validation error ({@code false}).
	 */
	boolean ignoreNonDigitCharacters() default false;

	/**
	 * Defines several {@code @CreditCardNumber} annotations on the same element.
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		QpCreaditCard[] value();
	}
}
