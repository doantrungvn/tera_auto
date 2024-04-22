package org.terasoluna.qp.app.subjectarea;

import java.util.ArrayList;
import java.util.List;
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
import org.terasoluna.qp.app.message.SubjectAreaMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class SubjectAreaValidator implements Validator {
	
	@Inject 
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (SubjectAreaForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SubjectAreaForm subjectAreaForm = (SubjectAreaForm) target;
		AccountProfile accountProfile = systemService.getDefaultProfile();
		
		// Validate Table Name
		if(FunctionCommon.equals(subjectAreaForm.getAreaName(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0004)}, null);
		}else{
			if(subjectAreaForm.getAreaName().length() > accountProfile.getNameMaxLength()){
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0004), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getNamePattern(), subjectAreaForm.getAreaName())){
				errors.reject(CommonMessageConst.ERR_SYS_0126, new Object[] { MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0004),accountProfile.getNameMask()}, null);
			}
		}

		// Validate Table Code
		if(FunctionCommon.equals(subjectAreaForm.getAreaCode(), null)){
			errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0005)}, null);
		}else{
			if(subjectAreaForm.getAreaCode().length() > DbDomainConst.MAX_LENGTH_OF_CODE){
				errors.reject(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0005), DbDomainConst.MIN_VAL_INPUT, DbDomainConst.MAX_LENGTH_OF_CODE}, null);
			}
			if(!Pattern.matches(accountProfile.getCodePattern(), subjectAreaForm.getAreaCode())){
				errors.reject(CommonMessageConst.ERR_SYS_0066, new Object[] { MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0005)}, null);
			}
		}
		
		this.validateTableListDuplicate(subjectAreaForm, errors);
		this.validateKeywordDuplicate(subjectAreaForm, errors);
	}

	private void validateTableListDuplicate(SubjectAreaForm subjectAreaForm, Errors errors) {
		List<SubjectAreaTableDesignForm> tableLst = subjectAreaForm.getTableLst();
		if(tableLst == null) {
			return;
		}
		
		int count = 0;
		
		List<String> listOfTableName = new ArrayList<String>();
		
		for (SubjectAreaTableDesignForm subjectAreaTableDesignForm : tableLst) {
			count++;
			String tableName = subjectAreaTableDesignForm.getTableDesignIdAutocomplete();
			if (StringUtils.isEmpty(tableName)) {
				errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0006), count }, null);
				continue;
			}
			
			if (listOfTableName.contains(tableName)) {
				String[] args =  { MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0006), String.valueOf(count) };
				errors.reject(CommonMessageConst.ERR_SYS_0041, args , null);
				continue;
			}
			
			listOfTableName.add(tableName);
		}
	}

	private void validateKeywordDuplicate(SubjectAreaForm subjectAreaForm, Errors errors) {
		List<SubjectAreaKeywordForm> keywordLst = subjectAreaForm.getKeywordLst();
		if(keywordLst == null){
			return;
		}

		int count = 0;
		List<String> listOfKeyWord = new ArrayList<String>();

		for (SubjectAreaKeywordForm subjectAreaKeywordForm : keywordLst) {
			count++;
			String keyWord = subjectAreaKeywordForm.getKeyword();
			
			if(StringUtils.isBlank(keyWord)){
				errors.reject(CommonMessageConst.ERR_SYS_0077, new Object[] { MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0008), count }, null);
				continue;
			}

			if (StringUtils.length(keyWord) > DbDomainConst.MAX_VAL_OTHER) {
				errors.reject(CommonMessageConst.ERR_SYS_0021, new Object[] { MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0008), count }, null);
				continue;
			}

			if (listOfKeyWord.contains(keyWord)) {
				String[] args = {MessageUtils.getMessage(SubjectAreaMessageConst.SC_SUBAREADESIGN_0008), String.valueOf(count) };
				errors.reject(CommonMessageConst.ERR_SYS_0041, args , null);
				continue;
			}

			listOfKeyWord.add(keyWord);
		}
	}
}
