package org.terasoluna.qp.app.project;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ProjectMessageConst;

@Component
public class ProjectValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return (ProjectForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProjectForm projectForm = (ProjectForm) target;
		ValidationUtils.validateEmail(projectForm.getEmailAddress(), errors, ProjectMessageConst.SC_PROJECT_0039);
		ValidationUtils.validatePackageName(projectForm.getPackageName(), errors, ProjectMessageConst.SC_PROJECT_0027,CommonMessageConst.ERR_SYS_0025,ProjectMessageConst.SC_PROJECT_0027,CommonMessageConst.ERR_SYS_0018);
	}
}
