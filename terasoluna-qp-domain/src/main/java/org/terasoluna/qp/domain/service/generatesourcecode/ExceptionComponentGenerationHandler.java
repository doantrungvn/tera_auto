package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.ExceptionComponent;
import org.terasoluna.qp.domain.model.ExceptionDetail;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignShareService;
import org.terasoluna.qp.domain.service.generatesourcecode.CommonComponentGenerateHandler.TypeOfDataType;

@Component("ExceptionGenerationHandler")
public class ExceptionComponentGenerationHandler extends SequenceLogicGenerationHandler {

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

	private static final class ExceptionToType {
		private static final int CURRENT_BLOGIC = 0;
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
	
	@Inject
    @Named("CL_COMMON_ERROR_PATH")
	private CodeList CL_COMMON_ERROR_PATH;

	private String getPackageName(Module module, BusinessDesign blogic, Object obj) {
		StringBuilder pakageName = new StringBuilder(project.getPackageName());
		String pakageExternal = "";
		String code = null;
		int dataType = -1;

		if(obj instanceof InputBean) {
			InputBean in = (InputBean) obj;
			dataType = in.getDataType();
			pakageExternal = in.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_INPUT_BEAN;
		} else if (obj instanceof ObjectDefinition) {
			ObjectDefinition objDef = (ObjectDefinition) obj;
			dataType = objDef.getDataType();
			pakageExternal = objDef.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION;
		} else if (obj instanceof OutputBean) {
			OutputBean out = (OutputBean) obj;
			dataType = out.getDataType();
			pakageExternal = out.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN;
		}

		switch (dataType) {
			case TypeOfDataType.OBJECT :
				if (Boolean.TRUE.equals(blogic.getCustomizeFlg())) {
					pakageName.append(".domain.service.").append((blogic.getBlogicType().equals(1)?"commonservicecustomize": this.module.getModuleCode()))
					.append(".").append(blogic.getBusinessLogicCode()).append(code).append(".");
				} else {
					pakageName.append(".domain.service.").append((blogic.getBlogicType().equals(1)?"commonservice": this.module.getModuleCode()))
					.append(".").append(blogic.getBusinessLogicCode()).append(code).append(".");
				}
				break;
			case TypeOfDataType.ENTITY :
				pakageName.append(".domain.model.");
				break;
			case TypeOfDataType.COMMON_OBJECT :
				pakageName.append(".domain.commonobject.");
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
		StringBuilder strGenExceptionDetails = new StringBuilder("");
		BusinessDesign blogicRefer = new BusinessDesign();
		String blogicCodeCurr = blogicCurrent.getBusinessLogicCode() + "OutputBean";
		String businessLogicCodeRefer = "";
		String inputBeanSyntax = "";
		if (currentComponent != null) {
			preGencode(additionParam, paramIO);
			// get infor of related blogic
			if (currentComponent.getExceptionToId() != null) {
				blogicRefer = businessDesignShareService.findInputBeanOfBusinessLogicForGensource(currentComponent.getExceptionToId(), languageId, project.getProjectId());
				//Fix bug navigate to another module
				module.setModuleCode(blogicRefer.getModuleCode());
			}

			businessLogicCodeRefer = GenerateSourceCodeUtil.normalizedVariantName(blogicRefer.getBusinessLogicCode());
			inputBeanSyntax = businessLogicCodeRefer + "InputBean" + currentComponent.getExceptionComponentId().toString();

			if (CollectionUtils.isNotEmpty(currentComponent.getParameterInputBeans())) {
				strGenExceptionDetails.append(NL).append(MessageFormat.format(INIT_BLOGIC_INPUT_BEAN, GenerateSourceCodeUtil.normalizedClassName(businessLogicCodeRefer), inputBeanSyntax)).append("\n\t\t");;
				// Preparing data
				Map<String, List<?>> mAllBlogicCurrent = paramIO.getmAllParentAndSeflByLevelOfInOutObj();
				Map<String, List<?>> mAllBlogicRefer = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, blogicRefer.getLstInputBean(), null, null);
				
				// Variable for main processing
				int countExceptionDetail = 0;
				String getterParentInputList = "";
				String parentInputForSet = "";
				String parentOutputForGet = "";
				// Storing parent id of object
				String parentIdOfInput = "";
				// Start is false
				Boolean isParentArray = false;
				String instanceNmObj = "";
				
				// Build setting value from parameter to input of blogic input bean refer 
				List<ExceptionDetail> lstExceptionDetailParam = currentComponent.getParameterInputBeans();
				for (ExceptionDetail exceptionDetail : lstExceptionDetailParam) {
					countExceptionDetail++;
					boolean isTwooArrayPrimitive = false;
					
					if(StringUtils.isEmpty(exceptionDetail.getParameterId())) {
						if(isParentArray && countExceptionDetail == lstExceptionDetailParam.size()) {
							strGenExceptionDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}"));
						}

						continue;
					}
					
					// Get input Refer for setting
					InputBean inbeanRefer = (InputBean) getObjByTypeScope(0, 
							mAllBlogicRefer.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID + exceptionDetail.getInputBeanId().toString()),
							exceptionDetail.getInputBeanId().toString());

					if(inbeanRefer == null) continue;
					
					if(BusinessDesignConst.DataType.OBJECT.equals(inbeanRefer.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(inbeanRefer.getDataType()) 
							|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(inbeanRefer.getDataType()) || BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(inbeanRefer.getDataType())) {
						// Marking value
						parentIdOfInput = inbeanRefer.getInputBeanId();
						
						if(Boolean.FALSE.equals(inbeanRefer.getArrayFlg())) {
							// Marking value
							if(Boolean.TRUE.equals(isParentArray)) {
								strGenExceptionDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}"));
								isParentArray = false;
							}
							
							String getterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null);
							// Get name of instance object
							instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inbeanRefer);
							// Get new instance of object data type
							String instanceOf = "new " + getPackageName(paramIO.getModule(), blogicRefer, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
							strGenExceptionDetails.append(String.format("if (%s == null){", getterInReferParent)).append(NL_TAB);
							
							String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax, null)+"("+instanceOf+");";
							
							strGenExceptionDetails.append(setterInReferParent).append("\n\t\t}").append("\n\t\t");
						} else {
							
							if(Boolean.TRUE.equals(isParentArray)) {
								strGenExceptionDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}"));
								isParentArray = false;
							}
							
							strGenExceptionDetails.append("\n\t");
							// Marking value
							isParentArray = true;
							// Marking parent syntax for set
							parentInputForSet = "temp";
							
							// Get name of instance object
							instanceNmObj = detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inbeanRefer);
							// Get new instance of object data type
							String instanceOf = "new ArrayList<" + getPackageName(paramIO.getModule(), blogicRefer, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + ">()";
							String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax, null)+"("+instanceOf+");";
							
							strGenExceptionDetails.append("\t").append(setterInReferParent).append("\n\t\t");;
							
							// Build for each from list - list
							// Get output current for getter
							OutputBean outbeanCurrent = (OutputBean) getObjByTypeScope(exceptionDetail.getParameterScope(), 
									mAllBlogicCurrent.get(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID + exceptionDetail.getParameterId()), exceptionDetail.getParameterId().toString());
							
							if(outbeanCurrent == null) continue;
							String getterParamOfParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, outbeanCurrent.getOutputBeanId(), exceptionDetail.getParameterScope(), blogicCodeCurr, null);
							// Marking parent syntax for get
							parentOutputForGet =  detailServiceImpHandler.getNameDeclareObjByScope(GenerateSourceCodeConst.GenerateScope.BLOGIC, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, outbeanCurrent);
							// initial for new instant object from parameter is list object
							String declareObj = getPackageName(paramIO.getModule(), blogicCurrent, outbeanCurrent) + GenerateSourceCodeUtil.normalizedClassName(parentOutputForGet);
							
							strGenExceptionDetails.append("\n\t\t").append(String.format("for (%s "+parentOutputForGet+" : %s){", declareObj, getterParamOfParent)).append("\n\t\t\t");
							// Build new temporary object from input refer
							strGenExceptionDetails.append(MessageFormat.format(INIT_OBJECT, getPackageName(paramIO.getModule(), blogicRefer, inbeanRefer) + GenerateSourceCodeUtil.normalizedClassName(instanceNmObj), GenerateSourceCodeUtil.normalizedVariantName(parentInputForSet))).append("\n\t\t\t");;	
							// Using for add one item into the list object
							getterParentInputList = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax, null);
							// In the case item is end of list at here
							if(countExceptionDetail == lstExceptionDetailParam.size()) {
								strGenExceptionDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t};"));
							}
						}

						continue;
					}

					String getter = "";

					if (exceptionDetail.getParameterId() != null) {
						// Input bean
						if (exceptionDetail.getParameterScope().equals(2)) {
							// Get output current for getter
							OutputBean outbeanCurrent = (OutputBean) getObjByTypeScope(2, 
									mAllBlogicCurrent.get(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID + exceptionDetail.getParameterId()), 
									exceptionDetail.getParameterId().toString());
							// Marking two list primitive
							if(Boolean.TRUE.equals(outbeanCurrent.getArrayFlg()) && inbeanRefer.getArrayFlg() && DataTypeUtils.notEquals(outbeanCurrent.getDataType(), inbeanRefer.getDataType())) isTwooArrayPrimitive = true;
							
							// In the case item is top level
							if (StringUtils.isEmpty(inbeanRefer.getParentInputBeanId())) {
								
								if(Boolean.TRUE.equals(isParentArray)) {
									strGenExceptionDetails.append(String.format("\n\t\t\t")).append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t};")).append(String.format("\n\t\t"));
									isParentArray = false;
								}
								
								if(isTwooArrayPrimitive) {
									strGenExceptionDetails.append("\n\t\t");
									String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null);
									strGenExceptionDetails.append(String.format("if (%s == null) {", targetNull));
									String dataType = GenerateSourceCodeUtil.getPrimitiveTypeName(inbeanRefer.getDataType());
									String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null)+"(new ArrayList<"+ dataType + ">());";
									strGenExceptionDetails.append(targetSet);
									strGenExceptionDetails.append("\n\t\t}");
									strGenExceptionDetails.append("\n\t\t");
									strGenExceptionDetails.append(String.format("%s.clear();", targetNull));
									strGenExceptionDetails.append("\n\t\t");
									String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, outbeanCurrent.getOutputBeanId(), 2, blogicCodeCurr, exceptionDetail.getLstParameterIndex());
									
									if(GenerateSourceCodeConst.DataType.BYTE == outbeanCurrent.getDataType()){
										strGenExceptionDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
									} else {
										strGenExceptionDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
									}

									strGenExceptionDetails.append("\n\t\t\t");
									strGenExceptionDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(outbeanCurrent.getDataType()), "iter", getterParam));
									String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), outbeanCurrent.getDataType(), "iter");
									strGenExceptionDetails.append("\n\t\t\t\t");
									String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax , null)+".add("+paramInput+");";
									strGenExceptionDetails.append(setterInReferParent);
									strGenExceptionDetails.append("\n\t\t\t");
									strGenExceptionDetails.append("}");
									strGenExceptionDetails.append("\n\t\t");
									strGenExceptionDetails.append("}");
									strGenExceptionDetails.append("\n\t\t");
								} else {
									getter = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, outbeanCurrent.getOutputBeanId(), 2, blogicCodeCurr , exceptionDetail.getLstParameterIndex());
									// Cast data type if happen
									getter = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), outbeanCurrent.getDataType(), getter);
									
									// setter of child
									String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax, null);
									
									if(Boolean.TRUE.equals(inbeanRefer.getArrayFlg()) && CollectionUtils.isNotEmpty(exceptionDetail.getLstParameterIndex())) {
										strGenExceptionDetails.append(String.format(setterChild, getter)+(";")).append("\n\t\t");
									} else {
										strGenExceptionDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t");
									}
								}
							// In the case item is top level	
							} else if(!isParentArray && parentIdOfInput != null && parentIdOfInput.equals(inbeanRefer.getParentInputBeanId())) {
								if(isTwooArrayPrimitive) {
									strGenExceptionDetails.append("\n\t\t");
									String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null);
									strGenExceptionDetails.append(String.format("if (%s == null) {", targetNull));
									strGenExceptionDetails.append("\n\t\t\t");
									String dataType = GenerateSourceCodeUtil.getPrimitiveTypeName(inbeanRefer.getDataType());
									String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null)+"(new ArrayList<"+ dataType +">());";
									strGenExceptionDetails.append(targetSet);
									strGenExceptionDetails.append("\n\t\t}");
									strGenExceptionDetails.append("\n\t\t");
									strGenExceptionDetails.append(String.format("%s.clear();", targetNull));
									strGenExceptionDetails.append("\n\t\t");
									String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, outbeanCurrent.getOutputBeanId(), 2, blogicCodeCurr, exceptionDetail.getLstParameterIndex());
									
									if(GenerateSourceCodeConst.DataType.BYTE == outbeanCurrent.getDataType()){
										strGenExceptionDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
									} else {
										strGenExceptionDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
									}

									strGenExceptionDetails.append("\n\t\t\t");
									strGenExceptionDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(outbeanCurrent.getDataType()), "iter", getterParam));
									String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), outbeanCurrent.getDataType(), "iter");
									strGenExceptionDetails.append("\n\t\t\t\t");
									String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax , null)+".add("+paramInput+");";
									strGenExceptionDetails.append(setterInReferParent);
									strGenExceptionDetails.append("\n\t\t\t");
									strGenExceptionDetails.append("}");
									strGenExceptionDetails.append("\n\t\t");
									strGenExceptionDetails.append("}");
									strGenExceptionDetails.append("\n\t\t");
								} else {
									getter = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, outbeanCurrent.getOutputBeanId(), 2, blogicCodeCurr, exceptionDetail.getLstParameterIndex());
									// Cast data type if happen
									getter = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), outbeanCurrent.getDataType(), getter);
									
									// setter of child
									String setterChild = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax, null);
	
									if(Boolean.TRUE.equals(inbeanRefer.getArrayFlg()) && CollectionUtils.isNotEmpty(exceptionDetail.getLstParameterIndex())) {
										strGenExceptionDetails.append(String.format(setterChild, getter)+(";")).append("\n\t\t");
									} else {
										strGenExceptionDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t");
									}
								}
							} else if (isParentArray && parentIdOfInput != null && parentIdOfInput.equals(inbeanRefer.getParentInputBeanId())){
								if(isTwooArrayPrimitive) {
									strGenExceptionDetails.append("\n\t\t");
									String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null);
									strGenExceptionDetails.append(String.format("if (%s == null) {", targetNull));
									strGenExceptionDetails.append("\n\t\t\t");
									String dataType = GenerateSourceCodeUtil.getPrimitiveTypeName(inbeanRefer.getDataType());
									String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_SETTER, inbeanRefer.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputBeanSyntax, null)+"(new ArrayList<"+ dataType + ">());";
									strGenExceptionDetails.append(targetSet);
									strGenExceptionDetails.append("\n\t\t}");
									strGenExceptionDetails.append("\n\t\t");
									strGenExceptionDetails.append(String.format("%s.clear();", targetNull));
									strGenExceptionDetails.append("\n\t\t");
									String getterParam = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicCurrent, IS_GETTER, outbeanCurrent.getOutputBeanId(), 2, blogicCodeCurr, exceptionDetail.getLstParameterIndex());
									
									if(GenerateSourceCodeConst.DataType.BYTE == outbeanCurrent.getDataType()){
										strGenExceptionDetails.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
									} else {
										strGenExceptionDetails.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
									}
									
									strGenExceptionDetails.append("\n\t\t\t");
									strGenExceptionDetails.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(outbeanCurrent.getDataType()), "iter", getterParam));
									String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(inbeanRefer.getDataType(), outbeanCurrent.getDataType(), "iter");
									strGenExceptionDetails.append("\n\t\t\t\t");
									String setterInReferParent = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllBlogicRefer, IS_GETTER, inbeanRefer.getInputBeanId(), 0, inputBeanSyntax , null)+".add("+paramInput+");";
									strGenExceptionDetails.append(setterInReferParent);
									strGenExceptionDetails.append("\n\t\t\t");
									strGenExceptionDetails.append("}");
									strGenExceptionDetails.append("\n\t\t");
									strGenExceptionDetails.append("}");
									strGenExceptionDetails.append("\n\t\t");
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
	
									strGenExceptionDetails.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t\t");
	
									if(countExceptionDetail == lstExceptionDetailParam.size()){
										strGenExceptionDetails.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t}"));
									}
								}
							}
						}
					}
				}
			}
		}

		// Setting blogic model
		// blogicCurrent.setStrModelSettingOfException(strGenExceptionDetails.toString());

		ExceptionComponent exceptionComponent = new ExceptionComponent();
		exceptionComponent.setExceptionId(currentComponent.getExceptionComponentId().toString());

		StringBuilder destination = new StringBuilder("");

		switch (currentComponent.getExceptionToType()) {
			case ExceptionToType.CURRENT_BLOGIC:
				ScreenDesign sc = screenDesignRepository.getScreenInfoById(paramIO.getBusinessDesign().getScreenId());
				destination.append(sc.getModuleCode()).append("/").append(sc.getScreenCode()).append("");
				break;
			case ExceptionToType.BLOGIC:
				if (strGenExceptionDetails.toString().length() == 0) {
					strGenExceptionDetails.append(NL).append(MessageFormat.format(INIT_BLOGIC_INPUT_BEAN, GenerateSourceCodeUtil.normalizedClassName(businessLogicCodeRefer), inputBeanSyntax));
				}

				strGenExceptionDetails.append(NL).append(MessageFormat.format("{0}InputForm {1}InputForm{3}  = beanMapper.map({2} , {0}InputForm.class);", GenerateSourceCodeUtil.normalizedClassName(businessLogicCodeRefer), businessLogicCodeRefer, inputBeanSyntax,  String.valueOf(currentComponent.getExceptionComponentId())));

				if (TransitionType.REDIRECT.equals(currentComponent.getTransitionType())) {
					strGenExceptionDetails.append(NL).append(MessageFormat.format("redirectAttr.addFlashAttribute(\"{0}InputForm\", {0}InputForm{1});", businessLogicCodeRefer,  String.valueOf(currentComponent.getExceptionComponentId())));
					strGenExceptionDetails.append(NL).append("redirectAttr.addFlashAttribute(\"message\", ex.getResultMessages());");
					destination.append("redirect:/").append(module.getModuleCode()).append("/").append(blogicRefer.getBusinessLogicCode()).append("");
				} else if (TransitionType.FORWARD.equals(currentComponent.getTransitionType())) {
					destination.append("forward:/").append(module.getModuleCode()).append("/").append(blogicRefer.getBusinessLogicCode()).append("");
				}
				break;
			case ExceptionToType.COMMON:
				destination.append(CL_COMMON_ERROR_PATH.asMap().get(String.valueOf(currentComponent.getExceptionToId())));
				break;
				
		}

		exceptionComponent.setExceptionAssignContent(strGenExceptionDetails.toString());

		exceptionComponent.setExceptionDirect(GenerateSourceCodeUtil.normalizedURL(destination.toString()));
		if (CollectionUtils.isNotEmpty(blogicCurrent.getLstExceptionDetails())) {
			blogicCurrent.getLstExceptionDetails().add(exceptionComponent);
		} else {
			List<ExceptionComponent> lstException = new ArrayList<ExceptionComponent>();
			lstException.add(exceptionComponent);
			blogicCurrent.setLstExceptionDetails(lstException);
		}

		StringBuilder tmp = new StringBuilder("");
//		tmp.append(NL).append("ou.setExceptionId(").append("\"").append(currentComponent.getExceptionComponentId().toString()).append("\"").append(");");

		additionParam.append(tmp);
		
//		additionParam.append(NL);
		additionParam.append("throw new BusinessException(lstErrorMessages, new Throwable(\"1_" + currentComponent.getExceptionComponentId() +"\"));");

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

	private ExceptionComponent currentComponent;

	public ExceptionComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(ExceptionComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start exception node");
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
			builder.append("// End exception node");
			builder.append(KEY_NL);
		}
	}
}