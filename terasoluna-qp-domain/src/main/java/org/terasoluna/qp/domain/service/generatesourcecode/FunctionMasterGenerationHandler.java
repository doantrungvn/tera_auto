package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.FunctionMaster;
import org.terasoluna.qp.domain.model.FunctionMethod;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.repository.functionmaster.FunctionMasterRepository;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst.BusinessLogicGenerate;

import freemarker.template.Template;

@Component(value="FunctionMasterGenerationHandler")
public class FunctionMasterGenerationHandler extends GenerationHandler{
	
	private static final int CUSTOMIZE = 1;
	private static final String FUNCTIONMASTER_TEMPLATE = "function_master_java.ftl";
	private static final String TEMPLATE_FUNCTIONMASTER_OBJ_OF_INPUT_BEAN = "function_master_object_input_bean_java.ftl";
	private static final String TEMPLATE_FUNCTIONMASTER_OBJ_OF_OUTPUT_BEAN = "function_master_object_output_bean_java.ftl";
	private static final String TEMPLATE_FUNCTIONMASTER_INPUT_BEAN = "function_master_input_bean_java.ftl";
	private static final String TEMPLATE_FUNCTIONMASTER_OUTPUT_BEAN = "function_master_output_bean_java.ftl";
	private StringBuilder pathPackage;
	
	@Inject
	FunctionMasterRepository functionMasterRepository;
	
	List<FunctionMaster> functionMasters;
	
	List<FunctionMethod> functionMethods;
	
	List<FunctionMethodInput> functionMethodInputs;
	
	List<FunctionMethodOutput> functionMethodOutputs;
	
	String path;

	/**
	 * Initializing common information.
	 * 
	 * @param generateSourceCode
	 */
	private void init(GenerateSourceCode generateSourceCode) {
		// Setting package folder source
		String[] split = null;
		if (StringUtils.isNotBlank(generateSourceCode.getProject().getPackageName())) {
			split = generateSourceCode.getProject().getPackageName().split("\\.");
		}
		if (split != null && split.length > 0) {
			pathPackage = new StringBuilder();
			for (String str : split) {
				pathPackage.append(str).append(File.separator);
			}
		}
	}
	
	public void handle(GenerateSourceCode generateSourceCode, CommonModel common) {
		init(generateSourceCode);
		
		Map<String, Object> data = new HashMap<String, Object>();
		Writer sqlMapWriter = null;
		Long projectId = common.getWorkingProjectId();
		
		functionMasters = functionMasterRepository.findAllFunctionMasterByProjectId(projectId);
		functionMethods = functionMasterRepository.findFuntionMethodNotCommonByProjectId(projectId);
		functionMethodInputs = functionMasterRepository.findFunctionMethodInputNotCommonByProjectId(projectId);
		functionMethodOutputs = functionMasterRepository.findFunctionMethodOutputNotCommonByProjectId(projectId);
		
		if (CollectionUtils.isNotEmpty(functionMasters)) {
			for(FunctionMaster functionMaster : functionMasters){
				if (functionMaster.getFunctionMasterType() == CUSTOMIZE) {
					for(FunctionMethod functionMethod : functionMethods){
						List<FunctionMethodInput> functionMethodInputLst = new ArrayList<FunctionMethodInput>();
						for (FunctionMethodInput functionMethodInput : functionMethodInputs) {
							if(functionMethodInput.getFunctionMethodId().equals(functionMethod.getFunctionMethodId())){
								functionMethodInputLst.add(functionMethodInput);
							}
						}
						functionMethod.setFunctionMethodInput(functionMethodInputLst);
						
						List<FunctionMethodOutput> functionMethodOutputLst = new ArrayList<FunctionMethodOutput>();
						for (FunctionMethodOutput functionMethodOutput : functionMethodOutputs) {
							if(functionMethodOutput.getFunctionMethodId().equals(functionMethod.getFunctionMethodId())){
								functionMethodOutputLst.add(functionMethodOutput);
							}
						}
						functionMethod.setFunctionMethodOutput(functionMethodOutputLst);
	
						String separator = File.separator;
						path = functionMaster.getPackageName().replace(".", separator) + File.separator + WordUtils.capitalize(functionMaster.getFunctionMasterCode());
						
						data.put("packageName", functionMaster.getPackageName());
						data.put("package", generateSourceCode.getProject().getPackageName());
						data.put("functionMaster", functionMaster);
						data.put("functionMethod", functionMethod);
						data.put("singleInputList", getSingleFunctionMethodInput(functionMethod.getFunctionMethodInput()));
						generateObjectInputLst(functionMaster.getPackageName(), functionMethod, generateSourceCode, getObjectListFunctionMethodInput(functionMethod.getFunctionMethodInput()), functionMaster);
						data.put("singleOutputList", getSingleListOutputBean(functionMethod.getFunctionMethodOutput()));
						generateObjectOutputLst(functionMaster.getPackageName(), functionMethod, generateSourceCode, getObjectListFunctionMethodOutput(functionMethod.getFunctionMethodOutput()), functionMaster);

						try {	
							// For source generate model bean at domain layer
							data.put("isdomain", true);
							sqlMapWriter = this.getFunctionMasterWriter(generateSourceCode.getSourcePathDomain(),
									generateSourceCode.getProject().getProjectCode(),
									functionMaster.getPackageName() + File.separator + StringUtils.uncapitalize(functionMaster.getFunctionMasterCode())
									+ File.separator + StringUtils.uncapitalize(functionMethod.getFunctionMethodCode()),
									StringUtils.capitalize(functionMethod.getFunctionMethodCode()) + BusinessLogicGenerate.SUFFIX_INPUT_BEAN);
						
							Template tempcFunctionMethodInput = this.getTemplate(TEMPLATE_FUNCTIONMASTER_INPUT_BEAN);
							tempcFunctionMethodInput.process(data, sqlMapWriter);

							sqlMapWriter = this.getFunctionMasterWriter(generateSourceCode.getSourcePathDomain(),
									generateSourceCode.getProject().getProjectCode(),
									functionMaster.getPackageName() + File.separator + functionMaster.getFunctionMasterCode()
									+ File.separator + functionMethod.getFunctionMethodCode(),
									StringUtils.capitalize(functionMethod.getFunctionMethodCode()) + BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN);

							Template tempcFunctionMethodOutput = this.getTemplate(TEMPLATE_FUNCTIONMASTER_OUTPUT_BEAN);
							tempcFunctionMethodOutput.process(data, sqlMapWriter);
							
							// For source generate model bean at batch layer
							data.put("isdomain", false);
							sqlMapWriter = this.getFunctionMasterWriter(generateSourceCode.getSourcePathBatch(),
									generateSourceCode.getProject().getProjectCode(),
									functionMaster.getPackageName() + File.separator + StringUtils.uncapitalize(functionMaster.getFunctionMasterCode())
									+ File.separator + StringUtils.uncapitalize(functionMethod.getFunctionMethodCode()),
									StringUtils.capitalize(functionMethod.getFunctionMethodCode()) + BusinessLogicGenerate.SUFFIX_INPUT_BEAN);
						
							tempcFunctionMethodInput.process(data, sqlMapWriter);

							sqlMapWriter = this.getFunctionMasterWriter(generateSourceCode.getSourcePathBatch(),
									generateSourceCode.getProject().getProjectCode(),
									functionMaster.getPackageName() + File.separator + functionMaster.getFunctionMasterCode()
									+ File.separator + functionMethod.getFunctionMethodCode(),
									StringUtils.capitalize(functionMethod.getFunctionMethodCode()) + BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN);

							tempcFunctionMethodOutput.process(data, sqlMapWriter);
						} catch (Exception e) {
							GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
						} finally{
							IOUtils.closeQuietly(sqlMapWriter);
						}
					}
					
					try {	
						data.put("content", new String(functionMaster.getContent(),"UTF-8"));
						
						// Gen domain layer
						sqlMapWriter = this.getFunctionMasterWriter(generateSourceCode.getSourcePathDomain(),
								generateSourceCode.getProject().getProjectCode(),
								functionMaster.getPackageName(),
								StringUtils.capitalize(functionMaster.getFunctionMasterCode()));
						Template functionMasterTemplate = this.getTemplate(FUNCTIONMASTER_TEMPLATE);
						functionMasterTemplate.process(data, sqlMapWriter);

						// Gen batch layer
						sqlMapWriter = this.getFunctionMasterWriter(generateSourceCode.getSourcePathBatch(),
								generateSourceCode.getProject().getProjectCode(),
								functionMaster.getPackageName(),
								StringUtils.capitalize(functionMaster.getFunctionMasterCode()));
						functionMasterTemplate.process(data, sqlMapWriter);
					} catch (Exception e) {
						GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
					} finally{
						IOUtils.closeQuietly(sqlMapWriter);
					}
				}
			}
		}
	}

	private void generateObjectInputLst(String packageName, FunctionMethod functionMethod, GenerateSourceCode generateSourceCode, List<FunctionMethodInput> objFunctionMethodInputLst, FunctionMaster functionMaster) {
		for (FunctionMethodInput element : objFunctionMethodInputLst) {
			if (CollectionUtils.isNotEmpty(element.getObjectList())) {
				generateObjectInputLst(packageName, functionMethod, generateSourceCode, element.getObjectList(), functionMaster);
			}
			if (CollectionUtils.isNotEmpty(element.getSingleList())) {
				generateObjectInput(packageName, functionMethod, generateSourceCode, element, functionMaster);
			}
		}
	}

	private void generateObjectOutputLst(String packageName, FunctionMethod functionMethod, GenerateSourceCode generateSourceCode, List<FunctionMethodOutput> objFunctionMethodOutputLst, FunctionMaster functionMaster) {
		for (FunctionMethodOutput element : objFunctionMethodOutputLst) {
			if (CollectionUtils.isNotEmpty(element.getObjectList())) {
				generateObjectOutputLst(packageName, functionMethod, generateSourceCode, element.getObjectList(), functionMaster);
			}
			if (CollectionUtils.isNotEmpty(element.getSingleList())) {
				generateObjectOutput(packageName, functionMethod, generateSourceCode, element, functionMaster);
			}
		}
	}
	
	private void generateObjectInput(String packageName, FunctionMethod functionMethod, GenerateSourceCode generateSourceCode, FunctionMethodInput objFunctionMethodInput, FunctionMaster functionMaster) {
		Writer sqlMapWriter = null;
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("packageName", packageName);
		data.put("functionMaster", functionMaster);
		data.put("package", generateSourceCode.getProject().getPackageName());
		data.put("functionMethod", functionMethod);
		data.put("functionMethodInput", objFunctionMethodInput);
		
		try {	
			// Gen domain layer
			data.put("isdomain", true);
			sqlMapWriter = this.getFunctionMasterWriter(generateSourceCode.getSourcePathDomain(),
														generateSourceCode.getProject().getProjectCode(),
														packageName + File.separator + StringUtils.uncapitalize(functionMaster.getFunctionMasterCode()) + File.separator + StringUtils.uncapitalize(functionMethod.getFunctionMethodCode()) + File.separator + functionMethod.getFunctionMethodCode() + BusinessLogicGenerate.SUFFIX_INPUT_BEAN,
														StringUtils.capitalize(objFunctionMethodInput.getMethodInputCode()));
			
			Template tempObjectFunctionMethodInput =  this.getTemplate(TEMPLATE_FUNCTIONMASTER_OBJ_OF_INPUT_BEAN);
			tempObjectFunctionMethodInput.process(data, sqlMapWriter);
			
			// Gen batch layer
			data.put("isdomain", false);
			sqlMapWriter = this.getFunctionMasterWriter(generateSourceCode.getSourcePathBatch(),
														generateSourceCode.getProject().getProjectCode(),
														packageName + File.separator + StringUtils.uncapitalize(functionMaster.getFunctionMasterCode()) + File.separator + StringUtils.uncapitalize(functionMethod.getFunctionMethodCode()) + File.separator + functionMethod.getFunctionMethodCode() + BusinessLogicGenerate.SUFFIX_INPUT_BEAN,
														StringUtils.capitalize(objFunctionMethodInput.getMethodInputCode()));
			
			tempObjectFunctionMethodInput.process(data, sqlMapWriter);
		} catch (Exception e) {
			GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		} finally{
			IOUtils.closeQuietly(sqlMapWriter);
		}
	}

	private void generateObjectOutput(String packageName, FunctionMethod functionMethod, GenerateSourceCode generateSourceCode, FunctionMethodOutput objFunctionMethodOutput, FunctionMaster functionMaster) {
		Writer sqlMapWriter = null;

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("packageName", packageName);
		data.put("functionMaster", functionMaster);
		data.put("package", generateSourceCode.getProject().getPackageName());
		data.put("functionMethod", functionMethod);
		data.put("functionMethodOutput", objFunctionMethodOutput);
		
		try {	
			// Gen domain layer
			data.put("isdomain", true);
			sqlMapWriter = this.getFunctionMasterWriter(generateSourceCode.getSourcePathDomain(),
														generateSourceCode.getProject().getProjectCode(),
														packageName + File.separator + StringUtils.uncapitalize(functionMaster.getFunctionMasterCode()) + File.separator + StringUtils.uncapitalize(functionMethod.getFunctionMethodCode()) + File.separator + functionMethod.getFunctionMethodCode() + BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN,
														StringUtils.capitalize(objFunctionMethodOutput.getMethodOutputCode()));

			Template tempObjectOutputBean =  this.getTemplate(TEMPLATE_FUNCTIONMASTER_OBJ_OF_OUTPUT_BEAN);
			tempObjectOutputBean.process(data, sqlMapWriter);
			
			// Gen batch layer
			data.put("isdomain", false);
			sqlMapWriter = this.getFunctionMasterWriter(generateSourceCode.getSourcePathBatch(),
														generateSourceCode.getProject().getProjectCode(),
														packageName + File.separator + StringUtils.uncapitalize(functionMaster.getFunctionMasterCode()) + File.separator + StringUtils.uncapitalize(functionMethod.getFunctionMethodCode()) + File.separator + functionMethod.getFunctionMethodCode() + BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN,
														StringUtils.capitalize(objFunctionMethodOutput.getMethodOutputCode()));

			tempObjectOutputBean.process(data, sqlMapWriter);
		} catch (Exception e) {
			GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		} finally {
			IOUtils.closeQuietly(sqlMapWriter);
		}
	}

	public String createFileOutputFolder(String moduleName, int sourceCodeType, String pathRoot) {
		StringBuilder pathOutput = new StringBuilder();
		switch (sourceCodeType) {
		case GenerateSourceCodeConst.BusinessLogicGenerate.SERVICE:
			pathOutput.append("src").append(File.separator).append("main").append(File.separator)
					  .append("java").append(File.separator).append(pathPackage.toString());
			break;
		}

		return GenerateSourceCodeUtil.createSaveFileDirectory(GenerateSourceCodeUtil.normalizedPackageName(pathOutput.toString()), pathRoot);
	}
	
	private Writer getFunctionMasterWriter(String baseSourcePath,String projectCode,String packageName,String functionName) throws Exception{
		StringBuilder pathBuilder = new StringBuilder(baseSourcePath);
		
		return this.getWriter(pathBuilder.append("src")
				.append(File.separator)
				.append("main")
				.append(File.separator)
				.append("java")
				.append(File.separator)
				.append(GenerateSourceCodeUtil.normalizedPackageName(packageName).replace(".", File.separator))
				.append(File.separator)
				.append(functionName)
				.append(".java").toString());
	}
	
	public static List<FunctionMethodInput> getSingleFunctionMethodInput(List<FunctionMethodInput> functionMethodInputs) {
		List<FunctionMethodInput> listInputSingle = new ArrayList<FunctionMethodInput>();
		for (FunctionMethodInput inputBean : functionMethodInputs) {
			if (inputBean.getParentFunctionMethodInputId() == null) listInputSingle.add(inputBean);
		}
		return listInputSingle;
	}
	
	public static List<FunctionMethodInput> getObjectListFunctionMethodInput(List<FunctionMethodInput> functionMethodInputs) {
		List<FunctionMethodInput> listInputObject = new ArrayList<FunctionMethodInput>();
		for (FunctionMethodInput inputBean : functionMethodInputs) {
			if (inputBean.getParentFunctionMethodInputId() == null && inputBean.getDataType().equals(0)) {
				settingFieldObjectFunctionMethodInput(inputBean, functionMethodInputs);
				listInputObject.add(inputBean);
			}
		}
		return listInputObject;
	}
	
	public static void settingFieldObjectFunctionMethodInput(FunctionMethodInput item, List<FunctionMethodInput> inputBeanList) {
		List<FunctionMethodInput> listInputSingle = new ArrayList<FunctionMethodInput>();
		List<FunctionMethodInput> listInputObject = new ArrayList<FunctionMethodInput>();
		for (FunctionMethodInput inputBean : inputBeanList) {
			if (item.getMethodInputId().equals(inputBean.getParentFunctionMethodInputId())){
				listInputSingle.add(inputBean);
				if (inputBean.getDataType().equals(0)) {
					settingFieldObjectFunctionMethodInput(inputBean, inputBeanList);
					listInputObject.add(inputBean);
				}
			}
		}
		item.setSingleList(listInputSingle);
		item.setObjectList(listInputObject);
	}
	
	public static List<FunctionMethodOutput> getSingleListOutputBean(List<FunctionMethodOutput> functionMethodOutputs) {
		List<FunctionMethodOutput> listOutputSingle = new ArrayList<FunctionMethodOutput>();
		for (FunctionMethodOutput OutputBean : functionMethodOutputs) {
			if (OutputBean.getParentFunctionMethodOutputId() == null) listOutputSingle.add(OutputBean);
		}
		return listOutputSingle;
	}
	
	public static List<FunctionMethodOutput> getObjectListFunctionMethodOutput(List<FunctionMethodOutput> functionMethodOutputs) {
		List<FunctionMethodOutput> listOutputObject = new ArrayList<FunctionMethodOutput>();
		for (FunctionMethodOutput inputBean : functionMethodOutputs) {
			if (inputBean.getParentFunctionMethodOutputId() == null && inputBean.getDataType().equals(0)) {
				settingFieldObjectFunctionMethodOutput(inputBean, functionMethodOutputs);
				listOutputObject.add(inputBean);
			}
		}
		return listOutputObject;
	}
	
	public static void settingFieldObjectFunctionMethodOutput(FunctionMethodOutput item, List<FunctionMethodOutput> inputBeanList) {
		List<FunctionMethodOutput> listOutputSingle = new ArrayList<FunctionMethodOutput>();
		List<FunctionMethodOutput> listOutputObject = new ArrayList<FunctionMethodOutput>();
		for (FunctionMethodOutput inputBean : inputBeanList) {
			if (item.getMethodOutputId().equals(inputBean.getParentFunctionMethodOutputId())){
				listOutputSingle.add(inputBean);
				if (inputBean.getDataType().equals(0)) {
					settingFieldObjectFunctionMethodOutput(inputBean, inputBeanList);
					listOutputObject.add(inputBean);
				}
			}
		}
		item.setSingleList(listOutputSingle);
		item.setObjectList(listOutputObject);
	}
}
