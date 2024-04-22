package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.FeedbackComponent;
import org.terasoluna.qp.domain.model.MessageParameter;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Component("FeedbackComponentGenerateHandler")
public class FeedbackComponentGenerateHandler extends SequenceLogicGenerationHandler {

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;

	private static final String NL = "\n\t\t";
	private static final String MSG_REDIRECT_IN_CONTROLLER_PARTTEN = "{0}.add(\"{1}\"{2});";
	private static final String MSG_CODE_REGREX_PARTTEN = "MessageUtils.getMessage(\"{0}\")";
	private static final Integer PARAMETER_TYPE_MSG_CODE = 0;
	private static final Integer PARAMETER_TYPE_VALUE = 1;
	private static final Integer PARAMETER_SCOPE_IN = 0;
	private static final Integer PARAMETER_SCOPE_OB = 1;

	private FeedbackComponent currentComponent;

	/**
	 * Get content of parameter
	 *
	 * @param list
	 * @param param 
	 * @return
	 */
	private String getContentFrmMsgParamLst(List<MessageParameter> list, BLogicHandlerIo param) {
		StringBuilder contentMsgParam = new StringBuilder();
		String paramScopeTmp = "";

		if (CollectionUtils.isNotEmpty(list)) {
			for (MessageParameter msgParam : list) {
				contentMsgParam.append(", ");
				if (PARAMETER_TYPE_MSG_CODE.equals(msgParam.getParameterType())) {
					contentMsgParam.append(NL).append("\t\t\t").append(MessageFormat.format(MSG_CODE_REGREX_PARTTEN, msgParam.getParameterCode()));
				} else if (PARAMETER_TYPE_VALUE.equals(msgParam.getParameterType())) {
					contentMsgParam.append(NL).append("\t\t").append("\"").append(msgParam.getParameterValue()).append("\"");
				} else {
					Integer scope = msgParam.getParameterScope();

					if (scope.intValue() == PARAMETER_SCOPE_IN) {
						// Modify by HungHX
						paramScopeTmp = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, msgParam.getParameterCode(), 0, param.getBlogicInSyntax(), msgParam.getLstParameterIndex());
					} else if (scope.intValue() == PARAMETER_SCOPE_OB) {
						// Modify by HungHX
						paramScopeTmp = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, msgParam.getParameterCode(), 1, param.getBlogicObSyntax(), msgParam.getLstParameterIndex());
					} else {
						// Modify by HungHX
						paramScopeTmp = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, msgParam.getParameterCode(), 2, param.getBlogicOutputSyntax(), msgParam.getLstParameterIndex());
					}
					contentMsgParam.append(NL).append("\t\t\t").append(paramScopeTmp);
				}
			}
		}

		return contentMsgParam.toString() == null ? "" : contentMsgParam.toString();
	}

	public FeedbackComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(FeedbackComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	@Override
	public void handle(StringBuilder additionParam, BLogicHandlerIo param) {
		BusinessDesign blogic = param.getBusinessDesign();
		// Information message content
		StringBuilder strInforMsg = new StringBuilder();
		// Error message content

		StringBuilder tmp = new StringBuilder("");

		if (currentComponent != null) {
			preGencode(additionParam, param);
			if (StringUtils.isNotEmpty(currentComponent.getType())) {
				String contentMsgParam = null;
				switch (currentComponent.getType()) {
					case "err":
						contentMsgParam = getContentFrmMsgParamLst(currentComponent.getMessageParameter(), param);
						blogic.setStrResultMsg(strInforMsg.append(MessageFormat.format(MSG_REDIRECT_IN_CONTROLLER_PARTTEN, "lstErrorMessages", currentComponent.getMessageCode(), contentMsgParam)).toString());
						tmp.append(NL).append(strInforMsg.toString());
						tmp.append(NL).append("ou.setResultMessages(lstErrorMessages);");
						break;
					case "inf":
						contentMsgParam = getContentFrmMsgParamLst(currentComponent.getMessageParameter(), param);
						blogic.setStrResultMsg(strInforMsg.append(MessageFormat.format(MSG_REDIRECT_IN_CONTROLLER_PARTTEN, "lstInfoMessages", currentComponent.getMessageCode(), contentMsgParam)).toString());
						tmp.append(NL).append(strInforMsg.toString());
						tmp.append(NL).append("ou.setResultMessages(lstInfoMessages);");
						break;
					case "wrn":
						contentMsgParam = getContentFrmMsgParamLst(currentComponent.getMessageParameter(), param);
						blogic.setStrResultMsg(strInforMsg.append(MessageFormat.format(MSG_REDIRECT_IN_CONTROLLER_PARTTEN, "lstWarningMessages", currentComponent.getMessageCode(), contentMsgParam)).toString());
						tmp.append(NL).append(strInforMsg.toString());
						tmp.append(NL).append("ou.setResultMessages(lstWarningMessages);");
						break;
				}
			}
		}
		
		additionParam.append(tmp);
		postGencode(additionParam, param);
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start feedback node");
			builder.append(KEY_NL);
			
			if(this.currentComponent !=null) {
				builder.append("// Label:" + currentComponent.getLabel());
				builder.append(KEY_NL);
				
				String remark = currentComponent.getRemark();
				if (StringUtils.isNotEmpty(remark)) {
					if (org.springframework.util.StringUtils.countOccurrencesOf(remark, "\n") > 0) {
						remark = remark.replace("\n", KEY_NL);
						builder.append(BusinessDesignConst.MULTI_COMMENT_START).append(KEY_NL).append(remark).append(KEY_NL).append(BusinessDesignConst.MULTI_COMMENT_END).append(KEY_NL);
					} else {
						builder.append(BusinessDesignConst.SINGLE_COMMENT_START).append(SPACE).append(remark).append(KEY_NL);
					}
				}
			}
		}
	}

	@Override
	public void postGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// End feedback node");
			builder.append(KEY_NL);
		}
	}
}
