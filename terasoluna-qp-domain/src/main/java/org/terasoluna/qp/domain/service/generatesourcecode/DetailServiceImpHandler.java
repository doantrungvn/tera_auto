package org.terasoluna.qp.domain.service.generatesourcecode;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.domain.model.AdvanceComponent;
import org.terasoluna.qp.domain.model.AdvanceInputValue;
import org.terasoluna.qp.domain.model.AdvanceOutputValue;
import org.terasoluna.qp.domain.model.AssignComponent;
import org.terasoluna.qp.domain.model.AssignDetail;
import org.terasoluna.qp.domain.model.BDParameterIndex;
import org.terasoluna.qp.domain.model.BusinessCheckComponent;
import org.terasoluna.qp.domain.model.BusinessCheckDetail;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.BusinessDetailContent;
import org.terasoluna.qp.domain.model.ColumnFileFormat;
import org.terasoluna.qp.domain.model.CommonComponent;
import org.terasoluna.qp.domain.model.CommonInputValue;
import org.terasoluna.qp.domain.model.CommonOutputValue;
import org.terasoluna.qp.domain.model.DecisionComponent;
import org.terasoluna.qp.domain.model.DecisionInputValue;
import org.terasoluna.qp.domain.model.DecisionOutputValue;
import org.terasoluna.qp.domain.model.DecisionTableInputBean;
import org.terasoluna.qp.domain.model.DecisionTableOutputBean;
import org.terasoluna.qp.domain.model.DownloadFileComponent;
import org.terasoluna.qp.domain.model.EmailComponent;
import org.terasoluna.qp.domain.model.EmailContent;
import org.terasoluna.qp.domain.model.EmailRecipient;
import org.terasoluna.qp.domain.model.ExceptionComponent;
import org.terasoluna.qp.domain.model.ExceptionDetail;
import org.terasoluna.qp.domain.model.ExecutionComponent;
import org.terasoluna.qp.domain.model.ExecutionInputValue;
import org.terasoluna.qp.domain.model.ExecutionOutputValue;
import org.terasoluna.qp.domain.model.ExportAssignValue;
import org.terasoluna.qp.domain.model.ExportFileComponent;
import org.terasoluna.qp.domain.model.FeedbackComponent;
import org.terasoluna.qp.domain.model.FileFormat;
import org.terasoluna.qp.domain.model.FileOperationComponent;
import org.terasoluna.qp.domain.model.FormulaDetail;
import org.terasoluna.qp.domain.model.FormulaMethodInput;
import org.terasoluna.qp.domain.model.FormulaMethodOutput;
import org.terasoluna.qp.domain.model.FunctionMaster;
import org.terasoluna.qp.domain.model.FunctionMethod;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;
import org.terasoluna.qp.domain.model.IfComponent;
import org.terasoluna.qp.domain.model.IfConditionDetail;
import org.terasoluna.qp.domain.model.ImportAssignValue;
import org.terasoluna.qp.domain.model.ImportFileComponent;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.LogComponent;
import org.terasoluna.qp.domain.model.LoopComponent;
import org.terasoluna.qp.domain.model.MergeFileDetail;
import org.terasoluna.qp.domain.model.MessageParameter;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.NavigatorComponent;
import org.terasoluna.qp.domain.model.NavigatorDetail;
import org.terasoluna.qp.domain.model.NestedLogicComponent;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.SequenceConnector;
import org.terasoluna.qp.domain.model.SequenceLogic;
import org.terasoluna.qp.domain.model.SequenceLogicExt;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.TransactionComponent;
import org.terasoluna.qp.domain.model.UtilityComponent;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.AdvanceComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.AssignComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.BDParameterIndexRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.BusinessCheckComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.CommonComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.DecisionComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.DownloadFileComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.EmailComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ExceptionComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ExecutionComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ExportFileComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.FeedbackComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.FileOperationComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.FormulaDefinitionRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.IfComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.IfConditionDetailRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ImportFileComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.LogComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.LoopComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.MessageParameterRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.NavigationComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.NestedLogicComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.TransactionComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.UtilityComponentRepository;
import org.terasoluna.qp.domain.repository.functionmaster.FunctionMasterRepository;
import org.terasoluna.qp.domain.repository.generatesourcecode.GenerateServiceImpDetailRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignInputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignOutputRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignBuilder;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst.FormulaBuilder;
import org.terasoluna.qp.domain.service.businessdesign.ComponentHelper;
import org.terasoluna.qp.domain.service.generatesourcecode.CommonComponentGenerateHandler.TypeOfDataType;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst.ModuleScope;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst.TypeScope;

@Service
public class DetailServiceImpHandler {

	@Inject
	GenerateServiceImpDetailRepository generateServiceImpDetailRepository;

	@Inject
	BusinessCheckComponentRepository businessCheckComponentRepository;

	@Inject
	SqlDesignInputRepository sqlDesignInputRepository;

	@Inject
	SqlDesignOutputRepository sqlDesignOutputRepository;

	@Inject
	ExecutionComponentRepository executionComponentRepository;

	@Inject
	DecisionComponentRepository decisionComponentRepository;

	@Inject
	DecisionComponentGenerateHandler decisionComponentGenerateHandler;

	@Inject
	FeedbackComponentGenerateHandler feedbackComponentGenerateHandler;

	@Inject
	FeedbackComponentRepository feedbackComponentRepository;

	@Inject
	MessageParameterRepository messageParameterRepository;

	@Inject
	ExecutionGenerationHandler executionGenerationHandler;

	@Inject
	AssignComponentRepository assignComponentRepository;

	@Inject
	AssignGenerateHandler assignGenerateHandler;

	@Inject
	IfNodeGenerateHandler ifNodeGenerateHandler;

	@Inject
	ScreenDesignRepository screenDesignRepository;

	@Inject
	EmailComponentRepository emailCompRepository;
	
	@Inject
	@Named("BusinessCheckGenerationHandler")
	BusinessCheckGenerationHandler businessCheckGenerationHandler;

	@Inject
	NavigationComponentRepository navigationComponentRepository;

	@Inject
	NavigatorComponentGenerateHandler navigatorComponentGenerateHandler;

	@Inject
	AdvanceComponentRepository advanceComponentRepository;

	@Inject
	AdvanceComponentGenerateHandler advanceComponentGenerateHandler;

	@Inject
	CommonComponentRepository commonComponentRepository;

	@Inject
	CommonComponentGenerateHandler commonComponentGenerateHandler;

	@Inject
	LoopComponentGenerateHandler loopComponentGenerateHandler;

	@Inject
	LoopComponentRepository loopComponentRepository;

	@Inject
	FormulaDefinitionRepository formulaDefinitionRepository;

	@Inject
	FunctionMasterRepository functionMasterRepository;

	@Inject
	IfComponentRepository ifComponentRepository;

	@Inject
	IfConditionDetailRepository ifConditionDetailRepository;

	@Inject
	FileOperationComponentGenerateHandler fileOperationComponentGenerateHandler;

	@Inject
	FileOperationComponentRepository fileOperationComponentRepository;

	@Inject
	TransactionComponentGenerateHandler transactionComponentGenerateHandler;

	@Inject
	ImportFileComponentGenerateHandler importFileComponentGenerateHandler;

	@Inject
	ImportFileComponentRepository importFileComponentRepository;

	@Inject
	TransactionComponentRepository transactionComponentRepository;

	@Inject
	ExportFileComponentGenerateHandler exportFileComponentGenerateHandler;

	@Inject
	ExportFileComponentRepository exportFileComponentRepository;

	@Inject
	LogComponentGenerateHandler logComponentGenerateHandler;

	@Inject
	LogComponentRepository logComponentRepository;

	@Inject
	UtilityComponentGenerateHandler utilityComponentGenerateHandler;

	@Inject
	UtilityComponentRepository utilityComponentRepository;

	@Inject
	BDParameterIndexRepository bDParameterIndexRepository;

	@Inject
	DownloadFileComponentRepository downloadFileComponentRepository;

	@Inject
	DownloadFileComponentGenerateHandler downloadFileComponentGenerateHandler;

	@Inject
	NestedLogicComponentGenerateHandler nestedLogicComponentGenerateHandler;

	@Inject
	NestedLogicComponentRepository nestedLogicComponentRepository;
	
	@Inject
	OutputMappingInputGenerateHandler outputMappingInputGenerateHandler;
	
	@Inject
	PageAbleGenerateHandler pageAbleGenerateHandler;
	
	@Inject
	EmailComponentGenerateHandler emailComponentGenerateHandler;
	
	@Inject
	OutputMappingDataSourceGenerateHandler outputMappingDataSourceGenerateHandler;

	@Inject
	@Named("ExceptionGenerationHandler")
	ExceptionComponentGenerationHandler exceptionComponentGenerationHandler;

	@Inject
	ExceptionComponentRepository exceptionComponentRepository;

//	@Inject
//	NavigatorComponentGenerateHandler navigatorComponentGenerateHandler;
	
	private Map<String, List<?>> mAllParentAndSeflByLevelOfInOutObj = new HashMap<String, List<?>>();

	private static final String INIT_BLOGIC_OBJECT_DEFINITION = "{0}ObjectDefinition {1} = new {2}ObjectDefinition() ;";

	public static Map<String, String> mNameParameter = new HashMap<String, String>();

	public static Map<Integer, String> mNameParameterScope = new HashMap<Integer, String>();

	private Map<String, ObjectDefinition> mapObjDef;

	private Map<String, OutputBean> mapOutputBean;
	//
	private List<FunctionMaster> functionMaster;

	private List<FunctionMethod> functionMethods;
	
	private List<FunctionMethodInput> functionMethodInputs;
	
	private List<FunctionMethodOutput> functionMethodOutputs;
	
	private static final boolean IS_GETTER = true;
	private static final boolean IS_SETTER = false;

	private String inSyntax;
	
	private String obSyntax;

	private String ouSyntax;

	public static StringBuilder buildMethod = new StringBuilder(StringUtils.EMPTY);
	
	private List<ScreenDesign> allScreenDesignOfModule;

	private List<SequenceLogic> allSequenceLogics = new ArrayList<SequenceLogic>();
	private List<SequenceConnector> allSequenceConnectors = new ArrayList<SequenceConnector>();
	private List<FeedbackComponent> allFeedbackComponents;
	private List<MessageParameter> allMessageParameters;
	private List<IfComponent> allIfComponents;
	private List<IfConditionDetail> allIfConditionDetails;
	private List<NavigatorComponent> allNavigatorComponents;
	private List<NavigatorDetail> allNavigatorDetails;
	private List<CommonComponent> allCommonComponents;
	private List<CommonInputValue> allCommonInputValues;
	private List<CommonOutputValue> allCommonOutputValues;
	private List<LoopComponent> allLoopComponents;
	private List<DecisionComponent> allDecisionComponents;
	private List<DecisionInputValue> allDecisionInputValues;
	private List<DecisionOutputValue> allDecisionOutputValues;
	private List<AssignComponent> allAssignComponents;
	private List<AssignDetail> allAssignDetails;
	private List<BusinessCheckComponent> allBusinessCheckComponents;
	private List<BusinessCheckDetail> allBusinessCheckDetails;
	private List<BusinessDetailContent> allBusinessDetailContents;
	private List<FormulaDetail> allFormulaDetails;
	private List<FormulaMethodInput> allFormulaMethodInputs;
	private List<FormulaMethodOutput> allFormulaMethodOutputs;
	private List<ExecutionComponent> allExecutionComponents;
	private List<ExecutionInputValue> allExecutionInputValues;
	private List<ExecutionOutputValue> allExecutionOutputValues;
	private List<NestedLogicComponent> allNestedLogicComponents;
	private List<AdvanceComponent> allAdvanceComponents;
	private List<AdvanceInputValue> allAdvanceInputValues;
	private List<AdvanceOutputValue> allAdvanceOutputValues;
	private List<FileOperationComponent> allFileOperationComponents;
	private List<MergeFileDetail> allMergeFileDetails;
	private List<ImportFileComponent> allImportFileComponents;
	private List<ImportAssignValue> allImportAssignValues;
	private List<FileFormat> allFileFormats;
	private List<ExportFileComponent> allExportFileComponents;
	private List<ExportAssignValue> allExportAssignValues;
	private List<ColumnFileFormat> allColumnFileFormats;
	private List<TransactionComponent> allTransactionComponents;
	private List<BDParameterIndex> allBdParameterIndexs;
	private List<LogComponent> allLogComponents;
	private List<UtilityComponent> allUtilityComponents;
	private List<EmailComponent> allEmailComponents;
	private List<EmailRecipient> allEmailRecipients;
	private List<EmailContent> allEmailContents;
	private List<DownloadFileComponent> allDownloadFiles;
	private List<ExceptionComponent> allExceptionComponents;
	private List<ExceptionDetail> allExceptionDetails;

	private Long currentLanguageId;
	private Long currentProjectId;

	private BLogicHandlerIo blogicHandlerIo = null;
	/**
	 * @param module
	 */
	public void generateServiceImpDetail(Module module, Project project, Long languageId) {
		try {
			functionMaster = functionMasterRepository.findFunctionMasterByProjectId(project.getProjectId());
			functionMethods = functionMasterRepository.findFuntionMethodByProjectId(project.getProjectId());
			functionMethodInputs = functionMasterRepository.findFunctionMethodInputByProjectId(project.getProjectId());
			functionMethodOutputs = functionMasterRepository.findFunctionMethodOutputByProjectId(project.getProjectId());
			
			settingFuncDesignDataOfProject(functionMaster, functionMethods, functionMethodInputs, functionMethodOutputs);

			if (FunctionCommon.isNotEmpty(module.getListBusinessDesign())) {
				currentLanguageId = languageId;
				currentProjectId = project.getProjectId();
				Long currentModuleId = module.getModuleId();

				setPrefixByScopeParameter();
				// get sequence logic of this module
				if (currentModuleId != null) {
					allSequenceLogics = generateServiceImpDetailRepository.findSequenceLogicByModuleId(currentModuleId);
					allSequenceConnectors = generateServiceImpDetailRepository.findSequenceConnectorOfModule(currentModuleId);

					allScreenDesignOfModule = screenDesignRepository.getAllScreenInfoByModuleId(currentModuleId, currentLanguageId);

					allFeedbackComponents = feedbackComponentRepository.findFeedbackComponentByModuleId(currentLanguageId, currentModuleId);
					allMessageParameters = messageParameterRepository.findMessageParameterByModuleId(currentLanguageId, currentModuleId);
					allIfComponents = ifComponentRepository.findIfComponentByModuleId(currentModuleId);
					allIfConditionDetails = ifConditionDetailRepository.findIfConditionByModule(currentModuleId);
					allNavigatorComponents = navigationComponentRepository.findAllNavigationComponentByModuleId(currentLanguageId, currentModuleId);
					allNavigatorDetails = navigationComponentRepository.findAllNavigationDetailByModuleId(currentLanguageId, currentModuleId);
					allCommonComponents = commonComponentRepository.findAllCommonComponentByModuleId(currentModuleId);
					allCommonInputValues = commonComponentRepository.findAllCommonInputValueByModuleId(currentModuleId);
					allCommonOutputValues = commonComponentRepository.findAllCommonOutputValueByModuleId(currentModuleId);
					allLoopComponents = loopComponentRepository.findLoopComponentByModuleId(currentModuleId);
					allDecisionComponents = decisionComponentRepository.findAllDecisionComponentByModuleId(currentModuleId);
					allDecisionInputValues = decisionComponentRepository.findAllDecisionInputValueByModuleId(currentModuleId);
					allDecisionOutputValues = decisionComponentRepository.findAllDecisionOutputValueByModuleId(currentModuleId);
					allAssignComponents = assignComponentRepository.findAssignComponentByModule(currentModuleId);
					allAssignDetails = assignComponentRepository.findAssignDetailsByModule(currentModuleId);
					// allValidationCheckDetails =
					// validationCheckDetailRepository.fin
					allBusinessCheckComponents = businessCheckComponentRepository.findBusinessCheckComponentByModuleId(currentModuleId);
					allBusinessCheckDetails = businessCheckComponentRepository.findBusinessCheckDetailByModuleId(currentModuleId);
					allBusinessDetailContents = businessCheckComponentRepository.findBusinessDetailContentByModuleId(currentModuleId);
					allFormulaDetails = formulaDefinitionRepository.findFormulaDetailsByModule(currentModuleId);
					allFormulaMethodInputs = formulaDefinitionRepository.findFormulaMethodInputsByFormulaDetails(allFormulaDetails);
					allFormulaMethodOutputs = formulaDefinitionRepository.findFormulaMethodOutputsByFormulaDetails(allFormulaDetails);
					allExecutionComponents = executionComponentRepository.findExecutionComponentByModuleId(currentModuleId);
					allExecutionInputValues = executionComponentRepository.findExecutionInputValueByModuleId(currentModuleId);
					allExecutionOutputValues = executionComponentRepository.findExecutionOutputValueByModuleId(currentModuleId);

					allNestedLogicComponents = nestedLogicComponentRepository.findNestedLogicComponentByModuleId(currentModuleId);
					allAdvanceComponents = advanceComponentRepository.findAdvanceComponentByModule(currentModuleId);
					allAdvanceInputValues = advanceComponentRepository.findAdvanceInputValueByModule(currentModuleId);
					allAdvanceOutputValues = advanceComponentRepository.findAdvanceOutValueByModule(currentModuleId);
					allFileOperationComponents = fileOperationComponentRepository.findAllFileOperationComponentByModule(currentModuleId);
					allMergeFileDetails = fileOperationComponentRepository.findAllMergeFileDetailByModule(currentModuleId);
					allImportFileComponents = importFileComponentRepository.findAllImportFileComponentByModule(currentModuleId);
					allImportAssignValues = importFileComponentRepository.findAllImportAssignValueByModule(currentModuleId);
					allFileFormats = importFileComponentRepository.findAllFileFormatByModule(currentModuleId);
					allExportFileComponents = exportFileComponentRepository.findAllExportFileComponentByModule(currentModuleId);
					allExportAssignValues = exportFileComponentRepository.findAllExportAssignValueByModule(currentModuleId);
					allColumnFileFormats = exportFileComponentRepository.findAllColumnFileFormatByModule(currentModuleId);
					allTransactionComponents = transactionComponentRepository.findAllTransactionComponentByModule(currentModuleId);
					allLogComponents = logComponentRepository.findAllLogComponentByModule(currentModuleId);
					allUtilityComponents = utilityComponentRepository.findAllUtilityComponentByModuleId(currentLanguageId, currentModuleId);
					allBdParameterIndexs = bDParameterIndexRepository.findBDParameterIndexByModuleId(module.getModuleId());
					allEmailComponents = emailCompRepository.findAllEmailComponent(module.getModuleId());
					allEmailRecipients = emailCompRepository.findAllEmailRecipient(module.getModuleId());
					allEmailContents = emailCompRepository.findAllEmailContent(module.getModuleId());
					allDownloadFiles = downloadFileComponentRepository.findAllDownloadFileComponentByModule(module.getModuleId());
					allExceptionComponents = exceptionComponentRepository.findAllExceptionComponentByModuleId(currentLanguageId, currentModuleId);
					allExceptionDetails = exceptionComponentRepository.findAllExceptionDetailByModuleId(currentLanguageId, currentModuleId);
					mappingDetailOfComponent();
					mappingComponentOfBlogic(module.getListBusinessDesign());
					builAllNodeOfBlogic(project, module, module.getListBusinessDesign());
				}

			}
		} catch (Exception ex) {
			throw ex;
			// System.out.println("ERROR");
		}

	}

	/**
	 * 
	 * Arrang data
	 * 
	 * @param functionMaster
	 * @param functionMethods
	 * @param functionMethodInputs
	 * @param functionMethodOutputs
	 */
	public void settingFuncDesignDataOfProject(List<FunctionMaster> functionMaster, List<FunctionMethod> functionMethods, 
			List<FunctionMethodInput> functionMethodInputs, List<FunctionMethodOutput> functionMethodOutputs) {
		
		if(CollectionUtils.isNotEmpty(functionMethods)) {
			for(FunctionMethod functionMethod : functionMethods){
				List<FunctionMethodInput> functionMethodInputLst = new ArrayList<FunctionMethodInput>();
				List<FunctionMethodOutput> functionMethodOutputtLst = new ArrayList<FunctionMethodOutput>();
				if(CollectionUtils.isNotEmpty(functionMethodInputs)) {
					for (FunctionMethodInput functionMethodInput : functionMethodInputs) {
						if(functionMethodInput.getFunctionMethodId().equals(functionMethod.getFunctionMethodId())){
							functionMethodInputLst.add(functionMethodInput);
						}
					}
				}
				
				functionMethod.setFunctionMethodInput(functionMethodInputLst);
				
				if(CollectionUtils.isNotEmpty(functionMethodOutputs)) {
					for (FunctionMethodOutput functionMethodOutput : functionMethodOutputs) {
						if(functionMethodOutput.getFunctionMethodId().equals(functionMethod.getFunctionMethodId())){
							functionMethodOutputtLst.add(functionMethodOutput);
						}
					}
				}
				
				functionMethod.setFunctionMethodOutput(functionMethodOutputtLst);
			}
		}
	}
	
	private void mappingComponentOfBlogic(List<BusinessDesign> lstBusinessDesigns) {
		if(CollectionUtils.isNotEmpty(lstBusinessDesigns)) {
			for (BusinessDesign businessDesign : lstBusinessDesigns) {
				BusinessDesignBuilder builder = new BusinessDesignBuilder(true, 
						allSequenceLogics, allFeedbackComponents, allIfComponents, allNavigatorComponents, allCommonComponents, 
						allLoopComponents, allDecisionComponents, allAssignComponents, allBusinessCheckComponents, allExecutionComponents, 
						allAdvanceComponents,allFileOperationComponents, allImportFileComponents, allExportFileComponents, 
						allTransactionComponents,allLogComponents, allUtilityComponents, allNestedLogicComponents, 
						allSequenceConnectors, allEmailComponents, allDownloadFiles, allExceptionComponents);

				builder.mappingComponentOfBlogic(businessDesign);
			}
		}
	}

	private void mappingDetailOfComponent() {
		Boolean isGensource = true;
		Map<String, String> mNameParameterOfDetail = new HashMap<String, String>();

		// map detail of parameter index
		ComponentHelper.mappingDetailOfParameterIndex(isGensource,allBdParameterIndexs);

		// map detail function for formula setting
		ComponentHelper.mappingFormulaDetailOfFormulaDefinition(isGensource,allFormulaDetails, allFormulaMethodInputs, allFormulaMethodOutputs, allBdParameterIndexs, mNameParameterOfDetail);

		// map detail for message parameter
		ComponentHelper.mappingMessageParameter(isGensource,allMessageParameters, mNameParameterOfDetail, allBdParameterIndexs);

		// get parameter code for feedback message
		ComponentHelper.mappingMessageOfFeedback(isGensource,allFeedbackComponents, allMessageParameters);

		// map detail condition of if component
		ComponentHelper.mappingConditionOfIf(isGensource,allIfComponents, allIfConditionDetails, allFormulaDetails);

		// map detail of navigator component
		ComponentHelper.mappingDetailOfNavigator(isGensource,allNavigatorComponents, allNavigatorDetails, allBdParameterIndexs, mNameParameterOfDetail);

		// map detail of exception component
		ComponentHelper.mappingDetailOfException(isGensource,allExceptionComponents, allExceptionDetails, allBdParameterIndexs, mNameParameterOfDetail);

		// map detail of common component
		ComponentHelper.mappingDetailOfCommon(isGensource,allCommonComponents, allCommonInputValues, allCommonOutputValues, allBdParameterIndexs, mNameParameterOfDetail);

		// map detail of loop component
		ComponentHelper.mappingDetailOfLoop(isGensource,allLoopComponents, allFormulaDetails, allBdParameterIndexs, mNameParameterOfDetail);

		// map detail of decision component
		ComponentHelper.mappingDetailOfDecision(isGensource,allDecisionComponents, allDecisionInputValues, allDecisionOutputValues, allBdParameterIndexs, mNameParameterOfDetail);

		// map detail of assign component
		ComponentHelper.mappingDetailOfAssign(isGensource,allAssignComponents, allAssignDetails, allFormulaDetails, allBdParameterIndexs, mNameParameterOfDetail);

		// map detail of business check component
		ComponentHelper.mappingDetailOfBusinessCheck(isGensource,allBusinessCheckComponents, allBusinessCheckDetails, allBusinessDetailContents, allMessageParameters, allFormulaDetails, allBdParameterIndexs, mNameParameterOfDetail);

		// map detail of execution component
		ComponentHelper.mappingDetailOfExecution(isGensource,allExecutionComponents, allExecutionInputValues, allExecutionOutputValues, allBdParameterIndexs, mNameParameterOfDetail);

		// map detail of advance
		ComponentHelper.mappingDetailOfAdvance(isGensource,allAdvanceComponents, allAdvanceInputValues, allAdvanceOutputValues, allBdParameterIndexs, mNameParameterOfDetail);

		// map detail of file operation
		ComponentHelper.mappingDetailOfFileOperation(isGensource,allFileOperationComponents, allMergeFileDetails, allFormulaDetails);

		// map detail of import file component
		ComponentHelper.mappingDetailOfImportFile(isGensource,allImportFileComponents, allImportAssignValues, allFileFormats, allFormulaDetails, mNameParameterOfDetail);

		// map detail of export file component
		ComponentHelper.mappingDetailOfExportFile(isGensource,allExportFileComponents, allExportAssignValues, allFileFormats, allColumnFileFormats, allFormulaDetails, mNameParameterOfDetail);

		// map detail of log component
		ComponentHelper.mappingDetailOfLog(isGensource,allLogComponents, allFormulaDetails);

		// map detail of utility component
		ComponentHelper.mappingDetailOfUtility(isGensource,allUtilityComponents, allBdParameterIndexs, mNameParameterOfDetail);

		// map detail of download file component
		ComponentHelper.mappingDetailOfDownload(isGensource, allDownloadFiles, allFormulaDetails, mNameParameterOfDetail);
		
		// map detail of email component
		ComponentHelper.mappingDetailOfEmail(isGensource, allEmailComponents, allEmailRecipients, allEmailContents, allFormulaDetails);
	}

	/**
	 * Setting content of service by business logic common.
	 *
	 * @param project
	 * @param module
	 * @param lstBusinessDesign
	 */
	// quangvd comment : sua source code sau:
	public void generateServiceDetailTypeCommonBlogic(Project project, Module module, List<BusinessDesign> lstBusinessDesign, Long languageId) {
		functionMaster = functionMasterRepository.findFunctionMasterByProjectId(project.getProjectId());
		functionMethods = functionMasterRepository.findFuntionMethodByProjectId(project.getProjectId());
		functionMethodInputs = functionMasterRepository.findFunctionMethodInputByProjectId(project.getProjectId());
		functionMethodOutputs = functionMasterRepository.findFunctionMethodOutputByProjectId(project.getProjectId());
		
		settingFuncDesignDataOfProject(functionMaster, functionMethods, functionMethodInputs, functionMethodOutputs);
		
		if (FunctionCommon.isNotEmpty(lstBusinessDesign)) {
			currentLanguageId = languageId;
			currentProjectId = project.getProjectId();

			setPrefixByScopeParameter();
			// get sequence logic of this module
			allSequenceLogics = generateServiceImpDetailRepository.findAllSequenceLogicByLstBlogic(lstBusinessDesign);
			allSequenceConnectors = generateServiceImpDetailRepository.findAllSequenceConnectorByLstBlogic(lstBusinessDesign);
			allScreenDesignOfModule = new ArrayList<ScreenDesign>();
			allFeedbackComponents = feedbackComponentRepository.findFeedbackComponentByProjectId(currentLanguageId, currentProjectId);
			allMessageParameters = messageParameterRepository.findMessageParameterByModuleCommon(currentLanguageId, currentProjectId);
			allIfComponents = ifComponentRepository.findIfComponentByModuleCommon(currentLanguageId, currentProjectId);
			allIfConditionDetails = ifConditionDetailRepository.findIfConditionByModuleCommon(currentLanguageId, currentProjectId);
			allNavigatorComponents = navigationComponentRepository.findAllNavigationComponentByModuleCommon(currentLanguageId, currentProjectId);
			allNavigatorDetails = navigationComponentRepository.findAllNavigationDetailByModuleCommon(currentLanguageId, currentProjectId);
			allCommonComponents = commonComponentRepository.findAllCommonComponentByModuleCommon(currentProjectId);
			allCommonInputValues = commonComponentRepository.findAllCommonInputValueByModuleCommon(currentProjectId);
			allCommonOutputValues = commonComponentRepository.findAllCommonOutputValueByModuleCommon(currentProjectId);
			allLoopComponents = loopComponentRepository.findLoopComponentByModuleCommon(currentProjectId);
			allDecisionComponents = decisionComponentRepository.findAllDecisionComponentByModuleCommon(currentProjectId);
			allDecisionInputValues = decisionComponentRepository.findAllDecisionInputValueByModuleCommon(currentProjectId);
			allDecisionOutputValues = decisionComponentRepository.findAllDecisionOutputValueByModuleCommon(currentProjectId);
			allAssignComponents = assignComponentRepository.findAssignComponentByModuleCommon(currentProjectId);
			allAssignDetails = assignComponentRepository.findAssignDetailsByModuleCommon(currentProjectId);
			// allValidationCheckDetails =
			// validationCheckDetailRepository.fin
			allBusinessCheckComponents = businessCheckComponentRepository.findBusinessCheckComponentByModuleCommon(currentProjectId);
			allBusinessCheckDetails = businessCheckComponentRepository.findBusinessCheckDetailByModuleCommon(currentProjectId);
			allBusinessDetailContents = businessCheckComponentRepository.findBusinessDetailContentByModuleCommon(currentProjectId);
			allFormulaDetails = formulaDefinitionRepository.findFormulaDetailsByModuleCommon(currentProjectId);
			allFormulaMethodInputs = formulaDefinitionRepository.findFormulaMethodInputsByFormulaDetails(allFormulaDetails);
			allFormulaMethodOutputs = formulaDefinitionRepository.findFormulaMethodOutputsByFormulaDetails(allFormulaDetails);
			allExecutionComponents = executionComponentRepository.findExecutionComponentByModuleCommon(currentProjectId);
			allExecutionInputValues = executionComponentRepository.findExecutionInputValueByModuleCommon(currentProjectId);
			allExecutionOutputValues = executionComponentRepository.findExecutionOutputValueByModuleCommon(currentProjectId);

			// List<NestedLogicComponent> lstNestedLogicComponents =
			//
			allNestedLogicComponents = nestedLogicComponentRepository.findNestedLogicComponentByModuleCommon(currentProjectId);
			allAdvanceComponents = advanceComponentRepository.findAdvanceComponentByModuleCommon(currentProjectId);
			allAdvanceInputValues = advanceComponentRepository.findAdvanceInputValueByModuleCommon(currentProjectId);
			allAdvanceOutputValues = advanceComponentRepository.findAdvanceOutValueByModuleCommon(currentProjectId);
			allFileOperationComponents = fileOperationComponentRepository.findAllFileOperationComponentByModuleCommon(currentProjectId);
			allMergeFileDetails = fileOperationComponentRepository.findAllMergeFileDetailByModuleCommon(currentProjectId);
			allImportFileComponents = importFileComponentRepository.findAllImportFileComponentByModuleCommon(currentProjectId);
			allImportAssignValues = importFileComponentRepository.findAllImportAssignValueByModuleCommon(currentProjectId);
			allFileFormats = importFileComponentRepository.findAllFileFormatByModuleCommon(currentProjectId);
			allExportFileComponents = exportFileComponentRepository.findAllExportFileComponentByModuleCommon(currentProjectId);
			allExportAssignValues = exportFileComponentRepository.findAllExportAssignValueByModuleCommon(currentProjectId);
			allColumnFileFormats = exportFileComponentRepository.findAllColumnFileFormatByModuleCommon(currentProjectId);
			allTransactionComponents = transactionComponentRepository.findAllTransactionComponentByModuleCommon(currentProjectId);
			allLogComponents = logComponentRepository.findAllLogComponentByModuleCommon(currentProjectId);
			allUtilityComponents = utilityComponentRepository.findAllUtilityComponentByModuleCommon(currentLanguageId, currentProjectId);
			allBdParameterIndexs = bDParameterIndexRepository.findBDParameterIndexByModuleCommon(currentProjectId);
			allDownloadFiles = downloadFileComponentRepository.findAllDownloadFileComponentByModuleCommon(currentProjectId);
			mappingDetailOfComponent();
			mappingComponentOfBlogic(lstBusinessDesign);
			builAllNodeOfBlogic(project, module, lstBusinessDesign);
		}
	}

	public void builAllNodeOfBlogic(Project project, Module module, List<BusinessDesign> lstBlogic) {

		if(CollectionUtils.isNotEmpty(lstBlogic)) {
			for (BusinessDesign blogic : lstBlogic) {
				try {
					mapObjDef = new HashMap<String, ObjectDefinition>();
					
					if(CollectionUtils.isNotEmpty(blogic.getLstObjectDefinition())) {
						for (ObjectDefinition ob : blogic.getLstObjectDefinition()) {
							mapObjDef.put(ob.getObjectDefinitionId(), ob);
						}
					}

					mapOutputBean = new HashMap<String, OutputBean>();
					
					if(CollectionUtils.isNotEmpty(blogic.getLstOutputBean())) {
						for (OutputBean ou : blogic.getLstOutputBean()) {
							mapOutputBean.put(ou.getOutputBeanId(), ou);
						}
					}

					mNameParameter = setLevelOfInputBeanOutputBeanObjectDefinition(blogic.getLstInputBean(), blogic.getLstOutputBean(), blogic.getLstObjectDefinition());
					mAllParentAndSeflByLevelOfInOutObj = getAllParentAndSeflByLevelOfInOutObj(0, blogic.getLstInputBean(), blogic.getLstObjectDefinition(), blogic.getLstOutputBean());

					// Initial content of service
					StringBuilder logicStringBuilder = new StringBuilder();
					inSyntax = "in";
					obSyntax = "";
					if (CollectionUtils.isNotEmpty(blogic.getLstObjectDefinition())) {
						obSyntax = "ob";
						String initStr = initDataOfDetailServiceImp(blogic, obSyntax);
						logicStringBuilder.append(initStr);
					}
					ouSyntax = "";
					if (CollectionUtils.isNotEmpty(blogic.getLstOutputBean())) {
						ouSyntax = "ou";
					}
					
					buildNodeOfBlogic(project, module, blogic, logicStringBuilder);
					module.setModuleCode(blogic.getModuleCode());
					blogic.setStrDetailsOfServiceImp(logicStringBuilder.toString());

					if (StringUtils.isNotEmpty(buildMethod.toString())) {
						blogic.setStrMethodOfAdvance(buildMethod.toString());
						buildMethod.setLength(0);
					};
					// Setting mapping at controller is here
					if (BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(blogic.getBlogicType())) continue;
					outputMappingInputGenerateHandler.handle(new StringBuilder(StringUtils.EMPTY), blogicHandlerIo);
					outputMappingDataSourceGenerateHandler.handle(new StringBuilder(StringUtils.EMPTY), blogicHandlerIo);
					pageAbleGenerateHandler.handle(new StringBuilder(StringUtils.EMPTY), blogicHandlerIo);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private String generateContentOfAllNode(Project project, Module module, BusinessDesign blogic, ScreenDesign currentScreen, List<SequenceLogicExt> lstLogics, StringBuilder logicStringBuilder) {
		if (CollectionUtils.isNotEmpty(lstLogics)) {
			BLogicHandlerIo blogicHandlerIo = null;
			blogicHandlerIo = new BLogicHandlerIo();
			blogicHandlerIo.setModule(module);
			blogicHandlerIo.setProject(project);
			blogicHandlerIo.setBusinessDesign(blogic);
			blogicHandlerIo.setFunctionMasters(functionMaster);
			blogicHandlerIo.setFunctionMethods(functionMethods);
			// blogicHandlerIo.setFormulaDefinition(formulaDefinition);(formulaDefinitionBusinessCheckNode);
			blogicHandlerIo.setScreenDesign(currentScreen);
			blogicHandlerIo.setMapObjDef(mapObjDef);
			blogicHandlerIo.setMapOutputBean(mapOutputBean);
			blogicHandlerIo.setBlogicInSyntax(inSyntax);
			blogicHandlerIo.setBlogicObSyntax(obSyntax);
			blogicHandlerIo.setBlogicOutputSyntax(ouSyntax);
			blogicHandlerIo.setmNameParameter(mNameParameter);
			blogicHandlerIo.setmAllParentAndSeflByLevelOfInOutObj(mAllParentAndSeflByLevelOfInOutObj);
			this.setBlogicHandlerIo(blogicHandlerIo);

			for (SequenceLogicExt sequenceLogic : lstLogics) {
				if (sequenceLogic != null && sequenceLogic.getStrData() != null) {
					blogicHandlerIo.setSequenceLogic(sequenceLogic);
					switch (sequenceLogic.getComponentType()) {
						case BusinessDesignConst.COMPONENT_BUSINESSCHECK:
							// blogicHandlerIo.setFormulaDefinition(formulaDefinitionBusinessCheckNode);

							// map data
							BusinessCheckComponent currentComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), BusinessCheckComponent.class);
							businessCheckGenerationHandler.setCurrentComponent(currentComponent);
							businessCheckGenerationHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_EXECUTION:
							// map data
							ExecutionComponent executionComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), ExecutionComponent.class);
							executionGenerationHandler.setCurrentComponent(executionComponent);
							executionGenerationHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_DECISION:
							DecisionComponent decisionComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), DecisionComponent.class);
							decisionComponentGenerateHandler.setCurrentComponent(decisionComponent);
							decisionComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_FEEDBACK:
							FeedbackComponent feedbackComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), FeedbackComponent.class);
							feedbackComponentGenerateHandler.setCurrentComponent(feedbackComponent);
							feedbackComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_NAVIGATOR:
							NavigatorComponent navigatorComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), NavigatorComponent.class);
							navigatorComponentGenerateHandler.setCurrentComponent(navigatorComponent);
							navigatorComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_ASSIGN:
							AssignComponent assignComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), AssignComponent.class);
							assignGenerateHandler.setCurrentComponent(assignComponent);
							assignGenerateHandler.setAllLoopComponent(allLoopComponents);
							assignGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_IF:

							logicStringBuilder.append(IfNodeGenerateHandler.NL).append("// Start if node");

							logicStringBuilder.append(StringUtils.LF);
							logicStringBuilder.append(IfNodeGenerateHandler.NL);
							int count = 0;
							IfComponent ifComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), IfComponent.class);
							if (ifComponent != null){
								String remark = ifComponent.getRemark();
								if (StringUtils.isNotEmpty(remark)) {
									if (org.springframework.util.StringUtils.countOccurrencesOf(remark, "\n") > 0) {
										remark = remark.replace("\n", BusinessDesignConst.NL);
										logicStringBuilder.append(BusinessDesignConst.MULTI_COMMENT_START).append(BusinessDesignConst.NL).append(remark).append(BusinessDesignConst.NL).append(BusinessDesignConst.MULTI_COMMENT_END).append(BusinessDesignConst.NL);
									} else {
										logicStringBuilder.append(BusinessDesignConst.SINGLE_COMMENT_START).append(BusinessDesignConst.SPACE).append(remark).append(BusinessDesignConst.NL);
									}
								}
								count = 0;
								for (IfConditionDetail ifConditionDetail : ifComponent.getIfConditionDetails()) {
									String condition = "";
									String bodyCondition = generateContentOfAllNode(project, module, blogic, currentScreen, sequenceLogic.getLstChildOfIfLogics().get(count), new StringBuilder());
									List<String> result = new ArrayList<String>();
									if (count == 0) {
										result = generateConditionByFormula(blogicHandlerIo, ifConditionDetail.getFormulaDefinitionDetails());
										condition = result.get(0);
										logicStringBuilder.append(result.get(1));
										logicStringBuilder.append("if (" + condition + ") {");
										logicStringBuilder.append(IfNodeGenerateHandler.NL).append(IfNodeGenerateHandler.TAB);
										logicStringBuilder.append(bodyCondition);
										logicStringBuilder.append(IfNodeGenerateHandler.NL).append("}");
									} else if (count == ifComponent.getIfConditionDetails().size() - 1) {
										logicStringBuilder.append("else {");
										logicStringBuilder.append(IfNodeGenerateHandler.NL).append(IfNodeGenerateHandler.TAB);
										logicStringBuilder.append(bodyCondition);
										logicStringBuilder.append(IfNodeGenerateHandler.NL).append("}");
										logicStringBuilder.append(IfNodeGenerateHandler.NL);
									} else {
										result = generateConditionByFormula(blogicHandlerIo, ifConditionDetail.getFormulaDefinitionDetails());
										condition = result.get(0);
										logicStringBuilder.append(result.get(1));
										logicStringBuilder.append("else if (" + condition + ") {");
										logicStringBuilder.append(IfNodeGenerateHandler.NL).append(IfNodeGenerateHandler.TAB);
										logicStringBuilder.append(bodyCondition);
										logicStringBuilder.append(IfNodeGenerateHandler.NL).append("}");
									}
									count++;
								}
							}

							logicStringBuilder.append(IfNodeGenerateHandler.NL).append("// End if node");

							break;
						case BusinessDesignConst.COMPONENT_ADVANCE:
							AdvanceComponent advanceComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), AdvanceComponent.class);
							advanceComponentGenerateHandler.setCurrentComponent(advanceComponent);
							advanceComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							buildMethod.append(blogicHandlerIo.getAdvanceComponentMethod());
							break;
						case BusinessDesignConst.COMPONENT_COMMON:
							CommonComponent commonComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), CommonComponent.class);
							commonComponentGenerateHandler.setCurrentComponent(commonComponent);
							commonComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_LOOP:
							List<SequenceLogic> childs = new ArrayList<SequenceLogic>();
							for (SequenceLogicExt item : sequenceLogic.getLstChildOfForLogics()) {
								childs.add(item);
							}
							String sChild = generateContentOfAllNode(project, module, blogic, currentScreen, sequenceLogic.getLstChildOfForLogics(), new StringBuilder());
							blogicHandlerIo.setContent(sChild);
							LoopComponent loopComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), LoopComponent.class);
							loopComponentGenerateHandler.setCurrentComponent(loopComponent);
							loopComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_FILEOPERATION:
							FileOperationComponent fileOperationComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), FileOperationComponent.class);
							fileOperationComponentGenerateHandler.setCurrentComponent(fileOperationComponent);
							fileOperationComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_TRANSACTION:
							TransactionComponent transactionComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), TransactionComponent.class);
							transactionComponentGenerateHandler.setCurrentComponent(transactionComponent);
							transactionComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_IMPORTFILE:
							ImportFileComponent importFileComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), ImportFileComponent.class);
							importFileComponentGenerateHandler.setCurrentComponent(importFileComponent);
							importFileComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_EXPORTFILE:
							ExportFileComponent exportFileComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), ExportFileComponent.class);
							exportFileComponentGenerateHandler.setCurrentComponent(exportFileComponent);
							exportFileComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_LOG:
							LogComponent logComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), LogComponent.class);
							logComponentGenerateHandler.setCurrentComponent(logComponent);
							logComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_UTILITY:
							UtilityComponent utilityComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), UtilityComponent.class);
							utilityComponentGenerateHandler.setCurrentComponent(utilityComponent);
							utilityComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_DOWNLOAD_FILE:
							DownloadFileComponent downloadFileComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), DownloadFileComponent.class);
							downloadFileComponentGenerateHandler.setCurrentComponent(downloadFileComponent);
							downloadFileComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_NESTEDLOGIC:
							NestedLogicComponent nestedLogicComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), NestedLogicComponent.class);
							BusinessLogicGenerateHelper helper = new BusinessLogicGenerateHelper();
							helper.setLstSequenceConnectors(nestedLogicComponent.getArrConnection());

							List<SequenceLogicExt> lstDetailLogics = new ArrayList<SequenceLogicExt>();
							for (SequenceLogic detailSequenceLogic : nestedLogicComponent.getArrComponent()) {
								SequenceLogicExt temp = new SequenceLogicExt(detailSequenceLogic);
								lstDetailLogics.add(temp);
							}
							helper.setLstSequenceLogics(lstDetailLogics);
							helper.setLstSequenceConnectors(nestedLogicComponent.getArrConnection());
							List<SequenceLogicExt> lstLogicOfNested = helper.buildTreeSourceCode();
							String nChild = generateContentOfAllNode(project, module, blogic, currentScreen, lstLogicOfNested, new StringBuilder());
							blogicHandlerIo.setContent(nChild);
							nestedLogicComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
						case BusinessDesignConst.COMPONENT_EXCEPTION:
							ExceptionComponent exceptionComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), ExceptionComponent.class);
							exceptionComponentGenerationHandler.setCurrentComponent(exceptionComponent);
							exceptionComponentGenerationHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
							
						case BusinessDesignConst.COMPONENT_EMAIL:
							EmailComponent emailComponent = DataTypeUtils.toObject(sequenceLogic.getStrData(), EmailComponent.class);
							emailComponentGenerateHandler.setCurrentComponent(emailComponent);
							emailComponentGenerateHandler.handle(logicStringBuilder, blogicHandlerIo);
							break;
							
						default:
							break;
					}
				}
			}
		}
		return logicStringBuilder.toString();
	}

	public String buildNodeOfBlogic(Project project, Module module, BusinessDesign blogic, StringBuilder logicStringBuilder) {
		ScreenDesign currentScreen = null;
		if(CollectionUtils.isNotEmpty(allScreenDesignOfModule)) {
			for (ScreenDesign screen : allScreenDesignOfModule) {
				if (screen.getScreenId().equals(blogic.getScreenId())) {
					currentScreen = screen;
					break;
				}
			}
		}

		BusinessLogicGenerateHelper helper = new BusinessLogicGenerateHelper();
		helper.setLstSequenceConnectors(blogic.getLstSequenceConnectors());
		List<SequenceLogicExt> lstLogics = new ArrayList<SequenceLogicExt>();
		
		if(CollectionUtils.isNotEmpty(blogic.getLstSequenceLogics())) {
			for (SequenceLogic sequenceLogic : blogic.getLstSequenceLogics()) {
				SequenceLogicExt temp = new SequenceLogicExt(sequenceLogic);
				lstLogics.add(temp);
			}
		}
		
		helper.setLstSequenceLogics(lstLogics);
		lstLogics = helper.buildTreeSourceCode();
		generateContentOfAllNode(project, module, blogic, currentScreen, lstLogics, logicStringBuilder);
		return logicStringBuilder.toString();
	}

	private void setPrefixByScopeParameter() {
		mNameParameterScope.put(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_CODE);
		mNameParameterScope.put(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_CODE);
		mNameParameterScope.put(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_CODE);
	}

	private String initDataOfDetailServiceImp(BusinessDesign blogic, String obSyntax) {
		StringBuilder initStr = new StringBuilder();
		initStr.append(MessageFormat.format(INIT_BLOGIC_OBJECT_DEFINITION, StringUtils.capitalize(blogic.getBusinessLogicCode()), obSyntax, StringUtils.capitalize(blogic.getBusinessLogicCode())));
		return initStr.toString();
	}

	/** Adding by HungHX */
	/**
	 * Get value setter getter of from assign
	 *
	 * @param isDefltInOuObjDefOfBlogic
	 * @param isBlogic : 0 -> Business Logic, 1 : SqlDesign, 2 : Decision, 3 : Function Master
	 * @param mAllParentAndSeflByLevelOfInOutObjCustom
	 * @param isGetter
	 * @param id
	 * @param scope
	 * @param inputSyntax
	 * @param lstIndex
	 * @return
	 */
	public String getterAndSetterOfParameter(int isBlogic, Map<String, List<?>> mAllParentAndSeflByLevelOfInOutObj,
			Boolean isGetter, String id, Integer scope, String inputSyntax, List<BDParameterIndex> lstIndex) {

		StringBuilder strResult = new StringBuilder(inputSyntax);
		String indexStr = "";
		int idx = 0;
		boolean isFirst = true;

		List<?> lstAllParentByLevel = mAllParentAndSeflByLevelOfInOutObj.get(scope + id);
		boolean arrayFlg = false;
		String code = "";

		if (CollectionUtils.isNotEmpty(lstAllParentByLevel)) {
			for (int i = 0; i < lstAllParentByLevel.size(); i++) {
				// Initializing data
				switch (scope) {
					// Input
					case TypeScope.INPUTBEAN:
						switch (isBlogic) {
							case ModuleScope.BLOGIC:
								InputBean in = (InputBean) lstAllParentByLevel.get(i);
								arrayFlg = in.getArrayFlg();
								code = in.getInputBeanCode();
								break;
							case ModuleScope.SQL:
								SqlDesignInput inSql = (SqlDesignInput) lstAllParentByLevel.get(i);
								arrayFlg = (inSql.getArrayFlag() == null || inSql.getArrayFlag() == 0)?false:true;
								code = inSql.getSqlDesignInputCode();
								break;
							case ModuleScope.DECISION:
								DecisionTableInputBean inDc = (DecisionTableInputBean) lstAllParentByLevel.get(i);
								arrayFlg = false;
								code = inDc.getDecisionInputBeanCode();
								break;
							case ModuleScope.FUNCTION_MASTER:
								FunctionMethodInput inFmi = (FunctionMethodInput) lstAllParentByLevel.get(i);
								arrayFlg = (inFmi.getArrayFlg() == null || inFmi.getArrayFlg() == 0)?false:true;
								code = inFmi.getMethodInputCode();
								break;
						}
						break;
						// Object definition
					case TypeScope.OBJECTDEFINITION:
						ObjectDefinition objDef = (ObjectDefinition) lstAllParentByLevel.get(i);
						arrayFlg = objDef.getArrayFlg();
						code = objDef.getObjectDefinitionCode();
						break;
						// Output
					case TypeScope.OUTPUTBEAN:
						switch (isBlogic) {
							case ModuleScope.BLOGIC:
								OutputBean ou = (OutputBean) lstAllParentByLevel.get(i);
								arrayFlg = ou.getArrayFlg();
								code = ou.getOutputBeanCode();
								break;
							case ModuleScope.SQL:
								SqlDesignOutput ouSql = (SqlDesignOutput) lstAllParentByLevel.get(i);
								arrayFlg = (ouSql.getArrayFlag() == null || ouSql.getArrayFlag() == 0)?false:true;
								code = ouSql.getSqlDesignOutputCode();
								break;
							case ModuleScope.DECISION:
								DecisionTableOutputBean ouDc = (DecisionTableOutputBean) lstAllParentByLevel.get(i);
								arrayFlg = false;
								code = ouDc.getDecisionOutputBeanCode();
								break;
								
							case ModuleScope.FUNCTION_MASTER:
								FunctionMethodOutput inFmo = (FunctionMethodOutput) lstAllParentByLevel.get(i);
								arrayFlg = (inFmo.getArrayFlg() == null || inFmo.getArrayFlg() == 0)?false:true;
								code = inFmo.getMethodOutputCode();
								break;
						}
						break;
				}

				// Main process
				if (arrayFlg) {
					idx = isFirst ? 0 : idx + 1;
					isFirst = false;
					BDParameterIndex bdIndex = CollectionUtils.isEmpty(lstIndex) ? null : (idx < lstIndex.size()) ? lstIndex.get(idx) : new BDParameterIndex();

					if (bdIndex != null) {
						if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_CUSTOMIZE.equals(bdIndex.getParameterIndexType())) {
							indexStr = bdIndex.getParameterIndexId();
						} else {
							indexStr = this.getParamTarget(isBlogic, mAllParentAndSeflByLevelOfInOutObj, bdIndex.getParameterIndexId(), bdIndex.getParameterIndexType(), true,
								getLoopComponentBySequence(allLoopComponents, bdIndex.getParameterIndexId()));
						}
					} else {
						indexStr = "";
					}
				}

				if (isGetter) {
					// in the case of using index
					if (indexStr != null && indexStr != "") {
						strResult.append(".get" + StringUtils.capitalize(code) + "()" + (arrayFlg ? ".get(" + indexStr + ")" : ""));
					} else {
						// in the case of using for each
						strResult.append(".get" + StringUtils.capitalize(code) + "()");
					}
				} else {
					if (arrayFlg && i == lstAllParentByLevel.size() - 1) {
						// in the case of using index then had build template :
						// ob.getFieldA().set(index, %s)
						if (indexStr != null && indexStr != "") {
							strResult.append(".get" + StringUtils.capitalize(code) + "()");
							// Validate when assign using index
							StringBuilder sbTmp = new StringBuilder();
							sbTmp.append(String.format("if(%s != null && %s.size() > %s) {", strResult.toString(), strResult.toString(), indexStr));
							sbTmp.append("\n\t\t\t");
							sbTmp.append(strResult.append(".set(" + indexStr + ", %s)").toString()).append(";");
							sbTmp.append("\n\t\t");
							sbTmp.append("}");
							// Setting return value
							strResult.setLength(0);
							strResult.append(sbTmp.toString());
						} else {
							// in the case assign type for each
							strResult.append(".set" + StringUtils.capitalize(code));
						}
					} else if (arrayFlg && i < lstAllParentByLevel.size() - 1) {

						if (indexStr != null && indexStr != "") {
							strResult.append(".get" + StringUtils.capitalize(code) + "().get(" + indexStr + ")");
						} else {
							// in the case assign type for each
							strResult.append(".get" + StringUtils.capitalize(code) + "()");
						}
					} else if (i == lstAllParentByLevel.size() - 1) {
						strResult.append(".set" + StringUtils.capitalize(code));
					} else {
						strResult.append(".get" + StringUtils.capitalize(code) + "()");
					}
				}
			}

			// Reset default value
			idx = 0;
			isFirst = true;
			indexStr = "";
		}

		return strResult.toString();
	}

	/**
	 *
	 * @param isBlogic
	 * @param mAllParentAndSeflByLevelOfInOutObjCustom
	 * @param id
	 * @param scope
	 * @param isGetter
	 * @param loopComponent
	 * @return
	 */
	private String getParamTarget(Integer isBlogic ,Map<String, List<?>> mAllParentAndSeflByLevelOfInOutObjCustom,
			String id, Integer scope, Boolean isGetter, LoopComponent loopComponent) {

		String param = "";

		if (DataTypeUtils.equals(BusinessDesignConst.ParameterIndex.INDEX_TYPE_INPUT_BEAN, scope)) {
			param = getterAndSetterOfParameter(isBlogic, mAllParentAndSeflByLevelOfInOutObjCustom, isGetter, id, scope, "in", null);
		} else if (DataTypeUtils.equals(BusinessDesignConst.ParameterIndex.INDEX_TYPE_OBJECT_DEFINITION, scope)) {
			param = getterAndSetterOfParameter(isBlogic, mAllParentAndSeflByLevelOfInOutObjCustom, isGetter, id, scope, "ob", null);
		} else if (DataTypeUtils.equals(BusinessDesignConst.ParameterIndex.INDEX_TYPE_OUTPUT_BEAN, scope)) {
			param = getterAndSetterOfParameter(isBlogic, mAllParentAndSeflByLevelOfInOutObjCustom, isGetter, id, scope, "ou", null);
		} else if (DataTypeUtils.equals(BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP, scope)) {
			param = (loopComponent != null && StringUtils.isNotEmpty(loopComponent.getIndex()))?loopComponent.getIndex():"";
		}
		
		return param;
	}

	/**
	 * Build parent and self by level
	 *
	 * @param isBlogic : [0 : Business, 1 : Execution, 2 : Decision, 3 : FunctionMaster]
	 * @param lstInput
	 * @param lstObjectDefinition
	 * @param lstOutput
	 * @return
	 */
	public Map<String, List<?>> getAllParentAndSeflByLevelOfInOutObj(int isBlogic, List<?> lstInput, List<?> lstObjectDefinition, List<?> lstOutput) {
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		List<Object> lstObj = new ArrayList<Object>();
		String id = "";
		// Build for input
		if(CollectionUtils.isNotEmpty(lstInput)){
			for (Object obj : lstInput) {
				switch (isBlogic) {
					case ModuleScope.BLOGIC:
						InputBean in = (InputBean) obj; id = in.getInputBeanId();
						break;
					case ModuleScope.SQL:
						SqlDesignInput inSql = (SqlDesignInput) obj; id = inSql.getSqlDesignInputId().toString();
						break;
					case ModuleScope.DECISION:
						// Decision
						DecisionTableInputBean dci = (DecisionTableInputBean) obj; id = dci.getDecisionInputBeanId();
						break;
					case ModuleScope.FUNCTION_MASTER:
						// Function Master
						FunctionMethodInput fmi = (FunctionMethodInput) obj; id = fmi.getMethodInputId();
						break;
				}

				lstObj = getParentLstFrmObjByTypeOfScope(isBlogic, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, obj, lstObj, lstInput);
				map.put(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID + id, lstObj);
				lstObj = new ArrayList<Object>(); id = "";
			}
		}

		// Build for Object Definition
		if(CollectionUtils.isNotEmpty(lstObjectDefinition)){
			lstObj = new ArrayList<Object>();
			for (Object obj : lstObjectDefinition) {
				ObjectDefinition objDef = (ObjectDefinition) obj;
				lstObj = getParentLstFrmObjByTypeOfScope(isBlogic, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, objDef, lstObj, lstObjectDefinition);
				map.put(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID + objDef.getObjectDefinitionId(), lstObj);
				lstObj = new ArrayList<Object>(); id = "";
			}
		}

		// Build for Output
		if(CollectionUtils.isNotEmpty(lstOutput)){
			lstObj = new ArrayList<Object>();
			for (Object obj : lstOutput) {
				switch(isBlogic) {
					case ModuleScope.BLOGIC:
						OutputBean ou = (OutputBean) obj;id = ou.getOutputBeanId();
						break;
					case ModuleScope.SQL:
						SqlDesignOutput ouSql = (SqlDesignOutput) obj;id = ouSql.getSqlDesignOutputId().toString();
						break;
					case ModuleScope.DECISION:
						// Decision
						DecisionTableOutputBean dco = (DecisionTableOutputBean) obj; id = dco.getDecisionOutputBeanId();
						break;
					case ModuleScope.FUNCTION_MASTER:
						// Function Master
						FunctionMethodOutput fmo = (FunctionMethodOutput) obj; id = fmo.getMethodOutputId();
						break;
				}

				lstObj = getParentLstFrmObjByTypeOfScope(isBlogic, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, obj, lstObj, lstOutput);
				map.put(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID + id, lstObj);
				lstObj = new ArrayList<Object>(); id = "";
			}
		}

		return map;
	}

	/**
	 *
	 * @param isBlogic  [0 : Business, 1 : Sql, 2 : Decision, 3 : FunctionMaster]
	 * @param scope
	 * @param obj
	 * @param lstObjReturn
	 * @param lstArgObj
	 * @return
	 */
	private static List<Object> getParentLstFrmObjByTypeOfScope(int isBlogic, int scope, Object obj, List<Object> lstObjReturn, List<?> lstArgObj) {

		lstObjReturn = (CollectionUtils.isNotEmpty(lstObjReturn)) ? lstObjReturn : new ArrayList<Object>();

		switch (scope) {
			// Input bean
			case TypeScope.INPUTBEAN:
				switch (isBlogic) {
					case ModuleScope.BLOGIC:
						InputBean inputParam = (InputBean) obj;
						if (inputParam.getParentInputBeanId() == null) {
							lstObjReturn.add(0, inputParam);
						} else {
							lstObjReturn.add(0, inputParam);
							for (Object iter : lstArgObj) {
								InputBean input = (InputBean) iter;
								if (DataTypeUtils.equals(inputParam.getParentInputBeanId(), input.getInputBeanId())) {
									lstObjReturn = getParentLstFrmObjByTypeOfScope(isBlogic, scope, input, lstObjReturn, lstArgObj);
									break;
								}
							}
						}
						break;
					case ModuleScope.SQL:
						SqlDesignInput inputParamSql = (SqlDesignInput) obj;
						if (inputParamSql.getSqlDesignInputParentId() == null) {
							lstObjReturn.add(0, inputParamSql);
						} else {
							lstObjReturn.add(0, inputParamSql);
							for (Object iter : lstArgObj) {
								SqlDesignInput input = (SqlDesignInput) iter;
								if (DataTypeUtils.equals(inputParamSql.getSqlDesignInputParentId(), input.getSqlDesignInputId())) {
									lstObjReturn = getParentLstFrmObjByTypeOfScope(isBlogic, scope, input, lstObjReturn, lstArgObj);
									break;
								}
							}
						}
						break;
					case ModuleScope.DECISION:
						DecisionTableInputBean inputDcParam = (DecisionTableInputBean) obj;
						if (inputDcParam.getParentDecisionInputBeanId() == null) {
							lstObjReturn.add(0, inputDcParam);
						} else {
							lstObjReturn.add(0, inputDcParam);
							for (Object iter : lstArgObj) {
								DecisionTableInputBean input = (DecisionTableInputBean) iter;
								if (DataTypeUtils.equals(inputDcParam.getParentDecisionInputBeanId(), input.getDecisionInputBeanId())) {
									lstObjReturn = getParentLstFrmObjByTypeOfScope(isBlogic, scope, input, lstObjReturn, lstArgObj);
									break;
								}
							}
						}
						break;
					case ModuleScope.FUNCTION_MASTER:
						FunctionMethodInput functionMethodInput = (FunctionMethodInput) obj;
						if (functionMethodInput.getParentFunctionMethodInputId() == null) {
							lstObjReturn.add(0, functionMethodInput);
						} else {
							lstObjReturn.add(0, functionMethodInput);
							for (Object iter : lstArgObj) {
								FunctionMethodInput input = (FunctionMethodInput) iter;
								if (DataTypeUtils.equals(functionMethodInput.getParentFunctionMethodInputId(), input.getMethodInputId())) {
									lstObjReturn = getParentLstFrmObjByTypeOfScope(isBlogic, scope, input, lstObjReturn, lstArgObj);
									break;
								}
							}
						}
						break;
				}

				break;
				// Object definition
			case TypeScope.OBJECTDEFINITION:
				ObjectDefinition objDefParam = (ObjectDefinition) obj;
				if (objDefParam.getParentObjectDefinitionId() == null) {
					lstObjReturn.add(0, objDefParam);
				} else {
					lstObjReturn.add(0, objDefParam);
					for (Object iter : lstArgObj) {
						ObjectDefinition objDef = (ObjectDefinition) iter;
						if (DataTypeUtils.equals(objDefParam.getParentObjectDefinitionId(), objDef.getObjectDefinitionId())) {
							lstObjReturn = getParentLstFrmObjByTypeOfScope(isBlogic, scope, objDef, lstObjReturn, lstArgObj);
							break;
						}
					}
				}

				break;

				// Output bean
			case TypeScope.OUTPUTBEAN:
				switch (isBlogic) {
					case ModuleScope.BLOGIC:
						OutputBean outputParam = (OutputBean) obj;
						if (outputParam.getParentOutputBeanId() == null) {
							lstObjReturn.add(0, outputParam);
						} else {
							lstObjReturn.add(0, outputParam);
							for (Object iter : lstArgObj) {
								OutputBean output = (OutputBean) iter;
								if (DataTypeUtils.equals(outputParam.getParentOutputBeanId(), output.getOutputBeanId())) {
									lstObjReturn = getParentLstFrmObjByTypeOfScope(isBlogic, scope, iter, lstObjReturn, lstArgObj);
									break;
								}
							}
						}
						break;
					case ModuleScope.SQL:
						SqlDesignOutput outputParamSql = (SqlDesignOutput) obj;
						if (outputParamSql.getSqlDesignOutputParentId() == null) {
							lstObjReturn.add(0, outputParamSql);
						} else {
							lstObjReturn.add(0, outputParamSql);
							for (Object iter : lstArgObj) {
								SqlDesignOutput input = (SqlDesignOutput) iter;
								if (DataTypeUtils.equals(outputParamSql.getSqlDesignOutputParentId(), input.getSqlDesignOutputId())) {
									lstObjReturn = getParentLstFrmObjByTypeOfScope(isBlogic, scope, input, lstObjReturn, lstArgObj);
									break;
								}
							}
						}
						break;
					case ModuleScope.DECISION:
						DecisionTableOutputBean inputDcParam = (DecisionTableOutputBean) obj;
						if (inputDcParam.getParentDecisionOutputBeanId() == null) {
							lstObjReturn.add(0, inputDcParam);
						} else {
							lstObjReturn.add(0, inputDcParam);
							for (Object iter : lstArgObj) {
								DecisionTableOutputBean input = (DecisionTableOutputBean) iter;
								if (DataTypeUtils.equals(inputDcParam.getParentDecisionOutputBeanId(), input.getDecisionOutputBeanId())) {
									lstObjReturn = getParentLstFrmObjByTypeOfScope(isBlogic, scope, input, lstObjReturn, lstArgObj);
									break;
								}
							}
						}
						break;
					case ModuleScope.FUNCTION_MASTER:
						FunctionMethodOutput functionMethodOutput = (FunctionMethodOutput) obj;
						if (functionMethodOutput.getParentFunctionMethodOutputId() == null) {
							lstObjReturn.add(0, functionMethodOutput);
						} else {
							lstObjReturn.add(0, functionMethodOutput);
							for (Object iter : lstArgObj) {
								FunctionMethodOutput output = (FunctionMethodOutput) iter;
								if (DataTypeUtils.equals(functionMethodOutput.getParentFunctionMethodOutputId(), output.getMethodOutputId())) {
									lstObjReturn = getParentLstFrmObjByTypeOfScope(isBlogic, scope, output, lstObjReturn, lstArgObj);
									break;
								}
							}
						}
						break;
				}

				break;
		}

		return lstObjReturn;
	}

	/**
	 * @param loopComponents
	 * @param sequenceId
	 * @return
	 */
	protected LoopComponent getLoopComponentBySequence(List<LoopComponent> loopComponents, String sequenceId) {

		if (CollectionUtils.isNotEmpty(loopComponents)) {
			for (LoopComponent loopComponent : loopComponents) {
				if (DataTypeUtils.equals(sequenceId, loopComponent.getSequenceLogicId().toString())) {
					return loopComponent;
				}
			}
		}

		return null;
	}

	/**
	 * Get all child of father
	 *
	 * @param isBlogic (0 : isBlogic, 1 : Sql, 2 : Decision)
	 * @param scope
	 * @param id : id of father
	 * @return
	 */
	public List<Object> getAllChildByParent(int isBlogic, int scope, String id, List<?> lstArgObj) {
		List<Object> lstObjReturn = new ArrayList<Object>();

		switch (scope) {
			case TypeScope.INPUTBEAN:
				switch (isBlogic) {
					case ModuleScope.BLOGIC:
						for (Object iter : lstArgObj) {
							InputBean input = (InputBean) iter;
							if (DataTypeUtils.equals(id, input.getParentInputBeanId())) {
								lstObjReturn.add(input);
							}
						}
						break;
					case ModuleScope.SQL:
						for (Object iter : lstArgObj) {
							SqlDesignInput input = (SqlDesignInput) iter;
							if (DataTypeUtils.equals(id, input.getSqlDesignInputParentId())) {
								lstObjReturn.add(input);
							}
						}
						break;
					case ModuleScope.DECISION:
						break;
						
					case ModuleScope.FUNCTION_MASTER:
						for (Object iter : lstArgObj) {
							FunctionMethodInput input = (FunctionMethodInput) iter;
							if (DataTypeUtils.equals(id, input.getParentFunctionMethodInputId())) {
								lstObjReturn.add(input);
							}
						}
						break;
				}
				break;
			case TypeScope.OBJECTDEFINITION:
				switch (isBlogic) {
					case 0 :
						for (Object iter : lstArgObj) {
							ObjectDefinition objDef = (ObjectDefinition) iter;
							if (DataTypeUtils.equals(id, objDef.getParentObjectDefinitionId())) {
								lstObjReturn.add(objDef);
							}
						}
						break;
					case 1 :
						break;
					case 2 :
						break;
				}

				break;
			case TypeScope.OUTPUTBEAN:
				switch (isBlogic) {
					case ModuleScope.BLOGIC:
						for (Object iter : lstArgObj) {
							OutputBean output = (OutputBean) iter;
							if (DataTypeUtils.equals(id, output.getParentOutputBeanId())) {
								lstObjReturn.add(output);
							}
						}
						break;
					case ModuleScope.SQL:
						break;
					case ModuleScope.DECISION:
						break;
				}
				break;
		}

		return lstObjReturn;
	}

	/**
	 * Get setting of formula detail
	 * 
	 * @param paramIO
	 * @param lstFormulaDefinitionDetails
	 * @return
	 */
	public List<String> generateConditionByFormula(BLogicHandlerIo paramIO, List<FormulaDetail> lstFormulaDefinitionDetails) {

		StringBuilder sbSetGet  = new StringBuilder(StringUtils.EMPTY);

		String condition = "";
		if (CollectionUtils.isNotEmpty(lstFormulaDefinitionDetails)) {
			for (FormulaDetail detail : lstFormulaDefinitionDetails) {
				if (GenerateSourceCodeConst.formulaType.containsKey(detail.getType())) {
					condition += GenerateSourceCodeConst.formulaType.get(detail.getType()) + " ";
				} else if (detail.getType() == GenerateSourceCodeConst.FORMULA_TYPE_NEW_OBJECT) {
					if (StringUtils.isNotEmpty(paramIO.getAssignObjectForFormula())) {
						condition += paramIO.getAssignObjectForFormula();
					}
					paramIO.setAssignObjectForFormula("");
				} else {
					// if is input bean
					if (detail.getType().equals(FormulaBuilder.TYPE_IN_BUSINESSLOGIC)) {
						
						switch (paramIO.getModuleScope()) {
						
							/** Decision Table */
							case 2:
								if(CollectionUtils.isNotEmpty(paramIO.getDecisionTable().getInputLst())) {
									for (DecisionTableInputBean in : paramIO.getDecisionTable().getInputLst()) {
										if (DataTypeUtils.equals(in.getDecisionInputBeanId().toString(), detail.getParameterId())) {
											condition += this.getterAndSetterOfParameter(ModuleScope.DECISION , paramIO.getmAllParentAndSeflByLevelOfInOutObj(), true, in.getDecisionInputBeanId(), 
													BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, paramIO.getBlogicInSyntax(), detail.getLstParameterIndex());
											break;
										}
									}
								}
								break;

							default:
								/* Defualt is Blogic */
								if(CollectionUtils.isNotEmpty(paramIO.getBusinessDesign().getLstInputBean())) {
									for (InputBean in : paramIO.getBusinessDesign().getLstInputBean()) {
										if (DataTypeUtils.equals(in.getInputBeanId(), detail.getParameterId())) {
											condition += this.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), true, in.getInputBeanId(), 
													BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, paramIO.getBlogicInSyntax(), detail.getLstParameterIndex());
											break;
										}
									}
								}
								break;
						}
					}
					// if is output
					else if (detail.getType().equals(FormulaBuilder.TYPE_OU_BUSINESSLOGIC) && CollectionUtils.isNotEmpty(paramIO.getBusinessDesign().getLstOutputBean())) {
						for (OutputBean ou : paramIO.getBusinessDesign().getLstOutputBean()) {
							if (DataTypeUtils.equals(ou.getOutputBeanId(), detail.getParameterId())) {
								condition += this.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), true, ou.getOutputBeanId(), 2, paramIO.getBlogicOutputSyntax(), detail.getLstParameterIndex());
								break;
							}
						}
					}
					// if is object definition
					else if (detail.getType().equals(FormulaBuilder.TYPE_OB_BUSINESSLOGIC) && CollectionUtils.isNotEmpty(paramIO.getBusinessDesign().getLstObjectDefinition())) {
						for (ObjectDefinition ob : paramIO.getBusinessDesign().getLstObjectDefinition()) {
							if (DataTypeUtils.equals(ob.getObjectDefinitionId(), detail.getParameterId())) {
								condition += this.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), true, ob.getObjectDefinitionId(), 1, paramIO.getBlogicObSyntax(), detail.getLstParameterIndex());
								break;
							}
						}
					}
					// if is function
					else if (detail.getType().equals(17)) {

						FunctionMethod method = null;

						if (CollectionUtils.isNotEmpty(paramIO.getFunctionMethods())) {
							for (FunctionMethod item : paramIO.getFunctionMethods()) {
								if (detail.getFunctionMethodId() != null && detail.getFunctionMethodId().equals(item.getFunctionMethodId())) {
									method = item;
									break;
								}
							}
						}

						FunctionMaster func = null;

						if (CollectionUtils.isNotEmpty(paramIO.getFunctionMasters())) {
							for (FunctionMaster item : paramIO.getFunctionMasters()) {
								if (method != null && method.getFunctionMasterId() != null && method.getFunctionMasterId().equals(item.getFunctionMasterId())) {
									func = item;
									break;
								}
							}
						}

						if (method == null) continue;
						if (func == null) continue;
						
						if (GenerateSourceCodeConst.FUNCTION_TYPE_COMMON.equals(func.getFunctionMasterType())) {
							condition += "FunctionMasterUtils." + StringUtils.capitalize(func.getFunctionMasterCode()) + "." + method.getFunctionMethodCode();
						} else if(GenerateSourceCodeConst.FUNCTION_TYPE_CUSTOMIZE.equals(func.getFunctionMasterType())) {
							condition += ((!StringUtils.isEmpty(GenerateSourceCodeUtil.normalizedPackageName(func.getPackageName()))) ?
									GenerateSourceCodeUtil.normalizedPackageName(func.getPackageName()) + "." : "") + GenerateSourceCodeUtil.normalizedClassName(func.getFunctionMasterCode()) 
									+ "." + GenerateSourceCodeUtil.normalizedMethodName(method.getFunctionMethodCode());
						}
						
						if (StringUtils.isEmpty(condition)) continue;
						
						String methodParam = "(";
						Integer dataType = null;
						String parseParam = "";
						
						// In the case of method is common type
						if (GenerateSourceCodeConst.FUNCTION_TYPE_COMMON.equals(func.getFunctionMasterType())) {
							if (CollectionUtils.isNotEmpty(detail.getFormulaMethodInputs())) {
								for (int i = 0; i < detail.getFormulaMethodInputs().size(); i++) {
									FormulaMethodInput item = detail.getFormulaMethodInputs().get(i);

									if (item.getFormulaDetailId() != null && item.getFormulaDetailId().equals(detail.getFormulaDetailId())) {
										if (item.getParameterType() == null) {
											continue;
										}
										
										for (FunctionMethodInput functionMethodInput : method.getFunctionMethodInput()) {
											if (FunctionCommon.equals(functionMethodInput.getMethodInputId(), item.getMethodInputId())) {
												dataType = functionMethodInput.getDataType();
											}
										}

										// value
										if (Integer.valueOf(BusinessDesignConst.FormulaBuilder.PARAMETER_TYPE_VALUE).equals(item.getParameterType())) {
											if (FunctionCommon.equals(dataType, GenerateSourceCodeConst.DataType.STRING)) {
												parseParam += "\"" + item.getParameterValue() + "\"";
											} else {
												parseParam = item.getParameterValue();
											}
											
											methodParam += parseParam;
										} else {
											// input
											if (Integer.valueOf(BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_INPUT_BEAN).equals(item.getParameterScope())) {
												
												switch (paramIO.getModuleScope()) {
													// Decision
													case 2:
														methodParam += this.getterAndSetterOfParameter(ModuleScope.DECISION, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), true, item.getParameterId(), 
																BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, paramIO.getBlogicInSyntax(), item.getLstParameterIndex());
														break;
														// Blogic
													default : 
														methodParam += this.getterAndSetterOfParameter(ModuleScope.BLOGIC, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), true, item.getParameterId(), 
																BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, paramIO.getBlogicInSyntax(), item.getLstParameterIndex());
														break;
												}
											}
											// output
											if (Integer.valueOf(BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_OUTPUT_BEAN).equals(item.getParameterScope())) {
												methodParam += this.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), true, item.getParameterId(), 
														BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, paramIO.getBlogicOutputSyntax(), item.getLstParameterIndex());
											}
											// object definition
											if (Integer.valueOf(BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_OBJECT_DEFINITION).equals(item.getParameterScope())) {
												methodParam += this.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), true, item.getParameterId(), 
														BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, paramIO.getBlogicObSyntax(), item.getLstParameterIndex());
											}
										}
										
										if (i < detail.getFormulaMethodInputs().size() - 1) {
											methodParam += ", ";
										}
									}
								}
							}

							methodParam += ")";	
							condition += methodParam;
						// In the case of method is customize
						} else if(GenerateSourceCodeConst.FUNCTION_TYPE_CUSTOMIZE.equals(func.getFunctionMasterType())) {
							sbSetGet.append(buildSetterOfFunctionMaster(paramIO, detail, func, method)).append("\n\t\t");
							condition += "(" + GenerateSourceCodeUtil.normalizedMethodName(method.getFunctionMethodCode()) + detail.getFormulaDefinitionId().toString() + detail.getFormulaDetailId() + ")";
							condition += buildGetterOfFunctionMaster(detail, func, method);
						}
					}
					// if is value
					else if (detail.getType().equals(16)) {
						condition += detail.getValue() + " ";
					}
				}
			}
		}

		List<String> lstResult = new ArrayList<>();
		// add contion first
		lstResult.add(condition);
		// add setter after
		lstResult.add(sbSetGet.toString());
		
		return lstResult;
	}
	
	/**
	 * Getter of setting output function master
	 * 
	 * @param detail
	 * @param func
	 * @param method
	 * @return
	 */
	private String buildGetterOfFunctionMaster(FormulaDetail detail, FunctionMaster func, FunctionMethod method) {
		String getterOutput = "";
		if (CollectionUtils.isNotEmpty(detail.getFormulaMethodOutputs())) {
			
			Map<String, List<?>> mParentAndChildByLevel =  this.getAllParentAndSeflByLevelOfInOutObj(3, method.getFunctionMethodInput(), null, method.getFunctionMethodOutput());
			
			if(CollectionUtils.isNotEmpty(method.getFunctionMethodOutput())) {
				for (FunctionMethodOutput functionMethodOutput : method.getFunctionMethodOutput()) {
					for (int i = 0; i < detail.getFormulaMethodOutputs().size(); i++) {
						FormulaMethodOutput item = detail.getFormulaMethodOutputs().get(i);
						if (FunctionCommon.equals(functionMethodOutput.getMethodOutputId(), item.getMethodOutputId())) {
							// Build setter of output function master
							getterOutput = this.getterAndSetterOfParameter(3, mParentAndChildByLevel, IS_GETTER, functionMethodOutput.getMethodOutputId(), 2, StringUtils.EMPTY, null);
							break;
						}
					}
				}
			}
		}

		return getterOutput;
	}

	/**
	 * 
	 * 
	 * @param paramIO
	 * @param detail
	 * @param func
	 * @param method
	 * @return
	 */
	private String buildSetterOfFunctionMaster(BLogicHandlerIo paramIO, FormulaDetail detail, FunctionMaster func, FunctionMethod method) {
		StringBuilder sb = new StringBuilder(StringUtils.EMPTY);
		boolean isTwooArrayPrimitive = false;
		int dataTypeSrc = -1;
		String INIT_OBJECT = "{0} {1} = new {0}();";
		String INPUT_SETTER = "{0}({1});";
		
		if (CollectionUtils.isNotEmpty(detail.getFormulaMethodInputs())) {
			
			String inputSyntax = GenerateSourceCodeUtil.normalizedMethodName(method.getFunctionMethodCode()) 
					+ detail.getFormulaDefinitionId().toString() + detail.getFormulaDetailId();
			
			String mainPackage = ((!StringUtils.isEmpty(GenerateSourceCodeUtil.normalizedPackageName(func.getPackageName()))) 
					? func.getPackageName() + "." : StringUtils.EMPTY) + func.getFunctionMasterCode() + "." 
					+ StringUtils.uncapitalize(method.getFunctionMethodCode());
			
			mainPackage  = GenerateSourceCodeUtil.normalizedPackageName(mainPackage) + "." + StringUtils.capitalize(method.getFunctionMethodCode());

			sb.append("\n\t\t").append(MessageFormat.format("{0}InputBean {1} = new {0}InputBean();", 
					mainPackage, inputSyntax)).append("\n\t\t");
			
			mainPackage = mainPackage + GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_INPUT_BEAN;
			
			Map<String, List<?>> mParentAndChildByLevel =  this.getAllParentAndSeflByLevelOfInOutObj(ModuleScope.FUNCTION_MASTER, 
					method.getFunctionMethodInput(), null, method.getFunctionMethodOutput());
			
			FormulaMethodInput formulaMethodInputParam = null;
			String getterParam = StringUtils.EMPTY;
			String parseParam = StringUtils.EMPTY;
			
			// Variable for main processing
			String parentIdOfRefer = StringUtils.EMPTY;
			int countInputParam = 0;
			String getterParentInputList = StringUtils.EMPTY;
			String parentInputForSet = StringUtils.EMPTY;
			// Start is false
			Boolean isParentArray = false;
			String instanceNmObj = StringUtils.EMPTY;
			// Parent Object for getter
			String parentobjForGet = StringUtils.EMPTY;
			
			// Initial variable
			Object objMark = null;
			String parentId = StringUtils.EMPTY;
			String codeScope = StringUtils.EMPTY;
			boolean arrayFlg = false;
			boolean isPramaterValue = false;
			
			if(CollectionUtils.isNotEmpty(method.getFunctionMethodInput())) {
				for (FunctionMethodInput functionMethodInput : method.getFunctionMethodInput()) {
					countInputParam++;
					getterParam = StringUtils.EMPTY;
					parseParam = StringUtils.EMPTY;
					isTwooArrayPrimitive = false;
					for (int i = 0; i < detail.getFormulaMethodInputs().size(); i++) {
						FormulaMethodInput item = detail.getFormulaMethodInputs().get(i);
						if (FunctionCommon.equals(functionMethodInput.getMethodInputId(), item.getMethodInputId())) {
							formulaMethodInputParam = item;
							break;
						}
					}
					
					if(formulaMethodInputParam == null) continue;

					isPramaterValue = Integer.valueOf(BusinessDesignConst.FormulaBuilder.PARAMETER_TYPE_VALUE).equals(formulaMethodInputParam.getParameterType());
					
					switch (paramIO.getModuleScope()) {
						case ModuleScope.DECISION:
						
							if (isPramaterValue) {
								
								if (FunctionCommon.equals(functionMethodInput.getDataType(), GenerateSourceCodeConst.DataType.STRING)) {
									parseParam += "\"" + formulaMethodInputParam.getParameterValue() + "\"";
								} else {
									parseParam = formulaMethodInputParam.getParameterValue();
								}
								
								getterParam += parseParam;
							} else {
								dataTypeSrc = -1;
								// input
								if (Integer.valueOf(BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_INPUT_BEAN).equals(formulaMethodInputParam.getParameterScope())) {
									
									switch (paramIO.getModuleScope()) {
										case ModuleScope.DECISION:
											DecisionTableInputBean dti = (DecisionTableInputBean) this.getObjByTypeScope(ModuleScope.DECISION, 
													formulaMethodInputParam.getParameterScope(), formulaMethodInputParam.getParameterId(), 
													paramIO.getmAllParentAndSeflByLevelOfInOutObj().get(formulaMethodInputParam.getParameterScope() + formulaMethodInputParam.getParameterId()));
											getterParam = this.getterAndSetterOfParameter(ModuleScope.DECISION, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, formulaMethodInputParam.getParameterId(), 0, paramIO.getBlogicInSyntax(), formulaMethodInputParam.getLstParameterIndex());
											dataTypeSrc = dti.getDataType();
											break;
										default:
											InputBean tm = (InputBean) this.getObjByTypeScope(ModuleScope.BLOGIC, formulaMethodInputParam.getParameterScope(), formulaMethodInputParam.getParameterId(), paramIO.getmAllParentAndSeflByLevelOfInOutObj().get(formulaMethodInputParam.getParameterScope() + formulaMethodInputParam.getParameterId()));
											getterParam = this.getterAndSetterOfParameter(ModuleScope.BLOGIC, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, formulaMethodInputParam.getParameterId(), 0, paramIO.getBlogicInSyntax(), formulaMethodInputParam.getLstParameterIndex());
											dataTypeSrc = tm.getDataType();
											// Marking two list primitive
											if(Boolean.TRUE.equals(tm.getArrayFlg()) && functionMethodInput.getArrayFlg() == 1 && DataTypeUtils.notEquals(tm.getDataType(), functionMethodInput.getDataType())) isTwooArrayPrimitive = true;
											break;
									}
								// output
								}
							}
							
							if(StringUtils.isEmpty(getterParam)) continue;
							
							sb.append("\n\t\t");
							
							if(!isPramaterValue) {
								getterParam = BusinessLogicGenerateHelper.getContentByCastDataType(functionMethodInput.getDataType(), dataTypeSrc, getterParam);
							}
							
							String setterInReferParentDc = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mParentAndChildByLevel, IS_SETTER, functionMethodInput.getMethodInputId(), 0, inputSyntax, null)+"("+getterParam+");";
							sb.append(setterInReferParentDc);
							sb.append("\n\t\t");
						
						break;

					default:
						// Add new processing 
						if (formulaMethodInputParam.getParameterScope() != null && formulaMethodInputParam.getParameterId() != null) {
							objMark = NavigatorComponentGenerateHandler.getObjByTypeScope(formulaMethodInputParam.getParameterScope(), paramIO.getmAllParentAndSeflByLevelOfInOutObj().get(
									formulaMethodInputParam.getParameterScope() + formulaMethodInputParam.getParameterId()), formulaMethodInputParam.getParameterId());
						}
						
						if(objMark != null && !isPramaterValue) {
							dataTypeSrc = -1;
							if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(formulaMethodInputParam.getParameterScope())) {
								InputBean in = (InputBean) objMark;
								parentId = in.getParentInputBeanId();
								codeScope = paramIO.getBlogicInSyntax();
								dataTypeSrc = in.getDataType();
								arrayFlg = in.getArrayFlg();
							} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(formulaMethodInputParam.getParameterScope())) {
								ObjectDefinition objDef =  (ObjectDefinition) objMark;
								parentId = objDef.getParentObjectDefinitionId();
								codeScope = paramIO.getBlogicObSyntax();
								dataTypeSrc = objDef.getDataType();
								arrayFlg = objDef.getArrayFlg();
							} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(formulaMethodInputParam.getParameterScope())) {
								OutputBean ou =  (OutputBean) objMark;
								parentId = ou.getParentOutputBeanId();
								codeScope = paramIO.getBlogicOutputSyntax();
								dataTypeSrc = ou.getDataType();
								arrayFlg = ou.getArrayFlg();
							}
						
							if(BusinessDesignConst.DataType.ENTITY.equals(functionMethodInput.getDataType()) 
									|| BusinessDesignConst.DataType.OBJECT.equals(functionMethodInput.getDataType())
									|| BusinessDesignConst.DataType.COMMON_OBJECT.equals(functionMethodInput.getDataType()) 
									|| BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(functionMethodInput.getDataType())) {
								// Marking value
								parentIdOfRefer = functionMethodInput.getMethodInputId();
								
								if(isPramaterValue) continue;
								
								if(Boolean.FALSE.equals(functionMethodInput.getArrayFlg())) {
									// Marking value
									if(Boolean.TRUE.equals(isParentArray)) {
										sb.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t};"));
										isParentArray = false;
									}

									String getterInReferParent = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mParentAndChildByLevel, 
											true, functionMethodInput.getMethodInputId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null);
									
									// Get name of instance object
									instanceNmObj = this.getNameDeclareObjByScope(ModuleScope.FUNCTION_MASTER, 
											BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, functionMethodInput);
									// Get new instance of object data type
									String instanceOf = "new " + this.getPackageName(false, paramIO, functionMethodInput, mainPackage) 
											+ GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + "()";
									sb.append(String.format("if (%s == null){", getterInReferParent)).append("\n\t\t");
									
									String setterInReferParent = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mParentAndChildByLevel, 
											false, functionMethodInput.getMethodInputId(), 0, inputSyntax, null)+"("+instanceOf+");";
									
									sb.append("\t").append(setterInReferParent).append("\n\t\t}").append("\n\t\t");
								} else {

									if(Boolean.TRUE.equals(isParentArray)) {
										 sb.append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t};"));
											isParentArray = false;
										}
									// Marking value
									isParentArray = true;
									parentInputForSet = functionMethodInput.getMethodInputCode()+"Set";

									String getterParentRefer =  this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mParentAndChildByLevel,true,
											functionMethodInput.getMethodInputId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null);
									
									// Get name of instance object
									instanceNmObj = this.getNameDeclareObjByScope(ModuleScope.FUNCTION_MASTER, 
											BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, functionMethodInput);
									// Get new instance of object data type
									String instanceOf = "new ArrayList<" + this.getPackageName(false, paramIO, functionMethodInput, mainPackage) 
											+ GenerateSourceCodeUtil.normalizedClassName(instanceNmObj) + ">()";
									
									String setterInReferParent = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mParentAndChildByLevel, false, 
											functionMethodInput.getMethodInputId(), 0, inputSyntax, null);
									
									sb.append("if(" + getterParentRefer + " " + "== null) {").append("\n\t\t\t");
									sb.append(setterInReferParent + "("+instanceOf+");").append("\n\t\t");
									sb.append("}").append("\n\t\t");
									
									sb.append(String.format("%s.clear();", getterParentRefer));
									
									// Building for each
									String declareObjCode = this.getNameDeclareObjByScope(ModuleScope.BLOGIC, formulaMethodInputParam.getParameterScope(), objMark);
									
									parentobjForGet = declareObjCode + "Get";
									
									String declareObj = this.getPackageName(true, paramIO, objMark, null) + GenerateSourceCodeUtil.normalizedClassName(declareObjCode);

									String getterParentParam =  this.getterAndSetterOfParameter(ModuleScope.BLOGIC, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), 
											true, formulaMethodInputParam.getParameterId(), formulaMethodInputParam.getParameterScope(), codeScope, null);
									sb.append("\n\t\t").append(String.format("for (%s "+parentobjForGet+" : %s){", declareObj, getterParentParam)).append("\n\t\t\t");
									
									// Build new temporary object from input refer
									sb.append(MessageFormat.format(INIT_OBJECT, this.getPackageName(false, paramIO, functionMethodInput, mainPackage) 
											+ GenerateSourceCodeUtil.normalizedClassName(instanceNmObj), parentInputForSet)).append("\n\t\t\t");;
									
									// Using for add one item into the list object
									getterParentInputList = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mParentAndChildByLevel, true, 
											functionMethodInput.getMethodInputId(), 0, inputSyntax, null);
								}
								
								continue;
							}
						}
						
						String getter = "";
						getterParam = StringUtils.EMPTY;
						// Start Setter/ Getter of properties
						
						if (isPramaterValue) {
							
							if (FunctionCommon.equals(functionMethodInput.getDataType(), GenerateSourceCodeConst.DataType.STRING)) {
								parseParam += "\"" + formulaMethodInputParam.getParameterValue() + "\"";
							} else {
								parseParam = formulaMethodInputParam.getParameterValue();
							}
							
							getterParam += parseParam;
						}
						
						// Marking two list primitive
						if(Boolean.TRUE.equals(functionMethodInput.getArrayFlg()) && arrayFlg && 
								DataTypeUtils.notEquals(functionMethodInput.getDataType(), dataTypeSrc)) isTwooArrayPrimitive = true;
						
						// In the case item is top level
						if (StringUtils.isEmpty(functionMethodInput.getParentFunctionMethodInputId())) {

							if(Boolean.TRUE.equals(isParentArray)) {
								sb.append(String.format("\n\t\t\t")).append(getterParentInputList + ".add("+parentInputForSet+");").append(String.format("\n\t\t};")).append(String.format("\n\t\t"));
								isParentArray = false;
							}

							if(isTwooArrayPrimitive && isPramaterValue) {
								sb.append("\n\t\t");
								String targetNull = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER , mParentAndChildByLevel, IS_GETTER, String.valueOf(functionMethodInput.getFunctionMethodId()),
										BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null);
								sb.append(String.format("if (%s == null) {", targetNull));
								sb.append("\n\t\t\t");
								String dataType = GenerateSourceCodeUtil.getPrimitiveTypeName(functionMethodInput.getDataType());
								String targetSet = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mParentAndChildByLevel, IS_SETTER, String.valueOf(functionMethodInput.getFunctionMethodId()),
										BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null)+"(new ArrayList<"+dataType+">());";
								sb.append(targetSet);
								sb.append("\n\t\t}");
								sb.append("\n\t\t\t");
								sb.append(String.format("%s.clear();", targetNull));
								sb.append("\n\t\t\t");
								
								getterParam = this.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), 
										IS_GETTER, formulaMethodInputParam.getParameterId(), formulaMethodInputParam.getParameterScope(), 
										codeScope, formulaMethodInputParam.getLstParameterIndex());
								
								if(GenerateSourceCodeConst.DataType.BYTE == dataTypeSrc){
									sb.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
								} else {
									sb.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
								}

								sb.append("\n\t\t\t");
								sb.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc), "iter", getterParam));
								String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(functionMethodInput.getDataType(), dataTypeSrc, "iter");
								sb.append("\n\t\t\t\t");
								String setterInReferParent = this.getterAndSetterOfParameter(0, mParentAndChildByLevel, IS_GETTER, 
										String.valueOf(functionMethodInput.getMethodInputId()), 0, inputSyntax , formulaMethodInputParam.getLstParameterIndex())+".add("+paramInput+");";
								sb.append(setterInReferParent);
								sb.append("\n\t\t\t");
								sb.append("}");
								sb.append("\n\t\t");
								sb.append("}");
								sb.append("\n\t\t");
							} else {
								
								if(isPramaterValue) {
									getter = getterParam;
								} else {
									getter = this.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, formulaMethodInputParam.getParameterId(), 
											formulaMethodInputParam.getParameterScope(), codeScope , formulaMethodInputParam.getLstParameterIndex());
									// Cast data type
									getter = BusinessLogicGenerateHelper.getContentByCastDataType(functionMethodInput.getDataType(), dataTypeSrc, getter);
								}
								
								// setter of child
								String setterChild = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mParentAndChildByLevel, IS_SETTER, 
										String.valueOf(functionMethodInput.getFunctionMethodId()), 0, inputSyntax, null);
								
								sb.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t\t");
							}
						// In the case item is top level	
						} else if(!isParentArray && parentIdOfRefer != null && parentIdOfRefer.equals(functionMethodInput.getParentFunctionMethodInputId())) {

							if(isTwooArrayPrimitive && !isPramaterValue) {
								sb.append("\n\t\t");
								String targetNull = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mParentAndChildByLevel, IS_GETTER, String.valueOf(functionMethodInput.getFunctionMethodId()), 
										BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null);
								sb.append(String.format("if (%s == null) {", targetNull));
								sb.append("\n\t\t\t");
								String dataType = GenerateSourceCodeUtil.getPrimitiveTypeName(functionMethodInput.getDataType());
								String targetSet = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mParentAndChildByLevel, IS_SETTER, String.valueOf(functionMethodInput.getFunctionMethodId()), 
										BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, inputSyntax, null)+"(new ArrayList<"+ dataType +">());";
								sb.append(targetSet);
								sb.append("\n\t\t}");
								sb.append("\n\t\t\t");
								sb.append(String.format("%s.clear();", targetNull));
								sb.append("\n\t\t\t");
								
								getterParam = this.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, 
										formulaMethodInputParam.getParameterId(), formulaMethodInputParam.getParameterScope(), codeScope, 
										formulaMethodInputParam.getLstParameterIndex());
								
								if(GenerateSourceCodeConst.DataType.BYTE == dataTypeSrc){
									sb.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
								} else {
									sb.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
								}
								
								sb.append("\n\t\t\t");
								sb.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc), "iter", getterParam));
								String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(functionMethodInput.getDataType(), dataTypeSrc, "iter");
								sb.append("\n\t\t\t\t");
								String setterInReferParent = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mParentAndChildByLevel, IS_GETTER, 
										String.valueOf(functionMethodInput.getMethodInputId()), 0, inputSyntax , null)+".add("+paramInput+");";
								sb.append(setterInReferParent);
								sb.append("\n\t\t\t");
								sb.append("}");
								sb.append("\n\t\t");
								sb.append("}");
								sb.append("\n\t\t");
							} else {
								
								if(isPramaterValue) {
									getter = getterParam;
								} else {
									getter = this.getterAndSetterOfParameter(0, paramIO.getmAllParentAndSeflByLevelOfInOutObj(), IS_GETTER, 
											formulaMethodInputParam.getParameterId(), formulaMethodInputParam.getParameterScope(), codeScope, formulaMethodInputParam.getLstParameterIndex());
									// Cast data type
									getter = BusinessLogicGenerateHelper.getContentByCastDataType(functionMethodInput.getDataType(), dataTypeSrc, getter);
								}
		
								// setter of child
								String setterChild = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mParentAndChildByLevel, IS_SETTER, 
										String.valueOf(functionMethodInput.getMethodInputId()), 0, inputSyntax, null);
		
								sb.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t\t");
							}
						} else if (isParentArray && parentIdOfRefer != null && parentIdOfRefer.equals(functionMethodInput.getParentFunctionMethodInputId())){
							
							// setter of child of parentInputForSet
							List<?> listInputReferOfParent = this.getAllChildByParent(ModuleScope.FUNCTION_MASTER, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, 
									parentIdOfRefer, method.getFunctionMethodInput());
							Map<String, List<?>> mAllInputReferTmp = this.getAllParentAndSeflByLevelOfInOutObj(ModuleScope.FUNCTION_MASTER, listInputReferOfParent, null, null);
							
							List<?> listObjParamOfParent = null;
							Map<String, List<?>> mAllBlogicCurrentTmp = null;
							
							if(!isPramaterValue){
								if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(formulaMethodInputParam.getParameterScope())) {
									listObjParamOfParent = this.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, 
											parentId, paramIO.getBusinessDesign().getLstInputBean());
									mAllBlogicCurrentTmp = this.getAllParentAndSeflByLevelOfInOutObj(0, listObjParamOfParent, null, null);
								} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(formulaMethodInputParam.getParameterScope())) {
									listObjParamOfParent = this.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, 
											parentId, paramIO.getBusinessDesign().getLstObjectDefinition());
									mAllBlogicCurrentTmp = this.getAllParentAndSeflByLevelOfInOutObj(0, null, listObjParamOfParent, null);
								} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(formulaMethodInputParam.getParameterScope())) {
									listObjParamOfParent = this.getAllChildByParent(0, BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID, 
											parentId, paramIO.getBusinessDesign().getLstOutputBean());
									mAllBlogicCurrentTmp = this.getAllParentAndSeflByLevelOfInOutObj(0, null, null, listObjParamOfParent);
								}
							}

							if(mAllBlogicCurrentTmp == null && !isPramaterValue) continue;

							if(isTwooArrayPrimitive && !isPramaterValue) {
								sb.append("\n\t\t\t");
								String targetNull = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mAllInputReferTmp, IS_GETTER, 
										String.valueOf(functionMethodInput.getFunctionMethodId()), 0, parentInputForSet, null);
								sb.append(String.format("if (%s == null) {", targetNull));
								sb.append("\n\t\t\t\t");
								String dataType = GenerateSourceCodeUtil.getPrimitiveTypeName(functionMethodInput.getDataType());
								String targetSet = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mAllInputReferTmp, IS_SETTER, 
										String.valueOf(functionMethodInput.getFunctionMethodId()), 0, parentInputForSet, null)+"(new ArrayList<"+dataType+">());";
								sb.append(targetSet);
								sb.append("\n\t\t\t}");
								sb.append("\n\t\t\t");
								sb.append(String.format("%s.clear();", targetNull));
								sb.append("\n\t\t\t");
								getterParam = this.getterAndSetterOfParameter(0, mAllBlogicCurrentTmp, IS_GETTER, formulaMethodInputParam.getParameterId(), 
										formulaMethodInputParam.getParameterScope(), parentobjForGet, formulaMethodInputParam.getLstParameterIndex());
								
								if(GenerateSourceCodeConst.DataType.BYTE == dataTypeSrc){
									sb.append(String.format("if (%s != null && %s.length > 0) {", getterParam, getterParam));
								} else {
									sb.append(String.format("if (%s != null && %s.size() > 0) {", getterParam, getterParam));
								}
							
								sb.append("\n\t\t\t\t");
								sb.append(String.format("for (%s %s : %s) {", GenerateSourceCodeUtil.getPrimitiveTypeName(dataTypeSrc), "iter", getterParam));
								String paramInput = BusinessLogicGenerateHelper.getContentByCastDataType(functionMethodInput.getDataType(), dataTypeSrc, "iter");
								sb.append("\n\t\t\t\t\t");
								String setterInReferParent = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mAllInputReferTmp, IS_GETTER, 
										String.valueOf(functionMethodInput.getMethodInputId()), 0, parentInputForSet, null)+".add("+paramInput+");";
								sb.append(setterInReferParent);
								sb.append("\n\t\t\t\t");
								sb.append("}");
								sb.append("\n\t\t\t");
								sb.append("}");
								sb.append("\n\t\t\t");
		
								if(countInputParam == method.getFunctionMethodInput().size()) {
									sb.append(getterParentInputList + ".add("+parentInputForSet+");").append("\n\t\t}");
								}
							} else {
								
								if(isPramaterValue){
									getter = getterParam;
								} else {
									getter = this.getterAndSetterOfParameter(ModuleScope.BLOGIC, mAllBlogicCurrentTmp, IS_GETTER, formulaMethodInputParam.getParameterId(), 
											formulaMethodInputParam.getParameterScope(), parentobjForGet, formulaMethodInputParam.getLstParameterIndex());
									// Cast data type
									getter = BusinessLogicGenerateHelper.getContentByCastDataType(functionMethodInput.getDataType(), dataTypeSrc, getter);
								}

								String setterChild = this.getterAndSetterOfParameter(ModuleScope.FUNCTION_MASTER, mAllInputReferTmp, IS_SETTER, 
										String.valueOf(functionMethodInput.getMethodInputId()), 0, parentInputForSet, null);
		
								sb.append(MessageFormat.format(INPUT_SETTER, setterChild, getter)).append("\n\t\t\t");
		
								if(countInputParam == method.getFunctionMethodInput().size()){
									sb.append(getterParentInputList + ".add("+parentInputForSet+");").append("\n\t\t}");
								}
							}
						}

						break;
					}
					
					// End setter/ getter of properties
					// End add new processing
				}
			}
		}
		
		return sb.toString();
	}

	private String getPackageName(Boolean isBlogic, BLogicHandlerIo  paramIO, Object obj, String mainPackage) {
		StringBuilder pakageName = new StringBuilder(paramIO.getProject().getPackageName());
		String pakageExternal = StringUtils.EMPTY;
		String code = null;
		int dataType = -1;
		String place = ".domain.service.";
		String moduleCode = StringUtils.EMPTY;
		
		if(BusinessDesignConst.MODULE_TYPE_BATCH.equals(paramIO.getModule().getModuleType())) {
			place = ".batch.service.";
		}
		
		if(isBlogic && obj instanceof InputBean) {
			InputBean in = (InputBean) obj;
			dataType = in.getDataType();
			pakageExternal = in.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_INPUT_BEAN;
			moduleCode = in.getModuleCode();
		} else if (isBlogic && obj instanceof ObjectDefinition) {
			ObjectDefinition objDef = (ObjectDefinition) obj;
			dataType = objDef.getDataType();
			pakageExternal = objDef.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OBJ_DEFINITION;
			moduleCode = objDef.getModuleCode();
		} else if (isBlogic && obj instanceof OutputBean) {
			OutputBean out = (OutputBean) obj;
			dataType = out.getDataType();
			pakageExternal = out.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_OUTPUT_BEAN;
			moduleCode = out.getModuleCode();
		} else if (!isBlogic && obj instanceof FunctionMethodInput) {
			FunctionMethodInput in = (FunctionMethodInput) obj;
			dataType = in.getDataType();
			pakageExternal = in.getPackageNameObjExt();
			code = GenerateSourceCodeConst.BusinessLogicGenerate.SUFFIX_INPUT_BEAN;
		}

		switch (dataType) {
			case TypeOfDataType.OBJECT :
				if(isBlogic) {
					pakageName.append(place).append(paramIO.getModule().getModuleCode()).append(".")
						.append(paramIO.getBusinessDesign().getBusinessLogicCode()).append(code).append(".");
				} else {
					pakageName.setLength(0);
					pakageName.append(mainPackage).append(".");
				}
				
				break;
			case TypeOfDataType.ENTITY :
				if(BusinessDesignConst.MODULE_TYPE_ONLINE.equals(paramIO.getModule().getModuleType())) {
					pakageName.append(".domain.model.");
				} else {
					pakageName.append(".batch.model.");
				}
				break;
			case TypeOfDataType.COMMON_OBJECT :
				if(BusinessDesignConst.MODULE_TYPE_ONLINE.equals(paramIO.getModule().getModuleType())) {
					pakageName.append(".domain.commonobject.");
				} else {
					pakageName.append(".batch.commonobject.");
				}
				
				if(StringUtils.isNotEmpty(moduleCode)) {
					pakageName.append(moduleCode).append(".");
				}
				break;
			case TypeOfDataType.EXTERNAL_OBJECT :
				pakageName.setLength(0);
				pakageName.append(pakageExternal).append(".");
				break;
		}

		return GenerateSourceCodeUtil.normalizedPackageName(pakageName.toString());
	}
	
	/**
	 * Get Object type scope
	 * 
	 * @param isBusinessScope [0 : Business , 1 : Sql, 2 : Decision]
	 * @param scope [0 : Input, 1 : Object Definition, 2 : Output]
	 * @param lstBbj
	 * @param id
	 * @return
	 */
	public Object getObjByTypeScope(int isBusinessScope, int scope, String id, List<?> lstBbj) {
		String idTmp = StringUtils.EMPTY;

		if (CollectionUtils.isNotEmpty(lstBbj)) {
			for(Object obj : lstBbj) {
				switch (scope) {
				case TypeScope.INPUTBEAN:
					switch (isBusinessScope) {
						case ModuleScope.BLOGIC:
							InputBean ib = (InputBean) obj;
							idTmp = ib.getInputBeanId();
							break;
	
						case ModuleScope.SQL:
							
							break;
							
						case ModuleScope.DECISION:
							DecisionTableInputBean dti = (DecisionTableInputBean) obj;
							idTmp = dti.getDecisionInputBeanId();
							break;
					}
					
					break;
				case TypeScope.OBJECTDEFINITION:
					switch (isBusinessScope) {
						case 0:
							ObjectDefinition od = (ObjectDefinition) obj;
							idTmp = od.getObjectDefinitionId();
							break;
						case 1:break;
							
						case 2:break;
					}
					
					break;
				case TypeScope.OUTPUTBEAN:
					switch (isBusinessScope) {
						case ModuleScope.BLOGIC:
							OutputBean ob = (OutputBean) obj;
							idTmp = ob.getOutputBeanId();
							break;
						case ModuleScope.SQL:break;
							
						case ModuleScope.DECISION:break;

					}
					
					break;
				}
				
				if (DataTypeUtils.equals(idTmp, id)) {
					return obj;
				}
			}
		}
		
		return new Object();
	}
	
	/**
	 * Get declare of object name
	 * 
	 * @param blogicScope (0 : Business Design, 1 : Sql, 2 : Decision)
	 * @param scope (0 :InputBean, 1 : ObjectDefinition, 2 : Outputbean)
	 * @param obj
	 * @return
	 */
	public String getNameDeclareObjByScope(int blogicScope, int scope, Object obj) {
		String nameDeclareObj = StringUtils.EMPTY;
		Integer dataType = -1;
		String objCode = StringUtils.EMPTY;
		String entityCode = StringUtils.EMPTY;
		String commonObjCode = StringUtils.EMPTY;
		String externalObjCode = StringUtils.EMPTY;

		switch (scope) {
		case TypeScope.INPUTBEAN:
			switch (blogicScope) {
				case ModuleScope.BLOGIC:
					InputBean in = (InputBean) obj;
					dataType = in.getDataType();
					objCode = in.getInputBeanCode();
					entityCode = in.getTblDesignCode();
					commonObjCode = in.getCommonObjDefiCode();
					externalObjCode = in.getExternalObjDefiCode();
					break;
				case ModuleScope.SQL:
					SqlDesignInput sin = (SqlDesignInput) obj;
					dataType = sin.getDataType();
					objCode = sin.getSqlDesignInputCode();
					entityCode = sin.getTableCode();
					commonObjCode = sin.getCommonObjCode();
					externalObjCode = sin.getExternalObjCode();
					break;
				case ModuleScope.DECISION:
					DecisionTableInputBean dti = (DecisionTableInputBean) obj;
					dataType = dti.getDataType();
					objCode = dti.getDecisionInputBeanCode();
					commonObjCode = dti.getCommonObjDefiCode();
					externalObjCode = dti.getExternalObjDefiCode();
					break;
					
				case ModuleScope.FUNCTION_MASTER:
					FunctionMethodInput fmi = (FunctionMethodInput) obj;
					dataType = fmi.getDataType();
					objCode = fmi.getMethodInputCode();
					commonObjCode = fmi.getCommonObjDefiCode();
					externalObjCode = fmi.getExternalObjDefiCode();
					break;
			}
			
			break;
		case TypeScope.OBJECTDEFINITION:
			switch (blogicScope) {
			case 0:
				ObjectDefinition ob = (ObjectDefinition) obj;
				dataType = ob.getDataType();
				objCode = ob.getObjectDefinitionCode();
				entityCode = ob.getTblDesignCode();
				commonObjCode = ob.getCommonObjDefiCode();
				externalObjCode = ob.getExternalObjDefiCode();
				break;
			case 1:
				break;
			case 2:
				break;
			}
			
			break;
		case TypeScope.OUTPUTBEAN:
			switch (blogicScope) {
			case ModuleScope.BLOGIC:
				OutputBean ou = (OutputBean) obj;
				dataType = ou.getDataType();
				objCode = ou.getOutputBeanCode();
				entityCode = ou.getTblDesignCode();
				commonObjCode = ou.getCommonObjDefiCode();
				externalObjCode = ou.getExternalObjDefiCode();
				break;
			case ModuleScope.SQL:

				break;
			case ModuleScope.DECISION:
				DecisionTableOutputBean dto = (DecisionTableOutputBean) obj;
				dataType = dto.getDataType();
				objCode = dto.getDecisionOutputBeanCode();
				commonObjCode = dto.getCommonObjDefiCode();
				externalObjCode = dto.getExternalObjDefiCode();
				break;
			}

			break;
		}

		switch (dataType) {
		case GenerateSourceCodeConst.DataType.OBJECT:
			nameDeclareObj = objCode;
			break;
		case GenerateSourceCodeConst.DataType.ENTITY:
			nameDeclareObj = entityCode;
			break;
		case GenerateSourceCodeConst.DataType.COMMON_OBJECT:
			nameDeclareObj = commonObjCode;
			break;
		case GenerateSourceCodeConst.DataType.EXTERNAL_OBJECT:
			nameDeclareObj = externalObjCode;
			break;
		}

		return nameDeclareObj;
	}

	/* End adding HungHX */

	public Map<String, String> setLevelSqlInputOutput(List<SqlDesignInput> lstSqlDesignInputs, List<SqlDesignOutput> lstSqlDesignOutputs) {
		Map<String, String> sqlNameParameter = new HashMap<String, String>();
		Map<String, String> mapTableIndex = new HashMap<String, String>();
		// set level of sql input
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
		for (SqlDesignInput in : lstSqlDesignInputs) {
			String code = "";
			String currentGroup = "";
			if (in.getSqlDesignInputParentId() != null) {
				currentGroup = mapTableIndex.get(in.getSqlDesignInputParentId().toString());

			}
			in.setGroupId(currentGroup);
			int maxIndex = mapSequence.getOrDefault(in.getGroupId(), 0);
			maxIndex++;
			if (in.getSqlDesignInputParentId() == null) {
				in.setGroupIndex(String.valueOf(maxIndex));
				// code = BusinessDesignConst.PREFIX_MAPPING.SQL_INPUT_CODE +
				// "." + in.getSqlDesignInputCode();
				code = in.getSqlDesignInputCode();
			} else {
				in.setGroupIndex(currentGroup + "." + maxIndex);
				code = sqlNameParameter.getOrDefault(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID + in.getSqlDesignInputParentId().toString(), "");
				code += "." + in.getSqlDesignInputCode();
			}
			mapTableIndex.put(in.getSqlDesignInputId().toString(), in.getGroupIndex());
			mapSequence.put(in.getGroupId(), maxIndex);
			sqlNameParameter.put(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID + in.getSqlDesignInputId().toString(), code);
		}
		mapTableIndex.clear();
		mapSequence.clear();
		// process sql output
		for (SqlDesignOutput ou : lstSqlDesignOutputs) {
			String code = "";
			String currentGroup = "";
			if (ou.getSqlDesignOutputParentId() != null) {
				currentGroup = mapTableIndex.get(ou.getSqlDesignOutputParentId().toString());
			}
			ou.setGroupId(currentGroup);
			int maxIndex = mapSequence.getOrDefault(ou.getGroupId(), 0);
			maxIndex++;
			if (ou.getSqlDesignOutputParentId() == null) {
				ou.setGroupIndex(String.valueOf(maxIndex));
				// code = BusinessDesignConst.PREFIX_MAPPING.SQL_OUTPUT_CODE +
				// "." + ou.getSqlDesignOutputCode();
				code = ou.getSqlDesignOutputCode();
			} else {
				ou.setGroupIndex(currentGroup + "." + maxIndex);
				code = sqlNameParameter.getOrDefault(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID + ou.getSqlDesignOutputParentId().toString(), "");
				code += "." + ou.getSqlDesignOutputCode();
			}
			mapTableIndex.put(ou.getSqlDesignOutputId().toString(), ou.getGroupIndex());
			mapSequence.put(ou.getGroupId(), maxIndex);
			sqlNameParameter.put(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID + ou.getSqlDesignOutputId().toString(), code);
		}
		return sqlNameParameter;
	}

	public static Map<String, String> setLevelOfInputBeanOutputBeanObjectDefinition(List<InputBean> lstInputBean, List<OutputBean> lstOutputBean, List<ObjectDefinition> lstObjectDefinition) {
		Map<String, String> mapTableIndex = new HashMap<String, String>();
		mNameParameter = new HashMap<String, String>();
		// set level of input bean
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
		if(FunctionCommon.isNotEmpty(lstInputBean)){
			for (InputBean in : lstInputBean) {
				String code = "";
				String currentGroup = "";
				if (in.getParentInputBeanId() != null) {
					currentGroup = mapTableIndex.get(in.getParentInputBeanId());
				}
				in.setGroupId(currentGroup);
				int maxIndex = mapSequence.getOrDefault(in.getGroupId(), 0);
				maxIndex++;
				if (in.getParentInputBeanId() == null) {
					in.setTableIndex(String.valueOf(maxIndex));
					code = BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_CODE + "." + in.getInputBeanCode();
				} else {
					in.setTableIndex(currentGroup + "." + maxIndex);
					code = mNameParameter.getOrDefault(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID + in.getParentInputBeanId(), "");
					code += "." + in.getInputBeanCode();
				}
				mapTableIndex.put(in.getInputBeanId(), in.getTableIndex());
				mapSequence.put(in.getGroupId(), maxIndex);
				mNameParameter.put(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID + in.getInputBeanId(), code);
			}
		}

		mapTableIndex.clear();
		mapSequence.clear();
		// process output bean
		if(FunctionCommon.isNotEmpty(lstOutputBean)){
			for (OutputBean ou : lstOutputBean) {
				String code = "";
				String currentGroup = "";
				if (ou.getParentOutputBeanId() != null) {
					currentGroup = mapTableIndex.get(ou.getParentOutputBeanId());
				}
				ou.setGroupId(currentGroup);
				int maxIndex = mapSequence.getOrDefault(ou.getGroupId(), 0);
				maxIndex++;
				if (ou.getParentOutputBeanId() == null) {
					ou.setTableIndex(String.valueOf(maxIndex));
					code = BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_CODE + "." + ou.getOutputBeanCode();
				} else {
					ou.setTableIndex(currentGroup + "." + maxIndex);
					code = mNameParameter.getOrDefault(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID + ou.getParentOutputBeanId(), "");
					code += "." + ou.getOutputBeanCode();
				}
				mapTableIndex.put(ou.getOutputBeanId(), ou.getTableIndex());
				mapSequence.put(ou.getGroupId(), maxIndex);
				mNameParameter.put(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID + ou.getOutputBeanId(), code);
			}
		}

		// process object definition bean
		mapTableIndex.clear();
		mapSequence.clear();
		if(FunctionCommon.isNotEmpty(lstObjectDefinition)){
			for (ObjectDefinition ob : lstObjectDefinition) {
				String code = "";
				String currentGroup = "";
				if (ob.getParentObjectDefinitionId() != null) {
					currentGroup = mapTableIndex.get(ob.getParentObjectDefinitionId());
				}
				ob.setGroupId(currentGroup);
				int maxIndex = mapSequence.getOrDefault(ob.getGroupId(), 0);
				maxIndex++;
				if (ob.getParentObjectDefinitionId() == null) {
					ob.setTableIndex(String.valueOf(maxIndex));
					code = BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_CODE + "." + ob.getObjectDefinitionCode();
				} else {
					ob.setTableIndex(currentGroup + "." + maxIndex);
					code = mNameParameter.getOrDefault(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID + ob.getParentObjectDefinitionId(), "");
					code += "." + ob.getObjectDefinitionCode();
				}
				mapTableIndex.put(ob.getObjectDefinitionId(), ob.getTableIndex());
				mapSequence.put(ob.getGroupId(), maxIndex);
				mNameParameter.put(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID + ob.getObjectDefinitionId(), code);
			}
		}
		return mNameParameter;
	}

	public List<LoopComponent> getAllLoopComponents() {
		return allLoopComponents;
	}

	public void setAllLoopComponents(List<LoopComponent> allLoopComponents) {
		this.allLoopComponents = allLoopComponents;
	}

	/**
	 * @return the blogicHandlerIo
	 */
	public BLogicHandlerIo getBlogicHandlerIo() {
		return blogicHandlerIo;
	}

	/**
	 * @param blogicHandlerIo the blogicHandlerIo to set
	 */
	public void setBlogicHandlerIo(BLogicHandlerIo blogicHandlerIo) {
		this.blogicHandlerIo = blogicHandlerIo;
	}

}
