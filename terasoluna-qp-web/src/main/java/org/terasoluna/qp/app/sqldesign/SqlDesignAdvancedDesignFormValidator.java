package org.terasoluna.qp.app.sqldesign;

import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.AutocompleteMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

/**
 * @author anlt
 *
 */
@Component
public class SqlDesignAdvancedDesignFormValidator implements Validator {

	@Inject
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (SqlDesignAdvancedDesignForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SqlDesignAdvancedDesignForm sqlDesignAdvancedForm = (SqlDesignAdvancedDesignForm) target;
		this.validateInputForm(sqlDesignAdvancedForm.getInputForm(),errors);
		this.validateOutputForm(sqlDesignAdvancedForm.getOutputForm(),errors);
	}

	private void validateOutputForm(OutputForm[] outputForm, Errors errors) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		if (ArrayUtils.isNotEmpty(outputForm) && accountProfile != null) {
			Pattern codePattern = Pattern.compile(accountProfile.getCodePattern());
			String codeMask = accountProfile.getCodeMask();
			Pattern namePattern = Pattern.compile(accountProfile.getNamePattern());
			String nameMask = accountProfile.getNameMask();
			int nameMinSize = accountProfile.getNameMinLength();
			int nameMaxSize = accountProfile.getNameMaxLength();
			int codeMinSize = accountProfile.getCodeMinLength();
			int codeMaxSize = accountProfile.getCodeMaxLength();
			
			for (int i = 0; i < outputForm.length; i++) {
				if (StringUtils.isEmpty(outputForm[i].getSqlDesignOutputName())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME)
							},
							null);
				} else {
					if(outputForm[i].getSqlDesignOutputName().length()<nameMinSize || outputForm[i].getSqlDesignOutputName().length()>nameMaxSize){
						errors.reject(CommonMessageConst.ERR_SYS_0108, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
											outputForm[i].getItemSeqNo(),
											nameMinSize,nameMaxSize
								},
								null);
					}
					if(!namePattern.matcher(outputForm[i].getSqlDesignOutputName()).matches()){
						errors.reject(CommonMessageConst.ERR_SYS_0107, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
											outputForm[i].getItemSeqNo(),
											nameMask
								},
								null);
					}
				}
				
				if (StringUtils.isEmpty(outputForm[i].getSqlDesignOutputCode())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE)
							},
							null);
				} else {
					if(outputForm[i].getSqlDesignOutputCode().length()<codeMinSize || outputForm[i].getSqlDesignOutputCode().length()>codeMaxSize){
						errors.reject(CommonMessageConst.ERR_SYS_0108, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
											outputForm[i].getItemSeqNo(),
											codeMinSize,codeMaxSize
								},
								null);
					}
					if(!codePattern.matcher(outputForm[i].getSqlDesignOutputCode()).matches()){
						errors.reject(CommonMessageConst.ERR_SYS_0107, 
								new Object[] {
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
											MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
											outputForm[i].getItemSeqNo(),
											codeMask
								},
								null);
					}

					ValidationUtils.validateReservedJava(outputForm[i].getSqlDesignOutputCode(), errors, CommonMessageConst.ERR_SYS_0096, new Object[] {MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE), outputForm[i].getItemSeqNo()});
				}
				
				//Bangnl allow outputs have same name
				/*for(int j=0;j<i;j++) {
					if(DataTypeUtils.equal(outputForm[i].getGroupId(), outputForm[j].getGroupId()) && DataTypeUtils.equal(outputForm[i].getSqlDesignOutputName(),outputForm[j].getSqlDesignOutputName())) {
						errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
									outputForm[i].getGroupIndex()
								}, 
								null);
						break;
					}
				}*/
				for(int j=0;j<i;j++) {
					if(DataTypeUtils.equals(outputForm[i].getGroupId(), outputForm[j].getGroupId()) && DataTypeUtils.equals(outputForm[i].getSqlDesignOutputCode(),outputForm[j].getSqlDesignOutputCode())) {
						errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
									outputForm[i].getGroupIndex()
								}, 
								null);
						break;
					}
				}
			}
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

					ValidationUtils.validateReservedJava(inputForm[i].getSqlDesignInputCode(), errors, CommonMessageConst.ERR_SYS_0096, new Object[] {MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE), inputForm[i].getItemSeqNo()});
				}
				
				//Bangnl Allow inputs have same name
				/*for(int j=0;j<i;j++) {
					if(DataTypeUtils.equal(inputForm[i].getGroupId(), inputForm[j].getGroupId()) && DataTypeUtils.equal(inputForm[i].getGroupId(), inputForm[j].getGroupId()) && DataTypeUtils.equal(inputForm[i].getSqlDesignInputName(),inputForm[j].getSqlDesignInputName())) {
						errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
									inputForm[i].getGroupIndex()
								}, 
								null);
						break;
					}
				}*/
				for(int j=0;j<i;j++) {
					if(DataTypeUtils.equals(inputForm[i].getGroupId(), inputForm[j].getGroupId()) && DataTypeUtils.equals(inputForm[i].getGroupId(), inputForm[j].getGroupId()) && DataTypeUtils.equals(inputForm[i].getSqlDesignInputCode(),inputForm[j].getSqlDesignInputCode())) {
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
