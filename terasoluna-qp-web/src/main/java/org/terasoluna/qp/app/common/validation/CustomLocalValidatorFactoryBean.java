package org.terasoluna.qp.app.common.validation;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ParameterNameProvider;
import javax.validation.executable.ExecutableValidator;

import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
public class CustomLocalValidatorFactoryBean extends LocalValidatorFactoryBean {
	@Override
	public ParameterNameProvider getParameterNameProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExecutableValidator forExecutables() {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void processConstraintViolations(Set<ConstraintViolation<Object>> violations, Errors errors) {
		if (violations.size() > 1) {
			Set<ConstraintViolation<Object>> violationsTemp = new LinkedHashSet<ConstraintViolation<Object>>();
			Class classValidation = violations.iterator().next().getLeafBean().getClass();
			if(!classValidation.isPrimitive()){
				Field[] arrField = classValidation.getDeclaredFields();
				for (int i = 0; i < arrField.length; i++) {
					for (ConstraintViolation<Object> constraintViolation : violations) {
						if (getPath(constraintViolation.getPropertyPath().toString()).equals(arrField[i].getName())) {
							if (!violationsTemp.contains(constraintViolation))
								violationsTemp.add(constraintViolation);
						}
					}
				}
			}
			if (violationsTemp.size() == violations.size())
				super.processConstraintViolations(violationsTemp, errors);
			else
				super.processConstraintViolations(violations, errors);
		} else {
			super.processConstraintViolations(violations, errors);
		}
		
	}
	private String getPath(String propertyPath)
	{
		int index = propertyPath.indexOf('[');
		if(index >0) {
			return propertyPath.substring(0,index);
		}
		else
			return propertyPath;
	}
	@Override
	public void setValidationMessageSource(MessageSource messageSource) {
		super.setMessageInterpolator(new QpMessageInterpolator(messageSource));
	}
}
