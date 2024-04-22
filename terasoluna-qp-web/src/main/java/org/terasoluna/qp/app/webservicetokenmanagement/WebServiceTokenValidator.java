package org.terasoluna.qp.app.webservicetokenmanagement;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ProjectMessageConst;
import org.terasoluna.qp.app.message.WebServiceTokenManagementMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;
@Component
public class WebServiceTokenValidator implements Validator {
	@Inject
	SystemService systemService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (WebServiceTokenManagementForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		WebServiceTokenManagementForm webServiceTokenManagementForm = (WebServiceTokenManagementForm)target;
		this.validateWebServiceToken(webServiceTokenManagementForm, errors);
	}
	
	private void validateWebServiceToken(WebServiceTokenManagementForm webServiceTokenManagementForm, Errors errors){
		AccountProfile accountProfile = systemService.getDefaultProfile();
		// Validate project id
		// Validate common function is not correct for Long type, so i have to check it manually 
		if(webServiceTokenManagementForm.getProjectId() == null){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0005) }, null);
		}else{
			String projectId = webServiceTokenManagementForm.getProjectId().toString();
			ValidationUtils.validateRequired(projectId, errors, ProjectMessageConst.SC_PROJECT_0005);
		}

		// Validate client id
		// Fix bug by ticket #790
		String clientId = webServiceTokenManagementForm.getClientId();
		if(StringUtils.isEmpty(clientId)) {
			ValidationUtils.validateRequired(clientId, errors, WebServiceTokenManagementMessageConst.SC_WEBSERVICETOKENMANAGEMENT_0005);
		} else {
			ValidationUtils.validateMaxLength(clientId, errors,accountProfile.getNameMaxLength(), WebServiceTokenManagementMessageConst.SC_WEBSERVICETOKENMANAGEMENT_0005);
			ValidationUtils.validateAccountName(clientId, errors, WebServiceTokenManagementMessageConst.SC_WEBSERVICETOKENMANAGEMENT_0005, systemService.getDefaultProfile().getCodePattern());
		}
				
		// Validate client secret
		String clientSecret = webServiceTokenManagementForm.getClientSecret();
		ValidationUtils.validateRequired(clientSecret, errors, WebServiceTokenManagementMessageConst.SC_WEBSERVICETOKENMANAGEMENT_0006);
		ValidationUtils.validateMaxLength(clientSecret, errors,accountProfile.getNameMaxLength(), WebServiceTokenManagementMessageConst.SC_WEBSERVICETOKENMANAGEMENT_0006);
	}

}
