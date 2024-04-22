package org.terasoluna.qp.app.screendesign;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ScreenDesignMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst;

@Component
public class ScreenItemValidator implements Validator{
	
	@Inject
	Mapper beanMapper;
	
	@Inject 
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (ScreenDesignItemForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ScreenDesignItemForm containScreenItemForm = (ScreenDesignItemForm)target;
		this.checkEmptyItemCodeAndItemName(containScreenItemForm, errors);
		this.checkDuplicateItemCode(containScreenItemForm, errors);
	}
	
	/**
	 * 
	 * @param screenDesignForm
	 * @param errors
	 */
	private void checkEmptyItemCodeAndItemName(ScreenDesignItemForm screenDesignForm, Errors errors){
		AccountProfile accountProfile = systemService.getDefaultProfile();
		String dataType = SessionUtils.getCurrentDatabaseType();
		Integer maxLengOfCode = accountProfile.getSqlCodeMaxLengthByDbType(dataType);
		
		if(null != screenDesignForm && null != screenDesignForm.getScreenItemForms()){
			int countItem = 1;
			for(ScreenItemForm item : screenDesignForm.getScreenItemForms()){
				if (null == item.getDelete()) {
					if(FunctionCommon.equals(item.getMessageCode(), null)){
						errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0194), countItem }, null);
					}
					
					if(!(item.getLogicalDataType().equals(new Long(20)) || item.getLogicalDataType().equals(new Long(21)))){
						if(FunctionCommon.equals(item.getItemCode(), null)){
							errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0093), countItem }, null);
						} else if(item.getItemCode().length() > maxLengOfCode){
							errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0093), accountProfile.getCodeMinLength(), maxLengOfCode}, null);
							
						}
					}
				}
				countItem++;
			}
		}
	}
	
	private void checkDuplicateItemCode(ScreenDesignItemForm screenDesignForm, Errors errors){
		List<ScreenItem> listScreenItem = new ArrayList<ScreenItem>();
		//mapp form to model
		if(null != screenDesignForm && null != screenDesignForm.getScreenItemForms()){
			for(ScreenItemForm item : screenDesignForm.getScreenItemForms()){
				if (null == item.getDelete()) {
					ScreenItem screenItem = beanMapper.map(item, ScreenItem.class);
					if (screenItem.getLogicalDataType() != 20 && screenItem.getItemCode() != null) {
						listScreenItem.add(screenItem);
					}
				}
			}
		}
		Set<Long> s = new HashSet<Long>();
		List<Long> listArea = new ArrayList<Long>();
		
		for(ScreenItem item : listScreenItem){
			s.add(item.getScreenAreaId());
		}
		for (Iterator<Long> it = s.iterator(); it.hasNext(); ) {
	        listArea.add(it.next());
	    }
		
		for(Long itemArea :listArea){
			List<ScreenItem> listItemCode = new ArrayList<ScreenItem>();
			for(ScreenItem item : listScreenItem){
				if(itemArea.equals(item.getScreenAreaId())){
					listItemCode.add(item);
				}
			}
			if(null != listItemCode && listItemCode.size() > 0){
				List<ScreenItem> listDuplicate = findDuplicates(listItemCode);
				String nameArea = listItemCode.get(0).getScreenArea().getMessageDesign().getMessageString();
				for(ScreenItem it : listDuplicate){
					int line = listScreenItem.indexOf(it) + 1;
					errors.reject(ScreenDesignConst.SC_SCREENDESIGN_0195,new Object[] {it.getItemCode(),line,nameArea},null);					
				}
			}
		}
	}
	
	private static List<ScreenItem> findDuplicates(List<ScreenItem> listContainingDuplicates) {
		List<ScreenItem> listToReturn = new ArrayList<ScreenItem>();
		Set<String> set1 = new HashSet<String>();
 
		for (ScreenItem item : listContainingDuplicates) {
			if (!set1.add(item.getItemCode())) {
				listToReturn.add(item);
			}
		}
		return listToReturn;
	}
}
