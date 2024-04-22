package org.terasoluna.qp.app.generatedocument;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.GenerateDocumentMessageConst;
import org.terasoluna.qp.domain.model.GenerateDocumentItem;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst;

/**
 * @author hunghx
 *
 */
@Component
public class GenerateDocumentValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (GenerateDocumentForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		GenerateDocumentForm generateDocumentForm = (GenerateDocumentForm) target;
		this.validateRequireDocumentSelect(generateDocumentForm, errors);
		this.validateRequireModuleSelect(generateDocumentForm, errors);
	}

	private void validateRequireDocumentSelect(GenerateDocumentForm generateDocumentForm, Errors errors) {
		
		if(generateDocumentForm.getSelectType().equals(GenerateDocumentConst.PROJECT_SCOPRE)) {
			
			switch (generateDocumentForm.getGenerateDocumentItem().getDocumentItemParenItemType()) {
			
			case GenerateDocumentConst.GENERATE_PROJECT_RD:
				validateRequireDocumentSelectDetails(generateDocumentForm.getGenerateDocumentProjectTypeRDLst(), errors);
				break;
				
			case GenerateDocumentConst.GENERATE_PROJECT_ED:
				validateRequireDocumentSelectDetails(generateDocumentForm.getGenerateDocumentProjectTypeEDLst(), errors);
				break;
			
			case GenerateDocumentConst.GENERATE_PROJECT_ID:
				break;
			}
		} else if(generateDocumentForm.getSelectType().equals(GenerateDocumentConst.MODULE_SCOPRE)) {
			
			switch (generateDocumentForm.getGenerateDocumentItem().getDocumentItemParenItemType()) {
			
			case GenerateDocumentConst.GENERATE_MODULE_RD:
				break;
				
			case GenerateDocumentConst.GENERATE_MODULE_ED:
				validateRequireDocumentSelectDetails(generateDocumentForm.getGenerateDocumentModuleTypeEDLst(), errors);
				break;
			
			case GenerateDocumentConst.GENERATE_MODULE_ID:
				break;
				
			}
		}
	}
	
	private void validateRequireDocumentSelectDetails(List<GenerateDocumentItem> objs, Errors errors) {
		boolean isErr = true;
		for (GenerateDocumentItem item : objs) {
			if (item.getIsChecked() == true) {
				isErr = false;
				break;
			}
		}

		if (isErr) {
			errors.reject(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0063, 
					new Object[] { MessageUtils.getMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0003)}, null);
		}
	}
	
	private void validateRequireModuleSelect(GenerateDocumentForm generateDocumentForm, Errors errors) {
		if(generateDocumentForm.getSelectType().equals(GenerateDocumentConst.MODULE_SCOPRE)) {
			boolean isError = true;
			for (Module item : generateDocumentForm.getModuleList()) {
				if (item.getSelectedGenerate() == 1) {
					isError = false;
					break;
				}
			}
			
			if (isError) {
				errors.reject(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0062, 
						new Object[] { MessageUtils.getMessage(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0006)}, null);
			}
		}
	}
}
