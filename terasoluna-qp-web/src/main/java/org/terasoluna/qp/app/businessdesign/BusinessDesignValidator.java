package org.terasoluna.qp.app.businessdesign;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.BusinessDesignMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.FunctionDesignMessageConst;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Component
public class BusinessDesignValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return (BusinessDesignForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BusinessDesignForm businessDesignForm = (BusinessDesignForm) target;
		//fix temp : Use custom validation for only register form
		if(!BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(businessDesignForm.getBlogicType())){
			if(BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE.equals(businessDesignForm.getBlogicType())){
				// check module
				if(businessDesignForm.getModuleId()==null){
					errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0024)}, null);
				}
				// check return type
				if(businessDesignForm.getReturnType()==null){
					errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0023)}, null);
				}
				// check request method
				if(businessDesignForm.getRequestMethod()==null){
					errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0227)}, null);
				}
			}else if(BusinessDesignConst.BLOGIC_TYPE_STANDARD.equals(businessDesignForm.getBlogicType())){
				// check module
				if(businessDesignForm.getModuleId()==null){
					errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0024)}, null);
				}
				// check function design
				if(businessDesignForm.getFunctionDesignId()==null){
					errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0002)}, null);
				}
				
				//in case of online
				if(BusinessDesignConst.MODULE_TYPE_ONLINE.equals(businessDesignForm.getModuleType())){
					// check return type
					if(businessDesignForm.getReturnType()==null){
						errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0023)}, null);
					}
					
//					// check screen
//					if(businessDesignForm.getScreenId() == null){
//						errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005)},null);
//					}
//					// In case of process
//					if(BusinessDesignConst.REQUEST_METHOD_PROCESSING.equals(businessDesignForm.getRequestMethod())){
//						//check screen form
//						if(businessDesignForm.getScreenFormId() == null){
//							errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0251)},null);
//						}
//					}
				}else{
					// check batch type
					if(businessDesignForm.getBatchType()==null){
						errors.reject(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0169)}, null);
					}
				}

			}
		}
		
		for (InputBean bean : businessDesignForm.getLstInputBean()) {
			ValidationUtils.validateReservedJava(bean.getInputBeanCode(), errors, CommonMessageConst.ERR_SYS_0096, new Object[] { "[" + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0025) + "] " + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0038), bean.getItemSequenceNo() + 1});
		}
		
		for (OutputBean bean : businessDesignForm.getLstOutputBean()) {
			ValidationUtils.validateReservedJava(bean.getOutputBeanCode(), errors, CommonMessageConst.ERR_SYS_0096, new Object[] { "[" + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0026) + "] " + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0038), bean.getItemSequenceNo() + 1});
		}
		
		for (ObjectDefinition bean : businessDesignForm.getLstObjectDefinition()) {
			ValidationUtils.validateReservedJava(bean.getObjectDefinitionCode(), errors, CommonMessageConst.ERR_SYS_0096, new Object[] { "[" + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0026) + "] " + MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0038), bean.getItemSequenceNo() + 1});
		}
	}

}
