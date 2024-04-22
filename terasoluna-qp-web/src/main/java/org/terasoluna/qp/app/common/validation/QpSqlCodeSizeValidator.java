package org.terasoluna.qp.app.common.validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

public class QpSqlCodeSizeValidator implements ConstraintValidator<QpSqlCodeSize, String> {

	@Inject
	SystemService systemService;

	@Override
	public void initialize(QpSqlCodeSize constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		if (StringUtils.isNotBlank(value)) {
			int length = 0;
			if (value != null) {
				length = value.length();
			}
			if (length < accountProfile.getSqlCodeMinLength() || length > accountProfile.getSqlCodeMaxLength()) {
				if (context instanceof HibernateConstraintValidatorContext) {
					HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
					Integer maxLength = accountProfile.getSqlCodeMaxLength();
					try {
						//if project has config datatype
						String dataType = SessionUtils.getCurrentDatabaseType();
						maxLength = accountProfile.getSqlCodeMaxLengthByDbType(dataType);
					} catch (SystemException ex) {

					}
					hibernateContext.addExpressionVariable("min", accountProfile.getSqlCodeMinLength());
					hibernateContext.addExpressionVariable("max", maxLength);
				}
				return false;
			}
		}
		return true;
	}

}
