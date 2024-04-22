package org.terasoluna.qp.app.screendesign;

import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ScreenDesignMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class ScreenDesignEditValidator implements Validator {
	@Inject 
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (ScreenDesignForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ScreenDesignForm screenDesignForm = (ScreenDesignForm) target;
		AccountProfile accountProfile = systemService.getDefaultProfile();
		
		// Validate Screen Name
		if(FunctionCommon.equals(screenDesignForm.getScreenName(), null)) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005)}, null);
		} else {
			if(screenDesignForm.getScreenName().length() > accountProfile.getNameMaxLength()) {
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getNamePattern(), screenDesignForm.getScreenName())){
				errors.reject(CommonMessageConst.ERR_SYS_0126, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005), accountProfile.getNameMask()}, null);
			}
		}
		
		// Validate Screen Code
		if(FunctionCommon.equals(screenDesignForm.getScreenCode(), null)) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007)}, null);
		} else {
			if(screenDesignForm.getScreenCode().length() > accountProfile.getCodeMaxLength()) {
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getCodePattern(), screenDesignForm.getScreenCode())) {
				errors.reject(CommonMessageConst.ERR_SYS_0066, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007, accountProfile.getCodeMask())}, null);
			}
		}
		
		// Validate Module
		if(FunctionCommon.equals(screenDesignForm.getModuleId(), null)) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.screendesign.0018")}, null);
		}
		
		// Validate function design
		if(FunctionCommon.equals(screenDesignForm.getFunctionDesignId(), null)) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.functiondesign.0002")}, null);
		}
		
		// Validate screenPatternType
		if(FunctionCommon.equals(screenDesignForm.getScreenPatternType(), null)) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.screendesign.0009")}, null);
		}
		
		// Validate templateType
		if(FunctionCommon.equals(screenDesignForm.getTemplateType(), null)) {
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.screendesign.0008")}, null);
		}
	}
}
