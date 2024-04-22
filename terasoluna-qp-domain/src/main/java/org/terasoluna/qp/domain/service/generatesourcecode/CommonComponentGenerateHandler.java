package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonComponent;
import org.terasoluna.qp.domain.model.CommonInputValue;
import org.terasoluna.qp.domain.model.CommonOutputValue;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.repository.generatesourcecode.GenerateSourceCodeRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;

@Component("CommonComponentGenerateHandler")
public class CommonComponentGenerateHandler extends SequenceLogicGenerationHandler {

	@Inject
	BusinessLogicGenerateHandler businessLogicGenerateHandler;

	@Inject
	GenerateSourceCodeRepository generateSourceCodeRepository;

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;
	
	public static final class TypeOfDataType {
		public static final int OBJECT = 0;
		public static final int ENTITY = 14;
		public static final int COMMON_OBJECT = 16;
		public static final int EXTERNAL_OBJECT = 17;
	}

	public static final class TypeOfObject {
		public static final int INPUT_BEAN = 0;
		public static final int OBJECT_DEFINITION = 1;
		public static final int OUTPUT_BEAN = 2;
	}

	private static final String NL = "\n\t\t";
	private static final String NL_TAB = "\n\t\t\t";
	private static final String INPUT_SETTER = "{0}({1});";
	private static final String INIT_OBJECT = "{0} {1} = new {0}();";
	private static final String INIT_COMMON_OUTPUT = "{0}OutputBean {1} = {4}.{2}({3});";
	private static final String INIT_COMMON_OUTPUT_CUSTOMIZE = "{0}OutputBean {1} = {4}.{2}({3});";
	private static final boolean IS_GETTER = true;
	private static final boolean IS_SETTER = false;
	private static final String SPACE = " ";
	private Module module;
	//	/** Alway blogic common */
	private BusinessDesign blogicRefer;
	private String inputSyntax;
	private String outputSyntax;

	private CommonComponent currentComponent;

//	private String trimExtension(String fileName) {
//		return fileName.replace(".java", "");
//	}

	private String getPackageName(BusinessDesign blogic , int typeScope, Object obj) {

		StringBuilder pakageName = new StringBuilder(businessLogicGenerateHandler.getProject().getPackageName());
		String pakageExternal = "";
		String code = null;
		int dataType = -1;
		String moduleCode = StringUtils.EMPTY;
		if(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(typeScope) && obj instanceof InputBean) {
			InputBean in = (InputBean) obj;
			dataType = in.getDataType();
			pakageExternal = in.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_INPUT_BEAN;
			moduleCode = in.getModuleCode();
		} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(typeScope) && obj instanceof ObjectDefinition) {
			ObjectDefinition objDef = (ObjectDefinition) obj;
			dataType = objDef.getDataType();
			pakageExternal = objDef.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION;
			moduleCode = objDef.getModuleCode();
		} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(typeScope) && obj instanceof OutputBean) {
			OutputBean out = (OutputBean) obj;
			dataType = out.getDataType();
			pakageExternal = out.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN;
			moduleCode = out.getModuleCode();
		}

		switch (dataType) {
			case TypeOfDataType.OBJECT :
				
				if(BusinessDesignConst.MODULE_TYPE_ONLINE.equals(this.module.getModuleType())) {
					if (Boolean.TRUE.equals(blogic.getCustomizeFlg())) {
						pakageName.append(".domain.service.").append((blogic.getBlogicType().equals(1)?"commoncustomize": this.module.getModuleCode()))
						.append(".").append(blogic.getBusinessLogicCode()).append(code).append(".");
					} else {
						pakageName.append(".domain.service.").append((blogic.getBlogicType().equals(1)?"common": this.module.getModuleCode()))
						.append(".").append(blogic.getBusinessLogicCode()).append(code).append(".");
					}
				} else {
					if (Boolean.TRUE.equals(blogic.getCustomizeFlg())) {
						pakageName.append(".batch.").append((blogic.getBlogicType().equals(1)?"service.commoncustomize":this.module.getModuleCode()))
						.append(".").append(blogic.getBusinessLogicCode()).append(code).append(".");
					} else {
						pakageName.append(".batch.").append((blogic.getBlogicType().equals(1)?"service.common":this.module.getModuleCode()))
						.append(".").append(blogic.getBusinessLogicCode()).append(code).append(".");
					}
				}
				break;
			case TypeOfDataType.ENTITY :
				if(BusinessDesignConst.MODULE_TYPE_ONLINE.equals(this.module.getModuleType())) {
					pakageName.append(".domain.model.");
				} else {
					pakageName.append(".batch.model.");
				}
				break;
			case TypeOfDataType.COMMON_OBJECT :
				if(BusinessDesignConst.MODULE_TYPE_ONLINE.equals(this.module.getModuleType())) {
					pakageName.append(".domain.commonobject.");
				} else {
					pakageName.append(".batch.commonobject.");
				}
				if(StringUtils.isNotEmpty(moduleCode)){
					pakageName.append(moduleCode).append(".");
				}
				break;
			case TypeOfDataType.EXTERNAL_OBJECT :
				pakageName = new StringBuilder();
				pakageName.append(pakageExternal).append(".");
				break;
		}

		return GenerateSourceCodeUtil.normalizedPackageName(pakageName.toString());
	}

	public CommonComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(CommonComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	@Override
	public void handle(StringBuilder builder, BLogicHandlerIo param) {

		this.module = param.getModule();
		// Content of blogic common
		StringBuilder strGenCommonDetails = new StringBuilder(NL);
		// Processing for component current

		if(currentComponent != null) {
			preGencode(builder, param);
			this.blogicRefer = generateSourceCodeRepository.findBLogicByBlogicId(currentComponent.getBusinessLogicId());
			if (CollectionUtils.isNotEmpty(generateSourceCodeRepository.findAllOutputBeanByBusinessId(currentComponent.getBusinessLogicId()))) {
				blogicRefer.setLstOutputBean(generateSourceCodeRepository.findAllOutputBeanByBusinessId(currentComponent.getBusinessLogicId()));
			}

			if (CollectionUtils.isNotEmpty(generateSourceCodeRepository.findAllInputBeanByBusinessId(currentComponent.getBusinessLogicId()))) {
				blogicRefer.setLstInputBean(generateSourceCodeRepository.findAllInputBeanByBusinessId(currentComponent.getBusinessLogicId()));
			}
			
			if (CollectionUtils.isNotEmpty(generateSourceCodeRepository.findAllObjDefinedByBusinessId(currentComponent.getBusinessLogicId()))) {
				blogicRefer.setLstObjectDefinition(generateSourceCodeRepository.findAllObjDefinedByBusinessId(currentComponent.getBusinessLogicId()));
			}

			this.inputSyntax = blogicRefer.getBusinessLogicCode() + GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_INPUT_BEAN;;
			this.outputSyntax = blogicRefer.getBusinessLogicCode() + GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN;;
			// Build getting input param
			strGenCommonDetails = buildPassParameterInput(param, strGenCommonDetails);
			// Build setting value for output
			strGenCommonDetails.append(NL).append(NL);
			strGenCommonDetails = buildAssignTargetOutput(param, strGenCommonDetails);
		}

		builder.append(strGenCommonDetails);
		postGencode(builder, param);
	}

	private StringBuilder buildAssignTargetOutput(BLogicHandlerIo paramIO, StringBuilder strGenCommonDetails) {
		String place = ".domain.service.";
		if(BusinessDesignConst.MODULE_TYPE_BATCH.equals(this.module.getModuleType())) place = ".batch.service.";
		
		if (Boolean.TRUE.equals(blogicRefer.getCustomizeFlg())) {
			String callScript = MessageFormat.format(
				INIT_COMMON_OUTPUT_CUSTOMIZE, GenerateSourceCodeUtil.normalizedPackageName(paramIO.getProject().getPackageName()+ place +"commoncustomize.")+GenerateSourceCodeUtil.normalizedClassName(blogicRefer.getBusinessLogicCode()),
				this.outputSyntax, 
				GenerateSourceCodeUtil.normalizedMethodName(blogicRefer.getBusinessLogicCode()), 
				this.inputSyntax, 
				GenerateSourceCodeUtil.normalizedVariantName(blogicRefer.getBusinessLogicCode())+"Service");
			strGenCommonDetails.append(callScript);
			strGenCommonDetails.append(NL);
		} else {
			switch (paramIO.getBusinessDesign().getBlogicType()) {
			case 1:
				strGenCommonDetails.append(
						MessageFormat.format(
							INIT_COMMON_OUTPUT, GenerateSourceCodeUtil.normalizedPackageName(paramIO.getProject().getPackageName()+ place +"common.")+GenerateSourceCodeUtil.normalizedClassName(blogicRefer.getBusinessLogicCode()),
							this.outputSyntax, 
							GenerateSourceCodeUtil.normalizedMethodName(blogicRefer.getBusinessLogicCode()),
							this.inputSyntax, "this")).append(NL);
				break;

			default:
				strGenCommonDetails.append(
						MessageFormat.format(
							INIT_COMMON_OUTPUT, GenerateSourceCodeUtil.normalizedPackageName(paramIO.getProject().getPackageName()+ place +"common.")+GenerateSourceCodeUtil.normalizedClassName(blogicRefer.getBusinessLogicCode()),
							this.outputSyntax, 
							GenerateSourceCodeUtil.normalizedMethodName(blogicRefer.getBusinessLogicCode()), 
							this.inputSyntax, "businessLogicCommonService")).append(NL);
				break;
			}
		}

		if(CollectionUtils.isNotEmpty(currentComponent.getParameterOutputBeans())){
			
			// Preparing data
			Map<String, List<?>> mAllBlogicCurrent = paramIO.getmAllParentAndSeflByLevelOfInOutObj();
			// Blogic refer is blogic common
			Map<String, List<?>> mAllBlogicRefer = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, blogicRefer.getLstInputBean(), blogicRefer.getLstObjectDefinition(), blogicRefer.getLstOutputBean());
			
			// Initializing variable
			String parentIdOfRefer = "";
			boolean isParentArray = false;
			String parentInputForSet = "";
			String parentobjForGet = "";
			String getterParentInputList = "";
			int countOutputParam = 0;
			
			String instanceNmObj = "";
			
			for (CommonOutputValue cov : currentComponent.getParameterOutputBeans()) {
				boolean isTwooArrayPrimitive = false;
				countOutputParam++;
				
				// Get output Refer for setting
				OutputBean oubeanRefer = (OutputBean) NavigatorComponentGenerateHandler.getObjByTypeScope(2, 
						mAllBlogicRefer.get(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID + cov.getOutputBeanId().toString()),
						cov.getOutputBeanId().toString());
				
				Object obj = null;
				
				if(cov.getTargetId() != null) {
					
					obj = NavigatorComponentGenerateHandler.getObjByTypeScope(cov.getTargetScope(), 
							mAllBlogicCurrent.get(cov.getTargetScope() + cov.getTargetId()),cov.getTargetId());
					
					String code = "";
					String codeScope = "";
					String parentId = "";
					int dataTypeSrc = -1;
					
					boolean arrayFlg = false;
					
					switch (cov.getTargetScope()) {
						case 0: 
							InputBean inParam = (InputBean) obj;
							codeScope = paramIO.getBlogicInSyntax();
							code = inParam.getInputBeanCode();
							arrayFlg = inParam.getArrayFlg();
							parentId = inParam.getParentInputBeanId();
							dataTypeSrc= inParam.getDataType();
							break;
						case 1: 
							ObjectDefinition odParam = (ObjectDefinition) obj;
							codeScope = paramIO.getBlogicObSyntax(); 
							code = odParam.getObjectDefinitionCode();
							arrayFlg = odParam.getArrayFlg();
							parentId = odParam.getParentObjectDefinitionId();
							dataTypeSrc = odParam.getDataType();
							break;
						case 2: 
							OutputBean ouParam = (OutputBean) obj;
							codeScope = paramIO.getBlogicOutputSyntax();
							code = ouParam.getOutputBeanCode();
							arrayFlg = ouParam.getArrayFlg();
							parentId = ouParam.getParentOutputBeanId();
							dataTypeSrc = ouParam.getDataType();
							break;
					}

					if(BusinessDesignConst.DataType.ENTITY.equals(oubeanRefer.getDataType()) || BusinessDesignConst.DataType.OBJECT.equals(oubeanRefer.getDataType())
							|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(oubeanRefer.getDataType()) || BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(oubeanRefer.getDataType())) {
						
						// Marking value
						parentIdOfRefer = oubeanRefer.getOutputBeanId();
						// New assign detail
						if(Boolean.FALSE.equals(oubeanRefer.getArrayFlg())) {
							// Marking value
							if(Boolean.TRUE.equals(isParentArray)) {
								strGenCommonDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}")).append("\n\t\t");
								isParentArray = false;
							}
							
							String getterParentCurrent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, cov.getTargetId(), cov.getTargetScope(), codeScope, null);
							
							// Get name of instance object
							instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, cov.getTargetScope(), obj);
							// Get new instance of object data type
							String instanceOf = "new " + getPackageName(paramIO.getBusinessDesign(), cov.getTargetScope(), obj) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
							strGenCommonDetails.append(String.format("if (%s == null){", getterParentCurrent)).append("\n\t\t");
							
							String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_SETTER, cov.getTargetId(), cov.getTargetScope(), codeScope, null)+"("+instanceOf+");";
							strGenCommonDetails.append("\t").append(setterInReferParent).append("\n\t\t}");
							strGenCommonDetails.append("\n\t\t");
						} else {
							
							if(Boolean.TRUE.equals(isParentArray)) {
								strGenCommonDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}")).append("\n\t\t");
								isParentArray = false;
							}
							
							// Marking value
							isParentArray = true;
							parentInputForSet = code+"Set";

							String getterParentCurrent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, cov.getTargetId(), cov.getTargetScope(), codeScope, null);
							String getterParentRefer = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, oubeanRefer.getOutputBeanId(), 2, this.outputSyntax, null);
							// Get name of instance object
							instanceNmObj =  detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, cov.getTargetScope(), obj);

							// Get new instance of object data type
							String instanceOf = "new ArrayList<" + getPackageName(paramIO.getBusinessDesign(), cov.getTargetScope(), obj) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + ">()";

							String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_SETTER, cov.getTargetId(), cov.getTargetScope(), codeScope, null);

							strGenCommonDetails.append("if(" + getterParentCurrent + SPACE + "== null) {").append(NL_TAB);
							strGenCommonDetails.append(setterInReferParent + "("+instanceOf+");").append(NL);
							strGenCommonDetails.append("}").append(NL);

							strGenCommonDetails.append(String.format("%s.clear();", getterParentCurrent)).append(NL);
							
							// Building for each
							String declareObjCode = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, oubeanRefer);

							parentobjForGet = declareObjCode + "Get";
							String declareObjForEach = getPackageName(blogicRefer, 2, oubeanRefer) + GenerateSourceCodeUtil.normalizedClassName(declareObjCode);
							strGenCommonDetails.append("\n\t\t").append(String.format("for (%s "+parentobjForGet+" : %s){", declareObjForEach, getterParentRefer)).append("\n\t\t\t");
							
							// Build new temporary object from input refer
							strGenCommonDetails.append(MessageFormat.format(INIT_OBJECT, getPackageName(paramIO.getBusinessDesign(), cov.getTargetScope(), obj) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj), parentInputForSet)).append("\n\t\t\t");
							
							// Using for add one item into the list object
							getterParentInputList = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, true, cov.getTargetId(), cov.getTargetScope(), codeScope, null);
							
							if(countOutputParam == currentComponent.getParameterOutputBeans().size()){
								strGenCommonDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append("\n\t\t}").append("\n\t\t");
							}
						}

						continue;
					}
					
					String getter = "";
					
					if (cov.getTargetId() != null && cov.getTargetScope() != null) {
						// Marking two list primitive
						if(Boolean.TRUE.equals(cov.getArrayFlg()) && arrayFlg && DataTypeUtils.notEquals(cov.getDataType(), dataTypeSrc)) isTwooArrayPrimitive = true;
						
						if (StringUtils.isEmpty(oubeanRefer.getParentOutputBeanId())) {
							if(Boolean.TRUE.equals(isParentArray)) {
								strGenCommonDetails.append(String.format("\n\t\t\t")).append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t"));
								isParentArray = false;
							}
							
							if(isTwooArrayPrimitive) {
								strGenCommonDetails.append("\n\t\t");
								String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, cov.getTargetId(), cov.getTargetScope(), codeScope, cov.getLstTargetIndex());
								strGenCommonDetails.append(String.format("if (%s == null) {", targetNull));
								strGenCommonDetails.append("\n\t\t\t");
								String dataType = GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc);
								String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_SETTER, cov.getTargetId(), cov.getTargetScope(), codeScope, cov.getLstTargetIndex())+"(new ArrayList<"+ dataType +">());";
								strGenCommonDetails.append(targetSet);
								strGenCommonDetails.append("\n\t\t}");
								strGenCommonDetails.append("\n\t\t");
								strGenCommonDetails.append(String.format("%s.clear();", targetNull));
								strGenCommonDetails.append("\n\t\t");
								String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, oubeanRefer.getOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, this.outputSyntax, null);
								
								if(GenerateSourceCodeConst.DataType.BYTE == oubeanRefer.getDataType()){
									strGenCommonDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
								} else {
									strGenCommonDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
								}
								
								strGenCommonDetails.append("\n\t\t\t");
								strGenCommonDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(oubeanRefer.getDataType()), "iter", getterParam));
								String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(dataTypeSrc, oubeanRefer.getDataType(), "iter");
								strGenCommonDetails.append("\n\t\t\t\t");
								String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, cov.getTargetId(), cov.getTargetScope(), codeScope, cov.getLstTargetIndex())+".add("+paramInput+");";
								strGenCommonDetails.append(setterInReferParent);
								strGenCommonDetails.append("\n\t\t\t");
								strGenCommonDetails.append("}");
								strGenCommonDetails.append("\n\t\t");
								strGenCommonDetails.append("}");
								strGenCommonDetails.append("\n\t\t");
							} else {
								getter = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, oubeanRefer.getOutputBeanId(), 2, this.outputSyntax, null);
								// Cast data type
								getter = BusinessLogicGenerateHelper.getContentByCastDataType(dataTypeSrc, oubeanRefer.getDataType(), getter);
								
								// setter of child
								String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_SETTER, cov.getTargetId(), cov.getTargetScope(), codeScope, cov.getLstTargetIndex());
	
								if(CollectionUtils.isNotEmpty(cov.getLstTargetIndex())) {
									strGenCommonDetails.append(String.format(setterChild, getter)+(";")).append(NL_TAB);
								} else {
									strGenCommonDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append(NL_TAB);
								}
							}

						} else if(!isParentArray && parentIdOfRefer != null && parentIdOfRefer.equals(oubeanRefer.getParentOutputBeanId())) {
							if(isTwooArrayPrimitive) {
								strGenCommonDetails.append("\n\t\t");
								String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, cov.getTargetId(), cov.getTargetScope(), codeScope, cov.getLstTargetIndex());
								strGenCommonDetails.append(String.format("if (%s == null) {", targetNull));
								strGenCommonDetails.append("\n\t\t\t");
								String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_SETTER, cov.getTargetId(), cov.getTargetScope(), codeScope, cov.getLstTargetIndex())+"(new ArrayList<>());";
								strGenCommonDetails.append(targetSet);
								strGenCommonDetails.append("\n\t\t}");
								strGenCommonDetails.append("\n\t\t");
								strGenCommonDetails.append(String.format("%s.clear();", targetNull));
								strGenCommonDetails.append("\n\t\t");
								String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, oubeanRefer.getOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, this.outputSyntax, null);
								
								if(GenerateSourceCodeConst.DataType.BYTE == oubeanRefer.getDataType()){
									strGenCommonDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
								} else {
									strGenCommonDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
								}
								
								strGenCommonDetails.append("\n\t\t\t");
								strGenCommonDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(oubeanRefer.getDataType()), "iter", getterParam));
								String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(dataTypeSrc, oubeanRefer.getDataType(), "iter");
								strGenCommonDetails.append("\n\t\t\t\t");
								String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, cov.getTargetId(), cov.getTargetScope(), codeScope, cov.getLstTargetIndex())+".add("+paramInput+");";
								strGenCommonDetails.append(setterInReferParent);
								strGenCommonDetails.append("\n\t\t\t");
								strGenCommonDetails.append("}");
								strGenCommonDetails.append("\n\t\t");
								strGenCommonDetails.append("}");
								strGenCommonDetails.append("\n\t\t");
							} else {
								getter = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, oubeanRefer.getOutputBeanId(), 2, this.outputSyntax, null);
								// Cast data type
								getter = BusinessLogicGenerateHelper.getContentByCastDataType(dataTypeSrc, oubeanRefer.getDataType(), getter);
								
								// setter of child
								String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_SETTER, cov.getTargetId(), cov.getTargetScope(), codeScope, cov.getLstTargetIndex());
								
								if(CollectionUtils.isNotEmpty(cov.getLstTargetIndex())) {
									strGenCommonDetails.append(String.format(setterChild, getter)+(";")).append(NL_TAB);
								} else {
									strGenCommonDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append(NL_TAB);
								}
							}
						} else if (isParentArray && parentIdOfRefer != null && parentIdOfRefer.equals(oubeanRefer.getParentOutputBeanId())){
							
							List<?> listObjReferParent = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, oubeanRefer.getParentOutputBeanId(), blogicRefer.getLstOutputBean());
							Map<String, List<?>> mAllBlogicReferTmp = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, null, null, listObjReferParent);
							
							if(mAllBlogicReferTmp == null) continue;
							
							// setter of child of parentInputForSet
							List<?> listObjCurrParent = null;
							Map<String, List<?>> mAllObjCurrTmp = null;
							
							switch (cov.getTargetScope()) {
							case 0:
								listObjCurrParent = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, parentId, paramIO.getBusinessDesign().getLstInputBean());
								mAllObjCurrTmp = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, listObjCurrParent, null, null);
								break;
							case 1:
								listObjCurrParent = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, parentId, paramIO.getBusinessDesign().getLstObjectDefinition());
								mAllObjCurrTmp = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, null, listObjCurrParent, null);
								break;
							case 2:
								listObjCurrParent = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, parentId, paramIO.getBusinessDesign().getLstOutputBean());
								mAllObjCurrTmp = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, null, null, listObjCurrParent);
								break;
							}

							if(isTwooArrayPrimitive) {
								strGenCommonDetails.append("\n\t\t\t");
								String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllObjCurrTmp, IS_GETTER, cov.getTargetId(), cov.getTargetScope(), parentInputForSet, cov.getLstTargetIndex());
								strGenCommonDetails.append(String.format("if (%s == null) {", targetNull));
								strGenCommonDetails.append("\n\t\t\t\t");
								String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllObjCurrTmp, IS_SETTER, cov.getTargetId(), cov.getTargetScope(), parentInputForSet, cov.getLstTargetIndex())+"(new ArrayList<>());";
								strGenCommonDetails.append(targetSet);
								strGenCommonDetails.append("\n\t\t\t}");
								strGenCommonDetails.append("\n\t\t\t");
								strGenCommonDetails.append(String.format("%s.clear();", targetNull));
								strGenCommonDetails.append("\n\t\t\t");
								String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicReferTmp, IS_GETTER, oubeanRefer.getOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, parentobjForGet, null);
								
								if(GenerateSourceCodeConst.DataType.BYTE == oubeanRefer.getDataType()){
									strGenCommonDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
								} else {
									strGenCommonDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
								}
								
								strGenCommonDetails.append("\n\t\t\t\t");
								strGenCommonDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(oubeanRefer.getDataType()), "iter", getterParam));
								String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(dataTypeSrc, oubeanRefer.getDataType(), "iter");
								strGenCommonDetails.append("\n\t\t\t\t\t");
								String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllObjCurrTmp, IS_GETTER, cov.getTargetId(), cov.getTargetScope(), parentInputForSet, cov.getLstTargetIndex())+".add("+paramInput+");";
								strGenCommonDetails.append(setterInReferParent);
								strGenCommonDetails.append("\n\t\t\t\t");
								strGenCommonDetails.append("}");
								strGenCommonDetails.append("\n\t\t\t");
								strGenCommonDetails.append("}");
								strGenCommonDetails.append("\n\t\t\t");
								
								if(countOutputParam == currentComponent.getParameterOutputBeans().size()){
									strGenCommonDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append("\n\t\t}").append("\n\t\t");
								}
							} else {
								getter = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicReferTmp, IS_GETTER, oubeanRefer.getOutputBeanId(), BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, parentobjForGet, null);
								// Cast data type
								getter = BusinessLogicGenerateHelper.getContentByCastDataType(dataTypeSrc, oubeanRefer.getDataType(), getter);
								
								if (mAllObjCurrTmp == null) continue;
								
								String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllObjCurrTmp, IS_SETTER, cov.getTargetId(), cov.getTargetScope(), parentInputForSet, cov.getLstTargetIndex());
								
								if(CollectionUtils.isNotEmpty(cov.getLstTargetIndex())) {
									strGenCommonDetails.append(String.format(setterChild, getter)+(";")).append(NL_TAB);
								} else {
									strGenCommonDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append(NL_TAB);
								}
								
								if(countOutputParam == currentComponent.getParameterOutputBeans().size()){
									strGenCommonDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append("\n\t\t}").append("\n\t\t");
								}
							}
						}
					}
				}
			}
		}

		return strGenCommonDetails;
	}

	private StringBuilder buildPassParameterInput(BLogicHandlerIo paramIO, StringBuilder strGenCommonDetails) {

		String place = ".domain.service.";
		if(BusinessDesignConst.MODULE_TYPE_BATCH.equals(this.module.getModuleType())) place = ".batch.service.";
		
		if (Boolean.TRUE.equals(blogicRefer.getCustomizeFlg())){
			strGenCommonDetails.append(MessageFormat.format(INIT_OBJECT, GenerateSourceCodeUtil.normalizedPackageName(paramIO.getProject().getPackageName()+ place + "commoncustomize.")+GenerateSourceCodeUtil.normalizedClassName(blogicRefer.getBusinessLogicCode())
					+ GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_INPUT_BEAN, this.inputSyntax)).append("\n\t\t");
		} else {
			strGenCommonDetails.append(MessageFormat.format(INIT_OBJECT, GenerateSourceCodeUtil.normalizedPackageName(paramIO.getProject().getPackageName()+ place +"common.")+GenerateSourceCodeUtil.normalizedClassName(blogicRefer.getBusinessLogicCode())
					+ GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_INPUT_BEAN, this.inputSyntax)).append("\n\t\t");
		}
		
		if (CollectionUtils.isNotEmpty(currentComponent.getParameterInputBeans())) {

			// Preparing data
			Map<String, List<?>> mAllBlogicCurrent = paramIO.getmAllParentAndSeflByLevelOfInOutObj();
			// Blogic refer is blogic common
			Map<String, List<?>> mAllBlogicRefer = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, blogicRefer.getLstInputBean(), blogicRefer.getLstObjectDefinition(), blogicRefer.getLstOutputBean());
			
			// Variable for main processing
			String parentIdOfRefer = "";
			int countInputParam = 0;
			String getterParentInputList = "";
			String parentInputForSet = "";
			// Start is false
			Boolean isParentArray = false;
			String instanceNmObj = "";
			// Parent Object for getter
			String parentobjForGet = "";
			
			// Initial variable
			Object objMark = null;
			String parentId = "";
			String codeScope = "";
			boolean arrayFlg = false;
			
			for (CommonInputValue civ : currentComponent.getParameterInputBeans()) {
				countInputParam++;
				boolean isTwooArrayPrimitive = false;
				
				// Get input Refer for setting
				InputBean inbeanRefer = (InputBean) NavigatorComponentGenerateHandler.getObjByTypeScope(0, 
						mAllBlogicRefer.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID + civ.getInputBeanId().toString()),
						civ.getInputBeanId().toString());
				
				if(inbeanRefer == null) continue;
				
				objMark = null;
				if (civ.getParameterScope() != null && civ.getParameterId() != null) {
					objMark = NavigatorComponentGenerateHandler.getObjByTypeScope(civ.getParameterScope(), paramIO.getmAllParentAndSeflByLevelOfInOutObj().get(
							civ.getParameterScope() + civ.getParameterId()), civ.getParameterId());
				}

				int dataTypeSrc = -1;
				if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(civ.getParameterScope()) && objMark != null) {
					InputBean in = (InputBean) objMark;
					parentId = in.getParentInputBeanId();
					codeScope = paramIO.getBlogicInSyntax();
					dataTypeSrc = in.getDataType();
					arrayFlg = in.getArrayFlg();
				} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(civ.getParameterScope()) && objMark != null) {
					ObjectDefinition objDef =  (ObjectDefinition) objMark;
					parentId = objDef.getParentObjectDefinitionId();
					codeScope = paramIO.getBlogicObSyntax();
					dataTypeSrc = objDef.getDataType();
					arrayFlg = objDef.getArrayFlg();
				} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(civ.getParameterScope()) && objMark != null) {
					OutputBean ou =  (OutputBean) objMark;
					parentId = ou.getParentOutputBeanId();
					codeScope = paramIO.getBlogicOutputSyntax();
					dataTypeSrc = ou.getDataType();
					arrayFlg = ou.getArrayFlg();
				}

				if(BusinessDesignConst.DataType.ENTITY.equals(inbeanRefer.getDataType()) || BusinessDesignConst.DataType.OBJECT.equals(inbeanRefer.getDataType())
						|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(inbeanRefer.getDataType()) || BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(inbeanRefer.getDataType())) {
					// Marking value
					parentIdOfRefer = inbeanRefer.getInputBeanId();
					
					if(Boolean.FALSE.equals(inbeanRefer.getArrayFlg())) {
						// Marking value
						if(Boolean.TRUE.equals(isParentArray)) {
							strGenCommonDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t};"));
							isParentArray = false;
						}

						String getterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, true, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, this.inputSyntax, null);
						
						// Get name of instance object
						instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inbeanRefer);
						// Get new instance of object data type
						String instanceOf = "new " + getPackageName(blogicRefer, 0, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
						strGenCommonDetails.append(String.format("if (%s == null){", getterInReferParent)).append("\n\t\t");
						
						String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, false, inbeanRefer.getInputBeanId(), 0, this.inputSyntax, null)+"("+instanceOf+");";
						
						strGenCommonDetails.append("\t").append(setterInReferParent).append("\n\t\t}").append("\n\t\t");
					} else {
						 if (objMark == null) continue;
						 
						 if(Boolean.TRUE.equals(isParentArray)) {
								strGenCommonDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t};"));
								isParentArray = false;
							}
						// Marking value
						isParentArray = true;
						parentInputForSet = inbeanRefer.getInputBeanCode()+"Set";

						String getterParentRefer =  detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, true, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, this.inputSyntax, null);
						
						// Get name of instance object
						instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inbeanRefer);
						// Get new instance of object data type
						String instanceOf = "new ArrayList<" + getPackageName(blogicRefer,0, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + ">()";
						
						String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, false, inbeanRefer.getInputBeanId(), 0, this.inputSyntax, null);
						
						strGenCommonDetails.append("if(" + getterParentRefer + SPACE + "== null) {").append(NL_TAB);
						strGenCommonDetails.append(setterInReferParent + "("+instanceOf+");").append(NL);
						strGenCommonDetails.append("}").append(NL);
						
						strGenCommonDetails.append(String.format("%s.clear();", getterParentRefer));
						
						// Building for each
						String declareObjCode = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, civ.getParameterScope(), objMark);
						parentobjForGet = declareObjCode + "Get";
						
						String declareObj = getPackageName(paramIO.getBusinessDesign(), civ.getParameterScope(), objMark) + GenerateSourceCodeUtil.normalizedClassName(declareObjCode);

						if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(civ.getParameterScope())) {
							codeScope = paramIO.getBlogicInSyntax();
						} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(civ.getParameterScope())) {
							codeScope = paramIO.getBlogicObSyntax();
						} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(civ.getParameterScope())) {
							codeScope = paramIO.getBlogicOutputSyntax();
						}

						String getterParentParam =  detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, true, civ.getParameterId(), civ.getParameterScope(), codeScope, null);
						strGenCommonDetails.append("\n\t\t").append(String.format("for (%s "+parentobjForGet+" : %s){", declareObj, getterParentParam)).append("\n\t\t\t");
						
						// Build new temporary object from input refer
						strGenCommonDetails.append(MessageFormat.format(INIT_OBJECT, getPackageName(blogicRefer, 0, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj), parentInputForSet)).append("\n\t\t\t");;
						
						// Using for add one item into the list object
						getterParentInputList = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, true, inbeanRefer.getInputBeanId(), 0, this.inputSyntax, null);
					}
					
					continue;
				}
				
				String getter = "";

				if (civ.getParameterId() != null && civ.getParameterScope() != null) {
					// Marking two list primitive
					if(Boolean.TRUE.equals(civ.getArrayFlg()) && arrayFlg && DataTypeUtils.notEquals(civ.getDataType(), dataTypeSrc)) isTwooArrayPrimitive = true;
					
					// In the case item is top level
					if (StringUtils.isEmpty(inbeanRefer.getParentInputBeanId())) {
						
						if(Boolean.TRUE.equals(isParentArray)) {
							strGenCommonDetails.append(String.format("\n\t\t\t")).append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t};")).append(String.format("\n\t\t"));
							isParentArray = false;
						}

						if(isTwooArrayPrimitive) {
							strGenCommonDetails.append("\n\t\t");
							String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, this.inputSyntax, null);
							strGenCommonDetails.append(String.format("if (%s == null) {", targetNull));
							strGenCommonDetails.append("\n\t\t\t");
							String dataTypeName = GenerateSourceCodeUtil.getPrimitiveTypeName(inbeanRefer.getDataType());
							String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, this.inputSyntax, null)+"(new ArrayList<"+dataTypeName+">());";
							strGenCommonDetails.append(targetSet);
							strGenCommonDetails.append("\n\t\t}");
							strGenCommonDetails.append("\n\t\t\t");
							strGenCommonDetails.append(String.format("%s.clear();", targetNull));
							strGenCommonDetails.append("\n\t\t\t");
							String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, civ.getParameterId(), civ.getParameterScope(), codeScope, civ.getLstParameterIndex());
							
							if(GenerateSourceCodeConst.DataType.BYTE == civ.getDataType()){
								strGenCommonDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
							} else {
								strGenCommonDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
							}

							strGenCommonDetails.append("\n\t\t\t");
							strGenCommonDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc), "iter", getterParam));
							String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), dataTypeSrc, "iter");
							strGenCommonDetails.append("\n\t\t\t\t");
							String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), 0, this.inputSyntax , civ.getLstParameterIndex())+".add("+paramInput+");";
							strGenCommonDetails.append(setterInReferParent);
							strGenCommonDetails.append("\n\t\t\t");
							strGenCommonDetails.append("}");
							strGenCommonDetails.append("\n\t\t");
							strGenCommonDetails.append("}");
							strGenCommonDetails.append("\n\t\t");
						} else {
							getter = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, civ.getParameterId(), civ.getParameterScope(), codeScope , civ.getLstParameterIndex());
							// Cast data type
							getter = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), dataTypeSrc, getter);
							
							// setter of child
							String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), 0, this.inputSyntax, null);
							
							strGenCommonDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append(NL_TAB);
						}
					// In the case item is top level	
					} else if(!isParentArray && parentIdOfRefer != null && parentIdOfRefer.equals(inbeanRefer.getParentInputBeanId())) {

						if(isTwooArrayPrimitive) {
							strGenCommonDetails.append("\n\t\t");
							String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, this.inputSyntax, null);
							strGenCommonDetails.append(String.format("if (%s == null) {", targetNull));
							strGenCommonDetails.append("\n\t\t\t");
							String dataType = GenerateSourceCodeUtil.getPrimitiveTypeName(inbeanRefer.getDataType());
							String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, this.inputSyntax, null)+"(new ArrayList<"+ dataType +">());";
							strGenCommonDetails.append(targetSet);
							strGenCommonDetails.append("\n\t\t}");
							strGenCommonDetails.append("\n\t\t\t");
							strGenCommonDetails.append(String.format("%s.clear();", targetNull));
							strGenCommonDetails.append("\n\t\t\t");
							String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, civ.getParameterId(), civ.getParameterScope(), codeScope, civ.getLstParameterIndex());
							
							if(GenerateSourceCodeConst.DataType.BYTE == civ.getDataType()){
								strGenCommonDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
							} else {
								strGenCommonDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
							}
							
							strGenCommonDetails.append("\n\t\t\t");
							strGenCommonDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc), "iter", getterParam));
							String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), dataTypeSrc, "iter");
							strGenCommonDetails.append("\n\t\t\t\t");
							String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), 0, this.inputSyntax , null)+".add("+paramInput+");";
							strGenCommonDetails.append(setterInReferParent);
							strGenCommonDetails.append("\n\t\t\t");
							strGenCommonDetails.append("}");
							strGenCommonDetails.append("\n\t\t");
							strGenCommonDetails.append("}");
							strGenCommonDetails.append("\n\t\t");
						} else {
							getter = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, civ.getParameterId(), civ.getParameterScope(), codeScope, civ.getLstParameterIndex());
							// Cast data type
							getter = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), dataTypeSrc, getter);
	
							// setter of child
							String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), 0, this.inputSyntax, null);
	
							strGenCommonDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append(NL_TAB);
						}
					} else if (isParentArray && parentIdOfRefer != null && parentIdOfRefer.equals(inbeanRefer.getParentInputBeanId())){
						List<?> listObjParamOfParent = null;
						Map<String, List<?>> mAllBlogicCurrentTmp = null;
						
						if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(civ.getParameterScope())) {
							listObjParamOfParent = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, parentId, paramIO.getBusinessDesign().getLstInputBean());
							mAllBlogicCurrentTmp = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, listObjParamOfParent, null, null);
						} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(civ.getParameterScope())) {
							listObjParamOfParent = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, parentId, paramIO.getBusinessDesign().getLstObjectDefinition());
							mAllBlogicCurrentTmp = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, null, listObjParamOfParent, null);
						} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(civ.getParameterScope())) {
							listObjParamOfParent = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, parentId, paramIO.getBusinessDesign().getLstOutputBean());
							mAllBlogicCurrentTmp = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, null, null, listObjParamOfParent);
						}
						
						if(mAllBlogicCurrentTmp == null) continue;
						
						// setter of child of parentInputForSet
						List<?> listInputReferOfParent = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, parentIdOfRefer, blogicRefer.getLstInputBean());
						Map<String, List<?>> mAllInputReferTmp = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, listInputReferOfParent, null, null);

						if(isTwooArrayPrimitive) {
							strGenCommonDetails.append("\n\t\t\t");
							String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllInputReferTmp, IS_GETTER, inbeanRefer.getInputBeanId(), 0, parentInputForSet, null);
							strGenCommonDetails.append(String.format("if (%s == null) {", targetNull));
							strGenCommonDetails.append("\n\t\t\t\t");
							String dataTypeName = GenerateSourceCodeUtil.getPrimitiveTypeName(inbeanRefer.getDataType());
							String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllInputReferTmp, IS_SETTER, inbeanRefer.getInputBeanId(), 0, parentInputForSet, null)+"(new ArrayList<"+dataTypeName+">());";
							strGenCommonDetails.append(targetSet);
							strGenCommonDetails.append("\n\t\t\t}");
							strGenCommonDetails.append("\n\t\t\t");
							strGenCommonDetails.append(String.format("%s.clear();", targetNull));
							strGenCommonDetails.append("\n\t\t\t");
							String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrentTmp, IS_GETTER, civ.getParameterId(), civ.getParameterScope(), parentobjForGet, civ.getLstParameterIndex());
							
							if(GenerateSourceCodeConst.DataType.BYTE == civ.getDataType()){
								strGenCommonDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
							} else {
								strGenCommonDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
							}
						
							strGenCommonDetails.append("\n\t\t\t\t");
							strGenCommonDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc), "iter", getterParam));
							String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), dataTypeSrc, "iter");
							strGenCommonDetails.append("\n\t\t\t\t\t");
							String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllInputReferTmp, IS_GETTER, inbeanRefer.getInputBeanId(), 0, parentInputForSet, null)+".add("+paramInput+");";
							strGenCommonDetails.append(setterInReferParent);
							strGenCommonDetails.append("\n\t\t\t\t");
							strGenCommonDetails.append("}");
							strGenCommonDetails.append("\n\t\t\t");
							strGenCommonDetails.append("}");
							strGenCommonDetails.append("\n\t\t\t");
	
							if(countInputParam == currentComponent.getParameterInputBeans().size()){
								strGenCommonDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append("\n\t\t}");
							}
						} else {
							getter = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrentTmp, IS_GETTER, civ.getParameterId(), civ.getParameterScope(), parentobjForGet, civ.getLstParameterIndex());
							// Cast data type
							getter = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), dataTypeSrc, getter);
	
							
							String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllInputReferTmp, IS_SETTER, inbeanRefer.getInputBeanId(), 0, parentInputForSet, null);
	
							strGenCommonDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append(NL_TAB);
	
							if(countInputParam == currentComponent.getParameterInputBeans().size()){
								strGenCommonDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append("\n\t\t}");
							}
						}
					}
				}
			}
		}

		return strGenCommonDetails;
	}
	
	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start common node");
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
			builder.append("// End common node");
			builder.append(KEY_NL);
		}
	}
}
