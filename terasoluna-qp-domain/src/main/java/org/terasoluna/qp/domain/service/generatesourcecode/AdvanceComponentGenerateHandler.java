package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.app.common.constants.DbDomainConst.FunctionType;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.domain.model.AdvanceComponent;
import org.terasoluna.qp.domain.model.AdvanceInputValue;
import org.terasoluna.qp.domain.model.AdvanceOutputValue;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst.BusinessLogicGenerate;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component("advanceComponentGenerateHandler")
public class AdvanceComponentGenerateHandler extends SequenceLogicGenerationHandler {

	private static final String BEAN_FOLDER = "advancecomponentbean";

	@Inject
	AssignGenerateHandler assignGenerateHandler;

	private static final String NL = "\n\t\t";
	private static final String NL_TAB_1 = "\n\t";
	private static final String SPACE = " ";
	public static final String DOT = "\\.";
	public static final String GET = "get";
	private static final String FORMAT_SET = "{0}.set{1}({2});";
	private static final String FORMAT_GET = "{0}.get{1}()";
	public static final boolean IS_SETTER = false;
	public static final boolean IS_GETTER = true;

	private static final String TEMPLATE_ADVANCE_COMPONENT_INPUT_BEAN = "advance_component_input_bean_java.ftl";
	private static final String TEMPLATE_ADVANCE_COMPONENT_OUTPUT_BEAN = "advance_component_output_bean_java.ftl";
	private StringBuilder pathPackage;

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;
	
	@Inject
	SystemService systemService;

	private AdvanceComponent currentComponent;

	/**
	 * Initializing common information.
	 *
	 * @param generateSourceCode
	 */
	private void init(Project project) {
		// Setting package folder source
		String[] split = null;
		if (StringUtils.isNotBlank(project.getPackageName())) {
			split = project.getPackageName().split("\\.");
		}
		if (split != null && split.length > 0) {
			pathPackage = new StringBuilder();
			for (String str : split) {
				pathPackage.append(str).append(File.separator);
			}
		}
	}

	/**
	 * @param advanceComponent
	 * @param inputValues
	 * @param outputValues
	 * @param param
	 * @param typeOfMethod
	 * @param pakage 
	 * @return
	 */
	private String buildMethod(AdvanceComponent advanceComponent, List<AdvanceInputValue> inputValues, List<AdvanceOutputValue> outputValues, BLogicHandlerIo param, String[] typeOfMethod, String pakage) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(NL_TAB_1);
		stringBuilder.append("private ");
		stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(advanceComponent.getMethodName()));
		stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
		stringBuilder.append(BusinessLogicGenerate.SUFFIX_OUTPUT);
		stringBuilder.append(StringUtils.SPACE);
		stringBuilder.append(GenerateSourceCodeUtil.normalizedVariantName(advanceComponent.getMethodName()));
		stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
		stringBuilder.append("(");
		stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(advanceComponent.getMethodName()));
		stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
		stringBuilder.append(BusinessLogicGenerate.SUFFIX_INPUT+" methodInput) {");
		stringBuilder.append(NL);

		// Append content
		stringBuilder.append(advanceComponent.getContent());
		stringBuilder.append(NL_TAB_1);

		// Append return
		stringBuilder.append(NL_TAB_1).append("}");
		stringBuilder.append(NL);
		return stringBuilder.toString();
	}

	/**
	 * @param advanceComponent
	 * @param inputValues
	 * @param outputValues
	 * @param param
	 * @param typeOfMethod
	 * @param pakage 
	 * @return
	 */
	private String buildMethodVoid(AdvanceComponent advanceComponent, List<AdvanceInputValue> inputValues, List<AdvanceOutputValue> outputValues, BLogicHandlerIo param, String[] typeOfMethod, String pakage) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(NL_TAB_1);
		stringBuilder.append("private void");
		stringBuilder.append(StringUtils.SPACE);
		stringBuilder.append(GenerateSourceCodeUtil.normalizedVariantName(advanceComponent.getMethodName()));
		stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
		stringBuilder.append("(");
		stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(advanceComponent.getMethodName()));
		stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
		stringBuilder.append(BusinessLogicGenerate.SUFFIX_INPUT+" methodInput) {");
		stringBuilder.append(NL);

		// Append content
		stringBuilder.append(advanceComponent.getContent());
		stringBuilder.append(NL_TAB_1);

		// Append return
		stringBuilder.append(NL_TAB_1).append("}");
		stringBuilder.append(NL);
		return stringBuilder.toString();
	}

	public String createFileOutputFolder(String moduleName, int sourceCodeType, String pathRoot) {
		StringBuilder pathOutput = new StringBuilder();
		switch (sourceCodeType) {
			case GenerateSourceCodeConst.BusinessLogicGenerate.SERVICE:
				pathOutput.append("src").append(File.separator).append("main").append(File.separator).append("java").append(File.separator).append(pathPackage.toString()).append(File.separator).append(moduleName);
				break;
		}

		return  GenerateSourceCodeUtil.createSaveFileDirectory(GenerateSourceCodeUtil.normalizedPackageName(pathOutput.toString()), pathRoot);
	}

	/**
	 * @param outputValues
	 * @param mNameParameter
	 * @return
	 */
	private String[] checkOutputValue(List<AdvanceOutputValue> outputValues, Map<String, String> mNameParameter) {
		String[] strReturn = new String[0];
		String[] arrTemp = new String[0];
		for (AdvanceOutputValue advanceOutputValue : outputValues) {
			String source = mNameParameter.get(advanceOutputValue.getTargetScope() + advanceOutputValue.getTargetId());
			String[] arrSource = source.split(DOT);
			if (arrSource.length >= 3) {
				if (arrTemp.length > 0) {
					if (Arrays.equals(ArrayUtils.removeElement(arrSource, arrSource[arrSource.length - 1]), arrTemp)) {
						strReturn = ArrayUtils.removeElement(arrSource, arrSource[arrSource.length - 1]);
					} else {
						Arrays.fill(strReturn, null);
					}
				} else {
					arrTemp = ArrayUtils.removeElement(arrSource, arrSource[arrSource.length - 1]);
				}
			}
		}

		return strReturn;
	}

	/**
	 * @param source
	 * @return
	 */
	public String processTarget(String target) {
		StringBuilder strTarget = new StringBuilder();
		if (target != null) {
			String[] arrTarget = target.split(DOT);
			for (int i = 0; i < arrTarget.length; i++) {
				if (i == 0) {
					strTarget.append(arrTarget[i]);
				} else {
					strTarget.append(".");
					strTarget.append(GET);
					strTarget.append(String.valueOf(arrTarget[i].charAt(0)).toUpperCase());
					strTarget.append(arrTarget[i].substring(1, arrTarget[i].length()));
					if (i == arrTarget.length - 1) {
						strTarget.append("()");
					}
				}
			}
		}
		return strTarget.toString();
	}

	@Override
	public void handle(StringBuilder stringBuilder, BLogicHandlerIo param) {
		init(param.getProject());

		if (currentComponent != null) {
			preGencode(stringBuilder, param);

			if (FunctionCommon.equals(param.getModule().getModuleType(), FunctionType.BATCH)) {
				generateAdvanceNode(stringBuilder, param, FunctionType.BATCH);
			} else {
				generateAdvanceNode(stringBuilder, param, FunctionType.ONLINE);
			}
			postGencode(stringBuilder, param);
		}
	}
	
	private void generateAdvanceNode(StringBuilder stringBuilder, BLogicHandlerIo param, Integer blogicType) {

		// Adding temporary
		OutputStreamWriter out = null;
		String paramInput = "";
		String paramOutput = "";
		
		// Modify by HungHX
		String beanPath = "";
		String pakage = "";
		
		if (FunctionCommon.equals(blogicType, FunctionType.BATCH)) {
			if (StringUtils.isBlank(param.getModule().getModuleCode())) {
				beanPath = "batch" + File.separator + "service" + File.separator + "common" + File.separator + BEAN_FOLDER;
				pakage = param.getProject().getPackageName() + ".batch.service.common."+BEAN_FOLDER;
			} else {
				beanPath = "batch" + File.separator + "service" + File.separator + WordUtils.uncapitalize(param.getModule().getModuleCode()) + File.separator + BEAN_FOLDER;
				pakage = param.getProject().getPackageName() + ".batch.service."+ StringUtils.uncapitalize(param.getModule().getModuleCode())+"."+BEAN_FOLDER;
			}
		} else {
			if (BusinessDesignConst.BLOGIC_TYPE_STANDARD.equals(param.getBusinessDesign().getBlogicType()) 
					|| BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE.equals(param.getBusinessDesign().getBlogicType())) {
				beanPath = "domain" + File.separator + "service" + File.separator + WordUtils.uncapitalize(param.getModule().getModuleCode()) + File.separator + BEAN_FOLDER;
				pakage = param.getProject().getPackageName() + ".domain.service."+ StringUtils.uncapitalize(param.getModule().getModuleCode())+"."+BEAN_FOLDER;
			} else if (BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(param.getBusinessDesign().getBlogicType()) && Boolean.FALSE.equals(param.getBusinessDesign().getCustomizeFlg())) {
				beanPath = "domain"+ File.separator + "service" + File.separator + "common" + File.separator + BEAN_FOLDER;
				pakage = param.getProject().getPackageName() + ".domain.service.common."+ BEAN_FOLDER;
			}
		}

		if (StringUtils.isNoneEmpty(beanPath)) {
		
			List<AdvanceInputValue> inputValues = currentComponent.getParameterInputBeans();
			List<AdvanceOutputValue> outputValues = currentComponent.getParameterOutputBeans();

			String[] resultValue = this.checkOutputValue(outputValues, param.getmNameParameter());

			// Declare Input
			stringBuilder.append("// Declare Input Param");
			stringBuilder.append(NL);
			stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(currentComponent.getMethodName()));
			stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
			stringBuilder.append(BusinessLogicGenerate.SUFFIX_INPUT);
			stringBuilder.append(SPACE);
			stringBuilder.append(GenerateSourceCodeUtil.normalizedVariantName(currentComponent.getMethodName()));
			stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
			stringBuilder.append(BusinessLogicGenerate.SUFFIX_INPUT);
			stringBuilder.append(" = new ");
			stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(currentComponent.getMethodName()));
			stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
			stringBuilder.append(BusinessLogicGenerate.SUFFIX_INPUT);
			stringBuilder.append("();");
			stringBuilder.append(NL);

			if (CollectionUtils.isNotEmpty(inputValues)) {
				for (AdvanceInputValue advanceInputValue : inputValues) {
					paramInput = "";
					int dataTypeSrc = -1;
					boolean isTwooArrayPrimitive = false;
					if (advanceInputValue.getParameterScope() != null) {
						isTwooArrayPrimitive = false;
						dataTypeSrc = -1;
						
						if (advanceInputValue.getParameterScope().equals(BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_INPUT_BEAN)) {
							//paramInput = detailServiceImpHandler.getterAndSetterOfParameter(advanceInputValue.getParameterId(), advanceInputValue.getParameterScope(), "in", null, true, false);
							InputBean tm = (InputBean) detailServiceImpHandler.getObjByTypeScope(0, advanceInputValue.getParameterScope(), advanceInputValue.getParameterId(), param.getmAllParentAndSeflByLevelOfInOutObj().get(advanceInputValue.getParameterScope().toString() + advanceInputValue.getParameterId()));
							dataTypeSrc = tm.getDataType();
							paramInput = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, advanceInputValue.getParameterId(), advanceInputValue.getParameterScope(), "in", advanceInputValue.getLstParameterIndex());
							// Marking two list primitive
							if(Boolean.TRUE.equals(tm.getArrayFlg()) && Boolean.TRUE.equals(advanceInputValue.getArrayFlg()) && DataTypeUtils.notEquals(tm.getDataType(), advanceInputValue.getDataType())) isTwooArrayPrimitive = true;
						} else if (advanceInputValue.getParameterScope().equals(BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OBJECT_DEFINITION)) {
							//paramInput = detailServiceImpHandler.getterAndSetterOfParameter(advanceInputValue.getParameterId(), advanceInputValue.getParameterScope(), "ob", null, true, false);
							ObjectDefinition tm = (ObjectDefinition) detailServiceImpHandler.getObjByTypeScope(0, advanceInputValue.getParameterScope(), advanceInputValue.getParameterId(), param.getmAllParentAndSeflByLevelOfInOutObj().get(advanceInputValue.getParameterScope().toString() + advanceInputValue.getParameterId()));
							dataTypeSrc = tm.getDataType();
							paramInput = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, advanceInputValue.getParameterId(), advanceInputValue.getParameterScope(), "ob", advanceInputValue.getLstParameterIndex());
							// Marking two list primitive
							if(Boolean.TRUE.equals(tm.getArrayFlg()) && Boolean.TRUE.equals(advanceInputValue.getArrayFlg()) && DataTypeUtils.notEquals(tm.getDataType(), advanceInputValue.getDataType())) isTwooArrayPrimitive = true;
						} else if (advanceInputValue.getParameterScope().equals(BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OUTPUT_BEAN)) {
							//paramInput = detailServiceImpHandler.getterAndSetterOfParameter(advanceInputValue.getParameterId(), advanceInputValue.getParameterScope(), "ou", null, true, false);
							OutputBean tm = (OutputBean) detailServiceImpHandler.getObjByTypeScope(0, advanceInputValue.getParameterScope(), advanceInputValue.getParameterId(), param.getmAllParentAndSeflByLevelOfInOutObj().get(advanceInputValue.getParameterScope().toString() + advanceInputValue.getParameterId()));
							dataTypeSrc = tm.getDataType();
							paramInput = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), true, advanceInputValue.getParameterId(), advanceInputValue.getParameterScope(), "ou", advanceInputValue.getLstParameterIndex());
							// Marking two list primitive
							if(Boolean.TRUE.equals(tm.getArrayFlg()) && Boolean.TRUE.equals(advanceInputValue.getArrayFlg()) && DataTypeUtils.notEquals(tm.getDataType(), advanceInputValue.getDataType())) isTwooArrayPrimitive = true;
						}
					}
					
					if(isTwooArrayPrimitive) {
						stringBuilder.append("\n\t\t");
						stringBuilder.append(String.format("if (%s == null) {", MessageFormat.format(FORMAT_GET, GenerateSourceCodeUtil.normalizedVariantName(currentComponent.getMethodName()) + GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()) + BusinessLogicGenerate.SUFFIX_INPUT, GenerateSourceCodeUtil.normalizedClassName(advanceInputValue.getInputBeanCode()))));
						stringBuilder.append("\n\t\t\t");
						String dataType = GenerateSourceCodeUtil.getPrimitiveTypeName(advanceInputValue.getDataType());
						stringBuilder.append(MessageFormat.format(FORMAT_SET, GenerateSourceCodeUtil.normalizedVariantName(currentComponent.getMethodName()) + GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()) + BusinessLogicGenerate.SUFFIX_INPUT, GenerateSourceCodeUtil.normalizedClassName(advanceInputValue.getInputBeanCode()), "new ArrayList<"+ dataType +">()"));
						stringBuilder.append("\n\t\t}");
						stringBuilder.append("\n\t\t");
						stringBuilder.append(String.format("%s.clear();", MessageFormat.format(FORMAT_GET, GenerateSourceCodeUtil.normalizedVariantName(currentComponent.getMethodName()) + GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()) + BusinessLogicGenerate.SUFFIX_INPUT, GenerateSourceCodeUtil.normalizedClassName(advanceInputValue.getInputBeanCode()))));
						stringBuilder.append("\n\t\t");
						stringBuilder.append("\n\t\t");
						if(GenerateSourceCodeConst.DataType.BYTE == dataTypeSrc){
							stringBuilder.append(String.format("if (%s != null && %s.length > 0) {", paramInput, paramInput));
						} else {
							stringBuilder.append(String.format("if (%s != null && %s.size() > 0) {", paramInput, paramInput));
						}
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc), "iter", paramInput));
						paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(advanceInputValue.getDataType(), dataTypeSrc, "iter");
						stringBuilder.append("\n\t\t\t\t");
						String setterInReferParent = MessageFormat.format("{0}.add({1});", MessageFormat.format(FORMAT_GET, GenerateSourceCodeUtil.normalizedVariantName(currentComponent.getMethodName()) + GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()) + BusinessLogicGenerate.SUFFIX_INPUT, GenerateSourceCodeUtil.normalizedClassName(advanceInputValue.getInputBeanCode())), paramInput);
						stringBuilder.append(setterInReferParent);
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append("}");
						stringBuilder.append("\n\t\t");
						stringBuilder.append("}");
						stringBuilder.append("\n\t\t");
					} else {
						paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(advanceInputValue.getDataType(), dataTypeSrc, paramInput);
						
						stringBuilder.append(MessageFormat.format(FORMAT_SET, GenerateSourceCodeUtil.normalizedVariantName(currentComponent.getMethodName()) + GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()) + BusinessLogicGenerate.SUFFIX_INPUT, GenerateSourceCodeUtil.normalizedClassName(advanceInputValue.getInputBeanCode()), paramInput));
						stringBuilder.append(NL);
					}
				}
			}

			if (CollectionUtils.isNotEmpty(outputValues)) {
				stringBuilder.append(NL);
				stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(currentComponent.getMethodName()));
				stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
				stringBuilder.append(BusinessLogicGenerate.SUFFIX_OUTPUT);
				stringBuilder.append(SPACE);
				stringBuilder.append(GenerateSourceCodeUtil.normalizedVariantName(currentComponent.getMethodName()));
				stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
				stringBuilder.append(BusinessLogicGenerate.SUFFIX_OUTPUT);
				stringBuilder.append(" = this.");
				stringBuilder.append(GenerateSourceCodeUtil.normalizedVariantName(currentComponent.getMethodName()));
				stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
				stringBuilder.append("(");
				stringBuilder.append(GenerateSourceCodeUtil.normalizedVariantName(currentComponent.getMethodName()));
				stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
				stringBuilder.append(BusinessLogicGenerate.SUFFIX_INPUT);
				stringBuilder.append(");");
				stringBuilder.append(NL);
				
				for (AdvanceOutputValue advanceOutputValue : outputValues) {
					paramOutput = "";
					int dataTypeSrc = -1;
					boolean isTwooArrayPrimitive = false;
					String type = "";
					
					if (advanceOutputValue.getTargetScope() != null) {
						if (advanceOutputValue.getTargetScope().equals(BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_INPUT_BEAN)) {
							//paramOutput = detailServiceImpHandler.getterAndSetterOfParameter(advanceOutputValue.getTargetId(), advanceOutputValue.getTargetScope(), "in", null, false, false);
							InputBean tm = (InputBean) detailServiceImpHandler.getObjByTypeScope(0, advanceOutputValue.getTargetScope(), advanceOutputValue.getTargetId(), param.getmAllParentAndSeflByLevelOfInOutObj().get(advanceOutputValue.getTargetScope().toString() + advanceOutputValue.getTargetId()));
							dataTypeSrc = tm.getDataType();
							paramOutput = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, advanceOutputValue.getTargetId(), advanceOutputValue.getTargetScope(), "in", advanceOutputValue.getLstTargetIndex());
							// Marking two list primitive
							if(Boolean.TRUE.equals(tm.getArrayFlg()) && Boolean.TRUE.equals(advanceOutputValue.getArrayFlg()) && DataTypeUtils.notEquals(tm.getDataType(), advanceOutputValue.getDataType())) isTwooArrayPrimitive = true;
							type = "in";
						} else if (advanceOutputValue.getTargetScope().equals(BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OBJECT_DEFINITION)) {
							//paramOutput = detailServiceImpHandler.getterAndSetterOfParameter(advanceOutputValue.getTargetId(), advanceOutputValue.getTargetScope(), "ob", null, false, false);
							ObjectDefinition tm = (ObjectDefinition) detailServiceImpHandler.getObjByTypeScope(0, advanceOutputValue.getTargetScope(), advanceOutputValue.getTargetId(), param.getmAllParentAndSeflByLevelOfInOutObj().get(advanceOutputValue.getTargetScope().toString() + advanceOutputValue.getTargetId()));
							dataTypeSrc = tm.getDataType();
							paramOutput = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, advanceOutputValue.getTargetId(), advanceOutputValue.getTargetScope(), "ob", advanceOutputValue.getLstTargetIndex());
							// Marking two list primitive
							if(Boolean.TRUE.equals(tm.getArrayFlg()) && Boolean.TRUE.equals(advanceOutputValue.getArrayFlg()) && DataTypeUtils.notEquals(tm.getDataType(), advanceOutputValue.getDataType())) isTwooArrayPrimitive = true;
							type = "ob";
						} else if (advanceOutputValue.getTargetScope().equals(BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OUTPUT_BEAN)) {
							//paramOutput = detailServiceImpHandler.getterAndSetterOfParameter(advanceOutputValue.getTargetId(), advanceOutputValue.getTargetScope(), "ou", null, false, false);
							OutputBean tm = (OutputBean) detailServiceImpHandler.getObjByTypeScope(0, advanceOutputValue.getTargetScope(), advanceOutputValue.getTargetId(), param.getmAllParentAndSeflByLevelOfInOutObj().get(advanceOutputValue.getTargetScope().toString() + advanceOutputValue.getTargetId()));
							dataTypeSrc = tm.getDataType();
							paramOutput = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), false, advanceOutputValue.getTargetId(), advanceOutputValue.getTargetScope(), "ou", advanceOutputValue.getLstTargetIndex());
							// Marking two list primitive
							if(Boolean.TRUE.equals(tm.getArrayFlg()) && Boolean.TRUE.equals(advanceOutputValue.getArrayFlg()) && DataTypeUtils.notEquals(tm.getDataType(), advanceOutputValue.getDataType())) isTwooArrayPrimitive = true;
							type = "ou";
						}
					}

					
					if(isTwooArrayPrimitive) {
						stringBuilder.append("\n\t\t");
						String targetNull = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, advanceOutputValue.getTargetId(), advanceOutputValue.getTargetScope(), type, advanceOutputValue.getLstTargetIndex());
						stringBuilder.append(String.format("if (%s == null) {", targetNull));
						String dataType = GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc);
						String targetSet = detailServiceImpHandler.getterAndSetterOfParameter(0, param.getmAllParentAndSeflByLevelOfInOutObj(), IS_SETTER, advanceOutputValue.getTargetId(), advanceOutputValue.getTargetScope(), type, advanceOutputValue.getLstTargetIndex())+"(new ArrayList<"+ dataType +">());";
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append(targetSet);
						stringBuilder.append("\n\t\t}");
						stringBuilder.append("\n\t\t");
						stringBuilder.append(String.format("%s.clear();", targetNull));
						stringBuilder.append("\n\t\t");
						stringBuilder.append("\n\t\t");
						String getter = MessageFormat.format(FORMAT_GET, GenerateSourceCodeUtil.normalizedVariantName(currentComponent.getMethodName()) + GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()) + BusinessLogicGenerate.SUFFIX_OUTPUT, GenerateSourceCodeUtil.normalizedClassName(advanceOutputValue.getOutputBeanCode()));
						
						if(GenerateSourceCodeConst.DataType.BYTE == advanceOutputValue.getDataType()){
							stringBuilder.append(String.format("if (%s != null && %s.size() > 0) {", getter, getter));
						} else {
							stringBuilder.append(String.format("if (%s != null && %s.size() > 0) {", getter, getter));
						}
						
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(advanceOutputValue.getDataType()), "iter", getter));
						paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(dataTypeSrc, advanceOutputValue.getDataType(), "iter");
						stringBuilder.append("\n\t\t\t\t");
						stringBuilder.append(targetNull + ".add("+paramInput+");");
						stringBuilder.append("\n\t\t\t");
						stringBuilder.append("}");
						stringBuilder.append("\n\t\t");
						stringBuilder.append("}");
						stringBuilder.append("\n\t\t");
					} else {
						String getter = MessageFormat.format(FORMAT_GET, GenerateSourceCodeUtil.normalizedVariantName(currentComponent.getMethodName()) + GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()) + BusinessLogicGenerate.SUFFIX_OUTPUT, GenerateSourceCodeUtil.normalizedClassName(advanceOutputValue.getOutputBeanCode()));
						getter = BusinessLogicGenerateHelper.getContentByCastDataType(dataTypeSrc, advanceOutputValue.getDataType(), getter);
						
						if(CollectionUtils.isNotEmpty(advanceOutputValue.getLstTargetIndex())) {
							stringBuilder.append(String.format(paramOutput, getter)+";").append(NL);
						} else {
							stringBuilder.append(MessageFormat.format("{0}({1});", paramOutput, getter)).append(NL);
						}
					}
				}

				StringBuilder advanceMethod = new StringBuilder();
				advanceMethod.append("/**");
				advanceMethod.append(NL);
				advanceMethod.append("* ");
				advanceMethod.append(currentComponent.getRemark() != null? currentComponent.getRemark():"");
				advanceMethod.append(NL);
				advanceMethod.append("* ");
				advanceMethod.append(NL);
				advanceMethod.append("*/");
				advanceMethod.append(NL);
				advanceMethod.append(buildMethod(currentComponent, inputValues, outputValues, param, resultValue, pakage));
				param.setAdvanceComponentMethod(advanceMethod.toString());
			} else {
				stringBuilder.append(NL);
				stringBuilder.append("this.");
				stringBuilder.append(GenerateSourceCodeUtil.normalizedVariantName(currentComponent.getMethodName()));
				stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
				stringBuilder.append("(");
				stringBuilder.append(GenerateSourceCodeUtil.normalizedVariantName(currentComponent.getMethodName()));
				stringBuilder.append(GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()));
				stringBuilder.append(BusinessLogicGenerate.SUFFIX_INPUT);
				stringBuilder.append(");");
				stringBuilder.append(NL);
				
				StringBuilder advanceMethod = new StringBuilder();
				advanceMethod.append("/**");
				advanceMethod.append(NL);
				advanceMethod.append("* ");
				advanceMethod.append(currentComponent.getRemark() != null? currentComponent.getRemark():"");
				advanceMethod.append(NL);
				advanceMethod.append("* ");
				advanceMethod.append(NL);
				advanceMethod.append("*/");
				advanceMethod.append(NL);
				advanceMethod.append(buildMethodVoid(currentComponent, inputValues, outputValues, param, resultValue, pakage));
				param.setAdvanceComponentMethod(advanceMethod.toString());
			}
			String outputDirDomain;
			
			if (FunctionCommon.equals(blogicType, FunctionType.BATCH)) {
				outputDirDomain = createFileOutputFolder(beanPath, BusinessLogicGenerate.SERVICE, param.getModule().getPathSourceBatch());
			} else {
				outputDirDomain = createFileOutputFolder(beanPath, BusinessLogicGenerate.SERVICE, param.getModule().getPathSourceDomain());
			}

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package", pakage);
			data.put("singleInputList", inputValues);
			data.put("singleOutputList", outputValues);
			data.put("beanFolder", BEAN_FOLDER);
			data.put("methodName", currentComponent.getMethodName());
			data.put("module", param.getModule());
			data.put("blogic", param.getBusinessDesign());
			data.put("remark", currentComponent.getRemark());

			Template tempBusinessLogicInputBean = getTemplate(TEMPLATE_ADVANCE_COMPONENT_INPUT_BEAN);
			Template tempBusinessLogicOutputBean = getTemplate(TEMPLATE_ADVANCE_COMPONENT_OUTPUT_BEAN);

			try {
				out = new OutputStreamWriter(new FileOutputStream(new File(outputDirDomain + GenerateSourceCodeUtil.normalizedClassName(currentComponent.getMethodName()) + GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()) + BusinessLogicGenerate.SUFFIX_INPUT + GenerateSourceCodeConst.JAVA_EXTEND)), GenerateSourceCodeConst.ENCODE_UTF8);
				
				tempBusinessLogicInputBean.process(data, out);
				IOUtils.closeQuietly(out);

				if (CollectionUtils.isNotEmpty(outputValues)) {
					out = new OutputStreamWriter(new FileOutputStream(new File(outputDirDomain + GenerateSourceCodeUtil.normalizedClassName(currentComponent.getMethodName()) + GenerateSourceCodeUtil.normalizedClassName(param.getBusinessDesign().getBusinessLogicCode()) + BusinessLogicGenerate.SUFFIX_OUTPUT + GenerateSourceCodeConst.JAVA_EXTEND)), GenerateSourceCodeConst.ENCODE_UTF8);
					tempBusinessLogicOutputBean.process(data, out);
					IOUtils.closeQuietly(out);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(out);
			}
		}
	}

	protected Template getTemplate(String templateFile){
		Template template = null;
		Configuration freemarkerConfiguration = systemService.createDefaultFreemarkerConfiguration();
		try {
			template =  freemarkerConfiguration.getTemplate(templateFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return template;
	}

	public AdvanceComponent getCurrentComponent() {
		return currentComponent;
	}

	public void setCurrentComponent(AdvanceComponent currentComponent) {
		this.currentComponent = currentComponent;
	}

	@Override
	public void preGencode(StringBuilder builder, BLogicHandlerIo param) {
		if (param.getIsGenConfig()) {
			builder.append(KEY_NL);
			builder.append("// Start advance node");
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
			builder.append("// End advance node");
			builder.append(KEY_NL);
		}
	}
}
