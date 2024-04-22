package org.terasoluna.qp.app.menudesign;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.MenuDesignMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class MenuDesignValidator implements Validator {

	@Inject 
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (MenuDesignForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		MenuDesignForm MenuDesignForm = (MenuDesignForm) target;
		this.validateMenuName(MenuDesignForm, errors);
	}
	
	/**
	 * 
	 * @param menuDesignForm
	 * @param errors
	 */
	private void validateMenuName(MenuDesignForm menuDesignForm, Errors errors) {
		
		List<MenuDesignItemForm> menuDesignItemForms = menuDesignForm.getListMenuDesignItem();
		
		AccountProfile accountProfile = systemService.getDefaultProfile();
		String patterName = accountProfile.getNamePattern();
		String nameMask = accountProfile.getNameMask();
		
		if(StringUtils.isBlank(menuDesignForm.getHeaderMenuName())){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(MenuDesignMessageConst.SC_MENUDESIGN_0011)}, null);
		} else {
			if(!Pattern.matches(patterName, menuDesignForm.getHeaderMenuName())){
				errors.reject(CommonMessageConst.ERR_SYS_0126, new Object[] { MessageUtils.getMessage(MenuDesignMessageConst.SC_MENUDESIGN_0011), nameMask}, null);
			}
		}
		
		if(FunctionCommon.isEmpty(menuDesignItemForms)){
			String[] args =  {MessageUtils.getMessage(MenuDesignMessageConst.SC_MENUDESIGN_0005)};
			errors.reject(CommonMessageConst.ERR_SYS_0104, args , null);

			return;
		}

		Set<String> nameSet = new HashSet<String>();
		int index = 0;

		for (MenuDesignItemForm menuDesignItemForm : menuDesignItemForms) {
			index++;
			//if is separator don't validate
			if (DbDomainConst.MenuItemType.SEPARATOR.equals(menuDesignItemForm.getMenuItemType())) {
				continue;
			}
			
			String menuName = menuDesignItemForm.getMenuName();
			
			//checknull
			if( StringUtils.isBlank(menuName)){
				errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(MenuDesignMessageConst.SC_MENUDESIGN_0002), index }, null);
				continue;
			} else if(!Pattern.matches(patterName, menuName)){
				errors.reject(CommonMessageConst.ERR_SYS_0127, new Object[] { MessageUtils.getMessage(MenuDesignMessageConst.SC_MENUDESIGN_0002), nameMask, index }, null);
			}
			
			// Check max length
			if(menuName.length() > DbDomainConst.MAX_VAL_MESSAGE){
				errors.reject(CommonMessageConst.ERR_SYS_0021, new Object[] { MessageUtils.getMessage(MenuDesignMessageConst.SC_MENUDESIGN_0002), index , DbDomainConst.MAX_VAL_MESSAGE}, null);
				continue;
			}

			if (nameSet.contains(menuName)) {
				Object[] args =  { MessageUtils.getMessage(MenuDesignMessageConst.SC_MENUDESIGN_0002), index };
				errors.reject(CommonMessageConst.ERR_SYS_0041, args , null);
			} else {
				nameSet.add(menuName);
			}
		}
	}
}
