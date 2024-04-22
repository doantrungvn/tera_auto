package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.NavigatorComponent;
import org.terasoluna.qp.domain.model.NavigatorDetail;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignShareService;
import org.terasoluna.qp.domain.service.generatesourcecode.CommonComponentGenerateHandler.TypeOfDataType;

/**
 * Handler for generating navigator
 *
 * @author hunghx
 * @version 1.0
 */
@Component("NavigatorComponentGenerateHandler")
public class NavigatorComponentGenerateHandler extends SequenceLogicGenerationHandler {

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;

	@Inject
	BusinessDesignShareService businessDesignShareService;
	
	@Inject
	ScreenDesignRepository screenDesignRepository;

	private static final String NL = "\n\t\t";
	private static final String NL_TAB = "\n\t\t\t";
	private static final String INPUT_SETTER = "{0}({1});";
	private static final String INIT_OBJECT = "{0} {1} = new {0}();";
	private static final String INIT_BLOGIC_INPUT_BEAN = "{0}InputBean {1} = new {0}InputBean();";

	private NavigatorComponent currentComponent;

	public NavigatorComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(NavigatorComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	private static final class NaviagatorToType {
		private static final int SCREEN = 0;
		private static final int BLOGIC = 1;
		private static final int COMMON = 2;
	}

	private static final class TransitionType {
		private static final Integer REDIRECT = 0;
		private static final Integer FORWARD = 1;
	}
	
	private static final boolean IS_GETTER = true;
	private static final boolean IS_SETTER = false;

	private Project project;
	private Module module;
	private BusinessDesign blogicCurrent;

	private String getPackageName(Module module, BusinessDesign blogic, Object obj) {
		StringBuilder pakageName = new StringBuilder(project.getPackageName());
		String pakageExternal = "";
		String code = null;
		int dataType = -1;
		String moduleCode = StringUtils.EMPTY;

		if(obj instanceof InputBean) {
			InputBean in = (InputBean) obj;
			dataType = in.getDataType();
			pakageExternal = in.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_INPUT_BEAN;
			moduleCode = in.getModuleCode();
		} else if (obj instanceof ObjectDefinition) {
			ObjectDefinition objDef = (ObjectDefinition) obj;
			dataType = objDef.getDataType();
			pakageExternal = objDef.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION;
			moduleCode = objDef.getModuleCode();
		} else if (obj instanceof OutputBean) {
			OutputBean out = (OutputBean) obj;
			dataType = out.getDataType();
			pakageExternal = out.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN;
			moduleCode = out.getModuleCode();
		}

		switch (dataType) {
			case TypeOfDataType.OBJECT :
				if (Boolean.TRUE.equals(blogic.getCustomizeFlg())) {
					pakageName.append(".domain.service.").append((blogic.getBlogicType().equals(1)?"commoncustomize": this.module.getModuleCode()))
					.append(".").append(blogic.getBusinessLogicCode()).append(code).append(".");
				} else {
					pakageName.append(".domain.service.").append((blogic.getBlogicType().equals(1)?"common": this.module.getModuleCode()))
					.append(".").append(blogic.getBusinessLogicCode()).append(code).append(".");
				}
				break;
			case TypeOfDataType.ENTITY :
				pakageName.append(".domain.model.");
				break;
			case TypeOfDataType.COMMON_OBJECT :
				pakageName.append(".domain.commonobject.");
				
				if(StringUtils.isNotEmpty(moduleCode)) {
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

	@Override
	public void handle(StringBuilder additionParam, BLogicHandlerIo paramIO) {
		project = paramIO.getProject();
		module = paramIO.getModule();
		blogicCurrent = paramIO.getBusinessDesign();
		Long languageId = paramIO.getLanguageId();

		// Content
		StringBuilder strGenNavigatorDetails = new StringBuilder("");
		BusinessDesign blogicRefer = new BusinessDesign();
		String blogicCodeCurr = blogicCurrent.getBusinessLogicCode() + "OutputBean";
		String businessLogicCodeRefer = "";
		String inputBeanSyntax = "";
		if (currentComponent != null) {
			preGencode(additionParam, paramIO);
			// get infor of related blogic
			if (currentComponent.getNavigatorToId() != null) {
				blogicRefer = businessDesignShareService.findInputBeanOfBusinessLogicForGensource(currentComponent.getNavigatorToId(), languageId, project.getProjectId());
				//Fix bug navigate to another module
				module.setModuleCode(blogicRefer.getModuleCode());
			}

			businessLogicCodeRefer = GenerateSourceCodeUtil.normalizedVariantName(blogicRefer.getBusinessLogicCode());
			inputBeanSyntax = businessLogicCodeRefer + "InputBean" + currentComponent.getNavigatorComponentId().toString();

			if (CollectionUtils.isNotEmpty(currentComponent.getParameterInputBeans())) {
				strGenNavigatorDetails.append(NL).append(MessageFormat.format(INIT_BLOGIC_INPUT_BEAN, GenerateSourceCodeUtil.normalizedClassName(businessLogicCodeRefer), inputBeanSyntax)).append("\n\t\t");;
				// Preparing data
				Map<String, List<?>> mAllBlogicCurrent = paramIO.getmAllParentAndSeflByLevelOfInOutObj();
				Map<String, List<?>> mAllBlogicRefer = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, blogicRefer.getLstInputBean(), null, null);
				
				// Variable for main processing
				int countNavigatorDetail = 0;
				String getterParentInputList = "";
				String parentInputForSet = "";
				String parentOutputForGet = "";
				// Storing parent id of object
				String parentIdOfInput = "";
				// Start is false
				Boolean isParentArray = false;
				String instanceNmObj = "";
				
				// Build setting value from parameter to input of blogic input bean refer 
				List<NavigatorDetail> lstNavigatorDetailParam = currentComponent.getParameterInputBeans();
				for (NavigatorDetail navigatorDetail : lstNavigatorDetailParam) {
					countNavigatorDetail++;
					boolean isTwooArrayPrimitive = false;
					
					if(StringUtils.isEmpty(navigatorDetail.getParameterId())) {
						if(isParentArray && countNavigatorDetail == lstNavigatorDetailParam.size()) {
							strGenNavigatorDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}"));
						}

						continue;
					}
					
					// Get input Refer for setting
					InputBean inbeanRefer = (InputBean) getObjByTypeScope(0, 
							mAllBlogicRefer.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID + navigatorDetail.getInputBeanId().toString()),
							navigatorDetail.getInputBeanId().toString());

					if(inbeanRefer == null) continue;
					
					if(BusinessDesignConst.DataType.OBJECT.equals(inbeanRefer.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(inbeanRefer.getDataType()) 
							|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(inbeanRefer.getDataType()) || BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(inbeanRefer.getDataType())) {
						// Marking value
						parentIdOfInput = inbeanRefer.getInputBeanId();
						
						if(Boolean.FALSE.equals(inbeanRefer.getArrayFlg())) {
							// Marking value
							if(Boolean.TRUE.equals(isParentArray)) {
								strGenNavigatorDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}"));
								isParentArray = false;
							}
							
							String getterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null);
							// Get name of instance object
							instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inbeanRefer);
							// Get new instance of object data type
							String instanceOf = "new " + getPackageName(paramIO.getModule(), blogicRefer, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
							strGenNavigatorDetails.append(String.format("if (%s == null){", getterInReferParent)).append(NL_TAB);
							
							String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax, null)+"("+instanceOf+");";
							
							strGenNavigatorDetails.append(setterInReferParent).append("\n\t\t}").append("\n\t\t");
						} else {
							
							if(Boolean.TRUE.equals(isParentArray)) {
								strGenNavigatorDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}"));
								isParentArray = false;
							}
							
							strGenNavigatorDetails.append("\n\t");
							// Marking value
							isParentArray = true;
							// Marking parent syntax for set
							parentInputForSet = "temp";
							
							// Get name of instance object
							instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inbeanRefer);
							// Get new instance of object data type
							String instanceOf = "new ArrayList<" + getPackageName(paramIO.getModule(), blogicRefer, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + ">()";
							String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax, null)+"("+instanceOf+");";
							
							strGenNavigatorDetails.append("\t").append(setterInReferParent).append("\n\t\t");;
							
							// Build for each from list - list
							// Get output current for getter
							OutputBean outbeanCurrent = (OutputBean) getObjByTypeScope(navigatorDetail.getParameterScope(), 
									mAllBlogicCurrent.get(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID + navigatorDetail.getParameterId()), navigatorDetail.getParameterId().toString());
							
							if(outbeanCurrent == null) continue;
							String getterParamOfParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, outbeanCurrent.getOutputBeanId(), navigatorDetail.getParameterScope(), blogicCodeCurr, null);
							// Marking parent syntax for get
							parentOutputForGet =  detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, outbeanCurrent);
							// initial for new instant object from parameter is list object
							String declareObj = getPackageName(paramIO.getModule(), blogicCurrent, outbeanCurrent) + GenerateSourceCodeUtil.normalizedClassName(parentOutputForGet);
							// Using for add one item into the list object
							getterParentInputList = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax, null);
							
							strGenNavigatorDetails.append("\n\t\t").append(String.format("for (%s "+parentOutputForGet+" : %s){", declareObj, getterParamOfParent)).append("\n\t\t\t");
							// Build new temporary object from input refer
							strGenNavigatorDetails.append(MessageFormat.format(INIT_OBJECT, getPackageName(paramIO.getModule(), blogicRefer, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj), GenerateSourceCodeUtil.normalizedVariantName(parentInputForSet))).append("\n\t\t\t");;	
							// In the case item is end of list at here
							if(countNavigatorDetail == lstNavigatorDetailParam.size()) {
								strGenNavigatorDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t};"));
							}
						}

						continue;
					}

					String getter = "";

					if (navigatorDetail.getParameterId() != null) {
						// Input bean
						if (navigatorDetail.getParameterScope().equals(2)) {
							// Get output current for getter
							OutputBean outbeanCurrent = (OutputBean) getObjByTypeScope(2, 
									mAllBlogicCurrent.get(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID + navigatorDetail.getParameterId()), 
									navigatorDetail.getParameterId().toString());
							// Marking two list primitive
							if(Boolean.TRUE.equals(outbeanCurrent.getArrayFlg()) && inbeanRefer.getArrayFlg() && DataTypeUtils.notEquals(outbeanCurrent.getDataType(), inbeanRefer.getDataType())) isTwooArrayPrimitive = true;
							
							// In the case item is top level
							if (StringUtils.isEmpty(inbeanRefer.getParentInputBeanId())) {
								
								if(Boolean.TRUE.equals(isParentArray)) {
									strGenNavigatorDetails.append(String.format("\n\t\t\t")).append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t};")).append(String.format("\n\t\t"));
									isParentArray = false;
								}
								
								if(isTwooArrayPrimitive) {
									strGenNavigatorDetails.append("\n\t\t");
									String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null);
									strGenNavigatorDetails.append(String.format("if (%s == null) {", targetNull));
									strGenNavigatorDetails.append("\n\t\t\t");
									String dataTypeSrc = GenerateSourceCodeUtil.getPrimitiveTypeName(inbeanRefer.getDataType());
									String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null)+"(new ArrayList<"+ dataTypeSrc + ">());";
									strGenNavigatorDetails.append(targetSet);
									strGenNavigatorDetails.append("\n\t\t}");
									strGenNavigatorDetails.append("\n\t\t");
									strGenNavigatorDetails.append(String.format("%s.clear();", targetNull));
									strGenNavigatorDetails.append("\n\t\t");
									String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, outbeanCurrent.getOutputBeanId(), 2, blogicCodeCurr, navigatorDetail.getLstParameterIndex());
									
									if(GenerateSourceCodeConst.DataType.BYTE == outbeanCurrent.getDataType()){
										strGenNavigatorDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
									} else {
										strGenNavigatorDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
									}
									
									strGenNavigatorDetails.append("\n\t\t\t");
									strGenNavigatorDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(outbeanCurrent.getDataType()), "iter", getterParam));
									String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), outbeanCurrent.getDataType(), "iter");
									strGenNavigatorDetails.append("\n\t\t\t\t");
									String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax , null)+".add("+paramInput+");";
									strGenNavigatorDetails.append(setterInReferParent);
									strGenNavigatorDetails.append("\n\t\t\t");
									strGenNavigatorDetails.append("}");
									strGenNavigatorDetails.append("\n\t\t");
									strGenNavigatorDetails.append("}");
									strGenNavigatorDetails.append("\n\t\t");
								} else {
									getter = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, outbeanCurrent.getOutputBeanId(), 2, blogicCodeCurr , navigatorDetail.getLstParameterIndex());
									// Cast data type if happen
									getter = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), outbeanCurrent.getDataType(), getter);
									
									// setter of child
									String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax, null);
									
									if(Boolean.TRUE.equals(inbeanRefer.getArrayFlg()) && CollectionUtils.isNotEmpty(navigatorDetail.getLstParameterIndex())) {
										strGenNavigatorDetails.append(String.format(setterChild, getter)+(";")).append("\n\t\t");
									} else {
										strGenNavigatorDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t");
									}
								}
							// In the case item is top level	
							} else if(!isParentArray && parentIdOfInput != null && parentIdOfInput.equals(inbeanRefer.getParentInputBeanId())) {
								if(isTwooArrayPrimitive) {
									strGenNavigatorDetails.append("\n\t\t");
									String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null);
									strGenNavigatorDetails.append(String.format("if (%s == null) {", targetNull));
									strGenNavigatorDetails.append("\n\t\t\t");
									String dataTypeSrc = GenerateSourceCodeUtil.getPrimitiveTypeName(inbeanRefer.getDataType());
									String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null)+"(new ArrayList<"+ dataTypeSrc + ">());";
									strGenNavigatorDetails.append(targetSet);
									strGenNavigatorDetails.append("\n\t\t}");
									strGenNavigatorDetails.append("\n\t\t");
									strGenNavigatorDetails.append(String.format("%s.clear();", targetNull));
									strGenNavigatorDetails.append("\n\t\t");
									String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, outbeanCurrent.getOutputBeanId(), 2, blogicCodeCurr, navigatorDetail.getLstParameterIndex());
									
									if(GenerateSourceCodeConst.DataType.BYTE == outbeanCurrent.getDataType()){
										strGenNavigatorDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
									} else {
										strGenNavigatorDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
									}
									
									strGenNavigatorDetails.append("\n\t\t\t");
									strGenNavigatorDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(outbeanCurrent.getDataType()), "iter", getterParam));
									String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), outbeanCurrent.getDataType(), "iter");
									strGenNavigatorDetails.append("\n\t\t\t\t");
									String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax , null)+".add("+paramInput+");";
									strGenNavigatorDetails.append(setterInReferParent);
									strGenNavigatorDetails.append("\n\t\t\t");
									strGenNavigatorDetails.append("}");
									strGenNavigatorDetails.append("\n\t\t");
									strGenNavigatorDetails.append("}");
									strGenNavigatorDetails.append("\n\t\t");
								} else {
									getter = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, outbeanCurrent.getOutputBeanId(), 2, blogicCodeCurr, navigatorDetail.getLstParameterIndex());
									// Cast data type if happen
									getter = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), outbeanCurrent.getDataType(), getter);
									
									// setter of child
									String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax, null);
	
									if(Boolean.TRUE.equals(inbeanRefer.getArrayFlg()) && CollectionUtils.isNotEmpty(navigatorDetail.getLstParameterIndex())) {
										strGenNavigatorDetails.append(String.format(setterChild, getter)+(";")).append("\n\t\t");
									} else {
										strGenNavigatorDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t");
									}
								}
							} else if (isParentArray && parentIdOfInput != null && parentIdOfInput.equals(inbeanRefer.getParentInputBeanId())){
								if(isTwooArrayPrimitive) {
									strGenNavigatorDetails.append("\n\t\t");
									String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null);
									strGenNavigatorDetails.append(String.format("if (%s == null) {", targetNull));
									strGenNavigatorDetails.append("\n\t\t\t");
									String dataTypeSrc = GenerateSourceCodeUtil.getPrimitiveTypeName(inbeanRefer.getDataType());
									String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null)+"(new ArrayList<"+dataTypeSrc+">());";
									strGenNavigatorDetails.append(targetSet);
									strGenNavigatorDetails.append("\n\t\t}");
									strGenNavigatorDetails.append("\n\t\t");
									strGenNavigatorDetails.append(String.format("%s.clear();", targetNull));
									strGenNavigatorDetails.append("\n\t\t");
									String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, outbeanCurrent.getOutputBeanId(), 2, blogicCodeCurr, navigatorDetail.getLstParameterIndex());
									
									if(GenerateSourceCodeConst.DataType.BYTE == outbeanCurrent.getDataType()){
										strGenNavigatorDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
									} else {
										strGenNavigatorDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
									}

									strGenNavigatorDetails.append("\n\t\t\t");
									strGenNavigatorDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(outbeanCurrent.getDataType()), "iter", getterParam));
									String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), outbeanCurrent.getDataType(), "iter");
									strGenNavigatorDetails.append("\n\t\t\t\t");
									String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax , null)+".add("+paramInput+");";
									strGenNavigatorDetails.append(setterInReferParent);
									strGenNavigatorDetails.append("\n\t\t\t");
									strGenNavigatorDetails.append("}");
									strGenNavigatorDetails.append("\n\t\t");
									strGenNavigatorDetails.append("}");
									strGenNavigatorDetails.append("\n\t\t");
								} else {
									List<?> listOutputParamOfParent = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, outbeanCurrent.getParentOutputBeanId(), blogicCurrent.getLstOutputBean());
									Map<String, List<?>> mAllBlogicCurrentTmp = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, blogicCurrent.getLstInputBean(), blogicCurrent.getLstObjectDefinition(), listOutputParamOfParent);
									getter = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrentTmp, IS_GETTER, outbeanCurrent.getOutputBeanId(), 2, parentOutputForGet, null);
									
									// Cast data type if happen
									getter = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), outbeanCurrent.getDataType(), getter);
									
									// setter of child of parentInputForSet
									List<?> listInputReferOfParent = detailServiceImpHandler.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, parentIdOfInput, blogicRefer.getLstInputBean());
									Map<String, List<?>> mAllBlogicReferTmp = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, listInputReferOfParent, blogicRefer.getLstObjectDefinition(), blogicRefer.getLstOutputBean());
									String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicReferTmp, IS_SETTER, inbeanRefer.getInputBeanId(), 0, parentInputForSet, null);
	
									strGenNavigatorDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t\t");
	
									if(countNavigatorDetail == lstNavigatorDetailParam.size()){
										strGenNavigatorDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}"));
									}
								}
							}
						}
					}
				}
			}
		}

		// Setting blogic model
		// blogicCurrent.setStrModelSettingOfNavigator(strGenNavigatorDetails.toString());

		NavigatorComponent navigatorComponent = new NavigatorComponent();
		navigatorComponent.setNavigatorId(currentComponent.getNavigatorComponentId().toString());

		StringBuilder destination = new StringBuilder("");

		switch (currentComponent.getNavigatorToType()) {
			case NaviagatorToType.SCREEN:
				ScreenDesign sc = screenDesignRepository.getScreenInfoById(currentComponent.getNavigatorToId());
				destination.append(sc.getModuleCode()).append("/").append(sc.getScreenCode()).append("");
				break;
			case NaviagatorToType.BLOGIC:
				if (strGenNavigatorDetails.toString().length() == 0) {
					strGenNavigatorDetails.append(NL).append(MessageFormat.format(INIT_BLOGIC_INPUT_BEAN, GenerateSourceCodeUtil.normalizedClassName(businessLogicCodeRefer), inputBeanSyntax));
				}

				strGenNavigatorDetails.append(NL).append(MessageFormat.format("{0}InputForm {1}InputForm{3}  = beanMapper.map({2} , {0}InputForm.class);", GenerateSourceCodeUtil.normalizedClassName(businessLogicCodeRefer), businessLogicCodeRefer, inputBeanSyntax, String.valueOf(currentComponent.getNavigatorComponentId())));

				if (TransitionType.REDIRECT.equals(currentComponent.getTransitionType())) {
					strGenNavigatorDetails.append(NL).append(MessageFormat.format("redirectAttr.addFlashAttribute(\"{0}InputForm\", {0}InputForm{1});", businessLogicCodeRefer,  String.valueOf(currentComponent.getNavigatorComponentId())));
					destination.append("redirect:/").append(module.getModuleCode()).append("/").append(blogicRefer.getBusinessLogicCode()).append("");
				} else if (TransitionType.FORWARD.equals(currentComponent.getTransitionType())) {
					destination.append("forward:/").append(module.getModuleCode()).append("/").append(blogicRefer.getBusinessLogicCode()).append("");
				}
				break;
			case NaviagatorToType.COMMON:
				destination.append(DbDomainConst.REDIRECT_DELETION_SUCCESS);
				break;
		}

		navigatorComponent.setNavigatorAssignContent(strGenNavigatorDetails.toString());

		navigatorComponent.setNavigatorDirect(GenerateSourceCodeUtil.normalizedURL(destination.toString()));
		if (CollectionUtils.isNotEmpty(blogicCurrent.getLstNavigatorDetails())) {
			blogicCurrent.getLstNavigatorDetails().add(navigatorComponent);
		} else {
			List<NavigatorComponent> lstNavigator = new ArrayList<NavigatorComponent>();
			lstNavigator.add(navigatorComponent);
			blogicCurrent.setLstNavigatorDetails(lstNavigator);
		}

		StringBuilder tmp = new StringBuilder("");
		tmp.append(NL).append("ou.setNavigatorId(").append("\"").append(currentComponent.getNavigatorComponentId().toString()).append("\"").append(");").append(NL);
//		tmp.append(NL).append("return ou;");
		additionParam.append(tmp);
		postGencode(additionParam, paramIO);
	}

	public static Object getObjByTypeScope(int typeScope, List<?> lstBbj, String id) {
		String idTmp = "";
		
		if (CollectionUtils.isNotEmpty(lstBbj)) {
			for(Object obj : lstBbj) {
				switch (typeScope) {
				case 0:
					InputBean ib = (InputBean) obj;
					idTmp = ib.getInputBeanId();
					break;
				case 1:
					ObjectDefinition od = (ObjectDefinition) obj;
					idTmp = od.getObjectDefinitionId();
					break;
				case 2:
					OutputBean ob = (OutputBean) obj;
					idTmp = ob.getOutputBeanId();
					break;
				}
				
				if (DataTypeUtils.equals(idTmp, id)) {
					return obj;
				}
			}
		}
		
		return new Object();
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start navigator node");
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
			builder.append("// End navigator node");
			builder.append(KEY_NL);
		}
	}
}
