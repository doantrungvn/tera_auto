package org.terasoluna.qp.domain.service.generatescreen;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.JavaDataTypeOfBlogic;
import org.terasoluna.qp.app.common.constants.DbDomainConst.SqlPattern;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.BusinessDesignMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.AssignComponent;
import org.terasoluna.qp.domain.model.AssignDetail;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.BDParameterIndex;
import org.terasoluna.qp.domain.model.BusinessCheckComponent;
import org.terasoluna.qp.domain.model.BusinessCheckDetail;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.BusinessDetailContent;
import org.terasoluna.qp.domain.model.CodeListDetail;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.DownloadFileComponent;
import org.terasoluna.qp.domain.model.ExecutionComponent;
import org.terasoluna.qp.domain.model.ExecutionInputValue;
import org.terasoluna.qp.domain.model.ExecutionOutputValue;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.model.FeedbackComponent;
import org.terasoluna.qp.domain.model.FormulaDefinition;
import org.terasoluna.qp.domain.model.FormulaDetail;
import org.terasoluna.qp.domain.model.FormulaMethodInput;
import org.terasoluna.qp.domain.model.FormulaMethodOutput;
import org.terasoluna.qp.domain.model.FunctionDesign;
import org.terasoluna.qp.domain.model.FunctionMaster;
import org.terasoluna.qp.domain.model.FunctionMethod;
import org.terasoluna.qp.domain.model.FunctionMethodInput;
import org.terasoluna.qp.domain.model.FunctionMethodOutput;
import org.terasoluna.qp.domain.model.IfComponent;
import org.terasoluna.qp.domain.model.IfConditionDetail;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.LoopComponent;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.MessageParameter;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ModuleTableMapping;
import org.terasoluna.qp.domain.model.NavigatorComponent;
import org.terasoluna.qp.domain.model.NavigatorDetail;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenAction;
import org.terasoluna.qp.domain.model.ScreenActionParam;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenAreaSortMapping;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenGroupItem;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemCodelist;
import org.terasoluna.qp.domain.model.ScreenItemOutput;
import org.terasoluna.qp.domain.model.ScreenItemSequence;
import org.terasoluna.qp.domain.model.ScreenItemValidation;
import org.terasoluna.qp.domain.model.ScreenParameter;
import org.terasoluna.qp.domain.model.ScreenTransition;
import org.terasoluna.qp.domain.model.SequenceConnector;
import org.terasoluna.qp.domain.model.SequenceLogic;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.SqlDesignCondition;
import org.terasoluna.qp.domain.model.SqlDesignInput;
import org.terasoluna.qp.domain.model.SqlDesignOutput;
import org.terasoluna.qp.domain.model.SqlDesignResult;
import org.terasoluna.qp.domain.model.SqlDesignTable;
import org.terasoluna.qp.domain.model.SqlDesignTableItem;
import org.terasoluna.qp.domain.model.SqlDesignValue;
import org.terasoluna.qp.domain.model.TableDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.TableDesignForeignKey;
import org.terasoluna.qp.domain.model.TableDesignKeyItem;
import org.terasoluna.qp.domain.model.UserDefineCodelistDetails;
import org.terasoluna.qp.domain.model.UtilityComponent;
import org.terasoluna.qp.domain.model.ValidationCheckDetail;
import org.terasoluna.qp.domain.repository.autocomplete.AutocompleteDesignRepository;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.AssignComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.BDParameterIndexRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.BusinessCheckComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.DownloadFileComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ExecutionComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.FeedbackComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.FormulaDefinitionRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.IfComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.IfConditionDetailRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.LoopComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.MessageParameterRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.NavigationComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.UtilityComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ValidationCheckDetailRepository;
import org.terasoluna.qp.domain.repository.codelist.CodeListDetailRepository;
import org.terasoluna.qp.domain.repository.codelist.CodeListRepository;
import org.terasoluna.qp.domain.repository.domaindatatype.DomainDatatypeItemRepository;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectAttributeRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectDefinitionRepository;
import org.terasoluna.qp.domain.repository.functiondesign.FunctionDesignRepository;
import org.terasoluna.qp.domain.repository.functionmaster.FunctionMasterRepository;
import org.terasoluna.qp.domain.repository.languagedesign.LanguageDesignRepository;
import org.terasoluna.qp.domain.repository.messagedesign.MessageDesignRepository;
import org.terasoluna.qp.domain.repository.module.ModuleRepository;
import org.terasoluna.qp.domain.repository.moduletablemapping.ModuleTableMappingRepository;
import org.terasoluna.qp.domain.repository.screenaction.ScreenActionRepository;
import org.terasoluna.qp.domain.repository.screenactionparam.ScreenActionParamRepository;
import org.terasoluna.qp.domain.repository.screenarea.ScreenAreaRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.screenform.ScreenFormRepository;
import org.terasoluna.qp.domain.repository.screengroupitem.ScreenGroupItemRepository;
import org.terasoluna.qp.domain.repository.screenitem.ScreenItemRepository;
import org.terasoluna.qp.domain.repository.screenitemcodelist.ScreenItemCodelistRepository;
import org.terasoluna.qp.domain.repository.screenitemsequence.ScreenItemSequenceRepository;
import org.terasoluna.qp.domain.repository.screenitemvalidation.ScreenItemValidationRepository;
import org.terasoluna.qp.domain.repository.screentransition.ScreenTransitionRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignConditionRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignInputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignOutputRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignResultRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignTableItemsRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignTableRepository;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignValueRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignDetailRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignForeignKeyRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignKeyRepository;
import org.terasoluna.qp.domain.repository.tabledesign.TableDesignRepository;
import org.terasoluna.qp.domain.repository.tabledesign.UserDefineCodelistRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignHelper;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;
import org.terasoluna.qp.domain.service.functionmaster.FunctionMasterService;
import org.terasoluna.qp.domain.service.generatesourcecode.DetailServiceImpHandler;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst;
import org.terasoluna.qp.domain.service.messagedesign.MessageDesignService;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst.ScreenActionConst;

/**
 * Generate default screens for new screenDesignDefault.
 *
 * @author TrungDV
 */
@Service
@Transactional
public class GenerateScreenServiceImpl implements ScreenDesignGeneratorService {

	private static final String BACK_CONFIRM_CONFIRM_MODIFY = "BackConfirmConfirmModify";

	private static final String BACK_CONFIRM_CONFIRM_REGISTER = "BackConfirmConfirmRegister";

	private static final String MODIFY_DELETE_VIEW = "ModifyDeleteView";

	private static final String SAVE_MODIFY = "SaveModify";

	private static final String TO_MODIFY = "to modify";

	private static final String SAVE_REGISTER = "SaveRegister";

	private static final String VIEW = "view";

	private static final String MODIFY = "modify";

	private static final String SEARCH = "search";

	private static final String REGISTER = "register";
	
	private static final String REGISTER_AFTER_DELETE = "register {0} after delete";

	private static final String PROCESS_SEARCH_RECORDS = "Process search records";

	private static final String PROCESS_SEARCH_COUNT = "Process search count";

	private static final String INITIAL_SEARCH_RECORDS = "Initial search records";

	private static final String INITIAL_SEARCH_COUNT = "Initial search count";
	
	private static final String UPDATE = "update";
	private static final String GET = "Get";
	private static final String TO_VIEW = "to view";

	private static final String DELETE = "delete";

	private static final String COMPLETE = "complete";

	private static final String CONFIRM = "confirm";
	
	private static final String FUNCTION_METHOD_CODE_TOSTRING = "toString";
	
	private static final String FUNCTION_MASTER_CODE_QPARRAY = "QpArray";
	
	private static final String FUNCTION_MASTER_CODE_QPSTRING = "QpString";
	
	private static final String FUNCTION_METHOD_CODE_TO_ARRAYLIST = "toArrayList";
	
	private static final String TO_STRING_INPUT = "toStringInput";
	private static final String TO_ARRAYLIST_INPUT_STRING = "toArrayListInputString";
	private static final String TO_ARRAYLIST_INPUT_PATTERN = "toArrayListInputPattern";
	
	private static final Integer PARAMETER_TYPE_VALUE = 1;
	
	private static final Integer PARAMETER_TYPE_PARAMETER = 2;
	
	private static final String REGISTER_BATCH = "register batch";
	private static final String MODIFY_BATCH = "modify batch";
	private static final String DELETE_BATCH = "delete batch";
	private static final String TO_DOWNLOAD = "to download";
	private static final String AUTOCOMPLETE = "Autocomplete";
	private static final String UPDATED_DATE = "updated_date";
	private static final String SYSTEM_DATE = "system_date";
	private static final String SYSTEM_DATE_NAME = "System Date";
	private static boolean DOWNLOAD_FILE_BLOGIC_RELEASE = false;
	
	@Inject
	FunctionMasterRepository functionMasterRepository;

	@Inject
	ScreenDesignRepository screenDesignRepository;

	@Inject
	ScreenFormRepository screenFormRepository;

	@Inject
	DomainDatatypeItemRepository domainDatatypeItemRepository;

	@Inject
	ScreenAreaRepository screenAreaRepository;

	@Inject
	ScreenItemSequenceRepository screenItemSequenceRepository;

	@Inject
	ScreenItemRepository screenItemRepository;

	@Inject
	ScreenActionRepository screenActionRepository;

	@Inject
	ScreenItemValidationRepository screenItemValidationRepository;

	@Inject
	MessageDesignService messageDesignService;

	@Inject
	ScreenGroupItemRepository screenGroupItemRepository;

	@Inject
	ScreenItemCodelistRepository screenItemCodelistRepository;

	@Inject
	LanguageDesignRepository languageDesignRepository;

	@Inject
	AutocompleteDesignRepository autocompleteDesignRepository;

	@Inject
	BusinessDesignRepository businessDesignRepository;

	@Inject
	TableDesignDetailRepository tableDesignDetailRepository;

	@Inject
	TableDesignRepository tableDesignRepository;

	@Inject
	TableDesignKeyRepository tableDesignKeyRepository;

	@Inject
	TableDesignForeignKeyRepository tableDesignForeignKeyRepository;

	@Inject
	NavigationComponentRepository navigationComponentRepository;

	@Inject
	AssignComponentRepository assignComponentRepository;

	@Inject
	LoopComponentRepository loopComponentRepository;

	@Inject
	FeedbackComponentRepository feedbackComponentRepository;

	@Inject
	MessageParameterRepository messageParameterRepository;

	@Inject
	ModuleTableMappingRepository moduleTableMappingRepository;

	@Inject
	BusinessCheckComponentRepository businessCheckComponentRepository;

	@Inject
	SqlDesignRepository sqlDesignRepository;

	@Inject
	SqlDesignTableRepository sqlDesignTableRepository;

	@Inject
	SqlDesignTableItemsRepository sqlDesignTableItemsRepository;

	@Inject
	SqlDesignInputRepository sqlDesignInputRepository;

	@Inject
	SqlDesignValueRepository sqlDesignValueRepository;

	@Inject
	SqlDesignConditionRepository sqlDesignConditionRepository;

	@Inject
	SqlDesignResultRepository sqlDesignResultRepository;

	@Inject
	SqlDesignOutputRepository sqlDesignOutputRepository;

	@Inject
	ExecutionComponentRepository executionComponentRepository;

	@Inject
	UserDefineCodelistRepository userDefineCodelistRepository;

	@Inject
	ModuleRepository moduleRepository;

	@Inject
	CodeListRepository codeListRepository;

	@Inject
	ScreenActionParamRepository screenActionParamRepository;

	@Inject
	FunctionDesignRepository functionDesignRepository;

	@Inject
	SystemService systemService;

	@Inject
	ValidationCheckDetailRepository validationCheckDetailRepository;

	@Inject
	MessageDesignRepository messageDesignRepository;
	
	@Inject
	IfComponentRepository ifComponentRepository;
	
	@Inject
	IfConditionDetailRepository ifConditionDetailRepository;
	
	@Inject
	FormulaDefinitionRepository formulaDefinitionRepository;
	
	@Inject
	UtilityComponentRepository utilityCompRepository;
	
	@Inject
	BDParameterIndexRepository bDParameterIndexRepository;
	
	@Inject
	CodeListDetailRepository codeListDetailRepository;
	
	@Inject
	DomainDesignRepository domainDesignRepository; 
	
	@Inject
	FunctionMasterService functionMasterService;

	@Inject
	ProjectService projectService;
	
	@Inject
	ExternalObjectDefinitionRepository externalObjectDefinitionRepository;
	
	@Inject
	ExternalObjectAttributeRepository externalObjectAttributeRepository;
	
	@Inject
	DownloadFileComponentRepository downloadFileComponentRepository;
	
	@Inject
	ScreenTransitionRepository screenTransitionRepository;

	private final String SCREEN_AREA = "area";
	private final String SCREEN_AREA_RESULT = "areaResult";
	private final String SCREEN_FORM = "form";
	private final String SCREEN_TRANSITION = "transition";
	private final String VIEW_TO_MODIFY = "ViewToModify";
	private final String VIEW_TO_DELETE = "ViewToDelete";
	private final String CONFIRM_REGISTER_SUCCESS = "ConfirmRegisterSuccess";
	private final String CONFIRM_REGISTER_BACK = "ConfirmRegisterBack";
	private final String CONFIRM_MODIFY_SUCCESS = "ConfirmModifySuccess";
	private final String CONFIRM_MODIFY_BACK = "ConfirmModifyBack";
	private final String SEARCH_TO_REGISTER = "SearchToRegister";
	private final String SEARCH_TO_SEARCH = "SearchToSearch";
	private final String SEARCH_TO_MODIFY = "SearchToModify";
	private final String SEARCH_TO_VIEW = "SearchToView";
	private final String REGISTER_TO_SEARCH = "RegisterToSearch";
	private final String MODIFY_TO_SEARCH = "ModifyToSearch";
	private final String COMPLETE_REGISTER_TO_SEARCH = "CompleteRegisterToSearch";
	private final String COMPLETE_MODIFY_TO_SEARCH = "CompleteModifyToSearch";
	private final String MESSAGE_CODE_SEARCH_CONDITION = "sc.sys.0002";
	private final String MESSAGE_CODE_SEARCH_RESULT = "sc.sys.0003";
	private final String MESSAGE_CODE_SEARCH = "sc.sys.0001";
	private final String MESSAGE_CODE_REGISTER = "sc.sys.0005";
	private final String MESSAGE_CODE_VIEW = "sc.sys.0007";
	private final String MESSAGE_CODE_MODIFY = "sc.sys.0006";
	private final String MESSAGE_CODE_DELETE = "sc.sys.0008";
	private final String MESSAGE_CODE_SAVE = "sc.sys.0031";
	private final String MESSAGE_CODE_BACK = "sc.sys.0049";
	private final String MESSAGE_CODE_ACTION = "sc.sys.0096";
	private final String MESSAGE_CODE_CONFIRM = "sc.sys.0050";
	private final String MESSAGE_CODE_COMPLETE = "sc.sys.0051";
	private final String MESSAGE_CONFIRM_SAVE = "inf.sys.0015";
	private final String MESSAGE_CONFIRM_UPDATE = "inf.sys.0018";
	private final String MESSAGE_CONFIRM_DELETE = "inf.sys.0014";
	private final String SPACE = " ";
	private final String HEADER = "header";
	private final String MESSAGE_TYPE_SCREEN = "sc";
	private final Integer ITEM_TYPE_NORMAL = 1;
	private final Integer ITEM_TYPE_HIDDEN = 2;
	private final Integer FROM = 0;
	private final Integer TO = 1;

	private final Integer TYPE_LIST_INSERT = 1;
	private final Integer TYPE_LIST_UPDATE = 2;
	private final Long ZERO = 0L;
	private final String ONE = "1";
	private final String DOT = ".";
	private final String DOWNLINE = "\n";
	private final String TAB = "\t";
	private final String QUERY_ESCAPE_UTILS = "@org.terasoluna.gfw.common.query.QueryEscapeUtils@toContainingCondition";
	private final String COMMA = ",";
	private final String BLANK = "";
	private final String TOTALCOUNT = "TotalCount";
	private final String ICON_EDIT = " glyphicon glyphicon-pencil";

	private final Integer LIST_TYPE = 1;
	private final Integer SINGLE_TYPE = 0;
	private final String FROM_INPUT_TO_OUTPUT = "from_input_to_output";
	private final String FROM_OBJECTDEFINITION_TO_OUTPUT = "from_objectdefinition_to_output";
	private final String FROM_INPUT_TO_OBJECTDEFINITION = "from_input_to_objectdefinition";
	
	private final String REGISTER_NAVIGATE = "REGISTER_NAVIGATE";
	private final String REGISTER_COMFIRM_NAVIGATE = "REGISTER_COMFIRM_NAVIGATE";
	
	private final String MODIFY_NAVIGATE = "MODIFY_NAVIGATE";
	private final String MODIFY_COMFIRM_NAVIGATE = "MODIFY_COMFIRM_NAVIGATE";
	
	private final String SEARCH_NAVIGATE = "SEARCH_NAVIGATE";
	private final String DELETE_NAVIGATE = "DELETE_NAVIGATE";
	
	private final String BACK_REGISTER_COMFIRM_NAVIGATE = "BACK_REGISTER_COMFIRM_NAVIGATE";
	private final String BACK_MODIFY_COMFIRM_NAVIGATE = "BACK_MODIFY_COMFIRM_NAVIGATE";
	
	private final String MODIFY_VIEW = "MODIFY_VIEW";
	private final String MODIFY_SEARCH = "MODIFY_SEARCH";
	
	private final String HEADER_SEARCH_TO_REGISTER = "HEADER_SEARCH_TO_REGISTER";
	private final String HEADER_REGISTER_TO_SEARCH = "HEADER_REGISTER_TO_SEARCH";
	private final String HEADER_MODIFY_TO_SEARCH = "HEADER_MODIFY_TO_SEARCH";
	private final String HEADER_COMPLETE_MODIFY_TO_SEARCH = "HEADER_COMPLETE_MODIFY_TO_SEARCH";
	private final String HEADER_COMPLETE_REGISTER_TO_SEARCH = "HEADER_COMPLETE_REGISTER_TO_SEARCH";
	private final String HEADER_SEARCH_TO_VIEW = "HEADER_SEARCH_TO_VIEW";
	
	private final String SCREEN_ACTION_PARAM_SEARCH_TO_VIEW = "SCREEN_ACTION_PARAM_SEARCH_TO_VIEW";
	private final String SCREEN_ACTION_PARAM_SEARCH_TO_MODIFY = "SCREEN_ACTION_PARAM_SEARCH_TO_MODIFY";
	private final String SCREEN_ACTION_PARAM_VIEW_TO_MODIFY = "SCREEN_ACTION_PARAM_VIEW_TO_MODIFY";
	private final String SCREEN = " screen";
	
	private int idFormularDefinitionTemp = 0;
	private Map<String, ScreenAction> mapNavi = new HashMap<String, ScreenAction>();

	private List<NavigatorComponent> lstNavigatorComponents;
	private List<AssignComponent> lstAssignComponents;
	private List<AssignDetail> lstAssignDetails;
	private List<LoopComponent> listLoopComponent;
	private List<FeedbackComponent> lstFeedbacks;
	private List<MessageParameter> lstMessageParamettersOfFeedback;
	private List<MessageParameter> lstMessageParamettersOfBusinessCheckDetail;
	private List<BusinessCheckComponent> lstBusinessCheckComponents;
	private List<BusinessCheckDetail> lstCheckDetails;
	private List<BusinessDetailContent> lstDetailsContents;
	private List<NavigatorDetail> lstNavigatorDetails;
	private List<ExecutionComponent> lstExecutionComponent;
	private List<ExecutionInputValue> lstExecutionInputValue;
	private List<ExecutionOutputValue> lstExecutionOutputValue;
	private List<IfComponent> lstIfComponents;
	private List<FormulaDefinition> lstFormulaDefinitions;
	private List<FormulaDefinition> lstFormulaDefinitionsForAssign;
	private List<UtilityComponent> lstUtilityComponents;
	private Map<String, String> mNameParameter;
	private Map<String, List<ScreenActionParam>> mScreenActionParam;
	private List<DownloadFileComponent> lstDownloadFileComponents;

	/**
	 * populate new message design
	 *
	 * @return the new MessageDesign object
	 */
	private MessageDesign poplateMessageDesign(String messageString, ScreenDesignDefault screenDesignDefault, Integer messageLevel, String messageType, LanguageDesign languageDesign) {
		MessageDesign messageDesign = new MessageDesign();
		messageDesign.setMessageString(messageString);
		messageDesign.setModuleId(screenDesignDefault.getModuleId());
		messageDesign.setModuleCode(screenDesignDefault.getModuleCode());
		messageDesign.setProjectId(screenDesignDefault.getProjectId());
		messageDesign.setModuleName(screenDesignDefault.getModuleName());
		messageDesign.setMessageLevel(messageLevel);
		messageDesign.setMessageType(messageType);
		messageDesign.setGeneratedStatus(DbDomainConst.MessageGeneratedStatus.AUTO_TRANSLATE);
		messageDesign.setLanguageId(languageDesign.getLanguageId());

		messageDesign.setMessageCode(messageDesign.getAutoMessageCode());
		return messageDesign;
	}

	/**
	 * populate new screen design
	 *
	 * @return the new ScreenDesign object
	 */
	private ScreenDesign populateScreenDesign(ScreenDesignDefault screenDesignDefault, String screenCode, MessageDesign messageDesign, String screenUrlCode, Integer screenPatternType, Integer templateType, Integer xCoordinate, Integer yCoordinate, Integer maxYcoordinate, String screenCodeInForm, Boolean flgConfirmScreen, Boolean flgCompleteScreen, Long tempFunctionId) {
		ScreenDesign screenDesign = new ScreenDesign();
		screenDesign.setModuleId(screenDesignDefault.getModuleId());
		screenDesign.setScreenCode(screenCode);
		screenDesign.setMessageDesign(messageDesign);
		screenDesign.setScreenUrlCode(screenUrlCode);
		screenDesign.setScreenPatternType(screenPatternType);
		screenDesign.setTemplateType(templateType);
		screenDesign.setRemark(screenDesignDefault.getRemark());
		screenDesign.setxCoordinate(xCoordinate);
		if (maxYcoordinate != null && maxYcoordinate != 0) {
			screenDesign.setyCoordinate(maxYcoordinate);
		} else {
			screenDesign.setyCoordinate(yCoordinate);
		}
		if (screenCodeInForm == null) {
			screenDesign.setDesignMode(ScreenDesignConst.DesignMode.DESIGN);
		} else {
			screenDesign.setDesignMode(ScreenDesignConst.DesignMode.PROTOTYPE);
		}
		screenDesign.setDesignStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
		if (flgConfirmScreen) {
			screenDesign.setConfirmationType(screenDesignDefault.getConfirmationType());
		}
		if (flgCompleteScreen) {
			screenDesign.setCompletionType(screenDesignDefault.getCompletionType());
		}
		screenDesign.setProjectId(screenDesignDefault.getProjectId());
		screenDesign.setFunctionDesignId(tempFunctionId);

		screenDesign.setCreatedBy(screenDesignDefault.getCreatedBy());
		screenDesign.setCreatedDate(screenDesignDefault.getCreatedDate());
		screenDesign.setUpdatedBy(screenDesignDefault.getCreatedBy());
		screenDesign.setUpdatedDate(screenDesignDefault.getCreatedDate());

		return screenDesign;
	}

	/**
	 * populate new screen form
	 *
	 * @return the new ScreenForm object
	 */
	private ScreenForm populateScreenForm(String formCode, Integer enctypeType, Integer methodType, Integer formType, Long screenId) {
		ScreenForm screenForm = new ScreenForm();
		screenForm.setFormCode(FunctionCommon.convertNameToCode(formCode));
		screenForm.setEnctypeType(enctypeType);
		screenForm.setMethodType(methodType);
		screenForm.setFormType(formType);
		Integer currentFormNo = screenFormRepository.getMaxFormSeqNoByScreenId(screenId);
		if (null == currentFormNo) {
			currentFormNo = -1;
		}
		screenForm.setFormSeqNo(++currentFormNo);
		screenForm.setScreenId(screenId);
		screenForm.setCreatedBy(accountId);
		screenForm.setCreatedDate(systemDate);
		screenForm.setUpdatedBy(accountId);
		screenForm.setUpdatedDate(systemDate);
		return screenForm;
	}

	/**
	 * populate new screen area
	 *
	 * @return the new ScreenArea object
	 */
	private ScreenArea populateScreenArea(ScreenForm screenForm, MessageDesign messageDesign, Integer areaType, Integer areaPatternType, String areaCode, Integer totalElement, String colWidthUnit, Integer totalCol, String tblWidthUnit, Integer tblHeaderRow, Integer alignPositionType, String screenTypeName, ModuleTableMapping table) {
		ScreenArea screenArea = new ScreenArea();
		screenArea.setScreenFormId(screenForm.getScreenFormId());
		screenArea.setScreenId(screenForm.getScreenId());
		screenArea.setMessageDesign(messageDesign);
		screenArea.setAreaType(areaType);
		screenArea.setAreaPatternType(areaPatternType);
		screenArea.setAreaCode(areaCode);
		if (1 != areaType) {
			if (totalElement % 2 == 0) {
				screenArea.setTotalElement(totalElement * 2);
			} else {
				screenArea.setTotalElement(++totalElement * 2);
			}
		} else {
			screenArea.setTotalElement(totalElement * 2);
		}
		screenArea.setColWidthUnit(colWidthUnit);
		screenArea.setTotalCol(totalCol);
		screenArea.setTblWidthUnit(tblWidthUnit);
		screenArea.setTblHeaderRow(tblHeaderRow);
		screenArea.setAlignPositionType(alignPositionType);
		Integer currentArea = screenAreaRepository.getMaxScreenAreaSeqNoByScreenFormId(screenForm.getScreenFormId());
		if (null == currentArea) {
			currentArea = -1;
		}
		screenArea.setAreaSeqNo(++currentArea);
		screenArea.setCreatedBy(accountId);
		screenArea.setCreatedDate(systemDate);
		screenArea.setUpdatedBy(accountId);
		screenArea.setUpdatedDate(systemDate);
		if (ScreenDesignConst.REGISTER_SCREEN.equals(screenTypeName) || ScreenDesignConst.MODIFY_SCREEN.equals(screenTypeName)) {
			if (ScreenDesignConst.TableMappingType.SINGLE.equals(areaType)) {
				screenArea.setAreaCustomType(ScreenDesignConst.AreaCustomType.NORMAL);
			} else {
				screenArea.setAreaTypeAction(ScreenDesignConst.AreaTypeAction.ADD_REMOVE);
				screenArea.setFixedRow(ScreenDesignConst.FixedRow.NO);
			}
		} else {
			if (ScreenDesignConst.AreaType.SINGLE.equals(areaType)) {
				screenArea.setAreaCustomType(ScreenDesignConst.AreaCustomType.NORMAL);
			} else {
				if (ScreenDesignConst.SEARCH_SCREEN.equals(screenTypeName) && ScreenDesignConst.AreaType.LIST.equals(areaType)) {
					screenArea.setAreaTypeAction(ScreenDesignConst.AreaTypeAction.PAGEABLE);
				} else {
					screenArea.setAreaTypeAction(ScreenDesignConst.AreaTypeAction.VIEW);
				}
			}
		}
		screenArea.setObjectMappingType(ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_TABLE_DESIGN);
		if(table != null) {
			screenArea.setObjectMappingId(table.getTblDesignId());
		}
		return screenArea;
	}

	/**
	 * populate new screen action
	 *
	 * @return the new ScreenAction object
	 */
	private ScreenAction populateScreenAction(Integer actionType, String connectionMsg, Long fromScreenId, Long toScreenId, Integer submitMethodType) {
		ScreenAction screenAction = new ScreenAction();
		screenAction.setActionType(actionType);
		screenAction.setFromScreenId(fromScreenId);
		screenAction.setConnectionMsg(connectionMsg);
		screenAction.setToScreenId(toScreenId);
		screenAction.setSubmitMethodType(submitMethodType);
		screenAction.setCreatedBy(accountId);
		screenAction.setCreatedDate(systemDate);
		screenAction.setUpdatedBy(accountId);
		screenAction.setUpdatedDate(systemDate);
		return screenAction;
	}
	
	private ScreenTransition populateScreenTransition(String code, Long fromScreen, Long toScreen, Long moduleId, Boolean flgViewDeleteTrans) {
		ScreenTransition screenTransition = new ScreenTransition();
		screenTransition.setTransitionName(code);
		screenTransition.setTransitionCode(code);
		if (fromScreen != null) {
			screenTransition.setFromScreen(fromScreen.toString());
		}
		if (toScreen != null) {
			screenTransition.setToScreen(toScreen.toString());
		}
		screenTransition.setModuleId(moduleId);
		screenTransition.setType(ScreenDesignConst.ScreenTransitionType.SCREEN_TO_SCREEN);
		screenTransition.setFlgViewDeleteTrans(flgViewDeleteTrans);
		
		return screenTransition;
	}

	/**
	 * populate new screen item sequence
	 *
	 * @return the new ScreenItemSequence object
	 */
	private ScreenItemSequence populateScreenItemSequence(Integer itemGroupType, Integer itemSeqNo, Long screenAreaId) {
		ScreenItemSequence screenItemSequence = new ScreenItemSequence();
		screenItemSequence.setItemGroupType(itemGroupType);
		screenItemSequence.setItemSeqNo(itemSeqNo);
		screenItemSequence.setScreenAreaId(screenAreaId);
		screenItemSequence.setCreatedBy(accountId);
		screenItemSequence.setCreatedDate(systemDate);
		screenItemSequence.setUpdatedBy(accountId);
		screenItemSequence.setUpdatedDate(systemDate);
		return screenItemSequence;
	}

	/**
	 * populate new screen group item
	 *
	 * @return the new ScreenGroupItem object
	 */
	private ScreenGroupItem populateScreenGroupItem(Long screenAreaId, String groupName, Integer groupType, Integer maxItemSeqNo) {
		ScreenGroupItem screenGroupItem = new ScreenGroupItem();
		screenGroupItem.setScreenAreaId(screenAreaId);
		screenGroupItem.setGroupName(groupName);
		screenGroupItem.setGroupType(groupType);
		screenGroupItem.setItemSeqNo(maxItemSeqNo);
		/*
		 * Integer currentGroupSeqNo = screenGroupItemRepository .getMaxGroupItemSeqNoByScreenAreaId(screenAreaId); if(null == currentGroupSeqNo) { currentGroupSeqNo = -1; }
		 */

		Integer currentGroupSeqNo = getItemSeqNo(screenAreaId, mapScreenAreaGroupItemSeqNo);

		screenGroupItem.setGroupSeqNo(currentGroupSeqNo);
		screenGroupItem.setCreatedBy(accountId);
		screenGroupItem.setCreatedDate(systemDate);
		screenGroupItem.setUpdatedBy(accountId);
		screenGroupItem.setUpdatedDate(systemDate);
		return screenGroupItem;
	}

	/**
	 * populate new screen item
	 *
	 * @return the new ScreenItem object
	 */
	private ScreenItem populateScreenItem(Integer itemSeqNo, Long screenId, String itemCode, MessageDesign messageDesign, Long screenAreaId, Long groupItemId, Integer logicalDataType, Long screenActionId, Integer itemType, Integer enableConfirm, MessageDesign messageConfirm, Integer buttonType) {
		ScreenItem screenItem = new ScreenItem();
		screenItem = new ScreenItem();
		screenItem.setItemSeqNo(itemSeqNo);
		screenItem.setScreenId(screenId);
		screenItem.setItemCode(itemCode);
		screenItem.setMessageDesign(messageDesign);
		screenItem.setScreenAreaId(screenAreaId);
		screenItem.setPhysicalDataType(1);
		screenItem.setLogicalDataType(logicalDataType);
		screenItem.setGroupItemId(groupItemId);
		screenItem.setScreenActionId(screenActionId);
		if(itemType != null) {
			screenItem.setItemType(itemType);
		} else {
			screenItem.setItemType(ITEM_TYPE_NORMAL);
		}
		screenItem.setCreatedBy(accountId);
		screenItem.setCreatedDate(systemDate);
		screenItem.setUpdatedBy(accountId);
		screenItem.setUpdatedDate(systemDate);
		if (DbDomainConst.LogicDataType.BUTTON.equals(logicalDataType)) {
			screenItem.setShowLabel(1);
		}
		screenItem.setEnableConfirm(enableConfirm);
		screenItem.setMessageConfirm(messageConfirm);
		screenItem.setButtonType(buttonType);
		return screenItem;
	}

	/**
	 * populate new screen item validation
	 *
	 * @return the new ScreenItemValidation object
	 */
	private ScreenItemValidation populateScreenItemValidation(Integer maxlength, Integer requireFlg, String minVal, String maxVal, String fmtCode, Boolean flagDefaultValidaion, Integer baseType) {
		ScreenItemValidation screenItemValidation = new ScreenItemValidation();
		screenItemValidation.setMaxlength(maxlength);
		if (null != requireFlg && requireFlg.intValue() == 1) {
			screenItemValidation.setMandatoryFlg(requireFlg);
		}
		if(flagDefaultValidaion) {
			if(StringUtils.isNotBlank(minVal)) {
				screenItemValidation.setMinVal(minVal);
			} else {
				List<String> lstMinMax = defaultMinMax(baseType);
				if(FunctionCommon.isNotEmpty(lstMinMax)) {
					screenItemValidation.setMinVal(lstMinMax.get(0));
				}
			}
			if(StringUtils.isNotBlank(maxVal)) {
				screenItemValidation.setMaxVal(maxVal);
			} else {
				List<String> lstMinMax = defaultMinMax(baseType);
				if (FunctionCommon.isNotEmpty(lstMinMax) && lstMinMax.size() > 1) {
					screenItemValidation.setMaxVal(lstMinMax.get(1));
				}
			}
		} else {
			screenItemValidation.setMinVal(minVal);
			screenItemValidation.setMaxVal(maxVal);
		}
		screenItemValidation.setFmtCode(fmtCode);
		screenItemValidation.setCreatedBy(accountId);
		screenItemValidation.setCreatedDate(systemDate);
		screenItemValidation.setUpdatedBy(accountId);
		screenItemValidation.setUpdatedDate(systemDate);
		return screenItemValidation;
	}

	/**
	 * @param screenDesignDefault
	 * @param languageDesign
	 */
	private void populateListModuleTableMapping(ScreenDesignDefault screenDesignDefault,LanguageDesign languageDesign) {
		if (ArrayUtils.isNotEmpty(screenDesignDefault.getModuleTableMappings())) {
			List<Long> lstTableId = new ArrayList<Long>();

			List<Long> listUserCodelistId = new ArrayList<Long>();
			List<Long> listSystemCodelistId = new ArrayList<Long>();
			List<Long> listSQLDesignId = new ArrayList<Long>();
			List<Long> listAutocompleteId = new ArrayList<Long>();
			ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings()[0];

			for (ModuleTableMapping moduleTableMapping : screenDesignDefault.getModuleTableMappings()) {
				lstTableId.add(moduleTableMapping.getTblDesignId());
			}

			List<TableDesignDetails> lstAllColumns = tableDesignDetailRepository.findByListTableDesignId(screenDesignDefault.getProjectId(), lstTableId);
			List<MessageDesign> listMessageDesign = new ArrayList<MessageDesign>();

			allForeignKeyInProject = tableDesignForeignKeyRepository.getAllForeignKey(workingProjectId);

			for (int index = 0; index < screenDesignDefault.getModuleTableMappings().length; index++) {
				ModuleTableMapping moduleTableMapping = screenDesignDefault.getModuleTableMappings()[index];
				List<TableDesignDetails> columnsDisplay = new ArrayList<TableDesignDetails>();
				List<TableDesignDetails> allColumns = new ArrayList<TableDesignDetails>();
				List<TableDesignDetails> lstPkOfTable = new ArrayList<TableDesignDetails>();
				MessageDesign messageDesignTable = poplateMessageDesign(moduleTableMapping.getTblDesignName(), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
				listMessageDesign.add(messageDesignTable);
				moduleTableMapping.setMessageDesign(messageDesignTable);

				lstPkOfTable = findPKOfTable(moduleTableMapping, lstAllColumns);

				for (TableDesignDetails column : lstAllColumns) {
					if (moduleTableMapping.getTblDesignId().equals(column.getTableDesignId())) {
						// insert label for screen item
						MessageDesign messageDesign = poplateMessageDesign(column.getName(), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
						listMessageDesign.add(messageDesign);
						column.setMessageDesign(messageDesign);
						if (column.getDisplayType() != null && DbDomainConst.DisplayType.USED.equals(column.getDisplayType())) {
							columnsDisplay.add(column);
						} else {
							// if PK is not set USED, still add to list column display
							for (TableDesignDetails pk : lstPkOfTable) {
								if (column.getTableDesignId().equals(pk.getTableDesignId()) && column.getColumnId().equals(pk.getColumnId())) {
									column.setIsPkHidden(true);
									columnsDisplay.add(column);
								}
							}
							// if column "updated_date" is not set USED, still add to list column display to check concurrency
							if(UPDATED_DATE.equalsIgnoreCase(column.getCode()) && firstTable.getTblDesignId().equals(moduleTableMapping.getTblDesignId())) {
								columnsDisplay.add(column);
							}
						}

						allColumns.add(column);

						if (DbDomainConst.DatasourceType.USER_DEFINE.equals(column.getDatasourceType())) {
							if (!listUserCodelistId.contains(column.getDatasourceId())) {
								listUserCodelistId.add(column.getDatasourceId());
							}
						} else if (DbDomainConst.DatasourceType.CODELIST.equals(column.getDatasourceType())) {
							if (!listSystemCodelistId.contains(column.getDatasourceId())) {
								listSystemCodelistId.add(column.getDatasourceId());
							}
						} else if (DbDomainConst.DatasourceType.SQL_BUILDER_IS_AUTOCOMPLETE.equals(column.getDatasourceType())) {
							if (!listAutocompleteId.contains(column.getDatasourceId())) {
								listAutocompleteId.add(column.getDatasourceId());
							}
						} else if (DbDomainConst.DatasourceType.SQL_BUILDER.equals(column.getDatasourceType())) {
							if (!listSQLDesignId.contains(column.getDatasourceId())) {
								listSQLDesignId.add(column.getDatasourceId());
							}
						}
					}
				}

				if (FunctionCommon.isNotEmpty(columnsDisplay)) {
					// remove column is FK
					List<Long> tableIdPrevious = new ArrayList<Long>();
					for (int i = index; i >= 0; i--) {
						tableIdPrevious.add(screenDesignDefault.getModuleTableMappings()[i].getTblDesignId());
					}
					List<TableDesignDetails> lstColumn = new ArrayList<TableDesignDetails>();
					for (TableDesignDetails column : columnsDisplay) {
						if (DbDomainConst.YesNoFlg.YES.equals(column.isKey(DbDomainConst.TblDesignKeyType.FK))) {
							for (TableDesignForeignKey fk : allForeignKeyInProject) {
								List<Long> columnsOfTargetTable = new ArrayList<Long>();
								for (TableDesignDetails columnTarget : lstAllColumns) {
									if (fk.getToTableId().equals(columnTarget.getTableDesignId())) {
										columnsOfTargetTable.add(columnTarget.getColumnId());
									}
								}

								if (fk.getFromTableId().equals(moduleTableMapping.getTblDesignId()) && tableIdPrevious.contains(fk.getToTableId()) && fk.getFromColumnId().equals(column.getColumnId()) && columnsOfTargetTable.contains(fk.getToColumnId())) {
									column.setHaveFkFlag(true);
								}
							}
							if (column.getHaveFkFlag() == null || !column.getHaveFkFlag()) {
								lstColumn.add(column);
							}
						} else {
							lstColumn.add(column);
						}
					}
					moduleTableMapping.setListTableDesignDetail(lstColumn);
				}
				//				MessageDesign messageDesign = poplateMessageDesign(moduleTableMapping.getTblDesignName() + SPACE + "information", screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
				//				listMessageDesign.add(messageDesign);
				//				moduleTableMapping.setMessageDesign(messageDesign);
				if (FunctionCommon.isNotEmpty(allColumns)) {
					moduleTableMapping.setListAllColumns(allColumns);
				}
			}

			if (FunctionCommon.isNotEmpty(listMessageDesign)) {
				List<MessageDesign> filterMess = filterMessageCanUse(listMessageDesign, screenDesignDefault.getListMessRegisted(), Arrays.asList(screenDesignDefault.getModuleTableMappings()), null);
				messageDesignService.registerMessageDesign(filterMess, false);
			}
			// get all user define code list
			if (FunctionCommon.isNotEmpty(listUserCodelistId)) {
				userDefineCodelistDetails = userDefineCodelistRepository.getUserDefineCodeListDetailsByListIds(listUserCodelistId);
			} else {
				userDefineCodelistDetails = new ArrayList<UserDefineCodelistDetails>();
			}
			// get all code list
			if (FunctionCommon.isNotEmpty(listSystemCodelistId)) {
				lstCodeListId = codeListRepository.checkCodeListExists(listSystemCodelistId);
			}
			// get all sql design
			if (FunctionCommon.isNotEmpty(listSQLDesignId)) {
				lstSqlDesignId = sqlDesignRepository.checkSqlDesignExists(listSQLDesignId);
			}
			// get all autocomplete design
			if (FunctionCommon.isNotEmpty(listAutocompleteId)) {
				lstAutocompleteId = autocompleteDesignRepository.checkAutocompleteDesignExists(listAutocompleteId);
			}
		}
	}

	private List<TableDesignDetails> findPKOfTable(ModuleTableMapping moduleTableMapping, List<TableDesignDetails> lstAllColumns) {
		List<TableDesignKeyItem> keysOfTable = new ArrayList<TableDesignKeyItem>();
		List<TableDesignDetails> lstPkOfTable = new ArrayList<TableDesignDetails>();
		List<TableDesignDetails> lstColumnsOfTable = new ArrayList<TableDesignDetails>();
		if(lstAllColumns == null || lstAllColumns.size() == 0) {
			lstAllColumns = moduleTableMapping.getListAllColumns();
		}
		if(FunctionCommon.isNotEmpty(lstAllColumns)) {
			for(TableDesignDetails col : lstAllColumns) {
				if(moduleTableMapping.getTblDesignId().equals(col.getTableDesignId())) {
					lstColumnsOfTable.add(col);
				}
			}
		}

		Set<Long> primaryKeys = new HashSet<Long>();
		Set<Long> uniqueKeys = new HashSet<Long>();
		for (TableDesignKeyItem key : allKeys) {
			if (key.getTableDesignId() != null && key.getTableDesignId().equals(moduleTableMapping.getTblDesignId())) {
				keysOfTable.add(key);
			}
		}
		for (TableDesignKeyItem key : keysOfTable) {
			if (key.getType().equals(DbDomainConst.TblDesignKeyType.PK)) {
				primaryKeys.add(key.getColumnId());
			}
			if (key.getType().equals(DbDomainConst.TblDesignKeyType.UNIQUE)) {
				uniqueKeys.add(key.getColumnId());
			}
		}
		if (primaryKeys.size() > 0) {
			for (Long key : primaryKeys) {
				for (TableDesignDetails item : lstColumnsOfTable) {
					if (item.getColumnId() != null && key.equals(item.getColumnId())) {
						lstPkOfTable.add(item);
					}
				}
			}
		} else if (uniqueKeys.size() > 0) {
			for (Long key : uniqueKeys) {
				for (TableDesignDetails item : lstColumnsOfTable) {
					if (item.getColumnId() != null && key.equals(item.getColumnId())) {
						lstPkOfTable.add(item);
					}
				}
			}
		} else {
			lstPkOfTable.add(lstColumnsOfTable.get(0));
		}
		return lstPkOfTable;
	}

	/**
	 * generate/ register a new screen
	 *
	 * @return ScreenForm of new screen
	 */
	public ScreenForm generateScreenItemDefault(ScreenDesignDefault screenDesignDefault, ScreenDesign screenDesign, Long screenDesignSearchId, Long screenDesignViewId, Long modifyScreenId, LanguageDesign languageDesign, Map<String, Object> messageCommons, List<ScreenItem> lstScreenItems, List<ScreenItem> lstScreenItemSearchResults, Map<Long, List<ScreenItem>> mapScreenIdAndUserdefineCL, List<ScreenTransition> lstScreenTransitions, Map<ScreenTransition, ScreenItem> mapTransitionAndItem) {
		MessageDesign messageDesign;
		ScreenArea screenArea = new ScreenArea();
		ScreenAction screenAction = null;
		List<ScreenArea> allScreenArea = new ArrayList<ScreenArea>();
		List<ScreenItem> lstItemConfigUserDefineCL = new ArrayList<ScreenItem>();

		ScreenItemValidation screenItemValidation;
		ScreenForm screenForm = new ScreenForm();

		List<MessageDesign> listMessageDesign = new ArrayList<MessageDesign>();
		List<ScreenGroupItem> listOfScreenItemGroupForBatch = new ArrayList<ScreenGroupItem>();

		List<ScreenGroupItem> listOfScreenItemGroupForBatchOne = new ArrayList<ScreenGroupItem>();
		List<ScreenGroupItem> listOfScreenItemGroupForBatchTwo = new ArrayList<ScreenGroupItem>();

		// list screen item has not depend
		List<ScreenItem> listOfScreenItemWithOutDepend = new ArrayList<ScreenItem>();

		// list screen item has depend information for lstScreenItems
		List<ScreenItem> listOfScreenItemWithDependOne = new ArrayList<ScreenItem>();

		// list screen item has depend information for lstScreenItemSearchResults
		List<ScreenItem> listOfScreenItemWithDependTwo = new ArrayList<ScreenItem>();

		List<ScreenItem> lstItemHidden = new ArrayList<ScreenItem>();
		String screenTypeName = screenDesign.getScreenTypeName();
		
		Project project = screenDesignDefault.getProject();
		AccountProfile accountProfile = systemService.getDefaultProfile();
		String dataType = String.valueOf(project.getDbType());
		Integer maxLengOfCode = accountProfile.getSqlCodeMaxLengthByDbType(dataType);
		
		GenerateUniqueKey generateUniqueKey = new GenerateUniqueKey(accountProfile.getNameMaxLength(), maxLengOfCode);
		
		// loop screen list for generate screen items
		if (!DbDomainConst.ScreenPatternType.SEARCH.equals(screenDesign.getScreenPatternType())) {
			int flag = 0;
			if (screenDesignDefault.getModuleTableMappings() != null && screenDesignDefault.getModuleTableMappings().length > 0) {
				// loop table mapping list for generate screen items of
				// register/view/modify/confirm_register/complete_register/confirm_modify/complete_modify
				for (ModuleTableMapping moduleTableMapping : screenDesignDefault.getModuleTableMappings()) {
					Integer areaPatternType = moduleTableMapping.getAreaPatternType();
					Integer newAreaPatternType = null;
					List<TableDesignDetails> listOfColumn = moduleTableMapping.getListTableDesignDetail();

					if (FunctionCommon.isNotEmpty(listOfColumn)) {
						Integer size = 0;
						for (TableDesignDetails col : listOfColumn) {
							if (DbDomainConst.DisplayType.USED.equals(col.getDisplayType())) {
								size++;
							}
						}
						if (flag == 0) {
							// insert screen form
							screenForm = populateScreenForm(SCREEN_FORM + screenDesignDefault.getModuleCode(), 1, 3, 1, screenDesign.getScreenId());
							screenFormRepository.insertScreenForm(screenForm);
							screenDesignDefault.getMapFormOfScreen().put(screenDesign.getScreenId(), screenForm);
							flag++;
						}
						String colWidthUnit = "";
						Integer totalCol = 0;
						if (moduleTableMapping.getTableMappingType().equals(1)) {
							colWidthUnit = String.valueOf(100 / size) + "%";
							totalCol = size;
						} else {
							colWidthUnit = "25%";
							totalCol = 4;
						}
						if (null == areaPatternType) {
							if (DbDomainConst.ScreenPatternType.REGISTER.equals(screenDesign.getScreenPatternType())) {
								newAreaPatternType = 1;
							} else if (DbDomainConst.ScreenPatternType.MODIFY.equals(screenDesign.getScreenPatternType())) {
								newAreaPatternType = 2;
							} else if (DbDomainConst.ScreenPatternType.VIEW.equals(screenDesign.getScreenPatternType())) {
								newAreaPatternType = 3;
							}
						} else {
							newAreaPatternType = areaPatternType;
						}
						// insert screen area for corresponding table
						String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, StringUtils.capitalize(screenDesignDefault.getModuleCode()) + StringUtils.capitalize(moduleTableMapping.getTblDesignCode()), screenDesignDefault.getSuffix());
						screenArea = populateScreenArea(screenForm, moduleTableMapping.getMessageDesign(), moduleTableMapping.getTableMappingType(), newAreaPatternType, areaCode, size, colWidthUnit, totalCol, "100%", 1, 1, screenTypeName, moduleTableMapping);
						allScreenArea.add(screenArea);
						screenAreaRepository.insertScreenArea(screenArea);
						Integer maxItemSeqNo = -1;

						// loop screen items
						for (TableDesignDetails item : listOfColumn) {
							// create hidden item in modify screen and view screen if column be set is not used
							if (item.getIsPkHidden() != null && item.getIsPkHidden()) {
								if(ScreenDesignConst.MODIFY_SCREEN.equals(screenDesign.getScreenTypeName()) || ScreenDesignConst.VIEW_SCREEN.equals(screenDesign.getScreenTypeName())
										|| ScreenDesignConst.CONFIRM_REGISTER_SCREEN.equals(screenDesign.getScreenTypeName())
										|| ScreenDesignConst.CONFIRM_MODIFY_SCREEN.equals(screenDesign.getScreenTypeName())) {
									maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
									ScreenItem screenItem = GenerateScreenContruct.populateScreenItemHidden(screenDesignDefault, maxItemSeqNo, screenDesign.getScreenId(), item.getCode(), item.getName(), item.getMessageDesign(), screenArea.getScreenAreaId(), screenArea.getAreaCode(), null, DbDomainConst.LogicDataType.LABEL_DYNAMIC, null, ITEM_TYPE_HIDDEN, item.getBaseType(), item.getTableDesignId(), item.getColumnId(), item.getKeyType(), true);
									lstItemHidden.add(screenItem);
								}
							} else {
								if(UPDATED_DATE.equalsIgnoreCase(item.getCode()) && !DbDomainConst.DisplayType.USED.equals(item.getDisplayType())) {
									if (ScreenDesignConst.MODIFY_SCREEN.equals(screenDesign.getScreenTypeName())) {
										maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
										ScreenItem screenItem = GenerateScreenContruct.populateScreenItemHidden(screenDesignDefault, maxItemSeqNo, screenDesign.getScreenId(), item.getCode(), item.getName(), item.getMessageDesign(), screenArea.getScreenAreaId(), screenArea.getAreaCode(), null, DbDomainConst.LogicDataType.LABEL_DYNAMIC, null, ITEM_TYPE_HIDDEN, item.getBaseType(), item.getTableDesignId(), item.getColumnId(), item.getKeyType(), false);
										lstItemHidden.add(screenItem);
									}
								} else {
									maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
									ScreenGroupItem screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "group" + item.getName(), 1, maxItemSeqNo);
									ScreenItem screenItem = populateScreenItem(maxItemSeqNo, screenDesign.getScreenId(), "lbl" + item.getName(), item.getMessageDesign(), screenArea.getScreenAreaId(), screenGroupItem.getGroupItemId(), DbDomainConst.LogicDataType.LABEL, null, ITEM_TYPE_NORMAL, null, null, null);
									if (!screenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.VIEW)) {
										// screenItemRepository.insertScreenItem(screenItem);
										if (screenItem.getLogicalDataType() != null && !screenItem.getLogicalDataType().equals(5) && !screenItem.getLogicalDataType().equals(6)
												&& !screenItem.getLogicalDataType().equals(7)) {
											screenItemValidation = populateScreenItemValidation(item.getMaxlength(), item.getIsMandatory(), item.getMinVal(), item.getMaxVal(), item.getFmtCode(), false, item.getBaseType());
										} else {
											screenItemValidation = populateScreenItemValidation(null, item.getIsMandatory(), "", "", "", false, item.getBaseType());
										}
										// screenItemValidation.setScreenItemId(screenItem.getScreenItemId());
										screenItem.setScreenItemValidation(screenItemValidation);
										// insert screen item validation for corresponding screen item
										// screenItemValidationRepository.insertScreenItemValidation(screenItem.getScreenItemValidation());
									}
									listOfScreenItemGroupForBatch.add(screenGroupItem);
									listOfScreenItemWithOutDepend.add(screenItem);
									
									// populate screen item information
									screenItem = new ScreenItem();
									maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
									screenItem.setItemSeqNo(maxItemSeqNo);
									
									screenItem.setScreenId(screenDesign.getScreenId());
									screenItem.setItemCode(item.getCode());
									screenItem.setItemName(item.getName());
									screenItem.setTblDesignId(item.getTableDesignId());
									screenItem.setColumnId(item.getColumnId());
									// in case, auto generate screen by default
									if (null == moduleTableMapping.getAreaPatternType()) {
										if (!DbDomainConst.ScreenPatternType.VIEW.equals(screenDesign.getScreenPatternType())) {
											Integer itemType = item.getItemType() == null ? null : item.getItemType().intValue();
											screenItem.setLogicalDataType(itemType);
											screenItem.setPhysicalDataType(item.getBaseType());
										} else {
											// in case, current screen is view screen then all screen items are TEXT
											screenItem.setPhysicalDataType(item.getBaseType());
											screenItem.setLogicalDataType(DbDomainConst.LogicDataType.LABEL_DYNAMIC);
										}
									} else {
										if (!DbDomainConst.AreaPatternType.VIEW.equals(moduleTableMapping.getAreaPatternType())) {
											if (!DbDomainConst.ScreenPatternType.VIEW.equals(screenDesign.getScreenPatternType())) {
												Integer itemType = item.getItemType() == null ? null : item.getItemType().intValue();
												screenItem.setLogicalDataType(itemType);
												screenItem.setPhysicalDataType(item.getBaseType());
											} else {
												screenItem.setPhysicalDataType(item.getBaseType());
												screenItem.setLogicalDataType(DbDomainConst.LogicDataType.LABEL_DYNAMIC);
											}
										} else {
											// in case, current screen is view screen  then all screen items are TEXT
											screenItem.setPhysicalDataType(item.getBaseType());
											screenItem.setLogicalDataType(DbDomainConst.LogicDataType.LABEL_DYNAMIC);
										}
									}
									
									screenItem.setColSpan(1);
									screenItem.setRowSpan(1);
									List<UserDefineCodelistDetails> lstUserCLofItem = new ArrayList<UserDefineCodelistDetails>();
									if (DbDomainConst.DatasourceType.USER_DEFINE.equals(item.getDatasourceType())) {
										for (UserDefineCodelistDetails udd : userDefineCodelistDetails) {
											if (udd.getCodelistId().equals(item.getDatasourceId())) {
												lstUserCLofItem.add(udd);
											}
										}
										if (lstUserCLofItem.size() > 0) {
											screenItem.setCodelistType(ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_CUSTOM);
											screenItem.setUserDefineCodelistId(item.getDatasourceId());
											screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_CODELIST);
										}
										lstItemConfigUserDefineCL.add(screenItem);
									} else if (DbDomainConst.DatasourceType.CODELIST.equals(item.getDatasourceType())) {
										if (FunctionCommon.checkExists(lstCodeListId, item.getDatasourceId())) {
											screenItem.setCodelistType(ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_SYSTEM);
											screenItem.setCodelistId(item.getDatasourceId());
											screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_CODELIST);
										}
									} else if (DbDomainConst.DatasourceType.SQL_BUILDER_IS_AUTOCOMPLETE.equals(item.getDatasourceType())) {
										if (FunctionCommon.checkExists(lstAutocompleteId, item.getDatasourceId())) {
											screenItem.setAutocompleteId(item.getDatasourceId());
											screenItem.setSqlDesignId(item.getDatasourceId());
											screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_BLOGIC_DEFINE);
											// set logical if have config autocomplete
											if (!DbDomainConst.ScreenPatternType.VIEW.equals(screenDesign.getScreenPatternType())) {
												screenItem.setLogicalDataType(DbDomainConst.LogicDataType.AUTOCOMPLETE);
											}
										}
									} else if (DbDomainConst.DatasourceType.SQL_BUILDER.equals(item.getDatasourceType())) {
										if (FunctionCommon.checkExists(lstSqlDesignId, item.getDatasourceId())) {
											screenItem.setSqlDesignId(item.getDatasourceId());
											screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_BLOGIC_DEFINE);
										}
									}
									
									screenItem.setArrayFlg(0);
									screenItem.setKeyType(item.getKeyType());
									screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "group" + item.getName(), 1, maxItemSeqNo);
									listOfScreenItemGroupForBatchOne.add(screenGroupItem);
									
									screenItem.setScreenAreaId(screenArea.getScreenAreaId());
									screenItem.setScreenArea(screenArea);
									screenItem.setAreaCode(screenArea.getAreaCode());
									screenItem.setGroupItemId(screenGroupItem.getGroupItemId());
									String messCode = item.getMessageDesign() == null ? null : item.getMessageDesign().getMessageCode();
									screenItem.setMessageDesign(item.getMessageDesign());
									screenItem.setMessageCode(messCode);
									// insert screen item
									screenItem.setCreatedBy(accountId);
									screenItem.setCreatedDate(systemDate);
									screenItem.setUpdatedBy(accountId);
									screenItem.setUpdatedDate(systemDate);
									screenItem.setItemType(ITEM_TYPE_NORMAL);
									screenItem.setDisplayFromTo(ScreenDesignConst.FromTo.NORMAL);
									if(ScreenDesignConst.REGISTER_SCREEN.equals(screenDesign.getScreenTypeName()) || ScreenDesignConst.MODIFY_SCREEN.equals(screenDesign.getScreenTypeName())) {
										if (!DbDomainConst.DefaultType.FUNCTION.equals(item.getDefaultType())) {
											screenItem.setDefaultValue(item.getDefaultValue());
										}
									}
									
									// set screen item code list
									if (lstUserCLofItem.size() > 0) {
										List<ScreenItemCodelist> lstScreenItemCodelist = new ArrayList<ScreenItemCodelist>();
										for (UserDefineCodelistDetails udd : lstUserCLofItem) {
											ScreenItemCodelist screenItemCodelist = GenerateScreenContruct.populateScreenItemCodeList(screenDesignDefault, screenItem, udd);
											lstScreenItemCodelist.add(screenItemCodelist);
										}
										screenItem.setListScreenItemCodelists(lstScreenItemCodelist);
									}
									
									if (!screenDesign.getScreenPatternType().equals(DbDomainConst.ScreenPatternType.VIEW)) {
										// set validation of screen item
										if(DbDomainConst.DisplayType.USED.equals(item.getDisplayType())){
											if (screenItem.getLogicalDataType() != null && !screenItem.getLogicalDataType().equals(5) && !screenItem.getLogicalDataType().equals(6)
													&& !screenItem.getLogicalDataType().equals(7)) {
												screenItemValidation = populateScreenItemValidation(item.getMaxlength(), item.getIsMandatory(), item.getMinVal(), item.getMaxVal(), item.getFmtCode(), true, item.getBaseType());
											} else {
												screenItemValidation = populateScreenItemValidation(null, item.getIsMandatory(), "", "", "", false, item.getBaseType());
											}
											screenItem.setScreenItemValidation(screenItemValidation);
										}
									} else {
										// in view screen : set default datasource type if item link_dynamic or label_dynamic don't have config datasource
										if (DbDomainConst.DisplayType.USED.equals(item.getDisplayType())) {
											if (DbDomainConst.LogicDataType.LINK_DYNAMIC.equals(screenItem.getLogicalDataType()) || DbDomainConst.LogicDataType.LABEL_DYNAMIC.equals(screenItem.getLogicalDataType())) {
												if(screenItem.getDataSourceType() == null) {
													screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_BLOGIC_DEFINE);
												}
											}
										}
									}
									
									listOfScreenItemWithDependOne.add(screenItem);
								}
							}
						}
						
						// special case : If is Confirm screen or Complete screen, add item hidden
						if(ScreenDesignConst.CONFIRM_REGISTER_SCREEN.equals(screenDesign.getScreenTypeName()) || ScreenDesignConst.CONFIRM_MODIFY_SCREEN.equals(screenDesign.getScreenTypeName())) {
							ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings() != null ? screenDesignDefault.getModuleTableMappings()[0] : null;
							Long fkColumnId = 0L;
							if (firstTable != null && !firstTable.getTblDesignId().equals(moduleTableMapping.getTblDesignId())) {
								for (TableDesignForeignKey fk : allForeignKeyInProject) {
									if (fk.getFromTableId().equals(moduleTableMapping.getTblDesignId()) && fk.getToTableId().equals(firstTable.getTblDesignId())) {
										fkColumnId = fk.getFromColumnId();
										break;
									}
								}
							}
							for (TableDesignDetails item : moduleTableMapping.getListTableDesignDetail()) {
								if (item.getIsPkHidden() == null || !item.getIsPkHidden()) {
									if (!fkColumnId.equals(item.getColumnId())) {
										// not support MultipartFile in confirm/complete screen
										if (item.getBaseType() != null && DbDomainConst.BaseType.BINARY_BASETYPE != item.getBaseType().intValue()) {
											maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
											ScreenItem screenItem = GenerateScreenContruct.populateScreenItemHidden(screenDesignDefault, maxItemSeqNo, screenDesign.getScreenId(), item.getCode(), item.getName(), item.getMessageDesign(), screenArea.getScreenAreaId(), screenArea.getAreaCode(), null, DbDomainConst.LogicDataType.LABEL_DYNAMIC, null, ITEM_TYPE_HIDDEN, item.getBaseType(), item.getTableDesignId(), item.getColumnId(), item.getKeyType(), false);
											lstItemHidden.add(screenItem);
										}
									}
								}
							}
						}

						// insert the last screen item when total element % 2 !=  0
						if (size % 2 != 0 && moduleTableMapping.getTableMappingType() == 0) {
							maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
							ScreenGroupItem screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "groupBlank", 1, maxItemSeqNo);
							// insert screen item label
							ScreenItem screenItem = populateScreenItem(maxItemSeqNo, screenDesign.getScreenId(), "lblBlank", null, screenArea.getScreenAreaId(), screenGroupItem.getGroupItemId(), DbDomainConst.LogicDataType.LABEL, null, ITEM_TYPE_NORMAL, null, null, null);
							listOfScreenItemGroupForBatch.add(screenGroupItem);
							listOfScreenItemWithOutDepend.add(screenItem);

							maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
							screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "groupBlank", 1, maxItemSeqNo);
							// insert screen item label
							screenItem = populateScreenItem(maxItemSeqNo, screenDesign.getScreenId(), "lblBlank", null, screenArea.getScreenAreaId(), screenGroupItem.getGroupItemId(), null, null, ITEM_TYPE_NORMAL, null, null, null);
							listOfScreenItemWithOutDepend.add(screenItem);
							listOfScreenItemGroupForBatch.add(screenGroupItem);
						}
					}
				}
				mapScreenIdAndUserdefineCL.put(screenDesign.getScreenId(), lstItemConfigUserDefineCL);
				if (ScreenDesignConst.VIEW_SCREEN.equals(screenDesign.getScreenTypeName()) || 
						ScreenDesignConst.CONFIRM_REGISTER_SCREEN.equals(screenDesign.getScreenTypeName()) || 
						ScreenDesignConst.COMPLETE_REGISTER_SCREEN.equals(screenDesign.getScreenTypeName()) || 
						ScreenDesignConst.CONFIRM_MODIFY_SCREEN.equals(screenDesign.getScreenTypeName()) ||
						ScreenDesignConst.COMPLETE_MODIFY_SCREEN.equals(screenDesign.getScreenTypeName())) {
					List<ScreenItem> itemCLofThisScreen = mapScreenIdAndUserdefineCL.get(screenDesign.getScreenId());
					ScreenDesign sourceScreen = new ScreenDesign();
					if (ScreenDesignConst.CONFIRM_REGISTER_SCREEN.equals(screenDesign.getScreenTypeName()) || ScreenDesignConst.COMPLETE_REGISTER_SCREEN.equals(screenDesign.getScreenTypeName())) {
						sourceScreen = screenDesignDefault.getMapScreenDesign().get(ScreenDesignConst.REGISTER_SCREEN);
					} else if (ScreenDesignConst.CONFIRM_MODIFY_SCREEN.equals(screenDesign.getScreenTypeName()) || ScreenDesignConst.COMPLETE_MODIFY_SCREEN.equals(screenDesign.getScreenTypeName())) {
						sourceScreen = screenDesignDefault.getMapScreenDesign().get(ScreenDesignConst.MODIFY_SCREEN);
					} else if(ScreenDesignConst.VIEW_SCREEN.equals(screenDesign.getScreenTypeName())) {
						sourceScreen = screenDesignDefault.getMapScreenDesign().get(ScreenDesignConst.SEARCH_SCREEN);
					}
					if (sourceScreen != null && sourceScreen.getScreenId() != null) {
						ScreenForm formOfScreen = screenDesignDefault.getMapFormOfScreen().get(sourceScreen.getScreenId());
						List<ScreenItem> itemCLofSourceScreen = new ArrayList<ScreenItem>();
						itemCLofSourceScreen = mapScreenIdAndUserdefineCL.get(sourceScreen.getScreenId());
						if (FunctionCommon.isNotEmpty(itemCLofThisScreen) && FunctionCommon.isNotEmpty(itemCLofSourceScreen)) {
							for (ScreenItem itemCL : itemCLofThisScreen) {
								for (ScreenItem itemCLSourceScreen : itemCLofSourceScreen) {
									if (itemCL.getColumnId() != null && itemCL.getColumnId().equals(itemCLSourceScreen.getColumnId())) {
										itemCL.setScreenDesignIdCodeListId(sourceScreen.getScreenId());
										itemCL.setScreenItemTextCodeListId(formOfScreen.getFormCode() + DOT + itemCLSourceScreen.getScreenArea().getAreaCode() + DOT + itemCLSourceScreen.getItemCode());
										break;
									}
								}
							}
						}
					} else {
						if (FunctionCommon.isNotEmpty(itemCLofThisScreen)) {
							for (ScreenItem itemView : itemCLofThisScreen) {
								if (itemView.getScreenDesignIdCodeListId() == null) {
									itemView.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_BLOGIC_DEFINE);
									itemView.setCodelistType(null);
									itemView.setCodelistId(null);
									itemView.setUserDefineCodelistId(null);
								}
							}
						}
					}
				}
			} else {
				// generate form, area and blank item
				Integer newAreaPatternType = null;
				screenForm = populateScreenForm(SCREEN_FORM + screenDesignDefault.getModuleName(), 1, 3, 1, screenDesign.getScreenId());
				screenFormRepository.insertScreenForm(screenForm);
				String colWidthUnit = "25%";
				Integer totalCol = 4;
				if (DbDomainConst.ScreenPatternType.REGISTER.equals(screenDesign.getScreenPatternType())) {
					newAreaPatternType = 1;
				} else if (DbDomainConst.ScreenPatternType.MODIFY.equals(screenDesign.getScreenPatternType())) {
					newAreaPatternType = 2;
				} else if (DbDomainConst.ScreenPatternType.VIEW.equals(screenDesign.getScreenPatternType())) {
					newAreaPatternType = 3;
				}
				String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
				screenArea = populateScreenArea(screenForm, null, 0, newAreaPatternType, areaCode, 4, colWidthUnit, totalCol, "100%", 1, 1, screenTypeName, null);
				allScreenArea.add(screenArea);
				screenAreaRepository.insertScreenArea(screenArea);

				Integer maxItemSeqNo;
				Integer tempItemCount = 4;
				for (int i = 0; i < tempItemCount; i++) {
					maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
					ScreenGroupItem screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "groupBlank" + i, 1, maxItemSeqNo);
					ScreenItem screenItem = populateScreenItem(maxItemSeqNo, screenDesign.getScreenId(), "lblBlank" + i, null, screenArea.getScreenAreaId(), screenGroupItem.getGroupItemId(), DbDomainConst.LogicDataType.LABEL, null, ITEM_TYPE_NORMAL, null, null, null);
					listOfScreenItemWithOutDepend.add(screenItem);
					listOfScreenItemGroupForBatch.add(screenGroupItem);

					maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
					screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "groupBlank" + i, 1, maxItemSeqNo);
					// insert screen item label
					screenItem = populateScreenItem(maxItemSeqNo, screenDesign.getScreenId(), "lblBlank" + i, null, screenArea.getScreenAreaId(), screenGroupItem.getGroupItemId(), null, null, ITEM_TYPE_NORMAL, null, null, null);
					listOfScreenItemWithOutDepend.add(screenItem);
					listOfScreenItemGroupForBatch.add(screenGroupItem);
				}
			}
		} else {
			int countElement = 0;
			ScreenArea tempAreaCondition = new ScreenArea();
			List<ScreenItem> screenItemConditions = new ArrayList<ScreenItem>();
			List<ScreenItem> screenItemResults = new ArrayList<ScreenItem>();
			Set<String> hashSetItemCodeCondition = new HashSet<String>();

			int flag = 0;
			if (screenDesignDefault.getModuleTableMappings() != null && screenDesignDefault.getModuleTableMappings().length > 0) {
				// QuangVD:
				// only generate attribute of first table
				ModuleTableMapping moduleTableMapping = screenDesignDefault.getModuleTableMappings()[0];

				List<TableDesignDetails> listOfColumn = moduleTableMapping.getListTableDesignDetail();

				if (FunctionCommon.isNotEmpty(listOfColumn)) {
					if (flag == 0) {
						// insert screen form for search screen
						screenForm = populateScreenForm(SCREEN_FORM + screenDesignDefault.getModuleCode() + flag, 1, 3, 1, screenDesign.getScreenId());
						screenDesignDefault.getMapFormOfScreen().put(screenDesign.getScreenId(), screenForm);
						screenFormRepository.insertScreenForm(screenForm);
					}
					flag++;
					Integer columnNotHidden = 0;
					for (TableDesignDetails column : listOfColumn) {
						if (column.getIsPkHidden() == null || !column.getIsPkHidden()) {
							columnNotHidden++;
						}
					}

					countElement += columnNotHidden;
					ScreenItem screenItem = null;
					for (TableDesignDetails item : listOfColumn) {
						if(DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getItemType())) {
							continue;
						}
						
						screenItem = new ScreenItem();
						screenItem.setScreenId(screenDesign.getScreenId());
						screenItem.setItemCode(item.getCode());
						screenItem.setItemName(item.getName());
						screenItem.setMessageDesign(item.getMessageDesign());
						Integer itemType = item.getItemType() == null ? null : item.getItemType().intValue();
						if (DbDomainConst.LogicDataType.RADIO.equals(itemType)) {
							screenItem.setLogicalDataType(DbDomainConst.LogicDataType.CHECKBOX);
						} else {
							screenItem.setLogicalDataType(itemType);
						}
						
						screenItem.setPhysicalDataType(item.getBaseType());
						screenItem.setTblDesignId(item.getTableDesignId());
						screenItem.setTblDesignName(item.getTableDesignName());
						screenItem.setTblDesignCode(item.getTableDesignCode());
						screenItem.setColumnId(item.getColumnId());
						if (screenItem.getLogicalDataType() != null && !screenItem.getLogicalDataType().equals(5) && !screenItem.getLogicalDataType().equals(6)
								&& !screenItem.getLogicalDataType().equals(7)) {
							screenItemValidation = populateScreenItemValidation(item.getMaxlength(), item.getIsMandatory(), item.getMinVal(), item.getMaxVal(), item.getFmtCode(), false, item.getBaseType());
						} else {
							screenItemValidation = populateScreenItemValidation(null, item.getIsMandatory(), "", "", "", false, item.getBaseType());
						}
						screenItem.setScreenItemValidation(screenItemValidation);
						screenItem.setColSpan(1);
						screenItem.setRowSpan(1);
						screenItem.setItemType(1);
						screenItem.setDataSourceType(item.getDatasourceType());
						screenItem.setDatasourceId(item.getDatasourceId());
						screenItem.setArrayFlg(0);
						screenItem.setKeyType(item.getKeyType());
						screenItem.setCreatedBy(accountId);
						screenItem.setCreatedDate(systemDate);
						screenItem.setUpdatedBy(accountId);
						screenItem.setUpdatedDate(systemDate);
						if (item.getIsPkHidden() != null && item.getIsPkHidden()) {
							screenItem.setItemType(ITEM_TYPE_HIDDEN);
						} else {
							screenItem.setItemType(ITEM_TYPE_NORMAL);
						}
						screenItem.setMandatoryFlg(item.getIsMandatory());
						if (!hashSetItemCodeCondition.contains(screenItem.getItemCode())) {
							hashSetItemCodeCondition.add(screenItem.getItemCode());
							screenItemConditions.add(screenItem);
							ScreenItem itemResult;
							try {
								itemResult = (ScreenItem) screenItem.clone();
								screenItemResults.add(itemResult);
							} catch (CloneNotSupportedException e) {
								throw new SystemException("", "");
							}
						}
					}
				}
			} else {
				// generate form, area and blank item
				// CONDITION AREA
				screenForm = populateScreenForm(SCREEN_FORM + screenDesignDefault.getModuleCode() + flag, 1, 3, 1, screenDesign.getScreenId());
				screenFormRepository.insertScreenForm(screenForm);

				// message for corresponding screen search condition area
				messageDesign = (MessageDesign) messageCommons.get("searchCondition");
				// insert search condition area
				String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
				screenArea = populateScreenArea(screenForm, messageDesign, 0, 1, areaCode, countElement, "25%", 4, "100%", 1, 1, screenTypeName, null);
				allScreenArea.add(screenArea);
				screenAreaRepository.insertScreenArea(screenArea);
				Integer maxItemBlankSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);

				// insert register submit area
				String areaCodeSearchButton = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, MessageUtils.getMessage(MESSAGE_CODE_SEARCH) + screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix());
				ScreenArea screenAreaSearchButton = populateScreenArea(screenForm, null, 2, null, areaCodeSearchButton, 1, "100%", 1, "100%", 1, 1, screenTypeName, null);
				allScreenArea.add(screenAreaSearchButton);
				screenAreaRepository.insertScreenArea(screenAreaSearchButton);
				screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(MESSAGE_CODE_SEARCH), screenDesign.getScreenId(), screenDesign.getScreenId(), ScreenActionConst.SUBMIT_METHOD_TYPE_POST);
				screenActionRepository.insertScreenAction(screenAction);
				mapNavi.put(SEARCH_NAVIGATE, screenAction);
				Integer maxItemSeqNo = getItemSeqNo(screenAreaSearchButton.getScreenAreaId(), mapScreenAreaItemSeqNo);
				// insert button submit screen item
				ScreenItem screenItem = populateScreenItem(maxItemSeqNo, screenDesign.getScreenId(), "btnSearch" + screenDesignDefault.getModuleName(), (MessageDesign) messageCommons.get("btnSearch"), screenAreaSearchButton.getScreenAreaId(), null, DbDomainConst.LogicDataType.BUTTON, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, null, null, ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE);
				/* screenItemRepository.insertScreenItem(screenItem); */
				listOfScreenItemWithOutDepend.add(screenItem);
				listOfScreenItemGroupForBatch.add(null);
				
				// insert screen transition
				String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, SEARCH_TO_SEARCH +  StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
				ScreenTransition screenTransition = populateScreenTransition(transitionCode, screenDesign.getScreenId(), screenDesign.getScreenId(), screenDesignDefault.getModuleId(), false);
				lstScreenTransitions.add(screenTransition);
				mapTransitionAndItem.put(screenTransition, screenItem);

				Integer tempItemCount = 4;
				for (int i = 0; i < tempItemCount; i++) {
					maxItemBlankSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
					ScreenGroupItem screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "groupBlank" + i, 1, maxItemBlankSeqNo);
					// insert screen item label
					ScreenItem screenItemBlank = populateScreenItem(maxItemBlankSeqNo, screenDesign.getScreenId(), "lblBlank" + i, null, screenArea.getScreenAreaId(), screenGroupItem.getGroupItemId(), DbDomainConst.LogicDataType.LABEL, null, ITEM_TYPE_NORMAL, null, null, null);
					listOfScreenItemWithOutDepend.add(screenItemBlank);
					listOfScreenItemGroupForBatch.add(screenGroupItem);

					maxItemBlankSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
					screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "groupBlank" + i, 1, maxItemBlankSeqNo);
					listOfScreenItemGroupForBatch.add(screenGroupItem);
					// insert screen item label
					screenItemBlank = populateScreenItem(maxItemBlankSeqNo, screenDesign.getScreenId(), "lblBlank" + i, null, screenArea.getScreenAreaId(), screenGroupItem.getGroupItemId(), null, null, ITEM_TYPE_NORMAL, null, null, null);
					listOfScreenItemWithOutDepend.add(screenItemBlank);
				}

				// RESULTS AREA
				// message for corresponding screen search result area
				messageDesign = (MessageDesign) messageCommons.get("searchResult");
				// insert search result area
				String areaCodeResult = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA_RESULT, StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
				screenArea = populateScreenArea(screenForm, messageDesign, 1, 3, areaCodeResult, 4, "25%", 4, "100%", 1, 2, screenTypeName, null);
				allScreenArea.add(screenArea);
				screenAreaRepository.insertScreenArea(screenArea);
				Integer maxItemResultSeqNo;

				for (int i = 0; i < tempItemCount; i++) {
					maxItemResultSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
					ScreenGroupItem screenGroupItem2 = populateScreenGroupItem(screenArea.getScreenAreaId(), "groupResult" + i, 1, maxItemResultSeqNo);
					listOfScreenItemGroupForBatch.add(screenGroupItem2);
					// insert screen item label
					ScreenItem screenItemLabel = populateScreenItem(maxItemResultSeqNo, screenDesign.getScreenId(), "lblResult" + i, null, screenArea.getScreenAreaId(), screenGroupItem2.getGroupItemId(), DbDomainConst.LogicDataType.LABEL, null, ITEM_TYPE_NORMAL, null, null, null);
					listOfScreenItemWithOutDepend.add(screenItemLabel);

					maxItemResultSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
					ScreenGroupItem screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "groupBlank" + i, 1, maxItemResultSeqNo);
					listOfScreenItemGroupForBatch.add(screenGroupItem);
					// insert screen item label
					screenItem = populateScreenItem(maxItemResultSeqNo, screenDesign.getScreenId(), "lblBlank" + i, null, screenArea.getScreenAreaId(), screenGroupItem.getGroupItemId(), null, null, ITEM_TYPE_NORMAL, null, null, null);
					listOfScreenItemWithOutDepend.add(screenItem);
				}
			}

			if (screenItemConditions.size() != 0) {
				ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings() != null ? screenDesignDefault.getModuleTableMappings()[0] : null;
				// insert message for corresponding screen search condition area
				messageDesign = (MessageDesign) messageCommons.get("searchCondition");
				// insert search condition area
				String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
				screenArea = populateScreenArea(screenForm, messageDesign, 0, 1, areaCode, countElement, "25%", 4, "100%", 1, 1, screenTypeName, firstTable);
				tempAreaCondition = screenArea;
				allScreenArea.add(screenArea);
				screenAreaRepository.insertScreenArea(screenArea);
				Integer maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
				// insert register submit area
				String areaCodeSearchButton = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, SEARCH + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
				ScreenArea screenAreaSearchButton = populateScreenArea(screenForm, null, 2, null, areaCodeSearchButton, 1, "100%", 1, "100%", 1, 1, screenTypeName, null);
				allScreenArea.add(screenAreaSearchButton);
				screenAreaRepository.insertScreenArea(screenAreaSearchButton);
				Integer maxItemButtonSeqNo;

				screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(MESSAGE_CODE_SEARCH), screenDesign.getScreenId(), screenDesign.getScreenId(), ScreenActionConst.SUBMIT_METHOD_TYPE_POST);
				screenActionRepository.insertScreenAction(screenAction);
				mapNavi.put(SEARCH_NAVIGATE, screenAction);
				maxItemButtonSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
				// insert button submit screen item
				ScreenItem screenItemButton = populateScreenItem(maxItemButtonSeqNo, screenDesign.getScreenId(), "btnSearch" + screenDesignDefault.getModuleName(), (MessageDesign) messageCommons.get("btnSearch"), screenAreaSearchButton.getScreenAreaId(), null, DbDomainConst.LogicDataType.BUTTON, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, null, null, ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE);
				listOfScreenItemWithOutDepend.add(screenItemButton);
				listOfScreenItemGroupForBatch.add(null);

				// insert screen transition
				String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, SEARCH_TO_SEARCH +  StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
				ScreenTransition screenTransition = populateScreenTransition(transitionCode, screenDesign.getScreenId(), screenDesign.getScreenId(), screenDesignDefault.getModuleId(), false);
				lstScreenTransitions.add(screenTransition);
				mapTransitionAndItem.put(screenTransition, screenItemButton);
				
				for (ScreenItem screenItem : screenItemConditions) {
					if (!ITEM_TYPE_HIDDEN.equals(screenItem.getItemType())) {
						maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
						ScreenGroupItem screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "group" + screenItem.getMessageDesign().getMessageString(), 1, maxItemSeqNo);
						listOfScreenItemGroupForBatch.add(screenGroupItem);
						// insert screen item label
						ScreenItem screenItemLabel = populateScreenItem(maxItemSeqNo, screenDesign.getScreenId(), "lbl" + screenItem.getMessageDesign().getMessageString(), screenItem.getMessageDesign(), screenArea.getScreenAreaId(), screenGroupItem.getGroupItemId(), DbDomainConst.LogicDataType.LABEL, null, ITEM_TYPE_NORMAL, null, null, null);
						listOfScreenItemWithOutDepend.add(screenItemLabel);

						// insert screen item
						maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
						screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "group" + screenItem.getMessageDesign().getMessageString(), 1, maxItemSeqNo);

						listOfScreenItemGroupForBatchOne.add(screenGroupItem);

						String messCode = screenItem.getMessageDesign() == null ? null : screenItem.getMessageDesign().getMessageCode();
						screenItem.setMessageCode(messCode);
						// screenItem.setMessageDesign(null);
						screenItem.setItemSeqNo(maxItemSeqNo);
						screenItem.setGroupItemId(screenGroupItem.getGroupItemId());
						screenItem.setScreenAreaId(screenArea.getScreenAreaId());
						screenItem.setAreaCode(areaCode);
						screenItem.setScreenArea(screenArea);
						List<UserDefineCodelistDetails> lstUserCLofItem = new ArrayList<UserDefineCodelistDetails>();
						if (DbDomainConst.DatasourceType.USER_DEFINE.equals(screenItem.getDataSourceType())) {
							for (UserDefineCodelistDetails udd : userDefineCodelistDetails) {
								if (udd.getCodelistId().equals(screenItem.getDatasourceId())) {
									lstUserCLofItem.add(udd);
								}
							}
							if (lstUserCLofItem.size() > 0) {
								screenItem.setCodelistType(ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_CUSTOM);
								screenItem.setUserDefineCodelistId(screenItem.getDatasourceId());
								screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_CODELIST);
							}
							lstItemConfigUserDefineCL.add(screenItem);
						} else if (DbDomainConst.DatasourceType.CODELIST.equals(screenItem.getDataSourceType())) {
							if (FunctionCommon.checkExists(lstCodeListId, screenItem.getDatasourceId())) {
								screenItem.setCodelistType(ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_SYSTEM);
								screenItem.setCodelistId(screenItem.getDatasourceId());
								screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_CODELIST);
							}
						} else if (DbDomainConst.DatasourceType.SQL_BUILDER_IS_AUTOCOMPLETE.equals(screenItem.getDataSourceType())) {
							if (FunctionCommon.checkExists(lstAutocompleteId, screenItem.getDatasourceId())) {
								screenItem.setAutocompleteId(screenItem.getDatasourceId());
								screenItem.setSqlDesignId(screenItem.getDatasourceId());
								screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_BLOGIC_DEFINE);
								// set logical if have config autocomplete
								screenItem.setLogicalDataType(DbDomainConst.LogicDataType.AUTOCOMPLETE);
							}
						} else if (DbDomainConst.DatasourceType.SQL_BUILDER.equals(screenItem.getDataSourceType())) {
							if (FunctionCommon.checkExists(lstSqlDesignId, screenItem.getDatasourceId())) {
								screenItem.setSqlDesignId(screenItem.getDatasourceId());
								screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_BLOGIC_DEFINE);
							}
						}
						if (DbDomainConst.LogicDataType.CURRENCY.equals(screenItem.getLogicalDataType()) || DbDomainConst.LogicDataType.DECIMAL.equals(screenItem.getLogicalDataType()) || DbDomainConst.LogicDataType.INTEGER.equals(screenItem.getLogicalDataType()) || DbDomainConst.LogicDataType.DATETIME.equals(screenItem.getLogicalDataType()) || DbDomainConst.LogicDataType.DATE.equals(screenItem.getLogicalDataType()) || DbDomainConst.LogicDataType.TIME.equals(screenItem.getLogicalDataType())) {
							screenItem.setDisplayFromTo(ScreenDesignConst.FromTo.FROM_TO);
						} else {
							screenItem.setDisplayFromTo(ScreenDesignConst.FromTo.NORMAL);
						}
						// set screen item code list
						if (lstUserCLofItem.size() > 0) {
							List<ScreenItemCodelist> lstScreenItemCodelist = new ArrayList<ScreenItemCodelist>();
							for (UserDefineCodelistDetails udd : lstUserCLofItem) {
								ScreenItemCodelist screenItemCodelist = GenerateScreenContruct.populateScreenItemCodeList(screenDesignDefault, screenItem, udd);
								lstScreenItemCodelist.add(screenItemCodelist);
							}
							screenItem.setListScreenItemCodelists(lstScreenItemCodelist);
						}
						// special case : if data type is BOOLEAN, gen check box have config datasource is user define is True, False
						if(DbDomainConst.BaseType.BOOLEAN_BASETYPE == screenItem.getPhysicalDataType() && DbDomainConst.LogicDataType.CHECKBOX.equals(screenItem.getLogicalDataType())) {
							screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_CODELIST);
							screenItem.setCodelistType(ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_CUSTOM);
							List<ScreenItemCodelist> lstCodelistTrueFalse = GenerateScreenContruct.populateScreenItemCodeListForCheckBoxTrueFalse(screenDesignDefault, screenItem);
							screenItem.setListScreenItemCodelists(lstCodelistTrueFalse);
						}
						// if logical is SELECT, set attribute show blank item
						if(DbDomainConst.LogicDataType.SELECT.equals(screenItem.getLogicalDataType())) {
							screenItem.setShowBlankItem(1);
						}
						listOfScreenItemWithDependOne.add(screenItem);
					}
				}
				mapScreenIdAndUserdefineCL.put(screenDesign.getScreenId(), lstItemConfigUserDefineCL);
				// insert the last screen item when total element % 2 != 0
				if (countElement % 2 != 0) {
					maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
					ScreenGroupItem screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "groupBlank", 1, maxItemSeqNo);
					listOfScreenItemGroupForBatch.add(screenGroupItem);
					// insert screen item label
					ScreenItem screenItem = populateScreenItem(maxItemSeqNo, screenDesign.getScreenId(), "lblBlank", null, screenArea.getScreenAreaId(), screenGroupItem.getGroupItemId(), DbDomainConst.LogicDataType.LABEL, null, ITEM_TYPE_NORMAL, null, null, null);
					listOfScreenItemWithOutDepend.add(screenItem);

					maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
					screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "groupBlank", 1, maxItemSeqNo);
					listOfScreenItemGroupForBatch.add(screenGroupItem);
					// insert screen item label
					screenItem = populateScreenItem(maxItemSeqNo, screenDesign.getScreenId(), "lblBlank", null, screenArea.getScreenAreaId(), screenGroupItem.getGroupItemId(), null, null, ITEM_TYPE_NORMAL, null, null, null);
					listOfScreenItemWithOutDepend.add(screenItem);
				}
			}

			if (screenItemResults.size() > 0) {
				ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings() != null ? screenDesignDefault.getModuleTableMappings()[0] : null;
				Map<Long, ScreenAction> mapScreenActionOfSearchResultArea = new HashMap<Long, ScreenAction>();

				// message for corresponding screen search result area
				messageDesign = (MessageDesign) messageCommons.get("searchResult");
				// insert search result area
				Integer columnNotHidden = 0;
				for(ScreenItem item : screenItemResults) {
					if(!ITEM_TYPE_HIDDEN.equals(item.getItemType())) {
						columnNotHidden++;
					}
				}
				
				Integer totalCol = null;
				if (modifyScreenId != 0) {
					totalCol = columnNotHidden + 1;
				} else {
					totalCol = columnNotHidden;
				}
				String percentage = "";
				if (totalCol > 0) {
					percentage = String.valueOf(100 / totalCol) + "%";
				} else {
					percentage = "100%";
				}
				String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA_RESULT, StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
				screenArea = populateScreenArea(screenForm, messageDesign, 1, 3, areaCode, totalCol, percentage, totalCol, "100%", 1, 2, screenTypeName, firstTable);
				screenArea.setEnableSort(true);
				allScreenArea.add(screenArea);
				screenAreaRepository.insertScreenArea(screenArea);
				Integer maxItemSeqNo = -1;

				Long screenActionViewId = new Long(0);
				if (screenDesignViewId != 0) {
					screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0082), screenDesignSearchId, screenDesignViewId, ScreenActionConst.SUBMIT_METHOD_TYPE_GET);
					// insert action link to view in search result
					screenActionRepository.insertScreenAction(screenAction);
					screenActionViewId = screenAction.getScreenActionId();
					mapNavi.put(HEADER_SEARCH_TO_VIEW, screenAction);
					mapScreenActionOfSearchResultArea.put(screenActionViewId, screenAction);
				} else {
					screenActionViewId = null;
				}

				for (ScreenItem screenItem : screenItemResults) {
					if(!ITEM_TYPE_HIDDEN.equals(screenItem.getItemType())) {
						screenItem.setKeyFlag(true);
						break;
					}
				}
				
				if (modifyScreenId != 0) {
					// add btn modify
					MessageDesign messagelblAction = (MessageDesign) messageCommons.get("lblAction");
					ScreenItem screenItemBtn = new ScreenItem();
					screenItemBtn.setScreenId(screenDesign.getScreenId());
					screenItemBtn.setItemCode("");
					screenItemBtn.setItemName("");
					screenItemBtn.setIcon(ICON_EDIT);
					screenItemBtn.setMessageDesign(messagelblAction);
					screenItemBtn.setLogicalDataType(DbDomainConst.LogicDataType.BUTTON);
					screenItemBtn.setShowLabel(0);
					screenItemBtn.setPhysicalDataType(1);
					screenItemBtn.setColSpan(1);
					screenItemBtn.setRowSpan(1);
					screenItemBtn.setItemType(1);
					screenItemBtn.setArrayFlg(0);
					screenItemBtn.setCreatedBy(accountId);
					screenItemBtn.setCreatedDate(systemDate);
					screenItemBtn.setUpdatedBy(accountId);
					screenItemBtn.setUpdatedDate(systemDate);
					screenItemBtn.setItemType(ITEM_TYPE_NORMAL);
					screenItemBtn.setBtnModifyFlag(true);
					screenItemResults.add(screenItemBtn);
				}

				Long screenActionModifyId = new Long(0);

				if (modifyScreenId != 0) {
					screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0083), screenDesignSearchId, modifyScreenId, ScreenActionConst.SUBMIT_METHOD_TYPE_GET);
					// insert action link to view in search result
					screenActionRepository.insertScreenAction(screenAction);
					
					screenActionModifyId = screenAction.getScreenActionId();
					mapScreenActionOfSearchResultArea.put(screenActionModifyId, screenAction);
					mapNavi.put(MODIFY_SEARCH, screenAction);
				} else {
					screenActionModifyId = null;
				}

				// insert parameter
				if (screenArea != null && screenArea.getScreenAreaId() != null) {
					buildItemHidden(screenDesign, screenDesignDefault, screenItemResults);
				}

				// insert screen items for screen result area and if flag is FALSE, then choose the first column for view action
				int link = 0;
				List<ScreenItem> itemsHeader = new ArrayList<ScreenItem>();
				for (ScreenItem screenItem : screenItemResults) {
					screenItem.setAreaCode(screenArea.getAreaCode());
					Boolean settedFlag = false;
					if(!ITEM_TYPE_HIDDEN.equals(screenItem.getItemType())) {
						maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
						// insert screen item sequence for label screen item
						ScreenGroupItem screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "group" + screenItem.getMessageDesign().getMessageString(), 1, maxItemSeqNo);
						listOfScreenItemGroupForBatch.add(screenGroupItem);
						// insert screen item label
						ScreenItem screenItemLabel = populateScreenItem(maxItemSeqNo, screenDesign.getScreenId(), "lbl" + screenItem.getMessageDesign().getMessageString(), screenItem.getMessageDesign(), screenArea.getScreenAreaId(), screenGroupItem.getGroupItemId(), DbDomainConst.LogicDataType.LABEL, null, screenItem.getItemType(), null, null, null);
						screenItemLabel.setTblDesignId(screenItem.getTblDesignId());
						screenItemLabel.setColumnId(screenItem.getColumnId());
						listOfScreenItemWithOutDepend.add(screenItemLabel);
						itemsHeader.add(screenItemLabel);
					}

					if (link == 0) {
						if (screenDesignViewId != 0 && screenItem.getKeyFlag() != null && screenItem.getKeyFlag()) {
							// insert first column params for action view
							screenItem.setScreenActionId(screenActionViewId);
							ScreenAction screenActionView = mapScreenActionOfSearchResultArea.get(screenActionViewId);
							if (screenActionView != null) {
								List<ScreenActionParam> lstScreenActionParam = GenerateScreenContruct.populateScreenActionParam(screenDesignDefault, screenDesign, screenItemResults, screenForm, mapScreenItemHidden, screenActionView);
								if (lstScreenActionParam.size() > 0) {
									Long seq = screenActionParamRepository.getSequencesScreenActionParam(lstScreenActionParam.size() - 1);
									Long startSeqActionParam = seq - (lstScreenActionParam.size() - 1);
									for (ScreenActionParam actionParam : lstScreenActionParam) {
										actionParam.setScreenActionParamId(startSeqActionParam++);
									}
									screenActionParamRepository.registerListScreenActionParam(lstScreenActionParam);
									mScreenActionParam.put(SCREEN_ACTION_PARAM_SEARCH_TO_VIEW, lstScreenActionParam);
								}
							}
							screenItem.setLogicalDataType(DbDomainConst.LogicDataType.LINK_DYNAMIC);
							settedFlag = true;
							link++;
							
							// insert screen transition
							String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, SEARCH_TO_VIEW +  StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							ScreenTransition screenTransition = populateScreenTransition(transitionCode, screenDesignSearchId, screenDesignViewId, screenDesignDefault.getModuleId(), false);
							lstScreenTransitions.add(screenTransition);
							mapTransitionAndItem.put(screenTransition, screenItem);
						}
					}
					if (modifyScreenId != 0) {
						if (screenItem.getBtnModifyFlag() != null && screenItem.getBtnModifyFlag()) {
							screenItem.setScreenActionId(screenActionModifyId);
							ScreenAction screenActionModify = mapScreenActionOfSearchResultArea.get(screenActionModifyId);
							if (screenActionModify != null) {
								List<ScreenActionParam> lstScreenActionParam = GenerateScreenContruct.populateScreenActionParam(screenDesignDefault, screenDesign, screenItemResults, screenForm, mapScreenItemHidden, screenActionModify);
								if (lstScreenActionParam.size() > 0) {
									Long seq = screenActionParamRepository.getSequencesScreenActionParam(lstScreenActionParam.size() - 1);
									Long startSeqActionParam = seq - (lstScreenActionParam.size() - 1);
									for (ScreenActionParam actionParam : lstScreenActionParam) {
										actionParam.setScreenActionParamId(startSeqActionParam++);
									}
									screenActionParamRepository.registerListScreenActionParam(lstScreenActionParam);
									mScreenActionParam.put(SCREEN_ACTION_PARAM_SEARCH_TO_MODIFY, lstScreenActionParam);
								}
							}
							screenItem.setLogicalDataType(DbDomainConst.LogicDataType.BUTTON);
							settedFlag = true;
							
							// insert screen transition
							String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, SEARCH_TO_MODIFY +  StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							ScreenTransition screenTransition = populateScreenTransition(transitionCode, screenDesignSearchId, modifyScreenId, screenDesignDefault.getModuleId(), false);
							lstScreenTransitions.add(screenTransition);
							mapTransitionAndItem.put(screenTransition, screenItem);
						}
					}
					if (!settedFlag) {
						screenItem.setLogicalDataType(DbDomainConst.LogicDataType.LABEL_DYNAMIC);
					}

					maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
					screenItem.setItemSeqNo(maxItemSeqNo);
					// insert screen item sequence for corresponding screen item
					ScreenGroupItem screenGroupItem = populateScreenGroupItem(screenArea.getScreenAreaId(), "groupBlank", 1, maxItemSeqNo);

					listOfScreenItemGroupForBatchTwo.add(screenGroupItem);
					screenItem.setGroupItemId(screenGroupItem.getGroupItemId());

					screenItem.setScreenAreaId(screenArea.getScreenAreaId());
					screenItem.setScreenArea(screenArea);
					screenItem.setDisplayFromTo(ScreenDesignConst.FromTo.NORMAL);
					List<UserDefineCodelistDetails> lstUserCLofItem = new ArrayList<UserDefineCodelistDetails>();
					if (DbDomainConst.DatasourceType.USER_DEFINE.equals(screenItem.getDataSourceType())) {
						for (UserDefineCodelistDetails udd : userDefineCodelistDetails) {
							if (udd.getCodelistId().equals(screenItem.getDatasourceId())) {
								lstUserCLofItem.add(udd);
							}
						}
						if (lstUserCLofItem.size() > 0) {
							screenItem.setCodelistType(ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_CUSTOM);
							screenItem.setUserDefineCodelistId(screenItem.getDatasourceId());
							screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_CODELIST);
						}
						List<ScreenItem> itemsConfigUserDefine = mapScreenIdAndUserdefineCL.get(screenDesign.getScreenId());
						if (FunctionCommon.isNotEmpty(itemsConfigUserDefine)) {
							for (ScreenItem obj : itemsConfigUserDefine) {
								if (obj.getColumnId() != null && obj.getColumnId().equals(screenItem.getColumnId())) {
									screenItem.setScreenDesignIdCodeListId(screenDesign.getScreenId());
									screenItem.setScreenItemTextCodeListId(screenForm.getFormCode() + DOT + tempAreaCondition.getAreaCode() + DOT + obj.getItemCode());
									break;
								}
							}
						}
					} else if (DbDomainConst.DatasourceType.CODELIST.equals(screenItem.getDataSourceType())) {
						if (FunctionCommon.checkExists(lstCodeListId, screenItem.getDatasourceId())) {
							screenItem.setCodelistType(ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_SYSTEM);
							screenItem.setCodelistId(screenItem.getDatasourceId());
							screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_CODELIST);
						}
					} else if (DbDomainConst.DatasourceType.SQL_BUILDER_IS_AUTOCOMPLETE.equals(screenItem.getDataSourceType())) {
						if (FunctionCommon.checkExists(lstAutocompleteId, screenItem.getDatasourceId())) {
							screenItem.setAutocompleteId(screenItem.getDatasourceId());
							screenItem.setSqlDesignId(screenItem.getDatasourceId());
							screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_BLOGIC_DEFINE);
						}
					} else if (DbDomainConst.DatasourceType.SQL_BUILDER.equals(screenItem.getDataSourceType())) {
						if (FunctionCommon.checkExists(lstSqlDesignId, screenItem.getDatasourceId())) {
							screenItem.setSqlDesignId(screenItem.getDatasourceId());
							screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_BLOGIC_DEFINE);
						}
					}

					// set screen item code list
					if (lstUserCLofItem.size() > 0) {
						List<ScreenItemCodelist> lstScreenItemCodelist = new ArrayList<ScreenItemCodelist>();
						for (UserDefineCodelistDetails udd : lstUserCLofItem) {
							ScreenItemCodelist screenItemCodelist = GenerateScreenContruct.populateScreenItemCodeList(screenDesignDefault, screenItem, udd);
							lstScreenItemCodelist.add(screenItemCodelist);
						}
						screenItem.setListScreenItemCodelists(lstScreenItemCodelist);
					}
					
					// set default datasource type if item don't have config datasource type
					if (DbDomainConst.LogicDataType.LINK_DYNAMIC.equals(screenItem.getLogicalDataType()) || DbDomainConst.LogicDataType.LABEL_DYNAMIC.equals(screenItem.getLogicalDataType())) {
						if(screenItem.getDataSourceType() == null) {
							screenItem.setDataSourceType(ScreenDesignConst.ScreenItemConst.DATA_SOURCE_TYPE_BLOGIC_DEFINE);
						}
					}
					listOfScreenItemWithDependTwo.add(screenItem);
				}
				screenDesignDefault.setFormOfSearchScreen(screenForm);
				screenDesignDefault.getMapAreaResultAndItemHeader().put(screenArea, itemsHeader);
			}
		}

		/**
		 * DungNN
		 */
		if (FunctionCommon.isNotEmpty(listMessageDesign)) {
			messageDesignService.registerMessageDesign(listMessageDesign, false);
		}

		// insert screen item hidden
		if (FunctionCommon.isNotEmpty(lstItemHidden)) {
			Long seq = screenDesignRepository.screenItemGetSequences(lstItemHidden.size() - 1);
			Long startSreenSeqId = seq - (lstItemHidden.size() - 1);
			for (ScreenItem item : lstItemHidden) {
				item.setScreenItemId(startSreenSeqId++);
				lstScreenItems.add(item);
			}
			screenDesignRepository.screenHiddenItemsInsert(lstItemHidden);
		}

		if (FunctionCommon.isNotEmpty(listOfScreenItemWithOutDepend) || FunctionCommon.isNotEmpty(listOfScreenItemWithDependOne) || FunctionCommon.isNotEmpty(listOfScreenItemWithDependTwo)) {

			Integer numOfItem = listOfScreenItemWithOutDepend.size() + listOfScreenItemWithDependOne.size() + listOfScreenItemWithDependTwo.size();
			Long seq = screenDesignRepository.screenItemGetSequences(numOfItem - 1);
			Long startSreenSeqId = seq - (numOfItem - 1);

			seq = screenDesignRepository.screenGroupItemGetSequences(numOfItem - 1);
			Long startGroupSeqId = seq - (numOfItem - 1);

			List<ScreenItemSequence> listOfItemSeq = new ArrayList<ScreenItemSequence>();
			List<ScreenGroupItem> listOfScreenItemGroup = new ArrayList<ScreenGroupItem>();

			if (FunctionCommon.isNotEmpty(listOfScreenItemWithOutDepend)) {

				numOfItem = listOfScreenItemWithOutDepend.size();

				for (int i = 0; i < numOfItem; i++) {
					ScreenItem item = listOfScreenItemWithOutDepend.get(i);
					ScreenGroupItem screenGroupItem = listOfScreenItemGroupForBatch.get(i);

					item.setScreenItemId(startSreenSeqId);
					listOfItemSeq.add(populateScreenItemSequence(1, item.getItemSeqNo(), item.getScreenAreaId()));

					if (null != screenGroupItem) {
						screenGroupItem.setGroupItemId(startGroupSeqId);
						listOfScreenItemGroup.add(screenGroupItem);
						item.setGroupItemId(startGroupSeqId);
					}

					startGroupSeqId++;
					startSreenSeqId++;
				}

			}

			if (FunctionCommon.isNotEmpty(listOfScreenItemWithDependOne)) {
				numOfItem = listOfScreenItemWithDependOne.size();
				for (int i = 0; i < numOfItem; i++) {
					ScreenItem item = listOfScreenItemWithDependOne.get(i);
					ScreenGroupItem screenGroupItem = listOfScreenItemGroupForBatchOne.get(i);

					item.setScreenItemId(startSreenSeqId);
					listOfItemSeq.add(populateScreenItemSequence(1, item.getItemSeqNo(), item.getScreenAreaId()));

					if (null != screenGroupItem) {
						screenGroupItem.setGroupItemId(startGroupSeqId);
						listOfScreenItemGroup.add(screenGroupItem);
						item.setGroupItemId(startGroupSeqId);
					}

					listOfScreenItemWithOutDepend.add(item);
					lstScreenItems.add(item);
					startGroupSeqId++;
					startSreenSeqId++;
				}

			}

			if (FunctionCommon.isNotEmpty(listOfScreenItemWithDependTwo)) {
				numOfItem = listOfScreenItemWithDependTwo.size();
				for (int i = 0; i < numOfItem; i++) {
					ScreenItem item = listOfScreenItemWithDependTwo.get(i);
					ScreenGroupItem screenGroupItem = listOfScreenItemGroupForBatchTwo.get(i);

					item.setScreenItemId(startSreenSeqId);
					listOfItemSeq.add(populateScreenItemSequence(1, item.getItemSeqNo(), item.getScreenAreaId()));

					if (null != screenGroupItem) {
						screenGroupItem.setGroupItemId(startGroupSeqId);
						listOfScreenItemGroup.add(screenGroupItem);
						item.setGroupItemId(startGroupSeqId);
					}

					listOfScreenItemWithOutDepend.add(item);
					if (item.getBtnModifyFlag() == null || !item.getBtnModifyFlag()) {
						lstScreenItemSearchResults.add(item);
					}
					startGroupSeqId++;
					startSreenSeqId++;
				}
			}

			screenItemSequenceRepository.multiInsertScreenItemSequence(listOfItemSeq);
			screenGroupItemRepository.multiInsertScreenGroupItem(listOfScreenItemGroup);
			screenDesignRepository.multiInsertscreenItems(listOfScreenItemWithOutDepend);
		}
		screenDesign.setScreenAreas(allScreenArea.toArray(new ScreenArea[allScreenArea.size()]));
		return screenForm;
	}

	/**
	 * DungNN comment -> need improve performance
	 *
	 * @param screenDesignDefault
	 * @param screenDesign
	 * @param languageDesign
	 * @return
	 */
	private List<ScreenForm> populateListItemForCopyTemplate(ScreenDesignDefault screenDesignDefault, ScreenDesign screenDesign, LanguageDesign languageDesign) {
		List<ScreenArea> listAreaResults = new ArrayList<ScreenArea>();
		List<ScreenForm> listFormResults = new ArrayList<ScreenForm>();

		// get all areas by screen id
		Map<String, Object> sqlParamScreenId = new HashMap<String, Object>();
		sqlParamScreenId.put("screenId", screenDesignDefault.getTempScreenId());
		sqlParamScreenId.put("languageId", languageDesign.getLanguageId());
		sqlParamScreenId.put("projectId", workingProjectId);

		// if register view the
		Integer patternTypeOfTemp = 0;
		if (DbDomainConst.ScreenPatternType.VIEW.equals(screenDesign.getScreenPatternType())) {
			ScreenDesign sdTemp = screenDesignRepository.findById(screenDesignDefault.getTempScreenId(), languageDesign.getLanguageId(), workingProjectId);
			patternTypeOfTemp = sdTemp.getScreenPatternType();
		}

		List<MessageDesign> listOfMessage = new ArrayList<MessageDesign>();

		listAreaResults = screenAreaRepository.getScreenAreaByScreenId(sqlParamScreenId);

		int numArea = listAreaResults == null ? 0 : listAreaResults.size();
		for (int i = 0; i < numArea; i++) {
			ScreenArea screenAreaTemp = listAreaResults.get(i);
			// if register screen view from register/modify don't copy panel action
			if (DbDomainConst.ScreenPatternType.VIEW.equals(screenDesign.getScreenPatternType()) && !DbDomainConst.ScreenPatternType.VIEW.equals(patternTypeOfTemp) && DbDomainConst.AreaType.SUBMIT_ACTION.equals(screenAreaTemp.getAreaType())) {
				listAreaResults.remove(i);
				i--;
				numArea--;
				continue;
			}

			Map<String, Object> sqlParamScreenItemId = new HashMap<String, Object>();
			sqlParamScreenItemId.put("languageId", languageDesign.getLanguageId());
			sqlParamScreenItemId.put("screenAreaId", screenAreaTemp.getScreenAreaId());
			sqlParamScreenItemId.put("projectId", workingProjectId);
			// get all single items by area id
			List<ScreenItem> listItems = screenItemRepository.getScreenItemSingleByScreenAreaId(sqlParamScreenItemId);
			if (CollectionUtils.isNotEmpty(listItems)) {
				for (ScreenItem item : listItems) {
					// get all code list by screen item id
					item.setListScreenItemCodelists(screenItemCodelistRepository.getScreenItemCodelistByScreenItemId(item.getScreenItemId()));
					if (DbDomainConst.LogicDataType.LABEL.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.BUTTON.equals(item.getLogicalDataType()) && null != item.getMessageDesign().getMessageString()) {
						MessageDesign messageDesign = poplateMessageDesign(item.getMessageDesign().getMessageString(), screenDesignDefault, screenDesignDefault.getFlagMessageType(), MESSAGE_TYPE_SCREEN, languageDesign);
						listOfMessage.add(messageDesign);
						item.setMessageDesign(messageDesign);
					}
				}
				screenAreaTemp.setListItems(listItems);
			}
			// get all item sequences by area id
			screenAreaTemp.setListItemSequences(screenItemSequenceRepository.getScreenItemSequenceByScreenAreaId(screenAreaTemp.getScreenAreaId()));
			// get all screen group items by area id
			List<ScreenGroupItem> listScreenGroupItems = screenGroupItemRepository.getScreenGroupItemByScreenAreaId(screenAreaTemp.getScreenAreaId());
			if (CollectionUtils.isNotEmpty(listScreenGroupItems)) {
				for (ScreenGroupItem group : listScreenGroupItems) {
					Map<String, Object> sqlParam = new HashMap<String, Object>();
					sqlParam.put("screenAreaId", screenAreaTemp.getScreenAreaId());
					sqlParam.put("groupItemId", group.getGroupItemId());
					sqlParam.put("languageId", languageDesign.getLanguageId());
					sqlParam.put("projectId", workingProjectId);
					// get all items that were group item id
					List<ScreenItem> listItemsInGroup = screenItemRepository.getScreenItemGroupByScreenAreaId(sqlParam);
					if (CollectionUtils.isNotEmpty(listItemsInGroup)) {
						for (ScreenItem item : listItemsInGroup) {
							item.setListScreenItemCodelists(screenItemCodelistRepository.getScreenItemCodelistByScreenItemId(item.getScreenItemId()));
							if ((DbDomainConst.LogicDataType.LABEL.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.BUTTON.equals(item.getLogicalDataType())) && null != item.getMessageDesign() && null != item.getMessageDesign().getMessageString()) {
								MessageDesign messageDesign = poplateMessageDesign(item.getMessageDesign().getMessageString(), screenDesignDefault, screenDesignDefault.getFlagMessageType(), MESSAGE_TYPE_SCREEN, languageDesign);

								listOfMessage.add(messageDesign);
								item.setMessageDesign(messageDesign);
							}
						}
						group.setListItemsInGroup(listItemsInGroup);
					}
				}
				screenAreaTemp.setListGroupItems(listScreenGroupItems);
			}
		}
		listFormResults = screenFormRepository.getScreenFormByScreenId(screenDesignDefault.getTempScreenId());
		if (CollectionUtils.isNotEmpty(listFormResults)) {
			for (ScreenForm screenFormTemp : listFormResults) {
				screenFormTemp.setListScreenAreas(new ArrayList<ScreenArea>());
				if (numArea > 0) {
					for (ScreenArea screenAreaTemp : listAreaResults) {
						// only single/ list area are copied
						/* if (screenAreaTemp.getScreenFormId().equals(screenFormTemp.getScreenFormId()) && (screenAreaTemp.getAreaType() == 0 || screenAreaTemp.getAreaType() == 1)) { */
						if (screenAreaTemp.getScreenFormId().equals(screenFormTemp.getScreenFormId()) && !DbDomainConst.AreaType.HEADER_LINK.equals(screenAreaTemp.getAreaType())) {
							if (screenAreaTemp.getMessageDesign() != null && StringUtils.isNoneBlank(screenAreaTemp.getMessageDesign().getMessageString())) {
								MessageDesign messageDesign = poplateMessageDesign(screenAreaTemp.getMessageDesign().getMessageString(), screenDesignDefault, screenDesignDefault.getFlagMessageType(), MESSAGE_TYPE_SCREEN, languageDesign);

								listOfMessage.add(messageDesign);

								screenAreaTemp.setMessageDesign(messageDesign);
							}
							screenFormTemp.getListScreenAreas().add(screenAreaTemp);
						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(listOfMessage)) {
			messageDesignService.registerMessageDesign(listOfMessage);
		}

		return listFormResults;
	}

	/**
	 * if copy attribute from register/modify screen to view -> convert logical data type from input to text
	 *
	 * @param logicalDataType
	 * @return
	 */
	private Integer convertDataTypeForViewScreen(Integer logicalDataType) {
		if (null != logicalDataType && !DbDomainConst.LogicDataType.LABEL.equals(logicalDataType) && !DbDomainConst.LogicDataType.BUTTON.equals(logicalDataType) && !DbDomainConst.LogicDataType.LINK.equals(logicalDataType) && !DbDomainConst.LogicDataType.LINK_DYNAMIC.equals(logicalDataType)) {
			return DbDomainConst.LogicDataType.LABEL_DYNAMIC;
		}
		return logicalDataType;
	}

	/**
	 * copy screen function
	 * DungNN review -> need to improve performance
	 */
	private ScreenForm copyTemplateScreen(ScreenDesignDefault screenDesignDefault, List<ScreenForm> listFormResults, ScreenDesign screenDesign, LanguageDesign languageDesign) {
		ScreenForm screenForm = new ScreenForm();
		ScreenArea screenArea;
		if (null != listFormResults) {
			// loop screen list for generate screen items
			for (ScreenForm newScreenForm : listFormResults) {
				screenForm = populateScreenForm(SCREEN_FORM + screenDesignDefault.getModuleCode(), newScreenForm.getEnctypeType(), newScreenForm.getMethodType(), newScreenForm.getFormType(), screenDesign.getScreenId());
				screenForm.setScreenFormId(null);
				screenFormRepository.insertScreenForm(screenForm);
				if (CollectionUtils.isEmpty(newScreenForm.getListScreenAreas())) {
					continue;
				}

				for (ScreenArea newScreenArea : newScreenForm.getListScreenAreas()) {
					// Don't generate area submit action.
					if (DbDomainConst.AreaType.SUBMIT_ACTION.equals(newScreenArea.getAreaType())) {
						continue;
					}
					screenArea = populateScreenArea(screenForm, newScreenArea.getMessageDesign(), newScreenArea.getAreaType(), newScreenArea.getAreaPatternType(), newScreenArea.getAreaCode(), newScreenArea.getTotalElement(), newScreenArea.getColWidthUnit(), newScreenArea.getTotalCol(), newScreenArea.getTblWidthUnit(), newScreenArea.getTblHeaderRow(), newScreenArea.getAlignPositionType(), screenDesign.getScreenTypeName(), null);
					screenArea.setScreenAreaId(null);
					screenArea.setObjectMappingType(newScreenArea.getObjectMappingType());
					screenArea.setObjectMappingId(newScreenArea.getObjectMappingId());
					screenAreaRepository.insertScreenArea(screenArea);
					if (CollectionUtils.isNotEmpty(newScreenArea.getListItemSequences())) {
						for (ScreenItemSequence newScreenItemSeq : newScreenArea.getListItemSequences()) {
							// insert all item sequences of current area with copy information
							newScreenItemSeq.setScreenAreaId(screenArea.getScreenAreaId());
							screenItemSequenceRepository.insertScreenItemSequence(newScreenItemSeq);
						}
					}
					if (CollectionUtils.isNotEmpty(newScreenArea.getListItems())) {
						for (ScreenItem newScreenItem : newScreenArea.getListItems()) {
							// insert all single items of current area with copy information
							newScreenItem.setScreenActionId(null);
							newScreenItem.setScreenAreaId(screenArea.getScreenAreaId());
							newScreenItem.setScreenId(screenDesign.getScreenId());
							newScreenItem.setGroupItemId(null);
							newScreenItem.setScreenItemId(null);
							newScreenItem.setCreatedBy(accountId);
							newScreenItem.setCreatedDate(systemDate);
							newScreenItem.setUpdatedBy(accountId);
							newScreenItem.setUpdatedDate(systemDate);

							if (DbDomainConst.ScreenPatternType.VIEW.equals(screenDesign.getScreenPatternType())) {
								newScreenItem.setLogicalDataType(convertDataTypeForViewScreen(newScreenItem.getLogicalDataType()));
							}

							screenItemRepository.insertScreenItem(newScreenItem);
							if (CollectionUtils.isNotEmpty(newScreenItem.getListScreenItemCodelists())) {
								for (ScreenItemCodelist codelist : newScreenItem.getListScreenItemCodelists()) {
									// insert item code list of copied item with copy information
									codelist.setScreenItemId(newScreenItem.getScreenItemId());
									screenItemCodelistRepository.insertScreenItemCodelist(codelist);
								}
							}
							if (null != newScreenItem.getScreenItemValidation()) {
								// insert item validation of copied item
								newScreenItem.getScreenItemValidation().setScreenItemId(newScreenItem.getScreenItemId());
								screenItemValidationRepository.insertScreenItemValidation(newScreenItem.getScreenItemValidation());
							}
						}
					}
					if (CollectionUtils.isNotEmpty(newScreenArea.getListGroupItems())) {
						for (ScreenGroupItem newGroupItem : newScreenArea.getListGroupItems()) {
							// insert all group items of current area with copy information
							newGroupItem.setGroupItemId(null);
							newGroupItem.setScreenAreaId(screenArea.getScreenAreaId());
							screenGroupItemRepository.insertScreenGroupItem(newGroupItem);
							if (CollectionUtils.isNotEmpty(newGroupItem.getListItemsInGroup())) {

								for (ScreenItem newScreenItem : newGroupItem.getListItemsInGroup()) {
									// insert all items of current group with copy  information
									String messageCode = newScreenItem.getMessageDesign() != null ? newScreenItem.getMessageDesign().getMessageCode() : null;
									newScreenItem.setScreenActionId(null);
									newScreenItem.setScreenAreaId(screenArea.getScreenAreaId());
									newScreenItem.setScreenId(screenDesign.getScreenId());
									newScreenItem.setGroupItemId(newGroupItem.getGroupItemId());
									newScreenItem.setScreenItemId(null);
									newScreenItem.setCreatedBy(accountId);
									newScreenItem.setCreatedDate(systemDate);
									newScreenItem.setUpdatedBy(accountId);
									newScreenItem.setUpdatedDate(systemDate);
									newScreenItem.setMessageCode(messageCode);

									if (DbDomainConst.ScreenPatternType.VIEW.equals(screenDesign.getScreenPatternType()) && (null != screenDesignDefault.getCompletionType() || null != screenDesignDefault.getConfirmationType())) {
										newScreenItem.setLogicalDataType(convertDataTypeForViewScreen(newScreenItem.getLogicalDataType()));
									}

									screenItemRepository.insertScreenItem(newScreenItem);
									if (CollectionUtils.isNotEmpty(newScreenItem.getListScreenItemCodelists())) {
										for (ScreenItemCodelist codelist : newScreenItem.getListScreenItemCodelists()) {
											codelist.setScreenItemId(newScreenItem.getScreenItemId());
											screenItemCodelistRepository.insertScreenItemCodelist(codelist);
										}
									}
									if (null != newScreenItem.getScreenItemValidation()) {
										newScreenItem.getScreenItemValidation().setScreenItemId(newScreenItem.getScreenItemId());
										screenItemValidationRepository.insertScreenItemValidation(newScreenItem.getScreenItemValidation());
									}
								}
							}
						}
					}
				}
			}
		}
		return screenForm;
	}

	private Map<String, Object> populateMessageCommons(ScreenDesignDefault screenDesignDefault, LanguageDesign languageDesign) {
		Map<String, Object> messageCommons = new HashMap<String, Object>();

		List<MessageDesign> listMessageDesign = new ArrayList<MessageDesign>();

		MessageDesign messageDesign = new MessageDesign();

		messageDesign = poplateMessageDesign(MessageUtils.getMessage(MESSAGE_CODE_SEARCH) + SPACE + screenDesignDefault.getModuleName(), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
		listMessageDesign.add(messageDesign);
		messageCommons.put("linkGotoSearchScreen", messageDesign);

		messageDesign = poplateMessageDesign(MessageUtils.getMessage(MESSAGE_CODE_REGISTER) + SPACE + screenDesignDefault.getModuleName(), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
		listMessageDesign.add(messageDesign);
		messageCommons.put("linkGotoRegisterScreen", messageDesign);

		List<String> lstMessageCode = new ArrayList<String>();
		lstMessageCode.add(MESSAGE_CODE_ACTION);
		lstMessageCode.add(MESSAGE_CODE_BACK);
		lstMessageCode.add(MESSAGE_CODE_SAVE);
		lstMessageCode.add(MESSAGE_CODE_CONFIRM);
		lstMessageCode.add(MESSAGE_CODE_MODIFY);
		lstMessageCode.add(MESSAGE_CODE_SEARCH);
		lstMessageCode.add(MESSAGE_CODE_DELETE);
		lstMessageCode.add(MESSAGE_CODE_SEARCH_CONDITION);
		lstMessageCode.add(MESSAGE_CODE_SEARCH_RESULT);
		lstMessageCode.add(MESSAGE_CODE_SAVE);
		lstMessageCode.add(MESSAGE_CODE_MODIFY);
		lstMessageCode.add(MESSAGE_CODE_DELETE);
		lstMessageCode.add(MESSAGE_CONFIRM_SAVE);
		lstMessageCode.add(MESSAGE_CONFIRM_UPDATE);
		lstMessageCode.add(MESSAGE_CONFIRM_DELETE);

		List<MessageDesign> lstMessageDesignCommons = messageDesignRepository.findByMessageCodeOfProject(languageDesign.getLanguageId(), lstMessageCode, screenDesignDefault.getProjectId());
		if (FunctionCommon.isNotEmpty(lstMessageDesignCommons)) {
			for (MessageDesign md : lstMessageDesignCommons) {
				if (MESSAGE_CODE_BACK.equals(md.getMessageCode())) {
					messageCommons.put("btnBack", md);
				} else if (MESSAGE_CODE_ACTION.equals(md.getMessageCode())) {
					messageCommons.put("lblAction", md);
				} else if (MESSAGE_CODE_SAVE.equals(md.getMessageCode())) {
					messageCommons.put("btnSave", md);
				} else if (MESSAGE_CODE_CONFIRM.equals(md.getMessageCode())) {
					messageCommons.put("btnConfirm", md);
				} else if (MESSAGE_CODE_MODIFY.equals(md.getMessageCode())) {
					messageCommons.put("btnModify", md);
				} else if (MESSAGE_CODE_SEARCH.equals(md.getMessageCode())) {
					messageCommons.put("btnSearch", md);
				} else if (MESSAGE_CODE_DELETE.equals(md.getMessageCode())) {
					messageCommons.put("btnDelete", md);
				} else if (MESSAGE_CODE_SEARCH_CONDITION.equals(md.getMessageCode())) {
					messageCommons.put("searchCondition", md);
				} else if (MESSAGE_CODE_SEARCH_RESULT.equals(md.getMessageCode())) {
					messageCommons.put("searchResult", md);
				} else if (MESSAGE_CONFIRM_SAVE.equals(md.getMessageCode())) {
					messageCommons.put("saveConfirm", md);
				} else if (MESSAGE_CONFIRM_UPDATE.equals(md.getMessageCode())) {
					messageCommons.put("modifyConfirm", md);
				} else if (MESSAGE_CONFIRM_DELETE.equals(md.getMessageCode())) {
					messageCommons.put("deleteConfirm", md);
				}
			}
		}

		List<MessageDesign> filterMess = filterMessageCanUse(listMessageDesign, screenDesignDefault.getListMessRegisted(), null, messageCommons);
		if (FunctionCommon.isNotEmpty(filterMess)) {
			messageDesignService.registerMessageDesign(filterMess, false);
		}

		return messageCommons;
	}

	@Override
	public void generateSceenAndBlogic(Module generateScreen, CommonModel common) {

		ModuleTableMapping[] moduleTableMapping = generateScreen.getModuleTableMappings();

		systemDate = FunctionCommon.getCurrentTime();
		accountId = generateScreen.getCreatedBy();
		workingProjectId = generateScreen.getProjectId();

		if (ArrayUtils.isNotEmpty(moduleTableMapping)) {
			for (int i = 0; i < generateScreen.getModuleTableMappings().length; i++) {
				generateScreen.getModuleTableMappings()[i].setModuleId(generateScreen.getModuleId());
				// Setting user information/ first creation date
				generateScreen.getModuleTableMappings()[i].setCreatedBy(accountId);
				generateScreen.getModuleTableMappings()[i].setCreatedDate(systemDate);
				generateScreen.getModuleTableMappings()[i].setUpdatedBy(accountId);
				generateScreen.getModuleTableMappings()[i].setUpdatedDate(systemDate);
			}
			moduleTableMappingRepository.registerArray(generateScreen.getModuleTableMappings());
			// Convert module object before do generate operation
			ScreenDesignDefault screenDesignDefault = new ScreenDesignDefault();
			screenDesignDefault.toModule(generateScreen);
			// add suffix for name code
			screenDesignDefault.setSuffix(GenerateUniqueKey.generateRandomInteger());
			screenDesignDefault.setCreatedBy(accountId);
			screenDesignDefault.setCreatedDate(systemDate);
			screenDesignDefault.setProjectId(workingProjectId);
			screenDesignDefault.setProject(generateScreen.getProject());
			screenDesignDefault.setCommonModel(common);
			
			// Generate default screens for new module
			generateDefaultScreen(screenDesignDefault, false);
			// Generate default business logic design for new module
			generateDefaultBusinesslogic(screenDesignDefault);
		}
	}

	/**
	 * generate default screens for new module
	 */
	@Override
	public void generateDefaultScreen(ScreenDesignDefault screenDesignDefault, boolean generateFromTransition) {
		if (screenDesignDefault.getModuleTableMappings() != null && screenDesignDefault.getModuleTableMappings().length > 0) {
			ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings()[0];
			boolean errorFlag = false;
			if (LIST_TYPE.equals(firstTable.getTableMappingType())) {
				for (Integer screenType : screenDesignDefault.getScreenPatternTypes()) {
					if (!DbDomainConst.ScreenPatternType.SEARCH.equals(screenType)) {
						errorFlag = true;
						break;
					}
				}
			}
			if (errorFlag) {
				throw new BusinessException(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0087));
			}
		}
		
		List<ScreenDesign> listDefaultScreens = new ArrayList<ScreenDesign>();
		List<ScreenTransition> lstScreenTransitions = new ArrayList<ScreenTransition>();
		Map<ScreenTransition, ScreenItem> mapTransitionAndItem = new HashMap<ScreenTransition, ScreenItem>();
		List<ScreenItem> lstScreenItems = new ArrayList<ScreenItem>();
		List<ScreenItem> lstScreenItemSearchResults = new ArrayList<ScreenItem>();
		mScreenActionParam = new HashMap<String, List<ScreenActionParam>>();
		Long screenDesignViewId = new Long(0);
		Long screenDesignSearchId = new Long(0);
		Long registerScreenId = new Long(0);
		Long registerConfirmScreenId = new Long(0);
		Long registerCompleteScreenId = new Long(0);
		Long modifyScreenId = new Long(0);
		Long modifyConfirmScreenId = new Long(0);
		Long modifyCompleteScreenId = new Long(0);
		ScreenDesign screenDesign = new ScreenDesign();
		mapNavi = new HashMap<String, ScreenAction>();
		
		Project project = screenDesignDefault.getProject();
		AccountProfile accountProfile = systemService.getDefaultProfile();
		String dataType = String.valueOf(project.getDbType());
		Integer maxLengOfCode = accountProfile.getSqlCodeMaxLengthByDbType(dataType);
		
		GenerateUniqueKey generateUniqueKey = new GenerateUniqueKey(accountProfile.getNameMaxLength(), maxLengOfCode);

		ScreenForm screenForm = new ScreenForm();
		MessageDesign messageDesign;
		ScreenArea screenArea = null;
		ScreenAction screenAction;
		ScreenItemSequence screenItemSequence;
		String screenCode = screenDesignDefault.getScreenCode();
		String screenName = screenDesignDefault.getScreenName();
		Integer templateType = screenDesignDefault.getTemplateType();
		Long functionDesignId = screenDesignDefault.getFunctionDesignId();
		Integer newTemplateType = null;

		List<MessageDesign> listMessageToInsert = new ArrayList<MessageDesign>();
		Map<String, Object> messageCommons = new HashMap<String, Object>();
		List<ScreenForm> listFormResults = new ArrayList<ScreenForm>();

		LanguageDesign languageDesign = screenDesignDefault.getLanguageDesign();
		workingProjectId = screenDesignDefault.getProjectId();
		systemDate = screenDesignDefault.getCreatedDate();
		accountId = screenDesignDefault.getCreatedBy();

		// get all keys
		allKeys = tableDesignKeyRepository.findKeyItem(workingProjectId);

		screenDesignDefault.setLanguageDesign(languageDesign);
		
		List<MessageDesign> listMessRegisted = messageDesignRepository.getMessageDesignRegisted(workingProjectId, screenDesignDefault.getModuleId(), languageDesign.getLanguageId());
		screenDesignDefault.setListMessRegisted(listMessRegisted);
		
		if (null != screenDesignDefault && null != screenDesignDefault.getScreenPatternTypes() && screenDesignDefault.getScreenPatternTypes().length > 0 && null != languageDesign) {

			messageCommons = populateMessageCommons(screenDesignDefault, languageDesign);
			Integer maxYcoordinate = 0;
			if (screenDesignDefault.getModuleId() != null) {
				List<ScreenDesign> lstScreenDesignOfThisModule = screenDesignRepository.getAllScreenInfoByModuleId(screenDesignDefault.getModuleId(), null);
				for (ScreenDesign sd : lstScreenDesignOfThisModule) {
					if (maxYcoordinate.intValue() < sd.getyCoordinate().intValue()) {
						maxYcoordinate = sd.getyCoordinate();
					}
				}
			}

			Long tempFunctionId = 0l;
			List<FunctionDesign> lstFunctionDesign = new ArrayList<FunctionDesign>();
			for (Integer screenType : screenDesignDefault.getScreenPatternTypes()) {
				if(isGenerateScreen(screenDesignDefault, screenType)){
					if (DbDomainConst.ScreenPatternType.SEARCH.equals(screenType)) {
						if (functionDesignId == null) {
							FunctionDesign functionDesign = GenerateScreenContruct.populateFunctionDesign(tempFunctionId, screenDesignDefault, MessageUtils.getMessage(ScreenDesignConst.SC_SCREEN_DESIGN_0310), BusinessDesignConst.PATTERN_SEARCH);
							lstFunctionDesign.add(functionDesign);
						}
						if (null == templateType) {
							newTemplateType = 1;
						} else {
							newTemplateType = templateType;
						}

						// insert message for search screen
						if (StringUtils.isEmpty(screenName)) {
							messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(MessageUtils.getMessage(MESSAGE_CODE_SEARCH), screenDesignDefault.getModuleName() + SCREEN, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
						} else {
							messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(screenName, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
						}

						listMessageToInsert.add(messageDesign);
						// insert search screen
						if (StringUtils.isEmpty(screenCode)) {
							if (maxYcoordinate != 0) {
								maxYcoordinate = maxYcoordinate + 100;
							}
							screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForAuto(SEARCH, StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForAutoWithOutSeparate(SEARCH + screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), screenType, newTemplateType, 350, 60, maxYcoordinate, null, false, false, tempFunctionId);
						} else {
							if (maxYcoordinate != 0) {
								maxYcoordinate = maxYcoordinate + 100;
							}
							screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForManual(screenCode, screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForManualWithOutSeparate(SEARCH + screenCode, screenDesignDefault.getSuffix()), screenType, newTemplateType, 350, 60, maxYcoordinate, screenCode, false, false, tempFunctionId);
						}
						screenDesign.setScreenTypeName(ScreenDesignConst.SEARCH_SCREEN);
						// add search screen to screen list for generate screen  items
						listDefaultScreens.add(screenDesign);
					} else if (DbDomainConst.ScreenPatternType.REGISTER.equals(screenType)) {
						if (functionDesignId == null) {
							FunctionDesign functionDesign = GenerateScreenContruct.populateFunctionDesign(tempFunctionId, screenDesignDefault, MessageUtils.getMessage(ScreenDesignConst.SC_SCREEN_DESIGN_0309), BusinessDesignConst.PATTERN_REGISTER);
							lstFunctionDesign.add(functionDesign);
						}
						if (null == templateType) {
							newTemplateType = 1;
						} else {
							newTemplateType = templateType;
						}
						// insert message for register screen
						if (StringUtils.isEmpty(screenName)) {
							messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(MessageUtils.getMessage(MESSAGE_CODE_REGISTER), screenDesignDefault.getModuleName() + SCREEN, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
						} else {
							messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(screenName, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
						}

						listMessageToInsert.add(messageDesign);
						// insert register screen
						if (StringUtils.isEmpty(screenCode)) {
							if (maxYcoordinate != 0) {
								maxYcoordinate = maxYcoordinate + 100;
							}
							screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForAuto(REGISTER, screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForAutoWithOutSeparate(REGISTER + screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), screenType, newTemplateType, 10, 60, maxYcoordinate, null, false, false, tempFunctionId);
						} else {
							if (maxYcoordinate != 0) {
								maxYcoordinate = maxYcoordinate + 100;
							}
							screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForManual(screenCode, screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForManualWithOutSeparate(REGISTER + screenCode, screenDesignDefault.getSuffix()), screenType, newTemplateType, 10, 60, maxYcoordinate, screenCode, false, false, tempFunctionId);
						}
						screenDesign.setScreenTypeName(ScreenDesignConst.REGISTER_SCREEN);
						// add register screen to screen list for generate screen items
						listDefaultScreens.add(screenDesign);
						
						if (null != screenDesignDefault.getCompletionType() && ScreenDesignConst.CompleteType.SCREEN.equals(screenDesignDefault.getCompletionType())) {
							// insert message for register complete screen
							if (StringUtils.isEmpty(screenName)) {
								messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(MessageUtils.getMessage(MESSAGE_CODE_COMPLETE) + SPACE + WordUtils.uncapitalize(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0078)), screenDesignDefault.getModuleName() + SCREEN, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
							} else {
								messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(MessageUtils.getMessage(MESSAGE_CODE_COMPLETE) + SPACE + WordUtils.uncapitalize(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0078)) + screenName, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
							}

							listMessageToInsert.add(messageDesign);
							// insert register complete screen
							if (StringUtils.isEmpty(screenCode)) {
								if (maxYcoordinate != 0) {
									maxYcoordinate = maxYcoordinate + 100;
								}
								screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForAuto(COMPLETE + WordUtils.capitalize(REGISTER), screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForAutoWithOutSeparate("completeregister" + screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), 3, newTemplateType, 10, 380, maxYcoordinate, null, false, true, tempFunctionId);
							} else {
								if (maxYcoordinate != 0) {
									maxYcoordinate = maxYcoordinate + 100;
								}
								screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForManual(COMPLETE + WordUtils.capitalize(REGISTER), screenCode, screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForManualWithOutSeparate("completeregister" + screenCode, screenDesignDefault.getSuffix()), 3, newTemplateType, 10, 380, maxYcoordinate, screenCode, false, true, tempFunctionId);
							}

							screenDesign.setScreenTypeName(ScreenDesignConst.COMPLETE_REGISTER_SCREEN);
							// add register complete screen to screen list for
							// generate screen items
							listDefaultScreens.add(screenDesign);
						}

						if (null != screenDesignDefault.getConfirmationType() && DbDomainConst.ScreenPatternType.REGISTER.equals(screenDesignDefault.getConfirmationType())) {
							// insert message for register confirm screen
							if (StringUtils.isEmpty(screenName)) {
								messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(MessageUtils.getMessage(MESSAGE_CODE_CONFIRM) + SPACE + WordUtils.uncapitalize(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0078)), screenDesignDefault.getModuleName() + SCREEN, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
							} else {
								messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(MessageUtils.getMessage(MESSAGE_CODE_CONFIRM) + SPACE + WordUtils.uncapitalize(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0078)) + screenName, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
							}

							listMessageToInsert.add(messageDesign);
							// insert register confirm screen
							if (StringUtils.isEmpty(screenCode)) {
								if (maxYcoordinate != 0) {
									maxYcoordinate = maxYcoordinate + 100;
								}
								screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForAuto(CONFIRM + WordUtils.capitalize(REGISTER), screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForAutoWithOutSeparate("confirmregister" + screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), 3, newTemplateType, 10, 250, maxYcoordinate, null, true, false, tempFunctionId);
							} else {
								if (maxYcoordinate != 0) {
									maxYcoordinate = maxYcoordinate + 100;
								}
								screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForManual(CONFIRM + WordUtils.capitalize(REGISTER), screenCode, screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForManualWithOutSeparate("confirmregister" + screenCode, screenDesignDefault.getSuffix()), 3, newTemplateType, 10, 250, maxYcoordinate, screenCode, true, false, tempFunctionId);
							}

							screenDesign.setScreenTypeName(ScreenDesignConst.CONFIRM_REGISTER_SCREEN);
							// add register confirm screen to screen list for generate screen items
							listDefaultScreens.add(screenDesign);
						}
					} else if (DbDomainConst.ScreenPatternType.MODIFY.equals(screenType)) {
						if (functionDesignId == null) {
							FunctionDesign functionDesign = GenerateScreenContruct.populateFunctionDesign(tempFunctionId, screenDesignDefault, MessageUtils.getMessage(ScreenDesignConst.SC_SCREEN_DESIGN_0312), BusinessDesignConst.PATTERN_MODIFY);
							lstFunctionDesign.add(functionDesign);
						}
						if (null == templateType) {
							newTemplateType = 1;
						} else {
							newTemplateType = templateType;
						}

						// insert message for modify screen
						if (StringUtils.isEmpty(screenName)) {
							messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(MessageUtils.getMessage(MESSAGE_CODE_MODIFY), screenDesignDefault.getModuleName() + SCREEN, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
						} else {
							messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(screenName, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
						}

						listMessageToInsert.add(messageDesign);
						// insert modify screen
						if (StringUtils.isEmpty(screenCode)) {
							if (maxYcoordinate != 0) {
								maxYcoordinate = maxYcoordinate + 100;
							}
							screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForAuto(MODIFY, screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForAutoWithOutSeparate(MODIFY + screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), screenType, newTemplateType, 650, 60, maxYcoordinate, null, false, false, tempFunctionId);
						} else {
							if (maxYcoordinate != 0) {
								maxYcoordinate = maxYcoordinate + 100;
							}
							screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForManual(screenCode, screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForManualWithOutSeparate(MODIFY + screenCode, screenDesignDefault.getSuffix()), screenType, newTemplateType, 650, 60, maxYcoordinate, screenCode, false, false, tempFunctionId);
						}
						screenDesign.setScreenTypeName(ScreenDesignConst.MODIFY_SCREEN);
						// add modify screen to screen list for generate screen items
						listDefaultScreens.add(screenDesign);
						
						if (null != screenDesignDefault.getCompletionType() && ScreenDesignConst.CompleteType.SCREEN.equals(screenDesignDefault.getCompletionType())) {
							// insert message for modify complete screen
							if (StringUtils.isEmpty(screenName)) {
								messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(MessageUtils.getMessage(MESSAGE_CODE_COMPLETE) + SPACE + WordUtils.uncapitalize(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0079)), screenDesignDefault.getModuleName() + SCREEN, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
							} else {
								messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(MessageUtils.getMessage(MESSAGE_CODE_COMPLETE) + SPACE + WordUtils.uncapitalize(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0079)), screenName, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
							}

							listMessageToInsert.add(messageDesign);
							// insert modify complete screen
							if (StringUtils.isEmpty(screenCode)) {
								if (maxYcoordinate != 0) {
									maxYcoordinate = maxYcoordinate + 100;
								}
								screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForAuto(COMPLETE + WordUtils.capitalize(MODIFY), screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForAutoWithOutSeparate("completemodify" + screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), 3, newTemplateType, 650, 380, maxYcoordinate, null, false, true, tempFunctionId);
							} else {
								if (maxYcoordinate != 0) {
									maxYcoordinate = maxYcoordinate + 100;
								}
								screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForManual(COMPLETE + WordUtils.capitalize(MODIFY), screenCode, screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForManualWithOutSeparate("completemodify" + screenCode, screenDesignDefault.getSuffix()), 3, newTemplateType, 650, 380, maxYcoordinate, screenCode, false, true, tempFunctionId);
							}
							screenDesign.setScreenTypeName(ScreenDesignConst.COMPLETE_MODIFY_SCREEN);
							// add modify complete screen to screen list for generate screen items
							listDefaultScreens.add(screenDesign);
						}
						if (null != screenDesignDefault.getConfirmationType() && ScreenDesignConst.ConfirmType.SCREEN.equals(screenDesignDefault.getConfirmationType())) {
							// insert message for modify confirm screen
							if (StringUtils.isEmpty(screenName)) {
								messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(MessageUtils.getMessage(MESSAGE_CODE_CONFIRM) + SPACE + WordUtils.uncapitalize(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0079)), screenDesignDefault.getModuleName() + SCREEN, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
							} else {
								messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(MessageUtils.getMessage(MESSAGE_CODE_CONFIRM) + SPACE + WordUtils.uncapitalize(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0079)) + screenName, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
							}

							listMessageToInsert.add(messageDesign);
							// insert modify confirm screen
							if (StringUtils.isEmpty(screenCode)) {
								if (maxYcoordinate != 0) {
									maxYcoordinate = maxYcoordinate + 100;
								}
								screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForAuto(CONFIRM + WordUtils.capitalize(MODIFY), screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForAutoWithOutSeparate("confirmmodify" + screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), 3, newTemplateType, 650, 250, maxYcoordinate, null, true, false, tempFunctionId);
							} else {
								if (maxYcoordinate != 0) {
									maxYcoordinate = maxYcoordinate + 100;
								}
								screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForManual(CONFIRM + WordUtils.capitalize(MODIFY), screenCode, screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForManualWithOutSeparate("confirmmodify" + screenCode, screenDesignDefault.getSuffix()), 3, newTemplateType, 650, 250, maxYcoordinate, screenCode, true, false, tempFunctionId);
							}

							screenDesign.setScreenTypeName(ScreenDesignConst.CONFIRM_MODIFY_SCREEN);
							// add modify confirm screen to screen list for generate screen items
							listDefaultScreens.add(screenDesign);
						}
					} else if (DbDomainConst.ScreenPatternType.VIEW.equals(screenType)) {
						if (functionDesignId == null) {
							FunctionDesign functionDesign = GenerateScreenContruct.populateFunctionDesign(tempFunctionId, screenDesignDefault, MessageUtils.getMessage(ScreenDesignConst.SC_SCREEN_DESIGN_0311), BusinessDesignConst.PATTERN_VIEW);
							lstFunctionDesign.add(functionDesign);
						}
						if (null == templateType) {
							newTemplateType = 2;
						} else {
							newTemplateType = templateType;
						}
						// insert message for view screen
						if (StringUtils.isEmpty(screenName)) {
							messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(MessageUtils.getMessage(MESSAGE_CODE_VIEW), screenDesignDefault.getModuleName() + SCREEN, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
						} else {
							messageDesign = poplateMessageDesign(generateUniqueKey.calculateName(screenName, screenDesignDefault.getSuffix()), screenDesignDefault, DbDomainConst.MessageLevel.MODULE, MESSAGE_TYPE_SCREEN, languageDesign);
						}

						listMessageToInsert.add(messageDesign);
						// insert view screen
						if (StringUtils.isEmpty(screenCode)) {
							if (maxYcoordinate != 0) {
								maxYcoordinate = maxYcoordinate + 100;
							}
							screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForAuto(VIEW, screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForAutoWithOutSeparate(VIEW + screenDesignDefault.getModuleCode(), screenDesignDefault.getSuffix()), screenType, newTemplateType, 350, 250, maxYcoordinate, null, false, false, tempFunctionId);
						} else {
							if (maxYcoordinate != 0) {
								maxYcoordinate = maxYcoordinate + 100;
							}
							screenDesign = populateScreenDesign(screenDesignDefault, generateUniqueKey.calculateCodeForManual(screenCode, screenDesignDefault.getSuffix()), messageDesign, generateUniqueKey.calculateCodeForManualWithOutSeparate(VIEW + screenCode, screenDesignDefault.getSuffix()), screenType, newTemplateType, 350, 250, maxYcoordinate, screenCode, false, false, tempFunctionId);
						}

						screenDesign.setScreenTypeName(ScreenDesignConst.VIEW_SCREEN);
						// add view screen to screen list for generate screen items
						listDefaultScreens.add(screenDesign);
					}
					tempFunctionId++;
				}
			}
			// get latest seq from function design
			Map<Long, Long> mapFunctionId = new HashMap<Long, Long>();
			if (FunctionCommon.isNotEmpty(lstFunctionDesign)) {
				Long lastSeqFunction = functionDesignRepository.getSequencesFunctionDesign(lstFunctionDesign.size() - 1);
				Long startSeqFunction = lastSeqFunction - (lstFunctionDesign.size() - 1);
				for (FunctionDesign fd : lstFunctionDesign) {
					mapFunctionId.put(fd.getFunctionId(), startSeqFunction);
					fd.setFunctionId(startSeqFunction++);
				}
				functionDesignRepository.registerLstFunctionDesign(lstFunctionDesign);
			}

			// get latest seq from screen design
			int size = listDefaultScreens.size();
			Long sequence = screenDesignRepository.screenDesignGetSequences(size - 1);
			Long startSeq = sequence - (size - 1);
			for (ScreenDesign screenDesignToInsert : listDefaultScreens) {
				screenDesignToInsert.setScreenId(startSeq++);
				if (functionDesignId == null) {
					screenDesignToInsert.setFunctionDesignId(mapFunctionId.get(screenDesignToInsert.getFunctionDesignId()));
				} else {
					screenDesignToInsert.setFunctionDesignId(functionDesignId);
				}
			}

			// insert list Message code to message design
			List<MessageDesign> filterMess = filterMessageCanUse(listMessageToInsert, screenDesignDefault.getListMessRegisted(), listDefaultScreens, null);
			messageDesignService.registerMessageDesign(filterMess, false);
			// insert list Screen design
			screenDesignRepository.insertScreenDesign(listDefaultScreens);

			screenDesignDefault.setMapScreenDesign(new HashMap<String, ScreenDesign>());
			for (ScreenDesign screenDesignItem : listDefaultScreens) {
				screenDesignDefault.getMapScreenDesign().put(screenDesignItem.getScreenTypeName(), screenDesignItem);
				switch (screenDesignItem.getScreenTypeName()) {
					case ScreenDesignConst.REGISTER_SCREEN:
						registerScreenId = screenDesignItem.getScreenId();
						break;
					case ScreenDesignConst.CONFIRM_REGISTER_SCREEN:
						registerConfirmScreenId = screenDesignItem.getScreenId();
						break;
					case ScreenDesignConst.COMPLETE_REGISTER_SCREEN:
						registerCompleteScreenId = screenDesignItem.getScreenId();
						break;
					case ScreenDesignConst.SEARCH_SCREEN:
						screenDesignSearchId = screenDesignItem.getScreenId();
						break;
					case ScreenDesignConst.MODIFY_SCREEN:
						modifyScreenId = screenDesignItem.getScreenId();
						break;
					case ScreenDesignConst.CONFIRM_MODIFY_SCREEN:
						modifyConfirmScreenId = screenDesignItem.getScreenId();
						break;
					case ScreenDesignConst.COMPLETE_MODIFY_SCREEN:
						modifyCompleteScreenId = screenDesignItem.getScreenId();
						break;
					case ScreenDesignConst.VIEW_SCREEN:
						screenDesignViewId = screenDesignItem.getScreenId();
						break;
					default:
						break;
				}
			}

			screenDesignDefault.setLstScreenDesign(listDefaultScreens);
			// populate message code for each items
			if (null != screenDesignDefault.getIsCopy() && screenDesignDefault.getIsCopy()) {
				listFormResults = populateListItemForCopyTemplate(screenDesignDefault, screenDesign, languageDesign);
			} else {
				populateListModuleTableMapping(screenDesignDefault, languageDesign);
			}

			List<ScreenItem> listOfScreenItemForBatch = new ArrayList<ScreenItem>();

			// loop screen list for generate screen items
			mapScreenItemHidden = new HashMap<String, List<ScreenItem>>();

			Integer confirmType = screenDesignDefault.getConfirmationType();
			Boolean flgEnableConfirm = false;
			if(confirmType == null) {
				flgEnableConfirm = false;
			} else {
				if(ScreenDesignConst.ConfirmType.NONE.equals(confirmType) || ScreenDesignConst.ConfirmType.SCREEN.equals(confirmType)) {
					flgEnableConfirm = false;
				} else if(ScreenDesignConst.ConfirmType.MESSAGE.equals(confirmType)) {
					flgEnableConfirm  = true;
				}
			}
			
			Map<Long, List<ScreenItem>> mapScreenAndUserdefineCL = new HashMap<Long, List<ScreenItem>>();
			screenDesignDefault.setMapFormOfScreen(new HashMap<Long, ScreenForm>());
			for (ScreenDesign screen : listDefaultScreens) {
				String screenTypeName = screen.getScreenTypeName();
				if (null != screenDesignDefault.getIsCopy() && screenDesignDefault.getIsCopy()) {
					screenForm = copyTemplateScreen(screenDesignDefault, listFormResults, screen, languageDesign);
				} else {
					screenForm = generateScreenItemDefault(screenDesignDefault, screen, screenDesignSearchId, screenDesignViewId, modifyScreenId, languageDesign, messageCommons, lstScreenItems, lstScreenItemSearchResults, mapScreenAndUserdefineCL, lstScreenTransitions, mapTransitionAndItem);
				}
				if(!generateFromTransition) {
					if (null != screenForm.getScreenFormId()) {
						screen.setFirstFormOfScreen(screenForm.getScreenFormId());
						// insert submit area for register screen
						if (DbDomainConst.ScreenPatternType.REGISTER.equals(screen.getScreenPatternType())) {
							// insert register submit area
							String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, SAVE_REGISTER +  StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							screenArea = populateScreenArea(screenForm, null, 2, null, areaCode, 0, "100%", 1, "100%", 1, 1, screenTypeName, null);
							screenAreaRepository.insertScreenArea(screenArea);
							Integer maxItemSeqNo = -1;
							Long toScreen = new Long(0);
							if (registerConfirmScreenId == 0 && registerCompleteScreenId == 0) {
								if (screenDesignSearchId != 0) {
									toScreen = screenDesignSearchId;
								} else {
									toScreen = screen.getScreenId();
								}
							}
							if (registerConfirmScreenId != 0) {
								toScreen = registerConfirmScreenId;
							}
							if (registerConfirmScreenId == 0 && registerCompleteScreenId != 0) {
								toScreen = registerCompleteScreenId;
							}
							// insert register save action
							screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0059), screen.getScreenId(), toScreen, ScreenActionConst.SUBMIT_METHOD_TYPE_POST);
							screenActionRepository.insertScreenAction(screenAction);
							
							if (ScreenDesignConst.ConfirmType.SCREEN.equals(screenDesignDefault.getConfirmationType())) {
								mapNavi.put(REGISTER_NAVIGATE, screenAction);
							}else{
								mapNavi.put(REGISTER_COMFIRM_NAVIGATE, screenAction);
							}
							maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
							// insert button save for register screen
							ScreenItem itemBtnSave;
							if (!flgEnableConfirm) {
								itemBtnSave = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "btnSaveRegister" + screenDesignDefault.getModuleCode(), (MessageDesign) messageCommons.get("btnSave"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.BUTTON, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, ScreenDesignConst.EnableConfirm.DISABLE_CONFIRM, null, ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE);
							} else {
								MessageDesign messageConfirmSave = (MessageDesign) messageCommons.get("saveConfirm");
								itemBtnSave = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "btnSaveRegister" + screenDesignDefault.getModuleCode(), (MessageDesign) messageCommons.get("btnSave"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.BUTTON, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM, messageConfirmSave, ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE);
							}
							listOfScreenItemForBatch.add(itemBtnSave);
							
							// insert screen transition
							String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, SAVE_REGISTER +  StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							ScreenTransition screenTransition = populateScreenTransition(transitionCode, screen.getScreenId(), toScreen, screenDesignDefault.getModuleId(), false);
							lstScreenTransitions.add(screenTransition);
							mapTransitionAndItem.put(screenTransition, itemBtnSave);
						}
						// insert submit area for modify screen
						else if (DbDomainConst.ScreenPatternType.MODIFY.equals(screen.getScreenPatternType())) {
							// insert modify submit area
							String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, SAVE_MODIFY +  StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							screenArea = populateScreenArea(screenForm, null, 2, null, areaCode, 1, "100%", 1, "100%", 1, 1, screenTypeName, null);
							screenAreaRepository.insertScreenArea(screenArea);
							Integer maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
							Long toScreen = new Long(0);
							if (modifyConfirmScreenId == 0 && modifyCompleteScreenId == 0) {
								if (screenDesignSearchId != 0) {
									toScreen = screenDesignSearchId;
								} else {
									toScreen = screen.getScreenId();
								}
							}
							if (modifyConfirmScreenId != 0) {
								toScreen = modifyConfirmScreenId;
							}
							if (modifyConfirmScreenId == 0 && modifyCompleteScreenId != 0) {
								toScreen = modifyCompleteScreenId;
							}
							// insert modify save action
							screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0059), screen.getScreenId(), toScreen, ScreenActionConst.SUBMIT_METHOD_TYPE_POST);
							screenActionRepository.insertScreenAction(screenAction);
							if (ScreenDesignConst.ConfirmType.SCREEN.equals(screenDesignDefault.getConfirmationType())) {
								mapNavi.put(MODIFY_NAVIGATE, screenAction);
							}else{
								mapNavi.put(MODIFY_COMFIRM_NAVIGATE, screenAction);
							}
							maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
							ScreenItem screenItem;
							if (!flgEnableConfirm) {
								// insert button save for modify screen
								screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "btnSaveModify" + screenDesignDefault.getModuleCode(), (MessageDesign) messageCommons.get("btnSave"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.BUTTON, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, ScreenDesignConst.EnableConfirm.DISABLE_CONFIRM, null, ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE);
							} else {
								// insert button save for modify screen
								MessageDesign messageConfirmModify = (MessageDesign) messageCommons.get("modifyConfirm");
								screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "btnSaveModify" + screenDesignDefault.getModuleCode(), (MessageDesign) messageCommons.get("btnSave"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.BUTTON, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM, messageConfirmModify, ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE);
							}
							listOfScreenItemForBatch.add(screenItem);
							
							// insert parameter
							insertParameterScreen(screen, screenDesignDefault, lstScreenItems);
							
							// insert screen transition
							String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, SAVE_MODIFY +  StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							ScreenTransition screenTransition = populateScreenTransition(transitionCode, screen.getScreenId(), toScreen, screenDesignDefault.getModuleId(), false);
							lstScreenTransitions.add(screenTransition);
							mapTransitionAndItem.put(screenTransition, screenItem);
						}
						
						// insert submit area for view screen
						if (DbDomainConst.ScreenPatternType.VIEW.equals(screen.getScreenPatternType())) {
							Integer maxItemSeqNo = -1;
							if (screenDesignViewId == screen.getScreenId()) {
								// insert screen area
								String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, MODIFY_DELETE_VIEW +  StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
								screenArea = populateScreenArea(screenForm, null, 2, null, areaCode, 1, "100%", 1, "100%", 1, 1, screenTypeName, null);
								screenAreaRepository.insertScreenArea(screenArea);
								
								// insert parameter
								insertParameterScreen(screen, screenDesignDefault, lstScreenItems);
								buildItemHidden(screen, screenDesignDefault, lstScreenItems);
								
								if (modifyScreenId != 0) {
									// insert screen action
									screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0071), screen.getScreenId(), modifyScreenId, ScreenActionConst.SUBMIT_METHOD_TYPE_GET);
									screenActionRepository.insertScreenAction(screenAction);
									screenDesignDefault.setScreenActionNavigator(screenAction);
									mapNavi.put(MODIFY_VIEW, screenAction);
									List<ScreenActionParam> lstScreenActionParam = GenerateScreenContruct.populateScreenActionParam(screenDesignDefault, screen, lstScreenItems, screenForm, mapScreenItemHidden, screenAction);
									if (lstScreenActionParam.size() > 0) {
										Long seq = screenActionParamRepository.getSequencesScreenActionParam(lstScreenActionParam.size() - 1);
										Long startSeqActionParam = seq - (lstScreenActionParam.size() - 1);
										for (ScreenActionParam actionParam : lstScreenActionParam) {
											actionParam.setScreenActionParamId(startSeqActionParam++);
										}
										screenActionParamRepository.registerListScreenActionParam(lstScreenActionParam);
										mScreenActionParam.put(SCREEN_ACTION_PARAM_VIEW_TO_MODIFY, lstScreenActionParam);
									}
									maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
									// insert button modify for view screen
									ScreenItem screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "btnModifyView" + screenDesignDefault.getModuleCode(), (MessageDesign) messageCommons.get("btnModify"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.BUTTON, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, null, null, ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE);
									listOfScreenItemForBatch.add(screenItem);
									
									// insert screen transition
									String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, VIEW_TO_MODIFY +  StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
									ScreenTransition screenTransition = populateScreenTransition(transitionCode, screen.getScreenId(), modifyScreenId, screenDesignDefault.getModuleId(), false);
									lstScreenTransitions.add(screenTransition);
									mapTransitionAndItem.put(screenTransition, screenItem);
								}
								// insert screen action
								screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0072), screen.getScreenId(), null, ScreenActionConst.SUBMIT_METHOD_TYPE_POST);
								// TrungDV : not insert now, insert when have create blogic process delete
								// screenActionRepository.insertScreenAction(screenAction);
								screenDesignDefault.setScreenActionDelete(screenAction);
								mapNavi.put(DELETE_NAVIGATE, screenAction);
								setDataOfActionDelete(screenDesignDefault, lstScreenItems, screenForm, screenArea, screenAction);
								
								maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
								// insert screen item sequence for corresponding screen item
								screenItemSequence = populateScreenItemSequence(1, maxItemSeqNo, screenArea.getScreenAreaId());
								screenItemSequenceRepository.insertScreenItemSequence(screenItemSequence);
								// insert button delete for view screen
								Boolean flagConfirm = false;
								if (ScreenDesignConst.ConfirmType.MESSAGE.equals(screenDesignDefault.getConfirmationType()) || ScreenDesignConst.ConfirmType.SCREEN.equals(screenDesignDefault.getConfirmationType())) {
									flagConfirm = true;
								}
								
								ScreenItem screenItem;
								if(!flagConfirm) {
									screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "btnDeleteView" + screenDesignDefault.getModuleCode(), (MessageDesign) messageCommons.get("btnDelete"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.BUTTON, null, ITEM_TYPE_NORMAL, ScreenDesignConst.EnableConfirm.DISABLE_CONFIRM, null, ScreenDesignConst.ButtonType.BUTTON_TYPE_DELETE);
									// TRUNGDV : not insert now, insert when have create  blogic process delete
									// screenItemRepository.insertScreenItem(screenItem);
									screenDesignDefault.setScreenItemButtonDelete(screenItem);
								} else {
									MessageDesign messageConfirmDelete = (MessageDesign) messageCommons.get("deleteConfirm");
									screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "btnDeleteView" + screenDesignDefault.getModuleCode(), (MessageDesign) messageCommons.get("btnDelete"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.BUTTON, null, ITEM_TYPE_NORMAL, ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM, messageConfirmDelete, ScreenDesignConst.ButtonType.BUTTON_TYPE_DELETE);
									// TRUNGDV : not insert now, insert when have create  blogic process delete
									screenDesignDefault.setScreenItemButtonDelete(screenItem);
								}
								
								// insert screen transition
								String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, VIEW_TO_DELETE + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
								ScreenTransition screenTransition = populateScreenTransition(transitionCode, screen.getScreenId(), -1L, screenDesignDefault.getModuleId(), true);
								lstScreenTransitions.add(screenTransition);
								mapTransitionAndItem.put(screenTransition, screenItem);
							}
							if (registerConfirmScreenId == screen.getScreenId()) {
								// insert screen area
								String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, BACK_CONFIRM_CONFIRM_REGISTER +  StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
								screenArea = populateScreenArea(screenForm, null, 2, null, areaCode, 1, "100%", 1, "100%", 1, 1, screenTypeName, null);
								screenAreaRepository.insertScreenArea(screenArea);
								maxItemSeqNo = -1;
								
								// insert parameter
								insertParameterScreen(screen, screenDesignDefault, lstScreenItems);
								
								Long toScreen = new Long(0);
								if (registerCompleteScreenId != 0) {
									toScreen = registerCompleteScreenId;
								} else {
									if (screenDesignSearchId != 0) {
										toScreen = screenDesignSearchId;
									} else {
										toScreen = screen.getScreenId();
									}
								}
								// insert screen action
								screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0073), screen.getScreenId(), toScreen, ScreenActionConst.SUBMIT_METHOD_TYPE_POST);
								screenActionRepository.insertScreenAction(screenAction);
								mapNavi.put(REGISTER_COMFIRM_NAVIGATE, screenAction);
								maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
								// insert button confirm for modify screen
								MessageDesign messageSaveConfirm = (MessageDesign) messageCommons.get("saveConfirm");
								ScreenItem screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "btnConfirmConfirmRegister" + screenDesignDefault.getModuleCode(), (MessageDesign) messageCommons.get("btnConfirm"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.BUTTON, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM, messageSaveConfirm, ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE);
								listOfScreenItemForBatch.add(screenItem);
								
								// insert screen transition
								String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, CONFIRM_REGISTER_SUCCESS + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
								ScreenTransition screenTransition = populateScreenTransition(transitionCode, screen.getScreenId(), toScreen, screenDesignDefault.getModuleId(), false);
								lstScreenTransitions.add(screenTransition);
								mapTransitionAndItem.put(screenTransition, screenItem);
								
								// insert screen action
								screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0074), screen.getScreenId(), registerScreenId, ScreenActionConst.SUBMIT_METHOD_TYPE_POST);
								screenActionRepository.insertScreenAction(screenAction);
								mapNavi.put(BACK_REGISTER_COMFIRM_NAVIGATE, screenAction);
								maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
								// insert button back for modify screen
								screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "btnBackConfirmRegister" + screenDesignDefault.getModuleCode(), (MessageDesign) messageCommons.get("btnBack"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.BUTTON, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, ScreenDesignConst.EnableConfirm.DISABLE_CONFIRM, null, ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE);
								listOfScreenItemForBatch.add(screenItem);
								
								// insert screen transition
								String transitionCodeBack = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, CONFIRM_REGISTER_BACK + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
								ScreenTransition screenTransitionBack = populateScreenTransition(transitionCodeBack, screen.getScreenId(), registerScreenId, screenDesignDefault.getModuleId(), false);
								lstScreenTransitions.add(screenTransitionBack);
								mapTransitionAndItem.put(screenTransitionBack, screenItem);
							}
							
							if (modifyConfirmScreenId == screen.getScreenId()) {
								// insert screen area
								String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, BACK_CONFIRM_CONFIRM_MODIFY + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
								screenArea = populateScreenArea(screenForm, null, 2, null, areaCode, 1, "100%", 1, "100%", 1, 1, screenTypeName, null);
								screenAreaRepository.insertScreenArea(screenArea);
								maxItemSeqNo = -1;
								
								// insert parameter
								insertParameterScreen(screen, screenDesignDefault, lstScreenItems);
								
								Long toScreen = new Long(0);
								if (modifyCompleteScreenId != 0) {
									toScreen = modifyCompleteScreenId;
								} else {
									if (screenDesignSearchId != 0) {
										toScreen = screenDesignSearchId;
									} else {
										toScreen = screen.getScreenId();
									}
								}
								// insert screen action
								screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0073), screen.getScreenId(), toScreen, ScreenActionConst.SUBMIT_METHOD_TYPE_POST);
								screenActionRepository.insertScreenAction(screenAction);
								mapNavi.put(MODIFY_COMFIRM_NAVIGATE, screenAction);
								maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
								// insert button confirm for modify screen
								MessageDesign messageModifyConfirm = (MessageDesign) messageCommons.get("modifyConfirm");
								ScreenItem screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "btnConfirmConfirmModify" + screenDesignDefault.getModuleCode(), (MessageDesign) messageCommons.get("btnConfirm"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.BUTTON, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, ScreenDesignConst.EnableConfirm.ENABLE_CONFIRM, messageModifyConfirm, ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE);
								listOfScreenItemForBatch.add(screenItem);
								
								// insert screen transition
								String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, CONFIRM_MODIFY_SUCCESS + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
								ScreenTransition screenTransition = populateScreenTransition(transitionCode, screen.getScreenId(), toScreen, screenDesignDefault.getModuleId(), false);
								lstScreenTransitions.add(screenTransition);
								mapTransitionAndItem.put(screenTransition, screenItem);
								
								// insert screen action
								screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0074), screen.getScreenId(), modifyScreenId, ScreenActionConst.SUBMIT_METHOD_TYPE_POST);
								screenActionRepository.insertScreenAction(screenAction);
								mapNavi.put(BACK_MODIFY_COMFIRM_NAVIGATE, screenAction);
								maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
								// insert button back for modify screen
								screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "btnBackConfirmModify" + screenDesignDefault.getModuleCode(), (MessageDesign) messageCommons.get("btnBack"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.BUTTON, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, ScreenDesignConst.EnableConfirm.DISABLE_CONFIRM, null, ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE);
								listOfScreenItemForBatch.add(screenItem);
								
								// insert screen transition
								String transitionCodeBack = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, CONFIRM_MODIFY_BACK + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
								ScreenTransition screenTransitionBack = populateScreenTransition(transitionCodeBack, screen.getScreenId(), modifyScreenId, screenDesignDefault.getModuleId(), false);
								lstScreenTransitions.add(screenTransitionBack);
								mapTransitionAndItem.put(screenTransitionBack, screenItem);
							}
						}
						
						// Search screen header action
						if (DbDomainConst.ScreenPatternType.SEARCH.equals(screen.getScreenPatternType()) && registerScreenId != 0) {
							// insert header area for search screen
							String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, HEADER + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							screenArea = populateScreenArea(screenForm, null, -1, null, areaCode, 1, "100%", 1, "100%", 1, 3, screenTypeName, null);
							screenAreaRepository.insertScreenArea(screenArea);
							Integer maxItemSeqNo = -1;
							// insert link action for header area of search screen
							screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0075), screenDesignSearchId, registerScreenId, ScreenActionConst.SUBMIT_METHOD_TYPE_GET);
							screenActionRepository.insertScreenAction(screenAction);
							mapNavi.put(HEADER_SEARCH_TO_REGISTER, screenAction);
							maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
							// insert search screen header item
							ScreenItem screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "linkGotoRegisterScreen", (MessageDesign) messageCommons.get("linkGotoRegisterScreen"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.LINK, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, null, null, null);
							listOfScreenItemForBatch.add(screenItem);
							
							// insert screen transition
							String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, SEARCH_TO_REGISTER + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							ScreenTransition screenTransition = populateScreenTransition(transitionCode, screenDesignSearchId, registerScreenId, screenDesignDefault.getModuleId(), false);
							lstScreenTransitions.add(screenTransition);
							mapTransitionAndItem.put(screenTransition, screenItem);
						}
						
						// Register header screen action
						if (DbDomainConst.ScreenPatternType.REGISTER.equals(screen.getScreenPatternType()) && screenDesignSearchId != 0) {
							// insert header area for register screen
							String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, HEADER + REGISTER + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							screenArea = populateScreenArea(screenForm, null, -1, null, areaCode, 1, "100%", 1, "100%", 1, 3, screenTypeName, null);
							screenAreaRepository.insertScreenArea(screenArea);
							Integer maxItemSeqNo = -1;
							// insert link action for header area of register screen
							screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0076), registerScreenId, screenDesignSearchId, ScreenActionConst.SUBMIT_METHOD_TYPE_GET);
							screenActionRepository.insertScreenAction(screenAction);
							mapNavi.put(HEADER_REGISTER_TO_SEARCH, screenAction);
							maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
							// insert register header screen item
							ScreenItem screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "linkGotoSearchScreen", (MessageDesign) messageCommons.get("linkGotoSearchScreen"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.LINK, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, null, null, null);
							listOfScreenItemForBatch.add(screenItem);
							
							// insert screen transition
							String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, REGISTER_TO_SEARCH + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							ScreenTransition screenTransition = populateScreenTransition(transitionCode, registerScreenId, screenDesignSearchId, screenDesignDefault.getModuleId(), false);
							lstScreenTransitions.add(screenTransition);
							mapTransitionAndItem.put(screenTransition, screenItem);
						}
						
						// Modify header screen action
						if (DbDomainConst.ScreenPatternType.MODIFY.equals(screen.getScreenPatternType()) && screenDesignSearchId != 0) {
							// insert header area for modify screen
							String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, HEADER + MODIFY + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							screenArea = populateScreenArea(screenForm, null, -1, null, areaCode, 1, "100%", 1, "100%", 1, 3, screenTypeName, null);
							screenAreaRepository.insertScreenArea(screenArea);
							Integer maxItemSeqNo = -1;
							// insert link action for header area of modify screen
							screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0076), modifyScreenId, screenDesignSearchId, ScreenActionConst.SUBMIT_METHOD_TYPE_GET);
							screenActionRepository.insertScreenAction(screenAction);
							mapNavi.put(HEADER_MODIFY_TO_SEARCH, screenAction);
							maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
							// insert modify header screen item
							ScreenItem screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "linkGotoSearchScreen", (MessageDesign) messageCommons.get("linkGotoSearchScreen"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.LINK, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, null, null, null);
							listOfScreenItemForBatch.add(screenItem);
							
							// insert screen transition
							String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, MODIFY_TO_SEARCH + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							ScreenTransition screenTransition = populateScreenTransition(transitionCode, modifyScreenId, screenDesignSearchId, screenDesignDefault.getModuleId(), false);
							lstScreenTransitions.add(screenTransition);
							mapTransitionAndItem.put(screenTransition, screenItem);
						}
						
						// Register complete header screen action
						if (DbDomainConst.ScreenPatternType.VIEW.equals(screen.getScreenPatternType()) && screenDesignSearchId != 0 && registerCompleteScreenId != 0 && registerCompleteScreenId == screen.getScreenId()) {
							// insert header area for register complete screen
							String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, HEADER + COMPLETE + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							screenArea = populateScreenArea(screenForm, null, -1, null, areaCode, 1, "100%", 1, "100%", 1, 3, screenTypeName, null);
							screenAreaRepository.insertScreenArea(screenArea);
							Integer maxItemSeqNo = -1;
							// insert parameter
							insertParameterScreen(screen, screenDesignDefault, lstScreenItems);
							
							// insert link action for header area of modify register screen
							screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0076), registerCompleteScreenId, screenDesignSearchId, ScreenActionConst.SUBMIT_METHOD_TYPE_GET);
							screenActionRepository.insertScreenAction(screenAction);
							mapNavi.put(HEADER_COMPLETE_REGISTER_TO_SEARCH, screenAction);
							maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
							// insert register complete header screen item
							ScreenItem screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "linkGotoSearchScreen", (MessageDesign) messageCommons.get("linkGotoSearchScreen"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.LINK, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, null, null, null);
							listOfScreenItemForBatch.add(screenItem);
							
							// insert screen transition
							String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, COMPLETE_REGISTER_TO_SEARCH + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							ScreenTransition screenTransition = populateScreenTransition(transitionCode, registerCompleteScreenId, screenDesignSearchId, screenDesignDefault.getModuleId(), false);
							lstScreenTransitions.add(screenTransition);
							mapTransitionAndItem.put(screenTransition, screenItem);
						}
						
						// Modify complete header screen action
						if (DbDomainConst.ScreenPatternType.VIEW.equals(screen.getScreenPatternType()) && screenDesignSearchId != 0 && modifyCompleteScreenId != 0 && modifyCompleteScreenId == screen.getScreenId()) {
							// insert header area for modify complete screen
							String areaCode = generateUniqueKey.calculateCodeForAuto(SCREEN_AREA, HEADER +  MODIFY + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							screenArea = populateScreenArea(screenForm, null, -1, null, areaCode, 1, "100%", 1, "100%", 1, 3, screenTypeName, null);
							screenAreaRepository.insertScreenArea(screenArea);
							Integer maxItemSeqNo = -1;
							// insert parameter
							insertParameterScreen(screen, screenDesignDefault, lstScreenItems);
							
							// insert link action for header area of modify complete
							// screen
							screenAction = populateScreenAction(ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0076), modifyCompleteScreenId, screenDesignSearchId, ScreenActionConst.SUBMIT_METHOD_TYPE_GET);
							screenActionRepository.insertScreenAction(screenAction);
							mapNavi.put(HEADER_COMPLETE_MODIFY_TO_SEARCH, screenAction);
							maxItemSeqNo = getItemSeqNo(screenArea.getScreenAreaId(), mapScreenAreaItemSeqNo);
							// insert modify complete header screen item
							ScreenItem screenItem = populateScreenItem(maxItemSeqNo, screen.getScreenId(), "linkGotoSearchScreen", (MessageDesign) messageCommons.get("linkGotoSearchScreen"), screenArea.getScreenAreaId(), null, DbDomainConst.LogicDataType.LINK, screenAction.getScreenActionId(), ITEM_TYPE_NORMAL, null, null, null);
							listOfScreenItemForBatch.add(screenItem);
							
							// insert screen transition
							String transitionCode = generateUniqueKey.calculateCodeForAuto(SCREEN_TRANSITION, COMPLETE_MODIFY_TO_SEARCH + StringUtils.capitalize(screenDesignDefault.getModuleCode()), screenDesignDefault.getSuffix());
							ScreenTransition screenTransition = populateScreenTransition(transitionCode, modifyCompleteScreenId, screenDesignSearchId, screenDesignDefault.getModuleId(), false);
							lstScreenTransitions.add(screenTransition);
							mapTransitionAndItem.put(screenTransition, screenItem);
						}
					}
				}
			}

			if (FunctionCommon.isNotEmpty(listOfScreenItemForBatch)) {
				Integer numOfItem = listOfScreenItemForBatch.size();
				Long seq = screenDesignRepository.screenItemGetSequences(numOfItem - 1);
				Long startSreenSeqId = seq - (numOfItem - 1);
				for (ScreenItem screenItem : listOfScreenItemForBatch) {
					screenItem.setScreenItemId(startSreenSeqId);
					screenItem.setItemGroupType(0);
					startSreenSeqId++;
				}
				screenDesignRepository.screenItemsInsert(listOfScreenItemForBatch);
			}
			if (FunctionCommon.isNotEmpty(lstScreenTransitions)) {
				Integer numOfItem = lstScreenTransitions.size();
				Long seq = screenTransitionRepository.getSequences(numOfItem - 1);
				Long startSreenTransitionSeqId = seq - (numOfItem - 1);
				for (ScreenTransition trans : lstScreenTransitions) {
					trans.setScreenTransitionId(startSreenTransitionSeqId.toString());
					startSreenTransitionSeqId++;
				}
				screenTransitionRepository.createMultiTransitionHaveId(lstScreenTransitions);

				if (FunctionCommon.isNotEmpty(mapTransitionAndItem)) {
					List<ScreenItem> lstItemsUpdateScreenTransId = new ArrayList<ScreenItem>();
					for (ScreenTransition trans : mapTransitionAndItem.keySet()) {
						ScreenItem item = mapTransitionAndItem.get(trans);
						item.setScreenTransitionId(Long.parseLong(trans.getScreenTransitionId()));
						lstItemsUpdateScreenTransId.add(item);
					}
					screenDesignRepository.updateAttributeScreenTransitionId(lstItemsUpdateScreenTransId);
				}
			}

			screenDesignDefault.setLstScreenTransitions(lstScreenTransitions);
			screenDesignDefault.setLstScreenItem(lstScreenItems);
			screenDesignDefault.setLstScreenItemSearchResults(lstScreenItemSearchResults);
		}
	}

	private void insertParameterScreen(ScreenDesign screenDesign, ScreenDesignDefault screenDesignDefault, List<ScreenItem> lstScreenItem) {
		if (FunctionCommon.isNotEmpty(lstScreenItem)) {
			List<ScreenParameter> listParameter = new ArrayList<ScreenParameter>();
			Integer itemSeqNo = 0;
			Long screenId = screenDesign.getScreenId();
			List<ScreenItem> screenItemsOfFirstTable = new ArrayList<ScreenItem>();
			ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings()[0];
			for (ScreenItem item : lstScreenItem) {
				if (item.getScreenId() != null && item.getScreenId().equals(screenDesign.getScreenId()) && item.getTblDesignId() != null && item.getTblDesignId().equals(firstTable.getTblDesignId())) {
					screenItemsOfFirstTable.add(item);
				}
			}

			List<ScreenItem> lstKey = findKeyOfFirstTable(screenDesign, screenDesignDefault, lstScreenItem);
			Set<Long> lstKeyId = new HashSet<Long>();
			if (FunctionCommon.isNotEmpty(lstKey)) {
				for (ScreenItem key : lstKey) {
					lstKeyId.add(key.getColumnId());
				}
			}

			// if view screen then insert hidden for key column
			if (ScreenDesignConst.VIEW_SCREEN.equals(screenDesign.getScreenTypeName()) || ScreenDesignConst.MODIFY_SCREEN.equals(screenDesign.getScreenTypeName())) {
				for (ScreenItem item : screenItemsOfFirstTable) {
					if (lstKeyId.contains(item.getColumnId())) {
						ScreenParameter screenParameter = GenerateScreenContruct.populateScreenParameter(screenDesignDefault, screenId, item, ++itemSeqNo);
						listParameter.add(screenParameter);
					}
				}
				// if modify screen then insert hidden for all column
			} else if (ScreenDesignConst.CONFIRM_REGISTER_SCREEN.equals(screenDesign.getScreenTypeName()) || ScreenDesignConst.CONFIRM_MODIFY_SCREEN.equals(screenDesign.getScreenTypeName()) || ScreenDesignConst.COMPLETE_REGISTER_SCREEN.equals(screenDesign.getScreenTypeName()) || ScreenDesignConst.COMPLETE_MODIFY_SCREEN.equals(screenDesign.getScreenTypeName())) {
				for (ScreenItem item : screenItemsOfFirstTable) {
					if (lstKeyId.contains(item.getColumnId())) {
						ScreenParameter screenParameter = GenerateScreenContruct.populateScreenParameter(screenDesignDefault, screenId, item, ++itemSeqNo);
						listParameter.add(screenParameter);
					}
				}
			}
			// insert screen parameter
			if (FunctionCommon.isNotEmpty(listParameter)) {
				screenDesignRepository.multiCreateScreenParameter(listParameter);
			}
		}
	}

	private void buildItemHidden(ScreenDesign screenDesign, ScreenDesignDefault screenDesignDefault, List<ScreenItem> lstScreenItem) {
		if (FunctionCommon.isNotEmpty(lstScreenItem)) {
			List<ScreenItem> listHiddenItems = new ArrayList<ScreenItem>();
			List<ScreenItem> screenItemsOfFirstTable = new ArrayList<ScreenItem>();
			ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings()[0];
			for (ScreenItem item : lstScreenItem) {
				if (item.getScreenId() != null && item.getScreenId().equals(screenDesign.getScreenId()) && item.getTblDesignId() != null && item.getTblDesignId().equals(firstTable.getTblDesignId())) {
					screenItemsOfFirstTable.add(item);
				}
			}

			List<ScreenItem> lstKey = findKeyOfFirstTable(screenDesign, screenDesignDefault, lstScreenItem);
			Set<Long> lstKeyId = new HashSet<Long>();
			if (FunctionCommon.isNotEmpty(lstKey)) {
				for (ScreenItem key : lstKey) {
					lstKeyId.add(key.getColumnId());
				}
			}

			if (ScreenDesignConst.SEARCH_SCREEN.equals(screenDesign.getScreenTypeName())) {
				for (ScreenItem item : screenItemsOfFirstTable) {
					if (lstKeyId.contains(item.getColumnId())) {
						listHiddenItems.add(item);
					}
				}
				mapScreenItemHidden.put(ScreenDesignConst.SEARCH_SCREEN, listHiddenItems);
			} else if (ScreenDesignConst.MODIFY_SCREEN.equals(screenDesign.getScreenTypeName())) {
				for (ScreenItem item : screenItemsOfFirstTable) {
					if (lstKeyId.contains(item.getColumnId())) {
						listHiddenItems.add(item);
					}
				}
				mapScreenItemHidden.put(ScreenDesignConst.MODIFY_SCREEN, listHiddenItems);
			} else if (ScreenDesignConst.VIEW_SCREEN.equals(screenDesign.getScreenTypeName())) {
				// in this case : hidden include PK and FK to delete children then delete parent
				listHiddenItems = findInputForDelete(screenDesign, screenDesignDefault, lstScreenItem);
				mapScreenItemHidden.put(ScreenDesignConst.VIEW_SCREEN, listHiddenItems);
			}
		}
	}

	/**
	 * generate default business logic design for new module
	 */
	@Override
	public void generateDefaultBusinesslogic(ScreenDesignDefault screenDesignDefault) {
		List<ScreenDesign> lstScreenDesigns = screenDesignDefault.getLstScreenDesign();
		accountId = screenDesignDefault.getCreatedBy();
		workingProjectId = screenDesignDefault.getProjectId();
		systemDate = screenDesignDefault.getCreatedDate();
		// get all keys
		allKeys = tableDesignKeyRepository.findKeyItem(workingProjectId);
		// get all foreign key
		allForeignKeyInProject = tableDesignForeignKeyRepository.getAllForeignKey(workingProjectId);
		// get all sql builder
		allSqlBuilderInProject = sqlDesignRepository.getAllSqlDesignByProjectId(workingProjectId, null);
		// get all autocomple design
		allAutocompleInProject = autocompleteDesignRepository.getAllAutocompleteDesignByProjectId(workingProjectId);
		Map<Long, SqlDesign> mapSqlDesign = new HashMap<Long, SqlDesign>();
		if(FunctionCommon.isNotEmpty(allSqlBuilderInProject)) {
			for(SqlDesign sql : allSqlBuilderInProject) {
				mapSqlDesign.put(sql.getSqlDesignId(), sql);
			}
		}
		screenDesignDefault.setMapSqlDesign(mapSqlDesign);
		Map<Long, AutocompleteDesign> mapAutocomple = new HashMap<Long, AutocompleteDesign>();
		if(FunctionCommon.isNotEmpty(allAutocompleInProject)) {
			for(AutocompleteDesign ac : allAutocompleInProject) {
				mapAutocomple.put(ac.getAutocompleteId(), ac);
			}
		}
		screenDesignDefault.setMapAutocomplete(mapAutocomple);

		boolean hasConfirm = false;
		if (ScreenDesignConst.ConfirmType.SCREEN.equals(screenDesignDefault.getConfirmationType())) {
			hasConfirm = true;
		}
		screenDesignDefault.setMapScreenDesign(new HashMap<String, ScreenDesign>());
		// gen initial blogic
		for (ScreenDesign sObj : lstScreenDesigns) {
			screenDesignDefault.getMapScreenDesign().put(sObj.getScreenTypeName(), sObj);
			switch (sObj.getScreenTypeName()) {
				case ScreenDesignConst.REGISTER_SCREEN:
					displayRegisterBlogic(sObj, screenDesignDefault, accountId, systemDate);
					break;
				case ScreenDesignConst.CONFIRM_REGISTER_SCREEN:
					displayConfirmRegisterBlogic(sObj, screenDesignDefault, accountId, systemDate);
					break;
				case ScreenDesignConst.COMPLETE_REGISTER_SCREEN:
					displayCompleteRegisterBlogic(sObj, screenDesignDefault, accountId, systemDate);
					break;
				case ScreenDesignConst.SEARCH_SCREEN:
					displaySearchBlogic(sObj, screenDesignDefault, accountId, systemDate);
					break;
				case ScreenDesignConst.MODIFY_SCREEN:
					displayModifyBlogic(sObj, screenDesignDefault, accountId, systemDate);
					break;
				case ScreenDesignConst.CONFIRM_MODIFY_SCREEN:
					displayConfirmModifyBlogic(sObj, screenDesignDefault, accountId, systemDate);
					break;
				case ScreenDesignConst.COMPLETE_MODIFY_SCREEN:
					displayCompleteModifyBlogic(sObj, screenDesignDefault, accountId, systemDate);
					break;
				case ScreenDesignConst.VIEW_SCREEN:
					displayViewBlogic(sObj, screenDesignDefault, accountId, systemDate);
					if (DOWNLOAD_FILE_BLOGIC_RELEASE) {
						// Check if table have column binary. Then generate blogic download file
						for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
							List<ScreenItem> lstScreenItems = findKeyOfTableUsingScreenItems(table, sObj, screenDesignDefault);
							for (TableDesignDetails column : table.getListAllColumns()) {
								for (ScreenItem item : screenDesignDefault.getLstScreenItem()) {
									if ((sObj.getScreenId() != null && item.getScreenId() != null && sObj.getScreenId().equals(item.getScreenId()) && item.getTblDesignId() != null && table.getTblDesignId() != null && item.getTblDesignId().equals(table.getTblDesignId()))
											&& column.getTableDesignId().equals(table.getTblDesignId()) && column.getColumnId().equals(item.getColumnId()) && column.getBaseType() != null && column.getBaseType().intValue() == DbDomainConst.BaseType.BINARY_BASETYPE) {
										downloadFileBlogic(sObj, screenDesignDefault, accountId, systemDate, table, lstScreenItems, item);
										break;
									}
								}
							}
						}
					}
					break;
				default:
					break;
			}
		}

		// gen process blogic
		for (ScreenDesign sObj : lstScreenDesigns) {
			switch (sObj.getScreenTypeName()) {
				case ScreenDesignConst.REGISTER_SCREEN:
					if (!hasConfirm) {
						processRegisterBlogic(sObj, screenDesignDefault, accountId, systemDate);
					} else {
						redirectConfirmRegisterBlogic(sObj, screenDesignDefault, accountId, systemDate);
					}
					break;
				case ScreenDesignConst.CONFIRM_REGISTER_SCREEN:
					undoRegisterBlogic(sObj, screenDesignDefault, accountId, systemDate);
					processRegisterBlogic(sObj, screenDesignDefault, accountId, systemDate);
					break;
				case ScreenDesignConst.SEARCH_SCREEN:
					processSearchBlogic(sObj, screenDesignDefault, accountId, systemDate);
					break;
				case ScreenDesignConst.MODIFY_SCREEN:
					if (!hasConfirm) {
						processModifyBlogic(sObj, screenDesignDefault, accountId, systemDate);
					} else {
						redirectConfirmModifyBlogic(sObj, screenDesignDefault, accountId, systemDate);
					}
					break;
				case ScreenDesignConst.CONFIRM_MODIFY_SCREEN:
					undoModifyBlogic(sObj, screenDesignDefault, accountId, systemDate);
					processModifyBlogic(sObj, screenDesignDefault, accountId, systemDate);
					break;
				case ScreenDesignConst.VIEW_SCREEN:
					processDeleteBlogic(sObj, screenDesignDefault, accountId, systemDate);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param blogicCode
	 * @param acountId
	 * @param currentDate
	 * @return
	 */
	private BusinessDesign registerBusinesslogicDefault(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, String blogicCode, String blogicMessageCode, Integer returnType, Long acountId, Timestamp currentDate, int patternType, Integer requestMethod) {
		BusinessDesign blogic = new BusinessDesign();
		String businessLogicCode = "";
		String businessLogicName = "";
		if (Boolean.FALSE.equals(screenDesignDefault.getIsGenDefault())) {
			businessLogicCode = blogicCode + StringUtils.capitalize(screenDesignDefault.getScreenCode());
			businessLogicName = MessageUtils.getMessage(blogicMessageCode) + SPACE + screenDesignDefault.getScreenName();
		} else {
			businessLogicCode = blogicCode + StringUtils.capitalize(screenDesignDefault.getModuleCode());
			businessLogicName = MessageUtils.getMessage(blogicMessageCode) + SPACE + screenDesignDefault.getModuleName();
		}
		blogic.setBusinessLogicName(businessLogicName);
		blogic.setBusinessLogicCode(businessLogicCode);
		blogic.setReturnType(returnType);
		blogic.setScreenId(sObj.getScreenId());
		blogic.setModuleId(screenDesignDefault.getModuleId());
		blogic.setProjectId(workingProjectId);
		blogic.setDesignStatus(DbDomainConst.DesignStatus.UNDER_DESIGN);
		blogic.setPatternType(patternType);

		Project project = screenDesignDefault.getProject();
		AccountProfile accountProfile = systemService.getDefaultProfile();
		String dataType = String.valueOf(project.getDbType());
		Integer maxLengOfCode = accountProfile.getSqlCodeMaxLengthByDbType(dataType);
		
		GenerateUniqueKey generateUniqueKey = new GenerateUniqueKey(accountProfile.getNameMaxLength(), maxLengOfCode);
		// check duplicated
		Long totalCount = businessDesignRepository.countNameCodeExist(blogic);
		String suffix = screenDesignDefault.getSuffix();
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			blogic.setBusinessLogicName(generateUniqueKey.calculateName(businessLogicName, SPACE, suffix));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			blogic.setBusinessLogicCode(generateUniqueKey.calculateCodeForAuto(FunctionCommon.convertNameToCode(businessLogicCode), suffix));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			blogic.setBusinessLogicName(generateUniqueKey.calculateName(businessLogicName, SPACE, suffix));
			blogic.setBusinessLogicCode(generateUniqueKey.calculateCodeForAuto(FunctionCommon.convertNameToCode(businessLogicCode), suffix));
		}

		if (ScreenDesignConst.ConfirmType.SCREEN.equals(screenDesignDefault.getConfirmationType())) {
			blogic.setConfirmFlg(true);
		} else {
			blogic.setConfirmFlg(false);
		}
		if (ScreenDesignConst.CompleteType.SCREEN.equals(screenDesignDefault.getCompletionType())) {
			blogic.setCompleteFlg(true);
		} else {
			blogic.setCompleteFlg(false);
		}
		blogic.setFunctionDesignId(sObj.getFunctionDesignId());
		blogic.setScreenFormId(sObj.getFirstFormOfScreen());
		blogic.setRemark(BusinessDesignConst.REMARK);
		blogic.setCreatedBy(acountId);
		blogic.setCreatedDate(currentDate);
		blogic.setUpdatedBy(acountId);
		blogic.setUpdatedDate(currentDate);
		blogic.setBlogicType(BusinessDesignConst.BLOGIC_TYPE_STANDARD);
		blogic.setRequestMethod(requestMethod);
		blogic.setDesignMode(BusinessDesignConst.DESIGN_MODE_AUTO);
		if (businessDesignRepository.register(blogic) <= 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010)));
		}
		// store all business logic in map
		Map<String, BusinessDesign> mapBusinessLogicId = screenDesignDefault.getMapBusinessLogicId();
		mapBusinessLogicId.put(blogicCode, blogic);
		
		// TrungDV : update screen_action : navigate_to_blogic_id
		if(BusinessDesignConst.DISPLAY_REGISTER.equals(blogicCode)) {
			updateBLogicNavigator(HEADER_SEARCH_TO_REGISTER, blogic.getBusinessLogicId());
		} else if (BusinessDesignConst.DISPLAY_SEARCH.equals(blogicCode)) {
			updateBLogicNavigator(HEADER_REGISTER_TO_SEARCH, blogic.getBusinessLogicId());
			updateBLogicNavigator(HEADER_MODIFY_TO_SEARCH, blogic.getBusinessLogicId());
			updateBLogicNavigator(HEADER_COMPLETE_REGISTER_TO_SEARCH, blogic.getBusinessLogicId());
			updateBLogicNavigator(HEADER_COMPLETE_MODIFY_TO_SEARCH, blogic.getBusinessLogicId());
		} else if (BusinessDesignConst.DISPLAY_VIEW.equals(blogicCode)) {
			updateBLogicNavigator(HEADER_SEARCH_TO_VIEW, blogic.getBusinessLogicId());
		}

		return blogic;
	}

	/**
	 * @param screenDesignObj
	 * @param screenDesignDefault
	 * @param lstScreenItems
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private List<InputBean> registerInputBean(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, List<ScreenItem> lstScreenItems, BusinessDesign businessDesign, Long acountId, Timestamp currentDate, Boolean flgInsertTableObject, Boolean flgMultiPart) {
		int itemSeqNo = 0;
		Long businessLogicId = businessDesign.getBusinessLogicId();
		List<InputBean> inputbeansRegisted = new ArrayList<InputBean>();
		Integer tempInputbeanId = 0, tempParentMultilepartFileId = 0;
		Set<ScreenArea> areaUpdateMappingObjectType = new HashSet<ScreenArea>();
		List<ValidationCheckDetail> lstCheckDetails = new ArrayList<ValidationCheckDetail>();
		
		ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionRepository.findExtObjDefIsMultipartFileByProjectId(workingProjectId);
		List<ExternalObjectAttribute> lstExternalObjectAttribute = new ArrayList<ExternalObjectAttribute>();
		if(externalObjectDefinition != null) {
			lstExternalObjectAttribute = externalObjectAttributeRepository.findExternalObjectAttributeByExternalObjectDefinitionId(externalObjectDefinition.getExternalObjectDefinitionId());
		}
		
		List<Long> lstColumnIds = new ArrayList<Long>();
		if(FunctionCommon.isNotEmpty(lstScreenItems)) {
			for(ScreenItem item : lstScreenItems) {
				lstColumnIds.add(item.getColumnId());
			}
		}
		List<TableDesignDetails> lstColumns = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIds);
		Map<Long, TableDesignDetails> mapColumns = new HashMap<Long, TableDesignDetails>();
		if (FunctionCommon.isNotEmpty(lstColumns)) {
			for (TableDesignDetails column : lstColumns) {
				mapColumns.put(column.getColumnId(), column);
			}
		}

		for (ModuleTableMapping moduleTableMapping : screenDesignDefault.getModuleTableMappings()) {
			Integer parentInputBean = null;
			if (flgInsertTableObject) {
				Boolean arrayFlag = null;
				if (ScreenDesignConst.SEARCH_SCREEN.equals(screenDesignObj.getScreenTypeName())) {
					arrayFlag = false;
				} else {
					if (LIST_TYPE.equals(moduleTableMapping.getTableMappingType())) {
						arrayFlag = true;
					} else {
						arrayFlag = false;
					}
				}
				String areaCode = "";
				for (ScreenItem item : lstScreenItems) {
					if (screenDesignObj.getScreenAreas() != null) {
						for (ScreenArea area : screenDesignObj.getScreenAreas()) {
							if (item.getScreenAreaId().equals(area.getScreenAreaId()) && (moduleTableMapping.getTblDesignId().equals(item.getTblDesignId()))) {
								area.setObjectMappingId(Long.parseLong(tempInputbeanId.toString()));
								areaUpdateMappingObjectType.add(area);
								areaCode = area.getAreaCode();
								break;
							}
						}
					}
				}
				InputBean inputbeanObject = null;
				if((ScreenDesignConst.CONFIRM_REGISTER_SCREEN.equals(screenDesignObj.getScreenTypeName())
						|| ScreenDesignConst.CONFIRM_MODIFY_SCREEN.equals(screenDesignObj.getScreenTypeName())) && DbDomainConst.BlogicReturnType.INITIAL.equals(businessDesign.getReturnType())) {
					inputbeanObject = GenerateScreenContruct.setInputbean(tempInputbeanId, screenDesignObj, FunctionCommon.isNotEmpty(areaCode) ? areaCode : moduleTableMapping.getTblDesignCode(), moduleTableMapping.getTblDesignName(), DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE, arrayFlag, null, businessLogicId, null, moduleTableMapping.getTblDesignId(), null, itemSeqNo++, null, DbDomainConst.InputBeanType.CUSTOMIZE);
				} else {
					String messageCodeOfTable = moduleTableMapping.getMessageDesign() != null ? moduleTableMapping.getMessageDesign().getMessageCode() : "";
					inputbeanObject = GenerateScreenContruct.setInputbean(tempInputbeanId, screenDesignObj, FunctionCommon.isNotEmpty(areaCode) ? areaCode : moduleTableMapping.getTblDesignCode(), messageCodeOfTable, DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE, arrayFlag, null, businessLogicId, null, moduleTableMapping.getTblDesignId(), null, itemSeqNo++, null, DbDomainConst.InputBeanType.DEFAULT);
				}

				//				InputBean inputbeanObject = GenerateScreenContruct.setInputbean(tempInputbeanId, moduleTableMapping.getTblDesignCode(), moduleTableMapping.getTblDesignCode(), DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE, arrayFlag, null, businessLogicId, null, moduleTableMapping.getTblDesignId(), null, itemSeqNo++);
				inputbeansRegisted.add(inputbeanObject);
				parentInputBean = tempInputbeanId;
				tempInputbeanId++;
			}
			if(ScreenDesignConst.CONFIRM_REGISTER_SCREEN.equals(screenDesignObj.getScreenTypeName())
					|| ScreenDesignConst.CONFIRM_MODIFY_SCREEN.equals(screenDesignObj.getScreenTypeName())) {
				for (ScreenItem item : lstScreenItems) {
					if (screenDesignObj.getScreenId() != null && item.getScreenId() != null && screenDesignObj.getScreenId().equals(item.getScreenId()) && moduleTableMapping.getTblDesignId() != null && item.getTblDesignId() != null && item.getTblDesignId().equals(moduleTableMapping.getTblDesignId())) {
						//if((ITEM_TYPE_NORMAL.equals(item.getItemType())) || (ITEM_TYPE_HIDDEN.equals(item.getItemType()) && item.getIsPkHidden() != null && item.getIsPkHidden())) {
						if((ITEM_TYPE_HIDDEN.equals(item.getItemType()))) {
							// not support MultipartFile in confirm/complete screen
							if(item.getPhysicalDataType() != null && DbDomainConst.BaseType.BINARY_BASETYPE != item.getPhysicalDataType().intValue()) {
								Integer dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType());
								TableDesignDetails column = mapColumns.get(item.getColumnId());
								String parentInputBeanId = parentInputBean == null ? null : parentInputBean.toString();
								InputBean inputBean = null;
								if(column != null) {
									if(this.isArrayForCheckbox(column)) {
										if(DbDomainConst.BlogicReturnType.INITIAL.equals(businessDesign.getReturnType())) {
											inputBean = GenerateScreenContruct.setInputbean(tempInputbeanId, screenDesignObj, item.getItemCode(), item.getItemName(), dataType, true, parentInputBeanId, businessLogicId, item.getScreenItemId(), item.getTblDesignId(), item.getColumnId(), itemSeqNo++, column.getItemType(), DbDomainConst.InputBeanType.CUSTOMIZE);
										} else {
											inputBean = GenerateScreenContruct.setInputbean(tempInputbeanId, screenDesignObj, item.getItemCode(), item.getMessageCode(), dataType, true, parentInputBeanId, businessLogicId, item.getScreenItemId(), item.getTblDesignId(), item.getColumnId(), itemSeqNo++, column.getItemType(), DbDomainConst.InputBeanType.DEFAULT);
										}
									} else {
										if(DbDomainConst.BlogicReturnType.INITIAL.equals(businessDesign.getReturnType())) {
											inputBean = GenerateScreenContruct.setInputbean(tempInputbeanId, screenDesignObj, item.getItemCode(), item.getItemName(), dataType, false, parentInputBeanId, businessLogicId, item.getScreenItemId(), item.getTblDesignId(), item.getColumnId(), itemSeqNo++, column.getItemType(), DbDomainConst.InputBeanType.CUSTOMIZE);
										} else {
											inputBean = GenerateScreenContruct.setInputbean(tempInputbeanId, screenDesignObj, item.getItemCode(), item.getMessageCode(), dataType, false, parentInputBeanId, businessLogicId, item.getScreenItemId(), item.getTblDesignId(), item.getColumnId(), itemSeqNo++, column.getItemType(), DbDomainConst.InputBeanType.DEFAULT);
										}
									}
								}
								inputbeansRegisted.add(inputBean);
								// validate check of inputbean
								populateValidateInputbean(item, column, inputBean, businessLogicId, lstCheckDetails);
								tempInputbeanId++;
							}
						}
					}
				}
			} else {
				for (ScreenItem item : lstScreenItems) {
					if (screenDesignObj.getScreenId() != null && item.getScreenId() != null && screenDesignObj.getScreenId().equals(item.getScreenId()) && moduleTableMapping.getTblDesignId() != null && item.getTblDesignId() != null && item.getTblDesignId().equals(moduleTableMapping.getTblDesignId())) {
						if (DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getLogicalDataType()) && externalObjectDefinition != null && Boolean.TRUE.equals(flgMultiPart)) {
							if (CollectionUtils.isNotEmpty(lstExternalObjectAttribute)) {
								// Assign object parent form object definition
								InputBean inputBean = new InputBean();
								inputBean.setInputBeanId(tempInputbeanId.toString());
								inputBean.setInputBeanCode(item.getItemCode());
								inputBean.setInputBeanName(ScreenDesignConst.MULTIPART_FILE);
								inputBean.setScreenItemId(item.getScreenItemId());
								inputBean.setScreenItem(item);
								inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.DEFAULT);
								inputBean.setDataType(BusinessDesignConst.DataType.EXTERNAL_OBJECT);
								inputBean.setArrayFlg(Boolean.FALSE);
								inputBean.setParentInputBeanId(parentInputBean == null ? null : parentInputBean.toString());
								inputBean.setObjectType(DbDomainConst.ObjectType.EXTERNAL_OBJECT);
								inputBean.setObjectId(externalObjectDefinition.getExternalObjectDefinitionId());
								inputBean.setObjectFlg(Boolean.TRUE);
								inputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
								inputBean.setFlagUsingTempId(true);
								inputBean.setBusinessLogicId(businessLogicId);
								inputBean.setTblDesignId(item.getTblDesignId());
								inputBean.setItemSequenceNo(itemSeqNo++);
								// add to list
								inputbeansRegisted.add(inputBean);
								tempParentMultilepartFileId = tempInputbeanId;
								tempInputbeanId++;
								
								// Assign object parent form object attribute
								for (ExternalObjectAttribute extObjAttrIter : lstExternalObjectAttribute) {
									inputBean = new InputBean();
									inputBean.setInputBeanId(tempInputbeanId.toString());
									inputBean.setInputBeanCode(extObjAttrIter.getExternalObjectAttributeCode());
									inputBean.setInputBeanName(extObjAttrIter.getExternalObjectAttributeName());
									inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.ADDED_DEFAULT);
									inputBean.setDataType(extObjAttrIter.getDataType());
									inputBean.setArrayFlg(extObjAttrIter.getArrayFlg());
									inputBean.setParentInputBeanId(tempParentMultilepartFileId.toString());
									inputBean.setObjectFlg(Boolean.FALSE);
									inputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
									inputBean.setFlagUsingTempId(true);
									inputBean.setBusinessLogicId(businessLogicId);
									inputBean.setItemSequenceNo(itemSeqNo++);
									// add to list
									inputbeansRegisted.add(inputBean);
									tempInputbeanId++;
								}
							}
						} else {
							Integer dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType());
							TableDesignDetails column = mapColumns.get(item.getColumnId());
							String parentInputBeanId = parentInputBean == null ? null : parentInputBean.toString();
							InputBean inputBean = null;
							if(column != null) {
								if(this.isArrayForCheckbox(column)) {
									inputBean = GenerateScreenContruct.setInputbean(tempInputbeanId, screenDesignObj, item.getItemCode(), item.getMessageCode(), dataType, true, parentInputBeanId, businessLogicId, item.getScreenItemId(), item.getTblDesignId(), item.getColumnId(), itemSeqNo++, column.getItemType(), DbDomainConst.InputBeanType.DEFAULT);
								} else {
									inputBean = GenerateScreenContruct.setInputbean(tempInputbeanId, screenDesignObj, item.getItemCode(), item.getMessageCode(), dataType, false, parentInputBeanId, businessLogicId, item.getScreenItemId(), item.getTblDesignId(), item.getColumnId(), itemSeqNo++, column.getItemType(), DbDomainConst.InputBeanType.DEFAULT);
								}
							}
							inputbeansRegisted.add(inputBean);
							tempInputbeanId++;
							// validate check of inputbean
							populateValidateInputbean(item, column, inputBean, businessLogicId, lstCheckDetails);
							if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
								inputBean = GenerateScreenContruct.setInputbean(tempInputbeanId, screenDesignObj, item.getItemCode() + AUTOCOMPLETE, item.getMessageCode(), DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE, false, parentInputBeanId, businessLogicId, item.getScreenItemId(), item.getTblDesignId(), null, itemSeqNo++, column.getItemType(), DbDomainConst.InputBeanType.DEFAULT);
								inputbeansRegisted.add(inputBean);
							}
							tempInputbeanId++;
						}
					}
				}
			}
		}
		Map<String, Long> mapInputBeanId = new HashMap<String, Long>();
		if (inputbeansRegisted.size() > 0) {
			Long sequenceInputBeanItem = businessDesignRepository.getSequencesInputBean(inputbeansRegisted.size() - 1);
			Long startSequence = sequenceInputBeanItem - (inputbeansRegisted.size() - 1);
			for (InputBean obj : inputbeansRegisted) {
				mapInputBeanId.put(obj.getInputBeanId(), startSequence);
				obj.setInputBeanId(startSequence.toString());
				String parentInputBeanId = mapInputBeanId.get(obj.getParentInputBeanId()) != null ? mapInputBeanId.get(obj.getParentInputBeanId()).toString() : null;
				obj.setParentInputBeanId(parentInputBeanId);
				startSequence++;
			}
			businessDesignRepository.registerInputBean(inputbeansRegisted);
			
			//register default validation check
			registerValidationCheckOfInputBean(lstCheckDetails,mapInputBeanId);
		}
		// update object_mapping_type and object_mapping_id of screen area
		for (ScreenArea area : areaUpdateMappingObjectType) {
			area.setObjectMappingType(ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_INPUT_BEAN);
			area.setObjectMappingId(mapInputBeanId.get(area.getObjectMappingId().toString()));
			
			screenAreaRepository.updateObjectMappingTypeOfScreenArea(area);
		}
		// store all input beans of business logic in map
		screenDesignDefault.getMapInputBeanOfBLogic().put(businessLogicId, inputbeansRegisted);

		return inputbeansRegisted;
	}

	private void registerValidationCheckOfInputBean(List<ValidationCheckDetail> lstCheckDetails ,Map<String, Long> mapInputBeanId){
		Long startSequence = 0L;
		int totalMessage = 0;
		if(CollectionUtils.isNotEmpty(lstCheckDetails)){
			List<MessageParameter> lstMessageParameters = new ArrayList<MessageParameter>();
			int size = lstCheckDetails.size() - 1;
			Long sequenceComponent = validationCheckDetailRepository.getSequencesValidationCheckDetail(size);
			startSequence = sequenceComponent - (lstCheckDetails.size() - 1);

			for (ValidationCheckDetail objDetail : lstCheckDetails) {
				if (CollectionUtils.isNotEmpty(objDetail.getParameters())) {
					totalMessage += objDetail.getParameters().size();
				}
			}

			size = totalMessage - 1;
			Long sequenceParameter = messageParameterRepository.getSequencesMessageParameter(size);
			Long startMessageParameterSequence = sequenceParameter - (size);

			for (ValidationCheckDetail objDetail : lstCheckDetails) {
				objDetail.setValidationCheckDetailId(startSequence);
				Long inputBeanId = mapInputBeanId.get(objDetail.getInputBeanId());
				objDetail.setInputBeanId(inputBeanId.toString());
				startSequence++;
				if (CollectionUtils.isNotEmpty(objDetail.getParameters())) {
					for (MessageParameter objMessage : objDetail.getParameters()) {
						objMessage.setMessageParameterId(startMessageParameterSequence);
						startMessageParameterSequence++;

						objMessage.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
						objMessage.setTargetId(objDetail.getValidationCheckDetailId());
						lstMessageParameters.add(objMessage);
					}
				}
			}
			validationCheckDetailRepository.registerValidationCheckDetails(lstCheckDetails);
			if (lstMessageParameters.size() > 0) {
				messageParameterRepository.registerMessageParameter(lstMessageParameters);
			}
		}
	}

	/**
	 * @param screenDesignObj
	 * @param screenDesignDefault
	 * @param lstScreenItems
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private List<InputBean> registerInputBeanOfSeachScreen(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, List<ScreenItem> lstScreenItemConditions, Long businessLogicId, Long acountId, Timestamp currentDate, Integer inputbeanType, List<ScreenItem> lstItemResult, Boolean flagProcessSearch) {
		List<InputBean> inputBeansRegisted = new ArrayList<InputBean>();
		int itemSeqNo = 0;
		List<InputBean> lstInputItem = new ArrayList<InputBean>();
		Set<String> hashSetCode = new HashSet<String>();
		Set<ScreenArea> areaUpdateMappingObjectType = new HashSet<ScreenArea>();
		String from = "From";
		String to = "To";
		
		List<Long> lstColumnIds = new ArrayList<Long>();
		if(FunctionCommon.isNotEmpty(lstScreenItemConditions)) {
			for(ScreenItem item : lstScreenItemConditions) {
				lstColumnIds.add(item.getColumnId());
			}
		}
		if(FunctionCommon.isNotEmpty(lstItemResult)) {
			for(ScreenItem item : lstItemResult) {
				lstColumnIds.add(item.getColumnId());
			}
		}
		List<TableDesignDetails> lstColumns = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIds);
		Map<Long, TableDesignDetails> mapColumns = new HashMap<Long, TableDesignDetails>();
		if (FunctionCommon.isNotEmpty(lstColumns)) {
			for (TableDesignDetails column : lstColumns) {
				mapColumns.put(column.getColumnId(), column);
			}
		}
		
		if(FunctionCommon.isNotEmpty(lstScreenItemConditions) && screenDesignDefault.getModuleTableMappings() != null ) {
			ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings()[0];
			Integer parentInputbeanId = 0;
			if(firstTable != null) {
				String areaCode = "";
				for (ScreenItem item : lstScreenItemConditions) {
					if (screenDesignObj.getScreenAreas() != null) {
						for (ScreenArea area : screenDesignObj.getScreenAreas()) {
							if (item.getScreenAreaId().equals(area.getScreenAreaId())) {
								area.setObjectMappingId(Long.parseLong(parentInputbeanId.toString()));
								areaUpdateMappingObjectType.add(area);
								areaCode = area.getAreaCode();
								break;
							}
						}
					}
				}
				String messageCodeOfTable = firstTable.getMessageDesign() != null ? firstTable.getMessageDesign().getMessageString() : "";
				InputBean inputbeanObject = GenerateScreenContruct.setInputbean(parentInputbeanId, screenDesignObj, FunctionCommon.isNotEmpty(areaCode) ? areaCode : firstTable.getTblDesignCode(), messageCodeOfTable, DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE, false, null, businessLogicId, null, firstTable.getTblDesignId(), null, itemSeqNo++, null, inputbeanType);
				lstInputItem.add(inputbeanObject);
			}
			
			for (ScreenItem item : lstScreenItemConditions) {
				if(screenDesignObj.getScreenId().equals(item.getScreenId())) {
					if (!hashSetCode.contains(item.getItemCode())) {
						if (DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getLogicalDataType())) {
							continue;
						}
						TableDesignDetails column = mapColumns.get(item.getColumnId());
						hashSetCode.add(item.getItemCode());
						String messageCode = item.getMessageDesign() == null ? BLANK : item.getMessageDesign().getMessageString();
						Integer dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType());
						if(ScreenDesignConst.FromTo.FROM_TO.equals(item.getDisplayFromTo()) && (DbDomainConst.LogicDataType.CURRENCY.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DECIMAL.equals(item.getLogicalDataType())
								|| DbDomainConst.LogicDataType.INTEGER.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DATETIME.equals(item.getLogicalDataType())
								|| DbDomainConst.LogicDataType.DATE.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.TIME.equals(item.getLogicalDataType()))) {
							InputBean inputBeanFrom = GenerateScreenContruct.setInputbean(null, screenDesignObj, item.getItemCode() + from, messageCode, dataType, false, parentInputbeanId.toString(), businessLogicId, item.getScreenItemId(), item.getTblDesignId(), item.getColumnId(), itemSeqNo++, column.getItemType(), inputbeanType);
							inputBeanFrom.setFromOrTo(FROM);
							lstInputItem.add(inputBeanFrom);
							
							InputBean inputBeanTo = GenerateScreenContruct.setInputbean(null, screenDesignObj, item.getItemCode() + to, messageCode, dataType, false, parentInputbeanId.toString(), businessLogicId, item.getScreenItemId(), item.getTblDesignId(), item.getColumnId(), itemSeqNo++, column.getItemType(), inputbeanType);
							inputBeanTo.setFromOrTo(TO);
							lstInputItem.add(inputBeanTo);
						} else {
							Boolean arrayFlag = false;
							if(DbDomainConst.LogicDataType.CHECKBOX.equals(column.getItemType())) {
								arrayFlag = isArrayForCheckbox(column);
							}
							if(DbDomainConst.LogicDataType.RADIO.equals(column.getItemType())) {
								arrayFlag = getStatusColumn(column.getBaseType().longValue(), column.getConstrainsType(), column.getDatasourceType(), column.getDatasourceId());
							}
							if(column.getDataTypeFlg().equals(DbDomainConst.DataTypeFlag.PRIMITIVE)){
								if (column.getDataType().intValue() == DbDomainConst.BaseType.BOOLEAN_BASETYPE) {
									arrayFlag = true;
								}
							} else {
								DomainDesign domainDesign = domainDesignRepository.findOne(column.getDataType());
								if (domainDesign!= null && domainDesign.getBaseType() == DbDomainConst.BaseType.BOOLEAN_BASETYPE) {
									arrayFlag = true;
								}
							}
							InputBean inputBean = GenerateScreenContruct.setInputbean(null, screenDesignObj, item.getItemCode(), messageCode, dataType, arrayFlag, parentInputbeanId.toString(), businessLogicId, item.getScreenItemId(), item.getTblDesignId(), item.getColumnId(), itemSeqNo++, column.getItemType(), inputbeanType);
							lstInputItem.add(inputBean);
							
							if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
								inputBean = GenerateScreenContruct.setInputbean(null, screenDesignObj, item.getItemCode() + AUTOCOMPLETE, messageCode, DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE, false, parentInputbeanId.toString(), businessLogicId, item.getScreenItemId(), item.getTblDesignId(), null, itemSeqNo++, column.getItemType(), inputbeanType);
								lstInputItem.add(inputBean);
							}
						}
					}
				}
			}
		}
		if(flagProcessSearch != null && flagProcessSearch && FunctionCommon.isNotEmpty(lstItemResult)) {
			List<ScreenItem> screenItemHiddenOfAreaResults = new ArrayList<ScreenItem>();
			for (ScreenItem item : lstItemResult) {
				if (ITEM_TYPE_HIDDEN.equals(item.getItemType())) {
					screenItemHiddenOfAreaResults.add(item);
				}
			}
			if (FunctionCommon.isNotEmpty(screenItemHiddenOfAreaResults)) {
				ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings()[0];
				Integer parentInputbeanId = itemSeqNo;
				if (firstTable != null) {
					String areaCode = "";
					for (ScreenItem item : screenItemHiddenOfAreaResults) {
						if (screenDesignObj.getScreenAreas() != null) {
							for (ScreenArea area : screenDesignObj.getScreenAreas()) {
								if (item.getScreenAreaId().equals(area.getScreenAreaId())) {
									area.setObjectMappingId(Long.parseLong(parentInputbeanId.toString()));
									areaUpdateMappingObjectType.add(area);
									areaCode = area.getAreaCode();
									break;
								}
							}
						}
					}
					String messageCodeOfTable = firstTable.getMessageDesign() != null ? firstTable.getMessageDesign().getMessageString() : "";
					InputBean inputbeanObject = GenerateScreenContruct.setInputbean(parentInputbeanId, screenDesignObj, FunctionCommon.isNotEmpty(areaCode) ? areaCode : firstTable.getTblDesignCode(), messageCodeOfTable, DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE, true, null, businessLogicId, null, firstTable.getTblDesignId(), null, itemSeqNo++, null, inputbeanType);
					lstInputItem.add(inputbeanObject);
				}
				for (ScreenItem item : screenItemHiddenOfAreaResults) {
					if(screenDesignObj.getScreenId().equals(item.getScreenId())) {
						if (!hashSetCode.contains(item.getItemCode())) {
							if (DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getLogicalDataType())) {
								continue;
							}
							TableDesignDetails column = mapColumns.get(item.getColumnId());
							hashSetCode.add(item.getItemCode());
							String messageCode = item.getMessageDesign() == null ? BLANK : item.getMessageDesign().getMessageString();
							Integer dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType());
							Boolean arrayFlag = false;
							if(DbDomainConst.LogicDataType.CHECKBOX.equals(column.getItemType())) {
								arrayFlag = isArrayForCheckbox(column);
							}
							InputBean inputBean = GenerateScreenContruct.setInputbean(null, screenDesignObj, item.getItemCode(), messageCode, dataType, arrayFlag, parentInputbeanId.toString(), businessLogicId, item.getScreenItemId(), item.getTblDesignId(), item.getColumnId(), itemSeqNo++, column.getItemType(), inputbeanType);
							lstInputItem.add(inputBean);
							if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
								inputBean = GenerateScreenContruct.setInputbean(null, screenDesignObj, item.getItemCode() + AUTOCOMPLETE, messageCode, DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE, false, parentInputbeanId.toString(), businessLogicId, item.getScreenItemId(), item.getTblDesignId(), null, itemSeqNo++, column.getItemType(), inputbeanType);
								lstInputItem.add(inputBean);
							}
						}
					}
				}
			}
		}
		Map<String, Long> mapInputbeanId = new HashMap<String, Long>();
		if (lstInputItem.size() > 0) {
			Long sequenceInBeanItem = businessDesignRepository.getSequencesInputBean(lstInputItem.size() - 1);
			Long startSequence = sequenceInBeanItem - (lstInputItem.size() - 1);
			for (InputBean obj : lstInputItem) {
				if(StringUtils.isNotBlank(obj.getInputBeanId())) {
					mapInputbeanId.put(obj.getInputBeanId(), startSequence);
				}
				obj.setInputBeanId(startSequence.toString());
				if(StringUtils.isNotBlank(obj.getParentInputBeanId())) {
					String parentInputbeanId = mapInputbeanId.get(obj.getParentInputBeanId()) != null ? mapInputbeanId.get(obj.getParentInputBeanId()).toString() : null;
					obj.setParentInputBeanId(parentInputbeanId);
				}
				inputBeansRegisted.add(obj);
				startSequence++;
			}
			businessDesignRepository.registerInputBean(lstInputItem);
		}
		// update object_mapping_type and object_mapping_id of screen area
		for (ScreenArea area : areaUpdateMappingObjectType) {
			area.setObjectMappingType(ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_INPUT_BEAN);
			area.setObjectMappingId(mapInputbeanId.get(area.getObjectMappingId().toString()));
			
			screenAreaRepository.updateObjectMappingTypeOfScreenArea(area);
		}
		return inputBeansRegisted;
	}

	/**
	 * @param screenDesignObj
	 * @param screenDesignDefault
	 * @param lstScreenItems
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private List<OutputBean> registerOutputBean(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, List<ScreenItem> lstScreenItems, Long businessLogicId, Long acountId, Timestamp currentDate, Boolean flagUndoBlogic) {
		List<OutputBean> outputBeansRegisted = new ArrayList<OutputBean>();
		int itemSeqNoOutput = 0;
		Long tempOutputbeanId = 0l;
		List<Long> columnIds = new ArrayList<Long>();
		Map<Long, TableDesignDetails> mapTableDesignDetails = new HashMap<Long, TableDesignDetails>();
		for (ScreenItem item : lstScreenItems) {
			if (item.getColumnId() != null && !columnIds.contains(item.getColumnId())) {
				columnIds.add(item.getColumnId());
			}
		}
		List<TableDesignDetails> lstTableDesignDetails = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, columnIds);
		if (FunctionCommon.isNotEmpty(lstTableDesignDetails)) {
			for (TableDesignDetails obj : lstTableDesignDetails) {
				mapTableDesignDetails.put(obj.getColumnId(), obj);
			}
		}
		for (ModuleTableMapping moduleTableMapping : screenDesignDefault.getModuleTableMappings()) {
			Long parentOutputBeanId = null;
			Boolean arrayFlg = null;
			if (LIST_TYPE.equals(moduleTableMapping.getTableMappingType())) {
				arrayFlg = true;
			} else {
				arrayFlg = false;
			}

			String messageCodeOfTable = moduleTableMapping.getMessageDesign() != null ? moduleTableMapping.getMessageDesign().getMessageCode() : "";
			OutputBean outputbeanObject;
			if(flagUndoBlogic) {
				outputbeanObject = GenerateScreenContruct.contructOutputbean(tempOutputbeanId, screenDesignObj, moduleTableMapping.getTblDesignCode(), moduleTableMapping.getTblDesignName(), messageCodeOfTable, DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE, businessLogicId, null, itemSeqNoOutput++, moduleTableMapping.getTblDesignId(), null, null, null, arrayFlg, null, true, false, false, null, null, null);
			} else {
				outputbeanObject = GenerateScreenContruct.contructOutputbean(tempOutputbeanId, screenDesignObj, moduleTableMapping.getTblDesignCode(), moduleTableMapping.getTblDesignName(), messageCodeOfTable, DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE, businessLogicId, null, itemSeqNoOutput++, moduleTableMapping.getTblDesignId(), null, null, null, arrayFlg, null, true, false, false, null, null, null);
			}
			
			outputBeansRegisted.add(outputbeanObject);
			parentOutputBeanId = tempOutputbeanId;
			tempOutputbeanId++;

			if(ScreenDesignConst.CONFIRM_REGISTER_SCREEN.equals(screenDesignObj.getScreenTypeName())
					|| ScreenDesignConst.CONFIRM_MODIFY_SCREEN.equals(screenDesignObj.getScreenTypeName())) {
				Map<String, String> mapOutputbeanIdAndScreenId = new HashMap<String, String>();
				List<ScreenItem> lstItemHiddensOfConfirmScreen = new ArrayList<ScreenItem>();
				for (ScreenItem item : lstScreenItems) {
					if (screenDesignObj.getScreenId() != null && item.getScreenId() != null && screenDesignObj.getScreenId().equals(item.getScreenId()) && moduleTableMapping.getTblDesignId() != null && item.getTblDesignId() != null && item.getTblDesignId().equals(moduleTableMapping.getTblDesignId())) {
						if (ITEM_TYPE_HIDDEN.equals(item.getItemType()) && (item.getIsPkHidden() == null || !item.getIsPkHidden())) {
							lstItemHiddensOfConfirmScreen.add(item);
						}
					}
				}
				for (ScreenItem item : lstScreenItems) {
					if (screenDesignObj.getScreenId() != null && item.getScreenId() != null && screenDesignObj.getScreenId().equals(item.getScreenId()) && moduleTableMapping.getTblDesignId() != null && item.getTblDesignId() != null && item.getTblDesignId().equals(moduleTableMapping.getTblDesignId())) {
						if ((ITEM_TYPE_NORMAL.equals(item.getItemType())) || (ITEM_TYPE_HIDDEN.equals(item.getItemType()) && item.getIsPkHidden() != null && item.getIsPkHidden())) {
							// not support MultipartFile in confirm/complete screen
							if (item.getPhysicalDataType() != null && DbDomainConst.BaseType.BINARY_BASETYPE != item.getPhysicalDataType().intValue()) {
								Boolean arrayFlag = null;
								TableDesignDetails tblDesignDetails = mapTableDesignDetails.get(item.getColumnId());
								if (flagUndoBlogic) {
									if (tblDesignDetails != null && isArrayForCheckbox(tblDesignDetails)) {
										arrayFlag = true;
									}
								}
								OutputBean outputBean = new OutputBean();
								Integer dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType());
								String parentString = parentOutputBeanId == null ? null : parentOutputBeanId.toString();
								outputBean = GenerateScreenContruct.contructOutputbean(tempOutputbeanId++, screenDesignObj, item.getItemCode(), item.getItemName(), item.getMessageCode(), dataType, businessLogicId, parentString, itemSeqNoOutput++, item.getTblDesignId(), item.getColumnId(), tblDesignDetails.getGroupBaseTypeId(), item.getScreenItemId(), arrayFlag, item, false, false, true, lstItemHiddensOfConfirmScreen, mapOutputbeanIdAndScreenId, tblDesignDetails.getDatasourceType());
								if (flagUndoBlogic) {
									outputBean.setObjectFlg(true);
								}
								outputBeansRegisted.add(outputBean);
							}
						}
					}
				}
			} else {
				for (ScreenItem item : lstScreenItems) {
					if (screenDesignObj.getScreenId() != null && item.getScreenId() != null && screenDesignObj.getScreenId().equals(item.getScreenId()) && moduleTableMapping.getTblDesignId() != null && item.getTblDesignId() != null && item.getTblDesignId().equals(moduleTableMapping.getTblDesignId())) {
						OutputBean outputBean = new OutputBean();
						Integer dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType());
						String parentString = parentOutputBeanId == null ? null : parentOutputBeanId.toString();
						TableDesignDetails tblDesignDetails = mapTableDesignDetails.get(item.getColumnId());
						outputBean = GenerateScreenContruct.contructOutputbean(tempOutputbeanId++, screenDesignObj, item.getItemCode(), item.getItemName(), item.getMessageCode(), dataType, businessLogicId, parentString, itemSeqNoOutput++, item.getTblDesignId(), item.getColumnId(), tblDesignDetails.getGroupBaseTypeId(), item.getScreenItemId(), null, item, false, false, false, null, null, tblDesignDetails.getDatasourceType());
						
						outputBeansRegisted.add(outputBean);
					}
				}
			}
		}

		if (outputBeansRegisted.size() > 0) {
			Long sequenceOutputBeanItem = businessDesignRepository.getSequencesOutputBean(outputBeansRegisted.size() - 1);
			Long startSequence = sequenceOutputBeanItem - (outputBeansRegisted.size() - 1);
			Map<String, Long> mapOutputbeanId = new HashMap<String, Long>();
			for (OutputBean obj : outputBeansRegisted) {
				mapOutputbeanId.put(obj.getOutputBeanId(), startSequence);
				obj.setOutputBeanId(startSequence.toString());
				String parentOutputBeanId = mapOutputbeanId.get(obj.getParentOutputBeanId()) != null ? mapOutputbeanId.get(obj.getParentOutputBeanId()).toString() : null;
				obj.setParentOutputBeanId(parentOutputBeanId);
				startSequence++;
			}
			businessDesignRepository.registerOutputBean(outputBeansRegisted);
		}

		// store all input beans of business logic in map
		screenDesignDefault.getMapOutputBeanOfBLogic().put(businessLogicId, outputBeansRegisted);

		return outputBeansRegisted;
	}

	/**
	 * @param screenDesignObj
	 * @param screenDesignDefault
	 * @param lstScreenItems
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private List<OutputBean> registerOutputBeanOfSearchScreen(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, List<ScreenItem> lstScreenItemSearchResults, Long businessLogicId, Long acountId, Timestamp currentDate) {
		List<OutputBean> outputBeansRegisted = new ArrayList<OutputBean>();
		int itemSeqNoOutput = 0;
		Long tempOutputbeanId = 0l;
		ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings()[0];
		OutputBean outputbeanObject = GenerateScreenContruct.contructOutputbean(tempOutputbeanId, screenDesignObj, firstTable.getTblDesignCode(), firstTable.getTblDesignName(), null, DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE, businessLogicId, null, itemSeqNoOutput++, firstTable.getTblDesignId(), null, null, null, true, null, true, false, false, null, null, null);
		outputBeansRegisted.add(outputbeanObject);
		Long parentOutputBean = tempOutputbeanId;
		tempOutputbeanId++;

		List<Long> columnIds = new ArrayList<Long>();
		Map<Long, TableDesignDetails> mapTableDesignDetails = new HashMap<Long, TableDesignDetails>();
		for (ScreenItem item : lstScreenItemSearchResults) {
			if (item.getColumnId() != null && !columnIds.contains(item.getColumnId())) {
				columnIds.add(item.getColumnId());
			}
		}
		List<TableDesignDetails> lstTableDesignDetails = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, columnIds);
		if (FunctionCommon.isNotEmpty(lstTableDesignDetails)) {
			for (TableDesignDetails obj : lstTableDesignDetails) {
				mapTableDesignDetails.put(obj.getColumnId(), obj);
			}
		}
		Set<String> hashSetCode = new HashSet<String>();
		if (lstScreenItemSearchResults != null && lstScreenItemSearchResults.size() > 0) {
			for (ScreenItem item : lstScreenItemSearchResults) {
				if (!hashSetCode.contains(item.getItemCode())) {
					hashSetCode.add(item.getItemCode());
					String messageCode = item.getMessageDesign() == null ? null : item.getMessageDesign().getMessageCode();
					Integer dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType());
					String parentString = parentOutputBean == null ? null : parentOutputBean.toString();
					TableDesignDetails tblDesignDetails = mapTableDesignDetails.get(item.getColumnId());
					OutputBean outputBean = GenerateScreenContruct.contructOutputbean(tempOutputbeanId++, screenDesignObj, item.getItemCode(), item.getItemName(), messageCode, dataType, businessLogicId, parentString, itemSeqNoOutput++, item.getTblDesignId(), item.getColumnId(), tblDesignDetails.getGroupBaseTypeId(), item.getScreenItemId(), null, item, true, false, false, null, null, tblDesignDetails.getDatasourceType());
					outputBeansRegisted.add(outputBean);
				}
			}
		}

		OutputBean totalCountOb = GenerateScreenContruct.contructOutputbean(tempOutputbeanId++,  screenDesignObj,TOTALCOUNT, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0077), null, DbDomainConst.JavaDataTypeOfBlogic.LONG_DATATYPE, businessLogicId, null, itemSeqNoOutput++, null, null, null, null, null, null, true, true, false, null, null, null);
		outputBeansRegisted.add(totalCountOb);

		if (outputBeansRegisted.size() > 0) {
			Long sequenceOutputBeanItem = businessDesignRepository.getSequencesOutputBean(outputBeansRegisted.size() - 1);
			Long startSequence = sequenceOutputBeanItem - (outputBeansRegisted.size() - 1);
			Map<String, Long> mapOutputId = new HashMap<String, Long>();
			for (OutputBean obj : outputBeansRegisted) {
				mapOutputId.put(obj.getOutputBeanId(), startSequence);
				obj.setOutputBeanId(startSequence.toString());
				String parentOutputBeanId = mapOutputId.get(obj.getParentOutputBeanId()) != null ? mapOutputId.get(obj.getParentOutputBeanId()).toString() : null;
				obj.setParentOutputBeanId(parentOutputBeanId);
				startSequence++;
			}
			businessDesignRepository.registerOutputBean(outputBeansRegisted);
		}

		return outputBeansRegisted;
	}

	/**
	 * @param screenDesignObj
	 * @param screenDesignDefault
	 * @param lstScreenItems
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private List<ObjectDefinition> registerObjectDefiniton(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, Integer type) {
		int itemSeqNoObjDef = 0;
		Long tempObjDefId = 0l;
		List<ObjectDefinition> objDefsRegisted = new ArrayList<ObjectDefinition>();
		List<Long> columnIds = new ArrayList<Long>();
		Map<Long, TableDesignDetails> mapTableDesignDetails = new HashMap<Long, TableDesignDetails>();
		for (ModuleTableMapping moduleTableMapping : screenDesignDefault.getModuleTableMappings()) {
			for (TableDesignDetails item : moduleTableMapping.getListAllColumns()) {
				if (item.getColumnId() != null && !columnIds.contains(item.getColumnId())) {
					columnIds.add(item.getColumnId());
				}
			}
		}
		List<TableDesignDetails> lstTableDesignDetails = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, columnIds);
		if (FunctionCommon.isNotEmpty(lstTableDesignDetails)) {
			for (TableDesignDetails obj : lstTableDesignDetails) {
				mapTableDesignDetails.put(obj.getColumnId(), obj);
			}
		}
		for (ModuleTableMapping moduleTableMapping : screenDesignDefault.getModuleTableMappings()) {
			List<TableDesignDetails> lstAllColumnsOfTable = moduleTableMapping.getListAllColumns();
			Long parentObjDefId = null;
			Boolean arrFlag = null;
			if (ScreenDesignConst.SEARCH_SCREEN.equals(screenDesignObj.getScreenTypeName())) {
				arrFlag = true;
			} else {
				if (LIST_TYPE.equals(moduleTableMapping.getTableMappingType())) {
					//QuangVD
					// Case : processRegister/processModify : use single object
					if((ScreenDesignConst.REGISTER_SCREEN.equals(screenDesignObj.getScreenTypeName()) || ScreenDesignConst.MODIFY_SCREEN.equals(screenDesignObj.getScreenTypeName())
							|| ScreenDesignConst.CONFIRM_REGISTER_SCREEN.equals(screenDesignObj.getScreenTypeName()) || ScreenDesignConst.CONFIRM_MODIFY_SCREEN.equals(screenDesignObj.getScreenTypeName()))
							&& BusinessDesignConst.RETURN_TYPE_SCREEN.equals(type)){
						arrFlag = false;
					} else {
						arrFlag = true;
					}
				} else {
					arrFlag = false;
				}
			}
			ObjectDefinition objectDefinitionParent = GenerateScreenContruct.contructObjectDefinition(tempObjDefId, moduleTableMapping.getTblDesignCode(), moduleTableMapping.getTblDesignName(), DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE, businessLogicId, null, itemSeqNoObjDef++, null, moduleTableMapping.getTblDesignId(), null, null, arrFlag, null, null, true, false, null);

			objDefsRegisted.add(objectDefinitionParent);
			parentObjDefId = tempObjDefId;
			tempObjDefId++;

			for (TableDesignDetails item : lstAllColumnsOfTable) {
				ObjectDefinition objectDefinition = new ObjectDefinition();
				Integer dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getBaseType());
				String parentIdString = parentObjDefId == null ? null : parentObjDefId.toString();
				TableDesignDetails tblDesignDetails = mapTableDesignDetails.get(item.getColumnId());
				objectDefinition = GenerateScreenContruct.contructObjectDefinition(tempObjDefId++, item.getCode(), item.getName(), dataType, businessLogicId, parentIdString, itemSeqNoObjDef++, item.getKeyType(), item.getTableDesignId(), item.getColumnId(), tblDesignDetails.getGroupBaseTypeId(), false, null, null, false, false, null);
				objectDefinition.setBaseType(item.getBaseType());
				objDefsRegisted.add(objectDefinition);
			}
		}
		
		if (objDefsRegisted.size() > 0) {
			Long sequenceObjectDefinitionItem = businessDesignRepository.getSequencesObjectDefinition(objDefsRegisted.size() - 1);
			Long startSequence = sequenceObjectDefinitionItem - (objDefsRegisted.size() - 1);
			Map<String, Long> mapObjDefId = new HashMap<String, Long>();
			for (ObjectDefinition obj : objDefsRegisted) {
				mapObjDefId.put(obj.getObjectDefinitionId(), startSequence);
				obj.setObjectDefinitionId(startSequence.toString());
				String parentId = mapObjDefId.get(obj.getParentObjectDefinitionId()) != null ? mapObjDefId.get(obj.getParentObjectDefinitionId()).toString() : null;
				obj.setParentObjectDefinitionId(parentId);
				startSequence++;
			}
			businessDesignRepository.registerObjectDefinition(objDefsRegisted);
		}
		return objDefsRegisted;
	}

	/**
	 * @param screenDesignObj
	 * @param screenDesignDefault
	 * @param lstScreenItems
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private List<ObjectDefinition> registerObjectDefinitionOfSearchScreen(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, List<ScreenItem> lstScreenItemSearchConditions, Long businessLogicId, Long acountId, Timestamp currentDate, Boolean arrayFlg, Boolean flagProcessSearch) {
		int itemSeqNo = 0;
		Long tempObjDefId = 0l;
		String from = "From";
		String to = "To";
		
		List<Long> columnIds = new ArrayList<Long>();
		Map<Long, TableDesignDetails> mapTableDesignDetails = new HashMap<Long, TableDesignDetails>();
		for (ScreenItem item : lstScreenItemSearchConditions) {
			if (item.getColumnId() != null && !columnIds.contains(item.getColumnId())) {
				columnIds.add(item.getColumnId());
			}
		}
		List<TableDesignDetails> lstTableDesignDetails = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, columnIds);
		if (FunctionCommon.isNotEmpty(lstTableDesignDetails)) {
			for (TableDesignDetails obj : lstTableDesignDetails) {
				mapTableDesignDetails.put(obj.getColumnId(), obj);
			}
		}

		List<ObjectDefinition> objDefsRegisted = new ArrayList<ObjectDefinition>();
		ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings()[0];
		ObjectDefinition objDefParent = GenerateScreenContruct.contructObjectDefinition(tempObjDefId, firstTable.getTblDesignCode(), firstTable.getTblDesignName(), DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE, 
				businessLogicId, null, itemSeqNo++, null, firstTable.getTblDesignId(), null, null, arrayFlg, firstTable.getTblDesignName(), firstTable.getTblDesignCode(), true, false, null);
		objDefsRegisted.add(objDefParent);
		Long parentObjDef = tempObjDefId;
		tempObjDefId++;

		Set<String> hashSetCode = new HashSet<String>();
		if(flagProcessSearch) {
			for (ScreenItem item : lstScreenItemSearchConditions) {
				if(screenDesignObj.getScreenId().equals(item.getScreenId())) {
					if (!hashSetCode.contains(item.getItemCode())) {
						if (DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getLogicalDataType())) {
							continue;
						}
						TableDesignDetails column = mapTableDesignDetails.get(item.getColumnId());
						// Convert to radio if item type = checkbox
//						if (DbDomainConst.LogicDataType.RADIO.equals(column.getItemType())) {
//							column.setItemType(DbDomainConst.LogicDataType.CHECKBOX);
//						}
						hashSetCode.add(item.getItemCode());
						Integer dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType());
						if (DbDomainConst.LogicDataType.CHECKBOX.equals(column.getItemType())) {
							if(DbDomainConst.BaseType.BOOLEAN_BASETYPE != column.getBaseType()) {
								dataType = DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE;
							} else {
								dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType());
							}
						} else {
							dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType());
						}
						String parentString = parentObjDef == null ? null : parentObjDef.toString();
						TableDesignDetails tblDesignDetails = mapTableDesignDetails.get(item.getColumnId());
						if(ScreenDesignConst.FromTo.FROM_TO.equals(item.getDisplayFromTo()) && (DbDomainConst.LogicDataType.CURRENCY.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DECIMAL.equals(item.getLogicalDataType())
								|| DbDomainConst.LogicDataType.INTEGER.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DATETIME.equals(item.getLogicalDataType())
								|| DbDomainConst.LogicDataType.DATE.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.TIME.equals(item.getLogicalDataType()))) {
							ObjectDefinition objdefFrom = GenerateScreenContruct.contructObjectDefinition(tempObjDefId++, item.getItemCode() + from, item.getItemCode(), dataType, businessLogicId, parentString, itemSeqNo++, null, item.getTblDesignId(), item.getColumnId(), tblDesignDetails.getGroupBaseTypeId(), null, item.getTblDesignName(), item.getTblDesignCode(), true, false, null);
							objdefFrom.setFromOrTo(FROM);
							objDefsRegisted.add(objdefFrom);
							
							ObjectDefinition objdefTo = GenerateScreenContruct.contructObjectDefinition(tempObjDefId++, item.getItemCode() + to, item.getItemCode(), dataType, businessLogicId, parentString, itemSeqNo++, null, item.getTblDesignId(), item.getColumnId(), tblDesignDetails.getGroupBaseTypeId(), null, item.getTblDesignName(), item.getTblDesignCode(), true, false, null);
							objdefTo.setFromOrTo(TO);
							objDefsRegisted.add(objdefTo);
						} else {
							Boolean arrayFlag = false;
							if (DbDomainConst.BaseType.BOOLEAN_BASETYPE == column.getBaseType()) {
								arrayFlag = true;
							}
							if (DbDomainConst.LogicDataType.RADIO.equals(column.getItemType())) {
								arrayFlag = getStatusColumn(column.getBaseType().longValue(), column.getConstrainsType(), column.getDatasourceType(), column.getDatasourceId());
							}
							ObjectDefinition objdef = GenerateScreenContruct.contructObjectDefinition(tempObjDefId++, item.getItemCode(), item.getItemCode(), dataType, businessLogicId, parentString, itemSeqNo++, null, item.getTblDesignId(), item.getColumnId(), tblDesignDetails.getGroupBaseTypeId(), arrayFlag, item.getTblDesignName(), item.getTblDesignCode(), true, false, null);
							objDefsRegisted.add(objdef);
						}
					}
				}
			}
		} else {
			for (ScreenItem item : lstScreenItemSearchConditions) {
				if (!hashSetCode.contains(item.getItemCode())) {
					hashSetCode.add(item.getItemCode());
					Integer dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType());
					String parentString = parentObjDef == null ? null : parentObjDef.toString();
					TableDesignDetails tblDesignDetails = mapTableDesignDetails.get(item.getColumnId());
					ObjectDefinition objDef = GenerateScreenContruct.contructObjectDefinition(tempObjDefId++, item.getItemCode(), item.getItemName(), dataType, businessLogicId, parentString, itemSeqNo++, null, item.getTblDesignId(), item.getColumnId(), tblDesignDetails.getGroupBaseTypeId(), null, item.getTblDesignName(), item.getTblDesignCode(), true, false, null);
					objDefsRegisted.add(objDef);
				}
			}
			// add object definition total count
			ObjectDefinition totalCountObjDef = GenerateScreenContruct.contructObjectDefinition(tempObjDefId++, TOTALCOUNT, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0077), DbDomainConst.JavaDataTypeOfBlogic.LONG_DATATYPE, businessLogicId, null, itemSeqNo++, null, null, null, null, false, null, null, true, true, null);
			objDefsRegisted.add(totalCountObjDef);
		}
		// register
		if (objDefsRegisted.size() > 0) {
			Long sequenceObjDefItem = businessDesignRepository.getSequencesObjectDefinition(objDefsRegisted.size() - 1);
			Long startSequence = sequenceObjDefItem - (objDefsRegisted.size() - 1);
			Map<String, Long> mapObjDefId = new HashMap<String, Long>();
			for (ObjectDefinition obj : objDefsRegisted) {
				mapObjDefId.put(obj.getObjectDefinitionId(), startSequence);
				obj.setObjectDefinitionId(startSequence.toString());
				String parentId = mapObjDefId.get(obj.getParentObjectDefinitionId()) != null ? mapObjDefId.get(obj.getParentObjectDefinitionId()).toString() : null;
				obj.setParentObjectDefinitionId(parentId);
				startSequence++;
			}
			businessDesignRepository.registerObjectDefinition(objDefsRegisted);
		}
		return objDefsRegisted;
	}

	/**
	 * @param screenDesignObj
	 * @param screenDesignDefault
	 * @param lstScreenItems
	 * @return
	 */
	private List<ScreenItem> findKeyOfFirstTable(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, List<ScreenItem> lstScreenItems) {
		List<ScreenItem> lstKey = new ArrayList<ScreenItem>();
		List<ScreenItem> itemsOfFirstTable = new ArrayList<ScreenItem>();
		List<ScreenItem> lstPrimaryKey = new ArrayList<ScreenItem>();
		List<ScreenItem> lstUniqueKey = new ArrayList<ScreenItem>();
		ModuleTableMapping firstTable = new ModuleTableMapping();
		if (screenDesignDefault.getModuleTableMappings().length > 0) {
			firstTable = screenDesignDefault.getModuleTableMappings()[0];
		}

		for (ScreenItem item : lstScreenItems) {
			if (screenDesignObj.getScreenId() != null && item.getScreenId() != null && screenDesignObj.getScreenId().equals(item.getScreenId()) && item.getTblDesignId() != null && firstTable.getTblDesignId() != null && item.getTblDesignId().equals(firstTable.getTblDesignId())) {
				itemsOfFirstTable.add(item);
			}
		}
		if (FunctionCommon.isEmpty(itemsOfFirstTable)) {
			for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
				for (ScreenItem item : lstScreenItems) {
					if (screenDesignObj.getScreenId() != null && item.getScreenId() != null && screenDesignObj.getScreenId().equals(item.getScreenId()) && item.getTblDesignId() != null && table.getTblDesignId() != null && item.getTblDesignId().equals(table.getTblDesignId())) {
						itemsOfFirstTable.add(item);
					}
				}
				if (FunctionCommon.isNotEmpty(itemsOfFirstTable)) {
					firstTable = table;
					break;
				}
			}
		}

		// if table mapping is single then choice Primary key / Unique / First column
		if (SINGLE_TYPE.equals(firstTable.getTableMappingType())) {
			for (ScreenItem item : itemsOfFirstTable) {
				if (DbDomainConst.YesNoFlg.YES.equals(item.isKey(DbDomainConst.TblDesignKeyType.PK))) {
					lstPrimaryKey.add(item);
				}
				if (DbDomainConst.YesNoFlg.YES.equals(item.isKey(DbDomainConst.TblDesignKeyType.UNIQUE))) {
					lstUniqueKey.add(item);
				}
			}
			if (lstPrimaryKey.size() > 0) {
				lstKey = new ArrayList<ScreenItem>(lstPrimaryKey);
			} else if (lstUniqueKey.size() > 0) {
				lstKey = new ArrayList<ScreenItem>(lstUniqueKey);
			} else {
				if(FunctionCommon.isNotEmpty(itemsOfFirstTable) && itemsOfFirstTable.size() > 0) {
					lstKey.add(itemsOfFirstTable.get(0));
				}
			}
		}
		// if table mapping is list then choice First column ( difference Primary key / Unique )
		else {
			for (ScreenItem item : itemsOfFirstTable) {
				if (DbDomainConst.YesNoFlg.NO.equals(item.isKey(DbDomainConst.TblDesignKeyType.PK)) && DbDomainConst.YesNoFlg.NO.equals(item.isKey(DbDomainConst.TblDesignKeyType.UNIQUE))) {
					lstKey.add(item);
					break;
				}
			}
		}

		return lstKey;
	}

	/**
	 * @param screenDesignObj
	 * @param screenDesignDefault
	 * @param lstScreenItems
	 * @return
	 */
	private List<ScreenItem> findInputForDelete(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, List<ScreenItem> lstScreenItems) {
		List<ScreenItem> lstKey = new ArrayList<ScreenItem>();
		List<ScreenItem> lstPrimaryKey = new ArrayList<ScreenItem>();
		List<ScreenItem> lstUniqueKey = new ArrayList<ScreenItem>();
		ModuleTableMapping firstTable = new ModuleTableMapping();
		if (screenDesignDefault.getModuleTableMappings().length > 0) {
			firstTable = screenDesignDefault.getModuleTableMappings()[0];
		}
		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			List<ScreenItem> itemsOfTable = new ArrayList<ScreenItem>();
			// input of first table
			if (table.getTblDesignId().equals(firstTable.getTblDesignId())) {
				for (ScreenItem item : lstScreenItems) {
					if (screenDesignObj.getScreenId() != null && item.getScreenId() != null && screenDesignObj.getScreenId().equals(item.getScreenId()) && item.getTblDesignId() != null && firstTable.getTblDesignId() != null && item.getTblDesignId().equals(firstTable.getTblDesignId())) {
						itemsOfTable.add(item);
					}
				}
				// if table mapping is single then choice Primary key / Unique / First column
				if (SINGLE_TYPE.equals(firstTable.getTableMappingType())) {
					for (ScreenItem item : itemsOfTable) {
						if (DbDomainConst.YesNoFlg.YES.equals(item.isKey(DbDomainConst.TblDesignKeyType.PK))) {
							lstPrimaryKey.add(item);
						}
						if (DbDomainConst.YesNoFlg.YES.equals(item.isKey(DbDomainConst.TblDesignKeyType.UNIQUE))) {
							lstUniqueKey.add(item);
						}
					}
					if (lstPrimaryKey.size() > 0) {
						lstKey = new ArrayList<ScreenItem>(lstPrimaryKey);
					} else if (lstUniqueKey.size() > 0) {
						lstKey = new ArrayList<ScreenItem>(lstUniqueKey);
					} else {
						if (FunctionCommon.isNotEmpty(itemsOfTable) && itemsOfTable.size() > 0) {
							lstKey.add(itemsOfTable.get(0));
						}
					}
				}
				// if table mapping is list then choice First column ( difference Primary key / Unique )
				else {
					for (ScreenItem item : itemsOfTable) {
						if (DbDomainConst.YesNoFlg.NO.equals(item.isKey(DbDomainConst.TblDesignKeyType.PK)) && DbDomainConst.YesNoFlg.NO.equals(item.isKey(DbDomainConst.TblDesignKeyType.UNIQUE))) {
							lstKey.add(item);
							break;
						}
					}
				}
			} else { // input of children table
				for (ScreenItem item : lstScreenItems) {
					if (screenDesignObj.getScreenId() != null && item.getScreenId() != null && screenDesignObj.getScreenId().equals(item.getScreenId())
							&& item.getTblDesignId() != null && table.getTblDesignId() != null && item.getTblDesignId().equals(table.getTblDesignId())) {
						itemsOfTable.add(item);
					}
				}
				Long referColumnId = 0L;
				for (TableDesignForeignKey fk : allForeignKeyInProject) {
					if (fk.getFromTableId().equals(table.getTblDesignId()) && fk.getToTableId().equals(firstTable.getTblDesignId())) {
						referColumnId = fk.getFromColumnId();
						break;
					}
				}
				for (ScreenItem item : itemsOfTable) {
					if(referColumnId.equals(item.getColumnId())) {
						lstKey.add(item);
					}
				}
			}
		}
		return lstKey;
	}

	private List<TableDesignDetails> findPKOfTable(ModuleTableMapping table) {
		List<TableDesignDetails> lstKey = new ArrayList<TableDesignDetails>();
		List<TableDesignDetails> lstColumns = table.getListAllColumns();
		if (FunctionCommon.isNotEmpty(lstColumns)) {
			for (TableDesignDetails item : lstColumns) {
				if (DbDomainConst.YesNoFlg.YES.equals(item.isKey(DbDomainConst.TblDesignKeyType.PK))) {
					lstKey.add(item);
				}
			}
		}
		return lstKey;
	}
	
	private List<TableDesignDetails> findKeyOfTable(ModuleTableMapping table) {
		List<TableDesignDetails> lstKey = new ArrayList<TableDesignDetails>();
		List<TableDesignDetails> lstColumns = table.getListAllColumns();
		if (FunctionCommon.isNotEmpty(lstColumns)) {
			for (TableDesignDetails item : lstColumns) {
				if (DbDomainConst.YesNoFlg.YES.equals(item.isKey(DbDomainConst.TblDesignKeyType.FK)) || DbDomainConst.YesNoFlg.YES.equals(item.isKey(DbDomainConst.TblDesignKeyType.PK))) {
					lstKey.add(item);
				}
			}
		}
		return lstKey;
	}

	/**
	 * @param table
	 * @param screenDesignObj
	 * @param screenDesignDefault
	 * @return
	 */
	private List<ScreenItem> findKeyOfTableUsingScreenItems(ModuleTableMapping table, ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault) {
		List<ScreenItem> lstScreenItems = screenDesignDefault.getLstScreenItem();
		List<ScreenItem> lstInputItems = new ArrayList<ScreenItem>();
		List<ScreenItem> itemsOfTable = new ArrayList<ScreenItem>();
		List<TableDesignKeyItem> keysOfTable = new ArrayList<TableDesignKeyItem>();
		Set<Long> primaryKeys = new HashSet<Long>();
		Set<Long> uniqueKeys = new HashSet<Long>();
		for (ScreenItem item : lstScreenItems) {
			if (screenDesignObj.getScreenId() != null && item.getScreenId() != null && screenDesignObj.getScreenId().equals(item.getScreenId()) && item.getTblDesignId() != null && table.getTblDesignId() != null && item.getTblDesignId().equals(table.getTblDesignId())) {
				itemsOfTable.add(item);
			}
		}
		for (TableDesignKeyItem key : allKeys) {
			if (key.getTableDesignId() != null && key.getTableDesignId().equals(table.getTblDesignId())) {
				keysOfTable.add(key);
			}
		}
		for (TableDesignKeyItem key : keysOfTable) {
			if (key.getType().equals(DbDomainConst.TblDesignKeyType.PK)) {
				primaryKeys.add(key.getColumnId());
			}
			if (key.getType().equals(DbDomainConst.TblDesignKeyType.UNIQUE)) {
				uniqueKeys.add(key.getColumnId());
			}
		}
		if (keysOfTable.size() > 0) {
			if (primaryKeys.size() > 0) {
				for (Long key : primaryKeys) {
					for (ScreenItem item : itemsOfTable) {
						if (item.getColumnId() != null && key.equals(item.getColumnId())) {
							lstInputItems.add(item);
						}
					}
				}
			} else if (uniqueKeys.size() > 0) {
				for (Long key : uniqueKeys) {
					for (ScreenItem item : itemsOfTable) {
						if (item.getColumnId() != null && key.equals(item.getColumnId())) {
							lstInputItems.add(item);
						}
					}
				}
			}
		} else {
			if (itemsOfTable.size() > 0) {
				lstInputItems.add(itemsOfTable.get(0));
			}
		}
		// if don't have congruous item, get first column
		if (lstInputItems.size() == 0 && itemsOfTable.size() > 0) {
			lstInputItems.add(itemsOfTable.get(0));
		}

		return lstInputItems;
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void undoRegisterBlogic(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		// REGISTER BUSINESS LOGIC : UNDO REGISTER
		BusinessDesign businessLogic = registerBusinesslogicDefault(sObj, screenDesignDefault, BusinessDesignConst.UNDO_REGISTER, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0029, DbDomainConst.BlogicReturnType.SCREEN, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_CONFIRM_REGISTER, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
		// REGISTER INPUT BEAN OF THIS BLOGIC
		List<InputBean> inputbeansRegisted = registerInputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic, acountId, currentDate, true, false);
		// register output bean of this blogic
		List<OutputBean> outputBeansRegisted = registerOutputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic.getBusinessLogicId(), acountId, currentDate, true);
		// REGISTER SEQUENCE LOGIC
		generateSequenceLogicUndoRegister(sObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, inputbeansRegisted, outputBeansRegisted);
		// REGISTER COMPONENT
		registerComponent();
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicUndoRegister(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<InputBean> inputbeansRegisted, List<OutputBean> outputBeansRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L;;
		SequenceConnector connector;
		Integer tempId = 0;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempId++;
		// Set to HaskMap
		mNameParameter = DetailServiceImpHandler.setLevelOfInputBeanOutputBeanObjectDefinition(inputbeansRegisted, outputBeansRegisted, null);

		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			yCoordinates = yCoordinates + 70;
			SequenceLogic assign = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
			lstSequenceLogic.add(assign);
			// set assign component
			AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), tempId.longValue());
			lstAssignComponents.add(assignComponent);
			// set assign details
			contructAssignDetailFromInputBeanToOutputBean(table, inputbeansRegisted, outputBeansRegisted, assignIdTemp, true, screenDesignDefault.getCommonModel());
			assignIdTemp++;
			tempId++;
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic navigator = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0067) + SPACE, DbDomainConst.ComponentType.NAVIGATOR, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(navigator);
		// do set data for navigator component
		BusinessDesign displayRegisterBlogic = screenDesignDefault.getMapBusinessLogicId().get(BusinessDesignConst.DISPLAY_REGISTER);
		ScreenDesign screenRegister = screenDesignDefault.getMapScreenDesign().get(ScreenDesignConst.REGISTER_SCREEN);
		NavigatorComponent navigatorComponent = GenerateScreenContruct.populateNavigatorComponent(screenDesignDefault, tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0067), screenRegister.getScreenId(), null, screenRegister.getMessageDesign().getMessageCode(), BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_SCREEN);
		lstNavigatorComponents.add(navigatorComponent);
		// navigator details
		contructNavigatorDetail(screenDesignDefault, tempId.longValue(), outputBeansRegisted, displayRegisterBlogic.getBusinessLogicId());
		tempId++;

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);

		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);
		
		updateBLogicNavigator(BACK_REGISTER_COMFIRM_NAVIGATE, businessLogicId);

		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for navigator component
		for (NavigatorComponent navi : lstNavigatorComponents) {
			navi.setSequenceLogicId(mapKeySequence.get(navi.getSequenceLogicId().toString()));
		}
	}


	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void redirectConfirmRegisterBlogic(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		// REGISTER BUSINESS LOGIC : UNDO REGISTER
		BusinessDesign businessLogic = registerBusinesslogicDefault(sObj, screenDesignDefault, BusinessDesignConst.REDIRECT_CONFIRM_REGISTER, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0031, DbDomainConst.BlogicReturnType.SCREEN, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_REDIRECT_CONFIRM_REGISTER, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
		// REGISTER INPUT BEAN OF THIS BLOGIC
		List<InputBean> inputbeansRegisted = registerInputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic, acountId, currentDate, true, true);

		// register output bean of this blogic
		List<OutputBean> outputBeansRegisted = registerOutputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic.getBusinessLogicId(), acountId, currentDate, false);

		// REGISTER SEQUENCE LOGIC
		generateSequenceLogicRedirectConfirmRegister(sObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, inputbeansRegisted, outputBeansRegisted);
		// REGISTER COMPONENT
		registerComponent();
	}
	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicRedirectConfirmRegister(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<InputBean> inputbeansRegisted, List<OutputBean> outputbeanRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		mNameParameter = DetailServiceImpHandler.setLevelOfInputBeanOutputBeanObjectDefinition(inputbeansRegisted, outputbeanRegisted, null);
		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L;;
		SequenceConnector connector;
		Integer tempId = 0;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempId++;

		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			yCoordinates = yCoordinates + 70;
			SequenceLogic assign = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
			lstSequenceLogic.add(assign);
			// set assign component
			AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), tempId.longValue());
			lstAssignComponents.add(assignComponent);
			// set assign details
			contructAssignDetailFromInputBeanToOutputBeanForRedirectConfirm(table, inputbeansRegisted, outputbeanRegisted, assignIdTemp, screenDesignDefault.getCommonModel());
			assignIdTemp++;
			tempId++;
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic navigator = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0060) + SPACE, DbDomainConst.ComponentType.NAVIGATOR, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(navigator);
		// do set data for navigator component
		BusinessDesign displayConfirmRegisterBlogic = screenDesignDefault.getMapBusinessLogicId().get(BusinessDesignConst.DISPLAY_CONFIRM_REGISTER);
		NavigatorComponent navigatorComponent = GenerateScreenContruct.populateNavigatorComponent(screenDesignDefault, tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0060), displayConfirmRegisterBlogic.getBusinessLogicId(), null, displayConfirmRegisterBlogic.getBusinessLogicCode(), BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_BLOGIC);
		lstNavigatorComponents.add(navigatorComponent);
		// navigator details
		contructNavigatorDetail(screenDesignDefault, tempId.longValue(), outputbeanRegisted, displayConfirmRegisterBlogic.getBusinessLogicId());
		tempId++;

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);
		
		updateBLogicNavigator(REGISTER_NAVIGATE, businessLogicId);
		

		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);

		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for navigator component
		for (NavigatorComponent navi : lstNavigatorComponents) {
			navi.setSequenceLogicId(mapKeySequence.get(navi.getSequenceLogicId().toString()));
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void redirectConfirmModifyBlogic(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		// REGISTER BUSINESS LOGIC : UNDO REGISTER
		BusinessDesign businessLogic = registerBusinesslogicDefault(sObj, screenDesignDefault, BusinessDesignConst.REDIRECT_CONFIRM_MODIFY, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0032, DbDomainConst.BlogicReturnType.SCREEN, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_REDIRECT_CONFIRM_MODIFY, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
		// REGISTER INPUT BEAN OF THIS BLOGIC
		List<InputBean> inputbeansRegisted = registerInputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic, acountId, currentDate, true, true);

		// register output bean of this blogic
		List<OutputBean> outputBeansRegisted = registerOutputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic.getBusinessLogicId(), acountId, currentDate, false);

		// REGISTER SEQUENCE LOGIC
		generateSequenceLogicRedirectConfirmModify(sObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, inputbeansRegisted, outputBeansRegisted);
		// REGISTER COMPONENT
		registerComponent();
	}
	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicRedirectConfirmModify(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<InputBean> inputbeansRegisted, List<OutputBean> outputbeansRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		mNameParameter = DetailServiceImpHandler.setLevelOfInputBeanOutputBeanObjectDefinition(inputbeansRegisted, outputbeansRegisted, null);
		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L;;
		SequenceConnector connector;
		Integer tempId = 0;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempId++;

		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			yCoordinates = yCoordinates + 70;
			SequenceLogic assign = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
			lstSequenceLogic.add(assign);
			// set assign component
			AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), tempId.longValue());
			lstAssignComponents.add(assignComponent);
			// set assign details
			contructAssignDetailFromInputBeanToOutputBeanForRedirectConfirm(table, inputbeansRegisted, outputbeansRegisted, assignIdTemp, screenDesignDefault.getCommonModel());
			assignIdTemp++;
			tempId++;
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic navigator = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0061) + SPACE, DbDomainConst.ComponentType.NAVIGATOR, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(navigator);
		// do set data for navigator component
		BusinessDesign displayConfirmModifyBlogic = screenDesignDefault.getMapBusinessLogicId().get(BusinessDesignConst.DISPLAY_CONFIRM_MODIFY);
		NavigatorComponent navigatorComponent = GenerateScreenContruct.populateNavigatorComponent(screenDesignDefault, tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0061), displayConfirmModifyBlogic.getBusinessLogicId(), null, displayConfirmModifyBlogic.getBusinessLogicCode(), BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_BLOGIC);
		lstNavigatorComponents.add(navigatorComponent);
		// navigator details
		contructNavigatorDetail(screenDesignDefault, tempId.longValue(), outputbeansRegisted, displayConfirmModifyBlogic.getBusinessLogicId());
		tempId++;

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);

		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);
		
		updateBLogicNavigator(MODIFY_NAVIGATE, businessLogicId);

		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for navigator component
		for (NavigatorComponent navi : lstNavigatorComponents) {
			navi.setSequenceLogicId(mapKeySequence.get(navi.getSequenceLogicId().toString()));
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void undoModifyBlogic(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		// REGISTER BUSINESS LOGIC : UNDO MODIFY
		BusinessDesign businessLogic = registerBusinesslogicDefault(sObj, screenDesignDefault, BusinessDesignConst.UNDO_MODIFY, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0030, DbDomainConst.BlogicReturnType.SCREEN, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_CONFIRM_MODIFY, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
		// REGISTER INPUT BEAN OF THIS BLOGIC
		List<InputBean> inputbeansRegisted = registerInputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic, acountId, currentDate, true, false);
		// register output bean of this blogic
		List<OutputBean> outputBeansRegisted = registerOutputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic.getBusinessLogicId(), acountId, currentDate, true);
		// REGISTER SEQUENCE LOGIC
		generateSequenceLogicUndoModify(sObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, inputbeansRegisted, outputBeansRegisted);
		// REGISTER COMPONENT
		registerComponent();
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicUndoModify(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<InputBean> inputbeansRegisted, List<OutputBean> outputBeansRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L;
		SequenceConnector connector;
		Integer tempId = 0;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempId++;
		// Set to HaskMap
		mNameParameter = DetailServiceImpHandler.setLevelOfInputBeanOutputBeanObjectDefinition(inputbeansRegisted, outputBeansRegisted, null);

		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			yCoordinates = yCoordinates + 70;
			SequenceLogic assign = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
			lstSequenceLogic.add(assign);
			// set assign component
			AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), tempId.longValue());
			lstAssignComponents.add(assignComponent);
			// set assign details
			contructAssignDetailFromInputBeanToOutputBean(table, inputbeansRegisted, outputBeansRegisted, assignIdTemp, true, screenDesignDefault.getCommonModel());
			assignIdTemp++;
			tempId++;
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic navigator = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0058), DbDomainConst.ComponentType.NAVIGATOR, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(navigator);
		// do set data for navigator component
		BusinessDesign displayModifyBlogic = screenDesignDefault.getMapBusinessLogicId().get(BusinessDesignConst.DISPLAY_MODIFY);
		ScreenDesign screenModify = screenDesignDefault.getMapScreenDesign().get(ScreenDesignConst.MODIFY_SCREEN);
		NavigatorComponent navigatorComponent = GenerateScreenContruct.populateNavigatorComponent(screenDesignDefault, tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0058), screenModify.getScreenId(), null, screenModify.getMessageDesign().getMessageCode(), BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_SCREEN);
		lstNavigatorComponents.add(navigatorComponent);
		// navigator details
		contructNavigatorDetail(screenDesignDefault, tempId.longValue(), outputBeansRegisted, displayModifyBlogic.getBusinessLogicId());
		tempId++;

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);

		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);
		
		updateBLogicNavigator(BACK_MODIFY_COMFIRM_NAVIGATE, businessLogicId);

		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for navigator component
		for (NavigatorComponent navi : lstNavigatorComponents) {
			navi.setSequenceLogicId(mapKeySequence.get(navi.getSequenceLogicId().toString()));
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 */
	private void processDeleteBlogic(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		// REGISTER BUSINESS LOGIC PROCESS DELETE
		BusinessDesign businessLogic = registerBusinesslogicDefault(sObj, screenDesignDefault, BusinessDesignConst.PROCESS_DELETE, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0024, DbDomainConst.BlogicReturnType.SCREEN, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_DELETE, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
		// Special case : set business logic id for screen action delete
		ScreenAction screenActionDelete = screenDesignDefault.getScreenActionDelete();
		if (screenActionDelete != null) {
			screenActionDelete.setToScreenId(null);
			screenActionDelete.setNavigateToBlogicId(businessLogic.getBusinessLogicId());
			screenActionRepository.insertScreenAction(screenActionDelete);
			ScreenItem screenItemButtonDelete = screenDesignDefault.getScreenItemButtonDelete();
			screenItemButtonDelete.setScreenActionId(screenActionDelete.getScreenActionId());
			if (FunctionCommon.isNotEmpty(screenDesignDefault.getLstScreenTransitions())) {
				ScreenTransition transViewToDelete = null;
				for (ScreenTransition trans : screenDesignDefault.getLstScreenTransitions()) {
					if (trans.getFlgViewDeleteTrans() != null && trans.getFlgViewDeleteTrans()) {
						transViewToDelete = trans;
						break;
					}
				}
				if (transViewToDelete != null && transViewToDelete.getScreenTransitionId() != null) {
					screenItemButtonDelete.setScreenTransitionId(Long.parseLong(transViewToDelete.getScreenTransitionId()));
				}
			}
			screenItemRepository.insertScreenItem(screenItemButtonDelete);
		}
		// REGISTER INPUT BEAN OF THIS BLOGIC
		List<ScreenItem> lstInputItems = findInputForDelete(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem());
		List<InputBean> inputbeansRegisted = registerInputBean(sObj, screenDesignDefault, lstInputItems, businessLogic, acountId, currentDate, true, false);
		// GENERATE SEQUENCE LOGIC
		generateSequenceLogicProcessDelete(sObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, inputbeansRegisted);
		// GENERATE COMPONENT
		registerComponent();
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicProcessDelete(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<InputBean> inputbeansRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		stillHaveConstrainFlag = true;
		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		SqlDesign sqlDesign;
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, checkDetailIdTemp = 0L;
		SequenceConnector connector;
		Integer tempId = 0;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempId++;

		for(int i = screenDesignDefault.getModuleTableMappings().length - 1; i >= 0; i--) {
			ModuleTableMapping table = screenDesignDefault.getModuleTableMappings()[i];
			List<BusinessDetailContent> businessDetailContentsTemp = contructBusinessDetailContent(sObj, table, inputbeansRegisted, checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE, null);
			lstDetailsContents.addAll(businessDetailContentsTemp);
			if (!CollectionUtils.sizeIsEmpty(businessDetailContentsTemp)) {
				yCoordinates = yCoordinates + 70;
				SequenceLogic businessCheck = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0052, table.getTblDesignName()), DbDomainConst.ComponentType.BUSINESS_CHECK, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
				lstSequenceLogic.add(businessCheck);
				// set business component
				BusinessCheckComponent bc = GenerateScreenContruct.contructBusinessCheckComponent(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0052, table.getTblDesignName()), tempId.longValue());
				lstBusinessCheckComponents.add(bc);

				BusinessCheckDetail checkDetail = GenerateScreenContruct.contructBusinessCheckDetail(checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE, CommonMessageConst.ERR_SYS_0037, tempId.longValue(), 0);
				lstCheckDetails.add(checkDetail);

				MessageParameter messageParameterOfCheck = GenerateScreenContruct.contructMessageParametter(null, table, BusinessDesignConst.MessageParameter.TARGET_TYPE_BUSINESSCHECK, checkDetailIdTemp, businessLogicId);
				lstMessageParamettersOfBusinessCheckDetail.add(messageParameterOfCheck);
				checkDetailIdTemp++;
			}
			tempId++;

			yCoordinates = yCoordinates + 70;
			SequenceLogic executionDelete = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0062, table.getTblDesignName()), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
			lstSequenceLogic.add(executionDelete);
			// execution component
			sqlDesign = autoGenerateSqlbuilder(sObj, screenDesignDefault, MessageUtils.getMessage(MESSAGE_CODE_DELETE) + SPACE + table.getTblDesignName(), DELETE + SPACE + table.getTblDesignCode(), SqlPattern.DELETE, table, inputbeansRegisted, null, null, true, false, false, false, false, false, false);
			ExecutionComponent executionComponent = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0062, table.getTblDesignName()), sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), true);
			lstExecutionComponent.add(executionComponent);
			contructExecutionInputValue(screenDesignDefault, table, tempId.longValue(), inputbeansRegisted, null, sqlDesign.getAllSqlDesignInputs(), false);
			tempId++;
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic feedback = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0063), DbDomainConst.ComponentType.FEEDBACK, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(feedback);
		FeedbackComponent feedbackComponent = GenerateScreenContruct.contructFeedbackComponent(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0063), CommonMessageConst.INF_SYS_0004, tempId.longValue());
		lstFeedbacks.add(feedbackComponent);
		MessageParameter messageParameter = GenerateScreenContruct.contructMessageParametter(screenDesignDefault, null, BusinessDesignConst.MessageParameter.TARGET_TYPE_FEEDBACK, tempId.longValue(), businessLogicId);
		lstMessageParamettersOfFeedback.add(messageParameter);
		tempId++;

		yCoordinates = yCoordinates + 70;
		SequenceLogic navigator = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0064), DbDomainConst.ComponentType.NAVIGATOR, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(navigator);
		// do set data for navigator component
		NavigatorComponent navigatorComponent = GenerateScreenContruct.populateNavigatorComponent(screenDesignDefault, tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0064), null, true, null, null);
		lstNavigatorComponents.add(navigatorComponent);
		// navigator details
		// contructNavigatorDetail(screenDesignDefault, tempId.longValue(), inputbeansRegisted, null, displayViewBlogic.getBusinessLogicId());
		tempId++;

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);

		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);
		
		updateBLogicNavigator(DELETE_NAVIGATE, businessLogicId);
		

		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for navigator component
		for (NavigatorComponent navi : lstNavigatorComponents) {
			navi.setSequenceLogicId(mapKeySequence.get(navi.getSequenceLogicId().toString()));
		}
		// set sequence logic for feedback component
		for (FeedbackComponent fb : lstFeedbacks) {
			fb.setSequenceLogicId(mapKeySequence.get(fb.getSequenceLogicId().toString()));
		}
		// set sequence logic for feedback component
		for (BusinessCheckComponent bcomp : lstBusinessCheckComponents) {
			bcomp.setSequenceLogicId(mapKeySequence.get(bcomp.getSequenceLogicId().toString()));
		}
		// set sequence logic for execution component
		for (ExecutionComponent exec : lstExecutionComponent) {
			exec.setSequenceLogicId(mapKeySequence.get(exec.getSequenceLogicId().toString()));
		}
	}

	/**
	 * @param screenDesignDefault
	 * @param businessLogicId
	 */
	private void updateBLogicNavigator(String strMap, Long businessLogicId) {
		if(mapNavi.get(strMap) != null){
			ScreenAction screenAction = mapNavi.get(strMap);
			screenAction.setNavigateToBlogicId(businessLogicId);
			screenActionRepository.updateBLoicNavigator(screenAction);
		}
	}
	
	/**
	 * @param screenDesignDefault
	 * @param businessLogicId
	 */
	private void updateScreenActionParam(String strMap, List<InputBean> inputBeans) {
		List<ScreenActionParam> lstScreenActionParams = mScreenActionParam.get(strMap);
		if (FunctionCommon.isNotEmpty(lstScreenActionParams)) {
			for (ScreenActionParam screenActionParam : lstScreenActionParams) {
				boolean flgUpdate = false;
				for (InputBean in : inputBeans) {
					if (screenActionParam.getDomainTblMappingId() != null && screenActionParam.getDomainTblMappingItemId() != null 
							&& screenActionParam.getDomainTblMappingId().equals(in.getTblDesignId()) && screenActionParam.getDomainTblMappingItemId().equals(in.getColumnId())) {
						screenActionParam.setActionParamCode(in.getInputBeanId());
						flgUpdate = true;
						break;
					}
				}
				if (flgUpdate) {
					screenActionParamRepository.updateScreenActionCode(screenActionParam);
				}
			}
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void displayViewBlogic(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		// REGISTER BUSINESS LOGIC DISPLAY VIEW
		BusinessDesign businessLogic = registerBusinesslogicDefault(sObj, screenDesignDefault, BusinessDesignConst.DISPLAY_VIEW, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0023, DbDomainConst.BlogicReturnType.INITIAL, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_VIEW, BusinessDesignConst.REQUEST_METHOD_INITIAL);
		// REGISTER INPUT BEAN OF THIS BLOGIC
		List<ScreenItem> lstInputItems = findKeyOfFirstTable(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem());
		List<InputBean> inputbeansRegisted = registerInputBean(sObj, screenDesignDefault, lstInputItems, businessLogic, acountId, currentDate, false, false);
		
		// TrungDV 8/4/2016: update screen action param to mapping with input bean id 
		updateScreenActionParam(SCREEN_ACTION_PARAM_SEARCH_TO_VIEW, inputbeansRegisted);
					
		// REGISTER OUTPUT BEAN OF THIS BLOGIC
		List<OutputBean> outputBeansRegisted = registerOutputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic.getBusinessLogicId(), acountId, currentDate, false);
		// REGISTER OBJECT DEFINITION
		// List<ObjectDefinition> objDefsRegisted = registerObjectDefiniton(sObj, screenDesignDefault, businessLogicId, acountId, currentDate, BusinessDesignConst.RETURN_TYPE_INITIALSCREEN);
		// GENERATE SEQUENCE LOGIC
		generateSequenceLogicDisplayView(sObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, null, outputBeansRegisted, inputbeansRegisted);
		// GENERATE COMPONENT
		registerComponent();

	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicDisplayView(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<ObjectDefinition> objDefsRegisted, List<OutputBean> outputBeansRegisted, List<InputBean> inputbeansRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		SqlDesign sqlDesign;
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L, checkDetailIdTemp = 0L;
		SequenceConnector connector;
		Integer tempId = 0;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempId++;

		int doOne = 0;
		stillHaveConstrainFlag = true;
		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			if (doOne == 0) {
				List<BusinessDetailContent> businessDetailContentsTemp = contructBusinessDetailContent(sObj, table, inputbeansRegisted, checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE, null);
				lstDetailsContents.addAll(businessDetailContentsTemp);
				if (!CollectionUtils.sizeIsEmpty(businessDetailContentsTemp)) {
					yCoordinates = yCoordinates + 70;
					SequenceLogic businessCheck = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0052, table.getTblDesignName()), DbDomainConst.ComponentType.BUSINESS_CHECK, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
					lstSequenceLogic.add(businessCheck);
					// set business component
					BusinessCheckComponent bc = GenerateScreenContruct.contructBusinessCheckComponent(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0052, table.getTblDesignName()), tempId.longValue());
					lstBusinessCheckComponents.add(bc);

					BusinessCheckDetail checkDetail = GenerateScreenContruct.contructBusinessCheckDetail(checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE, CommonMessageConst.ERR_SYS_0037, tempId.longValue(), 0);
					lstCheckDetails.add(checkDetail);

					MessageParameter messageParameter = GenerateScreenContruct.contructMessageParametter(null, table, BusinessDesignConst.MessageParameter.TARGET_TYPE_BUSINESSCHECK, checkDetailIdTemp, businessLogicId);
					lstMessageParamettersOfBusinessCheckDetail.add(messageParameter);
					checkDetailIdTemp++;
				}
				tempId++;
				doOne++;
			}

			yCoordinates = yCoordinates + 70;
			SequenceLogic execution = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0038, table.getTblDesignName()), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
			lstSequenceLogic.add(execution);
			// execution component
			sqlDesign = autoGenerateSqlbuilderUsingOutputBean(sObj, screenDesignDefault, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0038, table.getTblDesignName()), GET + SPACE + table.getTblDesignCode() + SPACE + TO_VIEW, SqlPattern.SELECT, table, inputbeansRegisted, outputBeansRegisted, true, true, false, false, false, false, false);
			ExecutionComponent executionComponent = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0038, table.getTblDesignName()), sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), false);
			lstExecutionComponent.add(executionComponent);
			contructExecutionInputValue(screenDesignDefault, table, tempId.longValue(), inputbeansRegisted, null, sqlDesign.getAllSqlDesignInputs(), false);
			contructExecutionOutputValue(tempId.longValue(), null, sqlDesign.getAllSqlDesignOutputs(), outputBeansRegisted);
			tempId++;

			//			yCoordinates = yCoordinates + 70;
			//			SequenceLogic assign = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null);
			//			lstSequenceLogic.add(assign);
			//			// set assign component
			//			AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), tempId.longValue());
			//			lstAssignComponents.add(assignComponent);
			//			// set assign details
			//			contructAssignDetailFromObjectDefinitonToOutputBean(table, objDefsRegisted, outputBeansRegisted, assignIdTemp, false);
			//			assignIdTemp++;
			//			tempId++;
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);

		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);

		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for business check component
		for (BusinessCheckComponent bc : lstBusinessCheckComponents) {
			bc.setSequenceLogicId(mapKeySequence.get(bc.getSequenceLogicId().toString()));
		}
		// set sequence logic for execution component
		for (ExecutionComponent exec : lstExecutionComponent) {
			exec.setSequenceLogicId(mapKeySequence.get(exec.getSequenceLogicId().toString()));
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void displayCompleteRegisterBlogic(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		// REGISTER BUSINESS LOGIC DISPLAY COMPLETE REGISTER
		BusinessDesign businessLogic = registerBusinesslogicDefault(sObj, screenDesignDefault, BusinessDesignConst.DISPLAY_COMPLETE_REGISTER, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0025, DbDomainConst.BlogicReturnType.INITIAL, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_COMPLETE_REGISTER, BusinessDesignConst.REQUEST_METHOD_INITIAL);
		// REGISTER INPUT BEAN OF THIS BLOGIC
		List<InputBean> inputbeansRegisted = registerInputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic, acountId, currentDate, true, true);
		// REGISTER OUTPUT BEAN OF THIS BLOGIC
		List<OutputBean> outputbeansRegisted = registerOutputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic.getBusinessLogicId(), acountId, currentDate, false);
		// REGISTER SEQUENCE LOGIC
		generateSequenceLogicCompleteRegister(sObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, inputbeansRegisted, outputbeansRegisted);
		// REGISTER COMPONENTS
		registerComponent();
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicCompleteRegister(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<InputBean> inputbeansRegisted, List<OutputBean> outputbeansRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L;
		SequenceConnector connector;
		Integer tempSequenceLogicId = 0;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempSequenceLogicId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempSequenceLogicId++;

		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			yCoordinates = yCoordinates + 70;
			SequenceLogic assign = GenerateScreenContruct.populateSequenceLogicNew(tempSequenceLogicId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0054, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
			lstSequenceLogic.add(assign);
			// set assign component
			AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0054, table.getTblDesignName()), tempSequenceLogicId.longValue());
			lstAssignComponents.add(assignComponent);
			// set assign details
			contructAssignDetailFromInputBeanToOutputBean(table, inputbeansRegisted, outputbeansRegisted, assignIdTemp, false, screenDesignDefault.getCommonModel());
			assignIdTemp++;

			tempSequenceLogicId++;
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempSequenceLogicId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);

		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		// register sequence logic
		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);

		// register connector
		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for feedback component
		for (FeedbackComponent fb : lstFeedbacks) {
			fb.setSequenceLogicId(mapKeySequence.get(fb.getSequenceLogicId().toString()));
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void displayCompleteModifyBlogic(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		// REGISTER BUSINESS LOGIC DISPLAY COMPLETE MODIFY
		BusinessDesign businessLogic = registerBusinesslogicDefault(sObj, screenDesignDefault, BusinessDesignConst.DISPLAY_COMPLETE_MODIFY, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0026, DbDomainConst.BlogicReturnType.INITIAL, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_COMPLETE_MODIFY, BusinessDesignConst.REQUEST_METHOD_INITIAL);
		// REGISTER INPUT BEAN OF THIS BLOGIC
		List<InputBean> inputbeansRegisted = registerInputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic, acountId, currentDate, true, false);
		// REGISTER OUTPUT BEAN OF THIS BLOGIC
		List<OutputBean> outputbeansRegisted = registerOutputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic.getBusinessLogicId(), acountId, currentDate, false);
		// REGISTER SEQUENCE LOGIC
		generateSequenceLogicCompleteModify(sObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, inputbeansRegisted, outputbeansRegisted);
		// REGISTER COMPONENTS
		registerComponent();
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicCompleteModify(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<InputBean> inputbeansRegisted, List<OutputBean> outputbeansRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L;
		SequenceConnector connector;
		Integer tempSequenceLogicId = 0;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempSequenceLogicId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempSequenceLogicId++;

		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			yCoordinates = yCoordinates + 70;
			SequenceLogic assign = GenerateScreenContruct.populateSequenceLogicNew(tempSequenceLogicId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0054, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
			lstSequenceLogic.add(assign);
			// set assign component
			AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0054, table.getTblDesignName()), tempSequenceLogicId.longValue());
			lstAssignComponents.add(assignComponent);
			// set assign details
			contructAssignDetailFromInputBeanToOutputBean(table, inputbeansRegisted, outputbeansRegisted, assignIdTemp, false, screenDesignDefault.getCommonModel());
			assignIdTemp++;
			tempSequenceLogicId++;
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempSequenceLogicId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);

		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		// register sequence logic
		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);

		// register connector
		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for feedback component
		for (FeedbackComponent fb : lstFeedbacks) {
			fb.setSequenceLogicId(mapKeySequence.get(fb.getSequenceLogicId().toString()));
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void displayConfirmModifyBlogic(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		// REGISTER BUSINESS LOGIC DISPLAY CONFIRM MODIFY
		BusinessDesign businessLogic = registerBusinesslogicDefault(sObj, screenDesignDefault, BusinessDesignConst.DISPLAY_CONFIRM_MODIFY, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0028, DbDomainConst.BlogicReturnType.INITIAL, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_CONFIRM_MODIFY, BusinessDesignConst.REQUEST_METHOD_INITIAL);
		// REGISTER INPUT BEAN OF THIS BLOGIC
		List<InputBean> inputbeansRegisted = registerInputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic, acountId, currentDate, true, false);
		// REGISTER OUTPUT BEAN OF THIS BLOGIC
		List<OutputBean> outputbeansRegisted = registerOutputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic.getBusinessLogicId(), acountId, currentDate, false);
		// REGISTER SEQUENCE LOGIC
		generateSequenceLogicConfirmModify(sObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, inputbeansRegisted, outputbeansRegisted);
		// REGISTER COMPONENTS
		registerComponent();
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 * @param inputbeansRegisted
	 * @param outputbeansRegisted
	 */
	private void generateSequenceLogicConfirmModify(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<InputBean> inputbeansRegisted, List<OutputBean> outputbeansRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L;
		SequenceConnector connector;
		Integer tempSequenceLogicId = 0;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempSequenceLogicId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempSequenceLogicId++;

		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			yCoordinates = yCoordinates + 70;
			SequenceLogic assign = GenerateScreenContruct.populateSequenceLogicNew(tempSequenceLogicId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
			lstSequenceLogic.add(assign);
			// set assign component
			AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), tempSequenceLogicId.longValue());
			lstAssignComponents.add(assignComponent);
			// set assign details
			contructAssignDetailFromInputBeanToOutputBean(table, inputbeansRegisted, outputbeansRegisted, assignIdTemp, false, screenDesignDefault.getCommonModel());
			assignIdTemp++;
			tempSequenceLogicId++;
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempSequenceLogicId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);

		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		// register sequence logic
		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);

		// register connector
		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void displayModifyBlogic(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		// REGISTER BUSINESS LOGIC DISPLAY MODIFY
		BusinessDesign businessLogic = registerBusinesslogicDefault(sObj, screenDesignDefault, BusinessDesignConst.DISPLAY_MODIFY, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0018, DbDomainConst.BlogicReturnType.INITIAL, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_MODIFY, BusinessDesignConst.REQUEST_METHOD_INITIAL);
		// REGISTER INPUT BEAN OF THIS BLOGIC
		List<ScreenItem> lstInputItems = findKeyOfFirstTable(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem());
		List<InputBean> inputbeansRegisted = registerInputBean(sObj, screenDesignDefault, lstInputItems, businessLogic, acountId, currentDate, false, false);
		
		// TrungDV 8/4/2016: update screen action param to mapping with input bean id 
		updateScreenActionParam(SCREEN_ACTION_PARAM_SEARCH_TO_MODIFY, inputbeansRegisted);
		updateScreenActionParam(SCREEN_ACTION_PARAM_VIEW_TO_MODIFY, inputbeansRegisted);
				
		// REGISTER OUTPUT BEAN OF THIS BLOGIC
		List<OutputBean> outputbeansRegisted = registerOutputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic.getBusinessLogicId(), acountId, currentDate, false);
		// REGISTER OBJECT DEFINITION OF THIS BLOGIC
		// List<ObjectDefinition> objDefsRegisted = registerObjectDefiniton(sObj, screenDesignDefault, businessLogicId, acountId, currentDate, BusinessDesignConst.RETURN_TYPE_INITIALSCREEN);
		// GENERATE SEQUENCE LOGIC
		generateSequenceLogicDisplayModify(sObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, null, outputbeansRegisted, inputbeansRegisted);
		// REGISTER COMPONENTS
		registerComponent();
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicDisplayModify(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<ObjectDefinition> objDefsRegisted, List<OutputBean> outputbeansRegisted, List<InputBean> inputbeansRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		SqlDesign sqlDesign = null;
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L, checkDetailIdTemp = 0L;
		SequenceConnector connector;
		Integer tempId = 0, dupCode = 1;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempId++;

		int insertOneTime = 0;
		stillHaveConstrainFlag = true;
		Set<String> hashSetSqlCode = new HashSet<String>();
		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			if (insertOneTime == 0) {
				List<BusinessDetailContent> businessDetailContentsTemp = contructBusinessDetailContent(sObj, table, inputbeansRegisted, checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE, null);
				lstDetailsContents.addAll(businessDetailContentsTemp);
				if (!CollectionUtils.sizeIsEmpty(businessDetailContentsTemp)) {
					yCoordinates = yCoordinates + 70;
					SequenceLogic businessCheck = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0052, table.getTblDesignName()), DbDomainConst.ComponentType.BUSINESS_CHECK, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
					lstSequenceLogic.add(businessCheck);
					// set business component
					BusinessCheckComponent bc = GenerateScreenContruct.contructBusinessCheckComponent(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0052, table.getTblDesignName()), tempId.longValue());
					lstBusinessCheckComponents.add(bc);

					BusinessCheckDetail checkDetail = GenerateScreenContruct.contructBusinessCheckDetail(checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE, CommonMessageConst.ERR_SYS_0037, tempId.longValue(), 0);
					lstCheckDetails.add(checkDetail);

					MessageParameter messageParameter = GenerateScreenContruct.contructMessageParametter(null, table, BusinessDesignConst.MessageParameter.TARGET_TYPE_BUSINESSCHECK, checkDetailIdTemp, businessLogicId);
					lstMessageParamettersOfBusinessCheckDetail.add(messageParameter);
					checkDetailIdTemp++;
				}
				tempId++;
				insertOneTime++;
			}

			yCoordinates = yCoordinates + 70;
			SequenceLogic executionGet = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0053, table.getTblDesignName()), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
			lstSequenceLogic.add(executionGet);
			// execution component
			sqlDesign = autoGenerateSqlbuilderUsingOutputBean(sObj, screenDesignDefault, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0039, table.getTblDesignName()), GET + SPACE + table.getTblDesignCode() + SPACE + TO_MODIFY, SqlPattern.SELECT, table, inputbeansRegisted, outputbeansRegisted, true, true, false, false, false, false, false);
			ExecutionComponent executionComponent = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0039, table.getTblDesignName()), sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), false);
			lstExecutionComponent.add(executionComponent);
			contructExecutionInputValue(screenDesignDefault, table, tempId.longValue(), inputbeansRegisted, null, sqlDesign.getAllSqlDesignInputs(), false);
			contructExecutionOutputValue(tempId.longValue(), null, sqlDesign.getAllSqlDesignOutputs(), outputbeansRegisted);
			tempId++;

			//			yCoordinates = yCoordinates + 70;
			//			SequenceLogic assign = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null);
			//			lstSequenceLogic.add(assign);
			//			// set assign component
			//			AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), tempId.longValue());
			//			lstAssignComponents.add(assignComponent);
			//			// set assign details
			//			lstAssignDetails = contructAssignDetailFromObjectDefinitonToOutputBean(table, objDefsRegisted, outputbeansRegisted, assignIdTemp, false);
			//			assignIdTemp++;
			//			tempId++;
			List<TableDesignDetails> listOfColumn = table.getListTableDesignDetail();
			if (FunctionCommon.isNotEmpty(listOfColumn)) {
				for (TableDesignDetails column : listOfColumn) {
					if (column.getDatasourceType() != null && DbDomainConst.DatasourceType.SQL_BUILDER.equals(column.getDatasourceType())) {
						yCoordinates = yCoordinates + 70;
						SequenceLogic execution = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0047, column.getName()), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
						lstSequenceLogic.add(execution);
						SqlDesign sqlDesignDatasource = screenDesignDefault.getMapSqlDesign().get(column.getDatasourceId());
						if (sqlDesignDatasource == null) {
							throw new BusinessException(ResultMessages.error().add(ScreenDesignConst.ERR_SCREENDESIGN_0433, column.getCode(), column.getTableDesignName()));
						}
						String rootSqlDesignCode = "";
						if(!hashSetSqlCode.contains(sqlDesignDatasource.getSqlDesignCode())) {
							hashSetSqlCode.add(sqlDesignDatasource.getSqlDesignCode());
							rootSqlDesignCode = sqlDesignDatasource.getSqlDesignCode() + "Output";
						} else {
							dupCode++;
							rootSqlDesignCode = sqlDesignDatasource.getSqlDesignCode() + dupCode + "Output";
						}
						SqlDesignOutput[] lstSqlOutput = sqlDesignOutputRepository.findAllBySqlDesignId(sqlDesignDatasource.getSqlDesignId());
						if (lstSqlOutput != null && lstSqlOutput.length > 0) {
							sqlDesignDatasource.setAllSqlDesignOutputs(Arrays.asList(lstSqlOutput));
						}
						ExecutionComponent executionComponentOfDatasource = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0047, column.getName()), column.getDatasourceId(), sqlDesignDatasource, tempId.longValue(), false);
						lstExecutionComponent.add(executionComponentOfDatasource);
						if(FunctionCommon.isEmpty(outputbeansRegisted)) {
							outputbeansRegisted = new ArrayList<OutputBean>();
						}
						List<OutputBean> outputbeans = registerOutputbeanOfDatasourceValue(sObj, screenDesignDefault, businessLogicId, column, outputbeansRegisted, rootSqlDesignCode);
						for (OutputBean ou : outputbeans) {
							outputbeansRegisted.add(ou);
						}
						
						if(FunctionCommon.isEmpty(objDefsRegisted)) {
							objDefsRegisted = new ArrayList<ObjectDefinition>();
						}
						List<ObjectDefinition> objDefs = registerObjectDefinitionOfDatasourceValue(businessLogicId, column.getDatasourceId(), objDefsRegisted, rootSqlDesignCode);
						for (ObjectDefinition ob : objDefs) {
							objDefsRegisted.add(ob);
						}
						mappingExecutionOutputValueWithObjectDefinition(tempId.longValue(), objDefs, sqlDesignDatasource.getAllSqlDesignOutputs());
						tempId++;

						yCoordinates = yCoordinates + 70;
						SequenceLogic assignDatasource = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0048, column.getName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
						lstSequenceLogic.add(assignDatasource);
						// set assign component
						AssignComponent assignComponentOfDatasource = GenerateScreenContruct.contructAssignComponent(tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0048, column.getName()), tempId.longValue());
						lstAssignComponents.add(assignComponentOfDatasource);

						// assign details
						contructAssignDetailFromObjectDefinitonToOutputBean(null, objDefs, outputbeans, tempId.longValue(), true, null, null);

						tempId++;
					}
				}
			}
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);

		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		// register sequence logic
		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);
		
		updateBLogicNavigator(MODIFY_VIEW, businessLogicId);
		updateBLogicNavigator(MODIFY_SEARCH, businessLogicId);

		// register connector
		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for business check component
		for (BusinessCheckComponent bc : lstBusinessCheckComponents) {
			bc.setSequenceLogicId(mapKeySequence.get(bc.getSequenceLogicId().toString()));
		}
		// set sequence logic for execution component
		for (ExecutionComponent exec : lstExecutionComponent) {
			exec.setSequenceLogicId(mapKeySequence.get(exec.getSequenceLogicId().toString()));
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void processModifyBlogic(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		// REGISTER BUSINESS LOGIC PROCESS MODIFY
		Integer patternType;
		String businessLogicCode;
		String businessLogicName;
		if (screenDesignDefault.getConfirmationType() != null && 2 == screenDesignDefault.getConfirmationType().intValue()) {
			patternType = BusinessDesignConst.SCREEN_PATTERN_CONFIRM_MODIFY;
			businessLogicCode = BusinessDesignConst.PROCESS_CONFIRM_MODIFY;
			businessLogicName = GenerateSourceCodeConst.SC_GENERATESOURCECODE_0020;
		} else {
			patternType = BusinessDesignConst.SCREEN_PATTERN_MODIFY;
			businessLogicCode = BusinessDesignConst.PROCESS_MODIFY;
			businessLogicName = GenerateSourceCodeConst.SC_GENERATESOURCECODE_0019;
		}
		BusinessDesign businessLogic = registerBusinesslogicDefault(sObj, screenDesignDefault, businessLogicCode, businessLogicName, DbDomainConst.BlogicReturnType.SCREEN, acountId, currentDate, patternType, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
		BusinessDesign businessDesign = screenDesignDefault.getMapBusinessLogicId().get(businessLogicCode);
		// REGISTER INPUT BEAN OF THIS BLOGIC
		List<InputBean> inputbeansRegisted = registerInputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic, acountId, currentDate, true, true);
		// REGISTER OBJECT DEFINITION
		List<ObjectDefinition> objDefsRegisted = registerObjectDefiniton(sObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, BusinessDesignConst.RETURN_TYPE_SCREEN);
		// REGISTER OUTPUT BEAN
		List<OutputBean> outputbeanRegisted = registerOutputBean(sObj, screenDesignDefault, screenDesignDefault.getLstScreenItem(), businessLogic.getBusinessLogicId(), acountId, currentDate, false);
		
		// REGISTER SEQUENCE LOGIC
		generateSequenceLogicProcessModify(sObj, screenDesignDefault, businessDesign, acountId, currentDate, inputbeansRegisted, objDefsRegisted, outputbeanRegisted);
		// REGISTER COMPONENTS
		registerComponent();
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void displayRegisterBlogic(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		// REGISTER BUSINESS_LOGIC : DISPLAY REGISTER
		BusinessDesign businessLogic = registerBusinesslogicDefault(sObj, screenDesignDefault, BusinessDesignConst.DISPLAY_REGISTER, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0015, DbDomainConst.BlogicReturnType.INITIAL, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_REGISTER, BusinessDesignConst.REQUEST_METHOD_INITIAL);
		// GEN SEQUENCE LOGIC
		generateSequenceLogicDisplayRegister(sObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, null, null);
		// GEN COMPONENT
		registerComponent();
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicDisplayRegister(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<OutputBean> outputbeansRegisted, List<ObjectDefinition> objDefsRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence;
		Integer tempId = 0, dupCode = 1;
		SequenceConnector connector;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempId++;

		Set<String> hashSetSqlCode = new HashSet<String>();
		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			if (FunctionCommon.isNotEmpty(table.getListTableDesignDetail())) {
				for (TableDesignDetails column : table.getListTableDesignDetail()) {
					if (column.getDatasourceType() != null && DbDomainConst.DatasourceType.SQL_BUILDER.equals(column.getDatasourceType())) {
						yCoordinates = yCoordinates + 70;
						SequenceLogic execution = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0047, column.getName()), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
						lstSequenceLogic.add(execution);
						SqlDesign sqlDesign = screenDesignDefault.getMapSqlDesign().get(column.getDatasourceId());
						if (sqlDesign == null) {
							throw new BusinessException(ResultMessages.error().add(ScreenDesignConst.ERR_SCREENDESIGN_0433, column.getCode(), column.getTableDesignName()));
						}
						String rootSqlDesignCode = "";
						if(!hashSetSqlCode.contains(sqlDesign.getSqlDesignCode())) {
							hashSetSqlCode.add(sqlDesign.getSqlDesignCode());
							rootSqlDesignCode = sqlDesign.getSqlDesignCode() + "Output";
						} else {
							dupCode++;
							rootSqlDesignCode = sqlDesign.getSqlDesignCode() + dupCode + "Output";
						}
						SqlDesignOutput[] lstSqlOutput = sqlDesignOutputRepository.findAllBySqlDesignId(sqlDesign.getSqlDesignId());
						if (lstSqlOutput != null && lstSqlOutput.length > 0) {
							sqlDesign.setAllSqlDesignOutputs(Arrays.asList(lstSqlOutput));
						}
						ExecutionComponent executionComponent = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0047, column.getName()), column.getDatasourceId(), sqlDesign, tempId.longValue(), false);
						lstExecutionComponent.add(executionComponent);
						if (outputbeansRegisted == null) {
							outputbeansRegisted = new ArrayList<OutputBean>();
						}
						List<OutputBean> outputbeans = registerOutputbeanOfDatasourceValue(sObj, screenDesignDefault, businessLogicId, column, outputbeansRegisted, rootSqlDesignCode);
						for (OutputBean ou : outputbeans) {
							outputbeansRegisted.add(ou);
						}
						if (objDefsRegisted == null) {
							objDefsRegisted = new ArrayList<ObjectDefinition>();
						}
						List<ObjectDefinition> objDefs = registerObjectDefinitionOfDatasourceValue(businessLogicId, column.getDatasourceId(), objDefsRegisted, rootSqlDesignCode);
						for (ObjectDefinition ob : objDefs) {
							objDefsRegisted.add(ob);
						}
						mappingExecutionOutputValueWithObjectDefinition(tempId.longValue(), objDefs, sqlDesign.getAllSqlDesignOutputs());

						tempId++;

						yCoordinates = yCoordinates + 70;
						SequenceLogic assign = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0048, column.getName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
						lstSequenceLogic.add(assign);
						// set assign component
						AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0048, column.getName()), tempId.longValue());
						lstAssignComponents.add(assignComponent);

						// assign details
						contructAssignDetailFromObjectDefinitonToOutputBean(null, objDefs, outputbeans, tempId.longValue(), true, null, null);
						tempId++;
					}
				}
			}
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);

		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);

		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for execution component
		for (ExecutionComponent exec : lstExecutionComponent) {
			exec.setSequenceLogicId(mapKeySequence.get(exec.getSequenceLogicId().toString()));
		}
	}

	/**
	 * @param screenDesignObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void processRegisterBlogic(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		List<ScreenItem> lstScreenItems = screenDesignDefault.getLstScreenItem();
		
		// register business logic
		Integer patternType;
		String businessLogicCode;
		String businessLogicName;
		if (screenDesignDefault.getConfirmationType() != null && 2 == screenDesignDefault.getConfirmationType().intValue()) {
			patternType = BusinessDesignConst.SCREEN_PATTERN_CONFIRM_REGISTER;
			businessLogicCode = BusinessDesignConst.PROCESS_CONFIRM_REGISTER;
			businessLogicName = GenerateSourceCodeConst.SC_GENERATESOURCECODE_0017;
		} else {
			patternType = BusinessDesignConst.SCREEN_PATTERN_REGISTER;
			businessLogicCode = BusinessDesignConst.PROCESS_REGISTER;
			businessLogicName = GenerateSourceCodeConst.SC_GENERATESOURCECODE_0016;
		}
		BusinessDesign businessLogic = registerBusinesslogicDefault(screenDesignObj, screenDesignDefault, businessLogicCode, businessLogicName, DbDomainConst.BlogicReturnType.SCREEN, acountId, currentDate, patternType, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
		// register input bean of this blogic
		List<InputBean> inputbeansRegisted = registerInputBean(screenDesignObj, screenDesignDefault, lstScreenItems, businessLogic, acountId, currentDate, true, true);
		// register object definition
		List<ObjectDefinition> objDefsRegisted = registerObjectDefiniton(screenDesignObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, BusinessDesignConst.RETURN_TYPE_SCREEN);
		// register output bean
		List<OutputBean> outputbeanRegisted = new ArrayList<OutputBean>();
		if (2 == screenDesignDefault.getCompletionType().intValue()) {
			outputbeanRegisted = registerOutputBean(screenDesignObj, screenDesignDefault, lstScreenItems, businessLogic.getBusinessLogicId(), acountId, currentDate, false);
		}
				
		// generate sequence logic
		BusinessDesign businessDesign = screenDesignDefault.getMapBusinessLogicId().get(businessLogicCode);
		generateSequenceLogicProcessRegister(screenDesignObj, screenDesignDefault, businessDesign, acountId, currentDate, inputbeansRegisted, objDefsRegisted, outputbeanRegisted);
		// register component
		registerComponent();
	}

	/**
	 * register component
	 */
	private void registerComponent() {
		Long startSequence;
		Map<Long, Long> mapId = new HashMap<Long, Long>();
		// navigator component
		if (lstNavigatorComponents != null && lstNavigatorComponents.size() > 0) {
			Long sequenceNavigatorComponent = navigationComponentRepository.getSequencesNavigationComponent(lstNavigatorComponents.size() - 1);
			startSequence = sequenceNavigatorComponent - (lstNavigatorComponents.size() - 1);
			for (NavigatorComponent navi : lstNavigatorComponents) {
				mapId.put(navi.getNavigatorComponentId(), startSequence);
				navi.setNavigatorComponentId(startSequence);
				startSequence++;
			}
			if (lstNavigatorDetails != null && lstNavigatorDetails.size() > 0) {
				int countInputBeans = lstNavigatorDetails.size();
				Long sequenceNavigatorDetail = navigationComponentRepository.getSequencesNavigationDetail(countInputBeans - 1);
				Long startNavigatorDetailSequence = sequenceNavigatorDetail - (countInputBeans - 1);

				for (NavigatorDetail naviDetail : lstNavigatorDetails) {
					naviDetail.setNavigatorComponentId(mapId.get(naviDetail.getNavigatorComponentId()));
					naviDetail.setNavigatorDetailId(startNavigatorDetailSequence);
					startNavigatorDetailSequence ++;
				}
				for (NavigatorComponent navi : lstNavigatorComponents) {
					List<NavigatorDetail> navigatorDetails = new ArrayList<NavigatorDetail>();
					for (NavigatorDetail naviDetail : lstNavigatorDetails) {
						if (naviDetail.getNavigatorComponentId().equals(navi.getNavigatorComponentId())) {
							navigatorDetails.add(naviDetail);
						}
					}
					navi.setParameterInputBeans(navigatorDetails);
				}
			}
			if (navigationComponentRepository.registerNavigationComponent(lstNavigatorComponents) <= 0) {
				throw new BusinessException(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0084));
			}
		}

		// assign component
		mapId.clear();
		if (lstAssignComponents != null && lstAssignComponents.size() > 0) {
			Long sequenceAssignComponent = assignComponentRepository.getSequencesAssignComponent(lstAssignComponents.size() - 1);
			startSequence = sequenceAssignComponent - (lstAssignComponents.size() - 1);
			for (AssignComponent assign : lstAssignComponents) {
				mapId.put(assign.getAssignComponentId(), startSequence);
				assign.setAssignComponentId(startSequence);
				startSequence++;
			}
			if (assignComponentRepository.registerAssignComponent(lstAssignComponents) <= 0) {
				throw new BusinessException("Fail");
			}
			
			if (lstFormulaDefinitionsForAssign != null && lstFormulaDefinitionsForAssign.size() > 0) {
				for (FormulaDefinition formulaDefinition : lstFormulaDefinitionsForAssign) {
					Long sequenceFormulaDefinition = formulaDefinitionRepository.getSequencesFormulaDefinition(lstFormulaDefinitionsForAssign.size() - 1);
					Long startSequenceFormulaDefinition = sequenceFormulaDefinition - (lstFormulaDefinitionsForAssign.size() - 1);
					
					if (lstAssignDetails != null && lstAssignDetails.size() > 0) {
						for (AssignDetail assignDetail : lstAssignDetails) {
							if(assignDetail.getFormulaDefinitionId() != null && assignDetail.getFormulaDefinitionId().equals(formulaDefinition.getFormulaDefinitionId())){
								assignDetail.setFormulaDefinitionId(startSequenceFormulaDefinition);
							}
						}
					}
					formulaDefinition.setFormulaDefinitionId(startSequenceFormulaDefinition);
					
					List<FormulaDetail> formulaDetails = formulaDefinition.getFormulaDefinitionDetails();
					if (formulaDetails != null && formulaDetails.size() > 0) {
						for (FormulaDetail formulaDetail : formulaDetails) {
							Long sequenceFormulaDetail = formulaDefinitionRepository.getSequencesFormulaDetail(formulaDetails.size() -1);
							Long startSequenceF = sequenceFormulaDetail - (formulaDetails.size() -1);
							formulaDetail.setFormulaDetailId(startSequenceF);
							formulaDetail.setFormulaDefinitionId(formulaDefinition.getFormulaDefinitionId());
							
							// Set ID FormulaMethodOutput
							List<FormulaMethodOutput> formulaMethodOutputs = formulaDetail.getFormulaMethodOutputs();
							if (formulaMethodOutputs != null && formulaMethodOutputs.size() > 0) {
								Long sequenceParameterOut = formulaDefinitionRepository.getSequencesFormulaMethodOutput(formulaMethodOutputs.size() - 1);
								Long startFormulaMethodOutputSequence = sequenceParameterOut - (formulaMethodOutputs.size() - 1);
								for (FormulaMethodOutput formulaMethodOutput : formulaMethodOutputs) {
									formulaMethodOutput.setFormulaMethodOutputId(startFormulaMethodOutputSequence);
									formulaMethodOutput.setFormulaDetailId(formulaDetail.getFormulaDetailId());
								}
								startFormulaMethodOutputSequence++;
							}
							
							// Set ID FormulaMethodInput
							List<FormulaMethodInput> formulaMethodInputs = formulaDetail.getFormulaMethodInputs();
							if (formulaMethodInputs != null && formulaMethodInputs.size() > 0) {
								Long sequenceParameterOut = formulaDefinitionRepository.getSequencesFormulaMethodInput(formulaMethodInputs.size() - 1);
								Long startFormulaMethodInputSequence = sequenceParameterOut - (formulaMethodInputs.size() - 1);
								for (FormulaMethodInput formulaMethodInput : formulaMethodInputs) {
									formulaMethodInput.setFormulaMethodInputId(startFormulaMethodInputSequence++);
									formulaMethodInput.setFormulaDetailId(formulaDetail.getFormulaDetailId());
									
									List<BDParameterIndex> bdParameterIndexs = formulaMethodInput.getLstParameterIndex();
									Long sequenceParameterBDIndex = bDParameterIndexRepository.getSequencesBDParameterIndex(bdParameterIndexs.size() - 1);
									Long startFormulaMethodIntputSequenceBDIndex = sequenceParameterBDIndex - (bdParameterIndexs.size() - 1);
									if (bdParameterIndexs != null && bdParameterIndexs.size() > 0) {
										for (BDParameterIndex bdParameterIndex : bdParameterIndexs) {
											bdParameterIndex.setTableId(formulaMethodInput.getFormulaMethodInputId());
											bdParameterIndex.setBdParameterIndexId(startFormulaMethodIntputSequenceBDIndex);
										}
									}
									
								}
//								startFormulaMethodInputSequence++;
							}
							List<FormulaDefinition> listF = new ArrayList<FormulaDefinition>();
							listF.add(formulaDefinition);
							formulaDefinitionRepository.registerFormulaDefinition(listF);
							formulaDefinitionRepository.registerFormulaDetailsForCheckbox(formulaDetails);
						}
					}
				}
			}
			
			// details
			if (lstAssignDetails != null && lstAssignDetails.size() > 0) {
				//DungNN - 20160130
				Long sequenceDetail = assignComponentRepository.getSequencesAssignDetail(lstAssignDetails.size() - 1);
				startSequence = sequenceDetail - (lstAssignDetails.size() - 1);

				for (AssignDetail assignDetail : lstAssignDetails) {
					assignDetail.setAssignComponentId(mapId.get(assignDetail.getAssignComponentId()));
					assignDetail.setAssignDetailId(startSequence++);
				}
				if (assignComponentRepository.registerAssignDetails(lstAssignDetails) <= 0) {
					throw new BusinessException("Fail");
				}
			}
		}

		// loop component
		mapId.clear();
		if (listLoopComponent != null && listLoopComponent.size() > 0) {
			Long sequenceComponent = loopComponentRepository.getSequencesLoopComponent(listLoopComponent.size() - 1);
			startSequence = sequenceComponent - (listLoopComponent.size() - 1);
			for (LoopComponent objComponent : listLoopComponent) {
				objComponent.setLoopComponentId(startSequence);
				startSequence++;
			}

			if (loopComponentRepository.registerLoopComponent(listLoopComponent) <= 0) {
				throw new BusinessException("Fail");
			}
		}
		
		// If component
		mapId.clear();
		if(FunctionCommon.isNotEmpty(lstIfComponents)){
			ifComponentRepository.registerIfComponent(lstIfComponents);
			List<IfConditionDetail> lstIfConditionDetails = new ArrayList<IfConditionDetail>();
			for (IfComponent ifComponent : lstIfComponents) {
				lstIfConditionDetails.addAll(ifComponent.getIfConditionDetails());
			}
			ifConditionDetailRepository.registerIfConditionDetail(lstIfConditionDetails);
			formulaDefinitionRepository.registerFormulaDefinition(lstFormulaDefinitions);
			for (FormulaDefinition formulaDefinition : lstFormulaDefinitions) {
				formulaDefinitionRepository.registerFormulaDetails(formulaDefinition.getFormulaDefinitionDetails());
			}
		}
		
		// Utility component
		if(FunctionCommon.isNotEmpty(lstUtilityComponents)){
			Long sequenceUtilityComponent = utilityCompRepository.getSequencesUtilityComponent(lstUtilityComponents.size() - 1);
			startSequence = sequenceUtilityComponent - (lstUtilityComponents.size() - 1);
			for (UtilityComponent utilityComponent : lstUtilityComponents) {
				utilityComponent.setUtilityComponentId(startSequence);
				startSequence++;
			}
			utilityCompRepository.registerUtilityComponent(lstUtilityComponents);
		}
		
		// feedback component
		mapId.clear();
		if (lstFeedbacks != null && lstFeedbacks.size() > 0) {
			Long sequenceFeedbackComponent = feedbackComponentRepository.getSequencesFeedbackComponent(lstFeedbacks.size() - 1);
			startSequence = sequenceFeedbackComponent - (lstFeedbacks.size() - 1);
			for (FeedbackComponent fb : lstFeedbacks) {
				mapId.put(fb.getFeedbackComponentId(), startSequence);
				fb.setFeedbackComponentId(startSequence);
				startSequence++;
			}
			if (feedbackComponentRepository.registerFeedbackComponent(lstFeedbacks) <= 0) {
				throw new BusinessException("Fail");
			}
			// message parameters of feedback
			if (lstMessageParamettersOfFeedback != null && lstMessageParamettersOfFeedback.size() > 0) {
				int size = lstMessageParamettersOfFeedback.size() - 1;
				Long sequenceParameter = messageParameterRepository.getSequencesMessageParameter(size);
				Long startMessageParameterSequence = sequenceParameter - (lstMessageParamettersOfFeedback.size() - 1);

				for (MessageParameter objContent : lstMessageParamettersOfFeedback) {
					objContent.setMessageParameterId(startMessageParameterSequence);
					startMessageParameterSequence++;
					objContent.setTargetId(mapId.get(objContent.getTargetId()));
					// index of pass parameter
					if (CollectionUtils.isNotEmpty(objContent.getLstParameterIndex())) {
						for (BDParameterIndex index : objContent.getLstParameterIndex()) {
							index.setTableId(objContent.getMessageParameterId());
						}
					}
				}

				if (messageParameterRepository.registerMessageParameter(lstMessageParamettersOfFeedback) <= 0) {
					throw new BusinessException("Fail");
				}
			}
		}

		// business check component
		mapId.clear();
		if (lstBusinessCheckComponents != null && lstBusinessCheckComponents.size() > 0) {
			Long seqBusinessCheck = businessCheckComponentRepository.getSequencesBusinessCheckComponent(lstBusinessCheckComponents.size() - 1);
			startSequence = seqBusinessCheck - (lstBusinessCheckComponents.size() - 1);
			for (BusinessCheckComponent bc : lstBusinessCheckComponents) {
				mapId.put(bc.getBusinessCheckComponentId(), startSequence);
				bc.setBusinessCheckComponentId(startSequence);
				startSequence++;
			}
			if (businessCheckComponentRepository.registerBusinessCheckComponent(lstBusinessCheckComponents) <= 0) {
				throw new BusinessException("Fail");
			}
			// business check details
			Map<Long, Long> mapCheckDetailId = new HashMap<Long, Long>();
			if (lstCheckDetails != null && lstCheckDetails.size() > 0) {
				Long seqCheckDetail = businessCheckComponentRepository.getSequencesBusinessCheckDetail(lstCheckDetails.size() - 1);
				startSequence = seqCheckDetail - (lstCheckDetails.size() - 1);
				for (BusinessCheckDetail checkDetail : lstCheckDetails) {
					checkDetail.setBusinessCheckComponentId(mapId.get(checkDetail.getBusinessCheckComponentId()));
					mapCheckDetailId.put(checkDetail.getBusinessCheckDetailId(), startSequence);
					checkDetail.setBusinessCheckDetailId(startSequence);
					startSequence++;
				}
				if (businessCheckComponentRepository.registerBusinessCheckDetails(lstCheckDetails) <= 0) {
					throw new BusinessException("Fail");
				}
			}
			// details contents
			if (lstDetailsContents != null && lstDetailsContents.size() > 0) {
				int size = lstDetailsContents.size() - 1;
				Long sequenceDetailContent = businessCheckComponentRepository.getSequencesBusinessDetailContent(size);
				Long startDetailContentSequence = sequenceDetailContent - size;
				for (BusinessDetailContent content : lstDetailsContents) {
					content.setBusinessCheckDetailId(mapCheckDetailId.get(content.getBusinessCheckDetailId()));
					content.setBusinessDetailContentId(startDetailContentSequence);
					startDetailContentSequence ++;
				}
				if (businessCheckComponentRepository.registerBusinessDetailContents(lstDetailsContents) <= 0) {
					throw new BusinessException("Fail");
				}
			}
			// message parameters of business check
			if (lstMessageParamettersOfBusinessCheckDetail != null && lstMessageParamettersOfBusinessCheckDetail.size() > 0) {
				int size = lstMessageParamettersOfBusinessCheckDetail.size() - 1;
				Long sequenceParameter = messageParameterRepository.getSequencesMessageParameter(size);
				Long startMessageParameterSequence = sequenceParameter - (size);

				for (MessageParameter mess : lstMessageParamettersOfBusinessCheckDetail) {
					mess.setMessageParameterId(startMessageParameterSequence);
					startMessageParameterSequence++;
					mess.setTargetId(mapCheckDetailId.get(mess.getTargetId()));
					// index of pass parameter
					if (CollectionUtils.isNotEmpty(mess.getLstParameterIndex())) {
						for (BDParameterIndex index : mess.getLstParameterIndex()) {
							index.setTableId(mess.getMessageParameterId());
						}
					}
				}
				if (messageParameterRepository.registerMessageParameter(lstMessageParamettersOfBusinessCheckDetail) <= 0) {
					throw new BusinessException("Fail");
				}
			}
		}
		// execution component
		mapId.clear();
		if (lstExecutionComponent != null && lstExecutionComponent.size() > 0) {
			Long seqExecutionComponent = executionComponentRepository.getSequencesExecutionComponent(lstExecutionComponent.size() - 1);
			startSequence = seqExecutionComponent - (lstExecutionComponent.size() - 1);
			for (ExecutionComponent exec : lstExecutionComponent) {
				mapId.put(exec.getExecutionComponentId(), startSequence);
				exec.setExecutionComponentId(startSequence++);
			}
			if (executionComponentRepository.registerExecutionComponent(lstExecutionComponent) <= 0) {
				throw new BusinessException("Fail");
			}
			// execution input value
			if (lstExecutionInputValue != null && lstExecutionInputValue.size() > 0) {
				for (ExecutionInputValue inputValue : lstExecutionInputValue) {
					inputValue.setExecutionComponentId(mapId.get(inputValue.getExecutionComponentId()));
				}
				if (executionComponentRepository.registerExecutionInputValue(lstExecutionInputValue) <= 0) {
					throw new BusinessException("Fail");
				}
			}
			// execution output value
			if (lstExecutionOutputValue != null && lstExecutionOutputValue.size() > 0) {
				for (ExecutionOutputValue outputValue : lstExecutionOutputValue) {
					outputValue.setExecutionComponentId(mapId.get(outputValue.getExecutionComponentId()));
				}
				if (executionComponentRepository.registerExecutionOutputValue(lstExecutionOutputValue) <= 0) {
					throw new BusinessException("Fail");
				}
			}
		}
		// Register download component
		if (lstDownloadFileComponents != null && lstDownloadFileComponents.size() > 0) {
			Long seqDownloadFileComponent = downloadFileComponentRepository.getSequencesDownloadFileComponent(lstDownloadFileComponents.size() - 1);
			startSequence = seqDownloadFileComponent - (lstExecutionComponent.size() - 1);
			for (DownloadFileComponent down : lstDownloadFileComponents) {
				down.setDownloadFileComponentId(startSequence ++);
			}
			if (downloadFileComponentRepository.registerDownloadFileComponent(lstDownloadFileComponents) <= 0) {
				throw new BusinessException("Fail");
			}
		}
	}

	/**
	 * @param screenDesignObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void displayConfirmRegisterBlogic(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		List<ScreenItem> lstScreenItems = screenDesignDefault.getLstScreenItem();
		// register business logic
		BusinessDesign businessLogic = registerBusinesslogicDefault(screenDesignObj, screenDesignDefault, BusinessDesignConst.DISPLAY_CONFIRM_REGISTER, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0027, DbDomainConst.BlogicReturnType.INITIAL, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_CONFIRM_REGISTER, BusinessDesignConst.REQUEST_METHOD_INITIAL);
		// register input bean of this blogic
		List<InputBean> inputBeansRegisted = registerInputBean(screenDesignObj, screenDesignDefault, lstScreenItems, businessLogic, acountId, currentDate, true, false);
		// register output bean of this blogic
		List<OutputBean> outputBeansRegisted = registerOutputBean(screenDesignObj, screenDesignDefault, lstScreenItems, businessLogic.getBusinessLogicId(), acountId, currentDate, false);
		// generate sequence logic
		generateSequenceLogicConfirmRegister(screenDesignObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, inputBeansRegisted, outputBeansRegisted);
		// generate components
		registerComponent();
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicConfirmRegister(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<InputBean> inputBeansRegisted, List<OutputBean> outputBeansRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		mNameParameter = DetailServiceImpHandler.setLevelOfInputBeanOutputBeanObjectDefinition(inputBeansRegisted, outputBeansRegisted, null);
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L;
		SequenceConnector connector;
		Integer tempSequenceLogicId = 0;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempSequenceLogicId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempSequenceLogicId++;

		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			yCoordinates = yCoordinates + 70;
			SequenceLogic assign = GenerateScreenContruct.populateSequenceLogicNew(tempSequenceLogicId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
			lstSequenceLogic.add(assign);
			// set assign component
			AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), tempSequenceLogicId.longValue());
			lstAssignComponents.add(assignComponent);
			// set assign details
			contructAssignDetailFromInputBeanToOutputBean(table, inputBeansRegisted, outputBeansRegisted, assignIdTemp, false, screenDesignDefault.getCommonModel());
			assignIdTemp++;
			tempSequenceLogicId++;
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempSequenceLogicId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);

		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		// register sequence logic
		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);

		// register connector
		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void displaySearchBlogic(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		List<ScreenItem> lstScreenItemConditions = screenDesignDefault.getLstScreenItem();
		List<ScreenItem> lstScreenItemSearchResults = screenDesignDefault.getLstScreenItemSearchResults();
		if (lstScreenItemSearchResults != null && lstScreenItemSearchResults.size() > 0) {
			// REGISTER BUSINESS_LOGIC : DISPLAY SEARCH
			BusinessDesign businessLogic = registerBusinesslogicDefault(screenDesignObj, screenDesignDefault, BusinessDesignConst.DISPLAY_SEARCH, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0021, DbDomainConst.BlogicReturnType.INITIAL, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_SEARCH, BusinessDesignConst.REQUEST_METHOD_INITIAL);
			// register input bean of this blogic
			List<InputBean> inputbeansRegisted = registerInputBeanOfSeachScreen(screenDesignObj, screenDesignDefault, lstScreenItemConditions, businessLogic.getBusinessLogicId(), acountId, currentDate, DbDomainConst.InputBeanType.CUSTOMIZE, null, null);
			// register output bean of this blogic
			List<OutputBean> outputbeansRegisted = registerOutputBeanOfSearchScreen(screenDesignObj, screenDesignDefault, lstScreenItemSearchResults, businessLogic.getBusinessLogicId(), acountId, currentDate);
			// register object definition of
			List<ObjectDefinition> objDefsRegisted = registerObjectDefinitionOfSearchScreen(screenDesignObj, screenDesignDefault, lstScreenItemConditions, businessLogic.getBusinessLogicId(), acountId, currentDate, false, true);
			// gen sequence logic
			generateSequenceLogicDisplaySearch(screenDesignObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, objDefsRegisted, outputbeansRegisted, inputbeansRegisted);
			// register components
			registerComponent();
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicDisplaySearch(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<ObjectDefinition> objDefsRegisted, List<OutputBean> outputbeansRegisted, List<InputBean> inputbeansRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		ModuleTableMapping table = screenDesignDefault.getModuleTableMappings()[0];
		contructNewListComponents();
		SqlDesign sqlDesign;
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L;
		Integer tempId = 0, dupCode = 1;
		SequenceConnector connector;
		// Set to HaskMap
		mNameParameter = DetailServiceImpHandler.setLevelOfInputBeanOutputBeanObjectDefinition(inputbeansRegisted, outputbeansRegisted, objDefsRegisted);
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempId++;
		
		yCoordinates = yCoordinates + 70;
		SequenceLogic assignInputbeanToEntity = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(assignInputbeanToEntity);
		// set assign component
		AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), tempId.longValue());
		lstAssignComponents.add(assignComponent);
		// set assign details
		lstAssignDetails = contructAssignDetailFromInputBeanToObjectDefinitionForSearchScreen(table, inputbeansRegisted, objDefsRegisted, assignIdTemp, screenDesignDefault.getCommonModel());
		assignIdTemp++;
		tempId++;

		yCoordinates = yCoordinates + 70;
		SequenceLogic executionTotal = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0041), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(executionTotal);
		// execution component
		sqlDesign = autoGenerateSqlbuilder(sObj, screenDesignDefault, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0033), INITIAL_SEARCH_COUNT, SqlPattern.SELECT, null, inputbeansRegisted, null, outputbeansRegisted, true, true, true, true, true, false, false);
		screenDesignDefault.setSqlSearchCount(sqlDesign);
		ExecutionComponent executionComponent = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), INITIAL_SEARCH_COUNT, sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), false);
		lstExecutionComponent.add(executionComponent);
		contructExecutionInputValue(screenDesignDefault, table, tempId.longValue(), null, objDefsRegisted, sqlDesign.getAllSqlDesignInputs(), false);
		contructExecutionOutputValue(tempId.longValue(), null, sqlDesign.getAllSqlDesignOutputs(), outputbeansRegisted);
		tempId++;

		yCoordinates = yCoordinates + 70;
		SequenceLogic executionGet = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0042), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(executionGet);
		// execution component
		sqlDesign = autoGenerateSqlbuilder(sObj, screenDesignDefault, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0034), INITIAL_SEARCH_RECORDS, SqlPattern.SELECT, null, inputbeansRegisted, null, outputbeansRegisted, true, true, true, true, false, false, false);
		screenDesignDefault.setSqlSearchRecord(sqlDesign);
		ExecutionComponent executionComponentGetRecord = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), INITIAL_SEARCH_RECORDS, sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), false);
		lstExecutionComponent.add(executionComponentGetRecord);
		contructExecutionInputValue(screenDesignDefault, table, tempId.longValue(), null, objDefsRegisted, sqlDesign.getAllSqlDesignInputs(), false);
		contructExecutionOutputValue(tempId.longValue(), null, sqlDesign.getAllSqlDesignOutputs(), outputbeansRegisted);
		tempId++;

		Set<String> hashSetSqlCode = new HashSet<String>();
		if (FunctionCommon.isNotEmpty(table.getListTableDesignDetail())) {
			for (TableDesignDetails column : table.getListTableDesignDetail()) {
				if (column.getDatasourceType() != null && DbDomainConst.DatasourceType.SQL_BUILDER.equals(column.getDatasourceType())) {
					yCoordinates = yCoordinates + 70;
					SequenceLogic execution = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0047, column.getName()), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
					lstSequenceLogic.add(execution);
					SqlDesign sqlDesignDatasource = screenDesignDefault.getMapSqlDesign().get(column.getDatasourceId());
					if (sqlDesignDatasource == null) {
						throw new BusinessException(ResultMessages.error().add(ScreenDesignConst.ERR_SCREENDESIGN_0433, column.getCode(), column.getTableDesignName()));
					}
					String rootSqlDesignCode = "";
					if(!hashSetSqlCode.contains(sqlDesignDatasource.getSqlDesignCode())) {
						hashSetSqlCode.add(sqlDesignDatasource.getSqlDesignCode());
						rootSqlDesignCode = sqlDesignDatasource.getSqlDesignCode()+ "Output";
					} else {
						dupCode++;
						rootSqlDesignCode = sqlDesignDatasource.getSqlDesignCode() + dupCode + "Output";
					}
					SqlDesignOutput[] lstSqlOutput = sqlDesignOutputRepository.findAllBySqlDesignId(sqlDesignDatasource.getSqlDesignId());
					if (lstSqlOutput != null && lstSqlOutput.length > 0) {
						sqlDesignDatasource.setAllSqlDesignOutputs(Arrays.asList(lstSqlOutput));
					}
					ExecutionComponent executionComponentOfDatasource = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0047, column.getName()), column.getDatasourceId(), sqlDesignDatasource, tempId.longValue(), false);
					lstExecutionComponent.add(executionComponentOfDatasource);
					List<OutputBean> outputbeans = registerOutputbeanOfDatasourceValue(sObj, screenDesignDefault, businessLogicId, column, outputbeansRegisted, rootSqlDesignCode);
					if (FunctionCommon.isEmpty(outputbeansRegisted)) {
						outputbeansRegisted = new ArrayList<OutputBean>();
					}
					for (OutputBean ou : outputbeans) {
						outputbeansRegisted.add(ou);
					}
					List<ObjectDefinition> objDefs = registerObjectDefinitionOfDatasourceValue(businessLogicId, column.getDatasourceId(), objDefsRegisted, rootSqlDesignCode);
					if (FunctionCommon.isEmpty(objDefsRegisted)) {
						objDefsRegisted = new ArrayList<ObjectDefinition>();
					}
					for (ObjectDefinition ob : objDefs) {
						objDefsRegisted.add(ob);
					}
					mappingExecutionOutputValueWithObjectDefinition(tempId.longValue(), objDefs, sqlDesignDatasource.getAllSqlDesignOutputs());
					tempId++;

					yCoordinates = yCoordinates + 70;
					SequenceLogic assign = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0048, column.getName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
					lstSequenceLogic.add(assign);
					// set assign component
					AssignComponent assignComponentOfDatasource = GenerateScreenContruct.contructAssignComponent(tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0048, column.getName()), tempId.longValue());
					lstAssignComponents.add(assignComponentOfDatasource);
					// assign details
					contructAssignDetailFromObjectDefinitonToOutputBean(null, objDefs, outputbeans, tempId.longValue(), true, null, null);

					tempId++;
				}
			}
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);

		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		// register sequence logic
		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);

		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for execution component
		for (ExecutionComponent exec : lstExecutionComponent) {
			exec.setSequenceLogicId(mapKeySequence.get(exec.getSequenceLogicId().toString()));
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param acountId
	 * @param currentDate
	 */
	private void processSearchBlogic(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate) {
		// REGISTER BUSINESS_LOGIC : PROCESS SEARCH
		List<ScreenItem> lstScreenItemConditions = screenDesignDefault.getLstScreenItem();
		List<ScreenItem> lstScreenItemSearchResults = screenDesignDefault.getLstScreenItemSearchResults();
		contructNewListComponents();
		if (lstScreenItemSearchResults != null && lstScreenItemSearchResults.size() > 0) {
			BusinessDesign businessLogic = registerBusinesslogicDefault(screenDesignObj, screenDesignDefault, BusinessDesignConst.PROCESS_SEARCH, GenerateSourceCodeConst.SC_GENERATESOURCECODE_0022, DbDomainConst.BlogicReturnType.SCREEN, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_SEARCH, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
			// register input bean of this blogic
			List<InputBean> inputbeansRegisted = registerInputBeanOfSeachScreen(screenDesignObj, screenDesignDefault, lstScreenItemConditions, businessLogic.getBusinessLogicId(), acountId, currentDate, DbDomainConst.InputBeanType.DEFAULT, lstScreenItemSearchResults, true);
			// register output bean of this blogic
			List<OutputBean> outputbeansRegisted = registerOutputBeanOfSearchScreen(screenDesignObj, screenDesignDefault, lstScreenItemSearchResults, businessLogic.getBusinessLogicId(), acountId, currentDate);
			// register object definition of
			List<ObjectDefinition> objDefsRegisted = registerObjectDefinitionOfSearchScreen(screenDesignObj, screenDesignDefault, lstScreenItemConditions, businessLogic.getBusinessLogicId(), acountId, currentDate, false, true);
			// gen sequence logic
			generateSequenceLogicProcessSearch(screenDesignObj, screenDesignDefault, businessLogic.getBusinessLogicId(), acountId, currentDate, objDefsRegisted, outputbeansRegisted, inputbeansRegisted);
			// register components
			registerComponent();
		}
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicProcessSearch(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, Long acountId, Timestamp currentDate, List<ObjectDefinition> objDefsRegisted, List<OutputBean> outputbeansRegisted, List<InputBean> inputbeansRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		ModuleTableMapping table = screenDesignDefault.getModuleTableMappings()[0];
		contructNewListComponents();
		SqlDesign sqlDesign;
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L;
		SequenceConnector connector;
		Integer tempId = 0;
		// Set to HaskMap
		mNameParameter = DetailServiceImpHandler.setLevelOfInputBeanOutputBeanObjectDefinition(inputbeansRegisted, outputbeansRegisted, objDefsRegisted);
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempId++;
		
		yCoordinates = yCoordinates + 70;
		SequenceLogic assignInputbeanToEntity = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(assignInputbeanToEntity);
		// set assign component
		AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), tempId.longValue());
		lstAssignComponents.add(assignComponent);
		// set assign details
		lstAssignDetails = contructAssignDetailFromInputBeanToObjectDefinitionForSearchScreen(table, inputbeansRegisted, objDefsRegisted, assignIdTemp, screenDesignDefault.getCommonModel());
		assignIdTemp++;
		tempId++;

		yCoordinates = yCoordinates + 70;
		SequenceLogic executionTotal = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0049), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(executionTotal);
		// execution component
//		sqlDesign = autoGenerateSqlbuilder(sObj, screenDesignDefault, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0036), PROCESS_SEARCH_COUNT, SqlPattern.SELECT, null, inputbeansRegisted, null, outputbeansRegisted,true, true, true, true, true, false, false);
		sqlDesign = screenDesignDefault.getSqlSearchCount();
		ExecutionComponent executionComponent = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), PROCESS_SEARCH_COUNT, sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), false);
		lstExecutionComponent.add(executionComponent);
		contructExecutionInputValue(screenDesignDefault, table, tempId.longValue(), null, objDefsRegisted, sqlDesign.getAllSqlDesignInputs(), false);
		mappingExecutionOutputValueWithOutputbean(tempId.longValue(), outputbeansRegisted, sqlDesign.getAllSqlDesignOutputs());

		tempId++;

		yCoordinates = yCoordinates + 70;
		SequenceLogic executionGet = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0050), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(executionGet);
		// execution component
//		sqlDesign = autoGenerateSqlbuilder(sObj, screenDesignDefault,  MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0037), PROCESS_SEARCH_RECORDS, SqlPattern.SELECT, null, inputbeansRegisted, null, outputbeansRegisted, true, true, true, true, false, false, false);
		sqlDesign = screenDesignDefault.getSqlSearchRecord();
		ExecutionComponent executionComponentGetRecord = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), PROCESS_SEARCH_RECORDS, sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), false);
		lstExecutionComponent.add(executionComponentGetRecord);
		contructExecutionInputValue(screenDesignDefault, table, tempId.longValue(), null, objDefsRegisted, sqlDesign.getAllSqlDesignInputs(), false);
		mappingExecutionOutputValueWithOutputbean(tempId.longValue(), outputbeansRegisted, sqlDesign.getAllSqlDesignOutputs());
		tempId++;
		
		// update screen_area to config header sort
		if (FunctionCommon.isNotEmpty(screenDesignDefault.getMapAreaResultAndItemHeader())) {
			int doOne = 0;
			List<ScreenAreaSortMapping> lstAreaSortMappings = new ArrayList<ScreenAreaSortMapping>();
			ScreenArea areaResult = null;
			for (ScreenArea area : screenDesignDefault.getMapAreaResultAndItemHeader().keySet()) {
				if (doOne == 0) {
					area.setSqlColumnId(sqlDesign.getSqlDesignId());
					areaResult = area;
				}
				List<ScreenItem> headerItems = screenDesignDefault.getMapAreaResultAndItemHeader().get(area);
				if (FunctionCommon.isNotEmpty(headerItems)) {
					for (ScreenItem it : headerItems) {
						ScreenAreaSortMapping sortMapping = new ScreenAreaSortMapping();
						if (screenDesignDefault.getFormOfSearchScreen() != null) {
							sortMapping.setScreenAreaCode(screenDesignDefault.getFormOfSearchScreen().getFormCode() + DOT + area.getAreaCode());
						}
						sortMapping.setScreenItemCode(it.getItemCode());
						if (FunctionCommon.isNotEmpty(sqlDesign.getAllSqlDesignOutputs())) {
							for (SqlDesignOutput sqlOut : sqlDesign.getAllSqlDesignOutputs()) {
								if (DataTypeUtils.equals(it.getColumnId(), sqlOut.getColumnId())) {
									if(!DbDomainConst.JavaDataTypeOfBlogic.BYTE_DATATYPE.equals(sqlOut.getDataType())) {
										sortMapping.setSqlColumnCode(sqlOut.getMappingColumn());
										break;
									}
								}
							}
						}
						sortMapping.setScreenId(sObj.getScreenId());
						lstAreaSortMappings.add(sortMapping);
					}
				}
			}
			if (areaResult != null) {
				screenAreaRepository.updateSqlColumnIdOfScreenArea(areaResult);
				if (FunctionCommon.isNotEmpty(lstAreaSortMappings)) {
					screenAreaRepository.insertScreenAreaSortMapping(lstAreaSortMappings);
				}
			}
		}

//		yCoordinates = yCoordinates + 70;
//		SequenceLogic assignSetToOutput = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0051), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
//		lstSequenceLogic.add(assignSetToOutput);
//		// set assign component
//		AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0051), tempId.longValue());
//		lstAssignComponents.add(assignComponent);
//		// set assign details
//		lstAssignDetails = contructAssignDetailFromObjectDefinitonToOutputBean(null, objDefsRegisted, outputbeansRegisted, assignIdTemp, true, null, null);
//		assignIdTemp++;
//		tempId++;
		
		// set data for navigator component
		yCoordinates = yCoordinates + 70;
		SequenceLogic navigator = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0057), DbDomainConst.ComponentType.NAVIGATOR, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(navigator);
		NavigatorComponent navigatorComponent = GenerateScreenContruct.populateNavigatorComponent(screenDesignDefault, tempId.longValue(),  MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0057), sObj.getScreenId(), null, sObj.getMessageDesign().getMessageCode(), BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_SCREEN);
		lstNavigatorComponents.add(navigatorComponent);
		contructNavigatorDetail(screenDesignDefault, tempId.longValue(), outputbeansRegisted, null);
		tempId++;

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);
		
		updateBLogicNavigator(SEARCH_NAVIGATE, businessLogicId);
		
		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		// register sequence logic
		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);

		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for execution component
		for (ExecutionComponent exec : lstExecutionComponent) {
			exec.setSequenceLogicId(mapKeySequence.get(exec.getSequenceLogicId().toString()));
		}
		// set sequence logic for navigator component
		for (NavigatorComponent navi : lstNavigatorComponents) {
			navi.setSequenceLogicId(mapKeySequence.get(navi.getSequenceLogicId().toString()));
		}
	}

	/**
	 * @param screenDesignDefault
	 * @param navigatorComponentIdTemp
	 * @param inputbeansRegisted
	 * @param objDefsRegisted
	 * @param businessLogicIdTarget
	 */
	private void contructNavigatorDetail(ScreenDesignDefault screenDesignDefault, Long navigatorComponentIdTemp, List<OutputBean> outputbeanRegisted, Long businessLogicIdTarget) {
//		List<Long> lstColumnIds = new ArrayList<Long>();
		List<InputBean> lstInputOfScreenTarget = screenDesignDefault.getMapInputBeanOfBLogic().get(businessLogicIdTarget);
//		if(FunctionCommon.isNotEmpty(lstInputOfScreenTarget)){
//			for (InputBean inTarget : lstInputOfScreenTarget) {
//				if(inTarget.getColumnId() != null){
//					lstColumnIds.add(inTarget.getColumnId());
//				}
//			}
//		}
//		List<TableDesignDetails> lstColumns = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIds);
//		Map<Long, TableDesignDetails> mapColumns = new HashMap<Long, TableDesignDetails>();
//		if (FunctionCommon.isNotEmpty(lstColumns)) {
//			for (TableDesignDetails column : lstColumns) {
//				mapColumns.put(column.getColumnId(), column);
//			}
//		}
		
		if (outputbeanRegisted != null && outputbeanRegisted.size() > 0 && FunctionCommon.isNotEmpty(lstInputOfScreenTarget)) {
			for (InputBean inTarget : lstInputOfScreenTarget) {
//				if(inTarget.getColumnId() != null){
//					lstColumnIds.add(inTarget.getColumnId());
//				}
				for (OutputBean ou : outputbeanRegisted) {
					boolean isSetting = false;
					if (BusinessDesignConst.DataType.OBJECT.equals(ou.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(ou.getDataType()) || BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(ou.getDataType()) || BusinessDesignConst.DataType.COMMON_OBJECT.equals(ou.getDataType())) {
						if (ou.getTblDesignId() != null && ou.getTblDesignId().equals(inTarget.getTblDesignId()) && ou.getColumnId() == null && inTarget.getColumnId() == null) {
							isSetting = true;
						}
					} else {
						if (inTarget.getColumnId() != null) {
							if (inTarget.getColumnId().equals(ou.getColumnId())) {
								isSetting = true;
							}
						}
					}
					if (isSetting) {
						NavigatorDetail detail = new NavigatorDetail();
						detail.setNavigatorComponentId(navigatorComponentIdTemp);
						Long inputBeanId = inTarget.getInputBeanId() == null ? null : Long.parseLong(inTarget.getInputBeanId());
						detail.setInputBeanId(inputBeanId);
						detail.setInputBeanCode(inTarget.getInputBeanCode());
						String inputbeanName = inTarget.getMessageString() != null ? inTarget.getMessageString() : inTarget.getInputBeanName();
						detail.setInputBeanName(inputbeanName);
						detail.setArrayFlg(inTarget.getArrayFlg());
						detail.setDataType(inTarget.getDataType());
						detail.setParameterScope(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID);
						detail.setParameterId(ou.getOutputBeanId());

						lstNavigatorDetails.add(detail);
						break;
					}
				}
			}
		}
	}

	/**
	 * @param table
	 * @param inputbeansRegisted
	 * @param objDefsRegisted
	 * @param assignIdTemp
	 * @return
	 */
	private List<AssignDetail> contructAssignDetailFromInputBeanToObjectDefinition(ModuleTableMapping table, List<InputBean> inputbeansRegisted, List<ObjectDefinition> objDefsRegisted, Long assignIdTemp, Integer type, String sequenceLogicOfLoopId) {
		List<InputBean> inputbeansOfThisTable = new ArrayList<InputBean>();
		List<ObjectDefinition> objDefOfThisTable = new ArrayList<ObjectDefinition>();
		AssignDetail assignDetail = new AssignDetail();
		for (InputBean in : inputbeansRegisted) {
			if (table.getTblDesignId().equals(in.getTblDesignId())) {
				inputbeansOfThisTable.add(in);
			}
		}
		for (ObjectDefinition ob : objDefsRegisted) {
			if (table.getTblDesignId().equals(ob.getTblDesignId())) {
				objDefOfThisTable.add(ob);
			}
		}
		
		List<BDParameterIndex> lstIndexOfParameter;
		BDParameterIndex temp;
		for (InputBean in : inputbeansOfThisTable) {
			for (ObjectDefinition ob : objDefOfThisTable) {
				boolean isSetting = false;
				if(BusinessDesignConst.DataType.OBJECT.equals(in.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(in.getDataType())) {
					if (in.getTblDesignId() != null && in.getTblDesignId().equals(ob.getTblDesignId()) && in.getColumnId() == null && ob.getColumnId() == null) {
						isSetting = true;
					}
				} else if(BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(in.getDataType())) {
					if (in.getTblDesignId() != null && in.getTblDesignId().equals(ob.getTblDesignId()) && in.getColumnId() == null && BusinessDesignConst.DataType.BYTE.equals(ob.getDataType())) {
						isSetting = true;
					}
				} else {
					if(in.getColumnId() != null && in.getColumnId().equals(ob.getColumnId())) {
						isSetting = true;
					}
				}
				if (isSetting) {
					if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(in.getDataType())) {
						List<InputBean> childInputBeans = getChildInputBean(inputbeansRegisted, in.getInputBeanId());
						for (InputBean child : childInputBeans) {
							if (child.getDataType().equals(ob.getDataType())) {
								assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_INPUT_BEAN, child.getInputBeanId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OBJECT_DEFINITION, ob.getObjectDefinitionId(), assignIdTemp, child, null, ob, FROM_INPUT_TO_OBJECTDEFINITION);
								break;
							}
						}
					} else {
						assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_INPUT_BEAN, in.getInputBeanId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OBJECT_DEFINITION, ob.getObjectDefinitionId(), assignIdTemp, in, null, ob, FROM_INPUT_TO_OBJECTDEFINITION);
					}
					if(LIST_TYPE.equals(type)){
						lstIndexOfParameter = new ArrayList<BDParameterIndex>();
						temp = new BDParameterIndex();
						temp.setBusinessLogicId(in.getBusinessLogicId());
						temp.setTableType(BusinessDesignConst.ParameterIndex.TABLE_ASSIGN_DETAIL_PARAMETER);
						// In case : in is table.
						if(in.getColumnId() != null) {
							temp.setParameterId(in.getParentInputBeanId());
						} else {
							temp.setParameterId(in.getInputBeanId());
						}
						temp.setParameterIndexType(BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP);
						temp.setParameterIndexId(sequenceLogicOfLoopId);
						temp.setTableId(assignDetail.getAssignDetailId());
						lstIndexOfParameter.add(temp);
						assignDetail.setLstParameterIndex(lstIndexOfParameter);
					}
					lstAssignDetails.add(assignDetail);
				}
			}
		}
		return lstAssignDetails;
	}
	
	private List<AssignDetail> contructAssignDetailFromInputBeanToObjectDefinitionForCheckbox(ScreenDesignDefault screenDesignDefault, ModuleTableMapping table, List<InputBean> inputbeansRegisted, List<ObjectDefinition> objDefsRegisted, Long assignIdTemp, Integer type, String sequenceLogicOfLoopId, CommonModel common, boolean processModify) {
		List<InputBean> inputbeansOfThisTable = new ArrayList<InputBean>();
		List<ObjectDefinition> objDefOfThisTable = new ArrayList<ObjectDefinition>();
		ModuleTableMapping firstTable  = screenDesignDefault.getModuleTableMappings()[0];
		AssignDetail assignDetail = new AssignDetail();
		List<Long> lstColumnIds = new ArrayList<Long>();
		for (InputBean in : inputbeansRegisted) {
			if (table.getTblDesignId().equals(in.getTblDesignId())) {
				inputbeansOfThisTable.add(in);
				lstColumnIds.add(in.getColumnId());
			}
		}
		for (ObjectDefinition ob : objDefsRegisted) {
			if (table.getTblDesignId().equals(ob.getTblDesignId())) {
				objDefOfThisTable.add(ob);
			}
		}
		
		List<TableDesignDetails> lstColumns = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIds);
		Map<Long, TableDesignDetails> mapColumns = new HashMap<Long, TableDesignDetails>();
		if (FunctionCommon.isNotEmpty(lstColumns)) {
			for (TableDesignDetails column : lstColumns) {
				mapColumns.put(column.getColumnId(), column);
			}
		}
		
		List<BDParameterIndex> lstIndexOfParameter;
		BDParameterIndex temp;
		int doOne = 0;
		if (processModify) {
			for (InputBean in : inputbeansOfThisTable) {
				TableDesignDetails column = mapColumns.get(in.getColumnId());
				for (ObjectDefinition ob : objDefOfThisTable) {
					if(UPDATED_DATE.equalsIgnoreCase(ob.getObjectDefinitionCode()) && firstTable.getTblDesignId().equals(table.getTblDesignId()) && 
							!BusinessDesignConst.DataType.OBJECT.equals(ob.getDataType()) && 
							!BusinessDesignConst.DataType.ENTITY.equals(ob.getDataType()) && 
							!BusinessDesignConst.DataType.COMMON_OBJECT.equals(ob.getDataType()) && 
							!BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(ob.getDataType())) {
						if(doOne == 0) {
							assignDetail = GenerateScreenContruct.setAssignDetail(null, null, BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OBJECT_DEFINITION, ob.getObjectDefinitionId(), assignIdTemp, null, null, ob, null);
							createFormulaDefinitionCurrentDate(assignDetail, screenDesignDefault.getCommonModel().getWorkingProjectId());
							lstAssignDetails.add(assignDetail);
							doOne++;
						}
					} else {
						boolean isSetting = false;
						if(BusinessDesignConst.DataType.OBJECT.equals(in.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(in.getDataType())) {
							if (in.getTblDesignId() != null && in.getTblDesignId().equals(ob.getTblDesignId()) && in.getColumnId() == null && ob.getColumnId() == null) {
								isSetting = true;
							}
						} else if(BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(in.getDataType())) {
							if (in.getTblDesignId() != null && in.getTblDesignId().equals(ob.getTblDesignId()) && in.getColumnId() == null && BusinessDesignConst.DataType.BYTE.equals(ob.getDataType())) {
								isSetting = true;
							}
						} else {
							if(in.getColumnId() != null && in.getColumnId().equals(ob.getColumnId())) {
								isSetting = true;
							}
						}
						
						if (isSetting) {
							if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(in.getDataType())) {
								List<InputBean> childInputBeans = getChildInputBean(inputbeansRegisted, in.getInputBeanId());
								for (InputBean child : childInputBeans) {
									if (child.getDataType().equals(ob.getDataType())) {
										assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_INPUT_BEAN, child.getInputBeanId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OBJECT_DEFINITION, ob.getObjectDefinitionId(), assignIdTemp, child, null, ob, FROM_INPUT_TO_OBJECTDEFINITION);
										break;
									}
								}
							} else {
								assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_INPUT_BEAN, in.getInputBeanId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OBJECT_DEFINITION, ob.getObjectDefinitionId(), assignIdTemp, in, null, ob, FROM_INPUT_TO_OBJECTDEFINITION);
							}
							if(LIST_TYPE.equals(type)){
								lstIndexOfParameter = new ArrayList<BDParameterIndex>();
								temp = new BDParameterIndex();
								temp.setBusinessLogicId(in.getBusinessLogicId());
								temp.setTableType(BusinessDesignConst.ParameterIndex.TABLE_ASSIGN_DETAIL_PARAMETER);
								// In case : in is table.
								if(in.getColumnId() != null) {
									temp.setParameterId(in.getParentInputBeanId());
								} else {
									temp.setParameterId(in.getInputBeanId());
								}
								temp.setParameterIndexType(BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP);
								temp.setParameterIndexId(sequenceLogicOfLoopId);
								temp.setTableId(assignDetail.getAssignDetailId());
								lstIndexOfParameter.add(temp);
								if(!(column != null && this.isArrayForCheckbox(column))){
									assignDetail.setLstParameterIndex(lstIndexOfParameter);
								}
							}
							if(column != null && this.isArrayForCheckbox(column)){
								if(sequenceLogicOfLoopId == null){
									this.createFormulaDefinitionAssignNode(assignDetail, in, "", common);
								}else{
									this.createFormulaDefinitionAssignNode(assignDetail, in, sequenceLogicOfLoopId, common);
								}
							}
							lstAssignDetails.add(assignDetail);
						}
					}
				}
			}
			
		} else {
			for (InputBean in : inputbeansOfThisTable) {
				TableDesignDetails column = mapColumns.get(in.getColumnId());
				for (ObjectDefinition ob : objDefOfThisTable) {
					boolean isSetting = false;
					if(BusinessDesignConst.DataType.OBJECT.equals(in.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(in.getDataType())) {
						if (in.getTblDesignId() != null && in.getTblDesignId().equals(ob.getTblDesignId()) && in.getColumnId() == null && ob.getColumnId() == null) {
							isSetting = true;
						}
					} else if(BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(in.getDataType())) {
						if (in.getTblDesignId() != null && in.getTblDesignId().equals(ob.getTblDesignId()) && in.getColumnId() == null && BusinessDesignConst.DataType.BYTE.equals(ob.getDataType())) {
							isSetting = true;
						}
					} else {
						if(in.getColumnId() != null && in.getColumnId().equals(ob.getColumnId())) {
							isSetting = true;
						}
					}
					
					if (isSetting) {
						if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(in.getDataType())) {
							List<InputBean> childInputBeans = getChildInputBean(inputbeansRegisted, in.getInputBeanId());
							for (InputBean child : childInputBeans) {
								if (child.getDataType().equals(ob.getDataType())) {
									assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_INPUT_BEAN, child.getInputBeanId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OBJECT_DEFINITION, ob.getObjectDefinitionId(), assignIdTemp, child, null, ob, FROM_INPUT_TO_OBJECTDEFINITION);
									break;
								}
							}
						} else {
							assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_INPUT_BEAN, in.getInputBeanId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OBJECT_DEFINITION, ob.getObjectDefinitionId(), assignIdTemp, in, null, ob, FROM_INPUT_TO_OBJECTDEFINITION);
						}
						if(LIST_TYPE.equals(type)){
							lstIndexOfParameter = new ArrayList<BDParameterIndex>();
							temp = new BDParameterIndex();
							temp.setBusinessLogicId(in.getBusinessLogicId());
							temp.setTableType(BusinessDesignConst.ParameterIndex.TABLE_ASSIGN_DETAIL_PARAMETER);
							// In case : in is table.
							if(in.getColumnId() != null) {
								temp.setParameterId(in.getParentInputBeanId());
							} else {
								temp.setParameterId(in.getInputBeanId());
							}
							temp.setParameterIndexType(BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP);
							temp.setParameterIndexId(sequenceLogicOfLoopId);
							temp.setTableId(assignDetail.getAssignDetailId());
							lstIndexOfParameter.add(temp);
							if(!(column != null && this.isArrayForCheckbox(column))){
								assignDetail.setLstParameterIndex(lstIndexOfParameter);
							}
						}
						if(column != null && this.isArrayForCheckbox(column)){
							if(sequenceLogicOfLoopId == null){
								this.createFormulaDefinitionAssignNode(assignDetail, in, "", common);
							}else{
								this.createFormulaDefinitionAssignNode(assignDetail, in, sequenceLogicOfLoopId, common);
							}
						}
						lstAssignDetails.add(assignDetail);
					}
				}
			}
		}
		return lstAssignDetails;
	}
	
	private List<AssignDetail> contructAssignDetailFromInputBeanToObjectDefinitionForSearchScreen(ModuleTableMapping table, List<InputBean> inputbeansRegisted, List<ObjectDefinition> objDefsRegisted, Long assignIdTemp, CommonModel common) {
		List<InputBean> inputbeansOfThisTable = new ArrayList<InputBean>();
		List<ObjectDefinition> objDefOfThisTable = new ArrayList<ObjectDefinition>();
		AssignDetail assignDetail = new AssignDetail();
		List<Long> lstColumnIds = new ArrayList<Long>();
		for (InputBean in : inputbeansRegisted) {
			if (table.getTblDesignId().equals(in.getTblDesignId())) {
				inputbeansOfThisTable.add(in);
				lstColumnIds.add(in.getColumnId());
			}
		}
		for (ObjectDefinition ob : objDefsRegisted) {
			if (table.getTblDesignId().equals(ob.getTblDesignId())) {
				objDefOfThisTable.add(ob);
			}
		}
		
		List<TableDesignDetails> lstColumns = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIds);
		Map<Long, TableDesignDetails> mapColumns = new HashMap<Long, TableDesignDetails>();
		if (FunctionCommon.isNotEmpty(lstColumns)) {
			for (TableDesignDetails column : lstColumns) {
				mapColumns.put(column.getColumnId(), column);
			}
		}
		
		for (InputBean in : inputbeansOfThisTable) {
			TableDesignDetails column = mapColumns.get(in.getColumnId());
			for (ObjectDefinition ob : objDefOfThisTable) {
				boolean isSetting = false;
				if (BusinessDesignConst.DataType.OBJECT.equals(in.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(in.getDataType())) {
					if (in.getTblDesignId() != null && in.getTblDesignId().equals(ob.getTblDesignId()) && in.getColumnId() == null && ob.getColumnId() == null) {
						isSetting = true;
					}
				} else if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(in.getDataType())) {
					if (in.getTblDesignId() != null && in.getTblDesignId().equals(ob.getTblDesignId()) && in.getColumnId() == null && BusinessDesignConst.DataType.BYTE.equals(ob.getDataType())) {
						isSetting = true;
					}
				} else {
					if (in.getColumnId() != null && in.getColumnId().equals(ob.getColumnId())) {
						isSetting = true;
					}
				}

				if (isSetting) {
					if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(in.getDataType())) {
						List<InputBean> childInputBeans = getChildInputBean(inputbeansRegisted, in.getInputBeanId());
						for (InputBean child : childInputBeans) {
							if (child.getDataType().equals(ob.getDataType())) {
								assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_INPUT_BEAN, child.getInputBeanId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OBJECT_DEFINITION, ob.getObjectDefinitionId(), assignIdTemp, child, null, ob, FROM_INPUT_TO_OBJECTDEFINITION);
								lstAssignDetails.add(assignDetail);
								break;
							}
						}
					} else {
						if (in.getFromOrTo() != null) {
							if (in.getFromOrTo().equals(ob.getFromOrTo())) {
								assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_INPUT_BEAN, in.getInputBeanId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OBJECT_DEFINITION, ob.getObjectDefinitionId(), assignIdTemp, in, null, ob, FROM_INPUT_TO_OBJECTDEFINITION);
								if (column != null && DbDomainConst.BaseType.BOOLEAN_BASETYPE != column.getBaseType() && (DbDomainConst.LogicDataTypePrimitive.CHECKBOX == column.getItemType())) {
									if(isArrayForCheckbox(column)){
										createFormulaDefinitionAssignNode(assignDetail, in, "", common);
									}
								}
								lstAssignDetails.add(assignDetail);
								break;
							}
						} else {
							assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_INPUT_BEAN, in.getInputBeanId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OBJECT_DEFINITION, ob.getObjectDefinitionId(), assignIdTemp, in, null, ob, FROM_INPUT_TO_OBJECTDEFINITION);
							if (column != null && DbDomainConst.BaseType.BOOLEAN_BASETYPE != column.getBaseType() && (DbDomainConst.LogicDataTypePrimitive.CHECKBOX == column.getItemType())) {
								if(isArrayForCheckbox(column)){
									createFormulaDefinitionAssignNode(assignDetail, in, "", common);
								}
							}
							lstAssignDetails.add(assignDetail);
							break;
						}
					}
				}
			}
		}
		return lstAssignDetails;
	}

	/**
	 * @param table
	 * @param objDefsRegisted
	 * @param outputBeansRegisted
	 * @param assignIdTemp
	 * @return
	 */
	private List<AssignDetail> contructAssignDetailFromObjectDefinitonToOutputBean(ModuleTableMapping table, List<ObjectDefinition> objDefsRegisted, List<OutputBean> outputBeansRegisted, Long assignIdTemp, Boolean flgSearchScreen, Integer type, String sequenceLogicOfLoopId) {
		List<ObjectDefinition> objDefOfThisTable = new ArrayList<ObjectDefinition>();
		List<OutputBean> outputbeansOfThisTable = new ArrayList<OutputBean>();
		AssignDetail assignDetail;

		if (!flgSearchScreen) {
			for (ObjectDefinition ob : objDefsRegisted) {
				if (table.getTblDesignId().equals(ob.getTblDesignId())) {
					objDefOfThisTable.add(ob);
				}
			}
			for (OutputBean out : outputBeansRegisted) {
				if (table.getTblDesignId().equals(out.getTblDesignId())) {
					outputbeansOfThisTable.add(out);
				}
			}
			List<BDParameterIndex> lstIndexOfParameter;
			BDParameterIndex temp;
			for (ObjectDefinition ob : objDefOfThisTable) {
				for (OutputBean out : outputbeansOfThisTable) {
					boolean isSetting = false;
					if(BusinessDesignConst.DataType.OBJECT.equals(ob.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(ob.getDataType())) {
						if (ob.getTblDesignId() != null && ob.getTblDesignId().equals(out.getTblDesignId()) && ob.getColumnId() == null && out.getColumnId() == null) {
							isSetting = true;
						}
					} else if(BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(ob.getDataType())) {
						if (ob.getTblDesignId() != null && ob.getTblDesignId().equals(out.getTblDesignId()) && ob.getColumnId() == null && BusinessDesignConst.DataType.BYTE.equals(out.getDataType())) {
							isSetting = true;
						}
					} else {
						if(ob.getColumnId() != null && ob.getColumnId().equals(out.getColumnId())) {
							isSetting = true;
						}
					}
					if (isSetting) {
						assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_OBJECT_DEFINITION, ob.getObjectDefinitionId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OUTPUT_BEAN, out.getOutputBeanId(), assignIdTemp, null, out, ob, FROM_OBJECTDEFINITION_TO_OUTPUT);
						if(LIST_TYPE.equals(type)){
							lstIndexOfParameter = new ArrayList<BDParameterIndex>();
							temp = new BDParameterIndex();
							temp.setBusinessLogicId(out.getBusinessLogicId());
							temp.setTableType(BusinessDesignConst.ParameterIndex.TABLE_ASSIGN_DETAIL_TARGET);
							// In case : in is table.
							if(out.getColumnId() != null) {
								temp.setParameterId(out.getParentOutputBeanId());
							} else {
								temp.setParameterId(out.getOutputBeanId());
							}
							temp.setParameterIndexType(BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP);
							temp.setParameterIndexId(sequenceLogicOfLoopId);
							temp.setTableId(assignDetail.getAssignDetailId());
							lstIndexOfParameter.add(temp);
							assignDetail.setLstParameterIndex(lstIndexOfParameter);
						}
						lstAssignDetails.add(assignDetail);
						break;
					}
				}
			}
		} else {
			for (ObjectDefinition ob : objDefsRegisted) {
				for (OutputBean out : outputBeansRegisted) {
					if (ob.getObjectDefinitionCode().equals(out.getOutputBeanCode())) {
						assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_OBJECT_DEFINITION, ob.getObjectDefinitionId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OUTPUT_BEAN, out.getOutputBeanId(), assignIdTemp, null, out, ob, FROM_OBJECTDEFINITION_TO_OUTPUT);
						lstAssignDetails.add(assignDetail);
					}
				}
			}
		}
		return lstAssignDetails;
	}

	/**
	 * @param table
	 * @param inputbeansRegisted
	 * @param objDefsRegisted
	 * @param assignIdTemp
	 * @return
	 */
	private void contructAssignDetailFromInputBeanToOutputBean(ModuleTableMapping table, List<InputBean> inputbeansRegisted, List<OutputBean> outputbeansRegisted, Long assignIdTemp, Boolean flagUndoBlogic, CommonModel common) {
		List<InputBean> inputbeansOfThisTable = new ArrayList<InputBean>();
		List<OutputBean> outputbeansOfThisTable = new ArrayList<OutputBean>();
		AssignDetail assignDetail = new AssignDetail();
		List<Long> lstColumnIds = new ArrayList<Long>();
		Map<Long, TableDesignDetails> mapColumns = new HashMap<Long, TableDesignDetails>();
		for (InputBean in : inputbeansRegisted) {
			if (table.getTblDesignId().equals(in.getTblDesignId())) {
				inputbeansOfThisTable.add(in);
				lstColumnIds.add(in.getColumnId());
			}
		}
		for (OutputBean out : outputbeansRegisted) {
			if (table.getTblDesignId().equals(out.getTblDesignId())) {
				outputbeansOfThisTable.add(out);
			}
		}
		
		if(flagUndoBlogic) {
			List<TableDesignDetails> lstColumns = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIds);
			if (FunctionCommon.isNotEmpty(lstColumns)) {
				for (TableDesignDetails column : lstColumns) {
					mapColumns.put(column.getColumnId(), column);
				}
			}
		}
		
		for (InputBean in : inputbeansOfThisTable) {
			for (OutputBean out : outputbeansOfThisTable) {
				boolean isSetting = false;
				if(BusinessDesignConst.DataType.OBJECT.equals(in.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(in.getDataType())) {
					if (in.getTblDesignId() != null && in.getTblDesignId().equals(out.getTblDesignId()) && in.getColumnId() == null && out.getColumnId() == null) {
						isSetting = true;
					}
				} else if(BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(in.getDataType())) {
					if (in.getTblDesignId() != null && in.getTblDesignId().equals(out.getTblDesignId()) && in.getColumnId() == null && BusinessDesignConst.DataType.BYTE.equals(out.getDataType())) {
						isSetting = true;
					}
				} else {
					if(in.getColumnId() != null && in.getColumnId().equals(out.getColumnId())) {
						isSetting = true;
					}
				}
				if (isSetting) {
					if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(in.getDataType())) {
						List<InputBean> childInputBeans = getChildInputBean(inputbeansRegisted, in.getInputBeanId());
						for (InputBean child : childInputBeans) {
							if (child.getDataType().equals(out.getDataType())) {
								assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_INPUT_BEAN, child.getInputBeanId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OUTPUT_BEAN, out.getOutputBeanId(), assignIdTemp, child, out, null, FROM_INPUT_TO_OUTPUT);
								break;
							}
						}
					} else {
						assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_INPUT_BEAN, in.getInputBeanId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OUTPUT_BEAN, out.getOutputBeanId(), assignIdTemp, in, out, null, FROM_INPUT_TO_OUTPUT);
						if(flagUndoBlogic) {
							TableDesignDetails column = mapColumns.get(in.getColumnId());
							if (column != null && isArrayForCheckbox(column)) {
								createFormulaDefinitionOfUndoBlogic(assignDetail, in, common);
							}
						}
					}
					lstAssignDetails.add(assignDetail);
				}
			}
		}
	}
	
	private void contructAssignDetailFromInputBeanToOutputBeanForRedirectConfirm(ModuleTableMapping table, List<InputBean> inputbeansRegisted, List<OutputBean> outputbeansRegisted, Long assignIdTemp, CommonModel common) {
		List<InputBean> inputbeansOfThisTable = new ArrayList<InputBean>();
		List<OutputBean> outputbeansOfThisTable = new ArrayList<OutputBean>();
		List<Long> lstColumnIds = new ArrayList<Long>();
		AssignDetail assignDetail = new AssignDetail();
		for (InputBean in : inputbeansRegisted) {
			if (table.getTblDesignId().equals(in.getTblDesignId())) {
				inputbeansOfThisTable.add(in);
				lstColumnIds.add(in.getColumnId());
			}
		}
		for (OutputBean out : outputbeansRegisted) {
			if (table.getTblDesignId().equals(out.getTblDesignId())) {
				outputbeansOfThisTable.add(out);
			}
		}
		
		List<TableDesignDetails> lstColumns = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIds);
		Map<Long, TableDesignDetails> mapColumns = new HashMap<Long, TableDesignDetails>();
		if (FunctionCommon.isNotEmpty(lstColumns)) {
			for (TableDesignDetails column : lstColumns) {
				mapColumns.put(column.getColumnId(), column);
			}
		}
		
		for (InputBean in : inputbeansOfThisTable) {
			TableDesignDetails column = mapColumns.get(in.getColumnId());
			for (OutputBean out : outputbeansOfThisTable) {
				boolean isSetting = false;
				if(BusinessDesignConst.DataType.OBJECT.equals(in.getDataType()) || BusinessDesignConst.DataType.ENTITY.equals(in.getDataType())) {
					if (in.getTblDesignId() != null && in.getTblDesignId().equals(out.getTblDesignId()) && in.getColumnId() == null && out.getColumnId() == null) {
						isSetting = true;
					}
				} else if(BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(in.getDataType())) {
					if (in.getTblDesignId() != null && in.getTblDesignId().equals(out.getTblDesignId()) && in.getColumnId() == null && BusinessDesignConst.DataType.BYTE.equals(out.getDataType())) {
						isSetting = true;
					}
				} else {
					if(in.getColumnId() != null && in.getColumnId().equals(out.getColumnId())) {
						isSetting = true;
					}
				}
				if (isSetting) {
					if (BusinessDesignConst.DataType.EXTERNAL_OBJECT.equals(in.getDataType())) {
						List<InputBean> childInputBeans = getChildInputBean(inputbeansRegisted, in.getInputBeanId());
						for (InputBean child : childInputBeans) {
							if (child.getDataType().equals(out.getDataType())) {
								assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_INPUT_BEAN, child.getInputBeanId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OUTPUT_BEAN, out.getOutputBeanId(), assignIdTemp, child, out, null, FROM_INPUT_TO_OUTPUT);
								break;
							}
						}
					} else {
						assignDetail = GenerateScreenContruct.setAssignDetail(BusinessDesignConst.AssignDetailComponent.PARAMETER_SCOPE_INPUT_BEAN, in.getInputBeanId(), BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OUTPUT_BEAN, out.getOutputBeanId(), assignIdTemp, in, out, null, FROM_INPUT_TO_OUTPUT);
						if (column != null && isArrayForCheckbox(column)) {
							this.createFormulaDefinitionAssignNode(assignDetail, in, "", common);
						}
					}
					lstAssignDetails.add(assignDetail);
				}
			}
		}
	}

	/**
	 * @param talbe
	 * @param lstInputBean
	 * @param lstObjDef
	 * @param checkDetailId
	 * @return
	 */
	private List<BusinessDetailContent> contructBusinessDetailContent(ScreenDesign screen, ModuleTableMapping talbe, List<InputBean> lstInputBean, Long checkDetailId, Integer checkType, BusinessDesign businessDesign) {
		List<BusinessDetailContent> businessDetailContentsTemp = new ArrayList<BusinessDetailContent>();
		List<InputBean> inputbeansInTable = new ArrayList<InputBean>();
		List<InputBean> listInputBeanInformation = new ArrayList<InputBean>();
		BusinessDetailContent detailContent;
		List<TableDesignKeyItem> uniquekeysInTable = new ArrayList<TableDesignKeyItem>();
		List<TableDesignKeyItem> primarykeysInTable = new ArrayList<TableDesignKeyItem>();
		List<Long> lstAllColumnId = new ArrayList<Long>();
		List<TableDesignDetails> lstAllColumn = new ArrayList<TableDesignDetails>();
		Map<Long, TableDesignDetails> mapColumnMapping = new HashMap<Long, TableDesignDetails>();
		
		if (lstInputBean != null && lstInputBean.size() > 0) {
			for (InputBean in : lstInputBean) {
				if (talbe.getTblDesignId().equals(in.getTblDesignId())) {
					inputbeansInTable.add(in);
				}
			}
		}
		if (inputbeansInTable.size() > 0) {
			listInputBeanInformation = businessDesignRepository.findColumnInformationOfInputBeans(inputbeansInTable);
		}
		for (TableDesignKeyItem key : allKeys) {
			if (key.getTableDesignId().equals(talbe.getTblDesignId()) && DbDomainConst.TblDesignKeyType.PK.equals(key.getType())) {
				primarykeysInTable.add(key);
			}
			if (key.getTableDesignId().equals(talbe.getTblDesignId()) && DbDomainConst.TblDesignKeyType.UNIQUE.equals(key.getType())) {
				uniquekeysInTable.add(key);
			}
		}
		for (InputBean in : lstInputBean) {
			lstAllColumnId.add(in.getColumnId());
		}
		lstAllColumn = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstAllColumnId);
		if (lstAllColumn != null) {
			for (InputBean in : lstInputBean) {
				for (TableDesignDetails column : lstAllColumn) {
					if (column.getColumnId().equals(in.getColumnId())) {
						mapColumnMapping.put(in.getColumnId(), column);
					}
				}
			}
		}
		if (checkType != null && BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_DUPLICATED.equals(checkType)) {
			Map<String, Long> mapDetailContents = new HashMap<String, Long>();

			//			if (!ScreenDesignConst.MODIFY_SCREEN.equals(screen.getScreenTypeName())) {
			if (primarykeysInTable.size() > 0) {
				for (InputBean in : listInputBeanInformation) {
					TableDesignDetails column = mapColumnMapping.get(in.getColumnId());
					for (TableDesignKeyItem key : primarykeysInTable) {
						if (column != null && column.getColumnId().equals(key.getColumnId())) {
							// Non auto increment: check
							// Auto increment and used: check
							// Auto increment and not used: no check
							if (!(BusinessDesignConst.BusinessCheckComponent.AUTO_INCREMENT.equals(column.getAutoIncrementFlag()))
									|| (BusinessDesignConst.BusinessCheckComponent.AUTO_INCREMENT.equals(column.getAutoIncrementFlag()) && DbDomainConst.DisplayType.USED.equals(column.getDisplayType()))){
								detailContent = GenerateScreenContruct.setBusinessDetailContent(in, checkDetailId);

								if (businessDesign != null) {
									if ((BusinessDesignConst.SCREEN_PATTERN_MODIFY.equals(businessDesign.getPatternType()) || BusinessDesignConst.SCREEN_PATTERN_CONFIRM_MODIFY.equals(businessDesign.getPatternType()))
											&& BusinessDesignConst.RETURN_TYPE_SCREEN.equals(businessDesign.getReturnType())) {
										detailContent.setOperatorCode(GenerateSourceCodeConst.OperatorType.OPERATOR_EQUAL);
									}
								}
								businessDetailContentsTemp.add(detailContent);
								mapDetailContents.put(in.getInputBeanId(), in.getColumnId());
							}
						}
					}
				}
			}
			//			}

			if (uniquekeysInTable.size() > 0) {
				for (InputBean in : listInputBeanInformation) {
					TableDesignDetails column = mapColumnMapping.get(in.getColumnId());
					for (TableDesignKeyItem key : uniquekeysInTable) {
						if (column != null && column.getColumnId().equals(key.getColumnId()) && DbDomainConst.DisplayType.USED.equals(column.getDisplayType())) {
							if(!mapDetailContents.containsKey(in.getInputBeanId())){
								detailContent = GenerateScreenContruct.setBusinessDetailContent(in, checkDetailId);

								if (businessDesign != null) {
									if ((businessDesign.getPatternType() == BusinessDesignConst.SCREEN_PATTERN_MODIFY || businessDesign.getPatternType() == BusinessDesignConst.SCREEN_PATTERN_CONFIRM_MODIFY)
											&& businessDesign.getReturnType() == BusinessDesignConst.RETURN_TYPE_SCREEN) {
										detailContent.setOperatorCode(GenerateSourceCodeConst.OperatorType.OPERATOR_EQUAL);
									}
								}

								businessDetailContentsTemp.add(detailContent);
							}
						}
					}
				}
			}
			// QuangVD : not support this case
			//			else {
			//				if (listInputBeanInformation != null && listInputBeanInformation.size() > 0) {
			//					InputBean in = listInputBeanInformation.get(0);
			//					detailContent = GenerateScreenContruct.setBusinessDetailContent(in, checkDetailId);
			//					lstDetailsContents.add(detailContent);
			//				}
			//			}
		} else if (BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE.equals(checkType)) {
			Map<String, Long> mapDetailContents = new HashMap<String, Long>();
			if (primarykeysInTable.size() > 0) {
				for (InputBean in : listInputBeanInformation) {
					for (TableDesignKeyItem primarykey : primarykeysInTable) {
//						if (in.getColumnId() != null && in.getColumnId().equals(primarykey.getColumnId()) && DbDomainConst.DisplayType.USED.equals(in.getDisplayType())) {
						if (in.getColumnId() != null && in.getColumnId().equals(primarykey.getColumnId())) {
							detailContent = GenerateScreenContruct.setBusinessDetailContent(in, checkDetailId);
							businessDetailContentsTemp.add(detailContent);
							mapDetailContents.put(in.getInputBeanId(), in.getColumnId());
						}
					}
				}
			}
			else if (uniquekeysInTable.size() > 0) {
				for (InputBean in : listInputBeanInformation) {
					for (TableDesignKeyItem uniquekey : uniquekeysInTable) {
//						if (in.getColumnId() != null && in.getColumnId().equals(uniquekey.getColumnId()) && DbDomainConst.DisplayType.USED.equals(in.getDisplayType())) {
						if (in.getColumnId() != null && in.getColumnId().equals(uniquekey.getColumnId())) {
							if(!mapDetailContents.containsKey(in.getInputBeanId())){
								detailContent = GenerateScreenContruct.setBusinessDetailContent(in, checkDetailId);
								businessDetailContentsTemp.add(detailContent);
							}
						}
					}
				}
			}
			// QuangVD : not support this case
			//			else {
			//				if (listInputBeanInformation != null && listInputBeanInformation.size() > 0) {
			//					InputBean in = listInputBeanInformation.get(0);
			//					detailContent = GenerateScreenContruct.setBusinessDetailContent(in, checkDetailId);
			//					lstDetailsContents.add(detailContent);
			//				}
			//			}
		}

		return businessDetailContentsTemp;
	}
	
	/**
	 * 
	 * @param screen
	 * @param talbe
	 * @param lstObjectDefinition
	 * @param checkDetailId
	 * @param checkType
	 * @param businessDesign
	 * @return
	 */
	private List<BusinessDetailContent> contructBusinessDetailContentByOb(ScreenDesign screen, ModuleTableMapping talbe, List<ObjectDefinition> lstObjectDefinition, Long checkDetailId, Integer checkType, BusinessDesign businessDesign) {
		List<BusinessDetailContent> businessDetailContentsTemp = new ArrayList<BusinessDetailContent>();
		BusinessDetailContent detailContent;
		List<TableDesignKeyItem> uniquekeysInTable = new ArrayList<TableDesignKeyItem>();
		List<TableDesignKeyItem> primarykeysInTable = new ArrayList<TableDesignKeyItem>();
		List<Long> lstAllColumnId = new ArrayList<Long>();
		List<TableDesignDetails> lstAllColumn = new ArrayList<TableDesignDetails>();
		Map<Long, TableDesignDetails> mapColumnMapping = new HashMap<Long, TableDesignDetails>();
		
		for (TableDesignKeyItem key : allKeys) {
			if (key.getTableDesignId().equals(talbe.getTblDesignId()) && DbDomainConst.TblDesignKeyType.PK.equals(key.getType())) {
				primarykeysInTable.add(key);
			}
			if (key.getTableDesignId().equals(talbe.getTblDesignId()) && DbDomainConst.TblDesignKeyType.UNIQUE.equals(key.getType())) {
				uniquekeysInTable.add(key);
			}
		}
		if (lstObjectDefinition != null) {
			List<ObjectDefinition> ObjectDefinitionsInTable = new ArrayList<ObjectDefinition>();
			List<ObjectDefinition> listObjectDefinitionInformation = new ArrayList<ObjectDefinition>();
			for (ObjectDefinition ob : lstObjectDefinition) {
				lstAllColumnId.add(ob.getColumnId());
			}
			lstAllColumn = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstAllColumnId);
			if (lstAllColumn != null) {
				for (ObjectDefinition ob : lstObjectDefinition) {
					for (TableDesignDetails column : lstAllColumn) {
						if (column.getColumnId().equals(ob.getColumnId())) {
							mapColumnMapping.put(ob.getColumnId(), column);
						}
					}
				}
			}
			
			for (ObjectDefinition ob : lstObjectDefinition) {
				if (talbe.getTblDesignId().equals(ob.getTblDesignId())) {
					ObjectDefinitionsInTable.add(ob);
				}
			}
			listObjectDefinitionInformation = businessDesignRepository.findColumnInformationOfObjectDefition(ObjectDefinitionsInTable);
			if (checkType != null && BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_DUPLICATED.equals(checkType)) {
				Map<String, Long> mapDetailContents = new HashMap<String, Long>();

				if (primarykeysInTable.size() > 0) {
					for (ObjectDefinition ob : listObjectDefinitionInformation) {
						TableDesignDetails column = mapColumnMapping.get(ob.getColumnId());
						for (TableDesignKeyItem key : primarykeysInTable) {
							if (column != null && column.getColumnId().equals(key.getColumnId())) {
								// Non auto increment: check
								// Auto increment and used: check
								// Auto increment and not used: no check
								if (!(BusinessDesignConst.BusinessCheckComponent.AUTO_INCREMENT.equals(column.getAutoIncrementFlag()))
										|| (BusinessDesignConst.BusinessCheckComponent.AUTO_INCREMENT.equals(column.getAutoIncrementFlag()) && DbDomainConst.DisplayType.USED.equals(column.getDisplayType()))){
									detailContent = GenerateScreenContruct.setBusinessDetailContentByOb(ob, checkDetailId);

									if (businessDesign != null) {
										if ((BusinessDesignConst.SCREEN_PATTERN_MODIFY.equals(businessDesign.getPatternType()) || BusinessDesignConst.SCREEN_PATTERN_CONFIRM_MODIFY.equals(businessDesign.getPatternType()))
												&& BusinessDesignConst.RETURN_TYPE_SCREEN.equals(businessDesign.getReturnType())) {
											detailContent.setOperatorCode(GenerateSourceCodeConst.OperatorType.OPERATOR_EQUAL);
										}
									}

									businessDetailContentsTemp.add(detailContent);
									mapDetailContents.put(ob.getObjectDefinitionId(), ob.getColumnId());
								}
							}
						}
					}
				}

				if (uniquekeysInTable.size() > 0) {
					for (ObjectDefinition ob : listObjectDefinitionInformation) {
						TableDesignDetails column = mapColumnMapping.get(ob.getColumnId());
						for (TableDesignKeyItem key : uniquekeysInTable) {
							if (column != null && column.getColumnId().equals(key.getColumnId()) && DbDomainConst.DisplayType.USED.equals(column.getDisplayType())) {
								if(!mapDetailContents.containsKey(ob.getObjectDefinitionId())){
									detailContent = GenerateScreenContruct.setBusinessDetailContentByOb(ob, checkDetailId);

									if (businessDesign != null) {
										if ((BusinessDesignConst.SCREEN_PATTERN_MODIFY.equals(businessDesign.getPatternType()) || BusinessDesignConst.SCREEN_PATTERN_CONFIRM_MODIFY.equals(businessDesign.getPatternType()))
												&& BusinessDesignConst.RETURN_TYPE_SCREEN.equals(businessDesign.getReturnType())) {
											detailContent.setOperatorCode(GenerateSourceCodeConst.OperatorType.OPERATOR_EQUAL);
										}
									}

									businessDetailContentsTemp.add(detailContent);
								}
							}
						}
					}
				}
				// QuangVD : not support this case
				//			else {
				//				if (listObjectDefinitionInformation != null && listObjectDefinitionInformation.size() > 0) {
				//					ObjectDefinition in = listObjectDefinitionInformation.get(0);
				//					detailContent = GenerateScreenContruct.setBusinessDetailContentByOb(in, checkDetailId);
				//					lstDetailsContents.add(detailContent);
				//				}
				//			}
			} else if (BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE.equals(checkType)) {
				Map<String, Long> mapDetailContents = new HashMap<String, Long>();
				if (primarykeysInTable.size() > 0) {
					for (ObjectDefinition in : listObjectDefinitionInformation) {
						for (TableDesignKeyItem primarykey : primarykeysInTable) {
							if (in.getColumnId() != null && in.getColumnId().equals(primarykey.getColumnId()) && DbDomainConst.DisplayType.USED.equals(in.getDisplayType())) {
								detailContent = GenerateScreenContruct.setBusinessDetailContentByOb(in, checkDetailId);
								businessDetailContentsTemp.add(detailContent);
								mapDetailContents.put(in.getObjectDefinitionId(), in.getColumnId());
							}
						}
					}
				}
				if (uniquekeysInTable.size() > 0) {
					for (ObjectDefinition in : listObjectDefinitionInformation) {
						for (TableDesignKeyItem uniquekey : uniquekeysInTable) {
							if (in.getColumnId() != null && in.getColumnId().equals(uniquekey.getColumnId()) && DbDomainConst.DisplayType.USED.equals(in.getDisplayType())) {
								if(!mapDetailContents.containsKey(in.getObjectDefinitionId())){
									detailContent = GenerateScreenContruct.setBusinessDetailContentByOb(in, checkDetailId);
									businessDetailContentsTemp.add(detailContent);
								}
							}
						}
					}
				}
				// QuangVD : not support this case
				//				else {
				//					if (listObjectDefinitionInformation != null && listObjectDefinitionInformation.size() > 0) {
				//						ObjectDefinition in = listObjectDefinitionInformation.get(0);
				//						detailContent = GenerateScreenContruct.setBusinessDetailContentByOb(in, checkDetailId);
				//						lstDetailsContents.add(detailContent);
				//					}
				//				}
			}
		}
		return businessDetailContentsTemp;
	}
	/**
	 * @param screenDesignDefault
	 * @param sqlDesignName
	 * @param sqlpattern
	 * @param table
	 * @param inputBeansRegisted
	 * @param objDefsRegisted
	 * @return
	 */
	private SqlDesign autoGenerateSqlbuilder(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, String sqlDesignName, String sqlDesignCode, int sqlpattern, ModuleTableMapping table, List<InputBean> inputBeansRegisted, List<ObjectDefinition> objDefsRegisted, List<OutputBean> outputBeansRegisted,  Boolean flgHaveCondition, Boolean flgHaveResult, Boolean flgSearchScreen, Boolean isProcessSearch, Boolean isGetTotal, Boolean initialSearch, Boolean flgBlogicProcessRegister) {
		Integer itemSeqNo = 1;
		Long tempSqlDesignId = 1L;
		List<Long> lstColumnIdOfThisTable = new ArrayList<Long>();
		List<SqlDesignTable> lstSqlDesignTable = new ArrayList<SqlDesignTable>();
		List<SqlDesignTableItem> lstSqlDesignTableItem = new ArrayList<SqlDesignTableItem>();
		List<SqlDesignInput> lstSqlDesignInput = new ArrayList<SqlDesignInput>();
		List<SqlDesignValue> lstSqlDesignValue = new ArrayList<SqlDesignValue>();
		List<SqlDesignCondition> lstCondition = new ArrayList<SqlDesignCondition>();
		List<SqlDesignResult> lstSqlDesignResult = new ArrayList<SqlDesignResult>();
		List<SqlDesignOutput> lstSqlDesignOutput = new ArrayList<SqlDesignOutput>();
		List<TableDesignDetails> lstData = new ArrayList<TableDesignDetails>();
		List<SqlDesignInput> allSqlDesignInputs = new ArrayList<SqlDesignInput>();
		List<SqlDesignOutput> allSqlDesignOutputs = new ArrayList<SqlDesignOutput>();
		SqlDesign sqlDesign = null;
		int countPK = 0;
		if(table != null) {
			List<TableDesignDetails> lstPK = findPKOfTable(table);
			if(FunctionCommon.isNotEmpty(lstPK)) {
				countPK = lstPK.size();
			}
		}

		if (!flgSearchScreen) {
			ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings() != null ? screenDesignDefault.getModuleTableMappings()[0] : null;
			// TrungDV : gen Advance SQL for insert first table to return Primary key in case : master - details
			Boolean flgGenAdvanceInsert = false;
			Long sqlDesignId = null;
			if (flgBlogicProcessRegister != null && flgBlogicProcessRegister) {
				boolean flgHavePKHidden = false;
				for(TableDesignDetails column : firstTable.getListTableDesignDetail()) {
					if (DbDomainConst.YesNoFlg.YES.equals(column.isKey(DbDomainConst.TblDesignKeyType.PK)) && DbDomainConst.DisplayType.UNUSED.equals(column.getDisplayType())
							&& DbDomainConst.YesNoFlg.YES.equals(column.getAutoIncrementFlag())
							) {
						flgHavePKHidden = true;
						break;
					}
				}
				if (flgHavePKHidden && screenDesignDefault.getModuleTableMappings().length > 1 && firstTable != null && table != null && table.getTblDesignId().equals(firstTable.getTblDesignId()) && SINGLE_TYPE.equals(firstTable.getTableMappingType())) {
					flgGenAdvanceInsert = true;
				}
			}
			if(!flgGenAdvanceInsert) {
				sqlDesign = GenerateScreenContruct.populateSqlDesign(DomainDatatypeConst.SQLDesignType.SQL_BUILDER, sqlDesignName, sqlDesignCode, sqlpattern, screenDesignDefault, screenDesignObj, table, isGetTotal, 0);
				sqlDesignRepository.register(sqlDesign);
				sqlDesignId = sqlDesign.getSqlDesignId();
			} else {
				sqlDesign = GenerateScreenContruct.populateSqlDesign(DomainDatatypeConst.SQLDesignType.ADVANCED_SQL, sqlDesignName, sqlDesignCode, sqlpattern, screenDesignDefault, screenDesignObj, table, false, null);
			}
			// end by TrungDV

			if (SqlPattern.INSERT == sqlpattern || SqlPattern.UPDATE == sqlpattern) {
				if (objDefsRegisted != null && objDefsRegisted.size() > 0) {
					if(!flgGenAdvanceInsert) {
						// register sql design table
						SqlDesignTable sqlTable = GenerateScreenContruct.setSqlDesignTable(sqlDesignId, null, null, table.getTblDesignId(), null, 0, table.getTblDesignName(), null, table.getTblDesignCode(), null);
						sqlDesignTableRepository.register(sqlTable);

						for (ObjectDefinition objDef : objDefsRegisted) {
							if (objDef.getTblDesignId() != null && objDef.getTblDesignId().equals(table.getTblDesignId())) {
								lstColumnIdOfThisTable.add(objDef.getColumnId());
							}
						}
						if (lstColumnIdOfThisTable.size() > 0) {
							lstData = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIdOfThisTable);
						}
						if (lstData.size() > 0) {
							boolean flagLogicCode = false;
							if (SqlPattern.INSERT == sqlpattern) {
								// sql design input entity
								Integer arrayFlg = table.getTableMappingType() != null ? table.getTableMappingType() : 0;
								SqlDesignInput sqlInputParent = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, table.getTblDesignName(), table.getTblDesignCode(), null, itemSeqNo, null, table.getTblDesignId(), true, null, true, arrayFlg, DbDomainConst.DesignType.DESIGN_TYPE, DbDomainConst.ObjectType.ENTITY_OBJECT, true);
								lstSqlDesignInput.add(sqlInputParent);
								allSqlDesignInputs.add(sqlInputParent);
								Long parentSqlDesignInputId = sqlInputParent.getSqlDesignInputId();
								Integer fixGroupIndex= 1;
								Integer imcrement = 0;
								itemSeqNo++;
								for (TableDesignDetails ob : lstData) {
									if (ob.getDataType() != null && !DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType())) {
										imcrement = imcrement + 1;
										String strGroupIndex = fixGroupIndex.toString() + DOT + imcrement.toString();
										// sql design input
										SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, ob.getName(), ob.getCode(), ob.getBaseType(), itemSeqNo, ob.getColumnId(), ob.getTableDesignId(), false, parentSqlDesignInputId, true, null, DbDomainConst.DesignType.REFERENCE_TYPE, DbDomainConst.ObjectType.ENTITY_ATTRIBUTE, true);
										lstSqlDesignInput.add(sqlInput);
										allSqlDesignInputs.add(sqlInput);

										if (DbDomainConst.DisplayType.USED.equals(ob.getDisplayType()) || countPK > 1) {
											// sql design value
											SqlDesignValue sqlValue = GenerateScreenContruct.setSqlDesignValue(sqlDesignId, itemSeqNo, ob.getColumnId(), ob.getName(), strGroupIndex.trim(), ob.getBaseType());
											lstSqlDesignValue.add(sqlValue);
										}
										itemSeqNo++;
									}
								}
							} else if (SqlPattern.UPDATE == sqlpattern) {
								List<ScreenItem> pkOfTable = findKeyOfTableUsingScreenItems(table, screenDesignObj, screenDesignDefault);
								Set<Long> pkColumnIds = new HashSet<Long>();
								for (ScreenItem item : pkOfTable) {
									pkColumnIds.add(item.getColumnId());
								}
								// sql design input entity
								Integer arrayFlg = table.getTableMappingType() != null ? table.getTableMappingType() : 0;
								
								String parentSqlInputCode ;
								Integer objectTypeParent, objectTypeChild, designTypeChild;
								if(firstTable.getTblDesignId().equals(table.getTblDesignId())) {
									objectTypeParent = DbDomainConst.ObjectType.OBJECT_OBJECT;
									objectTypeChild = DbDomainConst.ObjectType.OBJECT_ATTRIBUTE;
									designTypeChild = DbDomainConst.DesignType.DESIGN_TYPE;
									parentSqlInputCode = table.getTblDesignCode() + GenerateUniqueKey.generateRandomInteger(); 
								} else {
									objectTypeParent = DbDomainConst.ObjectType.ENTITY_OBJECT;
									objectTypeChild = DbDomainConst.ObjectType.ENTITY_ATTRIBUTE;
									designTypeChild = DbDomainConst.DesignType.REFERENCE_TYPE;
									parentSqlInputCode = table.getTblDesignCode();
								}
								SqlDesignInput sqlInputParent = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, table.getTblDesignName(), parentSqlInputCode, null, itemSeqNo, null, table.getTblDesignId(), true, null, true, arrayFlg, DbDomainConst.DesignType.DESIGN_TYPE, objectTypeParent, true);
								lstSqlDesignInput.add(sqlInputParent);
								allSqlDesignInputs.add(sqlInputParent);
								Integer fixGroupIndex = 1;
								Integer imcrement = 0;
								Long parentSqlDesignInputId = sqlInputParent.getSqlDesignInputId();
								
								itemSeqNo++;
								for (TableDesignDetails ob : lstData) {
									if (ob.getDataType() != null && !DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType())) {
										if (pkColumnIds.contains(ob.getColumnId())) {
											imcrement = imcrement + 1;
											String strGroupIndex = fixGroupIndex.toString() + DOT + imcrement.toString();
											// sql design input
											SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, ob.getName(), ob.getCode(), ob.getBaseType(), itemSeqNo, ob.getColumnId(), ob.getTableDesignId(), false, parentSqlDesignInputId, true, null, designTypeChild, objectTypeChild, true);
											lstSqlDesignInput.add(sqlInput);
											allSqlDesignInputs.add(sqlInput);
											// sql design condition
											SqlDesignCondition condition = GenerateScreenContruct.setSqlDesignCondition(sqlDesignId, ob.getTableDesignId(), ob.getColumnId(), "0", itemSeqNo, ob.getTableDesignName(), ob.getName(), ob.getBaseType(), strGroupIndex.trim());
											if (!flagLogicCode) {
												condition.setLogicCode("");
												flagLogicCode = true;
											} else {
												condition.setLogicCode("0");
											}
											lstCondition.add(condition);
											// TRUNGDV : FIX CODE, MUST BE DELETE THIS CODE
											// sql design value
//											if(screenDesignDefault.getModuleTableMappings().length > 1) {
//												SqlDesignValue sqlValue = GenerateScreenContruct.setSqlDesignValue(sqlDesignId, itemSeqNo, ob.getColumnId(), ob.getName(), strGroupIndex.trim(), ob.getBaseType());
//												lstSqlDesignValue.add(sqlValue);
//											}
											// TRUNGDV : END THIS CODE
											itemSeqNo++;
										}
									}
								}
								for (TableDesignDetails col : lstData) {
									if (col.getDataType() != null && !DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(col.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(col.getDataType())) {
										// check concurrency of first table
										if(UPDATED_DATE.equalsIgnoreCase(col.getCode()) && table.getTblDesignId().equals(screenDesignDefault.getModuleTableMappings()[0].getTblDesignId())) {
											imcrement = imcrement + 1;
											String strGroupIndex = fixGroupIndex.toString() + DOT + imcrement.toString();
											// sql design input SYSTEM DATE to set VALUE
											SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, SYSTEM_DATE_NAME, SYSTEM_DATE, col.getBaseType(), itemSeqNo, col.getColumnId(), col.getTableDesignId(), false, parentSqlDesignInputId, true, null, designTypeChild, objectTypeChild, true);
											lstSqlDesignInput.add(sqlInput);
											allSqlDesignInputs.add(sqlInput);
											// sql design value
											SqlDesignValue sqlValue = GenerateScreenContruct.setSqlDesignValue(sqlDesignId, itemSeqNo, col.getColumnId(), col.getName(), strGroupIndex.trim(), col.getBaseType());
											lstSqlDesignValue.add(sqlValue);
											itemSeqNo++;
											
											imcrement = imcrement + 1;
											strGroupIndex = fixGroupIndex.toString() + DOT + imcrement.toString();
											// sql design input UPDATED DATE to WHERE condition
											SqlDesignInput sqlInputUpdateDate = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, col.getName(), col.getCode(), col.getBaseType(), itemSeqNo, col.getColumnId(), col.getTableDesignId(), false, parentSqlDesignInputId, true, null, designTypeChild, objectTypeChild, true);
											lstSqlDesignInput.add(sqlInputUpdateDate);
											allSqlDesignInputs.add(sqlInputUpdateDate);
											
											// sql design condition
											SqlDesignCondition condition = GenerateScreenContruct.setSqlDesignCondition(sqlDesignId, col.getTableDesignId(), col.getColumnId(), "0", itemSeqNo, col.getTableDesignName(), col.getName(), col.getBaseType(), strGroupIndex.trim());
											if (!flagLogicCode) {
												condition.setLogicCode("");
												flagLogicCode = true;
											} else {
												condition.setLogicCode("0");
											}
											lstCondition.add(condition);
											itemSeqNo++;
										} else {
											if (!pkColumnIds.contains(col.getColumnId())) {
												imcrement = imcrement + 1;
												String strGroupIndex = fixGroupIndex.toString() + DOT + imcrement.toString();
												// sql design input
												SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, col.getName(), col.getCode(), col.getBaseType(), itemSeqNo, col.getColumnId(), col.getTableDesignId(), false, parentSqlDesignInputId, true, null, designTypeChild, objectTypeChild, true);
												lstSqlDesignInput.add(sqlInput);
												allSqlDesignInputs.add(sqlInput);
												// sql design value
												SqlDesignValue sqlValue = GenerateScreenContruct.setSqlDesignValue(sqlDesignId, itemSeqNo, col.getColumnId(), col.getName(), strGroupIndex.trim(), col.getBaseType());
												lstSqlDesignValue.add(sqlValue);
												itemSeqNo++;
											}
										}
									}
								}
							}
						}
					} else {
						// HungNLK : gen Advance SQL of case Insert first table and return Primary key (only for case Master - Details)
						for (ObjectDefinition objDef : objDefsRegisted) {
							if (objDef.getTblDesignId() != null && objDef.getTblDesignId().equals(table.getTblDesignId())) {
								lstColumnIdOfThisTable.add(objDef.getColumnId());
							}
						}
						if (lstColumnIdOfThisTable.size() > 0) {
							lstData = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIdOfThisTable);
						}
						if (lstData.size() > 0) {
							if (SqlPattern.INSERT == sqlpattern) {
								List<TableDesignDetails> tableDetails = findPKOfTable(firstTable);
								TableDesignDetails tableDetail = null;

								for (TableDesignDetails column : tableDetails) {
									if (DbDomainConst.YesNoFlg.YES.equals(column.getAutoIncrementFlag()) && DbDomainConst.DisplayType.UNUSED.equals(column.getDisplayType())) {
										tableDetail = column;
										break;
									}
								}

								StringBuilder builder = new StringBuilder();
								StringBuilder builderInsertField = new StringBuilder();
								StringBuilder builderValueField = new StringBuilder();

								if (tableDetail != null) {
									builder.append(MessageFormat.format("<selectKey keyProperty=\"{0}.{1}\" resultType=\"Long\" order=\"BEFORE\">", tableDetail.getTableDesignCode(), tableDetail.getCode()));
									builder.append(DOWNLINE);
									builder.append(TAB);
									if (DbDomainConst.DatabaseType.ORACLE.equals(screenDesignDefault.getProject().getDbType())) {
										builder.append(MessageFormat.format("SELECT {0}.nextval FROM dual", tableDetail.getSeqCode()));
									} else {
										builder.append(MessageFormat.format("SELECT NEXTVAL(''{0}''::REGCLASS)", tableDetail.getSeqCode()));
									}
									builder.append(DOWNLINE); 
									builder.append("</selectKey>");
									builder.append(DOWNLINE);
								}
								builder.append("INSERT INTO");
								builder.append(MessageFormat.format(" {0} (", firstTable.getTblDesignCode()));

								for (TableDesignDetails ob : lstData) {
									if(builderInsertField.length() > 0){
										builderInsertField.append(COMMA);
									}
									builderInsertField.append(DOWNLINE);
									builderInsertField.append(TAB);
									builderInsertField.append(ob.getCode());
								}

								builder.append(builderInsertField);
								builder.append(DOWNLINE);
								builder.append(") VALUES (");

								for (TableDesignDetails ob : lstData) {
									if(builderValueField.length() > 0){
										builderValueField.append(COMMA);
									}
									builderValueField.append(DOWNLINE);
									builderValueField.append(TAB);
									builderValueField.append("#{");
									builderValueField.append(ob.getTableDesignCode());
									builderValueField.append(DOT);
									builderValueField.append(ob.getCode());
									builderValueField.append("}");
								}

								builder.append(builderValueField);
								builder.append(DOWNLINE);
								builder.append(")");

								sqlDesign.setSqlText(builder.toString());
								sqlDesignRepository.register(sqlDesign);
								sqlDesignId = sqlDesign.getSqlDesignId();

								// sql design input entity
								Integer arrayFlg = table.getTableMappingType() != null ? table.getTableMappingType() : 0;
								SqlDesignInput sqlInputParent = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, table.getTblDesignName(), table.getTblDesignCode(), null, itemSeqNo, null, table.getTblDesignId(), true, null, true, arrayFlg, DbDomainConst.DesignType.DESIGN_TYPE, DbDomainConst.ObjectType.ENTITY_OBJECT, true);
								lstSqlDesignInput.add(sqlInputParent);
								allSqlDesignInputs.add(sqlInputParent);
								Long parentSqlDesignInputId = sqlInputParent.getSqlDesignInputId();
								itemSeqNo++;

								for (TableDesignDetails ob : lstData) {
									if (ob.getDataType() != null && !DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType())) {
										// sql design input
										SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, ob.getName(), ob.getCode(), ob.getBaseType(), itemSeqNo, ob.getColumnId(), ob.getTableDesignId(), false, parentSqlDesignInputId, true, null, DbDomainConst.DesignType.REFERENCE_TYPE, DbDomainConst.ObjectType.ENTITY_ATTRIBUTE, true);
										lstSqlDesignInput.add(sqlInput);
										allSqlDesignInputs.add(sqlInput);
										itemSeqNo++;
									}
								}
							}
						}
					}
				}
			} else if (SqlPattern.SELECT == sqlpattern) {
				if (inputBeansRegisted != null && inputBeansRegisted.size() > 0) {
					List<Long> lstAllColumnIdOfInputbeans = new ArrayList<Long>();
					for (InputBean in : inputBeansRegisted) {
						if (in.getTblDesignId() != null && in.getTblDesignId().equals(table.getTblDesignId())) {
							lstColumnIdOfThisTable.add(in.getColumnId());
						}
						lstAllColumnIdOfInputbeans.add(in.getColumnId());
					}
					List<TableDesignForeignKey> fkConstrains = new ArrayList<TableDesignForeignKey>();
					// is first table
					if (lstColumnIdOfThisTable.size() > 0) {
						lstData = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIdOfThisTable);
						// only register sql design table
						SqlDesignTable sqlTable = GenerateScreenContruct.setSqlDesignTable(sqlDesignId, null, null, table.getTblDesignId(), null, 0, table.getTblDesignName(), null, table.getTblDesignCode(), null);
						lstSqlDesignTable.add(sqlTable);
					}
					// is children table
					else {
						if (stillHaveConstrainFlag) {
							int indexOfCurrentTable = 0;
							for (int i = 0; i < screenDesignDefault.getModuleTableMappings().length; i++) {
								if (screenDesignDefault.getModuleTableMappings()[i].getTblDesignId().equals(table.getTblDesignId())) {
									indexOfCurrentTable = i;
								}
							}
							boolean haveConstrainBetweenTwoTable = false;
							outerloop: for (int i = indexOfCurrentTable; i > 0; i--) {
								ModuleTableMapping currentTable = screenDesignDefault.getModuleTableMappings()[i];
								ModuleTableMapping previousTable = screenDesignDefault.getModuleTableMappings()[i - 1];
								for (TableDesignForeignKey fk : allForeignKeyInProject) {
									if (fk.getFromTableId().equals(currentTable.getTblDesignId()) && fk.getToTableId().equals(previousTable.getTblDesignId())) {
										fkConstrains.add(fk);
										haveConstrainBetweenTwoTable = true;
										break;
									}
								}
								if (!haveConstrainBetweenTwoTable) {
									break outerloop;
								}
							}
							if (!haveConstrainBetweenTwoTable) {
								fkConstrains.clear();
								stillHaveConstrainFlag = false;
							}
						}
						if (fkConstrains.size() > 0) {
							lstData = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstAllColumnIdOfInputbeans);
							Integer itemSeqNoSqlTable = 1;
							for (TableDesignForeignKey fk : fkConstrains) {
								SqlDesignTable sqlTable = GenerateScreenContruct.setSqlDesignTable(sqlDesignId, itemSeqNoSqlTable.longValue(), ONE, fk.getFromTableId(), fk.getToTableId(), itemSeqNoSqlTable, fk.getFromTableName(), fk.getToTableName(), fk.getFromTableCode(), fk.getToTableCode());
								lstSqlDesignTable.add(sqlTable);

								SqlDesignTableItem sqlDesignTableItem = GenerateScreenContruct.setSqlDesignTableItem(itemSeqNoSqlTable.longValue(), itemSeqNoSqlTable, fk);
								lstSqlDesignTableItem.add(sqlDesignTableItem);
								itemSeqNoSqlTable++;
							}
						} else {
							List<ScreenItem> pkOfTable = findKeyOfTableUsingScreenItems(table, screenDesignObj, screenDesignDefault);
							List<Long> lstColumnId = new ArrayList<Long>();
							for (ScreenItem itemPK : pkOfTable) {
								lstColumnId.add(itemPK.getColumnId());
							}
							lstData = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnId);
							// only register sql design table
							SqlDesignTable sqlTable = GenerateScreenContruct.setSqlDesignTable(sqlDesignId, null, null, table.getTblDesignId(), null, 0, table.getTblDesignName(), null, table.getTblDesignCode(), null);
							lstSqlDesignTable.add(sqlTable);
						}
					}

					if (lstData.size() > 0) {
						boolean flagLogicCode = false;
						for (TableDesignDetails in : lstData) {
							// sql design input
							SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, in.getName(), in.getCode(), in.getBaseType(), itemSeqNo, in.getColumnId(), in.getTableDesignId(), false, null, false, null, DbDomainConst.DesignType.DESIGN_TYPE, null, null);
							lstSqlDesignInput.add(sqlInput);
							allSqlDesignInputs.add(sqlInput);
							// sql design condition
							if (flgHaveCondition) {
								SqlDesignCondition condition = GenerateScreenContruct.setSqlDesignCondition(sqlDesignId, in.getTableDesignId(), in.getColumnId(), "0", itemSeqNo, in.getTableDesignName(), in.getName(), in.getBaseType(), itemSeqNo.toString());
								if (!flagLogicCode) {
									condition.setLogicCode("");
									flagLogicCode = true;
								} else {
									condition.setLogicCode("0");
								}
								lstCondition.add(condition);
							}

							itemSeqNo++;
						}
					}
				}
			} else if (SqlPattern.DELETE == sqlpattern) {
				if (inputBeansRegisted != null && inputBeansRegisted.size() > 0) {
					List<Long> lstAllColumnIdOfInputbeans = new ArrayList<Long>();
					for (InputBean in : inputBeansRegisted) {
						lstAllColumnIdOfInputbeans.add(in.getColumnId());
					}
					lstData = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstAllColumnIdOfInputbeans);
					// only register sql design table
					SqlDesignTable sqlTable = GenerateScreenContruct.setSqlDesignTable(sqlDesignId, null, null, table.getTblDesignId(), null, 0, table.getTblDesignName(), null, table.getTblDesignCode(), null);
					lstSqlDesignTable.add(sqlTable);
					if (lstData.size() > 0) {
						boolean flagLogicCode = false;
						if (table.getTblDesignId().equals(firstTable.getTblDesignId())) {
							for (TableDesignDetails in : lstData) {
								// sql design input
								SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, in.getName(), in.getCode(), in.getBaseType(), itemSeqNo, in.getColumnId(), in.getTableDesignId(), false, null, false, null, DbDomainConst.DesignType.DESIGN_TYPE, null, null);
								lstSqlDesignInput.add(sqlInput);
								allSqlDesignInputs.add(sqlInput);
								// sql design condition
								if (flgHaveCondition) {
									SqlDesignCondition condition = GenerateScreenContruct.setSqlDesignCondition(sqlDesignId, in.getTableDesignId(), in.getColumnId(), "0", itemSeqNo, in.getTableDesignName(), in.getName(), in.getBaseType(), itemSeqNo.toString());
									if (!flagLogicCode) {
										condition.setLogicCode("");
										flagLogicCode = true;
									} else {
										condition.setLogicCode("0");
									}
									lstCondition.add(condition);
								}
								itemSeqNo++;
							}
						} else {
							Long fromColumnId = null, toColumnId = null;
							if (!firstTable.getTblDesignId().equals(table)) {
								for (TableDesignForeignKey fk : allForeignKeyInProject) {
									if (fk.getFromTableId().equals(table.getTblDesignId()) && fk.getToTableId().equals(firstTable.getTblDesignId())) {
										fromColumnId = fk.getFromColumnId();
										toColumnId = fk.getToColumnId();
										break;
									}
								}
							}
							if (fromColumnId != null && toColumnId != null) {
								TableDesignDetails masterColumnFK = null;
								for (TableDesignDetails item : firstTable.getListAllColumns()) {
									if (item.getColumnId().equals(toColumnId)) {
										masterColumnFK = item;
										break;
									}
								}
								TableDesignDetails referColumnFK = null;
								for (TableDesignDetails item : table.getListAllColumns()) {
									if (item.getColumnId().equals(fromColumnId)) {
										referColumnFK = item;
										break;
									}
								}
								if (masterColumnFK != null && referColumnFK != null) {
									// sql design input
									SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, masterColumnFK.getName(), masterColumnFK.getCode(), masterColumnFK.getBaseType(), itemSeqNo, masterColumnFK.getColumnId(), masterColumnFK.getTableDesignId(), false, null, false, null, DbDomainConst.DesignType.DESIGN_TYPE, null, null);
									lstSqlDesignInput.add(sqlInput);
									allSqlDesignInputs.add(sqlInput);
									// sql design condition
									if (flgHaveCondition) {
										SqlDesignCondition condition = GenerateScreenContruct.setSqlDesignCondition(sqlDesignId, referColumnFK.getTableDesignId(), referColumnFK.getColumnId(), "0", itemSeqNo, table.getTblDesignName(), referColumnFK.getName(), referColumnFK.getBaseType(), itemSeqNo.toString());
										if (!flagLogicCode) {
											condition.setLogicCode("");
											flagLogicCode = true;
										} else {
											condition.setLogicCode("0");
										}
										lstCondition.add(condition);
									}
									itemSeqNo++;
								}
							} else {
								List<ScreenItem> pkOfTable = findKeyOfTableUsingScreenItems(table, screenDesignObj, screenDesignDefault);
								List<Long> lstColumnId = new ArrayList<Long>();
								for (ScreenItem itemPK : pkOfTable) {
									lstColumnId.add(itemPK.getColumnId());
								}
								lstData = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnId);
								for (TableDesignDetails in : lstData) {
									// sql design input
									SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, in.getName(), in.getCode(), in.getBaseType(), itemSeqNo, in.getColumnId(), in.getTableDesignId(), false, null, false, null, DbDomainConst.DesignType.DESIGN_TYPE, null, null);
									lstSqlDesignInput.add(sqlInput);
									allSqlDesignInputs.add(sqlInput);
									// sql design condition
									if (flgHaveCondition) {
										SqlDesignCondition condition = GenerateScreenContruct.setSqlDesignCondition(sqlDesignId, in.getTableDesignId(), in.getColumnId(), "0", itemSeqNo, in.getTableDesignName(), in.getName(), in.getBaseType(), itemSeqNo.toString());
										if (!flagLogicCode) {
											condition.setLogicCode("");
											flagLogicCode = true;
										} else {
											condition.setLogicCode("0");
										}
										lstCondition.add(condition);
									}
									itemSeqNo++;
								}
							}
						}
					}
				}
			}
			// sql design result and output
			itemSeqNo = 1;
			Integer itemSeqNoOutput = 1;
			//BangNL
			//Add output default for insert update delete
			if(SqlPattern.INSERT == sqlpattern || SqlPattern.UPDATE == sqlpattern || SqlPattern.DELETE == sqlpattern){
				SqlDesignOutput sqlOutput = GenerateScreenContruct.setSqlDesignOutput(null, sqlDesignId, "result", "result", DbDomainConst.JavaDataTypeOfBlogic.INT_DATATYPE, null, null, null, null, null, null, null, null, 0, 1);
				lstSqlDesignOutput.add(sqlOutput);
			}
			if (SqlPattern.SELECT == sqlpattern && objDefsRegisted != null && objDefsRegisted.size() > 0) {
				// sql design result
				List<ObjectDefinition> lstObjDefsInformation = businessDesignRepository.findColumnInformationOfObjectDefition(objDefsRegisted);
				if (lstObjDefsInformation != null && lstObjDefsInformation.size() > 0) {
					List<ObjectDefinition> lstObInsert = new ArrayList<ObjectDefinition>();
					List<TableDesignDetails> allColumns = table.getListAllColumns();
					Map<String, ObjectDefinition> mapObjDef = new HashMap<String, ObjectDefinition>();
					for (ObjectDefinition ob : lstObjDefsInformation) {
						for (SqlDesignTable sqlTable : lstSqlDesignTable) {
							if ((ob.getTblDesignId() != null && ob.getTblDesignId().equals(sqlTable.getTableId())) || (ob.getTblDesignId() != null && ob.getTblDesignId().equals(sqlTable.getJoinTableId()))) {
								lstObInsert.add(ob);
								mapObjDef.put(ob.getObjectDefinitionId(), ob);
							}
						}
					}
					List<Long> columnIdsAdded = new ArrayList<Long>();
					for (ObjectDefinition all : objDefsRegisted) {
						ObjectDefinition ob = mapObjDef.get(all.getObjectDefinitionId());
						if (ob != null) {
							if (ob.getTblDesignId() != null && ob.getTblDesignId().equals(table.getTblDesignId())) {
								ob.setEnabledFlag(1);
							} else {
								ob.setEnabledFlag(0);
							}
							// sql design result
							if (ob.getDataType() != null && !DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType())) {
								SqlDesignResult result = GenerateScreenContruct.setSqlDesignResult(sqlDesignId, BLANK, ob.getTblDesignId(), ob.getColumnId(), itemSeqNo, ob.getTblDesignName(), ob.getColumnName(), ob.getEnabledFlag(), ob.getBaseType());
								lstSqlDesignResult.add(result);
								columnIdsAdded.add(ob.getColumnId());
							}
						}
						itemSeqNo++;
					}
					for (TableDesignDetails col : allColumns) {
						if (!columnIdsAdded.contains(col.getColumnId())) {
							SqlDesignResult result = GenerateScreenContruct.setSqlDesignResult(sqlDesignId, BLANK, col.getTableDesignId(), col.getColumnId(), itemSeqNo, table.getTblDesignName(), col.getName(), 0, col.getBaseType());
							lstSqlDesignResult.add(result);
							itemSeqNo++;
						}
					}
				}
				// sql design output
				Map<String, ObjectDefinition> mapObjDef = new HashMap<String, ObjectDefinition>();
				for (ObjectDefinition ob : objDefsRegisted) {
					if (ob.getTblDesignId() != null && ob.getTblDesignId().equals(table.getTblDesignId())) {
						ob.setEnabledFlag(1);
					} else {
						ob.setEnabledFlag(0);
					}
					if (ob.getEnabledFlag() != null && 1 == ob.getEnabledFlag().intValue()) {
						mapObjDef.put(ob.getObjectDefinitionId(), ob);
						Integer arrayFlg = ob.getArrayFlg().booleanValue() ? 1 : 0;
						Long tempSqlOutputId = ob.getObjectDefinitionId() != null ? Long.parseLong(ob.getObjectDefinitionId()) : null;
						Long tempSqlOutputParentId = ob.getParentObjectDefinitionId() != null ? Long.parseLong(ob.getParentObjectDefinitionId()) : null;
						GenerateScreenContruct.buildDesignTypeAndObjectType(ob, mapObjDef, null, null);
						SqlDesignOutput sqlOutput = GenerateScreenContruct.setSqlDesignOutput(tempSqlOutputId, sqlDesignId, ob.getObjectDefinitionName(), ob.getObjectDefinitionCode(), ob.getDataType(), tempSqlOutputParentId, arrayFlg,
							itemSeqNoOutput, ob.getObjectDefinitionId(), null, null, ob.getTblDesignId(), ob.getColumnId(), ob.getDesignType(), ob.getObjectType());
						lstSqlDesignOutput.add(sqlOutput);
						allSqlDesignOutputs.add(sqlOutput);
					}
					itemSeqNoOutput++;
				}

			}
		} else {
			// if is initial search : gen sql builder, else is process search : gen advance sql
			if (initialSearch && isGetTotal) {
//				Integer pageable = 0;
//				sqlDesign = GenerateScreenContruct.populateSqlDesign(DomainDatatypeConst.SQLDesignType.SQL_BUILDER, sqlDesignName, sqlDesignCode, sqlpattern, screenDesignDefault, screenDesignObj, table, isGetTotal, pageable);
//				sqlDesign.setSqlDesignId(tempSqlDesignId);
//
//				// TrungDV 29/12/15 : Case Search : only need first table
//				ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings()[0];
//				SqlDesignTable sqlTable = GenerateScreenContruct.setSqlDesignTable(tempSqlDesignId, null, null, firstTable.getTblDesignId(), null, 0, firstTable.getTblDesignName(), null, firstTable.getTblDesignCode(), null);
//				lstSqlDesignTable.add(sqlTable);
//
//				// sql design result , sql design output
//				if (objDefsRegisted != null && objDefsRegisted.size() > 0) {
//					List<ObjectDefinition> lstObjDefsInformation = businessDesignRepository.findColumnInformationOfObjectDefition(objDefsRegisted);
//					// if get total count
//					itemSeqNo = 1;
//					List<ScreenItem> lstPrimaryKeyOrUniqueColumn = findKeyOfFirstTable(screenDesignObj, screenDesignDefault, screenDesignDefault.getLstScreenItemSearchResults());
//					ObjectDefinition objdef = null;
//					outerLoop: for (ObjectDefinition ob : lstObjDefsInformation) {
//						for (ScreenItem item : lstPrimaryKeyOrUniqueColumn) {
//							if (ob.getColumnId() != null && ob.getColumnId().equals(item.getColumnId())) {
//								objdef = ob;
//								break outerLoop;
//							}
//						}
//					}
//					if (objdef == null) {
//						for (ObjectDefinition ob : lstObjDefsInformation) {
//							if (!DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType())) {
//								objdef = ob;
//								break;
//							}
//						}
//					}
//					int sqlResultSequence = 0;
//					for (ObjectDefinition ou : lstObjDefsInformation) {
//						if (ou.getObjectDefinitionId().equals(objdef.getObjectDefinitionId())) {
//							// sql design result
//							SqlDesignResult result = GenerateScreenContruct.setSqlDesignResult(tempSqlDesignId, "1", ou.getTblDesignId(), ou.getColumnId(), sqlResultSequence, ou.getTblDesignName(), ou.getColumnName(), 1, ou.getBaseType());
//							lstSqlDesignResult.add(result);
//							itemSeqNo = sqlResultSequence;
//						} else {
//							SqlDesignResult result = GenerateScreenContruct.setSqlDesignResult(tempSqlDesignId, "", ou.getTblDesignId(), ou.getColumnId(), sqlResultSequence, ou.getTblDesignName(), ou.getColumnName(), 0, ou.getBaseType());
//							lstSqlDesignResult.add(result);
//						}
//						sqlResultSequence++;
//					}
//
//					for (ObjectDefinition ou : objDefsRegisted) {
//						if (ou.getObjectDefinitionCode() != null && TOTALCOUNT.equals(ou.getObjectDefinitionCode())) {
//							objdef = ou;
//						}
//					}
//
//					SqlDesignOutput sqlOutput = GenerateScreenContruct.setSqlDesignOutput(null, tempSqlDesignId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0077), TOTALCOUNT, objdef.getDataType(), null, 0, itemSeqNo, objdef.getObjectDefinitionId(), itemSeqNo.toString(), true, objdef.getTblDesignId(), objdef.getColumnId(), DbDomainConst.DesignType.DESIGN_TYPE, null);
//					lstSqlDesignOutput.add(sqlOutput);
//					allSqlDesignOutputs.add(sqlOutput);
//				} else if (outputBeansRegisted != null && outputBeansRegisted.size() > 0) {
//					List<OutputBean> lstOutputbeansInformation = businessDesignRepository.findColumnInformationOfOutputbean(outputBeansRegisted);
//					// if get total count
//					itemSeqNo = 1;
//					List<ScreenItem> lstPrimaryKeyOrUniqueColumn = findKeyOfFirstTable(screenDesignObj, screenDesignDefault, screenDesignDefault.getLstScreenItemSearchResults());
//					OutputBean output = null;
//					outerLoop: for (OutputBean ou : lstOutputbeansInformation) {
//						for (ScreenItem item : lstPrimaryKeyOrUniqueColumn) {
//							if (ou.getColumnId() != null && ou.getColumnId().equals(item.getColumnId())) {
//								output = ou;
//								break outerLoop;
//							}
//						}
//					}
//					if (output == null) {
//						for (OutputBean ou : lstOutputbeansInformation) {
//							if (!DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ou.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ou.getDataType())) {
//								output = ou;
//								break;
//							}
//						}
//					}
//					int sqlResultSequence = 0;
//					for (OutputBean ou : lstOutputbeansInformation) {
//						if (ou.getOutputBeanId().equals(output.getOutputBeanId())) {
//							// sql design result
//							SqlDesignResult result = GenerateScreenContruct.setSqlDesignResult(tempSqlDesignId, "1", ou.getTblDesignId(), ou.getColumnId(), sqlResultSequence, ou.getTblDesignName(), ou.getColumnName(), 1, ou.getBaseType());
//							lstSqlDesignResult.add(result);
//							itemSeqNo = sqlResultSequence;
//						} else {
//							SqlDesignResult result = GenerateScreenContruct.setSqlDesignResult(tempSqlDesignId, "", ou.getTblDesignId(), ou.getColumnId(), sqlResultSequence, ou.getTblDesignName(), ou.getColumnName(), 0, ou.getBaseType());
//							lstSqlDesignResult.add(result);
//						}
//						sqlResultSequence++;
//					}
//
//					for (OutputBean ou : outputBeansRegisted) {
//						if (ou.getOutputBeanCode() != null && TOTALCOUNT.equals(ou.getOutputBeanCode())) {
//							output = ou;
//						}
//					}
//
//					SqlDesignOutput sqlOutput = GenerateScreenContruct.setSqlDesignOutput(null, tempSqlDesignId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0077), TOTALCOUNT, output.getDataType(), null, 0, itemSeqNo, output.getOutputBeanId(), itemSeqNo.toString(), true, output.getTblDesignId(), output.getColumnId(), DbDomainConst.DesignType.DESIGN_TYPE, null);
//					lstSqlDesignOutput.add(sqlOutput);
//					allSqlDesignOutputs.add(sqlOutput);
//				}
			} else {
				Integer pageable = 0;
				if (isGetTotal) {
					pageable = 0;
				} else {
					pageable = 1;
				}
				sqlDesign = GenerateScreenContruct.populateSqlDesign(DomainDatatypeConst.SQLDesignType.ADVANCED_SQL, sqlDesignName, sqlDesignCode, sqlpattern, screenDesignDefault, screenDesignObj, table, isGetTotal, pageable);
				sqlDesign.setSqlDesignId(tempSqlDesignId);

				StringBuilder resultStr = new StringBuilder("SELECT ");
				StringBuilder fromStr = new StringBuilder(DOWNLINE + "FROM ");
				StringBuilder innerStr = new StringBuilder(" ");
				StringBuilder whereStr = new StringBuilder("");
				ModuleTableMapping firstTable = null;

				// TrungDV 29/12/15 : Case Search : only need first table
				firstTable = screenDesignDefault.getModuleTableMappings()[0];
				SqlDesignTable sqlTable = GenerateScreenContruct.setSqlDesignTable(tempSqlDesignId, null, null, firstTable.getTblDesignId(), null, 0, firstTable.getTblDesignName(), null, firstTable.getTblDesignCode(), null);
				lstSqlDesignTable.add(sqlTable);
				fromStr.append(SPACE + DOWNLINE + firstTable.getTblDesignCode());

				// sql design result , sql design output
				if (objDefsRegisted != null && objDefsRegisted.size() > 0) {
					List<ObjectDefinition> lstObjDefsInformation = businessDesignRepository.findColumnInformationOfObjectDefition(objDefsRegisted);
					// if get total count
					if (isGetTotal) {
						itemSeqNo = 1;
						List<ScreenItem> lstPrimaryKeyOrUniqueColumn = findKeyOfFirstTable(screenDesignObj, screenDesignDefault, screenDesignDefault.getLstScreenItemSearchResults());
						ObjectDefinition objdef = null;
						outerLoop: for (ObjectDefinition ob : lstObjDefsInformation) {
							for (ScreenItem item : lstPrimaryKeyOrUniqueColumn) {
								if (ob.getColumnId() != null && ob.getColumnId().equals(item.getColumnId())) {
									objdef = ob;
									break outerLoop;
								}
							}
						}
						if (objdef == null) {
							for (ObjectDefinition ob : lstObjDefsInformation) {
								if (!DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType())) {
									objdef = ob;
									break;
								}
							}
						}
						resultStr.append(SPACE + "COUNT(" + objdef.getTblDesignCode() + DOT + objdef.getColumnCode() + ")" + SPACE + "AS total_count");

						for (ObjectDefinition ou : objDefsRegisted) {
							if (ou.getObjectDefinitionCode() != null && TOTALCOUNT.equals(ou.getObjectDefinitionCode())) {
								objdef = ou;
							}
						}
						SqlDesignOutput sqlOutput = GenerateScreenContruct.setSqlDesignOutput(null, tempSqlDesignId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0077), TOTALCOUNT, objdef.getDataType(), null, 0, itemSeqNo, objdef.getObjectDefinitionId(), "total_count", true, objdef.getTblDesignId(), objdef.getColumnId(), DbDomainConst.DesignType.DESIGN_TYPE, null);
						lstSqlDesignOutput.add(sqlOutput);
						allSqlDesignOutputs.add(sqlOutput);
					}
					// if get records
					else {
						itemSeqNo = 1;
						Set<String> hashSetCode = new HashSet<String>();
						for (int i = 0; i < lstObjDefsInformation.size(); i++) {
							ObjectDefinition ob = lstObjDefsInformation.get(i);
							for (SqlDesignTable sqlTableObj : lstSqlDesignTable) {
								if (ob.getTblDesignId().equals(sqlTableObj.getTableId()) || ob.getTblDesignId().equals(sqlTableObj.getJoinTableId())) {
									if (!hashSetCode.contains(ob.getObjectDefinitionCode())) {
										hashSetCode.add(ob.getObjectDefinitionCode());
										if (i == lstObjDefsInformation.size() - 1) {
											resultStr.append(SPACE + ob.getTblDesignCode() + DOT + ob.getColumnCode());
										} else {
											resultStr.append(SPACE + ob.getTblDesignCode() + DOT + ob.getColumnCode() + COMMA);
										}
										// sql design output
										Integer arrayFlg = ob.getArrayFlg().booleanValue() ? 1 : 0;
										SqlDesignOutput sqlOutput = GenerateScreenContruct.setSqlDesignOutput(null, tempSqlDesignId, ob.getColumnName(), ob.getColumnCode(), ob.getDataType(), null, arrayFlg, itemSeqNo, ob.getObjectDefinitionId(), ob.getColumnCode(), true, ob.getTblDesignId(), ob.getColumnId(), DbDomainConst.DesignType.DESIGN_TYPE, null);
										lstSqlDesignOutput.add(sqlOutput);
										allSqlDesignOutputs.add(sqlOutput);
										itemSeqNo++;
									}
								}
							}
						}
					}
				} else if (outputBeansRegisted != null && outputBeansRegisted.size() > 0) {
					List<OutputBean> lstOutputBeansInformation = businessDesignRepository.findColumnInformationOfOutputbean(outputBeansRegisted);
					// if get total count
					if (isGetTotal) {
						itemSeqNo = 1;
						List<ScreenItem> lstPrimaryKeyOrUniqueColumn = findKeyOfFirstTable(screenDesignObj, screenDesignDefault, screenDesignDefault.getLstScreenItemSearchResults());
						OutputBean output = null;
						outerLoop: for (OutputBean ou : lstOutputBeansInformation) {
							for (ScreenItem item : lstPrimaryKeyOrUniqueColumn) {
								if (ou.getColumnId() != null && ou.getColumnId().equals(item.getColumnId())) {
									output = ou;
									break outerLoop;
								}
							}
						}
						if (output == null) {
							for (OutputBean ou : lstOutputBeansInformation) {
								if (!DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ou.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ou.getDataType())) {
									output = ou;
									break;
								}
							}
						}
						resultStr.append(SPACE + "COUNT(" + output.getTblDesignCode() + DOT + output.getColumnCode() + ")" + SPACE + "AS total_count");

						for (OutputBean ou : outputBeansRegisted) {
							if (ou.getOutputBeanCode() != null && TOTALCOUNT.equals(ou.getOutputBeanCode())) {
								output = ou;
							}
						}
						SqlDesignOutput sqlOutput = GenerateScreenContruct.setSqlDesignOutput(null, tempSqlDesignId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0077), TOTALCOUNT, output.getDataType(), null, 0, itemSeqNo, output.getOutputBeanId(), "total_count", true, output.getTblDesignId(), output.getColumnId(), DbDomainConst.DesignType.DESIGN_TYPE, null);
						lstSqlDesignOutput.add(sqlOutput);
						allSqlDesignOutputs.add(sqlOutput);
					}
					// if get records
					else {
						itemSeqNo = 1;
						Set<String> hashSetCode = new HashSet<String>();
						for (int i = 0; i < lstOutputBeansInformation.size(); i++) {
							OutputBean ou = lstOutputBeansInformation.get(i);
							for (SqlDesignTable sqlTableObj : lstSqlDesignTable) {
								if (ou.getTblDesignId().equals(sqlTableObj.getTableId()) || ou.getTblDesignId().equals(sqlTableObj.getJoinTableId())) {
									if (!hashSetCode.contains(ou.getOutputBeanCode())) {
										hashSetCode.add(ou.getOutputBeanCode());
										if (i == lstOutputBeansInformation.size() - 1) {
											resultStr.append(SPACE + ou.getTblDesignCode() + DOT + ou.getColumnCode());
										} else {
											resultStr.append(SPACE + ou.getTblDesignCode() + DOT + ou.getColumnCode() + COMMA);
										}
										// sql design output
										Integer arrayFlg = ou.getArrayFlg().booleanValue() ? 1 : 0;
										SqlDesignOutput sqlOutput = GenerateScreenContruct.setSqlDesignOutput(null, tempSqlDesignId, ou.getColumnName(), ou.getColumnCode(), ou.getDataType(), null, arrayFlg, itemSeqNo, ou.getOutputBeanId(), ou.getColumnCode(), true, ou.getTblDesignId(), ou.getColumnId(), DbDomainConst.DesignType.DESIGN_TYPE, null);
										lstSqlDesignOutput.add(sqlOutput);
										allSqlDesignOutputs.add(sqlOutput);
										itemSeqNo++;
									}
								}
							}
						}
					}
				}

				// sql design input and sql design condition
				if (isProcessSearch) {
					if (inputBeansRegisted != null && inputBeansRegisted.size() > 0) {
						itemSeqNo = 1;
						Set<String> hashSetCode = new HashSet<String>();
						for (InputBean in : inputBeansRegisted) {
							for (SqlDesignTable sqlTableObj : lstSqlDesignTable) {
								if (in.getTblDesignId().equals(sqlTableObj.getTableId()) || in.getTblDesignId().equals(sqlTableObj.getJoinTableId())) {
									if (!hashSetCode.contains(in.getInputBeanCode())) {
										hashSetCode.add(in.getInputBeanCode());
										lstColumnIdOfThisTable.add(in.getColumnId());
										break;
									}
								}
							}
						}
						if (lstColumnIdOfThisTable.size() > 0) {
							lstData = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIdOfThisTable);
						}
						// sql design input && sql design condition
						if (lstData.size() > 0) {
							String from = "From";
							String to = "To";
							String input = "";
							// if is a search records, add "input." before attribute
							if (!isGetTotal) {
								input = "input.";
							}
							for (int i = 0; i < lstData.size(); i++) {
								if (flgHaveCondition) {
									if (i == 0) {
										if(screenDesignDefault.getProject() != null && DbDomainConst.DatabaseType.PostgreSQL.equals(screenDesignDefault.getProject().getDbType())) {
											whereStr.append(SPACE + DOWNLINE + "WHERE TRUE");
										} else if(screenDesignDefault.getProject() != null && DbDomainConst.DatabaseType.ORACLE.equals(screenDesignDefault.getProject().getDbType())) {
											whereStr.append(SPACE + DOWNLINE + "WHERE 1=1");
										}
									}
								}
								TableDesignDetails in = lstData.get(i);
								if(DbDomainConst.LogicDataType.CURRENCY.equals(in.getItemType()) || DbDomainConst.LogicDataType.DECIMAL.equals(in.getItemType())
										|| DbDomainConst.LogicDataType.INTEGER.equals(in.getItemType()) || DbDomainConst.LogicDataType.DATETIME.equals(in.getItemType())
										|| DbDomainConst.LogicDataType.DATE.equals(in.getItemType()) || DbDomainConst.LogicDataType.TIME.equals(in.getItemType())) {
									// sql design input FROM
									SqlDesignInput sqlInputFrom = GenerateScreenContruct.setSqlDesignInput(tempSqlDesignId, in.getName() + SPACE + from, in.getCode() + from, in.getBaseType(), itemSeqNo++, in.getColumnId(), in.getTableDesignId(), false, null, false, null, DbDomainConst.DesignType.DESIGN_TYPE, null, null);
									sqlInputFrom.setFromOrTo(FROM);
									lstSqlDesignInput.add(sqlInputFrom);
									allSqlDesignInputs.add(sqlInputFrom);
									// sql design input TO
									SqlDesignInput sqlInputTo = GenerateScreenContruct.setSqlDesignInput(tempSqlDesignId, in.getName() + SPACE + to, in.getCode() + to, in.getBaseType(), itemSeqNo++, in.getColumnId(), in.getTableDesignId(), false, null, false, null, DbDomainConst.DesignType.DESIGN_TYPE, null, null);
									sqlInputTo.setFromOrTo(TO);
									lstSqlDesignInput.add(sqlInputTo);
									allSqlDesignInputs.add(sqlInputTo);
									if(flgHaveCondition) {
										if (DbDomainConst.DatabaseType.ORACLE.equals(screenDesignDefault.getProject().getDbType()) 
												&& (DbDomainConst.LogicDataType.DATE.equals(in.getItemType()) || DbDomainConst.LogicDataType.TIME.equals(in.getItemType()))) {
											if (DbDomainConst.LogicDataType.DATE.equals(in.getItemType())) {
												String sqlDesignInputCodeFrom = sqlInputFrom.getSqlDesignInputCode();
												whereStr.append(SPACE + DOWNLINE + "<if test=\"" + input + sqlDesignInputCodeFrom + " != null and" + SPACE + input + sqlDesignInputCodeFrom + " != ''" + "\" >");
												whereStr.append(SPACE + DOWNLINE + "<![CDATA[");
												whereStr.append(SPACE + DOWNLINE + "AND" + SPACE + "TRUNC(" +in.getTableDesignCode() + DOT + in.getCode() + ", 'DDD')" + " >= " + "TRUNC(" + "#{" + input + sqlDesignInputCodeFrom + "}" + ", 'DDD')" + SPACE);
												whereStr.append(SPACE + DOWNLINE + "]]>");
												whereStr.append(SPACE + DOWNLINE + "</if>");
												
												String sqlDesignInputCodeTo = sqlInputTo.getSqlDesignInputCode();
												whereStr.append(SPACE + DOWNLINE + "<if test=\"" + input + sqlDesignInputCodeTo + " != null and" + SPACE + input + sqlDesignInputCodeTo + " != ''" + "\" >");
												whereStr.append(SPACE + DOWNLINE + "<![CDATA[");
												whereStr.append(SPACE + DOWNLINE + "AND" + SPACE + "TRUNC(" + in.getTableDesignCode() + DOT + in.getCode() + ", 'DDD')" + " <= " + "TRUNC(" + "#{" + input + sqlDesignInputCodeTo + "}" + ", 'DDD')" + SPACE);
												whereStr.append(SPACE + DOWNLINE + "]]>");
												whereStr.append(SPACE + DOWNLINE + "</if>");
											} else if (DbDomainConst.LogicDataType.TIME.equals(in.getItemType())) {
												String sqlDesignInputCodeFrom = sqlInputFrom.getSqlDesignInputCode();
												whereStr.append(SPACE + DOWNLINE + "<if test=\"" + input + sqlDesignInputCodeFrom + " != null and" + SPACE + input + sqlDesignInputCodeFrom + " != ''" + "\" >");
												whereStr.append(SPACE + DOWNLINE + "<![CDATA[");
												whereStr.append(SPACE + DOWNLINE + "AND" + SPACE + "TO_CHAR(" +in.getTableDesignCode() + DOT + in.getCode() + ", 'HH24:MI:SS')" + " >= " + "TO_CHAR(" + "#{" + input + sqlDesignInputCodeFrom + "}" + ", 'HH24:MI:SS')" + SPACE);
												whereStr.append(SPACE + DOWNLINE + "]]>");
												whereStr.append(SPACE + DOWNLINE + "</if>");
												
												String sqlDesignInputCodeTo = sqlInputTo.getSqlDesignInputCode();
												whereStr.append(SPACE + DOWNLINE + "<if test=\"" + input + sqlDesignInputCodeTo + " != null and" + SPACE + input + sqlDesignInputCodeTo + " != ''" + "\" >");
												whereStr.append(SPACE + DOWNLINE + "<![CDATA[");
												whereStr.append(SPACE + DOWNLINE + "AND" + SPACE + "TO_CHAR(" + in.getTableDesignCode() + DOT + in.getCode() + ", 'HH24:MI:SS')" + " <= " + "TO_CHAR(" +  "#{" + input + sqlDesignInputCodeTo + "}" + ", 'HH24:MI:SS')" + SPACE);
												whereStr.append(SPACE + DOWNLINE + "]]>");
												whereStr.append(SPACE + DOWNLINE + "</if>");
											}
										} else {
											String sqlDesignInputCodeFrom = sqlInputFrom.getSqlDesignInputCode();
											whereStr.append(SPACE + DOWNLINE + "<if test=\"" + input + sqlDesignInputCodeFrom + " != null and" + SPACE + input + sqlDesignInputCodeFrom + " != ''" + "\" >");
											whereStr.append(SPACE + DOWNLINE + "<![CDATA[");
											whereStr.append(SPACE + DOWNLINE + "AND" + SPACE + in.getTableDesignCode() + DOT + in.getCode() + " >= " + "#{" + input + sqlDesignInputCodeFrom + "}" + SPACE);
											whereStr.append(SPACE + DOWNLINE + "]]>");
											whereStr.append(SPACE + DOWNLINE + "</if>");
											
											String sqlDesignInputCodeTo = sqlInputTo.getSqlDesignInputCode();
											whereStr.append(SPACE + DOWNLINE + "<if test=\"" + input + sqlDesignInputCodeTo + " != null and" + SPACE + input + sqlDesignInputCodeTo + " != ''" + "\" >");
											whereStr.append(SPACE + DOWNLINE + "<![CDATA[");
											whereStr.append(SPACE + DOWNLINE + "AND" + SPACE + in.getTableDesignCode() + DOT + in.getCode() + " <= " + "#{" + input + sqlDesignInputCodeTo + "}" + SPACE);
											whereStr.append(SPACE + DOWNLINE + "]]>");
											whereStr.append(SPACE + DOWNLINE + "</if>");
										}
									}
								} else {
									Integer arrayFlag = 0;
									if (DbDomainConst.BaseType.BOOLEAN_BASETYPE == in.getBaseType()) {
										arrayFlag = 1;
									}
									if (DbDomainConst.LogicDataType.RADIO.equals(in.getItemType())) {
										if (getStatusColumn(in.getBaseType().longValue(), in.getConstrainsType(), in.getDatasourceType(), in.getDatasourceId())) {
											arrayFlag = 1;
										}
									}
									// convert base type to String for use QpCommon.qp_intersect_str if column config datasource and is a checkbox
									if (DbDomainConst.LogicDataType.CHECKBOX.equals(in.getItemType()) && DbDomainConst.BaseType.BOOLEAN_BASETYPE != in.getBaseType()) {
										in.setBaseType(DbDomainConst.BaseType.CHARACTER_VARYING_BASETYPE);
									}
									SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(tempSqlDesignId, in.getName(), in.getCode(), in.getBaseType(), itemSeqNo++, in.getColumnId(), in.getTableDesignId(), false, null, false, arrayFlag, DbDomainConst.DesignType.DESIGN_TYPE, null, null);
									lstSqlDesignInput.add(sqlInput);
									allSqlDesignInputs.add(sqlInput);
									if (flgHaveCondition) {
										String sqlDesignInputCode = sqlInput.getSqlDesignInputCode();
										if (arrayFlag == 1) {
											whereStr.append(SPACE + DOWNLINE + "<if test=\"" + input + sqlDesignInputCode + " != null and" + SPACE + input + sqlDesignInputCode + ".size() > 0" + "\" >");
											whereStr.append(SPACE + DOWNLINE + "AND" + SPACE + in.getTableDesignCode() + DOT + in.getCode() + " IN");
											whereStr.append(DOWNLINE).append("<foreach item=\"item\" collection=\"" + input + sqlDesignInputCode + "\" open=\"(\" close=\")\" separator=\",\">");
											whereStr.append(DOWNLINE).append("#{item}");
											whereStr.append(DOWNLINE).append("</foreach>");
											whereStr.append(SPACE + DOWNLINE + "</if>");
										} else {
											String searchLike = "";
											Project project = screenDesignDefault.getProject();
											if (project != null) {
												if (DbDomainConst.DatabaseType.PostgreSQL.equals(project.getDbType())) {
													String compareOperator = "ILIKE";
													if (DbDomainConst.YesNoFlg.YES.intValue() == project.getCaseSensitivity()) {
														compareOperator = "LIKE";
													}
													searchLike = in.getTableDesignCode() + DOT + in.getCode() + SPACE + compareOperator + SPACE + "#{" + sqlDesignInputCode + "}" + SPACE + "ESCAPE '~'";
												} else {
													if (DbDomainConst.YesNoFlg.YES.intValue() == project.getCaseSensitivity()) {
														searchLike = in.getTableDesignCode() + DOT + in.getCode() + SPACE + "LIKE" + SPACE + "#{" + sqlDesignInputCode + "}" + SPACE + "ESCAPE '~'";
													} else {
														searchLike = "UPPER(" + in.getTableDesignCode() + DOT + in.getCode() +")" + SPACE + "LIKE" + SPACE + "UPPER(#{" + sqlDesignInputCode + "})" + SPACE + "ESCAPE '~'";
													}
												}
											}
											whereStr.append(SPACE + DOWNLINE + "<if test=\"" + input + sqlDesignInputCode + " != null and" + SPACE + input + sqlDesignInputCode + " != ''" + "\" >");
											if (DbDomainConst.LogicDataType.CHECKBOX.equals(in.getItemType())) {
												String param1 = "#{" + input + sqlDesignInputCode + "}";
												String param2 = in.getCode();
												String param3 = "';'";
												if (DbDomainConst.DatabaseType.PostgreSQL.equals(project.getDbType())) {
													whereStr.append(SPACE + DOWNLINE + "AND" + SPACE + "qp_intersect_str(" + param1 + "," + param2 + "," + param3 + ")>0");
												} else {
													whereStr.append(SPACE + DOWNLINE + "AND" + SPACE + "qp_common.qp_intersect_str(" + param1 + "," + param2 + "," + param3 + ")>0");
												}
											} else {
												if (in.getBaseType() != null && JavaDataTypeOfBlogic.STRING_DATATYPE.equals(BusinessDesignHelper.convertJavaTypeFromBaseType(in.getBaseType()))) {
													whereStr.append(SPACE + DOWNLINE + "<bind name=\"" + sqlDesignInputCode + "\"" + " value=\"" + QUERY_ESCAPE_UTILS + "(" + input + sqlDesignInputCode + ")" + "\" />");
													whereStr.append(SPACE + DOWNLINE + "AND" + SPACE + searchLike);
												} else {
													whereStr.append(SPACE + DOWNLINE + "AND" + SPACE + in.getTableDesignCode() + DOT + in.getCode() + " = " + "#{" + input + sqlDesignInputCode + "}" + SPACE);
												}
											}
											whereStr.append(SPACE + DOWNLINE + "</if>");
										}
									}
								}
							}
						}
					}
				}
				String sqlText = "";
			    // Pagination for oracle
				if (DbDomainConst.DatabaseType.ORACLE.equals(screenDesignDefault.getProject().getDbType()) && !isGetTotal) {
					List<TableDesignDetails> pks = findPKOfTable(firstTable);
					StringBuilder sbPk = new StringBuilder();
					if (pks != null) {
						for (TableDesignDetails column : pks) {
							if (sbPk.length() > 0) {
								sbPk.append(COMMA);
							}
							sbPk.append(column.getCode());
						}
					}
					resultStr.append(COMMA).append("ROW_NUMBER()");
					resultStr.append(DOWNLINE);
					resultStr.append("<if test=\"pageable.sort != null\">");
					resultStr.append(DOWNLINE);
					resultStr.append("OVER (ORDER BY");
					resultStr.append(DOWNLINE);
					resultStr.append("<foreach collection=\"pageable.sort\" item=\"account\" separator=\",\">");
					resultStr.append(DOWNLINE);
					resultStr.append("${account.property} ${account.direction}");
					resultStr.append(DOWNLINE);
					resultStr.append("</foreach>");
					resultStr.append(DOWNLINE);
					resultStr.append(") rn");
					resultStr.append(DOWNLINE);
					
					resultStr.append("</if>");

					resultStr.append(DOWNLINE);
					resultStr.append("<if test=\"pageable.sort == null\">");
					resultStr.append(DOWNLINE);
					resultStr.append("OVER (ORDER BY ").append(sbPk).append(" DESC) rn");
					resultStr.append(DOWNLINE);
					resultStr.append("</if>");
					resultStr.append(DOWNLINE);
					sqlText = resultStr.append(fromStr).append(innerStr).append(whereStr).toString();
				} else {
			     sqlText = resultStr.append(fromStr).append(innerStr).append(whereStr).toString();
			    }
			    sqlDesign.setSqlText(sqlText);
			}
		}


		if (!flgSearchScreen) {
			if (lstSqlDesignInput.size() > 0) {
				SqlDesignInput[] arrSqlDesignInput = lstSqlDesignInput.toArray(new SqlDesignInput[lstSqlDesignInput.size()]);
				Long startSequenceSqlInput = sqlDesignInputRepository.preserveIds(arrSqlDesignInput.length) - arrSqlDesignInput.length;
				Map<Long, Long> mapSqlInput = new HashMap<Long, Long>();
				for (int i = 0; i < arrSqlDesignInput.length; i++) {
					mapSqlInput.put(arrSqlDesignInput[i].getSqlDesignInputId(), startSequenceSqlInput);
					if (arrSqlDesignInput[i].getSqlDesignInputParentId() != null) {
						arrSqlDesignInput[i].setSqlDesignInputParentId(mapSqlInput.get(arrSqlDesignInput[i].getSqlDesignInputParentId()));
					}
					arrSqlDesignInput[i].setSqlDesignInputId(startSequenceSqlInput++);
				}
				sqlDesignInputRepository.registerAll(arrSqlDesignInput);
			}
			if (lstSqlDesignOutput.size() > 0) {
				SqlDesignOutput[] arrSqlOutput = lstSqlDesignOutput.toArray(new SqlDesignOutput[lstSqlDesignOutput.size()]);
				Map<Long, Long> mapSqlOutput = new HashMap<Long, Long>();
				Long startSqlOutput = sqlDesignOutputRepository.preserveIds(arrSqlOutput.length) - arrSqlOutput.length;
				for (int i = 0; i < arrSqlOutput.length; i++) {
					mapSqlOutput.put(arrSqlOutput[i].getSqlDesignOutputId(), startSqlOutput);
					if (arrSqlOutput[i].getSqlDesignOutputParentId() != null) {
						arrSqlOutput[i].setSqlDesignOutputParentId(mapSqlOutput.get(arrSqlOutput[i].getSqlDesignOutputParentId()));
					}
					arrSqlOutput[i].setSqlDesignOutputId(startSqlOutput);
					startSqlOutput++;
				}
				sqlDesignOutputRepository.registerAll(arrSqlOutput);
			}
			if (lstSqlDesignValue.size() > 0) {
				SqlDesignValue[] arrSqlDesignValue = lstSqlDesignValue.toArray(new SqlDesignValue[lstSqlDesignValue.size()]);
				sqlDesignValueRepository.registerAll(arrSqlDesignValue);
			}
			if (lstCondition.size() > 0) {
				SqlDesignCondition[] arrCondition = lstCondition.toArray(new SqlDesignCondition[lstCondition.size()]);
				sqlDesignConditionRepository.registerAll(arrCondition);
			}
			if (lstSqlDesignResult.size() > 0) {
				SqlDesignResult[] arrResults = lstSqlDesignResult.toArray(new SqlDesignResult[lstSqlDesignResult.size()]);
				sqlDesignResultRepository.registerAll(arrResults);
			}
			Map<Long, Long> mapIdSqlTable = new HashMap<Long, Long>();
			if (lstSqlDesignTable.size() > 0) {
				Long sequenceSqlTable = sqlDesignTableRepository.getSequencesSqlDesignTable(lstSqlDesignTable.size() - 1);
				Long startSequence = sequenceSqlTable - (lstSqlDesignTable.size() - 1);
				for (SqlDesignTable sqlTable : lstSqlDesignTable) {
					mapIdSqlTable.put(sqlTable.getSqlDesignTableId(), startSequence);
					sqlTable.setSqlDesignTableId(startSequence++);
				}
				SqlDesignTable[] arrSqlTables = lstSqlDesignTable.toArray(new SqlDesignTable[lstSqlDesignTable.size()]);
				sqlDesignTableRepository.registerAllHaveId(arrSqlTables);
			}
			if (lstSqlDesignTableItem.size() > 0) {
				for (SqlDesignTableItem sqlTableItem : lstSqlDesignTableItem) {
					sqlTableItem.setSqlDesignTableId(mapIdSqlTable.get(sqlTableItem.getSqlDesignTableId()));
				}
				SqlDesignTableItem[] arrSqlTableItems = lstSqlDesignTableItem.toArray(new SqlDesignTableItem[lstSqlDesignTableItem.size()]);
				sqlDesignTableItemsRepository.registerAll(arrSqlTableItems);
			}
		} else {
			sqlDesignRepository.register(sqlDesign);
			Map<Long, Long> mapSqlDesignId = new HashMap<Long, Long>();
			mapSqlDesignId.put(tempSqlDesignId, sqlDesign.getSqlDesignId());

			if (lstSqlDesignOutput.size() > 0) {
				SqlDesignOutput[] arrSqlOutput = lstSqlDesignOutput.toArray(new SqlDesignOutput[lstSqlDesignOutput.size()]);
				Long startSqlOutput = sqlDesignOutputRepository.preserveIds(arrSqlOutput.length) - arrSqlOutput.length;
				for (int i = 0; i < arrSqlOutput.length; i++) {
					arrSqlOutput[i].setSqlDesignId(mapSqlDesignId.get(arrSqlOutput[i].getSqlDesignId()));
					arrSqlOutput[i].setSqlDesignOutputId(startSqlOutput++);
				}
				sqlDesignOutputRepository.registerAll(arrSqlOutput);
			}

			if(initialSearch) {
//				Map<Long, Long> mapIdSqlTable = new HashMap<Long, Long>();
//				if (lstSqlDesignTable.size() > 0) {
//					Long sequenceSqlTable = sqlDesignTableRepository.getSequencesSqlDesignTable(lstSqlDesignTable.size() - 1);
//					Long startSequence = sequenceSqlTable - (lstSqlDesignTable.size() - 1);
//					for (SqlDesignTable sqlTable : lstSqlDesignTable) {
//						sqlTable.setSqlDesignId(mapSqlDesignId.get(sqlTable.getSqlDesignId()));
//						mapIdSqlTable.put(sqlTable.getSqlDesignTableId(), startSequence);
//						sqlTable.setSqlDesignTableId(startSequence++);
//					}
//					SqlDesignTable[] arrSqlTables = lstSqlDesignTable.toArray(new SqlDesignTable[lstSqlDesignTable.size()]);
//					sqlDesignTableRepository.registerAllHaveId(arrSqlTables);
//				}
//				if (lstSqlDesignTableItem.size() > 0) {
//					for (SqlDesignTableItem sqlTableItem : lstSqlDesignTableItem) {
//						sqlTableItem.setSqlDesignTableId(mapIdSqlTable.get(sqlTableItem.getSqlDesignTableId()));
//					}
//					SqlDesignTableItem[] arrSqlTableItems = lstSqlDesignTableItem.toArray(new SqlDesignTableItem[lstSqlDesignTableItem.size()]);
//					sqlDesignTableItemsRepository.registerAll(arrSqlTableItems);
//				}
//				if (lstSqlDesignResult.size() > 0) {
//					for (SqlDesignResult result : lstSqlDesignResult) {
//						result.setSqlDesignId(mapSqlDesignId.get(result.getSqlDesignId()));
//					}
//					SqlDesignResult[] arrResults = lstSqlDesignResult.toArray(new SqlDesignResult[lstSqlDesignResult.size()]);
//					sqlDesignResultRepository.registerAll(arrResults);
//				}
			} else {
				if (lstSqlDesignInput.size() > 0) {
					SqlDesignInput[] arrSqlDesignInput = lstSqlDesignInput.toArray(new SqlDesignInput[lstSqlDesignInput.size()]);
					Long startSequenceSqlInput = sqlDesignInputRepository.preserveIds(arrSqlDesignInput.length) - arrSqlDesignInput.length;
					for (int i = 0; i < arrSqlDesignInput.length; i++) {
						arrSqlDesignInput[i].setSqlDesignId(mapSqlDesignId.get(arrSqlDesignInput[i].getSqlDesignId()));
						arrSqlDesignInput[i].setSqlDesignInputId(startSequenceSqlInput++);
					}
					sqlDesignInputRepository.registerAll(arrSqlDesignInput);
				}
			}
		}

		sqlDesign.setAllSqlDesignInputs(allSqlDesignInputs);
		sqlDesign.setAllSqlDesignOutputs(allSqlDesignOutputs);
		return sqlDesign;
	}

	/**
	 * @param executionComponentIdTemp
	 * @param inputbeansRegisted
	 * @param objDefsRegisted
	 * @param sqlDesignInputs
	 * @return
	 */
	private List<ExecutionInputValue> contructExecutionInputValue(ScreenDesignDefault screenDesignDefault, ModuleTableMapping table, Long executionComponentIdTemp, List<InputBean> inputbeansRegisted, List<ObjectDefinition> objDefsRegisted, List<SqlDesignInput> sqlDesignInputs, boolean processModify) {
		ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings()[0];
		if (objDefsRegisted != null && objDefsRegisted.size() > 0 && sqlDesignInputs != null && sqlDesignInputs.size() > 0) {
			// get tables previous
			List<ModuleTableMapping> lstTablePrevious = new ArrayList<ModuleTableMapping>();
			for (ModuleTableMapping obj : screenDesignDefault.getModuleTableMappings()) {
				if (obj.getTblDesignId().equals(table.getTblDesignId())) {
					break;
				} else {
					lstTablePrevious.add(obj);
				}
			}
			// if object definition is foreign key, execution input is reference object definition
			for (ObjectDefinition ob : objDefsRegisted) {
				if (DbDomainConst.YesNoFlg.YES.equals(ob.isKey(DbDomainConst.TblDesignKeyType.FK))) {
					Long toTableId = null, toColumnId = null;
					outerLoop: for (TableDesignForeignKey fk : allForeignKeyInProject) {
						for (ModuleTableMapping obj : lstTablePrevious) {
							if (fk.getFromTableId().equals(table.getTblDesignId()) && fk.getToTableId().equals(obj.getTblDesignId()) && fk.getFromColumnId().equals(ob.getColumnId())) {
								toTableId = fk.getToTableId();
								toColumnId = fk.getToColumnId();
								break outerLoop;
							}
						}
					}
					for (ObjectDefinition objDef : objDefsRegisted) {
						if (objDef.getTblDesignId() != null && objDef.getTblDesignId().equals(toTableId) && objDef.getColumnId() != null && objDef.getColumnId().equals(toColumnId)) {
							ob.setMappingObjectDefinitionId(objDef.getObjectDefinitionId());
						}
					}
				}
				if (ob.getMappingObjectDefinitionId() == null) {
					ob.setMappingObjectDefinitionId(ob.getObjectDefinitionId());
				}
			}
			// set execution input value
			for (ObjectDefinition item : objDefsRegisted) {
				if (processModify && firstTable.getTblDesignId().equals(table.getTblDesignId()) && UPDATED_DATE.equalsIgnoreCase(item.getObjectDefinitionCode())) {
					for (SqlDesignInput designInput : sqlDesignInputs) {
						if (SYSTEM_DATE.equalsIgnoreCase(designInput.getSqlDesignInputCode())) {
							ExecutionInputValue execInput = GenerateScreenContruct.setExecutionInputValue(executionComponentIdTemp, designInput, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, item.getMappingObjectDefinitionId());
							lstExecutionInputValue.add(execInput);
							break;
						}
					}
				} else {
					for (SqlDesignInput designInput : sqlDesignInputs) {
						if((BusinessDesignConst.DataType.ENTITY.equals(item.getDataType()) || BusinessDesignConst.DataType.OBJECT.equals(item.getDataType()))
									&& (BusinessDesignConst.DataType.ENTITY.equals(designInput.getDataType()) || BusinessDesignConst.DataType.OBJECT.equals(designInput.getDataType()) )) {
							if (item.getTblDesignId() != null && designInput.getTableId() != null && designInput.getTableId() != 0
									&& item.getTblDesignId().equals(designInput.getTableId())) {
								ExecutionInputValue execInput = GenerateScreenContruct.setExecutionInputValue(executionComponentIdTemp, designInput, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, item.getMappingObjectDefinitionId());
								lstExecutionInputValue.add(execInput);
							}
						}
						if (item.getColumnId() != null && item.getColumnId().equals(designInput.getTempColumnId())) {
							if(item.getFromOrTo() != null) {
								if (item.getFromOrTo().equals(designInput.getFromOrTo())) {
									ExecutionInputValue execInput = GenerateScreenContruct.setExecutionInputValue(executionComponentIdTemp, designInput, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, item.getMappingObjectDefinitionId());
									lstExecutionInputValue.add(execInput);
								}
							} else {
								ExecutionInputValue execInput = GenerateScreenContruct.setExecutionInputValue(executionComponentIdTemp, designInput, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, item.getMappingObjectDefinitionId());
								lstExecutionInputValue.add(execInput);
							}
						}
					}
				}
			}
		}
		if (inputbeansRegisted != null && inputbeansRegisted.size() > 0) {
			if(processModify && firstTable.getTblDesignId().equals(table.getTblDesignId())) {
				int doOne = 0;
				for (InputBean in : inputbeansRegisted) {
					for (SqlDesignInput designInput : sqlDesignInputs) {
						if(doOne == 0 && UPDATED_DATE.equalsIgnoreCase(designInput.getSqlDesignInputCode()) && UPDATED_DATE.equalsIgnoreCase(in.getInputBeanCode())) {
							ExecutionInputValue execInput = GenerateScreenContruct.setExecutionInputValue(executionComponentIdTemp, designInput, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, in.getInputBeanId());
							lstExecutionInputValue.add(execInput);
							doOne++;
						}
					}
				}
			} else {
				for (InputBean in : inputbeansRegisted) {
					for (SqlDesignInput designInput : sqlDesignInputs) {
						if ((BusinessDesignConst.DataType.ENTITY.equals(in.getDataType()) && BusinessDesignConst.DataType.ENTITY.equals(designInput.getDataType())) || 
								(BusinessDesignConst.DataType.OBJECT.equals(in.getDataType()) && BusinessDesignConst.DataType.OBJECT.equals(designInput.getDataType()))) {
							if (in.getTblDesignId() != null && designInput.getTableId() != null && designInput.getTableId() != 0
									&& in.getTblDesignId().equals(designInput.getTableId())) {
								ExecutionInputValue execInput = GenerateScreenContruct.setExecutionInputValue(executionComponentIdTemp, designInput, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, in.getInputBeanId());
								lstExecutionInputValue.add(execInput);
							}
						} else if (in.getColumnId() != null && in.getColumnId().equals(designInput.getTempColumnId())) {
							if (in.getFromOrTo() != null) {
								if (in.getFromOrTo().equals(designInput.getFromOrTo())) {
									ExecutionInputValue execInput = GenerateScreenContruct.setExecutionInputValue(executionComponentIdTemp, designInput, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, in.getInputBeanId());
									lstExecutionInputValue.add(execInput);
								}
							} else {
								ExecutionInputValue execInput = GenerateScreenContruct.setExecutionInputValue(executionComponentIdTemp, designInput, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, in.getInputBeanId());
								lstExecutionInputValue.add(execInput);
							}
						}
					}
				}
			}
		}

		return lstExecutionInputValue;
	}
	
	/**
	  * 
	  * @param screenDesignDefault
	  * @param table
	  * @param executionComponentIdTemp
	  * @param inputbeansRegisted
	  * @param objDefsRegisted
	  * @param sqlDesignInputs
	  * @param allObjDefsRegisted
	  * @return
	  */
	private List<ExecutionInputValue> contructExecutionInputValueForCaseMasterDetails(ScreenDesignDefault screenDesignDefault, ModuleTableMapping table, Long executionComponentIdTemp, List<InputBean> inputbeansRegisted, List<ObjectDefinition> objDefsRegisted, List<SqlDesignInput> sqlDesignInputs, List<ObjectDefinition> allObjDefsRegisted) {
		if (objDefsRegisted != null && objDefsRegisted.size() > 0 && sqlDesignInputs != null && sqlDesignInputs.size() > 0) {
			// get tables previous
			List<ModuleTableMapping> lstTablePrevious = new ArrayList<ModuleTableMapping>();
			for (ModuleTableMapping obj : screenDesignDefault.getModuleTableMappings()) {
				if (obj.getTblDesignId().equals(table.getTblDesignId())) {
					break;
				} else {
					lstTablePrevious.add(obj);
				}
			}
			// if object definition is foreign key, execution input is reference object definition
			for (ObjectDefinition ob : objDefsRegisted) {
				if (DbDomainConst.YesNoFlg.YES.equals(ob.isKey(DbDomainConst.TblDesignKeyType.FK))) {
					Long toTableId = null, toColumnId = null;
					outerLoop: for (TableDesignForeignKey fk : allForeignKeyInProject) {
						for (ModuleTableMapping obj : lstTablePrevious) {
							if (fk.getFromTableId().equals(table.getTblDesignId()) && fk.getToTableId().equals(obj.getTblDesignId()) && fk.getFromColumnId().equals(ob.getColumnId())) {
								toTableId = fk.getToTableId();
								toColumnId = fk.getToColumnId();
								break outerLoop;
							}
						}
					}
					for (ObjectDefinition objDef : allObjDefsRegisted) {
						if (objDef.getTblDesignId() != null && objDef.getTblDesignId().equals(toTableId) && objDef.getColumnId() != null && objDef.getColumnId().equals(toColumnId)) {
							ob.setMappingObjectDefinitionId(objDef.getObjectDefinitionId());
						}
					}
				}
				if (ob.getMappingObjectDefinitionId() == null) {
					ob.setMappingObjectDefinitionId(ob.getObjectDefinitionId());
				}
			}
			// set execution input value
			for (ObjectDefinition item : objDefsRegisted) {
				for (SqlDesignInput designInput : sqlDesignInputs) {
					if (BusinessDesignConst.DataType.ENTITY.equals(item.getDataType()) && BusinessDesignConst.DataType.ENTITY.equals(designInput.getDataType())) {
						if (item.getTblDesignId() != null && designInput.getTableId() != null && designInput.getTableId() != 0 && item.getTblDesignId().equals(designInput.getTableId())) {
							ExecutionInputValue execInput = GenerateScreenContruct.setExecutionInputValue(executionComponentIdTemp, designInput, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, item.getMappingObjectDefinitionId());
							lstExecutionInputValue.add(execInput);
						}
					}
					if (item.getColumnId() != null && item.getColumnId().equals(designInput.getTempColumnId())) {
						ExecutionInputValue execInput = GenerateScreenContruct.setExecutionInputValue(executionComponentIdTemp, designInput, BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID, item.getMappingObjectDefinitionId());
						lstExecutionInputValue.add(execInput);
					}
				}
			}
		}
		if (inputbeansRegisted != null && inputbeansRegisted.size() > 0) {
			for (InputBean in : inputbeansRegisted) {
				for (SqlDesignInput designInput : sqlDesignInputs) {
					if ((BusinessDesignConst.DataType.ENTITY.equals(in.getDataType()) && BusinessDesignConst.DataType.ENTITY.equals(designInput.getDataType())) || (BusinessDesignConst.DataType.OBJECT.equals(in.getDataType()) && BusinessDesignConst.DataType.OBJECT.equals(designInput.getDataType()))) {
						if (in.getTblDesignId() != null && designInput.getTableId() != null && designInput.getTableId() != 0 && in.getTblDesignId().equals(designInput.getTableId())) {
							ExecutionInputValue execInput = GenerateScreenContruct.setExecutionInputValue(executionComponentIdTemp, designInput, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, in.getInputBeanId());
							lstExecutionInputValue.add(execInput);
						}
					} else if (in.getColumnId() != null && in.getColumnId().equals(designInput.getTempColumnId())) {
						ExecutionInputValue execInput = GenerateScreenContruct.setExecutionInputValue(executionComponentIdTemp, designInput, BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID, in.getInputBeanId());
						lstExecutionInputValue.add(execInput);
					}
				}
			}
		}

		return lstExecutionInputValue;
	}

	private List<ExecutionOutputValue> contructExecutionOutputValue(Long executionComponentIdTemp, List<ObjectDefinition> objDefsRegisted, List<SqlDesignOutput> sqlDesignOutputs, List<OutputBean> outputBeansRegisted) {
		if (objDefsRegisted != null && objDefsRegisted.size() > 0) {
			for (ObjectDefinition ob : objDefsRegisted) {
				for (SqlDesignOutput sqlOuput : sqlDesignOutputs) {
					if (ob.getObjectDefinitionId() != null && ob.getObjectDefinitionId().equals(sqlOuput.getObjectDefinitionId())) {
						ExecutionOutputValue obj = new ExecutionOutputValue();
						obj.setExecutionComponentId(executionComponentIdTemp);
						obj.setSqlDesignOutputId(sqlOuput.getSqlDesignOutputId());
						obj.setSqlDesignOutputCode(sqlOuput.getSqlDesignOutputCode());
						obj.setSqlDesignOutputName(sqlOuput.getSqlDesignOutputName());
						obj.setDataType(sqlOuput.getDataType());
						if (sqlOuput.getArrayFlag() != null) {
							Boolean arrayFlg = sqlOuput.getArrayFlag() == 1 ? true : false;
							obj.setArrayFlg(arrayFlg);
						}
						if (sqlOuput.getItemSeqNo() != null) {
							obj.setItemSequenceNo(sqlOuput.getItemSeqNo().toString());
						}
						obj.setTargetScope(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID);
						obj.setTargetId(ob.getObjectDefinitionId());
						lstExecutionOutputValue.add(obj);
					}
				}
			}
		} else if (outputBeansRegisted != null && outputBeansRegisted.size() > 0) {
			for (OutputBean ou : outputBeansRegisted) {
				for (SqlDesignOutput sqlOuput : sqlDesignOutputs) {
					if (ou.getOutputBeanId() != null && ou.getOutputBeanId().equals(sqlOuput.getOutputBeanId())) {
						ExecutionOutputValue obj = new ExecutionOutputValue();
						obj.setExecutionComponentId(executionComponentIdTemp);
						obj.setSqlDesignOutputId(sqlOuput.getSqlDesignOutputId());
						obj.setSqlDesignOutputCode(sqlOuput.getSqlDesignOutputCode());
						obj.setSqlDesignOutputName(sqlOuput.getSqlDesignOutputName());
						obj.setDataType(sqlOuput.getDataType());
						if (sqlOuput.getArrayFlag() != null) {
							Boolean arrayFlg = sqlOuput.getArrayFlag() == 1 ? true : false;
							obj.setArrayFlg(arrayFlg);
						}
						if (sqlOuput.getItemSeqNo() != null) {
							obj.setItemSequenceNo(sqlOuput.getItemSeqNo().toString());
						}
						obj.setTargetScope(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID);
						obj.setTargetId(ou.getOutputBeanId());
						lstExecutionOutputValue.add(obj);
					}
				}
			}
		}
		return lstExecutionOutputValue;
	}

	private List<ExecutionOutputValue> mappingExecutionOutputValueWithObjectDefinition(Long executionComponentIdTemp, List<ObjectDefinition> objDefsRegisted, List<SqlDesignOutput> sqlDesignOutputs) {
		if (objDefsRegisted != null && objDefsRegisted.size() > 0) {
			for (SqlDesignOutput sqlOuput : sqlDesignOutputs) {
				for (ObjectDefinition ob : objDefsRegisted) {
					if(StringUtils.isNotBlank(sqlOuput.getSqlDesignOutputCode()) && StringUtils.isNotBlank(ob.getObjectDefinitionCode()) && sqlOuput.getSqlDesignOutputCode().equals(ob.getObjectDefinitionCode())) {
						ExecutionOutputValue obj = new ExecutionOutputValue();
						obj.setExecutionComponentId(executionComponentIdTemp);
						obj.setSqlDesignOutputId(sqlOuput.getSqlDesignOutputId());
						obj.setSqlDesignOutputCode(sqlOuput.getSqlDesignOutputCode());
						obj.setSqlDesignOutputName(sqlOuput.getSqlDesignOutputName());
						obj.setDataType(sqlOuput.getDataType());
						if (sqlOuput.getArrayFlag() != null) {
							Boolean arrayFlg = sqlOuput.getArrayFlag() == 1 ? true : false;
							obj.setArrayFlg(arrayFlg);
						}
						if (sqlOuput.getItemSeqNo() != null) {
							obj.setItemSequenceNo(sqlOuput.getItemSeqNo().toString());
						}
						obj.setTargetScope(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID);
						obj.setTargetId(ob.getObjectDefinitionId());
						lstExecutionOutputValue.add(obj);
					}
				}
			}
		}
		return lstExecutionOutputValue;
	}
	
	private List<ExecutionOutputValue> mappingExecutionOutputValueWithOutputbean(Long executionComponentIdTemp, List<OutputBean> outputbeanRegisted, List<SqlDesignOutput> sqlDesignOutputs) {
		if (outputbeanRegisted != null && outputbeanRegisted.size() > 0) {
			for (SqlDesignOutput sqlOuput : sqlDesignOutputs) {
				for (OutputBean ou : outputbeanRegisted) {
					if (StringUtils.isNotBlank(sqlOuput.getSqlDesignOutputCode()) && StringUtils.isNotBlank(ou.getOutputBeanCode()) && sqlOuput.getSqlDesignOutputCode().equals(ou.getOutputBeanCode())) {
						ExecutionOutputValue obj = new ExecutionOutputValue();
						obj.setExecutionComponentId(executionComponentIdTemp);
						obj.setSqlDesignOutputId(sqlOuput.getSqlDesignOutputId());
						obj.setSqlDesignOutputCode(sqlOuput.getSqlDesignOutputCode());
						obj.setSqlDesignOutputName(sqlOuput.getSqlDesignOutputName());
						obj.setDataType(sqlOuput.getDataType());
						if (sqlOuput.getArrayFlag() != null) {
							Boolean arrayFlg = sqlOuput.getArrayFlag() == 1 ? true : false;
							obj.setArrayFlg(arrayFlg);
						}
						if (sqlOuput.getItemSeqNo() != null) {
							obj.setItemSequenceNo(sqlOuput.getItemSeqNo().toString());
						}
						obj.setTargetScope(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID);
						obj.setTargetId(ou.getOutputBeanId());
						lstExecutionOutputValue.add(obj);
					}
				}
			}
		}
		return lstExecutionOutputValue;
	}

	private List<ObjectDefinition> registerListObjectDefinitionUpdateAndInsert(ModuleTableMapping moduleTableMapping, Long businessLogicId, List<ObjectDefinition> lstObjDefRegisted, boolean isModifyScreen) {
		List<ObjectDefinition> lstObjDef = new ArrayList<ObjectDefinition>();
		Integer itemSeqNoObjDef = 0;
		if (lstObjDefRegisted != null && lstObjDefRegisted.size() > 0) {
			itemSeqNoObjDef = lstObjDefRegisted.get(lstObjDefRegisted.size() - 1).getItemSequenceNo() != null ? lstObjDefRegisted.get(lstObjDefRegisted.size() - 1).getItemSequenceNo() + 1 : 0;
		}
		List<Long> columnIds = new ArrayList<Long>();
		Map<Long, TableDesignDetails> mapTableDesignDetails = new HashMap<Long, TableDesignDetails>();
		for (TableDesignDetails item : moduleTableMapping.getListAllColumns()) {
			if (item.getColumnId() != null && !columnIds.contains(item.getColumnId())) {
				columnIds.add(item.getColumnId());
			}
		}
		List<TableDesignDetails> lstTableDesignDetails = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, columnIds);
		if (FunctionCommon.isNotEmpty(lstTableDesignDetails)) {
			for (TableDesignDetails obj : lstTableDesignDetails) {
				mapTableDesignDetails.put(obj.getColumnId(), obj);
			}
		}
		List<TableDesignDetails> lstAllColumnsOfTable = moduleTableMapping.getListAllColumns();
		Long parentObjDefId = null, tempObjDefId = 0L;
		Boolean arrFlag = true;
		
		if(isModifyScreen){
			// lst entity update
			String codeListEntityUpdate = "lst" + moduleTableMapping.getTblDesignCode() + "Update";
			ObjectDefinition objectDefinitionParentUpdate = GenerateScreenContruct.contructObjectDefinition(tempObjDefId, codeListEntityUpdate, moduleTableMapping.getTblDesignName(), DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE, businessLogicId, null, itemSeqNoObjDef++, null, moduleTableMapping.getTblDesignId(), null, null, arrFlag, null, null, true, false, TYPE_LIST_UPDATE);
	
			lstObjDef.add(objectDefinitionParentUpdate);
			parentObjDefId = tempObjDefId;
			tempObjDefId++;
	
			for (TableDesignDetails item : lstAllColumnsOfTable) {
				ObjectDefinition objectDefinition = new ObjectDefinition();
				Integer dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getBaseType());
				String parentIdString = parentObjDefId == null ? null : parentObjDefId.toString();
				TableDesignDetails tblDesignDetails = mapTableDesignDetails.get(item.getColumnId());
				objectDefinition = GenerateScreenContruct.contructObjectDefinition(tempObjDefId++, item.getCode(), item.getName(), dataType, businessLogicId, parentIdString, itemSeqNoObjDef++, item.getKeyType(), item.getTableDesignId(), item.getColumnId(), tblDesignDetails.getGroupBaseTypeId(), false, null, null, false, false, TYPE_LIST_UPDATE);
				objectDefinition.setBaseType(item.getBaseType());
				lstObjDef.add(objectDefinition);
			}
		}
		// lst entity insert
		String codeListEntityUpdateInsert = "lst" + moduleTableMapping.getTblDesignCode() + "Insert";
		ObjectDefinition objectDefinitionParentInsert = GenerateScreenContruct.contructObjectDefinition(tempObjDefId, codeListEntityUpdateInsert, moduleTableMapping.getTblDesignName(), DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE, businessLogicId, null, itemSeqNoObjDef++, null, moduleTableMapping.getTblDesignId(), null, null, arrFlag, null, null, true, false, TYPE_LIST_INSERT);

		lstObjDef.add(objectDefinitionParentInsert);
		parentObjDefId = tempObjDefId;
		tempObjDefId++;

		for (TableDesignDetails item : lstAllColumnsOfTable) {
			ObjectDefinition objectDefinition = new ObjectDefinition();
			Integer dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getBaseType());
			String parentIdString = parentObjDefId == null ? null : parentObjDefId.toString();
			TableDesignDetails tblDesignDetails = mapTableDesignDetails.get(item.getColumnId());
			objectDefinition = GenerateScreenContruct.contructObjectDefinition(tempObjDefId++, item.getCode(), item.getName(), dataType, businessLogicId, parentIdString, itemSeqNoObjDef++, item.getKeyType(), item.getTableDesignId(), item.getColumnId(), tblDesignDetails.getGroupBaseTypeId(), false, null, null, false, false, TYPE_LIST_INSERT);
			objectDefinition.setBaseType(item.getBaseType());
			lstObjDef.add(objectDefinition);
		}

		if (lstObjDef.size() > 0) {
			Map<String, Long> hashMapId = new HashMap<String, Long>();
			Long sequenceObjectDefinitionItem = businessDesignRepository.getSequencesObjectDefinition(lstObjDef.size() - 1);
			Long startSequence = sequenceObjectDefinitionItem - (lstObjDef.size() - 1);
			for (ObjectDefinition obj : lstObjDef) {
				hashMapId.put(obj.getObjectDefinitionId(), startSequence);
				if (obj.getParentObjectDefinitionId() != null) {
					Long parentObjectDefinitionId = hashMapId.get(obj.getParentObjectDefinitionId());
					obj.setParentObjectDefinitionId(parentObjectDefinitionId.toString());
				}
				obj.setObjectDefinitionId(startSequence.toString());
				startSequence++;
			}
			businessDesignRepository.registerObjectDefinition(lstObjDef);
		}
		return lstObjDef;
	}
	
	/**
	 * @param businessLogicId
	 * @param datasourceId
	 * @param lstObjDefRegisted
	 * @return
	 */
	private List<ObjectDefinition> registerObjectDefinitionOfDatasourceValue(Long businessLogicId, Long datasourceId, List<ObjectDefinition> lstObjDefRegisted, String rootSqlDesignCode) {
		SqlDesignOutput[] arrSqlDesignOutput = sqlDesignOutputRepository.findAllBySqlDesignId(datasourceId);
		Integer itemSeqNoObjDef = 0;
		List<ObjectDefinition> lstObjDef = new ArrayList<ObjectDefinition>();
		if (arrSqlDesignOutput != null && arrSqlDesignOutput.length > 0) {
			if (lstObjDefRegisted != null && lstObjDefRegisted.size() > 0) {
				itemSeqNoObjDef = lstObjDefRegisted.get(lstObjDefRegisted.size() - 1).getItemSequenceNo() != null ? lstObjDefRegisted.get(lstObjDefRegisted.size() - 1).getItemSequenceNo() + 1 : 0;
			}

			Long tempId = 0l, parentId = null, rootId = 0L;
			for (int i = 0; i < arrSqlDesignOutput.length; i++) {
				// do once : add root object
				if(ZERO.equals(tempId)) {
					ObjectDefinition ob = new ObjectDefinition();
					ob.setObjectDefinitionId(tempId.toString());
					ob.setObjectDefinitionCode(rootSqlDesignCode);
					ob.setObjectDefinitionName(rootSqlDesignCode);
					ob.setDataType(BusinessDesignConst.DataType.OBJECT);
					ob.setArrayFlg(true);
					ob.setBusinessLogicId(businessLogicId);
					ob.setParentObjectDefinitionId(null);
					ob.setItemSequenceNo(itemSeqNoObjDef++);
					lstObjDef.add(ob);

					tempId++;
				}
				SqlDesignOutput sqlOutput = arrSqlDesignOutput[i];

				ObjectDefinition ob = new ObjectDefinition();
				ob.setObjectDefinitionId(tempId.toString());
				ob.setObjectDefinitionCode(sqlOutput.getSqlDesignOutputCode());
				ob.setObjectDefinitionName(sqlOutput.getSqlDesignOutputName());
				ob.setDataType(sqlOutput.getDataType());
				if ((sqlOutput.getDataType() != null && BusinessDesignConst.DataType.OBJECT.equals(sqlOutput.getDataType()))
						|| (sqlOutput.getDataType() != null && BusinessDesignConst.DataType.ENTITY.equals(sqlOutput.getDataType()))) {
					parentId = tempId;
					ob.setArrayFlg(false);
				}
				if (sqlOutput.getSqlDesignOutputParentId() == null) {
					ob.setParentObjectDefinitionId(rootId.toString());
				} else {
					ob.setParentObjectDefinitionId(parentId.toString());
				}
				ob.setBusinessLogicId(businessLogicId);
				ob.setTblDesignId(sqlOutput.getTableId());
				ob.setColumnId(sqlOutput.getColumnId());
				ob.setItemSequenceNo(itemSeqNoObjDef++);
				lstObjDef.add(ob);

				tempId++;
			}
			// set datasource value to object definition
			if (lstObjDef.size() > 0) {
				Map<String, Long> hashMapId = new HashMap<String, Long>();
				Long sequenceObjectDefinitionItem = businessDesignRepository.getSequencesObjectDefinition(lstObjDef.size() - 1);
				Long startSequence = sequenceObjectDefinitionItem - (lstObjDef.size() - 1);
				for (ObjectDefinition obj : lstObjDef) {
					hashMapId.put(obj.getObjectDefinitionId(), startSequence);
					if (obj.getParentObjectDefinitionId() != null) {
						Long parentObjectDefinitionId = hashMapId.get(obj.getParentObjectDefinitionId());
						obj.setParentObjectDefinitionId(parentObjectDefinitionId.toString());
					}
					obj.setObjectDefinitionId(startSequence.toString());
					startSequence++;
				}
				businessDesignRepository.registerObjectDefinition(lstObjDef);
			}
		}
		return lstObjDef;
	}

	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param datasourceId
	 * @param assignComponentId
	 * @param lstOutputRegisted
	 * @return
	 */
	private List<OutputBean> registerOutputbeanOfDatasourceValue(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long businessLogicId, TableDesignDetails column, List<OutputBean> lstOutputRegisted, String rootSqlDesignCode) {
		Long datasourceId = column.getDatasourceId();
		SqlDesignOutput[] arrSqlDesignOutput = sqlDesignOutputRepository.findAllBySqlDesignId(datasourceId);
		Integer itemSeqNoOutputbean = 0;
		List<OutputBean> lstOutput = new ArrayList<OutputBean>();
		if (arrSqlDesignOutput != null && arrSqlDesignOutput.length > 0) {
			if (lstOutputRegisted != null && lstOutputRegisted.size() > 0) {
				itemSeqNoOutputbean = lstOutputRegisted.get(lstOutputRegisted.size() - 1).getItemSequenceNo() != null ? lstOutputRegisted.get(lstOutputRegisted.size() - 1).getItemSequenceNo() + 1 : 0;
			}
			Long tempId = 0l, parentId = null, rootId = 0L;
			for (int i = 0; i < arrSqlDesignOutput.length; i++) {
				if (ZERO.equals(tempId)) {
					OutputBean output = new OutputBean();
					output.setOutputBeanId(tempId.toString());
					output.setOutputBeanCode(rootSqlDesignCode);
					output.setOutputBeanName(rootSqlDesignCode);
					output.setDataType(BusinessDesignConst.DataType.OBJECT);
					output.setBusinessLogicId(businessLogicId);
					output.setItemSequenceNo(itemSeqNoOutputbean++);
					output.setArrayFlg(true);
					output.setParentOutputBeanId(null);
					lstOutput.add(output);

					tempId++;
				}
				SqlDesignOutput sqlOutput = arrSqlDesignOutput[i];

				OutputBean output = new OutputBean();
				output.setOutputBeanId(tempId.toString());
				output.setOutputBeanCode(sqlOutput.getSqlDesignOutputCode());
				output.setOutputBeanName(sqlOutput.getSqlDesignOutputName());
				output.setDataType(sqlOutput.getDataType());
				output.setBusinessLogicId(businessLogicId);
				output.setTblDesignId(sqlOutput.getTableId());
				output.setColumnId(sqlOutput.getColumnId());
				output.setItemSequenceNo(itemSeqNoOutputbean++);
				if ((sqlOutput.getDataType() != null && BusinessDesignConst.DataType.OBJECT.equals(sqlOutput.getDataType())) || (sqlOutput.getDataType() != null && BusinessDesignConst.DataType.ENTITY.equals(sqlOutput.getDataType()))) {
					parentId = tempId;
					output.setArrayFlg(false);
				} else {
					if (sqlOutput.getArrayFlag() != null) {
						Boolean arrayFlg = sqlOutput.getArrayFlag() == 1 ? true : false;
						output.setArrayFlg(arrayFlg);
					}
				}
				if(sqlOutput.getSqlDesignOutputParentId() == null) {
					output.setParentOutputBeanId(rootId.toString());
				} else {
					output.setParentOutputBeanId(parentId.toString());
				}
				lstOutput.add(output);

				tempId++;
			}

			// mapping screen item
			ScreenItem screenItemMapping = null;
			for(ScreenItem item : screenDesignDefault.getLstScreenItem()) {
				if(item.getScreenId().equals(sObj.getScreenId()) && item.getColumnId().equals(column.getColumnId())
						&& !DbDomainConst.LogicDataType.LABEL.equals(item.getLogicalDataType())
						&& !DbDomainConst.LogicDataType.LINK.equals(item.getLogicalDataType())
						&& !DbDomainConst.LogicDataType.BUTTON.equals(item.getLogicalDataType())
						&& !DbDomainConst.LogicDataType.LINK_DYNAMIC.equals(item.getLogicalDataType())
						) {
					screenItemMapping = item;
					break;
				}
			}
			if (FunctionCommon.isNotEmpty(lstOutput)) {
				// mapping datasource
//				boolean flagDatasourceIsRoot = true;
				ArrayList<ScreenItemOutput> lstScreenItemMapping = new ArrayList<ScreenItemOutput>();
				OutputBean ouRoot = lstOutput.get(0);
//				OutputBean ouFirstChild = null;
//				if (lstOutput.size() > 1) {
//					ouFirstChild = lstOutput.get(1);
//				}
//				if (ouFirstChild != null && (BusinessDesignConst.DataType.ENTITY.equals(ouFirstChild.getDataType()) || BusinessDesignConst.DataType.OBJECT.equals(ouFirstChild.getDataType()))) {
//					flagDatasourceIsRoot = false;
//				}
//				if (flagDatasourceIsRoot) {
//					ScreenItemOutput screenItemOutputRoot = new ScreenItemOutput();
//					Long outputBeanRootId = ouRoot.getOutputBeanId() != null ? Long.parseLong(ouRoot.getOutputBeanId()) : null;
//					screenItemOutputRoot.setOutputBeanId(outputBeanRootId);
//					screenItemOutputRoot.setScreenItemId(screenItemMapping.getScreenItemId());
//					screenItemOutputRoot.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_DATASOURCE);
//					lstScreenItemMapping.add(screenItemOutputRoot);
//					ouRoot.setLstScreenItemMapping(lstScreenItemMapping);
//				}
//				else {
//					ScreenItemOutput screenItemOutput = new ScreenItemOutput();
//					Long outputBeanFirstChildId = ouFirstChild.getOutputBeanId() != null ? Long.parseLong(ouFirstChild.getOutputBeanId()) : null;
//					screenItemOutput.setOutputBeanId(outputBeanFirstChildId);
//					screenItemOutput.setScreenItemId(screenItemMapping.getScreenItemId());
//					screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_DATASOURCE);
//					lstScreenItemMapping.add(screenItemOutput);
//					ouFirstChild.setLstScreenItemMapping(lstScreenItemMapping);
//				}
				ScreenItemOutput screenItemOutputRoot = new ScreenItemOutput();
				Long outputBeanRootId = ouRoot.getOutputBeanId() != null ? Long.parseLong(ouRoot.getOutputBeanId()) : null;
				screenItemOutputRoot.setOutputBeanId(outputBeanRootId);
				screenItemOutputRoot.setScreenItemId(screenItemMapping.getScreenItemId());
				screenItemOutputRoot.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_DATASOURCE);
				lstScreenItemMapping.add(screenItemOutputRoot);
				ouRoot.setLstScreenItemMapping(lstScreenItemMapping);
					
				// mapping option display, option submit
				if(column.getOptionValue() != null) {
					lstScreenItemMapping = new ArrayList<ScreenItemOutput>();
					SqlDesignOutput sqlOutputTemp = sqlDesignOutputRepository.findById(column.getOptionValue());
					for (OutputBean output : lstOutput) {
						if (output.getColumnId() != null && output.getColumnId().equals(sqlOutputTemp.getColumnId())) {
							ScreenItemOutput screenItemOutput = new ScreenItemOutput();
							Long outputBeanId = output.getOutputBeanId() != null ? Long.parseLong(output.getOutputBeanId()) : null;
							screenItemOutput.setOutputBeanId(outputBeanId);
							screenItemOutput.setScreenItemId(screenItemMapping.getScreenItemId());
							screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_SUBMIT);
							lstScreenItemMapping.add(screenItemOutput);
							if(FunctionCommon.isNotEmpty(output.getLstScreenItemMapping())) {
								for(ScreenItemOutput obj : output.getLstScreenItemMapping()) {
									lstScreenItemMapping.add(obj);
								}
							}
							output.setLstScreenItemMapping(lstScreenItemMapping);
							break;
						}
					}
				}

				if (column.getOptionLabel() != null) {
					lstScreenItemMapping = new ArrayList<ScreenItemOutput>();
					SqlDesignOutput sqlOutputTemp = sqlDesignOutputRepository.findById(column.getOptionLabel());
					if (sqlOutputTemp == null) {
						throw new BusinessException(ResultMessages.error().add(ScreenDesignConst.ERR_SCREENDESIGN_0433, column.getCode(), column.getTableDesignName()));
					}
					for (OutputBean output : lstOutput) {
						if (output.getColumnId() != null && output.getColumnId().equals(sqlOutputTemp.getColumnId())) {
							ScreenItemOutput screenItemOutput = new ScreenItemOutput();
							Long outputBeanId = output.getOutputBeanId() != null ? Long.parseLong(output.getOutputBeanId()) : null;
							screenItemOutput.setOutputBeanId(outputBeanId);
							screenItemOutput.setScreenItemId(screenItemMapping.getScreenItemId());
							screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_DISPLAY);
							lstScreenItemMapping.add(screenItemOutput);
							if(FunctionCommon.isNotEmpty(output.getLstScreenItemMapping())) {
								for(ScreenItemOutput obj : output.getLstScreenItemMapping()) {
									lstScreenItemMapping.add(obj);
								}
							}
							output.setLstScreenItemMapping(lstScreenItemMapping);
							break;
						}
					}
				}
			}

			// set datasource value to output bean
			if (lstOutput.size() > 0) {
				Map<String, Long> hashMapId = new HashMap<String, Long>();
				Long sequenceOutputBeanItem = businessDesignRepository.getSequencesOutputBean(lstOutput.size() - 1);
				Long startSequence = sequenceOutputBeanItem - (lstOutput.size() - 1);
				for (OutputBean obj : lstOutput) {
					hashMapId.put(obj.getOutputBeanId(), startSequence);
					if (obj.getParentOutputBeanId() != null) {
						Long parentOutputbeanId = hashMapId.get(obj.getParentOutputBeanId());
						obj.setParentOutputBeanId(parentOutputbeanId.toString());
					}
					obj.setOutputBeanId(startSequence.toString());
					startSequence++;
				}
				businessDesignRepository.registerOutputBean(lstOutput);
			}
		}
		return lstOutput;
	}

	/**
	 *
	 */
	private void contructNewListComponents() {
		lstNavigatorComponents = new ArrayList<NavigatorComponent>();
		lstAssignComponents = new ArrayList<AssignComponent>();
		lstAssignDetails = new ArrayList<AssignDetail>();
		listLoopComponent = new ArrayList<LoopComponent>();
		lstFeedbacks = new ArrayList<FeedbackComponent>();
		lstMessageParamettersOfFeedback = new ArrayList<MessageParameter>();
		lstBusinessCheckComponents = new ArrayList<BusinessCheckComponent>();
		lstCheckDetails = new ArrayList<BusinessCheckDetail>();
		lstDetailsContents = new ArrayList<BusinessDetailContent>();
		lstMessageParamettersOfBusinessCheckDetail = new ArrayList<MessageParameter>();
		lstNavigatorDetails = new ArrayList<NavigatorDetail>();
		lstExecutionComponent = new ArrayList<ExecutionComponent>();
		lstExecutionInputValue = new ArrayList<ExecutionInputValue>();
		lstExecutionOutputValue = new ArrayList<ExecutionOutputValue>();
		lstIfComponents = new ArrayList<IfComponent>();
		lstFormulaDefinitions = new ArrayList<FormulaDefinition>();
		lstFormulaDefinitionsForAssign = new ArrayList<FormulaDefinition>();
		lstUtilityComponents = new ArrayList<UtilityComponent>();
		mNameParameter = new HashMap<String, String>();
		lstDownloadFileComponents = new ArrayList<DownloadFileComponent>();
	}

	// get all user define code list
	private List<UserDefineCodelistDetails> userDefineCodelistDetails;;
	// get all code list
	private Long[] lstCodeListId;
	// get all sql design
	private Long[] lstSqlDesignId;
	// get all autocomplete design
	private Long[] lstAutocompleteId;
	// get all foreign key
	private Boolean stillHaveConstrainFlag;
	private Map<String, List<ScreenItem>> mapScreenItemHidden;
	private List<TableDesignKeyItem> allKeys;
	private List<TableDesignForeignKey> allForeignKeyInProject;
	private List<SqlDesign> allSqlBuilderInProject;
	private List<AutocompleteDesign> allAutocompleInProject;

	private Long workingProjectId;
	private Timestamp systemDate;
	private Long accountId;

	private Map<Long, Integer> mapScreenAreaItemSeqNo = new HashMap<Long, Integer>();

	private Map<Long, Integer> mapScreenAreaGroupItemSeqNo = new HashMap<Long, Integer>();

	private void putMapScreenAreaItem(Long areaId, Map<Long, Integer> map) {
		if (map.containsKey(areaId)) {
			map.put(areaId, map.get(areaId) + 1);
		} else {
			map.put(areaId, 1);
		}
	}

	private Integer getItemSeqNo(Long areaId, Map<Long, Integer> map) {
		putMapScreenAreaItem(areaId, map);
		return map.get(areaId);
	}

	private void setDataOfActionDelete(ScreenDesignDefault screenDesignDefault, List<ScreenItem> lstScreenItems, ScreenForm form, ScreenArea area, ScreenAction screenAction) {
		screenDesignDefault.setLstScreenItemActionDelete(lstScreenItems);
		screenDesignDefault.setFormActionDelete(form);
		screenDesignDefault.setAreaActionDelete(area);
		screenDesignDefault.setScreenActionofActionDelete(screenAction);
	}

	private boolean isDuplicatedCheck(ModuleTableMapping table,List<InputBean> listInputBeanInformation){
		//		Map<Long,Long> mapNeedColumns = new HashMap<Long, Long>();
		//		for (TableDesignKeyItem key : allKeys) {
		//			if (key.getTableDesignId().equals(table.getTblDesignId()) && DbDomainConst.TblDesignKeyType.PK.equals(key.getType())) {
		//				mapNeedColumns.put(key.getColumnId(), key.getColumnId());
		//			}
		//			if (key.getTableDesignId().equals(table.getTblDesignId()) && DbDomainConst.TblDesignKeyType.UNIQUE.equals(key.getType())) {
		//				mapNeedColumns.put(key.getColumnId(), key.getColumnId());
		//			}
		//		}
		//		if(mapNeedColumns.size()>0){
		//			for (InputBean in : listInputBeanInformation){
		//				if(mapNeedColumns.containsKey(in.getColumnId())){
		//					return true;
		//				}
		//			}
		//		}
		//		return false;

		//pass Quater3
		return true;
	}

	private boolean isGenerateScreen(ScreenDesignDefault screenDesignDefault, Integer type){
		boolean flag = false;
		ModuleTableMapping[] tables = screenDesignDefault.getModuleTableMappings();
		if(tables.length >= 1){
			if(SINGLE_TYPE.equals(tables[0].getTableMappingType())){
				flag = true;
			}else if(LIST_TYPE.equals(tables[0].getTableMappingType())){
				if(DbDomainConst.ScreenPatternType.SEARCH.equals(type)){
					flag = true;
				}else{
					flag = false;
				}
			}
		}else{
			flag = true;
		}
		return flag;
	}
	/**
	 * @param screenDesignDefault
	 * @param sqlDesignName
	 * @param sqlpattern
	 * @param table
	 * @param inputBeansRegisted
	 * @param objDefsRegisted
	 * @return
	 */
	private SqlDesign autoGenerateSqlbuilderUsingOutputBean(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, String sqlDesignName, String sqlDesignCode, int sqlpattern, ModuleTableMapping table, List<InputBean> inputBeansRegisted, List<OutputBean> outputBeansRegisted, Boolean flgHaveCondition, Boolean flgHaveResult, Boolean flgSearchScreen, Boolean isProcessSearch, Boolean isGetTotal, Boolean initialSearch, Boolean flgBlogicProcessRegister) {
		Integer itemSeqNo = 1;
		List<Long> lstColumnIdOfThisTable = new ArrayList<Long>();
		List<SqlDesignTable> lstSqlDesignTable = new ArrayList<SqlDesignTable>();
		List<SqlDesignTableItem> lstSqlDesignTableItem = new ArrayList<SqlDesignTableItem>();
		List<SqlDesignInput> lstSqlDesignInput = new ArrayList<SqlDesignInput>();
		List<SqlDesignValue> lstSqlDesignValue = new ArrayList<SqlDesignValue>();
		List<SqlDesignCondition> lstCondition = new ArrayList<SqlDesignCondition>();
		List<SqlDesignResult> lstSqlDesignResult = new ArrayList<SqlDesignResult>();
		List<SqlDesignOutput> lstSqlDesignOutput = new ArrayList<SqlDesignOutput>();
		List<TableDesignDetails> lstData = new ArrayList<TableDesignDetails>();
		List<SqlDesignInput> allSqlDesignInputs = new ArrayList<SqlDesignInput>();
		List<SqlDesignOutput> allSqlDesignOutputs = new ArrayList<SqlDesignOutput>();
		SqlDesign sqlDesign = null;

		if (!flgSearchScreen) {
			ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings() != null ? screenDesignDefault.getModuleTableMappings()[0] : null;
			// TrungDV : gen Advance SQL for insert first table to return Primary key in case : master - details
			Boolean flgGenAdvanceInsert = false;
			Long sqlDesignId = null;
			if (flgBlogicProcessRegister != null && flgBlogicProcessRegister) {
				boolean flgHavePKHidden = false;
				for(TableDesignDetails column : firstTable.getListTableDesignDetail()) {
					if (DbDomainConst.YesNoFlg.YES.equals(column.isKey(DbDomainConst.TblDesignKeyType.PK)) && DbDomainConst.DisplayType.UNUSED.equals(column.getDisplayType())
							&& DbDomainConst.YesNoFlg.YES.equals(column.getAutoIncrementFlag())
							) {
						flgHavePKHidden = true;
						break;
					}
				}
				if (flgHavePKHidden && screenDesignDefault.getModuleTableMappings().length > 1 && firstTable != null && table != null && table.getTblDesignId().equals(firstTable.getTblDesignId()) && SINGLE_TYPE.equals(firstTable.getTableMappingType())) {
					flgGenAdvanceInsert = true;
				}
			}
			if(!flgGenAdvanceInsert) {
				sqlDesign = GenerateScreenContruct.populateSqlDesign(DomainDatatypeConst.SQLDesignType.SQL_BUILDER, sqlDesignName, sqlDesignCode, sqlpattern, screenDesignDefault, screenDesignObj, table, isGetTotal, 0);
				sqlDesignRepository.register(sqlDesign);
				sqlDesignId = sqlDesign.getSqlDesignId();
			} else {
				sqlDesign = GenerateScreenContruct.populateSqlDesign(DomainDatatypeConst.SQLDesignType.ADVANCED_SQL, sqlDesignName, sqlDesignCode, sqlpattern, screenDesignDefault, screenDesignObj, table, false, null);
			}
			// end by TrungDV
			if (SqlPattern.SELECT == sqlpattern) {
				if (inputBeansRegisted != null && inputBeansRegisted.size() > 0) {
					List<Long> lstAllColumnIdOfInputbeans = new ArrayList<Long>();
					for (InputBean in : inputBeansRegisted) {
						if (in.getTblDesignId() != null && in.getTblDesignId().equals(table.getTblDesignId())) {
							lstColumnIdOfThisTable.add(in.getColumnId());
						}
						lstAllColumnIdOfInputbeans.add(in.getColumnId());
					}
					List<TableDesignForeignKey> fkConstrains = new ArrayList<TableDesignForeignKey>();
					// is first table
					if (lstColumnIdOfThisTable.size() > 0) {
						lstData = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIdOfThisTable);
						// only register sql design table
						SqlDesignTable sqlTable = GenerateScreenContruct.setSqlDesignTable(sqlDesignId, null, null, table.getTblDesignId(), null, 0, table.getTblDesignName(), null, table.getTblDesignCode(), null);
						lstSqlDesignTable.add(sqlTable);
					}
					// is children table
					else {
						if (stillHaveConstrainFlag) {
							int indexOfCurrentTable = 0;
							for (int i = 0; i < screenDesignDefault.getModuleTableMappings().length; i++) {
								if (screenDesignDefault.getModuleTableMappings()[i].getTblDesignId().equals(table.getTblDesignId())) {
									indexOfCurrentTable = i;
								}
							}
							boolean haveConstrainBetweenTwoTable = false;
							outerloop: for (int i = indexOfCurrentTable; i > 0; i--) {
								ModuleTableMapping currentTable = screenDesignDefault.getModuleTableMappings()[i];
								ModuleTableMapping previousTable = screenDesignDefault.getModuleTableMappings()[i - 1];
								for (TableDesignForeignKey fk : allForeignKeyInProject) {
									if (fk.getFromTableId().equals(currentTable.getTblDesignId()) && fk.getToTableId().equals(previousTable.getTblDesignId())) {
										fkConstrains.add(fk);
										haveConstrainBetweenTwoTable = true;
										break;
									}
								}
								if (!haveConstrainBetweenTwoTable) {
									break outerloop;
								}
							}
							if (!haveConstrainBetweenTwoTable) {
								fkConstrains.clear();
								stillHaveConstrainFlag = false;
							}
						}
						if (fkConstrains.size() > 0) {
							lstData = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstAllColumnIdOfInputbeans);
							Integer itemSeqNoSqlTable = 1;
							for (TableDesignForeignKey fk : fkConstrains) {
								SqlDesignTable sqlTable = GenerateScreenContruct.setSqlDesignTable(sqlDesignId, itemSeqNoSqlTable.longValue(), ONE, fk.getFromTableId(), fk.getToTableId(), itemSeqNoSqlTable, fk.getFromTableName(), fk.getToTableName(), fk.getFromTableCode(), fk.getToTableCode());
								lstSqlDesignTable.add(sqlTable);

								SqlDesignTableItem sqlDesignTableItem = GenerateScreenContruct.setSqlDesignTableItem(itemSeqNoSqlTable.longValue(), itemSeqNoSqlTable, fk);
								lstSqlDesignTableItem.add(sqlDesignTableItem);
								itemSeqNoSqlTable++;
							}
						} else {
							List<ScreenItem> pkOfTable = findKeyOfTableUsingScreenItems(table, screenDesignObj, screenDesignDefault);
							List<Long> lstColumnId = new ArrayList<Long>();
							for (ScreenItem itemPK : pkOfTable) {
								lstColumnId.add(itemPK.getColumnId());
							}
							lstData = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnId);
							// only register sql design table
							SqlDesignTable sqlTable = GenerateScreenContruct.setSqlDesignTable(sqlDesignId, null, null, table.getTblDesignId(), null, 0, table.getTblDesignName(), null, table.getTblDesignCode(), null);
							lstSqlDesignTable.add(sqlTable);
						}
					}

					if (lstData.size() > 0) {
						boolean flagLogicCode = false;
						for (TableDesignDetails in : lstData) {
							// sql design input
							SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, in.getName(), in.getCode(), in.getBaseType(), itemSeqNo, in.getColumnId(), in.getTableDesignId(), false, null, false, null, DbDomainConst.DesignType.DESIGN_TYPE, null, null);
							lstSqlDesignInput.add(sqlInput);
							allSqlDesignInputs.add(sqlInput);
							// sql design condition
							if (flgHaveCondition) {
								SqlDesignCondition condition = GenerateScreenContruct.setSqlDesignCondition(sqlDesignId, in.getTableDesignId(), in.getColumnId(), "0", itemSeqNo, in.getTableDesignName(), in.getName(), in.getBaseType(), itemSeqNo.toString());
								if (!flagLogicCode) {
									condition.setLogicCode("");
									flagLogicCode = true;
								} else {
									condition.setLogicCode("0");
								}
								lstCondition.add(condition);
							}

							itemSeqNo++;
						}
					}
				}
			} 
			// sql design result and output
			itemSeqNo = 1;
			Integer itemSeqNoOutput = 1;
			List<TableDesignDetails> allColumns =  table.getListAllColumns();
			if (SqlPattern.SELECT == sqlpattern && outputBeansRegisted != null && outputBeansRegisted.size() > 0) {
				// sql design result
				List<OutputBean> lstOutputBeansInformation = businessDesignRepository.findColumnInformationOfOutputbean(outputBeansRegisted);
				if (lstOutputBeansInformation != null && lstOutputBeansInformation.size() > 0) {
					Map<String, OutputBean> mapOutputBean = new HashMap<String, OutputBean>();
					for (OutputBean ou : lstOutputBeansInformation) {
						for (SqlDesignTable sqlTable : lstSqlDesignTable) {
							if ((ou.getTblDesignId() != null && ou.getTblDesignId().equals(sqlTable.getTableId())) || (ou.getTblDesignId() != null && ou.getTblDesignId().equals(sqlTable.getJoinTableId()))) {
								mapOutputBean.put(ou.getOutputBeanId(), ou);
							}
						}
					}
					List<Long> columnIdsAdded = new ArrayList<Long>();
					for (OutputBean all : outputBeansRegisted) {
						OutputBean ou = mapOutputBean.get(all.getOutputBeanId());
						if (ou != null) {
							if (ou.getTblDesignId() != null && ou.getTblDesignId().equals(table.getTblDesignId())) {
								ou.setEnabledFlag(1);
							} else {
								ou.setEnabledFlag(0);
							}
							// sql design result
							if (ou.getDataType() != null && !DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ou.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ou.getDataType())) {
								SqlDesignResult result = GenerateScreenContruct.setSqlDesignResult(sqlDesignId, BLANK, ou.getTblDesignId(), ou.getColumnId(), itemSeqNo, ou.getTblDesignName(), ou.getColumnName(), ou.getEnabledFlag(), ou.getBaseType());
								lstSqlDesignResult.add(result);
								columnIdsAdded.add(ou.getColumnId());
							}
						}
						itemSeqNo++;
					}
					for (TableDesignDetails col : allColumns) {
						if (!columnIdsAdded.contains(col.getColumnId())) {
							SqlDesignResult result = GenerateScreenContruct.setSqlDesignResult(sqlDesignId, BLANK, col.getTableDesignId(), col.getColumnId(), itemSeqNo, table.getTblDesignName(), col.getName(), 0, col.getBaseType());
							lstSqlDesignResult.add(result);
							itemSeqNo++;
						}
					}
				}
				// sql design output
				Map<String, OutputBean> mapOutputBean = new HashMap<String, OutputBean>();
				for (OutputBean ou : outputBeansRegisted) {
					if (ou.getTblDesignId() != null && ou.getTblDesignId().equals(table.getTblDesignId())) {
						ou.setEnabledFlag(1);
					} else {
						ou.setEnabledFlag(0);
					}
					if (ou.getEnabledFlag() != null && 1 == ou.getEnabledFlag().intValue()) {
						mapOutputBean.put(ou.getOutputBeanId(), ou);
						Integer arrayFlg = ou.getArrayFlg().booleanValue() ? 1 : 0;
						Long tempSqlOutputId = ou.getOutputBeanId() != null ? Long.parseLong(ou.getOutputBeanId()) : null;
						Long tempSqlOutputParentId = ou.getParentOutputBeanId() != null ? Long.parseLong(ou.getParentOutputBeanId()) : null;
						GenerateScreenContruct.buildDesignTypeAndObjectType(null, null, ou, mapOutputBean);
						SqlDesignOutput sqlOutput = GenerateScreenContruct.setSqlDesignOutput(tempSqlOutputId, sqlDesignId, ou.getOutputBeanName(), ou.getOutputBeanCode(), ou.getDataType(), tempSqlOutputParentId, arrayFlg,
							itemSeqNoOutput, ou.getOutputBeanId(), null, null, ou.getTblDesignId(), ou.getColumnId(), ou.getDesignType(), ou.getObjectType());
						lstSqlDesignOutput.add(sqlOutput);
						allSqlDesignOutputs.add(sqlOutput);
					}
					itemSeqNoOutput++;
				}
			}
		}

		if (!flgSearchScreen) {
			if (lstSqlDesignInput.size() > 0) {
				SqlDesignInput[] arrSqlDesignInput = lstSqlDesignInput.toArray(new SqlDesignInput[lstSqlDesignInput.size()]);
				Long startSequenceSqlInput = sqlDesignInputRepository.preserveIds(arrSqlDesignInput.length) - arrSqlDesignInput.length;
				Map<Long, Long> mapSqlInput = new HashMap<Long, Long>();
				for (int i = 0; i < arrSqlDesignInput.length; i++) {
					mapSqlInput.put(arrSqlDesignInput[i].getSqlDesignInputId(), startSequenceSqlInput);
					if (arrSqlDesignInput[i].getSqlDesignInputParentId() != null) {
						arrSqlDesignInput[i].setSqlDesignInputParentId(mapSqlInput.get(arrSqlDesignInput[i].getSqlDesignInputParentId()));
					}
					arrSqlDesignInput[i].setSqlDesignInputId(startSequenceSqlInput++);
				}
				sqlDesignInputRepository.registerAll(arrSqlDesignInput);
			}
			if (lstSqlDesignOutput.size() > 0) {
				SqlDesignOutput[] arrSqlOutput = lstSqlDesignOutput.toArray(new SqlDesignOutput[lstSqlDesignOutput.size()]);
				Map<Long, Long> mapSqlOutput = new HashMap<Long, Long>();
				Long startSqlOutput = sqlDesignOutputRepository.preserveIds(arrSqlOutput.length) - arrSqlOutput.length;
				for (int i = 0; i < arrSqlOutput.length; i++) {
					mapSqlOutput.put(arrSqlOutput[i].getSqlDesignOutputId(), startSqlOutput);
					if (arrSqlOutput[i].getSqlDesignOutputParentId() != null) {
						arrSqlOutput[i].setSqlDesignOutputParentId(mapSqlOutput.get(arrSqlOutput[i].getSqlDesignOutputParentId()));
					}
					arrSqlOutput[i].setSqlDesignOutputId(startSqlOutput);
					startSqlOutput++;
				}
				sqlDesignOutputRepository.registerAll(arrSqlOutput);
			}
			if (lstSqlDesignValue.size() > 0) {
				SqlDesignValue[] arrSqlDesignValue = lstSqlDesignValue.toArray(new SqlDesignValue[lstSqlDesignValue.size()]);
				sqlDesignValueRepository.registerAll(arrSqlDesignValue);
			}
			if (lstCondition.size() > 0) {
				SqlDesignCondition[] arrCondition = lstCondition.toArray(new SqlDesignCondition[lstCondition.size()]);
				sqlDesignConditionRepository.registerAll(arrCondition);
			}
			if (lstSqlDesignResult.size() > 0) {
				SqlDesignResult[] arrResults = lstSqlDesignResult.toArray(new SqlDesignResult[lstSqlDesignResult.size()]);
				sqlDesignResultRepository.registerAll(arrResults);
			}
			Map<Long, Long> mapIdSqlTable = new HashMap<Long, Long>();
			if (lstSqlDesignTable.size() > 0) {
				Long sequenceSqlTable = sqlDesignTableRepository.getSequencesSqlDesignTable(lstSqlDesignTable.size() - 1);
				Long startSequence = sequenceSqlTable - (lstSqlDesignTable.size() - 1);
				for (SqlDesignTable sqlTable : lstSqlDesignTable) {
					mapIdSqlTable.put(sqlTable.getSqlDesignTableId(), startSequence);
					sqlTable.setSqlDesignTableId(startSequence++);
				}
				SqlDesignTable[] arrSqlTables = lstSqlDesignTable.toArray(new SqlDesignTable[lstSqlDesignTable.size()]);
				sqlDesignTableRepository.registerAllHaveId(arrSqlTables);
			}
			if (lstSqlDesignTableItem.size() > 0) {
				for (SqlDesignTableItem sqlTableItem : lstSqlDesignTableItem) {
					sqlTableItem.setSqlDesignTableId(mapIdSqlTable.get(sqlTableItem.getSqlDesignTableId()));
				}
				SqlDesignTableItem[] arrSqlTableItems = lstSqlDesignTableItem.toArray(new SqlDesignTableItem[lstSqlDesignTableItem.size()]);
				sqlDesignTableItemsRepository.registerAll(arrSqlTableItems);
			}
		}
		sqlDesign.setAllSqlDesignInputs(allSqlDesignInputs);
		sqlDesign.setAllSqlDesignOutputs(allSqlDesignOutputs);
		return sqlDesign;
	}
	
	/**
	 * 
	 * @param ifConditionDetails
	 */
	private void processGenFormula(IfComponent ifComponent, List<InputBean> inputbeansRegisted, List<ObjectDefinition> objDefRegisted, ModuleTableMapping table, int loopId){
		Long startSequence = 0L;
		List<IfConditionDetail> lstIfConditionDetails = new ArrayList<IfConditionDetail>();
		Long sequenceIfComponent = ifComponentRepository.getSequencesIfComponent(lstIfComponents.size() - 1);
		startSequence = sequenceIfComponent - (lstIfComponents.size() - 1);
		IfConditionDetail ifConditionDetail = null;
		ifComponent.setIfComponentId(startSequence);
		startSequence++;
		for (int i = 0; i < 2; i++) {
			ifConditionDetail = new IfConditionDetail();
			if(i == 0){
				FormulaDefinition objFormula = this.createFormulaDefinition(inputbeansRegisted, objDefRegisted, table, loopId);
				ifConditionDetail.setCaption("check id not null");
				ifConditionDetail.setIfComponentId(ifComponent.getIfComponentId());
				ifConditionDetail.setFormulaDefinitionId(objFormula.getFormulaDefinitionId());
				ifConditionDetail.setFormulaDefinitionContent(objFormula.getFormulaDefinitionContent());
			}else{
				ifConditionDetail.setCaption("If condition");
				ifConditionDetail.setIfComponentId(ifComponent.getIfComponentId());
			}
			lstIfConditionDetails.add(ifConditionDetail);
		}
		ifComponent.setIfConditionDetails(lstIfConditionDetails);
	}

	/**
	 * 
	 * @param inputbeansRegisted
	 * @param objDefRegisted
	 * @param table
	 * @param loopId
	 * @return
	 */
	private FormulaDefinition createFormulaDefinition(List<InputBean> inputbeansRegisted, List<ObjectDefinition> objDefRegisted, ModuleTableMapping table, int loopId){
		
		int count = 1;
		Long sequence = 0L;
		Long startSequence = 0L;
		Long startSequenceF = 0L;
		ObjectDefinition ob = this.getOb(objDefRegisted, table);
		FormulaDefinition objFormula = new FormulaDefinition();
		if(ob != null){
			sequence = formulaDefinitionRepository.getSequencesFormulaDefinition(count - 1);
			startSequence = sequence - (count - 1);
			
			Long sequenceFormulaDetail = formulaDefinitionRepository.getSequencesFormulaDetail(3 - 1);
			startSequenceF = sequenceFormulaDetail - (3 - 1);
			
			objFormula.setProjectId(workingProjectId);
			objFormula.setFormulaDefinitionId(startSequence);
			
			List<FormulaDetail> formulaDetails = new ArrayList<FormulaDetail>();
			FormulaDetail formulaDetail = null;
			for (int i = 0; i < 3; i++) {
				formulaDetail = new FormulaDetail();
				formulaDetail.setFormulaDefinitionId(objFormula.getFormulaDefinitionId());
				formulaDetail.setItemSequenceNo(i);
				formulaDetail.setFormulaDetailId(startSequenceF);
				switch (i) {
				case 0:
					formulaDetail.setType(19);
					formulaDetail.setType(BusinessDesignConst.FormulaBuilder.TYPE_OB_BUSINESSLOGIC);
					formulaDetail.setParameterId(ob.getObjectDefinitionId());
					break;
				case 1:
					formulaDetail.setType(12);
					break;
				case 2:
					if(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE.equals(ob.getDataType())){
						formulaDetail.setType(15);
						objFormula.setFormulaDefinitionContent(mNameParameter.get(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.toString() + ob.getObjectDefinitionId().toString()) + " <> Empty ");
					}else{
						formulaDetail.setType(22);
						objFormula.setFormulaDefinitionContent(mNameParameter.get(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID.toString() + ob.getObjectDefinitionId().toString()) + " <> Null ");
					}
					break;
				}
				formulaDetails.add(formulaDetail);
				startSequenceF++;
			}
			
			// Create content formula
			objFormula.setFormulaDefinitionDetails(formulaDetails);
			lstFormulaDefinitions.add(objFormula);
		}
		return objFormula;
	}
	
	/**
	 * 
	 * @param formulaDetail
	 * @param objDefRegisted
	 * @param table
	 * @param loopId
	 */
	private ObjectDefinition getOb(List<ObjectDefinition> objDefRegisted, ModuleTableMapping table){
		List<TableDesignDetails> tableDesignDetails = this.findPKOfTable(table);
		for (TableDesignDetails designDetails : tableDesignDetails) {
			for (ObjectDefinition ob : objDefRegisted) {
				if(ob.getColumnId() != null){
					if(ob.getColumnId().equals(designDetails.getColumnId())){
						return ob;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param utilityComponent
	 * @param lstObjDef
	 * @param tableCode
	 */
	private void processUltilityComponent(UtilityComponent utilityComponent, List<ObjectDefinition> lstObjDef, String tableCode){
		for (ObjectDefinition objectDefinition : lstObjDef) {
			if(objectDefinition.getColumnId() == null){
				utilityComponent.setTargetId(objectDefinition.getObjectDefinitionId());
				utilityComponent.setTargetScope(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID);
				for (Map.Entry<String, String> e : mNameParameter.entrySet()) {
					String value = "ob." + tableCode;
					if(e.getValue().equals(value)){
						utilityComponent.setParameterScope(BusinessDesignConst.PREFIX_MAPPING.OBJECTDEFINITION_ID);
						utilityComponent.setParameterId(e.getKey().substring(1, e.getKey().length()));
						break;
					}
				}
				break;
			}
		}
	}
	
//	private void processUltilityComponentByOu(UtilityComponent utilityComponent, List<OutputBean> lstOutput, ModuleTableMapping table, List<OutputBean> outputToAssign) {
//		for (OutputBean out : lstOutput) {
//			if (BusinessDesignConst.DataType.ENTITY.equals(out.getDataType()) || BusinessDesignConst.DataType.OBJECT.equals(out.getDataType())) {
//				if (table.getTblDesignId().equals(out.getTblDesignId())) {
//					utilityComponent.setTargetScope(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID);
//					utilityComponent.setTargetId(out.getOutputBeanId());
//					break;
//				}
//			}
//		}
//		for(OutputBean obj : outputToAssign) {
//			if (BusinessDesignConst.DataType.ENTITY.equals(obj.getDataType()) || BusinessDesignConst.DataType.OBJECT.equals(obj.getDataType())) {
//				utilityComponent.setParameterScope(BusinessDesignConst.PREFIX_MAPPING.OUTPUTBEAN_ID);
//				utilityComponent.setParameterId(obj.getOutputBeanId());
//				break;
//			}
//		}
//	}
	
	/**
	 * 
	 * @param assignDetail
	 * @param parameterId
	 * @return
	 */
	private FormulaDefinition createFormulaDefinitionAssignNode(AssignDetail assignDetail, InputBean inputBean, String sequenceLogicOfLoopId, CommonModel common){
		int count = 1;
		Long sequence = 0L;
		Long startSequence = 0L;
		
		sequence = formulaDefinitionRepository.getSequencesFormulaDefinition(count - 1);
		startSequence = sequence - (count - 1);
		
		Long sequenceFormulaDetail = formulaDefinitionRepository.getSequencesFormulaDetail(0);
		
		FormulaDefinition objFormula = new FormulaDefinition();
		objFormula.setProjectId(workingProjectId);
		objFormula.setFormulaDefinitionId(startSequence);
		
		// Create formula details
		List<FormulaDetail> formulaDetails = new ArrayList<FormulaDetail>();
		FormulaDetail formulaDetail = new FormulaDetail();
		formulaDetail.setFormulaDefinitionId(objFormula.getFormulaDefinitionId());
		formulaDetail.setItemSequenceNo(0);
		formulaDetail.setFormulaDetailId(sequenceFormulaDetail);
		formulaDetail.setType(17);
		
		// Create FormulaMethodOutput
		List<FormulaMethodOutput> formulaMethodOutputs = new ArrayList<FormulaMethodOutput>();
		Long sequenceParameterOut = formulaDefinitionRepository.getSequencesFormulaMethodOutput(0);
		FormulaMethodOutput methodOutput = new FormulaMethodOutput();
		methodOutput.setFormulaDetailId(formulaDetail.getFormulaDetailId());
		methodOutput.setFormulaMethodOutputId(sequenceParameterOut);
		formulaMethodOutputs.add(methodOutput);
		
		// Create FormulaMethodIntput
		List<FormulaMethodInput> formulaMethodIntputs = new ArrayList<FormulaMethodInput>();
		Long sequenceParameterIn = formulaDefinitionRepository.getSequencesFormulaMethodInput(0);
		
		//
		List<BDParameterIndex> bdParameterIndexs = new ArrayList<BDParameterIndex>();
		BDParameterIndex index = new BDParameterIndex();
//		index.setBdParameterIndexId(startFormulaMethodIntputSequenceBDIndex);
		index.setTableType(BusinessDesignConst.ParameterIndex.TABLE_FORMULA_DETAIL);
		index.setTableId(sequenceParameterIn);
		index.setParameterId(inputBean.getParentInputBeanId());
		index.setParameterIndexType(BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP);
		index.setParameterIndexId(sequenceLogicOfLoopId);
		index.setBusinessLogicId(inputBean.getBusinessLogicId());
		bdParameterIndexs.add(index);
		
		FormulaMethodInput methodIntput = new FormulaMethodInput();
		if(sequenceLogicOfLoopId != ""){
			methodIntput.setLstParameterIndex(bdParameterIndexs);
		}
		methodIntput.setFormulaMethodInputId(sequenceParameterIn);
		methodIntput.setParameterScope(BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_INPUT_BEAN);
		methodIntput.setParameterId(inputBean.getInputBeanId());
		methodIntput.setFormulaDetailId(formulaDetail.getFormulaDetailId());
		methodIntput.setParameterType(BusinessDesignConst.FormulaBuilder.PARAMETER_TYPE_PARAMETER);
		formulaMethodIntputs.add(methodIntput);
		
		formulaDetail.setFormulaMethodInputs(formulaMethodIntputs);
		formulaDetail.setFormulaMethodOutputs(formulaMethodOutputs);
		formulaDetails.add(formulaDetail);
		objFormula.setFormulaDefinitionDetails(formulaDetails);
		objFormula.setFormulaDefinitionId(new Long(idFormularDefinitionTemp));
		assignDetail.setFormulaDefinitionId(objFormula.getFormulaDefinitionId());
		idFormularDefinitionTemp++;
		assignDetail.setAssignType(BusinessDesignConst.AssignDetailComponent.ASSIGN_TYPE_FORMULA);
		assignDetail.setFormulaDefinitionContent(this.getFormulaDefinitionContent(objFormula, inputBean.getInputBeanId(), sequenceLogicOfLoopId, FUNCTION_MASTER_CODE_QPARRAY, FUNCTION_METHOD_CODE_TOSTRING, common));
		List<FormulaDefinition> listF = new ArrayList<FormulaDefinition>();
		listF.add(objFormula);
		lstFormulaDefinitionsForAssign.add(objFormula);
//		formulaDefinitionRepository.registerFormulaDefinition(listF);
//		formulaDefinitionRepository.registerFormulaDetailsForCheckbox(formulaDetails);
		
		return objFormula;
	}
	
	private FormulaDefinition createFormulaDefinitionCurrentDate(AssignDetail assignDetail, Long projectId){
		Long sequenceFormulaDetail = formulaDefinitionRepository.getSequencesFormulaDetail(0);
		List<FunctionMethod> allFunctionMethodsOfProject = functionMasterRepository.findFuntionMethodByProjectId(projectId);
		Long functionGetCurrentDateTimeId = null;
		if(FunctionCommon.isNotEmpty(allFunctionMethodsOfProject)) {
			for(FunctionMethod fm : allFunctionMethodsOfProject) {
				if(StringUtils.isNotBlank(fm.getFunctionMethodCode()) && fm.getFunctionMethodCode().equals("getCurrentDateTime")) {
					functionGetCurrentDateTimeId = fm.getFunctionMethodId();
				}
			}
		}
		
		FormulaDefinition objFormula = new FormulaDefinition();
		objFormula.setProjectId(workingProjectId);
		objFormula.setFormulaDefinitionContent("QpDate.getCurrentDateTime()");
		
		// Create formula details
		List<FormulaDetail> formulaDetails = new ArrayList<FormulaDetail>();
		FormulaDetail formulaDetail = new FormulaDetail();
		formulaDetail.setFormulaDefinitionId(objFormula.getFormulaDefinitionId());
		formulaDetail.setItemSequenceNo(0);
		formulaDetail.setFormulaDetailId(sequenceFormulaDetail);
		formulaDetail.setType(17);
		formulaDetail.setFunctionMethodId(functionGetCurrentDateTimeId);
		
		formulaDetails.add(formulaDetail);
		objFormula.setFormulaDefinitionDetails(formulaDetails);
		objFormula.setFormulaDefinitionId(new Long(idFormularDefinitionTemp));
		assignDetail.setFormulaDefinitionId(objFormula.getFormulaDefinitionId());
		idFormularDefinitionTemp++;
		assignDetail.setAssignType(BusinessDesignConst.AssignDetailComponent.ASSIGN_TYPE_FORMULA);
		assignDetail.setFormulaDefinitionContent("QpDate.getCurrentDateTime()");
		List<FormulaDefinition> listF = new ArrayList<FormulaDefinition>();
		listF.add(objFormula);
		lstFormulaDefinitionsForAssign.add(objFormula);
		
		return objFormula;
	}
	
	private FormulaDefinition createFormulaDefinitionOfUndoBlogic(AssignDetail assignDetail, InputBean inputBean, CommonModel common){
		int count = 1;
		Long sequence = 0L;
		Long startSequence = 0L;
		
		sequence = formulaDefinitionRepository.getSequencesFormulaDefinition(count - 1);
		startSequence = sequence - (count - 1);
		
		Long sequenceFormulaDetail = formulaDefinitionRepository.getSequencesFormulaDetail(0);
		
		FormulaDefinition objFormula = new FormulaDefinition();
		objFormula.setProjectId(workingProjectId);
		objFormula.setFormulaDefinitionId(startSequence);
		
		// Create formula details
		List<FormulaDetail> formulaDetails = new ArrayList<FormulaDetail>();
		FormulaDetail formulaDetail = new FormulaDetail();
		formulaDetail.setFormulaDefinitionId(objFormula.getFormulaDefinitionId());
		formulaDetail.setItemSequenceNo(0);
		formulaDetail.setFormulaDetailId(sequenceFormulaDetail);
		formulaDetail.setType(17);
		
		// Create FormulaMethodOutput
		List<FormulaMethodOutput> formulaMethodOutputs = new ArrayList<FormulaMethodOutput>();
		Long sequenceParameterOut = formulaDefinitionRepository.getSequencesFormulaMethodOutput(0);
		FormulaMethodOutput methodOutput = new FormulaMethodOutput();
		methodOutput.setFormulaDetailId(formulaDetail.getFormulaDetailId());
		methodOutput.setFormulaMethodOutputId(sequenceParameterOut);
		formulaMethodOutputs.add(methodOutput);
		
		// Create FormulaMethodIntput
		List<FormulaMethodInput> formulaMethodIntputs = new ArrayList<FormulaMethodInput>();
		Long sequenceParameterIn = formulaDefinitionRepository.getSequencesFormulaMethodInput(0);
		
		FormulaMethodInput methodIntput = new FormulaMethodInput();
		methodIntput.setFormulaMethodInputId(sequenceParameterIn++);
		methodIntput.setParameterScope(BusinessDesignConst.FormulaBuilder.PARAMETER_SCOPE_INPUT_BEAN);
		methodIntput.setParameterType(BusinessDesignConst.FormulaBuilder.PARAMETER_TYPE_PARAMETER);
		methodIntput.setParameterId(inputBean.getInputBeanId());
		methodIntput.setFormulaDetailId(formulaDetail.getFormulaDetailId());
		formulaMethodIntputs.add(methodIntput);
		
		FormulaMethodInput methodIntputValue = new FormulaMethodInput();
		methodIntputValue.setFormulaMethodInputId(sequenceParameterIn++);
		methodIntputValue.setParameterScope(null);
		methodIntputValue.setParameterType(BusinessDesignConst.FormulaBuilder.PARAMETER_TYPE_VALUE);
		methodIntputValue.setParameterId(null);
		methodIntputValue.setParameterValue(";");
		methodIntputValue.setFormulaDetailId(formulaDetail.getFormulaDetailId());
		formulaMethodIntputs.add(methodIntputValue);
		
		formulaDetail.setFormulaMethodInputs(formulaMethodIntputs);
		formulaDetail.setFormulaMethodOutputs(formulaMethodOutputs);
		formulaDetails.add(formulaDetail);
		objFormula.setFormulaDefinitionDetails(formulaDetails);
		objFormula.setFormulaDefinitionId(new Long(idFormularDefinitionTemp));
		assignDetail.setFormulaDefinitionId(objFormula.getFormulaDefinitionId());
		idFormularDefinitionTemp++;
		assignDetail.setAssignType(BusinessDesignConst.AssignDetailComponent.ASSIGN_TYPE_FORMULA);
		assignDetail.setFormulaDefinitionContent(this.getFormulaDefinitionContent(objFormula, inputBean.getInputBeanId(), "", FUNCTION_MASTER_CODE_QPSTRING, FUNCTION_METHOD_CODE_TO_ARRAYLIST, common));
		List<FormulaDefinition> listF = new ArrayList<FormulaDefinition>();
		listF.add(objFormula);
		lstFormulaDefinitionsForAssign.add(objFormula);
		
		return objFormula;
	}
	
	/**
	 * 
	 * @param inputBean
	 * @return
	 */
	private String getFormulaDefinitionContent(FormulaDefinition objFormula, String inputBean, String sequenceLogicOfLoopId, String functionMasterCode, String functionMethodCode, CommonModel common){
		FunctionMaster functionMaster = this.getFunctionMaster(functionMasterCode, functionMethodCode, common);
		StringBuilder stringBuilder = new StringBuilder();
		long methodInputString = 0, methodInputPattern = 0, methodOutputId = 0, methodId = 0;
		
		if(functionMaster != null){
			String getSourceIn = mNameParameter.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.toString() +inputBean);
			if(FUNCTION_MASTER_CODE_QPARRAY.equals(functionMasterCode)) {
				if(getSourceIn != null){
					String [] getTextIn = getSourceIn.split("\\.");
					if(getTextIn.length >= 3){
						if(sequenceLogicOfLoopId != ""){
							stringBuilder.append(functionMaster.getFunctionMasterCode());
							stringBuilder.append(DOT);
							stringBuilder.append(functionMethodCode).append("(").append(getTextIn[0]).append(DOT).append(getTextIn[1]);
							stringBuilder.append("[index0]").append(DOT).append(getTextIn[2]).append("[]");
						}else{
							stringBuilder.append(functionMaster.getFunctionMasterCode());
							stringBuilder.append(DOT);
							stringBuilder.append(functionMethodCode).append("(");
							stringBuilder.append(mNameParameter.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.toString() +inputBean)).append("[]");
						}
					}else{
						stringBuilder.append(mNameParameter.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.toString() +inputBean));
					}
					stringBuilder.append(")");
				}
			} else if(FUNCTION_MASTER_CODE_QPSTRING.equals(functionMasterCode)) { 
				if(getSourceIn != null){
					stringBuilder.append(functionMaster.getFunctionMasterCode());
					stringBuilder.append(DOT);
					stringBuilder.append(functionMethodCode).append("(");
					stringBuilder.append(mNameParameter.get(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID.toString() +inputBean));
					stringBuilder.append(",;");
					stringBuilder.append(")");
				}
			}
			
			for (FunctionMethod functionMethod : functionMaster.getFunctionMethod()) {
				if(functionMethodCode.equals(functionMethod.getFunctionMethodCode())) {
					methodId = functionMethod.getFunctionMethodId();
					for (FunctionMethodInput functionMethodInput : functionMethod.getFunctionMethodInput()) {
						if (FUNCTION_METHOD_CODE_TOSTRING.equals(functionMethodCode)) {
							if (TO_STRING_INPUT.equals(functionMethodInput.getMethodInputCode())) {
								methodInputString = Long.parseLong(functionMethodInput.getMethodInputId());
							}
						} else if (FUNCTION_METHOD_CODE_TO_ARRAYLIST.equals(functionMethodCode)) {
							if (TO_ARRAYLIST_INPUT_STRING.equals(functionMethodInput.getMethodInputCode())) {
								methodInputString = Long.parseLong(functionMethodInput.getMethodInputId());
							}
							if (TO_ARRAYLIST_INPUT_PATTERN.equals(functionMethodInput.getMethodInputCode())) {
								methodInputPattern = Long.parseLong(functionMethodInput.getMethodInputId());
							}
						}
					}
					for (FunctionMethodOutput functionMethodOutput : functionMethod.getFunctionMethodOutput()) {
						methodOutputId = Long.parseLong(functionMethodOutput.getMethodOutputId());
					}
				}
			}
		}
		objFormula.setFormulaDefinitionContent(stringBuilder.toString());
		
		for (FormulaDetail formulaDetail : objFormula.getFormulaDefinitionDetails()) {
			formulaDetail.setFunctionMethodId(methodId);
			for (FormulaMethodInput formulaMethodInput : formulaDetail.getFormulaMethodInputs()) {
				if (PARAMETER_TYPE_PARAMETER.equals(formulaMethodInput.getParameterType())) {
					formulaMethodInput.setMethodInputId(new Long(methodInputString));
				}
				if (PARAMETER_TYPE_VALUE.equals(formulaMethodInput.getParameterType())) {
					formulaMethodInput.setMethodInputId(new Long(methodInputPattern));
				}
			}
			
			for (FormulaMethodOutput formulaMethodOutput : formulaDetail.getFormulaMethodOutputs()) {
				formulaMethodOutput.setMethodOutputId(new Long(methodOutputId));
			}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 
	 * @param functionMethodId
	 * @return
	 */
	private FunctionMaster getFunctionMaster(String functionMasterCode, String functionMethodCode, CommonModel common){
		List<FunctionMaster> lstFunctionMaster = functionMasterService.loadAllFunctionMasterByProject(common);
		
		if(FunctionCommon.isNotEmpty(lstFunctionMaster)){
			for (FunctionMaster functionMaster : lstFunctionMaster) {
				if(functionMaster.getFunctionMasterCode().equals(functionMasterCode)){
					if(FunctionCommon.isNotEmpty(functionMaster.getFunctionMethod())){
						for (FunctionMethod functionMethod : functionMaster.getFunctionMethod()) {
							if(functionMethod.getFunctionMethodCode().equals(functionMethodCode)){
								return functionMaster;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Generate batch insert query
	 * 
	 * @param screenDesignDefault
	 * @param screenDesignObj
	 * @param objDefs
	 * @param sqlDesignName
	 * @param sqlDesignCode
	 * @param sqlpattern
	 * @return
	 */
	public SqlDesign autoGenerateAdvanceSqlInsert(ScreenDesignDefault screenDesignDefault, ScreenDesign screenDesignObj, List<ObjectDefinition> objDefs, List<OutputBean> objOuts, String sqlDesignName, String sqlDesignCode, int sqlpattern) {
		SqlDesign sqlDesign = null;
		String parentObjectDefinitionId = null;
		List<SqlDesignInput> lstSqlDesignInput = new ArrayList<SqlDesignInput>();
		List<Long> lstAllColumnId = new ArrayList<Long>();
		List<TableDesignDetails> lstAllColumn = new ArrayList<TableDesignDetails>();
		StringBuilder builder = new StringBuilder();
		StringBuilder columnBuilder = new StringBuilder();
		StringBuilder valueBuilder = new StringBuilder();
		String tableCode = "";
		String collectionCode = "";
		Long sqlDesignInputParentId = 1L;
		
		if (screenDesignDefault.getProject() != null && DataTypeUtils.equals(DbDomainConst.DatabaseType.ORACLE, screenDesignDefault.getProject().getDbType())) {
			builder.append("<if test=\"{0} != null and	{0}.size() > 0 \">");
			builder.append(DOWNLINE);
			builder.append(TAB).append("INSERT ALL");
			builder.append(DOWNLINE);
			builder.append(TAB).append("<foreach collection=\"{0}\" item=\"item\" separator=\" \">");
			builder.append(DOWNLINE);
			builder.append(TAB).append("<![CDATA[");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append("INTO {1} ");
			builder.append(DOWNLINE);
			builder.append(TAB).append("(");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append("{2}");
			builder.append(DOWNLINE);
			builder.append(TAB).append(") VALUES ");
			builder.append(DOWNLINE);
			builder.append(TAB).append("(");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append("{3}");
			builder.append(DOWNLINE);
			builder.append(TAB).append(")");
			builder.append(DOWNLINE);
			builder.append(TAB).append("]]>");
			builder.append(DOWNLINE);
			builder.append(TAB).append("</foreach>");
			builder.append(DOWNLINE);
			builder.append(TAB).append("SELECT * FROM dual");
			builder.append(DOWNLINE);
			builder.append("</if>");
		} else if(screenDesignDefault.getProject() != null && DataTypeUtils.equals(DbDomainConst.DatabaseType.PostgreSQL, screenDesignDefault.getProject().getDbType())) {
			builder.append("<if test=\"{0} != null and	{0}.size() > 0 \">");
			builder.append(DOWNLINE);
			builder.append(TAB).append("INSERT INTO");
			builder.append(DOWNLINE);
			builder.append(TAB).append("{1}");
			builder.append(DOWNLINE);
			builder.append(TAB).append("(");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append("{2}");
			builder.append(DOWNLINE);
			builder.append(TAB).append(") VALUES");
			builder.append(DOWNLINE);
			builder.append(TAB).append("<foreach collection=\"{0}\" item=\"item\" separator=\",\">");
			builder.append(DOWNLINE);
			builder.append(TAB).append("(");
			builder.append(DOWNLINE);
			builder.append(TAB).append("<![CDATA[");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append("{3}");
			builder.append(DOWNLINE);
			builder.append(TAB).append("]]>");
			builder.append(DOWNLINE);
			builder.append(TAB).append(")");
			builder.append(DOWNLINE);
			builder.append(TAB).append("</foreach>");
			builder.append(DOWNLINE);
			builder.append("</if>");
		}
		int itemSeqNo = 0;
		if (objDefs != null) {
			for (ObjectDefinition ob : objDefs) {
				lstAllColumnId.add(ob.getColumnId());
			}
		} else if (objOuts != null) {
			for (OutputBean ou : objOuts) {
				lstAllColumnId.add(ou.getColumnId());
			}
		}
		lstAllColumn = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstAllColumnId);
		if (objDefs != null) {
			for (ObjectDefinition ob : objDefs) {
				if (BusinessDesignConst.DataType.ENTITY.equals(ob.getDataType())) {
					TableDesign tableDesign = tableDesignRepository.findOneById(ob.getTblDesignId());
					if (tableDesign != null) {
						tableCode = tableDesign.getTableCode();
						collectionCode = ob.getObjectDefinitionCode();
						SqlDesignInput sqlInputParent = GenerateScreenContruct.setSqlDesignInput(sqlDesignInputParentId, tableDesign.getTableName(), collectionCode, ob.getBaseType(), itemSeqNo, null, tableDesign.getTableDesignId(), true, null, true, DbDomainConst.YesNoFlg.YES, DbDomainConst.DesignType.DESIGN_TYPE, DbDomainConst.ObjectType.ENTITY_OBJECT, false);
						lstSqlDesignInput.add(sqlInputParent);
						parentObjectDefinitionId = ob.getObjectDefinitionId();
					}
				} else {
					if (ob.getParentObjectDefinitionId() != null && StringUtils.equals(ob.getParentObjectDefinitionId(), parentObjectDefinitionId)) {
						if (columnBuilder.length() > 0) {
							columnBuilder.append(COMMA);
							columnBuilder.append(DOWNLINE);
							columnBuilder.append(TAB).append(TAB);
						}
						if (valueBuilder.length() > 0) {
							valueBuilder.append(COMMA);
							valueBuilder.append(DOWNLINE);
							valueBuilder.append(TAB).append(TAB);
						}
						if (ob.isKey(DbDomainConst.TblDesignKeyType.PK) == 1) {
							if (lstAllColumn != null) {
								for (TableDesignDetails column : lstAllColumn) {
									if (column.getColumnId().equals(ob.getColumnId()) && DbDomainConst.DisplayType.USED.equals(column.getDisplayType())) {
										columnBuilder.append(ob.getObjectDefinitionCode());
										valueBuilder.append("#{item");
										valueBuilder.append(DOT);
										valueBuilder.append(ob.getObjectDefinitionCode());
										valueBuilder.append("}");
										if (ob.getDataType() != null && !DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType())) {
											SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(null, ob.getObjectDefinitionName(), ob.getObjectDefinitionCode(), ob.getBaseType(), itemSeqNo, ob.getColumnId(), ob.getTblDesignId(), false, sqlDesignInputParentId, true, null, DbDomainConst.DesignType.REFERENCE_TYPE, DbDomainConst.ObjectType.ENTITY_ATTRIBUTE, true);
											lstSqlDesignInput.add(sqlInput);
										}
									}
								}
							}
						} else {
							columnBuilder.append(ob.getObjectDefinitionCode());
							valueBuilder.append("#{item");
							valueBuilder.append(DOT);
							valueBuilder.append(ob.getObjectDefinitionCode());
							valueBuilder.append("}");
							if (ob.getDataType() != null && !DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType())) {
								SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(null, ob.getObjectDefinitionName(), ob.getObjectDefinitionCode(), ob.getBaseType(), itemSeqNo, ob.getColumnId(), ob.getTblDesignId(), false, sqlDesignInputParentId, true, null, DbDomainConst.DesignType.REFERENCE_TYPE, DbDomainConst.ObjectType.ENTITY_ATTRIBUTE, true);
								lstSqlDesignInput.add(sqlInput);
							}
						}
					}
				}
				itemSeqNo ++;
			}
		} else if (objOuts != null) {
			for (OutputBean ou : objOuts) {
				if (BusinessDesignConst.DataType.ENTITY.equals(ou.getDataType())) {
					TableDesign tableDesign = tableDesignRepository.findOneById(ou.getTblDesignId());
					if (tableDesign != null) {
						tableCode = tableDesign.getTableCode();
						collectionCode = ou.getOutputBeanCode();
						SqlDesignInput sqlInputParent = GenerateScreenContruct.setSqlDesignInput(sqlDesignInputParentId, tableDesign.getTableName(), collectionCode, ou.getBaseType(), itemSeqNo, null, tableDesign.getTableDesignId(), true, null, true, DbDomainConst.YesNoFlg.YES, DbDomainConst.DesignType.DESIGN_TYPE, DbDomainConst.ObjectType.ENTITY_OBJECT, false);
						lstSqlDesignInput.add(sqlInputParent);
						parentObjectDefinitionId = ou.getOutputBeanId();
					}
				} else {
					if (ou.getParentOutputBeanId() != null && StringUtils.equals(ou.getParentOutputBeanId(), parentObjectDefinitionId)) {
						if (columnBuilder.length() > 0) {
							columnBuilder.append(COMMA);
							columnBuilder.append(DOWNLINE);
							columnBuilder.append(TAB).append(TAB);
						}
						if (valueBuilder.length() > 0) {
							valueBuilder.append(COMMA);
							valueBuilder.append(DOWNLINE);
							valueBuilder.append(TAB).append(TAB);
						}
						if (lstAllColumn != null) {
							for (TableDesignDetails column : lstAllColumn) {
								boolean isUse = false;
								if (column.getColumnId().equals(ou.getColumnId())) {
									if (column.isKey(DbDomainConst.TblDesignKeyType.PK) == 1) {
										if (DbDomainConst.DisplayType.USED.equals(column.getDisplayType())) {
											isUse = true;
										}
									} else {
										isUse = true;
									}
								}
								if (isUse) {
									columnBuilder.append(ou.getOutputBeanCode());
									valueBuilder.append("#{item");
									valueBuilder.append(DOT);
									valueBuilder.append(ou.getOutputBeanCode());
									valueBuilder.append("}");
									if (ou.getDataType() != null && !DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ou.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ou.getDataType())) {
										SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(null, ou.getOutputBeanName(), ou.getOutputBeanCode(), ou.getBaseType(), itemSeqNo, ou.getColumnId(), ou.getTblDesignId(), false, sqlDesignInputParentId, true, null, DbDomainConst.DesignType.REFERENCE_TYPE, DbDomainConst.ObjectType.ENTITY_ATTRIBUTE, true);
										lstSqlDesignInput.add(sqlInput);
									}
								}
							}
						}
					}
				}
				itemSeqNo ++;
			}
		
		}
		String sqlText = MessageFormat.format(builder.toString(), collectionCode, tableCode, columnBuilder, valueBuilder);
		sqlDesign = GenerateScreenContruct.populateSqlDesign(DomainDatatypeConst.SQLDesignType.ADVANCED_SQL, sqlDesignName, sqlDesignCode, sqlpattern, screenDesignDefault, screenDesignObj, null, false, null);
		sqlDesign.setSqlText(sqlText);
		sqlDesignRepository.register(sqlDesign);
		Long sqlDesignId = sqlDesign.getSqlDesignId();
		if (lstSqlDesignInput.size() > 0) {
			SqlDesignInput[] arrSqlDesignInput = lstSqlDesignInput.toArray(new SqlDesignInput[lstSqlDesignInput.size()]);
			Long startSequenceSqlInput = sqlDesignInputRepository.preserveIds(arrSqlDesignInput.length) - arrSqlDesignInput.length;
			for (int i = 0; i < arrSqlDesignInput.length; i++) {
				if (arrSqlDesignInput[i].getSqlDesignInputParentId() == null) {
					sqlDesignInputParentId = startSequenceSqlInput;
				}
				if (arrSqlDesignInput[i].getSqlDesignInputParentId() != null) {
					arrSqlDesignInput[i].setSqlDesignInputParentId(sqlDesignInputParentId);
				}
				arrSqlDesignInput[i].setSqlDesignId(sqlDesignId);
				arrSqlDesignInput[i].setSqlDesignInputId(startSequenceSqlInput);
				startSequenceSqlInput++;
			}
			sqlDesignInputRepository.registerAll(arrSqlDesignInput);
		}
		sqlDesign.setAllSqlDesignInputs(lstSqlDesignInput);
		return sqlDesign;
	}
	
	/**
	 *  Generate batch update query
	 * 
	 * @param screenDesignDefault
	 * @param screenDesignObj
	 * @param moduleTableMapping
	 * @param objDefs
	 * @param sqlDesignName
	 * @param sqlDesignCode
	 * @param sqlpattern
	 * @return
	 */
	public SqlDesign autoGenerateAdvanceSqlUpdate(ScreenDesignDefault screenDesignDefault, ScreenDesign screenDesignObj, ModuleTableMapping moduleTableMapping, List<ObjectDefinition> objDefs, String sqlDesignName, String sqlDesignCode, int sqlpattern) {
		SqlDesign sqlDesign = null;
		String parentObjectDefinitionId = null;
		List<SqlDesignInput> lstSqlDesignInput = new ArrayList<SqlDesignInput>();
		StringBuilder builder = new StringBuilder();
		StringBuilder selectBuilder = new StringBuilder();
		StringBuilder setBuilder = new StringBuilder();
		StringBuilder whereBuilder = new StringBuilder();
		String tableCode = "";
		String collectionCode = "";
		Long sqlDesignInputParentId = 1L;
		List<TableDesignDetails> pkOfTable = findPKOfTable(moduleTableMapping);
		
		if(screenDesignDefault.getProject() != null && DbDomainConst.DatabaseType.PostgreSQL.equals(screenDesignDefault.getProject().getDbType())) {
			builder.append("<if test=\"{0} != null and	{0}.size() > 0 \">");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB);
			builder.append("<foreach item=\"item\" collection=\"{0}\" separator=\";\">");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append(TAB);
			builder.append("<![CDATA[");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append(TAB);
			builder.append("UPDATE");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append(TAB).append(TAB);
			builder.append("{1}");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append(TAB);
			builder.append("SET");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append(TAB).append(TAB);
			builder.append("{2}");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append(TAB);
			builder.append("WHERE");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append(TAB).append(TAB);
			builder.append("{3}");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append(TAB);
			builder.append("]]>");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB);
			builder.append("</foreach>");
			builder.append(DOWNLINE);
			builder.append(TAB);
			builder.append("</if>");
			
			int itemSeqNo = 0;
			for (ObjectDefinition ob : objDefs) {
				if (BusinessDesignConst.DataType.ENTITY.equals(ob.getDataType())) {
					TableDesign tableDesign = tableDesignRepository.findOneById(ob.getTblDesignId());
					if (tableDesign != null) {
						tableCode = tableDesign.getTableCode();
						collectionCode = ob.getObjectDefinitionCode();
						SqlDesignInput sqlInputParent = GenerateScreenContruct.setSqlDesignInput(sqlDesignInputParentId, tableDesign.getTableName(), collectionCode, ob.getBaseType(), itemSeqNo, null, tableDesign.getTableDesignId(), true, null, true, DbDomainConst.YesNoFlg.YES, DbDomainConst.DesignType.DESIGN_TYPE, DbDomainConst.ObjectType.ENTITY_OBJECT, false);
						lstSqlDesignInput.add(sqlInputParent);
						parentObjectDefinitionId = ob.getObjectDefinitionId();
					}
				} else {
					if (ob.getParentObjectDefinitionId() != null && StringUtils.equals(ob.getParentObjectDefinitionId(), parentObjectDefinitionId)) {
						if (setBuilder.length() > 0) {
							setBuilder.append(COMMA);
							setBuilder.append(DOWNLINE);
							setBuilder.append(TAB).append(TAB).append(TAB).append(TAB);
						}
						setBuilder.append(ob.getObjectDefinitionCode());
						setBuilder.append(SPACE).append("=").append(SPACE);
						setBuilder.append("#{item");
						setBuilder.append(DOT);
						setBuilder.append(ob.getObjectDefinitionCode());
						setBuilder.append("}");
						for (TableDesignDetails tableDesignDetails : pkOfTable) {
							if (tableDesignDetails.getColumnId().equals(ob.getColumnId())) {
								if (whereBuilder.length() > 0) {
									whereBuilder.append(DOWNLINE);
									whereBuilder.append(TAB).append(TAB).append(TAB).append("AND").append(SPACE);
								}
								whereBuilder.append(tableDesignDetails.getCode());
								whereBuilder.append(SPACE).append("=").append(SPACE);
								whereBuilder.append("#{item");
								whereBuilder.append(DOT);
								whereBuilder.append(tableDesignDetails.getCode());
								whereBuilder.append("}");
							}
						}
						if (ob.getDataType() != null && !DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType())) {
							SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(null, ob.getObjectDefinitionName(), ob.getObjectDefinitionCode(), ob.getBaseType(), itemSeqNo, ob.getColumnId(), ob.getTblDesignId(), false, sqlDesignInputParentId, true, null, DbDomainConst.DesignType.REFERENCE_TYPE, DbDomainConst.ObjectType.ENTITY_ATTRIBUTE, true);
							lstSqlDesignInput.add(sqlInput);
						}
					}
				
				}
				itemSeqNo ++;
			}
		} else if(screenDesignDefault.getProject() != null && DbDomainConst.DatabaseType.ORACLE.equals(screenDesignDefault.getProject().getDbType())) {
			builder.append(TAB).append("<if test=\"{0} != null and	{0}.size() > 0 \">");
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB);
			builder.append("merge into {1} obj using (");
			
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB);
			builder.append("<foreach item=\"item\" collection=\"{0}\" index=\"index\" open=\"\" close=\"\" separator=\"union\">");
			
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append(TAB).append(TAB);
			builder.append("SELECT");
			
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append(TAB).append(TAB).append(TAB).append(TAB);
			builder.append("{2}");
			
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append(TAB).append(TAB);
			builder.append("FROM DUAL");
			
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB);
			builder.append("</foreach>");
			
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB);
			builder.append(")tmp on(");
			
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append(TAB).append(TAB);
			builder.append("{3}");
			
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB);
			builder.append(")");
			
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB);
			builder.append("when matched THEN update");
			
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB);
			builder.append("<set>");
			
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB).append(TAB).append(TAB);
			builder.append("{4}");
			
			builder.append(DOWNLINE);
			builder.append(TAB).append(TAB);
			builder.append("</set>");
			
			builder.append(DOWNLINE);
			builder.append(TAB);
			builder.append("</if>");
			
			int itemSeqNo = 0;
			Map<Long, TableDesignDetails> mapColumn = new HashMap<Long, TableDesignDetails>();
			if (FunctionCommon.isNotEmpty(moduleTableMapping.getListAllColumns())) {
				for (TableDesignDetails col : moduleTableMapping.getListAllColumns()) {
					mapColumn.put(col.getColumnId(), col);
				}
			}
			for (ObjectDefinition ob : objDefs) {
				if (BusinessDesignConst.DataType.ENTITY.equals(ob.getDataType())) {
					TableDesign tableDesign = tableDesignRepository.findOneById(ob.getTblDesignId());
					if (tableDesign != null) {
						tableCode = tableDesign.getTableCode();
						collectionCode = ob.getObjectDefinitionCode();
						SqlDesignInput sqlInputParent = GenerateScreenContruct.setSqlDesignInput(sqlDesignInputParentId, tableDesign.getTableName(), collectionCode, ob.getBaseType(), itemSeqNo, null, tableDesign.getTableDesignId(), true, null, true, DbDomainConst.YesNoFlg.YES, DbDomainConst.DesignType.DESIGN_TYPE, DbDomainConst.ObjectType.ENTITY_OBJECT, false);
						lstSqlDesignInput.add(sqlInputParent);
						parentObjectDefinitionId = ob.getObjectDefinitionId();
					}
				} else {
					if (ob.getParentObjectDefinitionId() != null && StringUtils.equals(ob.getParentObjectDefinitionId(), parentObjectDefinitionId)) {
						if (selectBuilder.length() > 0) {
							selectBuilder.append(COMMA).append(DOWNLINE).append(TAB).append(TAB).append(TAB).append(TAB).append(TAB).append(TAB);
						}
						selectBuilder.append("#{item.").append(ob.getObjectDefinitionCode()).append("}").append(SPACE).append(ob.getObjectDefinitionCode());
						TableDesignDetails col = mapColumn.get(ob.getColumnId());
						if (col != null && DbDomainConst.YesNoFlg.YES.equals(col.isKey(DbDomainConst.TblDesignKeyType.PK))) {
							if (col.getColumnId().equals(ob.getColumnId())) {
								if (whereBuilder.length() > 0) {
									whereBuilder.append(COMMA).append(DOWNLINE).append(TAB).append(TAB).append(TAB).append(TAB);
								}
								whereBuilder.append("tmp.").append(ob.getObjectDefinitionCode());
								whereBuilder.append(SPACE).append("=").append(SPACE);
								whereBuilder.append("obj.").append(ob.getObjectDefinitionCode());
							}
						} else if (col != null && !DbDomainConst.YesNoFlg.YES.equals(col.isKey(DbDomainConst.TblDesignKeyType.PK))) {
							if (setBuilder.length() > 0) {
								setBuilder.append(COMMA).append(DOWNLINE).append(TAB).append(TAB).append(TAB).append(TAB);
							}
							setBuilder.append("obj.").append(ob.getObjectDefinitionCode());
							setBuilder.append(SPACE).append("=").append(SPACE);
							setBuilder.append("tmp.").append(ob.getObjectDefinitionCode());
						}
						if (ob.getDataType() != null && !DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType())) {
							SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(null, ob.getObjectDefinitionName(), ob.getObjectDefinitionCode(), ob.getBaseType(), itemSeqNo, ob.getColumnId(), ob.getTblDesignId(), false, sqlDesignInputParentId, true, null, DbDomainConst.DesignType.REFERENCE_TYPE, DbDomainConst.ObjectType.ENTITY_ATTRIBUTE, true);
							lstSqlDesignInput.add(sqlInput);
						}
					}
				}
				itemSeqNo++;
			}
		}
		
		
		String sqlText = "";
		if(screenDesignDefault.getProject() != null && DbDomainConst.DatabaseType.PostgreSQL.equals(screenDesignDefault.getProject().getDbType())) {
			sqlText = MessageFormat.format(builder.toString(), collectionCode, tableCode, setBuilder, whereBuilder);
		} else if(screenDesignDefault.getProject() != null && DbDomainConst.DatabaseType.ORACLE.equals(screenDesignDefault.getProject().getDbType())) {
			sqlText = MessageFormat.format(builder.toString(), collectionCode, tableCode, selectBuilder, whereBuilder, setBuilder);
		}
		sqlDesign = GenerateScreenContruct.populateSqlDesign(DomainDatatypeConst.SQLDesignType.ADVANCED_SQL, sqlDesignName, sqlDesignCode, sqlpattern, screenDesignDefault, screenDesignObj, null, false, null);
		sqlDesign.setSqlText(sqlText);
		sqlDesignRepository.register(sqlDesign);
		Long sqlDesignId = sqlDesign.getSqlDesignId();
		if (lstSqlDesignInput.size() > 0) {
			SqlDesignInput[] arrSqlDesignInput = lstSqlDesignInput.toArray(new SqlDesignInput[lstSqlDesignInput.size()]);
			Long startSequenceSqlInput = sqlDesignInputRepository.preserveIds(arrSqlDesignInput.length) - arrSqlDesignInput.length;
			for (int i = 0; i < arrSqlDesignInput.length; i++) {
				if (arrSqlDesignInput[i].getSqlDesignInputParentId() == null) {
					sqlDesignInputParentId = startSequenceSqlInput;
				}
				if (arrSqlDesignInput[i].getSqlDesignInputParentId() != null) {
					arrSqlDesignInput[i].setSqlDesignInputParentId(sqlDesignInputParentId);
				}
				arrSqlDesignInput[i].setSqlDesignId(sqlDesignId);
				arrSqlDesignInput[i].setSqlDesignInputId(startSequenceSqlInput);
				startSequenceSqlInput++;
			}
			sqlDesignInputRepository.registerAll(arrSqlDesignInput);
		}
		sqlDesign.setAllSqlDesignInputs(lstSqlDesignInput);
		return sqlDesign;
	}
	
	/**
	 *  Generate batch delete query
	 *  
	 * @param screenDesignDefault
	 * @param screenDesignObj
	 * @param moduleTableMapping
	 * @param objDefs
	 * @param sqlDesignName
	 * @param sqlDesignCode
	 * @param sqlpattern
	 * @return
	 */
	public SqlDesign autoGenerateAdvanceSqlDelete(ScreenDesignDefault screenDesignDefault, ScreenDesign screenDesignObj, ModuleTableMapping moduleTableMapping, List<ObjectDefinition> objDefs, String sqlDesignName, String sqlDesignCode, int sqlpattern, List<ObjectDefinition> allObjDefRegisted) {
		SqlDesign sqlDesign = null;
		Long sqlDesignInputParentId = 1L;
		String parentObjectDefinitionId = null;
		List<SqlDesignInput> lstSqlDesignInput = new ArrayList<SqlDesignInput>();
		StringBuilder builder = new StringBuilder();
		StringBuilder whereBuilder = new StringBuilder();
		String tableCode = "";
		String collectionCode = "";
		List<TableDesignDetails> keyOfTable = findKeyOfTable(moduleTableMapping);
		List<TableDesignDetails> pkOfTableWithoutFk = new ArrayList<TableDesignDetails>();
		List<TableDesignDetails> pkOfTableWithFk = new ArrayList<TableDesignDetails>();
		List<TableDesignForeignKey> fkOfTable = new ArrayList<TableDesignForeignKey>();
		ModuleTableMapping firstTable = screenDesignDefault.getModuleTableMappings()[0];

		for (TableDesignForeignKey fk : allForeignKeyInProject) {
			if (fk.getFromTableId().equals(moduleTableMapping.getTblDesignId())) {
				fkOfTable.add(fk);
			}
		}

		for (TableDesignDetails key : keyOfTable) {
			boolean exist = false;
			for (TableDesignForeignKey fk : fkOfTable) {
				if (key.getColumnId().equals(fk.getFromColumnId()) && fk.getToTableId().equals(firstTable.getTblDesignId())) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				pkOfTableWithoutFk.add(key);
			} else {
				pkOfTableWithFk.add(key);
			}
		}

		List<ObjectDefinition> objDefForeignKey = new ArrayList<ObjectDefinition>();
		for (ObjectDefinition ob : allObjDefRegisted) {
			for (TableDesignDetails column : pkOfTableWithFk) {
				if (column.getColumnId().equals(ob.getColumnId())) {
					objDefForeignKey.add(ob);
				}
			}
		}
		
		builder.append("DELETE FROM {1}").append(DOWNLINE);
		builder.append("WHERE").append(DOWNLINE).append("{2}");
		int itemSeqNo = 0;
		for (ObjectDefinition ob : objDefForeignKey) {
			if (whereBuilder.length() > 0) {
				whereBuilder.append(DOWNLINE);
				whereBuilder.append(TAB);
				whereBuilder.append("AND");
			}
			whereBuilder.append(TAB).append(ob.getObjectDefinitionCode()).append(SPACE).append("=").append(SPACE).append("#{" + ob.getObjectDefinitionCode() + "}").append(DOWNLINE);
			SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(null, ob.getObjectDefinitionName(), ob.getObjectDefinitionCode(), ob.getBaseType(), itemSeqNo, ob.getColumnId(), ob.getTblDesignId(), false, null, true, null, DbDomainConst.DesignType.DESIGN_TYPE, null, true);
			lstSqlDesignInput.add(sqlInput);
			itemSeqNo ++;
		}
		for (ObjectDefinition ob : objDefs) {
			if (BusinessDesignConst.DataType.ENTITY.equals(ob.getDataType())) {
				TableDesign tableDesign = tableDesignRepository.findOneById(ob.getTblDesignId());
				if (tableDesign != null) {
					tableCode = tableDesign.getTableCode();
					collectionCode = ob.getObjectDefinitionCode();
					SqlDesignInput sqlInputParent = GenerateScreenContruct.setSqlDesignInput(sqlDesignInputParentId, tableDesign.getTableName(), collectionCode, null, itemSeqNo, null, tableDesign.getTableDesignId(), true, null, true, DbDomainConst.YesNoFlg.YES, DbDomainConst.DesignType.DESIGN_TYPE, DbDomainConst.ObjectType.ENTITY_OBJECT, false);
					lstSqlDesignInput.add(sqlInputParent);
					parentObjectDefinitionId = ob.getObjectDefinitionId();
				}
			} else {
				if (ob.getParentObjectDefinitionId() != null && StringUtils.equals(ob.getParentObjectDefinitionId(), parentObjectDefinitionId)) {
					for (TableDesignDetails tableDesignDetails : pkOfTableWithoutFk) {
						if (tableDesignDetails.getColumnId().equals(ob.getColumnId())) {
							whereBuilder.append("<if test=\""+ collectionCode +" != null and "+ collectionCode +".size() > 0 \">");
							whereBuilder.append(DOWNLINE);
							whereBuilder.append("AND");
							whereBuilder.append(DOWNLINE);
							whereBuilder.append(TAB).append(ob.getObjectDefinitionCode()).append(SPACE).append("NOT IN").append(DOWNLINE);
							whereBuilder.append(TAB).append("<foreach item=\"item\" collection=\""+ collectionCode +"\" open=\"(\" separator=\",\" close=\")\">");
							whereBuilder.append(DOWNLINE);
							whereBuilder.append(TAB).append(TAB).append("#{item").append(DOT).append(ob.getObjectDefinitionCode()).append("}");
							whereBuilder.append(DOWNLINE);
							whereBuilder.append(TAB).append("</foreach>");
							whereBuilder.append(DOWNLINE);
							whereBuilder.append("</if>");
							
							if (ob.getDataType() != null && !DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ob.getDataType()) && !DbDomainConst.JavaDataTypeOfBlogic.ENTITY_DATATYPE.equals(ob.getDataType())) {
								SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(null, ob.getObjectDefinitionName(), ob.getObjectDefinitionCode(), ob.getBaseType(), itemSeqNo, ob.getColumnId(), ob.getTblDesignId(), false, sqlDesignInputParentId, true, null, DbDomainConst.DesignType.REFERENCE_TYPE, DbDomainConst.ObjectType.ENTITY_ATTRIBUTE, true);
								lstSqlDesignInput.add(sqlInput);
							}
						}
					}
				}
			}
			itemSeqNo ++;
		}
		String sqlText = MessageFormat.format(builder.toString(), collectionCode, tableCode, whereBuilder.toString());
		sqlDesign = GenerateScreenContruct.populateSqlDesign(DomainDatatypeConst.SQLDesignType.ADVANCED_SQL, sqlDesignName, sqlDesignCode, sqlpattern, screenDesignDefault, screenDesignObj, null, false, null);
		sqlDesign.setSqlText(sqlText);
		sqlDesignRepository.register(sqlDesign);
		Long sqlDesignId = sqlDesign.getSqlDesignId();
		
		if (lstSqlDesignInput.size() > 0) {
			SqlDesignInput[] arrSqlDesignInput = lstSqlDesignInput.toArray(new SqlDesignInput[lstSqlDesignInput.size()]);
			Long startSequenceSqlInput = sqlDesignInputRepository.preserveIds(arrSqlDesignInput.length) - arrSqlDesignInput.length;
			for (int i = 0; i < arrSqlDesignInput.length; i++) {
				if (arrSqlDesignInput[i].getSqlDesignInputParentId() == null) {
					sqlDesignInputParentId = startSequenceSqlInput;
				}
				if (arrSqlDesignInput[i].getSqlDesignInputParentId() != null) {
					arrSqlDesignInput[i].setSqlDesignInputParentId(sqlDesignInputParentId);
				}
				arrSqlDesignInput[i].setSqlDesignId(sqlDesignId);
				arrSqlDesignInput[i].setSqlDesignInputId(startSequenceSqlInput);
				startSequenceSqlInput++;
			}
			sqlDesignInputRepository.registerAll(arrSqlDesignInput);
		}
		sqlDesign.setAllSqlDesignInputs(lstSqlDesignInput);
		return sqlDesign;
	}
	
	/**
	 * 
	 * @param tableDesignDetails
	 * @return
	 */
	private boolean isArrayForCheckbox(TableDesignDetails tableDesignDetails){
		if(tableDesignDetails != null){
			if (tableDesignDetails.getItemType() != null && tableDesignDetails.getItemType().intValue() == DbDomainConst.LogicDataTypePrimitive.CHECKBOX) {
				// Primative Data Type
				if(tableDesignDetails.getDataTypeFlg().equals(DbDomainConst.DataTypeFlag.PRIMITIVE)){
					return getStatusColumn(tableDesignDetails.getDataType(), tableDesignDetails.getConstrainsType(), tableDesignDetails.getDatasourceType(), tableDesignDetails.getDatasourceId());
				}
				// Domain Data Type
				else{
					DomainDesign domainDesign = domainDesignRepository.findOne(tableDesignDetails.getDataType());
					return getStatusColumn(new Long(domainDesign.getBaseType()), domainDesign.getConstrainsType(), domainDesign.getDatasourceType(), domainDesign.getDatasourceId());
				}
			} else {
				return false;
			}
		}else{
			return false;
		}
	}

	/**
	 * 
	 * @param dataType
	 * @param constrainsType
	 * @param datasourceType
	 * @param datasourceId
	 * @return
	 */
	private boolean getStatusColumn(Long dataType, Integer constrainsType, Integer datasourceType, Long datasourceId) {
		if (dataType.equals(new Long(DbDomainConst.BaseType.BOOLEAN_BASETYPE))) {
			return false;
		} else if (DbDomainConst.ConstrainsType.DATASOURCE.equals(constrainsType)) {
			if (DbDomainConst.DatasourceType.USER_DEFINE.equals(datasourceType)) {
				List<UserDefineCodelistDetails> codelistDetails = userDefineCodelistRepository.getAllByCodeList(datasourceId);
				if (FunctionCommon.isNotEmpty(codelistDetails)) {
					if (codelistDetails.size() > 1) {
						return true;
					}
				} else {
					return false;
				}
			} else if (DbDomainConst.DatasourceType.CODELIST.equals(datasourceType)) {
				CodeListDetail[] codeListDetails = codeListDetailRepository.findCodeListDetailByCodeListId(datasourceId);
				if (codeListDetails != null) {
					if (codeListDetails.length > 1) {
						return true;
					}
				} else {
					return false;
				}
			} else if (DbDomainConst.DatasourceType.SQL_BUILDER.equals(datasourceType)) {
				return true;
			}
		}
		return false;
	}
	
	private List<InputBean> getChildInputBean (List<InputBean> allInputBeans, String inputBeanId) {
		List<InputBean> childInputBeans = new ArrayList<InputBean>();
		for (InputBean inputBean : allInputBeans) {
			if (inputBean.getParentInputBeanId() != null && inputBean.getParentInputBeanId().equals(inputBeanId)) {
				childInputBeans.add(inputBean);
			}
		}
		return childInputBeans;
	}
	
	private void populateValidateInputbean(ScreenItem item, TableDesignDetails column, InputBean inputBean, Long businessLogicId, List<ValidationCheckDetail> lstCheckDetails) {
		//set validation check
		if(item.getScreenItemValidation() != null && Integer.valueOf(1).equals(item.getScreenItemValidation().getMandatoryFlg())){
			ValidationCheckDetail details = new ValidationCheckDetail();
			details.setInputBeanId(inputBean.getInputBeanId());
			details.setInputBeanCode(inputBean.getInputBeanCode());
			if(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE.equals(inputBean.getDataType()) || 
					DbDomainConst.JavaDataTypeOfBlogic.BIGDECIMAL_DATATYPE.equals(inputBean.getDataType()) ||
					DbDomainConst.JavaDataTypeOfBlogic.DATE_DATATYPE.equals(inputBean.getDataType()) || 
					DbDomainConst.JavaDataTypeOfBlogic.DATETIME_DATATYPE.equals(inputBean.getDataType()) ||
					DbDomainConst.JavaDataTypeOfBlogic.TIME_DATATYPE.equals(inputBean.getDataType()) || 
					DbDomainConst.JavaDataTypeOfBlogic.TIMESTAMP_DATATYPE.equals(inputBean.getDataType())) {
				details.setValidationType(BusinessDesignConst.ValidateType.NOT_EMPTY);
			} else {
				details.setValidationType(BusinessDesignConst.ValidateType.NOT_NULL);
			}

			List<MessageParameter> params = new ArrayList<MessageParameter>();
			MessageParameter defaultParam = new MessageParameter();
			defaultParam.setBusinessLogicId(businessLogicId);
			defaultParam.setItemSequenceNo(0);
			defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
			defaultParam.setParameterCode(inputBean.getInputBeanName());
			defaultParam.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
			//temp id
			defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
			params.add(defaultParam);
			details.setParameters(params);
			lstCheckDetails.add(details);
		}
		
		if (item.getLogicalDataType() != null && (item.getLogicalDataType().equals(5) || item.getLogicalDataType().equals(6) || item.getLogicalDataType().equals(7))) {
			return;
		}
		// validate length
		if(DbDomainConst.BaseType.CHAR_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.CHARACTER_VARYING_BASETYPE == column.getBaseType() 
				|| DbDomainConst.BaseType.TEXT_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.NUMERIC_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.CURRENCY_BASETYPE == column.getBaseType()) {
			if (column.getMaxlength() != null) {
				ValidationCheckDetail details = new ValidationCheckDetail();
				details.setInputBeanId(inputBean.getInputBeanId());
				details.setInputBeanCode(inputBean.getInputBeanCode());
				Integer javaType = BusinessDesignHelper.convertJavaTypeFromBaseType(column.getBaseType());
				if(BusinessDesignConst.DataType.STRING.equals(javaType) || BusinessDesignConst.DataType.BIGDECIMAL.equals(javaType)) {
					details.setValidationType(BusinessDesignConst.ValidateType.QP_SIZE);
				} else {
					details.setValidationType(BusinessDesignConst.ValidateType.SIZE);
				}
				
				List<MessageParameter> params = new ArrayList<MessageParameter>();
				int itemSeqNoOfMessageParameter = 0;
				
				MessageParameter defaultParam = new MessageParameter();
				defaultParam.setBusinessLogicId(businessLogicId);
				defaultParam.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				defaultParam.setParameterCode(inputBean.getInputBeanName());
				defaultParam.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
				defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParam);
				MessageParameter defaultParamMin = new MessageParameter();
				defaultParamMin.setBusinessLogicId(businessLogicId);
				defaultParamMin.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParamMin.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				defaultParamMin.setParameterValue("0");
				defaultParamMin.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
				defaultParamMin.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParamMin);
				
				MessageParameter defaultParamMax = new MessageParameter();
				defaultParamMax.setBusinessLogicId(businessLogicId);
				defaultParamMax.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParamMax.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				defaultParamMax.setParameterValue(column.getMaxlength().toString());
				defaultParamMax.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
				defaultParamMax.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParamMax);
				
				details.setParameters(params);
				lstCheckDetails.add(details);
			}
		}
		
		// validate minVal
		if (column.getMinVal() != null) {
			if (DbDomainConst.BaseType.TIME_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.DATE_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.DATETIME_BASETYPE == column.getBaseType()) {
				ValidationCheckDetail details = new ValidationCheckDetail();
				details.setInputBeanId(inputBean.getInputBeanId());
				details.setInputBeanCode(inputBean.getInputBeanCode());
				if (DbDomainConst.BaseType.TIME_BASETYPE == column.getBaseType()) {
					details.setValidationType(BusinessDesignConst.ValidateType.TIME_MIN);
				} else if (DbDomainConst.BaseType.DATE_BASETYPE == column.getBaseType()) {
					details.setValidationType(BusinessDesignConst.ValidateType.DATE_MIN);
				} else if (DbDomainConst.BaseType.DATETIME_BASETYPE == column.getBaseType()) {
					details.setValidationType(BusinessDesignConst.ValidateType.DATE_TIME_MIN);
				}
				
				List<MessageParameter> params = new ArrayList<MessageParameter>();
				int itemSeqNoOfMessageParameter = 0;
				
				MessageParameter defaultParam = new MessageParameter();
				defaultParam.setBusinessLogicId(businessLogicId);
				defaultParam.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				defaultParam.setParameterCode(inputBean.getInputBeanName());
				defaultParam.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
				defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParam);
				MessageParameter defaultParamMin = new MessageParameter();
				defaultParamMin.setBusinessLogicId(businessLogicId);
				defaultParamMin.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParamMin.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				defaultParamMin.setParameterValue(column.getMinVal().toString());
				defaultParamMin.setPatternFormat(column.getPatternFormat());
				defaultParamMin.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
				defaultParamMin.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParamMin);
				details.setParameters(params);
				lstCheckDetails.add(details);
			} else {
				ValidationCheckDetail details = new ValidationCheckDetail();
				details.setInputBeanId(inputBean.getInputBeanId());
				details.setInputBeanCode(inputBean.getInputBeanCode());
				if(BusinessDesignConst.DataType.STRING.equals(inputBean.getDataType()) || BusinessDesignConst.DataType.BIGDECIMAL.equals(inputBean.getDataType())) {
					details.setValidationType(BusinessDesignConst.ValidateType.DECIMAL_MIN);
				} else {
					details.setValidationType(BusinessDesignConst.ValidateType.MIN);
				}
				
				List<MessageParameter> params = new ArrayList<MessageParameter>();
				int itemSeqNoOfMessageParameter = 0;
				
				MessageParameter defaultParam = new MessageParameter();
				defaultParam.setBusinessLogicId(businessLogicId);
				defaultParam.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				defaultParam.setParameterCode(inputBean.getInputBeanName());
				defaultParam.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
				defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParam);
				MessageParameter defaultParamMin = new MessageParameter();
				defaultParamMin.setBusinessLogicId(businessLogicId);
				defaultParamMin.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParamMin.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				defaultParamMin.setParameterValue(column.getMinVal().toString());
				defaultParamMin.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
				defaultParamMin.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParamMin);
				details.setParameters(params);
				lstCheckDetails.add(details);
			}
		} else {
			if (DbDomainConst.BaseType.TIME_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.DATE_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.DATETIME_BASETYPE == column.getBaseType()) {
				ValidationCheckDetail details = new ValidationCheckDetail();
				details.setInputBeanId(inputBean.getInputBeanId());
				details.setInputBeanCode(inputBean.getInputBeanCode());
				if(DbDomainConst.BaseType.TIME_BASETYPE == column.getBaseType()) {
					details.setValidationType(BusinessDesignConst.ValidateType.TIME_MIN);
				} else if (DbDomainConst.BaseType.DATE_BASETYPE == column.getBaseType()) {
					details.setValidationType(BusinessDesignConst.ValidateType.DATE_MIN);
				} else if (DbDomainConst.BaseType.DATETIME_BASETYPE == column.getBaseType()) {
					details.setValidationType(BusinessDesignConst.ValidateType.DATE_TIME_MIN);
				}
				List<MessageParameter> params = new ArrayList<MessageParameter>();
				int itemSeqNoOfMessageParameter = 0;
				
				MessageParameter defaultParam = new MessageParameter();
				defaultParam.setBusinessLogicId(businessLogicId);
				defaultParam.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				defaultParam.setParameterCode(inputBean.getInputBeanName());
				defaultParam.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
				defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParam);
				MessageParameter defaultParamMin = new MessageParameter();
				defaultParamMin.setBusinessLogicId(businessLogicId);
				defaultParamMin.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParamMin.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				if(DbDomainConst.BaseType.TIME_BASETYPE == column.getBaseType()) {
					defaultParamMin.setParameterValue(BusinessDesignConst.DEFAULT_TIME_MIN);
					defaultParamMin.setPatternFormat(BusinessDesignConst.PATTEN_DEFAULT_TIME);
				} else if (DbDomainConst.BaseType.DATE_BASETYPE == column.getBaseType()) {
					defaultParamMin.setParameterValue(BusinessDesignConst.DEFAULT_DATE_MIN);
					defaultParamMin.setPatternFormat(BusinessDesignConst.PATTEN_DEFAULT_DATE);
				} else if (DbDomainConst.BaseType.DATETIME_BASETYPE == column.getBaseType()) {
					defaultParamMin.setParameterValue(BusinessDesignConst.DEFAULT_DATETIME_MIN);
					defaultParamMin.setPatternFormat(BusinessDesignConst.PATTEN_DEFAULT_DATETIME);
				}
				defaultParamMin.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
				defaultParamMin.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParamMin);
				details.setParameters(params);
				lstCheckDetails.add(details);
			} else {
				List<String> lstMinMax = defaultMinMax(column.getBaseType());
				if(FunctionCommon.isNotEmpty(lstMinMax)) {
					ValidationCheckDetail details = new ValidationCheckDetail();
					details.setInputBeanId(inputBean.getInputBeanId());
					details.setInputBeanCode(inputBean.getInputBeanCode());
					if(BusinessDesignConst.DataType.STRING.equals(inputBean.getDataType()) || BusinessDesignConst.DataType.BIGDECIMAL.equals(inputBean.getDataType())) {
						details.setValidationType(BusinessDesignConst.ValidateType.DECIMAL_MIN);
					} else {
						details.setValidationType(BusinessDesignConst.ValidateType.MIN);
					}
					List<MessageParameter> params = new ArrayList<MessageParameter>();
					int itemSeqNoOfMessageParameter = 0;
					
					MessageParameter defaultParam = new MessageParameter();
					defaultParam.setBusinessLogicId(businessLogicId);
					defaultParam.setItemSequenceNo(itemSeqNoOfMessageParameter++);
					defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
					defaultParam.setParameterCode(inputBean.getInputBeanName());
					defaultParam.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
					defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
					params.add(defaultParam);
					MessageParameter defaultParamMin = new MessageParameter();
					defaultParamMin.setBusinessLogicId(businessLogicId);
					defaultParamMin.setItemSequenceNo(itemSeqNoOfMessageParameter++);
					defaultParamMin.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
					if(lstMinMax.size() >= 1) {
						defaultParamMin.setParameterValue(lstMinMax.get(0));
					}
					defaultParamMin.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
					defaultParamMin.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
					params.add(defaultParamMin);
					details.setParameters(params);
					lstCheckDetails.add(details);
				}
			}
		}
		
		// validate maxVal
		if (column.getMaxVal() != null) {
			if (DbDomainConst.BaseType.TIME_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.DATE_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.DATETIME_BASETYPE == column.getBaseType()) {
				ValidationCheckDetail details = new ValidationCheckDetail();
				details.setInputBeanId(inputBean.getInputBeanId());
				details.setInputBeanCode(inputBean.getInputBeanCode());
				if (DbDomainConst.BaseType.TIME_BASETYPE == column.getBaseType()) {
					details.setValidationType(BusinessDesignConst.ValidateType.TIME_MAX);
				} else if (DbDomainConst.BaseType.DATE_BASETYPE == column.getBaseType()) {
					details.setValidationType(BusinessDesignConst.ValidateType.DATE_MAX);
				} else if (DbDomainConst.BaseType.DATETIME_BASETYPE == column.getBaseType()) {
					details.setValidationType(BusinessDesignConst.ValidateType.DATE_TIME_MAX);
				}
				List<MessageParameter> params = new ArrayList<MessageParameter>();
				int itemSeqNoOfMessageParameter = 0;
				
				MessageParameter defaultParam = new MessageParameter();
				defaultParam.setBusinessLogicId(businessLogicId);
				defaultParam.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				defaultParam.setParameterCode(inputBean.getInputBeanName());
				defaultParam.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
				defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParam);
				MessageParameter defaultParamMin = new MessageParameter();
				defaultParamMin.setBusinessLogicId(businessLogicId);
				defaultParamMin.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParamMin.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				defaultParamMin.setParameterValue(column.getMaxVal().toString());
				defaultParamMin.setPatternFormat(column.getPatternFormat());
				defaultParamMin.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
				defaultParamMin.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParamMin);
				details.setParameters(params);
				lstCheckDetails.add(details);
			} else {
				ValidationCheckDetail details = new ValidationCheckDetail();
				details.setInputBeanId(inputBean.getInputBeanId());
				details.setInputBeanCode(inputBean.getInputBeanCode());
				if(BusinessDesignConst.DataType.STRING.equals(inputBean.getDataType()) || BusinessDesignConst.DataType.BIGDECIMAL.equals(inputBean.getDataType())) {
					details.setValidationType(BusinessDesignConst.ValidateType.DECIMAL_MAX);
				} else {
					details.setValidationType(BusinessDesignConst.ValidateType.MAX);
				}
				List<MessageParameter> params = new ArrayList<MessageParameter>();
				int itemSeqNoOfMessageParameter = 0;
				
				MessageParameter defaultParam = new MessageParameter();
				defaultParam.setBusinessLogicId(businessLogicId);
				defaultParam.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				defaultParam.setParameterCode(inputBean.getInputBeanName());
				defaultParam.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
				defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParam);
				MessageParameter defaultParamMin = new MessageParameter();
				defaultParamMin.setBusinessLogicId(businessLogicId);
				defaultParamMin.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParamMin.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				defaultParamMin.setParameterValue(column.getMaxVal().toString());
				defaultParamMin.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
				defaultParamMin.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParamMin);
				details.setParameters(params);
				lstCheckDetails.add(details);
			}
		} else {
			if (DbDomainConst.BaseType.TIME_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.DATE_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.DATETIME_BASETYPE == column.getBaseType()) {
				ValidationCheckDetail details = new ValidationCheckDetail();
				details.setInputBeanId(inputBean.getInputBeanId());
				details.setInputBeanCode(inputBean.getInputBeanCode());
				if (DbDomainConst.BaseType.TIME_BASETYPE == column.getBaseType()) {
					details.setValidationType(BusinessDesignConst.ValidateType.TIME_MAX);
				} else if (DbDomainConst.BaseType.DATE_BASETYPE == column.getBaseType()) {
					details.setValidationType(BusinessDesignConst.ValidateType.DATE_MAX);
				} else if (DbDomainConst.BaseType.DATETIME_BASETYPE == column.getBaseType()) {
					details.setValidationType(BusinessDesignConst.ValidateType.DATE_TIME_MAX);
				}
				List<MessageParameter> params = new ArrayList<MessageParameter>();
				int itemSeqNoOfMessageParameter = 0;
				
				MessageParameter defaultParam = new MessageParameter();
				defaultParam.setBusinessLogicId(businessLogicId);
				defaultParam.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				defaultParam.setParameterCode(inputBean.getInputBeanName());
				defaultParam.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
				defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParam);
				MessageParameter defaultParamMin = new MessageParameter();
				defaultParamMin.setBusinessLogicId(businessLogicId);
				defaultParamMin.setItemSequenceNo(itemSeqNoOfMessageParameter++);
				defaultParamMin.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
				if(DbDomainConst.BaseType.TIME_BASETYPE == column.getBaseType()) {
					defaultParamMin.setParameterValue(BusinessDesignConst.DEFAULT_TIME_MAX);
					defaultParamMin.setPatternFormat(BusinessDesignConst.PATTEN_DEFAULT_TIME);
				} else if (DbDomainConst.BaseType.DATE_BASETYPE == column.getBaseType()) {
					defaultParamMin.setParameterValue(BusinessDesignConst.DEFAULT_DATE_MAX);
					defaultParamMin.setPatternFormat(BusinessDesignConst.PATTEN_DEFAULT_DATE);
				} else if (DbDomainConst.BaseType.DATETIME_BASETYPE == column.getBaseType()) {
					defaultParamMin.setParameterValue(BusinessDesignConst.DEFAULT_DATETIME_MAX);
					defaultParamMin.setPatternFormat(BusinessDesignConst.PATTEN_DEFAULT_DATETIME);
				}
				defaultParamMin.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
				defaultParamMin.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
				params.add(defaultParamMin);
				details.setParameters(params);
				lstCheckDetails.add(details);
			} else {
				List<String> lstMinMax = defaultMinMax(column.getBaseType());
				if (FunctionCommon.isNotEmpty(lstMinMax)) {
					ValidationCheckDetail details = new ValidationCheckDetail();
					details.setInputBeanId(inputBean.getInputBeanId());
					details.setInputBeanCode(inputBean.getInputBeanCode());
					if (BusinessDesignConst.DataType.STRING.equals(inputBean.getDataType()) || BusinessDesignConst.DataType.BIGDECIMAL.equals(inputBean.getDataType())) {
						details.setValidationType(BusinessDesignConst.ValidateType.DECIMAL_MAX);
					} else {
						details.setValidationType(BusinessDesignConst.ValidateType.MAX);
					}
					List<MessageParameter> params = new ArrayList<MessageParameter>();
					int itemSeqNoOfMessageParameter = 0;
					
					MessageParameter defaultParam = new MessageParameter();
					defaultParam.setBusinessLogicId(businessLogicId);
					defaultParam.setItemSequenceNo(itemSeqNoOfMessageParameter++);
					defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
					defaultParam.setParameterCode(inputBean.getInputBeanName());
					defaultParam.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
					defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
					params.add(defaultParam);
					MessageParameter defaultParamMin = new MessageParameter();
					defaultParamMin.setBusinessLogicId(businessLogicId);
					defaultParamMin.setItemSequenceNo(itemSeqNoOfMessageParameter++);
					defaultParamMin.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
					if(lstMinMax.size() >= 2) {
						defaultParamMin.setParameterValue(lstMinMax.get(1));
					}
					defaultParamMin.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
					defaultParamMin.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
					params.add(defaultParamMin);
					details.setParameters(params);
					lstCheckDetails.add(details);
				}
			}
		}

		// validation check with domain design data type
		if (column != null && DbDomainConst.DataTypeFlag.DOMAIN_DATA.equals(column.getDataTypeFlg()) && StringUtils.isNotBlank(column.getFmtCode())) {
			if(DbDomainConst.BaseType.CHAR_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.CHARACTER_VARYING_BASETYPE == column.getBaseType() 
					|| DbDomainConst.BaseType.TEXT_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.NUMERIC_BASETYPE == column.getBaseType() || DbDomainConst.BaseType.CURRENCY_BASETYPE == column.getBaseType()) {
				String[] arrFmtCode = column.getFmtCode().split(",");
				for (int i = 0; i < arrFmtCode.length; i++) {
					String fmtCode = arrFmtCode[i];
					List<Integer> lstType = BusinessDesignHelper.convertFmtCodeToType(fmtCode) != null ? BusinessDesignHelper.convertFmtCodeToType(fmtCode) : new ArrayList<Integer>();
					int itemSeqNoOfMessageParameter = 0;
					for (Integer type : lstType) {
						ValidationCheckDetail details = new ValidationCheckDetail();
						details.setInputBeanId(inputBean.getInputBeanId());
						details.setInputBeanCode(inputBean.getInputBeanCode());
						details.setValidationType(type);
						
						List<MessageParameter> params = new ArrayList<MessageParameter>();
						MessageParameter defaultParam = new MessageParameter();
						defaultParam.setBusinessLogicId(businessLogicId);
						defaultParam.setItemSequenceNo(itemSeqNoOfMessageParameter++);
						defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
						defaultParam.setParameterCode(inputBean.getInputBeanName());
						defaultParam.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
						// temp id
						defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
						params.add(defaultParam);
						details.setParameters(params);
						lstCheckDetails.add(details);
					}
				}
			}
		}
	}
	
	private void generateSequenceLogicProcessModify(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, BusinessDesign businessDesign , Long acountId, Timestamp currentDate, List<InputBean> inputbeansRegisted, List<ObjectDefinition> objDefRegisted, List<OutputBean> outputbeanRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		SqlDesign sqlDesign;
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L, checkDetailIdTemp = 0L;
		SequenceConnector connector;
		Integer tempId = 0;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempId++;
		// Set to HaskMap
		mNameParameter = DetailServiceImpHandler.setLevelOfInputBeanOutputBeanObjectDefinition(inputbeansRegisted, outputbeanRegisted, objDefRegisted);

		int itemSeqNoBCheck = 0, index = 0, loopId = 0, ifId = 0, endIfId = 0, trueIf = 0, falseIf = 0;
		List<SequenceConnector> lstSequenceConnectorSpecial = new ArrayList<SequenceConnector>();
		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			if (LIST_TYPE.equals(table.getTableMappingType())) {
				yCoordinates = yCoordinates + 70;
				SequenceLogic loop = GenerateScreenContruct.populateSequenceLogicNew(tempId,  MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0069, table.getTblDesignName()), DbDomainConst.ComponentType.FOREACH, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, true);
				lstSequenceLogic.add(loop);
				// set loop component
				LoopComponent loopComponent = GenerateScreenContruct.contructLoopComponent( MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0069, table.getTblDesignName()), table, inputbeansRegisted, null, tempId.longValue(), index);
				listLoopComponent.add(loopComponent);
				loopId = tempId;
				index++;
				tempId++;

				// connector loop - assign
				SequenceConnector connectorLoopAssign = GenerateScreenContruct.populateSequenceConnector(String.valueOf(loopId), String.valueOf(tempId), BusinessDesignConst.ConnectorType.CYCLE);
				lstSequenceConnectorSpecial.add(connectorLoopAssign);
				
				yCoordinates = yCoordinates + 70;
				SequenceLogic assignInputbeanToEntity = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, 370, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
				lstSequenceLogic.add(assignInputbeanToEntity);
				// set assign component
				AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), tempId.longValue());
				lstAssignComponents.add(assignComponent);
				// set assign details
				if(ScreenDesignConst.CONFIRM_MODIFY_SCREEN.equals(sObj.getScreenTypeName()) && ScreenDesignConst.ConfirmType.SCREEN.equals(screenDesignDefault.getConfirmationType())){
					lstAssignDetails = contructAssignDetailFromInputBeanToObjectDefinition(table, inputbeansRegisted, objDefRegisted, assignIdTemp, LIST_TYPE,loopComponent.getSequenceLogicId().toString());
				}else{
					lstAssignDetails = contructAssignDetailFromInputBeanToObjectDefinitionForCheckbox(screenDesignDefault, table, inputbeansRegisted, objDefRegisted, assignIdTemp,LIST_TYPE,loopComponent.getSequenceLogicId().toString(), screenDesignDefault.getCommonModel(), true);
				}
				assignIdTemp++;
				tempId++;
				
				yCoordinates = yCoordinates + 70;
				SequenceLogic assignObToOu = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, 370, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
				lstSequenceLogic.add(assignObToOu);
				// set assign component
				AssignComponent assignComponentObToOu = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), tempId.longValue());
				lstAssignComponents.add(assignComponentObToOu);
				// set assign details
				lstAssignDetails = contructAssignDetailFromObjectDefinitonToOutputBean(table, objDefRegisted, outputbeanRegisted, assignIdTemp, false, LIST_TYPE, loopComponent.getSequenceLogicId().toString());
				assignIdTemp++;
				tempId++;
				
				// Process modify don't need to check existence
				//				List<BusinessDetailContent> businessDetailContentsTemp = contructBusinessDetailContentByOb(sObj, table, objDefRegisted, checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE);
				//				lstDetailsContents.addAll(businessDetailContentsTemp);
				//				if (!CollectionUtils.sizeIsEmpty(businessDetailContentsTemp)) {
				//	                BusinessCheckDetail checkDetailExisted = GenerateScreenContruct.contructBusinessCheckDetail(checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE, CommonMessageConst.ERR_SYS_0037, tempId.longValue(), itemSeqNoBCheck);
				//				    lstCheckDetails.add(checkDetailExisted);
				//
				//	                MessageParameter messageParameter = GenerateScreenContruct.contructMessageParametter(null, table, BusinessDesignConst.MessageParameter.TARGET_TYPE_BUSINESSCHECK, checkDetailIdTemp, businessLogicId);
				//	                lstMessageParamettersOfBusinessCheckDetail.add(messageParameter);
				//	                checkDetailIdTemp++;
				//				}
				// check duplicate
				List<BusinessDetailContent> businessDetailContentsTemp2 = contructBusinessDetailContentByOb(sObj, table, objDefRegisted, checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_DUPLICATED, businessDesign);
				lstDetailsContents.addAll(businessDetailContentsTemp2);
				if (!CollectionUtils.sizeIsEmpty(businessDetailContentsTemp2)) {
					yCoordinates = yCoordinates + 70;
					SequenceLogic businessCheck = GenerateScreenContruct.populateSequenceLogicNew(tempId,  MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0070, table.getTblDesignName()), DbDomainConst.ComponentType.BUSINESS_CHECK, sequenceNo++, 370, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
					lstSequenceLogic.add(businessCheck);
					// set business component
					BusinessCheckComponent bc = GenerateScreenContruct.contructBusinessCheckComponent( MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0070, table.getTblDesignName()), tempId.longValue());
					lstBusinessCheckComponents.add(bc);

					for (BusinessDetailContent businessCheckDetail : businessDetailContentsTemp2) {
						itemSeqNoBCheck++;
						BusinessCheckDetail checkDetailDuplicate = GenerateScreenContruct.contructBusinessCheckDetail(checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_DUPLICATED, CommonMessageConst.ERR_SYS_0036, tempId.longValue(), itemSeqNoBCheck);
						checkDetailDuplicate.setBusinessCheckDetailId(checkDetailIdTemp);
						businessCheckDetail.setBusinessCheckDetailId(checkDetailIdTemp);
						lstCheckDetails.add(checkDetailDuplicate);

						MessageParameter messageParameterDuplicate = GenerateScreenContruct.contructMessageParametter(null, table, BusinessDesignConst.MessageParameter.TARGET_TYPE_BUSINESSCHECK, checkDetailIdTemp, businessDesign.getBusinessLogicId());
						lstMessageParamettersOfBusinessCheckDetail.add(messageParameterDuplicate);
						checkDetailIdTemp++;
					}
					tempId++;
				}
				
				// Object Definition : add list entity update, list entity insert 
				List<ObjectDefinition> lstObjectDefinitionAddition = registerListObjectDefinitionUpdateAndInsert(table, businessDesign.getBusinessLogicId(), objDefRegisted, true);
				List<ObjectDefinition> lstObjDefInsert = new ArrayList<ObjectDefinition>();
				List<ObjectDefinition> lstObjDefUpdate = new ArrayList<ObjectDefinition>();
				if (FunctionCommon.isNotEmpty(lstObjectDefinitionAddition)) {
					for (ObjectDefinition objdef : lstObjectDefinitionAddition) {
						if (objdef.getTypeListInsertOrUpdate() != null && TYPE_LIST_INSERT.equals(objdef.getTypeListInsertOrUpdate())) {
							lstObjDefInsert.add(objdef);
						} else if (objdef.getTypeListInsertOrUpdate() != null && TYPE_LIST_UPDATE.equals(objdef.getTypeListInsertOrUpdate())) {
							lstObjDefUpdate.add(objdef);
						}
					}
				}
				
				// if component
				yCoordinates = yCoordinates + 70;
				SequenceLogic ifComponent = GenerateScreenContruct.populateSequenceLogicNew(tempId, "If ", BusinessDesignConst.COMPONENT_IF, sequenceNo++, 370, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, true);
				lstSequenceLogic.add(ifComponent);
				IfComponent ifCom = GenerateScreenContruct.contructIfComponent("Check null " + table.getTblDesignName(), "Auto generate", tempId.longValue());
				lstIfComponents.add(ifCom);
				this.processGenFormula(ifCom, inputbeansRegisted, objDefRegisted, table, loopId);
				ifId = tempId;
				tempId++;
				
				//DATLD : gen if component content
				yCoordinates = yCoordinates + 70;
				SequenceLogic ulitilyUpdate = GenerateScreenContruct.populateSequenceLogicNew(tempId, "Add " + table.getTblDesignCode() + " to list update", BusinessDesignConst.COMPONENT_UTILITY, sequenceNo++, 340, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, true);
				lstSequenceLogic.add(ulitilyUpdate);
				UtilityComponent utilityComponentUpdate = GenerateScreenContruct.contructUtilityComponent(ulitilyUpdate.getSequenceLogicName(), ulitilyUpdate.getRemark(), tempId.longValue());
				this.processUltilityComponent(utilityComponentUpdate, lstObjDefUpdate, table.getTblDesignCode());
				lstUtilityComponents.add(utilityComponentUpdate);
				
				// connector if - true
				SequenceConnector connectorUpdate = GenerateScreenContruct.populateSequenceConnector(String.valueOf(ifId), String.valueOf(tempId), BusinessDesignConst.ConnectorType.TRUE);
				lstSequenceConnectorSpecial.add(connectorUpdate);
				trueIf = tempId;
				tempId++;
				
//				yCoordinates = yCoordinates + 70;
				SequenceLogic ulitilyInsert = GenerateScreenContruct.populateSequenceLogicNew(tempId, "Add " + table.getTblDesignCode() + " to list insert", BusinessDesignConst.COMPONENT_UTILITY, sequenceNo++, 640, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, true);
				lstSequenceLogic.add(ulitilyInsert);
				UtilityComponent utilityComponentInsert = GenerateScreenContruct.contructUtilityComponent(ulitilyUpdate.getSequenceLogicName(), ulitilyUpdate.getRemark(), tempId.longValue());
				this.processUltilityComponent(utilityComponentInsert, lstObjDefInsert, table.getTblDesignCode());
				lstUtilityComponents.add(utilityComponentInsert);
				
				// connector if - false
				SequenceConnector connectorInsert = GenerateScreenContruct.populateSequenceConnector(String.valueOf(ifId), String.valueOf(tempId), BusinessDesignConst.ConnectorType.FALSE);
				lstSequenceConnectorSpecial.add(connectorInsert);
				falseIf = tempId;
				tempId++;
				
				yCoordinates = yCoordinates + 70;
				SequenceLogic endIfComponent = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", BusinessDesignConst.COMPONENT_END_IF, sequenceNo++, 370, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, true);
				lstSequenceLogic.add(endIfComponent);
				endIfId = tempId;
				ifComponent.setRelatedSequenceLogicId(String.valueOf(endIfId));
				// connector true - end
				SequenceConnector connectorTrueEnd = GenerateScreenContruct.populateSequenceConnector(String.valueOf(trueIf), String.valueOf(tempId), BusinessDesignConst.ConnectorType.NORMAL);
				lstSequenceConnectorSpecial.add(connectorTrueEnd);
				// connector false - end
				SequenceConnector connectorFalseEnd = GenerateScreenContruct.populateSequenceConnector(String.valueOf(falseIf), String.valueOf(tempId), BusinessDesignConst.ConnectorType.NORMAL);
				lstSequenceConnectorSpecial.add(connectorFalseEnd);
				// connector end - loop
				SequenceConnector connectorEndLoop = GenerateScreenContruct.populateSequenceConnector(String.valueOf(tempId), String.valueOf(loop.getTempSequenceId()), BusinessDesignConst.ConnectorType.BACK);
				lstSequenceConnectorSpecial.add(connectorEndLoop);
				tempId++;
				
				if(loopId != 0) {
					// connector end of loop
					SequenceConnector connectorEndOfLoop = GenerateScreenContruct.populateSequenceConnector(String.valueOf(loopId), String.valueOf(tempId), BusinessDesignConst.ConnectorType.NORMAL);
					lstSequenceConnectorSpecial.add(connectorEndOfLoop);
				}
				// NINHNV
				// execution advance delete
				yCoordinates = yCoordinates + 70;
				SequenceLogic executionBatchDelete = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(BusinessDesignConst.SC_BUSINESSLOGIC_DESIGN_0217) + SPACE + table.getTblDesignName(), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, tempId, null, false);
				lstSequenceLogic.add(executionBatchDelete);
				// execution component
				sqlDesign = autoGenerateAdvanceSqlDelete(screenDesignDefault, sObj, table, lstObjDefUpdate, MessageUtils.getMessage(MESSAGE_CODE_DELETE) + SPACE + table.getTblDesignName(), DELETE_BATCH + SPACE + table.getTblDesignCode(), SqlPattern.DELETE, objDefRegisted);
				ExecutionComponent executionComponentDelete = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), MessageUtils.getMessage(BusinessDesignConst.SC_BUSINESSLOGIC_DESIGN_0217) + SPACE  + table.getTblDesignName(), sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), false);
				lstExecutionComponent.add(executionComponentDelete);
				contructExecutionInputValueForCaseMasterDetails(screenDesignDefault, table, tempId.longValue(), null, lstObjDefUpdate, sqlDesign.getAllSqlDesignInputs(), objDefRegisted);
				tempId++;
				// execution advance update
				yCoordinates = yCoordinates + 70;
				SequenceLogic executionBatchUpdate = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(BusinessDesignConst.SC_BUSINESSLOGIC_DESIGN_0216) + SPACE + table.getTblDesignName(), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, tempId, null, false);
				lstSequenceLogic.add(executionBatchUpdate);
				// execution component
				sqlDesign = autoGenerateAdvanceSqlUpdate(screenDesignDefault, sObj, table, lstObjDefUpdate, MessageUtils.getMessage(MESSAGE_CODE_MODIFY) + SPACE + table.getTblDesignName(), MODIFY_BATCH + SPACE + table.getTblDesignCode(), SqlPattern.UPDATE);
				ExecutionComponent executionComponentUpdate = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), MessageUtils.getMessage(BusinessDesignConst.SC_BUSINESSLOGIC_DESIGN_0216) + SPACE  + table.getTblDesignName(), sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), false);
				lstExecutionComponent.add(executionComponentUpdate);
				contructExecutionInputValueForCaseMasterDetails(screenDesignDefault, table, tempId.longValue(), null, lstObjDefUpdate, sqlDesign.getAllSqlDesignInputs(), objDefRegisted);
				tempId++;
				// execution advance insert
				yCoordinates = yCoordinates + 70;
				SequenceLogic executionBatchInsert = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(BusinessDesignConst.SC_BUSINESSLOGIC_DESIGN_0215) + SPACE + table.getTblDesignName(), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, tempId, null, false);
				lstSequenceLogic.add(executionBatchInsert);
				// execution component
				sqlDesign = autoGenerateAdvanceSqlInsert(screenDesignDefault, sObj, lstObjDefInsert, null, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0088, table.getTblDesignName()), MessageFormat.format(REGISTER_AFTER_DELETE, table.getTblDesignCode()), SqlPattern.INSERT);
				ExecutionComponent executionComponentInsert = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), MessageUtils.getMessage(BusinessDesignConst.SC_BUSINESSLOGIC_DESIGN_0215) + SPACE  + table.getTblDesignName(), sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), false);
				lstExecutionComponent.add(executionComponentInsert);
				contructExecutionInputValueForCaseMasterDetails(screenDesignDefault, table, tempId.longValue(), null, lstObjDefInsert, sqlDesign.getAllSqlDesignInputs(), objDefRegisted);
				tempId++;
			} else {
				// Process modify don't need to check existence
				//				List<BusinessDetailContent> businessDetailContentsTemp = contructBusinessDetailContent(sObj, table, inputbeansRegisted, checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE);
				//				lstDetailsContents.addAll(businessDetailContentsTemp);
				//				if (!CollectionUtils.sizeIsEmpty(businessDetailContentsTemp)) {
				//	                BusinessCheckDetail checkDetailExisted = GenerateScreenContruct.contructBusinessCheckDetail(checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_EXISTENCE, CommonMessageConst.ERR_SYS_0037, tempId.longValue(), itemSeqNoBCheck);
				//	                lstCheckDetails.add(checkDetailExisted);
				//
				//	                MessageParameter messageParameter = GenerateScreenContruct.contructMessageParametter(null, table, BusinessDesignConst.MessageParameter.TARGET_TYPE_BUSINESSCHECK, checkDetailIdTemp, businessLogicId);
				//	                lstMessageParamettersOfBusinessCheckDetail.add(messageParameter);
				//	                checkDetailIdTemp++;
				//				}
				// check duplicate
				List<BusinessDetailContent> businessDetailContentsTemp2 = contructBusinessDetailContent(sObj, table, inputbeansRegisted, checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_DUPLICATED, businessDesign);
				lstDetailsContents.addAll(businessDetailContentsTemp2);
				if (!CollectionUtils.sizeIsEmpty(businessDetailContentsTemp2)) {
					yCoordinates = yCoordinates + 70;
					SequenceLogic businessCheckDuplicated = GenerateScreenContruct.populateSequenceLogicNew(tempId,  MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0070, table.getTblDesignName()), DbDomainConst.ComponentType.BUSINESS_CHECK, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
					lstSequenceLogic.add(businessCheckDuplicated);
					// set business component
					BusinessCheckComponent bc = GenerateScreenContruct.contructBusinessCheckComponent( MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0070, table.getTblDesignName()), tempId.longValue());
					lstBusinessCheckComponents.add(bc);

					for (BusinessDetailContent businessCheckDetail : businessDetailContentsTemp2) {
						itemSeqNoBCheck++;
						BusinessCheckDetail checkDetailDuplicate = GenerateScreenContruct.contructBusinessCheckDetail(checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_DUPLICATED, CommonMessageConst.ERR_SYS_0036, tempId.longValue(), itemSeqNoBCheck);
						checkDetailDuplicate.setBusinessCheckDetailId(checkDetailIdTemp);
						businessCheckDetail.setBusinessCheckDetailId(checkDetailIdTemp);
						lstCheckDetails.add(checkDetailDuplicate);

						MessageParameter messageParameterDuplicate = GenerateScreenContruct.contructMessageParametter(null, table, BusinessDesignConst.MessageParameter.TARGET_TYPE_BUSINESSCHECK, checkDetailIdTemp, businessDesign.getBusinessLogicId());
						lstMessageParamettersOfBusinessCheckDetail.add(messageParameterDuplicate);
						checkDetailIdTemp++;
					}
					tempId++;
				}

				yCoordinates = yCoordinates + 70;
				SequenceLogic assignInputbeanToEntity = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
				lstSequenceLogic.add(assignInputbeanToEntity);
				// set assign component
				AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), tempId.longValue());
				lstAssignComponents.add(assignComponent);
				// set assign details
				if(ScreenDesignConst.CONFIRM_MODIFY_SCREEN.equals(sObj.getScreenTypeName()) && ScreenDesignConst.ConfirmType.SCREEN.equals(screenDesignDefault.getConfirmationType())){
					lstAssignDetails = contructAssignDetailFromInputBeanToObjectDefinition(table, inputbeansRegisted, objDefRegisted, assignIdTemp,null,null);
				}else{
					lstAssignDetails = contructAssignDetailFromInputBeanToObjectDefinitionForCheckbox(screenDesignDefault, table, inputbeansRegisted, objDefRegisted, assignIdTemp,null,null, screenDesignDefault.getCommonModel(), true);
				}
				assignIdTemp++;
				tempId++;
				
				yCoordinates = yCoordinates + 70;
				SequenceLogic assignObToOu = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
				lstSequenceLogic.add(assignObToOu);
				// set assign component
				AssignComponent assignComponentObToOu = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), tempId.longValue());
				lstAssignComponents.add(assignComponentObToOu);
				// set assign details
				lstAssignDetails = contructAssignDetailFromObjectDefinitonToOutputBean(table, objDefRegisted, outputbeanRegisted, assignIdTemp, false, null, null);
				assignIdTemp++;
				tempId++;

				yCoordinates = yCoordinates + 70;
				SequenceLogic executionInsert = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(BusinessDesignConst.SC_BUSINESSLOGIC_DESIGN_0216) + SPACE + table.getTblDesignName(), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
				lstSequenceLogic.add(executionInsert);
				// execution component
				sqlDesign = autoGenerateSqlbuilder(sObj, screenDesignDefault, MessageUtils.getMessage(BusinessDesignConst.SC_BUSINESSLOGIC_DESIGN_0216)  + SPACE + table.getTblDesignName(), UPDATE + SPACE + table.getTblDesignCode(), SqlPattern.UPDATE, table, null, objDefRegisted, null, true, false, false, false, false, false, false);
				ExecutionComponent executionComponent = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), MessageUtils.getMessage(BusinessDesignConst.SC_BUSINESSLOGIC_DESIGN_0216) + SPACE + table.getTblDesignName(), sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), true);
				lstExecutionComponent.add(executionComponent);
				contructExecutionInputValue(screenDesignDefault, table, tempId.longValue(), inputbeansRegisted, objDefRegisted, sqlDesign.getAllSqlDesignInputs(), true);
				tempId++;
			}
		}
		
		yCoordinates = yCoordinates + 70;
		SequenceLogic feedback = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0055), DbDomainConst.ComponentType.FEEDBACK, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
		lstSequenceLogic.add(feedback);
		// set data for feedback component
		FeedbackComponent feedbackComponent = GenerateScreenContruct.contructFeedbackComponent(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0055), CommonMessageConst.INF_SYS_0003, tempId.longValue());
		lstFeedbacks.add(feedbackComponent);
		MessageParameter messageParameter = GenerateScreenContruct.contructMessageParametter(screenDesignDefault, null, BusinessDesignConst.MessageParameter.TARGET_TYPE_FEEDBACK, tempId.longValue(), businessDesign.getBusinessLogicId());
		lstMessageParamettersOfFeedback.add(messageParameter);
		tempId++;
		
		if (ScreenDesignConst.CompleteType.SCREEN.equals(screenDesignDefault.getCompletionType())) {
			yCoordinates = yCoordinates + 70;
			SequenceLogic navigator = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0056), DbDomainConst.ComponentType.NAVIGATOR, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
			lstSequenceLogic.add(navigator);
			// do set data for navigator component
			BusinessDesign displayCompleteModifyBlogic = screenDesignDefault.getMapBusinessLogicId().get(BusinessDesignConst.DISPLAY_COMPLETE_MODIFY);
			NavigatorComponent navigatorComponent = GenerateScreenContruct.populateNavigatorComponent(screenDesignDefault, tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0056), displayCompleteModifyBlogic.getBusinessLogicId(), null, displayCompleteModifyBlogic.getBusinessLogicCode(), BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_BLOGIC);
			lstNavigatorComponents.add(navigatorComponent);
			// navigator details
			contructNavigatorDetail(screenDesignDefault, tempId.longValue(), outputbeanRegisted, displayCompleteModifyBlogic.getBusinessLogicId());
			tempId++;
		} else {
			// do set data for navigator component
			BusinessDesign displaySearchBlogic = screenDesignDefault.getMapBusinessLogicId().get(BusinessDesignConst.DISPLAY_SEARCH);
			if (displaySearchBlogic != null) {
				yCoordinates = yCoordinates + 70;
				SequenceLogic navigator = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0057), DbDomainConst.ComponentType.NAVIGATOR, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
				lstSequenceLogic.add(navigator);

				NavigatorComponent navigatorComponent = GenerateScreenContruct.populateNavigatorComponent(screenDesignDefault, tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0057), displaySearchBlogic.getBusinessLogicId(), null, displaySearchBlogic.getBusinessLogicCode(), BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_BLOGIC);
				lstNavigatorComponents.add(navigatorComponent);
				// navigator details
				contructNavigatorDetail(screenDesignDefault, tempId.longValue(), outputbeanRegisted, displaySearchBlogic.getBusinessLogicId());
			} else {
				yCoordinates = yCoordinates + 70;
				SequenceLogic navigator = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0058), DbDomainConst.ComponentType.NAVIGATOR, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
				lstSequenceLogic.add(navigator);

				BusinessDesign displayModifyBlogic = screenDesignDefault.getMapBusinessLogicId().get(BusinessDesignConst.DISPLAY_MODIFY);
				NavigatorComponent navigatorComponent = GenerateScreenContruct.populateNavigatorComponent(screenDesignDefault, tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0058), displayModifyBlogic.getBusinessLogicId(), null, displayModifyBlogic.getBusinessLogicCode(), BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_BLOGIC);
				lstNavigatorComponents.add(navigatorComponent);
				// navigator details
				contructNavigatorDetail(screenDesignDefault, tempId.longValue(), outputbeanRegisted, displayModifyBlogic.getBusinessLogicId());
			}
			tempId++;
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
		lstSequenceLogic.add(end);

		Map<String, Long> mapKeySequence = new HashMap<String, Long>();

		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);

		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			SequenceLogic logicCurrent = lstSequenceLogic.get(i);
			if(logicCurrent.getFlagHaveConnector() != null && !logicCurrent.getFlagHaveConnector()) {
				connector = GenerateScreenContruct.populateSequenceConnector(logicCurrent.getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
				lstConnector.add(connector);
			}
		}
		
		int sizeOfLstSequenceConnectorSpecial = lstSequenceConnectorSpecial.size() != 0 ? lstSequenceConnectorSpecial.size() : 0;
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() + sizeOfLstSequenceConnectorSpecial - 1);
		startSequence = sequenceConnector - (lstConnector.size() + sizeOfLstSequenceConnectorSpecial - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		for(SequenceConnector obj : lstSequenceConnectorSpecial) {
			obj.setSequenceConnectorId(startSequence++);
			Long source = mapKeySequence.get(obj.getConnectorSource()) != null ? mapKeySequence.get(obj.getConnectorSource()) : 0L;
			Long dest = mapKeySequence.get(obj.getConnectorDest()) != null ? mapKeySequence.get(obj.getConnectorDest()) : 0L;
			obj.setConnectorSource(String.valueOf(source));
			obj.setConnectorDest(String.valueOf(dest));
			lstConnector.add(obj);
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);
		
		updateBLogicNavigator(MODIFY_COMFIRM_NAVIGATE, businessDesign.getBusinessLogicId());
		
		
		// set sequence logic for if component
		for (IfComponent ifComponent : lstIfComponents) {
			ifComponent.setSequenceLogicId(mapKeySequence.get(ifComponent.getSequenceLogicId().toString()));
			for (IfConditionDetail ifConditionDetail : ifComponent.getIfConditionDetails()) {
				for (FormulaDetail formulaDetail : ifConditionDetail.getFormulaDefinitionDetails()) {
					
					for (BDParameterIndex bdParameterIndex : formulaDetail.getLstParameterIndex()) {
						if(BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP.equals(bdParameterIndex.getParameterIndexType())){
							bdParameterIndex.setParameterIndexId(mapKeySequence.get(bdParameterIndex.getParameterIndexId()).toString());
						}
					}
				}
			}
		}
		
		// Formula Definfition
		if(FunctionCommon.isNotEmpty(lstFormulaDefinitionsForAssign)){
			for (FormulaDefinition formulaDefinition : lstFormulaDefinitionsForAssign) {
				for (FormulaDetail formulaDetail : formulaDefinition.getFormulaDefinitionDetails()) {
					if(formulaDetail.getFormulaMethodInputs() != null){
						for (FormulaMethodInput formulaMethodInput : formulaDetail.getFormulaMethodInputs()) {
							if(formulaMethodInput.getLstParameterIndex() != null){
								for (BDParameterIndex bdParameterIndex : formulaMethodInput.getLstParameterIndex()) {
									if(bdParameterIndex != null && bdParameterIndex.getParameterIndexId() != null){
										bdParameterIndex.setParameterIndexId(mapKeySequence.get(bdParameterIndex.getParameterIndexId().toString()).toString());
									}
								}
							}
						}
					}
				}
			}
		} 
		
		// set sequence logic for navigator component
		for (UtilityComponent utilityComponent : lstUtilityComponents) {
			utilityComponent.setSequenceLogicId(mapKeySequence.get(utilityComponent.getSequenceLogicId().toString()));
		}
		
		// set sequence logic for navigator component
		for (NavigatorComponent navi : lstNavigatorComponents) {
			navi.setSequenceLogicId(mapKeySequence.get(navi.getSequenceLogicId().toString()));
		}
		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for loop component
		for (LoopComponent loopObj : listLoopComponent) {
			loopObj.setSequenceLogicId(mapKeySequence.get(loopObj.getSequenceLogicId().toString()));
		}
		// set sequence logic for feedback component
		for (FeedbackComponent fb : lstFeedbacks) {
			fb.setSequenceLogicId(mapKeySequence.get(fb.getSequenceLogicId().toString()));
		}
		// set sequence logic for business check component
		for (BusinessCheckComponent bc : lstBusinessCheckComponents) {
			bc.setSequenceLogicId(mapKeySequence.get(bc.getSequenceLogicId().toString()));
		}
		// set sequence logic for execution component
		for (ExecutionComponent exec : lstExecutionComponent) {
			exec.setSequenceLogicId(mapKeySequence.get(exec.getSequenceLogicId().toString()));
		}
		// mapping key for loop index of assign detail
		for(AssignDetail detail : lstAssignDetails){
			if(CollectionUtils.isNotEmpty(detail.getLstParameterIndex())){
				for (BDParameterIndex indexOfParam : detail.getLstParameterIndex()) {
					if(BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP.equals(indexOfParam.getParameterIndexType())){
						indexOfParam.setParameterIndexId(mapKeySequence.get(indexOfParam.getParameterIndexId()).toString());
					}
				}
			}
		}
	}
	
	public void downloadFileBlogic(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, Long acountId, Timestamp currentDate, ModuleTableMapping moduleTableMapping, List<ScreenItem> keyItems, ScreenItem binaryItem) {
		// REGISTER BUSINESS LOGIC DISPLAY VIEW
		BusinessDesign businessLogic = registerBusinesslogicDefault(sObj, screenDesignDefault, BusinessDesignConst.DOWNLOAD_FILE + WordUtils.capitalize(binaryItem.getItemCode()), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0085, binaryItem.getItemName()), DbDomainConst.BlogicReturnType.DOWNLOAD, acountId, currentDate, BusinessDesignConst.SCREEN_PATTERN_VIEW, BusinessDesignConst.REQUEST_METHOD_PROCESSING);
		// REGISTER INPUT BEAN OF THIS BLOGIC
		List<InputBean> inputbeansRegisted = registerInputBeanOfDownloadFile(sObj, screenDesignDefault, moduleTableMapping, keyItems, businessLogic.getBusinessLogicId(), acountId, currentDate);
		// REGISTER OUTPUT BEAN OF THIS BLOGIC
		List<OutputBean> outputBeansRegisted = registerOutputBeanOfDownloadFile(screenDesignDefault, businessLogic.getBusinessLogicId());
		// GENERATE SEQUENCE LOGIC
		generateSequenceLogicDownloadFile(sObj, screenDesignDefault, moduleTableMapping, businessLogic.getBusinessLogicId(), acountId, currentDate, null, outputBeansRegisted, inputbeansRegisted, binaryItem);
		// GENERATE COMPONENT
		registerComponent();

	}
	
	private SqlDesign autoGenerateSqlbuilderDownloadFile(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, String sqlDesignName, String sqlDesignCode, int sqlpattern, ModuleTableMapping table, List<InputBean> inputBeansRegisted, List<OutputBean> outputBeansRegisted, ScreenItem binaryItem) {
		Integer itemSeqNo = 1;
		List<Long> lstColumnIdOfThisTable = new ArrayList<Long>();
		List<SqlDesignTable> lstSqlDesignTable = new ArrayList<SqlDesignTable>();
		List<SqlDesignTableItem> lstSqlDesignTableItem = new ArrayList<SqlDesignTableItem>();
		List<SqlDesignInput> lstSqlDesignInput = new ArrayList<SqlDesignInput>();
		List<SqlDesignCondition> lstCondition = new ArrayList<SqlDesignCondition>();
		List<SqlDesignResult> lstSqlDesignResult = new ArrayList<SqlDesignResult>();
		List<SqlDesignOutput> lstSqlDesignOutput = new ArrayList<SqlDesignOutput>();
		List<TableDesignDetails> lstData = new ArrayList<TableDesignDetails>();
		SqlDesign sqlDesign = null;
		Long sqlDesignId = null;

		sqlDesign = GenerateScreenContruct.populateSqlDesign(DomainDatatypeConst.SQLDesignType.SQL_BUILDER, sqlDesignName, sqlDesignCode, sqlpattern, screenDesignDefault, screenDesignObj, table, false, 0);
		sqlDesignRepository.register(sqlDesign);
		sqlDesignId = sqlDesign.getSqlDesignId();
		
		if (inputBeansRegisted != null && inputBeansRegisted.size() > 0) {
			for (InputBean in : inputBeansRegisted) {
				if (in.getTblDesignId() != null && in.getTblDesignId().equals(table.getTblDesignId())) {
					lstColumnIdOfThisTable.add(in.getColumnId());
				}
			}
			if (lstColumnIdOfThisTable.size() > 0) {
				lstData = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIdOfThisTable);
				SqlDesignTable sqlTable = GenerateScreenContruct.setSqlDesignTable(sqlDesignId, null, null, table.getTblDesignId(), null, 0, table.getTblDesignName(), null, table.getTblDesignCode(), null);
				lstSqlDesignTable.add(sqlTable);
			}

			if (lstData.size() > 0) {
				boolean flagLogicCode = false;
				for (TableDesignDetails in : lstData) {
					// sql design input
					SqlDesignInput sqlInput = GenerateScreenContruct.setSqlDesignInput(sqlDesignId, in.getName(), in.getCode(), in.getBaseType(), itemSeqNo, in.getColumnId(), in.getTableDesignId(), false, null, false, null, DbDomainConst.DesignType.DESIGN_TYPE, null, null);
					lstSqlDesignInput.add(sqlInput);
					// sql design condition
					SqlDesignCondition condition = GenerateScreenContruct.setSqlDesignCondition(sqlDesignId, in.getTableDesignId(), in.getColumnId(), "0", itemSeqNo, in.getTableDesignName(), in.getName(), in.getBaseType(), itemSeqNo.toString());
					if (!flagLogicCode) {
						condition.setLogicCode("");
						flagLogicCode = true;
					} else {
						condition.setLogicCode("0");
					}
					lstCondition.add(condition);
					itemSeqNo++;
				}
			}
		}
		// sql design result and output
		itemSeqNo = 1;
		Integer itemSeqNoOutput = 1;
		List<TableDesignDetails> allColumns =  table.getListAllColumns();
		// Sql design result
		for (TableDesignDetails col : allColumns) {
			if (col.getColumnId().equals(binaryItem.getColumnId())) {
				SqlDesignResult result = GenerateScreenContruct.setSqlDesignResult(sqlDesignId, BLANK, col.getTableDesignId(), col.getColumnId(), itemSeqNo, table.getTblDesignName(), col.getName(), 1, col.getBaseType());
				lstSqlDesignResult.add(result);
				itemSeqNo++;
			}
		}
		
		// sql design output
		for (OutputBean ou : outputBeansRegisted) {
			if (DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ou.getDataType())) {
				List<OutputBean> childOutputBean = getChildOutputBean(outputBeansRegisted, ou.getOutputBeanId());
				for (OutputBean output : childOutputBean) {
					if (DbDomainConst.JavaDataTypeOfBlogic.BYTE_DATATYPE.equals(output.getDataType()) && output.getArrayFlg()) {
						Long tempSqlOutputId = ou.getOutputBeanId() != null ? Long.parseLong(ou.getOutputBeanId()) : null;
						Long tempSqlOutputParentId = ou.getParentOutputBeanId() != null ? Long.parseLong(ou.getParentOutputBeanId()) : null;
						SqlDesignOutput sqlOutput = GenerateScreenContruct.setSqlDesignOutput(tempSqlOutputId, sqlDesignId, output.getOutputBeanName(), output.getOutputBeanCode(), output.getDataType(), tempSqlOutputParentId, 1,
								itemSeqNoOutput, output.getOutputBeanId(), null, null, ou.getTblDesignId(), ou.getColumnId(), output.getDesignType(), output.getObjectType());
							lstSqlDesignOutput.add(sqlOutput);
					}
				}
			}
			itemSeqNoOutput++;
		}

		if (lstSqlDesignInput.size() > 0) {
			SqlDesignInput[] arrSqlDesignInput = lstSqlDesignInput.toArray(new SqlDesignInput[lstSqlDesignInput.size()]);
			Long startSequenceSqlInput = sqlDesignInputRepository.preserveIds(arrSqlDesignInput.length) - arrSqlDesignInput.length;
			Map<Long, Long> mapSqlInput = new HashMap<Long, Long>();
			for (int i = 0; i < arrSqlDesignInput.length; i++) {
				mapSqlInput.put(arrSqlDesignInput[i].getSqlDesignInputId(), startSequenceSqlInput);
				if (arrSqlDesignInput[i].getSqlDesignInputParentId() != null) {
					arrSqlDesignInput[i].setSqlDesignInputParentId(mapSqlInput.get(arrSqlDesignInput[i].getSqlDesignInputParentId()));
				}
				arrSqlDesignInput[i].setSqlDesignInputId(startSequenceSqlInput++);
			}
			sqlDesignInputRepository.registerAll(arrSqlDesignInput);
		}
		if (lstSqlDesignOutput.size() > 0) {
			SqlDesignOutput[] arrSqlOutput = lstSqlDesignOutput.toArray(new SqlDesignOutput[lstSqlDesignOutput.size()]);
			Map<Long, Long> mapSqlOutput = new HashMap<Long, Long>();
			Long startSqlOutput = sqlDesignOutputRepository.preserveIds(arrSqlOutput.length) - arrSqlOutput.length;
			for (int i = 0; i < arrSqlOutput.length; i++) {
				mapSqlOutput.put(arrSqlOutput[i].getSqlDesignOutputId(), startSqlOutput);
				if (arrSqlOutput[i].getSqlDesignOutputParentId() != null) {
					arrSqlOutput[i].setSqlDesignOutputParentId(mapSqlOutput.get(arrSqlOutput[i].getSqlDesignOutputParentId()));
				}
				arrSqlOutput[i].setSqlDesignOutputId(startSqlOutput);
				startSqlOutput++;
			}
			sqlDesignOutputRepository.registerAll(arrSqlOutput);
		}
		if (lstCondition.size() > 0) {
			SqlDesignCondition[] arrCondition = lstCondition.toArray(new SqlDesignCondition[lstCondition.size()]);
			sqlDesignConditionRepository.registerAll(arrCondition);
		}
		if (lstSqlDesignResult.size() > 0) {
			SqlDesignResult[] arrResults = lstSqlDesignResult.toArray(new SqlDesignResult[lstSqlDesignResult.size()]);
			sqlDesignResultRepository.registerAll(arrResults);
		}
		Map<Long, Long> mapIdSqlTable = new HashMap<Long, Long>();
		if (lstSqlDesignTable.size() > 0) {
			Long sequenceSqlTable = sqlDesignTableRepository.getSequencesSqlDesignTable(lstSqlDesignTable.size() - 1);
			Long startSequence = sequenceSqlTable - (lstSqlDesignTable.size() - 1);
			for (SqlDesignTable sqlTable : lstSqlDesignTable) {
				mapIdSqlTable.put(sqlTable.getSqlDesignTableId(), startSequence);
				sqlTable.setSqlDesignTableId(startSequence++);
			}
			SqlDesignTable[] arrSqlTables = lstSqlDesignTable.toArray(new SqlDesignTable[lstSqlDesignTable.size()]);
			sqlDesignTableRepository.registerAllHaveId(arrSqlTables);
		}
		if (lstSqlDesignTableItem.size() > 0) {
			for (SqlDesignTableItem sqlTableItem : lstSqlDesignTableItem) {
				sqlTableItem.setSqlDesignTableId(mapIdSqlTable.get(sqlTableItem.getSqlDesignTableId()));
			}
			SqlDesignTableItem[] arrSqlTableItems = lstSqlDesignTableItem.toArray(new SqlDesignTableItem[lstSqlDesignTableItem.size()]);
			sqlDesignTableItemsRepository.registerAll(arrSqlTableItems);
		}
	
		sqlDesign.setAllSqlDesignInputs(lstSqlDesignInput);
		sqlDesign.setAllSqlDesignOutputs(lstSqlDesignOutput);
		return sqlDesign;
	}
	
	private List<OutputBean> registerOutputBeanOfDownloadFile(ScreenDesignDefault screenDesignDefault, Long businessLogicId) {
		Integer tempInputbeanId = 0, itemSeqNo = 0, tempParentMultilepartFileId = 0;
		List<OutputBean> outputBeansRegisted = new ArrayList<OutputBean>();
		ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionRepository.findExtObjDefIsMultipartFileByProjectId(workingProjectId);
		List<ExternalObjectAttribute> lstExternalObjectAttribute = new ArrayList<ExternalObjectAttribute>();
		if(externalObjectDefinition != null) {
			lstExternalObjectAttribute = externalObjectAttributeRepository.findExternalObjectAttributeByExternalObjectDefinitionId(externalObjectDefinition.getExternalObjectDefinitionId());
		}
		
		
		if (CollectionUtils.isNotEmpty(lstExternalObjectAttribute)) {
			// Assign object parent form object definition
			OutputBean output = new OutputBean();
			output.setOutputBeanId(tempInputbeanId.toString());
			output.setOutputBeanCode(WordUtils.uncapitalize(ScreenDesignConst.MULTIPART_FILE));
			output.setOutputBeanName(ScreenDesignConst.MULTIPART_FILE);
			output.setDataType(DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE);
			output.setBusinessLogicId(businessLogicId);
			output.setItemSequenceNo(itemSeqNo ++);
			outputBeansRegisted.add(output);
			tempParentMultilepartFileId = tempInputbeanId;
			tempInputbeanId++;
			// Assign object parent form object attribute
			for (ExternalObjectAttribute extObjAttrIter : lstExternalObjectAttribute) {
				output = new OutputBean();
				output.setOutputBeanId(tempInputbeanId.toString());
				output.setOutputBeanCode(extObjAttrIter.getExternalObjectAttributeCode());
				output.setOutputBeanName(extObjAttrIter.getExternalObjectAttributeName());
				output.setDataType(extObjAttrIter.getDataType());
				output.setArrayFlg(extObjAttrIter.getArrayFlg());
				output.setBusinessLogicId(businessLogicId);
				output.setItemSequenceNo(itemSeqNo ++);
				output.setParentOutputBeanId(tempParentMultilepartFileId.toString());
				outputBeansRegisted.add(output);
				tempInputbeanId++;
			}
		}
		
		if (outputBeansRegisted.size() > 0) {
			Long sequenceOutputBeanItem = businessDesignRepository.getSequencesOutputBean(outputBeansRegisted.size() - 1);
			Long startSequence = sequenceOutputBeanItem - (outputBeansRegisted.size() - 1);
			Map<String, Long> mapOutputbeanId = new HashMap<String, Long>();
			for (OutputBean obj : outputBeansRegisted) {
				mapOutputbeanId.put(obj.getOutputBeanId(), startSequence);
				obj.setOutputBeanId(startSequence.toString());
				String parentOutputBeanId = mapOutputbeanId.get(obj.getParentOutputBeanId()) != null ? mapOutputbeanId.get(obj.getParentOutputBeanId()).toString() : null;
				obj.setParentOutputBeanId(parentOutputBeanId);
				startSequence++;
			}
			businessDesignRepository.registerOutputBean(outputBeansRegisted);
		}
		// store all input beans of business logic in map
		screenDesignDefault.getMapOutputBeanOfBLogic().put(businessLogicId, outputBeansRegisted);
		
		return outputBeansRegisted;
	}
	
	private void generateSequenceLogicDownloadFile(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, ModuleTableMapping table, Long businessLogicId, Long acountId, Timestamp currentDate, List<ObjectDefinition> objDefsRegisted, List<OutputBean> outputBeansRegisted, List<InputBean> inputbeansRegisted, ScreenItem binaryItem) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		contructNewListComponents();
		SqlDesign sqlDesign = null;
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence;
		SequenceConnector connector;
		Integer tempId = 0;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempId++;
		yCoordinates = yCoordinates + 70;
		SequenceLogic executionGet = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0053, table.getTblDesignName()), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(executionGet);
		// execution component
		sqlDesign = autoGenerateSqlbuilderDownloadFile(sObj, screenDesignDefault, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0086, binaryItem.getItemName()), GET + SPACE + binaryItem.getItemCode() + SPACE + TO_DOWNLOAD, SqlPattern.SELECT, table, inputbeansRegisted, outputBeansRegisted, binaryItem);
		ExecutionComponent executionComponent = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0086, binaryItem.getItemName()), sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), false);
		lstExecutionComponent.add(executionComponent);
		contructExecutionInputValue(screenDesignDefault, table, tempId.longValue(), inputbeansRegisted, null, sqlDesign.getAllSqlDesignInputs(), false);
		contructExecutionOutputValue(tempId.longValue(), null, sqlDesign.getAllSqlDesignOutputs(), outputBeansRegisted);
		tempId++;
		
		yCoordinates = yCoordinates + 70;
		SequenceLogic seqDownloadFlile = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0085, binaryItem.getItemName()), BusinessDesignConst.COMPONENT_DOWNLOAD_FILE, sequenceNo++, xCoordinates, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(seqDownloadFlile);
		// Download file component
		DownloadFileComponent downloadNode = new DownloadFileComponent();
		downloadNode.setSequenceLogicId(tempId.longValue());
		downloadNode.setLabel(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0085, binaryItem.getItemName()));
		downloadNode.setFileNameContent(binaryItem.getItemCode() + "_" + GenerateUniqueKey.generateAutoCode());
		downloadNode.setParameterScope(BusinessDesignConst.AssignDetailComponent.TARGET_SCOPE_OUTPUT_BEAN);
		downloadNode.setFileNameType(DbDomainConst.FileNameType.USER_INPUT);
		for (OutputBean ou : outputBeansRegisted) {
			if (DbDomainConst.JavaDataTypeOfBlogic.OBJECT_DATATYPE.equals(ou.getDataType())) {
				List<OutputBean> childOutputBean = getChildOutputBean(outputBeansRegisted, ou.getOutputBeanId());
				for (OutputBean output : childOutputBean) {
					if (DbDomainConst.JavaDataTypeOfBlogic.BYTE_DATATYPE.equals(output.getDataType()) && output.getArrayFlg()) {
						downloadNode.setParameterId(output.getOutputBeanId());
						break;
					}
				}
			}
		}
		lstDownloadFileComponents.add(downloadNode);
		tempId++;
		
		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessLogicId, null, null, null, false);
		lstSequenceLogic.add(end);

		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		// register sequence logic
		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);
		
		updateBLogicNavigator(MODIFY_VIEW, businessLogicId);
		updateBLogicNavigator(MODIFY_SEARCH, businessLogicId);

		// register connector
		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			connector = GenerateScreenContruct.populateSequenceConnector(lstSequenceLogic.get(i).getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
			lstConnector.add(connector);
		}
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size() - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);

		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for business check component
		for (BusinessCheckComponent bc : lstBusinessCheckComponents) {
			bc.setSequenceLogicId(mapKeySequence.get(bc.getSequenceLogicId().toString()));
		}
		// set sequence logic for execution component
		for (ExecutionComponent exec : lstExecutionComponent) {
			exec.setSequenceLogicId(mapKeySequence.get(exec.getSequenceLogicId().toString()));
		}
		for (DownloadFileComponent down : lstDownloadFileComponents) {
			down.setSequenceLogicId(mapKeySequence.get(down.getSequenceLogicId().toString()));
		}
	}
	
	private List<InputBean> registerInputBeanOfDownloadFile(ScreenDesign screenDesignObj, ScreenDesignDefault screenDesignDefault, ModuleTableMapping moduleTableMapping, List<ScreenItem> keyItems, Long businessLogicId, Long acountId, Timestamp currentDate) {
		int itemSeqNo = 0;
		List<InputBean> inputbeansRegisted = new ArrayList<InputBean>();
		Integer tempInputbeanId = 0;
		List<ValidationCheckDetail> lstCheckDetails = new ArrayList<ValidationCheckDetail>();
		List<Long> lstColumnIds = new ArrayList<Long>();
		if(FunctionCommon.isNotEmpty(keyItems)) {
			for(ScreenItem item : keyItems) {
				lstColumnIds.add(item.getColumnId());
			}
		}
		List<TableDesignDetails> lstColumns = tableDesignDetailRepository.getTableDesignDetailsByListColumnId(workingProjectId, lstColumnIds);
		Map<Long, TableDesignDetails> mapColumns = new HashMap<Long, TableDesignDetails>();
		if (FunctionCommon.isNotEmpty(lstColumns)) {
			for (TableDesignDetails column : lstColumns) {
				mapColumns.put(column.getColumnId(), column);
			}
		}

		for (ScreenItem item : keyItems) {
			if (screenDesignObj.getScreenId() != null && item.getScreenId() != null && screenDesignObj.getScreenId().equals(item.getScreenId()) && moduleTableMapping.getTblDesignId() != null && item.getTblDesignId() != null && item.getTblDesignId().equals(moduleTableMapping.getTblDesignId())) {
				Integer dataType = BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType());
				TableDesignDetails column = mapColumns.get(item.getColumnId());
				InputBean inputBean = null;
				inputBean = GenerateScreenContruct.setInputbean(tempInputbeanId, screenDesignObj, item.getItemCode(), item.getMessageCode(), dataType, false, null, businessLogicId, item.getScreenItemId(), item.getTblDesignId(), item.getColumnId(), itemSeqNo++, column.getItemType(), DbDomainConst.InputBeanType.DEFAULT);
				inputbeansRegisted.add(inputBean);
				// set validation check
				if (item.getScreenItemValidation() != null && Integer.valueOf(1).equals(item.getScreenItemValidation().getMandatoryFlg())) {
					ValidationCheckDetail details = new ValidationCheckDetail();
					details.setInputBeanId(inputBean.getInputBeanId());
					details.setInputBeanCode(inputBean.getInputBeanCode());
					details.setValidationType(BusinessDesignConst.ValidationCheckDetail.VALIDATIONTYPE_NOTNULL);

					List<MessageParameter> params = new ArrayList<MessageParameter>();
					MessageParameter defaultParam = new MessageParameter();
					defaultParam.setBusinessLogicId(businessLogicId);
					defaultParam.setItemSequenceNo(0);
					defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
					defaultParam.setParameterCode(inputBean.getInputBeanName());
					defaultParam.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
					// temp id
					defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
					params.add(defaultParam);
					details.setParameters(params);
					lstCheckDetails.add(details);
				}

				// validation check with domain design data type
				if (column != null && DbDomainConst.DataTypeFlag.DOMAIN_DATA.equals(column.getDataTypeFlg()) && StringUtils.isNotBlank(column.getFmtCode())) {
					String[] arrFmtCode = column.getFmtCode().split(",");
					for (int i = 0; i < arrFmtCode.length; i++) {
						String fmtCode = arrFmtCode[i];
						List<Integer> lstType = BusinessDesignHelper.convertFmtCodeToType(fmtCode) != null ? BusinessDesignHelper.convertFmtCodeToType(fmtCode) : new ArrayList<Integer>();
						int itemSeqNoOfMessageParameter = 0;
						for (Integer type : lstType) {
							ValidationCheckDetail details = new ValidationCheckDetail();
							details.setInputBeanId(inputBean.getInputBeanId());
							details.setInputBeanCode(inputBean.getInputBeanCode());
							details.setValidationType(type);

							List<MessageParameter> params = new ArrayList<MessageParameter>();
							MessageParameter defaultParam = new MessageParameter();
							defaultParam.setBusinessLogicId(businessLogicId);
							defaultParam.setItemSequenceNo(itemSeqNoOfMessageParameter++);
							defaultParam.setMessageLevel(DbDomainConst.MessageLevel.SCREEN);
							defaultParam.setParameterCode(inputBean.getInputBeanName());
							defaultParam.setParameterType(BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
							// temp id
							defaultParam.setTargetType(BusinessDesignConst.MessageParameter.TARGET_TYPE_VALIDATION);
							params.add(defaultParam);
							details.setParameters(params);
							lstCheckDetails.add(details);
						}
					}
				}
				tempInputbeanId++;
			}
		}
		Map<String, Long> mapInputBeanId = new HashMap<String, Long>();
		if (inputbeansRegisted.size() > 0) {
			Long sequenceInputBeanItem = businessDesignRepository.getSequencesInputBean(inputbeansRegisted.size() - 1);
			Long startSequence = sequenceInputBeanItem - (inputbeansRegisted.size() - 1);
			for (InputBean obj : inputbeansRegisted) {
				mapInputBeanId.put(obj.getInputBeanId(), startSequence);
				obj.setInputBeanId(startSequence.toString());
				String parentInputBeanId = mapInputBeanId.get(obj.getParentInputBeanId()) != null ? mapInputBeanId.get(obj.getParentInputBeanId()).toString() : null;
				obj.setParentInputBeanId(parentInputBeanId);
				startSequence++;
			}
			businessDesignRepository.registerInputBean(inputbeansRegisted);
			
			//register default validation check
			registerValidationCheckOfInputBean(lstCheckDetails,mapInputBeanId);
		}
		// store all input beans of business logic in map
		screenDesignDefault.getMapInputBeanOfBLogic().put(businessLogicId, inputbeansRegisted);

		return inputbeansRegisted;
	
	}
	
	private List<OutputBean> getChildOutputBean (List<OutputBean> allOutputBeans, String outputBeanId) {
		List<OutputBean> childOutputBeans = new ArrayList<OutputBean>();
		for (OutputBean outputBean : allOutputBeans) {
			if (outputBean.getParentOutputBeanId() != null && outputBean.getParentOutputBeanId().equals(outputBeanId)) {
				childOutputBeans.add(outputBean);
			}
		}
		return childOutputBeans;
	}
	
	/**
	 * @param sObj
	 * @param screenDesignDefault
	 * @param businessLogicId
	 * @param acountId
	 * @param currentDate
	 */
	private void generateSequenceLogicProcessRegister(ScreenDesign sObj, ScreenDesignDefault screenDesignDefault, BusinessDesign businessDesign, Long acountId, Timestamp currentDate, List<InputBean> inputbeansRegisted, List<ObjectDefinition> objDefRegisted, List<OutputBean> outputbeanRegisted) {
		List<SequenceLogic> lstSequenceLogic = new ArrayList<SequenceLogic>();
		List<SequenceConnector> lstConnector = new ArrayList<SequenceConnector>();
		SqlDesign sqlDesign;
		contructNewListComponents();
		int sequenceNo = 0, xCoordinates = 35, yCoordinates = 15;
		Long startSequence, assignIdTemp = 0L, checkDetailIdTemp = 0L;
		SequenceConnector connector;
		Integer tempId = 0;
		SequenceLogic slStart = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.START, sequenceNo++, 100, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
		lstSequenceLogic.add(slStart);
		tempId++;
		mNameParameter = DetailServiceImpHandler.setLevelOfInputBeanOutputBeanObjectDefinition(inputbeansRegisted, outputbeanRegisted, objDefRegisted);

		int index=0;
		int itemSeqNoBCheck = 0;
		for (ModuleTableMapping table : screenDesignDefault.getModuleTableMappings()) {
			if (LIST_TYPE.equals(table.getTableMappingType())) {
				yCoordinates = yCoordinates + 70;
				SequenceLogic loop = GenerateScreenContruct.populateSequenceLogicNew(tempId,  MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0069, table.getTblDesignName()), DbDomainConst.ComponentType.FOREACH, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), tempId, null, null, false);
				lstSequenceLogic.add(loop);
				LoopComponent loopComponent = GenerateScreenContruct.contructLoopComponent( MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0069, table.getTblDesignName()), table, inputbeansRegisted, null, tempId.longValue(),index);
				listLoopComponent.add(loopComponent);
				index ++;
				tempId++;

				yCoordinates = yCoordinates + 70;
				SequenceLogic assignInputbeanToEntity = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, 370, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
				lstSequenceLogic.add(assignInputbeanToEntity);
				// set assign component
				AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), tempId.longValue());
				lstAssignComponents.add(assignComponent);
				// set assign details
				if(ScreenDesignConst.CONFIRM_REGISTER_SCREEN.equals(sObj.getScreenTypeName()) && ScreenDesignConst.ConfirmType.SCREEN.equals(screenDesignDefault.getConfirmationType())){
					lstAssignDetails = contructAssignDetailFromInputBeanToObjectDefinition(table, inputbeansRegisted, objDefRegisted, assignIdTemp,LIST_TYPE,loopComponent.getSequenceLogicId().toString());
				}else{
					lstAssignDetails = contructAssignDetailFromInputBeanToObjectDefinitionForCheckbox(screenDesignDefault, table, inputbeansRegisted, objDefRegisted, assignIdTemp,LIST_TYPE,loopComponent.getSequenceLogicId().toString(), screenDesignDefault.getCommonModel(), false);
				}
				assignIdTemp++;
				tempId++;
				
				if (2 == screenDesignDefault.getCompletionType().intValue()) {
					yCoordinates = yCoordinates + 70;
					SequenceLogic assignObToOu = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, 370, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
					lstSequenceLogic.add(assignObToOu);
					// set assign component
					AssignComponent assignComponentObToOu = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), tempId.longValue());
					lstAssignComponents.add(assignComponentObToOu);
					// set assign details
					lstAssignDetails = contructAssignDetailFromObjectDefinitonToOutputBean(table, objDefRegisted, outputbeanRegisted, assignIdTemp, false, LIST_TYPE, loopComponent.getSequenceLogicId().toString());
					assignIdTemp++;
					tempId++;
				}
				
				//quangvd
				//In case of auto increment : no check duplicate
				if(this.isDuplicatedCheck(table,inputbeansRegisted)){
					List<BusinessDetailContent> businessDetailContentsTemp = contructBusinessDetailContentByOb(sObj, table, objDefRegisted, checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_DUPLICATED, businessDesign);
					lstDetailsContents.addAll(businessDetailContentsTemp);
					if (!CollectionUtils.sizeIsEmpty(businessDetailContentsTemp)) {
						yCoordinates = yCoordinates + 70;
						SequenceLogic businessCheckDuplicated = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0043, table.getTblDesignName()), DbDomainConst.ComponentType.BUSINESS_CHECK, sequenceNo++, 370, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
						lstSequenceLogic.add(businessCheckDuplicated);
						// set business component
						BusinessCheckComponent bc = GenerateScreenContruct.contructBusinessCheckComponent(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0043, table.getTblDesignName()), tempId.longValue());
						lstBusinessCheckComponents.add(bc);

						for (BusinessDetailContent businessCheckDetail : businessDetailContentsTemp) {
							BusinessCheckDetail checkDetail = GenerateScreenContruct.contructBusinessCheckDetail(checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_DUPLICATED, CommonMessageConst.ERR_SYS_0036, tempId.longValue(), itemSeqNoBCheck);
							checkDetail.setBusinessCheckDetailId(checkDetailIdTemp);
							businessCheckDetail.setBusinessCheckDetailId(checkDetailIdTemp);
							lstCheckDetails.add(checkDetail);

							MessageParameter messageParameter = GenerateScreenContruct.contructMessageParametter(null, table, BusinessDesignConst.MessageParameter.TARGET_TYPE_BUSINESSCHECK, checkDetailIdTemp, businessDesign.getBusinessLogicId());
							lstMessageParamettersOfBusinessCheckDetail.add(messageParameter);
							checkDetailIdTemp++;
							itemSeqNoBCheck++;
						}
					}
					tempId++;
				}
				
				// Object Definition : add list entity update, list entity insert 
				List<ObjectDefinition> lstObjectDefinitionAddition = registerListObjectDefinitionUpdateAndInsert(table, businessDesign.getBusinessLogicId(), objDefRegisted, false);
				
				yCoordinates = yCoordinates + 70;
				SequenceLogic ulitilyUpdate = GenerateScreenContruct.populateSequenceLogicNew(tempId, "Add " + table.getTblDesignCode() + " to list insert", BusinessDesignConst.COMPONENT_UTILITY, sequenceNo++, 340, yCoordinates, businessDesign.getBusinessLogicId(), null, tempId, null, false);
				lstSequenceLogic.add(ulitilyUpdate);
				UtilityComponent utilityComponentUpdate = GenerateScreenContruct.contructUtilityComponent(ulitilyUpdate.getSequenceLogicName(), ulitilyUpdate.getRemark(), tempId.longValue());
				this.processUltilityComponent(utilityComponentUpdate, lstObjectDefinitionAddition, table.getTblDesignCode());
				lstUtilityComponents.add(utilityComponentUpdate);
				tempId++;
				
				// execution insert
				yCoordinates = yCoordinates + 70;
				SequenceLogic executionBatchInsert = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(BusinessDesignConst.SC_BUSINESSLOGIC_DESIGN_0215) + SPACE + table.getTblDesignName(), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
				lstSequenceLogic.add(executionBatchInsert);
				// execution component
				sqlDesign = autoGenerateAdvanceSqlInsert(screenDesignDefault, sObj, lstObjectDefinitionAddition, null, MessageUtils.getMessage(MESSAGE_CODE_REGISTER) + SPACE + table.getTblDesignName(), REGISTER_BATCH + SPACE + table.getTblDesignCode(), SqlPattern.INSERT);
				ExecutionComponent executionComponentInsert = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), MessageUtils.getMessage(BusinessDesignConst.SC_BUSINESSLOGIC_DESIGN_0215) + SPACE  + table.getTblDesignName(), sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), false);
				lstExecutionComponent.add(executionComponentInsert);
				contructExecutionInputValueForCaseMasterDetails(screenDesignDefault, table, tempId.longValue(), null, lstObjectDefinitionAddition, sqlDesign.getAllSqlDesignInputs(), objDefRegisted);
				tempId++;
				
			} else {

				//quangvd
				//In case of auto increment : no check duplicate
				if(this.isDuplicatedCheck(table,inputbeansRegisted)){
					List<BusinessDetailContent> businessDetailContentsTemp = contructBusinessDetailContent(sObj, table, inputbeansRegisted, checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_DUPLICATED, businessDesign);
					lstDetailsContents.addAll(businessDetailContentsTemp);

					if (!CollectionUtils.sizeIsEmpty(businessDetailContentsTemp)) {
						yCoordinates = yCoordinates + 70;
						SequenceLogic businessCheckDuplicated = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0043, table.getTblDesignName()), DbDomainConst.ComponentType.BUSINESS_CHECK, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
						lstSequenceLogic.add(businessCheckDuplicated);
						// set business component
						BusinessCheckComponent bc = GenerateScreenContruct.contructBusinessCheckComponent(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0043, table.getTblDesignName()), tempId.longValue());
						lstBusinessCheckComponents.add(bc);

						for (BusinessDetailContent businessCheckDetail : businessDetailContentsTemp) {
							BusinessCheckDetail checkDetail = GenerateScreenContruct.contructBusinessCheckDetail(checkDetailIdTemp, BusinessDesignConst.BusinessCheckComponent.BCHECK_TYPE_DUPLICATED, CommonMessageConst.ERR_SYS_0036, tempId.longValue(), itemSeqNoBCheck);
							checkDetail.setBusinessCheckDetailId(checkDetailIdTemp);
							businessCheckDetail.setBusinessCheckDetailId(checkDetailIdTemp);
							lstCheckDetails.add(checkDetail);

							MessageParameter messageParameter = GenerateScreenContruct.contructMessageParametter(null, table, BusinessDesignConst.MessageParameter.TARGET_TYPE_BUSINESSCHECK, checkDetailIdTemp, businessDesign.getBusinessLogicId());
							lstMessageParamettersOfBusinessCheckDetail.add(messageParameter);
							checkDetailIdTemp++;
							itemSeqNoBCheck++;
						}
					}
					tempId++;
				}
				yCoordinates = yCoordinates + 70;
				SequenceLogic assignInputbeanToEntity = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
				lstSequenceLogic.add(assignInputbeanToEntity);
				// set assign component
				AssignComponent assignComponent = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0044, table.getTblDesignName()), tempId.longValue());
				lstAssignComponents.add(assignComponent);
				// set assign details
				if(ScreenDesignConst.CONFIRM_REGISTER_SCREEN.equals(sObj.getScreenTypeName()) && ScreenDesignConst.ConfirmType.SCREEN.equals(screenDesignDefault.getConfirmationType())){
					lstAssignDetails = contructAssignDetailFromInputBeanToObjectDefinition(table, inputbeansRegisted, objDefRegisted, assignIdTemp,null,null);
				}else{
					lstAssignDetails = contructAssignDetailFromInputBeanToObjectDefinitionForCheckbox(screenDesignDefault, table, inputbeansRegisted, objDefRegisted, assignIdTemp,null,null, screenDesignDefault.getCommonModel(), false);
				}
				assignIdTemp++;
				tempId++;
				
				if (2 == screenDesignDefault.getCompletionType().intValue()) {
					yCoordinates = yCoordinates + 70;
					SequenceLogic assignObToOu = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), DbDomainConst.ComponentType.ASSIGN, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
					lstSequenceLogic.add(assignObToOu);
					// set assign component
					AssignComponent assignComponentObToOu = GenerateScreenContruct.contructAssignComponent(assignIdTemp, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0045, table.getTblDesignName()), tempId.longValue());
					lstAssignComponents.add(assignComponentObToOu);
					// set assign details
					lstAssignDetails = contructAssignDetailFromObjectDefinitonToOutputBean(table, objDefRegisted, outputbeanRegisted, assignIdTemp, false, null, null);
					assignIdTemp++;
					tempId++;
				}
				
				yCoordinates = yCoordinates + 70;
				SequenceLogic executionInsert = GenerateScreenContruct.populateSequenceLogicNew(tempId, "Insert " + table.getTblDesignName(), DbDomainConst.ComponentType.EXECUTION, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
				lstSequenceLogic.add(executionInsert);
				// execution component
				sqlDesign = autoGenerateSqlbuilder(sObj, screenDesignDefault, MessageUtils.getMessage(BusinessDesignConst.SC_BUSINESSLOGIC_DESIGN_0215) + SPACE + table.getTblDesignName(), "Insert " + table.getTblDesignCode(), SqlPattern.INSERT, table, null, objDefRegisted,null, false, false, false, false, false, false, true);
				ExecutionComponent executionComponent = GenerateScreenContruct.contructExecutionComponent(tempId.longValue(), "Insert " + table.getTblDesignName(), sqlDesign.getSqlDesignId(), sqlDesign, tempId.longValue(), false);
				lstExecutionComponent.add(executionComponent);
				contructExecutionInputValue(screenDesignDefault, table, tempId.longValue(), null, objDefRegisted, sqlDesign.getAllSqlDesignInputs(), false);
				tempId++;
			}
		}
		
		yCoordinates = yCoordinates + 70;
		SequenceLogic feedback = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0065), DbDomainConst.ComponentType.FEEDBACK, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
		lstSequenceLogic.add(feedback);
		// set data for feedback component
		FeedbackComponent feedbackComponent = GenerateScreenContruct.contructFeedbackComponent(MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0065), CommonMessageConst.INF_SYS_0002, tempId.longValue());
		lstFeedbacks.add(feedbackComponent);
		MessageParameter messageParameter = GenerateScreenContruct.contructMessageParametter(screenDesignDefault, null, BusinessDesignConst.MessageParameter.TARGET_TYPE_FEEDBACK, tempId.longValue(), businessDesign.getBusinessLogicId());
		lstMessageParamettersOfFeedback.add(messageParameter);
		tempId++;
		
		if (ScreenDesignConst.CompleteType.SCREEN.equals(screenDesignDefault.getCompletionType())) {
			yCoordinates = yCoordinates + 70;
			SequenceLogic navigator = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0066), DbDomainConst.ComponentType.NAVIGATOR, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
			lstSequenceLogic.add(navigator);
			// do set data for navigator component
			BusinessDesign displayCompleteRegisterBlogic = screenDesignDefault.getMapBusinessLogicId().get(BusinessDesignConst.DISPLAY_COMPLETE_REGISTER);
			NavigatorComponent navigatorComponent = GenerateScreenContruct.populateNavigatorComponent(screenDesignDefault, tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0066), displayCompleteRegisterBlogic.getBusinessLogicId(), null, displayCompleteRegisterBlogic.getBusinessLogicCode(), BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_BLOGIC);
			lstNavigatorComponents.add(navigatorComponent);
			// navigator details
			contructNavigatorDetail(screenDesignDefault, tempId.longValue(), outputbeanRegisted, displayCompleteRegisterBlogic.getBusinessLogicId());
			tempId++;
		} else {
			// do set data for navigator component
			BusinessDesign displaySearchBlogic = screenDesignDefault.getMapBusinessLogicId().get(BusinessDesignConst.DISPLAY_SEARCH);
			if (displaySearchBlogic != null) {
				yCoordinates = yCoordinates + 70;
				SequenceLogic navigator = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0057), DbDomainConst.ComponentType.NAVIGATOR, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
				lstSequenceLogic.add(navigator);

				NavigatorComponent navigatorComponent = GenerateScreenContruct.populateNavigatorComponent(screenDesignDefault, tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0057), displaySearchBlogic.getBusinessLogicId(), null, displaySearchBlogic.getBusinessLogicCode(), BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_BLOGIC);
				lstNavigatorComponents.add(navigatorComponent);
				// navigator details
				contructNavigatorDetail(screenDesignDefault, tempId.longValue(), outputbeanRegisted, displaySearchBlogic.getBusinessLogicId());
			} else {
				yCoordinates = yCoordinates + 70;
				SequenceLogic navigator = GenerateScreenContruct.populateSequenceLogicNew(tempId, MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0067), DbDomainConst.ComponentType.NAVIGATOR, sequenceNo++, xCoordinates, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
				lstSequenceLogic.add(navigator);

				BusinessDesign displayRegisterBlogic = screenDesignDefault.getMapBusinessLogicId().get(BusinessDesignConst.DISPLAY_REGISTER);
				NavigatorComponent navigatorComponent = GenerateScreenContruct.populateNavigatorComponent(screenDesignDefault, tempId.longValue(), MessageUtils.getMessage(GenerateSourceCodeConst.SC_GENERATESOURCECODE_0067), displayRegisterBlogic.getBusinessLogicId(), null, displayRegisterBlogic.getBusinessLogicCode(), BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_BLOGIC);
				lstNavigatorComponents.add(navigatorComponent);
				// navigator details
				contructNavigatorDetail(screenDesignDefault, tempId.longValue(), outputbeanRegisted, displayRegisterBlogic.getBusinessLogicId());
			}
			tempId++;
		}

		yCoordinates = yCoordinates + 70;
		SequenceLogic end = GenerateScreenContruct.populateSequenceLogicNew(tempId, "", DbDomainConst.ComponentType.END, sequenceNo, 100, yCoordinates, businessDesign.getBusinessLogicId(), null, null, null, false);
		lstSequenceLogic.add(end);

		Map<String, Long> mapKeySequence = new HashMap<String, Long>();
		// register sequence logic
		Long sequenceLogic = businessDesignRepository.getSequencesLogic(lstSequenceLogic.size() - 1);
		startSequence = sequenceLogic - (lstSequenceLogic.size() - 1);
		for (SequenceLogic objSequenceLogic : lstSequenceLogic) {
			mapKeySequence.put(objSequenceLogic.getTempSequenceId().toString(), startSequence);
			objSequenceLogic.setSequenceLogicId(startSequence.toString());
			startSequence++;
		}
		businessDesignRepository.registerSequenceLogic(lstSequenceLogic);

		// register connector
		Long firstPointForEach = null;
		for (int i = 0; i < lstSequenceLogic.size() - 1; i++) {
			SequenceLogic logicCurrent = lstSequenceLogic.get(i);
			if(logicCurrent.getFlagHaveConnector() != null && !logicCurrent.getFlagHaveConnector()) {
				if (logicCurrent.getFirstPointForEarch() != null) {
					connector = GenerateScreenContruct.populateSequenceConnector(logicCurrent.getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.CYCLE);
					lstConnector.add(connector);
					firstPointForEach = mapKeySequence.get(logicCurrent.getFirstPointForEarch().toString());
				} else if (logicCurrent.getEndPointForEarch() != null) {
					// connector back
					connector = GenerateScreenContruct.populateSequenceConnector(logicCurrent.getSequenceLogicId(), firstPointForEach.toString(), BusinessDesignConst.ConnectorType.BACK);
					lstConnector.add(connector);
					
					// connector normal
					connector = GenerateScreenContruct.populateSequenceConnector(firstPointForEach.toString(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
					lstConnector.add(connector);
				} else {
					connector = GenerateScreenContruct.populateSequenceConnector(logicCurrent.getSequenceLogicId(), lstSequenceLogic.get(i + 1).getSequenceLogicId(), BusinessDesignConst.ConnectorType.NORMAL);
					lstConnector.add(connector);
				}
			}
		}
		
		updateBLogicNavigator(REGISTER_COMFIRM_NAVIGATE, businessDesign.getBusinessLogicId());
		
		Long sequenceConnector = businessDesignRepository.getSequencesConnector(lstConnector.size()  - 1);
		startSequence = sequenceConnector - (lstConnector.size() - 1);
		
		for (SequenceConnector obj : lstConnector) {
			obj.setSequenceConnectorId(startSequence);
			startSequence++;
		}
		businessDesignRepository.registerSequenceConnector(lstConnector);
		
		// set sequence logic for navigator component
		for (UtilityComponent utilityComponent : lstUtilityComponents) {
			utilityComponent.setSequenceLogicId(mapKeySequence.get(utilityComponent.getSequenceLogicId().toString()));
		}

		// set sequence logic for navigator component
		for (NavigatorComponent navi : lstNavigatorComponents) {
			navi.setSequenceLogicId(mapKeySequence.get(navi.getSequenceLogicId().toString()));
		}
		// set sequence logic for assign component
		for (AssignComponent assign : lstAssignComponents) {
			assign.setSequenceLogicId(mapKeySequence.get(assign.getSequenceLogicId().toString()));
		}
		// set sequence logic for loop component
		for (LoopComponent loopObj : listLoopComponent) {
			loopObj.setSequenceLogicId(mapKeySequence.get(loopObj.getSequenceLogicId().toString()));
		}
		// set sequence logic for feedback component
		for (FeedbackComponent fb : lstFeedbacks) {
			fb.setSequenceLogicId(mapKeySequence.get(fb.getSequenceLogicId().toString()));
		}
		// set sequence logic for business check component
		for (BusinessCheckComponent bc : lstBusinessCheckComponents) {
			bc.setSequenceLogicId(mapKeySequence.get(bc.getSequenceLogicId().toString()));
		}
		// set sequence logic for execution component
		for (ExecutionComponent exec : lstExecutionComponent) {
			exec.setSequenceLogicId(mapKeySequence.get(exec.getSequenceLogicId().toString()));
		}
		// mapping key for loop index of assign detail
		for(AssignDetail detail : lstAssignDetails){
			if(CollectionUtils.isNotEmpty(detail.getLstParameterIndex())){
				for (BDParameterIndex indexOfParam : detail.getLstParameterIndex()) {
					if(BusinessDesignConst.ParameterIndex.INDEX_TYPE_LOOP.equals(indexOfParam.getParameterIndexType())){
						indexOfParam.setParameterIndexId(mapKeySequence.get(indexOfParam.getParameterIndexId()).toString());
					}
				}
			}

		}
		
		// Formula Definfition
		if(FunctionCommon.isNotEmpty(lstFormulaDefinitionsForAssign)){
			for (FormulaDefinition formulaDefinition : lstFormulaDefinitionsForAssign) {
				for (FormulaDetail formulaDetail : formulaDefinition.getFormulaDefinitionDetails()) {
					if(formulaDetail.getFormulaMethodInputs() != null){
						for (FormulaMethodInput formulaMethodInput : formulaDetail.getFormulaMethodInputs()) {
							if(formulaMethodInput.getLstParameterIndex() != null){
								for (BDParameterIndex bdParameterIndex : formulaMethodInput.getLstParameterIndex()) {
									if(bdParameterIndex != null && bdParameterIndex.getParameterIndexId() != null){
										bdParameterIndex.setParameterIndexId(mapKeySequence.get(bdParameterIndex.getParameterIndexId().toString()).toString());
									}
								}
							}
						}
					}
				}
			}
		} 
	}
	
	public static List<String> defaultMinMax(Integer baseType) {
		List<String> listReturn = new ArrayList<String>();
		// if base type is : Integer
		if (DbDomainConst.BaseType.INTEGER_BASETYPE == baseType) {
			listReturn.add(String.valueOf(Integer.MIN_VALUE));
			listReturn.add(String.valueOf(Integer.MAX_VALUE));
			return listReturn;
		} else if (DbDomainConst.BaseType.SMALLINT_BASETYPE == baseType) {
			listReturn.add(String.valueOf(Short.MIN_VALUE));
			listReturn.add(String.valueOf(Short.MAX_VALUE));
			return listReturn;
		} else if (DbDomainConst.BaseType.BIGINT_BASETYPE == baseType) {
			listReturn.add(String.valueOf(Long.MIN_VALUE));
			listReturn.add(String.valueOf(Long.MAX_VALUE));
			return listReturn;
		} else if (DbDomainConst.BaseType.SERIAL_BASETYPE == baseType) {
			listReturn.add(String.valueOf(DomainDatatypeConst.NumericSize.SERIAL_MIN));
			listReturn.add(String.valueOf(Integer.MAX_VALUE));
			return listReturn;
		} else if (DbDomainConst.BaseType.BIGSERIAL_BASETYPE == baseType) {
			listReturn.add(String.valueOf(DomainDatatypeConst.NumericSize.BIGSERIAL_MIN));
			listReturn.add(String.valueOf(Long.MAX_VALUE));
			return listReturn;
		} else if (DbDomainConst.BaseType.BYTE_BASETYPE == baseType) {
			listReturn.add(String.valueOf(Byte.MIN_VALUE));
			listReturn.add(String.valueOf(Byte.MAX_VALUE));
			return listReturn;
		} 
//		else if (DbDomainConst.BaseType.FLOAT_BASETYPE == baseType) {
//			listReturn.add(String.valueOf(Float.MIN_VALUE));
//			listReturn.add(String.valueOf(Float.MAX_VALUE));
//			return listReturn;
//		} else if (DbDomainConst.BaseType.DOUBLE_BASETYPE == baseType) {
//			listReturn.add(String.valueOf(Double.MIN_VALUE));
//			listReturn.add(String.valueOf(Double.MAX_VALUE));
//			return listReturn;
//		}
		return listReturn;
	}
	
	public static List<MessageDesign> filterMessageCanUse(List<MessageDesign> lstMessCheck, List<MessageDesign> listMessRegisted, List<?> lstObject, Map<String, Object> messageCommons) {
		List<MessageDesign> messages = new ArrayList<>();
		List<MessageDesign> messagesReturn = new ArrayList<>();
		listMessRegisted = listMessRegisted == null ? new ArrayList<MessageDesign>() : listMessRegisted;
		if (FunctionCommon.isNotEmpty(lstMessCheck)) {
			for (MessageDesign newMess : lstMessCheck) {
				boolean flgAdded = false;
				for (MessageDesign registedMess : listMessRegisted) {
					if (StringUtils.isNotBlank(newMess.getMessageString()) && newMess.getMessageString().equals(registedMess.getMessageString())) {
						messages.add(registedMess);
						flgAdded = true;
						break;
					}
				}
				if (!flgAdded) {
					messages.add(newMess);
				}
			}
		}
		if (FunctionCommon.isNotEmpty(lstObject)) {
			for (Object obj : lstObject) {
				if (obj instanceof ScreenDesign) {
					MessageDesign messObj = ((ScreenDesign) obj).getMessageDesign();
					if (messObj != null && StringUtils.isNotBlank(messObj.getMessageString())) {
						for (MessageDesign m : messages) {
							if (messObj.getMessageString().equals(m.getMessageString())) {
								((ScreenDesign) obj).setMessageDesign(m);
							}
						}
					}
				} else if (obj instanceof ModuleTableMapping) {
					MessageDesign messObj = ((ModuleTableMapping) obj).getMessageDesign();
					if (messObj != null && StringUtils.isNotBlank(messObj.getMessageString())) {
						for (MessageDesign m : messages) {
							if (messObj.getMessageString().equals(m.getMessageString())) {
								((ModuleTableMapping) obj).setMessageDesign(m);
							}
						}
					}
					if (FunctionCommon.isNotEmpty(((ModuleTableMapping) obj).getListAllColumns())) {
						for (TableDesignDetails col : ((ModuleTableMapping) obj).getListAllColumns()) {
							MessageDesign messObjOfCol = col.getMessageDesign();
							if (messObjOfCol != null && StringUtils.isNotBlank(messObjOfCol.getMessageString())) {
								for (MessageDesign m : messages) {
									if (messObjOfCol.getMessageString().equals(m.getMessageString())) {
										col.setMessageDesign(m);
									}
								}
							}
						}
					}
				}
			}
		}
		if (messageCommons != null) {
			MessageDesign messlinkGotoSearchScreen = null;
			MessageDesign messlinkGotoRegisterScreen = null;
			for (Iterator<Map.Entry<String, Object>> it = messageCommons.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, Object> entry = it.next();
				boolean flgAdded = false;
				if (entry.getKey().equals("linkGotoSearchScreen")) {
					Object value = entry.getValue();
					if (value instanceof MessageDesign) {
						for (MessageDesign mess : messages) {
							if (mess.getMessageDesignId() != null && mess.getMessageString().equals(((MessageDesign) value).getMessageString())) {
								flgAdded = true;
								messlinkGotoSearchScreen = mess;
								break;
							}
						}
					}
					if (flgAdded) {
						it.remove();
					}
				}
				if (entry.getKey().equals("linkGotoRegisterScreen")) {
					Object value = entry.getValue();
					if (value instanceof MessageDesign) {
						for (MessageDesign mess : messages) {
							if (mess.getMessageDesignId() != null && mess.getMessageString().equals(((MessageDesign) value).getMessageString())) {
								flgAdded = true;
								messlinkGotoRegisterScreen = mess;
								break;
							}
						}
					}
					if (flgAdded) {
						it.remove();
					}
				}
			}
			if (messlinkGotoSearchScreen != null) {
				messageCommons.put("linkGotoSearchScreen", messlinkGotoSearchScreen);
			}
			if (messlinkGotoRegisterScreen != null) {
				messageCommons.put("linkGotoRegisterScreen", messlinkGotoRegisterScreen);
			}
		}
		
		for(MessageDesign mess : messages) {
			if(mess.getMessageDesignId() == null) {
				messagesReturn.add(mess);
			}
		}
		return messagesReturn;
	}
}