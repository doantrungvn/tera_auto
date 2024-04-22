package org.terasoluna.qp.domain.service.generatesourcecode;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.LoopComponent;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Component("LoopComponentGenerateHandler")
public class LoopComponentGenerateHandler extends SequenceLogicGenerationHandler {
	@Inject
	DetailServiceImpHandler detailServiceImpHandler;
	private LoopComponent currentComponent;
	private static final String SPACE = " ";

	@Override
	public void handle(StringBuilder builder, BLogicHandlerIo paramOfParent) {
		StringBuilder sb = new StringBuilder();

		preGencode(builder, paramOfParent);
		LoopComponent loopComponent = currentComponent;
		BusinessDesign blogic = paramOfParent.getBusinessDesign();
		String child = paramOfParent.getContent();
		// Modify by HungHX
		String param = StringUtils.EMPTY;
		String condPreCheck = StringUtils.EMPTY;
		switch (loopComponent.getLoopType()) {
			case 0:

				//get parameter
				if(loopComponent.getParameterScope() != null){
					switch(loopComponent.getParameterScope()) {
						case 0:
							//input
							for (InputBean in : blogic.getLstInputBean()) {
								if (DataTypeUtils.equals(in.getInputBeanId(), loopComponent.getParameterId())) {
									param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramOfParent.getmAllParentAndSeflByLevelOfInOutObj(), true, in.getInputBeanId(), 0, paramOfParent.getBlogicInSyntax(), null);
									break;
								}
							}
							break;
						case 1:
							//ob
							for (ObjectDefinition ob : blogic.getLstObjectDefinition()) {
								if (DataTypeUtils.equals(ob.getObjectDefinitionId(), loopComponent.getParameterId())) {
									param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramOfParent.getmAllParentAndSeflByLevelOfInOutObj(), true, ob.getObjectDefinitionId(), 1, paramOfParent.getBlogicObSyntax(), null);
									break;
								}
							}
							break;
						default:
							break;
					}
				}

				String variable = loopComponent.getIndex();
				String from = "";
				String to = "";
				String condition = "";
				String step = "";
				String prefixIndex = "";

				if (loopComponent.getFromScope() != null) {
					if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_CUSTOMIZE.equals(loopComponent.getFromScope())) {
						from = loopComponent.getFromValue();
					} else if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP.equals(loopComponent.getFromScope())) {
						LoopComponent loopComponentTemp = detailServiceImpHandler.getLoopComponentBySequence(detailServiceImpHandler.getAllLoopComponents(), loopComponent.getFromValue());
						from = loopComponentTemp.getIndex();
					}else if(BusinessDesignConst.ParameterIndex.INDEX_TYPE_LENGTH.equals(loopComponent.getFromScope())){
						from = param + ".size()";
						condPreCheck = param + StringUtils.SPACE + "!= null";
					} else {
						prefixIndex = getPrefix(loopComponent.getFromScope(), paramOfParent);
						String paramOther = detailServiceImpHandler.getterAndSetterOfParameter(0, paramOfParent.getmAllParentAndSeflByLevelOfInOutObj(), true, loopComponent.getFromValue(), loopComponent.getFromScope(), prefixIndex, loopComponent.getLstFromIndex());
						from = paramOther;
						condPreCheck = paramOther + StringUtils.SPACE + "!= null";
					}
				}

				if (loopComponent.getToScope() != null) {
					if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_CUSTOMIZE.equals(loopComponent.getToScope())) {
						to = loopComponent.getToValue();
					} else if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP.equals(loopComponent.getToScope())) {
						LoopComponent loopComponentTemp = detailServiceImpHandler.getLoopComponentBySequence(detailServiceImpHandler.getAllLoopComponents(), loopComponent.getToValue());
						to = loopComponentTemp.getIndex();
					}else if(BusinessDesignConst.ParameterIndex.INDEX_TYPE_LENGTH.equals(loopComponent.getToScope())){
						to = param + ".size()";
						if (StringUtils.isNotEmpty(condPreCheck)) condPreCheck = condPreCheck + StringUtils.SPACE + "&&" + StringUtils.SPACE;
						condPreCheck = condPreCheck +  param + StringUtils.SPACE + "!= null";
					}
					else {
						prefixIndex = getPrefix(loopComponent.getToScope(), paramOfParent);
						String paramOther = detailServiceImpHandler.getterAndSetterOfParameter(0, paramOfParent.getmAllParentAndSeflByLevelOfInOutObj(), true, loopComponent.getToValue(), loopComponent.getToScope(), prefixIndex, loopComponent.getLstToIndex());
						to = paramOther;
						if (StringUtils.isNotEmpty(condPreCheck)) condPreCheck = condPreCheck + StringUtils.SPACE + "&&" + StringUtils.SPACE;
						condPreCheck = condPreCheck + paramOther + StringUtils.SPACE + "!= null";
					}
				}

				if(BusinessDesignConst.LOOP_STEP_DESC.equals(loopComponent.getLoopStepType())){
					condition = variable +">" + to;
					step = variable + "-=" + (loopComponent.getLoopStepValue()==null?1:loopComponent.getLoopStepValue());
				}else if(BusinessDesignConst.LOOP_STEP_ASC.equals(loopComponent.getLoopStepType())) {
					condition = variable +"<" + to;
					step = variable + "+=" + (loopComponent.getLoopStepValue()==null?1:loopComponent.getLoopStepValue());
				}

				//for
				sb.append("\n\t\t");
				// Adding check null and size in the case configuration get size from list object.
				if(StringUtils.isNotEmpty(condPreCheck)) sb.append(String.format("if(%s) {",condPreCheck));
				sb.append("\n\t\t");
				sb.append(String.format("for (int %s = %s; %s; %s) {", variable, from, condition, step));
				sb.append("\n\t\t");
				sb.append(child);
				sb.append("\n\t\t");
				sb.append("\n\t\t}");
				// Adding check null and size in the case configuration get size from list object.
				if(StringUtils.isNotEmpty(condPreCheck)) sb.append("\n\t\t}");
				break;
			case 1:
				//while
				List<String> result = detailServiceImpHandler.generateConditionByFormula(paramOfParent, loopComponent.getFormulaDefinitionDetails());

				String sWhile = String.format("while(%s) {", result.get(0));
				
				if(StringUtils.isNotEmpty(result.get(1))) sb.append("\n\t\t").append(result.get(1));
				sb.append("\n\t\t");
				sb.append(sWhile);
				sb.append("\n\t\t");
				sb.append(child);
				sb.append("\n\t\t}");
				break;
			default:
				break;
		}

		builder.append(sb);
		postGencode(builder, paramOfParent);
	}

	public LoopComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(LoopComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	private String getPrefix(Integer targetScope, BLogicHandlerIo paramOfParent) {
		if (GenerateSourceCodeConst.TypeScope.INPUTBEAN == targetScope) {
			return paramOfParent.getBlogicInSyntax();
		} else if (GenerateSourceCodeConst.TypeScope.OUTPUTBEAN == targetScope) {
			return paramOfParent.getBlogicOutputSyntax();
		} else {
			return paramOfParent.getBlogicObSyntax();
		}
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start loop node");
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
			builder.append("// End loop node");
			builder.append(KEY_NL);
		}
	}
}
