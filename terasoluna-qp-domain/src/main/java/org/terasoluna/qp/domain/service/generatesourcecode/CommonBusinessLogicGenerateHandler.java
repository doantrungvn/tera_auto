package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.UploadFile;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.AdvanceComponentRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableRepository;
import org.terasoluna.qp.domain.repository.generatesourcecode.GenerateSourceCodeRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.repository.uploadfile.UploadFileRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst.BusinessLogicGenerate;

@Component(value="CommonBusinessLogicGenerateHandler")
public class CommonBusinessLogicGenerateHandler extends GenerationHandler {

	@Inject
	GenerateSourceCodeRepository generateSourceCodeRepository;

	@Inject
	UploadFileRepository uploadFileRepository;
	
	@Inject
	DetailServiceImpHandler detailServiceImpHandler;
	
	@Inject
	BusinessLogicGenerateHandler businessLogicGenerateHandler;

	@Inject
	AdvanceComponentRepository advanceComponentRepository;
	
	@Inject
	SqlDesignRepository sqlDesignRepository;
	
	@Inject
	DecisionTableRepository decisionTableRepository;
	
	private static final String TEMPLATE_BUSSINESS_COMMON_SERVICE = "business_logic_common_service_java.ftl";
	private static final String TEMPLATE_BUSSINESS_COMMON_SERVICE_IMPL = "business_logic_common_service_impl_java.ftl";
	private static final String TEMPLATE_BUSSINESS_COMMON_CUSTOMIZE_SERVICE = "business_logic_common_customize_service_java.ftl";
	/** Template for domain */
	private static final String TEMPLATE_BUSSINESS_INPUT_BEAN = "business_logic_input_bean_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OBJ_OF_INPUT_BEAN = "business_logic_object_input_bean_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OUTPUT_BEAN = "business_logic_output_bean_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OBJ_OF_OUTPUT_BEAN = "business_logic_object_output_bean_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OBJ_DEFINITION = "business_logic_object_definition_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OBJ_OF_OBJ_DEFINITION = "business_logic_object_object_definition_java.ftl";
	/** Template for batch */
	private static final String TEMPLATE_BATCH_BUSSINESS_INPUT_BEAN = "batch_business_common_logic_input_bean_java.ftl";
	private static final String TEMPLATE_BATCH_BUSSINESS_OBJ_OF_INPUT_BEAN = "batch_business_common_logic_object_input_bean_java.ftl";
	private static final String TEMPLATE_BATCH_BUSSINESS_OUTPUT_BEAN = "batch_business_common_logic_output_bean_java.ftl";
	private static final String TEMPLATE_BATCH_BUSSINESS_OBJ_OF_OUTPUT_BEAN = "batch_business_common_logic_object_output_bean_java.ftl";
	private static final String TEMPLATE_BATCH_BUSSINESS_OBJ_DEFINITION = "batch_business_common_logic_object_definition_java.ftl";
	private static final String TEMPLATE_BATCH_BUSSINESS_OBJ_OF_OBJ_DEFINITION = "batch_business_common_logic_object_object_definition_java.ftl";
	
	private StringBuilder pathPackage;
	private Project project;
	private Long currentLanguageId;

	/**
	 * Initializing common information.
	 * 
	 * @param generateSourceCode
	 * @param comon 
	 */
	private void init(GenerateSourceCode generateSourceCode, CommonModel comon) {
		project = generateSourceCode.getProject();
		currentLanguageId = comon.getWorkingLanguageId();
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

	@Override
	public void handle(GenerateSourceCode generateSourceCode, CommonModel comon) {
		init(generateSourceCode, comon);

		// Preparing data for generate controller
		List<BusinessDesign> lstBLogicCommon = generateSourceCodeRepository
				.findAllCommonBusinessLogicByProject(project.getProjectId());
		//if (CollectionUtils.isEmpty(lstBLogicCommon)) return;
		List<BusinessDesign> lstBLogicCommonCustomized = new ArrayList<BusinessDesign>();
        List<BusinessDesign> lstBLogicCommonNonCustomize = new ArrayList<BusinessDesign>();
        
        for (BusinessDesign businessDesign : lstBLogicCommon) {
            if (businessDesign.getCustomizeFlg() != null && businessDesign.getCustomizeFlg()) {
                lstBLogicCommonCustomized.add(businessDesign);
            } else {
                lstBLogicCommonNonCustomize.add(businessDesign);
            }
        }
		// Generate source bean
		generateSourceBean(generateSourceCode, lstBLogicCommonCustomized, true);
        generateSourceBean(generateSourceCode, lstBLogicCommonNonCustomize, false);
		// Generate source service
        generateBlogicSettingCustomize(generateSourceCode, lstBLogicCommonCustomized);
		generateBlogicSetting(generateSourceCode, lstBLogicCommonNonCustomize);
	}

    private void generateSourceBean(GenerateSourceCode generateSourceCode, List<BusinessDesign> lstBLogicCommon, boolean customizeFlg) {
		OutputStreamWriter out = null;
		try {
			String outputDirBatch = "";
			if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
				outputDirBatch = createFileOutputFolder("common", BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathBatch());
			}

			String outputDirDomain = createFileOutputFolder("common", BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());
			if (customizeFlg) {
			    outputDirDomain = createFileOutputFolder("commoncustomize", BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());
			    if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
					outputDirBatch = createFileOutputFolder("commoncustomize", BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathBatch());
				}
			}
			
			List<InputBean> inputBeanLst = generateSourceCodeRepository.findAllInputBeanByBusinessIdLst(lstBLogicCommon);
			List<OutputBean> outputBeanLst = generateSourceCodeRepository.findAllOutputBeanByBusinessIdLst(lstBLogicCommon);
			List<ObjectDefinition> objBeanLst = generateSourceCodeRepository.findAllObjDefinedByBusinessIdLst(lstBLogicCommon);

			for (BusinessDesign businessDesign : lstBLogicCommon) {
				List<InputBean> inputLst = new ArrayList<InputBean>();
				for (InputBean inputBean : inputBeanLst) {
					if(businessDesign.getBusinessLogicId().equals(inputBean.getBusinessLogicId())){
						inputLst.add(inputBean);
					}
				}
				businessDesign.setLstInputBean(inputLst);
				
				List<OutputBean> outputLst = new ArrayList<OutputBean>();
				for (OutputBean outputBean : outputBeanLst) {
					if(businessDesign.getBusinessLogicId().equals(outputBean.getBusinessLogicId())){
						outputLst.add(outputBean);
					}
				}
				businessDesign.setLstOutputBean(outputLst);

				List<ObjectDefinition> objLst = new ArrayList<ObjectDefinition>();
				for (ObjectDefinition objBean : objBeanLst) {
					if(businessDesign.getBusinessLogicId().equals(objBean.getBusinessLogicId())){
						objLst.add(objBean);
					}
				}
				businessDesign.setLstObjectDefinition(objLst);
				Module module = new Module();
				module.setModuleCode("common");
				
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
				data.put("businessLogic", businessDesign);
				data.put("singleInputList", BusinessLogicGenerateHandler.getSingleListInputBean(businessDesign.getLstInputBean()));
				this.generateObjectInputLst(module, businessDesign, generateSourceCode, BusinessLogicGenerateHandler.getObjectListInputBean(businessDesign.getLstInputBean()), customizeFlg);
				data.put("singleOutputList", BusinessLogicGenerateHandler.getSingleListOutputBean(businessDesign.getLstOutputBean()));
				this.generateObjectOutputLst(module, businessDesign, generateSourceCode, BusinessLogicGenerateHandler.getObjectListOutputBean(businessDesign.getLstOutputBean()), customizeFlg);
				data.put("singleObjList", BusinessLogicGenerateHandler.getSingleListObjectDefinition(businessDesign.getLstObjectDefinition()));
				this.generateObjectObjectDefinitionLst(module, businessDesign, generateSourceCode, BusinessLogicGenerateHandler.getObjectListObjectDefinition(businessDesign.getLstObjectDefinition()));
				data.put("module", module);

				this.process(data, TEMPLATE_BUSSINESS_INPUT_BEAN, outputDirDomain + GenerateSourceCodeUtil.normalizedClassName(businessDesign.getBusinessLogicCode())
						+ BusinessLogicGenerate.SUFFIX_INPUT_BEAN + GenerateSourceCodeConst.JAVA_EXTEND);
				
				this.process(data, TEMPLATE_BUSSINESS_OUTPUT_BEAN, outputDirDomain + GenerateSourceCodeUtil.normalizedClassName(businessDesign.getBusinessLogicCode())
						+ BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN + GenerateSourceCodeConst.JAVA_EXTEND);
				
				
				if (!customizeFlg) {
    				this.process(data, TEMPLATE_BUSSINESS_OBJ_DEFINITION, outputDirDomain + GenerateSourceCodeUtil.normalizedClassName(businessDesign.getBusinessLogicCode())
							+ BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION + GenerateSourceCodeConst.JAVA_EXTEND);
				}
				
				if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
					this.process(data, TEMPLATE_BATCH_BUSSINESS_INPUT_BEAN, outputDirBatch + GenerateSourceCodeUtil.normalizedClassName(businessDesign.getBusinessLogicCode())
							+ BusinessLogicGenerate.SUFFIX_INPUT_BEAN + GenerateSourceCodeConst.JAVA_EXTEND);
					
					this.process(data, TEMPLATE_BATCH_BUSSINESS_OUTPUT_BEAN, outputDirBatch + GenerateSourceCodeUtil.normalizedClassName(businessDesign.getBusinessLogicCode())
							+ BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN + GenerateSourceCodeConst.JAVA_EXTEND);
					
					if (!customizeFlg) {
	    				this.process(data, TEMPLATE_BATCH_BUSSINESS_OBJ_DEFINITION, outputDirBatch + GenerateSourceCodeUtil.normalizedClassName(businessDesign.getBusinessLogicCode())
								+ BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION + GenerateSourceCodeConst.JAVA_EXTEND);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

    private void generateBlogicSettingCustomize(GenerateSourceCode generateSourceCode, List<BusinessDesign> lstBLogicCommonCustomized) {
        FileOutputStream fileOutputStream = null;
        String outputDirCustomize = createFileOutputFolder("commoncustomize", BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());

        try {
            for (BusinessDesign businessDesign : lstBLogicCommonCustomized) {
                UploadFile uploadFile = uploadFileRepository.findOne(businessDesign.getUploadFileId());
                
                if (uploadFile != null) {
	                fileOutputStream = new FileOutputStream(outputDirCustomize + uploadFile.getFileName());
	                fileOutputStream.write(uploadFile.getContent());
	                IOUtils.closeQuietly(fileOutputStream);

	                // Generate interface
	                Map<String, Object> data = new HashMap<String, Object>();
	    			data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
	    			data.put("businessLogic", businessDesign);
	    			data.put("project", project);
	    			data.put("isDomain", true);
	                this.process(data, TEMPLATE_BUSSINESS_COMMON_CUSTOMIZE_SERVICE, 
	                		outputDirCustomize + GenerateSourceCodeUtil.normalizedClassName(businessDesign.getBusinessLogicCode()) 
	                		+ BusinessLogicGenerate.SUFFIX_SERVICE + GenerateSourceCodeConst.JAVA_EXTEND);
	                
	                if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())){
	                	data.put("isDomain", false);
	                	String outputDirCustomizeBatch = createFileOutputFolder("commoncustomize", BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathBatch());
	                	
	                	fileOutputStream = new FileOutputStream(outputDirCustomizeBatch + uploadFile.getFileName());
		                fileOutputStream.write(uploadFile.getContent());
		                IOUtils.closeQuietly(fileOutputStream);
	                	
	                	this.process(data, TEMPLATE_BUSSINESS_COMMON_CUSTOMIZE_SERVICE, 
	                			outputDirCustomizeBatch + GenerateSourceCodeUtil.normalizedClassName(businessDesign.getBusinessLogicCode()) 
		                		+ BusinessLogicGenerate.SUFFIX_SERVICE + GenerateSourceCodeConst.JAVA_EXTEND);
	                }
	                
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private void generateBlogicSetting(GenerateSourceCode generateSourceCode, List<BusinessDesign> lstBLogicCommon) {
		OutputStreamWriter out = null;
		try {
			
			boolean isExistAdvancedCommon = CollectionUtils.isNotEmpty(advanceComponentRepository.findAdvanceComponentByModuleCommon(project.getProjectId()));
			boolean isExistSqlCommon = CollectionUtils.isNotEmpty(sqlDesignRepository.getAllSqlDesignByProjectId(project.getProjectId(), null));
			List<BusinessDesign> lstBlogicCustomize = generateSourceCodeRepository.findAllCommonBusinessLogicCustomizeByProject(project.getProjectId());
			boolean isExistDecision = CollectionUtils.isNotEmpty(decisionTableRepository.findAllDecisionByProjectId(project.getProjectId()));
			
			String outputDirNonCustomize = createFileOutputFolder("common", BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
			data.put("businessLogic", lstBLogicCommon);
			data.put("isExistAdvancedCommon", isExistAdvancedCommon);
			data.put("lstBlogicCustomize", lstBlogicCustomize);
			data.put("isExistSqlCommon", isExistSqlCommon);
			data.put("isExistDecision", isExistDecision);
			data.put("generateSourceCode", generateSourceCode);
			data.put("project", project);
			data.put("isDomain", true);
			// Setting content of Blogic
			settingGenerateBlogicDesignContent(lstBLogicCommon , generateSourceCode, BusinessDesignConst.MODULE_TYPE_ONLINE);
			// Variable template
			
			this.process(data, TEMPLATE_BUSSINESS_COMMON_SERVICE, outputDirNonCustomize + "BusinessLogicCommonService" + GenerateSourceCodeConst.JAVA_EXTEND);
			this.process(data, TEMPLATE_BUSSINESS_COMMON_SERVICE_IMPL, outputDirNonCustomize + "BusinessLogicCommonServiceImpl" + GenerateSourceCodeConst.JAVA_EXTEND);
			
			if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
				// Setting content of Blogic
				settingGenerateBlogicDesignContent(lstBLogicCommon , generateSourceCode, BusinessDesignConst.MODULE_TYPE_BATCH);
				// Variable template
				data.put("businessLogic", lstBLogicCommon);
				data.put("isDomain", false);

				String outputDirNonCustomizeBatch = createFileOutputFolder("common", BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathBatch());
				this.process(data, TEMPLATE_BUSSINESS_COMMON_SERVICE, outputDirNonCustomizeBatch + "BusinessLogicCommonService" + GenerateSourceCodeConst.JAVA_EXTEND);
				this.process(data, TEMPLATE_BUSSINESS_COMMON_SERVICE_IMPL, outputDirNonCustomizeBatch + "BusinessLogicCommonServiceImpl" + GenerateSourceCodeConst.JAVA_EXTEND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	public String createFileOutputFolder(String moduleName, int sourceCodeType, String pathRoot) {
		StringBuilder pathOutput = new StringBuilder();
		switch (sourceCodeType) {
		case GenerateSourceCodeConst.BusinessLogicGenerate.SERVICE:
			pathOutput.append("src").append(File.separator).append("main").append(File.separator)
					  .append("java").append(File.separator).append(pathPackage.toString())
					  .append(File.separator).append("domain")
					  .append(File.separator).append("service").append(File.separator).append(WordUtils.uncapitalize(moduleName));
			break;
			
		case GenerateSourceCodeConst.BusinessLogicGenerate.BATCH:
			pathOutput.append("src").append(File.separator).append("main").append(File.separator)
					  .append("java").append(File.separator).append(pathPackage.toString())
					  .append(File.separator).append("batch")
					  .append(File.separator).append("service").append(File.separator).append(WordUtils.uncapitalize(moduleName));
			break;
		}

		return GenerateSourceCodeUtil.createSaveFileDirectory(GenerateSourceCodeUtil.normalizedPackageName(pathOutput.toString()), pathRoot);
	}
	
	private void settingGenerateBlogicDesignContent(List<BusinessDesign> lstBLogicCommon, GenerateSourceCode generateSourceCode, int moduleType) {
		Module module = new Module();
		module.setModuleCode("common");
		module.setPathSourceDomain(generateSourceCode.getSourcePathDomain());
		module.setPathSourceBatch(generateSourceCode.getSourcePathBatch());
		module.setModuleType(moduleType);
		detailServiceImpHandler.generateServiceDetailTypeCommonBlogic(project, module, lstBLogicCommon, currentLanguageId);
	}

	private void generateObjectInputLst(Module module, BusinessDesign businessDesign, GenerateSourceCode generateSourceCode, List<InputBean> objInputBeanLst, boolean customizeFlg) {
		if(CollectionUtils.isNotEmpty(objInputBeanLst)) {
			for (InputBean element : objInputBeanLst) {
				if (CollectionUtils.isNotEmpty(element.getObjectList())) {
					generateObjectInputLst(module, businessDesign, generateSourceCode, element.getObjectList(), customizeFlg);
				}
				if (CollectionUtils.isNotEmpty(element.getSingleList())) {
					generateObjectInput(module, businessDesign, generateSourceCode, element, customizeFlg);
				}
			}
		}
	}

	private void generateObjectOutputLst(Module module, BusinessDesign businessDesign, GenerateSourceCode generateSourceCode, List<OutputBean> objOutputBeanLst, boolean customizeFlg) {
		if(CollectionUtils.isNotEmpty(objOutputBeanLst)) {
			for (OutputBean element : objOutputBeanLst) {
				if (CollectionUtils.isNotEmpty(element.getObjectList())) {
					generateObjectOutputLst(module, businessDesign, generateSourceCode, element.getObjectList(), customizeFlg);
				}
				if (CollectionUtils.isNotEmpty(element.getSingleList())) {
					generateObjectOutput(module, businessDesign, generateSourceCode, element, customizeFlg);
				}
			}
		}
	}

	private void generateObjectObjectDefinitionLst(Module module, BusinessDesign businessDesign, GenerateSourceCode generateSourceCode, List<ObjectDefinition> objObjDefinitionLst) {
		if(CollectionUtils.isNotEmpty(objObjDefinitionLst)) {
			for (ObjectDefinition element : objObjDefinitionLst) {
				if (CollectionUtils.isNotEmpty(element.getObjectList())) {
					generateObjectObjectDefinitionLst(module, businessDesign, generateSourceCode, element.getObjectList());
				}
				if (CollectionUtils.isNotEmpty(element.getSingleList())) {
					generateObjectObjectDefinition(module, businessDesign, generateSourceCode, element);
				}
			}
		}
	}

	private void generateObjectInput(Module module, BusinessDesign businessDesign, GenerateSourceCode generateSourceCode, InputBean objInputBean, boolean customizeFlg) {

		OutputStreamWriter out = null;

		try {
			String outputDirDomain = null;
			String outputDirBatch = null;
			
			if(customizeFlg) {
				outputDirDomain = createFileOutputFolder("commoncustomize"
						+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_INPUT_BEAN,
						BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());
				if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())){
					outputDirBatch = createFileOutputFolder("commoncustomize"
							+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_INPUT_BEAN,
							BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathBatch());
				}
			} else {
				outputDirDomain = createFileOutputFolder("common"
						+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_INPUT_BEAN,
						BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());
				
				if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())){
					outputDirBatch = createFileOutputFolder("common"
							+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_INPUT_BEAN,
							BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathBatch());
				}
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
			data.put("businessLogic", businessDesign);
			data.put("inputBean", objInputBean);
			data.put("module", module);
			
			this.process(data, TEMPLATE_BUSSINESS_OBJ_OF_INPUT_BEAN, outputDirDomain + GenerateSourceCodeUtil.normalizedClassName(objInputBean.getInputBeanCode())
							+ GenerateSourceCodeConst.JAVA_EXTEND);

			if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
				this.process(data, TEMPLATE_BATCH_BUSSINESS_OBJ_OF_INPUT_BEAN, outputDirBatch + GenerateSourceCodeUtil.normalizedClassName(objInputBean.getInputBeanCode())
						+ GenerateSourceCodeConst.JAVA_EXTEND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	private void generateObjectOutput(Module module, BusinessDesign businessDesign, GenerateSourceCode generateSourceCode, OutputBean objOutputBean, boolean customizeFlg) {
		OutputStreamWriter out = null;
		try {
			String outputDirDomain = null;
			String outputDirBatch = null;

			if(customizeFlg) {
				outputDirDomain = createFileOutputFolder("commoncustomize"
						+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN,
						BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());
				if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
					outputDirBatch = createFileOutputFolder("commoncustomize"
							+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN,
							BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathBatch());
				}
			} else {
				outputDirDomain = createFileOutputFolder("common"
						+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN,
						BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());
				if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
					outputDirBatch = createFileOutputFolder("common"
							+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN,
							BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathBatch());
				}
			}

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
			data.put("businessLogic", businessDesign);
			data.put("outputBean", objOutputBean);
			data.put("module", module);

			this.process(data, TEMPLATE_BUSSINESS_OBJ_OF_OUTPUT_BEAN, outputDirDomain + GenerateSourceCodeUtil.normalizedClassName(objOutputBean.getOutputBeanCode()) + GenerateSourceCodeConst.JAVA_EXTEND);
			
			if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())) {
				this.process(data, TEMPLATE_BATCH_BUSSINESS_OBJ_OF_OUTPUT_BEAN, outputDirBatch + GenerateSourceCodeUtil.normalizedClassName(objOutputBean.getOutputBeanCode()) + GenerateSourceCodeConst.JAVA_EXTEND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	private void generateObjectObjectDefinition(Module module, BusinessDesign businessDesign, GenerateSourceCode generateSourceCode, ObjectDefinition objectDefinition) {
		OutputStreamWriter out = null;
		try {
			String outputDir = createFileOutputFolder("common"
					+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION,
					BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());
			
			String outputDirBatch = null;
			if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())){
				outputDir = createFileOutputFolder("common"
						+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION,
						BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathBatch());
			}
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
			data.put("businessLogic", businessDesign);
			data.put("objectdefinition", objectDefinition);
			data.put("module", module);
			
			this.process(data, TEMPLATE_BUSSINESS_OBJ_OF_OBJ_DEFINITION, outputDir + GenerateSourceCodeUtil.normalizedClassName(objectDefinition.getObjectDefinitionCode()) + GenerateSourceCodeConst.JAVA_EXTEND);
		
			if(Boolean.TRUE.equals(generateSourceCode.getBatchModuleFlg())){
				this.process(data, TEMPLATE_BATCH_BUSSINESS_OBJ_OF_OBJ_DEFINITION, outputDirBatch + GenerateSourceCodeUtil.normalizedClassName(objectDefinition.getObjectDefinitionCode()) + GenerateSourceCodeConst.JAVA_EXTEND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
}
