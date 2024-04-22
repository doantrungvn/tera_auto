package org.terasoluna.qp.app.screendesign;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ModuleMessageConst;
import org.terasoluna.qp.app.message.ScreenDesignMessageConst;
import org.terasoluna.qp.app.module.ModuleTableMappingForm;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;

@Component
public class ScreenDesignValidator implements Validator {
	@Inject
	ScreenDesignService screenDesignService;
	
	@Inject 
	SystemService systemService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (ScreenRegisterForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ScreenRegisterForm screenRegisterForm = (ScreenRegisterForm) target;
		AccountProfile accountProfile = systemService.getDefaultProfile();
		
		// Validate Screen Name
		if(FunctionCommon.equals(screenRegisterForm.getScreenName(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005)}, null);
		}else{
			if(screenRegisterForm.getScreenName().length() > accountProfile.getNameMaxLength()){
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getNamePattern(), screenRegisterForm.getScreenName())){
				errors.reject(CommonMessageConst.ERR_SYS_0126, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005), accountProfile.getNameMask()}, null);
			}
		}
		
		// Validate Screen Code
		if(FunctionCommon.equals(screenRegisterForm.getScreenCode(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007)}, null);
		}else{
			if(screenRegisterForm.getScreenCode().length() > accountProfile.getCodeMaxLength()){
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007), accountProfile.getCodeMinLength(), accountProfile.getCodeMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getCodePattern(), screenRegisterForm.getScreenCode())){
				errors.reject(CommonMessageConst.ERR_SYS_0066, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007, accountProfile.getCodeMask())}, null);
			}
		}
		
		// Validate Module
		if(FunctionCommon.equals(screenRegisterForm.getModuleId(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.screendesign.0018")}, null);
		}
		
		// Validate function deisng
		if(FunctionCommon.equals(screenRegisterForm.getFunctionDesignId(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.functiondesign.0002")}, null);
		}
		
		// Validate screenPatternType
		if(FunctionCommon.equals(screenRegisterForm.getScreenPatternType(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.screendesign.0009")}, null);
		}
		
		// Validate templateType
		if(FunctionCommon.equals(screenRegisterForm.getTemplateType(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.screendesign.0008")}, null);
		}
				
		ModuleTableMappingForm[] moduleTableMappings = screenRegisterForm.getModuleTableMappings();
		
		for (int i = 1; i <= moduleTableMappings.length; i++) {
			String tableName = moduleTableMappings[i-1].getTblDesignId();
			if(FunctionCommon.equals(tableName, null)){
				errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage("sc.screendesign.0014"), i }, null);
			}
			
			String tableMappingType = moduleTableMappings[i-1].getTableMappingType();
			if(FunctionCommon.equals(tableMappingType, null)){
				errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage("sc.module.0015"), i }, null);
			}
		}
		
		String screenPatternType = screenRegisterForm.getScreenPatternType();
		ModuleTableMappingForm[] moduleTableMappingForms = screenRegisterForm.getModuleTableMappings();
		Boolean isCopy = screenRegisterForm.getIsCopy();
		Long copyScreenId = screenRegisterForm.getCopyScreenId();
		if(null == screenPatternType) {
			return;
		} else if(!screenPatternType.equals("1")) {
			for(int i = 0; i< moduleTableMappingForms.length; i++) {
				if(moduleTableMappingForms[i].getAreaPatternType() == null || moduleTableMappingForms[i].getAreaPatternType().length() == 0) {
					errors.rejectValue("screenPatternType", null,
							MessageUtils.getMessage("err.sys.0077", MessageUtils.getMessage("sc.screendesign.0016"), i+1));
				}
			}
		}
		if (moduleTableMappingForms == null) {
			return;
		}
		else {
			Set<String> valueSet = new HashSet<String>();
			int i = 0;
			for(ModuleTableMappingForm tempValueSet : moduleTableMappingForms) {
				++i;
				if (valueSet.contains(tempValueSet.getTblDesignId())) {
					String[] args =  { MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0016), String.valueOf(i) };
					errors.reject(CommonMessageConst.ERR_SYS_0041, args , null);
				} else {
					if(StringUtils.isNotBlank(tempValueSet.getTblDesignId())) {
						valueSet.add(tempValueSet.getTblDesignId());
					}
				}
			}
		}
		if(!isCopy) {
			return;
		} else {
			if(null == copyScreenId) {
				errors.rejectValue("copyScreenId", null,
						MessageUtils.getMessage("err.sys.0051", MessageUtils.getMessage("sc.screendesign.0012"), MessageUtils.getMessage("sc.screendesign.0001")));
			}
		}
	}
}
