package org.terasoluna.qp.app.externalobjectdefinition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ExternalObjectDefinitionMessageConst;
import org.terasoluna.qp.app.message.ProjectMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class ExternalObjectDefinitionValidator implements Validator {
	@Inject
	SystemService systemService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (ExternalObjectDefinitionForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ExternalObjectDefinitionForm externalObjectDefinitionForm = (ExternalObjectDefinitionForm) target;
		this.validateExternalObjectAttribute(externalObjectDefinitionForm, errors);
		this.validatePackageName(externalObjectDefinitionForm, errors);
	}

	private void validateExternalObjectAttribute(ExternalObjectDefinitionForm externalObjectDefinitionForm, Errors errors) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		List<ExternalObjectAttributeForm> externalObjectAttributes = externalObjectDefinitionForm.getExternalObjectAttributes();
		if (FunctionCommon.isEmpty(externalObjectAttributes)) {
			errors.reject(CommonMessageConst.ERR_SYS_0104, new Object[] { MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0008) }, null);
		} else {
			Set<String> codeSet = new HashSet<String>();
			int i = 1;
			for (ExternalObjectAttributeForm externalObjectAttribute : externalObjectAttributes) {
				if (externalObjectAttribute.getSaveFlg()) {
					// Check name
					String attributeName = externalObjectAttribute.getExternalObjectAttributeName();
					if (FunctionCommon.isEmpty(attributeName)) {
						errors.reject(CommonMessageConst.ERR_SYS_0120, new Object[] { MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0009), i, MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0008) }, null);
					} else {
						if (attributeName.length() > accountProfile.getNameMaxLength()) {
							errors.reject(CommonMessageConst.ERR_SYS_0095, new Object[] { MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0009), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength(), i }, null);
						}
						if (!Pattern.matches(accountProfile.getNamePattern(), attributeName)) {
							errors.reject(CommonMessageConst.ERR_SYS_0128, new Object[] { MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0008), MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0009), i, accountProfile.getNameMask() }, null);
						}
					}

					// Check code
					String attributeCode = externalObjectAttribute.getExternalObjectAttributeCode();
					if (FunctionCommon.isEmpty(attributeCode)) {
						errors.reject(CommonMessageConst.ERR_SYS_0120, new Object[] { MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0010), i, MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0008) }, null);
					} else {

						ValidationUtils.validateReservedJava(attributeCode, errors, CommonMessageConst.ERR_SYS_0096, new Object[] { MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0010), i });

						if (attributeCode.length() > accountProfile.getCodeMaxLength()) {
							errors.reject(CommonMessageConst.ERR_SYS_0095, new Object[] { MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0010), accountProfile.getCodeMinLength(), accountProfile.getCodeMaxLength(), i }, null);
						}
						if (!Pattern.matches(accountProfile.getCodePattern(), attributeCode)) {
							errors.reject(CommonMessageConst.ERR_SYS_0107, new Object[] { MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0008), MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0010), i }, null);
						}
					}

					// Check data type
					if (externalObjectAttribute.getDataType() == null) {
						errors.reject(CommonMessageConst.ERR_SYS_0120, new Object[] { MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0011), i, MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0008) }, null);
					}

					// Check duplicate attribute code
					if (codeSet.contains(attributeCode)) {
						String[] args = { MessageUtils.getMessage(ExternalObjectDefinitionMessageConst.SC_EXTERNALOBJECTDEFINITION_0010), String.valueOf(i) };
						errors.reject(CommonMessageConst.ERR_SYS_0041, args, null);
					} else {
						if (StringUtils.isNotBlank(attributeCode)) {
							codeSet.add(attributeCode);
						}
					}

					i++;
				}
			}
		}
	}

	private void validatePackageName(ExternalObjectDefinitionForm externalObjectDefinitionForm, Errors errors) {
		ValidationUtils.validatePackageName(externalObjectDefinitionForm.getPackageName(), errors, ProjectMessageConst.SC_PROJECT_0027, CommonMessageConst.ERR_SYS_0025, ProjectMessageConst.SC_PROJECT_0027, CommonMessageConst.ERR_SYS_0018);
	}
}
