/**
 * @(#)BusinessDesignServiceImpl.java
 */
package org.terasoluna.qp.domain.service.businessdesign;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.AutoFix;
import org.terasoluna.qp.app.common.constants.DbDomainConst.GenerateAppStatus;
import org.terasoluna.qp.app.common.constants.DbDomainConst.ImpactChangeDesign;
import org.terasoluna.qp.app.common.constants.DbDomainConst.MessageLevel;
import org.terasoluna.qp.app.common.constants.DbDomainConst.MessageType;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.BusinessDesignMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.FunctionMasterMessageConst;
import org.terasoluna.qp.app.message.ProblemListMessageConst;
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
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.CommonOutputValue;
import org.terasoluna.qp.domain.model.DecisionComponent;
import org.terasoluna.qp.domain.model.DecisionInputValue;
import org.terasoluna.qp.domain.model.DecisionOutputValue;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.model.DecisionTableConditionGroup;
import org.terasoluna.qp.domain.model.DecisionTableConditionItem;
import org.terasoluna.qp.domain.model.DecisionTableInputBean;
import org.terasoluna.qp.domain.model.DecisionTableItemDesignBean;
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
import org.terasoluna.qp.domain.model.FormulaDefinition;
import org.terasoluna.qp.domain.model.FormulaDetail;
import org.terasoluna.qp.domain.model.FormulaMethodInput;
import org.terasoluna.qp.domain.model.FormulaMethodOutput;
import org.terasoluna.qp.domain.model.IfComponent;
import org.terasoluna.qp.domain.model.IfConditionDetail;
import org.terasoluna.qp.domain.model.ImpactChangeJobControl;
import org.terasoluna.qp.domain.model.ImportAssignValue;
import org.terasoluna.qp.domain.model.ImportFileComponent;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.ItemValidation;
import org.terasoluna.qp.domain.model.LogComponent;
import org.terasoluna.qp.domain.model.LoopComponent;
import org.terasoluna.qp.domain.model.MergeFileDetail;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.MessageParameter;
import org.terasoluna.qp.domain.model.NavigatorComponent;
import org.terasoluna.qp.domain.model.NavigatorDetail;
import org.terasoluna.qp.domain.model.NestedLogicComponent;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.PatternedComponent;
import org.terasoluna.qp.domain.model.PatternedDetail;
import org.terasoluna.qp.domain.model.PatternedDetailConnector;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemOutput;
import org.terasoluna.qp.domain.model.ScreenItemValidation;
import org.terasoluna.qp.domain.model.ScreenParameter;
import org.terasoluna.qp.domain.model.SequenceConnector;
import org.terasoluna.qp.domain.model.SequenceLogic;
import org.terasoluna.qp.domain.model.TableDesignDetailsOutput;
import org.terasoluna.qp.domain.model.TransactionComponent;
import org.terasoluna.qp.domain.model.UploadFile;
import org.terasoluna.qp.domain.model.UtilityComponent;
import org.terasoluna.qp.domain.model.ValidationCheckDetail;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignCriteria;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
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
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ValidationCheckDetailRepository;
import org.terasoluna.qp.domain.repository.businessdesignpattern.PatternedComponentRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableConditionGroupRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableConditionItemRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableInputBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableItemDesignBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableOutputBeanRepository;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableRepository;
import org.terasoluna.qp.domain.repository.impactchange.ImpactChangeRepository;
import org.terasoluna.qp.domain.repository.messagedesign.MessageDesignRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.screenactionparam.ScreenActionParamRepository;
import org.terasoluna.qp.domain.repository.screenarea.ScreenAreaRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.screenitem.ScreenItemRepository;
import org.terasoluna.qp.domain.repository.screenitemvalidation.ScreenItemValidationRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.repository.uploadfile.UploadFileRepository;
import org.terasoluna.qp.domain.service.decisiontable.DecisionTableUtils;
import org.terasoluna.qp.domain.service.messagedesign.MessageDesignService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst;

/**
 * @author quangvd
 */
@Service
@Transactional
public class BusinessDesignServiceImpl implements BusinessDesignService {
	@Inject
	BusinessDesignShareService businessDesignShareService;

	@Inject
	private BusinessDesignRepository businessDesignRepository;

	@Inject
	private UploadFileRepository uploadFileRepository;
	
	@Inject
	private ScreenItemValidationRepository screenItemValidationRepository;

	@Inject
	private FeedbackComponentRepository feedbackComponentRepository;

	@Inject
	private MessageParameterRepository messageParameterRepository;

	@Inject
	private IfComponentRepository ifComponentRepository;

	@Inject
	private NavigationComponentRepository navigationComponentRepository;

	@Inject
	private IfConditionDetailRepository ifConditionDetailRepository;

	@Inject
	private MessageDesignRepository messageDesignRepository;

	@Inject
	private CommonComponentRepository commonCompRepository;

	@Inject
	private LoopComponentRepository loopCompRepository;

	@Inject
	private DecisionComponentRepository decisionCompRepository;

	@Inject
	private DecisionTableRepository decisionTableRepository;

	@Inject
	private DecisionTableInputBeanRepository decisionTableInputBeanRepository;

	@Inject
	private DecisionTableOutputBeanRepository decisionTableOutputBeanRepository;

	@Inject
	private DecisionTableItemDesignBeanRepository decisionTableItemDesignBeanRepository;

	@Inject
	private DecisionTableConditionGroupRepository decisionTableConditionGroupRepository;

	@Inject
	private DecisionTableConditionItemRepository decisionTableConditionItemRepository;

	@Inject
	private AssignComponentRepository assignComponentRepository;

	@Inject
	private ValidationCheckDetailRepository validationCheckDetailRepository;

	@Inject
	private BusinessCheckComponentRepository businessCheckCompRepository;

	@Inject
	private FormulaDefinitionRepository formulaDefinitionRepository;

	@Inject
	private ExecutionComponentRepository executionCompRepository;

	@Inject
	private ScreenDesignRepository screenDesignRepository;

	@Inject
	ProblemListRepository problemListRepository;

	@Inject
	ProjectService projectService;

	@Inject
	ModuleService moduleService;

	@Inject
	MessageDesignService messageDesignService;

	@Inject
	ScreenActionParamRepository screenActionParamRepository;

	@Inject
	NestedLogicComponentRepository nestedLogicCompRepository;

	@Inject
	AdvanceComponentRepository advanceCompRepository;

	@Inject
	FileOperationComponentRepository fileOperationCompRepository;

	@Inject
	PatternedComponentRepository patternedCompRepository;

	@Inject
	ImportFileComponentRepository importFileCompRepository;

	@Inject
	ExportFileComponentRepository exportFileCompRepository;

	@Inject
	TransactionComponentRepository transactionCompRepository;

	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;

	@Inject
	BDParameterIndexRepository bDParameterIndexRepository;

	@Inject
	LogComponentRepository logCompRepository;

	@Inject
	UtilityComponentRepository utilityCompRepository;

	@Inject
	EmailComponentRepository emailCompRepository;

	@Inject
	DownloadFileComponentRepository downloadFileCompRepository;
	
	@Inject
	ScreenAreaRepository screenAreaRepository;

	@Inject
	private ScreenItemRepository screenItemRepository;

	@Inject
	ExceptionComponentRepository exceptionComponentRepository;
	
	@Inject
	ImpactChangeRepository impactChangeRepository;

	private final Map<String, Long> mKeyInClient = new HashMap<String, Long>();

	private final Map<String, Long> mKeyObClient = new HashMap<String, Long>();

	private final Map<String, Long> mKeyOuClient = new HashMap<String, Long>();

	private final Map<String, String> mNameParameter = new HashMap<String, String>();

	private Map<String, Long> mKeySequenceLogic = new HashMap<String, Long>();

	private List<FormulaDetail> lstFormulaDetails = new ArrayList<FormulaDetail>();

	/**
	 * Finds all business logic design information with search criteria
	 *
	 * @param criteria
	 *            BusinessDesignCriteria
	 * @return List of all business logic design
	 */
	@Override
	public Page<BusinessDesign> searchBusinessDesign(BusinessDesignCriteria criteria, Pageable pageable, CommonModel commonModel) {
		List<BusinessDesign> lstBusinessDesigns;

		criteria.setProjectId(commonModel.getWorkingProjectId());
		long totalCount = businessDesignRepository.countBySearchCriteria(criteria);
		if (0 < totalCount) {
			lstBusinessDesigns = businessDesignRepository.findPageBySearchCriteria(criteria, pageable, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
		} else {
			lstBusinessDesigns = Collections.emptyList();
		}
		Page<BusinessDesign> page = new PageImpl<BusinessDesign>(lstBusinessDesigns, pageable, totalCount);
		return page;
	}

	/**
	 * find business logic design
	 *
	 * @param businessLogicId
	 * @return BusinessDesign
	 */
	@Override
	public BusinessDesign findBusinessLogicInformation(Long businessLogicId, boolean isOnlyView, CommonModel commonModel, boolean isGetContent) {
		mNameParameter.clear();
		BusinessDesign businessDesign = businessDesignRepository.findBusinessLogicInformation(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
		if (businessDesign == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010)));
		}

		List<InputBean> lstInputBean = businessDesignRepository.findInputBean(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
		List<OutputBean> lstOutputBean = businessDesignRepository.findOutputBean(businessLogicId);

		//validate screenitem/screen param mapping inputbean

		if(BusinessDesignConst.BLOGIC_TYPE_STANDARD.equals(businessDesign.getBlogicType())){
			//RETURN_TYPE_INITIALSCREEN = RETURN_TYPE_SCREEN + GET
			int type = -1;
			if((BusinessDesignConst.RETURN_TYPE_INITIALSCREEN.equals(businessDesign.getReturnType()) ||
					(BusinessDesignConst.RETURN_TYPE_SCREEN.equals(businessDesign.getReturnType()) && BusinessDesignConst.REQUEST_METHOD_INITIAL.equals(businessDesign.getRequestMethod()))) && businessDesign.getScreenId() != null){
				type = 0;
			} else if(BusinessDesignConst.RETURN_TYPE_SCREEN.equals(businessDesign.getReturnType()) && BusinessDesignConst.REQUEST_METHOD_PROCESSING.equals(businessDesign.getRequestMethod()) && businessDesign.getScreenId() != null){
				type = 1;
			} else if(BusinessDesignConst.RETURN_TYPE_DOWNLOAD.equals(businessDesign.getReturnType()) && businessDesign.getScreenId() != null) {
				type = 1;
			}

			switch (type) {
				case 0:
					ScreenParameter[] screenParameterResult = screenDesignRepository.getScreenParameterByScreenId(businessDesign.getScreenId());
					if(ArrayUtils.isNotEmpty(screenParameterResult) && CollectionUtils.isNotEmpty(lstInputBean)){
						for (InputBean inputbean : lstInputBean) {
							for(ScreenParameter param : screenParameterResult){
								if(inputbean.getInputBeanCode().equals(param.getScreenParamCode())
										&& inputbean.getDataType().equals(param.getDataType())
										&& Boolean.FALSE.equals(inputbean.getArrayFlg())){
									inputbean.setMappingScreenItemFlag(true);
									break;
								}
							}
						}

					}
					break;
				case 1:
					for (InputBean inputbean : lstInputBean) {
						if(BusinessDesignConst.DataType.OBJECT.equals(inputbean.getDataType())){
							inputbean.setMappingScreenItemFlag(true);
						}else if(inputbean.getScreenItemId() != null){
							inputbean.setMappingScreenItemFlag(true);
						}
					}
					break;
				default:
					break;
			}
		}

		if (BusinessDesignConst.BLOGIC_TYPE_STANDARD.equals(businessDesign.getBlogicType()) && businessDesign.getScreenId() != null) {
			List<ScreenItemOutput> lstScreenItemMappingInput = businessDesignRepository.findAllScreenItemMappingInputBeanByBusinessLogicId(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());

			Map<Long,String> mapCheckScreenItemFromTo= new HashMap<Long,String>();
			Map<Long,String> mapCheckScreenItemAutocomplete= new HashMap<Long,String>();
			for (InputBean inputBean : lstInputBean) {
				for (ScreenItemOutput screenItemOutput : lstScreenItemMappingInput) {
					if (inputBean.getInputBeanId().equals(screenItemOutput.getInputBeanId().toString())) {
						if(ScreenDesignConst.FromTo.FROM_TO.equals(screenItemOutput.getDisplayFromTo())){
							if(mapCheckScreenItemFromTo.containsKey(inputBean.getScreenItemId())){
								screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_TO);
							}else{
								screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_FROM);
								mapCheckScreenItemFromTo.put(inputBean.getScreenItemId(), inputBean.getInputBeanId());
							}
							screenItemOutput.setItemName(screenItemOutput.getItemName() + BusinessDesignHelper.getMappingNameOfMappingOuput(screenItemOutput.getMappingType(),screenItemOutput.getLogicalDataType()));
						}
						else if (DbDomainConst.LogicDataType.CHECKBOX.equals(screenItemOutput.getLogicalDataType()) || DbDomainConst.LogicDataType.RADIO.equals(screenItemOutput.getLogicalDataType()) || DbDomainConst.LogicDataType.SELECT.equals(screenItemOutput.getLogicalDataType())) {
							screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_SUBMIT);
							screenItemOutput.setItemName(screenItemOutput.getItemName() + BusinessDesignHelper.getMappingNameOfMappingOuput(screenItemOutput.getMappingType(),screenItemOutput.getLogicalDataType()));
						}else if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(screenItemOutput.getLogicalDataType())){
							if(mapCheckScreenItemAutocomplete.containsKey(inputBean.getScreenItemId())){
								screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_DISPLAY);
							}else{
								screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_SUBMIT);
								mapCheckScreenItemAutocomplete.put(inputBean.getScreenItemId(), inputBean.getInputBeanId());
							}
							screenItemOutput.setItemName(screenItemOutput.getItemName() + BusinessDesignHelper.getMappingNameOfMappingOuput(screenItemOutput.getMappingType(),screenItemOutput.getLogicalDataType()));
						}
						inputBean.setScreenItemMapping(screenItemOutput);
						break;
					}
				}
			}
			// List<ScreenItemOutput> listScreenItem =
			// businessDesignRepository.getScreenItemOutputByScreenIdForBD(businessDesign.getScreenId(),
			// workingLanguageId, workingProjectId);
			List<ScreenItemOutput> lstScreenItemMapping = businessDesignRepository.findAllScreenItemMappingByOutputBeanId(businessLogicId);
//			List<ScreenArea> lstPageableScreenArea = screenAreaRepository.findPageableScreenAreaByScreenId(businessDesign.getScreenId(), commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
//			for (ScreenArea area : lstPageableScreenArea) {
//	            
//            }
			for (OutputBean outputBean : lstOutputBean) {
				List<ScreenItemOutput> screenItemOutputLst = new ArrayList<ScreenItemOutput>();
				for (ScreenItemOutput screenItemOutput : lstScreenItemMapping) {
					if (outputBean.getOutputBeanId().equals(screenItemOutput.getOutputBeanId().toString())) {
						if(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_TOTAL_COUNT.equals(screenItemOutput.getMappingType())){
							screenItemOutput.setItemName("Total count");
						}
						
						if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(screenItemOutput.getLogicalDataType()) || DbDomainConst.LogicDataType.CHECKBOX.equals(screenItemOutput.getLogicalDataType()) || DbDomainConst.LogicDataType.RADIO.equals(screenItemOutput.getLogicalDataType()) || DbDomainConst.LogicDataType.SELECT.equals(screenItemOutput.getLogicalDataType())) {
							screenItemOutput.setItemName(screenItemOutput.getItemName() + BusinessDesignHelper.getMappingNameOfMappingOuput(screenItemOutput.getMappingType(),screenItemOutput.getLogicalDataType()));
						}
						screenItemOutputLst.add(screenItemOutput);
					}
				}
				outputBean.setLstScreenItemMapping(screenItemOutputLst);
			}
		}

		List<ObjectDefinition> lstObjectDefinition = new ArrayList<ObjectDefinition>();

		Map<String, String> mapTableIndex = new HashMap<String, String>();
		// set level of input bean
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
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
		mapTableIndex.clear();
		mapSequence.clear();
		// process output bean
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

		String jsonData = "";

		if (FunctionCommon.equals(businessDesign.getBlogicType(), BusinessDesignConst.BLOGIC_TYPE_COMMON) && Boolean.TRUE.equals(businessDesign.getCustomizeFlg())) {

		} else {
			lstObjectDefinition = businessDesignRepository.findObjectDefinition(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
			// process object definition
			mapTableIndex.clear();
			mapSequence.clear();
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
			if(isGetContent){
				List<SequenceLogic> lstSequenceLogics = businessDesignRepository.findSequenceLogic(businessLogicId);
				List<SequenceConnector> lstSequenceConnectors = businessDesignRepository.findSequenceConnector(businessLogicId);
				Map<Integer, Boolean> mapUsedComponent = new HashMap<>();
				for (SequenceLogic sequenceLogic : lstSequenceLogics) {
		            switch (sequenceLogic.getComponentType()) {
						case BusinessDesignConst.COMPONENT_EXECUTION:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_EXECUTION)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_EXECUTION, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_BUSINESSCHECK:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_BUSINESSCHECK)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_BUSINESSCHECK, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_DECISION:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_DECISION)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_DECISION, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_ADVANCE:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_ADVANCE)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_ADVANCE, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_COMMON:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_COMMON)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_COMMON, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_ASSIGN:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_ASSIGN)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_ASSIGN, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_IF:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_IF)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_IF, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_LOOP:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_LOOP)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_LOOP, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_FEEDBACK:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_FEEDBACK)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_FEEDBACK, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_NAVIGATOR:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_NAVIGATOR)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_NAVIGATOR, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_NESTEDLOGIC:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_NESTEDLOGIC)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_NESTEDLOGIC, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_FILEOPERATION:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_FILEOPERATION)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_FILEOPERATION, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_IMPORTFILE:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_IMPORTFILE)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_IMPORTFILE, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_EXPORTFILE:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_EXPORTFILE)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_EXPORTFILE, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_TRANSACTION:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_TRANSACTION)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_TRANSACTION, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_EMAIL:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_EMAIL)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_EMAIL, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_LOG:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_LOG)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_LOG, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_UTILITY:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_UTILITY)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_UTILITY, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_DOWNLOAD_FILE:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_DOWNLOAD_FILE)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_DOWNLOAD_FILE, true);
							}
							break;
						case BusinessDesignConst.COMPONENT_EXCEPTION:
							if(!mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_EXCEPTION)){
								mapUsedComponent.put(BusinessDesignConst.COMPONENT_EXCEPTION, true);
							}
							break;
						default:
							break;
					}
	            }
				List<ExecutionComponent> lstExecutionComponents = new ArrayList<ExecutionComponent>();
				List<ExecutionInputValue> lstExecutionInputValues = new ArrayList<ExecutionInputValue>();
				List<ExecutionOutputValue> lstExecutionOutputValues = new ArrayList<ExecutionOutputValue>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_EXECUTION)){
					lstExecutionComponents = executionCompRepository.findExecutionComponentByBusinessLogic(businessLogicId);
					if(CollectionUtils.isNotEmpty(lstExecutionComponents)){
						lstExecutionInputValues = executionCompRepository.findExecutionInputValueByBusinessLogic(businessLogicId);
						lstExecutionOutputValues = executionCompRepository.findExecutionOutputValueByBusinessLogic(businessLogicId);
					}
				}
				List<BusinessCheckComponent> lstBusinessCheckComponents = new ArrayList<BusinessCheckComponent>();
				List<BusinessCheckDetail> lstBusinessCheckDetails = new ArrayList<BusinessCheckDetail>();
				List<BusinessDetailContent> lstBusinessDetailContents = new ArrayList<BusinessDetailContent>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_BUSINESSCHECK)){
					lstBusinessCheckComponents = businessCheckCompRepository.findBusinessCheckComponentByBusinessLogic(businessLogicId);
					if(CollectionUtils.isNotEmpty(lstBusinessCheckComponents)){
						lstBusinessCheckDetails = businessCheckCompRepository.findBusinessCheckDetailsByBusinessLogic(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
						if(CollectionUtils.isNotEmpty(lstBusinessCheckComponents)){
							lstBusinessDetailContents = businessCheckCompRepository.findBusinessDetailContentsByBusinessLogic(businessLogicId);
						}
					}
				}
				List<DecisionComponent> lstDecisionComponent = new ArrayList<DecisionComponent>();
				List<DecisionInputValue> lstDecisionInputValues = new ArrayList<DecisionInputValue>();
				List<DecisionOutputValue> lstDecisionOutputValues = new ArrayList<DecisionOutputValue>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_DECISION)){
					lstDecisionComponent = decisionCompRepository.findDecisionComponentByBusinessLogic(businessLogicId);
					if(CollectionUtils.isNotEmpty(lstDecisionComponent)){
						lstDecisionInputValues = decisionCompRepository.findDecisionInputValueByBusinessLogic(businessLogicId);
						lstDecisionOutputValues = decisionCompRepository.findDecisionOutputValueByBusinessLogic(businessLogicId);
					}
				}
				List<AdvanceComponent> lstAdvanceComponents = new ArrayList<AdvanceComponent>();
				List<AdvanceInputValue> lstAdvanceInputValues = new ArrayList<AdvanceInputValue>();
				List<AdvanceOutputValue> lstAdvanceOutputValues = new ArrayList<AdvanceOutputValue>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_ADVANCE)){
					lstAdvanceComponents = advanceCompRepository.findAdvanceComponentByBusinessLogic(businessLogicId);
					if(CollectionUtils.isNotEmpty(lstAdvanceComponents)){
						lstAdvanceInputValues = advanceCompRepository.findAdvanceInputValueByBusinessLogic(businessLogicId);
						lstAdvanceOutputValues = advanceCompRepository.findAdvanceOutputValueByBusinessLogic(businessLogicId);
					}
				}
				List<CommonComponent> lstCommonComponent = new ArrayList<CommonComponent>();
				List<CommonInputValue> lstCommonInputValues = new ArrayList<CommonInputValue>();
				List<CommonOutputValue> lstCommonOutputValues = new ArrayList<CommonOutputValue>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_COMMON)){
					lstCommonComponent = commonCompRepository.findCommonComponentByBusinessLogic(businessLogicId);
					if(CollectionUtils.isNotEmpty(lstCommonComponent)){
						lstCommonInputValues = commonCompRepository.findCommonInputValueByBusinessLogic(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
						lstCommonOutputValues = commonCompRepository.findCommonOutputValueByBusinessLogic(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
					}
				}
				List<AssignComponent> lstAssignComponent = new ArrayList<AssignComponent>();
				List<AssignDetail> lstAssignDetails = new ArrayList<AssignDetail>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_ASSIGN)){
					lstAssignComponent = assignComponentRepository.findAssignComponentByBusinessLogic(businessLogicId);
					if(CollectionUtils.isNotEmpty(lstAssignComponent)){
						lstAssignDetails = assignComponentRepository.findAssignDetailsByBusinessLogic(businessLogicId);
					}
				}
				List<IfComponent> lstIfComponents = new ArrayList<IfComponent>();
				List<IfConditionDetail> lstIfConditionDetails = new ArrayList<IfConditionDetail>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_IF)){
					lstIfComponents = ifComponentRepository.findIfComponentByBusinessLogic(businessLogicId);
					if(CollectionUtils.isNotEmpty(lstIfComponents)){
						lstIfConditionDetails = ifConditionDetailRepository.findIfConditionByBusinessLogic(businessLogicId);
					}
				}
				List<LoopComponent> lstLoopComponent = new ArrayList<LoopComponent>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_LOOP)){
					lstLoopComponent = loopCompRepository.findLoopComponentByBusinessLogic(businessLogicId);
				}
				List<FeedbackComponent> lstFeedbackComponent = new ArrayList<FeedbackComponent>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_FEEDBACK)){
					lstFeedbackComponent = feedbackComponentRepository.findFeedbackComponentByBusinessLogic(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
				}
				List<NavigatorComponent> lstNavComponent = new ArrayList<NavigatorComponent>();
				List<NavigatorDetail> lstNavDetails = new ArrayList<NavigatorDetail>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_NAVIGATOR)){
					lstNavComponent = navigationComponentRepository.findNavigationComponentByBusinessLogic(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
					if(CollectionUtils.isNotEmpty(lstNavComponent)){
						lstNavDetails = navigationComponentRepository.findNavigationDetailByBusinessLogic(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
					}
				}
				List<NestedLogicComponent> lstNestedLogicComponents = new ArrayList<NestedLogicComponent>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_NESTEDLOGIC)){
					lstNestedLogicComponents = nestedLogicCompRepository.findNestedLogicComponentByBusinessLogic(businessLogicId);
				}
				List<FileOperationComponent> lstFileOperationComponents = new ArrayList<FileOperationComponent>();
				List<MergeFileDetail> lstMergeFileDetails = new ArrayList<MergeFileDetail>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_FILEOPERATION)){
					lstFileOperationComponents = fileOperationCompRepository.findFileOperationComponentByBusinessLogic(businessLogicId);
					if(CollectionUtils.isNotEmpty(lstFileOperationComponents)){
						lstMergeFileDetails = fileOperationCompRepository.findMergeFileDetailByBusinessLogic(businessLogicId);
					}
				}
				List<ImportFileComponent> lstImportFileComponents = new ArrayList<ImportFileComponent>();
				List<ImportAssignValue> lstImportAssignValues = new ArrayList<ImportAssignValue>(); 
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_IMPORTFILE)){
					lstImportFileComponents = importFileCompRepository.findImportFileComponentByBusinessLogic(businessLogicId);
					if(CollectionUtils.isNotEmpty(lstImportFileComponents)){
						lstImportAssignValues = importFileCompRepository.findImportAssignValueByBusinessLogic(businessLogicId);
					}
				}
				List<ExportFileComponent> lstExportFileComponents = new ArrayList<ExportFileComponent>();
				List<ExportAssignValue> lstExportAssignValues = new ArrayList<ExportAssignValue>();
				List<ColumnFileFormat> lstColumnFileFormats = new ArrayList<ColumnFileFormat>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_EXPORTFILE)){
					lstExportFileComponents = exportFileCompRepository.findExportFileComponentByBusinessLogic(businessLogicId);
					if(CollectionUtils.isNotEmpty(lstExportFileComponents)){
						lstExportAssignValues = exportFileCompRepository.findExportAssignValueByBusinessLogic(businessLogicId);
						if(CollectionUtils.isNotEmpty(lstExportAssignValues)){
							lstColumnFileFormats = exportFileCompRepository.findColumnFileFormatByBusinessLogic(businessLogicId);
						}
					}
				}
				List<TransactionComponent> lstTransactionComponents = new ArrayList<TransactionComponent>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_TRANSACTION)){
					lstTransactionComponents = transactionCompRepository.findTransactionComponentByBusinessLogic(businessLogicId);
				}
				List<EmailComponent> lstEmailComponents = new ArrayList<EmailComponent>();
				List<EmailRecipient> lstEmailRecipients = new ArrayList<EmailRecipient>();
				List<EmailContent> lstEmailContents =new ArrayList<EmailContent>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_EMAIL)){
					lstEmailComponents = emailCompRepository.findEmailComponentByBusinessLogic(businessLogicId);
					if(CollectionUtils.isNotEmpty(lstEmailComponents)){
						lstEmailRecipients = emailCompRepository.findEmailRecipientByBusinessLogic(businessLogicId);
						lstEmailContents = emailCompRepository.findEmailContentByBusinessLogic(businessLogicId);
					}
				}
				List<LogComponent> lstLogComponents = new ArrayList<LogComponent>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_LOG)){
					lstLogComponents = logCompRepository.findLogComponentByBusinessLogic(businessLogicId);
				}
				List<UtilityComponent> lstUtilityComponents = new ArrayList<UtilityComponent>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_UTILITY)){
					lstUtilityComponents = utilityCompRepository.findUtilityComponentByBusinessLogic(businessLogicId);
				}
				List<DownloadFileComponent> lstDownloadFileComponents = new ArrayList<DownloadFileComponent>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_DOWNLOAD_FILE)){
					lstDownloadFileComponents = downloadFileCompRepository.findDownloadFileComponentByBusinessLogic(businessLogicId);
				}
				List<ExceptionComponent> lstExceptionComponents = new ArrayList<ExceptionComponent>();
				List<ExceptionDetail> lstExceptionDetails = new ArrayList<ExceptionDetail>();
				if(mapUsedComponent.containsKey(BusinessDesignConst.COMPONENT_EXCEPTION)){
					lstExceptionComponents = exceptionComponentRepository.findExceptionComponentByBusinessLogic(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
					if(CollectionUtils.isNotEmpty(lstExceptionComponents)){
						lstExceptionDetails = exceptionComponentRepository.findExceptionDetailByBusinessLogic(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
					}
				}
				List<ValidationCheckDetail> lstValidationCheckDetails = new ArrayList<ValidationCheckDetail>();
				if(CollectionUtils.isNotEmpty(lstInputBean)){
					lstValidationCheckDetails = validationCheckDetailRepository.findValidationCheckDetailsByBusinessLogic(businessLogicId);
				}
				List<FormulaDetail> lstFormulaDetails = formulaDefinitionRepository.findFormulaDetailsByBusinessLogic(businessLogicId);
				List<FormulaMethodInput> lstFormulaMethodInputs = new ArrayList<FormulaMethodInput>();
				List<FormulaMethodOutput> lstFormulaMethodOutputs = new ArrayList<FormulaMethodOutput>();
				if(CollectionUtils.isNotEmpty(lstFormulaDetails)){
					lstFormulaMethodInputs = formulaDefinitionRepository.findFormulaMethodInputsByBusinessLogic(businessLogicId);
					lstFormulaMethodOutputs = formulaDefinitionRepository.findFormulaMethodOutputsByBusinessLogic(businessLogicId);
				}
				
				List<MessageParameter> lstMessageParameters = messageParameterRepository.findMessageParameterByBusinessLogic(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
				
				List<FileFormat> lstFileFormats = new ArrayList<FileFormat>();
				if(CollectionUtils.isNotEmpty(lstImportFileComponents) || CollectionUtils.isNotEmpty(lstExportFileComponents)){
					lstFileFormats = importFileCompRepository.findFileFormatByBusinessLogic(businessLogicId);
				}
				List<BDParameterIndex> lstBdParameterIndexs = bDParameterIndexRepository.findBDParameterIndexByBusinessLogic(businessLogicId);
				// set table index of object definition bean
	
				Boolean isGensource = false;
				// map detail of parameter index
				ComponentHelper.mappingDetailOfParameterIndex(isGensource,lstBdParameterIndexs);
	
				// map detail function for formula setting
				ComponentHelper.mappingFormulaDetailOfFormulaDefinition(isGensource,lstFormulaDetails, lstFormulaMethodInputs, lstFormulaMethodOutputs, lstBdParameterIndexs, mNameParameter);
	
				// map detail for message parameter
				ComponentHelper.mappingMessageParameter(isGensource,lstMessageParameters, mNameParameter, lstBdParameterIndexs);
	
				// get parameter code for feedback message
				ComponentHelper.mappingMessageOfFeedback(isGensource,lstFeedbackComponent, lstMessageParameters);
	
				// map detail condition of if component
				ComponentHelper.mappingConditionOfIf(isGensource,lstIfComponents, lstIfConditionDetails, lstFormulaDetails);
	
				// map detail of navigator component
				ComponentHelper.mappingDetailOfNavigator(isGensource,lstNavComponent, lstNavDetails, lstBdParameterIndexs, mNameParameter);
	
				// map detail of common component
				ComponentHelper.mappingDetailOfCommon(isGensource,lstCommonComponent, lstCommonInputValues, lstCommonOutputValues, lstBdParameterIndexs, mNameParameter);
	
				// map detail of loop component
				ComponentHelper.mappingDetailOfLoop(isGensource,lstLoopComponent, lstFormulaDetails,lstBdParameterIndexs, mNameParameter);
	
				// map detail of decision component
				ComponentHelper.mappingDetailOfDecision(isGensource,lstDecisionComponent, lstDecisionInputValues, lstDecisionOutputValues, lstBdParameterIndexs, mNameParameter);
	
				// map detail of assign component
				ComponentHelper.mappingDetailOfAssign(isGensource,lstAssignComponent, lstAssignDetails, lstFormulaDetails, lstBdParameterIndexs, mNameParameter);
	
				// map detail of business check component
				ComponentHelper.mappingDetailOfBusinessCheck(isGensource,lstBusinessCheckComponents, lstBusinessCheckDetails, lstBusinessDetailContents, lstMessageParameters, lstFormulaDetails, lstBdParameterIndexs, mNameParameter);
	
				// map detail of execution component
				ComponentHelper.mappingDetailOfExecution(isGensource,lstExecutionComponents, lstExecutionInputValues, lstExecutionOutputValues, lstBdParameterIndexs, mNameParameter);
	
				// map detail of advance
				ComponentHelper.mappingDetailOfAdvance(isGensource,lstAdvanceComponents, lstAdvanceInputValues, lstAdvanceOutputValues, lstBdParameterIndexs, mNameParameter);
	
				// map detail of file operation
				ComponentHelper.mappingDetailOfFileOperation(isGensource,lstFileOperationComponents, lstMergeFileDetails, lstFormulaDetails);
	
				// map detail of import file component
				ComponentHelper.mappingDetailOfImportFile(isGensource,lstImportFileComponents, lstImportAssignValues, lstFileFormats, lstFormulaDetails, mNameParameter);
	
				// map detail of export file component
				ComponentHelper.mappingDetailOfExportFile(isGensource,lstExportFileComponents, lstExportAssignValues, lstFileFormats, lstColumnFileFormats, lstFormulaDetails, mNameParameter);
	
				// map detail of log component
				ComponentHelper.mappingDetailOfLog(isGensource,lstLogComponents, lstFormulaDetails);
	
				// map detail of utility component
				ComponentHelper.mappingDetailOfUtility(isGensource,lstUtilityComponents, lstBdParameterIndexs, mNameParameter);
	
				// map detail of email component
				ComponentHelper.mappingDetailOfEmail(isGensource,lstEmailComponents, lstEmailRecipients, lstEmailContents, lstFormulaDetails);
	
				// map detail of log component
				ComponentHelper.mappingDetailOfDownload(isGensource,lstDownloadFileComponents, lstFormulaDetails, mNameParameter);
				
				// map detail of exception component
				ComponentHelper.mappingDetailOfException(isGensource, lstExceptionComponents, lstExceptionDetails, lstBdParameterIndexs, mNameParameter);
	
				// map detail of validation check
				for (ValidationCheckDetail objDetail : lstValidationCheckDetails) {
					List<MessageParameter> lstMessageParameterTemps = new ArrayList<MessageParameter>();
					for (MessageParameter objMessage : lstMessageParameters) {
						if (BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION.equals(objMessage.getTargetType()) && objMessage.getTargetId().equals(objDetail.getValidationCheckDetailId())) {
							if (Integer.valueOf(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE).equals(objMessage.getParameterType())) {
								objMessage.setParameterCode(objMessage.getParameterValue());
							}
							lstMessageParameterTemps.add(objMessage);
						}
					}
					objDetail.setParameters(lstMessageParameterTemps);
				}
				for (InputBean inputBean : lstInputBean) {
					List<ValidationCheckDetail> lstValidationCheckDetailTemps = new ArrayList<ValidationCheckDetail>();
					for (ValidationCheckDetail objDetail : lstValidationCheckDetails) {
						if (objDetail.getInputBeanId().equals(inputBean.getInputBeanId())) {
							lstValidationCheckDetailTemps.add(objDetail);
						}
					}
					inputBean.setLstValidationCheckDetails(lstValidationCheckDetailTemps);
					jsonData = DataTypeUtils.toJson(lstValidationCheckDetailTemps);
					inputBean.setJsonValidationInputs(jsonData);
				}
	
				// map details of nested logic
				BusinessDesignBuilder builder = new BusinessDesignBuilder(isOnlyView, lstSequenceLogics, lstFeedbackComponent, lstIfComponents, lstNavComponent, lstCommonComponent, lstLoopComponent, lstDecisionComponent, lstAssignComponent, lstBusinessCheckComponents, lstExecutionComponents, lstAdvanceComponents, lstFileOperationComponents, lstImportFileComponents, lstExportFileComponents, lstTransactionComponents, lstLogComponents, lstUtilityComponents, lstNestedLogicComponents, lstSequenceConnectors ,lstEmailComponents, lstDownloadFileComponents, lstExceptionComponents);
				builder.mappingComponentOfBlogic(businessDesign);
			}
		}

		jsonData = DataTypeUtils.toJson(lstInputBean);
		businessDesign.setJsonInputBean(jsonData);

		jsonData = DataTypeUtils.toJson(lstOutputBean);
		businessDesign.setJsonOutputBean(jsonData);

		jsonData = DataTypeUtils.toJson(lstObjectDefinition);
		businessDesign.setJsonObjectDefinition(jsonData);

		businessDesign.setLstInputBean(lstInputBean);
		businessDesign.setLstOutputBean(lstOutputBean);
		businessDesign.setLstObjectDefinition(lstObjectDefinition);

		// client check
		boolean isClientCheck = false;

		if (BusinessDesignConst.RETURN_TYPE_SCREEN.equals(businessDesign.getReturnType()) && BusinessDesignConst.REQUEST_METHOD_PROCESSING.equals(businessDesign.getRequestMethod()) && businessDesign.getScreenId() != null) {
			isClientCheck = true;
		}

		if (isClientCheck) {
			List<ItemValidation> lstItemValidations = new ArrayList<ItemValidation>();
			lstItemValidations = findClientCheckOfBusinessLogic(businessDesign.getBusinessLogicId(), commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
			businessDesign.setLstItemValidations(lstItemValidations);
		}

		if ( BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(businessDesign.getBlogicType())) {
			List<BusinessDesign> lstBusinessDesignsOfCommon = businessDesignRepository.findBlogicByCommonBusinessBlogicId(businessLogicId);
			businessDesign.setLstAffectedBlogicCommon(lstBusinessDesignsOfCommon);
			if (businessDesign.getLstAffectedBlogicCommon().size() > 0) {
				businessDesign.setFlagImpact(true);
			}
		} else if (businessDesign.getScreenId() == null) {
			List<BusinessDesign> lstBusinessDesignsOfNavigator = businessDesignRepository.findBlogicByNavigatorBusinessBlogicId(businessLogicId);
			businessDesign.setLstAffectedBlogicNavigator(lstBusinessDesignsOfNavigator);

			List<ScreenDesign> lstUsedScreen = screenDesignRepository.findAllScreenDesignsByLinkedBusinessLogicId(commonModel.getWorkingProjectId(), commonModel.getWorkingLanguageId(), businessLogicId);
			if (businessDesign.getLstAffectedBlogicNavigator().size() > 0 || lstUsedScreen.size() > 0) {
				businessDesign.setFlagImpact(true);
			}
		}
		businessDesign.setClientCheckFlg(isClientCheck);
		return businessDesign;
	}

	@Override
	public BusinessDesign findBusinessLoginByScreenId(Long screenId) {
		BusinessDesign businessDesign = businessDesignRepository.findBusinessLoginByScreenId(screenId);

		if (businessDesign == null) {
			return null;
		}
		List<OutputBean> outputBeans = businessDesignRepository.findOutputBean(businessDesign.getBusinessLogicId());

		Map<String, String> mapTableIndex = new HashMap<String, String>();
		int sequence = 1;
		int index = 1;

		for (OutputBean ou : outputBeans) {
			if (ou.getParentOutputBeanId() == null) {
				ou.setGroupId("");
				ou.setTableIndex(String.valueOf(index));
				index++;
			} else {
				String currentGroup = mapTableIndex.get(ou.getParentOutputBeanId());
				ou.setGroupId(currentGroup);
				ou.setTableIndex(currentGroup + "." + sequence);
			}
			mapTableIndex.put(ou.getOutputBeanId(), ou.getTableIndex());
			sequence++;
		}

		businessDesign.setLstOutputBean(outputBeans);
		return businessDesign;
	}

	@Override
	public BusinessDesign findInputBeanOfBusinessLogic(Long businessDesignId, CommonModel commonModel) {
		BusinessDesign businessLogic = businessDesignShareService.findInputBeanOfBusinessLogic(businessDesignId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
		return businessLogic;
	}

	@Override
	public List<BusinessDesign> findBusinessLoginsByScreenId(Long screenId) {
		return businessDesignRepository.findBusinessLogicsByScreenId(screenId);
	}
	
	@Override
	public List<BusinessDesign> findAllBlogicProcessByScreenId(Long screenId) {
		return businessDesignRepository.findAllBlogicProcessByScreenId(screenId);
	}
	
	@Override
	public List<BusinessDesign> findAllBlogicInitByScreenId(Long screenId) {
		return businessDesignRepository.findAllBlogicInitByScreenId(screenId);
	}
	
	@Override
	public List<BusinessDesign> findAllBLogicProcessToNavigatorByScreenId(Long screenId) {
		return businessDesignRepository.findAllBLogicProcessToNavigatorByScreenId(screenId);
	}
	
	/**
	 * Finds all business logic design information with search criteria
	 *
	 * @param criteria
	 *            BusinessDesignCriteria
	 * @return List of all business logic design
	 */
	@Override
	public List<BusinessDesign> findBlogicByModuleId(Long moduleId, CommonModel commonModel) {
		return businessDesignRepository.findBlogicByModuleId(moduleId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
	}

	private List<ItemValidation> findClientCheckOfBusinessLogic(Long businessLogicId, Long languageId, Long projectId) {
		List<ItemValidation> lstItemOutputs = businessDesignRepository.findScreenValidationByScreenId(businessLogicId, languageId, projectId);
		Map<Long, Integer> keyMapArea = new HashMap<Long, Integer>();
		Map<Long, Integer> keyMapForm = new HashMap<Long, Integer>();

		for (int i = 0; i < lstItemOutputs.size(); i++) {
			if (keyMapArea.containsKey(lstItemOutputs.get(i).getScreenAreaId())) {
				lstItemOutputs.get(i).setAreaRowspan(0);
				int currentArea = keyMapArea.get(lstItemOutputs.get(i).getScreenAreaId());
				keyMapArea.put(lstItemOutputs.get(i).getScreenAreaId(), currentArea + 1);
			} else {
				lstItemOutputs.get(i).setAreaRowspan(1);
				keyMapArea.put(lstItemOutputs.get(i).getScreenAreaId(), 1);
			}
			if (keyMapForm.containsKey(lstItemOutputs.get(i).getScreenFormId())) {
				lstItemOutputs.get(i).setFormRowspan(0);
				int currentForm = keyMapForm.get(lstItemOutputs.get(i).getScreenFormId());
				keyMapForm.put(lstItemOutputs.get(i).getScreenFormId(), currentForm + 1);
			} else {
				lstItemOutputs.get(i).setFormRowspan(1);
				keyMapForm.put(lstItemOutputs.get(i).getScreenFormId(), 1);
			}
		}
		for (Map.Entry<Long, Integer> entry : keyMapArea.entrySet()) {
			Long key = entry.getKey();
			Integer value = entry.getValue();
			if (value > 1) {
				for (ItemValidation obj : lstItemOutputs) {
					if (obj.getScreenAreaId().equals(key)) {
						obj.setAreaRowspan(value);
						break;
					}
				}
			}
		}
		for (Map.Entry<Long, Integer> entry : keyMapForm.entrySet()) {
			Long key = entry.getKey();
			Integer value = entry.getValue();
			if (value > 1) {
				for (ItemValidation obj : lstItemOutputs) {
					if (obj.getScreenFormId().equals(key)) {
						obj.setFormRowspan(value);
						break;
					}
				}
			}
		}
		return lstItemOutputs;

	}

	@Override
	public List<Object> findDataDecisionComp(Long decisionTbId) {

		// Storing all data for decision display
		List<Object> lstObj = new ArrayList<Object>();

		DecisionTable decisionTable = decisionTableRepository.findOneByDecisionTbId(decisionTbId);
		if (decisionTable != null && decisionTable.getDecisionTbId() != null) {
			List<DecisionTableInputBean> inputBean = decisionTableInputBeanRepository.findDecisionInputBeanTypeTree(decisionTbId);
			// Process setting table index
			Map<String, String> mapTableIndex = new HashMap<String, String>();
			Map<String, Integer> mapSequence = new HashMap<String, Integer>();
			for (DecisionTableInputBean in : inputBean) {
				String currentGroup = "";
				if (in.getParentDecisionInputBeanId() != null) {
					currentGroup = mapTableIndex.get(in.getParentDecisionInputBeanId());

				}
				in.setGroupId(currentGroup);
				int maxIndex = mapSequence.getOrDefault(in.getGroupId(), 0);
				maxIndex++;
				if (in.getParentDecisionInputBeanId() == null) {
					in.setTableIndex(String.valueOf(maxIndex));
				} else {
					in.setTableIndex(currentGroup + "." + maxIndex);
				}
				mapTableIndex.put(in.getDecisionInputBeanId(), in.getTableIndex());
				mapSequence.put(in.getGroupId(), maxIndex);
			}

			List<DecisionTableOutputBean> outputBean = decisionTableOutputBeanRepository.findDecisionOutputBeanTypeTree(decisionTbId);
			// Process setting table index
			mapTableIndex = new HashMap<String, String>();
			mapSequence = new HashMap<String, Integer>();
			for (DecisionTableOutputBean in : outputBean) {
				String currentGroup = "";
				if (in.getParentDecisionOutputBeanId() != null) {
					currentGroup = mapTableIndex.get(in.getParentDecisionOutputBeanId());

				}
				in.setGroupId(currentGroup);
				int maxIndex = mapSequence.getOrDefault(in.getGroupId(), 0);
				maxIndex++;
				if (in.getParentDecisionOutputBeanId() == null) {
					in.setTableIndex(String.valueOf(maxIndex));
				} else {
					in.setTableIndex(currentGroup + "." + maxIndex);
				}
				mapTableIndex.put(in.getDecisionOutputBeanId(), in.getTableIndex());
				mapSequence.put(in.getGroupId(), maxIndex);
			}

			// Get value for Item design
			List<DecisionTableItemDesignBean> itemDesign = decisionTableItemDesignBeanRepository.findDecisionItemDesignBeanById(decisionTbId);
			List<List<DecisionTableItemDesignBean>> itemDesigns = DecisionTableUtils.getDataItemDesignType(itemDesign);
			lstObj.add(decisionTable);
			lstObj.add(inputBean);
			lstObj.add(outputBean);
			lstObj.add(itemDesigns.get(0));
			lstObj.add(itemDesigns.get(1));

			// If existence condition and action column
			if (itemDesigns.get(0) != null && !itemDesigns.get(0).isEmpty() && itemDesigns.get(1) != null && !itemDesigns.get(1).isEmpty()) {
				// Get all id item design
				List<DecisionTableConditionGroup> conditionGroup = decisionTableConditionGroupRepository.findConditionGroupById(itemDesign);
				if (conditionGroup != null && !conditionGroup.isEmpty()) {
					// Get value for decision item group by list decision
					// condition
					// group
					List<DecisionTableConditionItem> conditionItem = decisionTableConditionItemRepository.findConditionItemById(conditionGroup);
					if (conditionItem != null && conditionItem.size() > 0) {
						lstObj.add(conditionGroup);
						lstObj.add(conditionItem);
					}
				}
			} else {
				lstObj.add(null);
				lstObj.add(null);
			}
		} else {
			decisionTable = new DecisionTable();
			lstObj.add(decisionTable);
		}
		return lstObj;
	}

	/**
	 * Process modify business design logic
	 *
	 * @param businessDesign
	 */
	@Override
	public void modifyBusinessDesignLogic(BusinessDesign businessDesign, CommonModel commonModel) {
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		ResultMessages resultMessages;
		// common check
		businessDesign.setProjectId(commonModel.getWorkingProjectId());
		checkCommonOfBusinessDesign(businessDesign, commonModel);
		resultMessages = checkExistenceOfBusinessDesign(businessDesign.getBusinessLogicId(), commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		}
		resultMessages = checkDuplicatedOfBusinessDesign(businessDesign);
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			String packageName = null;
			if (BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(businessDesign.getBlogicType())) {
				// detect impact change design of common blogic
//				detectImpactWhenModifyCommonBlogic(businessDesign, commonModel,true);
				
				Long uploadFileId = businessDesign.getUploadFileId();
				if (Boolean.TRUE.equals(businessDesign.getCustomizeFlg())) {
					
					// PROCESS UPLOAD FILE
					// if have change file in form
					if (businessDesign.getFlagChangeFile() != null && businessDesign.getFlagChangeFile()) {
						if (businessDesign.getFile() != null && businessDesign.getFile().length > 0) {
							// First : modify/insert upload file
							if (uploadFileId != null) {
								// modify upload file
								UploadFile uploadFile = uploadFileRepository.findOne(uploadFileId);
								if (uploadFile == null) {
									throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0026)));
								}
								uploadFile.setFileName(businessDesign.getFileName());
								uploadFile.setContent(businessDesign.getFile());
								uploadFile.setUpdatedBy(commonModel.getCreatedBy());
								uploadFile.setSysDatetime(currentTime);
								if (uploadFileRepository.modify(uploadFile) <= 0) {
									throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0026)));
								}
								uploadFileId = uploadFile.getUploadFileId();
							} else {
								// insert upload file
								UploadFile uploadFile = new UploadFile();
								uploadFile.setFileName(businessDesign.getFileName());
								uploadFile.setContent(businessDesign.getFile());
								uploadFile.setCreatedBy(commonModel.getCreatedBy());
								uploadFile.setCreatedDate(currentTime);
								uploadFile.setUpdatedBy(commonModel.getUpdatedBy());
								uploadFile.setUpdatedDate(currentTime);
								if (uploadFileRepository.register(uploadFile) <= 0) {
									throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0026)));
								}
								uploadFileId = uploadFile.getUploadFileId();
								businessDesign.setUploadFileId(uploadFileId);
							}
						} else {
							// First : update business design because constraint
							businessDesign.setUploadFileId(null);
							// After : delete upload file if have data in
							// database
							if (uploadFileId != null) {
								UploadFile uploadFile = uploadFileRepository.findOne(uploadFileId);
								if (uploadFile == null) {
									throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0026)));
								}
								if (uploadFileRepository.delete(uploadFileId) <= 0) {
									throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0026)));
								}
							}
						}
					}
					packageName = businessDesign.getPackageName();
					// PROCESS DELETE OBJECT DEFINITION, SEQUENCE LOGIC
					businessDesignRepository.deleteObjectDefinitionAndSequenceLogic(businessDesign.getBusinessLogicId());
				} else {
					businessDesign.setUploadFileId(null);
				}

				
			}else{
				// detect impact change design of online blogic
				moduleService.validateModule(businessDesign.getModuleId(), commonModel);
			}
			// only process modify business design
			businessDesign.setPackageName(packageName);
			businessDesign.setUpdatedBy(commonModel.getUpdatedBy());
			businessDesign.setSysDatetime(currentTime);
			int resultModifyBusiness = businessDesignRepository.modifyBusinessDesign(businessDesign);
			if (resultModifyBusiness <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010)));
			}
			// modify inputbean/outputbean
			modifyInputBeanList(businessDesign, commonModel.getUpdatedBy(), currentTime, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
			modifyOutputBeanList(businessDesign, commonModel.getUpdatedBy(), currentTime, commonModel.getWorkingProjectId());
			if (BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(businessDesign.getBlogicType()) && Boolean.TRUE.equals(businessDesign.getCustomizeFlg())) {

			} else {
				modifyObjectDefinitionList(businessDesign, commonModel.getCreatedBy(), currentTime);
				modifySequence(businessDesign, commonModel);
			}
			
			//insert batch job
			if(BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(businessDesign.getBlogicType())){
				modifyAffectedOfCommonBlogic(businessDesign,commonModel);
			}else if(BusinessDesignConst.BLOGIC_TYPE_STANDARD.equals(businessDesign.getBlogicType())){
				modifyAffectedOfStandardBlogic(businessDesign,commonModel);
			}
		}
	}

	private void modifyInputBeanList(BusinessDesign businessDesign, Long updateBy, Timestamp currentTime, Long languageId, Long projectId) {
		List<InputBean> lstInputBean = businessDesign.getLstInputBean();
		Long businessLogicId = businessDesign.getBusinessLogicId();
		mKeyInClient.clear();
		List<InputBean> lstInputBeansRegister = new ArrayList<InputBean>();
		List<InputBean> lstInputBeansModify = new ArrayList<InputBean>();
		Long startSequence = new Long(0);
		int resultRegister = 0;
		int resultModify = 0;

		if (lstInputBean.size() > 0) {
			for (InputBean objInputBean : lstInputBean) {
				if (objInputBean.getInputBeanId() != null && objInputBean.getInputBeanId().contains(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_CODE)) {
					objInputBean.setInputBeanType(BusinessDesignConst.InputBeanType.CUSTOMIZE);
					lstInputBeansRegister.add(objInputBean);
				} else if (objInputBean.getInputBeanId() != null) {
					mKeyInClient.put(objInputBean.getInputBeanId().toString(), Long.valueOf(objInputBean.getInputBeanId()));
					lstInputBeansModify.add(objInputBean);
				}
			}
			if (lstInputBeansModify.size() > 0) {
				businessDesignRepository.deleteBeforModifyInputBean(lstInputBeansModify, businessLogicId);
				resultModify = businessDesignRepository.modifyInputBean(lstInputBeansModify);
				if (resultModify == 0) {
					throw new BusinessException("Error");
				}
			}
			// get sequence of inputbean
			// get list sequence
			if (lstInputBeansRegister.size() > 0) {
				Long sequenceInputBean = businessDesignRepository.getSequencesInputBean(lstInputBeansRegister.size() - 1);
				startSequence = sequenceInputBean - (lstInputBeansRegister.size() - 1);
				Map<String, Long> mapKeyInput = new HashMap<String, Long>();
				for (InputBean objInputBean : lstInputBeansRegister) {
					mapKeyInput.put(objInputBean.getInputBeanId(), startSequence);
					mKeyInClient.put(objInputBean.getInputBeanId(), startSequence);
					objInputBean.setInputBeanId(startSequence.toString());
					startSequence++;

					if (StringUtils.isBlank(objInputBean.getParentInputBeanId())) {
						objInputBean.setParentInputBeanId(null);
					}
					if (FunctionCommon.isEmpty(objInputBean.getScreenItemId())) {
						objInputBean.setScreenItemId(null);
					}
					objInputBean.setBusinessLogicId(businessLogicId);
					objInputBean.setInputBeanType(BusinessDesignConst.InputBeanType.ADDED_DEFAULT);

					// map key of parent
					if (mapKeyInput.containsKey(objInputBean.getParentInputBeanId())) {
						objInputBean.setParentInputBeanId(mapKeyInput.get(objInputBean.getParentInputBeanId()).toString());
					}
				}
				resultRegister = businessDesignRepository.registerInputBean(lstInputBeansRegister);
				if (resultRegister == 0) {
					throw new BusinessException("Error");
				}
			}
		} else {
			businessDesignRepository.deleteBeforModifyInputBean(lstInputBean, businessLogicId);
		}
	}

	private void modifyOutputBeanList(BusinessDesign businessDesign, Long updateBy, Timestamp currentTime, Long projectId) {
		List<OutputBean> lstOutputBeans = businessDesign.getLstOutputBean();
		Long businessLogicId = businessDesign.getBusinessLogicId();
		List<OutputBean> lstOutputBeansRegister = new ArrayList<OutputBean>();
		List<OutputBean> lstOutputBeansModify = new ArrayList<OutputBean>();
		Long startSequence = new Long(0);
		int resultRegister = 0;
		int resultModify = 0;
		List<OutputBean> lstOldOutputBeans = businessDesignRepository.findOutputBean(businessLogicId);

		Map<String, OutputBean> mapOldOutputBean = new HashMap<String, OutputBean>();
		if (CollectionUtils.isNotEmpty(lstOldOutputBeans)) {
			for (OutputBean outputBean : lstOldOutputBeans) {
				mapOldOutputBean.put(outputBean.getOutputBeanId(), outputBean);
			}
		}

		mKeyOuClient.clear();
		if (lstOutputBeans.size() > 0) {
			for (OutputBean outputBean : lstOutputBeans) {
				if (outputBean.getOutputBeanId() != null && outputBean.getOutputBeanId().contains(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_CODE)) {
					lstOutputBeansRegister.add(outputBean);
				} else if (outputBean.getOutputBeanId() != null) {
					mKeyOuClient.put(outputBean.getOutputBeanId().toString(), Long.valueOf(outputBean.getOutputBeanId()));

					OutputBean outOld = mapOldOutputBean.getOrDefault(outputBean.getOutputBeanId(), null);
					if (outOld != null) {
						outputBean.setTblDesignId(outOld.getTblDesignId());
						outputBean.setColumnId(outOld.getColumnId());
						outputBean.setObjectType(outOld.getObjectType());
						outputBean.setObjectId(outOld.getObjectId());
					}

					lstOutputBeansModify.add(outputBean);
				}
			}
			businessDesignRepository.deleteBeforModifyOutputBean(lstOutputBeansModify, businessLogicId);
			if (lstOutputBeansModify.size() > 0) {
				resultModify = businessDesignRepository.modifyOutputBean(lstOutputBeansModify);
				if (resultModify == 0) {
					throw new BusinessException("Error");
				}
			}
			// get sequence of outputbean
			// get list sequence
			if (lstOutputBeansRegister.size() > 0) {
				Long sequence = businessDesignRepository.getSequencesOutputBean(lstOutputBeansRegister.size() - 1);
				startSequence = sequence - (lstOutputBeansRegister.size() - 1);

				Map<String, Long> mapKeyOutput = new HashMap<String, Long>();
				for (OutputBean objOutputBean : lstOutputBeansRegister) {
					mapKeyOutput.put(objOutputBean.getOutputBeanId(), startSequence);
					mKeyOuClient.put(objOutputBean.getOutputBeanId().toString(), startSequence);
					objOutputBean.setOutputBeanId(startSequence.toString());
					startSequence++;

					if ("".equals(objOutputBean.getParentOutputBeanId())) {
						objOutputBean.setParentOutputBeanId(null);
					}
					objOutputBean.setBusinessLogicId(businessLogicId);

					// map key of parent
					if (mapKeyOutput.containsKey(objOutputBean.getParentOutputBeanId())) {
						objOutputBean.setParentOutputBeanId(mapKeyOutput.get(objOutputBean.getParentOutputBeanId()).toString());
					}
				}
				resultRegister = businessDesignRepository.registerOutputBean(lstOutputBeansRegister);
				if (resultRegister == 0) {
					throw new BusinessException("Error");
				}
			}
		} else {
			businessDesignRepository.deleteBeforModifyOutputBean(lstOutputBeans, businessLogicId);
		}

		// Add problem list when modify in input bean
//		List<ProblemList> lstProblemForCommon = new ArrayList<ProblemList>();
//		List<ProblemList> lstProblemTemplateForCommon = new ArrayList<ProblemList>();
//		List<BusinessDesign> lstUsedCommonBusinessDesigns = businessDesignRepository.findBlogicByCommonBusinessBlogicId(businessDesign.getBusinessLogicId());
//		List<Long> lstFromResourceId = new ArrayList<Long>();
//		boolean isInsertProblemForCommon = false;
//		if (BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(businessDesign.getBlogicType()) && lstUsedCommonBusinessDesigns.size() > 0) {
//			isInsertProblemForCommon = true;
//		}
//		// insert problem list
//		if (isInsertProblemForCommon) {
//			List<CommonOutputValue> lstOutputBeanOfUsedCommons = commonCompRepository.findAllCommonOutputValueByUsedCommonBusinessLogic(businessDesign.getBusinessLogicId());
//			for (OutputBean outputBean : lstOutputBeansRegister) {
//				ProblemList problemList = new ProblemList();
//				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0006, outputBean.getOutputBeanName(), businessDesign.getBusinessLogicName()));
//				problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
//				problemList.setResourceId(businessDesign.getBusinessLogicId());
//				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
//				problemList.setAutofixFlg(AutoFix.DISABLE);
//				problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
//				problemList.setModuleId(null);
//				problemList.setProjectId(projectId);
//				problemList.setFromResourceType(DbDomainConst.FromResourceType.BLOGIC_COMMON_OUTPUTBEAN);
//				problemList.setFromResourceId(Long.valueOf(outputBean.getOutputBeanId()));
//				problemList.setCreatedBy(updateBy);
//				problemList.setCreatedDate(currentTime);
//				lstProblemTemplateForCommon.add(problemList);
//			}
//			for (OutputBean outputBean : lstOldOutputBeans) {
//				Long id = mKeyOuClient.getOrDefault(outputBean.getOutputBeanId(), -1L);
//				if (id.equals(Long.valueOf(-1L))) {
//					ProblemList problemList = new ProblemList();
//					problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0007, outputBean.getOutputBeanName(), businessDesign.getBusinessLogicName()));
//					problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
//					problemList.setResourceId(businessDesign.getBusinessLogicId());
//					problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
//					problemList.setAutofixFlg(AutoFix.ENABLE);
//					problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
//					problemList.setModuleId(null);
//					problemList.setProjectId(projectId);
//					problemList.setFromResourceType(DbDomainConst.FromResourceType.BLOGIC_COMMON_OUTPUTBEAN);
//					problemList.setFromResourceId(Long.valueOf(outputBean.getOutputBeanId()));
//					problemList.setCreatedBy(updateBy);
//					problemList.setCreatedDate(currentTime);
//					lstProblemTemplateForCommon.add(problemList);
//				}
//				lstFromResourceId.add(Long.valueOf(outputBean.getOutputBeanId()));
//			}
//
//			for (BusinessDesign usedBLogic : lstUsedCommonBusinessDesigns) {
//				for (ProblemList problem : lstProblemTemplateForCommon) {
//					problem.setModuleId(usedBLogic.getModuleId());
//					problem.setResourceId(usedBLogic.getBusinessLogicId());
//					lstProblemForCommon.add(SerializationUtils.clone(problem));
//				}
//			}
//
//			for (CommonOutputValue commonOutput : lstOutputBeanOfUsedCommons) {
//				for (OutputBean outputBean : lstOutputBeansModify) {
//					if (outputBean.getOutputBeanId().equals(commonOutput.getOutputBeanId().toString())) {
//						if (!commonOutput.getDataType().equals(outputBean.getDataType()) || !commonOutput.getArrayFlg().equals(outputBean.getArrayFlg())) {
//							ProblemList problemList = new ProblemList();
//							problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0008, outputBean.getOutputBeanName(), businessDesign.getBusinessLogicName(), BusinessDesignHelper.getDataTypeStr(commonOutput.getDataType(), commonOutput.getArrayFlg()), BusinessDesignHelper.getDataTypeStr(outputBean.getDataType(), outputBean.getArrayFlg())));
//							problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
//							problemList.setResourceId(commonOutput.getBusinessLogicId());
//							problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
//							problemList.setAutofixFlg(AutoFix.DISABLE);
//							problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
//							problemList.setModuleId(commonOutput.getModuleId());
//							problemList.setProjectId(projectId);
//							problemList.setFromResourceType(DbDomainConst.FromResourceType.BLOGIC_COMMON_OUTPUTBEAN);
//							problemList.setFromResourceId(Long.valueOf(commonOutput.getOutputBeanId()));
//							problemList.setCreatedBy(updateBy);
//							problemList.setCreatedDate(currentTime);
//							lstProblemForCommon.add(problemList);
//						}
//						break;
//					}
//				}
//			}
//			
//			// delete related problem
//			if (lstFromResourceId.size() > 0) {
//				problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.BLOGIC_COMMON_OUTPUTBEAN, lstFromResourceId);
//			}
//			// insert problem into database
//			if (lstProblemForCommon.size() > 0) {
//				problemListRepository.multiRegisterProblem(lstProblemForCommon);
//			}
//		}
			
		// insert problem list for related screen design
//		List<ProblemList> lstProblemTemplateForScreen = new ArrayList<ProblemList>();
//		if (isInsertProblemForLinkedByScreen) {
//			for (OutputBean outputBean : lstOldOutputBeans) {
//				Long id = mKeyInClient.getOrDefault(outputBean.getOutputBeanId(), -1L);
//				if (id.equals(Long.valueOf(-1L))) {
//					for (ScreenItem usedScreenItem : lstUsedItem) {
//						ProblemList problemList = new ProblemList();
//						problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0024, outputBean.getOutputBeanName(), businessDesign.getBusinessLogicName(), usedScreenItem.getMessageDesign().getMessageString()));
//						problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_ITEM);
//						problemList.setResourceId(usedScreenItem.getScreenId());
//						problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
//						problemList.setAutofixFlg(AutoFix.ENABLE);
//						problemList.setUrlId(DbDomainConst.ResourceURL.URL_SCREEN_DESIGN);
//						problemList.setModuleId(usedScreenItem.getModuleId());
//						problemList.setProjectId(projectId);
//						problemList.setFromResourceType(DbDomainConst.FromResourceType.BLOGIC_RELATED_SCREEN_INPUTBEAN);
//						problemList.setFromResourceId(Long.valueOf(outputBean.getOutputBeanId()));
//						problemList.setCreatedBy(updateBy);
//						problemList.setCreatedDate(currentTime);
//						lstProblemTemplateForScreen.add(problemList);
//					}
//				}
//				lstFromResourceIdScreenItem.add(Long.valueOf(outputBean.getOutputBeanId()));
//			}
//
//			// delete related problem
//			if (lstFromResourceIdScreenItem.size() > 0) {
//				problemListRepository.deleteResourceType(DbDomainConst.FromResourceType.BLOGIC_RELATED_SCREEN_INPUTBEAN, lstFromResourceIdScreenItem);
//			}
//			// insert problem into database
//			if (lstProblemTemplateForScreen.size() > 0) {
//				problemListRepository.multiRegisterProblem(lstProblemTemplateForScreen);
//			}
//		}
	}

	private void modifyObjectDefinitionList(BusinessDesign businessDesign, Long updateBy, Timestamp currentTime) {
		List<ObjectDefinition> lstObjectDefinitions = businessDesign.getLstObjectDefinition();
		List<ObjectDefinition> lstObjectDefinitionsRegister = new ArrayList<ObjectDefinition>();
		List<ObjectDefinition> lstObjectDefinitionsModify = new ArrayList<ObjectDefinition>();
		Long businessLogicId = businessDesign.getBusinessLogicId();
		mKeyObClient.clear();
		Long startSequence = new Long(0);
		int resultRegister = 0;
		int resultModify = 0;
		for (ObjectDefinition objectDefinition : lstObjectDefinitions) {
			if (objectDefinition.getObjectDefinitionId() != null && objectDefinition.getObjectDefinitionId().contains(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_CODE)) {
				lstObjectDefinitionsRegister.add(objectDefinition);
			} else if (objectDefinition.getObjectDefinitionId() != null) {
				mKeyObClient.put(objectDefinition.getObjectDefinitionId().toString(), Long.valueOf(objectDefinition.getObjectDefinitionId()));
				lstObjectDefinitionsModify.add(objectDefinition);
			}
		}

		businessDesignRepository.deleteBeforModifyObjectDefinition(lstObjectDefinitionsModify, businessLogicId);
		if (lstObjectDefinitionsModify.size() > 0) {
			resultModify = businessDesignRepository.modifyObjectDefinition(lstObjectDefinitionsModify);
			if (resultModify == 0) {
				throw new BusinessException("Error");
			}
		}
		// get sequence of object definition
		// get list sequence
		if (lstObjectDefinitionsRegister.size() > 0) {
			Long sequence = businessDesignRepository.getSequencesObjectDefinition(lstObjectDefinitionsRegister.size() - 1);
			startSequence = sequence - (lstObjectDefinitionsRegister.size() - 1);

			Map<String, Long> mapKeyOutput = new HashMap<String, Long>();
			for (ObjectDefinition objObjectDefinition : lstObjectDefinitionsRegister) {
				mapKeyOutput.put(objObjectDefinition.getObjectDefinitionId(), startSequence);
				mKeyObClient.put(objObjectDefinition.getObjectDefinitionId().toString(), startSequence);
				objObjectDefinition.setObjectDefinitionId(startSequence.toString());
				startSequence++;

				if ("".equals(objObjectDefinition.getParentObjectDefinitionId())) {
					objObjectDefinition.setParentObjectDefinitionId(null);
				}
				objObjectDefinition.setBusinessLogicId(businessLogicId);

				// map key of parent
				if (mapKeyOutput.containsKey(objObjectDefinition.getParentObjectDefinitionId())) {
					objObjectDefinition.setParentObjectDefinitionId(mapKeyOutput.get(objObjectDefinition.getParentObjectDefinitionId()).toString());
				}
			}
			resultRegister = businessDesignRepository.registerObjectDefinition(lstObjectDefinitionsRegister);
			if (resultRegister == 0) {
				throw new BusinessException("Error");
			}
		}
	}

	private void modifySequence(BusinessDesign businessDesign, CommonModel commonModel) {
		Long startSequence = new Long(0);
		formulaDefinitionRepository.deleteFormulaDefinitionByBusinessLogicId(businessDesign.getBusinessLogicId());
		businessDesignRepository.deleteSequenceAndComponentByBlogicId(businessDesign.getBusinessLogicId());

		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnectors = new ArrayList<SequenceConnector>();
		lstSequenceLogic = BusinessDesignHelper.parseSequenceLogic(businessDesign.getJsonComponent());
		lstConnectors = BusinessDesignHelper.parseSequenceConnector(businessDesign.getJsonConnector());
		// component and connector
		businessDesign.setLstSequenceLogics(lstSequenceLogic);
		businessDesign.setLstSequenceConnectors(lstConnectors);
		this.parseNode(businessDesign);
		// get sequence of component
		// get list sequence
		lstSequenceLogic = businessDesign.getLstSequenceLogics();
		mKeySequenceLogic.clear();
		if (lstSequenceLogic.size() > 0) {
			Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
			startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
			for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
				mKeySequenceLogic.put(objSequenceLogic.getSequenceLogicId(), startSequence);
				objSequenceLogic.setSequenceLogicId(startSequence.toString());
				if (objSequenceLogic.getSequenceLogicName() == null) {
					objSequenceLogic.setSequenceLogicName("");
				}
				if (objSequenceLogic.getComponentType() == null) {
					objSequenceLogic.setComponentType(0);
				}
				objSequenceLogic.setBusinessLogicId(businessDesign.getBusinessLogicId());
				if (objSequenceLogic.getParentSequenceLogicId() != null) {
					String parentSequence = mKeySequenceLogic.get(objSequenceLogic.getParentSequenceLogicId()) == null ? null : mKeySequenceLogic.get(objSequenceLogic.getParentSequenceLogicId()).toString();
					objSequenceLogic.setParentSequenceLogicId(parentSequence);
				}
				startSequence++;
			}
			// map related sequence logic id
			for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
				if (BusinessDesignConst.COMPONENT_IF == objSequenceLogic.getComponentType().intValue()) {
					if (objSequenceLogic.getRelatedSequenceLogicId() != null) {
						String relatedSequence = mKeySequenceLogic.get(objSequenceLogic.getRelatedSequenceLogicId()) == null ? null : mKeySequenceLogic.get(objSequenceLogic.getRelatedSequenceLogicId()).toString();
						objSequenceLogic.setRelatedSequenceLogicId(relatedSequence);
					}
				}
			}
			businessDesignRepository.registerSequenceLogic(lstSequenceLogic);
		}
		lstConnectors = businessDesign.getLstSequenceConnectors();
		if (lstConnectors.size() > 0) {
			Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnectors.size() - 1);
			startSequence = sequenceConnector - (lstConnectors.size() - 1);

			for (SequenceConnector objSequenceConnector : lstConnectors) {
				objSequenceConnector.setSequenceConnectorId(startSequence);
				objSequenceConnector.setConnectorSource(mKeySequenceLogic.get(objSequenceConnector.getConnectorSource()).toString());
				objSequenceConnector.setConnectorDest(mKeySequenceLogic.get(objSequenceConnector.getConnectorDest()).toString());
				startSequence++;
			}
			businessDesignRepository.registerSequenceConnector(lstConnectors);
		}

		// insert component detail
		registerComponent(lstSequenceLogic, businessDesign, commonModel);
	}

	/**
	 * process change design status
	 *
	 * @param businessLogicId
	 */
	@Override
	public void modifyDesignStatus(Long businessLogicId,Timestamp oldUpdatedDate, CommonModel commonModel) {
		BusinessDesign businessDesign = businessDesignRepository.findBusinessLogicInformation(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
		if (businessDesign == null) {
			ResultMessages resultMessages = ResultMessages.error();
			resultMessages.add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010));
			throw new BusinessException(resultMessages);
		} else {
			if (BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(businessDesign.getBlogicType())) {
			    commonModel.setProjectId(businessDesign.getProjectId());
				projectService.validateProject(commonModel);
			} else {
			    commonModel.setProjectId(businessDesign.getProjectId());
				moduleService.validateModule(businessDesign.getModuleId(), commonModel);
			}
			
			if (DbDomainConst.DesignStatus.UNDER_DESIGN.equals(businessDesign.getDesignStatus())) {
				businessDesign.setDesignStatus(DbDomainConst.DesignStatus.FIXED);
			} else {
				businessDesign.setDesignStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
			}
			businessDesign.setUpdatedBy(commonModel.getCreatedBy());
			businessDesign.setUpdatedDate(oldUpdatedDate);
			businessDesign.setSysDatetime(FunctionCommon.getCurrentTime());
			int result = businessDesignRepository.modifyDesignStatus(businessDesign);
			if (result <= 0) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010)));
			}
		}
	}

	/**
	 * Processing for delete business logic design
	 */
	@Override
	public void deleteBusinessDesignLogic(Long businessLogicId, Boolean deleteObjectHasFk ,Timestamp oldUpdatedDate, CommonModel commonModel) throws BusinessException {
		BusinessDesign businessDesign = businessDesignRepository.findBusinessLogicInformation(businessLogicId, null, null);
		if (businessDesign == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010)));
		}
		if (DbDomainConst.DesignStatus.FIXED.equals(businessDesign.getDesignStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, businessDesign.getBusinessLogicName()));
		}
		// Check change status parent
		problemListRepository.deleteResourceOfBLogic(DbDomainConst.FromResourceType.BLOGIC, businessDesign.getBusinessLogicId());
		if (BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(businessDesign.getBlogicType())) {
			commonModel.setProjectId(businessDesign.getProjectId());
			projectService.validateProject(commonModel);
			deleteAffectedOfCommonBlogic(businessDesign, commonModel);
		} else {
			moduleService.validateModule(businessDesign.getModuleId(), commonModel);
			if (BusinessDesignConst.BLOGIC_TYPE_STANDARD.equals(businessDesign.getBlogicType())){
				deleteAffectedOfStandardBlogic(businessDesign, commonModel);
			}
		}
		formulaDefinitionRepository.deleteFormulaDefinitionByBusinessLogicId(businessLogicId);
		businessDesignRepository.deleteSequenceAndComponentByBlogicId(businessLogicId);
		businessDesignRepository.deleteBusinessDesign(businessLogicId);
	}

	/**
	 * Processing for impact change design
	 */
	@Override
	public void processInpactChangeDesign(Long businessLogicId, Boolean deleteObjectHasFk, List<BusinessDesign> bLogicProcessToNavigatorList, CommonModel commonModel) throws BusinessException {
		// Check exist
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		BusinessDesign businessDesign = businessDesignRepository.findBusinessLogicInformation(businessLogicId, null, null);
		if (businessDesign == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010)));
		}
		if (DbDomainConst.DesignStatus.FIXED.equals(businessDesign.getDesignStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, businessDesign.getBusinessLogicName()));
		}

		List<ProblemList> lstProblemList = new ArrayList<ProblemList>();
		
		if (bLogicProcessToNavigatorList.size() > 0) {
			if (deleteObjectHasFk) {
				for (BusinessDesign usedBlogic : bLogicProcessToNavigatorList) {
					ProblemList problemList = new ProblemList();
					problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0023, businessDesign.getScreenCode()));
					problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
					problemList.setResourceId(usedBlogic.getBusinessLogicId());
					problemList.setProblemType(DbDomainConst.ProblemType.MISS_SETTING);
					problemList.setAutofixFlg(AutoFix.DISABLE);
					problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
					problemList.setModuleId(usedBlogic.getModuleId());
					problemList.setProjectId(businessDesign.getProjectId());
					problemList.setFromResourceType(DbDomainConst.FromResourceType.BLOGIC);
					problemList.setFromResourceId(businessDesign.getBusinessLogicId());
					problemList.setCreatedBy(commonModel.getCreatedBy());
					problemList.setCreatedDate(currentTime);
					lstProblemList.add(problemList);
				}
			} else {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0097));
			}
		}
		// insert problem list into database
		if (lstProblemList.size() > 0) {
			problemListRepository.multiRegisterProblem(lstProblemList);
			lstProblemList.clear();
		}
	}
	/**
	 * Process register business logic design
	 *
	 * @param businessDesign
	 *            entity
	 */
	@Override
	public void registerBusinessLogicDesign(BusinessDesign businessDesign, Long projectId, Long languageId, CommonModel commonModel) {
		businessDesign.setProjectId(commonModel.getWorkingProjectId());
		checkCommonOfBusinessDesign(businessDesign, commonModel);
		Set<ScreenArea> areaUpdateMappingObjectType = new HashSet<ScreenArea>();
		List<ScreenArea> screenAreas = screenAreaRepository.getLstScreenAreaByScreenFormId(businessDesign.getScreenId(), businessDesign.getScreenFormId(), projectId, languageId);
		mKeyInClient.clear();
		mKeyObClient.clear();
		mKeyOuClient.clear();
		
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		List<OutputBean> lstOutputBeanForm = businessDesign.getLstOutputBean();
		List<OutputBean> lstOutputBean = new ArrayList<OutputBean>();
		// remove empty row
		for (OutputBean out : lstOutputBeanForm) {
			if (StringUtils.isNotBlank(out.getOutputBeanId())) {
				lstOutputBean.add(out);
			}
		}
		List<ObjectDefinition> lstObjectDefinitionsForm = businessDesign.getLstObjectDefinition();
		List<ObjectDefinition> lstObjectDefinitions = new ArrayList<ObjectDefinition>();
		// remove empty row
		for (ObjectDefinition od : lstObjectDefinitionsForm) {
			if (StringUtils.isNotBlank(od.getObjectDefinitionId())) {
				lstObjectDefinitions.add(od);
			}
		}

		// check duplicated
		ResultMessages resultMessages = checkDuplicatedOfBusinessDesign(businessDesign);
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {

			Long uploadFileId = null;
			if (Boolean.TRUE.equals(businessDesign.getCustomizeFlg())) {
				// insert File content if have
				if (businessDesign.getFile() != null && businessDesign.getFile().length > 0) {
					UploadFile uploadFile = new UploadFile();
					uploadFile.setFileName(businessDesign.getFileName());
					uploadFile.setContent(businessDesign.getFile());
					uploadFile.setCreatedBy(commonModel.getCreatedBy());
					uploadFile.setCreatedDate(currentTime);
					uploadFile.setUpdatedBy(commonModel.getUpdatedBy());
					uploadFile.setUpdatedDate(currentTime);
					uploadFileRepository.register(uploadFile);
					uploadFileId = uploadFile.getUploadFileId();
				}
			} else {
				businessDesign.setPackageName(null);
			}
			businessDesign.setUploadFileId(uploadFileId);
			businessDesign.setCreatedBy(commonModel.getCreatedBy());
			businessDesign.setCreatedDate(currentTime);
			businessDesign.setUpdatedBy(commonModel.getUpdatedBy());
			businessDesign.setUpdatedDate(currentTime);
			businessDesign.setSysDatetime(currentTime);
			businessDesign.setPatternType(BusinessDesignConst.SCREEN_PATTERN_NORMAL);
			businessDesign.setCompleteFlg(false);
			businessDesign.setConfirmFlg(false);
			businessDesign.setDesignMode(BusinessDesignConst.DESIGN_MODE_MANUAL);
			// insert business design
			businessDesignRepository.register(businessDesign);

			Long startSequence;

			// get sequence of inputbean
			// get list sequence
			if (businessDesign.getLstInputBean().size() > 0) {
				Long sequenceInputBean = businessDesignRepository.getSequencesInputBean(businessDesign.getLstInputBean().size() - 1);
				startSequence = sequenceInputBean - (businessDesign.getLstInputBean().size() - 1);
				Map<String, Long> mapKeyInput = new HashMap<String, Long>();
				// List<MessageDesign> messageDesignArray = new
				// ArrayList<MessageDesign>();
				for (InputBean objInputBean : businessDesign.getLstInputBean()) {
					mapKeyInput.put(objInputBean.getInputBeanId(), startSequence);
					mKeyInClient.put(objInputBean.getInputBeanId(), startSequence);
					objInputBean.setInputBeanId(startSequence.toString());
					if(StringUtils.isBlank(objInputBean.getParentInputBeanId())) {
						objInputBean.setParentInputBeanId(null);
					}
					objInputBean.setScreenItemId(null);
					objInputBean.setBusinessLogicId(businessDesign.getBusinessLogicId());
					if(objInputBean.getInputBeanType() == null) {
						objInputBean.setInputBeanType(BusinessDesignConst.InputBeanType.CUSTOMIZE);
					}

					// map key of parent
					if (mapKeyInput.containsKey(objInputBean.getParentInputBeanId())) {
						objInputBean.setParentInputBeanId(mapKeyInput.get(objInputBean.getParentInputBeanId()).toString());
					}
					
					if (screenAreas != null) {
						for (ScreenArea area : screenAreas) {
							if (BusinessDesignConst.DataType.OBJECT.equals(objInputBean.getDataType()) && area.getAreaCode().equals(objInputBean.getInputBeanCode())) {
								area.setObjectMappingId(startSequence);
								areaUpdateMappingObjectType.add(area);
							}
						}
					}
					startSequence++;

				}
				
				businessDesignRepository.registerInputBean(businessDesign.getLstInputBean());
				for (ScreenArea area : areaUpdateMappingObjectType) {
					area.setObjectMappingType(ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_INPUT_BEAN);
					screenAreaRepository.updateObjectMappingTypeOfScreenArea(area);
				}
			}

			// get sequence of outputbean
			// get list sequence
			if (lstOutputBean.size() > 0) {
				Long sequenceOutputBean = businessDesignRepository.getSequencesOutputBean(lstOutputBean.size() - 1);
				startSequence = sequenceOutputBean - (lstOutputBean.size() - 1);

				Map<String, Long> mapKeyOutput = new HashMap<String, Long>();
				for (OutputBean objOutputBean : lstOutputBean) {
					mapKeyOutput.put(objOutputBean.getOutputBeanId(), startSequence);
					mKeyOuClient.put(objOutputBean.getOutputBeanId(), startSequence);
					objOutputBean.setOutputBeanId(startSequence.toString());
					objOutputBean.setBusinessLogicId(businessDesign.getBusinessLogicId());
					startSequence++;

					if ("".equals(objOutputBean.getParentOutputBeanId())) {
						objOutputBean.setParentOutputBeanId(null);
					}
					// map key of parent
					if (mapKeyOutput.containsKey(objOutputBean.getParentOutputBeanId())) {
						objOutputBean.setParentOutputBeanId(mapKeyOutput.get(objOutputBean.getParentOutputBeanId()).toString());
					}
				}
				businessDesignRepository.registerOutputBean(lstOutputBean);
			}

			// if Register common business logic design and design type is
			// Customize : don't insert object definition and blogic setting
			if (FunctionCommon.equals(businessDesign.getBlogicType(), BusinessDesignConst.BLOGIC_TYPE_COMMON) && Boolean.TRUE.equals(businessDesign.getCustomizeFlg())) {

			} else {
				List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
				List<SequenceConnector> lstConnectors = new ArrayList<SequenceConnector>();
				lstSequenceLogic = BusinessDesignHelper.parseSequenceLogic(businessDesign.getJsonComponent());
				lstConnectors = BusinessDesignHelper.parseSequenceConnector(businessDesign.getJsonConnector());
				businessDesign.setLstSequenceLogics(lstSequenceLogic);
				businessDesign.setLstSequenceConnectors(lstConnectors);
				this.parseNode(businessDesign);
				lstSequenceLogic = businessDesign.getLstSequenceLogics();
				lstConnectors = businessDesign.getLstSequenceConnectors();
				// get sequence of object definition
				// get list sequence
				if (lstObjectDefinitions.size() > 0) {
					Long sequenceObjectDefinition = businessDesignRepository.getSequencesObjectDefinition(lstObjectDefinitions.size() - 1);
					startSequence = sequenceObjectDefinition - (lstObjectDefinitions.size() - 1);

					Map<String, Long> mapKeyObjectDefinition = new HashMap<String, Long>();
					for (ObjectDefinition objObjectDefinition : lstObjectDefinitions) {
						mapKeyObjectDefinition.put(objObjectDefinition.getObjectDefinitionId(), startSequence);
						mKeyObClient.put(objObjectDefinition.getObjectDefinitionId(), startSequence);
						objObjectDefinition.setObjectDefinitionId(startSequence.toString());
						objObjectDefinition.setBusinessLogicId(businessDesign.getBusinessLogicId());
						startSequence++;

						if ("".equals(objObjectDefinition.getParentObjectDefinitionId())) {
							objObjectDefinition.setParentObjectDefinitionId(null);
						}

						// map key of parent
						if (mapKeyObjectDefinition.containsKey(objObjectDefinition.getParentObjectDefinitionId())) {
							objObjectDefinition.setParentObjectDefinitionId(mapKeyObjectDefinition.get(objObjectDefinition.getParentObjectDefinitionId()).toString());
						}
					}
					businessDesignRepository.registerObjectDefinition(lstObjectDefinitions);
				}

				// component and connector
				// get sequence of component
				// get list sequence
				mKeySequenceLogic.clear();
				if (lstSequenceLogic.size() > 0) {
					Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
					startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
					for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
						mKeySequenceLogic.put(objSequenceLogic.getSequenceLogicId(), startSequence);
						objSequenceLogic.setSequenceLogicId(startSequence.toString());
						if (objSequenceLogic.getSequenceLogicName() == null) {
							objSequenceLogic.setSequenceLogicName("");
						}
						if (objSequenceLogic.getComponentType() == null) {
							objSequenceLogic.setComponentType(0);
						}
						objSequenceLogic.setBusinessLogicId(businessDesign.getBusinessLogicId());
						if (objSequenceLogic.getParentSequenceLogicId() != null) {
							objSequenceLogic.setParentSequenceLogicId(mKeySequenceLogic.get(objSequenceLogic.getParentSequenceLogicId()).toString());
						}
						startSequence++;
					}

					// map related sequence logic id
					for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
						if (BusinessDesignConst.COMPONENT_IF == objSequenceLogic.getComponentType().intValue()) {
							if (objSequenceLogic.getRelatedSequenceLogicId() != null) {
								objSequenceLogic.setRelatedSequenceLogicId(mKeySequenceLogic.get(objSequenceLogic.getRelatedSequenceLogicId()).toString());
							}
						}
					}
					businessDesignRepository.registerSequenceLogic(lstSequenceLogic);
				}
				if (lstConnectors.size() > 0) {
					Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnectors.size() - 1);
					startSequence = sequenceConnector - (lstConnectors.size() - 1);
					for (SequenceConnector objSequenceConnector : lstConnectors) {
						objSequenceConnector.setSequenceConnectorId(startSequence);
						objSequenceConnector.setConnectorSource(mKeySequenceLogic.get(objSequenceConnector.getConnectorSource()).toString());
						objSequenceConnector.setConnectorDest(mKeySequenceLogic.get(objSequenceConnector.getConnectorDest()).toString());
						startSequence++;
					}
					businessDesignRepository.registerSequenceConnector(lstConnectors);
				}

				// insert component detail
				registerComponent(lstSequenceLogic, businessDesign, commonModel);
			}
		}

	}

	private int registerFeedbackComponent(List<FeedbackComponent> lstFeedbackComponents, Long businessLogicId) {
		int result = 0;
		Long startSequence = 0L;
		Long startMessageParameterSequence = 0L;
		// execute feedback component
		if (lstFeedbackComponents.size() > 0) {
			List<MessageParameter> lstMessageParameters = new ArrayList<MessageParameter>();
			Long sequenceFeedbackComponent = feedbackComponentRepository.getSequencesFeedbackComponent(lstFeedbackComponents.size() - 1);
			startSequence = sequenceFeedbackComponent - (lstFeedbackComponents.size() - 1);
			for (FeedbackComponent objFeedback : lstFeedbackComponents) {
				objFeedback.setFeedbackComponentId(startSequence);
				startSequence++;

				if (CollectionUtils.isNotEmpty(objFeedback.getMessageParameter())) {
					List<MessageParameter> lstTempParameters = initialMessageParameters(objFeedback.getMessageParameter(), BusinessDesignConst.MessageParameter.TARGET_TYPE_FEEDBACK, objFeedback.getFeedbackComponentId(), businessLogicId);
					lstMessageParameters.addAll(lstTempParameters);
				}
			}
			result = feedbackComponentRepository.registerFeedbackComponent(lstFeedbackComponents);

			if (lstMessageParameters.size() > 0) {
				int size = lstMessageParameters.size() - 1;
				Long sequenceParameter = messageParameterRepository.getSequencesMessageParameter(size);
				startMessageParameterSequence = sequenceParameter - (lstMessageParameters.size() - 1);

				for (MessageParameter objContent : lstMessageParameters) {
					objContent.setMessageParameterId(startMessageParameterSequence);
					startMessageParameterSequence++;
					// index of pass parameter
					if (CollectionUtils.isNotEmpty(objContent.getLstParameterIndex())) {
						for (BDParameterIndex index : objContent.getLstParameterIndex()) {
							index.setTableId(objContent.getMessageParameterId());
						}
					}
				}

				result = messageParameterRepository.registerMessageParameter(lstMessageParameters);
			}
		}
		return result;
	}

	private int registerIfComponent(List<IfComponent> lstIfComponents, Long projectId) {
		int result = 0;
		Long startSequence = 0L;
		// execute if component
		if (lstIfComponents.size() > 0) {
			List<IfConditionDetail> lstIfConditionDetails = new ArrayList<IfConditionDetail>();
			Long sequenceIfComponent = ifComponentRepository.getSequencesIfComponent(lstIfComponents.size() - 1);
			startSequence = sequenceIfComponent - (lstIfComponents.size() - 1);
			for (IfComponent objIfComponent : lstIfComponents) {
				objIfComponent.setIfComponentId(startSequence);
				startSequence++;

				if (objIfComponent.getIfConditionDetails() != null) {
					for (IfConditionDetail objCondDetail : objIfComponent.getIfConditionDetails()) {
						objCondDetail.setIfComponentId(objIfComponent.getIfComponentId());
						lstIfConditionDetails.add(objCondDetail);
					}
				}
			}
			result = ifComponentRepository.registerIfComponent(lstIfComponents);
			result = ifConditionDetailRepository.registerIfConditionDetail(lstIfConditionDetails);
		}
		return result;
	}

	private int registerNavigatorComponent(List<NavigatorComponent> lstNavigatorComponents, BusinessDesign businessDesign) {
		int result = 0;
		Long startSequence = 0L;
		Long startNavigatorDetailSequence = 0L;
		// execute navigator component
		if (lstNavigatorComponents.size() > 0) {
			Long sequenceNavigatorComponent = navigationComponentRepository.getSequencesNavigationComponent(lstNavigatorComponents.size() - 1);
			startSequence = sequenceNavigatorComponent - (lstNavigatorComponents.size() - 1);

			// get sequence of navigator detail
			int countInputBeans = 0;
			for (NavigatorComponent objComponent : lstNavigatorComponents) {
				if (objComponent.getParameterInputBeans() != null) {
					countInputBeans += objComponent.getParameterInputBeans().size();
				}
			}
			if (countInputBeans > 0) {
				Long sequenceNavigatorDetail = navigationComponentRepository.getSequencesNavigationDetail(countInputBeans - 1);
				startNavigatorDetailSequence = sequenceNavigatorDetail - (countInputBeans - 1);
			}
			for (NavigatorComponent objComponent : lstNavigatorComponents) {
				// List<NavigatorDetail> lstNavigatorDetails = new
				// ArrayList<NavigatorDetail>();
				objComponent.setNavigatorComponentId(startSequence);
				startSequence++;
				if (objComponent.getParameterInputBeans() != null) {
					for (NavigatorDetail objInputBean : objComponent.getParameterInputBeans()) {
						objInputBean.setNavigatorComponentId(objComponent.getNavigatorComponentId());
						// lstNavigatorDetails.add(SerializationUtils.clone(objInputBean));
						objInputBean.setNavigatorDetailId(startNavigatorDetailSequence);
						startNavigatorDetailSequence++;

						String parameterId = objInputBean.getParameterId();
						Integer scope = objInputBean.getParameterScope();
						if (parameterId != null && parameterId.length() > 0) {
							parameterId = parameterId.substring(1, parameterId.length());
							Long id = getKeyOfParameter(parameterId, scope);
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("Input bean error in navigator");
							}
							objInputBean.setParameterId(id.toString());
						} else {
							objInputBean.setParameterId(null);
							objInputBean.setParameterScope(null);
						}

						// index of pass parameter
						if (CollectionUtils.isNotEmpty(objInputBean.getLstParameterIndex())) {
							for (BDParameterIndex index : objInputBean.getLstParameterIndex()) {
								index.setBusinessLogicId(businessDesign.getBusinessLogicId());
								index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_NAVIGATOR_DETAIL_PARAMETER);
								index.setTableId(objInputBean.getNavigatorDetailId());
								parseParameterIndex(index);
							}
						}
					}
				}
				// objComponent.setParameterInputBeans(lstNavigatorDetails);
			}
			result = navigationComponentRepository.registerNavigationComponent(lstNavigatorComponents);
		}
		return result;
	}

	private int registerCommonComponent(List<CommonComponent> lstCommonComponents, BusinessDesign businessDesign) {
		int result = 0;
		Long startSequence = 0L;
		Long startInputValueSequence = 0L;
		Long startOutputValueSequence = 0L;
		// execute common component
		if (lstCommonComponents.size() > 0) {
			Long sequenceComponent = commonCompRepository.getSequencesCommonComponent(lstCommonComponents.size() - 1);
			startSequence = sequenceComponent - (lstCommonComponents.size() - 1);

			int countInputValue = 0;
			int countOutputValue = 0;
			for (CommonComponent objComponent : lstCommonComponents) {
				if (objComponent.getParameterInputBeans() != null) {
					countInputValue += objComponent.getParameterInputBeans().size();
				}
				if (objComponent.getParameterOutputBeans() != null) {
					countOutputValue += objComponent.getParameterOutputBeans().size();
				}
			}
			if (countInputValue > 0) {
				Long inputValueSequence = commonCompRepository.getSequencesCommonInputValue(countInputValue - 1);
				startInputValueSequence = inputValueSequence - (countInputValue - 1);
			}
			if (countOutputValue > 0) {
				Long outputValueSequence = commonCompRepository.getSequencesCommonOutputValue(countOutputValue - 1);
				startOutputValueSequence = outputValueSequence - (countOutputValue - 1);
			}

			for (CommonComponent objComponent : lstCommonComponents) {
				objComponent.setCommonComponentId(startSequence);
				startSequence++;
				if (objComponent.getParameterInputBeans() != null) {
					for (CommonInputValue objInputBean : objComponent.getParameterInputBeans()) {
						objInputBean.setCommonComponentId(objComponent.getCommonComponentId());
						objInputBean.setCommonInputValueId(startInputValueSequence);
						startInputValueSequence++;

						String parameterId = objInputBean.getParameterId();
						Integer scope = objInputBean.getParameterScope();
						if (parameterId != null && parameterId.length() > 0) {
							parameterId = parameterId.substring(1, parameterId.length());
							Long id = getKeyOfParameter(parameterId, scope);
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("Input bean error in common");
							}
							objInputBean.setParameterId(id.toString());
						} else {
							objInputBean.setParameterId(null);
							objInputBean.setParameterScope(null);
						}

						// index of pass parameter
						if (CollectionUtils.isNotEmpty(objInputBean.getLstParameterIndex())) {
							for (BDParameterIndex index : objInputBean.getLstParameterIndex()) {
								index.setBusinessLogicId(businessDesign.getBusinessLogicId());
								index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_COMMON_INPUT_VALUE);
								index.setTableId(objInputBean.getCommonInputValueId());
								parseParameterIndex(index);
							}
						}
					}
				}
				if (objComponent.getParameterOutputBeans() != null) {
					for (CommonOutputValue objOutputValue : objComponent.getParameterOutputBeans()) {
						objOutputValue.setCommonComponentId(objComponent.getCommonComponentId());
						objOutputValue.setCommonOutputValueId(startOutputValueSequence);
						startOutputValueSequence++;

						String parameterId = objOutputValue.getTargetId();
						Integer scope = objOutputValue.getTargetScope();
						if (parameterId != null && parameterId.length() > 0) {
							parameterId = parameterId.substring(1, parameterId.length());
							Long id = getKeyOfParameter(parameterId, scope);
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("Output bean error in common");
							}
							objOutputValue.setTargetId(id.toString());
						} else {
							objOutputValue.setTargetId(null);
							objOutputValue.setTargetScope(null);
						}

						// index of assign
						if (CollectionUtils.isNotEmpty(objOutputValue.getLstTargetIndex())) {
							for (BDParameterIndex index : objOutputValue.getLstTargetIndex()) {
								index.setBusinessLogicId(businessDesign.getBusinessLogicId());
								index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_COMMON_OUTPUT_VALUE);
								index.setTableId(objOutputValue.getCommonOutputValueId());
								parseParameterIndex(index);
							}
						}
					}
				}
			}
			result = commonCompRepository.registerCommonComponent(lstCommonComponents);
			if (result <= 0) {
				throw new BusinessException("Fail");
			}
		}
		return result;
	}

	private int registerDecisionComponent(List<DecisionComponent> lstDecisionComponents, BusinessDesign businessDesign) {
		int result = 0;
		Long startSequence = 0L;
		Long startInputValueSequence = 0L;
		Long startOutputValueSequence = 0L;
		// execute decision component
		if (lstDecisionComponents.size() > 0) {
			Long sequenceComponent = decisionCompRepository.getSequencesDecisionComponent(lstDecisionComponents.size() - 1);
			startSequence = sequenceComponent - (lstDecisionComponents.size() - 1);

			int countInputValue = 0;
			int countOutputValue = 0;
			for (DecisionComponent objComponent : lstDecisionComponents) {
				if (objComponent.getParameterInputBeans() != null) {
					List<DecisionInputValue> lstNotInsertInput = new ArrayList<DecisionInputValue>();
					for (DecisionInputValue objInputBean : objComponent.getParameterInputBeans()) {
						if(objInputBean.getDecisionInputBeanId() == null){
							lstNotInsertInput.add(objInputBean);
						}
					}
					objComponent.getParameterInputBeans().removeAll(lstNotInsertInput);
					countInputValue += objComponent.getParameterInputBeans().size();
				}
				if (objComponent.getParameterOutputBeans() != null) {
					List<DecisionOutputValue> lstNotInsertOutput = new ArrayList<DecisionOutputValue>();
					for (DecisionOutputValue objOutputBean : objComponent.getParameterOutputBeans()) {
						if(objOutputBean.getDecisionOutputBeanId() == null){
							lstNotInsertOutput.add(objOutputBean);
						}
					}
					objComponent.getParameterOutputBeans().removeAll(lstNotInsertOutput);
					countOutputValue += objComponent.getParameterOutputBeans().size();
				}
			}
			if (countInputValue > 0) {
				Long inputValueSequence = decisionCompRepository.getSequencesDecisionInputValue(countInputValue - 1);
				startInputValueSequence = inputValueSequence - (countInputValue - 1);
			}
			if (countOutputValue > 0) {
				Long outputValueSequence = decisionCompRepository.getSequencesDecisionOutputValue(countOutputValue - 1);
				startOutputValueSequence = outputValueSequence - (countOutputValue - 1);
			}

			for (DecisionComponent objComponent : lstDecisionComponents) {
				objComponent.setDecisionComponentId(startSequence);
				startSequence++;
				if (objComponent.getParameterInputBeans() != null) {
					for (DecisionInputValue objInputBean : objComponent.getParameterInputBeans()) {
						objInputBean.setDecisionComponentId(objComponent.getDecisionComponentId());
						objInputBean.setDecisionInputValueId(startInputValueSequence);
						startInputValueSequence++;

						String parameterId = objInputBean.getParameterId();
						Integer scope = objInputBean.getParameterScope();
						if (parameterId != null && parameterId.length() > 0) {
							parameterId = parameterId.substring(1, parameterId.length());
							Long id = getKeyOfParameter(parameterId, scope);
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("Input bean error in common");
							}
							objInputBean.setParameterId(id.toString());
						} else {
							objInputBean.setParameterId(null);
							objInputBean.setParameterScope(null);
						}
						// index of pass parameter
						if (CollectionUtils.isNotEmpty(objInputBean.getLstParameterIndex())) {
							for (BDParameterIndex index : objInputBean.getLstParameterIndex()) {
								index.setBusinessLogicId(businessDesign.getBusinessLogicId());
								index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_DECISION_INPUT_VALUE);
								index.setTableId(objInputBean.getDecisionInputValueId());
								parseParameterIndex(index);
							}
						}
					}
				}
				if (objComponent.getParameterOutputBeans() != null) {
					for (DecisionOutputValue objOutputValue : objComponent.getParameterOutputBeans()) {
						objOutputValue.setDecisionComponentId(objComponent.getDecisionComponentId());
						objOutputValue.setDecisionOutputValueId(startOutputValueSequence);
						startOutputValueSequence++;

						String parameterId = objOutputValue.getTargetId();
						Integer scope = objOutputValue.getTargetScope();
						if (parameterId != null && parameterId.length() > 0) {
							parameterId = parameterId.substring(1, parameterId.length());
							Long id = getKeyOfParameter(parameterId, scope);
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("Output bean error in common");
							}
							objOutputValue.setTargetId(id.toString());
						} else {
							objOutputValue.setTargetId(null);
							objOutputValue.setTargetScope(null);
						}

						// index of assign
						if (CollectionUtils.isNotEmpty(objOutputValue.getLstTargetIndex())) {
							for (BDParameterIndex index : objOutputValue.getLstTargetIndex()) {
								index.setBusinessLogicId(businessDesign.getBusinessLogicId());
								index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_DECISION_OUTPUT_VALUE);
								index.setTableId(objOutputValue.getDecisionOutputValueId());
								parseParameterIndex(index);
							}
						}
					}
				}
			}
			result = decisionCompRepository.registerDecisionComponent(lstDecisionComponents);
		}
		return result;
	}

	private int registerLoopComponent(List<LoopComponent> lstLoopComponents, Long businessLogicId) {
		int result = 0;
		Long startSequence = 0L;
		Long sequenceComponent = 0L;
		// execute loop component
		if (lstLoopComponents.size() > 0) {
			sequenceComponent = loopCompRepository.getSequencesLoopComponent(lstLoopComponents.size() - 1);
			startSequence = sequenceComponent - (lstLoopComponents.size() - 1);
			for (LoopComponent objComponent : lstLoopComponents) {
				String parameterId = objComponent.getParameterId();
				Integer scope = objComponent.getParameterScope();
				objComponent.setLoopComponentId(startSequence);
				startSequence++;
				if (parameterId != null && parameterId.length() > 1) {
					parameterId = parameterId.substring(1, parameterId.length());
					Long id = getKeyOfParameter(parameterId, scope);
					if (id.compareTo(0L) <= 0) {
						throw new BusinessException("Parameter error in loop");
					}
					objComponent.setParameterId(id.toString());
				} else {
					objComponent.setParameterId(null);
					objComponent.setParameterScope(null);
				}
				// from
				parseFrom(objComponent);
				if (CollectionUtils.isNotEmpty(objComponent.getLstFromIndex())) {
					for (BDParameterIndex from : objComponent.getLstFromIndex()) {
						from.setBusinessLogicId(businessLogicId);
						from.setTableType(BusinessDesignConst.ParameterIndex.TABLE_LOOP_FROM);
						from.setTableId(objComponent.getLoopComponentId());
						parseParameterIndex(from);
					}
				}
				// to
				parseTo(objComponent);
				if (CollectionUtils.isNotEmpty(objComponent.getLstToIndex())) {
					for (BDParameterIndex to : objComponent.getLstToIndex()) {
						to.setBusinessLogicId(businessLogicId);
						to.setTableType(BusinessDesignConst.ParameterIndex.TABLE_LOOP_TO);
						to.setTableId(objComponent.getLoopComponentId());
						parseParameterIndex(to);
					}
				}
			}

			result = loopCompRepository.registerLoopComponent(lstLoopComponents);
		}
		return result;
	}

	private int registerAssignComponent(List<AssignComponent> lstAssignComponents, BusinessDesign businessDesign) {
		int result = 0;
		Long startSequence = 0L;
		// execute assign component
		if (lstAssignComponents.size() > 0) {
			List<AssignDetail> lstDetails = new ArrayList<AssignDetail>();
			Long sequenceComponent = assignComponentRepository.getSequencesAssignComponent(lstAssignComponents.size() - 1);
			startSequence = sequenceComponent - (lstAssignComponents.size() - 1);
			for (AssignComponent objComponent : lstAssignComponents) {
				objComponent.setAssignComponentId(startSequence);
				startSequence++;
				for (AssignDetail objDetail : objComponent.getDetails()) {
					String targetId = objDetail.getTargetId();
					Integer scope = objDetail.getTargetScope();
					if (targetId != null && targetId.length() > 0) {
						targetId = targetId.substring(1, targetId.length());
						Long id = getKeyOfParameter(targetId, scope);
						if (id.compareTo(0L) <= 0) {
							throw new BusinessException("Target error in assign : " + targetId);
						}
						objDetail.setTargetId(id.toString());
					} else {
						objDetail.setTargetId(null);
						objDetail.setTargetScope(null);
					}

					if (BusinessDesignConst.AssignDetailComponent.ASSIGN_TYPE_PARAMETER.equals(objDetail.getAssignType())) {
						String parameterId = objDetail.getParameterId();
						Integer parameterScope = objDetail.getParameterScope();
						if (parameterId != null && parameterId.length() > 0) {
							parameterId = parameterId.substring(1, parameterId.length());
							Long id = getKeyOfParameter(parameterId, parameterScope);
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("Parameter error in assign");
							}
							objDetail.setParameterId(id.toString());
						} else {
							objDetail.setParameterId(null);
							objDetail.setParameterScope(null);
						}
					} else if (BusinessDesignConst.AssignDetailComponent.ASSIGN_TYPE_FORMULA.equals(objDetail.getAssignType())) {
						objDetail.setParameterId(null);
						objDetail.setParameterScope(null);
					}

					objDetail.setAssignComponentId(objComponent.getAssignComponentId());
					lstDetails.add(SerializationUtils.clone(objDetail));
				}
			}
			result = assignComponentRepository.registerAssignComponent(lstAssignComponents);
			if (lstDetails.size() > 0) {
				Long sequenceDetail = assignComponentRepository.getSequencesAssignDetail(lstDetails.size() - 1);
				startSequence = sequenceDetail - (lstDetails.size() - 1);
				for (AssignDetail assignDetail : lstDetails) {
					assignDetail.setAssignDetailId(startSequence);
					startSequence++;

					// index of target
					if (CollectionUtils.isNotEmpty(assignDetail.getLstTargetIndex())) {
						for (BDParameterIndex index : assignDetail.getLstTargetIndex()) {
							index.setBusinessLogicId(businessDesign.getBusinessLogicId());
							index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_ASSIGN_DETAIL_TARGET);
							index.setTableId(assignDetail.getAssignDetailId());
							parseParameterIndex(index);

						}
					}

					// index of parameter
					if (CollectionUtils.isNotEmpty(assignDetail.getLstParameterIndex())) {
						for (BDParameterIndex index : assignDetail.getLstParameterIndex()) {
							index.setBusinessLogicId(businessDesign.getBusinessLogicId());
							index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_ASSIGN_DETAIL_PARAMETER);
							index.setTableId(assignDetail.getAssignDetailId());
							parseParameterIndex(index);
						}
					}
				}
				result = assignComponentRepository.registerAssignDetails(lstDetails);
			}
		}
		return result;
	}

	private int registerValidationCheckDetail(BusinessDesign businessDesign) {
		int result = 0;
		Long startSequence = 0L;
		// validation check
		List<ValidationCheckDetail> lstValidationChecks = new ArrayList<ValidationCheckDetail>();
		for (InputBean inputBean : businessDesign.getLstInputBean()) {
			if (inputBean.getJsonValidationInputs() != null) {
				List<ValidationCheckDetail> lstTemp = BusinessDesignHelper.parseValidationCheckDetail(inputBean.getJsonValidationInputs());
//				Long id = getKeyOfParameter(inputBean.getInputBeanId(), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
//				inputBean.setInputBeanId(id.toString());
				for (ValidationCheckDetail objDetail : lstTemp) {
					objDetail.setInputBeanId(inputBean.getInputBeanId());
				}
				lstValidationChecks.addAll(lstTemp);
			}
		}

		if (lstValidationChecks.size() > 0) {
			List<MessageParameter> lstMessageParameters = new ArrayList<MessageParameter>();
			int size = lstValidationChecks.size() - 1;
			Long sequenceComponent = validationCheckDetailRepository.getSequencesValidationCheckDetail(size);
			startSequence = sequenceComponent - (lstValidationChecks.size() - 1);

			int totalMessage = 0;

			for (ValidationCheckDetail objDetail : lstValidationChecks) {
				if (CollectionUtils.isNotEmpty(objDetail.getParameters())) {
					totalMessage += objDetail.getParameters().size();
				}
			}

			size = totalMessage - 1;
			Long sequenceParameter = messageParameterRepository.getSequencesMessageParameter(size);
			Long startMessageParameterSequence = sequenceParameter - (size);

			for (ValidationCheckDetail objDetail : lstValidationChecks) {
				objDetail.setValidationCheckDetailId(startSequence);
				startSequence++;

				if (CollectionUtils.isNotEmpty(objDetail.getParameters())) {
					for (MessageParameter objMessage : objDetail.getParameters()) {
						objMessage.setMessageParameterId(startMessageParameterSequence);
						startMessageParameterSequence++;

						objMessage.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
						objMessage.setTargetId(objDetail.getValidationCheckDetailId());
						if (Integer.valueOf(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE).equals(objMessage.getParameterType())) {
							objMessage.setParameterValue(null);
						} else if (Integer.valueOf(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE).equals(objMessage.getParameterType())) {
							objMessage.setParameterValue(objMessage.getParameterCode());
							objMessage.setParameterCode(null);
						}
						objMessage.setBusinessLogicId(businessDesign.getBusinessLogicId());
						lstMessageParameters.add(objMessage);
					}
				}
			}
			result = validationCheckDetailRepository.registerValidationCheckDetails(lstValidationChecks);
			if (lstMessageParameters.size() > 0) {
				result = messageParameterRepository.registerMessageParameter(lstMessageParameters);
				if (result <= 0) {
					throw new BusinessException("Fail");
				}
			}
			// TRUNGDV : update screen item validation
			List<Long> lstScreenItemMapWithInputbean = new ArrayList<Long>();
			for (InputBean inputBean : businessDesign.getLstInputBean()) {
				lstScreenItemMapWithInputbean.add(inputBean.getScreenItemId());
			}
			List<ScreenItemValidation> lstScreenItemValidations = screenItemValidationRepository.getLstItemValidationByScreenItemId(lstScreenItemMapWithInputbean);
			if(FunctionCommon.isNotEmpty(lstScreenItemValidations)) {
				for(ScreenItemValidation itemValidation : lstScreenItemValidations) {
					InputBean in = new InputBean();
					for(InputBean obj : businessDesign.getLstInputBean()) {
						if(obj.getScreenItemId() != null && obj.getScreenItemId().equals(itemValidation.getScreenItemId())) {
							in = obj;
							break;
						}
					}
					for (ValidationCheckDetail objDetail : lstValidationChecks) {
						if (objDetail.getInputBeanId() != null && objDetail.getInputBeanId().equals(in.getInputBeanId())) {
							// mandatory
							if(BusinessDesignConst.ValidateType.NOT_NULL == objDetail.getValidationType() ||
									BusinessDesignConst.ValidateType.NOT_EMPTY == objDetail.getValidationType()) {
								itemValidation.setMandatoryFlg(1);
								itemValidation.setHaveMandatory(true);
							}
							// maxlength
							if(BusinessDesignConst.ValidateType.SIZE == objDetail.getValidationType() ||
									BusinessDesignConst.ValidateType.QP_SIZE == objDetail.getValidationType()) {
								if (CollectionUtils.isNotEmpty(objDetail.getParameters())) {
									MessageParameter mess = new MessageParameter();
									for (MessageParameter objMessage : objDetail.getParameters()) {
										if (objMessage.getTargetId().equals(objDetail.getValidationCheckDetailId()) && BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE.equals(objMessage.getParameterType())) {
											if (2 == objMessage.getItemSequenceNo()) {
												mess = objMessage;
												break;
											} else {
												mess = objMessage;
											}
										}
									}
									Integer maxlength = mess.getParameterValue() == null ? null : Integer.parseInt(mess.getParameterValue());
									itemValidation.setMaxlength(maxlength);
									itemValidation.setHaveMaxLength(true);
								}
							}
							// min
							if(BusinessDesignConst.ValidateType.MIN == objDetail.getValidationType() ||
									BusinessDesignConst.ValidateType.DECIMAL_MIN == objDetail.getValidationType() ||
									BusinessDesignConst.ValidateType.TIME_MIN == objDetail.getValidationType() ||
									BusinessDesignConst.ValidateType.DATE_MIN == objDetail.getValidationType() ||
									BusinessDesignConst.ValidateType.DATE_TIME_MIN == objDetail.getValidationType()) {
								if (CollectionUtils.isNotEmpty(objDetail.getParameters())) {
									MessageParameter mess = new MessageParameter();
									for (MessageParameter objMessage : objDetail.getParameters()) {
										if (objMessage.getTargetId().equals(objDetail.getValidationCheckDetailId()) && BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE.equals(objMessage.getParameterType())) {
											mess = objMessage;
											break;
										}
									}
									itemValidation.setMinVal(mess.getParameterValue());
									//TODO patten format
									//itemValidation.setPatternFormat(...);
									itemValidation.setHaveMinVal(true);
								}
							}
							// max
							if(BusinessDesignConst.ValidateType.MAX == objDetail.getValidationType() ||
									BusinessDesignConst.ValidateType.DECIMAL_MAX == objDetail.getValidationType() ||
									BusinessDesignConst.ValidateType.TIME_MAX == objDetail.getValidationType() ||
									BusinessDesignConst.ValidateType.DATE_MAX == objDetail.getValidationType() ||
									BusinessDesignConst.ValidateType.DATE_TIME_MAX == objDetail.getValidationType()) {
								if (CollectionUtils.isNotEmpty(objDetail.getParameters())) {
									MessageParameter mess = new MessageParameter();
									for (MessageParameter objMessage : objDetail.getParameters()) {
										if (objMessage.getTargetId().equals(objDetail.getValidationCheckDetailId()) && BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE.equals(objMessage.getParameterType())) {
											mess = objMessage;
											break;
										}
									}
									itemValidation.setMaxVal(mess.getParameterValue());
									//TODO patten format
									//itemValidation.setPatternFormat(...);
									itemValidation.setHaveMaxVal(true);
								}
							}
						}
					}
				}
				for (ScreenItemValidation itemValidation : lstScreenItemValidations) {
					if (itemValidation.getHaveMandatory() == null) {
						itemValidation.setMandatoryFlg(0);
					}
					if (itemValidation.getHaveMaxLength() == null) {
						itemValidation.setMaxlength(null);
					}
					if (itemValidation.getHaveMinVal() == null) {
						itemValidation.setMinVal(null);
					}
					if (itemValidation.getHaveMaxVal() == null) {
						itemValidation.setMaxVal(null);
					}
				}
				// update screen item validation
				if (screenItemValidationRepository.modifyScreenItemValidation(lstScreenItemValidations) <= 0) {
					throw new BusinessException("Fail");
				}
				// END by TRUNGDV : update screen item validation
			}
		}
		return result;
	}

	private int registerBusinessCheckComponent(List<BusinessCheckComponent> lstBusinessChecks, Long businessLogicId, Long projectId) {
		int result = 0;
		Long startSequence = 0L;
		Long startDetailContentSequence = 0L;
		Long startMessageParameterSequence = 0L;
		// business check component
		if (lstBusinessChecks.size() > 0) {
			List<BusinessCheckDetail> lstBusinessCheckDetails = new ArrayList<BusinessCheckDetail>();
			List<BusinessDetailContent> lstBusinessDetailContents = new ArrayList<BusinessDetailContent>();
			List<MessageParameter> lstMessageParameters = new ArrayList<MessageParameter>();
			int size = lstBusinessChecks.size() - 1;
			Long sequenceComponent = businessCheckCompRepository.getSequencesBusinessCheckComponent(size);
			startSequence = sequenceComponent - (lstBusinessChecks.size() - 1);
			for (BusinessCheckComponent objComponent : lstBusinessChecks) {
				objComponent.setBusinessCheckComponentId(startSequence);
				startSequence++;

				for (BusinessCheckDetail objDetail : objComponent.getBusinessCheckDetails()) {
					objDetail.setBusinessCheckComponentId(objComponent.getBusinessCheckComponentId());
					lstBusinessCheckDetails.add(objDetail);
				}

			}

			result = businessCheckCompRepository.registerBusinessCheckComponent(lstBusinessChecks);
			if (result <= 0) {
				throw new BusinessException("Fail");
			}
			// map sequence
			Long sequenceDetail = businessCheckCompRepository.getSequencesBusinessCheckDetail(lstBusinessCheckDetails.size() - 1);
			startSequence = sequenceDetail - (lstBusinessCheckDetails.size() - 1);
			for (BusinessCheckDetail objDetail : lstBusinessCheckDetails) {
				objDetail.setBusinessCheckDetailId(startSequence);
				startSequence++;

				// message parameter
				if (CollectionUtils.isNotEmpty(objDetail.getParameters())) {
					if (CollectionUtils.isNotEmpty(objDetail.getParameters())) {
						List<MessageParameter> lstTempParameters = initialMessageParameters(objDetail.getParameters(), BusinessDesignConst.MessageParameter.TARGET_TYPE_BUSINESSCHECK, objDetail.getBusinessCheckDetailId(), businessLogicId);
						lstMessageParameters.addAll(lstTempParameters);
					}
				}
				if (CollectionUtils.isNotEmpty(objDetail.getContents())) {
					for (BusinessDetailContent objContent : objDetail.getContents()) {
						objContent.setBusinessCheckDetailId(objDetail.getBusinessCheckDetailId());
						String parameterId = objContent.getParameterId();
						Integer parameterScope = objContent.getParameterScope();
						if (parameterId != null && parameterId.length() > 0) {
							parameterId = parameterId.substring(1, parameterId.length());
							Long id = getKeyOfParameter(parameterId, parameterScope);
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("parameter error in business check");
							}
							objContent.setParameterId(id.toString());
						} else {
							objContent.setParameterId(null);
							objContent.setParameterScope(null);
						}
						lstBusinessDetailContents.add(objContent);
					}
				}
			}
			if (lstBusinessCheckDetails.size() > 0) {
				result = businessCheckCompRepository.registerBusinessCheckDetails(lstBusinessCheckDetails);
			}
			if (lstBusinessDetailContents.size() > 0) {
				size = lstBusinessDetailContents.size() - 1;
				Long sequenceDetailContent = businessCheckCompRepository.getSequencesBusinessDetailContent(size);
				startDetailContentSequence = sequenceDetailContent - size;
				for (BusinessDetailContent objContent : lstBusinessDetailContents) {
					objContent.setBusinessDetailContentId(startDetailContentSequence);
					startDetailContentSequence++;
					// index of pass parameter
					if (CollectionUtils.isNotEmpty(objContent.getLstParameterIndex())) {
						for (BDParameterIndex index : objContent.getLstParameterIndex()) {
							index.setBusinessLogicId(businessLogicId);
							index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_BUSINESS_CHECK_CONTENT);
							index.setTableId(objContent.getBusinessDetailContentId());
							parseParameterIndex(index);
						}
					}
				}
				result = businessCheckCompRepository.registerBusinessDetailContents(lstBusinessDetailContents);
			}
			if (lstMessageParameters.size() > 0) {
				size = lstMessageParameters.size() - 1;
				Long sequenceParameter = messageParameterRepository.getSequencesMessageParameter(size);
				startMessageParameterSequence = sequenceParameter - (lstMessageParameters.size() - 1);

				for (MessageParameter objContent : lstMessageParameters) {
					objContent.setMessageParameterId(startMessageParameterSequence);
					startMessageParameterSequence++;
					// index of pass parameter
					if (CollectionUtils.isNotEmpty(objContent.getLstParameterIndex())) {
						for (BDParameterIndex index : objContent.getLstParameterIndex()) {
							index.setTableId(objContent.getMessageParameterId());
						}
					}
				}

				result = messageParameterRepository.registerMessageParameter(lstMessageParameters);
			}
		}
		return result;
	}

	private int registerExecutionComponent(List<ExecutionComponent> lstExecutionComponents, BusinessDesign businessDesign) {
		int result = 0;
		Long startSequence = 0L;
		Long startInputValueSequence = 0L;
		Long startOutputValueSequence = 0L;

		// execute execution component
		if (lstExecutionComponents.size() > 0) {
			Long sequenceComponent = executionCompRepository.getSequencesExecutionComponent(lstExecutionComponents.size() - 1);
			startSequence = sequenceComponent - (lstExecutionComponents.size() - 1);

			int countInputValue = 0;
			int countOutputValue = 0;
			for (ExecutionComponent objComponent : lstExecutionComponents) {
				if (objComponent.getParameterInputBeans() != null) {
					countInputValue += objComponent.getParameterInputBeans().size();
				}
				if (objComponent.getParameterOutputBeans() != null) {
					countOutputValue += objComponent.getParameterOutputBeans().size();
				}
			}
			if (countInputValue > 0) {
				Long inputValueSequence = executionCompRepository.getSequencesExecutionInputValue(countInputValue - 1);
				startInputValueSequence = inputValueSequence - (countInputValue - 1);
			}
			if (countOutputValue > 0) {
				Long outputValueSequence = executionCompRepository.getSequencesExecutionOutputValue(countOutputValue - 1);
				startOutputValueSequence = outputValueSequence - (countOutputValue - 1);
			}

			for (ExecutionComponent objComponent : lstExecutionComponents) {
				objComponent.setExecutionComponentId(startSequence);
				startSequence++;
				if (objComponent.getParameterInputBeans() != null) {
					for (ExecutionInputValue objInputBean : objComponent.getParameterInputBeans()) {
						//impact change
						if(objInputBean.getSqlDesignInputId() == null){
							objInputBean.setSqlDesignInputId(objInputBean.getSqlDesignInputIdRefer());
							objInputBean.setSqlDesignInputCode(objInputBean.getSqlDesignInputCodeRefer());
							objInputBean.setSqlDesignInputName(objInputBean.getSqlDesignInputNameRefer());
							objInputBean.setDataType(objInputBean.getDataTypeRefer());
							objInputBean.setArrayFlg(objInputBean.getArrayFlgRefer());
						}
						objInputBean.setExecutionComponentId(objComponent.getExecutionComponentId());
						objInputBean.setExecutionInputValueId(startInputValueSequence);
						startInputValueSequence++;

						String parameterId = objInputBean.getParameterId();
						Integer scope = objInputBean.getParameterScope();
						if (parameterId != null && parameterId.length() > 0) {
							parameterId = parameterId.substring(1, parameterId.length());
							Long id = getKeyOfParameter(parameterId, scope);
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("Input bean error in execution");
							}
							objInputBean.setParameterId(id.toString());
						} else {
							objInputBean.setParameterId(null);
							objInputBean.setParameterScope(null);
						}

						// index of pass parameter
						if (CollectionUtils.isNotEmpty(objInputBean.getLstParameterIndex())) {
							for (BDParameterIndex index : objInputBean.getLstParameterIndex()) {
								index.setBusinessLogicId(businessDesign.getBusinessLogicId());
								index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_EXECUTION_INPUT_VALUE);
								index.setTableId(objInputBean.getExecutionInputValueId());
								parseParameterIndex(index);
							}
						}
					}
				}
				if (objComponent.getParameterOutputBeans() != null) {
					for (ExecutionOutputValue objOutputValue : objComponent.getParameterOutputBeans()) {
						if(objOutputValue.getSqlDesignOutputId() == null){
							objOutputValue.setSqlDesignOutputId(objOutputValue.getSqlDesignOutputIdRefer());
							objOutputValue.setSqlDesignOutputCode(objOutputValue.getSqlDesignOutputCodeRefer());
							objOutputValue.setSqlDesignOutputName(objOutputValue.getSqlDesignOutputNameRefer());
							objOutputValue.setDataType(objOutputValue.getDataTypeRefer());
							objOutputValue.setArrayFlg(objOutputValue.getArrayFlgRefer());
						}
						objOutputValue.setExecutionComponentId(objComponent.getExecutionComponentId());
						objOutputValue.setExecutionOutputValueId(startOutputValueSequence);
						startOutputValueSequence++;

						String parameterId = objOutputValue.getTargetId();
						Integer scope = objOutputValue.getTargetScope();
						if (parameterId != null && parameterId.length() > 0) {
							parameterId = parameterId.substring(1, parameterId.length());
							Long id = getKeyOfParameter(parameterId, scope);
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("Output bean error in execution");
							}
							objOutputValue.setTargetId(id.toString());
						} else {
							objOutputValue.setTargetId(null);
							objOutputValue.setTargetScope(null);
						}

						// index of assign
						if (CollectionUtils.isNotEmpty(objOutputValue.getLstTargetIndex())) {
							for (BDParameterIndex index : objOutputValue.getLstTargetIndex()) {
								index.setBusinessLogicId(businessDesign.getBusinessLogicId());
								index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_EXECUTION_OUTPUT_VALUE);
								index.setTableId(objOutputValue.getExecutionOutputValueId());
								parseParameterIndex(index);
							}
						}
					}
				}
			}
			result = executionCompRepository.registerExecutionComponent(lstExecutionComponents);
			if (result <= 0) {
				throw new BusinessException("Fail");
			}
		}
		return result;
	}

	private int registerComponent(List<SequenceLogic> lstSequenceLogic, BusinessDesign businessDesign, CommonModel commonModel) throws BusinessException {
		Long businessLogicId = businessDesign.getBusinessLogicId();
		Long projectId = businessDesign.getProjectId();
		// insert component detail
		lstFormulaDetails = new ArrayList<FormulaDetail>();
		int resultFeedbackComponent = 0;
		List<FeedbackComponent> lstFeedbackComponents = new ArrayList<FeedbackComponent>();
		List<NavigatorComponent> lstNavigatorComponents = new ArrayList<NavigatorComponent>();
		List<IfComponent> lstIfComponents = new ArrayList<IfComponent>();
		List<CommonComponent> lstCommonComponent = new ArrayList<CommonComponent>();
		List<LoopComponent> lstLoopComponents = new ArrayList<LoopComponent>();
		List<DecisionComponent> lstDecisionComponents = new ArrayList<DecisionComponent>();
		List<AssignComponent> lstAsssignComponents = new ArrayList<AssignComponent>();
		List<BusinessCheckComponent> lstBusinessCheckComponents = new ArrayList<BusinessCheckComponent>();
		List<ExecutionComponent> lstExecutionComponents = new ArrayList<ExecutionComponent>();
		List<NestedLogicComponent> lstNestedLogicComponents = new ArrayList<NestedLogicComponent>();
		List<AdvanceComponent> lstAdvanceComponents = new ArrayList<AdvanceComponent>();
		List<FileOperationComponent> lstFileOperationComponents = new ArrayList<FileOperationComponent>();
		List<ImportFileComponent> lstImportFileComponents = new ArrayList<ImportFileComponent>();
		List<ExportFileComponent> lstExportFileComponents = new ArrayList<ExportFileComponent>();
		List<TransactionComponent> lstTransactionComponents = new ArrayList<TransactionComponent>();
		List<LogComponent> lstLogComponents = new ArrayList<LogComponent>();
		List<UtilityComponent> lstUtilityComponents = new ArrayList<UtilityComponent>();
		List<EmailComponent> lstEmailComponents = new ArrayList<EmailComponent>();
		List<DownloadFileComponent> lstDownloadFileComponents = new ArrayList<DownloadFileComponent>();
		List<ExceptionComponent> lstExceptionComponents = new ArrayList<ExceptionComponent>();

		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			switch (objSequenceLogic.getComponentType()) {
				case BusinessDesignConst.COMPONENT_IF:
					IfComponent objIf;
					objIf = DataTypeUtils.toObject(objSequenceLogic.getStrData(), IfComponent.class);

					if (objIf != null) {
						objIf.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstIfComponents.add(objIf);
					}
					break;

				case BusinessDesignConst.COMPONENT_FEEDBACK:
					FeedbackComponent objFeedback;
					objFeedback = DataTypeUtils.toObject(objSequenceLogic.getStrData(), FeedbackComponent.class);

					if (objFeedback != null) {
						objFeedback.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstFeedbackComponents.add(objFeedback);
					}
					break;
				case BusinessDesignConst.COMPONENT_NAVIGATOR:
					NavigatorComponent objNavigator;
					objNavigator = DataTypeUtils.toObject(objSequenceLogic.getStrData(), NavigatorComponent.class);

					if (objNavigator != null) {
						objNavigator.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstNavigatorComponents.add(objNavigator);
					}
					break;
				case BusinessDesignConst.COMPONENT_COMMON:
					CommonComponent objCommon;
					objCommon = DataTypeUtils.toObject(objSequenceLogic.getStrData(), CommonComponent.class);

					if (objCommon != null) {
						objCommon.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstCommonComponent.add(objCommon);
					}
					break;
				case BusinessDesignConst.COMPONENT_LOOP:
					LoopComponent objLoop;
					objLoop = DataTypeUtils.toObject(objSequenceLogic.getStrData(), LoopComponent.class);

					if (objLoop != null) {
						objLoop.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstLoopComponents.add(objLoop);
					}
					break;
				case BusinessDesignConst.COMPONENT_DECISION:
					DecisionComponent objDecision;
					objDecision = DataTypeUtils.toObject(objSequenceLogic.getStrData(), DecisionComponent.class);

					if (objDecision != null) {
						objDecision.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstDecisionComponents.add(objDecision);
					}
					break;
				case BusinessDesignConst.COMPONENT_ASSIGN:
					AssignComponent objAssign;
					objAssign = DataTypeUtils.toObject(objSequenceLogic.getStrData(), AssignComponent.class);

					if (objAssign != null) {
						objAssign.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstAsssignComponents.add(objAssign);
					}
					break;
				case BusinessDesignConst.COMPONENT_BUSINESSCHECK:
					BusinessCheckComponent objBusinessCheck;
					objBusinessCheck = DataTypeUtils.toObject(objSequenceLogic.getStrData(), BusinessCheckComponent.class);

					if (objBusinessCheck != null) {
						objBusinessCheck.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstBusinessCheckComponents.add(objBusinessCheck);
					}
					break;
				case BusinessDesignConst.COMPONENT_EXECUTION:
					ExecutionComponent objComponent;
					objComponent = DataTypeUtils.toObject(objSequenceLogic.getStrData(), ExecutionComponent.class);

					if (objComponent != null) {
						objComponent.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstExecutionComponents.add(objComponent);
					}
					break;
				case BusinessDesignConst.COMPONENT_NESTEDLOGIC:
					NestedLogicComponent objNestedLogic;
					objNestedLogic = DataTypeUtils.toObject(objSequenceLogic.getStrData(), NestedLogicComponent.class);

					if (objNestedLogic != null) {
						objNestedLogic.setSequenceLogicId(objSequenceLogic.getSequenceLogicId());
						lstNestedLogicComponents.add(objNestedLogic);
					}
					break;
				case BusinessDesignConst.COMPONENT_ADVANCE:
					AdvanceComponent objAdvance;
					objAdvance = DataTypeUtils.toObject(objSequenceLogic.getStrData(), AdvanceComponent.class);

					if (objAdvance != null) {
						objAdvance.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstAdvanceComponents.add(objAdvance);
					}
					break;
				case BusinessDesignConst.COMPONENT_FILEOPERATION:
					FileOperationComponent objFileOperation;
					objFileOperation = DataTypeUtils.toObject(objSequenceLogic.getStrData(), FileOperationComponent.class);

					if (objFileOperation != null) {
						objFileOperation.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstFileOperationComponents.add(objFileOperation);
					}
					break;
				case BusinessDesignConst.COMPONENT_IMPORTFILE:
					ImportFileComponent objImportFile;
					objImportFile = DataTypeUtils.toObject(objSequenceLogic.getStrData(), ImportFileComponent.class);

					if (objImportFile != null) {
						objImportFile.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstImportFileComponents.add(objImportFile);
					}
					break;
				case BusinessDesignConst.COMPONENT_EXPORTFILE:
					ExportFileComponent objExportFile;
					objExportFile = DataTypeUtils.toObject(objSequenceLogic.getStrData(), ExportFileComponent.class);

					if (objExportFile != null) {
						objExportFile.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstExportFileComponents.add(objExportFile);
					}
					break;
				case BusinessDesignConst.COMPONENT_TRANSACTION:
					TransactionComponent objTransaction;
					objTransaction = DataTypeUtils.toObject(objSequenceLogic.getStrData(), TransactionComponent.class);

					if (objTransaction != null) {
						objTransaction.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstTransactionComponents.add(objTransaction);
					}
					break;
				case BusinessDesignConst.COMPONENT_LOG:
					LogComponent objLog;
					objLog = DataTypeUtils.toObject(objSequenceLogic.getStrData(), LogComponent.class);

					if (objLog != null) {
						objLog.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstLogComponents.add(objLog);
					}
					break;
				case BusinessDesignConst.COMPONENT_UTILITY:
					UtilityComponent objUtility;
					objUtility = DataTypeUtils.toObject(objSequenceLogic.getStrData(), UtilityComponent.class);

					if (objUtility != null) {
						objUtility.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstUtilityComponents.add(objUtility);
					}
					break;
				case BusinessDesignConst.COMPONENT_EMAIL:
					EmailComponent objEmail;
					objEmail = DataTypeUtils.toObject(objSequenceLogic.getStrData(), EmailComponent.class);

					if (objEmail != null) {
						objEmail.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstEmailComponents.add(objEmail);
					}
					break;
				case BusinessDesignConst.COMPONENT_DOWNLOAD_FILE:
					DownloadFileComponent objDownloadFileComponent;
					objDownloadFileComponent = DataTypeUtils.toObject(objSequenceLogic.getStrData(), DownloadFileComponent.class);

					if (objDownloadFileComponent != null) {
						objDownloadFileComponent.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstDownloadFileComponents.add(objDownloadFileComponent);
					}
					break;
				case BusinessDesignConst.COMPONENT_EXCEPTION:
					ExceptionComponent objExceptionComponent;
					objExceptionComponent = DataTypeUtils.toObject(objSequenceLogic.getStrData(), ExceptionComponent.class);

					if (objExceptionComponent != null) {
						objExceptionComponent.setSequenceLogicId(Long.valueOf(objSequenceLogic.getSequenceLogicId()));
						lstExceptionComponents.add(objExceptionComponent);
					}
					break;
				default:
					break;
			}
		}

		// detect node using formula setting and set formula ID
		detectNodeUsingFormula(projectId, businessDesign, lstIfComponents, lstLoopComponents, lstAsssignComponents, lstBusinessCheckComponents, lstFileOperationComponents, lstImportFileComponents, lstExportFileComponents, lstLogComponents, lstEmailComponents, lstDownloadFileComponents);
		detectNodeUsingCreateMessageDesign(businessDesign.getModuleId(), lstBusinessCheckComponents, lstFeedbackComponents, businessDesign.getSysDatetime(), commonModel);
		registerFeedbackComponent(lstFeedbackComponents, businessLogicId);
		registerIfComponent(lstIfComponents, projectId);
		registerNavigatorComponent(lstNavigatorComponents, businessDesign);
		registerCommonComponent(lstCommonComponent, businessDesign);
		registerLoopComponent(lstLoopComponents, businessLogicId);
		registerDecisionComponent(lstDecisionComponents, businessDesign);
		registerAssignComponent(lstAsssignComponents, businessDesign);
		registerValidationCheckDetail(businessDesign);
		registerBusinessCheckComponent(lstBusinessCheckComponents, businessLogicId, projectId);
		registerExecutionComponent(lstExecutionComponents, businessDesign);
		registerNestedLogicComponent(lstNestedLogicComponents);
		registerAdvanceComponent(lstAdvanceComponents, businessDesign);
		registerFileOperationComponent(lstFileOperationComponents);
		registerImportFileComponent(lstImportFileComponents);
		registerExportFileComponent(lstExportFileComponents);
		registerTransactionComponent(lstTransactionComponents);
		registerLogComponent(lstLogComponents);
		registerUtilityComponent(lstUtilityComponents, businessLogicId);
		registerEmailComponent(lstEmailComponents);
		registerDownloadFileComponent(lstDownloadFileComponents);
		registerExceptionComponent(lstExceptionComponents, businessDesign);
		registerFormulaDetail(lstFormulaDetails, businessDesign);
		return resultFeedbackComponent;
	}

	private int registerNestedLogicComponent(List<NestedLogicComponent> lstNestedLogicComponents) {
		int result = nestedLogicCompRepository.registerNestedLogicComponent(lstNestedLogicComponents);
		return result;
	}

	private int registerAdvanceComponent(List<AdvanceComponent> lstAdvanceComponents, BusinessDesign businessDesign) {
		int result = 0;
		Long startSequence = 0L;
		Long startInputSequence = 0L;
		Long startOutputSequence = 0L;
		Long sequenceComponent = 0L;
		Long sequenceAdvanceInput = 0L;
		Long sequenceAdvanceOutput = 0L;
		int countInput = 0;
		int countOutput = 0;

		for (AdvanceComponent objComponent : lstAdvanceComponents) {
			if (objComponent.getParameterInputBeans() != null) {
				countInput += objComponent.getParameterInputBeans().size();
			}
			if (objComponent.getParameterOutputBeans() != null) {
				countOutput += objComponent.getParameterOutputBeans().size();
			}
		}
		// execute advance component
		if (lstAdvanceComponents.size() > 0) {
			sequenceComponent = advanceCompRepository.getSequencesAdvanceComponent(lstAdvanceComponents.size() - 1);
			startSequence = sequenceComponent - (lstAdvanceComponents.size() - 1);
			if (countInput > 0) {
				sequenceAdvanceInput = advanceCompRepository.getSequencesAdvanceInputBean(countInput - 1);
				startInputSequence = sequenceAdvanceInput - (countInput - 1);
			}
			if (countOutput > 0) {
				sequenceAdvanceOutput = advanceCompRepository.getSequencesAdvanceOutputBean(countOutput - 1);
				startOutputSequence = sequenceAdvanceOutput - (countOutput - 1);
			}
			for (AdvanceComponent objComponent : lstAdvanceComponents) {
				objComponent.setAdvanceComponentId(startSequence);
				startSequence++;
				if (objComponent.getParameterInputBeans() != null) {
					for (AdvanceInputValue objInputBean : objComponent.getParameterInputBeans()) {
						// map ID and parent ID
						objInputBean.setAdvanceComponentId(objComponent.getAdvanceComponentId());
						objInputBean.setAdvanceInputValueId(startInputSequence);
						startInputSequence++;
						String parameterId = objInputBean.getParameterId();
						Integer scope = objInputBean.getParameterScope();
						if (parameterId != null && parameterId.length() > 0) {
							parameterId = parameterId.substring(1, parameterId.length());
							Long id = getKeyOfParameter(parameterId, scope);
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("Input bean error in advance");
							}
							objInputBean.setParameterId(id.toString());
						} else {
							objInputBean.setParameterId(null);
							objInputBean.setParameterScope(null);
						}

						// index of pass parameter
						if (CollectionUtils.isNotEmpty(objInputBean.getLstParameterIndex())) {
							for (BDParameterIndex index : objInputBean.getLstParameterIndex()) {
								index.setBusinessLogicId(businessDesign.getBusinessLogicId());
								index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_ADVANCE_INPUT_VALUE);
								index.setTableId(objInputBean.getAdvanceInputValueId());
								parseParameterIndex(index);
							}
						}
					}
				}
				if (objComponent.getParameterOutputBeans() != null) {
					for (AdvanceOutputValue objOutputValue : objComponent.getParameterOutputBeans()) {
						// map ID and parent ID
						objOutputValue.setAdvanceComponentId(objComponent.getAdvanceComponentId());
						objOutputValue.setAdvanceOutputValueId(startOutputSequence);
						startOutputSequence++;

						String parameterId = objOutputValue.getTargetId();
						Integer scope = objOutputValue.getTargetScope();
						if (parameterId != null && parameterId.length() > 0) {
							parameterId = parameterId.substring(1, parameterId.length());
							Long id = getKeyOfParameter(parameterId, scope);
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("Output bean error in advance");
							}
							objOutputValue.setTargetId(id.toString());
						} else {
							objOutputValue.setTargetId(null);
							objOutputValue.setTargetScope(null);
						}

						// index of assign
						if (CollectionUtils.isNotEmpty(objOutputValue.getLstTargetIndex())) {
							for (BDParameterIndex index : objOutputValue.getLstTargetIndex()) {
								index.setBusinessLogicId(businessDesign.getBusinessLogicId());
								index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_ADVANCE_OUTPUT_VALUE);
								index.setTableId(objOutputValue.getAdvanceOutputValueId());
								parseParameterIndex(index);
							}
						}
					}
				}
			}
			result = advanceCompRepository.registerAllAdvanceComponent(lstAdvanceComponents);
			if (result <= 0) {
				throw new BusinessException("Fail");
			}
		}
		return result;
	}

	private int registerFileOperationComponent(List<FileOperationComponent> lstFileOperationComponents) {
		int result = 0;
		Long startSequence = 0L;
		Long sequenceComponent = 0L;
		// execute file operation component
		if (lstFileOperationComponents.size() > 0) {
			sequenceComponent = fileOperationCompRepository.getSequencesFileOperationComponent(lstFileOperationComponents.size() - 1);
			startSequence = sequenceComponent - (lstFileOperationComponents.size() - 1);

			for (FileOperationComponent objComponent : lstFileOperationComponents) {
				objComponent.setFileOperationComponentId(startSequence);
				startSequence++;
			}
			result = fileOperationCompRepository.registerFileOperationComponent(lstFileOperationComponents);
		}
		return result;
	}

	private int registerImportFileComponent(List<ImportFileComponent> lstImportFileComponents) {
		int result = 0;
		Long startSequence = 0L;
		Long sequenceComponent = 0L;
		// execute import file component
		if (lstImportFileComponents.size() > 0) {
			sequenceComponent = importFileCompRepository.getSequencesImportFileComponent(lstImportFileComponents.size() - 1);
			startSequence = sequenceComponent - (lstImportFileComponents.size() - 1);

			for (ImportFileComponent objComponent : lstImportFileComponents) {
				objComponent.setImportFileComponentId(startSequence);
				startSequence++;

				String targetId = objComponent.getTargetId();
				Integer scope = objComponent.getTargetScope();
				if (targetId != null && targetId.length() > 0) {
					targetId = targetId.substring(1, targetId.length());
					Long id = getKeyOfParameter(targetId, scope);
					if (id.compareTo(0L) <= 0) {
						throw new BusinessException("Target error in import type : " + targetId);
					}
					objComponent.setTargetId(id.toString());
				} else {
					objComponent.setTargetId(null);
					objComponent.setTargetScope(null);
				}

				for (ImportAssignValue objDetail : objComponent.getLstImportAssignValues()) {
					String targetIdDetail = objDetail.getTargetId();
					Integer scopeDetail = objDetail.getTargetScope();
					if (targetIdDetail != null && targetId.length() > 0) {
						targetIdDetail = targetIdDetail.substring(1, targetIdDetail.length());
						Long id = getKeyOfParameter(targetIdDetail, scopeDetail);
						if (id.compareTo(0L) <= 0) {
							throw new BusinessException("Target error in import type : " + targetIdDetail);
						}
						objDetail.setTargetId(id.toString());
					} else {
						objDetail.setTargetId(null);
						objDetail.setTargetScope(null);
					}

					objDetail.setImportFileComponentId(objComponent.getImportFileComponentId());
				}
			}
			result = importFileCompRepository.registerImportFileComponent(lstImportFileComponents);
		}
		return result;
	}

	private int registerExportFileComponent(List<ExportFileComponent> lstExportFileComponents) {
		int result = 0;
		Long startSequence = 0L;
		Long sequenceComponent = 0L;
		Long startSequenceExportAssignValue = 0L;
		Long sequenceAssignValue = 0L;
		// execute export file component
		int countExportAssignValue = 0;
		for (ExportFileComponent objComponent : lstExportFileComponents) {
			countExportAssignValue += objComponent.getLstExportAssignValues().size();
		}

		if (lstExportFileComponents.size() > 0) {
			sequenceComponent = exportFileCompRepository.getSequencesExportFileComponent(lstExportFileComponents.size() - 1);
			startSequence = sequenceComponent - (lstExportFileComponents.size() - 1);

			sequenceAssignValue = exportFileCompRepository.getSequencesExportAssignValue(countExportAssignValue - 1);
			startSequenceExportAssignValue = sequenceAssignValue - (countExportAssignValue - 1);

			for (ExportFileComponent objComponent : lstExportFileComponents) {
				objComponent.setExportFileComponentId(startSequence);
				startSequence++;

				String parameterId = objComponent.getParameterId();
				Integer scope = objComponent.getParameterScope();
				if (parameterId != null && parameterId.length() > 0) {
					parameterId = parameterId.substring(1, parameterId.length());
					Long id = getKeyOfParameter(parameterId, scope);
					if (id.compareTo(0L) <= 0) {
						throw new BusinessException("Parameter error in export type : " + parameterId);
					}
					objComponent.setParameterId(id.toString());
				} else {
					objComponent.setParameterId(null);
					objComponent.setParameterScope(null);
				}

				if (objComponent.getLstExportAssignValues().size() > 0) {

					for (ExportAssignValue objDetail : objComponent.getLstExportAssignValues()) {
						objDetail.setExportAssignValueId(startSequenceExportAssignValue);
						objDetail.getColumnFileFormat().setExportAssignValueId(startSequenceExportAssignValue);
						startSequenceExportAssignValue++;

						String parameterIdDetail = objDetail.getParameterId();
						Integer scopeDetail = objDetail.getParameterScope();
						if (parameterIdDetail != null && parameterIdDetail.length() > 0) {
							parameterIdDetail = parameterIdDetail.substring(1, parameterIdDetail.length());
							Long id = getKeyOfParameter(parameterIdDetail, scopeDetail);
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("Parameter error in export type : " + parameterIdDetail);
							}
							objDetail.setParameterId(id.toString());
						} else {
							objDetail.setParameterId(null);
							objDetail.setParameterScope(null);
						}

						objDetail.setExportFileComponentId(objComponent.getExportFileComponentId());
					}
				}
			}
			result = exportFileCompRepository.registerExportFileComponent(lstExportFileComponents);
		}
		return result;
	}

	private int registerTransactionComponent(List<TransactionComponent> lstTransactionComponents) {
		int result = 0;
		Long startSequence = 0L;
		Long sequenceComponent = 0L;
		// execute transaction component
		if (lstTransactionComponents.size() > 0) {
			sequenceComponent = transactionCompRepository.getSequencesTransactionComponent(lstTransactionComponents.size() - 1);
			startSequence = sequenceComponent - (lstTransactionComponents.size() - 1);

			for (TransactionComponent objComponent : lstTransactionComponents) {
				objComponent.setTransactionComponentId(startSequence);
				startSequence++;
			}
			result = transactionCompRepository.registerTransactionComponent(lstTransactionComponents);
		}
		return result;
	}

	private int registerLogComponent(List<LogComponent> lstLogComponents) {
		int result = 0;
		Long startSequence = 0L;
		Long sequenceComponent = 0L;
		// execute file operation component
		if (lstLogComponents.size() > 0) {
			sequenceComponent = logCompRepository.getSequencesLogComponent(lstLogComponents.size() - 1);
			startSequence = sequenceComponent - (lstLogComponents.size() - 1);

			for (LogComponent objComponent : lstLogComponents) {
				objComponent.setLogComponentId(startSequence);
				startSequence++;
			}
			result = logCompRepository.registerLogComponent(lstLogComponents);
		}
		return result;
	}

	private int registerUtilityComponent(List<UtilityComponent> lstUtilityComponents, Long businessLogicId) {
		int result = 0;
		Long startSequence = 0L;
		Long sequenceComponent = 0L;
		//
		if (lstUtilityComponents.size() > 0) {
			sequenceComponent = utilityCompRepository.getSequencesUtilityComponent(lstUtilityComponents.size() - 1);
			startSequence = sequenceComponent - (lstUtilityComponents.size() - 1);

			for (UtilityComponent objComponent : lstUtilityComponents) {
				objComponent.setUtilityComponentId(startSequence);
				startSequence++;

				// Target
				String targetId = objComponent.getTargetId();
				Integer targetScope = objComponent.getTargetScope();
				if (targetId != null && targetId.length() > 0) {
					targetId = targetId.substring(1, targetId.length());
					Long id = getKeyOfParameter(targetId, targetScope);
					if (id.compareTo(0L) <= 0) {
						throw new BusinessException("Target error in utility");
					}
					objComponent.setTargetId(id.toString());
				} else {
					objComponent.setTargetId(null);
					objComponent.setTargetScope(null);
				}

				// Param
				String parameterId = objComponent.getParameterId();
				Integer paramScope = objComponent.getParameterScope();
				if (parameterId != null && parameterId.length() > 0) {
					parameterId = parameterId.substring(1, parameterId.length());
					Long id = getKeyOfParameter(parameterId, paramScope);
					if (id.compareTo(0L) <= 0) {
						throw new BusinessException("Param error in utility");
					}
					objComponent.setParameterId(id.toString());
				} else {
					objComponent.setParameterId(null);
					objComponent.setParameterScope(null);
				}

				// Index
				parseIndex(objComponent);

				// index of pass parameter
				if (CollectionUtils.isNotEmpty(objComponent.getLstParameterIndex())) {
					for (BDParameterIndex index : objComponent.getLstParameterIndex()) {
						index.setBusinessLogicId(businessLogicId);
						index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_UTILITY_CONTENT);
						index.setTableId(objComponent.getUtilityComponentId());
						parseParameterIndex(index);
					}
				}

				// index of pass parameter
				if (CollectionUtils.isNotEmpty(objComponent.getLstIndex())) {
					for (BDParameterIndex index : objComponent.getLstIndex()) {
						index.setBusinessLogicId(businessLogicId);
						index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_UTILITY_INDEX);
						index.setTableId(objComponent.getUtilityComponentId());
						parseParameterIndex(index);
					}
				}
			}
			result = utilityCompRepository.registerUtilityComponent(lstUtilityComponents);
		}
		return result;
	}

	private int registerEmailComponent(List<EmailComponent> lstEmailComponents) {
		int result = 0;
		Long startSequence = 0L;
		Long sequenceComponent = 0L;
		// execute file operation component
		if (lstEmailComponents.size() > 0) {
			sequenceComponent = emailCompRepository.getSequencesEmailComponent(lstEmailComponents.size() - 1);
			startSequence = sequenceComponent - (lstEmailComponents.size() - 1);

			for (EmailComponent objComponent : lstEmailComponents) {
				objComponent.setEmailComponentId(startSequence);
				startSequence++;
			}
			result = emailCompRepository.registerEmailComponent(lstEmailComponents);
		}
		return result;
	}

	private int registerDownloadFileComponent(List<DownloadFileComponent> lstDownloadFileComponents) {
		int result = 0;
		Long startSequence = 0L;
		Long sequenceComponent = 0L;
		//
		if (lstDownloadFileComponents.size() > 0) {
			sequenceComponent = downloadFileCompRepository.getSequencesDownloadFileComponent(lstDownloadFileComponents.size() - 1);
			startSequence = sequenceComponent - (lstDownloadFileComponents.size() - 1);

			for (DownloadFileComponent objComponent : lstDownloadFileComponents) {
				objComponent.setDownloadFileComponentId(startSequence);
				startSequence++;

				// Param
				String parameterId = objComponent.getParameterId();
				Integer paramScope = objComponent.getParameterScope();
				if (parameterId != null && parameterId.length() > 0) {
					parameterId = parameterId.substring(1, parameterId.length());
					Long id = getKeyOfParameter(parameterId, paramScope);
					if (id.compareTo(0L) <= 0) {
						throw new BusinessException("Param error in utility");
					}
					objComponent.setParameterId(id.toString());
				} else {
					objComponent.setParameterId(null);
					objComponent.setParameterScope(null);
				}
			}
			result = downloadFileCompRepository.registerDownloadFileComponent(lstDownloadFileComponents);
		}
		return result;
	}

	private void parseFormulaDetail(FormulaDefinition formulaDefinition, BusinessDesign businessDesign) {
		Long formulaDefinitionId = -1L;
		formulaDefinitionId = formulaDefinition.getFormulaDefinitionId();
		for (FormulaDetail formulaDetail : formulaDefinition.getFormulaDefinitionDetails()) {
			if (formulaDetail.getType() != null) {
				switch (formulaDetail.getType().intValue()) {
					case BusinessDesignConst.FormulaBuilder.TYPE_IN_BUSINESSLOGIC:
					case BusinessDesignConst.FormulaBuilder.TYPE_OB_BUSINESSLOGIC:
					case BusinessDesignConst.FormulaBuilder.TYPE_OU_BUSINESSLOGIC:
						if (StringUtils.isNotBlank(formulaDetail.getParameterId())) {
							String parameterId = formulaDetail.getParameterId().substring(1, formulaDetail.getParameterId().length());
							String scope = formulaDetail.getParameterId().substring(0, 1);
							Long id = getKeyOfParameter(parameterId, Integer.valueOf(scope));
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("Parameter error in formula");
							}
							formulaDetail.setParameterId(id.toString());
						}
						break;
					default:
						break;
				}
			}
			// index of parameter
			if (formulaDetail.getFormulaMethodInputs() != null) {
				for (FormulaMethodInput objMethodInput : formulaDetail.getFormulaMethodInputs()) {
					// Param
					String parameterId = objMethodInput.getParameterId();
					Integer paramScope = objMethodInput.getParameterScope();
					if (parameterId != null && parameterId.length() > 0) {
						parameterId = parameterId.substring(1, parameterId.length());
						Long id = getKeyOfParameter(parameterId, paramScope);
						if (id.compareTo(0L) <= 0) {
							throw new BusinessException("Param error in utility");
						}
						objMethodInput.setParameterId(id.toString());
					} else {
						objMethodInput.setParameterId(null);
						objMethodInput.setParameterScope(null);
					}

					//					if (objMethodInput.getParameterScope() != null && objMethodInput.getParameterScope().intValue() != 0) {
					//						Long id = -1L;
					//						if (objMethodInput.getParameterScope().intValue() == BusinessDesignConst.FormulaBuilder.TYPE_IN_BUSINESSLOGIC) {
					//							id = getKeyOfParameter(objMethodInput.getParameterId().substring(1, objMethodInput.getParameterId().length()), BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
					//						} else if (objMethodInput.getParameterScope().intValue() == BusinessDesignConst.FormulaBuilder.TYPE_OB_BUSINESSLOGIC) {
					//							id = getKeyOfParameter(objMethodInput.getParameterId().substring(1, objMethodInput.getParameterId().length()), BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID);
					//						}
					//						if (id.compareTo(0L) <= 0) {
					//							throw new BusinessException("Parameter error in function of formula");
					//						}
					//						objMethodInput.setParameterId(id.toString());
					//					}

					// index of pass parameter
					if (CollectionUtils.isNotEmpty(objMethodInput.getLstParameterIndex())) {
						for (BDParameterIndex index : objMethodInput.getLstParameterIndex()) {
							if (index != null) {
								index.setBusinessLogicId(businessDesign.getBusinessLogicId());
								index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_FORMULA_DETAIL);
								parseParameterIndex(index);
							}
						}
					}
				}
			}
			formulaDetail.setFormulaDefinitionId(formulaDefinitionId);
			lstFormulaDetails.add(formulaDetail);
		}
	}

	private int registerFormulaDetail(List<FormulaDetail> lstFormulaDetails, BusinessDesign businessDesign) {
		Long startSequence = 0L;
		if (lstFormulaDetails.size() > 0) {
			Long sequenceFormulaDetail = formulaDefinitionRepository.getSequencesFormulaDetail(lstFormulaDetails.size() - 1);
			startSequence = sequenceFormulaDetail - (lstFormulaDetails.size() - 1);

			int totalMessage = 0;
			for (FormulaDetail objDetail : lstFormulaDetails) {
				if (CollectionUtils.isNotEmpty(objDetail.getFormulaMethodInputs())) {
					totalMessage += objDetail.getFormulaMethodInputs().size();
				}
			}

			int size = totalMessage - 1;
			Long sequenceParameter = formulaDefinitionRepository.getSequencesFormulaMethodInput(size);
			Long startFormulaMethodInputSequence = sequenceParameter - (size);

			for (FormulaDetail objDetail : lstFormulaDetails) {
				objDetail.setFormulaDetailId(startSequence);
				startSequence++;

				if (CollectionUtils.isNotEmpty(objDetail.getLstParameterIndex())) {
					for (BDParameterIndex index : objDetail.getLstParameterIndex()) {
						if (index != null) {
							index.setBusinessLogicId(businessDesign.getBusinessLogicId());
							index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_FORMULA_DETAIL_PARAMETER);
							index.setTableId(objDetail.getFormulaDetailId());
							parseParameterIndex(index);
						}
					}
				}

				if (CollectionUtils.isNotEmpty(objDetail.getFormulaMethodInputs())) {
					for (FormulaMethodInput obj : objDetail.getFormulaMethodInputs()) {
						obj.setFormulaMethodInputId(startFormulaMethodInputSequence);
						startFormulaMethodInputSequence++;

						// index of pass parameter
						if (CollectionUtils.isNotEmpty(obj.getLstParameterIndex())) {
							for (BDParameterIndex index : obj.getLstParameterIndex()) {
								if (index != null) {
									index.setTableId(obj.getFormulaMethodInputId());
								}
							}
						}
						//						if (Integer.valueOf(BusinessDesignConst.FormulaBuilder.PARAMETER_TYPE_PARAMETER).equals(obj.getParameterType())) {
						//							obj.setParameterValue(null);
						//						} else if (Integer.valueOf(BusinessDesignConst.FormulaBuilder.PARAMETER_TYPE_VALUE).equals(obj.getParameterType())) {
						//							obj.setParameterValue(obj.getParameterId());
						//							obj.setParameterId(null);
						//						}
					}
				}
			}
		}
		int result = formulaDefinitionRepository.registerFormulaDetails(lstFormulaDetails);
		return result;
	}

	@Override
	public List<TableDesignDetailsOutput> getColumnsByTableId(Long tableId) throws BusinessException {
		return businessDesignRepository.getColumnsByTableIdForBD(tableId);
	}

	@Override
	public List<ScreenItemOutput> getScreenItemByScreenId(Long screenId,Integer type, CommonModel commonModel) {
		List<ScreenItemOutput> lstItemOutputDBs = businessDesignRepository.getScreenItemOutputByScreenIdForBD(screenId,type, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
		Map<Long, Integer> keyMapArea = new HashMap<Long, Integer>();
		Map<Long, Integer> keyMapForm = new HashMap<Long, Integer>();
		List<ScreenArea> lstPageableScreenArea = screenAreaRepository.findPageableScreenAreaByScreenId(screenId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
		Map<Long,Boolean> mapIndexOfPageable = new HashMap<Long, Boolean>();
		for (ScreenArea screenArea : lstPageableScreenArea) {
			mapIndexOfPageable.put(screenArea.getScreenAreaId(), false);
        }
		// In case of autocomplete / sql builder
		List<ScreenItemOutput> lstItemOutputs = new ArrayList<ScreenItemOutput>();
		if (FunctionCommon.isNotEmpty(lstItemOutputDBs)) {
			ScreenItemOutput fromScreenItem,toScreenItem,optionDisplayScreenItem,sourceScreenItem,displayScreenItem;
			for (ScreenItemOutput screenItemOutput : lstItemOutputDBs) {
				try {
					if(BusinessDesignConst.MappingScreenItem.INPUTBEAN.equals(type)){
						if(ScreenDesignConst.FromTo.FROM_TO.equals(screenItemOutput.getDisplayFromTo())){
							fromScreenItem = new ScreenItemOutput();
							toScreenItem = new ScreenItemOutput();
							
							fromScreenItem = (ScreenItemOutput) screenItemOutput.clone();
							fromScreenItem.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_FROM);
							fromScreenItem.setDataType((screenItemOutput.getPhysicalDataType() != null)?BusinessDesignHelper.convertJavaTypeFromBaseType(screenItemOutput.getPhysicalDataType()):DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
							lstItemOutputs.add(fromScreenItem);
							
							toScreenItem = (ScreenItemOutput) screenItemOutput.clone();
							toScreenItem.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_TO);
							toScreenItem.setDataType((screenItemOutput.getPhysicalDataType() != null)?BusinessDesignHelper.convertJavaTypeFromBaseType(screenItemOutput.getPhysicalDataType()):DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
							lstItemOutputs.add(toScreenItem);
						}else{
							screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_SUBMIT);
							screenItemOutput.setDataType((screenItemOutput.getPhysicalDataType() != null)?BusinessDesignHelper.convertJavaTypeFromBaseType(screenItemOutput.getPhysicalDataType()):DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
							lstItemOutputs.add(screenItemOutput);
							if (screenItemOutput.getLogicalDataType().equals(DbDomainConst.LogicDataType.AUTOCOMPLETE)) {
								optionDisplayScreenItem = (ScreenItemOutput) screenItemOutput.clone();
								optionDisplayScreenItem.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_DISPLAY);
								optionDisplayScreenItem.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
								lstItemOutputs.add(optionDisplayScreenItem);
							}
						}
					}else{
						//pageable of screen area
						if(!mapIndexOfPageable.getOrDefault(screenItemOutput.getScreenAreaId(), true)){
							ScreenItemOutput screenItemTemp = new ScreenItemOutput();
							screenItemTemp = (ScreenItemOutput) screenItemOutput.clone();
							screenItemTemp.setScreenItemId(screenItemOutput.getScreenAreaId());
							screenItemTemp.setItemName("Total count");
							screenItemTemp.setLogicalDataType(DbDomainConst.LogicDataType.LABEL_DYNAMIC);
							screenItemTemp.setPhysicalDataType(DbDomainConst.BaseType.TEXT_BASETYPE);
							screenItemTemp.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
							screenItemTemp.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_TOTAL_COUNT);
							lstItemOutputs.add(screenItemTemp);
							mapIndexOfPageable.replace(screenItemOutput.getScreenAreaId(), true);
						}
						// have config datasource is auto complete
						if (screenItemOutput.getLogicalDataType().equals(DbDomainConst.LogicDataType.AUTOCOMPLETE)) {
							// screen item output : display
							screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_SELECT);
							screenItemOutput.setDataType((screenItemOutput.getPhysicalDataType() != null)?BusinessDesignHelper.convertJavaTypeFromBaseType(screenItemOutput.getPhysicalDataType()):DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
							lstItemOutputs.add(screenItemOutput);
	
							ScreenItemOutput screenItemTemp = new ScreenItemOutput();
							screenItemTemp = (ScreenItemOutput) screenItemOutput.clone();
							screenItemTemp.setPhysicalDataType(DbDomainConst.BaseType.TEXT_BASETYPE);
							screenItemTemp.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
							screenItemTemp.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_DISPLAY);
							lstItemOutputs.add(screenItemTemp);
						}
						else if(screenItemOutput.getLogicalDataType().equals(DbDomainConst.LogicDataType.SELECT) ||
								screenItemOutput.getLogicalDataType().equals(DbDomainConst.LogicDataType.CHECKBOX) ||
								screenItemOutput.getLogicalDataType().equals(DbDomainConst.LogicDataType.RADIO)){
							optionDisplayScreenItem = new ScreenItemOutput();
							sourceScreenItem = new ScreenItemOutput();
							displayScreenItem = new ScreenItemOutput();
	
								screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_SELECT);
								screenItemOutput.setDataType((screenItemOutput.getPhysicalDataType() != null)?BusinessDesignHelper.convertJavaTypeFromBaseType(screenItemOutput.getPhysicalDataType()):DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
								lstItemOutputs.add(screenItemOutput);
	
								if(BusinessDesignConst.DataSourceType.BLOGIC_DEFINE.equals(screenItemOutput.getDataSourceType())){
	
									optionDisplayScreenItem = (ScreenItemOutput) screenItemOutput.clone();
									optionDisplayScreenItem.setPhysicalDataType(DbDomainConst.BaseType.TEXT_BASETYPE);
									optionDisplayScreenItem.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
									optionDisplayScreenItem.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_DISPLAY);
									lstItemOutputs.add(optionDisplayScreenItem);
	
									displayScreenItem = (ScreenItemOutput) screenItemOutput.clone();
									displayScreenItem.setPhysicalDataType(DbDomainConst.BaseType.TEXT_BASETYPE);
									displayScreenItem.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
									displayScreenItem.setArrayFlg(0);
									displayScreenItem.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_SUBMIT);
									lstItemOutputs.add(displayScreenItem);
	
									sourceScreenItem = (ScreenItemOutput) screenItemOutput.clone();
									sourceScreenItem.setDataType(DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE);
									sourceScreenItem.setArrayFlg(1);
									sourceScreenItem.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_DATASOURCE);
									lstItemOutputs.add(sourceScreenItem);
								}
							
						}else{
							if(ScreenDesignConst.FromTo.FROM_TO.equals(screenItemOutput.getDisplayFromTo())){
								fromScreenItem = new ScreenItemOutput();
								toScreenItem = new ScreenItemOutput();
								
								fromScreenItem = (ScreenItemOutput) screenItemOutput.clone();
								fromScreenItem.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_FROM);
								fromScreenItem.setDataType((screenItemOutput.getPhysicalDataType() != null)?BusinessDesignHelper.convertJavaTypeFromBaseType(screenItemOutput.getPhysicalDataType()):DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
								lstItemOutputs.add(fromScreenItem);
								
								toScreenItem = (ScreenItemOutput) screenItemOutput.clone();
								toScreenItem.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_TO);
								toScreenItem.setDataType((screenItemOutput.getPhysicalDataType() != null)?BusinessDesignHelper.convertJavaTypeFromBaseType(screenItemOutput.getPhysicalDataType()):DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
								lstItemOutputs.add(toScreenItem);
							}else{
								screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_DISPLAY);
								screenItemOutput.setDataType((screenItemOutput.getPhysicalDataType() != null)?BusinessDesignHelper.convertJavaTypeFromBaseType(screenItemOutput.getPhysicalDataType()):DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
								lstItemOutputs.add(screenItemOutput);
							}
						}
					}
				} catch (CloneNotSupportedException e) {
				}
			}
		}
		//in case of area don't have another items.
		for (ScreenArea screenArea : lstPageableScreenArea) {
			if(!mapIndexOfPageable.getOrDefault(screenArea.getScreenAreaId(),true)){
				ScreenItemOutput screenItemTemp = new ScreenItemOutput();
				screenItemTemp.setScreenItemId(screenArea.getScreenAreaId());
				screenItemTemp.setItemName("Total count");
				screenItemTemp.setLogicalDataType(DbDomainConst.LogicDataType.LABEL_DYNAMIC);
				screenItemTemp.setPhysicalDataType(DbDomainConst.BaseType.TEXT_BASETYPE);
				screenItemTemp.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
				screenItemTemp.setAreaType(DbDomainConst.AreaType.LIST_ENTITIES.toString());
				screenItemTemp.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_TOTAL_COUNT);
				screenItemTemp.setFormCode(screenArea.getFormCode());
				screenItemTemp.setScreenFormId(screenArea.getScreenFormId());
				screenItemTemp.setScreenAreaId(screenArea.getScreenAreaId());
				screenItemTemp.setAreaName(screenArea.getAreaLocalName());
				screenItemTemp.setAreaType(DbDomainConst.AreaType.LIST_ENTITIES.toString());
				lstItemOutputs.add(screenItemTemp);
			}
		}
		
		for (ScreenItemOutput screenItemOutput : lstItemOutputs) {
//			screenItemOutput.setItemName(screenItemOutput.getItemName() + BusinessDesignHelper.getMappingNameOfMappingOuput(screenItemOutput.getMappingType(),screenItemOutput.getLogicalDataType()));
			
			if (BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_SUBMIT.equals(screenItemOutput.getMappingType())) {
				screenItemOutput.setDataType((screenItemOutput.getPhysicalDataType() != null)?BusinessDesignHelper.convertJavaTypeFromBaseType(screenItemOutput.getPhysicalDataType()):DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
			}

			//in case of fileupload >> Object
			if(DbDomainConst.LogicDataType.FILEUPLOAD.equals(screenItemOutput.getLogicalDataType())){
				screenItemOutput.setDataType(DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE);
			}
			if (keyMapArea.containsKey(screenItemOutput.getScreenAreaId())) {
				screenItemOutput.setAreaRowspan(0);
				int currentArea = keyMapArea.get(screenItemOutput.getScreenAreaId());
				keyMapArea.put(screenItemOutput.getScreenAreaId(), currentArea + 1);
				
			} else {
				screenItemOutput.setAreaRowspan(1);
				keyMapArea.put(screenItemOutput.getScreenAreaId(), 1);
			}
			if (keyMapForm.containsKey(screenItemOutput.getScreenFormId())) {
				screenItemOutput.setFormRowspan(0);
				int currentForm = keyMapForm.get(screenItemOutput.getScreenFormId());
				keyMapForm.put(screenItemOutput.getScreenFormId(), currentForm + 1);
			} else {
				screenItemOutput.setFormRowspan(1);
				keyMapForm.put(screenItemOutput.getScreenFormId(), 1);
			}
		}
		for (Map.Entry<Long, Integer> entry : keyMapArea.entrySet()) {
			Long key = entry.getKey();
			Integer value = entry.getValue();
			if (value > 1) {
				for (ScreenItemOutput obj : lstItemOutputs) {
					if (obj.getScreenAreaId().equals(key)) {
						obj.setAreaRowspan(value);
						break;
					}
				}
			}
		}
		for (Map.Entry<Long, Integer> entry : keyMapForm.entrySet()) {
			Long key = entry.getKey();
			Integer value = entry.getValue();
			if (value > 1) {
				for (ScreenItemOutput obj : lstItemOutputs) {
					if (obj.getScreenFormId().equals(key)) {
						obj.setFormRowspan(value);
						break;
					}
				}
			}
		}
		return lstItemOutputs;
	}

	private Long getKeyOfParameter(String parameterId, Integer scope) {
		Long id = -1L;
		if (BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.equals(scope)) {
			id = mKeyInClient.getOrDefault(parameterId, -1L);
		} else if (BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.equals(scope)) {
			id = mKeyObClient.getOrDefault(parameterId, -1L);
		} else if (BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID.equals(scope)) {
			id = mKeyOuClient.getOrDefault(parameterId, -1L);
		}
		return id;
	}

	@Override
	public Map<String, String> getStandardCheckFWOfBusinessLogic(List<String> listOfMesssageCode, CommonModel commonModel) {
		Map<String, String> mapData = new HashMap<String, String>();
		List<MessageDesign> lstMessageDesigns = messageDesignRepository.findByMessageCodeOfProject(commonModel.getWorkingLanguageId(), listOfMesssageCode, commonModel.getWorkingProjectId());
		for (MessageDesign objData : lstMessageDesigns) {
			mapData.put(objData.getMessageCode(), objData.getMessageString());
		}
		return mapData;
	}

	@Override
	public BusinessDesign getInformationOfCommonBusinessLogic(Long businessLogicId, CommonModel commonModel) throws BusinessException {
		BusinessDesign objBusinessDesign = businessDesignRepository.findBusinessLogicInformation(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
		if (objBusinessDesign != null && objBusinessDesign.getBusinessLogicId() != null) {
			Map<String, String> mapTableIndex = new HashMap<String, String>();
			Map<String, Integer> mapSequence = new HashMap<String, Integer>();
			List<InputBean> lstInputBeans = businessDesignRepository.findInputBean(businessLogicId, commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
			for (InputBean in : lstInputBeans) {
				String currentGroup = "";
				if (in.getParentInputBeanId() != null) {
					currentGroup = mapTableIndex.get(in.getParentInputBeanId());

				}
				in.setGroupId(currentGroup);
				int maxIndex = mapSequence.getOrDefault(in.getGroupId(), 0);
				maxIndex++;
				if (in.getParentInputBeanId() == null) {
					in.setTableIndex(String.valueOf(maxIndex));
				} else {
					in.setTableIndex(currentGroup + "." + maxIndex);
				}
				mapTableIndex.put(in.getInputBeanId(), in.getTableIndex());
				mapSequence.put(in.getGroupId(), maxIndex);
			}

			mapTableIndex = new HashMap<String, String>();
			mapSequence = new HashMap<String, Integer>();
			List<OutputBean> lstOutputBeans = businessDesignRepository.findOutputBean(businessLogicId);
			for (OutputBean in : lstOutputBeans) {
				String currentGroup = "";
				if (in.getParentOutputBeanId() != null) {
					currentGroup = mapTableIndex.get(in.getParentOutputBeanId());

				}
				in.setGroupId(currentGroup);
				int maxIndex = mapSequence.getOrDefault(in.getGroupId(), 0);
				maxIndex++;
				if (in.getParentOutputBeanId() == null) {
					in.setTableIndex(String.valueOf(maxIndex));
				} else {
					in.setTableIndex(currentGroup + "." + maxIndex);
				}
				mapTableIndex.put(in.getOutputBeanId(), in.getTableIndex());
				mapSequence.put(in.getGroupId(), maxIndex);
			}
			objBusinessDesign.setLstInputBean(lstInputBeans);
			objBusinessDesign.setLstOutputBean(lstOutputBeans);
		} else {
			objBusinessDesign = new BusinessDesign();
		}
		return objBusinessDesign;
	}

	@Override
	public BusinessDesign findAllDeletionAffectionOfCommonBlogic(BusinessDesign businessDesign) {
		List<BusinessDesign> lstBusinessDesigns = businessDesignRepository.findBlogicByCommonBusinessBlogicId(businessDesign.getBusinessLogicId());
		businessDesign.setLstAffectedBlogicCommon(lstBusinessDesigns);
		return businessDesign;
	}

	@Override
	public BusinessDesign findAllDeletionAffectionOfNavigatorBlogic(BusinessDesign businessDesign, CommonModel commonModel) throws BusinessException {
		List<BusinessDesign> lstBusinessDesigns = businessDesignRepository.findBlogicByNavigatorBusinessBlogicId(businessDesign.getBusinessLogicId());
		businessDesign.setLstAffectedBlogicNavigator(lstBusinessDesigns);
		List<ScreenItem> lstAffectedScreenItems = screenItemRepository.getAllScreenItemByBusinessLogicId(businessDesign.getBusinessLogicId(), commonModel.getWorkingLanguageId());
		businessDesign.setLstAffectedScreenItems(lstAffectedScreenItems);
		return businessDesign;
	}

	private ResultMessages checkDuplicatedOfBusinessDesign(BusinessDesign businessDesign) {
		// check duplicated
		Long totalCount = businessDesignRepository.countNameCodeExist(businessDesign);
		ResultMessages resultMessages = ResultMessages.error();
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0005));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0006));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0005));
			resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0006));
		}
		return resultMessages;
	}

	private ResultMessages checkExistenceOfBusinessDesign(Long businessLogicId, Long languageId, Long projectId) {
		// check existence
		ResultMessages resultMessages = ResultMessages.error();
		BusinessDesign businessDesignModify = businessDesignRepository.findBusinessLogicInformation(businessLogicId, languageId, projectId);
		if (businessDesignModify == null) {
			resultMessages.add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010));
		}
		return resultMessages;
	}

	private void checkCommonOfBusinessDesign(BusinessDesign businessDesign, CommonModel commonModel) {
		if (BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(businessDesign.getBlogicType())) {
//			projectService.initData(getWorkingProjectId(), getAccountId());
		    commonModel.setProjectId(businessDesign.getProjectId());
			projectService.validateProject(commonModel);
		} else {
//			moduleService.initData(getWorkingProjectId(), getAccountId());
			moduleService.validateModule(businessDesign.getModuleId(), commonModel);
		}
	}

	/*
	 * parse node
	 */
	private void parseNode(BusinessDesign businessDesign) {
		List<SequenceLogic> resultSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> resultSequenceConnector = businessDesign.getLstSequenceConnectors();
		for (SequenceLogic objSequenceLogic : businessDesign.getLstSequenceLogics()) {
			if (Integer.valueOf(BusinessDesignConst.COMPONENT_NESTEDLOGIC).equals(objSequenceLogic.getComponentType())) {
				resultSequenceLogic.add(objSequenceLogic);
				List<SequenceLogic> result = this.unGroupNode(objSequenceLogic);
				resultSequenceLogic.addAll(result);
				List<SequenceConnector> resultConnector = this.unGroupConnector(objSequenceLogic);
				resultSequenceConnector.addAll(resultConnector);
			} else {
				resultSequenceLogic.add(objSequenceLogic);
			}
		}
		businessDesign.setLstSequenceLogics(resultSequenceLogic);
		businessDesign.setLstSequenceConnectors(resultSequenceConnector);
	}

	/*
	 * Ungroup node
	 */
	private List<SequenceLogic> unGroupNode(SequenceLogic objSequenceLogic) {
		List<SequenceLogic> resultSequenceLogic = new ArrayList<SequenceLogic>();
		NestedLogicComponent objNestedLogic;
		try {
			objNestedLogic = DataTypeUtils.toObject(objSequenceLogic.getStrData(), NestedLogicComponent.class);
		} catch (BusinessException e) {
			throw e;
		}

		if (objNestedLogic != null) {
			for (SequenceLogic sequenceLogic : objNestedLogic.getArrComponent()) {
				sequenceLogic.setGroupFlg(true);
				sequenceLogic.setParentSequenceLogicId(objSequenceLogic.getSequenceLogicId());
				if (Integer.valueOf(BusinessDesignConst.COMPONENT_NESTEDLOGIC).equals(sequenceLogic.getComponentType())) {
					resultSequenceLogic.add(sequenceLogic);
					List<SequenceLogic> result = this.unGroupNode(sequenceLogic);
					resultSequenceLogic.addAll(result);
				} else {
					resultSequenceLogic.add(sequenceLogic);
				}
			}
		}
		return resultSequenceLogic;
	}

	/*
	 * Ungroup connector
	 */
	private List<SequenceConnector> unGroupConnector(SequenceLogic objSequenceLogic) {
		List<SequenceConnector> resultSequenceConnector = new ArrayList<SequenceConnector>();
		NestedLogicComponent objNestedLogic;
		try {
			objNestedLogic = DataTypeUtils.toObject(objSequenceLogic.getStrData(), NestedLogicComponent.class);
		} catch (BusinessException e) {
			throw e;
		}

		if (objNestedLogic != null) {
			resultSequenceConnector = objNestedLogic.getArrConnection();
			for (SequenceLogic sequenceLogic : objNestedLogic.getArrComponent()) {
				if (Integer.valueOf(BusinessDesignConst.COMPONENT_NESTEDLOGIC).equals(sequenceLogic.getComponentType())) {
					List<SequenceConnector> result = this.unGroupConnector(sequenceLogic);
					resultSequenceConnector.addAll(result);
				}
			}
		}
		return resultSequenceConnector;
	}

	@Override
	public NestedLogicComponent parseInformationOfNestedlogic(String strData, boolean isOnlyView) throws BusinessException {
		NestedLogicComponent logicComponent = new NestedLogicComponent();
		logicComponent = DataTypeUtils.toObject(strData, NestedLogicComponent.class);
		if (logicComponent != null && logicComponent.getArrComponent() != null) {
			for (SequenceLogic sequenceLogic : logicComponent.getArrComponent()) {
				BusinessDesignHelper.assignStyleComponent(sequenceLogic, isOnlyView);
			}
		}
		return logicComponent;
	}

	@Override
	public void checkDesignStatus(BusinessDesign businessDesign) {
		// if check design status
		if (DbDomainConst.DesignStatus.FIXED.equals(businessDesign.getDesignStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0111, StringUtils.lowerCase(MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0008)), businessDesign.getBusinessLogicName()));
		}
	}

	@Override
	public List<PatternedComponent> findPatternedComponentOfProject(CommonModel commonModel) {
		List<PatternedComponent> lstPatternedComponents = patternedCompRepository.findPatternedComponentByProject(commonModel.getWorkingProjectId());
		if (lstPatternedComponents.size() > 0) {
			List<PatternedDetail> lstPatternedDetails = patternedCompRepository.findPatternedDetailByProject(commonModel.getWorkingProjectId());
			List<PatternedDetailConnector> lstPatternedDetailConnectors = patternedCompRepository.findPatternedDetailConnectorByProject(commonModel.getWorkingProjectId());
			for (PatternedComponent patternedComponent : lstPatternedComponents) {
				List<PatternedDetail> lstPatternedDetailTemps = new ArrayList<PatternedDetail>();
				for (PatternedDetail patternedDetail : lstPatternedDetails) {
					if (patternedComponent.getPatternedComponentId().equals(patternedDetail.getPatternedComponentId())) {
						lstPatternedDetailTemps.add(patternedDetail);
					}
				}
				patternedComponent.setLstPatternedDetails(lstPatternedDetailTemps);

				List<PatternedDetailConnector> lstPatternedDetailConnectorTemps = new ArrayList<PatternedDetailConnector>();
				for (PatternedDetailConnector patternedDetailConnector : lstPatternedDetailConnectors) {
					if (patternedComponent.getPatternedComponentId().equals(patternedDetailConnector.getPatternedComponentId())) {
						lstPatternedDetailConnectorTemps.add(patternedDetailConnector);
					}
				}
				patternedComponent.setLstPatternedDetailConnectors(lstPatternedDetailConnectorTemps);

				patternedComponent.setJsonLstPatternedDetails(DataTypeUtils.toJson(patternedComponent.getLstPatternedDetails()));
				patternedComponent.setJsonLstPatternedDetailConnectors(DataTypeUtils.toJson(patternedComponent.getLstPatternedDetailConnectors()));
			}
		}
		return lstPatternedComponents;
	}

	private void detectNodeUsingFormula(Long projectId, BusinessDesign businessDesign, List<IfComponent> lstIfComponents, List<LoopComponent> lstLoopComponents, List<AssignComponent> lstAssignComponents, List<BusinessCheckComponent> lstBusinessChecks, List<FileOperationComponent> lstFileOperationComponents, List<ImportFileComponent> lstImportFileComponents, List<ExportFileComponent> lstExportFileComponents, List<LogComponent> lstLogComponents, List<EmailComponent> lstEmailComponents, List<DownloadFileComponent> lstDownloadFileComponents) {
		int count = 0;
		Long sequence = 0L;
		Long startSequence = 0L;
		List<FormulaDefinition> lstFormulaDefinitions = new ArrayList<FormulaDefinition>();
		for (IfComponent objComponent : lstIfComponents) {
			for (IfConditionDetail objCondDetail : objComponent.getIfConditionDetails()) {
				objCondDetail.setIfComponentId(objComponent.getIfComponentId());
				if (objCondDetail.getFormulaDefinitionDetails() != null && objCondDetail.getFormulaDefinitionDetails().size() > 0) {
					count++;
				}
			}
		}
		for (LoopComponent objComponent : lstLoopComponents) {
			if (BusinessDesignConst.LoopComponent.LOOP_TYPE_WHILE.equals(objComponent.getLoopType())) {
				if (objComponent.getFormulaDefinitionDetails() != null && objComponent.getFormulaDefinitionDetails().size() > 0) {
					count++;
				}
			}
		}
		for (AssignComponent objComponent : lstAssignComponents) {
			for (AssignDetail objDetail : objComponent.getDetails()) {
				if (BusinessDesignConst.AssignDetailComponent.ASSIGN_TYPE_FORMULA.equals(objDetail.getAssignType())) {
					count++;
				}
			}
		}
		for (BusinessCheckComponent objComponent : lstBusinessChecks) {
			for (BusinessCheckDetail objDetail : objComponent.getBusinessCheckDetails()) {
				if (BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_FORMULA.equals(objDetail.getBusinessCheckType())) {
					count++;
				}
			}
		}
		for (FileOperationComponent objComponent : lstFileOperationComponents) {
			if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getSourcePathType())) {
				count++;
			}
			if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getDestinationPathType())) {
				count++;
			}
			if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getNewFilenameType())) {
				count++;
			}
			if (BusinessDesignConst.FileOperationComponent.TYPE_MERGE.equals(objComponent.getType())) {
				for (MergeFileDetail objDetail : objComponent.getLstMergeFileDetails()) {
					if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objDetail.getSourcePathType())) {
						count++;
					}
				}
			}
		}
		for (ImportFileComponent objComponent : lstImportFileComponents) {
			if (BusinessDesignConst.ImportFileComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getSourcePathType())) {
				count++;
			}
		}
		for (ExportFileComponent objComponent : lstExportFileComponents) {
			if (BusinessDesignConst.ExportFileComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getDestinationPathType())) {
				count++;
			}
		}
		for (LogComponent objComponent : lstLogComponents) {
			if (BusinessDesignConst.LogComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getMessageType())) {
				count++;
			}
		}
		for (EmailComponent objComponent : lstEmailComponents) {
			if (BusinessDesignConst.EmailComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getSubjectType())) {
				count++;
			}
			for (EmailRecipient objDetail : objComponent.getEmailRecipients()) {
				if (BusinessDesignConst.EmailComponent.PATH_TYPE_FORMULASETTING.equals(objDetail.getRecipientType())) {
					count++;
				}
			}
		}
		for (DownloadFileComponent objComponent : lstDownloadFileComponents) {
			if (BusinessDesignConst.DownloadFileComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getFileNameType())) {
				count++;
			}
		}
		if (count > 0) {
			sequence = formulaDefinitionRepository.getSequencesFormulaDefinition(count - 1);
			startSequence = sequence - (count - 1);
			for (IfComponent objComponent : lstIfComponents) {
				for (IfConditionDetail objCondDetail : objComponent.getIfConditionDetails()) {
					objCondDetail.setIfComponentId(objComponent.getIfComponentId());
					if (objCondDetail.getFormulaDefinitionDetails() != null && objCondDetail.getFormulaDefinitionDetails().size() > 0) {
						FormulaDefinition objFormula = new FormulaDefinition();
						objFormula.setProjectId(projectId);
						objFormula.setFormulaDefinitionId(startSequence);
						objFormula.setFormulaDefinitionDetails(objCondDetail.getFormulaDefinitionDetails());
						objFormula.setFormulaDefinitionContent(objCondDetail.getFormulaDefinitionContent());
						lstFormulaDefinitions.add(objFormula);
						objCondDetail.setFormulaDefinitionId(startSequence);
						startSequence++;
						parseFormulaDetail(objFormula, businessDesign);
					}
				}
			}
			for (LoopComponent objComponent : lstLoopComponents) {
				if (BusinessDesignConst.LoopComponent.LOOP_TYPE_WHILE.equals(objComponent.getLoopType())) {
					if (objComponent.getFormulaDefinitionDetails() != null && objComponent.getFormulaDefinitionDetails().size() > 0) {
						FormulaDefinition objFormula = new FormulaDefinition();
						objFormula.setProjectId(projectId);
						objFormula.setFormulaDefinitionId(startSequence);
						objFormula.setFormulaDefinitionDetails(objComponent.getFormulaDefinitionDetails());
						objFormula.setFormulaDefinitionContent(objComponent.getFormulaDefinitionContent());
						lstFormulaDefinitions.add(objFormula);
						objComponent.setFormulaDefinitionId(startSequence);
						startSequence++;
						parseFormulaDetail(objFormula, businessDesign);
					}
				}
			}
			for (AssignComponent objComponent : lstAssignComponents) {
				for (AssignDetail objDetail : objComponent.getDetails()) {
					if (BusinessDesignConst.AssignDetailComponent.ASSIGN_TYPE_FORMULA.equals(objDetail.getAssignType())) {
						FormulaDefinition objFormula = new FormulaDefinition();
						objFormula.setProjectId(projectId);
						objFormula.setFormulaDefinitionId(startSequence);
						objFormula.setFormulaDefinitionDetails(objDetail.getFormulaDefinitionDetails());
						objFormula.setFormulaDefinitionContent(objDetail.getFormulaDefinitionContent());
						lstFormulaDefinitions.add(objFormula);
						objDetail.setFormulaDefinitionId(startSequence);
						startSequence++;
						parseFormulaDetail(objFormula, businessDesign);
					}
				}
			}
			for (BusinessCheckComponent objComponent : lstBusinessChecks) {
				for (BusinessCheckDetail objDetail : objComponent.getBusinessCheckDetails()) {
					if (BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_FORMULA.equals(objDetail.getBusinessCheckType())) {
						FormulaDefinition objFormula = new FormulaDefinition();
						objFormula.setProjectId(projectId);
						objFormula.setFormulaDefinitionId(startSequence);
						objFormula.setFormulaDefinitionDetails(objDetail.getFormulaDefinitionDetails());
						objFormula.setFormulaDefinitionContent(objDetail.getFormulaDefinitionContent());
						lstFormulaDefinitions.add(objFormula);
						objDetail.setFormulaDefinitionId(startSequence);
						startSequence++;
						parseFormulaDetail(objFormula, businessDesign);
					}
				}
			}
			for (FileOperationComponent objComponent : lstFileOperationComponents) {
				FormulaDefinition objFormula = new FormulaDefinition();
				if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getSourcePathType())) {
					objFormula = new FormulaDefinition();
					objFormula.setProjectId(projectId);
					objFormula.setFormulaDefinitionId(startSequence);
					objFormula.setFormulaDefinitionDetails(objComponent.getSourcePathFormulaDetails());
					objFormula.setFormulaDefinitionContent(objComponent.getSourcePathContent());
					lstFormulaDefinitions.add(objFormula);
					objComponent.setSourcePathFormulaId(startSequence);
					startSequence++;
					parseFormulaDetail(objFormula, businessDesign);
				}
				if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getDestinationPathType())) {
					objFormula = new FormulaDefinition();
					objFormula.setProjectId(projectId);
					objFormula.setFormulaDefinitionId(startSequence);
					objFormula.setFormulaDefinitionDetails(objComponent.getDestinationPathFormulaDetails());
					objFormula.setFormulaDefinitionContent(objComponent.getDestinationPathContent());
					lstFormulaDefinitions.add(objFormula);
					objComponent.setDestinationPathFormulaId(startSequence);
					startSequence++;
					parseFormulaDetail(objFormula, businessDesign);
				}
				if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getNewFilenameType())) {
					objFormula = new FormulaDefinition();
					objFormula.setProjectId(projectId);
					objFormula.setFormulaDefinitionId(startSequence);
					objFormula.setFormulaDefinitionDetails(objComponent.getNewFilenameFormulaDetails());
					objFormula.setFormulaDefinitionContent(objComponent.getNewFilenameContent());
					lstFormulaDefinitions.add(objFormula);
					objComponent.setNewFilenameFormulaId(startSequence);
					startSequence++;
					parseFormulaDetail(objFormula, businessDesign);
				}
				if (BusinessDesignConst.FileOperationComponent.TYPE_MERGE.equals(objComponent.getType())) {
					for (MergeFileDetail objDetail : objComponent.getLstMergeFileDetails()) {
						if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objDetail.getSourcePathType())) {
							objFormula = new FormulaDefinition();
							objFormula.setProjectId(projectId);
							objFormula.setFormulaDefinitionId(startSequence);
							objFormula.setFormulaDefinitionDetails(objDetail.getSourcePathFormulaDetails());
							objFormula.setFormulaDefinitionContent(objDetail.getSourcePathContent());
							lstFormulaDefinitions.add(objFormula);
							objDetail.setSourcePathFormulaId(startSequence);
							startSequence++;
							parseFormulaDetail(objFormula, businessDesign);
						}
					}
				}
			}
			for (ImportFileComponent objComponent : lstImportFileComponents) {
				FormulaDefinition objFormula = new FormulaDefinition();
				if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getSourcePathType())) {
					objFormula = new FormulaDefinition();
					objFormula.setProjectId(projectId);
					objFormula.setFormulaDefinitionId(startSequence);
					objFormula.setFormulaDefinitionDetails(objComponent.getSourcePathFormulaDetails());
					objFormula.setFormulaDefinitionContent(objComponent.getSourcePathContent());
					lstFormulaDefinitions.add(objFormula);
					objComponent.setSourcePathFormulaId(startSequence);
					startSequence++;
					parseFormulaDetail(objFormula, businessDesign);
				}
			}
			for (ExportFileComponent objComponent : lstExportFileComponents) {
				FormulaDefinition objFormula = new FormulaDefinition();
				if (BusinessDesignConst.FileOperationComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getDestinationPathType())) {
					objFormula = new FormulaDefinition();
					objFormula.setProjectId(projectId);
					objFormula.setFormulaDefinitionId(startSequence);
					objFormula.setFormulaDefinitionDetails(objComponent.getDestinationPathFormulaDetails());
					objFormula.setFormulaDefinitionContent(objComponent.getDestinationPathContent());
					lstFormulaDefinitions.add(objFormula);
					objComponent.setDestinationPathFormulaId(startSequence);
					startSequence++;
					parseFormulaDetail(objFormula, businessDesign);
				}
			}
			for (LogComponent objComponent : lstLogComponents) {
				FormulaDefinition objFormula = new FormulaDefinition();
				if (BusinessDesignConst.LogComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getMessageType())) {
					objFormula = new FormulaDefinition();
					objFormula.setProjectId(projectId);
					objFormula.setFormulaDefinitionId(startSequence);
					objFormula.setFormulaDefinitionDetails(objComponent.getMessageFormulaDetails());
					objFormula.setFormulaDefinitionContent(objComponent.getMessageContent());
					lstFormulaDefinitions.add(objFormula);
					objComponent.setMessageFormulaId(startSequence);
					startSequence++;
					parseFormulaDetail(objFormula, businessDesign);
				}
			}
			for (EmailComponent objComponent : lstEmailComponents) {
				FormulaDefinition objFormula = new FormulaDefinition();
				if (BusinessDesignConst.EmailComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getSubjectType())) {
					objFormula = new FormulaDefinition();
					objFormula.setProjectId(projectId);
					objFormula.setFormulaDefinitionId(startSequence);
					objFormula.setFormulaDefinitionDetails(objComponent.getSubjectFormulaDetails());
					objFormula.setFormulaDefinitionContent(objComponent.getSubjectContent());
					lstFormulaDefinitions.add(objFormula);
					objComponent.setSubjectFormulaId(startSequence);
					startSequence++;
					parseFormulaDetail(objFormula,businessDesign);
				}
				for (EmailRecipient objDetail : objComponent.getEmailRecipients()) {
					if (BusinessDesignConst.EmailComponent.PATH_TYPE_FORMULASETTING.equals(objDetail.getRecipientType())) {
						objFormula = new FormulaDefinition();
						objFormula.setProjectId(projectId);
						objFormula.setFormulaDefinitionId(startSequence);
						objFormula.setFormulaDefinitionDetails(objDetail.getRecipientFormulaDetails());
						objFormula.setFormulaDefinitionContent(objDetail.getRecipientContent());
						lstFormulaDefinitions.add(objFormula);
						objDetail.setRecipientFormulaId(startSequence);
						startSequence++;
						parseFormulaDetail(objFormula,businessDesign);
					}
				}
			}
			for (DownloadFileComponent objComponent : lstDownloadFileComponents) {
				FormulaDefinition objFormula = new FormulaDefinition();
				if (BusinessDesignConst.DownloadFileComponent.PATH_TYPE_FORMULASETTING.equals(objComponent.getFileNameType())) {
					objFormula = new FormulaDefinition();
					objFormula.setProjectId(projectId);
					objFormula.setFormulaDefinitionId(startSequence);
					objFormula.setFormulaDefinitionDetails(objComponent.getFileNameFormulaDetails());
					objFormula.setFormulaDefinitionContent(objComponent.getFileNameContent());
					lstFormulaDefinitions.add(objFormula);
					objComponent.setFileNameFormulaId(startSequence);
					startSequence++;
					parseFormulaDetail(objFormula, businessDesign);
				}
			}
			formulaDefinitionRepository.registerFormulaDefinition(lstFormulaDefinitions);
		}
	}

	private void detectNodeUsingCreateMessageDesign(Long moduleId, List<BusinessCheckComponent> lstBusinessChecks, List<FeedbackComponent> lstFeedbacks, Timestamp currentTime, CommonModel commonModel) {
		int count = 0;
		List<MessageDesign> lstMessageDesigns = new ArrayList<MessageDesign>();
		if (CollectionUtils.isNotEmpty(lstFeedbacks)) {
			for (FeedbackComponent objComponent : lstFeedbacks) {
				if (Boolean.FALSE.equals(objComponent.getExistedMessageFlg()) && StringUtils.isNotBlank(objComponent.getMessageCodeAutocomplete())) {
					MessageDesign message = this.initialMessageDesign(objComponent.getMessageCodeAutocomplete(), MessageType.LABEL_MESSAGE, moduleId, currentTime, commonModel);
					lstMessageDesigns.add(message);
				}
				if(CollectionUtils.isNotEmpty(objComponent.getMessageParameter())){
					for(MessageParameter objParameter : objComponent.getMessageParameter()){
						if (BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE.equals(objParameter.getParameterType()) && Boolean.FALSE.equals(objParameter.getExistedMessageFlg())
								&& StringUtils.isNotBlank(objParameter.getParameterCodeAutocomplete())) {
							MessageDesign message = this.initialMessageDesign(objParameter.getParameterCodeAutocomplete(), MessageType.LABEL_MESSAGE, moduleId, currentTime, commonModel);
							lstMessageDesigns.add(message);
						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(lstBusinessChecks)) {
			for (BusinessCheckComponent objComponent : lstBusinessChecks) {
				if (CollectionUtils.isNotEmpty(objComponent.getBusinessCheckDetails())) {
					for (BusinessCheckDetail checkDetail : objComponent.getBusinessCheckDetails()) {
						if (Boolean.FALSE.equals(checkDetail.getExistedMessageFlg()) && StringUtils.isNotBlank(checkDetail.getMessageCodeAutocomplete())) {
							MessageDesign message = this.initialMessageDesign(checkDetail.getMessageCodeAutocomplete(), MessageType.LABEL_MESSAGE, moduleId, currentTime, commonModel);
							lstMessageDesigns.add(message);
						}
						if(CollectionUtils.isNotEmpty(checkDetail.getParameters())){
							for(MessageParameter objParameter : checkDetail.getParameters()){
								if (BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE.equals(objParameter.getParameterType()) && Boolean.FALSE.equals(objParameter.getExistedMessageFlg())
										&& StringUtils.isNotBlank(objParameter.getParameterCodeAutocomplete())) {
									MessageDesign message = this.initialMessageDesign(objParameter.getParameterCodeAutocomplete(), MessageType.LABEL_MESSAGE, moduleId, currentTime, commonModel);
									lstMessageDesigns.add(message);
								}
							}
						}
					}
				}
			}
		}
		count = lstMessageDesigns.size();
		if (FunctionCommon.isNotEmpty(lstMessageDesigns)) {
			lstMessageDesigns = messageDesignService.registerMessageDesign(lstMessageDesigns, true);
		}
		if (count == lstMessageDesigns.size()) {
			count = 0;
			if (CollectionUtils.isNotEmpty(lstFeedbacks)) {
				for (FeedbackComponent objComponent : lstFeedbacks) {
					if (Boolean.FALSE.equals(objComponent.getExistedMessageFlg()) && StringUtils.isNotBlank(objComponent.getMessageCodeAutocomplete())) {
						objComponent.setMessageCode(lstMessageDesigns.get(count).getMessageCode());
						count++;
					}
					if(CollectionUtils.isNotEmpty(objComponent.getMessageParameter())){
						for(MessageParameter objParameter : objComponent.getMessageParameter()){
							if (BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE.equals(objParameter.getParameterType()) && Boolean.FALSE.equals(objParameter.getExistedMessageFlg())
									&& StringUtils.isNotBlank(objParameter.getParameterCodeAutocomplete())) {
								objParameter.setParameterCode(lstMessageDesigns.get(count).getMessageCode());
								count++;
							}
						}
					}
				}
			}
			if (CollectionUtils.isNotEmpty(lstBusinessChecks)) {
				for (BusinessCheckComponent objComponent : lstBusinessChecks) {
					if (CollectionUtils.isNotEmpty(objComponent.getBusinessCheckDetails())) {
						for (BusinessCheckDetail checkDetail : objComponent.getBusinessCheckDetails()) {
							if (Boolean.FALSE.equals(checkDetail.getExistedMessageFlg()) && StringUtils.isNotBlank(checkDetail.getMessageCodeAutocomplete())) {
								checkDetail.setMessageCode(lstMessageDesigns.get(count).getMessageCode());
								count++;
							}
							if(CollectionUtils.isNotEmpty(checkDetail.getParameters())){
								for(MessageParameter objParameter : checkDetail.getParameters()){
									if (BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE.equals(objParameter.getParameterType()) && Boolean.FALSE.equals(objParameter.getExistedMessageFlg())
											&& StringUtils.isNotBlank(objParameter.getParameterCodeAutocomplete())) {
										objParameter.setParameterCode(lstMessageDesigns.get(count).getMessageCode());
										count++;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private MessageDesign initialMessageDesign(String messageString, String messageType, Long moduleId, Timestamp currentTime, CommonModel commonModel) {
		MessageDesign messageDesign = new MessageDesign();
		messageDesign.setMessageString(messageString);
		messageDesign.setModuleId(moduleId);
		messageDesign.setProjectId(commonModel.getWorkingProjectId());
		messageDesign.setMessageLevel(MessageLevel.BUSINESS_LOGIC);
		messageDesign.setMessageType(messageType);
		messageDesign.setGeneratedStatus(DbDomainConst.MessageGeneratedStatus.AUTO_TRANSLATE);
		messageDesign.setLanguageId(commonModel.getWorkingLanguageId());
		messageDesign.setCreatedBy(commonModel.getCreatedBy());
		messageDesign.setCreatedDate(currentTime);
		messageDesign.setUpdatedBy(commonModel.getUpdatedBy());
		messageDesign.setUpdatedDate(currentTime);
		return messageDesign;
	}

	private List<MessageParameter> initialMessageParameters(List<MessageParameter> lstMessageParameters, Integer targetType, Long targetId, Long businessLogicId) {
		List<MessageParameter> lstNewParameters = new ArrayList<MessageParameter>();
		if (CollectionUtils.isNotEmpty(lstMessageParameters)) {
			for (MessageParameter objParameter : lstMessageParameters) {
				if (BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VARIABLE.equals(objParameter.getParameterType())) {
					String parameterId = objParameter.getParameterCode();
					Integer parameterScope;
					if (parameterId != null && parameterId.length() > 0) {
						parameterScope = Integer.valueOf(parameterId.substring(0, 1));
						parameterId = parameterId.substring(1, parameterId.length());
						Long id = getKeyOfParameter(parameterId, parameterScope);
						if (id.compareTo(0L) <= 0) {
							throw new BusinessException("Parrameter error in message parameter");
						}
						objParameter.setParameterCode(id.toString());
						objParameter.setParameterScope(parameterScope);
					} else {
						objParameter.setParameterCode(null);
						objParameter.setParameterScope(null);
					}

					// index of pass parameter
					if (CollectionUtils.isNotEmpty(objParameter.getLstParameterIndex())) {
						for (BDParameterIndex index : objParameter.getLstParameterIndex()) {
							index.setBusinessLogicId(businessLogicId);
							index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_MESSAGE_PARAMETER);
							index.setTableId(objParameter.getMessageParameterId());
							parseParameterIndex(index);
						}
					}
				}
				objParameter.setTargetType(targetType);
				objParameter.setTargetId(targetId);
				objParameter.setBusinessLogicId(businessLogicId);
				lstNewParameters.add(objParameter);
			}
		}
		return lstNewParameters;
	}

	private void parseParameterIndex(BDParameterIndex index) {
		if (StringUtils.isNotBlank(index.getParameterId())) {
			// get id of parameter id
			String indexId = index.getParameterId().substring(1, index.getParameterId().length());
			String scope = index.getParameterId().substring(0, 1);
			Long id = getKeyOfParameter(indexId, Integer.valueOf(scope));
			index.setParameterId(id.toString());

			if (StringUtils.isNotBlank(index.getParameterIndexId())) {
				// get id of parameter index id
				indexId = index.getParameterIndexId();
				if (!BusinessDesignConst.ParameterIndex.INDEX_TYPE_CUSTOMIZE.equals(index.getParameterIndexType())) {
					indexId = indexId.substring(1, index.getParameterIndexId().length());
					if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP.equals(index.getParameterIndexType())) {
						if (mKeySequenceLogic.containsKey(indexId)) {
							indexId = mKeySequenceLogic.get(indexId).toString();
						} else {
							throw new BusinessException("Index is not in map");
						}
					} else {
						indexId = getKeyOfParameter(indexId, index.getParameterIndexType()).toString();
					}
				}
				index.setParameterIndexId(indexId);
			} else {

			}
		}
	}

	private void parseIndex(UtilityComponent index) {
		if (StringUtils.isNotBlank(index.getIndexId())) {
			// get id of parameter index id
			String indexId = index.getIndexId();
			if (!BusinessDesignConst.ParameterIndex.INDEX_TYPE_CUSTOMIZE.equals(index.getIndexScope())) {
				indexId = indexId.substring(1, index.getIndexId().length());
				if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP.equals(index.getIndexScope())) {
					if (mKeySequenceLogic.containsKey(indexId)) {
						indexId = mKeySequenceLogic.get(indexId).toString();
					} else {
						throw new BusinessException("Index is not in map");
					}
				} else {
					indexId = getKeyOfParameter(indexId, index.getIndexScope()).toString();
				}
			}
			index.setIndexId(indexId);
		} else {
			index.setIndexId(null);
			index.setIndexScope(null);
		}
	}

	private void parseFrom(LoopComponent from) {
		if (from.getFromScope() != null && from.getFromScope() != -1) {
			// get id of parameter index id
			String fromValue = from.getFromValue();
			if (!BusinessDesignConst.ParameterIndex.INDEX_TYPE_CUSTOMIZE.equals(from.getFromScope())) {
				fromValue = fromValue.substring(1, from.getFromValue().length());
				if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP.equals(from.getFromScope())) {
					if (mKeySequenceLogic.containsKey(fromValue)) {
						fromValue = mKeySequenceLogic.get(fromValue).toString();
					} else {
						throw new BusinessException("Index is not in map");
					}
				} else {
					fromValue = getKeyOfParameter(fromValue, from.getFromScope()).toString();
				}
			}
			from.setFromValue(fromValue);
		} else {
			from.setFromValue(null);
		}
	}

	private void parseTo(LoopComponent to) {
		if (to.getToScope() != null && to.getToScope() != -1) {
			// get id of parameter index id
			String toValue = to.getToValue();
			if (!BusinessDesignConst.ParameterIndex.INDEX_TYPE_CUSTOMIZE.equals(to.getToScope())) {
				toValue = toValue.substring(1, to.getToValue().length());
				if (BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP.equals(to.getToScope())) {
					if (mKeySequenceLogic.containsKey(toValue)) {
						toValue = mKeySequenceLogic.get(toValue).toString();
					} else {
						throw new BusinessException("Index is not in map");
					}
				} else {
					toValue = getKeyOfParameter(toValue, to.getToScope()).toString();
				}
			}
			to.setToValue(toValue);
		} else {
			to.setToValue(null);
		}
	}

	private int registerExceptionComponent(List<ExceptionComponent> lstExceptionComponents, BusinessDesign businessDesign) {
		int result = 0;
		Long startSequence = 0L;
		Long startExceptionDetailSequence = 0L;
		// execute navigator component
		if (lstExceptionComponents.size() > 0) {
			Long sequenceExceptionComponent = exceptionComponentRepository.getSequencesExceptionComponent(lstExceptionComponents.size() - 1);
			startSequence = sequenceExceptionComponent - (lstExceptionComponents.size() - 1);

			// get sequence of navigator detail
			int countInputBeans = 0;
			for (ExceptionComponent objComponent : lstExceptionComponents) {
				if (objComponent.getParameterInputBeans() != null) {
					countInputBeans += objComponent.getParameterInputBeans().size();
				}
			}
			if (countInputBeans > 0) {
				Long sequenceExceptionDetail = exceptionComponentRepository.getSequencesExceptionDetail(countInputBeans - 1);
				startExceptionDetailSequence = sequenceExceptionDetail - (countInputBeans - 1);
			}
			for (ExceptionComponent objComponent : lstExceptionComponents) {
				// List<ExceptionDetail> lstExceptionDetails = new
				// ArrayList<ExceptionDetail>();
				objComponent.setExceptionComponentId(startSequence);
				startSequence++;
				if (objComponent.getParameterInputBeans() != null) {
					for (ExceptionDetail objInputBean : objComponent.getParameterInputBeans()) {
						objInputBean.setExceptionComponentId(objComponent.getExceptionComponentId());
						// lstExceptionDetails.add(SerializationUtils.clone(objInputBean));
						objInputBean.setExceptionDetailId(startExceptionDetailSequence);
						startExceptionDetailSequence++;

						String parameterId = objInputBean.getParameterId();
						Integer scope = objInputBean.getParameterScope();
						if (parameterId != null && parameterId.length() > 0) {
							parameterId = parameterId.substring(1, parameterId.length());
							Long id = getKeyOfParameter(parameterId, scope);
							if (id.compareTo(0L) <= 0) {
								throw new BusinessException("Input bean error in navigator");
							}
							objInputBean.setParameterId(id.toString());
						} else {
							objInputBean.setParameterId(null);
							objInputBean.setParameterScope(null);
						}

						// index of pass parameter
						if (CollectionUtils.isNotEmpty(objInputBean.getLstParameterIndex())) {
							for (BDParameterIndex index : objInputBean.getLstParameterIndex()) {
								index.setBusinessLogicId(businessDesign.getBusinessLogicId());
								index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_EXCEPTION_DETAIL_PARAMETER);
								index.setTableId(objInputBean.getExceptionDetailId());
								parseParameterIndex(index);
							}
						}
					}
				}
				// objComponent.setParameterInputBeans(lstExceptionDetails);
			}
			result = exceptionComponentRepository.registerExceptionComponent(lstExceptionComponents);
		}
		return result;
	}

//	private void buildProblemWhenModifyStandardBlogic(ImpactOfBusinessDesign impact,CommonModel commonModel,Boolean flagInsertProblem){
//		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
//		List<BusinessDesign> lstUsedBusinessDesign = new ArrayList<BusinessDesign>();
//		List<ScreenItem> lstUsedScreenItems = new ArrayList<ScreenItem>();
//		List<InputBean> lstModifedNotAffectedInputBeans = new ArrayList<InputBean>();
//		List<InputBean> lstModifedAffectedInputBeans = new ArrayList<InputBean>();
//		
//		BusinessDesign currentBDesign = impact.getCurrentBusinessDesign();
//		BusinessDesign oldBDesign = impact.getOldBusinessDesign();
//		
//		List<InputBean> lstNewInputBeans = impact.getLstNewInputBeans();
//		Map<InputBean,InputBean> mapModifedInputBeans = impact.getMapModifedInputBeans();
//		List<InputBean> lstDeletedInputBeans = impact.getLstDeletedInputBeans();
//		
//		Timestamp currentTime = FunctionCommon.getCurrentTime();
//		
//		boolean isChange = false;
//		if(CollectionUtils.isNotEmpty(lstNewInputBeans)){
//			isChange = true;
//		}
//		for (Map.Entry<InputBean, InputBean> entry : mapModifedInputBeans.entrySet()){
//			InputBean news = entry.getKey();
//			InputBean old = entry.getValue();
//			if(FunctionCommon.notEquals(news.getInputBeanCode(),old.getInputBeanCode()) || FunctionCommon.notEquals(news.getInputBeanName(),old.getInputBeanName())){
//    			lstModifedNotAffectedInputBeans.add(news);
//    		}
//    		if(FunctionCommon.notEquals(news.getDataType(),old.getDataType()) || FunctionCommon.notEquals(news.getArrayFlg(),old.getArrayFlg())){
//    			lstModifedAffectedInputBeans.add(news);
//    		}
//        }
//		if(CollectionUtils.isNotEmpty(lstModifedAffectedInputBeans)){
//			isChange = true;
//		}
//		
//		if(isChange){
//			lstUsedBusinessDesign = businessDesignRepository.findBlogicByNavigatorBusinessBlogicId(currentBDesign.getBusinessLogicId());
//			if (CollectionUtils.isNotEmpty(lstUsedBusinessDesign)) {
//				for (BusinessDesign businessDesign : lstUsedBusinessDesign) {
//					ProblemList problemList = new ProblemList();
//					problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0065,currentBDesign.getBusinessLogicName(),businessDesign.getBusinessLogicName()));
//					problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
//					problemList.setResourceId(businessDesign.getBusinessLogicId());
//					problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
//					problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
//					problemList.setModuleId(businessDesign.getModuleId());
//					problemList.setProjectId(businessDesign.getProjectId());
//					problemList.setCreatedBy(commonModel.getCreatedBy());
//					problemList.setFromResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC);
//	    			problemList.setFromResourceId(currentBDesign.getBusinessLogicId());
//					problemList.setCreatedDate(currentTime);
//					lstProblemLists.add(problemList);
//	            }
//			}
//			lstUsedScreenItems = screenItemRepository.getAllScreenItemByBusinessLogicId(currentBDesign.getBusinessLogicId(), commonModel.getWorkingLanguageId());
//			if (CollectionUtils.isNotEmpty(lstUsedScreenItems)) {
//				for (ScreenItem usedScreen : lstUsedScreenItems) {
//					ProblemList problemList = new ProblemList();
//					problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0026, currentBDesign.getBusinessLogicName(), usedScreen.getItemCode()));
//					problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_ITEM);
//					problemList.setResourceId(usedScreen.getScreenId());
//					problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
//					problemList.setAutofixFlg(AutoFix.DISABLE);
//					problemList.setUrlId(DbDomainConst.ResourceURL.URL_SCREEN_DESIGN);
//					problemList.setModuleId(usedScreen.getModuleId());
//					problemList.setProjectId(currentBDesign.getProjectId());
//					problemList.setFromResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC);
//					problemList.setFromResourceId(currentBDesign.getBusinessLogicId());
//					problemList.setCreatedBy(commonModel.getCreatedBy());
//					problemList.setCreatedDate(currentTime);
//					lstProblemLists.add(problemList);
//				}
//			}
//		}
//		if(Boolean.TRUE.equals(flagInsertProblem)){
//			if(CollectionUtils.isNotEmpty(lstProblemLists)){
//				problemListRepository.multiRegisterProblem(lstProblemLists);
//			}
//			//change name-code
//			if(FunctionCommon.notEquals(currentBDesign.getBusinessLogicName(), oldBDesign.getBusinessLogicName()) || FunctionCommon.notEquals(currentBDesign.getBusinessLogicCode(), oldBDesign.getBusinessLogicCode())){
//				navigationComponentRepository.modifyNavigatorNodeWhenModifyBLogic(currentBDesign);
//				exceptionComponentRepository.modifyExceptionNodeWhenModifyBLogic(currentBDesign);
//			}
//			//auto delete deletedInputbean
//			if(CollectionUtils.isNotEmpty(lstDeletedInputBeans)){
//				navigationComponentRepository.deleteNavigatorInputValueWhenModifyBLogic(lstDeletedInputBeans);
//				exceptionComponentRepository.deleteExceptionInputValueWhenModifyBLogic(lstDeletedInputBeans);
//			}
//			if(CollectionUtils.isNotEmpty(lstModifedNotAffectedInputBeans)){
//				navigationComponentRepository.modifyNavigatorInputValueWhenModifyBLogic(lstModifedNotAffectedInputBeans);
//				exceptionComponentRepository.modifyExceptionInputValueWhenModifyBLogic(lstModifedNotAffectedInputBeans);
//			}
//		}
//		impact.setLstUsedNavigatorBlogics(lstUsedBusinessDesign);
//		impact.setLstUsedNavigatorScreenItems(lstUsedScreenItems);
//	}
//	
//	private void buildProblemWhenDeleteStandardBlogic(ImpactOfBusinessDesign impact,CommonModel commonModel,Boolean flagInsertProblem){
//		BusinessDesign currentBDesign = impact.getCurrentBusinessDesign();
//		List<ProblemList> lstProblemLists = new ArrayList<ProblemList>();
//		List<BusinessDesign> lstUsedBusinessDesign = new ArrayList<BusinessDesign>();
//		List<ScreenItem> lstUsedScreenItems = new ArrayList<ScreenItem>();
//		Timestamp currentTime = FunctionCommon.getCurrentTime();
//		lstUsedBusinessDesign = businessDesignRepository.findBlogicByNavigatorBusinessBlogicId(currentBDesign.getBusinessLogicId());
//		for (BusinessDesign businessDesign : lstUsedBusinessDesign) {
//			ProblemList problemList = new ProblemList();
//			problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0066,currentBDesign.getBusinessLogicName(),businessDesign.getBusinessLogicName()));
//			problemList.setResourceType(DbDomainConst.ResourceType.BLOGIC);
//			problemList.setResourceId(businessDesign.getBusinessLogicId());
//			problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
//			problemList.setUrlId(DbDomainConst.ResourceURL.URL_BLOGIC);
//			problemList.setModuleId(businessDesign.getModuleId());
//			problemList.setProjectId(businessDesign.getProjectId());
//			problemList.setCreatedBy(commonModel.getCreatedBy());
//			problemList.setFromResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC);
//			problemList.setFromResourceId(currentBDesign.getBusinessLogicId());
//			problemList.setCreatedDate(currentTime);
//			lstProblemLists.add(problemList);
//        }
//		lstUsedScreenItems = screenItemRepository.getAllScreenItemByBusinessLogicId(currentBDesign.getBusinessLogicId(), commonModel.getWorkingLanguageId());
//		if (CollectionUtils.isNotEmpty(lstUsedScreenItems)) {
//			for (ScreenItem usedScreen : lstUsedScreenItems) {
//				ProblemList problemList = new ProblemList();
//				problemList.setProblemName(MessageUtils.getMessage(ProblemListMessageConst.INF_PROBLEMLIST_0022, currentBDesign.getBusinessLogicName(), usedScreen.getItemCode()));
//				problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_ITEM);
//				problemList.setResourceId(usedScreen.getScreenId());
//				problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
//				problemList.setAutofixFlg(AutoFix.DISABLE);
//				problemList.setUrlId(DbDomainConst.ResourceURL.URL_SCREEN_DESIGN);
//				problemList.setModuleId(usedScreen.getModuleId());
//				problemList.setProjectId(currentBDesign.getProjectId());
//				problemList.setFromResourceType(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC);
//				problemList.setFromResourceId(currentBDesign.getBusinessLogicId());
//				problemList.setCreatedBy(commonModel.getCreatedBy());
//				problemList.setCreatedDate(currentTime);
//				lstProblemLists.add(problemList);
//			}
//		}
//		if(Boolean.TRUE.equals(flagInsertProblem)){
//			if(CollectionUtils.isNotEmpty(lstProblemLists)){
//				problemListRepository.multiRegisterProblem(lstProblemLists);
//			}
//		}
//		impact.setLstUsedNavigatorBlogics(lstUsedBusinessDesign);
//		impact.setLstUsedNavigatorScreenItems(lstUsedScreenItems);
//	}
	
//	@Override
//    public List<BusinessDesign> detectImpactWhenModifyCommonBlogic(BusinessDesign businessDesign,CommonModel commonModel,Boolean flagInsertProblem) {
//	    BusinessDesign oldbusinessDesign = businessDesignRepository.findBusinessLogicInformation(businessDesign.getBusinessLogicId(), commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
//		if (oldbusinessDesign == null) {
//			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010)));
//		}
//
//		List<InputBean> lstOldInputBeans = businessDesignRepository.findInputBean(businessDesign.getBusinessLogicId(), commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
//		List<OutputBean> lstOldOutputBeans = businessDesignRepository.findOutputBean(businessDesign.getBusinessLogicId());
//
//		List<InputBean> lstNewInputBeans = new ArrayList<InputBean>();
//		Map<InputBean,InputBean> mapModifedInputBeans = new HashMap<InputBean, InputBean>();
//		List<InputBean> lstDeletedInputBeans = new ArrayList<InputBean>();
//
//		List<OutputBean> lstNewOutputBeans = new ArrayList<OutputBean>();
//		Map<OutputBean,OutputBean> mapModifedOutputBeans = new HashMap<OutputBean, OutputBean>();
//		List<OutputBean> lstDeletedOutputBeans = new ArrayList<OutputBean>();
//		for (InputBean news : businessDesign.getLstInputBean()) {
//			if (news.getInputBeanId() != null && news.getInputBeanId().contains(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_CODE)){
//        		lstNewInputBeans.add(news);
//        	}
//		}
//		
//		boolean isExists = false;
//		for (InputBean old : lstOldInputBeans) {
//			isExists = false;
//	        for (InputBean news : businessDesign.getLstInputBean()) {
//	        	if(FunctionCommon.equals(news.getInputBeanId(), old.getInputBeanId())){
//	        		isExists = true;
//	        		mapModifedInputBeans.put(news, old);
//	        		break;
//	        	}
//	        	
//            }
//	        if(!isExists){
//	        	lstDeletedInputBeans.add(old);
//	        }
//        }
//		for (OutputBean old : lstOldOutputBeans) {
//			isExists = false;
//	        for (OutputBean news : businessDesign.getLstOutputBean()) {
//	        	if(FunctionCommon.equals(news.getOutputBeanId(), old.getOutputBeanId())){
//	        		isExists = true;
//	        		mapModifedOutputBeans.put(news, old);
//	        		break;
//	        	}
//	        	
//            }
//	        if(!isExists){
//	        	lstDeletedOutputBeans.add(old);
//	        }
//        }
//		ImpactOfBusinessDesign impact = new ImpactOfBusinessDesign();
//		impact.setLstNewInputBeans(lstNewInputBeans);
//		impact.setMapModifedInputBeans(mapModifedInputBeans);
//		impact.setLstDeletedInputBeans(lstDeletedInputBeans);
//		impact.setLstNewOutputBeans(lstNewOutputBeans);
//		impact.setMapModifedOutputBeans(mapModifedOutputBeans);
//		impact.setLstDeletedOutputBeans(lstDeletedOutputBeans);
//		impact.setCurrentBusinessDesign(businessDesign);
//		impact.setOldBusinessDesign(oldbusinessDesign);
//		buildProblemWhenModifyCommonBlogic(impact, commonModel, flagInsertProblem);
//	    return impact.getLstUsedCommonBlogics();
//    }
//
//	@Override
//    public List<BusinessDesign> detectImpactWhenDeleteCommonBlogic(BusinessDesign businessDesign, CommonModel commonModel, Boolean flagInsertProblem) {
//		ImpactOfBusinessDesign impact = new ImpactOfBusinessDesign();
//		impact.setCurrentBusinessDesign(businessDesign);
//		buildProblemWhenDeleteCommonBlogic(impact,commonModel,flagInsertProblem);
//	    return impact.getLstUsedCommonBlogics();
//    }

//	@Override
//    public ImpactOfBusinessDesign detectImpactWhenModifyOnlineBlogic(BusinessDesign businessDesign, CommonModel commonModel, Boolean flagInsertProblem) {
//		BusinessDesign oldbusinessDesign = businessDesignRepository.findBusinessLogicInformation(businessDesign.getBusinessLogicId(), commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
//		if (oldbusinessDesign == null) {
//			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010)));
//		}
//
//		List<InputBean> lstOldInputBeans = businessDesignRepository.findInputBean(businessDesign.getBusinessLogicId(), commonModel.getWorkingLanguageId(), commonModel.getWorkingProjectId());
//		
//		List<InputBean> lstNewInputBeans = new ArrayList<InputBean>();
//		Map<InputBean,InputBean> mapModifedInputBeans = new HashMap<InputBean, InputBean>();
//		List<InputBean> lstDeletedInputBeans = new ArrayList<InputBean>();
//
//		for (InputBean news : businessDesign.getLstInputBean()) {
//			if (news.getInputBeanId() != null && news.getInputBeanId().contains(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_CODE)){
//        		lstNewInputBeans.add(news);
//        	}
//		}
//		
//		boolean isExists = false;
//		for (InputBean old : lstOldInputBeans) {
//			isExists = false;
//	        for (InputBean news : businessDesign.getLstInputBean()) {
//	        	if(FunctionCommon.equals(news.getInputBeanId(), old.getInputBeanId())){
//	        		isExists = true;
//	        		mapModifedInputBeans.put(news, old);
//	        		break;
//	        	}
//	        	
//            }
//	        if(!isExists){
//	        	lstDeletedInputBeans.add(old);
//	        }
//        }
//		ImpactOfBusinessDesign impact = new ImpactOfBusinessDesign();
//		impact.setLstNewInputBeans(lstNewInputBeans);
//		impact.setMapModifedInputBeans(mapModifedInputBeans);
//		impact.setLstDeletedInputBeans(lstDeletedInputBeans);
//		impact.setCurrentBusinessDesign(businessDesign);
//		impact.setOldBusinessDesign(oldbusinessDesign);
//		buildProblemWhenModifyStandardBlogic(impact, commonModel, flagInsertProblem);
//	    return impact;
//    }

//	@Override
//    public ImpactOfBusinessDesign detectImpactWhenDeleteOnlineBlogic(BusinessDesign businessDesign, CommonModel commonModel, Boolean flagInsertProblem) {
//		ImpactOfBusinessDesign impact = new ImpactOfBusinessDesign();
//		impact.setCurrentBusinessDesign(businessDesign);
//		buildProblemWhenDeleteStandardBlogic(impact,commonModel,flagInsertProblem);
//	    return impact;
//    }
	
	private void modifyAffectedOfCommonBlogic(BusinessDesign commonBlogic,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(commonBlogic.getProjectId()));
		jobControl.setModuleId(String.valueOf(commonBlogic.getModuleId()));
		jobControl.setCreatedBy(String.valueOf(common.getUpdatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.COMMON_BLOGIC));
		jobControl.setImpactId(String.valueOf(commonBlogic.getBusinessLogicId()));
		jobControl.setCurAppStatus(GenerateAppStatus.INIT);
		jobControl.setAddDateTime(currentTime);
		jobControl.setUpdDateTime(currentTime);
		jobControl.setJobArgNm6(ImpactChangeDesign.CASE_MODIFY);
		
		//check the same business type job.
		Long count = impactChangeRepository.countImpactChangeByType(jobControl);
		if(count == 0)
			impactChangeRepository.registerImpactChange(jobControl);
		else
			impactChangeRepository.modifyImpactChange(jobControl);
    }
    
    private void deleteAffectedOfCommonBlogic(BusinessDesign commonBlogic,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(commonBlogic.getProjectId()));
		jobControl.setModuleId(String.valueOf(commonBlogic.getModuleId()));
		jobControl.setCreatedBy(String.valueOf(common.getCreatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.COMMON_BLOGIC));
		jobControl.setImpactId(String.valueOf(commonBlogic.getBusinessLogicId()));
		jobControl.setCurAppStatus(GenerateAppStatus.INIT);
		jobControl.setAddDateTime(currentTime);
		jobControl.setUpdDateTime(currentTime);
		jobControl.setJobArgNm6(ImpactChangeDesign.CASE_DELETE);
		jobControl.setJobArgNm7(commonBlogic.getBusinessLogicCode());
		
		//check the same business type job.
		Long count = impactChangeRepository.countImpactChangeByType(jobControl);
		if(count == 0)
			impactChangeRepository.registerImpactChange(jobControl);
		else
			impactChangeRepository.modifyImpactChange(jobControl);
    }
    private void modifyAffectedOfStandardBlogic(BusinessDesign commonBlogic,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(commonBlogic.getProjectId()));
		jobControl.setModuleId(String.valueOf(commonBlogic.getModuleId()));
		jobControl.setCreatedBy(String.valueOf(common.getUpdatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC));
		jobControl.setImpactId(String.valueOf(commonBlogic.getBusinessLogicId()));
		jobControl.setCurAppStatus(GenerateAppStatus.INIT);
		jobControl.setAddDateTime(currentTime);
		jobControl.setUpdDateTime(currentTime);
		jobControl.setJobArgNm6(ImpactChangeDesign.CASE_MODIFY);
		
		//check the same business type job.
		Long count = impactChangeRepository.countImpactChangeByType(jobControl);
		if(count == 0)
			impactChangeRepository.registerImpactChange(jobControl);
		else
			impactChangeRepository.modifyImpactChange(jobControl);
    }
    
    private void deleteAffectedOfStandardBlogic(BusinessDesign commonBlogic,CommonModel common) {
		ImpactChangeJobControl jobControl = new ImpactChangeJobControl();
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		jobControl.setJobAppCd(DbDomainConst.ImpactChangeDesign.JOB_APP_CD);
		jobControl.setProjectId(String.valueOf(commonBlogic.getProjectId()));
		jobControl.setModuleId(String.valueOf(commonBlogic.getModuleId()));
		jobControl.setCreatedBy(String.valueOf(common.getCreatedBy()));
		jobControl.setImpactType(String.valueOf(DbDomainConst.FromResourceType.NAVIGATOR_BLOGIC));
		jobControl.setImpactId(String.valueOf(commonBlogic.getBusinessLogicId()));
		jobControl.setCurAppStatus(GenerateAppStatus.INIT);
		jobControl.setAddDateTime(currentTime);
		jobControl.setUpdDateTime(currentTime);
		jobControl.setJobArgNm6(ImpactChangeDesign.CASE_DELETE);
		jobControl.setJobArgNm7(commonBlogic.getBusinessLogicCode());
		
		//check the same business type job.
		Long count = impactChangeRepository.countImpactChangeByType(jobControl);
		if(count == 0)
			impactChangeRepository.registerImpactChange(jobControl);
		else
			impactChangeRepository.modifyImpactChange(jobControl);
    }
}
