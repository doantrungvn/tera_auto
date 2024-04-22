package org.terasoluna.qp.app.functionmaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.FunctionMasterMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.service.common.SystemService;

@Component
public class FunctionMasterValidator implements Validator{
	
	@Inject
	SystemService systemService;

	@Override
	public boolean supports(Class<?> clazz) {
		return FunctionMasterForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FunctionMasterForm functionMasterForm = (FunctionMasterForm) target;
		
		String fileName = functionMasterForm.getFileName();
		
		if (fileName != null) {
			String JAVA_PATTERN = "([^\\s]+(\\.(?i)(java))$)";
			Pattern pattern = Pattern.compile(JAVA_PATTERN);
			Matcher matcher = pattern.matcher(fileName);
			
			if (!matcher.matches()) {
				errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0069, null, null);
			}
		}
		
		ValidationUtils.validateReservedJava(functionMasterForm.getFunctionMasterCode(), errors, CommonMessageConst.ERR_SYS_0018, new Object[] { MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0006)});
		
		this.validateFunctionMethod(functionMasterForm, errors);
		this.validateDuplicate(functionMasterForm, errors);
		try {
			this.checkValidateUpload(functionMasterForm, errors);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void validateFunctionMethod(FunctionMasterForm functionMasterForm, Errors errors) {
		int idxMethod = 1;
		int idxMethodInput = 1;
		int idxMethodOutput = 1;
		
		AccountProfile accountProfile = systemService.getDefaultProfile();
		Pattern patternCode = Pattern.compile(accountProfile.getCodePattern());
		Pattern patternName = Pattern.compile(accountProfile.getNamePattern());
		
		if (functionMasterForm.getFunctionMethod() != null) {
			for (FunctionMethodForm functionMethodForm : functionMasterForm.getFunctionMethod()) {
				String methodInfoIdx = MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0032) + "[" + idxMethod + "]";
				
				// Check method name				
				if (StringUtils.isEmpty(functionMethodForm.getFunctionMethodName())) {
					errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0055, 
							new Object[] {
								methodInfoIdx,
							MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0027)}, null);
				} else if (functionMethodForm.getFunctionMethodName().length() < accountProfile.getNameMinLength()
						|| functionMethodForm.getFunctionMethodName().length() > accountProfile.getNameMaxLength()) {
					errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0056, 
							new Object[] {
								methodInfoIdx,
								MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0027),
								accountProfile.getCodeMinLength(),
								accountProfile.getCodeMaxLength()
							}, null);
				} else if (!patternName.matcher(functionMethodForm.getFunctionMethodName()).matches()) {
					errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0057, 
							new Object[] {
								methodInfoIdx,
								MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0027),
								accountProfile.getNameMask()
							}, null);
				}
				
				// Check method code
				if (StringUtils.isEmpty(functionMethodForm.getFunctionMethodCode())) {
					errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0055, 
							new Object[] {
								methodInfoIdx,
								MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0028)
							}, null);
				} else if (functionMethodForm.getFunctionMethodCode().length() < accountProfile.getCodeMinLength()
						|| functionMethodForm.getFunctionMethodCode().length() > accountProfile.getCodeMaxLength()) {
					errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0056, 
							new Object[] {
								methodInfoIdx,
								MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0028),
								accountProfile.getCodeMinLength(),
								accountProfile.getCodeMaxLength()
							}, null);
				} else if (!patternCode.matcher(functionMethodForm.getFunctionMethodCode()).matches()) {
					errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0058, 
							new Object[] {
								methodInfoIdx,
								MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0028)
							}, null);
				}

				ValidationUtils.validateReservedJava(functionMethodForm.getFunctionMethodCode(), errors, CommonMessageConst.ERR_SYS_0096, new Object[] {MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0028), methodInfoIdx});
				
				// Check function Input/Output
				for (FunctionMethodInputForm functionMethodInputForm : functionMethodForm.getFunctionMethodInput()) {
				    if (functionMethodInputForm.getObjectFlg() != null && functionMethodInputForm.getObjectFlg()){
    					// Check input object name				
    					if (StringUtils.isEmpty(functionMethodInputForm.getMethodInputName())) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0059, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0035),
    									idxMethodInput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0063),
    									methodInfoIdx,
    								}, null);
    					} else if (functionMethodInputForm.getMethodInputName().length() < accountProfile.getCodeMinLength()
    							|| functionMethodInputForm.getMethodInputName().length() > accountProfile.getCodeMaxLength()) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0062, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0035),
    									idxMethodInput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0063),
    									methodInfoIdx,
    									accountProfile.getCodeMinLength(),
    									accountProfile.getCodeMaxLength()
    								}, null);
    					} else if (!patternName.matcher(functionMethodInputForm.getMethodInputName()).matches()) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0060, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0035),
    									idxMethodInput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0063),
    									methodInfoIdx,
    									accountProfile.getNameMask()
    								}, null);
    					}
    					
    					// Check input object code 
    					if (StringUtils.isEmpty(functionMethodInputForm.getMethodInputCode())) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0059, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0036),
    									idxMethodInput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0063),
    									methodInfoIdx,
    								}, null);
    					} else if (functionMethodInputForm.getMethodInputCode().length() < accountProfile.getCodeMinLength()
    							|| functionMethodInputForm.getMethodInputCode().length() > accountProfile.getCodeMaxLength()) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0062, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0036),
    									idxMethodInput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0063),
    									methodInfoIdx,
    									accountProfile.getCodeMinLength(),
    									accountProfile.getCodeMaxLength()
    								}, null);
    					} else if (!patternCode.matcher(functionMethodInputForm.getMethodInputCode()).matches()) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0061, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0036),
    									idxMethodInput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0063),
    									methodInfoIdx
    								}, null);
    					}

    					ValidationUtils.validateReservedJava(functionMethodForm.getFunctionMethodCode(), errors, FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0076, new Object[] {MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0036), methodInfoIdx, FunctionMasterMessageConst.SC_FUNCTIONMASTER_0063, FunctionMasterMessageConst.SC_FUNCTIONMASTER_0032});
    					
    					// Check input object data type
    					if (StringUtils.isEmpty(functionMethodInputForm.getDataType())) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0059, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0037),
    									idxMethodInput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0063),
    									methodInfoIdx,
    								}, null);
    					}
    					
    					idxMethodInput++;
				    }
				}
				
				for (FunctionMethodOutputForm functionMethodOutputForm : functionMethodForm.getFunctionMethodOutput()) {
				    if (functionMethodOutputForm.getObjectFlg() != null && functionMethodOutputForm.getObjectFlg()){
    					// Check output object name				
    					if (StringUtils.isEmpty(functionMethodOutputForm.getMethodOutputName())) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0059, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0035),
    									idxMethodOutput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0064),
    									methodInfoIdx,
    								}, null);
    					} else if (functionMethodOutputForm.getMethodOutputName().length() < accountProfile.getCodeMinLength()
    							|| functionMethodOutputForm.getMethodOutputName().length() > accountProfile.getCodeMaxLength()) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0062, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0035),
    									idxMethodOutput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0064),
    									methodInfoIdx,
    									accountProfile.getCodeMinLength(),
    									accountProfile.getCodeMaxLength()
    								}, null);
    					} else if (!patternName.matcher(functionMethodOutputForm.getMethodOutputName()).matches()) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0060, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0035),
    									idxMethodOutput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0064),
    									methodInfoIdx,
    									accountProfile.getNameMask()
    								}, null);
    					}
    					
    					// Check output object code 
    					if (StringUtils.isEmpty(functionMethodOutputForm.getMethodOutputCode())) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0059, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0036),
    									idxMethodOutput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0064),
    									methodInfoIdx,
    								}, null);
    					} else if (functionMethodOutputForm.getMethodOutputCode().length() < accountProfile.getCodeMinLength()
    							|| functionMethodOutputForm.getMethodOutputCode().length() > accountProfile.getCodeMaxLength()) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0062, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0036),
    									idxMethodOutput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0064),
    									methodInfoIdx,
    									accountProfile.getCodeMinLength(),
    									accountProfile.getCodeMaxLength()
    								}, null);
    					} else if (!patternCode.matcher(functionMethodOutputForm.getMethodOutputCode()).matches()) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0061, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0036),
    									idxMethodOutput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0064),
    									methodInfoIdx
    								}, null);
    					}
    					ValidationUtils.validateReservedJava(functionMethodForm.getFunctionMethodCode(), errors, FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0076, new Object[] {MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0036), methodInfoIdx, FunctionMasterMessageConst.SC_FUNCTIONMASTER_0064, FunctionMasterMessageConst.SC_FUNCTIONMASTER_0032});
    					
    					// Check output object data type
    					if (StringUtils.isEmpty(functionMethodOutputForm.getDataType())) {
    						errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0059, 
    								new Object[] {
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0037),
    									idxMethodOutput,
    									MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0064),
    									methodInfoIdx,
    								}, null);
    					}
    					
    					idxMethodOutput++;
				    }
				}
				
				idxMethod++;
				idxMethodInput = 1;
				idxMethodOutput = 1;
			}
		}
	}
	
	private void validateDuplicate(FunctionMasterForm functionMasterForm,Errors errors) {
		
		List<FunctionMethodForm> functionMethodForms = functionMasterForm.getFunctionMethod();
		
		if(functionMethodForms != null && functionMethodForms .size() > 0){
			
			// Prepare List method name and code
			List<String> name = new ArrayList<String>();
			List<String> code = new ArrayList<String>();
			
			for (FunctionMethodForm methodForm : functionMethodForms) {
				if(methodForm.getFunctionMethodName() != null){
					name.add(methodForm.getFunctionMethodName());
				}
				//QuyND: Remove function method code check to apply overloading 
				/*if(methodForm.getFunctionMethodCode() != null){
					code.add(methodForm.getFunctionMethodCode());
				}*/
			}
			this.checkDuplicateMethod(errors, FunctionMasterMessageConst.SC_FUNCTIONMASTER_0027, FunctionMasterMessageConst.SC_FUNCTIONMASTER_0028, name, code);
			
			int indexMethod = 0;
			// Check duplicate Logic
			for (FunctionMethodForm methodForm : functionMethodForms) {
				indexMethod++;
				// Input
				for (int i = 0; i < methodForm.getFunctionMethodInput().size() - 1; i++) {
					FunctionMethodInputForm methodInputFirst = methodForm.getFunctionMethodInput().get(i);
					
					for (int j = i + 1; j < methodForm.getFunctionMethodInput().size(); j++) {
						FunctionMethodInputForm methodInputSecond = methodForm.getFunctionMethodInput().get(j);
						
						if (FunctionCommon.equals(methodInputFirst.getParentFunctionMethodInputId(), methodInputSecond.getParentFunctionMethodInputId())) {
							if (FunctionCommon.equals(methodInputFirst.getMethodInputCode(), methodInputSecond.getMethodInputCode())) {
								errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0065, 
										new Object[] { MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0036)
										, "[" + (i + 1) + "," + (j+1) + "]", 
										MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0063), 
										MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0032) + "[" + indexMethod + "]" }, null);
							}
						}
					}
				}
				// Output
				for (int i = 0; i < methodForm.getFunctionMethodOutput().size() - 1; i++) {
					FunctionMethodOutputForm methodOutputFirst = methodForm.getFunctionMethodOutput().get(i);
					
					for (int j = i + 1; j < methodForm.getFunctionMethodOutput().size(); j++) {
						FunctionMethodOutputForm methodOutputSecond = methodForm.getFunctionMethodOutput().get(j);
						
						if (FunctionCommon.equals(methodOutputFirst.getParentFunctionMethodOutputId(), methodOutputSecond.getParentFunctionMethodOutputId())) {
							if (FunctionCommon.equals(methodOutputFirst.getMethodOutputCode(), methodOutputSecond.getMethodOutputCode())) {
								errors.reject(FunctionMasterMessageConst.ERR_FUNCTIONMASTER_0065, 
										new Object[] { MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0036)
										, "[" + (i + 1) + "," + (j+1) + "]", 
										MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0064), 
										MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0032) + "[" + indexMethod + "]" }, null);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @param errors
	 * @param functionMethodForms
	 */
	private void checkDuplicateMethod(Errors errors, String messageName, String messageCode, List<String> name, List<String> code) {
		
		// Get all table list is not duplicate
		List<String> functionName = findDuplicates(name);
		//List<String> functionCode = findDuplicates(code);
		
		if (functionName.size() > 0) {
			
			for (String elm : functionName) {
				
				List<String> posDuplicate = new ArrayList<String>();

				for (int i = 0; i < name.size(); i++) {
					if (elm.equals(name.get(i))) {
						posDuplicate.add(String.valueOf(i+1));
					}
				}
				// Add error
				if (posDuplicate.size() > 1) {
					String allName = posDuplicate.toString();
					errors.reject(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0038,new Object[] { MessageUtils.getMessage(messageName), allName }, null);
				}
			}
		}
		
		/*if (functionCode.size() > 0) {
			
			for (String elm : functionCode) {
				
				List<String> posDuplicate = new ArrayList<String>();

				for (int i = 0; i < code.size(); i++) {
					if (elm.equals(code.get(i))) {
						posDuplicate.add(code.get(i));
					}
				}
				// Add error
				if (posDuplicate.size() > 1) {
					String allCode = posDuplicate.toString();
					errors.reject(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0038,new Object[] { MessageUtils.getMessage(messageCode), allCode }, null);
				}
			}
		}*/
	}

	public static List<String> findDuplicates(List<String> listStringCheckDuplicate) {

		HashSet<String> set = null;
		List<String> duplicate = null;
		List<String> listFunctionId = new ArrayList<String>();

		for (String elm : listStringCheckDuplicate) {
			listFunctionId.add(elm);
		}

		set = new HashSet<>(listFunctionId);
		duplicate = new ArrayList<>(set);

		return duplicate;
	}
	
	public void checkValidateUpload(FunctionMasterForm functionMasterForm,Errors errors) throws IOException {
		if(StringUtils.isEmpty(functionMasterForm.getFileName())){
				if(functionMasterForm.getFile() != null && StringUtils.isEmpty(functionMasterForm.getFile().getBytes().toString())){
					String[] args =  {MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0029)};
					errors.reject(CommonMessageConst.ERR_SYS_0025, args , null);
			}
		}
	}
}
