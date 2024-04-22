package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
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
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.ColumnFileFormat;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.ExportAssignValue;
import org.terasoluna.qp.domain.model.ExportFileComponent;
import org.terasoluna.qp.domain.model.FileFormat;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.ImportAssignValue;
import org.terasoluna.qp.domain.model.ImportFileComponent;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.AdvanceComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ExportFileComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ImportFileComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.MessageParameterRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ValidationCheckDetailRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableRepository;
import org.terasoluna.qp.domain.repository.generatesourcecode.GenerateSourceCodeRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst.BusinessLogicGenerate;

/**
 * Handler for generating BLogic class
 *
 * @author hunghx
 * @version 1.0
 */
@Component(value="BatchBusinessLogicGenerateHandler")
public class BatchBusinessLogicGenerateHandler extends GenerationHandler {

	@Inject
	GenerateSourceCodeRepository generateSourceCodeRepository;

	@Inject
	DetailServiceImpHandler detailServiceImpHandler;

	@Inject
	SystemService systemService;

	@Inject
	BusinessDesignRepository businessDesignRepository;

	@Inject
	TableDesignRepository tableDesignRepository;

	@Inject
	SqlDesignRepository sqlDesignRepository;

	@Inject
	ValidationCheckDetailRepository validationCheckDetailRepository;

	@Inject
	MessageParameterRepository messageParameterRepository;

	@Inject
	ImportFileComponentRepository importFileComponentRepository;

	@Inject
	ExportFileComponentRepository exportFileComponentRepository;

	@Inject
	DecisionTableRepository decisionTableRepository;
	
	@Inject
	AdvanceComponentRepository advanceComponentRepository;
	
	private static final String TEMPLATE_BUSSINESS_OBJ_DEFINITION = "business_logic_batch_object_definition_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OBJ_OF_OBJ_DEFINITION = "business_logic_batch_object_object_definition_java.ftl";
	private static final String TEMPLATE_BUSSINESS_LOGIC = "business_logic_batch_java.ftl";
	private static final String TEMPLATE_BUSSINESS_LOGIC_XML = "business_logic_batch_xml.ftl";
	private static final String TEMPLATE_BUSSINESS_LOGIC_COMMON_SQL_XML = "business_logic_common_sql_batch_xml.ftl";
	private static final String TEMPLATE_BUSSINESS_INPUT_BEAN = "business_logic_batch_input_bean_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OBJ_OF_INPUT_BEAN = "business_logic_batch_object_input_bean_java.ftl";
	private static final String TEMPLATE_BUSSINESS_MODEL = "business_logic_model_java.ftl";
	private static final String TEMPLATE_SMTP_PROPERTIES = "smtp_properties.ftl";
	
	private static final String TAB = "\t";
	private static final String NL = "\n";
	private static StringBuilder pathPackage;
	private Project project;
	private Long currentLanguageId;

	private void init(GenerateSourceCode generateSourceCode) {
		project = generateSourceCode.getProject();
		currentLanguageId = generateSourceCode.getLanguageId();
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

	/**
	 * Generate source by scope type
	 *
	 * @param generateSourceCode
	 * @param generateType of business : 0 -> online , 1: batch , else : all
	 */
	public void handlerGenerateSourceType(GenerateSourceCode generateSourceCode, int generateType){

		init(generateSourceCode);
		
		if (CollectionUtils.isEmpty(generateSourceCode.getModules())) {
			return;
		}

		// create list batch modules
		List<Module> batchModules = new ArrayList<Module>();
		for(Module item : generateSourceCode.getModules()) {
			if (DbDomainConst.FunctionType.BATCH.equals(item.getModuleType())) {
				batchModules.add(item);
			}
		}

		if(CollectionUtils.isEmpty(batchModules)) return;
		// Preparing data for generate controller
		preparingDataForGenerate(batchModules, generateType);
		// Generate source model
		generateSourceModel(generateSourceCode);
		for(Module item : batchModules) {
			generateSourceBatchLayer(item, generateSourceCode);
		}
		
		if(CollectionUtils.isNotEmpty(batchModules)) generateDomainProperties(generateSourceCode);
	}

	private void generateDomainProperties(GenerateSourceCode generateSourceCode) {
		try {
			String outputDirProperties = createFileOutputFolder(StringUtils.EMPTY, BusinessLogicGenerate.PROPERTIES, generateSourceCode.getSourcePathBatch());
	
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("title", "JAVAMAIL");
			data.put("host", StringUtils.isNotEmpty(project.getSmtpHost())?project.getSmtpHost():"xxx.xxx.xxx.xxx");
			data.put("port", project.getSmtpPort() != null?project.getSmtpPort():-1);
			data.put("user", StringUtils.isNotEmpty(project.getSmtpUserName())? project.getSmtpUserName():"xxxx");
			data.put("password", StringUtils.isNotEmpty(project.getSmtpPassword())? project.getSmtpPassword() : "xxxx");
			data.put("authen", (project.getSmtpEncryption() == null || project.getSmtpEncryption() == 1)?"true":"false");
			data.put("from", StringUtils.isNotEmpty(project.getEmailAddress())? project.getEmailAddress():"xxx@xxx");
			data.put("subject", "TerasolunaQp");
	
			this.process(data, TEMPLATE_SMTP_PROPERTIES, outputDirProperties + "terasoluna-qp-smtp.properties");
		} catch (Exception e) {
			GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		}
	}
	
	/**
	 * Generate source by scope type
	 *
	 * @param generateSourceCode
	 */
	@Override
	public void handle(GenerateSourceCode generateSourceCode,  CommonModel comon) {
		handlerGenerateSourceType(generateSourceCode, DbDomainConst.FunctionType.BATCH);
	}

	private void preparingDataForGenerate(List<Module> listOfModules, int generateType) {
		List<BusinessDesign> businessLogicLst = generateSourceCodeRepository.findAllBusinessLogicByModuleLst(listOfModules, project.getProjectId(), generateType);

		for (Module module : listOfModules) {
			List<BusinessDesign> tmpLst = new ArrayList<BusinessDesign>();
			for (BusinessDesign businessDesign : businessLogicLst) {
				if (module.getModuleId().equals(businessDesign.getModuleId())) {
					businessDesign.setBusinessLogicName(WordUtils.capitalize(businessDesign.getBusinessLogicCode()));
					tmpLst.add(businessDesign);
				}
			}
			module.setListBusinessDesign(tmpLst);
		}
	}

	private void generateSourceBatchLayer(Module item, GenerateSourceCode generateSourceCode) {
		generateSourceBean(item, generateSourceCode);
		generateSourceBatch(item, generateSourceCode.getSourcePathBatch());
	}

	/**
	 * Module for process generate form and model bean from form <br>
	 * used for mapping value between form and model bean via dozer mapping.
	 *
	 * @param module
	 * @param generateSourceCode
	 */
	private void generateSourceBean(Module module, GenerateSourceCode generateSourceCode) {

		try {
			List<BusinessDesign> lstBlogic = preparingDataGenerateSourceBean(module);

			String baseOutputDirBatch = createFileOutputFolder(WordUtils.uncapitalize(module.getModuleCode()), BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathBatch());

			for (BusinessDesign businessDesign : lstBlogic) {

				businessDesign.setBusinessLogicName(WordUtils.capitalize(businessDesign.getBusinessLogicCode()));
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
				data.put("businessLogic", businessDesign);
				data.put("singleInputList", getSingleListInputBean(businessDesign.getLstInputBean()));
				data.put("singleObjList", getSingleListObjectDefinition(businessDesign.getLstObjectDefinition()));
				this.generateObjectInputLst(module, businessDesign,
						generateSourceCode, BusinessLogicGenerateHandler.getObjectListInputBean(businessDesign.getLstInputBean()));
				this.generateObjectObjectDefinitionLst(module, businessDesign,
					generateSourceCode.getSourcePathBatch(), getObjectListObjectDefinition(businessDesign.getLstObjectDefinition()));
				data.put("module", module);

				String outputDirBatch = baseOutputDirBatch + File.separator + GenerateSourceCodeUtil.normalizedPackageName(businessDesign.getBusinessLogicCode()) + File.separator;
				FileUtilsQP.createDirectory(outputDirBatch);

				this.process(data, TEMPLATE_BUSSINESS_INPUT_BEAN, outputDirBatch
						+ businessDesign.getBusinessLogicName()
						+ BusinessLogicGenerate.SUFFIX_INPUT_BEAN
						+ GenerateSourceCodeConst.JAVA_EXTEND);
				
				this.process(data, TEMPLATE_BUSSINESS_OBJ_DEFINITION, outputDirBatch
						+ businessDesign.getBusinessLogicName()
						+ BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION
						+ GenerateSourceCodeConst.JAVA_EXTEND);
			}

		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	private List<BusinessDesign> preparingDataGenerateSourceBean(Module module) {

		List<BusinessDesign> lstBlogic = module.getListBusinessDesign();
		List<InputBean> inputBeanLst = generateSourceCodeRepository.findAllInputBeanByBusinessIdLst(lstBlogic);

		List<ObjectDefinition> objBeanLst = generateSourceCodeRepository.findAllObjDefinedByBusinessIdLst(lstBlogic);
		List<ImportFileComponent> allImportFile	= importFileComponentRepository.findAllImportFileComponentByModule(module.getModuleId());
		List<ImportAssignValue> listImportAssignValue = importFileComponentRepository.findAllImportAssignValueByModule(module.getModuleId());

		List<ExportFileComponent> allExportFile	= exportFileComponentRepository.findAllExportFileComponentByModule(module.getModuleId());
		List<ExportAssignValue> listExportAssignValue = exportFileComponentRepository.findAllExportAssignValueByModule(module.getModuleId());


		Map<Long, List<InputBean>> mapInputBeanLst = new HashMap<Long, List<InputBean>>();
		for (InputBean inputBean : inputBeanLst) {
			// push to map object
			if(mapInputBeanLst.containsKey(inputBean.getBusinessLogicId())){
				mapInputBeanLst.get(inputBean.getBusinessLogicId()).add(inputBean);
			}
			else {
				List<InputBean> objLst = new ArrayList<InputBean>();
				objLst.add(inputBean);
				mapInputBeanLst.put(inputBean.getBusinessLogicId(), objLst);
			}
		}

		Map<Long, List<ObjectDefinition>> mapObjBeanLst = new HashMap<Long, List<ObjectDefinition>>();
		for (ObjectDefinition objBean : objBeanLst) {

			// assign file format
			if(objBean.getDataType() == 0){
				// Processing for export file
				for(ExportFileComponent exportFileComponent : allExportFile){
					if(objBean.getObjectDefinitionId().equals(exportFileComponent.getParameterId())){
						FileFormat fileFormat = exportFileComponent.getFileFormat();

						if(fileFormat.getLineFeedCharType() == null){
							fileFormat.setLineFeedCharType(0);
						}
						if(fileFormat.getFileEncoding() == null){
							fileFormat.setFileEncoding(0);
						}
						if(fileFormat.getEncloseCharType() == null){
							fileFormat.setEncloseCharType(0);
						}
						if(fileFormat.getHeadLineCount() == null){
							fileFormat.setHeadLineCount("0");
						}
						if(fileFormat.getTrailerLineCount() == null){
							fileFormat.setTrailerLineCount("0");
						}
						if( fileFormat.getLineFeedCharType() != null && fileFormat.getLineFeedCharType() == 1){
							fileFormat.setLineFeedChar("\\n");
						}
						
						objBean.setFileFormat(fileFormat);
						//break;
					}
				}
				// Processing for import file
				for(ImportFileComponent importFileComponent : allImportFile){
					if(objBean.getObjectDefinitionId().equals(importFileComponent.getTargetId())){
						FileFormat fileFormat = importFileComponent.getFileFormat();

						if(fileFormat.getLineFeedCharType() == null){
							fileFormat.setLineFeedCharType(0);
						}
						if(fileFormat.getFileEncoding() == null){
							fileFormat.setFileEncoding(0);
						}
						if(fileFormat.getEncloseCharType() == null){
							fileFormat.setEncloseCharType(0);
						}
						if(fileFormat.getHeadLineCount() == null){
							fileFormat.setHeadLineCount("0");
						}
						if(fileFormat.getTrailerLineCount() == null){
							fileFormat.setTrailerLineCount("0");
						}
						if( fileFormat.getLineFeedCharType() != null && fileFormat.getLineFeedCharType() == 1){
							fileFormat.setLineFeedChar("\\n");
						}
						
						
						if(objBean.getFileFormat() != null && Boolean.TRUE.equals(objBean.getFileFormat().getOverwriteFlg()))
							fileFormat.setOverwriteFlg(Boolean.TRUE);
						
						objBean.setFileFormat(fileFormat);
						//break;
					}
				}
			}
			else if(objBean.getDataType() != 14 && objBean.getDataType() != 16 && objBean.getDataType() != 17){
				for(ExportAssignValue exportAssignValue : listExportAssignValue){
					if(objBean.getObjectDefinitionId().equals(exportAssignValue.getParameterId())){
						objBean.setOutputColumnNo(exportAssignValue.getColumnNo());
						ColumnFileFormat columnFileFormat = exportAssignValue.getColumnFileFormat();
						if(columnFileFormat == null){
							columnFileFormat = new ColumnFileFormat();
						}
						
						if(columnFileFormat.getPaddingType() == null){
							columnFileFormat.setPaddingType(0);
						}
						if(columnFileFormat.getPaddingCharType() == null){
							columnFileFormat.setPaddingCharType(0);
						}
						if(columnFileFormat.getSpecifyByte() == null){
							columnFileFormat.setSpecifyByte("");
						}
						if(columnFileFormat.getColumnFormat() == null){
							columnFileFormat.setColumnFormat(0);
						}
						if(columnFileFormat.getTrimType() == null){
							columnFileFormat.setTrimType(0);
						}
						if(columnFileFormat.getTrimChar() == null){
							columnFileFormat.setTrimChar("");
						}
						if(columnFileFormat.getConverter() == null){
							columnFileFormat.setConverter(0);
						}
						
						objBean.setColumnFileFormat(columnFileFormat);
						//break;
					}
				}
				for(ImportAssignValue importAssignValue : listImportAssignValue){
					if(objBean.getObjectDefinitionId().equals(importAssignValue.getTargetId())){
						objBean.setInputColumnNo(importAssignValue.getColumnNo());
						//break;
					}
				}
			}
			// push to map object
			if(mapObjBeanLst.containsKey(objBean.getBusinessLogicId())){
				mapObjBeanLst.get(objBean.getBusinessLogicId()).add(objBean);
			}
			else {
				List<ObjectDefinition> objLst = new ArrayList<ObjectDefinition>();
				objLst.add(objBean);
				mapObjBeanLst.put(objBean.getBusinessLogicId(), objLst);
			}
		}

		for (BusinessDesign businessDesign : lstBlogic) {

			List<ObjectDefinition> objLst = mapObjBeanLst.get(businessDesign.getBusinessLogicId());
			if( objLst == null ){
				objLst = new ArrayList<ObjectDefinition>();
			}
			businessDesign.setLstObjectDefinition(objLst);

			List<InputBean> inputLst = mapInputBeanLst.get(businessDesign.getBusinessLogicId());
			if( inputLst == null ){
				inputLst = new ArrayList<InputBean>();
			}
			businessDesign.setLstInputBean(inputLst);
		}

		return lstBlogic;
	}

	private void generateObjectInputLst(Module module, BusinessDesign businessDesign, GenerateSourceCode generateSourceCode, List<InputBean> objInputBeanLst) {
		if(CollectionUtils.isNotEmpty(objInputBeanLst)) {
			for (InputBean element : objInputBeanLst) {
				if (CollectionUtils.isNotEmpty(element.getObjectList())) {
					generateObjectInputLst(module, businessDesign, generateSourceCode, element.getObjectList());
				}
				// Generate of nested object 
				generateObjectInput(module, businessDesign, generateSourceCode, element);
			}
		}
	}
	
	private void generateObjectInput(Module module, BusinessDesign businessDesign, GenerateSourceCode generateSourceCode, InputBean objInputBean) {

		OutputStreamWriter out = null;
		try {
			String outputDirDomain = createFileOutputFolder(
					WordUtils.uncapitalize(module.getModuleCode())+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_INPUT_BEAN,
					BusinessLogicGenerate.BATCH, generateSourceCode.getSourcePathDomain());
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
			data.put("businessLogic", businessDesign);
			data.put("inputBean", objInputBean);
			data.put("module", module);
			
			this.process(data, TEMPLATE_BUSSINESS_OBJ_OF_INPUT_BEAN, outputDirDomain
							+ WordUtils.capitalize(objInputBean.getInputBeanCode())
							+ GenerateSourceCodeConst.JAVA_EXTEND);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
	
	private void generateObjectObjectDefinitionLst(Module module, BusinessDesign businessDesign, String pathSourceDomain, List<ObjectDefinition> objObjDefinitionLst) {
		for (ObjectDefinition element : objObjDefinitionLst) {
			if (CollectionUtils.isNotEmpty(element.getObjectList())) {
				generateObjectObjectDefinitionLst(module, businessDesign, pathSourceDomain, element.getObjectList());
			}
			// Generate of nested object
			generateObjectObjectDefinition(module, businessDesign, pathSourceDomain, element);
		}
	}

	private void generateObjectObjectDefinition(Module module, BusinessDesign businessDesign, String pathSourceDomain, ObjectDefinition objectDefinition) {

		OutputStreamWriter out = null;
		try {
			String outputDir = createFileOutputFolder(
					WordUtils.uncapitalize(module.getModuleCode()) + File.separator + businessDesign.getBusinessLogicCode() +
				BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION, BusinessLogicGenerate.BATCH, pathSourceDomain);

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package", project.getPackageName());
			data.put("businessLogic", businessDesign);
			data.put("objectdefinition", objectDefinition);
			data.put("module", module);
			
			this.process(data, TEMPLATE_BUSSINESS_OBJ_OF_OBJ_DEFINITION, outputDir
					+ WordUtils.capitalize(objectDefinition.getObjectDefinitionCode())
					+ GenerateSourceCodeConst.JAVA_EXTEND);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	private void generateSourceBatch(Module module, String pathSource){

		boolean isExistSql = CollectionUtils.isNotEmpty(sqlDesignRepository.getAllSqlDesignByModuleId(module.getModuleId(), null));
		boolean isExistSqlCommon = CollectionUtils.isNotEmpty(sqlDesignRepository.getAllSqlDesignByProjectId(project.getProjectId(), null));
		boolean isExistAdvancedNormal = CollectionUtils.isNotEmpty(advanceComponentRepository.findAdvanceComponentByModule(module.getModuleId()));
		//List<BusinessDesign> businessDesigns = generateSourceCodeRepository.findAllCommonBusinessLogicByProject(project.getProjectId());
		boolean isExistDecision = CollectionUtils.isNotEmpty(decisionTableRepository.findAllDecisionByProjectId(project.getProjectId()));
		
		try {
			String baseOutputDir = createFileOutputFolder(WordUtils.uncapitalize(module.getModuleCode()), BusinessLogicGenerate.BATCH, pathSource);
			String outputXmlDir = getFileOutputFolder(module.getModuleCode(), BusinessLogicGenerate.BATCH_XML, pathSource);
			module.setPathSourceBatch(pathSource);
			// demo gen details;
			detailServiceImpHandler.generateServiceImpDetail(module, project, currentLanguageId);

			Map<String, Object> data = new HashMap<String, Object>();
			
			for(BusinessDesign item : module.getListBusinessDesign()){
				
				// gen parse input bean
				String parseInput = generateParseInput(item);
				boolean isExistObjObjDef = false;

				for( ObjectDefinition od : item.getLstObjectDefinition()){
					if( od.getDataType() == 0){
						isExistObjObjDef = true;
						break;
					}
				}
				
				data.clear();
				data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
				data.put("item", item);
				data.put("module", module);
				data.put("isExistSql", isExistSql);
				data.put("isExistSqlCommon", isExistSqlCommon);
				data.put("isExistDecision", isExistDecision);
	            data.put("isExistAdvancedNormal", isExistAdvancedNormal);
				data.put("parseInput", parseInput);
				data.put("isExistObjObjDef", isExistObjObjDef);

				String javaFileName = WordUtils.capitalize(item.getBusinessLogicCode());
				String outputDir = baseOutputDir + File.separator + GenerateSourceCodeUtil.normalizedPackageName(item.getBusinessLogicCode()) + File.separator;
				FileUtilsQP.createDirectory(outputDir);
				
				this.process(data, TEMPLATE_BUSSINESS_LOGIC, outputDir
						+ javaFileName
						+ BusinessLogicGenerate.SUFFIX_BLOGIC
						+ GenerateSourceCodeConst.JAVA_EXTEND);

				this.process(data, TEMPLATE_BUSSINESS_LOGIC_XML, outputXmlDir
						+ WordUtils.capitalize(item.getBusinessLogicCode())
						+ ".xml");
			}
			
			// Generate configuration sql common
			data.clear();
			data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
			
			if(isExistSqlCommon){
				this.process(data, TEMPLATE_BUSSINESS_LOGIC_COMMON_SQL_XML, outputXmlDir + "commonSqlContext.xml");
			}
			// End
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateSourceModel(GenerateSourceCode generateSourceCode) {
		List<TableDesign> tableDesignLst = tableDesignRepository.getTableDesignByProjectId(project.getProjectId());
		List<TableDesignDetails> tableDesignDetailsLst = generateSourceCodeRepository.findAllTableInformationByProjectId(project.getProjectId());
		OutputStreamWriter out = null;
		
		// Generate source model all domain and batch
		if(CollectionUtils.isNotEmpty(tableDesignLst)) {
			String outputDirDomain = createFileOutputFolder("", BusinessLogicGenerate.MODEL, generateSourceCode.getSourcePathBatch());
			Map<String, Object> data = null;
			for (TableDesign tbl : tableDesignLst) {
				List<TableDesignDetails> tblDesignDetails = new  ArrayList<TableDesignDetails>();
				for (TableDesignDetails tableDesignDetails : tableDesignDetailsLst) {
					if (tbl.getTableDesignId().equals(tableDesignDetails.getTableDesignId())) {
						
						try {
							tableDesignDetails.setDataTypeName(GenerateSourceCodeUtil.convertToJavaDataTypeNameFromBaseType(tableDesignDetails.getBaseType(), true));
						}catch(Exception ex) {
							ex.printStackTrace();
						}
						
						//tableDesignDetails.setDataTypeName(convertToJavaDataTypeNameFromBaseType(tableDesignDetails.getBaseType(), true));
						tableDesignDetails.setName(WordUtils.capitalize(tableDesignDetails.getCode()));
						tblDesignDetails.add(tableDesignDetails);
					}
				}
				tbl.setTableName(GenerateSourceCodeUtil.normalizedClassName(tbl.getTableCode()));
				tbl.setListTableDesignDetails(tblDesignDetails);
				
				try {
					String javaFileName = GenerateSourceCodeUtil.normalizedClassName(tbl.getTableCode());
					data = new HashMap<String, Object>();
					data.put("pakage", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
					data.put("place", "batch");
					data.put("table", tbl);

					this.process(data, TEMPLATE_BUSSINESS_MODEL, outputDirDomain
							+ javaFileName
							+ GenerateSourceCodeConst.JAVA_EXTEND);
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					IOUtils.closeQuietly(out);
				}
			}
		}
	}
	
	public String createFileOutputFolder(String moduleName, int sourceCodeType, String pathRoot) {
		StringBuilder pathOutput = new StringBuilder();
		switch (sourceCodeType) {
			// is business logic
			case GenerateSourceCodeConst.BusinessLogicGenerate.MODEL:
				pathOutput.append("src").append(File.separator).append("main").append(File.separator)
				.append("java").append(File.separator).append(pathPackage.toString())
				.append("batch").append(File.separator).append("model");
				break;

			case GenerateSourceCodeConst.BusinessLogicGenerate.BATCH:
				pathOutput.append("src").append(File.separator).append("main").append(File.separator)
				.append("java").append(File.separator).append(pathPackage.toString())
				.append(File.separator).append("batch").append(File.separator).append(moduleName);
				break;
				
			case GenerateSourceCodeConst.BusinessLogicGenerate.PROPERTIES:
				pathOutput.append(GenerateSourceCodeUtil.normalizedPackageName("src"+ File.separator + "main"+ File.separator + "resources" + File.separator))
					.append("appprops");
				break;
		}

		return GenerateSourceCodeUtil.createSaveFileDirectory(GenerateSourceCodeUtil.normalizedPackageName(pathOutput.toString()), pathRoot);
	}

	public String getFileOutputFolder(String moduleName, int sourceCodeType, String pathRoot) {
		StringBuilder pathOutput = new StringBuilder();
		switch (sourceCodeType) {
			case GenerateSourceCodeConst.BusinessLogicGenerate.BATCH_XML:
				pathOutput.append("src").append(File.separator).append("main").append(File.separator)
				.append("resources").append(File.separator).append("beansDef").append(File.separator);
				break;
		}

		return pathRoot + pathOutput.toString();
	}

	public static List<ObjectDefinition> getSingleListObjectDefinition(List<ObjectDefinition> objList) {
		List<ObjectDefinition> listObjSingle = new ArrayList<ObjectDefinition>();
		for (ObjectDefinition objBean : objList) {
			if (objBean.getParentObjectDefinitionId() == null) {
				listObjSingle.add(objBean);
			}
		}
		return listObjSingle;
	}

	public static List<ObjectDefinition> getObjectListObjectDefinition(List<ObjectDefinition> outputBeanList) {
		List<ObjectDefinition> listObjDefinitionObject = new ArrayList<ObjectDefinition>();
		for (ObjectDefinition objBean : outputBeanList) {
			if (objBean.getParentObjectDefinitionId() == null && objBean.getDataType().equals(0)) {
				settingFieldObjectObjectDefinition(objBean, outputBeanList);
				listObjDefinitionObject.add(objBean);
			}
		}
		return listObjDefinitionObject;
	}

	public static void settingFieldObjectObjectDefinition(ObjectDefinition item, List<ObjectDefinition> outputBeanList) {
		List<ObjectDefinition> listObjDefinitionSingle = new ArrayList<ObjectDefinition>();
		List<ObjectDefinition> listObjDefinitionObject = new ArrayList<ObjectDefinition>();
		for (ObjectDefinition obj : outputBeanList) {
			if (item.getObjectDefinitionId().equals(obj.getParentObjectDefinitionId())) {
				listObjDefinitionSingle.add(obj);
				if(obj.getDataType().equals(0)) {
					settingFieldObjectObjectDefinition(obj, outputBeanList);
					listObjDefinitionObject.add(obj);
				}
			}
		}
		item.setSingleList(listObjDefinitionSingle);
		item.setObjectList(listObjDefinitionObject);
	}

	public static List<InputBean> getSingleListInputBean(List<InputBean> inputBeanList) {
		List<InputBean> listInputSingle = new ArrayList<InputBean>();
		for (InputBean inputBean : inputBeanList) {
			if (inputBean.getParentInputBeanId() == null) {
				listInputSingle.add(inputBean);
			}
		}
		return listInputSingle;
	}

	private String generateParseInput(BusinessDesign businessDesign){

		List<InputBean> inputBeanLst = getSingleListInputBean(businessDesign.getLstInputBean());
		StringBuilder parseInputBuilder = new StringBuilder();

		String NEW_INPUT_BEAN = "{0}InputBean in = new {0}InputBean();";
		parseInputBuilder.append("// Init input bean").append(NL);
		parseInputBuilder.append(TAB).append(TAB).append(MessageFormat.format(NEW_INPUT_BEAN, WordUtils.capitalize(businessDesign.getBusinessLogicCode()))).append(NL);

		for( int index = 0 ; index < inputBeanLst.size(); index++){
			InputBean inputBean = inputBeanLst.get(index);

			String target = "in.set" + WordUtils.capitalize(inputBean.getInputBeanCode()) +"({0});";
			String paramName =  MessageFormat.format("param.getJobArgNm{0}()", index + 1);
			String str = BusinessLogicGenerateHelper.getContentByCastDataType(inputBean.getDataType(), GenerateSourceCodeConst.DataType.STRING, paramName);

			parseInputBuilder.append(TAB).append(TAB).append(MessageFormat.format(target, str)).append(NL);
		}
		
		return parseInputBuilder.toString();
	}

	/**
	 * @return the pathPackage
	 */
	public static StringBuilder getPathPackage() {
		return pathPackage;
	}

	/**
	 * @param pathPackage the pathPackage to set
	 */
	public static void setPathPackage(StringBuilder pathPackage) {
		BatchBusinessLogicGenerateHandler.pathPackage = pathPackage;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}
}
