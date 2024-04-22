package org.terasoluna.qp.app.viewdesign;

import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.AutocompleteMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ViewDesignMessageConst;
import org.terasoluna.qp.app.sqldesign.InputForm;
import org.terasoluna.qp.app.sqldesign.OutputForm;
import org.terasoluna.qp.domain.service.common.SystemService;

/**
 * @author anlt
 *
 */
@Component
public class ViewDesignAdvancedDesignFormValidator implements Validator {

	@Inject
	SystemService systemService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (ViewDesignAdvancedDesignForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ViewDesignAdvancedDesignForm viewDesignAdvancedForm = (ViewDesignAdvancedDesignForm) target;
		
		String[] reservedWords = systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType());
		
		if (FunctionCommon.checkExists(reservedWords, viewDesignAdvancedForm.getSqlDesignForm().getSqlDesignCode())) {
			errors.reject(CommonMessageConst.ERR_SYS_0130, new Object[] { MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN_CODE)}, null);
		}
		
		this.validateInputForm(viewDesignAdvancedForm.getInputForm(),errors);
		this.validateOutputForm(viewDesignAdvancedForm.getOutputForm(),errors);
	}

	private void validateOutputForm(OutputForm[] outputForm, Errors errors) {
		if (ArrayUtils.isNotEmpty(outputForm)) {
			Pattern codePattern = Pattern.compile(CommonMessageConst.PATTERN_FOR_CODE);
			Pattern namePattern = Pattern.compile(CommonMessageConst.PATTERN_FOR_NAME);
			
			for (int i = 0; i < outputForm.length; i++) {
				if (StringUtils.isEmpty(outputForm[i].getSqlDesignOutputName())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME)
							},
							null);
				}
//				if(outputForm[i].getSqlDesignOutputName().length()<6 || outputForm[i].getSqlDesignOutputName().length()>200){
//					errors.reject(CommonMessageConst.ERR_SYS_0108, 
//							new Object[] {
//										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
//										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
//										outputForm[i].getItemSeqNo(),
//										6,200
//							},
//							null);
//				}
				if(!namePattern.matcher(outputForm[i].getSqlDesignOutputName()).matches()){
					errors.reject(CommonMessageConst.ERR_SYS_0107, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
										outputForm[i].getItemSeqNo(),
										CommonMessageConst.NAME_INPUTMASK
							},
							null);
				}
				if (StringUtils.isEmpty(outputForm[i].getSqlDesignOutputCode())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE)
							},
							null);
				} 
//				if(outputForm[i].getSqlDesignOutputCode().length()<6 || outputForm[i].getSqlDesignOutputCode().length()>200){
//					errors.reject(CommonMessageConst.ERR_SYS_0108, 
//							new Object[] {
//										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
//										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
//										outputForm[i].getItemSeqNo(),
//										6,200
//							},
//							null);
//				}
				if(!codePattern.matcher(outputForm[i].getSqlDesignOutputCode()).matches()){
					errors.reject(CommonMessageConst.ERR_SYS_0107, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
										outputForm[i].getItemSeqNo(),
										CommonMessageConst.CODE_INPUTMASK
							},
							null);
				}

				ValidationUtils.validateReservedJava(outputForm[i].getSqlDesignOutputCode(), errors, CommonMessageConst.ERR_SYS_0096, new Object[] {MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE), outputForm[i].getItemSeqNo()});
				
				for(int j=0;j<i;j++) {
					if(DataTypeUtils.equals(outputForm[i].getSqlDesignOutputName(),outputForm[j].getSqlDesignOutputName())) {
						errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
									outputForm[j].getItemSeqNo()
								}, 
								null);
						break;
					}
					if(DataTypeUtils.equals(outputForm[i].getSqlDesignOutputCode(),outputForm[j].getSqlDesignOutputCode())) {
						errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OUTPUT),
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
									outputForm[i].getItemSeqNo()
								}, 
								null);
						break;
					}
				}
			}
		}
	}

	private void validateInputForm(InputForm[] inputForm, Errors errors) {
		if (ArrayUtils.isNotEmpty(inputForm)) {
			Pattern codePattern = Pattern.compile(CommonMessageConst.PATTERN_FOR_CODE);
			Pattern namePattern = Pattern.compile(CommonMessageConst.PATTERN_FOR_NAME);
			
			for (int i = 0; i < inputForm.length; i++) {
				if (StringUtils.isEmpty(inputForm[i].getSqlDesignInputName())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME)
							},
							null);
				}
//				if(inputForm[i].getSqlDesignInputName().length()<6 || inputForm[i].getSqlDesignInputName().length()>200){
//					errors.reject(CommonMessageConst.ERR_SYS_0108, 
//							new Object[] {
//										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
//										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
//										inputForm[i].getItemSeqNo(),
//										6,200
//							},
//							null);
//				}
				if(!namePattern.matcher(inputForm[i].getSqlDesignInputName()).matches()){
					errors.reject(CommonMessageConst.ERR_SYS_0107, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
										inputForm[i].getItemSeqNo(),
										CommonMessageConst.NAME_INPUTMASK
							},
							null);
				}
				if (StringUtils.isEmpty(inputForm[i].getSqlDesignInputCode())) {
					errors.reject(AutocompleteMessageConst.ERR_AUTOCOMPLETE_0005, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
										String.valueOf(i + 1),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE)
							},
							null);
				} 
//				if(inputForm[i].getSqlDesignInputCode().length()<6 || inputForm[i].getSqlDesignInputCode().length()>200){
//					errors.reject(CommonMessageConst.ERR_SYS_0108, 
//							new Object[] {
//										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
//										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
//										inputForm[i].getItemSeqNo(),
//										6,200
//							},
//							null);
//				}
				if(!codePattern.matcher(inputForm[i].getSqlDesignInputCode()).matches()){
					errors.reject(CommonMessageConst.ERR_SYS_0107, 
							new Object[] {
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
										MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
										inputForm[i].getItemSeqNo(),
										CommonMessageConst.CODE_INPUTMASK
							},
							null);
				}

				ValidationUtils.validateReservedJava(inputForm[i].getSqlDesignInputCode(), errors, CommonMessageConst.ERR_SYS_0096, new Object[] {MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE), inputForm[i].getItemSeqNo()});
				
				for(int j=0;j<i;j++) {
					if(DataTypeUtils.equals(inputForm[i].getSqlDesignInputName(),inputForm[j].getSqlDesignInputName())) {
						errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_NAME),
									inputForm[j].getItemSeqNo()
								}, 
								null);
						break;
					}
					if(DataTypeUtils.equals(inputForm[i].getSqlDesignInputCode(),inputForm[j].getSqlDesignInputCode())) {
						errors.reject(CommonMessageConst.ERR_SYS_0106, new Object[] {
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_INPUT),
									MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_OBJECT_CODE),
									inputForm[i].getItemSeqNo()
								}, 
								null);
						break;
					}
				}
			}
		}
	}
}
