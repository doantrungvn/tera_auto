package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.ComponentType;
import org.terasoluna.qp.app.common.constants.DbDomainConst.JavaDataTypeOfBlogic;
import org.terasoluna.qp.domain.model.BusinessCheckComponent;
import org.terasoluna.qp.domain.model.BusinessCheckDetail;
import org.terasoluna.qp.domain.model.BusinessDetailContent;
import org.terasoluna.qp.domain.model.MessageParameter;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Component("BusinessCheckGenerationHandler")
public class BusinessCheckGenerationHandler extends SequenceLogicGenerationHandler {
	private static final Integer PARAMETER_TYPE_MSG_CODE = 0;
	private static final Integer PARAMETER_TYPE_VALUE = 1;
	private static final Integer PARAMETER_SCOPE_IN = 0;
	private static final Integer PARAMETER_SCOPE_OB = 1;
	private static final String PHASE_CREATE_CONDITION = "org.terasoluna.qp.domain.model.ConditionModel {0} = new org.terasoluna.qp.domain.model.ConditionModel({1}, {2}, {3}, {4});";

	private BusinessCheckComponent currentComponent = new BusinessCheckComponent();

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;

	@Override
	public void handle(StringBuilder builder, BLogicHandlerIo handleParam) {
		
		if(builder!=null){
			if(!(handleParam.getSequenceLogic()==null || handleParam.getSequenceLogic().getComponentType() != ComponentType.BUSINESS_CHECK)){
				if(this.currentComponent !=null){
					preGencode(builder, handleParam);
					builder.append(KEY_LINE_BREAK);
					if(CollectionUtils.isNotEmpty(this.currentComponent.getBusinessCheckDetails())){
						boolean existCheck = false;
						for(BusinessCheckDetail businessCheckDetail:this.currentComponent.getBusinessCheckDetails()){
							switch(businessCheckDetail.getBusinessCheckType()){
								case 1:
									generateCheckFormula(builder, businessCheckDetail, handleParam, this.currentComponent);
									break;
								case 2:
									
								case 3:
									generateCheckCount(builder, businessCheckDetail, handleParam,this.currentComponent);
									break;
							}
							if(BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE.equals(businessCheckDetail.getBusinessCheckType())){
								existCheck = true;
							}
						}
						this.generateThrowBusinessException(builder,this.currentComponent,existCheck);
					}
				}
				postGencode(builder, handleParam);
			}
		}
	}

	private void generateThrowBusinessException(StringBuilder builder, BusinessCheckComponent businessCheckComponent, boolean hasExistCheckFlag) {
		builder.append(KEY_LINE_BREAK);
		builder.append(KEY_TAB_2);
		builder.append(MessageFormat.format(PHASE_IF_BOOLEAN,"lstErrorMessages"+".isNotEmpty()"));
		builder.append(KEY_OPEN_CURLY_BRACKET);
		builder.append(KEY_LINE_BREAK);
		builder.append(KEY_TAB_3);
		if (hasExistCheckFlag){
			builder.append(MessageFormat.format(PHASE_THROW_BUSINESS_EX_WITHCAUSE,"lstErrorMessages","new Throwable(\"0_"+BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE+"\")"));
		} else {
			builder.append(MessageFormat.format(PHASE_THROW_BUSINESS_EX_WITHCAUSE,"lstErrorMessages","new Throwable(\"0_0\")"));
		}
		builder.append(KEY_LINE_BREAK);
		builder.append(KEY_TAB_2);
		builder.append(KEY_CLOSE_CURLY_BRACKET);
	}
	
//	private void generateContentBusinessException(BusinessCheckComponent businessCheckComponent){
//		if(businessCheckComponent.get)
//	}

	private void generateCheckFormula(StringBuilder builder, BusinessCheckDetail businessCheckDetail, BLogicHandlerIo handleParam, BusinessCheckComponent businessCheckComponent) {
		// modify by HungHX
		//String condition = generateConditionByFormula(handleParam.getBusinessDesign(), handleParam.getSequenceLogic(), businessCheckDetail.getFormulaDefinitionId(), handleParam.getFormulaDefinition(), handleParam.getFunctionMasters(), handleParam.getFunctionMethods());
		List<String> result = detailServiceImpHandler.generateConditionByFormula(handleParam, businessCheckDetail.getFormulaDefinitionDetails());
		
		String condition = result.get(0);
		
		if(StringUtils.isNoneEmpty(result.get(1))) {
			builder.append(KEY_LINE_BREAK);
			builder.append(KEY_TAB_2);
			builder.append(result.get(1));
		}
		
		builder.append(KEY_LINE_BREAK);
		builder.append(KEY_TAB_2);
		builder.append("if ("+condition+") {");
		builder.append(KEY_LINE_BREAK);
		builder.append(KEY_TAB_3);

		if (CollectionUtils.isNotEmpty(businessCheckDetail.getParameters())) {
			String contentMsgParam = getContentFrmMsgParamLst(businessCheckDetail.getParameters(), handleParam);
			builder.append(MessageFormat.format(PHASE_CALL_METHOD_2,"lstErrorMessages","add","\""+businessCheckDetail.getMessageCode()+"\"",contentMsgParam));
		} else {
			builder.append(MessageFormat.format(PHASE_CALL_METHOD_1,"lstErrorMessages","add","\""+businessCheckDetail.getMessageCode()+"\""));
		}

		if(businessCheckDetail.isAbortFlg()){
			this.generateThrowBusinessException(builder,businessCheckComponent,false);
		}

		builder.append(KEY_LINE_BREAK);
		builder.append(KEY_TAB_2).append("}");
	}

	private void generateCheckCount(StringBuilder builder,BusinessCheckDetail businessCheckDetail,BLogicHandlerIo handleParam, BusinessCheckComponent businessCheckComponent) {
		if(CollectionUtils.isNotEmpty(businessCheckDetail.getContents())){
			builder.append(KEY_LINE_BREAK);
			builder.append(KEY_TAB_2);
			builder.append(GenerateSourceCodeUtil.normalizedClassName(SqlDesignGenerationHandler.SQL_COUNT_CODE));
			builder.append(SUFFIX_INPUT);
			builder.append(KEY_SPACE);
			builder.append(GenerateSourceCodeUtil.normalizedVariantName(SqlDesignGenerationHandler.SQL_COUNT_CODE));
			builder.append(SUFFIX_INPUT);
			builder.append(businessCheckDetail.getBusinessCheckDetailId());
			builder.append(KEY_SPACE);
			builder.append(KEY_EQUAL);
			builder.append(KEY_SPACE);
			builder.append(KEY_NEW);
			builder.append(KEY_SPACE);
			builder.append(GenerateSourceCodeUtil.normalizedClassName(SqlDesignGenerationHandler.SQL_COUNT_CODE));
			builder.append(SUFFIX_INPUT);
			builder.append(KEY_OPEN_PARENTHESIS);
			builder.append(KEY_CLOSE_PARENTHESIS);
			builder.append(KEY_SEMI_COLON);
			builder.append(KEY_LINE_BREAK);
			builder.append(KEY_TAB_2);
			builder.append(MessageFormat.format(PHASE_SET, GenerateSourceCodeUtil.normalizedVariantName(SqlDesignGenerationHandler.SQL_COUNT_CODE)+SUFFIX_INPUT+businessCheckDetail.getBusinessCheckDetailId(),"TableCode",KEY_DOUBLE_QUOTE+businessCheckDetail.getContents().get(0).getTableCode()+KEY_DOUBLE_QUOTE));
			builder.append(KEY_LINE_BREAK);
			builder.append(KEY_TAB_2);
			// build conditions text
			builder.append(MessageFormat.format("List<org.terasoluna.qp.domain.model.ConditionModel> conditionModels{0} = new ArrayList<>();", String.valueOf(businessCheckDetail.getBusinessCheckDetailId())));
			
			
			Project project = handleParam.getProject();
			
			for (int i=0;i<businessCheckDetail.getContents().size();i++) {
				BusinessDetailContent businessDetailContent = businessCheckDetail.getContents().get(i);
				String columnCode = "\"" + businessDetailContent.getColumnCode() + "\"";
				String operatorCode = "\"" + getOperator(businessDetailContent.getOperatorCode()) + "\"";
				String conditionModelParamId = "param" + businessCheckDetail.getBusinessCheckDetailId() + i;
				
				builder.append(KEY_LINE_BREAK);
				builder.append(KEY_TAB_2);

				builder.append(MessageFormat.format(PHASE_CREATE_CONDITION, conditionModelParamId, columnCode, operatorCode, this.getParameterPath(businessDetailContent, handleParam), "org.terasoluna.qp.domain.model.ConditionModel.LogicCode.AND.getText()"));
				
				if (project.getCaseSensitivity() == NOT_CASE_SENSITIVE && (businessCheckDetail.getContents().get(i).getDataType()==JavaDataTypeOfBlogic.STRING_DATATYPE || businessCheckDetail.getContents().get(i).getDataType()==JavaDataTypeOfBlogic.CHAR_DATATYPE)) {
					builder.append(KEY_LINE_BREAK);
					builder.append(KEY_TAB_2);
					builder.append(MessageFormat.format("{0}.setFunctionCode(org.terasoluna.qp.domain.model.ConditionModel.FunctionCode.UPPER.getCode());", conditionModelParamId));
				}
				
				if (project.getDbType() == DbDomainConst.DatabaseType.ORACLE && (businessCheckDetail.getContents().get(i).getDataType() == JavaDataTypeOfBlogic.DATE_DATATYPE)) {
					builder.append(KEY_LINE_BREAK);
					builder.append(KEY_TAB_2);
					builder.append(MessageFormat.format("{0}.setFunctionCode(org.terasoluna.qp.domain.model.ConditionModel.FunctionCode.TRUNC.getCode());", conditionModelParamId));
				}
				
				if (project.getDbType() == DbDomainConst.DatabaseType.ORACLE && (businessCheckDetail.getContents().get(i).getDataType() == JavaDataTypeOfBlogic.TIME_DATATYPE)) {
					builder.append(KEY_LINE_BREAK);
					builder.append(KEY_TAB_2);
					builder.append(MessageFormat.format("{0}.setFunctionCode(org.terasoluna.qp.domain.model.ConditionModel.FunctionCode.TOCHAR.getCode());", conditionModelParamId));
					builder.append(KEY_LINE_BREAK);
					builder.append(KEY_TAB_2);
					builder.append(MessageFormat.format("{0}.setPattern(org.terasoluna.qp.domain.model.ConditionModel.FORMAT_TIME);", conditionModelParamId));
				}
				
				builder.append(KEY_LINE_BREAK);
				builder.append(KEY_TAB_2);
				builder.append(MessageFormat.format("conditionModels{0}.add({1});", String.valueOf(businessCheckDetail.getBusinessCheckDetailId()), conditionModelParamId));
			}
			builder.append(KEY_LINE_BREAK);
			builder.append(KEY_TAB_2);
			builder.append(MessageFormat.format(PHASE_SET, GenerateSourceCodeUtil.normalizedVariantName(SqlDesignGenerationHandler.SQL_COUNT_CODE)+SUFFIX_INPUT+businessCheckDetail.getBusinessCheckDetailId(),"Conditions", MessageFormat.format("conditionModels{0}", String.valueOf(businessCheckDetail.getBusinessCheckDetailId()))));
			builder.append(KEY_LINE_BREAK);
			builder.append(KEY_TAB_2);
			if(businessCheckDetail.getBusinessCheckType()==CHECK_EXIST){
				builder.append(MessageFormat.format(PHASE_IF, "commonRepository.countTableRowsWithConditions("+GenerateSourceCodeUtil.normalizedVariantName(SqlDesignGenerationHandler.SQL_COUNT_CODE)+SUFFIX_INPUT+businessCheckDetail.getBusinessCheckDetailId()+")",KEY_EQUAL+KEY_EQUAL,0));
			} else {
				if(BusinessDesignConst.SCREEN_PATTERN_MODIFY.equals(handleParam.getBusinessDesign().getPatternType()) || BusinessDesignConst.SCREEN_PATTERN_CONFIRM_MODIFY.equals(handleParam.getBusinessDesign().getPatternType())) {
					builder.append(MessageFormat.format(PHASE_IF, "commonRepository.countTableRowsWithConditions("+GenerateSourceCodeUtil.normalizedVariantName(SqlDesignGenerationHandler.SQL_COUNT_CODE)+SUFFIX_INPUT+businessCheckDetail.getBusinessCheckDetailId()+")",KEY_GREATER_THAN,1));
				} else {
					builder.append(MessageFormat.format(PHASE_IF, "commonRepository.countTableRowsWithConditions("+GenerateSourceCodeUtil.normalizedVariantName(SqlDesignGenerationHandler.SQL_COUNT_CODE)+SUFFIX_INPUT+businessCheckDetail.getBusinessCheckDetailId()+")",KEY_GREATER_EQUAL,1));
				}
			}
			builder.append(KEY_OPEN_CURLY_BRACKET);
			builder.append(KEY_LINE_BREAK);
			builder.append(KEY_TAB_3);

			if(CollectionUtils.isNotEmpty(businessCheckDetail.getParameters())) {
				String contentMsgParam = getContentFrmMsgParamLst(businessCheckDetail.getParameters(), handleParam);
				builder.append(MessageFormat.format(PHASE_CALL_METHOD_2,"lstErrorMessages","add","\""+businessCheckDetail.getMessageCode()+"\"",contentMsgParam));
			} else {
				builder.append(MessageFormat.format(PHASE_CALL_METHOD_1,"lstErrorMessages","add","\""+businessCheckDetail.getMessageCode()+"\""));
			}
			boolean existCheck = false;
			if(BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE.equals(businessCheckDetail.getBusinessCheckType())){
				existCheck = true;
			}
			if(businessCheckDetail.isAbortFlg()){
				this.generateThrowBusinessException(builder,businessCheckComponent,existCheck);
			}

			builder.append(KEY_LINE_BREAK);
			builder.append(KEY_TAB_2);
			builder.append(KEY_CLOSE_CURLY_BRACKET);
		}
	}

	private String getOperator(Integer operatorCode) {
		String operator;
		switch (operatorCode) {
			case 0:
				operator = "=";
				break;
			case 1:
				operator = "<";
				break;
			case 2:
				operator = "<=";
				break;
			case 3:
				operator = ">";
				break;
			case 4:
				operator = ">=";
				break;
			case 5:
				operator = "!=";
				break;
			case 6:
				operator = "LIKE";
				break;
			default:
				operator = "=";
		}

		return operator;
	}

	private String getParameterPath(BusinessDetailContent businessDetailContent, BLogicHandlerIo handleParam) {
		if(businessDetailContent.getBusinessDetailContentId()!=null){
			// Modify by HungHX
//			StringBuilder pathBuilder = new StringBuilder();
//			if(businessDetailContent.getParameterScope()==0){
//				getInputPath(pathBuilder,handleParam.getBusinessDesign().getLstInputBean(),businessDetailContent.getParameterId());
//				return "in." + pathBuilder.toString();
//			} else {
//				getObjectPath(pathBuilder,handleParam.getBusinessDesign().getLstObjectDefinition(),businessDetailContent.getParameterId());
//				return "ob." + pathBuilder.toString();
//			}

			String codeSyntaxScope = (businessDetailContent.getParameterScope() == null)?"":(businessDetailContent.getParameterScope() == 0)?"in":(businessDetailContent.getParameterScope() == 1)?"ob":"ou";
			return detailServiceImpHandler.getterAndSetterOfParameter(0, handleParam.getmAllParentAndSeflByLevelOfInOutObj(), true, businessDetailContent.getParameterId(), businessDetailContent.getParameterScope(), codeSyntaxScope, businessDetailContent.getLstParameterIndex());
		}
		
		return StringUtils.EMPTY;
	}

	// Modify by HungHX
//	private void getInputPath(StringBuilder pathBuilder,List<InputBean> inputBeans, String parameterId) {
//		for(InputBean input : inputBeans){
//			if(DataTypeUtils.equal(input.getInputBeanId(),parameterId)){
//				if(StringUtils.isNotBlank(input.getParentInputBeanId())){
//					getInputPath(pathBuilder,inputBeans,input.getParentInputBeanId());
//					pathBuilder.append(KEY_DOT);
//				}
//				pathBuilder.append("get");
//				pathBuilder.append(GenerateSourceCodeUtil.normalizedClassName(input.getInputBeanCode()));
//				pathBuilder.append("()");
//				break;
//			}
//		}
//	}
//	private void getObjectPath(StringBuilder pathBuilder,List<ObjectDefinition> objectBeans, String parameterId) {
//		for(ObjectDefinition object : objectBeans){
//			if(DataTypeUtils.equal(object.getObjectDefinitionId(),parameterId)){
//				if(StringUtils.isNotBlank(object.getParentObjectDefinitionId())){
//					getObjectPath(pathBuilder,objectBeans,object.getParentObjectDefinitionId());
//					pathBuilder.append(KEY_DOT);
//				}
//				pathBuilder.append("get");
//				pathBuilder.append(GenerateSourceCodeUtil.normalizedClassName(object.getObjectDefinitionCode()));
//				pathBuilder.append("()");
//				break;
//			}
//		}
//	}
//

//	/**
//	 *
//	 * @param blogic
//	 * @param sequenceLogic
//	 * @param formula
//	 * @param formulaDefinition
//	 * @param functionMasters
//	 * @param functionMethods
//	 * @return
//	 */
//	public String generateConditionByFormula(BusinessDesign blogic, SequenceLogic sequenceLogic, Long formulaId, List<FormulaDefinition> formulaDefinition, List<FunctionMaster> functionMasters, List<FunctionMethod> functionMethods) {
//		String condition = "";
//
//		FormulaDefinition formula = null;
//		for (FormulaDefinition f : formulaDefinition) {
//			if (formulaId != null && formulaId.equals(f.getFormulaDefinitionId())) {
//				formula = f;
//				break;
//			}
//		}
//
//		for (FormulaDetail detail : formula.getFormulaDefinitionDetails()) {
//			if (type.containsKey(detail.getType())) {
//				condition += type.get(detail.getType()) + " ";
//			} else {
//				//if is input bean
//				if (detail.getType().equals(18)) {
//					for (InputBean in : blogic.getLstInputBean()) {
//						if (DataTypeUtils.equal(in.getInputBeanId(), detail.getParameterId())) {
//							condition += detailServiceImpHandler.getterAndSetterOfParameter(in.getInputBeanId(), 0, "in", null, true, false) + " ";
//							break;
//						}
//					}
//				}
//				//if is object definition
//				else if (detail.getType().equals(19)) {
//					for (ObjectDefinition ob : blogic.getLstObjectDefinition()) {
//						if (DataTypeUtils.equal(ob.getObjectDefinitionId(), detail.getParameterId())) {
//							condition += detailServiceImpHandler.getterAndSetterOfParameter(ob.getObjectDefinitionId(), 1, "ob", null, true, false) + " ";
//							break;
//						}
//					}
//				}
//				//if is function
//				else if (detail.getType().equals(17)) {
//					FunctionMethod method = null;
//
//					for (FunctionMethod item : functionMethods) {
//						if (detail.getFunctionMethodId() != null && detail.getFunctionMethodId().equals(item.getFunctionMethodId())) {
//							method = item;
//							break;
//						}
//					}
//
//					FunctionMaster func = null;
//
//					for(FunctionMaster item : functionMasters) {
//						if (method != null && method.getFunctionMasterId() != null && method.getFunctionMasterId().equals(item.getFunctionMasterId())) {
//							func = item;
//							break;
//						}
//					}
//
//					if (method == null) {
//						continue;
//					}
//
//					condition += ((!StringUtils.isEmpty(func.getPackageName()))?func.getPackageName() + ".":"") + func.getFunctionMasterCode() + "." + method.getFunctionMethodCode();
//					String methodParam = "(";
//
//					for (int i = 0; i < detail.getFormulaMethodInputs().size(); i++) {
//						FormulaMethodInput item = detail.getFormulaMethodInputs().get(i);
//
//						if (item.getFormulaDetailId() != null && item.getFormulaDetailId().equals(detail.getFormulaDetailId())) {
//							if (item.getParameterScope() == null) {
//								continue;
//							}
//
//							//value
//							if (item.getParameterScope().equals(0)) {
//								methodParam += item.getParameterValue();
//							}
//							//input
//							else if(item.getParameterScope().equals(18)) {
//								methodParam += detailServiceImpHandler.getterAndSetterOfParameter(item.getParameterId(), 0, "in", null, true, false);
//							}
//							//object definition
//							else if(item.getParameterScope().equals(19)) {
//								methodParam += detailServiceImpHandler.getterAndSetterOfParameter(item.getParameterId(), 1, "ob", null, true, false);
//							}
//
//							if (i < detail.getFormulaMethodInputs().size() - 1) {
//								methodParam += ", ";
//							}
//						}
//					}
//					methodParam += ")";
//
//					for (int i = 0; i < detail.getFormulaMethodOutputs().size(); i++) {
//						FormulaMethodOutput output = detail.getFormulaMethodOutputs().get(i);
//
//						if (output.getFormulaDetailId() != null && output.getFormulaDetailId().equals(detail.getFormulaDetailId())) {
//							if (output.getFunctionMethodOutput() != null && !StringUtils.isEmpty(output.getFunctionMethodOutput().getMethodOutputCode())) {
//								methodParam += "." + output.getFunctionMethodOutput().getMethodOutputCode() + " ";
//							}
//							break;
//						}
//					}
//					condition += methodParam;
//				}
//				//if is value
//				else if (detail.getType().equals(16)) {
//					condition += detail.getValue() + " ";
//				}
//			}
//		}
//		return condition;
//	}

	/**
	 * Get content of parameter
	 *
	 * @param list
	 * @param handleParam 
	 * @return
	 */
	private String getContentFrmMsgParamLst(List<MessageParameter> list, BLogicHandlerIo handleParam) {
		StringBuilder contentMsgParam = new StringBuilder();
		String paramScopeTmp = "";
		if (CollectionUtils.isNotEmpty(list)) {
			for (MessageParameter msgParam : list) {
				if (contentMsgParam.length()  != 0) {
					contentMsgParam.append(KEY_COMMA);
				}
				if (PARAMETER_TYPE_MSG_CODE.equals(msgParam.getParameterType())) {
					contentMsgParam.append(KEY_LINE_BREAK).append(KEY_TAB_3).append(MessageFormat.format(PHASE_MESSAGE_UTILS_GET0, msgParam.getParameterCode()));
				} else if (PARAMETER_TYPE_VALUE.equals(msgParam.getParameterType())) {
					contentMsgParam.append(KEY_LINE_BREAK).append(KEY_TAB_3).append("\"").append(msgParam.getParameterValue()).append("\"");
				} else {
					Integer scope = msgParam.getParameterScope();

					if (scope.intValue() == PARAMETER_SCOPE_IN) {
						// Modify by HungHX
						//paramScopeTmp = detailServiceImpHandler.getterAndSetterOfParameter(msgParam.getParameterCode(), 0, "in", null, true, false);
						paramScopeTmp = detailServiceImpHandler.getterAndSetterOfParameter(0, handleParam.getmAllParentAndSeflByLevelOfInOutObj(), true, msgParam.getParameterCode(), 0, "in", msgParam.getLstParameterIndex());
					} else if (scope.intValue() == PARAMETER_SCOPE_OB) {
						// Modify by HungHX
						//paramScopeTmp = detailServiceImpHandler.getterAndSetterOfParameter(msgParam.getParameterCode(), 1, "ob", null, true, false);
						paramScopeTmp = detailServiceImpHandler.getterAndSetterOfParameter(0, handleParam.getmAllParentAndSeflByLevelOfInOutObj(), true, msgParam.getParameterCode(), 1, "ob", msgParam.getLstParameterIndex());
					} else {
						// Modify by HungHX
						//paramScopeTmp = detailServiceImpHandler.getterAndSetterOfParameter(msgParam.getParameterCode(), 2, "ou", null, true, false);
						paramScopeTmp = detailServiceImpHandler.getterAndSetterOfParameter(0, handleParam.getmAllParentAndSeflByLevelOfInOutObj(), true, msgParam.getParameterCode(), 2, "ou", msgParam.getLstParameterIndex());
					}
					contentMsgParam.append(KEY_LINE_BREAK).append(KEY_TAB_3).append(paramScopeTmp);
				}
			}
		}

		return contentMsgParam.toString()==null?"":contentMsgParam.toString();
	}

	public BusinessCheckComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(BusinessCheckComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start business check node");
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
			builder.append("// End business check node");
			builder.append(KEY_NL);
		}
	}

}