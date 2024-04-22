package org.terasoluna.qp.app.commonobjectdefinition;

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
import org.terasoluna.qp.app.message.CommonObjectDefinitionMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class CommonObjectDefinitionValidator implements Validator {

	@Inject
	SystemService systemService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (CommonObjectDefinitionForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CommonObjectDefinitionForm commonObjectDefinitionForm = (CommonObjectDefinitionForm) target;
		this.validateCommonObjectAttribute(commonObjectDefinitionForm, errors);
	}

	private void validateCommonObjectAttribute(CommonObjectDefinitionForm commonObjectDefinitionForm, Errors errors) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		List<CommonObjectAttributeForm> commonObjectAttributes = commonObjectDefinitionForm.getCommonObjectAttributes();
		if (FunctionCommon.isEmpty(commonObjectAttributes)) {
			errors.reject(CommonMessageConst.ERR_SYS_0104, new Object[] { MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0008) }, null);
		} else {
			int i = 1;
			// Set<String> nameSet = new HashSet<String>();
			Set<String> codeSet = new HashSet<String>();
			for (CommonObjectAttributeForm commonObjectAttribute : commonObjectAttributes) {
				if (commonObjectAttribute.getSaveFlg()) {
					// Check name
					String attributeName = commonObjectAttribute.getCommonObjectAttributeName();
					if (FunctionCommon.isEmpty(attributeName)) {
						errors.reject(CommonMessageConst.ERR_SYS_0120, new Object[] { MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0009), i, MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0008) }, null);
					} else {
						if (attributeName.length() > accountProfile.getNameMaxLength()) {
							errors.reject(CommonMessageConst.ERR_SYS_0095, new Object[] { MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0009), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength(), i }, null);
						}
						if (!Pattern.matches(accountProfile.getNamePattern(), attributeName)) {
							errors.reject(CommonMessageConst.ERR_SYS_0128, new Object[] { MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0008), MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0009), i, accountProfile.getNameMask() }, null);
						}
					}

					// Check code
					String attributeCode = commonObjectAttribute.getCommonObjectAttributeCode();
					if (FunctionCommon.isEmpty(attributeCode)) {
						errors.reject(CommonMessageConst.ERR_SYS_0120, new Object[] { MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0010), i, MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0008) }, null);
					} else {

						ValidationUtils.validateReservedJava(attributeCode, errors, CommonMessageConst.ERR_SYS_0096, new Object[] { MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0010), i });

						if (attributeCode.length() > accountProfile.getCodeMaxLength()) {
							errors.reject(CommonMessageConst.ERR_SYS_0095, new Object[] { MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0010), accountProfile.getCodeMinLength(), accountProfile.getCodeMaxLength(), i }, null);
						}
						if (!Pattern.matches(accountProfile.getCodePattern(), attributeCode)) {
							errors.reject(CommonMessageConst.ERR_SYS_0107, new Object[] { MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0008), MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0010), i }, null);
						}
					}

					// Check data type
					if (commonObjectAttribute.getDataType() == null) {
						errors.reject(CommonMessageConst.ERR_SYS_0120, new Object[] { MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0011), i, MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0008) }, null);
					}

					// Check duplicate name
					// if (nameSet.contains(attributeName)) {
					// String[] args = {
					// MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0009),
					// String.valueOf(i) };
					// errors.reject(CommonMessageConst.ERR_SYS_0041,
					// args,null);
					// } else {
					// if (StringUtils.isNotBlank(attributeName)) {
					// nameSet.add(attributeName);
					// }
					// }

					// Check duplicate code
					if (codeSet.contains(attributeCode)) {
						String[] args = { MessageUtils.getMessage(CommonObjectDefinitionMessageConst.SC_COMMONOBJECTDEFINITION_0010), String.valueOf(i) };
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
}
