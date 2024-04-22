package org.terasoluna.qp.app.decision;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DecisionTableMessageConst;
import org.terasoluna.qp.domain.model.DecisionTableInputBean;
import org.terasoluna.qp.domain.model.DecisionTableOutputBean;
import org.terasoluna.qp.domain.service.decisiontable.DecisionTableUtils;

@Component
public class DecisionTableValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return (DecisionTableForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		DecisionTableForm decisionTableForm = (DecisionTableForm) target;
		this.validateRequireInput(decisionTableForm, errors);
		this.validateRequireOutput(decisionTableForm, errors);
	}

	@SuppressWarnings("unchecked")
	private void validateRequireOutput(DecisionTableForm decisionTableForm, Errors errors) {
		List<DecisionTableOutputBean> outputBeanLst = null;
		if(!StringUtils.isEmpty(decisionTableForm.getListOutput())){
			outputBeanLst = (List<DecisionTableOutputBean>) DecisionTableUtils.toObjectLst(decisionTableForm.getListOutput(), DecisionTableOutputBean.class);
		};

		if (!CollectionUtils.isEmpty(outputBeanLst)) {
			int idx = 0;
			for (DecisionTableOutputBean item : outputBeanLst) {
				idx++;
				if(item.getDecisionOutputBeanName().length() < DbDomainConst.MIN_VAL_INPUT 
						|| item.getDecisionOutputBeanName().length() > DbDomainConst.MAX_LENGTH_OF_NAME) {
					errors.reject(CommonMessageConst.ERR_SYS_0095, 
							new Object[] { MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0040) , DbDomainConst.MIN_VAL_INPUT , DbDomainConst.MAX_LENGTH_OF_NAME ,idx}, null);
				}
				
				if(item.getDecisionOutputBeanCode().length() < DbDomainConst.MIN_VAL_INPUT 
						|| item.getDecisionOutputBeanCode().length() > DbDomainConst.MAX_LENGTH_OF_CODE) {
					errors.reject(CommonMessageConst.ERR_SYS_0095, 
							new Object[] { MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0039) , DbDomainConst.MIN_VAL_INPUT , DbDomainConst.MAX_LENGTH_OF_CODE ,idx}, null);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void validateRequireInput(DecisionTableForm decisionTableForm, Errors errors) {
		List<DecisionTableInputBean> inputBeanLst = null;
		if(!StringUtils.isEmpty(decisionTableForm.getListInput())){
			inputBeanLst = (List<DecisionTableInputBean>) DecisionTableUtils.toObjectLst(decisionTableForm.getListInput(), DecisionTableInputBean.class);
		};

		if (!CollectionUtils.isEmpty(inputBeanLst)) {
			int idx = 0;
			for (DecisionTableInputBean item : inputBeanLst) {
				idx++;
				if(item.getDecisionInputBeanName().length() < DbDomainConst.MIN_VAL_INPUT 
						|| item.getDecisionInputBeanName().length() > DbDomainConst.MAX_LENGTH_OF_NAME) {
					errors.reject(CommonMessageConst.ERR_SYS_0095, 
							new Object[] { MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0036) , DbDomainConst.MIN_VAL_INPUT , DbDomainConst.MAX_LENGTH_OF_NAME ,idx}, null);
				}
				
				if(item.getDecisionInputBeanCode().length() < DbDomainConst.MIN_VAL_INPUT 
						|| item.getDecisionInputBeanCode().length() > DbDomainConst.MAX_LENGTH_OF_CODE) {
					errors.reject(CommonMessageConst.ERR_SYS_0095, 
							new Object[] { MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0037) , DbDomainConst.MIN_VAL_INPUT , DbDomainConst.MAX_LENGTH_OF_CODE ,idx}, null);
				}
			}
		}
	}
}
