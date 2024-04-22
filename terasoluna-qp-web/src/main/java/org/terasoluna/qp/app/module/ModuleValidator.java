package org.terasoluna.qp.app.module;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ModuleMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class ModuleValidator implements Validator {
	
	@Inject 
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (ModuleForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ModuleForm module = (ModuleForm) target;
		AccountProfile accountProfile = systemService.getDefaultProfile();
		
		// Validate Module Name
		if(FunctionCommon.equals(module.getModuleName(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0007)}, null);
		}else{
			if(module.getModuleName().length() > accountProfile.getNameMaxLength()){
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0007), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getNamePattern(), module.getModuleName())){
				errors.reject(CommonMessageConst.ERR_SYS_0126, new Object[] { MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0007),accountProfile.getNameMask()}, null);
			}
		}
		
		// Validate Module Code
		if(FunctionCommon.equals(module.getModuleCode(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0008)}, null);
		}else{
			if(module.getModuleCode().length() > accountProfile.getCodeMaxLength()){
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0008), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getCodePattern(), module.getModuleCode())){
				errors.reject(CommonMessageConst.ERR_SYS_0066, new Object[] { MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0008)}, null);
			}

			ValidationUtils.validateReservedJava(module.getModuleCode(), errors, CommonMessageConst.ERR_SYS_0018, new Object[] { MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0008)});
		}
		
		if(module.getModuleType() != null && module.getModuleType().equals(0)) {
			if(module.getDefaultGenerationSetting() != null && module.getDefaultGenerationSetting().equals(0)) {
				validateGenerationSetting(errors, module);
			}
		}
		
	}

	/**
	 * @param errors
	 * @param module
	 */
	private void validateGenerationSetting(Errors errors, ModuleForm module) {
		ModuleTableMappingForm[] moduleTableMappingForms = module.getModuleTableMappings();
		if (moduleTableMappingForms == null || !module.getDefaultGeneration()) {
			// must be ignored
			return;
		}
		
		if (ArrayUtils.isEmpty(module.getScreenPatternTypes()) && FunctionCommon.isEmpty(module.getModuleId()) && (moduleTableMappingForms.length >= 1)) {
			String[] args = { MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0017)};
			errors.reject(CommonMessageConst.ERR_SYS_0025, args , null);
		}
		
		for (int i = 1; i <= moduleTableMappingForms.length; i++) {
			String tableName = moduleTableMappingForms[i-1].getTblDesignId();
			if(FunctionCommon.equals(tableName, null)){
				errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage("sc.screendesign.0014"), i }, null);
			}
			
			String tableMappingType = moduleTableMappingForms[i-1].getTableMappingType();
			if(FunctionCommon.equals(tableMappingType, null)){
				errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage("sc.module.0015"), i }, null);
			}
		}
		
		// Check module table mapping is duplicated or not
		List<String> valueSet = new ArrayList<String>();
		int i = 1;
		if(moduleTableMappingForms.length == 0){
			String[] args = { MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0020)};
			errors.reject(ModuleMessageConst.ERR_MODULE_0001, args , null);
		}else{
			for(ModuleTableMappingForm tempValueSet : moduleTableMappingForms) {
				if (valueSet.contains(tempValueSet.getTblDesignId())) {
					String[] args =  { MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0016), String.valueOf(i)};
					errors.reject(CommonMessageConst.ERR_SYS_0041, args , null);
				} else {
					if(StringUtils.isNotBlank(tempValueSet.getTblDesignId())) {
						valueSet.add(tempValueSet.getTblDesignId());
					}
				}
				i++;
			}
		}
	}
}
