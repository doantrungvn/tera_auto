package org.terasoluna.qp.app.common.validation;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.DecimalMin;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a number whose value must be higher or
 * equal to the specified minimum.
 * <p/>
 * Supported types are:
 * <ul>
 *     <li>{@code BigDecimal}</li>
 *     <li>{@code BigInteger}</li>
 *     <li>{@code CharSequence}</li>
 *     <li>{@code byte}, {@code short}, {@code int}, {@code long}, and their respective
 *     wrappers</li>
 * </ul>
 * Note that {@code double} and {@code float} are not supported due to rounding errors
 * (some providers might provide some approximative support).
 * <p/>
 * {@code null} elements are considered valid.
 *
 * @author Emmanuel Bernard
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {QpDecimalMinValidatorForCharSequence.class, QpDecimalMinValidatorForNumber.class})
public @interface QpDecimalMin {

	String message() default "{javax.validation.constraints.DecimalMin.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * The {@code String} representation of the min value according to the
	 * {@code BigDecimal} string representation.
	 *
	 * @return value the element must be higher or equal to
	 */
	String value();

	/**
	 * Specifies whether the specified minimum is inclusive or exclusive.
	 * By default, it is inclusive.
	 *
	 * @return {@code true} if the value must be higher or equal to the specified minimum,
	 *         {@code false} if the value must be higher
	 *
	 * @since 1.1
	 */
	boolean inclusive() default true;

	/**
	 * Defines several {@link DecimalMin} annotations on the same element.
	 *
	 * @see DecimalMin
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {

		QpDecimalMin[] value();
	}
}
