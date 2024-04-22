package org.terasoluna.qp.app.autocomplete;

import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.AutocompleteMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.sqldesign.InputForm;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

/**
 * @author anlt
 *
 */
@Component
public class AutocompleteAdvancedDesignFormValidator implements Validator {

	@Inject
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (AutocompleteAdvancedDesignForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AutocompleteAdvancedDesignForm autocompleteDesignForm = (AutocompleteAdvancedDesignForm) target;
		this.validateInputForm(autocompleteDesignForm.getInputForm(),errors);
		this.validateOutputForm(autocompleteDesignForm.getOutputForm(),errors);
	}

	private void validateOutputForm(OutputForm outputForm, Errors errors) {
		if(StringUtils.isEmpty(outputForm.getSubmitColumn())){
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0016,
					new String[]{
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0020)
								},
					"");
		}
		if(!outputForm.getOutput1Display() &&
				!outputForm.getOutput2Display() &&
				!outputForm.getOutput3Display() &&
				!outputForm.getOutput4Display() &&
				!outputForm.getOutput5Display() &&
				!outputForm.getOutput6Display() &&
				!outputForm.getOutput7Display() &&
				!outputForm.getOutput8Display() &&
				!outputForm.getOutput9Display() &&
				!outputForm.getOutput10Display() &&
				!outputForm.getOutput11Display() &&
				!outputForm.getOutput12Display() &&
				!outputForm.getOutput13Display() &&
				!outputForm.getOutput14Display() &&
				!outputForm.getOutput15Display() &&
				!outputForm.getOutput16Display() &&
				!outputForm.getOutput17Display() &&
				!outputForm.getOutput18Display() &&
				!outputForm.getOutput19Display() &&
				!outputForm.getOutput20Display()){
			errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0015,
					new String[]{
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
								MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0019)
								},
					"");
		}
	}

	private void validateInputForm(InputForm[] inputForm, Errors errors) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		if (ArrayUtils.isNotEmpty(inputForm) && accountProfile != null) {
			Pattern codePattern = Pattern.compile(accountProfile.getCodePattern());
			String codeMask = accountProfile.getCodeMask();
			Pattern namePattern = Pattern.compile(accountProfile.getNamePattern());
			String nameMask = accountProfile.getNameMask();
			int nameMinSize = accountProfile.getNameMinLength();
			int nameMaxSize = accountProfile.getNameMaxLength();
			int codeMinSize = accountProfile.getCodeMinLength();
			int codeMaxSize = accountProfile.getCodeMaxLength();
			
			for (int i = 0; i < inputForm.length; i++) {
				if (StringUtils.isEmpty(inputForm[i].getSqlDesignInputName())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME)
							},
							null);
				} else {
					if(inputForm[i].getSqlDesignInputName().length()<nameMinSize || inputForm[i].getSqlDesignInputName().length()>nameMaxSize){
						errors.reject(CommonMessageConst.ERR_SYS_0108, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
											inputForm[i].getItemSeqNo(),
											nameMinSize,nameMaxSize
								},
								null);
					}
					if(!namePattern.matcher(inputForm[i].getSqlDesignInputName()).matches()){
						errors.reject(CommonMessageConst.ERR_SYS_0107, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
											inputForm[i].getItemSeqNo(),
											nameMask
								},
								null);
					}
				}
				
				if (StringUtils.isEmpty(inputForm[i].getSqlDesignInputCode())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE)
							},
							null);
				} else {
					if(inputForm[i].getSqlDesignInputCode().length()<codeMinSize || inputForm[i].getSqlDesignInputCode().length()>codeMaxSize){
						errors.reject(CommonMessageConst.ERR_SYS_0108, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
											inputForm[i].getItemSeqNo(),
											codeMinSize,codeMaxSize
								},
								null);
					}
					if(!codePattern.matcher(inputForm[i].getSqlDesignInputCode()).matches()){
						errors.reject(CommonMessageConst.ERR_SYS_0107, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
											inputForm[i].getItemSeqNo(),
											codeMask
								},
								null);
					}
				}
				
				for(int j=0;j<i;j++) {
					if(DataTypeUtils.equals(inputForm[i].getSqlDesignInputName(),inputForm[j].getSqlDesignInputName())) {
						errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
									inputForm[i].getGroupIndex()
								}, 
								null);
						break;
					}
				}
				for(int j=0;j<i;j++) {
					if(DataTypeUtils.equals(inputForm[i].getSqlDesignInputCode(),inputForm[j].getSqlDesignInputCode())) {
						errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
									inputForm[i].getGroupIndex()
								}, 
								null);
						break;
					}
				}
			}
		}
	}
}
