package org.terasoluna.qp.domain.service.generatesourcecode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.JavaDataTypeOfBlogic;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.message.SessionManagementMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.MessageParameter;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenItemOutput;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.ValidationCheckDetail;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.AdvanceComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.MessageParameterRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ValidationCheckDetailRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableRepository;
import org.terasoluna.qp.domain.repository.generatesourcecode.GenerateSourceCodeRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst.ValidateType;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst.BusinessLogicGenerate;

/**
 * Handler for generating BLogic class
 * 
 * @author hunghx
 * @version 1.0
 */
@Component(value="BusinessLogicGenerateHandler")
public class BusinessLogicGenerateHandler extends GenerationHandler {

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
	DecisionTableRepository decisionTableRepository;
	
	@Inject
	ValidationCheckDetailRepository validationCheckDetailRepository;

	@Inject
	MessageParameterRepository messageParameterRepository;
	
	@Inject
	AdvanceComponentRepository advanceComponentRepository;
	
	@Inject ScreenDesignRepository screenDesignRepository;

	private static final String TEMPLATE_BUSSINESS_CONTROLLER = "business_logic_controller_java.ftl";
	private static final String TEMPLATE_BUSSINESS_INPUT_FORM = "business_logic_input_form_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OBJ_OF_INPUT_FORM = "business_logic_object_input_form_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OUTPUT_FORM = "business_logic_output_form_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OBJ_OF_OUTPUT_FORM = "business_logic_object_output_form_java.ftl";
	private static final String TEMPLATE_BUSSINESS_INPUT_BEAN = "business_logic_input_bean_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OBJ_OF_INPUT_BEAN = "business_logic_object_input_bean_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OUTPUT_BEAN = "business_logic_output_bean_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OBJ_OF_OUTPUT_BEAN = "business_logic_object_output_bean_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OBJ_DEFINITION = "business_logic_object_definition_java.ftl";
	private static final String TEMPLATE_BUSSINESS_OBJ_OF_OBJ_DEFINITION = "business_logic_object_object_definition_java.ftl";
	private static final String TEMPLATE_BUSSINESS_SERVICE = "business_logic_service_java.ftl";
	private static final String TEMPLATE_BUSSINESS_SERVICE_IMPL = "business_logic_service_impl_java.ftl";
	private static final String TEMPLATE_BUSSINESS_MODEL = "business_logic_model_java.ftl";
	private static final String TEMPLATE_SMTP_PROPERTIES = "smtp_properties.ftl";
	private static final String TEMPLATE_DOZER_MAPPER = "dozer_mapping_xml.ftl";
	private static final String TEMPLATE_BUSSINESS_DATA_SOURCE_OUTPUT_BEAN = "business_logic_data_source_output_bean_java.ftl";
	
	//KhangTM : Define web service template
	private static final String TEMPLATE_WS_BUSSINESS_CONTROLLER = "ws_business_logic_controller_java.ftl";
	private static final String TEMPLATE_WS_BUSSINESS_INPUT_FORM = "ws_business_logic_input_form_java.ftl";
	private static final String TEMPLATE_WS_BUSSINESS_OUTPUT_FORM = "ws_business_logic_output_form_java.ftl";
	private static final String TEMPLATE_WS_BUSSINESS_OBJ_OF_INPUT_FORM = "ws_business_logic_object_input_form_java.ftl";
	private static final String TEMPLATE_WS_BUSSINESS_OBJ_OF_OUTPUT_FORM = "ws_business_logic_object_output_form_java.ftl";
	
	
	private static final Map<Integer, String> mapAnnotation = new HashMap<Integer, String>();
	
	static {
		mapAnnotation.put(ValidateType.NOT_NULL, "@javax.validation.constraints.NotNull");
		mapAnnotation.put(ValidateType.NULL, "@javax.validation.constraints.Null");
		mapAnnotation.put(ValidateType.PATTERN, "@javax.validation.constraints.Pattern");
		mapAnnotation.put(ValidateType.MIN, "@org.terasoluna.qp.app.common.validation.QpMin");
		mapAnnotation.put(ValidateType.MAX, "@org.terasoluna.qp.app.common.validation.QpMax");
		mapAnnotation.put(ValidateType.DECIMAL_MIN, "@org.terasoluna.qp.app.common.validation.QpDecimalMin");
		mapAnnotation.put(ValidateType.DECIMAL_MAX, "@org.terasoluna.qp.app.common.validation.QpDecimalMax");
		mapAnnotation.put(ValidateType.SIZE, "@javax.validation.constraints.Size");
		mapAnnotation.put(ValidateType.DIGITS, "@javax.validation.constraints.Digits");
		mapAnnotation.put(ValidateType.ASSRT_TRUE, "@javax.validation.constraints.AssertTrue");
		mapAnnotation.put(ValidateType.ASSRT_FALSE, "@javax.validation.constraints.AssertFalse");
		mapAnnotation.put(ValidateType.FUTURE, "@javax.validation.constraints.Future");
		mapAnnotation.put(ValidateType.PAST, "@javax.validation.constraints.Past");
		mapAnnotation.put(ValidateType.CREDITCARD_NUMBER, "@org.terasoluna.qp.app.common.validation.QpCreaditCard");
		mapAnnotation.put(ValidateType.EMAIL, "@org.hibernate.validator.constraints.Email");
		mapAnnotation.put(ValidateType.URL, "@org.hibernate.validator.constraints.URL");
		mapAnnotation.put(ValidateType.NOT_BLANK, "@org.hibernate.validator.constraints.NotBlank");
		mapAnnotation.put(ValidateType.NOT_EMPTY, "@org.hibernate.validator.constraints.NotEmpty");
		mapAnnotation.put(ValidateType.ALPHABET, "@org.terasoluna.qp.app.common.validation.QpAlphabet");
		mapAnnotation.put(ValidateType.ALPHA_NUMERIC, "@org.terasoluna.qp.app.common.validation.QpAlphanumeric");
		mapAnnotation.put(ValidateType.BINARY, "@org.terasoluna.qp.app.common.validation.QpBinary");
		mapAnnotation.put(ValidateType.CURRENCY, "@org.terasoluna.qp.app.common.validation.QpCurrency");
		mapAnnotation.put(ValidateType.DECIMAL, "@org.terasoluna.qp.app.common.validation.QpDecimal");
		mapAnnotation.put(ValidateType.DOUBLE, "@org.terasoluna.qp.app.common.validation.QpDouble");
		mapAnnotation.put(ValidateType.EM_CHARACTER, "@org.terasoluna.qp.app.common.validation.QpEmCharacter");
		mapAnnotation.put(ValidateType.EN_CHARACTER, "@org.terasoluna.qp.app.common.validation.QpEnCharacter");
		mapAnnotation.put(ValidateType.FLOAT, "@org.terasoluna.qp.app.common.validation.QpFloat");
		mapAnnotation.put(ValidateType.HIRAGANA, "@org.terasoluna.qp.app.common.validation.QpHiragana");
		mapAnnotation.put(ValidateType.INTEGER, "@org.terasoluna.qp.app.common.validation.QpInteger");
		mapAnnotation.put(ValidateType.KANJI, "@org.terasoluna.qp.app.common.validation.QpKanji");
		mapAnnotation.put(ValidateType.KATAKANA, "@org.terasoluna.qp.app.common.validation.QpKatakana");
		mapAnnotation.put(ValidateType.LONG, "@org.terasoluna.qp.app.common.validation.QpLong");
		mapAnnotation.put(ValidateType.PHONE, "@org.terasoluna.qp.app.common.validation.QpPhone");
		mapAnnotation.put(ValidateType.POSTCODE, "@org.terasoluna.qp.app.common.validation.QpPostcode");
		mapAnnotation.put(ValidateType.SPACE, "@org.terasoluna.qp.app.common.validation.QpSpace");
		mapAnnotation.put(ValidateType.SYMBOL, "@org.terasoluna.qp.app.common.validation.QpSymbol");
		mapAnnotation.put(ValidateType.TIME, "@org.terasoluna.qp.app.common.validation.QpTime");
		mapAnnotation.put(ValidateType.YEAR, "@org.terasoluna.qp.app.common.validation.QpYear");
		mapAnnotation.put(ValidateType.ZENKAKU_ALPHABET, "@org.terasoluna.qp.app.common.validation.QpZenkakuAlphabet");
		mapAnnotation.put(ValidateType.ZENKAKU_KATAKANA, "@org.terasoluna.qp.app.common.validation.QpZenkakuKatakana");
		mapAnnotation.put(ValidateType.ZENKAKU_NUMERIC, "@org.terasoluna.qp.app.common.validation.QpZenkakuNumeric");
		mapAnnotation.put(ValidateType.ZENKAKU_SYMBOL, "@org.terasoluna.qp.app.common.validation.QpZenkakuSymbol");
		mapAnnotation.put(ValidateType.DAY, "@org.terasoluna.qp.app.common.validation.QpDay");
		mapAnnotation.put(ValidateType.HOUR, "@org.terasoluna.qp.app.common.validation.QpHour");
		mapAnnotation.put(ValidateType.MINUTE, "@org.terasoluna.qp.app.common.validation.QpMinute");
		mapAnnotation.put(ValidateType.MONTH, "@org.terasoluna.qp.app.common.validation.QpMonth");
		mapAnnotation.put(ValidateType.SECOND, "@org.terasoluna.qp.app.common.validation.QpSecond");
		mapAnnotation.put(ValidateType.WEEK, "@org.terasoluna.qp.app.common.validation.QpWeek");
		mapAnnotation.put(ValidateType.DATE_TIME, "@org.terasoluna.qp.app.common.validation.QpDateTime");
		mapAnnotation.put(ValidateType.DATE, "@org.terasoluna.qp.app.common.validation.QpDate");
		mapAnnotation.put(ValidateType.QP_SIZE, "@org.terasoluna.qp.app.common.validation.QpSize");

		mapAnnotation.put(ValidateType.TIMESTAMP, "@org.terasoluna.qp.app.common.validation.QpTimestamp");
		mapAnnotation.put(ValidateType.DATE_MIN, "@org.terasoluna.qp.app.common.validation.QpDateMin");
		mapAnnotation.put(ValidateType.DATE_MAX, "@org.terasoluna.qp.app.common.validation.QpDateMax");
		mapAnnotation.put(ValidateType.DATE_RANGE, "@org.terasoluna.qp.app.common.validation.QpDateRange");
		
		mapAnnotation.put(ValidateType.TIME_MIN, "@org.terasoluna.qp.app.common.validation.QpTimeMin");
		mapAnnotation.put(ValidateType.TIME_MAX, "@org.terasoluna.qp.app.common.validation.QpTimeMax");
		mapAnnotation.put(ValidateType.TIME_RANGE, "@org.terasoluna.qp.app.common.validation.QpTimeRange");
		
		mapAnnotation.put(ValidateType.TIMESTAMP_MIN, "@org.terasoluna.qp.app.common.validation.QpTimestampMin");
		mapAnnotation.put(ValidateType.TIMESTAMP_MAX, "@org.terasoluna.qp.app.common.validation.QpTimestampMax");
		mapAnnotation.put(ValidateType.TIMESTAMP_RANGE, "@org.terasoluna.qp.app.common.validation.QpTimestampRange");
		
		mapAnnotation.put(ValidateType.DATE_TIME_MIN, "@org.terasoluna.qp.app.common.validation.QpDateTimeMin");
		mapAnnotation.put(ValidateType.DATE_TIME_MAX, "@org.terasoluna.qp.app.common.validation.QpDateTimeMax");
		mapAnnotation.put(ValidateType.DATE_TIME_RANGE, "@org.terasoluna.qp.app.common.validation.QpDateTimeRange");
		
		mapAnnotation.put(ValidateType.QP_GREATER_THAN, "@org.terasoluna.qp.app.common.validation.QpGreaterThan");
		mapAnnotation.put(ValidateType.QP_LESS_THAN, "@org.terasoluna.qp.app.common.validation.QpLessThan");
		mapAnnotation.put(ValidateType.QP_EQUAL, "@org.terasoluna.qp.app.common.validation.QpEqual");
		mapAnnotation.put(ValidateType.QP_NOT_EQUAL, "@org.terasoluna.qp.app.common.validation.QpNotEqual");
		
		mapAnnotation.put(ValidateType.DATE_EQUAL, "@org.terasoluna.qp.app.common.validation.QpDateEqual");
		mapAnnotation.put(ValidateType.DATE_NOT_EQUAL, "@org.terasoluna.qp.app.common.validation.QpDateNotEqual");
		
		mapAnnotation.put(ValidateType.DATE_TIME_EQUAL, "@org.terasoluna.qp.app.common.validation.QpDateTimeEqual");
		mapAnnotation.put(ValidateType.DATE_TIME_NOT_EQUAL, "@org.terasoluna.qp.app.common.validation.QpDateTimeNotEqual");
		
		mapAnnotation.put(ValidateType.TIME_EQUAL, "@org.terasoluna.qp.app.common.validation.QpTimeEqual");
		mapAnnotation.put(ValidateType.TIME_NOT_EQUAL, "@org.terasoluna.qp.app.common.validation.QpTimeNotEqual");
		
		mapAnnotation.put(ValidateType.TIMESTAMP_EQUAL, "@org.terasoluna.qp.app.common.validation.QpTimestampEqual");
		mapAnnotation.put(ValidateType.TIMESTAMP_NOT_EQUAL, "@org.terasoluna.qp.app.common.validation.QpTimestampNotEqual");
		
		mapAnnotation.put(ValidateType.FUTURE_DATE, "@org.terasoluna.qp.app.common.validation.QpFutureDate");
		mapAnnotation.put(ValidateType.FUTURE_DATETIME, "@org.terasoluna.qp.app.common.validation.QpFutureDatetime");
		mapAnnotation.put(ValidateType.FUTURE_TIMESTAMP, "@org.terasoluna.qp.app.common.validation.QpFutureTimestamp");
		
		mapAnnotation.put(ValidateType.PAST_DATE, "@org.terasoluna.qp.app.common.validation.QpPastDate");
		mapAnnotation.put(ValidateType.PAST_DATETIME, "@org.terasoluna.qp.app.common.validation.QpPastDatetime");
		mapAnnotation.put(ValidateType.PAST_TIMESTAMP, "@org.terasoluna.qp.app.common.validation.QpPastTimestamp");
	}
	
	private static final Integer PARAMETER_TYPE_MSG_CODE = 0;
	private static final String NL = "\n\t";
	private static final String OPEN_BRACKET = "(";
	private static final String CLOSEST_BRACKET = ")";
	private static final String MSG_PARAM = "message = ";
	private static StringBuilder pathPackage;
	private Long currentLanguageId;
    private StringBuilder strSessionImport = new StringBuilder("");
    
	@Inject
    @Named("CL_HTTP_STATUS_CODE")
	private CodeList CL_HTTP_STATUS_CODE;
	
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
	
	/**
	 * Generate source by scope type
	 * 
	 * @param generateSourceCode
	 * @param comon 
	 * @param generateType of business : 0 -> online , 1: batch , else : all
	 */
	public void handlerGenerateSourceType(GenerateSourceCode generateSourceCode, CommonModel comon, int generateType){

		init(generateSourceCode, comon);

		if (CollectionUtils.isEmpty(generateSourceCode.getModules())) return;
		
		// create list online modules
		List<Module> onlineModules = new ArrayList<Module>();
		for(Module item : generateSourceCode.getModules()) {
			if (DbDomainConst.FunctionType.ONLINE.equals(item.getModuleType())) {
				onlineModules.add(item);
			}
		}
		
		// Preparing data for generate controller
		preparingDataForGeneraController(onlineModules, generateType);
		// Preparing data for generate model
		generateSourceModel(generateSourceCode);
		
		for(Module item : onlineModules) {			
			generateSourceDomainLayer(item, generateSourceCode);
			generateSourceWebLayer(item, generateSourceCode.getSourcePathWeb());
		}
		
		if(CollectionUtils.isNotEmpty(onlineModules)) generateDomainProperties(generateSourceCode);
	}
	
	private void generateDomainProperties(GenerateSourceCode generateSourceCode) {
		try {
			String outputDirProperties = createFileOutputFolder(StringUtils.EMPTY, BusinessLogicGenerate.PROPERTIES, generateSourceCode.getSourcePathDomain());
	
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
	public void handle(GenerateSourceCode generateSourceCode, CommonModel comon) {//KhangTM: main
		handlerGenerateSourceType(generateSourceCode, comon, DbDomainConst.FunctionType.ONLINE);
	}
	
	private void preparingDataForGeneraController(List<Module> listOfModules, int generateType) {
		List<BusinessDesign> businessLogicLst = generateSourceCodeRepository.findAllBusinessLogicByModuleLst(listOfModules, project.getProjectId(), generateType);

		for (Module module : listOfModules) {
			List<BusinessDesign> tmpLst = new ArrayList<BusinessDesign>();
			for (BusinessDesign businessDesign : businessLogicLst) {
				businessDesign.setBusinessLogicCode(GenerateSourceCodeUtil.normalizedVariantName(businessDesign.getBusinessLogicCode()));
				//check web service enable or not
				if(businessDesign.getBlogicType().equals(BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE) && project.getWebserviceFlg()==false){
					continue;
				}
				
				if (module.getModuleId().equals(businessDesign.getModuleId())) {
					businessDesign.setBusinessLogicName(WordUtils.capitalize(businessDesign.getBusinessLogicCode()));
					tmpLst.add(businessDesign);
				}
			}
			module.setListBusinessDesign(tmpLst);
		}
	}

	private void generateSourceDomainLayer(Module item, GenerateSourceCode generateSourceCode) {
		generateSourceBean(item, generateSourceCode);
		generateSourceService(item, generateSourceCode.getSourcePathDomain());
	}
	
	/**
	 * Generate source model for domain and batch layer.
	 * 
	 * @param generateSourceCode
	 */
	private void generateSourceModel(GenerateSourceCode generateSourceCode) {
		List<TableDesign> tableDesignLst = tableDesignRepository.getTableDesignByProjectId(project.getProjectId());
		List<TableDesignDetails> tableDesignDetailsLst = generateSourceCodeRepository.findAllTableInformationByProjectId(project.getProjectId());
		OutputStreamWriter out = null;
		
		// Generate source model all domain and batch
		if(CollectionUtils.isNotEmpty(tableDesignLst)){
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
					String outputDirDomain = createFileOutputFolder("", BusinessLogicGenerate.MODEL, generateSourceCode.getSourcePathDomain());
					String javaFileName = GenerateSourceCodeUtil.normalizedClassName(tbl.getTableCode());
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("pakage", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
					data.put("place", "domain");
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

	/**
	 * Module for process generate form and model bean from form <br>
	 * used for mapping value between form and model bean via dozer mapping.
	 * 
	 * @param module
	 * @param generateSourceCode
	 */
	private void generateSourceBean(Module module, GenerateSourceCode generateSourceCode) {
		OutputStreamWriter out = null;
		try {
			List<BusinessDesign> lstBlogic = preparingDataGenerateSourceBean(module.getListBusinessDesign());
			
			String outputDirDomain = createFileOutputFolder(GenerateSourceCodeUtil.normalizedPackageName(module.getModuleCode()), BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());
			String outputDirWeb = null;					
						
			for (BusinessDesign businessDesign : lstBlogic) {
				businessDesign.setBusinessLogicName(WordUtils.capitalize(businessDesign.getBusinessLogicCode()));
				
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
				data.put("businessLogic", businessDesign);
				data.put("singleInputList", getSingleListInputBean(businessDesign.getLstInputBean()));
				this.generateObjectInputLst(module, businessDesign, generateSourceCode, getObjectListInputBean(businessDesign.getLstInputBean()));
				data.put("singleOutputList", getSingleListOutputBean(businessDesign.getLstOutputBean()));
				this.generateObjectOutputLst(module, businessDesign, generateSourceCode, getObjectListOutputBean(businessDesign.getLstOutputBean()));
				data.put("singleObjList", getSingleListObjectDefinition(businessDesign.getLstObjectDefinition()));
				this.generateObjectObjectDefinitionLst(module, businessDesign, generateSourceCode.getSourcePathDomain(), getObjectListObjectDefinition(businessDesign.getLstObjectDefinition()));
				
				data.put("module", module);

				// Add new process write file
				this.process(data, TEMPLATE_BUSSINESS_INPUT_BEAN, outputDirDomain
						+ businessDesign.getBusinessLogicName() 
						+ BusinessLogicGenerate.SUFFIX_INPUT_BEAN 
						+ GenerateSourceCodeConst.JAVA_EXTEND);
				
				this.process(data, TEMPLATE_BUSSINESS_OUTPUT_BEAN, outputDirDomain
						+ businessDesign.getBusinessLogicName()
						+ BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN
						+ GenerateSourceCodeConst.JAVA_EXTEND);
				
				this.process(data, TEMPLATE_BUSSINESS_OBJ_DEFINITION, outputDirDomain
						+ businessDesign.getBusinessLogicName()
						+ BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION
						+ GenerateSourceCodeConst.JAVA_EXTEND);
				
				// Gen Data Source
				List<OutputBean> lstDataSourceOutputBean = new ArrayList<>();
				List<OutputBean> lstNonDataSourceOutputBean = new ArrayList<>();
				List<ScreenItemOutput> lstScreenItemMapping = businessDesignRepository.findAllScreenItemMappingByOutputBeanId(businessDesign.getBusinessLogicId());
				
				String parentId = "";
				boolean isDataSource = false;
				for (OutputBean outputBean : businessDesign.getLstOutputBean()) {
					isDataSource = false;
					if(CollectionUtils.isNotEmpty(lstScreenItemMapping)) {
						for (ScreenItemOutput screenItemOutput : lstScreenItemMapping) {
							if(DataTypeUtils.equals(screenItemOutput.getOutputBeanId().toString(), outputBean.getOutputBeanId()) 
									&& GenerateSourceCodeConst.MappingType.DATA_SOURCE.equals(screenItemOutput.getMappingType())) {
								isDataSource = true;
								break;
							}
						}
					}

					if(isDataSource) {
						lstDataSourceOutputBean.add(outputBean);
						parentId = outputBean.getOutputBeanId();
					} else if (StringUtils.isEmpty(outputBean.getParentOutputBeanId()) || !parentId.equals(outputBean.getParentOutputBeanId())) {
						lstNonDataSourceOutputBean.add(outputBean);
					}
				}
				
				if (lstDataSourceOutputBean.size() != 0) {
					List<OutputBean> lstDataSourceOutputBeanToGen = new ArrayList<>();
					
					for (OutputBean ou : lstDataSourceOutputBean) {
						findParentDataSourceOutputBean(businessDesign.getLstOutputBean(), ou, lstDataSourceOutputBeanToGen);
					}
					
					businessDesign.setLstDataSourceOutputBean(lstDataSourceOutputBeanToGen);
					data.put("singleDataSourceOutputList", getSingleListOutputBean(lstDataSourceOutputBeanToGen));
					
					this.process(data, TEMPLATE_BUSSINESS_DATA_SOURCE_OUTPUT_BEAN, outputDirDomain
							+ businessDesign.getBusinessLogicName()
							+ BusinessLogicGenerate.SUFFIX_DATA_SOURCE
							+ BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN
							+ GenerateSourceCodeConst.JAVA_EXTEND);
				}
				
				businessDesign.setLstNonDataSourceOutputBean(lstNonDataSourceOutputBean);
				
				// For source generate form at web layer
				if (BusinessDesignConst.BLOGIC_TYPE_STANDARD.equals(businessDesign.getBlogicType())) {
			
					outputDirWeb = createFileOutputFolder(GenerateSourceCodeUtil.normalizedPackageName(module.getModuleCode()), BusinessLogicGenerate.CONTROLLER, generateSourceCode.getSourcePathWeb());
					
					this.process(data, TEMPLATE_BUSSINESS_INPUT_FORM, outputDirWeb
							+ businessDesign.getBusinessLogicName() 
							+ BusinessLogicGenerate.SUFFIX_INPUT_FORM 
							+ GenerateSourceCodeConst.JAVA_EXTEND);
					
					this.process(data, TEMPLATE_BUSSINESS_OUTPUT_FORM, outputDirWeb
							+ businessDesign.getBusinessLogicName() 
							+ BusinessLogicGenerate.SUFFIX_OUTPUT_FORM 
							+ GenerateSourceCodeConst.JAVA_EXTEND);
				}
				
				//KhangTM:  For source generate resource at web service layer
				if (BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE.equals(businessDesign.getBlogicType())) {
					
					outputDirWeb = createFileOutputFolder(BusinessLogicGenerate.WEB_SERVICE_FOLDER + File.separator + GenerateSourceCodeUtil.normalizedPackageName(module.getModuleCode()), BusinessLogicGenerate.CONTROLLER, generateSourceCode.getSourcePathWeb());
					
					this.process(data, TEMPLATE_WS_BUSSINESS_INPUT_FORM, outputDirWeb
							+ businessDesign.getBusinessLogicName() 
							+ BusinessLogicGenerate.SUFFIX_INPUT_FORM
							+ GenerateSourceCodeConst.JAVA_EXTEND);
					
					this.process(data, TEMPLATE_WS_BUSSINESS_OUTPUT_FORM, outputDirWeb
							+ businessDesign.getBusinessLogicName() 
							+ BusinessLogicGenerate.SUFFIX_OUTPUT_FORM
							+ GenerateSourceCodeConst.JAVA_EXTEND);
				}

			}
			
			// Generate dozer mapper xml in the case online module
			if (DbDomainConst.FunctionType.ONLINE.equals(module.getModuleType())) {
				generateDozerMapping(module, lstBlogic, generateSourceCode);
			}

		} catch (Exception e) {
			GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	private void generateDozerMapping(Module module, List<BusinessDesign> lstBlogic, GenerateSourceCode generateSourceCode) {
		OutputStreamWriter out = null;
		List<BusinessDesign> lstBlogicFillter = new ArrayList<BusinessDesign>();
		for (BusinessDesign businessDesign : lstBlogic) {
			if (BusinessDesignConst.SCREEN_PATTERN_SEARCH.equals(businessDesign.getPatternType())
					&& (BusinessDesignConst.RETURN_TYPE_INITIALSCREEN.equals(businessDesign.getReturnType())
							|| BusinessDesignConst.RETURN_TYPE_SCREEN.equals(businessDesign.getReturnType()))
							&& isExistByteDataType(businessDesign.getLstOutputBean())) {
				lstBlogicFillter.add(businessDesign);
			} else if (BusinessDesignConst.SCREEN_PATTERN_VIEW.equals(businessDesign.getPatternType()) 
					&& BusinessDesignConst.RETURN_TYPE_INITIALSCREEN.equals(businessDesign.getReturnType())
					&& isExistByteDataType(businessDesign.getLstOutputBean())) {
				lstBlogicFillter.add(businessDesign);
			}
		}

		try {
			if (CollectionUtils.isNotEmpty(lstBlogicFillter)) {
				String outputDirDozer = createFileOutputFolder(GenerateSourceCodeUtil.normalizedPackageName(module.getModuleCode()), BusinessLogicGenerate.DOZER_XML, generateSourceCode.getSourcePathWeb());
				
				StringBuilder strMapperDetail = new StringBuilder("");
				for (BusinessDesign businessDesign : lstBlogicFillter) {
					for (OutputBean output : businessDesign.getLstOutputBean()) {
						if (JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(output.getDataType()) || (JavaDataTypeOfBlogic.BYTE_DATATYPE.equals(output.getDataType()) 
								&& output.getParentOutputBeanId() == null  )) {
							strMapperDetail.append(generateMappingSyntaxt(module, businessDesign, output));
						}
					}
				}

				Map<String, Object> data = new HashMap<String, Object>();
				data.put("mapperStr", strMapperDetail.toString());
				
				out = new OutputStreamWriter(
						new FileOutputStream(new File(outputDirDozer
								+ module.getModuleCode() 
								+ BusinessLogicGenerate.SUFFIX_DOZER_MAPPER
								+ GenerateSourceCodeConst.XML_EXTEND)),
						GenerateSourceCodeConst.ENCODE_UTF8);
				this.getTemplate(TEMPLATE_DOZER_MAPPER).process(data, out);
				
				out.flush();
			}
		} catch (Exception e) {
			GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	private String generateMappingSyntaxt(Module module, BusinessDesign businessDesign, OutputBean outputBean) {
		StringBuilder strMapperDetail = new StringBuilder();

		if (!JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(outputBean.getDataType())) {
			strMapperDetail.append("<mapping>").append(NL);

			String pakageOfClassSrc = GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName() + ".domain.service."+ module.getModuleCode()) + "."
						+ GenerateSourceCodeUtil.normalizedClassName(businessDesign.getBusinessLogicCode())+BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN;
			String pakageOfClassDest = GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName() + ".app."+ module.getModuleCode()) + "."
						+ GenerateSourceCodeUtil.normalizedClassName(businessDesign.getBusinessLogicCode())+BusinessLogicGenerate.SUFFIX_OUTPUT_FORM;

			strMapperDetail.append("<class-a>").append(pakageOfClassSrc).append("</class-a>").append(NL);
			strMapperDetail.append("<class-b>").append(pakageOfClassDest).append("</class-b>").append(NL);;
			strMapperDetail.append("<field custom-converter=\"org.terasoluna.qp.app.common.ultils.ImageFileCustomConverter\">");
			strMapperDetail.append("\n\t\t").append("<a>").append(outputBean.getOutputBeanCode()).append("</a>");
			strMapperDetail.append("\n\t\t").append("<b>").append(outputBean.getOutputBeanCode()).append("</b>");
			strMapperDetail.append(NL).append("</field>");

			strMapperDetail.append(NL).append("</mapping>").append(NL);
		} else {
			List<OutputBean> lstOutputDataOfByteIsArray = new ArrayList<OutputBean>();
			for (OutputBean item : businessDesign.getLstOutputBean()) {
				if (DataTypeUtils.equals(outputBean.getOutputBeanId(), item.getParentOutputBeanId()) 
						&& JavaDataTypeOfBlogic.BYTE_DATATYPE.equals(item.getDataType()) 
						&& Boolean.TRUE.equals(item.getArrayFlg())) {
					lstOutputDataOfByteIsArray.add(item);
				}
			}

			if (CollectionUtils.isNotEmpty(lstOutputDataOfByteIsArray)) {
				strMapperDetail.append("<mapping>").append(NL);
				String pakageOfClassSrc = GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName() + ".domain.service."+ module.getModuleCode() + "." 
							+ businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN + "." )
							+ GenerateSourceCodeUtil.normalizedClassName(outputBean.getOutputBeanCode());
				String pakageOfClassDest = GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName() + ".app."+ module.getModuleCode() + "." 
							+ businessDesign.getBusinessLogicCode() + BusinessLogicGenerate.SUFFIX_OUTPUT_FORM + "." )
							+ GenerateSourceCodeUtil.normalizedClassName(outputBean.getOutputBeanCode());

				strMapperDetail.append("<class-a>").append(pakageOfClassSrc).append("</class-a>").append(NL);
				strMapperDetail.append("<class-b>").append(pakageOfClassDest).append("</class-b>").append(NL);;

				for (OutputBean item : lstOutputDataOfByteIsArray) {
					strMapperDetail.append("<field custom-converter=\"org.terasoluna.qp.app.common.ultils.ImageFileCustomConverter\">");
					strMapperDetail.append("\n\t\t").append("<a>").append(item.getOutputBeanCode()).append("</a>");
					strMapperDetail.append("\n\t\t").append("<b>").append(item.getOutputBeanCode()).append("</b>");
					strMapperDetail.append(NL).append("</field>");
				}

				strMapperDetail.append(NL).append("</mapping>").append(NL);
			}
		}

		return strMapperDetail.toString()==null?"":strMapperDetail.toString();
	}

	private boolean isExistByteDataType(List<OutputBean> lstOutputBean) {
		for (OutputBean outputBean : lstOutputBean) 
			if (JavaDataTypeOfBlogic.BYTE_DATATYPE.equals(outputBean.getDataType()) && Boolean.TRUE.equals(outputBean.getArrayFlg()))
				return true;

		return false;
	}

	private List<BusinessDesign> preparingDataGenerateSourceBean(List<BusinessDesign> lstBlogic) {

		List<InputBean> inputBeanLst = generateSourceCodeRepository.findAllInputBeanByBusinessIdLst(lstBlogic);
		List<OutputBean> outputBeanLst = generateSourceCodeRepository.findAllOutputBeanByBusinessIdLst(lstBlogic);
		List<ObjectDefinition> objBeanLst = generateSourceCodeRepository.findAllObjDefinedByBusinessIdLst(lstBlogic);

		for (BusinessDesign businessDesign : lstBlogic) {
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
			
			// Setting validate check standard detail
			if (!BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(businessDesign.getBlogicType())) {
				List<ValidationCheckDetail> lstValidationCheckDetails = 
						validationCheckDetailRepository.findValidationCheckDetailsByBusinessLogic(businessDesign.getBusinessLogicId());
				List<MessageParameter> lstMessageParameters = 
						messageParameterRepository.findMessageParameterByBusinessLogic(businessDesign.getBusinessLogicId(), 
								currentLanguageId, project.getProjectId());

				for (ValidationCheckDetail objDetail : lstValidationCheckDetails) {
					List<MessageParameter> lstMessageParameterTemps = new ArrayList<MessageParameter>();
					for (MessageParameter objMessage : lstMessageParameters) {
						if (BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION.equals(objMessage.getTargetType()) 
								&& objMessage.getTargetId().equals(objDetail.getValidationCheckDetailId())) {
							if (Integer.valueOf(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE).equals(objMessage.getParameterType())) {
								objMessage.setParameterCode(objMessage.getParameterValue());
							}

							lstMessageParameterTemps.add(objMessage);
						}
					}
					objDetail.setParameters(lstMessageParameterTemps);
				}

				for (InputBean inputBean : businessDesign.getLstInputBean()) {
					List<ValidationCheckDetail> lstValidationCheckDetailTemps = new ArrayList<ValidationCheckDetail>();
					StringBuilder sb = new StringBuilder("");
					for (ValidationCheckDetail objDetail : lstValidationCheckDetails) {
						if (objDetail.getInputBeanId().equals(inputBean.getInputBeanId())) {
							// Call function get validate string
							objDetail.setDataType(inputBean.getDataType());
							sb.append(getValidateString(objDetail));
							lstValidationCheckDetailTemps.add(objDetail);
						}
					}

					inputBean.setValidateStandar(sb.toString());
					inputBean.setLstValidationCheckDetails(lstValidationCheckDetailTemps);
				}
			}
		}
		
		return lstBlogic;
	}

	private String getValidateString(ValidationCheckDetail objDetail) {
		StringBuilder sb = new StringBuilder(NL);

		// Setting string validate
		switch (objDetail.getValidationType()) {
		
			case ValidateType.NOT_NULL:
				sb.append(mapAnnotation.get(ValidateType.NOT_NULL)).append(OPEN_BRACKET).append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.NULL:
				sb.append(mapAnnotation.get(ValidateType.NULL)).append(OPEN_BRACKET).append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.PATTERN:
				String regrex = MessageFormat.format("regexp = \"{0}\"", getParamerCodeByItemSequenNo(objDetail.getParameters(), 1));
				sb.append(mapAnnotation.get(ValidateType.PATTERN)).append(OPEN_BRACKET).append(regrex).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.MIN:
					String min = MessageFormat.format("value = \"{0}\"", getParamerCodeByItemSequenNo(objDetail.getParameters(), 1));
						sb.append(mapAnnotation.get(ValidateType.MIN)).append(OPEN_BRACKET).append(min).append(", ").append(MSG_PARAM)
						.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.MAX:
					String max = MessageFormat.format("value = \"{0}\"", getParamerCodeByItemSequenNo(objDetail.getParameters(), 1));
					sb.append(mapAnnotation.get(ValidateType.MAX)).append(OPEN_BRACKET).append(max).append(", ").append(MSG_PARAM)
						.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.DECIMAL_MIN:
				String minDcm = MessageFormat.format("value = \"{0}\"", getParamerCodeByItemSequenNo(objDetail.getParameters(), 1));
				sb.append(mapAnnotation.get(ValidateType.DECIMAL_MIN)).append(OPEN_BRACKET).append(minDcm).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.DECIMAL_MAX:
				String maxDcm = MessageFormat.format("value = \"{0}\"", getParamerCodeByItemSequenNo(objDetail.getParameters(), 1));
				sb.append(mapAnnotation.get(ValidateType.DECIMAL_MAX)).append(OPEN_BRACKET).append(maxDcm).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.SIZE:
				// size must be between {min} and {max}
				String size = MessageFormat.format("min={0}, max={1}", 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1), 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 2));
				sb.append(mapAnnotation.get(ValidateType.SIZE)).append(OPEN_BRACKET).append(size).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.DIGITS:
				// integer = {0}, fraction = {1}
				String digist = MessageFormat.format("integer = {0}, fraction = {1}", 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1), 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 2));
				sb.append(mapAnnotation.get(ValidateType.DIGITS)).append(OPEN_BRACKET).append(digist).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.ASSRT_TRUE:
				sb.append(mapAnnotation.get(ValidateType.ASSRT_TRUE)).append(OPEN_BRACKET).append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.ASSRT_FALSE:
				sb.append(mapAnnotation.get(ValidateType.ASSRT_FALSE)).append(OPEN_BRACKET).append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.FUTURE:
				sb.append(mapAnnotation.get(ValidateType.FUTURE)).append(OPEN_BRACKET).append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.PAST:
				sb.append(mapAnnotation.get(ValidateType.PAST)).append(OPEN_BRACKET).append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.CREDITCARD_NUMBER:
				sb.append(mapAnnotation.get(ValidateType.CREDITCARD_NUMBER)).append(OPEN_BRACKET).append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.EMAIL:
				sb.append(mapAnnotation.get(ValidateType.EMAIL)).append(OPEN_BRACKET).append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.URL:
				sb.append(mapAnnotation.get(ValidateType.URL)).append(OPEN_BRACKET).append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.NOT_BLANK:
				sb.append(mapAnnotation.get(ValidateType.NOT_BLANK)).append(OPEN_BRACKET).append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.NOT_EMPTY:
				sb.append(mapAnnotation.get(ValidateType.NOT_EMPTY)).append(OPEN_BRACKET).append(MSG_PARAM)
						.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.ALPHABET:
				sb.append(mapAnnotation.get(ValidateType.ALPHABET)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.ALPHA_NUMERIC:
				sb.append(mapAnnotation.get(ValidateType.ALPHA_NUMERIC)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.BINARY:
				sb.append(mapAnnotation.get(ValidateType.BINARY)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.CURRENCY:
				sb.append(mapAnnotation.get(ValidateType.CURRENCY)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.DECIMAL:
				sb.append(mapAnnotation.get(ValidateType.DECIMAL)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
				
				// Lock double data type
			case ValidateType.DOUBLE:
				sb.append(mapAnnotation.get(ValidateType.DOUBLE)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
				// end double data type
			case ValidateType.EM_CHARACTER:
				sb.append(mapAnnotation.get(ValidateType.EM_CHARACTER)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.EN_CHARACTER:
				sb.append(mapAnnotation.get(ValidateType.EN_CHARACTER)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.FLOAT:
				sb.append(mapAnnotation.get(ValidateType.FLOAT)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.HIRAGANA:
				sb.append(mapAnnotation.get(ValidateType.HIRAGANA)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.INTEGER:
				sb.append(mapAnnotation.get(ValidateType.INTEGER)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.KANJI:
				sb.append(mapAnnotation.get(ValidateType.KANJI)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.KATAKANA:
				sb.append(mapAnnotation.get(ValidateType.KATAKANA)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.LONG:
				sb.append(mapAnnotation.get(ValidateType.LONG)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.PHONE:
				sb.append(mapAnnotation.get(ValidateType.PHONE)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.POSTCODE:
				sb.append(mapAnnotation.get(ValidateType.POSTCODE)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.SPACE:
				sb.append(mapAnnotation.get(ValidateType.SPACE)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.SYMBOL:
				sb.append(mapAnnotation.get(ValidateType.SYMBOL)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;		
			case ValidateType.TIME:
				String qpTime = MessageFormat.format("patternFomrat=\"{0}\"",getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.TIME)).append(OPEN_BRACKET).append(qpTime).append(", ").append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;		
			case ValidateType.YEAR:
				sb.append(mapAnnotation.get(ValidateType.YEAR)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;		
			case ValidateType.ZENKAKU_ALPHABET:
				sb.append(mapAnnotation.get(ValidateType.ZENKAKU_ALPHABET)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;		
			case ValidateType.ZENKAKU_KATAKANA:
				sb.append(mapAnnotation.get(ValidateType.ZENKAKU_KATAKANA)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.ZENKAKU_NUMERIC:
				sb.append(mapAnnotation.get(ValidateType.ZENKAKU_NUMERIC)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;		
			case ValidateType.ZENKAKU_SYMBOL:
				sb.append(mapAnnotation.get(ValidateType.ZENKAKU_SYMBOL)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.DAY:
				sb.append(mapAnnotation.get(ValidateType.DAY)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.HOUR:
				sb.append(mapAnnotation.get(ValidateType.HOUR)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.MINUTE:
				sb.append(mapAnnotation.get(ValidateType.MINUTE)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.MONTH:
				sb.append(mapAnnotation.get(ValidateType.MONTH)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.SECOND:
				sb.append(mapAnnotation.get(ValidateType.SECOND)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.WEEK:
				sb.append(mapAnnotation.get(ValidateType.WEEK)).append(OPEN_BRACKET).append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.DATE_TIME:
				String qpDatetime = MessageFormat.format("patternFomrat=\"{0}\"",getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.DATE_TIME)).append(OPEN_BRACKET).append(qpDatetime).append(", ").append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.DATE:
				String qpDate = MessageFormat.format("patternFomrat=\"{0}\"",getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.DATE)).append(OPEN_BRACKET).append(qpDate).append(", ").append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.QP_SIZE:
				// QpSize must be between {min} and {max}
				String qpSize = MessageFormat.format("min={0}, max={1}", 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1), 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 2));
				sb.append(mapAnnotation.get(ValidateType.QP_SIZE)).append(OPEN_BRACKET).append(qpSize).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
				
			case ValidateType.TIME_MIN:
				String timemin = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"", getParamerCodeByItemSequenNo(objDetail.getParameters(), 1),getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.TIME_MIN)).append(OPEN_BRACKET).append(timemin).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.DATE_MIN:
				String datemin = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"", getParamerCodeByItemSequenNo(objDetail.getParameters(), 1), getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.DATE_MIN)).append(OPEN_BRACKET).append(datemin).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.DATE_TIME_MIN:
				String datetimemin = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"", getParamerCodeByItemSequenNo(objDetail.getParameters(), 1), getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.DATE_TIME_MIN)).append(OPEN_BRACKET).append(datetimemin).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.TIMESTAMP_MIN:
				String timestampmin = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"", getParamerCodeByItemSequenNo(objDetail.getParameters(), 1), getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.TIMESTAMP_MIN)).append(OPEN_BRACKET).append(timestampmin).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;		
				
			case ValidateType.TIME_MAX:
				String timemax = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"",
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.TIME_MAX)).append(OPEN_BRACKET).append(timemax).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.DATE_MAX:
				String datemax = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"",
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.DATE_MAX)).append(OPEN_BRACKET).append(datemax).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.DATE_TIME_MAX:
				String datetimemax = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"",
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.DATE_TIME_MAX)).append(OPEN_BRACKET).append(datetimemax).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.TIMESTAMP_MAX:
				String timestampmax = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"", 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.TIMESTAMP_MAX)).append(OPEN_BRACKET).append(timestampmax).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
				
			case ValidateType.TIME_RANGE:
				String timerange = MessageFormat.format("min=\"{0}\", max=\"{1}\", patternFomrat=\"{2}\"", 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1), 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 2),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.TIME_RANGE)).append(OPEN_BRACKET).append(timerange).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.DATE_RANGE:
				String daterange = MessageFormat.format("min=\"{0}\", max=\"{1}\", patternFomrat=\"{2}\"", 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1), 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 2),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.DATE_RANGE)).append(OPEN_BRACKET).append(daterange).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.DATE_TIME_RANGE:
				String datetimerange = MessageFormat.format("min=\"{0}\", max=\"{1}\", patternFomrat=\"{2}\"", 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1), 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 2),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.DATE_TIME_RANGE)).append(OPEN_BRACKET).append(datetimerange).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;	
			case ValidateType.TIMESTAMP_RANGE:
				String timestamprange = MessageFormat.format("min=\"{0}\", max=\"{1}\", patternFomrat=\"{2}\"", 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1), 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 2),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.TIMESTAMP_RANGE)).append(OPEN_BRACKET).append(timestamprange).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.TIMESTAMP:
				String qpTimestamp = MessageFormat.format("patternFomrat=\"{0}\"",
						getPatternFormatByListMessage(objDetail.getParameters()),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.TIMESTAMP)).append(OPEN_BRACKET).append(qpTimestamp).append(", ").append(MSG_PARAM)
				.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.QP_GREATER_THAN:
				String qpMin = MessageFormat.format("value = \"{0}\"", getParamerCodeByItemSequenNo(objDetail.getParameters(), 1));
				sb.append(mapAnnotation.get(ValidateType.QP_GREATER_THAN)).append(OPEN_BRACKET).append(qpMin).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.QP_LESS_THAN:
				String qpMax = MessageFormat.format("value = \"{0}\"", getParamerCodeByItemSequenNo(objDetail.getParameters(), 1));
				sb.append(mapAnnotation.get(ValidateType.QP_LESS_THAN)).append(OPEN_BRACKET).append(qpMax).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.QP_EQUAL:
				String qpEqual = MessageFormat.format("value = \"{0}\"", getParamerCodeByItemSequenNo(objDetail.getParameters(), 1));
				sb.append(mapAnnotation.get(ValidateType.QP_EQUAL)).append(OPEN_BRACKET).append(qpEqual).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.QP_NOT_EQUAL:
				String qpNotEqual = MessageFormat.format("value = \"{0}\"", getParamerCodeByItemSequenNo(objDetail.getParameters(), 1));
				sb.append(mapAnnotation.get(ValidateType.QP_NOT_EQUAL)).append(OPEN_BRACKET).append(qpNotEqual).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.DATE_TIME_EQUAL:
				String datetimeEqual = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"",
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.DATE_TIME_EQUAL)).append(OPEN_BRACKET).append(datetimeEqual).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.DATE_TIME_NOT_EQUAL:
				String datetimeNotEqual = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"",
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.DATE_TIME_NOT_EQUAL)).append(OPEN_BRACKET).append(datetimeNotEqual).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.DATE_EQUAL:
				String dateEqual = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"",
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.DATE_EQUAL)).append(OPEN_BRACKET).append(dateEqual).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.DATE_NOT_EQUAL:
				String dateNotEqual = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"", 
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.DATE_NOT_EQUAL)).append(OPEN_BRACKET).append(dateNotEqual).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.TIME_EQUAL:
				String timeEqual = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"",
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.TIME_EQUAL)).append(OPEN_BRACKET).append(timeEqual).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.TIME_NOT_EQUAL:
				String timeNotEqual = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"",
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.TIME_NOT_EQUAL)).append(OPEN_BRACKET).append(timeNotEqual).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.TIMESTAMP_EQUAL:
				String timeStampEqual = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"",
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.TIMESTAMP_EQUAL)).append(OPEN_BRACKET).append(timeStampEqual).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.TIMESTAMP_NOT_EQUAL:
				String timeStampNotEqual = MessageFormat.format("value = \"{0}\", patternFomrat=\"{1}\"",
						getParamerCodeByItemSequenNo(objDetail.getParameters(), 1),
						getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.TIMESTAMP_NOT_EQUAL)).append(OPEN_BRACKET).append(timeStampNotEqual).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.FUTURE_DATE:
				String qpFutureDate = MessageFormat.format("patternFomrat=\"{0}\"",getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.FUTURE_DATE)).append(OPEN_BRACKET).append(qpFutureDate).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.FUTURE_DATETIME:
				String qpFutureDateTime = MessageFormat.format("patternFomrat=\"{0}\"",getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.FUTURE_DATETIME)).append(OPEN_BRACKET).append(qpFutureDateTime).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.FUTURE_TIMESTAMP:
				String qpFutureTimestamp = MessageFormat.format("patternFomrat=\"{0}\"",getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.FUTURE_TIMESTAMP)).append(OPEN_BRACKET).append(qpFutureTimestamp).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;		
			case ValidateType.PAST_DATE:
				String qpPastDate = MessageFormat.format("patternFomrat=\"{0}\"",getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.PAST_DATE)).append(OPEN_BRACKET).append(qpPastDate).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.PAST_DATETIME:
				String qpPastDateTime = MessageFormat.format("patternFomrat=\"{0}\"",getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.PAST_DATETIME)).append(OPEN_BRACKET).append(qpPastDateTime).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;
			case ValidateType.PAST_TIMESTAMP:
				String qpPastTimestamp = MessageFormat.format("patternFomrat=\"{0}\"",getPatternFormatByListMessage(objDetail.getParameters()));
				sb.append(mapAnnotation.get(ValidateType.PAST_TIMESTAMP)).append(OPEN_BRACKET).append(qpPastTimestamp).append(", ").append(MSG_PARAM)
					.append(getContentFrmMsgParamLst(objDetail.getParameters())).append(CLOSEST_BRACKET);
				break;		
		}

		return sb.toString()==null?"":sb.toString();
	}
	
	private String getParamerCodeByItemSequenNo(List<MessageParameter> list, int idx) {
		String valueFrmParam = "";
		if (CollectionUtils.isNotEmpty(list)) {
			for (MessageParameter item : list) {
				if (DataTypeUtils.equals(item.getItemSequenceNo(), idx)) {
					valueFrmParam = (PARAMETER_TYPE_MSG_CODE.equals(item.getParameterType()))?item.getParameterCode():item.getParameterValue();
					break;
				}
			}
		}

		return valueFrmParam;
	}
	
	private String getPatternFormatByListMessage(List<MessageParameter> list) {
		String patternFormat = "";
		if (CollectionUtils.isEmpty(list)) {
			return patternFormat;
		}
		for (MessageParameter item : list) {
			if(item != null && StringUtils.isNotBlank(item.getPatternFormat())){
				return item.getPatternFormat();
			}
		}
		return patternFormat;
	}
	
	/**
	 * Get content of parameter
	 * 
	 * @param list
	 * @return
	 */
	private String getContentFrmMsgParamLst(List<MessageParameter> list) {
		StringBuilder contentMsgParam = new StringBuilder();
		
		if (CollectionUtils.isNotEmpty(list)) {
			for (MessageParameter msgParam : list) {
				if (PARAMETER_TYPE_MSG_CODE.equals(msgParam.getParameterType())) {
					contentMsgParam = contentMsgParam.toString().length()==0?contentMsgParam:contentMsgParam.append(" + ").append("\";\"").append(" + ");
					contentMsgParam.append("\"").append(msgParam.getParameterCode()).append("\"");
				} else {
					contentMsgParam = contentMsgParam.toString().length()==0?contentMsgParam:contentMsgParam.append(" + ").append("\";\"").append(" + ");
					contentMsgParam.append("\"").append(msgParam.getParameterValue()).append("\"");
				}
			}
		}

		return contentMsgParam.toString()==null?"":contentMsgParam.toString();
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

	private void generateObjectOutputLst(Module module, BusinessDesign businessDesign, GenerateSourceCode generateSourceCode, List<OutputBean> objOutputBeanLst) {
		
		if(CollectionUtils.isNotEmpty(objOutputBeanLst)) {
			for (OutputBean element : objOutputBeanLst) {
				if (CollectionUtils.isNotEmpty(element.getObjectList())) {
					generateObjectOutputLst(module, businessDesign, generateSourceCode, element.getObjectList());
				}
				// Generate of nested object 
				generateObjectOutput(module, businessDesign, generateSourceCode, element);
			}
		}
	}

	private void generateObjectObjectDefinitionLst(Module module, BusinessDesign businessDesign, String pathSourceDomain, List<ObjectDefinition> objObjDefinitionLst) {
		if(CollectionUtils.isNotEmpty(objObjDefinitionLst)) {
			for (ObjectDefinition element : objObjDefinitionLst) {
				if (CollectionUtils.isNotEmpty(element.getObjectList())) {
					generateObjectObjectDefinitionLst(module, businessDesign, pathSourceDomain, element.getObjectList());
				}
				// Generate of nested object
				generateObjectObjectDefinition(module, businessDesign, pathSourceDomain, element);
			}
		}
	}

	private void generateObjectInput(Module module, BusinessDesign businessDesign, GenerateSourceCode generateSourceCode, InputBean objInputBean) {

		OutputStreamWriter out = null;
		try {
			String outputDirDomain = createFileOutputFolder(
					GenerateSourceCodeUtil.normalizedPackageName(module.getModuleCode())+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_INPUT_BEAN,
					BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());

			String outputDirWeb = null;
			String tempStrObjectInputWeb = null;
			
			//KhangTM : init outputDirWeb and template for business standard or  Web service
			if(BusinessDesignConst.BLOGIC_TYPE_STANDARD.equals(businessDesign.getBlogicType())){
				outputDirWeb = createFileOutputFolder(
						GenerateSourceCodeUtil.normalizedPackageName(module.getModuleCode())+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_INPUT_FORM,
						BusinessLogicGenerate.CONTROLLER, generateSourceCode.getSourcePathWeb());
				tempStrObjectInputWeb = TEMPLATE_BUSSINESS_OBJ_OF_INPUT_FORM;
			}
			
			if(BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE.equals(businessDesign.getBlogicType())){
				outputDirWeb = createFileOutputFolder(BusinessLogicGenerate.WEB_SERVICE_FOLDER +File.separator+
						GenerateSourceCodeUtil.normalizedPackageName(module.getModuleCode())+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_INPUT_FORM,
						BusinessLogicGenerate.CONTROLLER, generateSourceCode.getSourcePathWeb());
				tempStrObjectInputWeb = TEMPLATE_WS_BUSSINESS_OBJ_OF_INPUT_FORM;
			}
			
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package", project.getPackageName());
			data.put("businessLogic", businessDesign);
			data.put("inputBean", objInputBean);
			data.put("module", module);
			
			this.process(data, TEMPLATE_BUSSINESS_OBJ_OF_INPUT_BEAN, outputDirDomain
							+ WordUtils.capitalize(objInputBean.getInputBeanCode())
							+ GenerateSourceCodeConst.JAVA_EXTEND);
			
			// In the case of business common then not generate source code of Form
			if (BusinessDesignConst.BLOGIC_TYPE_STANDARD.equals(businessDesign.getBlogicType()) ||
					BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE.equals(businessDesign.getBlogicType())	) {
				
				if(tempStrObjectInputWeb != null) {
					this.process(data, tempStrObjectInputWeb, outputDirWeb
							+ WordUtils.capitalize(objInputBean.getInputBeanCode())
							+ GenerateSourceCodeConst.JAVA_EXTEND);
				}
			}
			
		} catch (Exception e) {
			GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	private void generateObjectOutput(Module module, BusinessDesign businessDesign, GenerateSourceCode generateSourceCode, OutputBean objOutputBean) {

		OutputStreamWriter out = null;
		try {
			String outputDirDomain = createFileOutputFolder(
					module.getModuleCode()+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN,
					BusinessLogicGenerate.SERVICE, generateSourceCode.getSourcePathDomain());

			String outputDirWeb = null;
			String tempStrObjectOutputWeb = null;

			//KhangTM : init outputDirWeb and template for business standard or  Web service
			if(BusinessDesignConst.BLOGIC_TYPE_STANDARD.equals(businessDesign.getBlogicType())){
				outputDirWeb = createFileOutputFolder(
						GenerateSourceCodeUtil.normalizedPackageName(module.getModuleCode())+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_OUTPUT_FORM,
						BusinessLogicGenerate.CONTROLLER, generateSourceCode.getSourcePathWeb());
				tempStrObjectOutputWeb = TEMPLATE_BUSSINESS_OBJ_OF_OUTPUT_FORM;
			}
			
			if(BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE.equals(businessDesign.getBlogicType())){
				outputDirWeb = createFileOutputFolder(BusinessLogicGenerate.WEB_SERVICE_FOLDER +File.separator+
						GenerateSourceCodeUtil.normalizedPackageName(module.getModuleCode())+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_OUTPUT_FORM,
						BusinessLogicGenerate.CONTROLLER, generateSourceCode.getSourcePathWeb());
				tempStrObjectOutputWeb = TEMPLATE_WS_BUSSINESS_OBJ_OF_OUTPUT_FORM;
			}

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package", project.getPackageName());
			data.put("businessLogic", businessDesign);
			data.put("outputBean", objOutputBean);
			data.put("module", module);
			
			this.process(data, TEMPLATE_BUSSINESS_OBJ_OF_OUTPUT_BEAN, outputDirDomain
					+ WordUtils.capitalize(objOutputBean.getOutputBeanCode())
					+ GenerateSourceCodeConst.JAVA_EXTEND);
			
			// In the case of business common then not generate source code of Form
			if (BusinessDesignConst.BLOGIC_TYPE_STANDARD.equals(businessDesign.getBlogicType()) ||
					BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE.equals(businessDesign.getBlogicType())) {
				
				if(tempStrObjectOutputWeb != null) {
					this.process(data, tempStrObjectOutputWeb, outputDirWeb
							+ WordUtils.capitalize(objOutputBean.getOutputBeanCode())
							+ GenerateSourceCodeConst.JAVA_EXTEND);
				}
			}

		} catch (Exception e) {
			GenerateSourceCodeUtil.writeLog(generateSourceCode.getLogFile(), e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	private void generateObjectObjectDefinition(Module module, BusinessDesign businessDesign, String pathSourceDomain, ObjectDefinition objectDefinition) {

		OutputStreamWriter out = null;
		try {
			String outputDir = createFileOutputFolder(
					GenerateSourceCodeUtil.normalizedPackageName(module.getModuleCode())+File.separator+businessDesign.getBusinessLogicCode()+BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION,
					BusinessLogicGenerate.SERVICE,
					pathSourceDomain);

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
			data.put("businessLogic", businessDesign);
			data.put("objectdefinition", objectDefinition);
			data.put("module", module);
			
			this.process(data, TEMPLATE_BUSSINESS_OBJ_OF_OBJ_DEFINITION, outputDir
							+ WordUtils.capitalize(objectDefinition.getObjectDefinitionCode())
							+ GenerateSourceCodeConst.JAVA_EXTEND);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
	
	private void generateSourceWebLayer(Module module, String pathSourceWeb) {
		generateSourceController(module, pathSourceWeb);
		//KhangTM : generate web service controller
		generateSourceWebServiceController(module, pathSourceWeb);
	}

	private void generateSourceController(Module module, String pathSourceWeb) {
		OutputStreamWriter out = null;
		List<BusinessDesign> lstBlogic = new ArrayList<>();
		HashMap<String, String> lstModel = new HashMap<>();
		
		for (BusinessDesign object : module.getListBusinessDesign()) {
			if (BusinessDesignConst.RETURN_TYPE_INITIALSCREEN.equals(object.getReturnType()) 
					|| BusinessDesignConst.RETURN_TYPE_SCREEN.equals(object.getReturnType())
					|| BusinessDesignConst.RETURN_TYPE_DOWNLOAD.equals(object.getReturnType())
					|| BusinessDesignConst.RETURN_TYPE_SCREEN_EVENT.equals(object.getReturnType())) {

				Map<String, List<?>> mAllParentAndSeflByLevelOfInOutObj = detailServiceImpHandler.getAllParentAndSeflByLevelOfInOutObj(0, object.getLstInputBean(), object.getLstObjectDefinition(), object.getLstOutputBean());
				
				for (OutputBean item : object.getLstOutputBean()) {
					if (item.getScopeType() == 1 && !StringUtils.isEmpty(item.getSessionManagementCode())) {
						StringBuilder sb = new StringBuilder();
						String getObject = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllParentAndSeflByLevelOfInOutObj, true, item.getOutputBeanId(), 2, "OutputBean", null);
						sb.append(NL).append("\t");
						sb.append(MessageFormat.format("SessionUtils.set(\"{0}\", {1}{2});", item.getSessionManagementCode(), object.getBusinessLogicCode(), getObject));
						sb.append(NL);
						object.getStrSessionOutput().append(sb.toString());
					}
				}
				
				for (InputBean item : object.getLstInputBean()) {
					if (item.getScopeType() == 1 && !StringUtils.isEmpty(item.getSessionManagementCode())) {
						StringBuilder sb = new StringBuilder();
						String setObject = detailServiceImpHandler.getterAndSetterOfParameter(0, mAllParentAndSeflByLevelOfInOutObj, false, item.getInputBeanId(), 0, "InputBean", null);
						String getSession = MessageFormat.format("CustomizeSessionUtils.get{0}()", WordUtils.capitalize(item.getSessionManagementCode()));
						getSession = BusinessLogicGenerateHelper.getContentByCastDataType(item.getDataType(), item.getDataTypeSession(), getSession);
						sb.append(NL).append("\t");
						
						if (item.getSessionManagementType() == SessionManagementMessageConst.TYPE_CUSTOMIZE) {
							sb.append(MessageFormat.format("{0}{1}({2});", object.getBusinessLogicCode(), setObject, getSession));
						} else {
							sb.append(MessageFormat.format("{0}{1}(({3}) SessionUtils.get(\"{2}\"));", object.getBusinessLogicCode(), setObject, item.getSessionManagementCode(), GenerateSourceCodeUtil.normalizedClassName(item.getSessionManagementTableDesignCode())));
							lstModel.put(GenerateSourceCodeUtil.normalizedClassName(item.getSessionManagementTableDesignCode()), GenerateSourceCodeUtil.normalizedClassName(item.getSessionManagementTableDesignCode()));
						}
						
						sb.append(NL);
						object.getStrSessionInput().append(sb.toString());
					}
				}
				
				lstBlogic.add(object);
			};
		}
		
		for (Map.Entry<String, String> entry : lstModel.entrySet()) {
			strSessionImport.append(MessageFormat.format("import {0}.domain.model.{1};", project.getPackageName(), entry.getKey()));
			strSessionImport.append("\n");
		}

		if (CollectionUtils.isNotEmpty(lstBlogic)) {
			
			List<ScreenDesign> lstScreen = screenDesignRepository.findAllScreenDessingByLstBusiness(lstBlogic);
			
			String sessionProcessSearch = "";
			boolean isFirst = true;
			for (BusinessDesign item : lstBlogic) {
				// Process Search
				if(!item.getBlogicType().equals(BusinessDesignConst.BLOGIC_TYPE_STANDARD) && !item.getBlogicType().equals(BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE))
					continue;
				
				if(BusinessDesignConst.SCREEN_PATTERN_SEARCH.equals(item.getPatternType()) && item.getReturnType() == 1) {
					if(isFirst) {
						sessionProcessSearch = GenerateSourceCodeUtil.normalizedClassName(item.getBusinessLogicCode()) + "InputForm.class";
						isFirst = false;
					} else {
						sessionProcessSearch = sessionProcessSearch+ "," + GenerateSourceCodeUtil.normalizedClassName(item.getBusinessLogicCode()) + "InputForm.class";
					}
					sessionProcessSearch = sessionProcessSearch+ "," + GenerateSourceCodeUtil.normalizedClassName(item.getBusinessLogicCode()) + "OutputForm.class";
				} else {
					if(isFirst) {
						sessionProcessSearch = GenerateSourceCodeUtil.normalizedClassName(item.getBusinessLogicCode()) + "OutputForm.class";
						isFirst = false;
					} else {
						sessionProcessSearch = sessionProcessSearch+ "," + GenerateSourceCodeUtil.normalizedClassName(item.getBusinessLogicCode()) + "OutputForm.class";
					}
				}
			}

			try {
				String outputDir = createFileOutputFolder(GenerateSourceCodeUtil.normalizedPackageName(module.getModuleCode()), BusinessLogicGenerate.CONTROLLER, pathSourceWeb);
				String javaFileName = WordUtils.capitalize(module.getModuleCode());
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("package", project.getPackageName());
				data.put("businessLogicLst", lstBlogic);
				data.put("module", module);
				data.put("strSessionImport", strSessionImport);
				data.put("sessionProcessSearch", sessionProcessSearch);
				data.put("lstScreen", lstScreen);
				
				this.process(data, TEMPLATE_BUSSINESS_CONTROLLER, outputDir 
						+ javaFileName
						+ BusinessLogicGenerate.SUFFIX_CONTROLLER
						+ GenerateSourceCodeConst.JAVA_EXTEND);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(out);
			}
		}
	}
	
	
	private void generateSourceService(Module module, String pathSourceDomain){
		
		boolean isExistSql = CollectionUtils.isNotEmpty(sqlDesignRepository.getAllSqlDesignByModuleId(module.getModuleId(), null));
		boolean isExistSqlCommon = CollectionUtils.isNotEmpty(sqlDesignRepository.getAllSqlDesignByProjectId(project.getProjectId(), null));
		List<BusinessDesign> businessDesigns = generateSourceCodeRepository.findAllCommonBusinessLogicByProject(project.getProjectId());
		boolean isExistDecision = CollectionUtils.isNotEmpty(decisionTableRepository.findAllDecisionByProjectId(project.getProjectId()));
		boolean isExistBlogicCommon = CollectionUtils.isNotEmpty(businessDesigns);
		boolean isExistBlogicCommonCustomize = false;
		boolean isExistAdvancedNormal = CollectionUtils.isNotEmpty(advanceComponentRepository.findAdvanceComponentByModule(module.getModuleId()));
		List<BusinessDesign> lstBlogicCustomize = generateSourceCodeRepository.findAllCommonBusinessLogicCustomizeByProject(project.getProjectId());
		
		for (BusinessDesign businessDesign : businessDesigns) {
		    if (Boolean.TRUE.equals(businessDesign.getCustomizeFlg())) {
		        isExistBlogicCommonCustomize = true;
		    }
		}
		
		OutputStreamWriter out = null;
		try {
			String outputDir = createFileOutputFolder(GenerateSourceCodeUtil.normalizedPackageName(module.getModuleCode()), BusinessLogicGenerate.SERVICE, pathSourceDomain);
			String javaFileName = WordUtils.capitalize(module.getModuleCode());
			module.setPathSourceDomain(pathSourceDomain);
			
			// Generate source service contend
			detailServiceImpHandler.generateServiceImpDetail(module, project, currentLanguageId);
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
			data.put("businessLogicLst", module.getListBusinessDesign());
			data.put("module", module);
			data.put("isExistSql", isExistSql);
			data.put("isExistBlogicCommon", isExistBlogicCommon);
            data.put("isExistBlogicCommonCustomize", isExistBlogicCommonCustomize);
            data.put("isExistDecision", isExistDecision);
            data.put("isExistAdvancedNormal", isExistAdvancedNormal);
            data.put("lstBlogicCustomize", lstBlogicCustomize);
            data.put("isExistSqlCommon", isExistSqlCommon);
			
			this.process(data, TEMPLATE_BUSSINESS_SERVICE, outputDir
					+ javaFileName
					+ BusinessLogicGenerate.SUFFIX_SERVICE
					+ GenerateSourceCodeConst.JAVA_EXTEND);
			this.process(data, TEMPLATE_BUSSINESS_SERVICE_IMPL, outputDir
					+ javaFileName
					+ BusinessLogicGenerate.SUFFIX_SERVICE_IMPL
					+ GenerateSourceCodeConst.JAVA_EXTEND);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
	
	//KhangTM : generate web service Controller
	private void generateSourceWebServiceController(Module module, String pathSourceWeb) {
		OutputStreamWriter out = null;
		List<BusinessDesign> lstBlogic = new ArrayList<>();
		for (BusinessDesign object : module.getListBusinessDesign()) {
			if (object.getReturnType().equals(4) || object.getReturnType().equals(5)) {
				object.setHttpStatusValue(CL_HTTP_STATUS_CODE.asMap().get(String.valueOf(object.getHttpStatus())));
				lstBlogic.add(object);
			};
		}
		
		if (CollectionUtils.isNotEmpty(lstBlogic)) {
			try {
				String outputDir = createFileOutputFolder(BusinessLogicGenerate.WEB_SERVICE_FOLDER +File.separator+GenerateSourceCodeUtil.normalizedPackageName(module.getModuleCode()), BusinessLogicGenerate.CONTROLLER, pathSourceWeb);
				String javaFileName = WordUtils.capitalize(module.getModuleCode());
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("package", GenerateSourceCodeUtil.normalizedPackageName(project.getPackageName()));
				data.put("businessLogicLst", lstBlogic);
				data.put("module", module);
				
				this.process(data, TEMPLATE_WS_BUSSINESS_CONTROLLER, outputDir 
								+ javaFileName
								+ BusinessLogicGenerate.SUFFIX_WS_CONTROLLER
								+ GenerateSourceCodeConst.JAVA_EXTEND);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(out);
			}
		}
	}
	
	public String createFileOutputFolder(String moduleName, int sourceCodeType, String pathRoot) {
		StringBuilder pathOutput = new StringBuilder();
		switch (sourceCodeType) {
		// is business logic
		case GenerateSourceCodeConst.BusinessLogicGenerate.CONTROLLER:
			pathOutput.append("src").append(File.separator).append("main").append(File.separator)
					  .append("java").append(File.separator).append(pathPackage.toString())
					  .append("app").append(File.separator).append(moduleName);
			break;
		case GenerateSourceCodeConst.BusinessLogicGenerate.SERVICE:
			pathOutput.append("src").append(File.separator).append("main").append(File.separator)
					  .append("java").append(File.separator).append(pathPackage.toString())
					  .append(File.separator).append("domain").append(File.separator).append("service")
					  .append(File.separator).append(moduleName);
			break;
		case GenerateSourceCodeConst.BusinessLogicGenerate.MODEL:
			pathOutput.append("src").append(File.separator).append("main").append(File.separator)
					  .append("java").append(File.separator).append(pathPackage.toString())
					  .append("domain").append(File.separator).append("model");
			break;
		case GenerateSourceCodeConst.BusinessLogicGenerate.MODEL_WEB:
			pathOutput.append("src").append(File.separator).append("main").append(File.separator)
					  .append("java").append(File.separator).append(pathPackage.toString())
					  .append("app").append(File.separator).append("model");
			break;
		case GenerateSourceCodeConst.BusinessLogicGenerate.DOZER_XML:
			pathOutput.append(GenerateSourceCodeUtil.normalizedPackageName("src"+ File.separator + "main"+ File.separator + "resources" + File.separator)).append("META-INF").append(File.separator).append(GenerateSourceCodeUtil.normalizedPackageName("dozer"+File.separator+pathPackage.toString()+moduleName));
			
			break;
		case GenerateSourceCodeConst.BusinessLogicGenerate.PROPERTIES:
			pathOutput.append(GenerateSourceCodeUtil.normalizedPackageName("src"+ File.separator + "main"+ File.separator + "resources" + File.separator))
				.append("META-INF").append(File.separator).append(GenerateSourceCodeUtil.normalizedPackageName("spring"));
		
			break;
		}

		if(GenerateSourceCodeConst.BusinessLogicGenerate.DOZER_XML == sourceCodeType) {
			return GenerateSourceCodeUtil.createSaveFileDirectory(pathOutput.toString(), pathRoot);
		}
		
		return GenerateSourceCodeUtil.createSaveFileDirectory(GenerateSourceCodeUtil.normalizedPackageName(pathOutput.toString()), pathRoot);
	}
	
	public static List<InputBean> getSingleListInputBean(List<InputBean> inputBeanList) {
		List<InputBean> listInputSingle = new ArrayList<InputBean>();
		
		if(CollectionUtils.isNotEmpty(inputBeanList)) {
			for (InputBean inputBean : inputBeanList) {
				if (inputBean.getParentInputBeanId() == null) listInputSingle.add(inputBean);
			}
		}
		
		return listInputSingle;
	}
	
	public static List<InputBean> getObjectListInputBean(List<InputBean> inputBeanList) {
		List<InputBean> listInputObject = new ArrayList<InputBean>();
		
		if(CollectionUtils.isNotEmpty(inputBeanList)) {
			for (InputBean inputBean : inputBeanList) {
				if (inputBean.getParentInputBeanId() == null && inputBean.getDataType().equals(0)) {
					settingFieldObjectInputBean(inputBean, inputBeanList);
					listInputObject.add(inputBean);
				}
			}
		}
		
		return listInputObject;
	}
	
	public static void settingFieldObjectInputBean(InputBean item, List<InputBean> inputBeanList) {
		List<InputBean> listInputSingle = new ArrayList<InputBean>();
		List<InputBean> listInputObject = new ArrayList<InputBean>();
		
		if(CollectionUtils.isNotEmpty(inputBeanList)) {
			for (InputBean inputBean : inputBeanList) {
				if (item.getInputBeanId().equals(inputBean.getParentInputBeanId())){
					listInputSingle.add(inputBean);
					if (inputBean.getDataType().equals(0)) {
						settingFieldObjectInputBean(inputBean, inputBeanList);
						listInputObject.add(inputBean);
					}
				}
			}
		}
		
		item.setSingleList(listInputSingle);
		item.setObjectList(listInputObject);
	}
	
	public static void findParentDataSourceOutputBean(List<OutputBean> outputBeanList, OutputBean outputBean, List<OutputBean> lstOutputGen) {
		if (outputBean.getParentOutputBeanId() == null) {
			lstOutputGen.add(outputBean);
		} else {
			if(CollectionUtils.isNotEmpty(outputBeanList)) {
				for (OutputBean bean : outputBeanList) {
					if (bean.getOutputBeanId().equals(outputBean.getParentOutputBeanId())) {
						if (bean.getParentOutputBeanId() == null) {
							lstOutputGen.add(bean);
							break;
						} else {
							findParentDataSourceOutputBean(outputBeanList, bean, lstOutputGen);
						}
					}
				}	
			}
		}
	}
	
	public static List<OutputBean> getSingleListOutputBean(List<OutputBean> inputBeanList) {
		List<OutputBean> listOutputSingle = new ArrayList<OutputBean>();
		
		if(CollectionUtils.isNotEmpty(inputBeanList)) {
			for (OutputBean outputBean : inputBeanList) {
				if (outputBean.getParentOutputBeanId() == null) listOutputSingle.add(outputBean);
			}
		}
		
		return listOutputSingle;
	}
	
	public static List<OutputBean> getObjectListOutputBean(List<OutputBean> outputBeanList) {
		List<OutputBean> listInputObject = new ArrayList<OutputBean>();
		
		if(CollectionUtils.isNotEmpty(outputBeanList)) {
			for (OutputBean outputBean : outputBeanList) {
				if (outputBean.getParentOutputBeanId() == null && outputBean.getDataType().equals(0)) {
					settingFieldObjectOutputBean(outputBean, outputBeanList);
					listInputObject.add(outputBean);
				}
			}
		}
		
		return listInputObject;
	}
	
	public static void settingFieldObjectOutputBean(OutputBean item, List<OutputBean> outputBeanList) {
		List<OutputBean> listOutputSingle = new ArrayList<OutputBean>();
		List<OutputBean> listOutputObject = new ArrayList<OutputBean>();
		
		if(CollectionUtils.isNotEmpty(outputBeanList)) {
			for (OutputBean outputBean : outputBeanList) {
				if (item.getOutputBeanId().equals(outputBean.getParentOutputBeanId())) {
					listOutputSingle.add(outputBean);
					if(outputBean.getDataType().equals(0)){
						settingFieldObjectOutputBean(outputBean, outputBeanList);
						listOutputObject.add(outputBean);
					}
				}
			}
		}
		
		item.setSingleList(listOutputSingle);
		item.setObjectList(listOutputObject);
	}
	
	public static List<ObjectDefinition> getSingleListObjectDefinition(List<ObjectDefinition> objList) {
		List<ObjectDefinition> listObjSingle = new ArrayList<ObjectDefinition>();
		
		if(CollectionUtils.isNotEmpty(objList)) {
			for (ObjectDefinition objBean : objList) {
				if (objBean.getParentObjectDefinitionId() == null) listObjSingle.add(objBean);
			}
		}
		
		return listObjSingle;
	}
	
	public static List<ObjectDefinition> getObjectListObjectDefinition(List<ObjectDefinition> outputBeanList) {
		List<ObjectDefinition> listObjDefinitionObject = new ArrayList<ObjectDefinition>();
		
		if(CollectionUtils.isNotEmpty(outputBeanList)) {
			for (ObjectDefinition objBean : outputBeanList) {
				if (objBean.getParentObjectDefinitionId() == null && objBean.getDataType().equals(0)) {
					settingFieldObjectObjectDefinition(objBean, outputBeanList);
					listObjDefinitionObject.add(objBean);
				}
			}
		}
		
		return listObjDefinitionObject;
	}
	
	public static void settingFieldObjectObjectDefinition(ObjectDefinition item, List<ObjectDefinition> outputBeanList) {
		List<ObjectDefinition> listObjDefinitionSingle = new ArrayList<ObjectDefinition>();
		List<ObjectDefinition> listObjDefinitionObject = new ArrayList<ObjectDefinition>();
		
		if(CollectionUtils.isNotEmpty(outputBeanList)) {
			for (ObjectDefinition obj : outputBeanList) {
				if (item.getObjectDefinitionId().equals(obj.getParentObjectDefinitionId())) {
					listObjDefinitionSingle.add(obj);
					if(obj.getDataType().equals(0)) {
						settingFieldObjectObjectDefinition(obj, outputBeanList);
						listObjDefinitionObject.add(obj);
					}
				}
			}
		}
		
		item.setSingleList(listObjDefinitionSingle);
		item.setObjectList(listObjDefinitionObject);
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
		BusinessLogicGenerateHandler.pathPackage = pathPackage;
	}

	private Project project;
	
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
	
	public Long getCurrentLanguageId() {
		return currentLanguageId;
	}

	public void setCurrentLanguageId(Long currentLanguageId) {
		this.currentLanguageId = currentLanguageId;
	}
}
