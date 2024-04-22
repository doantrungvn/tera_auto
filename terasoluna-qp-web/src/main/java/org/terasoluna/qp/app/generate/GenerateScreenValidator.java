package org.terasoluna.qp.app.generate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ModuleMessageConst;
import org.terasoluna.qp.app.module.ModuleTableMappingForm;

@Component
public class GenerateScreenValidator implements Validator {

	@Override
    public boolean supports(Class<?> clazz) {
		return (GenerateScreenForm.class).isAssignableFrom(clazz);
    }

	@Override
    public void validate(Object target, Errors errors) {
		GenerateScreenForm module = (GenerateScreenForm) target;
		ModuleTableMappingForm[] moduleTableMappingForms = module.getModuleTableMappings();
		if (moduleTableMappingForms == null || moduleTableMappingForms.length == 0) {
			String[] args = { MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0020)};
			errors.reject(ModuleMessageConst.ERR_MODULE_0001, args , null);
		}
		// Validate Screen pattern types
		if (ArrayUtils.isEmpty(module.getScreenPatternTypes()) && FunctionCommon.isEmpty(module.getModuleId()) && (moduleTableMappingForms.length >= 1)) {
			String[] args = { MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0017)};
			errors.reject(CommonMessageConst.ERR_SYS_0025, args , null);
		}
		// Validate Table name, Entity type required
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
