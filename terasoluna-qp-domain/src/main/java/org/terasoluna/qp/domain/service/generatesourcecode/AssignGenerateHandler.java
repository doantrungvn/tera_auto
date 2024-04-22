package org.terasoluna.qp.domain.service.generatesourcecode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.domain.model.AssignComponent;
import org.terasoluna.qp.domain.model.AssignDetail;
import org.terasoluna.qp.domain.model.BDParameterIndex;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.LoopComponent;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst.TypeScope;

@Component("AssignGenerateHandler")
public class AssignGenerateHandler extends SequenceLogicGenerationHandler {

	public static final String DOT = "\\.";
	public static final String SET = "set";
	public static final String GET = "get";
	public static final boolean IS_SETTER = false;
	public static final boolean IS_GETTER = true;

	private static final boolean TARGET_SCOPE = true;
	private static final boolean PARAMETER_SCOPE = false;
	
	private static class AssignType {
		private static final Integer PARAMETER_TYPE = 0;
		private static final Integer FORMULAR_TYPE = 1;
	}

	private List<LoopComponent> allLoopComponent = new ArrayList<LoopComponent>();

	private int index;
	
	private String parentIdOfParam = null;
	
	public AssignComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(AssignComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	private AssignComponent currentComponent;
	
	@Inject
	DetailServiceImpHandler detailServiceImpHandler;


	@Override
	public void handle(StringBuilder stringBuilder, BLogicHandlerIo paramIO) {

		stringBuilder = (stringBuilder == null)?new StringBuilder():stringBuilder;
		preGencode(stringBuilder, paramIO);
		
		AssignComponent ac = currentComponent;
		if (ac == null) {
			return;
		}
		
		boolean isArray = false;
		this.index = 0;
		
		/* Enhancement by HungHX */
		for (int i = 0; i < ac.getDetails().size(); i++) {
			AssignDetail item = ac.getDetails().get(i);
			
			isArray = isArrayToArray(item, paramIO);
			if(isArray) {
				this.index = i;
				buidAssignHadArrayToArray(ac.getDetails(), paramIO, stringBuilder);
				i = this.index;
			} else {
				buildAssingHadWithParamIndex(item, paramIO, stringBuilder);
			}
			
			if (i == ac.getDetails().size()-1 && this.parentIdOfParam != null) {
				stringBuilder.append("\n\t\t}");
				this.parentIdOfParam = null;
			}
		}

		postGencode(stringBuilder, paramIO);
	}
	
	private StringBuilder buidAssignHadArrayToArray(List<AssignDetail> lstAsignDetail, BLogicHandlerIo paramIO, StringBuilder stringBuilder) {
		
		if(this.parentIdOfParam != null) {
			stringBuilder.append("\n").append("};");
			this.parentIdOfParam = null;
		}
		
		stringBuilder.append("\n");
		
		String parent = StringUtils.EMPTY;
		String parentArrayId = StringUtils.EMPTY;
		String parentArrayIdOfGetter = StringUtils.EMPTY;
		Boolean isParentArray = false;

		// Prepare variable
		String parentForSet = StringUtils.EMPTY;
		String parentForGet = StringUtils.EMPTY;
		
		for (int idx = this.index; idx < lstAsignDetail.size(); idx++) {

			AssignDetail item = lstAsignDetail.get(idx);
			
			/** Declare for variable */
			InputBean inputTarget = null, inputParam = null;
			ObjectDefinition odTarget = null, odParam = null;
			OutputBean outputTarget = null, outputParam = null;
			String instanceNmObj = StringUtils.EMPTY;
			boolean isTwooArrayPrimitive = false;
			
			/** Switch for target scope */
			switch (item.getTargetScope()) {
			case TypeScope.INPUTBEAN:
				inputTarget = (InputBean) getObjByTypeScope(TypeScope.INPUTBEAN, TARGET_SCOPE, item, paramIO);
				break;
			case TypeScope.OBJECTDEFINITION:
				odTarget = (ObjectDefinition) getObjByTypeScope(TypeScope.OBJECTDEFINITION, TARGET_SCOPE, item, paramIO);
				break;
			case TypeScope.OUTPUTBEAN:
				outputTarget = (OutputBean) getObjByTypeScope(TypeScope.OUTPUTBEAN, TARGET_SCOPE, item, paramIO);
				break;
			}
			
			/** Switch for parameter scope */
			if (item.getParameterScope() != null) {
				switch (item.getParameterScope()) {
				case TypeScope.INPUTBEAN:
					inputParam = (InputBean) getObjByTypeScope(TypeScope.INPUTBEAN, PARAMETER_SCOPE, item, paramIO);
					break;
				case TypeScope.OBJECTDEFINITION:
					odParam = (ObjectDefinition) getObjByTypeScope(TypeScope.OBJECTDEFINITION, PARAMETER_SCOPE, item, paramIO);
					break;
				case TypeScope.OUTPUTBEAN:
					outputParam = (OutputBean) getObjByTypeScope(TypeScope.OUTPUTBEAN, PARAMETER_SCOPE, item, paramIO);
					break;
				}
			}
			
			if (inputTarget != null) {
				if ((inputTarget.getArrayFlg() != null && inputTarget.getArrayFlg()) && (inputTarget.getDataType() != null
						&& !BusinessDesignConst.DataType.BYTE.equals(inputTarget.getDataType()))) {

					// In the case of using two list for assign
					if (isParentArray && parentArrayId != null && !parentArrayId.equals(inputTarget.getParentInputBeanId())) {
						stringBuilder.append(String.format("\n\t\t\t"));
						stringBuilder.append(parent + ".add("+parentForSet+");");
						stringBuilder.append(String.format("\n\t\t}"));
						stringBuilder.append(String.format("\n\t\t}"));
						this.index = idx-1;
						break;
					}
					
					parentArrayId = inputTarget.getInputBeanId();
					isParentArray = true;
					String param = StringUtils.EMPTY;
					String avaiableParam = StringUtils.EMPTY;
					
					if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope())) {
						instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, item.getParameterScope(), inputParam);
						avaiableParam = getPackageName(paramIO, inputParam) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj);
						param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputParam.getInputBeanId(), item.getParameterScope(), paramIO.getBlogicInSyntax(), item.getLstParameterIndex());
						parentArrayIdOfGetter = inputParam.getInputBeanId();
					} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope())) {
						instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC,item.getParameterScope(), odParam);
						avaiableParam = getPackageName(paramIO, odParam) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj);
						param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odParam.getObjectDefinitionId(), item.getParameterScope(), paramIO.getBlogicObSyntax(), item.getLstParameterIndex());
						parentArrayIdOfGetter = odParam.getObjectDefinitionId();
					} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope())) {
						instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC,item.getParameterScope(), outputParam);
						avaiableParam = getPackageName(paramIO, outputParam) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj);
						param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputParam.getOutputBeanId(), item.getParameterScope(), paramIO.getBlogicOutputSyntax(), item.getLstParameterIndex());
						parentArrayIdOfGetter = outputParam.getOutputBeanId();
					}

					String code = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputTarget);
					String avaiable = getPackageName(paramIO, inputTarget) + GenerateSourceCodeUtil.normalizedClassName(code);
					String target = detailServiceImpHandler.getterAndSetterOfParameter(GenerateSourceCodeConst.BUSINESS_LOGIC, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, inputTarget.getInputBeanId(), 0, paramIO.getBlogicInSyntax(), item.getLstTargetIndex());

					stringBuilder.append("\n\t\t");
					stringBuilder.append(String.format("if (%s != null) {", param));
					
					stringBuilder.append("\n\t\t");
					String instanceOf = target + String.format("(new ArrayList<%s>());", avaiable);
					stringBuilder.append(instanceOf);
					stringBuilder.append("\n\t\t");
					
					parentForGet = "item";
					
					stringBuilder.append(String.format("for (%s "+parentForGet+" : %s){", avaiableParam, param));
					stringBuilder.append("\n\t\t\t");

					parentForSet = "temp";
					
					stringBuilder.append(getPackageName(paramIO, inputTarget) + GenerateSourceCodeUtil.normalizedClassName(code) + " " + parentForSet +
						" = new " + getPackageName(paramIO, inputTarget) + GenerateSourceCodeUtil.normalizedClassName(code) + "();");

					parent = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputTarget.getInputBeanId(), 0, paramIO.getBlogicInSyntax(), item.getLstTargetIndex());
					
					continue;
				} else  if (isParentArray && parentArrayId != null && !parentArrayId.equals(inputTarget.getParentInputBeanId())) {
					stringBuilder.append(String.format("\n\t\t\t"));
					stringBuilder.append(parent + ".add("+parentForSet+");");
					stringBuilder.append(String.format("\n\t\t}"));
					stringBuilder.append(String.format("\n\t\t}"));
					this.index = idx-1;
					break;
				}

				stringBuilder.append("\n\t\t\t");
				String param = StringUtils.EMPTY;

				String target = StringUtils.EMPTY;
				int dataTypeSrc = -1;
				String parentInLoopForGet = StringUtils.EMPTY;
				isTwooArrayPrimitive = false;
				
				if (AssignType.PARAMETER_TYPE.equals(item.getAssignType())) {
					if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope())) {
						if(parentArrayIdOfGetter.equals(inputParam.getParentInputBeanId())){
							param = GenerateSourceCodeUtil.normalizedClassName(inputParam.getInputBeanCode()) + "()";
							parentInLoopForGet = parentForGet+".get";
						} else {
							param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputParam.getInputBeanId(), item.getParameterScope(), paramIO.getBlogicInSyntax() , item.getLstParameterIndex());
						}
						dataTypeSrc = inputParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(inputTarget.getArrayFlg()) && Boolean.TRUE.equals(inputParam.getArrayFlg()) && DataTypeUtils.notEquals(inputTarget.getDataType(), inputParam.getDataType())) isTwooArrayPrimitive = true;
					} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope())) {
						if(parentArrayIdOfGetter.equals(odParam.getParentObjectDefinitionId())){
							param = GenerateSourceCodeUtil.normalizedClassName(odParam.getObjectDefinitionCode()) + "()";
							parentInLoopForGet = parentForGet+".get";
						} else {
							param =detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odParam.getObjectDefinitionId(), item.getParameterScope(), paramIO.getBlogicObSyntax(), item.getLstParameterIndex());
						}
						dataTypeSrc = odParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(inputTarget.getArrayFlg()) && Boolean.TRUE.equals(odParam.getArrayFlg()) && DataTypeUtils.notEquals(inputTarget.getDataType(), odParam.getDataType())) isTwooArrayPrimitive = true;
					} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope())) {
						if(parentArrayIdOfGetter.equals(outputParam.getParentOutputBeanId())){
							param = GenerateSourceCodeUtil.normalizedClassName(outputParam.getOutputBeanCode()) + "()";
							parentInLoopForGet = parentForGet+".get";
						} else {
							param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputParam.getOutputBeanId(), item.getParameterScope(), paramIO.getBlogicOutputSyntax(), item.getLstParameterIndex());
						}
						dataTypeSrc = outputParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(inputTarget.getArrayFlg()) && Boolean.TRUE.equals(outputParam.getArrayFlg()) && DataTypeUtils.notEquals(inputTarget.getDataType(), outputParam.getDataType())) isTwooArrayPrimitive = true;
					}
					
					if(isTwooArrayPrimitive && CollectionUtils.isEmpty(item.getLstTargetIndex()) && CollectionUtils.isEmpty(item.getLstParameterIndex())) {
						stringBuilder.append("\n\t\t\t");

						if(GenerateSourceCodeConst.DataType.BYTE == dataTypeSrc){
							stringBuilder.append(String.format("if(%s != null && %s.length > 0) {", parentInLoopForGet + param, parentInLoopForGet + param));
						} else {
							stringBuilder.append(String.format("if(%s != null && %s.size() > 0) {", parentInLoopForGet + param, parentInLoopForGet + param));
						}

						target = parentForSet+".get"+GenerateSourceCodeUtil.normalizedClassName(inputTarget.getInputBeanCode()) +"()";
						stringBuilder.append("\n\t\t\t\t");
						stringBuilder.append(String.format("if(%s == null) {", target));
						stringBuilder.append("\n\t\t\t\t\t");
						stringBuilder.append(String.format("%s(new ArrayList<%s>());", parentForSet+".set"+GenerateSourceCodeUtil.normalizedClassName(inputTarget.getInputBeanCode())+"()", GenerateSourceCodeUtil.getPrimitiveTypeName(inputTarget.getDataType())));
						stringBuilder.append("\n\t\t\t\t");
						stringBuilder.append("}");
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append(String.format("%s.clear();", target));
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append(String.format("for(%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc), "iter", parentInLoopForGet + param));
						stringBuilder.append("\n\t\t\t\t\t");
						param = BusinessLogicGenerateHelper.getContentByCastDataType(inputTarget.getDataType(), dataTypeSrc, "iter");
						target = target +".add("+param+");";
						stringBuilder.append(target);
						stringBuilder.append("\n\t\t\t\t");
						stringBuilder.append("}");
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append("}");
						stringBuilder.append("\n\t\t\t");
					} else {
						if(dataTypeSrc != -1) param = BusinessLogicGenerateHelper.getContentByCastDataType(inputTarget.getDataType(), dataTypeSrc, parentInLoopForGet + param);
						target = parentForSet+".set"+GenerateSourceCodeUtil.normalizedClassName(inputTarget.getInputBeanCode()) +"("+param+")";
						stringBuilder.append(target + String.format(";"));
					}
				} else if (AssignType.FORMULAR_TYPE.equals(item.getAssignType()) && item.getFormulaDefinitionId() != null) {
					List<String> result = detailServiceImpHandler.generateConditionByFormula(paramIO, item.getFormulaDefinitionDetails());
					param = result.get(0);
					stringBuilder.append(result.get(1));
					target = parentForSet+".set"+GenerateSourceCodeUtil.normalizedClassName(inputTarget.getInputBeanCode()) +"("+param+")";
					stringBuilder.append(target + String.format(";"));
				}
				
				// In the case is the last item in the list
				if(isParentArray && parentArrayId != null && parentArrayId.equals(inputTarget.getParentInputBeanId()) && idx == lstAsignDetail.size()-1){
					stringBuilder.append(String.format("\n\t\t\t"));
					stringBuilder.append(parent + ".add("+parentForSet+");");
					stringBuilder.append(String.format("\n\t\t}"));
					stringBuilder.append(String.format("\n\t\t}"));
					// Get index before with item is processing
					this.index = idx;
				}
			} else if (odTarget != null) {
				if ((odTarget.getArrayFlg() != null && odTarget.getArrayFlg()) && (odTarget.getDataType() != null
						&& !BusinessDesignConst.DataType.BYTE.equals(odTarget.getDataType()))) {
					
					// In the case of using two list for assign
					if (isParentArray && parentArrayId != null && !parentArrayId.equals(odTarget.getParentObjectDefinitionId())) {
						stringBuilder.append(String.format("\n\t\t\t"));
						stringBuilder.append(parent + ".add("+parentForSet+");");
						stringBuilder.append(String.format("\n\t\t}"));
						stringBuilder.append(String.format("\n\t\t}"));
						// Get index before with item is processing
						this.index = idx-1;
						break;
					}
					
					parentArrayId = odTarget.getObjectDefinitionId();
					isParentArray = true;
					String avaiableParam = StringUtils.EMPTY;
					String param = StringUtils.EMPTY;
					
					if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope())) {
						instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC,item.getParameterScope(), inputParam);
						avaiableParam = getPackageName(paramIO, inputParam) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj);
						param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputParam.getInputBeanId(), item.getParameterScope(), paramIO.getBlogicInSyntax(), item.getLstParameterIndex());
						parentArrayIdOfGetter = inputParam.getInputBeanId();
					} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope())) {
						instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC,item.getParameterScope(), odParam);
						avaiableParam = getPackageName(paramIO, odParam) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj);
						param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odParam.getObjectDefinitionId(), item.getParameterScope(), paramIO.getBlogicObSyntax(), item.getLstParameterIndex());
						parentArrayIdOfGetter = odParam.getObjectDefinitionId();
					} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope())) {
						instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC,item.getParameterScope(), outputParam);
						avaiableParam = getPackageName(paramIO, outputParam) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj);
						param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputParam.getOutputBeanId(), item.getParameterScope(), paramIO.getBlogicOutputSyntax(), item.getLstParameterIndex());
						parentArrayIdOfGetter = outputParam.getOutputBeanId();
					}

					String code = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, odTarget);
					String avaiable = getPackageName(paramIO, odTarget) + GenerateSourceCodeUtil.normalizedClassName(code);
					String target = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, odTarget.getObjectDefinitionId(), 1, paramIO.getBlogicObSyntax(), item.getLstTargetIndex());

					stringBuilder.append("\n\t\t");
					stringBuilder.append(String.format("if (%s != null) {", param));
					
					stringBuilder.append("\n\t\t");
					String instanceOf = target + String.format("(new ArrayList<%s>());", avaiable);
					stringBuilder.append(instanceOf);
					stringBuilder.append("\n\t\t");
					
					parentForGet = "item";
					
					stringBuilder.append(String.format("for (%s "+parentForGet+" : %s) {", avaiableParam, param));
					stringBuilder.append("\n\t\t\t");

					parentForSet = "temp";
					
					stringBuilder.append(getPackageName(paramIO, odTarget) + GenerateSourceCodeUtil.normalizedClassName(code) + " " + parentForSet +
						" = new " + getPackageName(paramIO, odTarget) + GenerateSourceCodeUtil.normalizedClassName(code) + "();");

					parent = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odTarget.getObjectDefinitionId(), 1, paramIO.getBlogicObSyntax(), item.getLstTargetIndex());
					
					continue;
				} else if (isParentArray && parentArrayId != null && !parentArrayId.equals(odTarget.getParentObjectDefinitionId())) {
					stringBuilder.append(String.format("\n\t\t\t"));
					stringBuilder.append(parent + ".add("+parentForSet+");");
					stringBuilder.append(String.format("\n\t\t}"));
					stringBuilder.append(String.format("\n\t\t}"));
					// Get index before with item is processing
					this.index = idx-1;
					break;
				}

				stringBuilder.append("\n\t\t\t");
				String param = StringUtils.EMPTY;
				String target = StringUtils.EMPTY;
				int dataTypeSrc = -1;
				String parentInLoopForGet = StringUtils.EMPTY;
				isTwooArrayPrimitive = false;
				
				if (AssignType.PARAMETER_TYPE.equals(item.getAssignType())) {
					if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope())) {
						if(parentArrayIdOfGetter.equals(inputParam.getParentInputBeanId())){
							param = GenerateSourceCodeUtil.normalizedClassName(inputParam.getInputBeanCode()) + "()";
							parentInLoopForGet = parentForGet+".get";
						} else {
							param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputParam.getInputBeanId(), item.getParameterScope(), paramIO.getBlogicInSyntax() , item.getLstParameterIndex());
						}
						dataTypeSrc = inputParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(odTarget.getArrayFlg()) && Boolean.TRUE.equals(inputParam.getArrayFlg()) && DataTypeUtils.notEquals(odTarget.getDataType(), inputParam.getDataType())) isTwooArrayPrimitive = true;
					} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope())) {
						if(parentArrayIdOfGetter.equals(odParam.getParentObjectDefinitionId())){
							param = GenerateSourceCodeUtil.normalizedClassName(odParam.getObjectDefinitionCode()) + "()";
							parentInLoopForGet = parentForGet+".get";
						} else {
							param =detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odParam.getObjectDefinitionId(), item.getParameterScope(), paramIO.getBlogicObSyntax(), item.getLstParameterIndex());
						}
						dataTypeSrc = odParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(odTarget.getArrayFlg()) && Boolean.TRUE.equals(odParam.getArrayFlg()) && DataTypeUtils.notEquals(odTarget.getDataType(), odParam.getDataType())) isTwooArrayPrimitive = true;
					} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope())) {
						if(parentArrayIdOfGetter.equals(outputParam.getParentOutputBeanId())){
							param = GenerateSourceCodeUtil.normalizedClassName(outputParam.getOutputBeanCode()) + "()";
							parentInLoopForGet = parentForGet+".get";
						} else {
							param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputParam.getOutputBeanId(), item.getParameterScope(), paramIO.getBlogicOutputSyntax(), item.getLstParameterIndex());
						}
						dataTypeSrc = outputParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(odTarget.getArrayFlg()) && Boolean.TRUE.equals(outputParam.getArrayFlg()) && DataTypeUtils.notEquals(odTarget.getDataType(), outputParam.getDataType())) isTwooArrayPrimitive = true;
					}
					
					if(isTwooArrayPrimitive && CollectionUtils.isEmpty(item.getLstTargetIndex()) && CollectionUtils.isEmpty(item.getLstParameterIndex())) {
						stringBuilder.append("\n\t\t\t");
						
						if(GenerateSourceCodeConst.DataType.BYTE == dataTypeSrc){
							stringBuilder.append(String.format("if(%s != null && %s.length > 0) {", parentInLoopForGet + param, parentInLoopForGet + param));
						} else {
							stringBuilder.append(String.format("if(%s != null && %s.size() > 0) {", parentInLoopForGet + param, parentInLoopForGet + param));
						}

						target = parentForSet+".get"+GenerateSourceCodeUtil.normalizedClassName(odTarget.getObjectDefinitionCode()) +"()";
						stringBuilder.append("\n\t\t\t\t");
						stringBuilder.append(String.format("if(%s == null) {", target));
						stringBuilder.append("\n\t\t\t\t\t");
						stringBuilder.append(String.format("%s(new ArrayList<%s>());", parentForSet+".set"+GenerateSourceCodeUtil.normalizedClassName(odTarget.getObjectDefinitionCode())+"()", GenerateSourceCodeUtil.getPrimitiveTypeName(odTarget.getDataType())));
						stringBuilder.append("\n\t\t\t\t");
						stringBuilder.append("}");
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append(String.format("%s.clear();", target));
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc), "iter", parentInLoopForGet + param));
						stringBuilder.append("\n\t\t\t\t\t");
						param = BusinessLogicGenerateHelper.getContentByCastDataType(odTarget.getDataType(), dataTypeSrc, "iter");
						target = target +".add("+param+");";
						stringBuilder.append(target);
						stringBuilder.append("\n\t\t\t\t");
						stringBuilder.append("}");
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append("}");
						stringBuilder.append("\n\t\t\t");
					} else {
						if(dataTypeSrc != -1) param = BusinessLogicGenerateHelper.getContentByCastDataType(odTarget.getDataType(), dataTypeSrc, parentInLoopForGet + param);
						target = parentForSet+".set"+GenerateSourceCodeUtil.normalizedClassName(odTarget.getObjectDefinitionCode()) +"("+param+")";
						stringBuilder.append(target + String.format(";"));
					}
				} else if (AssignType.FORMULAR_TYPE.equals(item.getAssignType()) && item.getFormulaDefinitionId() != null) {
					List<String> result = detailServiceImpHandler.generateConditionByFormula(paramIO, item.getFormulaDefinitionDetails());
					param = result.get(0);
					stringBuilder.append(result.get(1));
					target = parentForSet+".set"+GenerateSourceCodeUtil.normalizedClassName(odTarget.getObjectDefinitionCode()) +"("+param+")";
					stringBuilder.append(target + String.format(";"));
				}

				// In the case is the last item in the list
				if(isParentArray && parentArrayId != null && parentArrayId.equals(odTarget.getParentObjectDefinitionId()) && idx == lstAsignDetail.size()-1){
					stringBuilder.append(String.format("\n\t\t\t"));
					stringBuilder.append(parent + ".add("+parentForSet+");");
					stringBuilder.append(String.format("\n\t\t}"));
					stringBuilder.append(String.format("\n\t\t}"));
					// Get index before with item is processing
					this.index = idx;
				}
			} else if (outputTarget != null) {
				if ((outputTarget.getArrayFlg() != null && outputTarget.getArrayFlg()) && (outputTarget.getDataType() != null && !DbDomainConst.JavaDataTypeOfBlogic.BYTE_DATATYPE.equals(outputTarget.getDataType()))) {

					// In the case using two list for assign
					if (isParentArray && parentArrayId != null && !parentArrayId.equals(outputTarget.getParentOutputBeanId())) {
						stringBuilder.append(String.format("\n\t\t\t"));
						stringBuilder.append(parent + ".add("+parentForSet+");");
						stringBuilder.append(String.format("\n\t\t}"));
						stringBuilder.append(String.format("\n\t\t}"));
						// Get index before with item is processing
						this.index = idx-1;
						break;
					}
					
					String avaiableParam = StringUtils.EMPTY;
					parentArrayId = outputTarget.getOutputBeanId();
					isParentArray = true;
					String param = StringUtils.EMPTY;
					
					if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope())) {
						instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC,item.getParameterScope(), inputParam);
						avaiableParam = getPackageName(paramIO, inputParam) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj);
						param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputParam.getInputBeanId(), item.getParameterScope(), paramIO.getBlogicInSyntax(), item.getLstParameterIndex());
						parentArrayIdOfGetter = inputParam.getInputBeanId();
					} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope())) {
						instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC,item.getParameterScope(), odParam);
						avaiableParam = getPackageName(paramIO, odParam) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj);
						param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odParam.getObjectDefinitionId(), item.getParameterScope(), paramIO.getBlogicObSyntax(), item.getLstParameterIndex());
						parentArrayIdOfGetter = odParam.getObjectDefinitionId();
					} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope())) {
						instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC,item.getParameterScope(), outputParam);
						avaiableParam = getPackageName(paramIO, outputParam) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj);
						param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputParam.getOutputBeanId(), item.getParameterScope(), paramIO.getBlogicOutputSyntax(), item.getLstParameterIndex());
						parentArrayIdOfGetter = outputParam.getOutputBeanId();
					}

					String code = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, outputTarget);
					String avaiable = getPackageName(paramIO, outputTarget) + GenerateSourceCodeUtil.normalizedClassName(code);
					String target = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, outputTarget.getOutputBeanId(), 2, paramIO.getBlogicOutputSyntax(), item.getLstTargetIndex());
					
					stringBuilder.append("\n\t\t");
					stringBuilder.append(String.format("if (%s != null) {", param));
					
					stringBuilder.append("\n\t\t");
					String instanceOf = target + String.format("(new ArrayList<%s>());", avaiable);
					stringBuilder.append(instanceOf);
					stringBuilder.append("\n\t\t");
					
					parentForGet = "item";
					
					stringBuilder.append(String.format("for (%s "+parentForGet+" : %s){", avaiableParam, param));
					stringBuilder.append("\n\t\t\t");

					parentForSet = "temp";
					
					stringBuilder.append(getPackageName(paramIO, outputTarget) + GenerateSourceCodeUtil.normalizedClassName(code) + " " + "temp" +
							" = new " + getPackageName(paramIO, outputTarget) + GenerateSourceCodeUtil.normalizedClassName(code) + "();");

					parent = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputTarget.getOutputBeanId(), 2, paramIO.getBlogicOutputSyntax(), item.getLstTargetIndex());
					
					continue;
				} else if (isParentArray && parentArrayId != null && !parentArrayId.equals(outputTarget.getParentOutputBeanId())) {

					stringBuilder.append(String.format("\n\t\t\t"));
					stringBuilder.append(parent + ".add("+parentForSet+");");
					stringBuilder.append(String.format("\n\t\t}"));
					stringBuilder.append(String.format("\n\t\t}"));
					// Get index before with item is processing
					this.index = idx-1;
					break;
				}

				stringBuilder.append("\n\t\t\t");
				String target = StringUtils.EMPTY;
				String param = StringUtils.EMPTY;
				int dataTypeSrc = -1;
				String parentInLoopForGet = StringUtils.EMPTY;
				isTwooArrayPrimitive = false;
				
				if (AssignType.PARAMETER_TYPE.equals(item.getAssignType())) {
					if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope())) {
						if(parentArrayIdOfGetter.equals(inputParam.getParentInputBeanId())){
							param = GenerateSourceCodeUtil.normalizedClassName(inputParam.getInputBeanCode()) + "()";
							parentInLoopForGet = parentForGet+".get";
						} else {
							param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputParam.getInputBeanId(), item.getParameterScope(), paramIO.getBlogicInSyntax() , item.getLstParameterIndex());
						}
						dataTypeSrc = inputParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(outputTarget.getArrayFlg()) && Boolean.TRUE.equals(inputParam.getArrayFlg()) && DataTypeUtils.notEquals(outputTarget.getDataType(), inputParam.getDataType())) isTwooArrayPrimitive = true;
					} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope())) {
						if(parentArrayIdOfGetter.equals(odParam.getParentObjectDefinitionId())){
							param = GenerateSourceCodeUtil.normalizedClassName(odParam.getObjectDefinitionCode()) + "()";
							parentInLoopForGet = parentForGet+".get";
						} else {
							param =detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odParam.getObjectDefinitionId(), item.getParameterScope(), paramIO.getBlogicObSyntax(), item.getLstParameterIndex());
						}
						dataTypeSrc = odParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(outputTarget.getArrayFlg()) && Boolean.TRUE.equals(odParam.getArrayFlg()) && DataTypeUtils.notEquals(outputTarget.getDataType(), odParam.getDataType())) isTwooArrayPrimitive = true;
					} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope())) {
						if(parentArrayIdOfGetter.equals(outputParam.getParentOutputBeanId())){
							param = GenerateSourceCodeUtil.normalizedClassName(outputParam.getOutputBeanCode()) + "()";
							parentInLoopForGet = parentForGet+".get";
						} else {
							param = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputParam.getOutputBeanId(), item.getParameterScope(), paramIO.getBlogicOutputSyntax(), item.getLstParameterIndex());
						}
						dataTypeSrc = outputParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(outputTarget.getArrayFlg()) && Boolean.TRUE.equals(outputParam.getArrayFlg()) && DataTypeUtils.notEquals(outputTarget.getDataType(), outputParam.getDataType())) isTwooArrayPrimitive = true;
					}
					
					if(isTwooArrayPrimitive && CollectionUtils.isEmpty(item.getLstTargetIndex()) && CollectionUtils.isEmpty(item.getLstParameterIndex())) {
						stringBuilder.append("\n\t\t\t");
						
						if(GenerateSourceCodeConst.DataType.BYTE == dataTypeSrc){
							stringBuilder.append(String.format("if(%s != null && %s.length > 0) {", parentInLoopForGet + param, parentInLoopForGet + param));
						} else {
							stringBuilder.append(String.format("if(%s != null && %s.size() > 0) {", parentInLoopForGet + param, parentInLoopForGet + param));
						}

						target = parentForSet+".get"+GenerateSourceCodeUtil.normalizedClassName(outputTarget.getOutputBeanCode()) +"()";
						stringBuilder.append("\n\t\t\t\t");
						stringBuilder.append(String.format("if(%s == null) {", target));
						stringBuilder.append("\n\t\t\t\t\t");
						stringBuilder.append(String.format("%s(new ArrayList<%s>());", parentForSet+".set"+GenerateSourceCodeUtil.normalizedClassName(outputTarget.getOutputBeanCode())+"()", GenerateSourceCodeUtil.getPrimitiveTypeName(outputTarget.getDataType())));
						stringBuilder.append("\n\t\t\t\t");
						stringBuilder.append("}");
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append(String.format("%s.clear();", target));
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc), "iter", parentInLoopForGet + param));
						stringBuilder.append("\n\t\t\t\t\t");
						param = BusinessLogicGenerateHelper.getContentByCastDataType(outputTarget.getDataType(), dataTypeSrc, "iter");
						target = target +".add("+param+");";
						stringBuilder.append(target);
						stringBuilder.append("\n\t\t\t\t");
						stringBuilder.append("}");
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append("}");
						stringBuilder.append("\n\t\t\t");
					} else {
						if(dataTypeSrc != -1) param = BusinessLogicGenerateHelper.getContentByCastDataType(outputTarget.getDataType(), dataTypeSrc, parentInLoopForGet + param);
						target = parentForSet+".set"+GenerateSourceCodeUtil.normalizedClassName(outputTarget.getOutputBeanCode()) +"("+param+")";
						stringBuilder.append(target + String.format(";"));
					}
				} else if (AssignType.FORMULAR_TYPE.equals(item.getAssignType()) && item.getFormulaDefinitionId() != null) {
					List<String> result = detailServiceImpHandler.generateConditionByFormula(paramIO, item.getFormulaDefinitionDetails());
					param = result.get(0);
					stringBuilder.append(result.get(1));
					target = parentForSet+".set"+GenerateSourceCodeUtil.normalizedClassName(outputTarget.getOutputBeanCode()) +"("+param+")";
					stringBuilder.append(target + String.format(";"));
				}

				// In the case is the last item in the list
				if(isParentArray && parentArrayId != null && parentArrayId.equals(outputTarget.getParentOutputBeanId()) && idx == lstAsignDetail.size()-1){
					stringBuilder.append(String.format("\n\t\t\t"));
					stringBuilder.append(parent + ".add("+parentForSet+");");
					stringBuilder.append(String.format("\n\t\t}"));
					stringBuilder.append(String.format("\n\t\t}"));
					// Get index before with item is processing
					this.index = idx;
				}
			}
		}
		
		// Append one new line
		stringBuilder.append("\n");
		
		return stringBuilder;
	}

	private boolean isArrayToArray(AssignDetail item, BLogicHandlerIo paramIO) {
		boolean isArrToArr = false;
		int dataType = -1;
		
		// Get object target scope
		InputBean inputTarget = (InputBean) getObjByTypeScope(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, TARGET_SCOPE, item, paramIO);
		OutputBean outputTarget = (OutputBean) getObjByTypeScope(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, TARGET_SCOPE, item, paramIO);
		ObjectDefinition odTarget = (ObjectDefinition) getObjByTypeScope(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, TARGET_SCOPE, item, paramIO);
		// Get object parameter scope
		InputBean inputParam = (item.getParameterScope() != null)?(InputBean) getObjByTypeScope(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, PARAMETER_SCOPE, item, paramIO):null;
		OutputBean outputParam = (item.getParameterScope() != null)?(OutputBean) getObjByTypeScope(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, PARAMETER_SCOPE, item, paramIO):null;
		ObjectDefinition odParam = (item.getParameterScope() != null)?(ObjectDefinition) getObjByTypeScope(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, PARAMETER_SCOPE, item, paramIO):null;
		
		// **Check input target is array
		if (inputTarget != null && inputTarget.getArrayFlg() && !BusinessDesignConst.DataType.BYTE.equals(inputTarget.getDataType()) 
				&&  (BusinessDesignConst.DataType.OBJECT.equals(inputTarget.getDataType())
						|| BusinessDesignConst.DataType.ENTITY.equals(inputTarget.getDataType())
						|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(inputTarget.getDataType())
						|| BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(inputTarget.getDataType()))) {
			
			if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope())) {
				if (inputParam.getArrayFlg() && !BusinessDesignConst.DataType.BYTE.equals(inputTarget.getDataType())) {
					isArrToArr = true;
					dataType = inputTarget.getDataType();
				}
			} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope())) {
				if (odParam.getArrayFlg() && !BusinessDesignConst.DataType.BYTE.equals(odParam.getDataType())) {
					isArrToArr = true;
					dataType = odParam.getDataType();
				}
			} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope())) {
				if (outputParam.getArrayFlg() && !BusinessDesignConst.DataType.BYTE.equals(outputParam.getDataType())) {
					isArrToArr = true;
					dataType = outputParam.getDataType();
				}
			}
		} else if (outputTarget != null && outputTarget.getArrayFlg() && !BusinessDesignConst.DataType.BYTE.equals(outputTarget.getDataType()) 
				&&  (BusinessDesignConst.DataType.OBJECT.equals(outputTarget.getDataType())
						|| BusinessDesignConst.DataType.ENTITY.equals(outputTarget.getDataType())
						|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(outputTarget.getDataType())
						|| BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(outputTarget.getDataType()))) {
				
			if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope())) {
				if (inputParam.getArrayFlg() && !BusinessDesignConst.DataType.BYTE.equals(inputParam.getDataType())) {
					isArrToArr = true;
					dataType = inputParam.getDataType();
				}
			} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope())) {
				if (odParam.getArrayFlg() && !BusinessDesignConst.DataType.BYTE.equals(odParam.getDataType())) {
					isArrToArr = true;
					dataType = odParam.getDataType();
				}
			} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope())) {
				if (outputParam.getArrayFlg()  && !BusinessDesignConst.DataType.BYTE.equals(outputParam.getDataType())) {
					isArrToArr = true;
					dataType = outputParam.getDataType();
				}
			}
		} else if (odTarget != null && odTarget.getArrayFlg() && !BusinessDesignConst.DataType.BYTE.equals(odTarget.getDataType())
				&&  (BusinessDesignConst.DataType.OBJECT.equals(odTarget.getDataType())
						|| BusinessDesignConst.DataType.ENTITY.equals(odTarget.getDataType())
						|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(odTarget.getDataType())
						|| BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(odTarget.getDataType()))) {
			
			if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope())) {
				if (inputParam.getArrayFlg() && !BusinessDesignConst.DataType.BYTE.equals(inputParam.getDataType())) {
					isArrToArr = true;
					dataType = inputParam.getDataType();
				}
			} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope())) {
				if (odParam.getArrayFlg() && !BusinessDesignConst.DataType.BYTE.equals(odParam.getDataType())) {
					isArrToArr = true;
					dataType = odParam.getDataType();
				}
			} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope())) {
				if (outputParam.getArrayFlg() && !BusinessDesignConst.DataType.BYTE.equals(outputParam.getDataType())) {
					isArrToArr = true;
					dataType = outputParam.getDataType();
				}
			}
		}
		
		if (isArrToArr && (BusinessDesignConst.DataType.OBJECT.equals(dataType)
				|| BusinessDesignConst.DataType.ENTITY.equals(dataType)
				|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(dataType)
				|| BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(dataType))) {
			isArrToArr = true;
		} else {
			isArrToArr = false;
		}
		
		return isArrToArr;
	}

	private StringBuilder buildAssingHadWithParamIndex(AssignDetail item, BLogicHandlerIo paramIO, StringBuilder stringBuilder) {
		
		/** Declare for variable */
		InputBean inputTarget = null, inputParam = null;
		ObjectDefinition odTarget = null, odParam = null;
		OutputBean outputTarget = null, outputParam = null;
		String instanceNmObj = StringUtils.EMPTY;
		boolean isTwooArrayPrimitive = false;
		int dataTypeSrc = -1;
		// Defualt is had setted
		boolean isParamNotSet = false;
		
		/** Switch for target scope */
		switch (item.getTargetScope()) {
		case TypeScope.INPUTBEAN:
			inputTarget = (InputBean) getObjByTypeScope(TypeScope.INPUTBEAN, TARGET_SCOPE, item, paramIO);
			break;
		case TypeScope.OBJECTDEFINITION:
			odTarget = (ObjectDefinition) getObjByTypeScope(TypeScope.OBJECTDEFINITION, TARGET_SCOPE, item, paramIO);
			break;
		case TypeScope.OUTPUTBEAN:
			outputTarget = (OutputBean) getObjByTypeScope(TypeScope.OUTPUTBEAN, TARGET_SCOPE, item, paramIO);
			break;
		}
		
		/** Switch for parameter scope */
		if (item.getParameterScope() != null) {
			switch (item.getParameterScope()) {
			case TypeScope.INPUTBEAN:
				inputParam = (InputBean) getObjByTypeScope(TypeScope.INPUTBEAN, PARAMETER_SCOPE, item, paramIO);
				break;
			case TypeScope.OBJECTDEFINITION:
				odParam = (ObjectDefinition) getObjByTypeScope(TypeScope.OBJECTDEFINITION, PARAMETER_SCOPE, item, paramIO);
				break;
			case TypeScope.OUTPUTBEAN:
				outputParam = (OutputBean) getObjByTypeScope(TypeScope.OUTPUTBEAN, PARAMETER_SCOPE, item, paramIO);
				break;
			}
		}
		
		if (inputTarget != null) {
			String target = StringUtils.EMPTY;
			String paramIndex = StringUtils.EMPTY;
			
			// check native datatype
			if (BusinessDesignConst.DataType.OBJECT.equals(inputTarget.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(inputTarget.getDataType()) || 
					BusinessDesignConst.DataType.COMMON_OBJECT.equals(inputTarget.getDataType()) || BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(inputTarget.getDataType())) {
			
				stringBuilder.append("\n\t\t");

				// Check null item had get
				if (this.parentIdOfParam != null) {
					stringBuilder.append("\n\t\t}");
					this.parentIdOfParam = null;
				}

				// Get name of instance object
				instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC,BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputTarget);
				// Get new instance of object data type
				String instanceOf = "new " + getPackageName(paramIO, inputTarget) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
				
				String paramForGet = StringUtils.EMPTY;
				if (AssignType.PARAMETER_TYPE.equals(item.getAssignType())) {
					
					if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope()) && inputParam != null) {
						this.parentIdOfParam = inputParam.getInputBeanId();
						paramForGet = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputParam.getInputBeanId(), item.getParameterScope(), paramIO.getBlogicInSyntax(), item.getLstParameterIndex());
						isParamNotSet = true;
					} else if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope()) && odParam != null) {
						this.parentIdOfParam = odParam.getObjectDefinitionId();
						paramForGet = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odParam.getObjectDefinitionId(), item.getParameterScope(), paramIO.getBlogicObSyntax(), item.getLstParameterIndex());
						isParamNotSet = true;
					} else if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope()) && outputParam != null) {
						this.parentIdOfParam = outputParam.getOutputBeanId();
						paramForGet = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputParam.getOutputBeanId(), item.getParameterScope(), paramIO.getBlogicOutputSyntax(), item.getLstParameterIndex());
						isParamNotSet = true;
					}
					
					// In the case parameter not set value
					if(!isParamNotSet) return stringBuilder;
					
					stringBuilder.append(String.format("if (%s != null) {", paramForGet));
					stringBuilder.append("\n\t\t\t");
					
					// Get value from target
					String targetGetter = GenerateSourceCodeUtil.normalizedVariantName(detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputTarget.getInputBeanId(), 0, paramIO.getBlogicInSyntax(), null));
					
					String indexOfLoop = getIndexInLoop(item.getLstTargetIndex());
					if (Boolean.TRUE.equals(inputTarget.getArrayFlg()) && indexOfLoop != null) {
						stringBuilder.append(String.format("if (%s == null || (%s - %s) > 0) {", targetGetter, indexOfLoop, targetGetter+".size()"));
						stringBuilder.append("\n\t\t\t").append("break;");
						stringBuilder.append("\n\t\t");
						stringBuilder.append("} else {");
						stringBuilder.append("\n\t\t");
						stringBuilder.append(String.format("%s.add(%s);", targetGetter, instanceOf));
						stringBuilder.append("\n\t\t");
					} else if (CollectionUtils.isEmpty(item.getLstParameterIndex())) {
						// In the case assign object in list object to one object
						stringBuilder.append(String.format("if (%s == null) {", targetGetter));
					}

					stringBuilder.append("\n\t\t");
					// Set value for target
					target = (inputTarget.getArrayFlg())?String.format(detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, inputTarget.getInputBeanId(), 0, paramIO.getBlogicInSyntax(), item.getLstTargetIndex()), instanceOf)+";"
						:detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, inputTarget.getInputBeanId(), 0, paramIO.getBlogicInSyntax(), item.getLstTargetIndex())+"("+instanceOf+");";
					stringBuilder.append("\t");
					stringBuilder.append(target);
					
					if (CollectionUtils.isEmpty(item.getLstParameterIndex())) stringBuilder.append("\n\t\t}");
				} else if (AssignType.FORMULAR_TYPE.equals(item.getAssignType()) && item.getFormulaDefinitionId() != null) {
					String assignObject = "";
					if (Boolean.FALSE.equals(inputTarget.getArrayFlg()) || (Boolean.TRUE.equals(inputTarget.getArrayFlg()) 
							&& CollectionUtils.isNotEmpty(item.getLstTargetIndex()))) {
						assignObject = "new " + getPackageName(paramIO, inputTarget) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
					} else {
						assignObject = "new ArrayList<" + getPackageName(paramIO, inputTarget) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + ">()";
					}
					
					paramIO.setAssignObjectForFormula(assignObject);
					List<String> result = detailServiceImpHandler.generateConditionByFormula(paramIO, item.getFormulaDefinitionDetails());
					instanceOf = result.get(0);
					stringBuilder.append(result.get(1));

					stringBuilder.append("\n\t\t");
					// Set value for target
					target = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, inputTarget.getInputBeanId(), 0, paramIO.getBlogicInSyntax(), item.getLstTargetIndex())+"("+instanceOf+");";
					
					if (Boolean.FALSE.equals(inputTarget.getArrayFlg()) || (Boolean.TRUE.equals(inputTarget.getArrayFlg()) 
							&& CollectionUtils.isEmpty(item.getLstTargetIndex()))) {
						target = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, inputTarget.getInputBeanId(), 0, paramIO.getBlogicInSyntax(), item.getLstTargetIndex())+"("+instanceOf+");";
					} else {
						target = String.format(detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, inputTarget.getInputBeanId(), 0, paramIO.getBlogicInSyntax(), item.getLstTargetIndex()), instanceOf)+";";
					}
					
					stringBuilder.append("\t");
					stringBuilder.append(target);
				}
			} else {
				isTwooArrayPrimitive = false;
				dataTypeSrc = -1;
				if (AssignType.PARAMETER_TYPE.equals(item.getAssignType())) {
					if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope()) && inputParam != null) {
						paramIndex = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputParam.getInputBeanId(), item.getParameterScope(), paramIO.getBlogicInSyntax(), item.getLstParameterIndex());
						dataTypeSrc = inputParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(inputTarget.getArrayFlg()) && Boolean.TRUE.equals(inputParam.getArrayFlg()) && DataTypeUtils.notEquals(inputTarget.getDataType(), inputParam.getDataType())) isTwooArrayPrimitive = true;
					} else if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope()) && odParam != null) {
						paramIndex = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odParam.getObjectDefinitionId(), item.getParameterScope(), paramIO.getBlogicObSyntax(), item.getLstParameterIndex());
						dataTypeSrc = odParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(inputTarget.getArrayFlg()) && Boolean.TRUE.equals(odParam.getArrayFlg()) && DataTypeUtils.notEquals(inputTarget.getDataType(), odParam.getDataType())) isTwooArrayPrimitive = true;
					} else if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope()) && outputParam != null) {
						paramIndex = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputParam.getOutputBeanId(), item.getParameterScope(), paramIO.getBlogicOutputSyntax(), item.getLstParameterIndex());
						dataTypeSrc = outputParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(inputTarget.getArrayFlg()) && Boolean.TRUE.equals(outputParam.getArrayFlg()) && DataTypeUtils.notEquals(inputTarget.getDataType(), outputParam.getDataType())) isTwooArrayPrimitive = true;
					}
				// In the case using formula
				} else if (AssignType.FORMULAR_TYPE.equals(item.getAssignType()) && item.getFormulaDefinitionId() != null) {
					List<String> result = detailServiceImpHandler.generateConditionByFormula(paramIO, item.getFormulaDefinitionDetails());
					paramIndex = result.get(0);
					stringBuilder.append(result.get(1));
				}

				if(isTwooArrayPrimitive && CollectionUtils.isEmpty(item.getLstTargetIndex()) && CollectionUtils.isEmpty(item.getLstParameterIndex())) {
					stringBuilder.append("\n\t\t\t");
					
					if(GenerateSourceCodeConst.DataType.BYTE == dataTypeSrc){
						stringBuilder.append(String.format("if(%s != null && %s.length > 0) {", paramIndex, paramIndex));
					} else {
						stringBuilder.append(String.format("if(%s != null && %s.size() > 0) {", paramIndex, paramIndex));
					}
					
					target = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputTarget.getInputBeanId(), 0, paramIO.getBlogicInSyntax(), item.getLstTargetIndex());
					String targetSetter = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, inputTarget.getInputBeanId(), 0, paramIO.getBlogicInSyntax(), null);
					stringBuilder.append("\n\t\t\t\t");
					stringBuilder.append(String.format("if(%s == null) {", target));
					stringBuilder.append("\n\t\t\t\t\t");
					stringBuilder.append(String.format("%s(new ArrayList<%s>());", targetSetter, GenerateSourceCodeUtil.getPrimitiveTypeName(inputTarget.getDataType())));
					stringBuilder.append("\n\t\t\t\t");
					stringBuilder.append("}");
					stringBuilder.append("\n\t\t\t");
					stringBuilder.append(String.format("%s.clear();", target));
					stringBuilder.append("\n\t\t\t");
					stringBuilder.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc), "iter", paramIndex));
					stringBuilder.append("\n\t\t\t\t\t");
					paramIndex = BusinessLogicGenerateHelper.getContentByCastDataType(inputTarget.getDataType(), dataTypeSrc, "iter");
					target = target + ".add("+paramIndex+");";
					stringBuilder.append(target);
					stringBuilder.append("\n\t\t\t\t");
					stringBuilder.append("}");
					stringBuilder.append("\n\t\t\t");
					stringBuilder.append("}");
					stringBuilder.append("\n\t\t\t");
				} else {
					if(dataTypeSrc != -1) paramIndex = BusinessLogicGenerateHelper.getContentByCastDataType(inputTarget.getDataType(), dataTypeSrc, paramIndex);
					target = (inputTarget.getArrayFlg() && !inputTarget.getDataType().equals(1) && CollectionUtils.isNotEmpty(item.getLstTargetIndex()))?String.format(detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, inputTarget.getInputBeanId(), 0, paramIO.getBlogicInSyntax(), item.getLstTargetIndex()), paramIndex)+";"
							:detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, inputTarget.getInputBeanId(), 0, paramIO.getBlogicInSyntax(), item.getLstTargetIndex())+"("+paramIndex+");";
					stringBuilder.append("\n\t\t");
					stringBuilder.append(target);
				}
			}
		} else if (odTarget != null) {
			String target = StringUtils.EMPTY;
			String paramIndex = StringUtils.EMPTY;
			
			// In the case object target is Object Data type
			if (BusinessDesignConst.DataType.OBJECT.equals(odTarget.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(odTarget.getDataType()) || 
						BusinessDesignConst.DataType.COMMON_OBJECT.equals(odTarget.getDataType()) || BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(odTarget.getDataType())) {
			
				stringBuilder.append("\n\t\t");
				
				// Check null item had get
				if (this.parentIdOfParam != null) {
					stringBuilder.append("\n\t\t}");
					this.parentIdOfParam = null;
				}
				
				String paramForGet = StringUtils.EMPTY;
				
				// Get name of instance object
				instanceNmObj =  detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC,BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, odTarget);
				// Get new instance of object data type
				String instanceOf = "new " + getPackageName(paramIO, odTarget) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
				
				if (AssignType.PARAMETER_TYPE.equals(item.getAssignType())) {
					if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope()) && inputParam != null) {
						this.parentIdOfParam = inputParam.getInputBeanId();
						paramForGet = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputParam.getInputBeanId(), item.getParameterScope(), paramIO.getBlogicInSyntax(), item.getLstParameterIndex());
						isParamNotSet = true;
					} else if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope()) && odParam != null) {
						this.parentIdOfParam = odParam.getObjectDefinitionId();
						paramForGet = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odParam.getObjectDefinitionId(), item.getParameterScope(), paramIO.getBlogicObSyntax(), item.getLstParameterIndex());
						isParamNotSet = true;
					} else if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope()) && outputParam != null) {
						this.parentIdOfParam = outputParam.getOutputBeanId();
						paramForGet = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputParam.getOutputBeanId(), item.getParameterScope(), paramIO.getBlogicOutputSyntax(), item.getLstParameterIndex());
						isParamNotSet = true;
					}

					if(!isParamNotSet) return stringBuilder;
					
					stringBuilder.append(String.format("if (%s != null) {", paramForGet));
					stringBuilder.append("\n\t\t\t");
					
					// Get value from target
					String targetGetter = StringUtils.uncapitalize(detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odTarget.getObjectDefinitionId(), 1, paramIO.getBlogicObSyntax(), null));
					
					String indexOfLoop = getIndexInLoop(item.getLstTargetIndex());
					if (Boolean.TRUE.equals(odTarget.getArrayFlg()) && indexOfLoop != null) {
						stringBuilder.append(String.format("if (%s == null || (%s - %s) > 0) {", targetGetter, indexOfLoop, targetGetter+".size()"));
						stringBuilder.append("\n\t\t\t").append("break;");
						stringBuilder.append("\n\t\t");
						stringBuilder.append("} else {");
						stringBuilder.append("\n\t\t");
						stringBuilder.append(String.format("%s.add(%s);", targetGetter, instanceOf));
						stringBuilder.append("\n\t\t");
					} else if (CollectionUtils.isEmpty(item.getLstParameterIndex())) {
						// In the case assign object in list object to one object
						stringBuilder.append(String.format("if (%s == null) {", targetGetter));
					}
					
					stringBuilder.append("\n\t\t");
					// Set value for target
					target = (odTarget.getArrayFlg())?String.format(detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, odTarget.getObjectDefinitionId(), 1, paramIO.getBlogicObSyntax(), item.getLstTargetIndex()), instanceOf)+";"
						:detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, odTarget.getObjectDefinitionId(), 1, paramIO.getBlogicObSyntax(), item.getLstTargetIndex())+"("+instanceOf+");";
					stringBuilder.append("\t");
					stringBuilder.append(target);
					
					if (CollectionUtils.isEmpty(item.getLstParameterIndex())) stringBuilder.append("\n\t\t}");
				} else if(AssignType.FORMULAR_TYPE.equals(item.getAssignType()) && item.getFormulaDefinitionId() != null) {
					String assignObject = "";
					
					if (Boolean.FALSE.equals(odTarget.getArrayFlg()) || (Boolean.TRUE.equals(odTarget.getArrayFlg()) 
							&& CollectionUtils.isNotEmpty(item.getLstTargetIndex()))) {
						assignObject = "new " + getPackageName(paramIO, odTarget) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
					} else {
						assignObject = "new ArrayList<" + getPackageName(paramIO, odTarget) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + ">()";
					}
					
					paramIO.setAssignObjectForFormula(assignObject);
					List<String> result = detailServiceImpHandler.generateConditionByFormula(paramIO, item.getFormulaDefinitionDetails());
					instanceOf = result.get(0);
					stringBuilder.append(result.get(1));

					stringBuilder.append("\n\t\t");
					
					if (Boolean.FALSE.equals(odTarget.getArrayFlg()) || (Boolean.TRUE.equals(odTarget.getArrayFlg()) 
							&& CollectionUtils.isEmpty(item.getLstTargetIndex()))) {
						target = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, odTarget.getObjectDefinitionId(), 1, paramIO.getBlogicObSyntax(), item.getLstTargetIndex())+"("+instanceOf+");";
					} else {
						target = String.format(detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, odTarget.getObjectDefinitionId(), 1, paramIO.getBlogicObSyntax(), item.getLstTargetIndex()), instanceOf)+";";
					}
					
					stringBuilder.append("\t");
					stringBuilder.append(target);
				}
			} else {
				isTwooArrayPrimitive = false;
				dataTypeSrc = -1;
				// In the case object target is not Object data type.
				if (AssignType.PARAMETER_TYPE.equals(item.getAssignType())) {
					if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope()) && inputParam != null) {
						paramIndex = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputParam.getInputBeanId(), item.getParameterScope(), paramIO.getBlogicInSyntax(), item.getLstParameterIndex());
						dataTypeSrc = inputParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(odTarget.getArrayFlg()) && Boolean.TRUE.equals(inputParam.getArrayFlg()) && DataTypeUtils.notEquals(odTarget.getDataType(), inputParam.getDataType())) isTwooArrayPrimitive = true;
					} else if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope()) && odParam != null) {
						paramIndex = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odParam.getObjectDefinitionId(), item.getParameterScope(), paramIO.getBlogicObSyntax(), item.getLstParameterIndex());
						dataTypeSrc = odParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(odTarget.getArrayFlg()) && Boolean.TRUE.equals(odParam.getArrayFlg()) && DataTypeUtils.notEquals(odTarget.getDataType(), odParam.getDataType())) isTwooArrayPrimitive = true;
					} else if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope()) && outputParam != null) {
						paramIndex = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputParam.getOutputBeanId(), item.getParameterScope(), paramIO.getBlogicOutputSyntax(), item.getLstParameterIndex());
						dataTypeSrc = outputParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(odTarget.getArrayFlg()) && Boolean.TRUE.equals(outputParam.getArrayFlg()) && DataTypeUtils.notEquals(odTarget.getDataType(), outputParam.getDataType())) isTwooArrayPrimitive = true;
					}
				}
				// fomular
				else if(AssignType.FORMULAR_TYPE.equals(item.getAssignType()) && item.getFormulaDefinitionId() != null) {
					List<String> result = detailServiceImpHandler.generateConditionByFormula(paramIO, item.getFormulaDefinitionDetails());
					paramIndex = result.get(0);
					stringBuilder.append(result.get(1));
				}

				if(isTwooArrayPrimitive && CollectionUtils.isEmpty(item.getLstTargetIndex()) && CollectionUtils.isEmpty(item.getLstParameterIndex())) {
					stringBuilder.append("\n\t\t\t");

					if(GenerateSourceCodeConst.DataType.BYTE == dataTypeSrc){
						stringBuilder.append(String.format("if(%s != null && %s.length > 0) {", paramIndex, paramIndex));
					} else {
						stringBuilder.append(String.format("if(%s != null && %s.size() > 0) {", paramIndex, paramIndex));
					}

					target = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odTarget.getObjectDefinitionId(), 1, paramIO.getBlogicObSyntax(), item.getLstTargetIndex());
					String targetSetter = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, odTarget.getObjectDefinitionId(), 1, paramIO.getBlogicObSyntax(), null);
					stringBuilder.append("\n\t\t\t\t");
					stringBuilder.append(String.format("if(%s == null) {", target));
					stringBuilder.append("\n\t\t\t\t\t");
					stringBuilder.append(String.format("%s(new ArrayList<%s>());", targetSetter, GenerateSourceCodeUtil.getPrimitiveTypeName(odTarget.getDataType())));
					stringBuilder.append("\n\t\t\t\t");
					stringBuilder.append("}");
					stringBuilder.append("\n\t\t\t");
					stringBuilder.append(String.format("%s.clear();", target));
					stringBuilder.append("\n\t\t\t");
					stringBuilder.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc), "iter", paramIndex));
					stringBuilder.append("\n\t\t\t\t\t");
					paramIndex = BusinessLogicGenerateHelper.getContentByCastDataType(odTarget.getDataType(), dataTypeSrc, "iter");
					target = target + ".add("+paramIndex+");";
					stringBuilder.append(target);
					stringBuilder.append("\n\t\t\t\t");
					stringBuilder.append("}");
					stringBuilder.append("\n\t\t\t");
					stringBuilder.append("}");
					stringBuilder.append("\n\t\t\t");
				} else {
					if(dataTypeSrc != -1) paramIndex = BusinessLogicGenerateHelper.getContentByCastDataType(odTarget.getDataType(), dataTypeSrc, paramIndex);
					target = (odTarget.getArrayFlg() && !odTarget.getDataType().equals(1)  && CollectionUtils.isNotEmpty(item.getLstTargetIndex()))?String.format(detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, odTarget.getObjectDefinitionId(), 1, paramIO.getBlogicObSyntax(), item.getLstTargetIndex()), paramIndex)+";"
							:detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, odTarget.getObjectDefinitionId(), 1, paramIO.getBlogicObSyntax(), item.getLstTargetIndex())+"("+paramIndex+");";

					stringBuilder.append("\n\t\t");
					stringBuilder.append(target);
				}
			}
		} else if (outputTarget != null) {

			String target = StringUtils.EMPTY;
			String paramIndex = StringUtils.EMPTY;

			// check native datatype
			if (BusinessDesignConst.DataType.OBJECT.equals(outputTarget.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(outputTarget.getDataType()) || 
					BusinessDesignConst.DataType.COMMON_OBJECT.equals(outputTarget.getDataType()) || BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(outputTarget.getDataType())) {

				stringBuilder.append("\n\t\t");

				// Check null item had get
				if (this.parentIdOfParam != null) {
					stringBuilder.append("\n\t\t}");
					this.parentIdOfParam = null;
				}

				// Get name of instance object
				instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC,BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, outputTarget);
				// Get new instance of object data type
				String instanceOf = "new " + getPackageName(paramIO, outputTarget) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
				
				String paramForGet = StringUtils.EMPTY;
				if (AssignType.PARAMETER_TYPE.equals(item.getAssignType())) {
					if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope()) && inputParam != null) {
						this.parentIdOfParam = inputParam.getInputBeanId();
						paramForGet = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputParam.getInputBeanId(), item.getParameterScope(), paramIO.getBlogicInSyntax(), item.getLstParameterIndex());
						isParamNotSet = true;
					} else if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope()) && odParam != null) {
						this.parentIdOfParam = odParam.getObjectDefinitionId();
						paramForGet = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odParam.getObjectDefinitionId(), item.getParameterScope(), paramIO.getBlogicObSyntax(), item.getLstParameterIndex());
						isParamNotSet = true;
					} else if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope()) && outputParam != null) {
						this.parentIdOfParam = outputParam.getOutputBeanId();
						paramForGet = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputParam.getOutputBeanId(), item.getParameterScope(), paramIO.getBlogicOutputSyntax(), item.getLstParameterIndex());
						isParamNotSet = true;
					}

					if(!isParamNotSet) return stringBuilder;
					
					stringBuilder.append("\n\t\t\t").append(String.format("if (%s != null) {", paramForGet));
					stringBuilder.append("\n\t\t\t");

					// Get value from target
					String targetGetter = GenerateSourceCodeUtil.normalizedVariantName(detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputTarget.getOutputBeanId(), 2, paramIO.getBlogicOutputSyntax(), null));
					
					String indexOfLoop = getIndexInLoop(item.getLstTargetIndex());
					if (Boolean.TRUE.equals(outputTarget.getArrayFlg()) && indexOfLoop != null) {
						stringBuilder.append(String.format("if (%s == null || (%s - %s) > 0) {", targetGetter, indexOfLoop, targetGetter+".size()"));
						stringBuilder.append("\n\t\t\t").append("break;");
						stringBuilder.append("\n\t\t");
						stringBuilder.append("} else {");
						stringBuilder.append("\n\t\t");
						stringBuilder.append(String.format("%s.add(%s);", targetGetter, instanceOf));
						stringBuilder.append("\n\t\t");
						
					} else if (CollectionUtils.isEmpty(item.getLstParameterIndex())) {
						// In the case assign object in list object to one object
						stringBuilder.append(String.format("if (%s == null) {", targetGetter));
					}

					stringBuilder.append("\n\t\t");
					// Set value for target
					target = (outputTarget.getArrayFlg())?String.format(detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, outputTarget.getOutputBeanId(), 2, paramIO.getBlogicOutputSyntax(), item.getLstTargetIndex()), instanceOf)+";"
						:detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, outputTarget.getOutputBeanId(), 2, paramIO.getBlogicOutputSyntax(), item.getLstTargetIndex())+"("+instanceOf+");";
					stringBuilder.append("\t");
					stringBuilder.append(target);
					
					if (CollectionUtils.isEmpty(item.getLstParameterIndex()) && AssignType.PARAMETER_TYPE.equals(item.getAssignType())) stringBuilder.append("\n\t\t}");
				} else if(AssignType.FORMULAR_TYPE.equals(item.getAssignType()) && item.getFormulaDefinitionId() != null) {
					String assignObject = "";
					
					if (Boolean.FALSE.equals(outputTarget.getArrayFlg()) || (Boolean.TRUE.equals(outputTarget.getArrayFlg()) 
							&& CollectionUtils.isNotEmpty(item.getLstTargetIndex()))) {
						assignObject = "new " + getPackageName(paramIO, outputTarget) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
					} else {
						assignObject = "new ArrayList<" + getPackageName(paramIO, outputTarget) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + ">()";
					}
					
					paramIO.setAssignObjectForFormula(assignObject);
					List<String> result = detailServiceImpHandler.generateConditionByFormula(paramIO, item.getFormulaDefinitionDetails());
					instanceOf = result.get(0);
					stringBuilder.append(result.get(1));
					stringBuilder.append("\n\t\t");
					
					// Set value for target
					if (Boolean.FALSE.equals(outputTarget.getArrayFlg()) || (Boolean.TRUE.equals(outputTarget.getArrayFlg()) 
							&& CollectionUtils.isEmpty(item.getLstTargetIndex()))) {
						target = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, outputTarget.getOutputBeanId(), 2, paramIO.getBlogicOutputSyntax(), item.getLstTargetIndex())+"("+instanceOf+");";
					} else {
						target = String.format(detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, outputTarget.getOutputBeanId(), 2, paramIO.getBlogicOutputSyntax(), item.getLstTargetIndex()), instanceOf)+";";
					}
					
					stringBuilder.append("\t");
					stringBuilder.append(target);
				}
			} else {
				isTwooArrayPrimitive = false;
				dataTypeSrc = -1;
				if (AssignType.PARAMETER_TYPE.equals(item.getAssignType())) {
					if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(item.getParameterScope()) && inputParam != null) {
						paramIndex = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, inputParam.getInputBeanId(), item.getParameterScope(), paramIO.getBlogicInSyntax(), item.getLstParameterIndex());
						dataTypeSrc = inputParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(outputTarget.getArrayFlg()) && Boolean.TRUE.equals(inputParam.getArrayFlg()) && DataTypeUtils.notEquals(outputTarget.getDataType(), inputParam.getDataType())) isTwooArrayPrimitive = true;
					} else if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(item.getParameterScope()) && odParam != null) {
						paramIndex = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, odParam.getObjectDefinitionId(), item.getParameterScope(), paramIO.getBlogicObSyntax(), item.getLstParameterIndex());
						dataTypeSrc = odParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(outputTarget.getArrayFlg()) && Boolean.TRUE.equals(odParam.getArrayFlg()) && DataTypeUtils.notEquals(outputTarget.getDataType(), odParam.getDataType())) isTwooArrayPrimitive = true;
					} else if (item.getParameterId() != null && BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(item.getParameterScope()) && outputParam != null) {
						paramIndex = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputParam.getOutputBeanId(), item.getParameterScope(), paramIO.getBlogicOutputSyntax(), item.getLstParameterIndex());
						dataTypeSrc = outputParam.getDataType();
						// Marking two list primitive
						if(Boolean.TRUE.equals(outputTarget.getArrayFlg()) && Boolean.TRUE.equals(outputParam.getArrayFlg()) && DataTypeUtils.notEquals(outputTarget.getDataType(), outputParam.getDataType())) isTwooArrayPrimitive = true;
					}
				}
				// fomular
				else if(AssignType.FORMULAR_TYPE.equals(item.getAssignType()) && item.getFormulaDefinitionId() != null) {
					List<String> result = detailServiceImpHandler.generateConditionByFormula(paramIO, item.getFormulaDefinitionDetails());
					paramIndex = result.get(0);
					stringBuilder.append(result.get(1));
				}

				if(isTwooArrayPrimitive && CollectionUtils.isEmpty(item.getLstTargetIndex()) && CollectionUtils.isEmpty(item.getLstParameterIndex())) {
					stringBuilder.append("\n\t\t\t");
					
					if(GenerateSourceCodeConst.DataType.BYTE == dataTypeSrc){
						stringBuilder.append(String.format("if(%s != null && %s.length > 0) {", paramIndex, paramIndex));
					} else {
						stringBuilder.append(String.format("if(%s != null && %s.size() > 0) {", paramIndex, paramIndex));
					}
					
					target = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, outputTarget.getOutputBeanId(), 2, paramIO.getBlogicOutputSyntax(), item.getLstTargetIndex());
					String targetSetter = detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, outputTarget.getOutputBeanId(), 2, paramIO.getBlogicOutputSyntax(), null);
					stringBuilder.append("\n\t\t\t\t");
					stringBuilder.append(String.format("if(%s == null) {", target));
					stringBuilder.append("\n\t\t\t\t\t");
					stringBuilder.append(String.format("%s(new ArrayList<%s>());", targetSetter, GenerateSourceCodeUtil.getPrimitiveTypeName(outputTarget.getDataType())));
					stringBuilder.append("\n\t\t\t\t");
					stringBuilder.append("}");
					stringBuilder.append("\n\t\t\t");
					stringBuilder.append(String.format("%s.clear();", target));
					stringBuilder.append("\n\t\t\t");
					stringBuilder.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc), "iter", paramIndex));
					stringBuilder.append("\n\t\t\t\t\t");
					paramIndex = BusinessLogicGenerateHelper.getContentByCastDataType(outputTarget.getDataType(), dataTypeSrc, "iter");
					target = target + ".add("+paramIndex+");";
					stringBuilder.append(target);
					stringBuilder.append("\n\t\t\t\t");
					stringBuilder.append("}");
					stringBuilder.append("\n\t\t\t");
					stringBuilder.append("}");
					stringBuilder.append("\n\t\t\t");
				} else {
					if(dataTypeSrc != -1) paramIndex = BusinessLogicGenerateHelper.getContentByCastDataType(outputTarget.getDataType(), dataTypeSrc, paramIndex);
					target = (outputTarget.getArrayFlg() && !outputTarget.getDataType().equals(1) && CollectionUtils.isNotEmpty(item.getLstTargetIndex()))?String.format(detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, outputTarget.getOutputBeanId(), 2, paramIO.getBlogicOutputSyntax(), item.getLstTargetIndex()), paramIndex)+";"
							:detailServiceImpHandler.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, outputTarget.getOutputBeanId(), 2, paramIO.getBlogicOutputSyntax(), item.getLstTargetIndex())+"("+paramIndex+");";
					stringBuilder.append("\n\t\t");
					stringBuilder.append(target);
				}
			}
		}
		
		return stringBuilder;
	}

	/**
	 * Get object by type of scope
	 *
	 * @param typeScope -> (0 : input, 1 : obj, 2: output)
	 * @param isTarget -> (true : target, false : parameter)
	 * @param item
	 *
	 * @return Object by scope
	 */
	private Object getObjByTypeScope(int typeScope, boolean isTarget, AssignDetail item, BLogicHandlerIo paramIO) {
		Object obj = null;

		switch (typeScope) {
			// input param
			case TypeScope.INPUTBEAN:
				obj = isTarget?getInputBeanById(paramIO.getBusinessDesign().getLstInputBean(), item.getTargetId())
					:getInputBeanById(paramIO.getBusinessDesign().getLstInputBean(), item.getParameterId());
				break;
				// object definition param
			case TypeScope.OBJECTDEFINITION:
				obj = isTarget?getObjectDefinitionById(paramIO.getBusinessDesign().getLstObjectDefinition(), item.getTargetId())
					:getObjectDefinitionById(paramIO.getBusinessDesign().getLstObjectDefinition(), item.getParameterId());
				break;
				// output param
			case TypeScope.OUTPUTBEAN:
				obj = isTarget?getOutputBeanById(paramIO.getBusinessDesign().getLstOutputBean(), item.getTargetId())
					:getOutputBeanById(paramIO.getBusinessDesign().getLstOutputBean(), item.getParameterId());
				break;
		}

		return obj;
	}

	/**
	 * 
	 * @param paramIO
	 * @param obj
	 * @return
	 */
	private String getPackageName(BLogicHandlerIo paramIO, Object obj) {
		
		Integer dataType = -1;
		String endPrefix = StringUtils.EMPTY;
		String pakageExternal = StringUtils.EMPTY;
		
		String place = ".domain.";
		if(BusinessDesignConst.MODULE_TYPE_BATCH.equals(paramIO.getModule().getModuleType())) {
			place = ".batch.";
		}
		
		StringBuilder pakage = new StringBuilder(paramIO.getProject().getPackageName());
		String moduleCode = StringUtils.EMPTY;
		
		if (obj instanceof InputBean) {
			InputBean input = (InputBean) obj;
			dataType = input.getDataType();
			pakageExternal = input.getPackageNameObjExt();
			endPrefix = "InputBean.";
			moduleCode = input.getModuleCode();
		} else if(obj instanceof ObjectDefinition) {
			ObjectDefinition od = (ObjectDefinition) obj;
			dataType = od.getDataType();
			moduleCode = od.getModuleCode();
			pakageExternal = od.getPackageNameObjExt();
			endPrefix = "ObjectDefinition.";
		} else if(obj instanceof OutputBean) {
			OutputBean output = (OutputBean) obj;
			dataType = output.getDataType();
			pakageExternal = output.getPackageNameObjExt();
			endPrefix = "OutputBean.";
			moduleCode = output.getModuleCode();
		}
		
		switch (dataType) {
		// is Object
		case GenerateSourceCodeConst.DataType.OBJECT:
			
			if(BusinessDesignConst.MODULE_TYPE_ONLINE.equals(paramIO.getModule().getModuleType())) {
				pakage.append(place).append("service.").append(paramIO.getModule().getModuleCode())
					.append(".").append(paramIO.getBusinessDesign().getBusinessLogicCode()).append(endPrefix);
			} else {
				String blogicTypeName = paramIO.getBusinessDesign().getBlogicType().equals(1)?"service." :StringUtils.EMPTY;
				pakage.append(place).append(blogicTypeName).append(paramIO.getModule().getModuleCode())
					.append(".").append(paramIO.getBusinessDesign().getBusinessLogicCode()).append(endPrefix);
			}
			break;
		// is Entity
		case GenerateSourceCodeConst.DataType.ENTITY:
			pakage.append(place+"model.");
			break;
		// is Common Object
		case GenerateSourceCodeConst.DataType.COMMON_OBJECT:
			pakage.append(place+"commonobject.");
			
			if(StringUtils.isNotEmpty(moduleCode)){
				pakage.append(moduleCode).append(".");
			}
			break;
		// is External Object
		case GenerateSourceCodeConst.DataType.EXTERNAL_OBJECT:
			pakage.setLength(0);
			pakage.append(pakageExternal).append(".");
			break;
		}

		return GenerateSourceCodeUtil.normalizedPackageName(pakage.toString());
	}

	private InputBean getInputBeanById(List<InputBean> inputBeans, String id) {
		InputBean input = null;
		for (InputBean objInput : inputBeans) {
			if (DataTypeUtils.equals(objInput.getInputBeanId(), id)) {
				input = objInput;
				break;
			}
		}

		return input;
	}

	private OutputBean getOutputBeanById(List<OutputBean> outputBeans, String id) {
		OutputBean output = null;
		for (OutputBean objOutput : outputBeans) {
			if (DataTypeUtils.equals(objOutput.getOutputBeanId(), id)) {
				output = objOutput;
				break;
			}
		}

		return output;
	}

	private ObjectDefinition getObjectDefinitionById(List<ObjectDefinition> objectDefinitions, String id) {
		ObjectDefinition od = null;
		for (ObjectDefinition obj : objectDefinitions) {
			if (DataTypeUtils.equals(obj.getObjectDefinitionId(), id)) {
				od = obj;
				break;
			}
		}

		return od;
	}

	private String getIndexInLoop(List<BDParameterIndex> lstIndex){
		String param = null;
		
		if (CollectionUtils.isNotEmpty(lstIndex)) {
			for (BDParameterIndex bdParameterIndex : lstIndex) {
				if (DataTypeUtils.equals(BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP, bdParameterIndex.getParameterIndexType())) {
					
					LoopComponent loop = detailServiceImpHandler.getLoopComponentBySequence(allLoopComponent, bdParameterIndex.getParameterIndexId());
					param = (loop != null && StringUtils.isNotEmpty(loop.getIndex()))?loop.getIndex():null;
					break;
				}
			}
		}
		
		return param;
	}

	/**
	 * @return the allLoopComponent
	 */
	public List<LoopComponent> getAllLoopComponent() {
		return allLoopComponent;
	}

	/**
	 * @param allLoopComponent the allLoopComponent to set
	 */
	public void setAllLoopComponent(List<LoopComponent> allLoopComponent) {
		this.allLoopComponent = allLoopComponent;
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start assign node");
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
			builder.append("// End assign node");
			builder.append(KEY_NL);
		}
	}
}
