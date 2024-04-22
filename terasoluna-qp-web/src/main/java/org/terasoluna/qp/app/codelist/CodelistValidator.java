package org.terasoluna.qp.app.codelist;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CodelistMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class CodelistValidator implements Validator {
	@Inject 
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (CodeListForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CodeListForm codelist = (CodeListForm) target;
		
		AccountProfile accountProfile = systemService.getDefaultProfile();
		
		// Validate Table Name
		ValidationUtils.validateRequired(codelist.getCodeListName(), errors, CodelistMessageConst.SC_CODELIST_0003);
		ValidationUtils.validateMaxLength(codelist.getCodeListName(), errors,accountProfile.getNameMaxLength(), CodelistMessageConst.SC_CODELIST_0003);
		ValidationUtils.validateMaskName(codelist.getCodeListName(), errors, accountProfile.getNamePattern(), CodelistMessageConst.SC_CODELIST_0003);
		
		/*if(FunctionCommon.equals(codelist.getCodeListName(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0003)}, null);
		}else{
			if(codelist.getCodeListName().length() > accountProfile.getNameMaxLength()){
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0003), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getNamePattern(), codelist.getCodeListName())){
				errors.reject(CommonMessageConst.ERR_SYS_0126, new Object[] { MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0003),accountProfile.getNameMask()}, null);
			}
		}*/
		
		// Validate Table Code
		ValidationUtils.validateRequired(codelist.getCodeListCode(), errors, CodelistMessageConst.SC_CODELIST_0002);
		ValidationUtils.validateMaxLength(codelist.getCodeListCode(), errors,accountProfile.getNameMaxLength(), CodelistMessageConst.SC_CODELIST_0002);
		ValidationUtils.validateMaskCode(codelist.getCodeListCode(), errors, accountProfile.getCodePattern(), CodelistMessageConst.SC_CODELIST_0002);
		
		/*if(FunctionCommon.equals(codelist.getCodeListCode(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0002)}, null);
		}else{
			if(codelist.getCodeListCode().length() > accountProfile.getCodeMaxLength()){
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0002), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getCodePattern(), codelist.getCodeListCode())){
				errors.reject(CommonMessageConst.ERR_SYS_0066, new Object[] { MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0002)}, null);
			}
		}*/
		

		// Validate Remark
		ValidationUtils.validateMaxLength(codelist.getRemark(), errors,accountProfile.getRemarkMaxLength(),CommonMessageConst.SC_SYS_0028);
		/*if(FunctionCommon.isNotEmpty(codelist.getRemark())){
			if(codelist.getRemark().length() > accountProfile.getRemarkMaxLength()){
				errors.reject(CommonMessageConst.ERR_SYS_0067, new Object[] { MessageUtils.getMessage(CommonMessageConst.SC_SYS_0028), accountProfile.getRemarkMinLength(), accountProfile.getRemarkMaxLength() , i}, null);
			}
		}*/
		
		CodeListDetailForm[] codelistdetails = codelist.getCodeListDetails();
		if (codelistdetails == null || codelistdetails.length == 0) {
			// In the case o not setting 
			String[] args =  {MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0007)};
			errors.reject(CommonMessageConst.ERR_SYS_0104, args , null);
			return;
		}
		
		// Check codelist detail is duplicated or not
		Set<String> valueSet = new HashSet<String>();
		Set<String> nameSet = new HashSet<String>();

		int i = 0;
		for(CodeListDetailForm tempValueSet : codelistdetails) {
			i++;
			
			if (codelist.getIsOptionValude() == null ) {
				
				String codeListName = tempValueSet.getName();
				if(StringUtils.isBlank(codeListName)){
					errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0008), i }, null);
				} else {
					String[] args =  { MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0008), String.valueOf(i) };
					ValidationUtils.validateExistInListIgnoreCase(codeListName, nameSet, errors, CommonMessageConst.ERR_SYS_0041, args);
				}
			}
			
			String codeListValue = tempValueSet.getValue();
			if(StringUtils.isBlank(codeListValue)){
				errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0009), i }, null);
			} else {
				String[] args =  { MessageUtils.getMessage(CodelistMessageConst.SC_CODELIST_0009), String.valueOf(i) };
				ValidationUtils.validateExistInListIgnoreCase(codeListValue, valueSet, errors, CommonMessageConst.ERR_SYS_0041, args);
			}
		}
	}
}
