package org.terasoluna.qp.domain.service.screendesign;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.constants.DbDomainConst.LogicDataType;
import org.terasoluna.qp.app.common.constants.DbDomainConst.MessageLevel;
import org.terasoluna.qp.app.common.constants.TerasolunaQPValidationConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.GenerateUniqueKey;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.MessageDesignMessageConst;
import org.terasoluna.qp.app.message.ScreenDesignMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CodeList;
import org.terasoluna.qp.domain.model.CodeListDetail;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DomainDatatypeCodelist;
import org.terasoluna.qp.domain.model.DomainDatatypeItem;
import org.terasoluna.qp.domain.model.DomainDesign;
import org.terasoluna.qp.domain.model.ExternalObjectAttribute;
import org.terasoluna.qp.domain.model.ExternalObjectDefinition;
import org.terasoluna.qp.domain.model.InfoModuleForScreenDesign;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MenuDesign;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.MessageParameter;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.ModuleTableMapping;
import org.terasoluna.qp.domain.model.NavigatorComponent;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.ProblemList;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenAction;
import org.terasoluna.qp.domain.model.ScreenActionParam;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenAreaEvent;
import org.terasoluna.qp.domain.model.ScreenAreaSortMapping;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenFormTabGroup;
import org.terasoluna.qp.domain.model.ScreenFormTabs;
import org.terasoluna.qp.domain.model.ScreenGroupItem;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemAutocompleteInput;
import org.terasoluna.qp.domain.model.ScreenItemCodelist;
import org.terasoluna.qp.domain.model.ScreenItemEvent;
import org.terasoluna.qp.domain.model.ScreenItemEventMapping;
import org.terasoluna.qp.domain.model.ScreenItemOutput;
import org.terasoluna.qp.domain.model.ScreenItemSequence;
import org.terasoluna.qp.domain.model.ScreenItemValidation;
import org.terasoluna.qp.domain.model.ScreenParameter;
import org.terasoluna.qp.domain.model.ScreenTransition;
import org.terasoluna.qp.domain.model.ScreenTransitionBranch;
import org.terasoluna.qp.domain.model.ScreenTransitionBranchDetail;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.model.TableDesignDetails;
import org.terasoluna.qp.domain.model.UserDefineCodelistDetails;
import org.terasoluna.qp.domain.model.ValidationCheckDetail;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.MessageParameterRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.NavigationComponentRepository;
import org.terasoluna.qp.domain.repository.businessdesigncomponent.ValidationCheckDetailRepository;
import org.terasoluna.qp.domain.repository.codelist.CodeListDetailRepository;
import org.terasoluna.qp.domain.repository.domaindatatype.DomainDatatypeCodelistRepository;
import org.terasoluna.qp.domain.repository.domaindatatype.DomainDatatypeItemRepository;
import org.terasoluna.qp.domain.repository.domaindesign.DomainDesignRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectAttributeRepository;
import org.terasoluna.qp.domain.repository.externalobjectdefinition.ExternalObjectDefinitionRepository;
import org.terasoluna.qp.domain.repository.messagedesign.MessageDesignRepository;
import org.terasoluna.qp.domain.repository.module.ModuleRepository;
import org.terasoluna.qp.domain.repository.problemlist.ProblemListRepository;
import org.terasoluna.qp.domain.repository.screenaction.ScreenActionRepository;
import org.terasoluna.qp.domain.repository.screenactionparam.ScreenActionParamRepository;
import org.terasoluna.qp.domain.repository.screenarea.ScreenAreaRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignOutputBeanForSetting;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRegister;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignSearchCriteria;
import org.terasoluna.qp.domain.repository.screenform.ScreenFormRepository;
import org.terasoluna.qp.domain.repository.screengroupitem.ScreenGroupItemRepository;
import org.terasoluna.qp.domain.repository.screenitem.ScreenItemRepository;
import org.terasoluna.qp.domain.repository.screenitemcodelist.ScreenItemCodelistRepository;
import org.terasoluna.qp.domain.repository.screenitemsequence.ScreenItemSequenceRepository;
import org.terasoluna.qp.domain.repository.screentransition.ScreenTransitionRepository;
import org.terasoluna.qp.domain.repository.tabledesign.UserDefineCodelistRepository;
import org.terasoluna.qp.domain.service.autocomplete.AutocompleteDesignService;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignHelper;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignService;
import org.terasoluna.qp.domain.service.codelist.CodeListService;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.generatescreen.GenerateScreenContruct;
import org.terasoluna.qp.domain.service.generatescreen.ScreenDesignDefault;
import org.terasoluna.qp.domain.service.generatescreen.ScreenDesignGeneratorService;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GraphicDatabaseDesignService;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;
import org.terasoluna.qp.domain.service.menudesign.MenuDesignService;
import org.terasoluna.qp.domain.service.messagedesign.MessageDesignService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignService;
import org.terasoluna.qp.domain.service.tabledesign.TableDesignDetailService;
import org.terasoluna.qp.domain.service.tabledesign.TableDesignService;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class ScreenDesignServiceImpl implements ScreenDesignService {
	
	@Inject
	Mapper beanMapper;
	
	@Inject 
	SystemService systemService;

	@Inject
	ScreenDesignRepository screendesignRepository;

	@Inject
	ScreenActionRepository screenActionRepository;

	@Inject
	ScreenActionParamRepository screenActionParamRepository;

	@Inject
	ScreenAreaRepository screenAreaRepository;

	@Inject
	ScreenItemRepository screenItemRepository;

	@Inject
	ScreenItemSequenceRepository screenItemSequenceRepository;

	@Inject
	ScreenGroupItemRepository screenGroupItemRepository;

	@Inject
	ScreenItemCodelistRepository screenItemCodelistRepository;

	@Inject
	ModuleRepository moduleRepository;

	@Inject
	ScreenDesignGeneratorService generateScreenService;

	@Inject
	MessageDesignService messageDesignService;

	@Inject
	ModuleService moduleService;

	@Inject
	MessageDesignRepository messageDesignRepository;

	@Inject
	DomainDatatypeCodelistRepository domainDatatypeCodelistRepository;

	@Inject
	CodeListDetailRepository codeListDetailRepository;

	@Inject
	DomainDatatypeItemRepository domainDatatypeItemRepository;

	@Inject
	LanguageDesignService languageDesignService;

	@Inject
	TableDesignService tableDesignService;

	@Inject
	CodeListService codeListService;

	@Inject
	SqlDesignService sqlDesignService;

	@Inject
	UserDefineCodelistRepository userDefineCodelistRepository;

	@Inject
	ScreenDesignRepository screenDesignRepository;

	@Inject
	ScreenFormRepository screenFormRepository;

	@Inject
	BusinessDesignRepository businessDesignRepository;

	@Inject
	ProblemListRepository problemListRepository;

	@Inject
	AutocompleteDesignService autocompleteDesignService;

	@Inject
	BusinessDesignService businessDesignService;

	@Inject
	MenuDesignService menuDesignService;
	
	@Inject
	NavigationComponentRepository navigationComponentRepository;

	@Inject
	ExternalObjectDefinitionRepository externalObjectDefinitionRepository;

	@Inject
	ExternalObjectAttributeRepository externalObjectAttributeRepository;
	
	@Inject
	ScreenTransitionRepository screenTransitionRepository;
	
	@Inject
	GraphicDatabaseDesignService graphicDatabaseDesignService;

	@Inject
	@Named("CL_BD_DATATYPE_NOT_OBJECT_ENTITY")
	org.terasoluna.gfw.common.codelist.CodeList clDataType; // (1)

	@Inject
	ProjectService projectService;
	
	@Inject
	ValidationCheckDetailRepository validationCheckDetailRepository;
	
	@Inject
	MessageParameterRepository messageParameterRepository;
	
	@Inject
	DomainDesignRepository domainDesignRepository;

	@Inject
	private TableDesignDetailService tableDesignDetailService;

	private final Integer ITEM_TYPE_NORMAL = 1;
	private final String MESSAGE_TYPE_SCREEN = "sc";
	private final Integer SHOW_BLANK_ITEM = 1;
	
	private final Integer GEN_HTML = 0;
	private final Integer GEN_JSP = 1;
	private static final String AUTOCOMPLETE = "Autocomplete";
	
	/** ◘ */
	private static final String INVERSE_BULLET_SYSBOL = "◘";
	/** � */
	private static final String REPLACEMENT_CHARACTER_SYSBOL = "�";
	private static final Logger logger = LoggerFactory.getLogger(ScreenDesignServiceImpl.class);

	@Override
	public Page<ScreenDesign> findPageByCriteria(ScreenDesignSearchCriteria criteria, Pageable pageable, Long languageId) {
		/* MessageDesign messageDesign = new MessageDesign(); */
		// get LanguageId by languageCode and contryCode
		/*
		 * String languageCode = LocaleUtils.getDesaultLanguage() .getLanguageCode(); String countryCode = LocaleUtils.getDesaultLanguage().getCountryCode(); LanguageDesign param = new LanguageDesign(languageCode, countryCode); LanguageDesign languageDesign = languageDesignService .findByLanguageDesign(param);
		 */
		criteria.setLanguageId(languageId);
		/* criteria.setMessageDesign(messageDesign); */
		long totalCount = screendesignRepository.countBySearchCriteria(criteria);
		List<ScreenDesign> articles;
		if (0 < totalCount) {
			articles = screendesignRepository.findPageBySearchCriteria(criteria, pageable);
		} else {
			articles = Collections.emptyList();
		}
		Page<ScreenDesign> page = new PageImpl<ScreenDesign>(articles, pageable, totalCount);
		return page;
	}

	@Override
	public List<ScreenAction> findAllActionByScreenId(List<ScreenDesign> scrDesigns, Long languageId, Long projectId) {
		List<ScreenAction> screenActs = new ArrayList<ScreenAction>();
		List<ScreenAction> screenActsTmp;

		if (scrDesigns != null && scrDesigns.size() > 0) {
			for (ScreenDesign sa : scrDesigns) {
				screenActsTmp = screenActionRepository.findAllActionByScreenId(sa.getScreenId());
				if (screenActsTmp != null && screenActsTmp.size() > 0) {
					screenActs.addAll(screenActsTmp);
				}
			}
		}
		
		
		List<BusinessDesign> businessDesigns = businessDesignRepository.findBLogicByProject(projectId);
		
		for (ScreenAction screenAction : screenActs) {
			List<NavigatorComponent> lstNavComponent = navigationComponentRepository.findNavigationComponentByBusinessLogic(screenAction.getNavigateToBlogicId(), languageId, projectId);
			for (BusinessDesign businessDesign : businessDesigns) {
				if(businessDesign.getBusinessLogicId().equals(screenAction.getNavigateToBlogicId())){
					if(BusinessDesignConst.REQUEST_METHOD_PROCESSING.equals(businessDesign.getRequestMethod())){
						this.findScreenIdWhenReturnMethodIsProcess(businessDesigns,screenAction, lstNavComponent);
					}else if(BusinessDesignConst.REQUEST_METHOD_INITIAL.equals(businessDesign.getRequestMethod())){
						screenAction.setToScreenId(businessDesign.getScreenId());
					}
				}
			}
		}
		ScreenDesign screenDesign = null;
		MessageDesign messageDesign = null;
		
		long screenId = -1;
//		int xCoordinate = 200, yCoordinate= 250;
		for (int i = 0; i < screenActs.size(); i++) {
			ScreenAction screenAction = screenActs.get(i);
			if(screenAction.getToScreenId() == null || screenAction.getToScreenId() <= 0){
				screenDesign = new ScreenDesign();
				messageDesign = new MessageDesign();
				messageDesign.setMessageString("Screen Common");
				screenDesign.setTemplateType(-1);
				screenDesign.setScreenId(screenId);
				
				for (ScreenDesign sd : scrDesigns) {
					if(sd.getScreenId().equals(screenAction.getFromScreenId())){
						screenDesign.setxCoordinate(sd.getxCoordinate());
						screenDesign.setyCoordinate(sd.getyCoordinate() + 100);
					}
				}
				screenDesign.setMessageDesign(messageDesign);
				scrDesigns.add(screenDesign);
				screenAction.setToScreenId(screenId);
				screenId--;
//				yCoordinate += 500;
			}
		}
		return screenActs;
	}

	/**
	 * @param businessDesigns
	 * @param screenAction
	 * @param lstNavComponent
	 */
	private void findScreenIdWhenReturnMethodIsProcess(List<BusinessDesign> businessDesigns, ScreenAction screenAction, List<NavigatorComponent> lstNavComponent) {
		if(FunctionCommon.isNotEmpty(lstNavComponent) && lstNavComponent.size() == 1){
			NavigatorComponent navigatorComponent = lstNavComponent.get(0);
			if(BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_SCREEN.equals(navigatorComponent.getNavigatorToType())){
				screenAction.setToScreenId(navigatorComponent.getNavigatorToId());
			}else if(BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_BLOGIC.equals(navigatorComponent.getNavigatorToType())){
				for (BusinessDesign blogic : businessDesigns) {
					if(navigatorComponent.getNavigatorToId().equals(blogic.getBusinessLogicId())){
						screenAction.setToScreenId(blogic.getScreenId());
					}
				}
			}else if(BusinessDesignConst.NavigatorComponent.NAVIGATOR_TO_TYPE_COMMON.equals(navigatorComponent.getNavigatorToType())){
				screenAction.setToScreenId(null);
			}
		}
	}

	@Override
	public boolean updatePositionByScreenId(List<ScreenDesign> scrDesigns) {

		boolean updated = screendesignRepository.updatePositionByScreenId(scrDesigns);

		return updated;
	}

	@Override
	public List<ScreenDesign> getAllScreenInfoByModuleId(Long moduleId, Long languageId) {
		List<ScreenDesign> scrDesigns = screendesignRepository.getAllScreenInfoByModuleId(moduleId, languageId);
		return scrDesigns;
	}

	@Override
	public List<ScreenDesignOutput> getColumnsByTableId(long tableId, Long columnId) {
		// TODO Auto-generated method stub
		List<ScreenDesignOutput> columns = screendesignRepository.getColumnsByTableId(tableId, columnId);
		List<TableDesignDetails> tableDesignDetails = tableDesignDetailService.getAllInformationByTableDesign(tableId);
		List<ScreenDesignOutput> columnsOfDomain = new ArrayList<ScreenDesignOutput>();
		
		for (int i = 0; i < columns.size(); i++) {
			
			for (TableDesignDetails detail : tableDesignDetails) {
				if (!StringUtils.isEmpty(columns.get(i).getColumnId())) {
					Long column = Long.parseLong(columns.get(i).getColumnId());
					if (column.equals(detail.getColumnId())) {
						columns.get(i).setPhysicaldatatype(detail.getBaseType() + "");
					}

				}
			}

			ScreenDesignOutput obj = columns.get(i);
			if (obj.getDatatype() == null) {
				continue;
			}
			if (obj.getDataSourceId() != null && obj.getDataSourceId().length() > 0) {
				String isSupportOptionValue = "";
				List<ScreenItemCodelist> lstCodelistDetail = new ArrayList<ScreenItemCodelist>();

				// check data source type
				switch (obj.getDatasourcetype()) {
				case "0":// user define

					// setting parameter
					List<UserDefineCodelistDetails> userDefineCodelistDetails = userDefineCodelistRepository.getAllByCodeList(Long.parseLong(obj.getDataSourceId()));

					if (userDefineCodelistDetails != null) {
						for (UserDefineCodelistDetails UserDefineCodelistDetail : userDefineCodelistDetails) {
							ScreenItemCodelist objTemp = new ScreenItemCodelist();
							objTemp.setCodelistName(UserDefineCodelistDetail.getCodelistName());
							objTemp.setCodelistVal(UserDefineCodelistDetail.getCodelistValue());
							isSupportOptionValue = UserDefineCodelistDetail.getSupportOptionFlg() + "";
							lstCodelistDetail.add(objTemp);
						}
					}
					if (isSupportOptionValue.equals("0")) {
						obj.setIsSupportOptionValue("true");
					} else if (isSupportOptionValue.equals("1")) {
						obj.setIsSupportOptionValue("false");
					}

					obj.setLocalCodelist(3 + "");
					obj.setDatasourcetype(2 + "");
					break;
				case "1":// codelist

					// setting parameter
					List<CodeListDetail> codeListDetails = screendesignRepository.getCodeListDetailByCodeListId(Long.parseLong(obj.getDataSourceId()));
					if (codeListDetails != null) {
						for (CodeListDetail codeListDetail : codeListDetails) {
							ScreenItemCodelist objTemp = new ScreenItemCodelist();
							objTemp.setCodelistName(codeListDetail.getName());
							objTemp.setCodelistVal(codeListDetail.getValue());
							lstCodelistDetail.add(objTemp);
						}
					}

					CodeList codeList = codeListService.getCodeList(Long.parseLong(obj.getDataSourceId()));

					if (codeList.getIsOptionValude() == null) {
						obj.setIsSupportOptionValue("false");
					} else {
						if (codeList.getIsOptionValude().equals("1")) {
							obj.setIsSupportOptionValue("true");
						} else if (codeList.getIsOptionValude().equals("0")) {
							obj.setIsSupportOptionValue("false");
						}
					}

					obj.setCodelistText(codeList.getCodeListName());
					obj.setCodelistcode(codeList.getCodeListId() + "");
					obj.setCodelistCode(codeList.getCodeListId() + "");
					obj.setLocalCodelist(1 + "");
					obj.setDatasourcetype(2 + "");

					break;
				case "2": // sql design
					AutocompleteDesign au = autocompleteDesignService.findOneById(Long.parseLong(obj.getDataSourceId()));
					obj.setSqldesignid(obj.getDataSourceId() + "");
					obj.setSqldesignidtext(au.getAutocompleteName());
					obj.setDatasourcetype(1 + "");
					break;
				case "3":
					obj.setDatasourcetype(1 + "");
					try {
						SqlDesign sqlDesign = sqlDesignService.findById(Long.parseLong(obj.getDataSourceId()));

						obj.setSqldesignid(obj.getDataSourceId() + "");
						obj.setSqldesignidtext(sqlDesign.getSqlDesignName());

					} catch (Exception ex) {
						// TODO: not implement
					}
					break;
				default:
					obj.setDatasourcetype("");
					break;
				}

				if (lstCodelistDetail != null && lstCodelistDetail.size() > 0) {

					String parameters = "";
					String msglabel = "";
					String msgvalue = "";
					for (ScreenItemCodelist screenItemCodelist : lstCodelistDetail) {

						parameters += HtmlUtils.htmlEscape(screenItemCodelist.getCodelistName()) + ScreenDesignConst.ITEM_SPLIT + HtmlUtils.htmlEscape(screenItemCodelist.getCodelistVal()) + ScreenDesignConst.ROW_SPLIT;

						String label = "";
						if (screenItemCodelist.getCodelistName() != null && !screenItemCodelist.getCodelistName().isEmpty()) {
							label = HtmlUtils.htmlEscape(screenItemCodelist.getCodelistName());
						} else {
							label = HtmlUtils.htmlEscape(screenItemCodelist.getCodelistVal());
						}

						msglabel += label + ScreenDesignConst.ROW_SPLIT;
						msgvalue += HtmlUtils.htmlEscape(screenItemCodelist.getCodelistVal()) + ScreenDesignConst.ROW_SPLIT;
					}
					if (lstCodelistDetail.size() > 0) {
						parameters = parameters.substring(0, parameters.length() - 1);
						msglabel = msglabel.substring(0, msglabel.length() - 1);
						msgvalue = msgvalue.substring(0, msgvalue.length() - 1);
					}
					obj.setParameters(parameters);
					obj.setMsglabel(msglabel);
					obj.setMsgvalue(msgvalue);
				}

			}
			if(columns.get(i).getIsDomainType() != null && columns.get(i).getIsDomainType().equals("1")) {
				columnsOfDomain.add(columns.get(i));
			}
		}
		
		//Domain type
		if(columnsOfDomain != null && columnsOfDomain.size() > 0) {
			List<DomainDesign> arrDomain = domainDesignRepository.findListByScreenDesignOutput(columnsOfDomain);
			for(ScreenDesignOutput output : columnsOfDomain) {
				for(DomainDesign dDesign : arrDomain) {
					if(dDesign.getDomainId().equals(output.getDatatypeIdDomainType())) {
						output.setBaseType(String.valueOf(dDesign.getBaseType()));
						output.setMaxlength(String.valueOf(dDesign.getMaxLength()));
					}
				}
			}
		}
		
		for(ScreenDesignOutput output : columns) {
			for(ScreenDesignOutput outputDomain : columnsOfDomain) {
				if(output.getDatatypeIdDomainType() != null && outputDomain.getDatatypeIdDomainType() != null && output.getDatatypeIdDomainType().equals(outputDomain.getDatatypeIdDomainType())) {
					output.setBaseType(outputDomain.getBaseType());
					output.setMaxlength(outputDomain.getMaxlength());
				}
			}
		}
		
		for (Iterator<ScreenDesignOutput> iterator = columns.iterator(); iterator.hasNext();) {
			ScreenDesignOutput screenDesignOutput = (ScreenDesignOutput) iterator.next();
			if(screenDesignOutput.getIsUsedOnScreen() != "" && screenDesignOutput.getIsUsedOnScreen().equals("0") && screenDesignOutput.getItemType() != "" && screenDesignOutput.getItemType().equals("1")) {
				iterator.remove();
			}
		}
		
		return columns;
	}

	@Override
	public void design(ScreenDesign screenDesign, ScreenForm[] screenForms, ScreenArea[] screenAreas, ScreenGroupItem[] screenGroupItems, ScreenItem[] screenItems,
			Long accountId, Long languageId, Long projectId, AccountProfile accountProfile) throws BusinessException {
		for (ScreenItem item : screenItems) {
			if (item.getCustomItemContent() != null && (item.getCustomItemContent() == "" || item.getCustomItemContent().equals("π"))) {
				item.setCustomItemContent("");
			}
		}
		List<MessageDesign> messageDesigns = new ArrayList<MessageDesign>();
		for(ScreenItem item : screenItems) {
			messageDesigns.add(item.getMessageConfirm());
			messageDesigns.add(item.getMessageDesign());
		}
		for(ScreenArea area : screenAreas) {
			messageDesigns.add(area.getMessageDesign());
		}
		List<MessageDesign> messageDesignCodes = messageDesignService.getMessageByMessageCode(messageDesigns,projectId,languageId); 
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		List<ProblemList> problemLists = new ArrayList<ProblemList>();
		ProblemList problemList = null;
		// get languageId by countryCode and languageCode
		/*
		 * Language language = LocaleUtils.getDesaultLanguage(); // get LanguageId by languageCode and contryCode String languageCode = LocaleUtils.getDesaultLanguage().getLanguageCode(); String countryCode = LocaleUtils.getDesaultLanguage().getCountryCode(); LanguageDesign param = new LanguageDesign(languageCode, countryCode); LanguageDesign languageDesign = languageDesignService.findByLanguageDesign(param); language.setLanguageId(languageDesign.getLanguageId());
		 */
		Long userId = accountId;

		// check exist
		ScreenDesign screenDesignForUpdate = screendesignRepository.findById(screenDesign.getScreenId(), languageId, projectId);
		if (screenDesignForUpdate == null) {
			// screen is not exist
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0004)));
		} else if (screenDesignForUpdate.getUpdatedDate().compareTo(screenDesign.getUpdatedDate()) != 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0048));
		}

		/*
		 * // check exist home page if(screenDesign.getEnableHomePage()==true){ int countHomePage = screendesignRepository.countEnableHomePageByProjectId(screenDesign.getScreenCode(),workingProjectId); if(countHomePage > 0){ throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0075, MessageUtils.getMessage(CommonMessageConst.SC_TQP_0006))); } }
		 */

		// check screen name and screen code duplicated
		Map<String, Object> sqlParamCode = new HashMap<String, Object>();
		sqlParamCode.put("screenId", screenDesign.getScreenId());
		sqlParamCode.put("screenCode", screenDesign.getScreenCode());
		sqlParamCode.put("screenName", screenDesign.getMessageDesign().getMessageString());
		sqlParamCode.put("screenUrlCode", screenDesign.getScreenUrlCode());
		// sqlParamCode.put("countryCode",
		// LocaleUtils.getDesaultLanguage().getCountryCode());
		// sqlParamCode.put("languageCode",
		// LocaleUtils.getDesaultLanguage().getLanguageCode());
		sqlParamCode.put("projectId", screenDesign.getProjectId());
		sqlParamCode.put("languageId", languageId);

		Long totalCount = screendesignRepository.countNameCodeByProjectId(sqlParamCode);
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005)));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007)));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			String[] arrParam = { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005), MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007) };
			throw new BusinessException(ResultMessages.error().add(ScreenDesignMessageConst.ERR_SCREENDESIGN_0175, arrParam));
		}

		screenDesign.setUpdatedBy(accountId);
		screenDesign.setSysDatetime(currentTime);
		// update screen design
		if (screenDesign.getEnableHomePage() != null && screenDesign.getEnableHomePage() == true) {
			screendesignRepository.updateAllScreenIsDisableHomePageByProjectId(screenDesign.getProjectId());
		}
		Long modifyResult = screendesignRepository.screenDesignUpdate(screenDesign);
		if (modifyResult <= 0) {
			// concurrency
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0072, MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0004)));
		}

		// get list parameter from screen id
		ScreenParameter[] arrScreenParameterBeforeDelete = screendesignRepository.getScreenParameterByScreenId(screenDesign.getScreenId());

		// Sreen parameters have already used in other screen
		List<ScreenDesign> screenDesignFrom = getAllScreenActionChangeParameter(screenDesign.getScreenId(), languageId);

		// get module
		Module module = moduleService.validateModule(screenDesign.getModuleId(), accountId, projectId);
		// delete element
		screendesignRepository.deleteItemByScreenId(screenDesign.getScreenId(), projectId);
		// modify message design

		LanguageDesign lang = languageDesignService.getLanguageDesignById(languageId, projectId);

		sqlParamCode.clear();
		sqlParamCode.put("messageCode", screenDesign.getMessageDesign().getMessageCode());
		sqlParamCode.put("countryCode", lang.getCountryCode());
		sqlParamCode.put("languageCode", lang.getLanguageCode());
		sqlParamCode.put("messageString", screenDesign.getMessageDesign().getMessageString());
		sqlParamCode.put("updatedBy", accountId);
		sqlParamCode.put("updatedDate", currentTime);
		sqlParamCode.put("projectId", screenDesign.getProjectId());
		sqlParamCode.put("languageId", languageId);

		int resultModifyScreenName = messageDesignRepository.modifyByMessageCode(sqlParamCode);
		if (resultModifyScreenName <= 0) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0072, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0012)));
		}

		// add item to area
		addItemToArea(screenAreas, screenGroupItems, screenItems);

		// add group to area
		addGroupArea(screenAreas, screenGroupItems);

		// add item to group
		addItemToGroup(screenAreas);

		// add area to from
		for (int i = 0; i < screenForms.length; i++) {
			screenForms[i].setScreenId(screenDesign.getScreenId());
			screenForms[i].setAreas(new ArrayList<ScreenArea>());

			for (int j = 0; j < screenAreas.length; j++) {
				if (screenAreas[j].getScreenFormId() == i) {
					screenForms[i].getAreas().add(screenAreas[j]);
				}
			}
		}

		// insert screen parameter
		if (screenDesign.getArrScreenParameter() != null) {
			for (int parameterIndex = 0; parameterIndex < screenDesign.getArrScreenParameter().length; parameterIndex++) {
				if (screenDesign.getArrScreenParameter()[parameterIndex] != null) {
					screenDesign.getArrScreenParameter()[parameterIndex].setScreenId(screenDesign.getScreenId());
					screendesignRepository.screenParameterInsert(screenDesign.getArrScreenParameter()[parameterIndex]);
				}
			}
		}

		// delete problem
		problemListRepository.deleteByTargetId(DbDomainConst.ResourceType.SCREEN_DESIGN, screenDesign.getScreenId());

		// Check Problem list
		if (screenDesign.getDesignMode().equals(ScreenDesignConst.DesignMode.DESIGN)) {
			/* SonLD - updated 21/9/2015 - Insert problem list */
			if (screenDesign.getArrScreenParameter() != null) {

				if (screenDesignFrom.size() != 0) {
					// Set to Problem list
					List<ScreenParameter> listScreenParameterInsert = new ArrayList<ScreenParameter>();
					List<ScreenParameter> listScreenParameterDelete = new ArrayList<ScreenParameter>();
					List<ScreenParameter> listScreenParameterUpdate = new ArrayList<ScreenParameter>();

					for (ScreenParameter screenParameterNew : screenDesign.getArrScreenParameter()) {
						if (screenParameterNew != null) {
							int countCode = 0;
							for (ScreenParameter screenParameterOld : arrScreenParameterBeforeDelete) {
								if (screenParameterOld != null) {
									if (screenParameterOld.getScreenParamCode().equals(screenParameterNew.getScreenParamCode())) {
										int countType = 0;
										if (screenParameterOld.getDataType().equals(screenParameterNew.getDataType())) {
											countType++;
										}
										if (countType == 0) {
											// get list parameter update Data Type
											listScreenParameterUpdate.add(screenParameterNew);
										}
										countCode++;
									}
								}
							}
							if (countCode == 0) {
								listScreenParameterInsert.add(screenParameterNew);
							}
						}
					}

					for (ScreenParameter screenParameterOld : arrScreenParameterBeforeDelete) {
						if (screenParameterOld != null) {
							int countCode = 0;
							for (ScreenParameter screenParameterNew : screenDesign.getArrScreenParameter()) {
								if (screenParameterNew != null) {
									if (screenParameterNew.getScreenParamCode().equals(screenParameterOld.getScreenParamCode())) {
										countCode++;
									}
								}
							}
							if (countCode == 0) {
								listScreenParameterDelete.add(screenParameterOld);
							}
						}
					}

					for (ScreenDesign screenFromItem : screenDesignFrom) {
						// insert problem list
						if (listScreenParameterInsert.size() > 0) {
							for (ScreenParameter scp : listScreenParameterInsert) {
								problemList = new ProblemList();
								problemList.setProblemName(this.getNameProblem(DbDomainConst.ParameterType.INSERT, scp, screenDesign.getMessageDesign().getMessageString(), screenFromItem.getMessageDesign().getMessageString()));
								problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_DESIGN);
								problemList.setResourceId(screenFromItem.getScreenId());
								problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
								problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
								problemList.setModuleId(screenDesign.getModuleId());
								problemList.setUrlId(DbDomainConst.ResourceURL.URL_SCREEN_DESIGN);
								problemList.setFromResourceId(screenDesign.getScreenId());
								problemList.setFromResourceType(DbDomainConst.FromResourceType.SCREEN_DESIGN);

								problemList.setProjectId(projectId);
								problemList.setCreatedBy(userId);
								problemList.setCreatedDate(FunctionCommon.getCurrentTime());
								problemLists.add(problemList);
							}
						}
						if (listScreenParameterDelete.size() > 0) {
							for (ScreenParameter scp : listScreenParameterDelete) {
								problemList = new ProblemList();
								problemList.setProblemName(this.getNameProblem(DbDomainConst.ParameterType.DELETE, scp, screenDesign.getMessageDesign().getMessageString(), screenFromItem.getMessageDesign().getMessageString()));
								problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_DESIGN);
								problemList.setResourceId(screenFromItem.getScreenId());
								problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
								problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
								problemList.setModuleId(screenDesign.getModuleId());
								problemList.setUrlId(DbDomainConst.ResourceURL.URL_SCREEN_DESIGN);
								problemList.setFromResourceId(screenDesign.getScreenId());
								problemList.setFromResourceType(DbDomainConst.FromResourceType.SCREEN_DESIGN);

								problemList.setProjectId(projectId);
								problemList.setCreatedBy(userId);
								problemList.setCreatedDate(FunctionCommon.getCurrentTime());
								problemLists.add(problemList);
							}
						}
						if (listScreenParameterUpdate.size() > 0) {
							for (ScreenParameter scp : listScreenParameterUpdate) {
								problemList = new ProblemList();
								problemList.setProblemName(this.getNameProblem(DbDomainConst.ParameterType.UPDATE, scp, screenDesign.getMessageDesign().getMessageString(), screenFromItem.getMessageDesign().getMessageString()));
								problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_DESIGN);
								problemList.setResourceId(screenFromItem.getScreenId());
								problemList.setProblemType(DbDomainConst.ProblemType.UNMATCHED);
								problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
								problemList.setModuleId(screenDesign.getModuleId());
								problemList.setUrlId(DbDomainConst.ResourceURL.URL_SCREEN_DESIGN);
								problemList.setFromResourceId(screenDesign.getScreenId());
								problemList.setFromResourceType(DbDomainConst.FromResourceType.SCREEN_DESIGN);

								problemList.setProjectId(projectId);
								problemList.setCreatedBy(userId);
								problemList.setCreatedDate(FunctionCommon.getCurrentTime());
								problemLists.add(problemList);
							}
						}
					}
				}
			}

			if (problemLists.size() > 0) {
				problemListRepository.multiRegisterProblem(problemLists);
			}
		}

		// set form to screen design
		screenDesign.setScreenForms(screenForms);
		List<ScreenArea> screenAreaInputs = new ArrayList<ScreenArea>();
		/*
		 * //Debug for (int i = 0; i < screenAreas.length; i++) { logger.info("area: " + i); if (screenAreas[i].getAreaType() == -1 || screenAreas[i].getAreaType() == 2 ||screenAreas[i].getAreaType() == 3) { if (screenAreas[i].getItems() != null) { for (int j = 0; j < screenAreas[i].getItems().size(); j++) { logger.info("    	: SeqNo" + screenAreas[i].getItems().get(j).getItemSeqNo()); } } } else { for (int j = 0; j < screenAreas[i].getGroups().size(); j++) { logger.info("          Group: " + j);
		 * 
		 * for (int k = 0; k < screenAreas[i].getGroups().get(j).getItems().size(); k++) { logger.info("    		Item: SeqNo" + screenAreas[i].getGroups().get(j).getItems().get(k).getItemSeqNo()); } } } } int debug; debug = 0;
		 */
		
		List<MessageDesign> messages = messageDesignRepository.getMessageDesignModuleByModuleId(screenDesign.getModuleId(), languageId);
		if (messages == null) {
			messages = new ArrayList<MessageDesign>();
		}
		
		// insert data
		for (int formIndex = 0; formIndex < screenDesign.getScreenForms().length; formIndex++) {
			screenDesign.getScreenForms()[formIndex].setScreenItems(new ArrayList<ScreenItem>());
			// insert form
			screendesignRepository.screenFormInsert(screenDesign.getScreenForms()[formIndex]);
			ScreenForm form = screenDesign.getScreenForms()[formIndex];

			for (int areaIndex = 0; areaIndex < form.getAreas().size(); areaIndex++) {
				// insert area
				screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).setScreenFormId(form.getScreenFormId());
				screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).setScreenId(screenDesign.getScreenId());
				for(MessageDesign mDesign : messageDesignCodes) {
					if(screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign() != null && mDesign.getMessageCode().equals(screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign().getMessageCode())) {
						screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign().setMessageLevel(mDesign.getMessageLevel());
					}
				}
				/**
				 * check and insert message to area
				 */
				if (screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign() != null && screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign().getMessageCode() != null && screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign().getMessageCode().length() > 0
						&& (DbDomainConst.MessageLevel.PROJECT.equals(screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign().getMessageLevel()) || DbDomainConst.MessageLevel.MODULE.equals(screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign().getMessageLevel())) ) {
					MessageDesign message = screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign();

					// set module message design
					message.setModuleId(module.getModuleId());
					message.setProjectId(module.getProjectId());
					// set module code
					message.setModuleCode(module.getModuleCode());

					MessageDesign messageCommon = getCommonMessage(messages, message);
					if (messageCommon != null) {
						message = messageCommon;
					} else {
						//message = messageDesignService.registerMessageDesign(message);
						messages.add(message);
					}

					screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).setMessageDesign(message);

				} else {
					if (screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign() != null && !screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign().getMessageString().isEmpty()) {
						screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign().setModuleCode(module.getModuleCode());
						screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign().setModuleId(module.getModuleId());
						screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign().setProjectId(projectId);
						screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign().setLanguageId(languageId);
						screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign().setAccountId(accountId);

						MessageDesign message = screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getMessageDesign();
						MessageDesign messageCommon = getCommonMessage(messages, message);
						if (messageCommon != null) {
							message = messageCommon;
						} else {
							message = messageDesignService.registerMessageDesign(message);
							messages.add(message);
						}
						screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).setMessageDesign(message);
					}
				}

				screendesignRepository.screenAreaInsert(screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex));
				if (screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex) != null && screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getScreenAreaSorts() != null && 
						screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getScreenAreaSorts().size() > 0) {
					screenAreaRepository.insertScreenAreaSortMapping(screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex).getScreenAreaSorts());
				}

				ScreenArea area = screenDesign.getScreenForms()[formIndex].getAreas().get(areaIndex);
				screenAreaInputs.add(area);

				if (area.getScreenAreaEvents() != null && area.getScreenAreaEvents().size() > 0) {
					// save event area
					for (int eventIndex = 0; eventIndex < area.getScreenAreaEvents().size(); eventIndex++) {
						area.getScreenAreaEvents().get(eventIndex).setScreenAreaId(area.getScreenAreaId());
					}
				}
				screendesignRepository.screenAreaEventInsert(area.getScreenAreaEvents());

				// insert hidden item
				if (area.getListHiddenItems() != null && area.getListHiddenItems().size() > 0) {
					for (ScreenItem screenHiddenItem : area.getListHiddenItems()) {
						screenHiddenItem.setScreenAreaId(area.getScreenAreaId());
						screenHiddenItem.setScreenId(screenDesign.getScreenId());

						screenHiddenItem.getMessageDesign().setModuleCode(module.getModuleCode());
						screenHiddenItem.getMessageDesign().setModuleId(module.getModuleId());
						screenHiddenItem.getMessageDesign().setProjectId(projectId);
						screenHiddenItem.getMessageDesign().setLanguageId(languageId);
						screenHiddenItem.getMessageDesign().setAccountId(accountId);

						MessageDesign message = screenHiddenItem.getMessageDesign();
						MessageDesign messageCommon = getCommonMessage(messages, message);
						if (messageCommon != null) {
							message = messageCommon;
						} else {
							message = messageDesignService.registerMessageDesign(message);
							messages.add(message);
						}

						screenHiddenItem.setMessageDesign(message);
					}

					int size = area.getListHiddenItems().size();
					// get list sequence
					Long sequence = screendesignRepository.screenItemGetSequences(size - 1);
					Long[] itemIds = new Long[size];

					for (int i = size - 1; i >= 0; i--) {
						itemIds[i] = sequence--;
					}

					// update sequence for item
					for (int i = 0; i < size; i++) {
						area.getListHiddenItems().get(i).setScreenItemId(itemIds[i]);
					}

					for (int i = 0; i < area.getListHiddenItems().size(); i++) {
						if (area.getListHiddenItems().get(i).getScreenAction() != null 
								&& area.getListHiddenItems().get(i).getScreenAction().getToScreenId() != null 
								&& area.getListHiddenItems().get(i).getScreenTransitionId() != null 
										&& area.getListHiddenItems().get(i).getScreenTransitionId().toString().equals("-1")) {
							Date date = new Date();
							SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddhhmmss"); 
							String transitionName = "Auto generate transition " + dt.format(date);
							String transitionCode = "AutoGenerateTransition" + dt.format(date);
							String fromScreen = screenDesign.getScreenId() + "";
							String toScreen = area.getListHiddenItems().get(i).getScreenAction().getToScreenId() + "";
							ScreenTransition st = new ScreenTransition(transitionName, transitionCode, fromScreen, toScreen, module.getModuleId(), 0);
							screendesignRepository.ScreenItemTransitionInsert(st);
							area.getListHiddenItems().get(i).setScreenTransitionId(Long.parseLong(st.getScreenTransitionId()));
						}
					}
					
					screendesignRepository.screenHiddenItemsInsert(area.getListHiddenItems());
					// screendesignRepository.updateScreenItemIdInBlogic(area.getListHiddenItems());

					screenDesign.getScreenForms()[formIndex].getScreenItems().addAll(area.getListHiddenItems());
				}

				// insert Item
				if (area.getAreaType() == -1 || area.getAreaType() == 2 || area.getAreaType() == 3) {
					// update id area for item
					if (area.getItems() != null) {
						for (int itemIndex = 0; itemIndex < area.getItems().size(); itemIndex++) {
							area.getItems().get(itemIndex).setItemGroupType(0);
							area.getItems().get(itemIndex).setScreenAreaId(area.getScreenAreaId());
							area.getItems().get(itemIndex).setScreenId(screenDesign.getScreenId());
							
							for(MessageDesign mdesign : messageDesignCodes) {
								if(area.getItems().get(itemIndex).getMessageDesign() != null && mdesign.getMessageCode().equals(area.getItems().get(itemIndex).getMessageDesign().getMessageCode())) {
									area.getItems().get(itemIndex).getMessageDesign().setMessageLevel(mdesign.getMessageLevel());
									if(area.getItems().get(itemIndex).getMessageConfirm() != null) {
										area.getItems().get(itemIndex).getMessageConfirm().setMessageLevel(mdesign.getMessageLevel());
									}
								}
							}
							
							if (area.getItems().get(itemIndex).getMessageDesign() != null && !area.getItems().get(itemIndex).getMessageDesign().getMessageCode().isEmpty()
									&& ( DbDomainConst.MessageLevel.PROJECT.equals(area.getItems().get(itemIndex).getMessageDesign().getMessageLevel()) || DbDomainConst.MessageLevel.MODULE.equals(area.getItems().get(itemIndex).getMessageDesign().getMessageLevel()))) {
								// add new message
								area.getItems().get(itemIndex).getMessageDesign().setModuleCode(module.getModuleCode());
								area.getItems().get(itemIndex).getMessageDesign().setModuleId(module.getModuleId());
								area.getItems().get(itemIndex).getMessageDesign().setProjectId(projectId);
								area.getItems().get(itemIndex).getMessageDesign().setLanguageId(languageId);
								area.getItems().get(itemIndex).getMessageDesign().setAccountId(accountId);

								MessageDesign message = area.getItems().get(itemIndex).getMessageDesign();
								MessageDesign messageCommon = getCommonMessage(messages, message);
								if (messageCommon != null) {
									message = messageCommon;
								} else {
									//message = messageDesignService.registerMessageDesign(message);
									messages.add(message);
								}
								area.getItems().get(itemIndex).setMessageDesign(message);
							} else {
								if (area.getItems().get(itemIndex).getMessageDesign() != null && area.getItems().get(itemIndex).getMessageDesign().getMessageString().isEmpty()) {
									area.getItems().get(itemIndex).setMessageDesign(null);
								} else {
									if (area.getItems().get(itemIndex).getMessageDesign() != null) {
										area.getItems().get(itemIndex).getMessageDesign().setModuleCode(module.getModuleCode());
										area.getItems().get(itemIndex).getMessageDesign().setModuleId(module.getModuleId());
										area.getItems().get(itemIndex).getMessageDesign().setProjectId(projectId);
										area.getItems().get(itemIndex).getMessageDesign().setLanguageId(languageId);
										area.getItems().get(itemIndex).getMessageDesign().setAccountId(accountId);

										MessageDesign message = area.getItems().get(itemIndex).getMessageDesign();
										MessageDesign messageCommon = getCommonMessage(messages, message);
										if (messageCommon != null) {
											message = messageCommon;
										} else {
											message = messageDesignService.registerMessageDesign(message);
											messages.add(message);
										}
										area.getItems().get(itemIndex).setMessageDesign(message);
									}
								}
							}

							// message confirm
							if (area.getItems().get(itemIndex).getMessageConfirm() != null && !area.getItems().get(itemIndex).getMessageConfirm().getMessageCode().isEmpty() && 
									( DbDomainConst.MessageLevel.PROJECT.equals(area.getItems().get(itemIndex).getMessageConfirm().getMessageLevel()) || DbDomainConst.MessageLevel.MODULE.equals(area.getItems().get(itemIndex).getMessageConfirm().getMessageLevel()))) {
								// add new message
								area.getItems().get(itemIndex).getMessageConfirm().setModuleCode(module.getModuleCode());
								area.getItems().get(itemIndex).getMessageConfirm().setModuleId(module.getModuleId());
								area.getItems().get(itemIndex).getMessageConfirm().setProjectId(projectId);
								area.getItems().get(itemIndex).getMessageConfirm().setLanguageId(languageId);
								area.getItems().get(itemIndex).getMessageConfirm().setAccountId(accountId);

								//MessageDesign message = area.getItems().get(itemIndex).getMessageConfirm();
								//if (StringUtils.isEmpty(message.getMessageCode())) {
									//message = messageDesignService.registerMessageDesign(message);
								//}

								//area.getItems().get(itemIndex).setMessageConfirm(message);
							} else {
								if (area.getItems().get(itemIndex).getMessageConfirm() != null 
										&& area.getItems().get(itemIndex).getMessageConfirm().getMessageString() != null
										&& area.getItems().get(itemIndex).getMessageConfirm().getMessageString().isEmpty()) {
									area.getItems().get(itemIndex).setMessageConfirm(null);
								} else {
									if (area.getItems().get(itemIndex).getMessageConfirm() != null) {
										area.getItems().get(itemIndex).getMessageConfirm().setModuleCode(module.getModuleCode());
										area.getItems().get(itemIndex).getMessageConfirm().setModuleId(module.getModuleId());
										area.getItems().get(itemIndex).getMessageConfirm().setProjectId(projectId);
										area.getItems().get(itemIndex).getMessageConfirm().setLanguageId(languageId);
										area.getItems().get(itemIndex).getMessageConfirm().setAccountId(accountId);
	
										MessageDesign message = area.getItems().get(itemIndex).getMessageConfirm();
										if (StringUtils.isEmpty(message.getMessageCode())) {
											message = messageDesignService.registerMessageDesign(message);
										}
	
										area.getItems().get(itemIndex).setMessageConfirm(message);
									}
								}
							}

							if (area.getItems().get(itemIndex).getScreenAction() != null) {
								area.getItems().get(itemIndex).getScreenAction().setFromScreenId(screenDesign.getScreenId());
								Long result = screenActionRepository.insertScreenActionWithParam(area.getItems().get(itemIndex).getScreenAction());
								area.getItems().get(itemIndex).setScreenActionId(area.getItems().get(itemIndex).getScreenAction().getScreenActionId());
							}
						}

						int size = area.getItems().size();
						// get list sequence
						Long sequence = screendesignRepository.screenItemGetSequences(size - 1);
						Long[] itemIds = new Long[size];

						for (int i = size - 1; i >= 0; i--) {
							itemIds[i] = sequence--;
						}

						// update sequence for item
						for (int i = 0; i < size; i++) {
							area.getItems().get(i).setScreenItemId(itemIds[i]);
						}

						for (int i = 0; i < area.getItems().size(); i++) {
							if (area.getItems().get(i).getScreenItemEvents() != null && area.getItems().get(i).getScreenItemEvents().size() > 0) {
								size = area.getItems().get(i).getScreenItemEvents().size();
								sequence = screendesignRepository.screenItemEventGetSequences(size - 1);
								itemIds = new Long[size];

								for (int k = size - 1; k >= 0; k--) {
									itemIds[k] = sequence--;
								}

								for (int j = 0; j < size; j++) {
									area.getItems().get(i).getScreenItemEvents().get(j).setScreenItemEventId(itemIds[j]);
								}
							}
						}
						//insert screen transition
						for (int i = 0; i < area.getItems().size(); i++) {
							if (area.getItems().get(i).getScreenAction() != null 
									&& area.getItems().get(i).getScreenAction().getToScreenId() != null 
									&& area.getItems().get(i).getScreenTransitionId() != null 
									&& area.getItems().get(i).getScreenTransitionId().toString().equals("-1")) {
								Date date = new Date();
								SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddhhmmss"); 
								String transitionName = "Auto generate transition " + dt.format(date);
								String transitionCode = "AutoGenerateTransition" + dt.format(date);
								String fromScreen = screenDesign.getScreenId() + "";
								String toScreen = area.getItems().get(i).getScreenAction().getToScreenId() + "";
								ScreenTransition st = new ScreenTransition(transitionName, transitionCode, fromScreen, toScreen, module.getModuleId(), 0);
								screendesignRepository.ScreenItemTransitionInsert(st);
								area.getItems().get(i).setScreenTransitionId(Long.parseLong(st.getScreenTransitionId()));
							}
						}
						screendesignRepository.screenItemsInsert(area.getItems());
						// screendesignRepository.updateScreenItemIdInBlogic(area.getItems());
						for (int i = 0; i < area.getItems().size(); i++) {
							area.getItems().get(i).setScreenArea(area);
						}
						screenDesign.getScreenForms()[formIndex].getScreenItems().addAll(area.getItems());
					}
				} else {
					if (area.getGroups() != null && area.getGroups().size() > 0) {
						for (int groupIndex = 0; groupIndex < area.getGroups().size(); groupIndex++) {
							area.getGroups().get(groupIndex).setScreenAreaId(area.getScreenAreaId());
							screendesignRepository.screenGroupItemInsert(area.getGroups().get(groupIndex));
							ScreenGroupItem group = area.getGroups().get(groupIndex);

							// update area id & group id
							for (int itemIndex = 0; itemIndex < group.getItems().size(); itemIndex++) {
								group.getItems().get(itemIndex).setItemGroupType(1);
								group.getItems().get(itemIndex).setGroupItemId(group.getGroupItemId());
								group.getItems().get(itemIndex).setScreenAreaId(area.getScreenAreaId());
								group.getItems().get(itemIndex).setScreenId(screenDesign.getScreenId());
								for(MessageDesign mdesign : messageDesignCodes) {
									if(group.getItems().get(itemIndex).getMessageDesign() != null && mdesign.getMessageCode().equals(group.getItems().get(itemIndex).getMessageDesign().getMessageCode())) {
										group.getItems().get(itemIndex).getMessageDesign().setMessageLevel(mdesign.getMessageLevel());
										if( group.getItems().get(itemIndex).getMessageConfirm() != null) {
											group.getItems().get(itemIndex).getMessageConfirm().setMessageLevel(mdesign.getMessageLevel());
										}
									}
								}
								
								if (group.getItems().get(itemIndex).getMessageDesign() != null && !group.getItems().get(itemIndex).getMessageDesign().getMessageCode().isEmpty() 
										&& (DbDomainConst.MessageLevel.PROJECT.equals(group.getItems().get(itemIndex).getMessageDesign().getMessageLevel()) || DbDomainConst.MessageLevel.MODULE.equals(group.getItems().get(itemIndex).getMessageDesign().getMessageLevel()))) {
									// add new message
									group.getItems().get(itemIndex).getMessageDesign().setModuleCode(module.getModuleCode());
									group.getItems().get(itemIndex).getMessageDesign().setModuleId(module.getModuleId());
									group.getItems().get(itemIndex).getMessageDesign().setProjectId(module.getProjectId());
									group.getItems().get(itemIndex).getMessageDesign().setLanguageId(languageId);
									group.getItems().get(itemIndex).getMessageDesign().setAccountId(accountId);

									MessageDesign message = group.getItems().get(itemIndex).getMessageDesign();
									MessageDesign messageCommon = getCommonMessage(messages, message);
									if (messageCommon != null) {
										message = messageCommon;
									} else {
										//message = messageDesignService.registerMessageDesign(message);
										messages.add(message);
									}

									group.getItems().get(itemIndex).setMessageDesign(message);
								} else {
									if (group.getItems().get(itemIndex).getMessageDesign() != null && group.getItems().get(itemIndex).getMessageDesign().getMessageString().isEmpty()) {
										group.getItems().get(itemIndex).setMessageDesign(null);
									} else {
										if (group.getItems().get(itemIndex).getMessageDesign() != null) {
											group.getItems().get(itemIndex).getMessageDesign().setModuleCode(module.getModuleCode());
											group.getItems().get(itemIndex).getMessageDesign().setModuleId(module.getModuleId());
											group.getItems().get(itemIndex).getMessageDesign().setProjectId(projectId);
											group.getItems().get(itemIndex).getMessageDesign().setLanguageId(languageId);
											group.getItems().get(itemIndex).getMessageDesign().setAccountId(accountId);
	
											MessageDesign message = group.getItems().get(itemIndex).getMessageDesign();
											MessageDesign messageCommon = getCommonMessage(messages, message);
											if (messageCommon != null) {
												message = messageCommon;
											} else {
												message = messageDesignService.registerMessageDesign(message);
												messages.add(message);
											}
	
											group.getItems().get(itemIndex).setMessageDesign(message);
										}
									}
								}

								// message confirm
								if (group.getItems().get(itemIndex).getMessageConfirm() != null && group.getItems().get(itemIndex).getMessageConfirm().getMessageCode() != null 
										&& !group.getItems().get(itemIndex).getMessageConfirm().getMessageCode().isEmpty() 
										&& ( DbDomainConst.MessageLevel.PROJECT.equals(group.getItems().get(itemIndex).getMessageConfirm().getMessageLevel()) || DbDomainConst.MessageLevel.MODULE.equals(group.getItems().get(itemIndex).getMessageConfirm().getMessageLevel()))) {
									// add new message
									group.getItems().get(itemIndex).getMessageConfirm().setModuleCode(module.getModuleCode());
									group.getItems().get(itemIndex).getMessageConfirm().setModuleId(module.getModuleId());
									group.getItems().get(itemIndex).getMessageConfirm().setProjectId(projectId);
									group.getItems().get(itemIndex).getMessageConfirm().setLanguageId(languageId);
									group.getItems().get(itemIndex).getMessageConfirm().setAccountId(accountId);

									MessageDesign message = group.getItems().get(itemIndex).getMessageConfirm();
									if (StringUtils.isEmpty(message.getMessageCode())) {
										//message = messageDesignService.registerMessageDesign(message);
									}

									group.getItems().get(itemIndex).setMessageConfirm(message);
								} else {
									if (group.getItems().get(itemIndex).getMessageConfirm() != null &&
											group.getItems().get(itemIndex).getMessageConfirm().getMessageString() != null 
											&& group.getItems().get(itemIndex).getMessageConfirm().getMessageString().isEmpty()) {
										group.getItems().get(itemIndex).setMessageConfirm(null);
									} else {
										if (group.getItems().get(itemIndex).getMessageConfirm() != null) {
											group.getItems().get(itemIndex).getMessageConfirm().setModuleCode(module.getModuleCode());
											group.getItems().get(itemIndex).getMessageConfirm().setModuleId(module.getModuleId());
											group.getItems().get(itemIndex).getMessageConfirm().setProjectId(projectId);
											group.getItems().get(itemIndex).getMessageConfirm().setLanguageId(languageId);
											group.getItems().get(itemIndex).getMessageConfirm().setAccountId(accountId);
	
											MessageDesign message = group.getItems().get(itemIndex).getMessageConfirm();
											if (StringUtils.isEmpty(message.getMessageCode())) {
												message = messageDesignService.registerMessageDesign(message);
											}
	
											group.getItems().get(itemIndex).setMessageConfirm(message);
										}
									}
								}

								if (group.getItemGroupType().equals(1)) {
									group.getItems().get(itemIndex).setItemGroupType(1);
								} else {
									group.getItems().get(itemIndex).setItemGroupType(0);
								}

								if (group.getItems().get(itemIndex).getScreenAction() != null) {
									group.getItems().get(itemIndex).getScreenAction().setFromScreenId(screenDesign.getScreenId());
									Long result = screenActionRepository.insertScreenActionWithParam(group.getItems().get(itemIndex).getScreenAction());
									group.getItems().get(itemIndex).setScreenActionId(group.getItems().get(itemIndex).getScreenAction().getScreenActionId());
								}
							}

							int size = group.getItems().size();
							// get list sequence
							Long sequence = screendesignRepository.screenItemGetSequences(size - 1);
							Long[] itemIds = new Long[size];

							for (int i = size - 1; i >= 0; i--) {
								itemIds[i] = sequence--;
							}

							// update sequence for item
							for (int i = 0; i < size; i++) {
								group.getItems().get(i).setScreenItemId(itemIds[i]);
							}
							for (int i = 0; i < group.getItems().size(); i++) {
								if (group.getItems().get(i).getScreenItemEvents() != null && group.getItems().get(i).getScreenItemEvents().size() > 0) {
									size = group.getItems().get(i).getScreenItemEvents().size();
									sequence = screendesignRepository.screenItemEventGetSequences(size - 1);
									itemIds = new Long[size];

									for (int k = size - 1; k >= 0; k--) {
										itemIds[k] = sequence--;
									}

									for (int j = 0; j < size; j++) {
										group.getItems().get(i).getScreenItemEvents().get(j).setScreenItemEventId(itemIds[j]);
									}
								}
							}
							
							//insert screen transition
							for (int i = 0; i < group.getItems().size(); i++) {
								if (group.getItems().get(i).getScreenAction() != null 
										&& group.getItems().get(i).getScreenAction().getToScreenId() != null 
										&& group.getItems().get(i).getScreenTransitionId() != null 
												&& group.getItems().get(i).getScreenTransitionId().toString().equals("-1")) {
									Date date = new Date();
									SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddhhmmss"); 
									String transitionName = "Auto generate transition " + dt.format(date);
									String transitionCode = "AutoGenerateTransition" + dt.format(date);
									String fromScreen = screenDesign.getScreenId() + "";
									String toScreen = group.getItems().get(i).getScreenAction().getToScreenId() + "";
									ScreenTransition st = new ScreenTransition(transitionName, transitionCode, fromScreen, toScreen, module.getModuleId(), 0);
									screendesignRepository.ScreenItemTransitionInsert(st);
									group.getItems().get(i).setScreenTransitionId(Long.parseLong(st.getScreenTransitionId()));
								}
							}
							screendesignRepository.screenItemsInsert(group.getItems());
							// screendesignRepository.updateScreenItemIdInBlogic(group.getItems());
							for (int i = 0; i < area.getItems().size(); i++) {
								area.getItems().get(i).setScreenArea(area);
							}
							screenDesign.getScreenForms()[formIndex].getScreenItems().addAll(group.getItems());
						}
					}
				}
			}

		}

		// get blogic
		List<BusinessDesign> businessDesigns = businessDesignRepository.findBusinessLogicsByScreenId(screenDesign.getScreenId());

		// get list input bean initial and process
		List<BusinessDesign> businessDesignInitial = new ArrayList<BusinessDesign>();
		List<BusinessDesign> businessDesignProcess = new ArrayList<BusinessDesign>();
		for(BusinessDesign business : businessDesigns) {
			if (BusinessDesignConst.RETURN_TYPE_INITIALSCREEN.equals(business.getReturnType())) {
				businessDesignInitial.add(business);
			} else {
				businessDesignProcess.add(business);
			}
		}
		List<InputBean> inputBeansInitialScreen = businessDesignRepository.findInputBeanByBlogicIds(languageId, projectId, businessDesignInitial);
		List<InputBean> inputBeans = businessDesignRepository.findInputBeanByBlogicIds(languageId, projectId, businessDesignProcess);
		List<InputBean> lstParentInputBeans = new ArrayList<InputBean>();
		Map<Long, Integer> maxSeqItemNoOfInput = new HashMap<Long, Integer>();
		if (FunctionCommon.isNotEmpty(businessDesigns) && FunctionCommon.isNotEmpty(inputBeans)) {
			for (BusinessDesign bd : businessDesigns) {
				Integer max = 0;
				for (InputBean in : inputBeans) {
					if (bd.getBusinessLogicId().equals(in.getBusinessLogicId())) {
						if (in.getItemSequenceNo() != null && in.getItemSequenceNo() > max) {
							max = in.getItemSequenceNo();
						}
					}
				}
				maxSeqItemNoOfInput.put(bd.getBusinessLogicId(), max);
			}
		}

		for (int formIndex = 0; formIndex < screenDesign.getScreenForms().length; formIndex++) {
			// update blogic input
			screenDesign.getScreenForms()[formIndex].setInputBeans(new ArrayList<InputBean>());

			for (BusinessDesign blogic : businessDesignProcess) {
				if (screenDesign.getScreenForms()[formIndex].getScreenFormIdStore() != null && (screenDesign.getScreenForms()[formIndex].getScreenFormIdStore().equals(blogic.getScreenFormId()) || blogic.getReturnType() == 1)) {
					// check and update input bean into blogic
					for (InputBean input : inputBeans) {
						if (input.getBusinessLogicId().equals(blogic.getBusinessLogicId())) {
							screenDesign.getScreenForms()[formIndex].getInputBeans().add(input);
						}
						if (BusinessDesignConst.DataType.ENTITY.equals(input.getDataType()) || BusinessDesignConst.DataType.OBJECT.equals(input.getDataType())) {
							lstParentInputBeans.add(input);
						}
					}
					businessDesignRepository.updateFormForBlogic(blogic.getBusinessLogicId(), screenDesign.getScreenForms()[formIndex].getScreenFormId());
				}
			}
		}

		List<InputBean> inputBeanUpdate = new ArrayList<InputBean>();
		List<InputBean> inputBeanInsert = new ArrayList<InputBean>();
		List<InputBean> inputBeanInsertForParametters = new ArrayList<InputBean>();
		List<InputBean> inputBeanMappingDelete = new ArrayList<InputBean>();

		/*
		 * update input bean step 1: get list input bean update(check with code, data) and insert step 2: insert input bean new step 3: update input
		 */
		// List<ScreenArea> lstScreenArea = screenAreaRepository.getLstScreenAreaByScreenId(screenDesign.getScreenId(), workingLanguageId);
		// Map<Long, ScreenArea> mapScreenArea = new HashMap<Long, ScreenArea>();
		// if(FunctionCommon.isNotEmpty(lstScreenArea)) {
		// for(ScreenArea area : lstScreenArea) {
		// mapScreenArea.put(area.getScreenAreaId(), area);
		// }
		// }
		Integer tempInputBeanId = 0, tempParentObjectId = 0, tempParentMultilepartFileId = 0;
		Map<String, Long> mapInputBeanId = new HashMap<String, Long>();
		ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionRepository.findExtObjDefIsMultipartFileByProjectId(projectId);
		List<ExternalObjectAttribute> lstExternalObjectAttribute = new ArrayList<ExternalObjectAttribute>();
		if (externalObjectDefinition != null) {
			lstExternalObjectAttribute = externalObjectAttributeRepository.findExternalObjectAttributeByExternalObjectDefinitionId(externalObjectDefinition.getExternalObjectDefinitionId());
		}
		Set<ScreenArea> areaUpdateMappingObjectType = new HashSet<ScreenArea>();
		Set<ScreenArea> areaUpdateMappingObjectTypeNull = new HashSet<ScreenArea>();
		Map<Long, List<ScreenItem>> mapAreaAndItem = new HashMap<Long, List<ScreenItem>>();
		List<InputBean> lstAllInputBeans = new ArrayList<InputBean>();
		List<ScreenItem> lstAllScreenItem = new ArrayList<ScreenItem>();
		List<ScreenForm> screenFormMappingBlogic = new ArrayList<ScreenForm>();
		for (ScreenForm form : screenDesign.getScreenForms()) {
			for (BusinessDesign blogic : businessDesigns) {
				if (form.getScreenFormIdStore() != null && form.getScreenFormIdStore().equals(blogic.getScreenFormId())) {
					if (!screenFormMappingBlogic.contains(form)) {
						screenFormMappingBlogic.add(form);
					}
				}
			}
		}
		
		for (ScreenForm form : screenFormMappingBlogic) {
			lstAllScreenItem.addAll(form.getScreenItems());
			for (ScreenArea area : screenAreaInputs) {
				if (form.getScreenFormId().equals(area.getScreenFormId())) {
					List<ScreenItem> lstItemOfThisArea = new ArrayList<ScreenItem>();
					for (ScreenItem item : form.getScreenItems()) {
						if (area.getScreenAreaId().equals(item.getScreenAreaId())) {
							if (item.getScreenArea() == null) {
								item.setScreenArea(area);
							}
							lstItemOfThisArea.add(item);
						}
					}
					mapAreaAndItem.put(area.getScreenAreaId(), lstItemOfThisArea);
				}
				if (FunctionCommon.isNotEmpty(inputBeans)) {
					for (InputBean input : inputBeans) {
						if (input.getScreenItemId() == null) {
							if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_INPUT_BEAN.equals(area.getObjectMappingType())) {
								if (area.getObjectMappingId() != null && area.getObjectMappingId().toString().equals(input.getInputBeanId())) {
									if (!lstAllInputBeans.contains(input)) {
										lstAllInputBeans.add(input);
									}
									// Update code of input bean mapping to screen area
									String areaName = area.getMessageDesign() != null ? area.getMessageDesign().getMessageCode() : "";
									InputBean inputBeanMapping = new InputBean();
									inputBeanMapping.setArrayFlg(input.getArrayFlg());
									inputBeanMapping.setInputBeanId(input.getInputBeanId());
									inputBeanMapping.setInputBeanCode(area.getAreaCode());
									inputBeanMapping.setInputBeanName(areaName);
									inputBeanMapping.setDataType(input.getDataType());
									inputBeanMapping.setScreenItemId(input.getScreenItemId());
									inputBeanMapping.setBusinessLogicId(input.getBusinessLogicId());
									inputBeanUpdate.add(inputBeanMapping);
								}
							} else if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_TABLE_DESIGN.equals(area.getObjectMappingType())) {
								if (area.getObjectMappingId() != null && area.getObjectMappingId().equals(input.getTblDesignId())) {
									if (!lstAllInputBeans.contains(input)) {
										lstAllInputBeans.add(input);
									}
								}
							}
						} else {
							if (!lstAllInputBeans.contains(input)) {
								lstAllInputBeans.add(input);
							}
						}
					}
				}
			}
		}
		
		Map<Long, List<InputBean>> mapFormAndInputBean = new HashMap<Long, List<InputBean>>();
		for (ScreenForm form : screenFormMappingBlogic) {
			List<InputBean> lstInputbeanInsertOfThisForm = new ArrayList<InputBean>();
			Set<String> hashSetAreaCode = new HashSet<String>();
			Map<String, InputBean> mapAreaCode = new HashMap<String, InputBean>();

			for (ScreenArea area : screenAreaInputs) {
				if (form.getScreenFormId().equals(area.getScreenFormId())) {
					// flag check is have object
					boolean flagHaveObject = false;
					for (InputBean parentInput : lstParentInputBeans) {
						if (area.getObjectMappingType() != null) {
							if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_INPUT_BEAN.equals(area.getObjectMappingType())) {
								if (area.getObjectMappingId() != null && area.getObjectMappingId().toString().equals(parentInput.getInputBeanId())) {
									flagHaveObject = true;
								}
							} else if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_TABLE_DESIGN.equals(area.getObjectMappingType())) {
								if (area.getObjectMappingId() != null && area.getObjectMappingId().equals(parentInput.getTblDesignId()) && area.getAreaCode().equals(parentInput.getInputBeanCode())) {
									flagHaveObject = true;
								}
							}
						} else {
							if (parentInput.getInputBeanCode().equals(area.getAreaCode())) {
								flagHaveObject = true;
							}
						}
					}
					if (!flagHaveObject) {
						InputBean parentInputBean = null;
						List<ScreenItem> lstScreenItemOfThisArea = mapAreaAndItem.get(area.getScreenAreaId()) != null ? mapAreaAndItem.get(area.getScreenAreaId()) : new ArrayList<ScreenItem>();
						for (ScreenItem item : lstScreenItemOfThisArea) {
							if (item.getLogicalDataType() == null || DbDomainConst.LogicDataType.LABEL.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.BUTTON.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.LINK.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.LINK_DYNAMIC.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.CUSTOM_ITEM.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.CUSTOM_SECTION.equals(item.getLogicalDataType()) || (DbDomainConst.LogicDataType.LABEL_DYNAMIC.equals(item.getLogicalDataType()) && !ScreenDesignConst.ScreenItemConst.ITEM_TYPE_HIDDEN.equals(item.getItemType()))) {
								continue;
							}
							ScreenArea screenArea = item.getScreenArea();
							if (screenArea != null) {
								if (parentInputBean == null) {
									// Adding a object parent input bean
									if (!hashSetAreaCode.contains(screenArea.getAreaCode())) {
										parentInputBean = new InputBean();
										parentInputBean.setInputBeanId(tempInputBeanId.toString());
										parentInputBean.setInputBeanCode(screenArea.getAreaCode());
										parentInputBean.setInputBeanName(screenArea.getMessageDesign() == null ? "" : StringUtils.isNotEmpty(screenArea.getMessageDesign().getMessageCode()) ? screenArea.getMessageDesign().getMessageCode() : "");
										parentInputBean.setInputBeanType(BusinessDesignConst.InputBeanType.DEFAULT);
										parentInputBean.setDataType(BusinessDesignConst.DataType.OBJECT);
										if (ScreenDesignConst.AreaType.LIST.equals(screenArea.getAreaType())) {
											parentInputBean.setArrayFlg(Boolean.TRUE);
										} else {
											parentInputBean.setArrayFlg(Boolean.FALSE);
										}
										parentInputBean.setObjectFlg(Boolean.TRUE);
										parentInputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
										// add to list
										inputBeanInsert.add(parentInputBean);
										lstInputbeanInsertOfThisForm.add(parentInputBean);
										hashSetAreaCode.add(screenArea.getAreaCode());
										mapAreaCode.put(screenArea.getAreaCode(), parentInputBean);
										tempParentObjectId = tempInputBeanId;
										tempInputBeanId++;
									} else {
										parentInputBean = mapAreaCode.get(screenArea.getAreaCode());
										if (parentInputBean != null) {
											tempParentObjectId = Integer.parseInt(parentInputBean.getInputBeanId());
										}
									}

									// set object_mapping_id of screen area
									screenArea.setObjectMappingId(Long.parseLong(parentInputBean.getInputBeanId()));
									areaUpdateMappingObjectType.add(screenArea);
								}

								// Adding by HungHX - Special case : if add screen item is File Upload .
								if (DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getLogicalDataType()) && externalObjectDefinition != null) {
									if (CollectionUtils.isNotEmpty(lstExternalObjectAttribute)) {
										// Assign object parent form object definition
										InputBean inputBean = new InputBean();
										inputBean.setInputBeanId(tempInputBeanId.toString());
										inputBean.setInputBeanCode(item.getItemCode());
										inputBean.setInputBeanName(ScreenDesignConst.MULTIPART_FILE);
										inputBean.setScreenItemId(item.getScreenItemId());
										inputBean.setScreenItem(item);
										inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.DEFAULT);
										inputBean.setDataType(BusinessDesignConst.DataType.EXTERNAL_OBJECT);
										inputBean.setArrayFlg(Boolean.FALSE);
										inputBean.setParentInputBeanId(tempParentObjectId.toString());
										inputBean.setObjectType(DbDomainConst.ObjectType.EXTERNAL_OBJECT);
										inputBean.setObjectId(externalObjectDefinition.getExternalObjectDefinitionId());
										inputBean.setObjectFlg(Boolean.TRUE);
										inputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
										inputBean.setFlagUsingTempId(true);
										// add to list
										inputBeanInsert.add(inputBean);
										lstInputbeanInsertOfThisForm.add(inputBean);
										tempParentMultilepartFileId = tempInputBeanId;
										tempInputBeanId++;

										// Assign object parent form object attribute
										for (ExternalObjectAttribute extObjAttrIter : lstExternalObjectAttribute) {
											inputBean = new InputBean();
											inputBean.setInputBeanId(tempInputBeanId.toString());
											inputBean.setInputBeanCode(extObjAttrIter.getExternalObjectAttributeCode());
											inputBean.setInputBeanName(extObjAttrIter.getExternalObjectAttributeName());
											inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.ADDED_DEFAULT);
											inputBean.setDataType(extObjAttrIter.getDataType());
											inputBean.setArrayFlg(extObjAttrIter.getArrayFlg());
											inputBean.setParentInputBeanId(tempParentMultilepartFileId.toString());
											inputBean.setObjectFlg(Boolean.FALSE);
											inputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
											inputBean.setFlagUsingTempId(true);
											// add to list
											inputBeanInsert.add(inputBean);
											lstInputbeanInsertOfThisForm.add(inputBean);
											tempInputBeanId++;
										}
									}
									// End adding: Normal case
								} else {
									if (!DbDomainConst.ScreenPatternType.SEARCH.equals(screenDesign.getScreenPatternType())) {
										InputBean inputBean = new InputBean();
										inputBean.setInputBeanCode(item.getItemCode());
										if (item.getMessageDesign() != null) {
											inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
										} else {
											inputBean.setInputBeanName("");
										}
										if (item.getPhysicalDataType() != null) {
											inputBean.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
										}
										inputBean.setScreenItemId(item.getScreenItemId());
										inputBean.setScreenItem(item);
										inputBean.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
										inputBean.setParentInputBeanId(tempParentObjectId.toString());
										inputBean.setFlagUsingTempId(true);
										if (DbDomainConst.LogicDataType.CHECKBOX.equals(item.getLogicalDataType()) && inputBean.getDataType() != null && inputBean.getDataType().intValue() != DbDomainConst.BaseType.BOOLEAN_BASETYPE) {
											inputBean.setArrayFlg(Boolean.TRUE);
										}
										inputBeanInsert.add(inputBean);
										lstInputbeanInsertOfThisForm.add(inputBean);
										// Add autocomplete label input bean
										if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
											InputBean inputBeanLabel = new InputBean();
											inputBeanLabel.setInputBeanCode(item.getItemCode() + AUTOCOMPLETE);
											if (item.getMessageDesign() != null) {
												inputBeanLabel.setInputBeanName(item.getMessageDesign().getMessageCode());
											} else {
												inputBeanLabel.setInputBeanName("");
											}
											
											inputBeanLabel.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
											inputBeanLabel.setScreenItemId(item.getScreenItemId());
											inputBeanLabel.setScreenItem(item);
											inputBeanLabel.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
											inputBeanLabel.setParentInputBeanId(tempParentObjectId.toString());
											inputBeanLabel.setFlagUsingTempId(true);
											inputBeanInsert.add(inputBeanLabel);
											lstInputbeanInsertOfThisForm.add(inputBeanLabel);
										}
									} else {
										if ((item.getDisplayFromTo() == null || ScreenDesignConst.FromTo.FROM_TO.equals(item.getDisplayFromTo())) && (DbDomainConst.LogicDataType.CURRENCY.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DECIMAL.equals(item.getLogicalDataType()) 
												|| DbDomainConst.LogicDataType.INTEGER.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DATETIME.equals(item.getLogicalDataType()) 
												|| DbDomainConst.LogicDataType.DATE.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.TIME.equals(item.getLogicalDataType()))) {
											InputBean inputBeanFrom = new InputBean();
											inputBeanFrom.setInputBeanCode(item.getItemCode() + "From");
											if (item.getMessageDesign() != null) {
												inputBeanFrom.setInputBeanName(item.getMessageDesign().getMessageCode());
											} else {
												inputBeanFrom.setInputBeanName("");
											}
											if (item.getPhysicalDataType() != null) {
												inputBeanFrom.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
											}
											inputBeanFrom.setScreenItemId(item.getScreenItemId());
											inputBeanFrom.setScreenItem(item);
											inputBeanFrom.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
											inputBeanFrom.setParentInputBeanId(tempParentObjectId.toString());
											inputBeanFrom.setFlagUsingTempId(true);
											inputBeanInsert.add(inputBeanFrom);
											lstInputbeanInsertOfThisForm.add(inputBeanFrom);

											InputBean inputBeanTo = new InputBean();
											inputBeanTo.setInputBeanCode(item.getItemCode() + "To");
											if (item.getMessageDesign() != null) {
												inputBeanTo.setInputBeanName(item.getMessageDesign().getMessageCode());
											} else {
												inputBeanTo.setInputBeanName("");
											}
											if (item.getPhysicalDataType() != null) {
												inputBeanTo.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
											}
											inputBeanTo.setScreenItemId(item.getScreenItemId());
											inputBeanTo.setScreenItem(item);
											inputBeanTo.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
											inputBeanTo.setParentInputBeanId(tempParentObjectId.toString());
											inputBeanTo.setFlagUsingTempId(true);
											inputBeanInsert.add(inputBeanTo);
											lstInputbeanInsertOfThisForm.add(inputBeanTo);
										} else {
											InputBean inputBean = new InputBean();
											inputBean.setInputBeanCode(item.getItemCode());
											if (item.getMessageDesign() != null) {
												inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
											} else {
												inputBean.setInputBeanName("");
											}
											if (item.getPhysicalDataType() != null) {
												inputBean.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
											}
											inputBean.setScreenItemId(item.getScreenItemId());
											inputBean.setScreenItem(item);
											inputBean.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
											inputBean.setParentInputBeanId(tempParentObjectId.toString());
											inputBean.setFlagUsingTempId(true);
											if (DbDomainConst.LogicDataType.CHECKBOX.equals(item.getLogicalDataType()) && inputBean.getDataType() != null && inputBean.getDataType().intValue() != DbDomainConst.BaseType.BOOLEAN_BASETYPE) {
												inputBean.setArrayFlg(Boolean.TRUE);
											}
											inputBeanInsert.add(inputBean);
											lstInputbeanInsertOfThisForm.add(inputBean);
											
											// Add autocomplete label input bean
											if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
												InputBean inputBeanLabel = new InputBean();
												inputBeanLabel.setInputBeanCode(item.getItemCode() + AUTOCOMPLETE);
												if (item.getMessageDesign() != null) {
													inputBeanLabel.setInputBeanName(item.getMessageDesign().getMessageCode());
												} else {
													inputBeanLabel.setInputBeanName("");
												}
												inputBeanLabel.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
												inputBeanLabel.setScreenItemId(item.getScreenItemId());
												inputBeanLabel.setScreenItem(item);
												inputBeanLabel.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
												inputBeanLabel.setParentInputBeanId(tempParentObjectId.toString());
												inputBeanLabel.setFlagUsingTempId(true);
												inputBeanInsert.add(inputBeanLabel);
												lstInputbeanInsertOfThisForm.add(inputBeanLabel);
											}
										}
									}
								}
							}
						}
					} else {
						List<ScreenItem> lstScreenItemOfThisArea = mapAreaAndItem.get(area.getScreenAreaId()) != null ? mapAreaAndItem.get(area.getScreenAreaId()) : new ArrayList<ScreenItem>();
						Map<Long,String> mapCheckScreenItemAutocomplete= new HashMap<Long,String>();
						Set<Long> hashSetScreenItemId = new HashSet<Long>();
						
						for (ScreenItem item : lstScreenItemOfThisArea) {
							if (item.getLogicalDataType() == null || DbDomainConst.LogicDataType.LABEL.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.BUTTON.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.LINK.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.LINK_DYNAMIC.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.CUSTOM_ITEM.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.CUSTOM_SECTION.equals(item.getLogicalDataType()) || (DbDomainConst.LogicDataType.LABEL_DYNAMIC.equals(item.getLogicalDataType()) && !ScreenDesignConst.ScreenItemConst.ITEM_TYPE_HIDDEN.equals(item.getItemType())))
								continue;

							boolean isNotExists = false;
							for (InputBean input : form.getInputBeans()) {
								if (input.getScreenItemId() != null && item.getScreenItemId() != null && item.getScreenItemId().equals(input.getScreenItemId())) {
									isNotExists = true;
									boolean isMatchDataType = true;
									// check update
									if (((ScreenDesignConst.FromTo.NORMAL.equals(item.getDisplayFromTo()) && (item.getItemCode().equals(input.getInputBeanCode()) || input.getInputBeanCode().equals(item.getItemCode() + AUTOCOMPLETE))) 
											|| ((item.getDisplayFromTo() == null || ScreenDesignConst.FromTo.FROM_TO.equals(item.getDisplayFromTo())) && (input.getInputBeanCode().equals(item.getItemCode() + "From") || input.getInputBeanCode().equals(item.getItemCode() + "To"))))
										&& input.getMessageDesign() != null && item.getMessageDesign().getMessageString().equals(input.getMessageDesign().getMessageString())){
										if (!DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getLogicalDataType())) {
											if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
												if (!(input.getInputBeanCode().equals(item.getItemCode() + AUTOCOMPLETE)) && item.getPhysicalDataType() != null && input.getDataType() != null && !input.getDataType().equals(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()))) {
													isMatchDataType = false;
												}
											}else if (item.getPhysicalDataType() != null && input.getDataType() != null && !input.getDataType().equals(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()))) {
												isMatchDataType = false;
											}
										}
									} else {
										if(ScreenDesignConst.ScreenPatternType.SEARCH.equals(screenDesign.getScreenPatternType())) {
											if (DbDomainConst.LogicDataType.CURRENCY.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DECIMAL.equals(item.getLogicalDataType()) 
													|| DbDomainConst.LogicDataType.INTEGER.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DATETIME.equals(item.getLogicalDataType()) 
													|| DbDomainConst.LogicDataType.DATE.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.TIME.equals(item.getLogicalDataType())) {
												List<InputBean> inputOfScreenItem = new ArrayList<InputBean>();
												boolean createNormalInputBean = true;
												boolean createFromToInputBean = true;
												for(InputBean in : form.getInputBeans()) {
													if(in.getScreenItemId() != null && in.getScreenItemId().equals(item.getScreenItemId())) {
														inputOfScreenItem.add(in);
													}
													if (in.getDataType().equals(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()))
															&& in.getInputBeanCode().equals(item.getItemCode())) {
														createNormalInputBean = false;
													} else if (in.getDataType().equals(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()))
															&& (in.getInputBeanCode().equals(item.getItemCode() + "From") || in.getInputBeanCode().equals(item.getItemCode() + "To"))) {
														createFromToInputBean = false;
													}
												}
												// remove all input bean of this screen item
												for (InputBean in : inputOfScreenItem) {
													if (!inputBeanMappingDelete.contains(in) && (createFromToInputBean && createNormalInputBean)) {
														inputBeanMappingDelete.add(in);
													}
												}
												if (!hashSetScreenItemId.contains(item.getScreenItemId())) {
													hashSetScreenItemId.add(item.getScreenItemId());
													if (ScreenDesignConst.FromTo.FROM_TO.equals(item.getDisplayFromTo()) && createFromToInputBean) {
														// add 2 input bean: from and to
														InputBean inputbeanFrom = populateInputBean(item.getItemCode() + "From", item, form, item.getScreenArea(), businessDesigns, lstParentInputBeans);
														inputBeanInsert.add(inputbeanFrom);
														lstInputbeanInsertOfThisForm.add(inputbeanFrom);

														InputBean inputbeanTo = populateInputBean(item.getItemCode() + "To", item, form, item.getScreenArea(), businessDesigns, lstParentInputBeans);
														inputBeanInsert.add(inputbeanTo);
														lstInputbeanInsertOfThisForm.add(inputbeanTo);
													} else if (!ScreenDesignConst.FromTo.FROM_TO.equals(item.getDisplayFromTo()) && createNormalInputBean) {
														// add 1 input bean
														InputBean inputbean = populateInputBean(item.getItemCode(), item, form, item.getScreenArea(), businessDesigns, lstParentInputBeans);
														inputBeanInsert.add(inputbean);
														lstInputbeanInsertOfThisForm.add(inputbean);
													}
												}
											} else {
												isMatchDataType = false;
											}
										} else {
											isMatchDataType = false;
										}
									}

									if (!isMatchDataType) {
										if (item.getPhysicalDataType() != null)
											input.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));

										if (DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getLogicalDataType())) {
											input.setDataType(BusinessDesignConst.DataType.EXTERNAL_OBJECT);
										}
										// Check incase is autocomplete
										if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
											if(mapCheckScreenItemAutocomplete.containsKey(input.getScreenItemId())){
												input.setInputBeanCode(item.getItemCode() + AUTOCOMPLETE);
												input.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
											} else {
												input.setInputBeanCode(item.getItemCode());
												mapCheckScreenItemAutocomplete.put(input.getScreenItemId(), input.getInputBeanId());
											}
										} else {
											input.setInputBeanCode(item.getItemCode());
										}
										if (DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getLogicalDataType())) {
											input.setInputBeanName(ScreenDesignConst.MULTIPART_FILE);
										} else {
											if (item.getMessageDesign() != null) {
												input.setInputBeanName(item.getMessageDesign().getMessageCode());
											}
										}
										input.setScreenItem(item);
										if (input.getDataType() != null && input.getDataType().intValue() != DbDomainConst.BaseType.BOOLEAN_BASETYPE) {
											if (DbDomainConst.LogicDataType.CHECKBOX.equals(item.getLogicalDataType())) {
												input.setArrayFlg(Boolean.TRUE);
											} else if (DbDomainConst.LogicDataType.RADIO.equals(item.getLogicalDataType())) {
												input.setArrayFlg(Boolean.FALSE);
											}
										}
										inputBeanUpdate.add(input);
									} else {
										if (input.getDataType() != null && input.getDataType().intValue() != DbDomainConst.BaseType.BOOLEAN_BASETYPE) {
											if (DbDomainConst.LogicDataType.CHECKBOX.equals(item.getLogicalDataType())) {
												input.setArrayFlg(Boolean.TRUE);
												inputBeanUpdate.add(input);
											} else if (DbDomainConst.LogicDataType.RADIO.equals(item.getLogicalDataType())) {
												input.setArrayFlg(Boolean.FALSE);
												inputBeanUpdate.add(input);
											}
										}
									}

									continue;
								}
							}
							if (!isNotExists) {
								ScreenArea screenArea = item.getScreenArea();
								if (screenArea != null) {
									// Check if item have same code with old input bean. Then update mapping, not insert
									InputBean inputBeanMapping = null;
									boolean isRemapping = false;
									for (InputBean input : inputBeans) {
										if ((input.getInputBeanCode().equals(item.getItemCode()) || input.getInputBeanCode().equals(item.getItemCode() + "From") || input.getInputBeanCode().equals(item.getItemCode() + "To") || input.getInputBeanCode().equals(item.getItemCode() + AUTOCOMPLETE))
											&& FunctionCommon.isEmpty(input.getScreenItemId())) {
											inputBeanMapping = new InputBean();
											inputBeanMapping.setArrayFlg(input.getArrayFlg());
											inputBeanMapping.setInputBeanId(input.getInputBeanId());
											inputBeanMapping.setInputBeanCode(input.getInputBeanCode());
											inputBeanMapping.setInputBeanName(input.getInputBeanName());
											if (input.getInputBeanCode().equals(item.getItemCode() + AUTOCOMPLETE)) {
												inputBeanMapping.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
											} else {
												if (item.getPhysicalDataType() != null) {
													inputBeanMapping.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
												} else {
													inputBeanMapping.setDataType(item.getDataSourceType());
												}
											}
											inputBeanMapping.setScreenItemId(item.getScreenItemId());
											inputBeanMapping.setBusinessLogicId(input.getBusinessLogicId());
											inputBeanUpdate.add(inputBeanMapping);
											isRemapping = true;
										}
									}
									if (!isRemapping) {
										// Check if item don't have same code with old input bean. Then insert
										if (screenArea.getObjectMappingType() != null) {
											// Adding by HungHX - Special case : if add screen item is File Upload .
											if (DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getLogicalDataType()) && externalObjectDefinition != null) {
												if (CollectionUtils.isNotEmpty(lstExternalObjectAttribute)) {
													// Assign object parent form object definition
													InputBean inputBean = new InputBean();
													inputBean.setInputBeanId(tempInputBeanId.toString());
													inputBean.setInputBeanCode(item.getItemCode());
													inputBean.setInputBeanName(ScreenDesignConst.MULTIPART_FILE);
													inputBean.setScreenItemId(item.getScreenItemId());
													inputBean.setScreenItem(item);
													inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.DEFAULT);
													inputBean.setDataType(BusinessDesignConst.DataType.EXTERNAL_OBJECT);
													inputBean.setArrayFlg(Boolean.FALSE);
													inputBean.setObjectType(DbDomainConst.ObjectType.EXTERNAL_OBJECT);
													inputBean.setObjectId(externalObjectDefinition.getExternalObjectDefinitionId());
													inputBean.setObjectFlg(Boolean.TRUE);
													inputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
													// add to list
													inputBeanInsert.add(inputBean);
													lstInputbeanInsertOfThisForm.add(inputBean);
													tempParentMultilepartFileId = tempInputBeanId;
													// set input bean into parent object if have
													String parentInputBeanId = null;
													if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_TABLE_DESIGN.equals(screenArea.getObjectMappingType())) {
														for (BusinessDesign blogic : businessDesigns) {
															if (form.getScreenFormIdStore() != null && form.getScreenFormIdStore().equals(blogic.getScreenFormId())) {
																// set parent input bean id
																for (InputBean parentInputbean : lstParentInputBeans) {
																	if (blogic.getBusinessLogicId().equals(parentInputbean.getBusinessLogicId()) && parentInputbean.getTblDesignId() != null && parentInputbean.getTblDesignId().equals(screenArea.getObjectMappingId())) {
																		parentInputBeanId = parentInputbean.getInputBeanId();
																		break;
																	}
																}
																inputBean.setParentInputBeanId(parentInputBeanId);
															}
														}
													} else if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_INPUT_BEAN.equals(screenArea.getObjectMappingType())) {
														for (BusinessDesign blogic : businessDesigns) {
															if (form.getScreenFormIdStore() != null && form.getScreenFormIdStore().equals(blogic.getScreenFormId())) {
																// set parent input bean id
																for (InputBean parentInputbean : lstParentInputBeans) {
																	if (blogic.getBusinessLogicId().equals(parentInputbean.getBusinessLogicId()) && parentInputbean.getInputBeanId() != null && DataTypeUtils.equals(parentInputbean.getInputBeanId(), screenArea.getObjectMappingId())) {
																		parentInputBeanId = parentInputbean.getInputBeanId();
																		break;
																	}
																}
																inputBean.setParentInputBeanId(parentInputBeanId);
															}
														}
													}

													tempInputBeanId++;
													// Assign object parent form object attribute
													for (ExternalObjectAttribute extObjAttrIter : lstExternalObjectAttribute) {
														inputBean = new InputBean();
														inputBean.setInputBeanCode(extObjAttrIter.getExternalObjectAttributeCode());
														inputBean.setInputBeanName(extObjAttrIter.getExternalObjectAttributeName());
														inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.ADDED_DEFAULT);
														inputBean.setDataType(extObjAttrIter.getDataType());
														inputBean.setArrayFlg(extObjAttrIter.getArrayFlg());
														inputBean.setParentInputBeanId(tempParentMultilepartFileId.toString());
														inputBean.setObjectFlg(Boolean.FALSE);
														inputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
														inputBean.setFlagUsingTempId(true);
														// add to list
														inputBeanInsert.add(inputBean);
														lstInputbeanInsertOfThisForm.add(inputBean);
														tempInputBeanId++;
													}
												}
												// End adding: Normal case
											} else {
												if (!ScreenDesignConst.ScreenPatternType.SEARCH.equals(screenDesign.getScreenPatternType())) {
													InputBean inputbean = populateInputBean(item.getItemCode(), item, form, screenArea, businessDesigns, lstParentInputBeans);
													inputBeanInsert.add(inputbean);
													lstInputbeanInsertOfThisForm.add(inputbean);
													// Add autocomplete label input bean
													if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
														InputBean inputBeanLabel = populateInputBeanAutoCompleteLabel(item.getItemCode() + AUTOCOMPLETE, item, form, screenArea, businessDesigns, lstParentInputBeans);
														inputBeanInsert.add(inputBeanLabel);
														lstInputbeanInsertOfThisForm.add(inputBeanLabel);
													}
												} else {
													if ((item.getDisplayFromTo() == null || ScreenDesignConst.FromTo.FROM_TO.equals(item.getDisplayFromTo())) && (DbDomainConst.LogicDataType.CURRENCY.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DECIMAL.equals(item.getLogicalDataType()) 
															|| DbDomainConst.LogicDataType.INTEGER.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DATETIME.equals(item.getLogicalDataType()) 
															|| DbDomainConst.LogicDataType.DATE.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.TIME.equals(item.getLogicalDataType()))) {
														InputBean inputbeanFrom = populateInputBean(item.getItemCode() + "From", item, form, screenArea, businessDesigns, lstParentInputBeans);
														inputBeanInsert.add(inputbeanFrom);
														lstInputbeanInsertOfThisForm.add(inputbeanFrom);

														InputBean inputbeanTo = populateInputBean(item.getItemCode() + "To", item, form, screenArea, businessDesigns, lstParentInputBeans);
														inputBeanInsert.add(inputbeanTo);
														lstInputbeanInsertOfThisForm.add(inputbeanTo);
													} else {
														InputBean inputbean = populateInputBean(item.getItemCode(), item, form, screenArea, businessDesigns, lstParentInputBeans);
														inputBeanInsert.add(inputbean);
														lstInputbeanInsertOfThisForm.add(inputbean);
														
														// Add autocomplete label input bean
														if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
															InputBean inputBeanLabel = populateInputBeanAutoCompleteLabel(item.getItemCode() + AUTOCOMPLETE, item, form, screenArea, businessDesigns, lstParentInputBeans);
															inputBeanInsert.add(inputBeanLabel);
															lstInputbeanInsertOfThisForm.add(inputBeanLabel);
														}
													}
												}
											}
										} else {
											// Adding a object parent input bean
											InputBean parentInputBean = null;
											if (!hashSetAreaCode.contains(screenArea.getAreaCode())) {
												parentInputBean = new InputBean();
												parentInputBean.setInputBeanId(tempInputBeanId.toString());
												parentInputBean.setInputBeanCode(screenArea.getAreaCode());
												parentInputBean.setInputBeanName(screenArea.getMessageDesign() == null ? "" : StringUtils.isNotEmpty(screenArea.getMessageDesign().getMessageCode()) ? screenArea.getMessageDesign().getMessageCode() : "");
												parentInputBean.setInputBeanType(BusinessDesignConst.InputBeanType.DEFAULT);
												parentInputBean.setDataType(BusinessDesignConst.DataType.OBJECT);
												if (ScreenDesignConst.AreaType.LIST.equals(screenArea.getAreaType())) {
													parentInputBean.setArrayFlg(Boolean.TRUE);
												} else {
													parentInputBean.setArrayFlg(Boolean.FALSE);
												}
												parentInputBean.setObjectFlg(Boolean.TRUE);
												parentInputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
												// add to list
												inputBeanInsert.add(parentInputBean);
												lstInputbeanInsertOfThisForm.add(parentInputBean);
												hashSetAreaCode.add(screenArea.getAreaCode());
												mapAreaCode.put(screenArea.getAreaCode(), parentInputBean);
												tempParentObjectId = tempInputBeanId;
												tempInputBeanId++;
											} else {
												parentInputBean = mapAreaCode.get(screenArea.getAreaCode());
												if (parentInputBean != null) {
													tempParentObjectId = Integer.parseInt(parentInputBean.getInputBeanId());
												}
											}

											// set object_mapping_id of screen area
											screenArea.setObjectMappingId(Long.parseLong(parentInputBean.getInputBeanId()));
											areaUpdateMappingObjectType.add(screenArea);

											// Adding by HungHX - Special case : if add screen item is File Upload .
											if (DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getLogicalDataType()) && externalObjectDefinition != null) {
												if (CollectionUtils.isNotEmpty(lstExternalObjectAttribute)) {
													// Assign object parent form object definition
													InputBean inputBean = new InputBean();
													inputBean.setInputBeanId(tempInputBeanId.toString());
													inputBean.setInputBeanCode(item.getItemCode());
													inputBean.setInputBeanName(ScreenDesignConst.MULTIPART_FILE);
													inputBean.setScreenItemId(item.getScreenItemId());
													inputBean.setScreenItem(item);
													inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.DEFAULT);
													inputBean.setDataType(BusinessDesignConst.DataType.EXTERNAL_OBJECT);
													inputBean.setArrayFlg(Boolean.FALSE);
													inputBean.setParentInputBeanId(tempParentObjectId.toString());
													inputBean.setObjectType(DbDomainConst.ObjectType.EXTERNAL_OBJECT);
													inputBean.setObjectId(externalObjectDefinition.getExternalObjectDefinitionId());
													inputBean.setObjectFlg(Boolean.TRUE);
													inputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
													inputBean.setFlagUsingTempId(true);
													// add to list
													inputBeanInsert.add(inputBean);
													lstInputbeanInsertOfThisForm.add(inputBean);
													tempParentMultilepartFileId = tempInputBeanId;
													tempInputBeanId++;

													// Assign object parent form object attribute
													for (ExternalObjectAttribute extObjAttrIter : lstExternalObjectAttribute) {
														inputBean = new InputBean();
														inputBean.setInputBeanId(tempInputBeanId.toString());
														inputBean.setInputBeanCode(extObjAttrIter.getExternalObjectAttributeCode());
														inputBean.setInputBeanName(extObjAttrIter.getExternalObjectAttributeName());
														inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.ADDED_DEFAULT);
														inputBean.setDataType(extObjAttrIter.getDataType());
														inputBean.setArrayFlg(extObjAttrIter.getArrayFlg());
														inputBean.setParentInputBeanId(tempParentMultilepartFileId.toString());
														inputBean.setObjectFlg(Boolean.FALSE);
														inputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
														inputBean.setFlagUsingTempId(true);
														// add to list
														inputBeanInsert.add(inputBean);
														lstInputbeanInsertOfThisForm.add(inputBean);
														tempInputBeanId++;
													}
												}
												// End adding: Normal case
											} else {
												if (!DbDomainConst.ScreenPatternType.SEARCH.equals(screenDesign.getScreenPatternType())) {
													InputBean inputBean = new InputBean();
													inputBean.setInputBeanCode(item.getItemCode());
													if (item.getMessageDesign() != null) {
														inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
													} else {
														inputBean.setInputBeanName("");
													}
													if (item.getPhysicalDataType() != null) {
														inputBean.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
													}
													inputBean.setScreenItemId(item.getScreenItemId());
													inputBean.setScreenItem(item);
													inputBean.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
													inputBean.setParentInputBeanId(tempParentObjectId.toString());
													inputBean.setFlagUsingTempId(true);
													if (DbDomainConst.LogicDataType.CHECKBOX.equals(item.getLogicalDataType()) && inputBean.getDataType() != null && inputBean.getDataType().intValue() != DbDomainConst.BaseType.BOOLEAN_BASETYPE) {
														inputBean.setArrayFlg(Boolean.TRUE);
													}
													inputBeanInsert.add(inputBean);
													lstInputbeanInsertOfThisForm.add(inputBean);
													
													// Add autocomplete label input bean
													if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
														InputBean inputBeanLabel = new InputBean();
														inputBeanLabel.setInputBeanCode(item.getItemCode() + AUTOCOMPLETE);
														if (item.getMessageDesign() != null) {
															inputBeanLabel.setInputBeanName(item.getMessageDesign().getMessageCode());
														} else {
															inputBeanLabel.setInputBeanName("");
														}
														inputBeanLabel.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
														inputBeanLabel.setScreenItemId(item.getScreenItemId());
														inputBeanLabel.setScreenItem(item);
														inputBeanLabel.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
														inputBeanLabel.setParentInputBeanId(tempParentObjectId.toString());
														inputBeanLabel.setFlagUsingTempId(true);
														inputBeanInsert.add(inputBeanLabel);
														lstInputbeanInsertOfThisForm.add(inputBeanLabel);
													}
												} else {
													if ((item.getDisplayFromTo() == null || ScreenDesignConst.FromTo.FROM_TO.equals(item.getDisplayFromTo())) && (DbDomainConst.LogicDataType.CURRENCY.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DECIMAL.equals(item.getLogicalDataType()) 
															|| DbDomainConst.LogicDataType.INTEGER.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DATETIME.equals(item.getLogicalDataType()) 
															|| DbDomainConst.LogicDataType.DATE.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.TIME.equals(item.getLogicalDataType()))) {
														InputBean inputBeanFrom = new InputBean();
														inputBeanFrom.setInputBeanCode(item.getItemCode() + "From");
														if (item.getMessageDesign() != null) {
															inputBeanFrom.setInputBeanName(item.getMessageDesign().getMessageCode());
														} else {
															inputBeanFrom.setInputBeanName("");
														}
														if (item.getPhysicalDataType() != null) {
															inputBeanFrom.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
														}
														inputBeanFrom.setScreenItemId(item.getScreenItemId());
														inputBeanFrom.setScreenItem(item);
														inputBeanFrom.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
														inputBeanFrom.setParentInputBeanId(tempParentObjectId.toString());
														inputBeanFrom.setFlagUsingTempId(true);
														inputBeanInsert.add(inputBeanFrom);
														lstInputbeanInsertOfThisForm.add(inputBeanFrom);

														InputBean inputBeanTo = new InputBean();
														inputBeanTo.setInputBeanCode(item.getItemCode() + "To");
														if (item.getMessageDesign() != null) {
															inputBeanTo.setInputBeanName(item.getMessageDesign().getMessageCode());
														} else {
															inputBeanTo.setInputBeanName("");
														}
														if (item.getPhysicalDataType() != null) {
															inputBeanTo.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
														}
														inputBeanTo.setScreenItemId(item.getScreenItemId());
														inputBeanTo.setScreenItem(item);
														inputBeanTo.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
														inputBeanTo.setParentInputBeanId(tempParentObjectId.toString());
														inputBeanTo.setFlagUsingTempId(true);
														inputBeanInsert.add(inputBeanTo);
														lstInputbeanInsertOfThisForm.add(inputBeanTo);
													} else {
														InputBean inputBean = new InputBean();
														inputBean.setInputBeanCode(item.getItemCode());
														if (item.getMessageDesign() != null) {
															inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
														} else {
															inputBean.setInputBeanName("");
														}
														if (item.getPhysicalDataType() != null) {
															inputBean.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
														}
														inputBean.setScreenItemId(item.getScreenItemId());
														inputBean.setScreenItem(item);
														inputBean.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
														inputBean.setParentInputBeanId(tempParentObjectId.toString());
														inputBean.setFlagUsingTempId(true);
														inputBeanInsert.add(inputBean);
														lstInputbeanInsertOfThisForm.add(inputBean);
														
														// Add autocomplete label input bean
														if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
															InputBean inputBeanLabel = new InputBean();
															inputBeanLabel.setInputBeanCode(item.getItemCode() + AUTOCOMPLETE);
															if (item.getMessageDesign() != null) {
																inputBeanLabel.setInputBeanName(item.getMessageDesign().getMessageCode());
															} else {
																inputBeanLabel.setInputBeanName("");
															}
															inputBeanLabel.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
															inputBeanLabel.setScreenItemId(item.getScreenItemId());
															inputBeanLabel.setScreenItem(item);
															inputBeanLabel.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
															inputBeanLabel.setParentInputBeanId(tempParentObjectId.toString());
															inputBeanLabel.setFlagUsingTempId(true);
															inputBeanInsert.add(inputBeanLabel);
															lstInputbeanInsertOfThisForm.add(inputBeanLabel);
														}
													}
												}
											}
										}
									}
								}
							} else {
								// Check if item have same code and type with old input bean. Then update mapping, not insert
								if (DbDomainConst.ScreenPatternType.SEARCH.equals(screenDesign.getScreenPatternType())
										&& (DbDomainConst.LogicDataType.CURRENCY.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DECIMAL.equals(item.getLogicalDataType()) 
										|| DbDomainConst.LogicDataType.INTEGER.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DATETIME.equals(item.getLogicalDataType()) 
										|| DbDomainConst.LogicDataType.DATE.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.TIME.equals(item.getLogicalDataType()))) {
									if (item.getDisplayFromTo() == null || ScreenDesignConst.FromTo.FROM_TO.equals(item.getDisplayFromTo())) {
										InputBean inputBeanMapping = null;
										for (InputBean input : inputBeans) {
											if ((input.getInputBeanCode().equals(item.getItemCode() + "From") || input.getInputBeanCode().equals(item.getItemCode() + "To"))
													&& FunctionCommon.isEmpty(input.getScreenItemId())
													&& input.getDataType().equals(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()))) {
												inputBeanMapping = new InputBean();
												inputBeanMapping.setInputBeanId(input.getInputBeanId());
												inputBeanMapping.setInputBeanCode(input.getInputBeanCode());
												inputBeanMapping.setInputBeanName(input.getInputBeanName());
												inputBeanMapping.setDataType(input.getDataType());
												inputBeanMapping.setScreenItemId(item.getScreenItemId());
												inputBeanMapping.setBusinessLogicId(input.getBusinessLogicId());
												inputBeanUpdate.add(inputBeanMapping);
											}
											if ((input.getInputBeanCode().equals(item.getItemCode()) || input.getInputBeanCode().equals(item.getItemCode()))
													&& !FunctionCommon.isEmpty(input.getScreenItemId())
													&& input.getDataType().equals(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()))) {
												inputBeanMappingDelete.add(input);
											}
										}
									} else {
										InputBean inputBeanMapping = null;
										for (InputBean input : inputBeans) {
											if ((input.getInputBeanCode().equals(item.getItemCode()) || input.getInputBeanCode().equals(item.getItemCode()))
													&& FunctionCommon.isEmpty(input.getScreenItemId())
													&& input.getDataType().equals(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()))) {
												inputBeanMapping = new InputBean();
												inputBeanMapping.setInputBeanId(input.getInputBeanId());
												inputBeanMapping.setInputBeanCode(input.getInputBeanCode());
												inputBeanMapping.setInputBeanName(input.getInputBeanName());
												inputBeanMapping.setDataType(input.getDataType());
												inputBeanMapping.setScreenItemId(item.getScreenItemId());
												inputBeanMapping.setBusinessLogicId(input.getBusinessLogicId());
												inputBeanUpdate.add(inputBeanMapping);
											}
											if ((input.getInputBeanCode().equals(item.getItemCode() + "From") || input.getInputBeanCode().equals(item.getItemCode() + "To"))
													&& !FunctionCommon.isEmpty(input.getScreenItemId())
													&& input.getDataType().equals(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()))) {
												inputBeanMappingDelete.add(input);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			if (FunctionCommon.isNotEmpty(lstInputbeanInsertOfThisForm)) {
				mapFormAndInputBean.put(form.getScreenFormIdStore(), lstInputbeanInsertOfThisForm);
			}
		}
		Map<String, List<InputBean>> mapInputBeanAndChild = new HashMap<String, List<InputBean>>();
		lstAllInputBeans.addAll(inputBeanInsert);
		for (InputBean input : lstAllInputBeans) {
			if (input.getScreenItemId() == null) {
				// Check if screen screen area has been delete, then correspond delete input bean
				if (input.getParentInputBeanId() == null) {
					boolean isExists = false;
					for (ScreenArea area : screenAreas) {
						if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_INPUT_BEAN.equals(area.getObjectMappingType())) {
							if (area.getObjectMappingId() != null && area.getObjectMappingId().toString().equals(input.getInputBeanId())) {
								isExists = true;
								break;
							}
						} else if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_TABLE_DESIGN.equals(area.getObjectMappingType())) {
							if (area.getObjectMappingId() != null && area.getObjectMappingId().equals(input.getTblDesignId())) {
								isExists = true;
								break;
							}
						}
					}
					if (!isExists) {
						recursiveInputBean(input, inputBeanMappingDelete, inputBeans);
					}
				} else {
					continue;
				}
			} else {
				boolean isExists = false;
				for (ScreenItem item : lstAllScreenItem) {
					if (input.getScreenItemId() != null && item.getScreenItemId() != null && input.getScreenItemId().equals(item.getScreenItemId())) {
						isExists = true;
						break;
					}
				}
				if (!isExists) {
					recursiveInputBean(input, inputBeanMappingDelete, inputBeans);
				}
			}
			List<InputBean> childInput = mapInputBeanAndChild.get(input.getParentInputBeanId());
			if (childInput == null) {
				mapInputBeanAndChild.put(input.getInputBeanId(), new ArrayList<InputBean>());
			} else {
				childInput.add(input);
				mapInputBeanAndChild.put(input.getParentInputBeanId(), childInput);
			}
		}
		// Check if all input bean of screen item were deleted, then delete input bean mapping to area
		List<InputBean> tempDelete = new ArrayList<InputBean>();
		for (InputBean input : lstParentInputBeans) {
			List<InputBean> lstChildInput = mapInputBeanAndChild.get(input.getInputBeanId());
			if (lstChildInput != null && lstChildInput.size() > 0) {
				int count = 0;
				for (InputBean deletedInput : inputBeanMappingDelete) {
					if (input.getInputBeanId().equals(deletedInput.getInputBeanId())) {
						break;
					}
					for (InputBean childInput : lstChildInput) {
						if (childInput.getInputBeanId() == null) {
							count--;
						} else if (childInput.getInputBeanId().equals(deletedInput.getInputBeanId())) {
							count++;
						}
					}
					if (count >= lstChildInput.size()) {
						tempDelete.add(input);
						for (ScreenArea area : screenAreaInputs) {
							if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_INPUT_BEAN.equals(area.getObjectMappingType())) {
								if (area.getObjectMappingId() != null && area.getObjectMappingId().toString().equals(input.getInputBeanId())) {
									areaUpdateMappingObjectTypeNull.add(area);
									break;
								}
							} else if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_TABLE_DESIGN.equals(area.getObjectMappingType())) {
								if (area.getObjectMappingId() != null && area.getObjectMappingId().equals(input.getTblDesignId())) {
									areaUpdateMappingObjectTypeNull.add(area);
									break;
								}
							}
						}
					}
				}
			}
		}
		inputBeanMappingDelete.addAll(tempDelete);
		// Insert, update input bean of initial blogic according to screen parameter
		List<InputBean> inititalInputBeans = new ArrayList<InputBean>();
		for (BusinessDesign blogic : businessDesignInitial) {
			for (InputBean input : inputBeansInitialScreen) {
				if (blogic.getBusinessLogicId().equals(input.getBusinessLogicId())) {
					inititalInputBeans.add(input);
				}
			}
		}
		if (screenDesign.getArrScreenParameter() != null) {
			for (ScreenParameter screenParameter : screenDesign.getArrScreenParameter()) {
				if (screenParameter != null) {
					if (screenParameter.getScreenParamIdStore() == null) {
						// Check if item have same code and type with old input bean. Then update mapping, not insert
						InputBean inputBeanMapping = null;
						for (InputBean input : inititalInputBeans) {
							if (input.getInputBeanCode().equals(screenParameter.getScreenParamCode()) && FunctionCommon.isEmpty(input.getScreenItemId()) && (input.getDataType() != null && input.getDataType().equals(screenParameter.getDataType()))) {
								inputBeanMapping = new InputBean();
								inputBeanMapping.setInputBeanId(input.getInputBeanId());
								inputBeanMapping.setInputBeanCode(screenParameter.getScreenParamCode());
								inputBeanMapping.setInputBeanName(screenParameter.getScreenParamName());
								inputBeanMapping.setDataType(screenParameter.getDataType());
								inputBeanMapping.setBusinessLogicId(input.getBusinessLogicId());
								break;
							}
						}
						if (inputBeanMapping != null) {
							inputBeanUpdate.add(inputBeanMapping);
						} else {
							InputBean inputBean = GenerateScreenContruct.setInputbean(tempInputBeanId, screenDesign, screenParameter.getScreenParamCode(), screenParameter.getScreenParamName(), screenParameter.getDataType(), null, null, null, null, null, null, null, null, DbDomainConst.InputBeanType.DEFAULT);
							inputBeanInsertForParametters.add(inputBean);
							tempInputBeanId++;
						}
					} else {
						for (ScreenParameter param : arrScreenParameterBeforeDelete) {
							for (InputBean input : inititalInputBeans) {
								if (screenParameter.getScreenParamIdStore().equals(param.getScreenParamId()) && param.getDataType().equals(input.getDataType()) && param.getScreenParamCode().equals(input.getInputBeanCode()) && (!screenParameter.getScreenParamCode().equals(param.getScreenParamCode()) || !screenParameter.getDataType().equals(param.getDataType()))) {
									InputBean inputBeanMapping = new InputBean();
									inputBeanMapping.setInputBeanId(input.getInputBeanId());
									inputBeanMapping.setInputBeanCode(screenParameter.getScreenParamCode());
									inputBeanMapping.setInputBeanName(screenParameter.getScreenParamName());
									inputBeanMapping.setDataType(screenParameter.getDataType());
									inputBeanMapping.setBusinessLogicId(input.getBusinessLogicId());
									inputBeanUpdate.add(inputBeanMapping);
								}
							}
						}
					}
				}
			}
		}
		if (screenDesign.getDesignMode().equals(ScreenDesignConst.DesignMode.DESIGN)) {
			// TRUNGDV : insert, update, delete input bean
			if (FunctionCommon.isNotEmpty(inputBeanMappingDelete)) {
				businessDesignRepository.removeScreenItemMapping(inputBeanMappingDelete);
			}
			if (FunctionCommon.isNotEmpty(inputBeanUpdate)) {
				// BEGIN TASK : update validate input bean
				businessDesignRepository.modifyInputBeanWhenModifyScreenItem(inputBeanUpdate);
				modifyValidationCheckDetails(inputBeanUpdate, screenItems, accountProfile);
				// END TASK
			}
			if (FunctionCommon.isNotEmpty(mapFormAndInputBean)) {
				// clone input bean to set for each blogic
				List<InputBean> lstInputRealInsert = new ArrayList<InputBean>();
				for (BusinessDesign blogic : businessDesigns) {
					List<InputBean> inputbeanOfThisBlogic = mapFormAndInputBean.get(blogic.getScreenFormId());
					if (inputbeanOfThisBlogic != null) {
						if (BusinessDesignConst.RETURN_TYPE_SCREEN.equals(blogic.getReturnType())) {
							for (InputBean in : inputbeanOfThisBlogic) {
								try {
									InputBean inClone = (InputBean) in.clone();
									inClone.setBusinessLogicId(blogic.getBusinessLogicId());
									Integer maxItemSeqNo = maxSeqItemNoOfInput.get(inClone.getBusinessLogicId());
									// DungNN - if null need to
									maxItemSeqNo = maxItemSeqNo == null ? 0 : maxItemSeqNo;
									inClone.setItemSequenceNo(maxItemSeqNo + 1);
									maxSeqItemNoOfInput.put(inClone.getBusinessLogicId(), inClone.getItemSequenceNo());

									lstInputRealInsert.add(inClone);
								} catch (CloneNotSupportedException e) {
									throw new SystemException("", "");
								}

							}
						}
					}
				}

				Long sequenceInputBeanItem = businessDesignRepository.getSequencesInputBean(lstInputRealInsert.size() - 1);
				Long startSequence = sequenceInputBeanItem - (lstInputRealInsert.size() - 1);
				for (InputBean obj : lstInputRealInsert) {
					mapInputBeanId.put(obj.getInputBeanId(), startSequence);
					obj.setInputBeanId(startSequence.toString());
					if (obj.getFlagUsingTempId() != null && obj.getFlagUsingTempId()) {
						Long parentMultipartFileObjectId = mapInputBeanId.get(obj.getParentInputBeanId());
						if (parentMultipartFileObjectId != null) {
							obj.setParentInputBeanId(parentMultipartFileObjectId.toString());
						}
					}
					startSequence++;
				}
				businessDesignRepository.registerInputBean(lstInputRealInsert);
				// BEGIN TASK : update validate input bean
				modifyValidationCheckDetails(lstInputRealInsert, screenItems, accountProfile);
				// END TASK
			}
			
//			if (FunctionCommon.isNotEmpty(inputBeanInsert)) {
//				// clone input bean to set for each blogic
//				List<InputBean> lstInputRealInsert = new ArrayList<InputBean>();
//				for (BusinessDesign blogic : businessDesigns) {
//					if (BusinessDesignConst.RETURN_TYPE_SCREEN.equals(blogic.getReturnType())) {
//						for (InputBean in : inputBeanInsert) {
//							try {
//								InputBean inClone = (InputBean) in.clone();
//								inClone.setBusinessLogicId(blogic.getBusinessLogicId());
//								Integer maxItemSeqNo = maxSeqItemNoOfInput.get(inClone.getBusinessLogicId());
//								// DungNN - if null need to
//								maxItemSeqNo = maxItemSeqNo == null ? 0 : maxItemSeqNo;
//								inClone.setItemSequenceNo(maxItemSeqNo + 1);
//								maxSeqItemNoOfInput.put(inClone.getBusinessLogicId(), inClone.getItemSequenceNo());
//
//								lstInputRealInsert.add(inClone);
//							} catch (CloneNotSupportedException e) {
//								throw new SystemException("", "");
//							}
//
//						}
//					}
//				}
//
//				Long sequenceInputBeanItem = businessDesignRepository.getSequencesInputBean(lstInputRealInsert.size() - 1);
//				Long startSequence = sequenceInputBeanItem - (lstInputRealInsert.size() - 1);
//				for (InputBean obj : lstInputRealInsert) {
//					mapInputBeanId.put(obj.getInputBeanId(), startSequence);
//					obj.setInputBeanId(startSequence.toString());
//					if (obj.getFlagUsingTempId() != null && obj.getFlagUsingTempId()) {
//						Long parentMultipartFileObjectId = mapInputBeanId.get(obj.getParentInputBeanId());
//						if (parentMultipartFileObjectId != null) {
//							obj.setParentInputBeanId(parentMultipartFileObjectId.toString());
//						}
//					}
//					startSequence++;
//				}
//				businessDesignRepository.registerInputBean(lstInputRealInsert);
//			}
			if (FunctionCommon.isNotEmpty(inputBeanInsertForParametters)) {
				// clone input bean to set for each blogic
				List<InputBean> lstInputRealInsert = new ArrayList<InputBean>();
				for (BusinessDesign blogic : businessDesigns) {
					if (BusinessDesignConst.RETURN_TYPE_INITIALSCREEN.equals(blogic.getReturnType())) {
						for (InputBean in : inputBeanInsertForParametters) {
							try {
								InputBean inClone = (InputBean) in.clone();
								inClone.setBusinessLogicId(blogic.getBusinessLogicId());
								Integer maxItemSeqNo = maxSeqItemNoOfInput.get(inClone.getBusinessLogicId());
								// DungNN - if null need to
								maxItemSeqNo = maxItemSeqNo == null ? 0 : maxItemSeqNo;
								inClone.setItemSequenceNo(maxItemSeqNo + 1);
								maxSeqItemNoOfInput.put(inClone.getBusinessLogicId(), inClone.getItemSequenceNo());

								lstInputRealInsert.add(inClone);
							} catch (CloneNotSupportedException e) {
								throw new SystemException("", "");
							}

						}
					}
				}

				Long sequenceInputBeanItem = businessDesignRepository.getSequencesInputBean(lstInputRealInsert.size() - 1);
				Long startSequence = sequenceInputBeanItem - (lstInputRealInsert.size() - 1);
				for (InputBean obj : lstInputRealInsert) {
					mapInputBeanId.put(obj.getInputBeanId(), startSequence);
					obj.setInputBeanId(startSequence.toString());
					if (obj.getFlagUsingTempId() != null && obj.getFlagUsingTempId()) {
						Long parentMultipartFileObjectId = mapInputBeanId.get(obj.getParentInputBeanId());
						if (parentMultipartFileObjectId != null) {
							obj.setParentInputBeanId(parentMultipartFileObjectId.toString());
						}
					}
					startSequence++;
				}
				businessDesignRepository.registerInputBean(lstInputRealInsert);
			}
			// update object_mapping_type and object_mapping_id of screen area
			for (ScreenArea area : areaUpdateMappingObjectType) {
				area.setObjectMappingType(ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_INPUT_BEAN);
				area.setObjectMappingId(mapInputBeanId.get(area.getObjectMappingId().toString()));

				screenAreaRepository.updateObjectMappingTypeOfScreenArea(area);
			}
			// update object_mapping_type and object_mapping_id of screen area to null when all screen item were deleted
			for (ScreenArea area : areaUpdateMappingObjectTypeNull) {
				area.setObjectMappingType(null);
				area.setObjectMappingId(null);

				screenAreaRepository.updateObjectMappingTypeOfScreenArea(area);
			}

			checkProblemChangeDesign(screenDesign, screenItems, languageId, projectId, accountId);
		}
	}

	private MessageDesign getCommonMessage(List<MessageDesign> messages, MessageDesign message) {
		MessageDesign result = null;

		for (MessageDesign item : messages) {
			if (item == null)
				continue;

			if (!StringUtils.isEmpty(item.getMessageString()) && message != null && !StringUtils.isEmpty(message.getMessageString()) && item.getMessageString().equals(message.getMessageString())) {
				result = item;
				break;
			}
		}
		return result;
	}

	private String getProblemName(String screenDesignCode) {
		String result = MessageUtils.getMessage("sc.screendesign.0329") + " " + screenDesignCode;
		return result;
	}

	public void checkProblemChangeDesign(ScreenDesign screenDesign, ScreenItem[] screenItems, Long languageId, Long projectId, Long accountId) {
		Timestamp tt = FunctionCommon.getCurrentTime();
		List<ProblemList> problemLists = new ArrayList<ProblemList>();

		boolean isValid = true;
		for (ScreenItem item : screenItems) {

			if (item.getLogicalDataType() == null || item.getLogicalDataType().equals(DbDomainConst.LogicDataType.LABEL))
				continue;

			ProblemList problemList = new ProblemList();
			problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_DESIGN);
			problemList.setFromResourceId(screenDesign.getScreenId());
			problemList.setFromResourceType(DbDomainConst.FromResourceType.SCREEN_DESIGN);
			problemList.setResourceId(screenDesign.getScreenId());
			problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
			problemList.setModuleId(screenDesign.getModuleId());
			problemList.setUrlId(DbDomainConst.ResourceURL.URL_SCREEN_DESIGN);
			problemList.setProblemType(DbDomainConst.ProblemType.MISS_SETTING);
			problemList.setProjectId(projectId);
			problemList.setCreatedBy(accountId);
			problemList.setCreatedDate(tt);

			switch (item.getLogicalDataType()) {
			case 0:
				if (StringUtils.isEmpty(item.getItemCode()) || (item.getMessageDesign() == null || (StringUtils.isEmpty(item.getMessageDesign().getMessageCode()) || StringUtils.isEmpty(item.getMessageDesign().getMessageString())))) {
					isValid = false;
				} else {
					if (!StringUtils.isEmpty(item.getItemCode()) && (item.getMessageDesign() == null || (StringUtils.isEmpty(item.getMessageDesign().getMessageCode()) && StringUtils.isEmpty(item.getMessageDesign().getMessageString())))) {
						problemList.setProblemName(item.getItemCode() + " " + MessageUtils.getMessage("sc.screendesign.0325") + " " + screenDesign.getScreenCode());
						problemLists.add(problemList);
					}

					if (item.getSqlDesign() == null || item.getSqlDesign().getSqlDesignId() == null) {
						problemList.setProblemName(item.getItemCode() + " " + MessageUtils.getMessage("sc.screendesign.0326") + " " + screenDesign.getScreenCode());
						problemLists.add(problemList);
					}

				}

				break;
			case 5:
			case 6:
			case 7:
				if (StringUtils.isEmpty(item.getItemCode()) && item.getMessageDesign() == null && (StringUtils.isEmpty(item.getMessageDesign().getMessageCode()) && StringUtils.isEmpty(item.getMessageDesign().getMessageString()))) {

					isValid = false;
					if (item.getDataSourceType() == null) {
						if (item.getLogicalDataType().equals(DbDomainConst.LogicDataType.CHECKBOX) && item.getPhysicalDataType() != null && !item.getPhysicalDataType().equals(DbDomainConst.BaseType.BOOLEAN_BASETYPE)) {
							problemList.setProblemName(MessageUtils.getMessage("sc.screendesign.0331") + " " + screenDesign.getScreenCode());
							problemLists.add(problemList);
						}
					}
				} else {
					if (item.getDataSourceType() == null) {
						if (item.getLogicalDataType().equals(DbDomainConst.LogicDataType.CHECKBOX) && item.getPhysicalDataType() != null && !item.getPhysicalDataType().equals(DbDomainConst.BaseType.BOOLEAN_BASETYPE)) {
							problemList.setProblemName(item.getItemCode() + " " + MessageUtils.getMessage("sc.screendesign.0330") + " " + screenDesign.getScreenCode());
							problemLists.add(problemList);
						}
					}

					if (!StringUtils.isEmpty(item.getItemCode()) && (item.getMessageDesign() == null || (StringUtils.isEmpty(item.getMessageDesign().getMessageCode()) && StringUtils.isEmpty(item.getMessageDesign().getMessageString())))) {
						problemList.setProblemName(item.getItemCode() + " " + MessageUtils.getMessage("sc.screendesign.0332") + " " + screenDesign.getScreenCode());
						problemLists.add(problemList);
					}
				}

				break;
			case 13:
			case 22:
			case 11:
				if (item.getMessageDesign() != null && StringUtils.isEmpty(item.getMessageDesign().getMessageCode()) && StringUtils.isEmpty(item.getMessageDesign().getMessageString())) {
					isValid = false;
				} else {
					if (item.getScreenAction() == null || item.getScreenAction().getNavigateToBlogicId() == null) {
						problemList.setProblemName(item.getItemCode() + " " + MessageUtils.getMessage("sc.screendesign.0516") + " " + screenDesign.getScreenCode());
						problemLists.add(problemList);
					}
				}

				break;
			default:

				if (StringUtils.isEmpty(item.getItemCode()) && (item.getMessageDesign() == null || (StringUtils.isEmpty(item.getMessageDesign().getMessageCode()) && StringUtils.isEmpty(item.getMessageDesign().getMessageString())))) {
					isValid = false;
				} else {
					if (!StringUtils.isEmpty(item.getItemCode()) && (item.getMessageDesign() == null || (StringUtils.isEmpty(item.getMessageDesign().getMessageCode()) && StringUtils.isEmpty(item.getMessageDesign().getMessageString())))) {
						problemList.setProblemName(item.getItemCode() + " " + MessageUtils.getMessage("sc.screendesign.0332") + " " + screenDesign.getScreenCode());
						problemLists.add(problemList);
					}
				}
				break;
			}
		}

		if (!isValid) {
			ProblemList problemList = new ProblemList();
			problemList.setResourceType(DbDomainConst.ResourceType.SCREEN_DESIGN);
			problemList.setResourceId(screenDesign.getScreenId());
			problemList.setAutofixFlg(DbDomainConst.AutoFix.DISABLE);
			problemList.setModuleId(screenDesign.getModuleId());
			problemList.setUrlId(DbDomainConst.ResourceURL.URL_SCREEN_DESIGN);
			problemList.setProblemType(DbDomainConst.ProblemType.MISS_SETTING);

			problemList.setProblemName(getProblemName(screenDesign.getScreenCode()));
			problemList.setFromResourceId(screenDesign.getScreenId());
			problemList.setFromResourceType(DbDomainConst.FromResourceType.SCREEN_DESIGN);

			problemList.setProjectId(projectId);
			problemList.setCreatedBy(accountId);
			problemList.setCreatedDate(tt);
			problemLists.add(problemList);

		}

		if (problemLists.size() > 0) {
			problemListRepository.multiRegisterProblem(problemLists);
		}
	}

	/**
	 * 
	 * @param resouceType
	 * @param tableDesign
	 * @return
	 */
	private String getNameProblem(int parameterType, ScreenParameter screenParameter, String screenTo, String screenFrom) {
		switch (parameterType) {
		case DbDomainConst.ParameterType.UPDATE:
			return MessageUtils.getMessage(ScreenDesignConst.ParameterStatus.UPDATE_PARAMETER, screenParameter.getScreenParamName(), screenTo, screenFrom);
		case DbDomainConst.ParameterType.INSERT:
			return MessageUtils.getMessage(ScreenDesignConst.ParameterStatus.INSERT_PARAMETER, screenParameter.getScreenParamName(), screenTo, screenFrom);
		case DbDomainConst.ParameterType.DELETE:
			return MessageUtils.getMessage(ScreenDesignConst.ParameterStatus.DELETE_PARAMETER, screenParameter.getScreenParamName(), screenTo, screenFrom);
		default:
			return "Nothing";
		}

	}

	public static void addItemToArea(ScreenArea[] areas, ScreenGroupItem[] groups, ScreenItem[] items) {
		int startElementIndex = 0;
		int totalElement = 0;
		int itemSeqNo = 0;
		for (int i = 0; i < areas.length; i++) {

			// if area is not has element then return
			if (areas[i] == null || areas[i].getTotalElement() == null || areas[i].getTotalElement() == 0)
				continue;

			// get total element
			totalElement = areas[i].getTotalElement();

			areas[i].setItems(new ArrayList<ScreenItem>());

			for (int j = startElementIndex; j < items.length; j++) {
				if (totalElement == 0) {
					itemSeqNo = 0;
					break;
				}

				items[j].setItemSeqNo(itemSeqNo);
				areas[i].getItems().add(items[j]);

				startElementIndex++;
				totalElement--;
				itemSeqNo++;
			}

		}
	}

	public static void addGroupArea(ScreenArea[] areas, ScreenGroupItem[] groups) {

		int startGroupIndex = 0;
		int totalGroup = 0;
		int groupSeqNo = 0;

		for (int i = 0; i < areas.length; i++) {
			if (areas[i] == null || areas[i].getAreaType() == -1 || areas[i].getAreaType() == 2 || areas[i].getAreaType() == 3 || areas[i].getTotalGroup() == null || areas[i].getTotalGroup() == 0)
				continue;

			totalGroup = areas[i].getTotalGroup();

			areas[i].setGroups(new ArrayList<ScreenGroupItem>());

			for (int j = startGroupIndex; j < groups.length; j++) {
				if (totalGroup == 0) {
					groupSeqNo = 0;
					break;
				}

				groups[j].setGroupSeqNo(groupSeqNo);
				areas[i].getGroups().add(groups[j]);

				startGroupIndex++;
				totalGroup--;
				groupSeqNo++;
			}

		}

	}

	public static void addItemToGroup(ScreenArea[] areas) {

		for (int i = 0; i < areas.length; i++) {
			if (areas[i] == null || areas[i].getAreaType() == -1 || areas[i].getAreaType() == 2 || areas[i].getAreaType() == 3 || areas[i].getTotalGroup() == null || areas[i].getTotalGroup() == 0)
				continue;

			int startElementIndex = 0;
			int totalElement = 0;

			for (int j = 0; j < areas[i].getGroups().size(); j++) {

				totalElement = areas[i].getGroups().get(j).getTotalElement();

				areas[i].getGroups().get(j).setItems(new ArrayList<ScreenItem>());

				for (int k = startElementIndex; k < areas[i].getItems().size(); k++) {
					if (totalElement == 0)
						break;

					areas[i].getGroups().get(j).getItems().add(areas[i].getItems().get(k));

					startElementIndex++;
					totalElement--;
				}
			}
		}
	}

	@Override
	public ScreenDesign getScreenInfoById(Long screenId) {
		return screendesignRepository.getScreenInfoById(screenId);
	}

	@Override
	public List<ScreenDesign> register(ScreenDesignRegister screenDesignRegister, boolean fromTransition, Long languageId, Long projectId, Long accountId) {
		ResultMessages resultMessages = ResultMessages.error();
		/*
		 * Map<String, Object> sqlParamCode = new HashMap<String, Object>(); sqlParamCode.put("screenCode", screenDesignRegister.getScreenCode()); sqlParamCode.put("projectId", screenDesignRegister.getProjectId()); Integer countScreenDesignCode = screendesignRepository.countScreenDesignByScreenCode(sqlParamCode); Map<String, Object> sqlParamName = new HashMap<String, Object>(); sqlParamName.put("screenName", screenDesignRegister.getScreenName()); sqlParamName.put("projectId", screenDesignRegister.getProjectId()); Integer countScreenDesignName = screendesignRepository.countScreenDesignByScreenName(sqlParamName); if (null != countScreenDesignCode && countScreenDesignCode != 0 && null != countScreenDesignName && countScreenDesignName == 0) { resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage("sc.screendesign.0007")); } else if (null != countScreenDesignCode && countScreenDesignCode == 0 && null != countScreenDesignName && countScreenDesignName != 0) { resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage("sc.screendesign.0005")); } else if (null != countScreenDesignCode && countScreenDesignCode != 0 && null != countScreenDesignName && countScreenDesignName != 0) { resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage("sc.screendesign.0007")); resultMessages.add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage("sc.screendesign.0005")); }
		 */

		// check screen name and screen code duplicated
		Map<String, Object> sqlParamCode = new HashMap<String, Object>();
		sqlParamCode.put("screenCode", screenDesignRegister.getScreenCode());
		sqlParamCode.put("screenName", screenDesignRegister.getScreenName());
		sqlParamCode.put("projectId", screenDesignRegister.getProjectId());
		sqlParamCode.put("languageId", languageId);

		Long totalCount = screendesignRepository.countNameCodeByProjectId(sqlParamCode);
		if (TerasolunaQPValidationConst.DUPLICATED_NAME.equals(totalCount)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005)));
		} else if (TerasolunaQPValidationConst.DUPLICATED_CODE.equals(totalCount)) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007)));
		} else if (TerasolunaQPValidationConst.BOTH_ARE_DUPLICATED.equals(totalCount)) {
			Object[] arrParam = { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005), MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007) };
			throw new BusinessException(ResultMessages.error().add(ScreenDesignMessageConst.ERR_SCREENDESIGN_0175, arrParam));
		} /*
		 * else if (TerasolunaQPValidationConst.DUPLICATED_URL_CODE.equals(totalCount)) { throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0028))); } --> DungNN 20161019 don't validate dublicate
		 */

		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		} else {
			ScreenDesignDefault screenDesignDefault = new ScreenDesignDefault();

			LanguageDesign lang = languageDesignService.getLanguageDesignById(languageId, projectId);
			screenDesignDefault.setLanguageDesign(lang);
			Module module = moduleService.validateModule(screenDesignRegister.getModuleId(), accountId, projectId);
			screenDesignDefault.setModuleName(module.getModuleName());
			screenDesignDefault.setModuleCode(module.getModuleCode());
			screenDesignDefault.toScreenRegister(screenDesignRegister);
			screenDesignDefault.setFunctionDesignId(screenDesignRegister.getFunctionDesignId());
			screenDesignDefault.setProject(screenDesignRegister.getProject());
			screenDesignDefault.setxCoordinate(screenDesignRegister.getxCoordinate());
			screenDesignDefault.setyCoordinate(screenDesignRegister.getyCoordinate());
			CommonModel commonModel = new CommonModel();
			commonModel.setWorkingProjectId(projectId);
			commonModel.setWorkingLanguageId(languageId);
			commonModel.setCreatedBy(accountId);
			commonModel.setCreatedDate(FunctionCommon.getCurrentTime());
			commonModel.setUpdatedBy(accountId);
			commonModel.setUpdatedDate(FunctionCommon.getCurrentTime());
			screenDesignDefault.setCommonModel(commonModel);
//			if(fromTransition){
//				screenDesignDefault.setScreenCode(null);
//			}

			screenDesignDefault.setCreatedBy(screenDesignRegister.getProjectId());
			screenDesignDefault.setCreatedDate(FunctionCommon.getCurrentTime());
			// add suffix for name code
			screenDesignDefault.setSuffix(GenerateUniqueKey.generateRandomInteger());

			generateScreenService.generateDefaultScreen(screenDesignDefault, fromTransition);
			// Generate business logic
			if (ScreenDesignConst.DesignMode.DESIGN.equals(screenDesignRegister.getDesignMode())) {
				if (Boolean.TRUE.equals(screenDesignRegister.getIsCopy())) {
					if (!CollectionUtils.isEmpty(screenDesignDefault.getLstScreenDesign())) {
						List<Long> lstScreenIds = new ArrayList<Long>();
						for (ScreenDesign screenDesign : screenDesignDefault.getLstScreenDesign()) {
							lstScreenIds.add(screenDesign.getScreenId());
						}
						graphicDatabaseDesignService.generateBlogicFromScreen(screenDesignRegister.getModuleId(), lstScreenIds, screenDesignDefault.getLanguageDesign(), accountId, projectId);
					}
				} else {
					screenDesignDefault.setIsGenDefault(Boolean.FALSE);
					generateScreenService.generateDefaultBusinesslogic(screenDesignDefault);
				}
				// Update design mode of screen
				List<Long> listOfId = new ArrayList<Long>();
				for (ScreenDesign screenDesign : screenDesignDefault.getLstScreenDesign()) {
					listOfId.add(screenDesign.getScreenId());
				}
				screendesignRepository.updateDesignMode(listOfId, ScreenDesignConst.DesignMode.DESIGN);
			}
			return screenDesignDefault.getLstScreenDesign();
		}
	}

	@Override
	public void insertGenerateScreen(List<ScreenDesign> listScreenDesign) {
		screenDesignRepository.insertScreenDesign(listScreenDesign);
	}

	@Override
	public ScreenDesign getScreenDesignById(Long screenId, Long languageId, Long projectId) {

		LanguageDesign lang = new LanguageDesign();
		lang.setLanguageId(languageId);

		ScreenDesign screenDesign = screendesignRepository.getScreenDesignById(screenId, lang, projectId);
		if (screenDesign == null) {
			return null;
		}

		Module module = moduleRepository.findById(screenDesign.getModuleId());
		ScreenForm[] screenForms = screendesignRepository.getScreenFormByScreenId(screenId);
		ScreenArea[] screenAreas = screendesignRepository.getScreenAreaByScreenId(screenId, lang, projectId);
		ScreenGroupItem[] screenGroup = screendesignRepository.getScreenGroupItemByScreenId(screenId);
		ScreenItem[] screenItems = screendesignRepository.getScreenItemByScreenId(screenId, lang, projectId);
		ScreenItemCodelist[] screenItemCodeList = screendesignRepository.getScreenItemCodelistByScreenId(screenId);
		ScreenParameter[] arrScreenParameter = screendesignRepository.getScreenParameterByScreenId(screenId);
		List<ScreenItem> screenHiddenItems = screendesignRepository.getScreenHiddenItemByScreenId(screenId, lang, projectId);
		List<DomainDatatypeCodelist> lstDomainCodeList = domainDatatypeCodelistRepository.findAllByScreenId(screenId);
		List<CodeListDetail> lstCodelistDetail = codeListDetailRepository.findAllByScreenId(screenId);
		List<ScreenItemEvent> screenItemEvents = screendesignRepository.findScreenItemEventByScreenId(screenId);
		List<ScreenItemEventMapping> screenItemEventMappings = screendesignRepository.findScreenItemEventMappingByScreenId(screenId);
		List<ScreenAreaEvent> screenAreaEvents = screendesignRepository.findScreenAreaEventByScreenId(screenId);
		List<ScreenFormTabs> screenFormTabs = screendesignRepository.findScreenFormTabsByScreenId(screenId);
		List<ScreenItemAutocompleteInput> screenItemAutocompleteInputs = screenDesignRepository.findAutocompleteInputByScreenId(screenId);
		
		List<ScreenAreaSortMapping> screenAreaSortMappings = screendesignRepository.findScreenAreaSortByScreenId(screenId);
		
		for (int i = 0; i < screenItemEvents.size(); i++) {
			List<ScreenItemEventMapping> beanMapping = new ArrayList<ScreenItemEventMapping>();

			for (int j = 0; j < screenItemEventMappings.size(); j++) {
				if (screenItemEvents.get(i).getScreenItemEventId().equals(screenItemEventMappings.get(j).getScreenItemEventId())) {
					beanMapping.add(screenItemEventMappings.get(j));
				}
			}
			screenItemEvents.get(i).setScreenItemEventMappings(beanMapping);
		}

		// set total element for screen area
		for (int i = 0; i < screenAreas.length; i++) {
			screenAreas[i].setTotalGroup(getTotalGroupByScreenAreaId(screenAreas[i].getScreenAreaId(), screenGroup));

			// set hidden item for screen area
			List<ScreenItem> lstHiddenItemOfArea = new ArrayList<ScreenItem>();
			for (ScreenItem screenItem : screenHiddenItems) {
				if (screenItem.getScreenAreaId().equals(screenAreas[i].getScreenAreaId())) {
					lstHiddenItemOfArea.add(screenItem);
				}
			}
			String parameters = "";
			for (ScreenItem objTemp : lstHiddenItemOfArea) {
				String messageCode = "";
				String messageString = "";
				if (objTemp.getMessageDesign() != null) {
					messageCode = (objTemp.getMessageDesign().getMessageCode() != null) ? objTemp.getMessageDesign().getMessageCode() : "";
					messageString = (objTemp.getMessageDesign().getMessageString() != null) ? objTemp.getMessageDesign().getMessageString() : "";
				}

				if (lstHiddenItemOfArea.indexOf(objTemp) == lstHiddenItemOfArea.size() - 1) {
					parameters += objTemp.getItemCode() + ScreenDesignConst.ITEM_SPLIT + messageCode + ScreenDesignConst.ITEM_SPLIT + messageString + ScreenDesignConst.ITEM_SPLIT + objTemp.getScreenItemId() + ScreenDesignConst.ITEM_SPLIT + objTemp.getPhysicalDataType();
				} else {
					parameters += objTemp.getItemCode() + ScreenDesignConst.ITEM_SPLIT + messageCode + ScreenDesignConst.ITEM_SPLIT + messageString + ScreenDesignConst.ITEM_SPLIT + objTemp.getScreenItemId() + ScreenDesignConst.ITEM_SPLIT + objTemp.getPhysicalDataType() + ScreenDesignConst.ROW_SPLIT;
				}
			}
			screenAreas[i].setFormElementHidden(parameters);
			ScreenAreaEventOutput output = new ScreenAreaEventOutput();
			List<ScreenAreaEventRequire> screenAreaEventRequires = new ArrayList<ScreenAreaEventRequire>();
			screenAreas[i].setScreenAreaEvents(new ArrayList<ScreenAreaEvent>());
			
			//TungHT
			List<ScreenAreaSortMapping> areaSorts = new ArrayList<ScreenAreaSortMapping>(); 
			if(screenAreaSortMappings != null && screenAreaSortMappings.size() > 0) {
				for (ScreenAreaSortMapping areaSort : screenAreaSortMappings) {
					if(areaSort.getScreenAreaCode() != null) {
						String[] arr = areaSort.getScreenAreaCode().split("\\.");
						if(arr.length > 1) {
							if(arr[1] != "" && screenAreas[i].getAreaCode() != null && arr[1].equals(screenAreas[i].getAreaCode())) {
								areaSorts.add(areaSort);
							}
						} else {
							if(arr[0] != "" && screenAreas[i].getAreaCode() != null && arr[0].equals(screenAreas[i].getAreaCode())) {
								areaSorts.add(areaSort);
							}
						}
					}
				}
			}
			ScreenAreaSortOutput areaSortOutput = new ScreenAreaSortOutput();
			if(screenAreas[i].getEnableSort() != null) {
				areaSortOutput.setEnableSort(screenAreas[i].getEnableSort());
			}
			if(screenAreas[i].getSqlColumnId() != null) {
				areaSortOutput.setSqlId(String.valueOf(screenAreas[i].getSqlColumnId()));
			}
			if(screenAreas[i].getSqlColumnCode() != null) {
				areaSortOutput.setSqlCode(String.valueOf(screenAreas[i].getSqlColumnCode()));
			}
			
			areaSortOutput.setAreaSorts(areaSorts);
			JsonFactory json = new JsonFactory();
			ObjectMapper mapper = new ObjectMapper(json);
			TypeReference<ScreenAreaSortOutput> typeRef = new TypeReference<ScreenAreaSortOutput>() {
			};

			String strAreaSortJson = "";
			try {
				strAreaSortJson = mapper.writeValueAsString(areaSortOutput);

			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			screenAreas[i].setScreenAreaSortValue(strAreaSortJson);
			
			for (ScreenAreaEvent event : screenAreaEvents) {

				if (!event.getScreenAreaId().equals(screenAreas[i].getScreenAreaId()))
					continue;

				screenAreas[i].getScreenAreaEvents().add(event);

				ScreenAreaEventRequire eventRequire = new ScreenAreaEventRequire();
				eventRequire.setIfRequired(event.getIfRequire().split(","));
				eventRequire.setThenMustRequired(event.getThenMustRequire().split(","));
				screenAreaEventRequires.add(eventRequire);
			}
			if (screenAreaEventRequires.size() > 0) {
				ScreenAreaEventRequire[] events = new ScreenAreaEventRequire[screenAreaEventRequires.size()];
				events = screenAreaEventRequires.toArray(events);
				output.setRequireConstraints(events);

				JsonFactory eventjson = new JsonFactory();
				ObjectMapper eventmapper = new ObjectMapper(eventjson);
				TypeReference<ScreenAreaEventOutput> eventtypeRef = new TypeReference<ScreenAreaEventOutput>() {
				};

				String strJson = "";
				try {
					strJson = eventmapper.writeValueAsString(output);

				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

				screenAreas[i].setAreaEventValue(strJson);
			}
		}
		// set total element for screen group id
		for (int i = 0; i < screenGroup.length; i++) {
			screenGroup[i].setTotalElement(getTotalElementByScreenGroupId(screenGroup[i].getGroupItemId(), screenItems));
			ScreenGroupItem group = screenGroup[i];
			int startItemIndex = 0;
			int totalElement = screenGroup[i].getTotalElement();

			for (int j = 0; j < screenItems.length; j++) {
				ScreenItem item = screenItems[j];

				if (item.getGroupItemId() == null || !item.getGroupItemId().equals(group.getGroupItemId()))
					continue;

				if (totalElement == 0)
					break;

				if (startItemIndex == 0) {
					screenGroup[i].setElementStart(item.getItemSeqNo());

				}

				if (totalElement == 1) {
					screenGroup[i].setElementEnd(item.getItemSeqNo());

				}
				startItemIndex++;
				totalElement--;
			}
			startItemIndex = 0;

		}

		for (int i = 0; i < screenItems.length; i++) {

			ScreenItem item = screenItems[i];

			for (ScreenArea area : screenAreas) {
				if (item.getScreenAreaId() != null && area.getScreenAreaId() != null && item.getScreenAreaId().equals(area.getScreenAreaId())) {
					screenItems[i].setScreenArea(area);
				}
			}

			if (DbDomainConst.LogicDataType.LABEL_DYNAMIC.equals(screenItems[i].getLogicalDataType())) {
				// set style fomat
				switch (screenItems[i].getPhysicalDataType()) {
				case 5:
				case 4:
				case 15:
					screenItems[i].setFieldStyle(ScreenDesignConst.FieldStyle.NUMERIC);
					break;
				case 10:
				case 11:
					screenItems[i].setFieldStyle(ScreenDesignConst.FieldStyle.DATE);
					break;
				case 12:
					screenItems[i].setFieldStyle(ScreenDesignConst.FieldStyle.DATETIME);
					break;
				default:
					screenItems[i].setFieldStyle(ScreenDesignConst.FieldStyle.TEXT);
					break;
				}
			}

			// set codelist for screenitem
			screenItems[i].setListScreenItemCodelists(new ArrayList<ScreenItemCodelist>());
			if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_SYSTEM.equals(screenItems[i].getCodelistType())) {
				if (lstCodelistDetail != null && lstCodelistDetail.size() > 0) {
					for (CodeListDetail codeListDetail : lstCodelistDetail) {
						if(screenItems[i] != null && screenItems[i].getCodelistId() != null) {
							if (screenItems[i].getCodelistId().equals(codeListDetail.getCodeListId())) {
								ScreenItemCodelist objTemp = new ScreenItemCodelist();
								if (codeListDetail.getName() != null) {
									objTemp.setCodelistName(HtmlUtils.htmlEscape(codeListDetail.getName()));
								} else {
									objTemp.setCodelistName(HtmlUtils.htmlEscape(codeListDetail.getValue()));
								}
								objTemp.setCodelistVal(HtmlUtils.htmlEscape(codeListDetail.getValue()));
								screenItems[i].getListScreenItemCodelists().add(objTemp);
							}
						}
					}
				}
				/*
				 * if (lstCodelistDetail != null && lstCodelistDetail.size() > 0) { screenItems[i].setCodelistText(lstCodelistDetail.get(0).getCodelistName()); }
				 */
			} else if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_TABLE.equals(screenItems[i].getCodelistType())) {
				if (screenItems[i].getDomainTblMappingItemId() != null) {
					if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_CUSTOM.toString().equals(screenItems[i].getDomainCodelistType())) {
						// case of codelist_type of domain_table_mapping_items
						// is custom
						if (lstDomainCodeList != null && lstDomainCodeList.size() > 0) {
							for (DomainDatatypeCodelist domainCodelistDetail : lstDomainCodeList) {
								if (screenItems[i].getDomainTblMappingItemId().equals(domainCodelistDetail.getDomainDatatypeItemId())) {
									ScreenItemCodelist objTemp = new ScreenItemCodelist();
									objTemp.setCodelistName(domainCodelistDetail.getCodelistName());
									objTemp.setCodelistVal(domainCodelistDetail.getCodelistValue());
									screenItems[i].getListScreenItemCodelists().add(objTemp);
								}
							}
						}
					} else if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_SYSTEM.toString().equals(screenItems[i].getDomainCodelistType())) {
						// case of codelist_type of domain_table_mapping_items
						// is system
						for (CodeListDetail codeListDetail : lstCodelistDetail) {
							if (screenItems[i].getDomainCodelistId().equals(String.valueOf(codeListDetail.getCodeListId()))) {
								ScreenItemCodelist objTemp = new ScreenItemCodelist();
								objTemp.setCodelistName(codeListDetail.getName());
								objTemp.setCodelistVal(codeListDetail.getValue());
								screenItems[i].getListScreenItemCodelists().add(objTemp);
							}
						}
					}
				}
			}
			if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_CUSTOM.equals(screenItems[i].getCodelistType())) {
				if (screenItemCodeList != null) {
					for (int j = 0; j < screenItemCodeList.length; j++) {
						if (screenItems[i].getScreenItemId().equals(screenItemCodeList[j].getScreenItemId())) {
							screenItems[i].getListScreenItemCodelists().add(screenItemCodeList[j]);
						}
					}
				}
			}
			if (screenItems[i].getScreenActionId() != null) {
				// get
				ScreenAction screenAction = screenActionRepository.findById(screenItems[i].getScreenActionId(), languageId);
				
				if (screenAction != null) {
					ScreenDesign screen = screendesignRepository.findScreenDesignByDestinationBlogic(screenAction.getScreenActionId());
					if(screen != null) {
						screenAction.setToScreenCodeByNavigateBlogic(screen.getScreenCode());
						screenAction.setToModuleCodeByNavigateBlogic(screen.getModuleCode());
						screenAction.setToScreenIdByNavigateBlogic(screen.getScreenId());
						screenAction.setToModuleIdByNavigateBlogic(screen.getModuleId());
						screenAction.setToTemplateTypeByNavigateBlogic(screen.getTemplateType());
					}
					
					List<ScreenActionParam> lstScreenActionParam = screenActionParamRepository.findAllActionParamByScreenActionId(screenItems[i].getScreenActionId(), projectId);
					screenAction.setListScreenParameters(lstScreenActionParam);
				}
				screenItems[i].setScreenAction(screenAction);
			}

			List<ScreenItemEvent> events = new ArrayList<ScreenItemEvent>();
			// event
			for (ScreenItemEvent event : screenItemEvents) {
				if (screenItems[i].getScreenItemId().equals(event.getScreenItemId())) {
					events.add(event);
				}
			}

			screenItems[i].setScreenItemAutocompleteInputs(new ArrayList<ScreenItemAutocompleteInput>());
			for (ScreenItemAutocompleteInput input : screenItemAutocompleteInputs) {
				if (input.getScreenItemId().equals(screenItems[i].getScreenItemId())) {
					screenItems[i].getScreenItemAutocompleteInputs().add(input);
				}
			}

			screenItems[i].setScreenItemEvents(events);
			screenItems[i].setValue(getJsonScreenItem(screenItems[i]));
			screenItems[i].setElement(getElement(screenItems[i], screenDesign.getScreenPatternType(), module.getModuleCode()));
		}

		List<ScreenFormTabGroup> screenFormTabGroups = new ArrayList<ScreenFormTabGroup>();

		if (screenFormTabs.size() > 0) {
			List<String> groups = new ArrayList<String>();
			groups.add(screenFormTabs.get(0).getTabCode() + "-" + screenFormTabs.get(0).getScreenFormId());

			for (int i = 1; i < screenFormTabs.size(); i++) {
				if (groups.indexOf(screenFormTabs.get(i).getTabCode() + "-" + screenFormTabs.get(i).getScreenFormId()) == -1) {
					groups.add(screenFormTabs.get(i).getTabCode() + "-" + screenFormTabs.get(i).getScreenFormId());
				}
			}

			for (int i = 0; i < groups.size(); i++) {
				ScreenFormTabGroup groupTab = new ScreenFormTabGroup();
				String[] tabKey = groups.get(i).split("-");

				if (tabKey.length == 2) {
					ScreenFormTabGroup group = new ScreenFormTabGroup();

					group.setTabCode(tabKey[0]);
					group.setScreenFormId(Long.parseLong(tabKey[1]));
					group.setScreenFormTabs(new ArrayList<ScreenFormTabs>());

					for (int j = 0; j < screenFormTabs.size(); j++) {
						if (screenFormTabs.get(j).getScreenFormId().equals(group.getScreenFormId()) && screenFormTabs.get(j).getTabCode().equals(group.getTabCode())) {

							group.setTabDirection(screenFormTabs.get(j).getTabDirection());
							group.getScreenFormTabs().add(screenFormTabs.get(j));
						}
					}
					screenFormTabGroups.add(group);
				}

			}
		}

		for (int i = 0; i < screenForms.length; i++) {

			// add group tab
			screenForms[i].setScreenFormTabGroups(new ArrayList<ScreenFormTabGroup>());

			for (ScreenFormTabGroup group : screenFormTabGroups) {
				if (group.getScreenFormId().equals(screenForms[i].getScreenFormId())) {
					String formCode = screenForms[i].getFormCode() + "-" + group.getTabCode();
					formCode = formCode.replace(" ", "");
					// setting template
					String startHtml = "<div id=\"" + formCode + "\" style='width: 100%; float:left;' class='area-tab'>";
					String endHtml = "";
					if (group.getTabDirection().equals(1)) {
						startHtml += "<div class=\"menu-tab\" style=\"float: left; width: 20%; margin: 0px; padding: 0px; margin-left: 4px;\"><ul id=\"" + formCode + "\"-tab\" class=\"nav nav-tabs tabs-left\">";
					} else if (group.getTabDirection().equals(0)) {
						startHtml += "<ul style=\"margin-left: 4px; margin-right: 4px;\" id=\"" + formCode + "-tab\" class=\"nav nav-tabs\">";
					} else {
						startHtml += "<div style=\"margin-left: 4px; margin-right: 4px;\" class=\"panel-group-accordion\" id=\"" + formCode + "-tab\">";
					}

					if (group.getTabDirection().equals(1) || group.getTabDirection().equals(0)) {
						for (int j = 0; j < group.getScreenFormTabs().size(); j++) {
							ScreenFormTabs tab = group.getScreenFormTabs().get(j);
							String tabTitle = "<div class=\"form-inline\">" + "	<span style=\"cursor: move;\" class=\".ui-state-dark qp-glyphicon glyphicon glyphicon-sort sortable srcgenTableSort\"></span>" + " <span style=\"cursor: pointer;\" onclick=\"openTabSetting(this)\" class=\".ui-state-dark qp-glyphicon glyphicon glyphicon-cog\"></span>&nbsp;" + tab.getTabTitle() + "</div>";

							if (j == 0) {
								startHtml += "<li class=\"active\"><a data-toggle='tab' href='#" + formCode + "tab-" + j + "'>" + tabTitle + "</a></li>";
							} else {
								startHtml += "<li><a data-toggle='tab' href='#" + formCode + "tab-" + j + "'>" + tab.getTabTitle() + "</a></li>";
							}
						}
					}

					if (group.getTabDirection().equals(1)) {
						startHtml += "</ul></div><div class=\"contain-tab-content\" style=\"float: left; width: 79%;margin: 0px; padding: 0px;\"><div id=\"" + formCode + "-tab-content\" style=\"border: 1px solid #ddd;\" class=\"tab-content\">";
						endHtml = "</div></div></div>";
					} else if (group.getTabDirection().equals(0)) {
						startHtml += "</ul><div style=\"margin-left: 4px; margin-right: 4px; height: auto;\" id=\"" + formCode + "-tab-content\" class=\"tab-content\">";
						endHtml = "</div></div>";
					} else {
						endHtml = "</div></div>";
					}

					group.setStartHtml(startHtml);
					group.setEndHtml(endHtml);
					screenForms[i].getScreenFormTabGroups().add(group);
				}
			}

			// set value
			FormTab formTab = new FormTab();

			if (screenFormTabs.size() > 0) {
				List<Tab> tabs = new ArrayList<Tab>();
				List<ScreenFormTabs> formTabs = new ArrayList<ScreenFormTabs>();

				for (int j = 0; j < screenFormTabs.size(); j++) {
					if (screenForms[i].getScreenFormId().equals(screenFormTabs.get(j).getScreenFormId())) {
						Tab tab = new Tab();
						tab.setTabCode(screenFormTabs.get(j).getTabCode());
						tab.setTabDirection(screenFormTabs.get(j).getTabDirection() + "");
						tab.setTitle(screenFormTabs.get(j).getTabTitle());
						tab.setAreas(screenFormTabs.get(j).getAreas());
						tabs.add(tab);

						formTabs.add(screenFormTabs.get(j));
					}
				}

				ScreenFormTabs[] arrFormTabs = new ScreenFormTabs[formTabs.size()];
				arrFormTabs = formTabs.toArray(arrFormTabs);
				screenForms[i].setScreenFormTabs(arrFormTabs);

				Tab[] arrTabs = new Tab[tabs.size()];
				arrTabs = tabs.toArray(arrTabs);

				formTab.setTabs(arrTabs);
				JsonFactory json = new JsonFactory();
				ObjectMapper mapper = new ObjectMapper(json);
				TypeReference<FormTab> typeRef = new TypeReference<FormTab>() {
				};

				String strJson;
				try {
					strJson = mapper.writeValueAsString(formTab);
					screenForms[i].setTabValue(strJson);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					screenForms[i].setTabValue("");
				}
			}
		}

		screenDesign.setScreenForms(screenForms);
		screenDesign.setScreenAreas(screenAreas);
		screenDesign.setScreenGroupItems(screenGroup);
		screenDesign.setScreenItems(screenItems);
		screenDesign.setArrScreenParameter(arrScreenParameter);
		String screenInputParameters = new String();
		for (int i = 0; i < arrScreenParameter.length; i++) {
			screenInputParameters += arrScreenParameter[i].getScreenParamName() + ScreenDesignConst.ITEM_SPLIT + arrScreenParameter[i].getScreenParamCode() + ScreenDesignConst.ITEM_SPLIT + arrScreenParameter[i].getDataType() + ScreenDesignConst.ITEM_SPLIT + arrScreenParameter[i].getScreenParamId() + ScreenDesignConst.ROW_SPLIT;
		}
		if (arrScreenParameter.length > 0) {
			screenInputParameters = screenInputParameters.substring(0, screenInputParameters.length() - 1);
		}
		screenDesign.setScreenParameters(screenInputParameters);

		return screenDesign;
	}

	private String getElement(ScreenItem screenItem, int screenType, String moduleCode) {

		JsonFactory json = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(json);
		TypeReference<ScreenDesignOutput> typeRef = new TypeReference<ScreenDesignOutput>() {
		};

		ScreenDesignOutput item = new ScreenDesignOutput();
		String jsonString = FunctionCommon.getStringJson(screenItem.getValue());
		try {
			item = mapper.readValue(jsonString, typeRef);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (screenItem.getButtonType() != null) {
			item.setButtonType(screenItem.getButtonType());
		}

		if (StringUtils.isEmpty(item.getDatatype())) {
			return "";
		}
	/*	BusinessDesign blogic = new BusinessDesign();
		if(item.getNavigateToBlogic() != "" && item.getNavigateToBlogic() != null) {
			blogic = businessDesignRepository.findBusinessLogicInformation(Long.parseLong(item.getNavigateToBlogic()), workingLanguageId, workingProjectId);
		}
		*/
		screenItem.setWidth(item.getWidth());
		screenItem.setWidthUnit(item.getWidthunit());
		
		String maxLength = "";
		if(item.getMaxlength() != null && item.getMaxlength() != "" && Integer.parseInt(item.getMaxlength()) > 0) {
			maxLength = "maxlength=\"" + item.getMaxlength() + "\"";
		}
		String[] arrStyle = item.getStyle().split(";");
		List<String> arrMargin = new ArrayList<String>();
		List<String> arrNotMargin = new ArrayList<String>();
		for(int i = 0; i < arrStyle.length; i++) {
			if(arrStyle[i].contains("margin")) {
				arrMargin.add(arrStyle[i]);
			} else {
				arrNotMargin.add(arrStyle[i]);
			}
		}
		String margin = "";
		for(int i = 0; i < arrMargin.size(); i++) {
			margin += arrMargin.get(i).trim() + ";";
		}
		String notMargin = "";
		for(int i = 0; i < arrNotMargin.size(); i++) {
			notMargin += arrNotMargin.get(i).trim() + ";";
		}
		
		String styleLink = "";
		if(screenItem.getStyle() != null && screenItem.getStyle() != "" && screenItem.getStyle().length() > 0) {
			styleLink = screenItem.getStyle().substring(0, screenItem.getStyle().length()-1);
		}
		String hoverStyle = "";
		if(screenItem.getHoverStyle() != null && screenItem.getHoverStyle() != "" && screenItem.getHoverStyle().length() > 0) {
			hoverStyle = screenItem.getHoverStyle().substring(0, screenItem.getHoverStyle().length()-1);
		}
		
		String strHoverStyle = "";
		if(screenItem.getHoverStyle() != "") {
			strHoverStyle = "onmouseover=\"this.setAttribute('style','"+hoverStyle+"')\" onmouseout=\"this.setAttribute('style','"+styleLink+"')\"";
		}
		
		String tabIndex = "";
		if(screenItem.getTabIndex() != null && String.valueOf(screenItem.getTabIndex()) != "" ) {
			tabIndex = "tabIndex=\""+screenItem.getTabIndex()+"\" ";
		}
		
		//Width of section :
		String widthForSection = "";
		if(screenItem.getScreenArea() != null && screenItem.getScreenArea().getAreaType() != null && DbDomainConst.AreaType.FREE_ELEMENT.equals(screenItem.getScreenArea().getAreaType())) {
			if(screenItem.getLogicalDataType() != null && screenItem.getLogicalDataType() != 2 && screenItem.getLogicalDataType() != 3 && screenItem.getLogicalDataType() != 4 && screenItem.getLogicalDataType() != 8 && screenItem.getLogicalDataType() != 9 && screenItem.getLogicalDataType() != 14) {
				widthForSection = "100%";
			} else {
				widthForSection = "width: "+item.getWidth()+""+item.getWidthunit()+" ";
			}
		}
		
		String styleTdOfLayout = "";
		if(screenItem.getScreenArea() != null && screenItem.getScreenArea().getInputStyle() != null ) {
			styleTdOfLayout = screenItem.getScreenArea().getInputStyle();
		}
		
		String element = "";
		int dataType = Integer.parseInt(item.getDatatype());
		switch (dataType) {
		case 0:
			element = "<div class=\"input-group\" style=\" "+widthForSection+"; "+margin+"\" ><input style=\"" + notMargin + "\" type=\"text\" class=\"form-control\" "+tabIndex+" ";
			element += " name=\"" + item.getColumnname() + "\" "+maxLength+" optionvalue=\"optionValue\" optionlabel=\"optionLabel\" selectsqlid=\"\" emptylabel=\"\" onselectevent=\"\" onchangeevent=\"\" onremoveevent=\"\" mustmatch=\"true\" minlength=\"0\" arg01=\"\" arg02=\"\" arg03=\"\" arg4=\"\" arg05=\"\" arg06=\"\" arg07=\"\" arg08=\"\" arg09=\"\" arg10=\"\" arg11=\"\" arg12=\"\" arg13=\"\" arg14=\"\" arg15=\"\" arg16=\"\" arg17=\"\" arg18=\"\" arg19=\"\" arg20=\"\" placeholder=\"\" autocomplete=\"off\">" + "	<input type=\"hidden\" value=\"\">" + "	<span class=\"input-group-addon dropdown-toggle\" data-dropdown=\"dropdown\">" + "		<span class=\"caret\"></span> " + "	</span>" + "</div>";

			break;
		case 1:
			if(item.getEnablePassword() != null && item.getEnablePassword().equals("1")) {
				element = "<input style=\""+styleTdOfLayout+";"+widthForSection+"; " + item.getStyle() + "\" value=\"" + item.getDefaultvalue() + "\" type=\"password\" class=\"form-control qp-input-text\" name=\"" + item.getColumnname() + "\" "+maxLength+" "+tabIndex+"/>";
			} else {
				element = "<input style=\""+styleTdOfLayout+";"+widthForSection+"; " + item.getStyle() + "\" value=\"" + item.getDefaultvalue() + "\" type=\"text\" class=\"form-control qp-input-text\" name=\"" + item.getColumnname() + "\" "+maxLength+" "+tabIndex+"/>";
			}
			
			break;
		case 15:
		case 16:
		case 18:
			element = "<input style=\""+styleTdOfLayout+";"+widthForSection+"; " + item.getStyle() + "\" value=\"" + item.getDefaultvalue() + "\" type=\"text\" class=\"form-control qp-input-text\" name=\"" + item.getColumnname() + "\" "+maxLength+" "+tabIndex+"/>";
			break;
		case 21:
			element += "<Label style=\""+styleTdOfLayout+";" + item.getStyle() + "\" name=\"" + item.getColumnname() + "\" "+maxLength+" >" + item.getColumnname() + "</label>";
			break;
		case 2:
			if (screenType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")) {
					element += "<div style=\"width: 100%; " + item.getStyle() + "\">" + "<input style=\"" + item.getStyle() + "\" "+tabIndex+" type=\"text\" class=\"form-control qp-input-from qp-input-integer pull-left\"  name=\"" + item.getColumnname() + "\" "+maxLength+" />" + "" + "<div class=\"qp-separator-from-to\">~</div>" + "<input style=\"" + item.getStyle() + "\" type=\"text\" class=\"form-control qp-input-to qp-input-integer pull-right\"";
					element += " name=\"" + item.getColumnname() + "\" "+maxLength+" />" + "</div>";
				} else {
					element += "<div style=\"width: 100%; " + item.getStyle() + "\">" + "<input style=\"" + item.getStyle() + "\" "+tabIndex+" type=\"text\" class=\"form-control qp-input-integer pull-left\"  name=\"" + item.getColumnname() + "\" "+maxLength+" />";
				}
				
			} else {
				element = "<input style=\"width:100%; " + item.getStyle() + "\" "+tabIndex+" type=\"text\" value=\"" + item.getDefaultvalue() + "\" class=\"form-control qp-input-integer\" name=\"" + item.getColumnname() + "\" "+maxLength+" />";
			}
			break;
		case 3:
			if (screenType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")) {
					element += "<div style=\"width: 100%; " + item.getStyle() + "\">" + "<input style=\"" + item.getStyle() + "\" "+tabIndex+" type=\"text\" class=\"form-control qp-input-from qp-input-float pull-left\"  name=\"" + item.getColumnname() + "\" "+maxLength+" />" + "" + "<div class=\"qp-separator-from-to\">~</div>" + "<input style=\"" + item.getStyle() + "\" ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" class=\"form-control qp-input-to qp-input-float pull-right\"";
					element += " name=\"" + item.getColumnname() + "\" "+maxLength+" />" +

					"</div>";
				} else {
					element += "<div style=\"width: 100%; " + item.getStyle() + "\">" + "<input style=\"" + item.getStyle() + "\" "+tabIndex+" type=\"text\" class=\"form-control qp-input-float pull-left\"  name=\"" + item.getColumnname() + "\" "+maxLength+" />";
				}
			} else {
				element = "<input style=\"width: "+item.getWidth()+""+item.getWidthunit()+"; " + item.getStyle() + "\" type=\"text\" value=\"" + item.getDefaultvalue() + "\" "+tabIndex+" class=\"form-control qp-input-float\" name=\"" + item.getColumnname() + "\" "+maxLength+" />";
			}
			break;
		case 8:
			if (screenType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("")) {
					element += "<div style=\"width: 100%; " + item.getStyle() + "\">" + "<input style=\"" + item.getStyle() + "\" "+tabIndex+" type=\"text\" class=\"form-control qp-input-from qp-input-currency pull-left\"  name=\"" + item.getColumnname() + "\" "+maxLength+" />" + "" + "<div class=\"qp-separator-from-to\">~</div>" + "<input style=\"" + item.getStyle() + "\" ondblclick=\"openDialogComponentSetting(this)\" type=\"text\" class=\"form-control qp-input-to qp-input-currency pull-right\"";
					element += " name=\"" + item.getColumnname() + "\" "+maxLength+"/>" + "</div>";
				} else {
					element += "<div style=\"width: 100%; " + item.getStyle() + "\">" + "<input style=\"" + item.getStyle() + "\" "+tabIndex+" type=\"text\" class=\"form-control qp-input-currency pull-left\"  name=\"" + item.getColumnname() + "\" "+maxLength+" />";
				}
			} else {
				element = "<input type=\"text\" value=\"" + item.getDefaultvalue() + "\" "+tabIndex+" class=\"form-control qp-input-currency\" name=\"" + item.getColumnname() + "\" "+maxLength+"/>";
			}
			break;
		case 4:
			if (screenType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("") && screenItem.getScreenArea() != null && screenItem.getScreenArea().getAreaType() != null && DbDomainConst.AreaType.LIST_ENTITIES.equals(screenItem.getScreenArea().getAreaType())) {
					element += "<div>" + "<div class=\"input-group date qp-input-from-datepicker pull-left\" style=\""+margin+"\">";
					element += "<input "+tabIndex+" type=\"text\" class=\"form-control qp-input-from\"";
					element += " name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>" + "<div class=\"qp-separator-from-to\">~</div>" + "<div class=\"input-group date qp-input-to-datepicker pull-right\">";
					element += "<input type=\"text\" class=\"form-control\" ";
					element += " name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else if(screenItem.getScreenArea() != null && screenItem.getScreenArea().getAreaType() != null && DbDomainConst.AreaType.LIST_ENTITIES.equals(screenItem.getScreenArea().getAreaType())) {
					element += "<div>" + "<div class=\"input-group date qp-input-datepicker pull-left\" style=\""+margin+"\">";
					element += "<input "+tabIndex+" type=\"text\" class=\"form-control qp-input-from\"";
					element += " name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
				} else {
					element += "<div class=\"input-group date qp-input-datepicker\" style=\""+margin+"\">" + "<span><input type=\"text\" "+tabIndex+" class=\"form-control\" name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\"/></span>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
				}
			} else {
				element += "<div class=\"input-group date qp-input-datepicker\" style=\""+margin+"\">" + "<span><input type=\"text\" "+tabIndex+" class=\"form-control\" name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\"/></span>";
				element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
			}
			break;
		case 14:
			if (screenType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("") && screenItem.getScreenArea() != null && screenItem.getScreenArea().getAreaType() != null && DbDomainConst.AreaType.LIST_ENTITIES.equals(screenItem.getScreenArea().getAreaType())) {
					element += "<div>" + "<div class=\"input-group date qp-input-from-datetimepicker pull-left\" style=\""+margin+"\">";
					element += "<input "+tabIndex+" type=\"text\" class=\"form-control\"";
					element += " name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\" />";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>" + "<div class=\"qp-separator-from-to\">~</div>" + "<div class=\"input-group date qp-input-to-datetimepicker pull-right\">";
					element += "<input  type=\"text\" class=\"form-control\"";
					element += " name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else if(screenItem.getScreenArea() != null && screenItem.getScreenArea().getAreaType() != null && DbDomainConst.AreaType.LIST_ENTITIES.equals(screenItem.getScreenArea().getAreaType())){
					element += "<div>" + "<div class=\"input-group date qp-input-datetimepicker pull-left\" style=\""+margin+"\">";
					element += "<input "+tabIndex+" type=\"text\" class=\"form-control\"";
					element += " name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else {
					element += "<div class=\"input-group date qp-input-datetimepicker\" style=\""+margin+"\">" + "<span><input type=\"text\" "+tabIndex+" class=\"form-control\" name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\"/></span>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
				}
			} else {
				element += "<div class=\"input-group date qp-input-datetimepicker\" style=\""+margin+"\">" + "<span><input type=\"text\" "+tabIndex+" class=\"form-control\" name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\"/></span>";
				element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-calendar\"></span>" + "</span>" + "</div>";
			}
			break;
		case 9:
			if (screenType == 1) {
				if(item.getDisplayFromToOutput().equals("1") || item.getDisplayFromToOutput().equals("") && screenItem.getScreenArea() != null && screenItem.getScreenArea().getAreaType() != null && DbDomainConst.AreaType.LIST_ENTITIES.equals(screenItem.getScreenArea().getAreaType())) {
					element += "<div>" + "<div class=\"input-group date qp-input-from-timepicker pull-left\" style=\""+margin+"\">";
					element += "<input "+tabIndex+" type=\"text\" class=\"form-control\" ";
					element += " name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>" + "<div class=\"qp-separator-from-to\">~</div>" + "<div class=\"input-group date qp-input-to-timepicker pull-right\">";
					element += "<input  type=\"text\" class=\"form-control\"";
					element += " name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else if(screenItem.getScreenArea() != null && screenItem.getScreenArea().getAreaType() != null && DbDomainConst.AreaType.LIST_ENTITIES.equals(screenItem.getScreenArea().getAreaType())) {
					element += "<div>" + "<div class=\"input-group date qp-input-timepicker pull-left\" style=\""+margin+"\">";
					element += "<input "+tabIndex+" type=\"text\" class=\"form-control\" ";
					element += " name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\"/>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>";
					element += "</div>";
				} else {
					element += "<div class=\"input-group date qp-input-timepicker\" style=\""+margin+"\">" + "<span><input type=\"text\" "+tabIndex+" class=\"form-control\" name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\"/></span>";
					element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>";
				}
			} else {
				element += "<div class=\"input-group date qp-input-timepicker\" style=\""+margin+"\">" + "<span><input type=\"text\" "+tabIndex+" class=\"form-control\" name=\"" + item.getColumnname() + "\" "+maxLength+" style=\"" + notMargin + "\"/></span>";
				element += "<span class=\"input-group-addon\">" + "<span class=\"glyphicon glyphicon-time\"></span>" + "</span>" + "</div>";
			}
			break;
		case 5:
			if (item.getMsgvalue() != null && item.getMsgvalue().length() > 0) {
				String[] msgLabelArr = item.getMsglabel().split("�");
				String[] msgValArr = item.getMsgvalue().split("�");

				element += "";
				for (int j = 0; j < msgValArr.length; j++) {
					String checked = "";

					if (msgValArr[j].equals(item.getDefaultvalue())) {
						checked = "checked=\"checked\"";
					}
					element += "<label style=\""+styleTdOfLayout+";" + item.getStyle() + "\"> <input "+checked+" "+tabIndex+" type=\"radio\" class=\"qp-input-radio qp-input-radio-margin\"";
					if (item.getMsglabel() != null && item.getMsglabel().length() != 0 && !msgLabelArr[j].equals("null")) {
						element += " name=\"" + item.getColumnname() + "\"/>" + msgLabelArr[j] + " </label>";
					} else {
						element += " name=\"" + item.getColumnname() + "\"/>" + msgValArr[j] + "</label>";
					}
				}
			} else {
				element += "<label style=\""+styleTdOfLayout+";" + item.getStyle() + "\"> <input "+tabIndex+" type=\"radio\" class=\"qp-input-radio qp-input-radio-margin\"";
				element += " name=\"" + item.getColumnname() + "\"/>" + MessageUtils.getMessage("sc.screendesign.0137") + "1</label>";
				element += "<label style=\""+styleTdOfLayout+";" + item.getStyle() + "\"> <input type=\"radio\" class=\"qp-input-radio qp-input-radio-margin\" ";
				element += " name=\"" + item.getColumnname() + "\"/>" + MessageUtils.getMessage("sc.screendesign.0137") + "2</label>";
			}
			break;
		case 6:
			if ("8".equals(item.getPhysicaldatatype())) {
				element += "<input "+tabIndex+" type=\"checkbox\" class=\"qp-input-checkbox-margin qp-input-checkbox\"";
			} else {
				if (item.getMsgvalue() != null && item.getMsgvalue().length() > 0) {
					String[] msgLabelArr = item.getMsglabel().split("�");
					String[] msgValArr = item.getMsgvalue().split("�");

					element += "";
					for (int j = 0; j < msgValArr.length; j++) {

						String checked = "";

						if (msgValArr[j].equals(item.getDefaultvalue())) {
							checked = "checked=\"checked\"";
						}

						element += "<label style=\""+styleTdOfLayout+";" + item.getStyle() + "\"><input "+checked+" "+tabIndex+" type=\"checkbox\" class=\"qp-input-checkbox-margin qp-input-checkbox\"";
						if (item.getMsglabel() != null && item.getMsglabel().length() != 0 && !msgLabelArr[j].equals("null")) {
							element += " name=\"" + item.getColumnname() + "\"/>" + msgLabelArr[j] + "</label>";
						} else {
							element += " name=\"" + item.getColumnname() + "\"/>" + msgValArr[j] + "</label>";
						}
					}
				} else {
					element += "<label style=\""+styleTdOfLayout+";" + item.getStyle() + "\"><input "+tabIndex+" type=\"checkbox\" class=\"qp-input-checkbox-margin qp-input-checkbox\"";
					element += " name=\"" + item.getColumnname() + "\" />" + MessageUtils.getMessage("sc.screendesign.0137") + "1</label>";
					element += "<label style=\""+styleTdOfLayout+";" + item.getStyle() + "\"><input type=\"checkbox\" class=\"qp-input-checkbox-margin qp-input-checkbox\" ";
					element += " name=\"" + item.getColumnname() + "\" />" + MessageUtils.getMessage("sc.screendesign.0137") + "2</label>";
				}
			}

			break;
		case 7:

			element += "<select "+tabIndex+" name=\"" + item.getColumnname() + "\" class=\"form-control qp-input-select\" style=\"" + item.getStyle() + "\">";
			if (item.getMsgvalue() != null && item.getMsgvalue().length() > 0) {
				String[] msgLabelArr = item.getMsglabel().split("�");
				String[] msgValArr = item.getMsgvalue().split("�");
				if (screenItem.getShowBlankItem() != null && SHOW_BLANK_ITEM.equals(screenItem.getShowBlankItem())) {
					element += "<option value=\"\"></option>";
				}
				for (int j = 0; j < msgValArr.length; j++) {

					String selected = "";

					if (msgValArr[j].equals(item.getDefaultvalue())) {
						selected = "selected=\"selected\"";
					}
					if (item.getMsglabel() != null && item.getMsglabel().length() != 0 && !msgLabelArr[j].equals("null")) {
						element += "<option " + selected + ">" + msgLabelArr[j] + "</option>";
					} else {
						element += "<option " + selected + ">" + msgValArr[j] + "</option>";
					}
				}
			} else {
				element += "<option></option><option>" + MessageUtils.getMessage("sc.screendesign.0137") + "1</option><option>" + MessageUtils.getMessage("sc.screendesign.0137") + "2</option>";
			}
			element += "</select>";
			break;
		case 10:
			element += "<textarea "+tabIndex+" value=\"" + item.getDefaultvalue() + "\" class=\"form-control qp-input-textarea\"class=\"form-control qp-input-textarea\" name=\"" + item.getColumnname() + "1\" maxlength=\"" + item.getMaxlength() + "\" style=\""+styleTdOfLayout+";" + item.getStyle() + "\"></textarea>";
			break;
		case 11:
			if (screenItem.getScreenAction() != null && StringUtils.isNotEmpty(screenItem.getScreenAction().getToScreenCodeByNavigateBlogic())) {
				element += "<a  style=\""+styleLink+"\" "+tabIndex+" href=\"" + screenItem.getScreenAction().getToScreenCodeByNavigateBlogic()+ ".html\" "+strHoverStyle+" >" + item.getLabelText() + "</a>";
			} else {
				element += "<a style=\""+styleLink+"\" "+tabIndex+" href=\"#\" "+strHoverStyle+">" + item.getLabelText() + "</a>";
			}

			break;
		case 22:
			if (screenItem.getScreenAction() != null && StringUtils.isNotEmpty(screenItem.getScreenAction().getToScreenCodeByNavigateBlogic())) {
				element += "<a style=\""+styleLink+"\" "+tabIndex+" href=\"" + screenItem.getScreenAction().getToScreenCodeByNavigateBlogic()+ ".html\" "+strHoverStyle+">" + item.getLabelText() + "</a>";
			} else {
				element += "<a style=\""+styleLink+"\" "+tabIndex+" href=\"#\" "+strHoverStyle+">" + item.getLabelText() + "</a>";
			}

			break;

		case 23:
			if(item.getCustomItemContent() != null) {
				element += "<label class=\"qp-item-custom\" style=\""+styleTdOfLayout+";" + item.getStyle() + "\">" + item.getCustomItemContent() + "</label>";
			}
			break;

		case 12:
			element += "<input "+tabIndex+" class=\"qp-input-file\" type=\"file\" name=\"" + item.getColumnname() + "\" style=\""+styleTdOfLayout+";" + item.getStyle() + "\"/>";
			break;
		case 13:

			String buttonType = "";
			// Default or Save
			if ((item.getButtonType() != null && (ScreenDesignConst.ButtonType.BUTTON_TYPE_DEFAULT.equals(item.getButtonType()) || ScreenDesignConst.ButtonType.BUTTON_TYPE_SAVE.equals(item.getButtonType()))) || item.getButtonType() == null) {
				buttonType = "qp-button qp-button-type";
			}
			// Delete
			else if (item.getButtonType() != null && ScreenDesignConst.ButtonType.BUTTON_TYPE_DELETE.equals(item.getButtonType())) {
				buttonType = "qp-button-type-warning";
			}
			// Client
			else if (item.getButtonType() != null && ScreenDesignConst.ButtonType.BUTTON_TYPE_CLIENT.equals(item.getButtonType())) {
				buttonType = "qp-button qp-button-type-client";
			}

			if (screenItem.getScreenAction() != null && StringUtils.isNotEmpty(screenItem.getScreenAction().getToScreenCodeByNavigateBlogic())) {
				element += "<button "+tabIndex+" style=\""+styleTdOfLayout+";width: "+item.getWidth()+""+item.getWidthunit()+";\" type=\"button\" onclick=\"parent.location.href='" + screenItem.getScreenAction().getToScreenCodeByNavigateBlogic() + ".html'\" class=\"btn qp-button " + buttonType + "\" value=\"\" >" + item.getLabelText() + "</button>";
			} else {
				element += "<input "+tabIndex+" style=\""+styleTdOfLayout+";width: "+item.getWidth()+""+item.getWidthunit()+";\" type=\"button\" onclick=\"parent.location.href='.html'\"  class=\"btn qp-button " + buttonType + "\" value=\"" + item.getLabelText() + "\"/>";
			}
			break;
		case 20:
			String mandatory = "";
			String styleOfLabel = "";
			String styleTH = "";
			if(screenItem.getScreenArea() != null && screenItem.getScreenArea().getHeaderStyle() != null) {
				styleTH = screenItem.getScreenArea().getHeaderStyle();
			}
			if (item.getMandatory() != null && "true".equals(item.getMandatory())) {
				mandatory = "&nbsp;<span class=\"qp-required-field\">(*)</span>";
			}
			if (screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.SINGLE_ENTITY)) {
				Integer no = positionItemInSignleArea(screenItem);
				if(no == 0) {
					styleOfLabel = styleTH;
				} else {
					styleOfLabel = styleTdOfLayout;
				}
				element += "<label style=\" "+styleOfLabel+" ;" + item.getStyle() + "\">" + item.getLabelText() + " " + mandatory + "</label>";
			} else if (screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.LIST_ENTITIES)) {
				Integer no = positionItemInListEntities(screenItem); 
				if(no == 0) {
					styleOfLabel = styleTH;
				} else {
					styleOfLabel = styleTdOfLayout;
				}
				element += "<label style=\""+styleOfLabel+";cursor: pointer; " + item.getStyle() + "\">"+item.getLabelText()+" "+mandatory+"</label>";
			}

			break;
		}

		return element;
	}

	private void setValueScreenAction(List<ScreenAction> scr, List<ScreenAction> dest) {
		for (ScreenAction sa : scr) {
			dest.add(sa);
		}
	}

	private String getJsonScreenItem(ScreenItem screenItem) {

		// fix code
		ScreenDesignOutput screenDesignOutput = new ScreenDesignOutput();
		// screenDesignOutput.setActiontype();
		screenDesignOutput.setAutocompleteid(screenItem.getAutocompleteId() + "");
		screenDesignOutput.setCodelistcode(screenItem.getCodelistId() + "");
		screenDesignOutput.setColspan(screenItem.getColSpan() + "");
		screenDesignOutput.setDatatype(screenItem.getLogicalDataType() + "");
		screenDesignOutput.setPhysicaldatatype(screenItem.getPhysicalDataType() + "");
		// fix code 1
		screenDesignOutput.setGroupitemtype("1");
		screenDesignOutput.setOutputBeanId(screenItem.getOutputBeanId() + "");
		screenDesignOutput.setTabindex(screenItem.getTabIndex() + "");
		screenDesignOutput.setScreenItemId(screenItem.getScreenItemId() + "");
		screenDesignOutput.setStyle(screenItem.getStyle());
		screenDesignOutput.setHoverStyle(screenItem.getHoverStyle());
		screenDesignOutput.setIcon(screenItem.getIcon());
		screenDesignOutput.setShowLabel(screenItem.getShowLabel() + "");
		screenDesignOutput.setAllowAnyInput(screenItem.getAllowAnyInput() + "");
		screenDesignOutput.setEnablePassword(screenItem.getEnablePassword() + "");
		screenDesignOutput.setButtonStyle(screenItem.getButtonType() + "");
		screenDesignOutput.setDisplayFromToOutput(screenItem.getDisplayFromTo() + "");
		screenDesignOutput.setShowBlankItem(screenItem.getShowBlankItem() + "");
		
		if (screenItem.getScreenTransitionId() != null) {
			screenDesignOutput.setScreenTransition(screenItem.getScreenTransitionId() + "");
		}
		
		if (!StringUtils.isEmpty(screenItem.getScreenTransitionName())) {
			screenDesignOutput.setScreenTransitionText(screenItem.getScreenTransitionName());
		}
		
		screenDesignOutput.setScreenDesignIdCodeListId(screenItem.getScreenDesignIdCodeListId());
		screenDesignOutput.setScreenItemIdCodeListId(screenItem.getScreenItemIdCodeListId());
		
		screenDesignOutput.setScreenDesignTextCodeListId(screenItem.getScreenDesignTextCodeListId());
		screenDesignOutput.setScreenItemTextCodeListId(screenItem.getScreenItemTextCodeListId());
		
		screenDesignOutput.setCustomItemContent(screenItem.getCustomItemContent() + "");
		screenDesignOutput.setAreaCustomType(screenItem.getScreenArea().getAreaCustomType() + "");

		if (screenItem.getScreenArea() != null) {
			screenDesignOutput.setInputStyle(screenItem.getScreenArea().getInputStyle());
			screenDesignOutput.setHeaderStyle(screenItem.getScreenArea().getHeaderStyle());
		}

		if (!StringUtils.isEmpty(screenItem.getDefaultValue()))
			screenDesignOutput.setDefaultvalue(screenItem.getDefaultValue());

		if (screenItem.getDataSourceType() != null) {
			screenDesignOutput.setDatasourcetype(screenItem.getDataSourceType() + "");
			if (screenItem.getDataSourceType() == 1) {
				if (screenItem.getSqlDesign() != null) {
					if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(screenItem.getLogicalDataType())) {
						if (screenItem.getAutocompleteDesign() != null) {
							screenDesignOutput.setSqldesignidtext(screenItem.getAutocompleteDesign().getAutocompleteName());
							screenDesignOutput.setSqldesignid(screenItem.getSqlDesign().getSqlDesignId() + "");
						}
					} else {
						screenDesignOutput.setSqldesignidtext(screenItem.getSqlDesign().getSqlDesignName());
						screenDesignOutput.setSqldesignid(screenItem.getSqlDesign().getSqlDesignId() + "");
					}
				}

				if (screenItem.getOptionLabel() != null) {
					screenDesignOutput.setOptionlabel(screenItem.getOptionLabel().getResultId() + "");
					screenDesignOutput.setOptionlabeltext(screenItem.getOptionLabel().getColumnName());
				}

				if (screenItem.getOptionValue() != null) {
					screenDesignOutput.setOptionvalue(screenItem.getOptionValue().getResultId() + "");
					screenDesignOutput.setOptionvaluetext(screenItem.getOptionValue().getColumnName());
				}
			}
		}

		if (screenItem.getLogicalDataType() != null && screenItem.getLogicalDataType() == 20) {
			screenDesignOutput.setColumnname(HtmlUtils.htmlEscape(screenItem.getItemCode()));
		} else {
			screenDesignOutput.setColumnname(HtmlUtils.htmlEscape(screenItem.getItemCode()));
		}

		if (StringUtils.isNotEmpty(screenItem.getItemWidthUnit())) {
			String widthUnit = screenItem.getItemWidthUnit();

			if (widthUnit.indexOf("px") != -1) {
				screenDesignOutput.setWidth(screenItem.getItemWidthUnit().replaceAll("px", ""));
				screenDesignOutput.setWidthunit("px");
			} else if (widthUnit.indexOf("%") != -1) {
				screenDesignOutput.setWidth(screenItem.getItemWidthUnit().replaceAll("%", ""));
				screenDesignOutput.setWidthunit("%");
			} else {
				screenDesignOutput.setWidth(screenItem.getItemWidthUnit());
			}
		} else if (screenItem.getLogicalDataType() != null && !screenItem.getLogicalDataType().equals(DbDomainConst.LogicDataType.BUTTON) && screenItem.getScreenArea() != null && screenItem.getScreenArea().getAreaType() != null && !screenItem.getScreenArea().getAreaType().equals(DbDomainConst.AreaType.SUBMIT_ACTION)) {
			screenDesignOutput.setWidth("100");
			screenDesignOutput.setWidthunit("%");
		}

		if (screenItem.getDomainTblMappingId() != null && screenItem.getDomainTblMappingItemId() != null) {
			screenDesignOutput.setTablecode(screenItem.getDomainTblMappingId() + "");
			screenDesignOutput.setTablecolumncode(screenItem.getDomainTblMappingItemId() + "");
		}

		// validation
		if (screenItem.getScreenItemValidation() != null) {
			screenDesignOutput.setMinvalue(screenItem.getScreenItemValidation().getMinVal());
			screenDesignOutput.setMaxvalue(screenItem.getScreenItemValidation().getMaxVal());

			// fix code
			if (screenItem.getScreenItemValidation().getMandatoryFlg() != null && screenItem.getScreenItemValidation().getMandatoryFlg().equals(1)) {
				screenDesignOutput.setMandatory("true");
			} else {
				screenDesignOutput.setMandatory("false");
			}
			screenDesignOutput.setMaxlength(screenItem.getScreenItemValidation().getMaxlength() + "");
			screenDesignOutput.setFormatcode(screenItem.getScreenItemValidation().getFmtCode());

		}

		// fixed code
		if (screenItem.getLogicalDataType() != null) {
			if (screenItem.getLogicalDataType() == 0 || screenItem.getLogicalDataType() == 5 || screenItem.getLogicalDataType() == 6 || screenItem.getLogicalDataType() == 7) {
				screenDesignOutput.setBaseType(screenItem.getPhysicalDataType() + "");
			}
		}

		if (screenItem.getCodelistType() != null) {
			screenDesignOutput.setLocalCodelist(screenItem.getCodelistType() + "");
			if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_SYSTEM.equals(screenItem.getCodelistType())) {
				screenDesignOutput.setCodelistText(screenItem.getCodelistText());
				screenDesignOutput.setCodelistCode(screenItem.getCodelistId() + "");
			} else if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_CUSTOM.equals(screenItem.getCodelistType())) {
				// fix code
				if (screenItem.getListScreenItemCodelists() != null && screenItem.getListScreenItemCodelists().size() > 0 && screenItem.getListScreenItemCodelists().get(0).getSupportOptionValFlg().equals(0)) {

					screenDesignOutput.setIsSupportOptionValue(ScreenDesignConst.ScreenItemConst.VALUE_TRUE);
				} else {
					screenDesignOutput.setIsSupportOptionValue(ScreenDesignConst.ScreenItemConst.VALUE_FALSE);
				}
			}

			// bind parameter
			if (screenItem.getListScreenItemCodelists() != null && screenItem.getListScreenItemCodelists().size() > 0) {
				String parameters = "";
				String msglabel = "";
				String msgvalue = "";
				for (int i = 0; i < screenItem.getListScreenItemCodelists().size(); i++) {
					String codeListName = "";
					String codeListValue = "";
					if(screenItem.getListScreenItemCodelists().get(i).getCodelistName() != null && screenItem.getListScreenItemCodelists().get(i).getCodelistName() != "null" ) {
						codeListName = screenItem.getListScreenItemCodelists().get(i).getCodelistName();
					}
					if(screenItem.getListScreenItemCodelists().get(i).getCodelistVal() != null && screenItem.getListScreenItemCodelists().get(i).getCodelistVal() != "null" ) {
						codeListValue = screenItem.getListScreenItemCodelists().get(i).getCodelistVal();
					}
					parameters += HtmlUtils.htmlEscape(codeListName + ScreenDesignConst.ITEM_SPLIT + HtmlUtils.htmlEscape(codeListValue + ScreenDesignConst.ROW_SPLIT));

					String label = "";
					if (screenItem.getListScreenItemCodelists() != null && screenItem.getListScreenItemCodelists().size() > 0 && screenItem.getListScreenItemCodelists().get(i) != null && screenItem.getListScreenItemCodelists().get(i).getCodelistName() != null && !screenItem.getListScreenItemCodelists().get(i).getCodelistName().isEmpty()) {
						label = HtmlUtils.htmlEscape(screenItem.getListScreenItemCodelists().get(i).getCodelistName());
					} else {
						label = HtmlUtils.htmlEscape(screenItem.getListScreenItemCodelists().get(i).getCodelistVal());
					}

					msglabel += label + ScreenDesignConst.ROW_SPLIT;
					msgvalue += HtmlUtils.htmlEscape(screenItem.getListScreenItemCodelists().get(i).getCodelistVal()) + ScreenDesignConst.ROW_SPLIT;
				}

				if (screenItem.getListScreenItemCodelists().size() > 0) {
					parameters = parameters.substring(0, parameters.length() - 1);
					msglabel = msglabel.substring(0, msglabel.length() - 1);
					msgvalue = msgvalue.substring(0, msgvalue.length() - 1);
				}
				screenDesignOutput.setParameters(parameters);
				screenDesignOutput.setMsglabel(msglabel);
				screenDesignOutput.setMsgvalue(msgvalue);
			}
		}
		if (screenItem.getAutocompleteDesign() != null) {
			screenDesignOutput.setDialogAutocompleteCode(screenItem.getAutocompleteDesign().getAutocompleteId() + "");
			screenDesignOutput.setDialogAutocompleteText(screenItem.getAutocompleteDesign().getAutocompleteCode());
		}

		if (screenItem.getMessageDesign() != null) {
			screenDesignOutput.setLabel(screenItem.getMessageDesign().getMessageCode());
			screenDesignOutput.setLabelText(HtmlUtils.htmlEscape(screenItem.getMessageDesign().getMessageString()));
			if (screenItem.getLogicalDataType() != null && screenItem.getLogicalDataType() == 11) {
				if (screenItem.getMessageDesign() != null && screenItem.getMessageDesign().getMessageString() != null && screenItem.getMessageDesign().getMessageString().isEmpty()) {
					screenDesignOutput.setLabelText(HtmlUtils.htmlEscape(MessageUtils.getMessage("sc.screendesign.0039")));
				}
			} else if (screenItem.getLogicalDataType() != null && screenItem.getLogicalDataType() == 20 && screenItem.getMessageDesign() != null && screenItem.getMessageDesign().getMessageString() != null && screenItem.getMessageDesign().getMessageString().equals(MessageUtils.getMessage("sc.screendesign.0174"))) {
				// check item is lable
				screenDesignOutput.setIsBlank(ScreenDesignConst.ScreenItemConst.VALUE_TRUE);
			}
			if(screenItem.getMessageDesign() != null && screenItem.getMessageDesign().getMessageLevel() != null) {
				screenDesignOutput.setMessageLevel(String.valueOf(screenItem.getMessageDesign().getMessageLevel()));
				if(screenItem.getMessageDesign().getMessageLevel().equals(DbDomainConst.MessageLevel.PROJECT)) {
					screenDesignOutput.setMessageLevelText(MessageUtils.getMessage("sc.tqp.0011"));
				} else if(screenItem.getMessageDesign().getMessageLevel().equals(DbDomainConst.MessageLevel.MODULE)) {
					screenDesignOutput.setMessageLevelText(MessageUtils.getMessage("sc.generatedocument.0006"));
				} else if(screenItem.getMessageDesign().getMessageLevel().equals(DbDomainConst.MessageLevel.SCREEN)) {
					screenDesignOutput.setMessageLevelText(MessageUtils.getMessage("sc.businesslogicdesign.0159"));
				} else if(screenItem.getMessageDesign().getMessageLevel().equals(DbDomainConst.MessageLevel.BUSINESS_LOGIC)) {
					screenDesignOutput.setMessageLevelText(MessageUtils.getMessage("cl.generation.0007"));
				} else if(screenItem.getMessageDesign().getMessageLevel().equals(DbDomainConst.MessageLevel.MENU_DESIGN)) {
					screenDesignOutput.setMessageLevelText(MessageUtils.getMessage("sc.menudesign.0008"));
				} else if(screenItem.getMessageDesign().getMessageLevel().equals(DbDomainConst.MessageLevel.DESIGN_INFORMATION)) {
					screenDesignOutput.setMessageLevelText(MessageUtils.getMessage("tqp.designinformation"));
				} else {
					screenDesignOutput.setMessageLevelText("");
				}
				
			}
			
		} else {
			if (screenItem.getLogicalDataType() != null && screenItem.getLogicalDataType() == 11) {
				screenDesignOutput.setLabelText(MessageUtils.getMessage("sc.screendesign.0039"));
			}
		}

		if (screenItem.getMessageConfirm() != null) {
			screenDesignOutput.setMessageConfirm(screenItem.getMessageConfirm().getMessageCode());
			screenDesignOutput.setMessageConfirmText(screenItem.getMessageConfirm().getMessageString());
			screenDesignOutput.setMessageConfirmCode(screenItem.getMessageConfirm().getMessageCode());
		}
		screenDesignOutput.setEnableConfirm(screenItem.getEnableConfirm() + "");

		screenDesignOutput.setRowspan(screenItem.getRowSpan() + "");

		// set screen action
		if (screenItem.getScreenAction() != null) {
			ScreenAction screenAction = screenItem.getScreenAction();
			if (screenAction.getToScreenId() != null) {
				screenDesignOutput.setNavigateTo(screenAction.getToScreenId().toString());
				screenDesignOutput.setToScreenCode(screenItem.getScreenAction().getToScreenCode());
			} else {
				screenDesignOutput.setNavigateTo("");
			}

			if (ScreenDesignConst.ScreenActionConst.ACTION_TYPE_SCREEN.equals(screenAction.getActionType())) {
				if (screenAction.getToScreenText() != null) {
					screenDesignOutput.setNavigateToText(screenAction.getToScreenText());
				} else {
					screenDesignOutput.setNavigateToText("");
				}

				if (screenAction.getNavigateToBlogicId() != null) {
					screenDesignOutput.setNavigateToBlogic(screenAction.getNavigateToBlogicId() + "");
				} else {
					screenDesignOutput.setNavigateToBlogic("");
				}

				if (screenAction.getNavigateToBlogicText() != null) {
					screenDesignOutput.setNavigateToBlogicText(screenAction.getNavigateToBlogicText() + "");
				} else {
					screenDesignOutput.setNavigateToBlogicText("");
				}

			} else if (ScreenDesignConst.ScreenActionConst.ACTION_TYPE_BLOGIC.equals(screenAction.getActionType())) {
				if (screenAction.getToBlogicText() != null) {
					screenDesignOutput.setNavigateToText(screenAction.getToBlogicText());
				} else {
					screenDesignOutput.setNavigateToText("");
				}
			}

			screenDesignOutput.setActiontype(screenAction.getActionType() + "");

			if (screenAction.getConnectionMsg() != null) {
				screenDesignOutput.setActionName(screenAction.getConnectionMsg());
			} else {
				screenDesignOutput.setActionName("");
			}
			if (ScreenDesignConst.ScreenActionConst.SUBMIT_METHOD_TYPE_POST == screenAction.getSubmitMethodType()) {
				screenDesignOutput.setIsSubmit(ScreenDesignConst.ScreenActionConst.IS_SUBMIT_TRUE);
			} else {
				screenDesignOutput.setIsSubmit(ScreenDesignConst.ScreenActionConst.IS_SUBMIT_FALSE);
			}

			if (screenAction.getListScreenParameters() != null) {
				// set screen param
				String parameters = "";
				for (int i = 0; i < screenAction.getListScreenParameters().size(); i++) {
					ScreenActionParam param = screenAction.getListScreenParameters().get(i);
					parameters += param.getActionParamCode() + ScreenDesignConst.ITEM_SPLIT + param.getScreenItemCode() + ScreenDesignConst.ITEM_SPLIT + param.getDataType() + ScreenDesignConst.ROW_SPLIT;
				}
				if (screenAction.getListScreenParameters().size() > 0) {
					parameters = parameters.substring(0, parameters.length() - 1);
				}
				screenDesignOutput.setParameters(parameters);
			}
		}

		// mapping event
		if (screenItem.getScreenItemEvents() != null && screenItem.getScreenItemEvents().size() > 0) {
			Event[] events = new Event[screenItem.getScreenItemEvents().size()];

			for (int j = 0; j < screenItem.getScreenItemEvents().size(); j++) {
				ScreenItemEvent event = screenItem.getScreenItemEvents().get(j);
				Event e = new Event();
				if (event.getBlogicId() != null)
					e.setBlogicid(event.getBlogicId().toString());
				if (event.getBusinessLogic() != null)
					e.setBlogicname(event.getBusinessLogic().getBusinessLogicName());
				if (event.getEffectArea() != null)
					e.setEffectarea(event.getEffectArea());
				if (event.getEffectAreaType() != null)
					e.setEffectareatype(event.getEffectAreaType().toString());
				if (event.getEventType() != null)
					e.setEventtype(event.getEventType().toString());

				List<Bean> inputs = new ArrayList<Bean>();
				List<Bean> outputs = new ArrayList<Bean>();

				if (event.getScreenItemEventMappings() != null && event.getScreenItemEventMappings().size() > 0) {

					for (ScreenItemEventMapping bean : event.getScreenItemEventMappings()) {

						if (bean.getBeanType().equals(1)) {
							Bean input = new Bean();
							if (bean.getBeanId() != null)
								input.setBeanid(bean.getBeanId().toString());
							input.setItemcode(bean.getItemCode());
							inputs.add(input);
						} else {
							Bean output = new Bean();
							if (bean.getBeanId() != null)
								output.setBeanid(bean.getBeanId().toString());
							output.setItemcode(bean.getItemCode());
							outputs.add(output);
						}
					}
					Bean[] arrInput = new Bean[0];
					arrInput = inputs.toArray(arrInput);

					Bean[] arrOutput = new Bean[0];
					arrOutput = outputs.toArray(arrOutput);

					e.setInputbeans(arrInput);
					e.setOutputbeans(arrOutput);
				}
				events[j] = e;
			}
			screenDesignOutput.setEvents(events);
		}

		if (screenItem.getLogicalDataType() != null && screenItem.getLogicalDataType().equals(DbDomainConst.LogicDataType.AUTOCOMPLETE) && screenItem.getScreenItemAutocompleteInputs() != null && screenItem.getScreenItemAutocompleteInputs().size() > 0) {

			// set screen param
			String parameters = "";
			for (int i = 0; i < screenItem.getScreenItemAutocompleteInputs().size(); i++) {
				ScreenItemAutocompleteInput param = screenItem.getScreenItemAutocompleteInputs().get(i);
				parameters += param.getInputId() + ScreenDesignConst.ITEM_SPLIT + param.getScreenItemCode() + ScreenDesignConst.ROW_SPLIT;
			}
			if (screenItem.getScreenItemAutocompleteInputs().size() > 0) {
				parameters = parameters.substring(0, parameters.length() - 1);
			}
			screenDesignOutput.setParameters(parameters);
		}

		JsonFactory json = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(json);
		TypeReference<ScreenDesignOutput> typeRef = new TypeReference<ScreenDesignOutput>() {
		};

		String strJson;
		try {
			strJson = mapper.writeValueAsString(screenDesignOutput);
			return strJson;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	private Integer getTotalGroupByScreenAreaId(Long screenAreaId, ScreenGroupItem[] screenGroup) {
		Integer totalElement = 0;

		for (int i = 0; i < screenGroup.length; i++) {
			if (screenGroup[i].getScreenAreaId() != null && screenGroup[i].getScreenAreaId().equals(screenAreaId)) {
				totalElement++;
			}
		}
		return totalElement;
	}

	private Integer getTotalElementByScreenGroupId(Long screenGroupId, ScreenItem[] screenItems) {
		Integer totalElement = 0;

		for (int i = 0; i < screenItems.length; i++) {
			if (screenItems[i].getGroupItemId() != null && screenItems[i].getGroupItemId().equals(screenGroupId)) {
				totalElement++;
			}
		}
		return totalElement;
	}

	@Override
	public List<CodeListDetail> getTableCodeListDetailById(Long columnId) {
		List<CodeListDetail> lstCodelist = new ArrayList<>();
		DomainDatatypeItem domainDatatypeItem = domainDatatypeItemRepository.findOneByDomainDatatypeId(columnId);
		if (domainDatatypeItem != null) {
			if (DbDomainConst.DomainTableMappingItems.CODELIST_TYPE_SYSTEM.equals(domainDatatypeItem.getCodelistType())) {
				// system
				lstCodelist = codeListDetailRepository.getCodeListDetail(domainDatatypeItem.getCodelistId());
			}
			if (DbDomainConst.DomainTableMappingItems.CODELIST_TYPE_CUSTOM.equals(domainDatatypeItem.getCodelistType())) {
				// custom
				List<DomainDatatypeCodelist> lstDomainDatatypeCodelist = domainDatatypeCodelistRepository.findAllByDomainDatatypeItem(columnId);
				for (DomainDatatypeCodelist domainDatatypeCodelist : lstDomainDatatypeCodelist) {
					CodeListDetail objTemp = new CodeListDetail();
					objTemp.setName(domainDatatypeCodelist.getCodelistName());
					objTemp.setValue(domainDatatypeCodelist.getCodelistValue());
					lstCodelist.add(objTemp);
				}
			}
		}
		return lstCodelist;
	}

	@Override
	public List<CodeListDetail> getSystemCodeListDetailById(Long codelistId) {
		return screendesignRepository.getCodeListDetailByCodeListId(codelistId);
	}

	@Override
	public void deleteScreenDesign(ScreenDesign screenDesign, Boolean deleteObjectHasFk, Long accountId, Long languageId, Long projectId) {
		LanguageDesign lang = new LanguageDesign();
		lang.setLanguageId(languageId);
		// Adding check concurrence
		if (DbDomainConst.DesignStatus.FIXED.equals(screenDesign.getDesignStatus())) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, screenDesign.getMessageDesign().getMessageString()));
		}
		
		List<BusinessDesign> bLogicProcessToNavigatorList = businessDesignService.findAllBLogicProcessToNavigatorByScreenId(screenDesign.getScreenId());
		
		// 1. Delete business logic default is init --- request_method is 0
		List<BusinessDesign> businessInitDefault = businessDesignService.findAllBlogicInitByScreenId(screenDesign.getScreenId());
		for (BusinessDesign businessDesign : businessInitDefault) {
			CommonModel com = new CommonModel();
			com.setProjectId(projectId);
			com.setWorkingLanguageId(languageId);
			com.setWorkingProjectId(projectId);
			com.setCreatedBy(accountId);
			
			businessDesignService.deleteBusinessDesignLogic(businessDesign.getBusinessLogicId(), deleteObjectHasFk,null, com);
		}
		// 2. Get all business logic default is process and delete --- request_method is 4
		List<BusinessDesign> businessProcessDefault = businessDesignService.findAllBlogicProcessByScreenId(screenDesign.getScreenId());
		
		for (BusinessDesign businessDesign : businessProcessDefault) {
			CommonModel com = new CommonModel();
			com.setProjectId(projectId);
			com.setWorkingLanguageId(languageId);
			com.setWorkingProjectId(projectId);
			com.setCreatedBy(accountId);
			// Process impact change design
			businessDesignService.processInpactChangeDesign(businessDesign.getBusinessLogicId(), deleteObjectHasFk, bLogicProcessToNavigatorList, com);
			// Update screen id, screen from id is NULL by business logic id
			screendesignRepository.updateInformationByBusinessLogicId(businessDesign.getBusinessLogicId());
			// 3. Update screen item id is NULL from input bean by business logic id
			screendesignRepository.updateInputBeanByBusinessLogicId(businessDesign.getBusinessLogicId());
			// Delete blogic process default
			businessDesignService.deleteBusinessDesignLogic(businessDesign.getBusinessLogicId(), deleteObjectHasFk,null, com);
		}
		
		ScreenItem[] screenItems = screendesignRepository.getScreenItemByScreenId(screenDesign.getScreenId(), lang, projectId);
		for (ScreenItem screenItem : screenItems) {
			// Delete output bean by business logic id
			screendesignRepository.deleteOutputBeanScreenItemMappingByScreenItemId(screenItem.getScreenItemId());
		}
		
		
		List<ScreenDesign> listScreenActionParameter = getAllScreenActionChangeParameter(screenDesign.getScreenId(), languageId);
		if (deleteObjectHasFk) {
			// delete problem
			problemListRepository.deleteByTargetId(DbDomainConst.ResourceType.SCREEN_DESIGN, screenDesign.getScreenId());
			screendesignRepository.deleteByScreenId(screenDesign.getScreenId(), projectId);
		} else {
			if (0 < listScreenActionParameter.size()) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0097));
			} else {
				// delete problem
				problemListRepository.deleteByTargetId(DbDomainConst.ResourceType.SCREEN_DESIGN, screenDesign.getScreenId());
				screendesignRepository.deleteByScreenId(screenDesign.getScreenId(), projectId);
			}
		}
	}

	@Override
	public void deleteScreenActionForConnect(Long screenFromId, List<ScreenTransition> listScreenTransition) {
		try {
			screendesignRepository.updateScreenActionIdByScreenActionIdNotIn(screenFromId, listScreenTransition);
			screendesignRepository.deleteScreenActionParamByScreenActionId(screenFromId, listScreenTransition);
			screendesignRepository.deleteScreenActionByScreenActionIdNotIn(screenFromId, listScreenTransition);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public List<ScreenItem> getAllItemsByScreenId(Long screenId, Long languageId, Long projectId) {
		// Check screenId
		List<ScreenItem> lstScreenItems = new ArrayList<ScreenItem>();
		try {

			// get languageId by countryCode and languageCode
			/*
			 * Language language = LocaleUtils.getDesaultLanguage(); // get LanguageId by languageCode and contryCode String languageCode = LocaleUtils.getDesaultLanguage().getLanguageCode(); String countryCode = LocaleUtils.getDesaultLanguage().getCountryCode(); LanguageDesign param = new LanguageDesign(languageCode, countryCode); LanguageDesign languageDesign = languageDesignService.findByLanguageDesign(param); language.setLanguageId(languageDesign.getLanguageId());
			 */
			LanguageDesign lang = languageDesignService.getLanguageDesignById(languageId, projectId);
			lstScreenItems = screendesignRepository.getAllItemsByScreenId(screenId, lang);
		} catch (BusinessException e) {
			throw new BusinessException(e.getResultMessages());
		}
		return lstScreenItems;
	}

	@Override
	public String getScreenParams(Long screenId) {
		// TODO Auto-generated method stub
		ScreenParameter[] arrScreenParameter = screendesignRepository.getScreenParameterByScreenId(screenId);

		String screenInputParameters = new String();
		for (int i = 0; i < arrScreenParameter.length; i++) {
			screenInputParameters += arrScreenParameter[i].getScreenParamCode() + ScreenDesignConst.ITEM_SPLIT + arrScreenParameter[i].getDataType() + "##?##";
		}
		return HtmlUtils.htmlEscape(screenInputParameters);
	}

	@Override
	public void deleteScreenItem(List<ScreenItem> listScreenItemId) {
		screendesignRepository.deleteScreenItemByScreenItemId(listScreenItemId);
	}

	@Override
	public void updateScreenItem(List<ScreenItem> listScreenItem, Long accountId, Long projectId) {
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		List<ScreenItem> listDelete = new ArrayList<ScreenItem>();
		ResultMessages resultMessages = ResultMessages.error();
		for (int i = 0; i < listScreenItem.size(); i++) {
			ScreenItem item = listScreenItem.get(i);
			if (null != item.getDelete() && item.getDelete() == 1) {
				listDelete.add(item);
				listScreenItem.remove(i);
				i--;
			}
		}

		for (ScreenItem item : listScreenItem) {
			item.setSysDatetime(currentTime);
			item.setUpdatedBy(accountId);
			if(item.getScreenItemValidation() != null){
				item.getScreenItemValidation().setUpdatedBy(accountId);
				item.getScreenItemValidation().setSysDatetime(currentTime);
			}
		}
		try {
			// delete screen_item here
			if (null != listDelete && listDelete.size() > 0) {
				deleteScreenItem(listDelete);
				businessDesignRepository.removeScreenItemMappingByScreenItemId(listDelete);
			}
			List<InputBean> lstInputBean = businessDesignRepository.findInputBeanByListScreenItem(projectId, listScreenItem);
			if (lstInputBean == null) {
				lstInputBean = new ArrayList<InputBean>();
			}
			for (ScreenItem item : listScreenItem) {
				// update message code
				if (item.getMessageDesign() != null && StringUtils.isEmpty(item.getMessageDesign().getMessageCode())) {
					messageDesignService.registerMessageDesign(item.getMessageDesign());
				}
				for (InputBean inputBean : lstInputBean) {
					if (item.getScreenItemId().equals(inputBean.getScreenItemId())) {
						inputBean.setInputBeanCode(item.getItemCode());
						inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
					}
				}
			}
			// update screen_item here
			if (screendesignRepository.updateScreenItem(listScreenItem) <= 0) {
				resultMessages.add(CommonMessageConst.ERR_SYS_0048);
			}
			if (FunctionCommon.isNotEmpty(lstInputBean)) {
				businessDesignRepository.modifyInputBeanWhenModifyScreenItem(lstInputBean);
			}
		} catch (Exception e) {
			resultMessages.add(e.toString());
		}
		if (resultMessages.isNotEmpty()) {
			throw new BusinessException(resultMessages);
		}
	}

	@Override
	public ScreenDesign findById(Long screenId, Long languageId, Long projectId) {
		/*
		 * String languageCode = LocaleUtils.getDesaultLanguage().getLanguageCode(); String countryCode = LocaleUtils.getDesaultLanguage().getCountryCode(); LanguageDesign param = new LanguageDesign(languageCode, countryCode); LanguageDesign languageDesign = languageDesignService.findByLanguageDesign(param);
		 */
		ScreenDesign screenDesign = screendesignRepository.findById(screenId, languageId, projectId);
		if (screenDesign == null) {
			throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(CommonMessageConst.TQP_SCREENLIST)));
		}
		return screenDesign;
	}

	@Override
	public ScreenDesign findScreenById(Long screenId, Long languageId, Long projectId) {
		ScreenDesign screenDesign = screendesignRepository.findById(screenId, languageId, projectId);
		return screenDesign;
	}

	@Override
	public void screenDesignChangeStatus(ScreenDesign screenDesign, Long accountId) {
		// TODO Auto-generated method stub
		Timestamp currentTime = FunctionCommon.getCurrentTime();

		screenDesign.setUpdatedBy(accountId);
		screenDesign.setSysDatetime(currentTime);
		screendesignRepository.screenDesignChangeDesignStatus(screenDesign);
	}

	@Override
	public List<ScreenDesign> findAllByCriteria(ScreenDesignSearchCriteria criteria) {
		return screendesignRepository.findPageBySearchCriteria(criteria, null);
	}

	@Override
	public void saveTransition(List<ScreenDesign> screeDesigns, List<ScreenTransitionBranch> lstBranchs, List<ScreenTransition> connects, List<ScreenTransition> connectUpdates, List<ScreenTransition> connectNews, List<ScreenTransition> lstDelete, List<InfoModuleForScreenDesign> screenInfo,
			Long accountId, Long languageId, Long projectId) {
		Long moduleId = null;
		// Process branch
		List<ScreenTransitionBranch> listBranchUpdate = new ArrayList<ScreenTransitionBranch>();
		List<ScreenTransitionBranch> listBranchDelete = new ArrayList<ScreenTransitionBranch>();
		List<ScreenTransitionBranch> listBranchInsert = new ArrayList<ScreenTransitionBranch>();
		
		for (ScreenTransitionBranch screenTransitionBranch : lstBranchs) {
			if(screenTransitionBranch.getStatus() == 0){
				listBranchDelete.add(screenTransitionBranch);
			}else if(screenTransitionBranch.getStatus() == 2){
				listBranchInsert.add(screenTransitionBranch);
			}else{
				listBranchUpdate.add(screenTransitionBranch);
			}
		}
		
		// Delete
		if(FunctionCommon.isNotEmpty(listBranchDelete)){
			List<ScreenTransitionBranchDetail> deltransitionBranchDetails = new ArrayList<ScreenTransitionBranchDetail>();
			for (ScreenTransitionBranch branch : listBranchDelete) {
				deltransitionBranchDetails.addAll(branch.getObjNavigatorInfoDetail());
			}
			
			// Delete details
			screenTransitionRepository.deleteScreenTransitionBranchDetail(deltransitionBranchDetails);
			
			// Delete master
			screenTransitionRepository.deleteMultiBranch(listBranchDelete);
		}
		
		
		
		// Insert Branch
		List<ScreenTransitionBranchDetail> transitionBranchDetails = new ArrayList<ScreenTransitionBranchDetail>();
		List<ScreenTransitionBranchDetail> transitionBranchDetailsTemp = null;
		for (ScreenTransitionBranch branchInsert : listBranchInsert) {
			moduleId = branchInsert.getModuleId();
			branchInsert.setBranchId(null);
			screenTransitionRepository.createTransitionBranch(branchInsert);
			for (ScreenTransition screenTransition : connectNews) {
				if(screenTransition.getFromScreen() != null && screenTransition.getFromScreen().equals(branchInsert.getBranchIdTemp())){
					screenTransition.setFromScreen(branchInsert.getBranchId());
				}
				if(screenTransition.getToScreen() != null && screenTransition.getToScreen().equals(branchInsert.getBranchIdTemp())){
					screenTransition.setToScreen(branchInsert.getBranchId());
				}
			}
			
			transitionBranchDetailsTemp = branchInsert.getObjNavigatorInfoDetail();
			
			if(FunctionCommon.isNotEmpty(transitionBranchDetailsTemp)){
				transitionBranchDetails.addAll(transitionBranchDetailsTemp);
				for (ScreenTransitionBranchDetail screenTransitionBranchDetail : transitionBranchDetailsTemp) {
					screenTransitionBranchDetail.setBranchId(branchInsert.getBranchId());
				}
			}
		}
		screenTransitionRepository.createMultiTransitionBranchDetail(transitionBranchDetails);
		
		// Update Branch
		List<ScreenTransitionBranchDetail> transitionBranchDetailsUpdate = null;
		List<ScreenTransitionBranchDetail> transitionBranchDetailsInsert = null;
		List<ScreenTransitionBranchDetail> transitionBranchDetailsDelete = null;
		
		List<ScreenTransitionBranchDetail> branchDetailsDB = screenTransitionRepository.findAllTransitionBranchDetailByModuleId(moduleId, projectId);
		List<ScreenTransitionBranchDetail> branchDetailsDBByParent = null;
		
		for (ScreenTransitionBranch update : listBranchUpdate) {
			screenTransitionRepository.updateScreenTransitionBranch(update);
			branchDetailsDBByParent = new ArrayList<ScreenTransitionBranchDetail>();
			
			for (ScreenTransitionBranchDetail screenTransitionBranchDetail : branchDetailsDB) {
				if(screenTransitionBranchDetail.getBranchId().equals(update.getBranchId())){
					branchDetailsDBByParent.add(screenTransitionBranchDetail);
				}
			}
			
			
			transitionBranchDetailsInsert = new ArrayList<ScreenTransitionBranchDetail>();
			transitionBranchDetailsUpdate = new ArrayList<ScreenTransitionBranchDetail>();
			transitionBranchDetailsDelete = new ArrayList<ScreenTransitionBranchDetail>();
			
			List<ScreenTransitionBranchDetail> branchUpdate = update.getObjNavigatorInfoDetail();
			boolean check = false;
			for (ScreenTransitionBranchDetail screenTransitionBranchDetail : branchUpdate) {
				if(screenTransitionBranchDetail.getBranchDetailsId() == ""){
					transitionBranchDetailsInsert.add(screenTransitionBranchDetail);
				}
				
			}
			screenTransitionRepository.createMultiTransitionBranchDetail(transitionBranchDetailsInsert);
			
			for (ScreenTransitionBranchDetail screenTransitionBranchDetail : branchDetailsDBByParent) {
				check = false;
				for (ScreenTransitionBranchDetail brd : branchUpdate) {
					if(brd.getBranchDetailsId().equals(screenTransitionBranchDetail.getBranchDetailsId())){
						transitionBranchDetailsUpdate.add(brd);
						check = true;
						break;
					}
				}
				if(!check){
					transitionBranchDetailsDelete.add(screenTransitionBranchDetail);
				}	
			}
			
			// Update
			screenTransitionRepository.updateScreenTransitionBranchDetail(transitionBranchDetailsUpdate);
			// Delete
			screenTransitionRepository.deleteScreenTransitionBranchDetail(transitionBranchDetailsDelete);
		}
		
		List<ScreenDesign> lstUpdate = new ArrayList<ScreenDesign>();
		List<ScreenDesign> lstInsert = new ArrayList<ScreenDesign>();
		
		for (ScreenDesign screenDesign : screeDesigns) {
			if(screenDesign.getScreenId() == null){
				lstInsert.add(screenDesign);
			}else{
				lstUpdate.add(screenDesign);
			}
		}
		
		// Update position
		if (FunctionCommon.isNotEmpty(lstUpdate) && !updatePositionByScreenId(lstUpdate)) {
			throw new BusinessException(ResultMessages.error().add("err.screendesign.0026"));
		}
		
		// Insert new screen
		ScreenTransition transition = null;
		List<ScreenDesign> screenDesignsUpdateCoordinates = new ArrayList<ScreenDesign>();
		for (ScreenDesign screenDesign : lstInsert) {
			
			ResultMessages resultMessages = this.validateNewScreen(screenDesign);
			if(resultMessages.isNotEmpty()){
				throw new BusinessException(resultMessages);
			}else{
				ScreenDesignRegister screenDesignRegister = beanMapper.map(screenDesign, ScreenDesignRegister.class);
				screenDesignRegister.setModuleTableMappings(new ModuleTableMapping[0]);
				screenDesignRegister.setProjectId(projectId);
				Project p = projectService.getProjectInformation(projectId, accountId);
				screenDesignRegister.setProject(p);
				List<ScreenDesign> lstScreenDesigns = this.register(screenDesignRegister, true, languageId, projectId, accountId);
				
				ScreenDesign sd = null;
				if(FunctionCommon.isNotEmpty(lstScreenDesigns) && lstScreenDesigns.size() > 1){
					Long mainScreenId = null;
					for (ScreenDesign screenDesign2 : lstScreenDesigns) {
						if(screenDesign2.getScreenPatternType() != null && (screenDesign2.getScreenPatternType().equals(new Integer(2)) || screenDesign2.getScreenPatternType().equals(new Integer(4)))){
							sd = screenDesign2;
							mainScreenId = screenDesign2.getScreenId();
							screenDesign2.setxCoordinate(screenDesignRegister.getxCoordinate());
							screenDesign2.setyCoordinate(screenDesignRegister.getyCoordinate());
						}
						
						if(screenDesign2.getConfirmationType() != null && screenDesign2.getConfirmationType().equals(new Integer(2))){
							screenDesign2.setxCoordinate(screenDesignRegister.getxCoordinate());
							screenDesign2.setyCoordinate(screenDesignRegister.getyCoordinate() + 100);
							
							transition = new ScreenTransition();
							transition.setTransitionName("Comfirm " + screenDesignRegister.getScreenName());
							transition.setTransitionCode("comfirm" + screenDesignRegister.getScreenName());
							transition.setFromScreen(mainScreenId.toString());
							transition.setToScreen(screenDesign2.getScreenId().toString());
							transition.setModuleId(screenDesign2.getModuleId());
							transition.setType(2);
							connectNews.add(transition);
						}
						
						if(screenDesign2.getCompletionType() != null && screenDesign2.getCompletionType().equals(new Integer(2))){
							screenDesign2.setxCoordinate(screenDesignRegister.getxCoordinate());
							transition = new ScreenTransition();
							transition.setTransitionName("Complete " + screenDesignRegister.getScreenName());
							transition.setTransitionCode("complete" + screenDesignRegister.getScreenName());
							if(screenDesignRegister.getConfirmationType().equals(new Integer(2))){
								for (ScreenDesign sd1 : lstScreenDesigns) {
									if(sd1.getConfirmationType() != null && sd1.getConfirmationType().equals(new Integer(2))){
										transition.setFromScreen(sd1.getScreenId().toString());
										screenDesign2.setyCoordinate(screenDesignRegister.getyCoordinate() + 200);
									}
								}
							}else{
								screenDesign2.setyCoordinate(screenDesignRegister.getyCoordinate() + 100);
								transition.setFromScreen(mainScreenId.toString());
							}
							transition.setToScreen(screenDesign2.getScreenId().toString());
							transition.setModuleId(screenDesign2.getModuleId());
							transition.setType(2);
							connectNews.add(transition);
						}
					}
				}else if(FunctionCommon.isNotEmpty(lstScreenDesigns) && lstScreenDesigns.size() == 1){
					sd = lstScreenDesigns.get(0);
				}
				
				screenDesignRepository.updateScreenDesignCoordinates(lstScreenDesigns);
				
				if(sd != null){
					for (ScreenDesign scrDesign : screeDesigns) {
						if(scrDesign.getScreenIdTemp() != null && scrDesign.getScreenIdTemp().equals(screenDesign.getScreenIdTemp())){
							scrDesign.setScreenId(sd.getScreenId());
						}
					}
					for (ScreenTransition screenTransition : connectNews) {
						if(screenTransition.getFromScreen().equals(screenDesign.getScreenIdTemp())){
							screenTransition.setFromScreen(sd.getScreenId().toString());
						}
						if(screenTransition.getToScreen() != null && screenTransition.getToScreen().equals(screenDesign.getScreenIdTemp())){
							screenTransition.setToScreen(sd.getScreenId().toString());
						}
					}
				}
			}
			
		}
		
		
		// create transition
		screenTransitionRepository.createMultiTransition(connectNews);
		screenTransitionRepository.updateMultiTransition(connectUpdates);
		screenTransitionRepository.deleteMultiTransition(lstDelete);
	}
	
	private ResultMessages validateNewScreen(ScreenDesign screenDesign){
		
		ResultMessages resultMessages = ResultMessages.error();
		AccountProfile accountProfile = systemService.getDefaultProfile();
		
		// Validate Screen Name
		if(FunctionCommon.equals(screenDesign.getScreenName(), null)){
			resultMessages.add(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005)}, null);
		}else{
			if(screenDesign.getScreenName().length() > accountProfile.getNameMaxLength()){
				resultMessages.add(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005), accountProfile.getNameMinLength(), accountProfile.getNameMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getNamePattern(), screenDesign.getScreenName())){
				resultMessages.add(CommonMessageConst.ERR_SYS_0126, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0005), accountProfile.getNameMask()}, null);
			}
		}
		
		// Validate Screen Code
		if(FunctionCommon.equals(screenDesign.getScreenCode(), null)){
			resultMessages.add(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007)}, null);
		}else{
			if(screenDesign.getScreenCode().length() > accountProfile.getCodeMaxLength()){
				resultMessages.add(CommonMessageConst.ERR_SYS_0064, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007), accountProfile.getCodeMinLength(), accountProfile.getCodeMaxLength()}, null);
			}
			if(!Pattern.matches(accountProfile.getCodePattern(), screenDesign.getScreenCode())){
				resultMessages.add(CommonMessageConst.ERR_SYS_0066, new Object[] { MessageUtils.getMessage(ScreenDesignMessageConst.SC_SCREENDESIGN_0007, accountProfile.getCodeMask())}, null);
			}
		}
		
		// Validate function deisng
		if(FunctionCommon.equals(screenDesign.getFunctionDesignId(), null)){
			resultMessages.add(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.functiondesign.0002")}, null);
		}
		
		// Validate screenPatternType
		if(FunctionCommon.equals(screenDesign.getScreenPatternType(), null)){
			resultMessages.add(CommonMessageConst.ERR_SYS_0025, new Object[] { MessageUtils.getMessage("sc.screendesign.0009")}, null);
		}
		
		return resultMessages;
	}

	private void createNewConnect(ScreenTransition newTransition, List<InfoModuleForScreenDesign> listInfo, LanguageDesign languageDesign, Long accountId,  Long languageId, Long projectId) {
		Long sourceTransition = Long.parseLong(newTransition.getSource());
		Long targetTransition = newTransition.getTarget() == null ? null : Long.parseLong(newTransition.getTarget());
		
		if(targetTransition == null){
			
			ScreenForm screenForm = screendesignRepository.findScreenFormByScreenId(sourceTransition);
			
			ScreenArea area = populateScreenArea(screenForm, -1, MessageUtils.getMessage("sc.screendesign.0307"), 1, "100%", 1, "100%", 1, 1, accountId);
			screenAreaRepository.insertScreenArea(area);
			
			// insert Source and Target to table ScreenAction (purpose: draw connect)
			ScreenAction screenAction = populateScreenAction(1, newTransition.getConnectionMsg(), sourceTransition, targetTransition, newTransition.getNavigateToBlogicId(), newTransition.getSubmitMethodType(), accountId);
			screenActionRepository.insertScreenAction(screenAction);
			
			Integer maxItemSeqNo = screenItemRepository.getMaxSeqNoByScreenAreaId(area.getScreenAreaId());
			if (null == maxItemSeqNo) {
				maxItemSeqNo = -1;
			}
			
			ScreenItemSequence screenItemSequence = populateScreenItemSequence(0, ++maxItemSeqNo, area.getScreenAreaId(), accountId);
			screenItemSequenceRepository.insertScreenItemSequence(screenItemSequence);
			
			MessageDesign messageDesign = poplateMessageDesign(newTransition.getConnectionMsg(), listInfo.get(0), MessageLevel.SCREEN, MESSAGE_TYPE_SCREEN, accountId, languageId);
			messageDesignService.registerMessageDesign(messageDesign);
			
			ScreenItem screenItem = populateScreenItem(maxItemSeqNo, sourceTransition, "sc.screendesign.0039", messageDesign, area.getScreenAreaId(), null, LogicDataType.BUTTON, screenAction.getScreenActionId(), accountId);
			screenItemRepository.insertScreenItem(screenItem);
			
		}else{
			ScreenForm screenForm = screendesignRepository.findScreenFormByScreenId(sourceTransition);
			ScreenDesign source = findById(sourceTransition, languageId, projectId);
			ScreenDesign dest = findById(targetTransition, languageId, projectId);
			
			if (source.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.MODIFY) || source.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.REGISTER)) {
				/*
				 * Language lang = new Language(); lang.setLanguageId(SessionUtils.getCurrentProjectId());
				 */
				LanguageDesign lang = languageDesignService.getLanguageDesignById(languageId, projectId);
				
				ScreenArea[] areas = screendesignRepository.getScreenAreaByScreenId(source.getScreenId(), lang, projectId);
				ScreenArea area = new ScreenArea();
				
				for (int j = 0; j < areas.length; j++) {
					if (areas[j].getAreaType().equals(-1)) {
						area = areas[j];
						break;
					}
				}
				
				if (area.getScreenAreaId() == null) {
					area = populateScreenArea(screenForm, -1, MessageUtils.getMessage("sc.screendesign.0307"), 1, "100%", 1, "100%", 1, 1, accountId);
					screenAreaRepository.insertScreenArea(area);
				}
				
				Integer maxItemSeqNo = screenItemRepository.getMaxSeqNoByScreenAreaId(area.getScreenAreaId());
				if (null == maxItemSeqNo) {
					maxItemSeqNo = -1;
				}
				
				String destpatternType = "";
				
				if (dest.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.SEARCH)) {
					destpatternType = MessageUtils.getMessage("sc.screendesign.0310");
				} else if (dest.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.VIEW)) {
					destpatternType = MessageUtils.getMessage("sc.screendesign.0311");
				} else if (dest.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.REGISTER)) {
					destpatternType = MessageUtils.getMessage("sc.screendesign.0309");
				} else if (dest.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.MODIFY)) {
					destpatternType = MessageUtils.getMessage("sc.screendesign.0312");
				}
				
				// insert Source and Target to table ScreenAction (purpose: draw connect)
				ScreenAction screenAction = populateScreenAction(1, MessageUtils.getMessage("sc.screendesign.0313") + destpatternType, sourceTransition, targetTransition, newTransition.getNavigateToBlogicId(), newTransition.getSubmitMethodType(), accountId);
				screenActionRepository.insertScreenAction(screenAction);
				
				ScreenItemSequence screenItemSequence = populateScreenItemSequence(0, ++maxItemSeqNo, area.getScreenAreaId(), accountId);
				screenItemSequenceRepository.insertScreenItemSequence(screenItemSequence);
				
				String screenName = (dest.getMessageDesign() == null) ? "" : dest.getMessageDesign().getMessageString();
				
				MessageDesign messageDesign = poplateMessageDesign(destpatternType + screenName, listInfo.get(0), MessageLevel.SCREEN, MESSAGE_TYPE_SCREEN, accountId, languageId);
				messageDesignService.registerMessageDesign(messageDesign);
				
				ScreenItem screenItem = populateScreenItem(maxItemSeqNo, sourceTransition, "sc.screendesign.0039", messageDesign, area.getScreenAreaId(), null, LogicDataType.BUTTON, screenAction.getScreenActionId(), accountId);
				screenItemRepository.insertScreenItem(screenItem);
				
			} else if (source.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.SEARCH)) {
				if (dest.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.REGISTER) || dest.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.SEARCH) || dest.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.VIEW)) {
					
					/*
					 * Language lang = new Language(); lang.setLanguageId(SessionUtils.getCurrentProjectId());
					 */
					LanguageDesign lang = languageDesignService.getLanguageDesignById(languageId, projectId);
					ScreenArea[] areas = screendesignRepository.getScreenAreaByScreenId(source.getScreenId(), lang, projectId);
					ScreenArea area = new ScreenArea();
					
					for (int j = 0; j < areas.length; j++) {
						if (areas[j].getAreaType().equals(-1)) {
							area = areas[j];
							break;
						}
					}
					
					if (area.getScreenAreaId() == null) {
						area = populateScreenArea(screenForm, -1, MessageUtils.getMessage("sc.screendesign.0307"), 1, "100%", 1, "100%", 1, 1, accountId);
						screenAreaRepository.insertScreenArea(area);
					}
					
					Integer maxItemSeqNo = screenItemRepository.getMaxSeqNoByScreenAreaId(area.getScreenAreaId());
					if (null == maxItemSeqNo) {
						maxItemSeqNo = -1;
					}
					
					String destpatternType = "";
					
					if (dest.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.SEARCH)) {
						destpatternType = MessageUtils.getMessage("sc.screendesign.0310");
					} else if (dest.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.VIEW)) {
						destpatternType = MessageUtils.getMessage("sc.screendesign.0311");
					} else if (dest.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.REGISTER)) {
						destpatternType = MessageUtils.getMessage("sc.screendesign.0309");
					} else if (dest.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.MODIFY)) {
						destpatternType = MessageUtils.getMessage("sc.screendesign.0312");
					}
					
					// insert Source and Target to table ScreenAction (purpose: draw connect)
					ScreenAction screenAction = populateScreenAction(1, MessageUtils.getMessage("sc.screendesign.0313") + destpatternType, sourceTransition, targetTransition, newTransition.getNavigateToBlogicId(), newTransition.getSubmitMethodType(), accountId);
					screenActionRepository.insertScreenAction(screenAction);
					
					ScreenItemSequence screenItemSequence = populateScreenItemSequence(0, ++maxItemSeqNo, area.getScreenAreaId(), accountId);
					screenItemSequenceRepository.insertScreenItemSequence(screenItemSequence);
					
					String screenName = (dest.getMessageDesign() == null) ? "" : dest.getMessageDesign().getMessageString();
					
					MessageDesign messageDesign = poplateMessageDesign(destpatternType + screenName, listInfo.get(0), MessageLevel.SCREEN, MESSAGE_TYPE_SCREEN, accountId, languageId);
					messageDesignService.registerMessageDesign(messageDesign);
					
					ScreenItem screenItem = populateScreenItem(maxItemSeqNo, sourceTransition, "sc.screendesign.0039", messageDesign, area.getScreenAreaId(), null, LogicDataType.BUTTON, screenAction.getScreenActionId(), accountId);
					screenItemRepository.insertScreenItem(screenItem);
				}
				
			} else if (source.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.VIEW)) {
				if (dest.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.MODIFY) || dest.getScreenPatternType().equals(ScreenDesignConst.ScreenPatternType.SEARCH)) {
					// create new Area of ScreenForm
					ScreenArea screenArea = populateScreenArea(screenForm, 2, MessageUtils.getMessage("sc.screendesign.0307"), 1, "100%", 1, "100%", 1, 1, accountId);
					screenAreaRepository.insertScreenArea(screenArea);
					
					// insert Source and Target to table ScreenAction (purpose: draw connect)
					ScreenAction screenAction = populateScreenAction(1, MessageUtils.getMessage("sc.screendesign.0308"), sourceTransition, targetTransition, newTransition.getNavigateToBlogicId(), newTransition.getSubmitMethodType(), accountId);
					screenActionRepository.insertScreenAction(screenAction);
					
					Integer maxItemSeqNo = screenItemRepository.getMaxSeqNoByScreenAreaId(screenArea.getScreenAreaId());
					if (null == maxItemSeqNo) {
						maxItemSeqNo = -1;
					}
					ScreenItemSequence screenItemSequence = populateScreenItemSequence(0, ++maxItemSeqNo, screenArea.getScreenAreaId(), accountId);
					screenItemSequenceRepository.insertScreenItemSequence(screenItemSequence);
					
					MessageDesign messageDesign = poplateMessageDesign("sc.screendesign.0128", listInfo.get(0), MessageLevel.SCREEN, MESSAGE_TYPE_SCREEN, accountId, languageId);
					messageDesignService.registerMessageDesign(messageDesign);
					
					// insert search screen header item
					ScreenItem screenItem = populateScreenItem(maxItemSeqNo, sourceTransition, "sc.screendesign.0308", messageDesign, screenArea.getScreenAreaId(), null, LogicDataType.BUTTON, screenAction.getScreenActionId(), accountId);
					screenItemRepository.insertScreenItem(screenItem);
				}
			}
		}
	}

	/**
	 * populate new screen action
	 * 
	 * @return the new ScreenAction object
	 */
	private ScreenAction populateScreenAction(Integer actionType, String connectionMsg, Long fromScreenId, Long toScreenId, Long navigateToBlogicId, int submitMethodType, Long accountId) {
		ScreenAction screenAction = new ScreenAction();
		screenAction.setActionType(actionType);
		screenAction.setNavigateToBlogicId(navigateToBlogicId);
		screenAction.setFromScreenId(fromScreenId);
		screenAction.setConnectionMsg(connectionMsg);
		screenAction.setSubmitMethodType(submitMethodType);
		screenAction.setToScreenId(toScreenId);
		screenAction.setCreatedBy(accountId);
		screenAction.setCreatedDate(FunctionCommon.getCurrentTime());
		screenAction.setUpdatedBy(accountId);
		screenAction.setUpdatedDate(FunctionCommon.getCurrentTime());
		return screenAction;
	}

	/**
	 * populate new screen item sequence
	 * 
	 * @return the new ScreenItemSequence object
	 */
	private ScreenItemSequence populateScreenItemSequence(Integer itemGroupType, Integer itemSeqNo, Long screenAreaId, Long accountId) {
		ScreenItemSequence screenItemSequence = new ScreenItemSequence();
		screenItemSequence.setItemGroupType(itemGroupType);
		screenItemSequence.setItemSeqNo(itemSeqNo);
		screenItemSequence.setScreenAreaId(screenAreaId);
		screenItemSequence.setCreatedBy(accountId);
		screenItemSequence.setCreatedDate(FunctionCommon.getCurrentTime());
		screenItemSequence.setUpdatedBy(accountId);
		screenItemSequence.setUpdatedDate(FunctionCommon.getCurrentTime());
		return screenItemSequence;
	}

	/**
	 * populate new screen item
	 * 
	 * @return the new ScreenItem object
	 */
	private ScreenItem populateScreenItem(Integer itemSeqNo, Long screenId, String itemCode, MessageDesign messageDesign, Long screenAreaId, Long groupItemId, Integer logicalDataType, Long screenActionId, Long accountId) {
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
		screenItem.setItemType(ITEM_TYPE_NORMAL);
		screenItem.setCreatedBy(accountId);
		screenItem.setCreatedDate(FunctionCommon.getCurrentTime());
		screenItem.setUpdatedBy(accountId);
		screenItem.setUpdatedDate(FunctionCommon.getCurrentTime());
		return screenItem;
	}

	/**
	 * populate new screen area
	 * 
	 * @return the new ScreenArea object
	 */
	private ScreenArea populateScreenArea(ScreenForm screenForm, Integer areaType, String areaCode, Integer totalElement, String colWidthUnit, Integer totalCol, String tblWidthUnit, Integer tblHeaderRow, Integer alignPositionType, Long accountId) {
		ScreenArea screenArea = new ScreenArea();
		screenArea.setScreenFormId(screenForm.getScreenFormId());
		screenArea.setScreenId(screenForm.getScreenId());
		screenArea.setAreaType(areaType);
		screenArea.setAreaCode(areaCode);
		screenArea.setTotalElement(totalElement * 2);
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
		screenArea.setCreatedDate(FunctionCommon.getCurrentTime());
		screenArea.setUpdatedBy(accountId);
		screenArea.setUpdatedDate(FunctionCommon.getCurrentTime());
		return screenArea;
	}

	/**
	 * populate new message design
	 * 
	 * @return the new MessageDesign object
	 */
	private MessageDesign poplateMessageDesign(String messageString, InfoModuleForScreenDesign inputInfor, Integer messageLevel, String messageType, Long accountId, Long languageId) {
		MessageDesign messageDesign = new MessageDesign();
		messageDesign.setMessageString(messageString);
		messageDesign.setModuleId(inputInfor.getModuleId());
		messageDesign.setModuleCode(inputInfor.getModuleCode());
		messageDesign.setProjectId(inputInfor.getProjectId());
		messageDesign.setModuleName(inputInfor.getModuleName());
		messageDesign.setMessageLevel(messageLevel);
		messageDesign.setMessageType(messageType);
		messageDesign.setGeneratedStatus(0);
		messageDesign.setLanguageId(languageId);
		messageDesign.setCreatedBy(accountId);
		messageDesign.setCreatedDate(FunctionCommon.getCurrentTime());
		messageDesign.setUpdatedBy(accountId);
		messageDesign.setUpdatedDate(FunctionCommon.getCurrentTime());
		return messageDesign;
	}

	@Override
	public List<ScreenDesign> getAllScreenInfoByProjectId(Long projectId, Long languageId) {
		// TODO Auto-generated method stub
		List<ScreenDesign> screenDesigns = screendesignRepository.getAllScreenInfoByProjectId(projectId, languageId);

		if (screenDesigns != null && screenDesigns.size() > 0) {
			Long moduleId = screenDesigns.get(0).getModuleId();
			int i = 0;

			for (ScreenDesign screen : screenDesigns) {
				if (!screen.getModuleId().equals(moduleId)) {
					moduleId = screen.getModuleId();
					i++;
				}
				if (i > 0) {
					screen.setyCoordinate(screen.getyCoordinate() + (i * 500));
				}
			}
		}

		return screenDesigns;
	}

	/**
	 * 
	 */
	@Override
	public List<ScreenDesign> getAllScreenActionChangeParameter(Long screenId, Long languageId) {
		// TODO Auto-generated method stub
		return screendesignRepository.getAllScreenActionChangeParameter(screenId, languageId);
	}

	@Override
	public ScreenDesign getScreenDesignByIdForPreview(Long screenId, Integer genType, Long languageId, Long projectId) {
		// TODO Auto-generated method stub
		LanguageDesign lang = new LanguageDesign();
		lang.setLanguageId(languageId);

		ScreenDesign screenDesign = screendesignRepository.getScreenDesignById(screenId, lang, projectId);
		if (screenDesign == null) {
			return null;
		}
		Module module = moduleRepository.findById(screenDesign.getModuleId());
		ScreenForm[] screenForms = screendesignRepository.getScreenFormByScreenId(screenId);
		ScreenArea[] screenAreas = screendesignRepository.getScreenAreaByScreenIdForGenCode(screenId, lang, projectId);
		ScreenGroupItem[] screenGroup = screendesignRepository.getScreenGroupItemByScreenId(screenId);
		ScreenItem[] screenItems = screendesignRepository.getScreenItemByScreenIdForGenCode(screenId, lang, projectId);
		ScreenItemCodelist[] screenItemCodeList = screendesignRepository.getScreenItemCodelistByScreenId(screenId);
		ScreenParameter[] arrScreenParameter = screendesignRepository.getScreenParameterByScreenId(screenId);
		List<ScreenItem> screenHiddenItems = screendesignRepository.getScreenHiddenItemByScreenId(screenId, lang, projectId);
		List<DomainDatatypeCodelist> lstDomainCodeList = domainDatatypeCodelistRepository.findAllByScreenId(screenId);
		List<CodeListDetail> lstCodelistDetail = codeListDetailRepository.findAllByScreenId(screenId);
		List<ScreenItemEvent> screenItemEvents = screendesignRepository.findScreenItemEventByScreenId(screenId);
		List<ScreenItemEventMapping> screenItemEventMappings = screendesignRepository.findScreenItemEventMappingPreviewByScreenId(screenId);

		List<ScreenAreaEvent> screenAreaEvents = screendesignRepository.findScreenAreaEventByScreenId(screenId);
		List<ScreenFormTabs> screenFormTabs = screendesignRepository.findScreenFormTabsByScreenId(screenId);
		List<ScreenItemAutocompleteInput> screenItemAutocompleteInputs = screenDesignRepository.findAutocompleteInputByScreenId(screenId);
		List<ScreenAreaSortMapping> screenAreaSortMappings = screendesignRepository.findScreenAreaSortByScreenId(screenId);
		
		//TungHT-20/6/2016
		List<ScreenItem> screenItemLoadCodeList = new ArrayList<ScreenItem>();
		List<BusinessDesign> businessInitsDefault = screendesignRepository.getAllBlogicInitsDefaultByScreenId(screenId);
		List<BusinessDesign> businessProcessesDefault = screendesignRepository.getAllBlogicProcessesDefaultByScreenId(screenId, screenForms);
		screenDesign.setBusinessProcessesDefault(businessProcessesDefault);
		screenDesign.setBusinessInitsDefault(businessInitsDefault);
		
		for(ScreenItem item : screenItems) {
			if(StringUtils.isNotEmpty(item.getScreenItemTextCodeListId())) {
				screenItemLoadCodeList.add(item);
			}
		}
		List<ScreenItem> itemIdLoadCodelist = new ArrayList<ScreenItem>();
		
		if(screenItemLoadCodeList.size() > 0) {
			itemIdLoadCodelist = screendesignRepository.getScreenItemIdLoadCodelist(screenItemLoadCodeList);
		}
		for(ScreenItem itemCodelist : itemIdLoadCodelist) {
			for(ScreenItem item : screenItems) {
				if(StringUtils.isNotEmpty(itemCodelist.getScreenItemTextCodeListId()) && itemCodelist.getScreenId() != null && StringUtils.isNotEmpty(item.getScreenItemTextCodeListId()) && item.getScreenDesignIdCodeListId() != null) {
					if(itemCodelist.getScreenItemTextCodeListId().equals(item.getScreenItemTextCodeListId()) && itemCodelist.getScreenId().equals(item.getScreenDesignIdCodeListId())) {
						item.setScreenItemIdCodeListId(itemCodelist.getScreenItemId());
					}
				}
			}
		}
		
		for (int i = 0; i < screenItemEvents.size(); i++) {
			List<ScreenItemEventMapping> beanMapping = new ArrayList<ScreenItemEventMapping>();

			for (int j = 0; j < screenItemEventMappings.size(); j++) {
				if (screenItemEvents.get(i).getScreenItemEventId().equals(screenItemEventMappings.get(j).getScreenItemEventId())) {
					beanMapping.add(screenItemEventMappings.get(j));
				}
			}
			screenItemEvents.get(i).setScreenItemEventMappings(beanMapping);
		}

		// set total element for screen area
		for (int i = 0; i < screenAreas.length; i++) {
			screenAreas[i].setTotalGroup(getTotalGroupByScreenAreaId(screenAreas[i].getScreenAreaId(), screenGroup));

			// set hidden item for screen area
			List<ScreenItem> lstHiddenItemOfArea = new ArrayList<ScreenItem>();
			for (ScreenItem screenItem : screenHiddenItems) {
				if (screenItem.getScreenAreaId().equals(screenAreas[i].getScreenAreaId())) {
					lstHiddenItemOfArea.add(screenItem);
				}
			}
			String parameters = "";
			for (ScreenItem objTemp : lstHiddenItemOfArea) {
				if (lstHiddenItemOfArea.indexOf(objTemp) == lstHiddenItemOfArea.size() - 1) {
					parameters += objTemp.getItemCode() + ScreenDesignConst.ITEM_SPLIT + objTemp.getMessageDesign().getMessageCode() + ScreenDesignConst.ITEM_SPLIT + objTemp.getMessageDesign().getMessageString();
				} else {
					parameters += objTemp.getItemCode() + ScreenDesignConst.ITEM_SPLIT + objTemp.getMessageDesign().getMessageCode() + ScreenDesignConst.ITEM_SPLIT + objTemp.getMessageDesign().getMessageString() + ScreenDesignConst.ROW_SPLIT;
				}
			}
			screenAreas[i].setFormElementHidden(parameters);
			ScreenAreaEventOutput output = new ScreenAreaEventOutput();
			List<ScreenAreaEventRequire> screenAreaEventRequires = new ArrayList<ScreenAreaEventRequire>();
			screenAreas[i].setScreenAreaEvents(new ArrayList<ScreenAreaEvent>());
			
			//TungHT
			screenAreas[i].setScreenAreaSortMappings(screenAreaSortMappings);
			
			for (ScreenAreaEvent event : screenAreaEvents) {

				if (!event.getScreenAreaId().equals(screenAreas[i].getScreenAreaId()))
					continue;

				screenAreas[i].getScreenAreaEvents().add(event);

				ScreenAreaEventRequire eventRequire = new ScreenAreaEventRequire();
				eventRequire.setIfRequired(event.getIfRequire().split(","));
				eventRequire.setThenMustRequired(event.getThenMustRequire().split(","));
				screenAreaEventRequires.add(eventRequire);
			}
			if (screenAreaEventRequires.size() > 0) {
				ScreenAreaEventRequire[] events = new ScreenAreaEventRequire[screenAreaEventRequires.size()];
				events = screenAreaEventRequires.toArray(events);
				output.setRequireConstraints(events);

				JsonFactory json = new JsonFactory();
				ObjectMapper mapper = new ObjectMapper(json);
				TypeReference<ScreenAreaEventOutput> typeRef = new TypeReference<ScreenAreaEventOutput>() {
				};

				String strJson = "";
				try {
					strJson = mapper.writeValueAsString(output);

				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

				screenAreas[i].setAreaEventValue(strJson);
			}
		}
		// set total element for screen group id
		for (int i = 0; i < screenGroup.length; i++) {
			screenGroup[i].setTotalElement(getTotalElementByScreenGroupId(screenGroup[i].getGroupItemId(), screenItems));
			ScreenGroupItem group = screenGroup[i];
			int startItemIndex = 0;
			int totalElement = screenGroup[i].getTotalElement();

			for (int j = 0; j < screenItems.length; j++) {
				ScreenItem item = screenItems[j];

				if (item.getGroupItemId() == null || !item.getGroupItemId().equals(group.getGroupItemId()))
					continue;

				if (totalElement == 0)
					break;

				if (startItemIndex == 0) {
					screenGroup[i].setElementStart(item.getItemSeqNo());

				}

				if (totalElement == 1) {
					screenGroup[i].setElementEnd(item.getItemSeqNo());

				}
				startItemIndex++;
				totalElement--;
			}
			startItemIndex = 0;

		}

		for (int i = 0; i < screenItems.length; i++) {

			ScreenItem item = screenItems[i];

			for (ScreenArea area : screenAreas) {
				if (item.getScreenAreaId() != null && area.getScreenAreaId() != null && item.getScreenAreaId().equals(area.getScreenAreaId())) {
					screenItems[i].setScreenArea(area);
				}
			}

			if (DbDomainConst.LogicDataType.LABEL_DYNAMIC.equals(screenItems[i].getLogicalDataType())) {
				if (screenItems[i].getPhysicalDataType() != null) {
					// set style fomat
					switch (screenItems[i].getPhysicalDataType()) {
					case 5:
					case 4:
					case 15:
						screenItems[i].setFieldStyle(ScreenDesignConst.FieldStyle.NUMERIC);
						break;
					case 10:
					case 11:
						screenItems[i].setFieldStyle(ScreenDesignConst.FieldStyle.DATE);
						break;
					case 12:
						screenItems[i].setFieldStyle(ScreenDesignConst.FieldStyle.DATETIME);
						break;
					default:
						screenItems[i].setFieldStyle(ScreenDesignConst.FieldStyle.TEXT);
						break;
					}
				}
			}

			// set codelist for screenitem
			screenItems[i].setListScreenItemCodelists(new ArrayList<ScreenItemCodelist>());
			if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_SYSTEM.equals(screenItems[i].getCodelistType())) {
				if (lstCodelistDetail != null && lstCodelistDetail.size() > 0) {
					for (CodeListDetail codeListDetail : lstCodelistDetail) {
						if(screenItems[i] != null && screenItems[i].getCodelistId() != null) {
							if (screenItems[i].getCodelistId().equals(codeListDetail.getCodeListId())) {
								ScreenItemCodelist objTemp = new ScreenItemCodelist();
								objTemp.setCodelistName(codeListDetail.getName());
								objTemp.setCodelistVal(codeListDetail.getValue());
								screenItems[i].getListScreenItemCodelists().add(objTemp);
							}
						}
					}
				}
				/*
				 * if (lstCodelistDetail != null && lstCodelistDetail.size() > 0) { screenItems[i].setCodelistText(lstCodelistDetail.get(0).getCodelistName()); }
				 */
			} else if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_TABLE.equals(screenItems[i].getCodelistType())) {
				if (screenItems[i].getDomainTblMappingItemId() != null) {
					if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_CUSTOM.toString().equals(screenItems[i].getDomainCodelistType())) {
						// case of codelist_type of domain_table_mapping_items
						// is custom
						if (lstDomainCodeList != null && lstDomainCodeList.size() > 0) {
							for (DomainDatatypeCodelist domainCodelistDetail : lstDomainCodeList) {
								if (screenItems[i].getDomainTblMappingItemId().equals(domainCodelistDetail.getDomainDatatypeItemId())) {
									ScreenItemCodelist objTemp = new ScreenItemCodelist();
									objTemp.setCodelistName(domainCodelistDetail.getCodelistName());
									objTemp.setCodelistVal(domainCodelistDetail.getCodelistValue());
									screenItems[i].getListScreenItemCodelists().add(objTemp);
								}
							}
						}
					} else if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_SYSTEM.toString().equals(screenItems[i].getDomainCodelistType())) {
						// case of codelist_type of domain_table_mapping_items
						// is system
						for (CodeListDetail codeListDetail : lstCodelistDetail) {
							if (screenItems[i].getDomainCodelistId().equals(String.valueOf(codeListDetail.getCodeListId()))) {
								ScreenItemCodelist objTemp = new ScreenItemCodelist();
								objTemp.setCodelistName(codeListDetail.getName());
								objTemp.setCodelistVal(codeListDetail.getValue());
								screenItems[i].getListScreenItemCodelists().add(objTemp);
							}
						}
					}
				}
			}
			if (ScreenDesignConst.ScreenItemConst.CODELIST_TYPE_CUSTOM.equals(screenItems[i].getCodelistType())) {
				if (screenItemCodeList != null) {
					for (int j = 0; j < screenItemCodeList.length; j++) {
						if (screenItems[i].getScreenItemId().equals(screenItemCodeList[j].getScreenItemId())) {
							screenItems[i].getListScreenItemCodelists().add(screenItemCodeList[j]);
						}
					}
				}
			}
			if (screenItems[i].getScreenActionId() != null) {
				ScreenAction screenAction = screenActionRepository.findByIdOfNavigateBlogic(screenItems[i].getScreenActionId(), languageId);
				// Generate JSP
				if(GEN_JSP.equals(genType)) {
					// GET
					if(screenAction != null && screenAction.getSubmitMethodType() != null && screenAction.getSubmitMethodType().equals(2)) {
						ScreenDesign screen = screendesignRepository.findScreenDesignByDestinationBlogic(screenAction.getScreenActionId());
						if(screen != null) {
							screenAction.setToScreenCodeByNavigateBlogic(screen.getScreenCode());
							screenAction.setToModuleIdByNavigateBlogic(screen.getModuleId());
							screenAction.setToScreenIdByNavigateBlogic(screen.getScreenId());
							screenAction.setToModuleCodeByNavigateBlogic(screen.getModuleCode());
							screenAction.setToTemplateTypeByNavigateBlogic(screen.getTemplateType());
						}
					} 
					// POST
					else {
						if(screenAction != null && screenAction.getNavigateToBlogicId() != null) {
							BusinessDesign blogic = businessDesignRepository.findBusinessLogicInformation(screenAction.getNavigateToBlogicId(), languageId, projectId);
							if(blogic != null) {
								ScreenDesign screen = screendesignRepository.findById(blogic.getScreenId(), languageId, projectId);
								if(screen != null) {
									screenAction.setToScreenCodeByNavigateBlogic(screen.getScreenCode());
									screenAction.setToModuleIdByNavigateBlogic(screen.getModuleId());
									screenAction.setToScreenIdByNavigateBlogic(screen.getScreenId());
									screenAction.setToModuleCodeByNavigateBlogic(screen.getModuleCode());
									screenAction.setToTemplateTypeByNavigateBlogic(screen.getTemplateType());
								}
							}
						}
						
					}
				}
				// Generate HTML
				if(GEN_HTML.equals(genType)) {
					if(screenAction != null) {
						// Navigate direct to Screen
						if(screenAction.getToScreenId() != null) {
							/*// Design mode :
							}*/
							ScreenAction sAction = screenActionRepository.findById(screenAction.getScreenActionId(),languageId);
							screenAction.setToModuleIdByNavigateBlogic(sAction.getToModuleId());
							screenAction.setToModuleCode(sAction.getToModuleCode());
							screenAction.setToScreenCodeByNavigateBlogic(sAction.getToScreenCode());
							screenAction.setToTemplateTypeByNavigateBlogic(sAction.getToScreenTemplateType());
							
						} 
						// Navigate to blogic and base on navigate type : blogic - screen
						else {
							NavigatorComponent naviComponent = screenActionRepository.getNavigateByActionId(screenAction.getScreenActionId());
							if(naviComponent != null) {
								//Type is screen
								if(naviComponent.getNavigatorToType().equals(0)) {
									ScreenDesign sdScreen = screendesignRepository.findSDByDestinationBlogicTypeScreen(screenAction.getScreenActionId());
									screenAction.setToScreenCodeByNavigateBlogic(sdScreen.getScreenCode());
									screenAction.setToModuleIdByNavigateBlogic(sdScreen.getModuleId());
									screenAction.setToScreenIdByNavigateBlogic(sdScreen.getScreenId());
									screenAction.setToModuleCodeByNavigateBlogic(sdScreen.getModuleCode());
									screenAction.setToTemplateTypeByNavigateBlogic(sdScreen.getTemplateType());
									
								}
								//Type is blogic => sequence
								if(naviComponent.getNavigatorToType().equals(1)) {
									ScreenDesign sdBlogic = screendesignRepository.findScreenDesignByDestinationBlogic(screenAction.getScreenActionId());
									screenAction.setToScreenCodeByNavigateBlogic(sdBlogic.getScreenCode());
									screenAction.setToModuleIdByNavigateBlogic(sdBlogic.getModuleId());
									screenAction.setToScreenIdByNavigateBlogic(sdBlogic.getScreenId());
									screenAction.setToModuleCodeByNavigateBlogic(sdBlogic.getModuleCode());
									screenAction.setToTemplateTypeByNavigateBlogic(sdBlogic.getTemplateType());
								}
							}
						}
						
					}
				}
				
				List<ScreenActionParam> lstScreenActionParam = screenActionParamRepository.findAllActionParamByScreenActionId(screenItems[i].getScreenActionId(),projectId);
				if(screenAction != null){
					screenAction.setListScreenParameters(lstScreenActionParam);
				}
				screenItems[i].setScreenAction(screenAction);
			}

			List<ScreenItemEvent> events = new ArrayList<ScreenItemEvent>();
			// event
			for (ScreenItemEvent event : screenItemEvents) {
				if (screenItems[i].getScreenItemId().equals(event.getScreenItemId())) {
					events.add(event);
				}
			}

			screenItems[i].setScreenItemAutocompleteInputs(new ArrayList<ScreenItemAutocompleteInput>());
			for (ScreenItemAutocompleteInput input : screenItemAutocompleteInputs) {
				if (input.getScreenItemId().equals(screenItems[i].getScreenItemId())) {
					screenItems[i].getScreenItemAutocompleteInputs().add(input);
				}
			}

			screenItems[i].setScreenItemEvents(events);
			screenItems[i].setValue(getJsonScreenItem(screenItems[i]));
			screenItems[i].setElement(getElement(screenItems[i], screenDesign.getScreenPatternType(), module.getModuleCode()));
		}

		List<ScreenFormTabGroup> screenFormTabGroups = new ArrayList<ScreenFormTabGroup>();

		if (screenFormTabs.size() > 0) {
			List<String> groups = new ArrayList<String>();
			groups.add(screenFormTabs.get(0).getTabCode() + "-" + screenFormTabs.get(0).getScreenFormId());

			for (int i = 1; i < screenFormTabs.size(); i++) {
				if (groups.indexOf(screenFormTabs.get(i).getTabCode() + "-" + screenFormTabs.get(i).getScreenFormId()) == -1) {
					groups.add(screenFormTabs.get(i).getTabCode() + "-" + screenFormTabs.get(i).getScreenFormId());
				}
			}

			for (int i = 0; i < groups.size(); i++) {
				ScreenFormTabGroup groupTab = new ScreenFormTabGroup();
				String[] tabKey = groups.get(i).split("-");

				if (tabKey.length == 2) {
					ScreenFormTabGroup group = new ScreenFormTabGroup();

					group.setTabCode(tabKey[0]);
					group.setScreenFormId(Long.parseLong(tabKey[1]));
					group.setScreenFormTabs(new ArrayList<ScreenFormTabs>());

					for (int j = 0; j < screenFormTabs.size(); j++) {
						if (screenFormTabs.get(j).getScreenFormId().equals(group.getScreenFormId()) && screenFormTabs.get(j).getTabCode().equals(group.getTabCode())) {

							group.setTabDirection(screenFormTabs.get(j).getTabDirection());
							group.getScreenFormTabs().add(screenFormTabs.get(j));
						}
					}
					screenFormTabGroups.add(group);
				}

			}
		}

		for (int i = 0; i < screenForms.length; i++) {

			// add group tab
			screenForms[i].setScreenFormTabGroups(new ArrayList<ScreenFormTabGroup>());

			for (ScreenFormTabGroup group : screenFormTabGroups) {
				if (group.getScreenFormId().equals(screenForms[i].getScreenFormId())) {
					String formCode = screenForms[i].getFormCode() + "-" + group.getTabCode();
					formCode = formCode.replace(" ", "");
					// setting template
					String startHtml = "<div id=\"" + formCode + "\" style='width: 100%; float:left;' class='area-tab'>";
					String endHtml = "";
					if (group.getTabDirection().equals(1)) {
						startHtml += "<div class=\"menu-tab\" style=\"float: left; width: 20%; margin: 0px; padding: 0px; margin-left: 4px;\"><ul id=\"" + formCode + "\"-tab\" class=\"nav nav-tabs tabs-left\">";
					} else if (group.getTabDirection().equals(0)) {
						startHtml += "<ul style=\"margin-left: 4px; margin-right: 4px;\" id=\"" + formCode + "-tab\" class=\"nav nav-tabs\">";
					} else {
						startHtml += "<div style=\"margin-left: 4px; margin-right: 4px;\" class=\"panel-group-accordion\" id=\"" + formCode + "-tab\">";
					}

					if (group.getTabDirection().equals(1) || group.getTabDirection().equals(0)) {
						for (int j = 0; j < group.getScreenFormTabs().size(); j++) {
							ScreenFormTabs tab = group.getScreenFormTabs().get(j);

							if (j == 0) {
								startHtml += "<li class=\"active\"><a data-toggle='tab' href='#" + formCode + "tab-" + j + "'>" + tab.getTabTitle() + "</a></li>";
							} else {
								startHtml += "<li><a data-toggle='tab' href='#" + formCode + "tab-" + j + "'>" + tab.getTabTitle() + "</a></li>";
							}
						}
					}

					if (group.getTabDirection().equals(1)) {
						startHtml += "</ul></div><div class=\"contain-tab-content\" style=\"float: left; width: 79%;margin: 0px; padding: 0px;\"><div id=\"" + formCode + "-tab-content\" style=\"border: 1px solid #ddd;\" class=\"tab-content\">";
						endHtml = "</div></div></div>";
					} else if (group.getTabDirection().equals(0)) {
						startHtml += "</ul><div style=\"margin-left: 4px; margin-right: 4px; height: auto;\" id=\"" + formCode + "-tab-content\" class=\"tab-content\">";
						endHtml = "</div></div>";
					} else {
						endHtml = "</div></div>";
					}

					group.setStartHtml(startHtml);
					group.setEndHtml(endHtml);
					screenForms[i].getScreenFormTabGroups().add(group);
				}
			}

			// set value
			FormTab formTab = new FormTab();

			if (screenFormTabs.size() > 0) {
				List<Tab> tabs = new ArrayList<Tab>();
				List<ScreenFormTabs> formTabs = new ArrayList<ScreenFormTabs>();

				for (int j = 0; j < screenFormTabs.size(); j++) {
					if (screenForms[i].getScreenFormId().equals(screenFormTabs.get(j).getScreenFormId())) {
						Tab tab = new Tab();
						tab.setTabCode(screenFormTabs.get(j).getTabCode());
						tab.setTabDirection(screenFormTabs.get(j).getTabDirection() + "");
						tab.setTitle(screenFormTabs.get(j).getTabTitle());
						tab.setAreas(screenFormTabs.get(j).getAreas());
						tabs.add(tab);

						formTabs.add(screenFormTabs.get(j));
					}
				}

				ScreenFormTabs[] arrFormTabs = new ScreenFormTabs[formTabs.size()];
				arrFormTabs = formTabs.toArray(arrFormTabs);
				screenForms[i].setScreenFormTabs(arrFormTabs);

				Tab[] arrTabs = new Tab[tabs.size()];
				arrTabs = tabs.toArray(arrTabs);

				formTab.setTabs(arrTabs);
				JsonFactory json = new JsonFactory();
				ObjectMapper mapper = new ObjectMapper(json);
				TypeReference<FormTab> typeRef = new TypeReference<FormTab>() {
				};

				String strJson;
				try {
					strJson = mapper.writeValueAsString(formTab);
					screenForms[i].setTabValue(strJson);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					screenForms[i].setTabValue("");
				}
			}
		}

		screenDesign.setScreenForms(screenForms);
		screenDesign.setScreenAreas(screenAreas);
		screenDesign.setScreenGroupItems(screenGroup);
		screenDesign.setScreenItems(screenItems);
		screenDesign.setArrScreenParameter(arrScreenParameter);
		String screenInputParameters = new String();
		for (int i = 0; i < arrScreenParameter.length; i++) {
			screenInputParameters += arrScreenParameter[i].getScreenParamName() + ScreenDesignConst.ITEM_SPLIT + arrScreenParameter[i].getScreenParamCode() + ScreenDesignConst.ITEM_SPLIT + arrScreenParameter[i].getDataType() + ScreenDesignConst.ROW_SPLIT;
		}
		if (arrScreenParameter.length > 0) {
			screenInputParameters = screenInputParameters.substring(0, screenInputParameters.length() - 1);
		}
		screenDesign.setScreenParameters(screenInputParameters);

		return screenDesign;
	}

	@Override
	public List<ScreenDesignOutputBeanForSetting> getOutputBeanForSetting(Long screenId, Long languageId) {
		// TODO Auto-generated method stub
		List<ScreenDesignOutputBeanForSetting> outputBean = screenDesignRepository.getOutputBeanForSetting(screenId, languageId);
		return outputBean;
	}

	@Override
	public List<ScreenDesignOutputBeanForSetting> getScreenItemForSetting(Long screenId, Long languageId) {
		// TODO Auto-generated method stub
		List<ScreenDesignOutputBeanForSetting> outputBean = screenDesignRepository.getScreenItemForSetting(screenId, languageId);
		return outputBean;
	}

	@Override
	public List<ScreenForm> getAllFormItems(List<ScreenForm> screenForms, List<ScreenArea> screenAreas, List<ScreenItem> screenItems) {
		// TODO Auto-generated method stub
		// Add screen items to screen items map
		Map<Long, List<ScreenItem>> mapScreenItems = new HashMap<Long, List<ScreenItem>>();
		for (ScreenItem screenItem : screenItems) {
			if (mapScreenItems.containsKey(screenItem.getScreenAreaId())) {
				mapScreenItems.get(screenItem.getScreenAreaId()).add(screenItem);
			} else {
				List<ScreenItem> newItem = new ArrayList<ScreenItem>();
				newItem.add(screenItem);
				mapScreenItems.put(screenItem.getScreenAreaId(), newItem);
			}
		}

		// Set list screen item for screen area
		Map<Long, List<ScreenArea>> mapScreenAreas = new HashMap<Long, List<ScreenArea>>();
		for (ScreenArea screenArea : screenAreas) {
			// Add screen items to screen area
			if (mapScreenItems.containsKey(screenArea.getScreenAreaId())) {
				screenArea.setListItems(mapScreenItems.get(screenArea.getScreenAreaId()));
			} else {
				screenArea.setListItems(new ArrayList<ScreenItem>());
			}
			// Add screen areas to screen areas map
			if (mapScreenAreas.containsKey(screenArea.getScreenFormId())) {
				mapScreenAreas.get(screenArea.getScreenFormId()).add(screenArea);
			} else {
				List<ScreenArea> newItem = new ArrayList<ScreenArea>();
				newItem.add(screenArea);
				mapScreenAreas.put(screenArea.getScreenFormId(), newItem);
			}
		}

		// set list area for screen form
		for (ScreenForm screenForm : screenForms) {
			if (mapScreenAreas.containsKey(screenForm.getScreenFormId())) {
				screenForm.setListScreenAreas(mapScreenAreas.get(screenForm.getScreenFormId()));
			} else {
				screenForm.setListScreenAreas(new ArrayList<ScreenArea>());
			}
		}
		return screenForms;
	}

	@Override
	public Map<String, String> getProjectStyle(Long projectId) {
		// TODO Auto-generated method stub
		Map<String, String> mapTheme = projectService.findThemeByProjectIdForHTML(projectId);
		String panelHeader = "";
		String panelBody = "";
		String panelListTable = "";
		String panelListTh = "";
		String panelListTdText = "";
		String panelListTdNumeric = "";
		String panelListTdDate = "";
		String panelListTdDateTime = "";
		String panelListTdNoNumber = "";
		String panelListTdActionColumn = "";
		String panelTableForm = "";
		String panelTableFormTh = "";
		String panelTableFormTd = "";
		String screenStyle = "";
		String screenStyleSize = "";
		String menuBgColor = "";
		String menuBrandColor = "";
		String menuBrandSize = "";
		String menuTextColor = "";

		String menuSelectedBgColor = "";

		String menuStyle = "";
		String menuSelectedStyle = "";

		String menuItemStyle = "";
		String menuItemHoverStyle = "";
		String menuItemBgHoverStyle = "";
		String footerStyle = "";
		String logo = "";
		String logoPosition = "";
		/* String logoWidthHeight = ""; */

		String width = "";
		String height = "";
		String backgroundColor = "";
		String commonButtonBgColor = "";
		String commonButtonBgActiveColor = "";
		String commonButtonTextColor = "";

		String commonButtonDeleteBgColor = "";
		String commonButtonDeleteBgActiveColor = "";
		String commonButtonDeleteTextColor = "";

		String clientButtonDeleteBgColor = "";
		String clientButtonDeleteBgActiveColor = "";
		String clientButtonDeleteTextColor = "";

		String commonLinkPopupTextColor = "";

		String headerTitleColor = "";
		String headerTitleSize = "";
		String headerTitlePosition = "";

		String headerLinkPosition = "";
		String headerLinkColor = "";
		String headerLinkFontSize = "";

		String panelListThBackgroundColor = "";
		String panelListThFontSize = "";

		String panelListTableBorderSpacing = "";
		String panelTableFormTableBorderSpacing = "";

		for (Map.Entry<String, String> entry : mapTheme.entrySet()) {
			if (entry.getKey().indexOf("panel-header-") == 0) {
				panelHeader += entry.getKey().replace("panel-header-", "") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().indexOf("panel-body-") == 0) {
				panelBody += entry.getKey().replace("panel-body-", "") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().indexOf("panel-list-table-") == 0) {
				if (entry.getKey().replace("panel-list-table-", "").equalsIgnoreCase("border-spacing") && !entry.getValue().equalsIgnoreCase("px")) {
					if (!entry.getValue().equalsIgnoreCase("0px") && entry.getValue().indexOf("-") != 0) {
						panelListTable += "border-collapse: separate;";
					}
				}
				panelListTable += entry.getKey().replace("panel-list-table-", "") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().indexOf("panel-list-th-") == 0) {
				panelListTh += entry.getKey().replace("panel-list-th-", "") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().indexOf("panel-list-td-text") == 0) {
				panelListTdText += entry.getKey().replace("panel-list-td-text", "text-align") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("panel-list-td-numeric")) {
				panelListTdNumeric += entry.getKey().replace("panel-list-td-numeric", "text-align") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("panel-list-td-date")) {
				panelListTdDate += entry.getKey().replace("panel-list-td-date", "text-align") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("panel-list-td-date-time")) {
				panelListTdDateTime += entry.getKey().replace("panel-list-td-date-time", "text-align") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("panel-list-td-no-number")) {
				panelListTdNoNumber += entry.getKey().replace("panel-list-td-no-number", "text-align") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("panel-list-td-action-column")) {
				panelListTdActionColumn += entry.getKey().replace("panel-list-td-action-column", "text-align") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().indexOf("panel-table-form-table-") == 0) {
				if (entry.getKey().replace("panel-table-form-table-", "").equalsIgnoreCase("border-spacing") && !entry.getValue().equalsIgnoreCase("px")) {
					if (!entry.getValue().equalsIgnoreCase("0px") && entry.getValue().indexOf("-") != 0) {
						panelTableForm += "border-collapse: separate;";
					}
				}
				panelTableForm += entry.getKey().replace("panel-table-form-table-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-table-form-th-") == 0) {
				panelTableFormTh += entry.getKey().replace("panel-table-form-th-", "") + ":" + entry.getValue() + "; ";
				
			} else if (entry.getKey().indexOf("panel-table-form-td-") == 0) {
				panelTableFormTd += entry.getKey().replace("panel-table-form-td-", "") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("common-screen-size")) {
				screenStyle += entry.getKey().replace("common-screen-size", "width") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("common-font-family")) {
				screenStyle += entry.getKey().replace("common-font-family", "font-family") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("common-font-size")) {
				screenStyle += entry.getKey().replace("common-font-size", "font-size") + ":" + entry.getValue() + "; ";
				screenStyleSize = entry.getKey().replace("common-font-size", "font-size") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("commom-background-image")) {
				backgroundColor += entry.getKey().replace("commom-background-image", "background-image") + ":url(data:image/jpeg;base64," + entry.getValue() + "); ";

			} else if (entry.getKey().equals("common-background-color")) {
				backgroundColor += entry.getKey().replace("common-background-color", "background-color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("menu-bg-color")) {
				menuBgColor += entry.getValue();

			} else if (entry.getKey().equals("menu-brand-color")) {
				menuBrandColor += entry.getKey().replace("menu-brand-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("menu-brand-size")) {
				menuBrandSize += entry.getKey().replace("menu-brand-size", "font-size") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("menu-text-color")) {
				menuTextColor += entry.getKey().replace("menu-text-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("menu-font-color")) {
				menuStyle += entry.getKey().replace("menu-font-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("menu-font-size")) {
				menuStyle += entry.getKey().replace("menu-font-size", "font-size") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("menu-selected-bg-color")) {
				menuSelectedBgColor += entry.getValue();

			} else if (entry.getKey().equals("menu-selected-text-color")) {
				menuSelectedStyle += entry.getKey().replace("menu-selected-text-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("item-menu-bg-color")) {
				menuItemStyle += entry.getKey().replace("item-menu-bg-color", "background-color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("item-menu-font-color")) {
				menuItemStyle += entry.getKey().replace("item-menu-font-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("item-menu-font-size")) {
				menuItemStyle += entry.getKey().replace("item-menu-font-size", "font-size") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("item-menu-hover-bg-color")) {
				menuItemBgHoverStyle += entry.getValue();

			} else if (entry.getKey().equals("item-menu-hover-text-color")) {
				menuItemHoverStyle += entry.getKey().replace("item-menu-hover-text-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("footer-text-color")) {
				footerStyle += entry.getKey().replace("footer-text-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("footer-text-size")) {
				footerStyle += entry.getKey().replace("footer-text-size", "font-size") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("logo")) {
				logo += entry.getValue();

			} else if (entry.getKey().equals("panel-table-list-td-anotherChild-position")) {
				logoPosition += entry.getValue();

			} else if (entry.getKey().equals("panel-table-list-td-firstChild-width")) {
				width += entry.getValue();

			} else if (entry.getKey().equals("panel-table-list-td-firstChild-height")) {
				height += entry.getValue();

			} else if (entry.getKey().equals("common-button-bg-color")) {
				commonButtonBgColor += entry.getKey().replace("common-button-bg-color", "background-color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("common-button-bg-active-color")) {
				commonButtonBgActiveColor += entry.getKey().replace("common-button-bg-active-color", "background-color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("common-button-text-color")) {
				commonButtonTextColor += entry.getKey().replace("common-button-text-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("common-button-delete-bg-color")) {
				commonButtonDeleteBgColor += entry.getKey().replace("common-button-delete-bg-color", "background-color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("common-button-delete-bg-active-color")) {
				commonButtonDeleteBgActiveColor += entry.getKey().replace("common-button-delete-bg-active-color", "background-color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("common-button-delete-text-color")) {
				commonButtonDeleteTextColor += entry.getKey().replace("common-button-delete-text-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("client-button-delete-bg-color")) {
				clientButtonDeleteBgColor += entry.getKey().replace("client-button-delete-bg-color", "background-color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("client-button-delete-bg-active-color")) {
				clientButtonDeleteBgActiveColor += entry.getKey().replace("client-button-delete-bg-active-color", "background-color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("client-button-delete-text-color")) {
				clientButtonDeleteTextColor += entry.getKey().replace("client-button-delete-text-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("common-link-popup-text-color")) {
				commonLinkPopupTextColor += entry.getKey().replace("common-link-popup-text-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("header-title-color")) {
				headerTitleColor += entry.getKey().replace("header-title-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("header-title-size")) {
				headerTitleSize += entry.getKey().replace("header-title-size", "font-size") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("header-title-position")) {
				headerTitlePosition += entry.getKey().replace("header-title-position", "position") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("header-link-position")) {
				headerLinkPosition += entry.getKey().replace("header-link-position", "position") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("header-link-color")) {
				headerLinkColor += entry.getKey().replace("header-link-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("header-link-font-size")) {
				headerLinkFontSize += entry.getKey().replace("header-link-font-size", "font-size") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("panel-list-th-background-color")) {
				panelListThBackgroundColor += entry.getKey().replace("panel-list-th-background-color", "color") + ":" + entry.getValue() + "; ";

			} else if (entry.getKey().equals("panel-list-th-font-size")) {
				panelListThFontSize += entry.getKey().replace("panel-list-th-font-size", "font-size") + ":" + entry.getValue() + "; ";
			}

			if (entry.getKey().equals("panel-table-form-table-border-spacing") && !StringUtils.isEmpty(entry.getValue())) {
				panelTableFormTableBorderSpacing += entry.getKey().replace("panel-table-form-table-border-spacing", "border-spacing") + ":" + entry.getValue() + "; ";
			}
			if (entry.getKey().equals("panel-list-table-border-spacing") && !StringUtils.isEmpty(entry.getValue())) {
				panelListTableBorderSpacing += entry.getKey().replace("panel-list-table-border-spacing", "border-spacing") + ":" + entry.getValue() + "; ";
			}

		}

		mapTheme.put("logo", logo);

		if (StringUtils.isEmpty(logoPosition)) {
			logoPosition = "left";
		}

		mapTheme.put("logoPosition", logoPosition);

		if (height.length() > 0) {
			height = "height: " + height + ";";
		} else {
			height = "height: 25px;";
		}

		if (width.length() > 0) {
			width = "width: " + width + ";";
		}

		mapTheme.put("backgroundColor", backgroundColor);
		mapTheme.put("logoWidth", width);
		mapTheme.put("logoHeight", height);

		mapTheme.put("footerStyle", footerStyle);
		mapTheme.put("menuItemBgHoverStyle", menuItemBgHoverStyle);
		mapTheme.put("menuSelectedBgColor", menuSelectedBgColor);

		mapTheme.put("menuItemStyle", menuItemStyle);
		mapTheme.put("menuItemHoverStyle", menuItemHoverStyle);

		mapTheme.put("menuStyle", menuStyle);
		mapTheme.put("menuSelectedStyle", menuSelectedStyle);

		mapTheme.put("menuBgColor", menuBgColor);
		mapTheme.put("menuBrandColor", menuBrandColor);
		mapTheme.put("menuBrandSize", menuBrandSize);
		mapTheme.put("menuTextColor", menuTextColor);

		mapTheme.put("panelHeader", panelHeader);
		mapTheme.put("panelBody", panelBody);

		mapTheme.put("panelListTable", panelListTable);
		mapTheme.put("panelListTh", panelListTh);
		mapTheme.put("panelListTdText", panelListTdText);

		mapTheme.put("panelListTdNumeric", panelListTdNumeric);
		mapTheme.put("panelListTdDate", panelListTdDate);
		mapTheme.put("panelListTdDateTime", panelListTdDateTime);
		mapTheme.put("panelListTdNoNumber", panelListTdNoNumber);
		mapTheme.put("panelListTdActionColumn", panelListTdActionColumn);

		mapTheme.put("panelTableForm", panelTableForm);
		mapTheme.put("panelTableFormTh", panelTableFormTh);
		mapTheme.put("panelTableFormTd", panelTableFormTd);

		mapTheme.put("commonButtonBgColor", commonButtonBgColor);
		mapTheme.put("commonButtonBgActiveColor", commonButtonBgActiveColor);
		mapTheme.put("commonButtonTextColor", commonButtonTextColor);

		mapTheme.put("clientButtonDeleteBgColor", clientButtonDeleteBgColor);
		mapTheme.put("clientButtonDeleteBgActiveColor", clientButtonDeleteBgActiveColor);
		mapTheme.put("clientButtonDeleteTextColor", clientButtonDeleteTextColor);

		mapTheme.put("commonButtonDeleteBgColor", commonButtonDeleteBgColor);
		mapTheme.put("commonButtonDeleteBgActiveColor", commonButtonDeleteBgActiveColor);
		mapTheme.put("commonButtonDeleteTextColor", commonButtonDeleteTextColor);

		mapTheme.put("commonLinkPopupTextColor", commonLinkPopupTextColor);

		mapTheme.put("headerTitleColor", headerTitleColor);
		mapTheme.put("headerTitleSize", headerTitleSize);
		mapTheme.put("headerTitlePosition", headerTitlePosition);

		mapTheme.put("headerLinkPosition", headerLinkPosition);
		mapTheme.put("headerLinkColor", headerLinkColor);
		mapTheme.put("headerLinkFontSize", headerLinkFontSize);

		mapTheme.put("panelListThBackgroundColor", panelListThBackgroundColor);
		mapTheme.put("panelListThFontSize", panelListThFontSize);

		mapTheme.put("panelListTableBorderSpacing", panelListTableBorderSpacing);
		mapTheme.put("panelTableFormTableBorderSpacing", panelTableFormTableBorderSpacing);

		mapTheme.put("screenStyle", screenStyle);
		mapTheme.put("screenStyleSize", screenStyleSize);

		return mapTheme;
	}

	@Override
	public String getMenuBuilder(String menuContent, MenuDesign menuDesign, Project project, int menuType, Long projectId) {
		/* create menu content */
		String menuContentOfHome = StringUtils.EMPTY;
		menuDesign = menuDesignService.getMenuDesignForPreview(project.getProjectId(), projectId);

		if (menuDesign != null) {
			// menuType = menuDesign.getMenuType();
			menuDesign.setProjectId(project.getProjectId());
			menuDesign.setMenuType(menuType);
			menuDesign.setUrlMainAction("home.html");
			menuContentOfHome = menuDesignService.buildMenu(menuDesign, DbDomainConst.MenuType.PROTOTYPE);

			menuDesign.setUrlMainAction("../home.html");
			menuDesign.setUrlRoot("../");
			menuContent = menuDesignService.buildMenu(menuDesign, DbDomainConst.MenuType.PROTOTYPE);
		} else {
			menuDesign = new MenuDesign();
			/* menuDesign.setHeaderMenuName(generateHTMLForm.getProjectName()); */
			menuDesign.setProjectId(project.getProjectId());
			menuDesign.setMenuType(menuType);
			menuContent = menuDesignService.buildMenu(menuDesign, DbDomainConst.MenuType.PROTOTYPE);
			menuContentOfHome = menuContent;
		}
		return menuContentOfHome;
	}

	private InputBean populateInputBean(String inputBeanCode, ScreenItem item, ScreenForm form, ScreenArea screenArea, List<BusinessDesign> businessDesigns, List<InputBean> lstParentInputBeans) {
		InputBean inputBean = new InputBean();
		inputBean.setInputBeanCode(inputBeanCode);
		if (item.getMessageDesign() != null) {
			inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
		} else {
			inputBean.setInputBeanName("");
		}
		if (item.getPhysicalDataType() != null) {
			inputBean.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
		}
		inputBean.setScreenItemId(item.getScreenItemId());
		inputBean.setScreenItem(item);
		inputBean.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
		// set input bean into parent object if have

		String parentInputBeanId = null;
		if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_TABLE_DESIGN.equals(screenArea.getObjectMappingType())) {
			for (BusinessDesign blogic : businessDesigns) {
				if (form.getScreenFormIdStore() != null && form.getScreenFormIdStore().equals(blogic.getScreenFormId())) {
					// set parent input bean id
					for (InputBean parentInputbean : lstParentInputBeans) {
						if (blogic.getBusinessLogicId().equals(parentInputbean.getBusinessLogicId()) && parentInputbean.getTblDesignId() != null && parentInputbean.getTblDesignId().equals(screenArea.getObjectMappingId())) {
							parentInputBeanId = parentInputbean.getInputBeanId();
							break;
						}
					}
					inputBean.setParentInputBeanId(parentInputBeanId);
				}
			}
		} else if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_INPUT_BEAN.equals(screenArea.getObjectMappingType())) {
			for (BusinessDesign blogic : businessDesigns) {
				if (form.getScreenFormIdStore() != null && form.getScreenFormIdStore().equals(blogic.getScreenFormId())) {
					// set parent input bean id
					for (InputBean parentInputbean : lstParentInputBeans) {
						if (blogic.getBusinessLogicId().equals(parentInputbean.getBusinessLogicId()) && parentInputbean.getInputBeanId() != null && DataTypeUtils.equals(parentInputbean.getInputBeanId(), screenArea.getObjectMappingId())) {
							parentInputBeanId = parentInputbean.getInputBeanId();
							break;
						}
					}
					inputBean.setParentInputBeanId(parentInputBeanId);
				}
			}
		}
		if (DbDomainConst.LogicDataType.CHECKBOX.equals(item.getLogicalDataType()) && inputBean.getDataType() != null && inputBean.getDataType().intValue() != DbDomainConst.BaseType.BOOLEAN_BASETYPE) {
			inputBean.setArrayFlg(Boolean.TRUE);
		}
		return inputBean;
	}
	
	private InputBean populateInputBeanAutoCompleteLabel(String inputBeanCode, ScreenItem item, ScreenForm form, ScreenArea screenArea, List<BusinessDesign> businessDesigns, List<InputBean> lstParentInputBeans) {
		InputBean inputBean = new InputBean();
		inputBean.setInputBeanCode(inputBeanCode);
		if (item.getMessageDesign() != null) {
			inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
		} else {
			inputBean.setInputBeanName("");
		}
		inputBean.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
		inputBean.setScreenItemId(item.getScreenItemId());
		inputBean.setScreenItem(item);
		inputBean.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
		// set input bean into parent object if have

		String parentInputBeanId = null;
		if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_TABLE_DESIGN.equals(screenArea.getObjectMappingType())) {
			for (BusinessDesign blogic : businessDesigns) {
				if (form.getScreenFormIdStore() != null && form.getScreenFormIdStore().equals(blogic.getScreenFormId())) {
					// set parent input bean id
					for (InputBean parentInputbean : lstParentInputBeans) {
						if (blogic.getBusinessLogicId().equals(parentInputbean.getBusinessLogicId()) && parentInputbean.getTblDesignId() != null && parentInputbean.getTblDesignId().equals(screenArea.getObjectMappingId())) {
							parentInputBeanId = parentInputbean.getInputBeanId();
							break;
						}
					}
					inputBean.setParentInputBeanId(parentInputBeanId);
				}
			}
		} else if (ScreenDesignConst.OBJECT_MAPPING_TYPE.MAPPING_INPUT_BEAN.equals(screenArea.getObjectMappingType())) {
			for (BusinessDesign blogic : businessDesigns) {
				if (form.getScreenFormIdStore() != null && form.getScreenFormIdStore().equals(blogic.getScreenFormId())) {
					// set parent input bean id
					for (InputBean parentInputbean : lstParentInputBeans) {
						if (blogic.getBusinessLogicId().equals(parentInputbean.getBusinessLogicId()) && parentInputbean.getInputBeanId() != null && DataTypeUtils.equals(parentInputbean.getInputBeanId(), screenArea.getObjectMappingId())) {
							parentInputBeanId = parentInputbean.getInputBeanId();
							break;
						}
					}
					inputBean.setParentInputBeanId(parentInputBeanId);
				}
			}
		}
		return inputBean;
	}

	private void recursiveInputBean(InputBean inputBean, List<InputBean> inputBeanDelete, List<InputBean> allInputBeans) {
		if (inputBean.getInputBeanId() != null) {
			List<InputBean> lstInputBean = getChildInputBean(allInputBeans, inputBean.getInputBeanId());
			for (InputBean input : lstInputBean) {
				recursiveInputBean(input, inputBeanDelete, allInputBeans);
			}
			if (!inputBeanDelete.contains(inputBean)) {
				inputBeanDelete.add(inputBean);
			}
		}
	}

	private List<InputBean> getChildInputBean(List<InputBean> allInputBeans, String inputBeanId) {
		List<InputBean> childInputBeans = new ArrayList<InputBean>();
		for (InputBean inputBean : allInputBeans) {
			if (inputBean.getParentInputBeanId() != null && inputBean.getParentInputBeanId().equals(inputBeanId)) {
				childInputBeans.add(inputBean);
			}
		}
		return childInputBeans;
	}

	@Override
	public List<InputBean> buildInputBeanFromScreenId(Long screenId, Integer requestMethod, Long screenFormId,Long projectId,Long languageId) {
		List<InputBean> lstInputBeans = new ArrayList<InputBean>();
		ScreenDesign screenDesign = screendesignRepository.findById(screenId, languageId, projectId);
		if (screenDesign != null) {
			if (BusinessDesignConst.REQUEST_METHOD_INITIAL.equals(requestMethod)) {
				ScreenParameter[] screenParameters = screendesignRepository.getScreenParameterByScreenId(screenId);
				if (screenParameters != null) {
					for (ScreenParameter param : screenParameters) {
						InputBean inputBean = new InputBean();
						inputBean = new InputBean();
						inputBean.setInputBeanCode(param.getScreenParamCode());
						inputBean.setInputBeanName(param.getScreenParamName());
						inputBean.setMessageStringAutocomplete(param.getScreenParamName());
						inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.DEFAULT);
						inputBean.setDataType(param.getDataType());
						inputBean.setObjectFlg(false);
						lstInputBeans.add(inputBean);
					}
				}
			} else if (BusinessDesignConst.REQUEST_METHOD_PROCESSING.equals(requestMethod)) {
				// build tu screen item of form
				List<ScreenItem> lstScreenItem = screenItemRepository.getListScreenItemByScreenIdAndAjax(screenId,languageId,projectId);
				if(lstScreenItem == null)
				{
					lstScreenItem = new ArrayList<ScreenItem>();
				}
				List<ScreenArea> screenAreas = screenAreaRepository.getLstScreenAreaByScreenId(screenId, languageId, projectId);
				ScreenForm form = screenFormRepository.getById(screenFormId);
				Integer tempInputBeanId = 0, tempParentObjectId = 0, tempParentMultilepartFileId = 0;
				Map<Long, List<ScreenItem>> mapAreaAndItem = new HashMap<Long, List<ScreenItem>>();
				if (screenAreas != null) {
					for (ScreenArea area : screenAreas) {
						if (form.getScreenFormId().equals(area.getScreenFormId())) {
							List<ScreenItem> lstItemOfThisArea = new ArrayList<ScreenItem>();
							for (ScreenItem item : lstScreenItem) {
								if (area.getScreenAreaId().equals(item.getScreenAreaId())) {
									if (item.getScreenArea() == null) {
										item.setScreenArea(area);
									}
									lstItemOfThisArea.add(item);
								}
							}
							mapAreaAndItem.put(area.getScreenAreaId(), lstItemOfThisArea);
						}
					}
				}
				ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionRepository.findExtObjDefIsMultipartFileByProjectId(projectId);
				List<ExternalObjectAttribute> lstExternalObjectAttribute = new ArrayList<ExternalObjectAttribute>();
				if (externalObjectDefinition != null) {
					lstExternalObjectAttribute = externalObjectAttributeRepository.findExternalObjectAttributeByExternalObjectDefinitionId(externalObjectDefinition.getExternalObjectDefinitionId());
				}
				Set<String> hashSetAreaCode = new HashSet<String>();
				Map<String, InputBean> mapAreaCode = new HashMap<String, InputBean>();
				if (screenAreas != null) {
					for (ScreenArea area : screenAreas) {
						InputBean parentInputBean = null;
						if (form.getScreenFormId().equals(area.getScreenFormId())) {
							List<ScreenItem> lstScreenItemOfThisArea = mapAreaAndItem.get(area.getScreenAreaId()) != null ? mapAreaAndItem.get(area.getScreenAreaId()) : new ArrayList<ScreenItem>();
							for (ScreenItem item : lstScreenItemOfThisArea) {
								if (item.getLogicalDataType() == null || DbDomainConst.LogicDataType.LABEL.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.BUTTON.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.LINK.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.LINK_DYNAMIC.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.CUSTOM_ITEM.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.CUSTOM_SECTION.equals(item.getLogicalDataType()) || (DbDomainConst.LogicDataType.LABEL_DYNAMIC.equals(item.getLogicalDataType()) && !ScreenDesignConst.ScreenItemConst.ITEM_TYPE_HIDDEN.equals(item.getItemType()))) {
									continue;
								}
								ScreenArea screenArea = item.getScreenArea();
								if (screenArea != null) {
									if (parentInputBean == null) {
										// Adding a object parent input bean
										if (!hashSetAreaCode.contains(screenArea.getAreaCode())) {
											parentInputBean = new InputBean();
											parentInputBean.setInputBeanId(tempInputBeanId.toString());
											parentInputBean.setInputBeanCode(screenArea.getAreaCode());
											if(screenArea.getMessageDesign() != null){
												parentInputBean.setMessageStringAutocomplete(screenArea.getMessageDesign().getMessageString());
												parentInputBean.setInputBeanName(screenArea.getMessageDesign().getMessageCode());
											}else{
												parentInputBean.setMessageStringAutocomplete(StringUtils.EMPTY);
												parentInputBean.setInputBeanName(StringUtils.EMPTY);
											}
											parentInputBean.setInputBeanType(BusinessDesignConst.InputBeanType.DEFAULT);
											parentInputBean.setDataType(BusinessDesignConst.DataType.OBJECT);
											if (ScreenDesignConst.AreaType.LIST.equals(screenArea.getAreaType())) {
												parentInputBean.setArrayFlg(Boolean.TRUE);
											} else {
												parentInputBean.setArrayFlg(Boolean.FALSE);
											}
											parentInputBean.setObjectFlg(Boolean.FALSE);
											// add to list
											lstInputBeans.add(parentInputBean);
											hashSetAreaCode.add(screenArea.getAreaCode());
											mapAreaCode.put(screenArea.getAreaCode(), parentInputBean);
											tempParentObjectId = tempInputBeanId;
											tempInputBeanId++;
										} else {
											parentInputBean = mapAreaCode.get(screenArea.getAreaCode());
											if (parentInputBean != null) {
												tempParentObjectId = Integer.parseInt(parentInputBean.getInputBeanId());
											}
										}
									}

									if (DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getLogicalDataType()) && externalObjectDefinition != null) {
										if (CollectionUtils.isNotEmpty(lstExternalObjectAttribute)) {
											// Assign object parent form object
											// definition
											InputBean inputBean = new InputBean();
											inputBean.setInputBeanId(tempInputBeanId.toString());
											inputBean.setInputBeanCode(item.getItemCode());
											inputBean.setInputBeanName(ScreenDesignConst.MULTIPART_FILE);
											inputBean.setMessageStringAutocomplete(ScreenDesignConst.MULTIPART_FILE);
											inputBean.setScreenItemId(item.getScreenItemId());
											inputBean.setScreenItem(item);
											inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.DEFAULT);
											inputBean.setDataType(BusinessDesignConst.DataType.EXTERNAL_OBJECT);
											inputBean.setArrayFlg(Boolean.FALSE);
											inputBean.setParentInputBeanId(tempParentObjectId.toString());
											inputBean.setObjectType(DbDomainConst.ObjectType.EXTERNAL_OBJECT);
											inputBean.setObjectId(externalObjectDefinition.getExternalObjectDefinitionId());
											inputBean.setObjectFlg(Boolean.TRUE);
											inputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
											inputBean.setFlagUsingTempId(true);
											// add to list
											lstInputBeans.add(inputBean);
											tempParentMultilepartFileId = tempInputBeanId;
											tempInputBeanId++;

											// Assign object parent form object
											// attribute
											for (ExternalObjectAttribute extObjAttrIter : lstExternalObjectAttribute) {
												inputBean = new InputBean();
												inputBean.setInputBeanId(tempInputBeanId.toString());
												inputBean.setInputBeanCode(extObjAttrIter.getExternalObjectAttributeCode());
												inputBean.setInputBeanName(extObjAttrIter.getExternalObjectAttributeName());
												inputBean.setMessageStringAutocomplete(extObjAttrIter.getExternalObjectAttributeName());
												inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.ADDED_DEFAULT);
												inputBean.setDataType(extObjAttrIter.getDataType());
												inputBean.setArrayFlg(extObjAttrIter.getArrayFlg());
												inputBean.setParentInputBeanId(tempParentMultilepartFileId.toString());
												inputBean.setObjectType(DbDomainConst.ObjectType.EXTERNAL_ATTRIBUTE);
												inputBean.setObjectId(Long.valueOf(extObjAttrIter.getExternalObjectAttributeId()));
												inputBean.setObjectFlg(Boolean.TRUE);
												inputBean.setScopeType(BusinessDesignConst.PREFIX_MAPPING.INPUTBEAN_ID);
												inputBean.setFlagUsingTempId(true);
												// add to list
												lstInputBeans.add(inputBean);
												tempInputBeanId++;
											}
										}
										
									} if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(item.getLogicalDataType())) {
										InputBean inputBean = new InputBean();
										inputBean.setInputBeanCode(item.getItemCode());
										if (item.getMessageDesign() != null) {
											inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
											inputBean.setMessageStringAutocomplete(item.getMessageDesign().getMessageString());
										} else {
											inputBean.setInputBeanName(StringUtils.EMPTY);
											inputBean.setMessageStringAutocomplete(StringUtils.EMPTY);
										}
										if (item.getPhysicalDataType() != null) {
											inputBean.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
										}
										inputBean.setScreenItemId(item.getScreenItemId());
										inputBean.setScreenItem(item);
										inputBean.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
										inputBean.setParentInputBeanId(tempParentObjectId.toString());
										inputBean.setFlagUsingTempId(true);
										inputBean.setObjectFlg(Boolean.FALSE);
										if (DbDomainConst.LogicDataType.CHECKBOX.equals(item.getLogicalDataType()) && inputBean.getDataType() != null && inputBean.getDataType().intValue() != DbDomainConst.BaseType.BOOLEAN_BASETYPE) {
											inputBean.setArrayFlg(Boolean.TRUE);
										}
										lstInputBeans.add(inputBean);
										try {
											InputBean inputBeanLabel = (InputBean) inputBean.clone();
											inputBeanLabel.setInputBeanCode(inputBeanLabel.getInputBeanCode()+"Autocomplete");
											inputBeanLabel.setDataType(DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE);
	                                        lstInputBeans.add(inputBeanLabel);
                                        } catch (CloneNotSupportedException e) {
                                        }
									}else {
										// Normal case
										if (!DbDomainConst.ScreenPatternType.SEARCH.equals(screenDesign.getScreenPatternType())) {
											InputBean inputBean = new InputBean();
											inputBean.setInputBeanCode(item.getItemCode());
											if (item.getMessageDesign() != null) {
												inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
												inputBean.setMessageStringAutocomplete(item.getMessageDesign().getMessageString());
											} else {
												inputBean.setInputBeanName(StringUtils.EMPTY);
												inputBean.setMessageStringAutocomplete(StringUtils.EMPTY);
											}
											if (item.getPhysicalDataType() != null) {
												inputBean.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
											}
											inputBean.setScreenItemId(item.getScreenItemId());
											inputBean.setScreenItem(item);
											inputBean.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
											inputBean.setParentInputBeanId(tempParentObjectId.toString());
											inputBean.setFlagUsingTempId(true);
											inputBean.setObjectFlg(Boolean.FALSE);
											if (DbDomainConst.LogicDataType.CHECKBOX.equals(item.getLogicalDataType()) && inputBean.getDataType() != null && inputBean.getDataType().intValue() != DbDomainConst.BaseType.BOOLEAN_BASETYPE) {
												inputBean.setArrayFlg(Boolean.TRUE);
											}
											lstInputBeans.add(inputBean);
										} else {
											if (DbDomainConst.LogicDataType.CURRENCY.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DECIMAL.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.INTEGER.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DATETIME.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.DATE.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.TIME.equals(item.getLogicalDataType())) {
												item.setDisplayFromTo(ScreenDesignConst.FromTo.FROM_TO);
												InputBean inputBeanFrom = new InputBean();
												inputBeanFrom.setInputBeanCode(item.getItemCode() + "From");
												if (item.getMessageDesign() != null) {
													inputBeanFrom.setInputBeanName(item.getMessageDesign().getMessageCode());
													inputBeanFrom.setMessageStringAutocomplete(item.getMessageDesign().getMessageString());
												} else {
													inputBeanFrom.setInputBeanName("");
												}
												if (item.getPhysicalDataType() != null) {
													inputBeanFrom.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
												}
												inputBeanFrom.setScreenItemId(item.getScreenItemId());
												inputBeanFrom.setScreenItem(item);
												inputBeanFrom.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
												inputBeanFrom.setParentInputBeanId(tempParentObjectId.toString());
												inputBeanFrom.setFlagUsingTempId(true);
												inputBeanFrom.setObjectFlg(Boolean.FALSE);
//												inputBeanFrom.setDisplayType(ScreenDesignConst.FromTo.FROM_TO);
												lstInputBeans.add(inputBeanFrom);

												InputBean inputBeanTo = new InputBean();
												inputBeanTo.setInputBeanCode(item.getItemCode() + "To");
												if (item.getMessageDesign() != null) {
													inputBeanTo.setInputBeanName(item.getMessageDesign().getMessageCode());
													inputBeanTo.setMessageStringAutocomplete(item.getMessageDesign().getMessageString());
												} else {
													inputBeanTo.setInputBeanName("");
												}
												if (item.getPhysicalDataType() != null) {
													inputBeanTo.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
												}
												inputBeanTo.setScreenItemId(item.getScreenItemId());
												inputBeanTo.setScreenItem(item);
												inputBeanTo.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
												inputBeanTo.setParentInputBeanId(tempParentObjectId.toString());
												inputBeanTo.setFlagUsingTempId(true);
												inputBeanTo.setObjectFlg(Boolean.FALSE);
//												inputBeanTo.setDisplayType(ScreenDesignConst.FromTo.FROM_TO);
												lstInputBeans.add(inputBeanTo);
											} else {
												InputBean inputBean = new InputBean();
												inputBean.setInputBeanCode(item.getItemCode());
												if (item.getMessageDesign() != null) {
													inputBean.setInputBeanName(item.getMessageDesign().getMessageCode());
													inputBean.setMessageStringAutocomplete(item.getMessageDesign().getMessageString());
												} else {
													inputBean.setInputBeanName("");
													inputBean.setMessageString(StringUtils.EMPTY);
												}
												if (item.getPhysicalDataType() != null) {
													inputBean.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
												}
												inputBean.setScreenItemId(item.getScreenItemId());
												inputBean.setScreenItem(item);
												inputBean.setInputBeanType(DbDomainConst.InputBeanType.DEFAULT);
												inputBean.setParentInputBeanId(tempParentObjectId.toString());
												inputBean.setFlagUsingTempId(true);
												inputBean.setObjectFlg(Boolean.FALSE);
												if (DbDomainConst.LogicDataType.CHECKBOX.equals(item.getLogicalDataType()) && inputBean.getDataType() != null && inputBean.getDataType().intValue() != DbDomainConst.BaseType.BOOLEAN_BASETYPE) {
													inputBean.setArrayFlg(Boolean.TRUE);
												}
												
												lstInputBeans.add(inputBean);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(lstInputBeans)){
			int itemSeqNo = 0;
			ScreenItemOutput screenItemOutput = new ScreenItemOutput();
			Map<Long,String> mapCheckScreenItemFromTo= new HashMap<Long,String>();
			Map<Long,String> mapCheckScreenItemAutocomplete= new HashMap<Long,String>();
			for (InputBean inputBean : lstInputBeans) {
				inputBean.setItemSequenceNo(itemSeqNo);
				inputBean.setInputBeanType(BusinessDesignConst.InputBeanType.DEFAULT);
				if(inputBean.getScreenItem() != null){
					//mapping screen item
					screenItemOutput = new ScreenItemOutput();
					screenItemOutput.setScreenId(inputBean.getScreenItem().getScreenItemId());
					
//					if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(inputBean.getScreenItem().getLogicalDataType()) || DbDomainConst.LogicDataType.CHECKBOX.equals(inputBean.getScreenItem().getLogicalDataType()) || DbDomainConst.LogicDataType.RADIO.equals(inputBean.getScreenItem().getLogicalDataType()) || DbDomainConst.LogicDataType.SELECT.equals(inputBean.getScreenItem().getLogicalDataType())) {
//						screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_SUBMIT);
//						screenItemOutput.setItemName(inputBean.getInputBeanCode() + BusinessDesignHelper.getMappingNameOfMappingOuput(screenItemOutput.getMappingType(),inputBean.getScreenItem().getLogicalDataType()));
//					}else{
//						screenItemOutput.setItemName(inputBean.getInputBeanCode());
//					}
//					
//					inputBean.setScreenItemMapping(screenItemOutput);
					
					screenItemOutput.setLogicalDataType(inputBean.getScreenItem().getLogicalDataType());
					screenItemOutput.setDisplayFromTo(inputBean.getScreenItem().getDisplayFromTo());
					screenItemOutput.setItemName(inputBean.getScreenItem().getItemCode());
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
				}
				itemSeqNo++;
			}
		}
		return lstInputBeans;
	}
	
	@Override
	public String getCodelistByScreenItem(ScreenItem item){
		StringBuilder codelistInfo = null;
		
		if(item.getCodelistType() != null && item.getCodelistType().equals(new Integer(1))){
			codelistInfo = new StringBuilder();
			codelistInfo.append(item.getCodelistType());
			codelistInfo.append(INVERSE_BULLET_SYSBOL);
			CodeList codeList = codeListService.getCodeList(item.getCodelistId());
			
			codelistInfo.append(codeList.getCodeListName());
			codelistInfo.append(INVERSE_BULLET_SYSBOL);
			codelistInfo.append(codeList.getIsOptionValude());
			codelistInfo.append(INVERSE_BULLET_SYSBOL);
			
			for (int i = 0; i < codeList.getCodelistDetails().length; i++) {
				CodeListDetail codeListDetail = codeList.getCodelistDetails()[i];
				codelistInfo.append(codeListDetail.getName()).append(REPLACEMENT_CHARACTER_SYSBOL);
				codelistInfo.append(codeListDetail.getValue()).append(INVERSE_BULLET_SYSBOL);
				if(item.getDefaultValue() != null && item.getDefaultValue().equals(codeListDetail.getValue())){
					item.setDefaultLabel(codeListDetail.getName());
				}
			}
		} else if(item.getCodelistType() != null && item.getCodelistType().equals(new Integer(3))){
			codelistInfo = new StringBuilder();
			codelistInfo.append(item.getCodelistType());
			codelistInfo.append(INVERSE_BULLET_SYSBOL);
			List<ScreenItemCodelist> screenItemCodelists = screenItemCodelistRepository.getScreenItemCodelistByScreenItemId(item.getScreenItemId());
			codelistInfo.append("null");
			codelistInfo.append(INVERSE_BULLET_SYSBOL);
			if (FunctionCommon.isNotEmpty(screenItemCodelists)) {
				codelistInfo.append(screenItemCodelists.get(0).getSupportOptionValFlg());
			}
			codelistInfo.append(INVERSE_BULLET_SYSBOL);
			for (ScreenItemCodelist screenItemCodelist : screenItemCodelists) {
				codelistInfo.append(screenItemCodelist.getCodelistName()).append(REPLACEMENT_CHARACTER_SYSBOL);
				codelistInfo.append(screenItemCodelist.getCodelistVal()).append(INVERSE_BULLET_SYSBOL);
				if(item.getDefaultValue() != null && item.getDefaultValue().equals(screenItemCodelist.getCodelistVal())){
					item.setDefaultLabel(screenItemCodelist.getCodelistName());
				}
			}
		}
		if(codelistInfo != null){
			return codelistInfo.toString();
		}else{
			return null;
		}
	}

	@Override
    public List<OutputBean> buildOutputBeanFromScreenId(Long screenId, Long projectId, Long languageId) {
		List<OutputBean> lstOutputBeans = new ArrayList<OutputBean>();
		ScreenDesign screenDesign = screendesignRepository.findById(screenId, languageId, projectId);
		if (screenDesign != null) {
			// build tu screen item of form
			List<ScreenItem> lstScreenItem = screenItemRepository.getListScreenItemByScreenIdAndAjax(screenId,languageId,projectId);
			if(lstScreenItem == null)
			{
				lstScreenItem = new ArrayList<ScreenItem>();
			}
			List<ScreenArea> screenAreas = screenAreaRepository.getLstScreenAreaByScreenId(screenId, languageId, projectId);
			Integer tempOutputBeanId = 0;
			Map<Long, List<ScreenItem>> mapAreaAndItem = new HashMap<Long, List<ScreenItem>>();
			if (screenAreas != null) {
				for (ScreenArea area : screenAreas) {
					List<ScreenItem> lstItemOfThisArea = new ArrayList<ScreenItem>();
					for (ScreenItem item : lstScreenItem) {
						if (area.getScreenAreaId().equals(item.getScreenAreaId())) {
							if (item.getLogicalDataType() == null || DbDomainConst.LogicDataType.LABEL.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.BUTTON.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.LINK.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.CUSTOM_ITEM.equals(item.getLogicalDataType()) || DbDomainConst.LogicDataType.CUSTOM_SECTION.equals(item.getLogicalDataType())) {
								
							}else{
								item.setScreenArea(area);
								lstItemOfThisArea.add(item);
							}
						}
					}
					mapAreaAndItem.put(area.getScreenAreaId(), lstItemOfThisArea);
				}
			}
//			ExternalObjectDefinition externalObjectDefinition = externalObjectDefinitionRepository.findExtObjDefIsMultipartFileByProjectId(projectId);
//			List<ExternalObjectAttribute> lstExternalObjectAttribute = new ArrayList<ExternalObjectAttribute>();
//			if (externalObjectDefinition != null) {
//				lstExternalObjectAttribute = externalObjectAttributeRepository.findExternalObjectAttributeByExternalObjectDefinitionId(externalObjectDefinition.getExternalObjectDefinitionId());
//			}
			Set<String> hashSetAreaCode = new HashSet<String>();
			Map<String, OutputBean> mapAreaCode = new HashMap<String, OutputBean>();
			if (screenAreas != null) {
				for (ScreenArea screenArea : screenAreas) {
					OutputBean parentOutputBean = new OutputBean();
					OutputBean outputBean = new OutputBean();
					List<ScreenItem> lstScreenItemOfThisArea = mapAreaAndItem.getOrDefault(screenArea.getScreenAreaId(), new ArrayList<ScreenItem>());
					if(CollectionUtils.isNotEmpty(lstScreenItemOfThisArea)){
						parentOutputBean.setOutputBeanId(tempOutputBeanId.toString());
						parentOutputBean.setOutputBeanCode(screenArea.getAreaCode());
						if(screenArea.getMessageDesign() != null){
							parentOutputBean.setOutputBeanName(screenArea.getMessageDesign().getMessageString());
						}else{
							parentOutputBean.setOutputBeanName(StringUtils.EMPTY);
						}
						parentOutputBean.setDataType(BusinessDesignConst.DataType.OBJECT);
						if (ScreenDesignConst.AreaType.LIST.equals(screenArea.getAreaType())) {
							parentOutputBean.setArrayFlg(Boolean.TRUE);
						} else {
							parentOutputBean.setArrayFlg(Boolean.FALSE);
						}
						parentOutputBean.setObjectFlg(Boolean.TRUE);
						// add to list
						lstOutputBeans.add(parentOutputBean);
						hashSetAreaCode.add(screenArea.getAreaCode());
						mapAreaCode.put(screenArea.getAreaCode(), parentOutputBean);
						tempOutputBeanId++;
						for (ScreenItem item : lstScreenItemOfThisArea) {
							outputBean = new OutputBean();
							outputBean.setOutputBeanCode(item.getItemCode());
							if (item.getMessageDesign() != null) {
								outputBean.setOutputBeanName(item.getMessageDesign().getMessageString());
							} else {
								outputBean.setOutputBeanName(StringUtils.EMPTY);
							}
							if (item.getPhysicalDataType() != null) {
								outputBean.setDataType(BusinessDesignHelper.convertJavaTypeFromBaseType(item.getPhysicalDataType()));
							}
							outputBean.setScreenItemId(item.getScreenItemId());
							outputBean.setParentOutputBeanId(parentOutputBean.getOutputBeanId());
							outputBean.setObjectFlg(Boolean.TRUE);
							if (DbDomainConst.LogicDataType.FILEUPLOAD.equals(item.getLogicalDataType()) || (DbDomainConst.LogicDataType.CHECKBOX.equals(item.getLogicalDataType()) && outputBean.getDataType().intValue() != DbDomainConst.BaseType.BOOLEAN_BASETYPE)) {
								outputBean.setArrayFlg(Boolean.TRUE);
							}
							outputBean.setOutputBeanId(tempOutputBeanId.toString());
							tempOutputBeanId++;
							initScreenMappingOfOutput(outputBean,item);
							lstOutputBeans.add(outputBean);
						}
					}
				}
			}
		}
		return lstOutputBeans;
    }
	
	/*
	 * create screen mapping
	 */
	private void initScreenMappingOfOutput(OutputBean outputBean,ScreenItem screenItem){
		List<ScreenItemOutput> lstItemOutputs = new ArrayList<ScreenItemOutput>();
		ScreenItemOutput screenItemOutput = new ScreenItemOutput();
		String itemName = screenItem.getItemCode() != null ? screenItem.getItemCode() : "";
		screenItemOutput.setScreenId(screenItem.getScreenItemId());
		if (DbDomainConst.LogicDataType.AUTOCOMPLETE.equals(screenItem.getLogicalDataType()) || DbDomainConst.LogicDataType.CHECKBOX.equals(screenItem.getLogicalDataType()) || DbDomainConst.LogicDataType.RADIO.equals(screenItem.getLogicalDataType()) || DbDomainConst.LogicDataType.SELECT.equals(screenItem.getLogicalDataType())) {
			screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_SELECT);
			screenItemOutput.setItemName(itemName + BusinessDesignHelper.getMappingNameOfMappingOuput(screenItemOutput.getMappingType(),screenItem.getLogicalDataType()));
		}else{
			screenItemOutput.setItemName(itemName);
			screenItemOutput.setMappingType(BusinessDesignConst.OutputbeanScreenitemMapping.TYPE_OPTION_DISPLAY);
		}
		screenItemOutput.setScreenItemId(screenItem.getScreenItemId());
		lstItemOutputs.add(screenItemOutput);
		outputBean.setLstScreenItemMapping(lstItemOutputs);
	}

	@Override
	public List<ScreenTransition> findAllTransitionByModuleId(Long moduleId, Long projectId, Long languageId, List<ScreenDesign> scrDesigns) {
		List<ScreenTransition> lstScreenTransitions = screenTransitionRepository.findAllTransitionByModuleId(moduleId, projectId, languageId);
		List<ScreenTransitionBranch> transitionBranchs = screenTransitionRepository.findAllTransitionBranchByModuleId(moduleId, projectId);
		ScreenDesign screenDesign = null;
		MessageDesign messageDesign = null;
		long screenId = -1;
		for (ScreenTransition screenTransition : lstScreenTransitions) {
			if((screenTransition.getToScreen() == null || Long.parseLong(screenTransition.getToScreen()) <= 0) && screenTransition.getType() != 3){
				screenDesign = new ScreenDesign();
				messageDesign = new MessageDesign();
				messageDesign.setMessageString("Screen Common");
				screenDesign.setTemplateType(-1);
				screenDesign.setScreenId(screenId);
				
				if(screenTransition.getType() == 0){
					for (ScreenDesign sd : scrDesigns) {
						if(sd.getScreenId().toString().equals(screenTransition.getFromScreen())){
							screenDesign.setxCoordinate(sd.getxCoordinate());
							screenDesign.setyCoordinate(sd.getyCoordinate() + 100);
						}
					}
				}else if(screenTransition.getType() == 1){
					for (ScreenTransitionBranch screenTransitionBranch : transitionBranchs) {
						if(screenTransitionBranch.getBranchId().equals(screenTransition.getFromScreen())){
							screenDesign.setxCoordinate(screenTransitionBranch.getxCoordinates());
							screenDesign.setyCoordinate(screenTransitionBranch.getyCoordinates() + 100);
						}
					}
				}
				
				screenDesign.setMessageDesign(messageDesign);
				scrDesigns.add(screenDesign);
				screenTransition.setToScreen(String.valueOf(screenId));
				screenId--;
			}
			
			if((screenTransition.getFromScreen() == null || Long.parseLong(screenTransition.getFromScreen()) <= 0) && screenTransition.getType() == 4){
				screenDesign = new ScreenDesign();
				messageDesign = new MessageDesign();
				messageDesign.setMessageString("Screen Common");
				screenDesign.setTemplateType(-1);
				screenDesign.setScreenId(screenId);
				
				for (ScreenDesign sd : scrDesigns) {
					if(sd.getScreenId().toString().equals(screenTransition.getToScreen())){
						screenTransition.setFromScreen(sd.getScreenId().toString());
						screenDesign.setxCoordinate(sd.getxCoordinate());
						screenDesign.setyCoordinate(sd.getyCoordinate() + 100);
					}
				}
				
				screenDesign.setMessageDesign(messageDesign);
				scrDesigns.add(screenDesign);
				screenTransition.setToScreen(String.valueOf(screenId));
				screenId--;
			}
			
//			if(screenTransition.getFromScreen() == null || Long.parseLong(screenTransition.getFromScreen()) <= 0){
//				screenDesign = new ScreenDesign();
//				messageDesign = new MessageDesign();
//				messageDesign.setMessageString("Screen Common");
//				screenDesign.setTemplateType(-1);
//				screenDesign.setScreenId(screenId);
//				
//				if(screenTransition.getType() == 0){
//					for (ScreenDesign sd : scrDesigns) {
//						if(sd.getScreenId().equals(screenTransition.getFromScreen())){
//							screenDesign.setxCoordinate(sd.getxCoordinate());
//							screenDesign.setyCoordinate(sd.getyCoordinate() + 100);
//						}
//					}
//				}else if(screenTransition.getType() == 1){
//					for (ScreenTransitionBranch screenTransitionBranch : transitionBranchs) {
//						if(screenTransitionBranch.getBranchId().equals(screenTransition.getFromScreen())){
//							screenDesign.setxCoordinate(screenTransitionBranch.getxCoordinates());
//							screenDesign.setyCoordinate(screenTransitionBranch.getyCoordinates() + 100);
//						}
//					}
//				}
//				screenDesign.setMessageDesign(messageDesign);
//				scrDesigns.add(screenDesign);
//				screenTransition.setToScreen(String.valueOf(screenId));
//				screenId--;
//			}
		}
		return lstScreenTransitions;
	}
	
	private void modifyValidationCheckDetails(List<InputBean> inputBeanUpdate, ScreenItem[] screenItems, AccountProfile accountProfile) {
		List<String> lstInputBeanId = new ArrayList<String>();
		for(InputBean inUpdate : inputBeanUpdate) {
			lstInputBeanId.add(inUpdate.getInputBeanId());
		}
		List<ValidationCheckDetail> lstValidateInputOld = validationCheckDetailRepository.findValidationCheckDetailsByInputBeanIds(lstInputBeanId);
		List<ValidationCheckDetail> lstDeleteValidate = new ArrayList<ValidationCheckDetail>();
		if(FunctionCommon.isNotEmpty(lstValidateInputOld)) {
			for (InputBean in : inputBeanUpdate) {
				for (ValidationCheckDetail vali : lstValidateInputOld) {
					if (in.getInputBeanId().equals(vali.getInputBeanId())) {
						if(BusinessDesignConst.ValidateType.NOT_EMPTY == vali.getValidationType() ||
								BusinessDesignConst.ValidateType.NOT_NULL == vali.getValidationType() ||
								BusinessDesignConst.ValidateType.QP_SIZE == vali.getValidationType() ||
								BusinessDesignConst.ValidateType.SIZE == vali.getValidationType() ||
								BusinessDesignConst.ValidateType.DECIMAL_MIN == vali.getValidationType() ||
								BusinessDesignConst.ValidateType.MIN == vali.getValidationType() ||
								BusinessDesignConst.ValidateType.TIME_MIN == vali.getValidationType() ||
								BusinessDesignConst.ValidateType.DATE_MIN == vali.getValidationType() ||
								BusinessDesignConst.ValidateType.DATE_TIME_MIN == vali.getValidationType() ||
								BusinessDesignConst.ValidateType.DECIMAL_MAX == vali.getValidationType() ||
								BusinessDesignConst.ValidateType.MAX == vali.getValidationType() ||
								BusinessDesignConst.ValidateType.TIME_MAX == vali.getValidationType() ||
								BusinessDesignConst.ValidateType.DATE_MAX == vali.getValidationType() ||
								BusinessDesignConst.ValidateType.DATE_TIME_MAX == vali.getValidationType()) {
							lstDeleteValidate.add(vali);
						}
					}
				}
			}
		}
		// delete old validate and insert new
		if (FunctionCommon.isNotEmpty(lstDeleteValidate)) {
			validationCheckDetailRepository.deleteValidationCheckDetail(lstDeleteValidate);
		}
		List<ValidationCheckDetail> lstCheckDetails = new ArrayList<ValidationCheckDetail>();
		for (InputBean inputBean : inputBeanUpdate) {
			ScreenItemValidation screenItemValidate = null;
			ScreenItem thisScreenItem = null;
			for (ScreenItem item : screenItems) {
				if (inputBean.getScreenItemId() != null && inputBean.getScreenItemId().equals(item.getScreenItemId())) {
					thisScreenItem = item;
					screenItemValidate = item.getScreenItemValidation();
					break;
				}
			}
			if(thisScreenItem != null && screenItemValidate != null) {
				// mandatory
				Integer validateType = null;
				if (ScreenDesignConst.MANDATORY.equals(screenItemValidate.getMandatoryFlg())) {
					if (DbDomainConst.JavaDataTypeOfBlogic.STRING_DATATYPE.equals(inputBean.getDataType())) {
						validateType = BusinessDesignConst.ValidateType.NOT_EMPTY;
					} else {
						validateType = BusinessDesignConst.ValidateType.NOT_NULL;
					}
					ValidationCheckDetail details = GenerateScreenContruct.populateValidationCheckDetail(inputBean, validateType);
					List<MessageParameter> params = new ArrayList<MessageParameter>();
					MessageParameter defaultParam = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), 0, inputBean.getInputBeanName(), null, BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
					params.add(defaultParam);
					details.setParameters(params);
					lstCheckDetails.add(details);
				}
				// maxlength
				validateType = null;
				if (screenItemValidate.getMaxlength() != null) {
					Integer javaType = BusinessDesignHelper.convertJavaTypeFromBaseType(thisScreenItem.getPhysicalDataType());
					if (BusinessDesignConst.DataType.STRING.equals(javaType) || BusinessDesignConst.DataType.BIGDECIMAL.equals(javaType)) {
						validateType = BusinessDesignConst.ValidateType.QP_SIZE;
					} else {
						validateType = BusinessDesignConst.ValidateType.SIZE;
					}
					ValidationCheckDetail details = GenerateScreenContruct.populateValidationCheckDetail(inputBean, validateType);
					List<MessageParameter> params = new ArrayList<MessageParameter>();
					int itemSeqNoOfMessageParameter = 0;
					MessageParameter defaultParam = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, inputBean.getInputBeanName(), null, BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
					params.add(defaultParam);
					MessageParameter defaultParamMin = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, null, "0", BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
					params.add(defaultParamMin);
					MessageParameter defaultParamMax = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, null, screenItemValidate.getMaxlength().toString(), BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
					params.add(defaultParamMax);
					details.setParameters(params);
					lstCheckDetails.add(details);
				}
				// min value
				validateType = null;
				if(screenItemValidate.getMinVal() != null) {
					Integer baseType = thisScreenItem.getPhysicalDataType();
					if (DbDomainConst.BaseType.TIME_BASETYPE == baseType || DbDomainConst.BaseType.DATE_BASETYPE == baseType || DbDomainConst.BaseType.DATETIME_BASETYPE == baseType) {
						String patternFormat = "";
						if (DbDomainConst.BaseType.TIME_BASETYPE == baseType) {
							validateType = BusinessDesignConst.ValidateType.TIME_MIN;
							patternFormat = DateUtils.getPatternDateTime(accountProfile.getTimeFormat());
						} else if (DbDomainConst.BaseType.DATE_BASETYPE == baseType) {
							validateType = BusinessDesignConst.ValidateType.DATE_MIN;
							patternFormat = accountProfile.getDateFormat();
						} else if (DbDomainConst.BaseType.DATETIME_BASETYPE == baseType) {
							validateType = BusinessDesignConst.ValidateType.DATE_TIME_MIN;
							patternFormat = DateUtils.getPatternDateTime(accountProfile.getDateTimeFormat());
						}
						ValidationCheckDetail details = GenerateScreenContruct.populateValidationCheckDetail(inputBean, validateType);
						List<MessageParameter> params = new ArrayList<MessageParameter>();
						int itemSeqNoOfMessageParameter = 0;
						MessageParameter defaultParam = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, inputBean.getInputBeanName(), null, BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
						params.add(defaultParam);
						MessageParameter defaultParamMin = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, null, screenItemValidate.getMinVal().toString(), BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
						defaultParamMin.setPatternFormat(patternFormat);
						params.add(defaultParamMin);
						details.setParameters(params);
						lstCheckDetails.add(details);
					} else {
						if(BusinessDesignConst.DataType.STRING.equals(inputBean.getDataType()) || BusinessDesignConst.DataType.BIGDECIMAL.equals(inputBean.getDataType())) {
							validateType = BusinessDesignConst.ValidateType.DECIMAL_MIN;
						} else {
							validateType = BusinessDesignConst.ValidateType.MIN;
						}
						ValidationCheckDetail details = GenerateScreenContruct.populateValidationCheckDetail(inputBean, validateType);
						
						List<MessageParameter> params = new ArrayList<MessageParameter>();
						int itemSeqNoOfMessageParameter = 0;
						MessageParameter defaultParam = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, inputBean.getInputBeanName(), null, BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
						params.add(defaultParam);
						MessageParameter defaultParamMin = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, null, screenItemValidate.getMinVal().toString(), BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
						params.add(defaultParamMin);
						details.setParameters(params);
						lstCheckDetails.add(details);
					}
				}
//				else {
//					Integer baseType = thisScreenItem.getPhysicalDataType();
//					if (DbDomainConst.BaseType.TIME_BASETYPE == baseType || DbDomainConst.BaseType.DATE_BASETYPE == baseType || DbDomainConst.BaseType.DATETIME_BASETYPE == baseType) {
//						String patternFormat = "", parameterCode = "";
//						if (DbDomainConst.BaseType.TIME_BASETYPE == baseType) {
//							validateType = BusinessDesignConst.ValidateType.TIME_MIN;
//							parameterCode = BusinessDesignConst.DEFAULT_TIME_MIN;
//							patternFormat = BusinessDesignConst.PATTEN_DEFAULT_TIME;
//						} else if (DbDomainConst.BaseType.DATE_BASETYPE == baseType) {
//							validateType = BusinessDesignConst.ValidateType.DATE_MIN;
//							parameterCode = BusinessDesignConst.DEFAULT_DATE_MIN;
//							patternFormat = BusinessDesignConst.PATTEN_DEFAULT_DATE;
//						} else if (DbDomainConst.BaseType.DATETIME_BASETYPE == baseType) {
//							validateType = BusinessDesignConst.ValidateType.DATE_TIME_MIN;
//							parameterCode = BusinessDesignConst.DEFAULT_DATETIME_MIN;
//							patternFormat = BusinessDesignConst.PATTEN_DEFAULT_DATETIME;
//						}
//						ValidationCheckDetail details = GenerateScreenContruct.populateValidationCheckDetail(inputBean, validateType);
//						
//						List<MessageParameter> params = new ArrayList<MessageParameter>();
//						int itemSeqNoOfMessageParameter = 0;
//						
//						MessageParameter defaultParam = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, inputBean.getInputBeanName(), null, BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
//						params.add(defaultParam);
//						MessageParameter defaultParamMin = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, null, parameterCode, BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
//						defaultParamMin.setPatternFormat(patternFormat);
//						params.add(defaultParamMin);
//						details.setParameters(params);
//						lstCheckDetails.add(details);
//					} else {
//						List<String> lstMinMax = GenerateScreenServiceImpl.defaultMinMax(thisScreenItem.getPhysicalDataType());
//						if (FunctionCommon.isNotEmpty(lstMinMax)) {
//							if (BusinessDesignConst.DataType.STRING.equals(inputBean.getDataType()) || BusinessDesignConst.DataType.BIGDECIMAL.equals(inputBean.getDataType())) {
//								validateType = BusinessDesignConst.ValidateType.DECIMAL_MIN;
//							} else {
//								validateType = BusinessDesignConst.ValidateType.MIN;
//							}
//							ValidationCheckDetail details = GenerateScreenContruct.populateValidationCheckDetail(inputBean, validateType);
//							
//							List<MessageParameter> params = new ArrayList<MessageParameter>();
//							int itemSeqNoOfMessageParameter = 0;
//							
//							MessageParameter defaultParam = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, inputBean.getInputBeanName(), null, BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
//							params.add(defaultParam);
//							MessageParameter defaultParamMin = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, null, lstMinMax.get(0), BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
//							params.add(defaultParamMin);
//							details.setParameters(params);
//							lstCheckDetails.add(details);
//						}
//					}
//				}
				
				// max value
				validateType = null;
				if(screenItemValidate.getMaxVal() != null) {
					Integer baseType = thisScreenItem.getPhysicalDataType();
					if (DbDomainConst.BaseType.TIME_BASETYPE == baseType || DbDomainConst.BaseType.DATE_BASETYPE == baseType || DbDomainConst.BaseType.DATETIME_BASETYPE == baseType) {
						String patternFormat = "";
						ValidationCheckDetail details = new ValidationCheckDetail();
						details.setInputBeanId(inputBean.getInputBeanId());
						details.setInputBeanCode(inputBean.getInputBeanCode());
						if (DbDomainConst.BaseType.TIME_BASETYPE == baseType) {
							validateType = BusinessDesignConst.ValidateType.TIME_MAX;
							patternFormat = DateUtils.getPatternDateTime(accountProfile.getTimeFormat());
						} else if (DbDomainConst.BaseType.DATE_BASETYPE == baseType) {
							validateType = BusinessDesignConst.ValidateType.DATE_MAX;
							patternFormat = accountProfile.getDateFormat();
						} else if (DbDomainConst.BaseType.DATETIME_BASETYPE == baseType) {
							validateType = BusinessDesignConst.ValidateType.DATE_TIME_MAX;
							patternFormat = DateUtils.getPatternDateTime(accountProfile.getDateTimeFormat());
						}
						details = GenerateScreenContruct.populateValidationCheckDetail(inputBean, validateType);
						
						List<MessageParameter> params = new ArrayList<MessageParameter>();
						int itemSeqNoOfMessageParameter = 0;
						MessageParameter defaultParam = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, inputBean.getInputBeanName(), null, BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
						params.add(defaultParam);
						MessageParameter defaultParamMax = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, null, screenItemValidate.getMaxVal().toString(), BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
						defaultParamMax.setPatternFormat(patternFormat);
						params.add(defaultParamMax);
						details.setParameters(params);
						lstCheckDetails.add(details);
					} else {
						if(BusinessDesignConst.DataType.STRING.equals(inputBean.getDataType()) || BusinessDesignConst.DataType.BIGDECIMAL.equals(inputBean.getDataType())) {
							validateType = BusinessDesignConst.ValidateType.DECIMAL_MAX;
						} else {
							validateType = BusinessDesignConst.ValidateType.MAX;
						}
						ValidationCheckDetail details = GenerateScreenContruct.populateValidationCheckDetail(inputBean, validateType);
						
						List<MessageParameter> params = new ArrayList<MessageParameter>();
						int itemSeqNoOfMessageParameter = 0;
						
						MessageParameter defaultParam = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, inputBean.getInputBeanName(), null, BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
						params.add(defaultParam);
						MessageParameter defaultParamMax = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, null, screenItemValidate.getMaxVal().toString(), BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
						params.add(defaultParamMax);
						details.setParameters(params);
						lstCheckDetails.add(details);
					}
				} 
//				else {
//					Integer baseType = thisScreenItem.getPhysicalDataType();
//					if (DbDomainConst.BaseType.TIME_BASETYPE == baseType || DbDomainConst.BaseType.DATE_BASETYPE == baseType || DbDomainConst.BaseType.DATETIME_BASETYPE == baseType) {
//						String patternFormat = "", parameterCode = "";
//						if (DbDomainConst.BaseType.TIME_BASETYPE == baseType) {
//							validateType = BusinessDesignConst.ValidateType.TIME_MAX;
//							parameterCode = BusinessDesignConst.DEFAULT_TIME_MAX;
//							patternFormat = BusinessDesignConst.PATTEN_DEFAULT_TIME;
//						} else if (DbDomainConst.BaseType.DATE_BASETYPE == baseType) {
//							validateType = BusinessDesignConst.ValidateType.DATE_MAX;
//							parameterCode = BusinessDesignConst.DEFAULT_DATE_MAX;
//							patternFormat = BusinessDesignConst.PATTEN_DEFAULT_DATE;
//						} else if (DbDomainConst.BaseType.DATETIME_BASETYPE == baseType) {
//							validateType = BusinessDesignConst.ValidateType.DATE_TIME_MAX;
//							parameterCode = BusinessDesignConst.DEFAULT_DATETIME_MAX;
//							patternFormat = BusinessDesignConst.PATTEN_DEFAULT_DATETIME;
//						}
//						ValidationCheckDetail details = GenerateScreenContruct.populateValidationCheckDetail(inputBean, validateType);
//						
//						List<MessageParameter> params = new ArrayList<MessageParameter>();
//						int itemSeqNoOfMessageParameter = 0;
//						MessageParameter defaultParam = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, inputBean.getInputBeanName(), null, BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
//						params.add(defaultParam);
//						MessageParameter defaultParamMax = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, null, parameterCode, BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
//						defaultParamMax.setPatternFormat(patternFormat);
//						params.add(defaultParamMax);
//						details.setParameters(params);
//						lstCheckDetails.add(details);
//					} else {
//						List<String> lstMinMax = GenerateScreenServiceImpl.defaultMinMax(thisScreenItem.getPhysicalDataType());
//						if (FunctionCommon.isNotEmpty(lstMinMax)) {
//							if (BusinessDesignConst.DataType.STRING.equals(inputBean.getDataType()) || BusinessDesignConst.DataType.BIGDECIMAL.equals(inputBean.getDataType())) {
//								validateType = BusinessDesignConst.ValidateType.DECIMAL_MAX;
//							} else {
//								validateType = BusinessDesignConst.ValidateType.MAX;
//							}
//							ValidationCheckDetail details = GenerateScreenContruct.populateValidationCheckDetail(inputBean, validateType);
//							
//							List<MessageParameter> params = new ArrayList<MessageParameter>();
//							int itemSeqNoOfMessageParameter = 0;
//							MessageParameter defaultParam = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, inputBean.getInputBeanName(), null, BusinessDesignConst.MessageParameter.PARAMETER_TYPE_MESSAGECODE);
//							params.add(defaultParam);
//							MessageParameter defaultParamMax = GenerateScreenContruct.populateMessageParameter(inputBean.getBusinessLogicId(), itemSeqNoOfMessageParameter++, null, lstMinMax.get(1), BusinessDesignConst.MessageParameter.PARAMETER_TYPE_VALUE);
//							params.add(defaultParamMax);
//							details.setParameters(params);
//							lstCheckDetails.add(details);
//						}
//					}
//				}
			}
		}
		
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
				objDetail.setValidationCheckDetailId(startSequence++);
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
	
	private Integer positionItemInSignleArea(ScreenItem item) {
		//Base from item_seq_no
		Integer no1 = 0;
		// Even
		if(item.getItemSeqNo() != null && item.getItemSeqNo()%2 == 0) {
			no1 = 0;
		}
		//Odd
		if(item.getItemSeqNo() != null && item.getItemSeqNo()%2 != 0) {
			no1 = 1;
		}
		return no1;
	}
	
	private Integer positionItemInListEntities(ScreenItem item) {
		//Base from item_seq_no
		Integer no1 = 0;
		Integer limitOfTH = 0;
		if(item.getScreenArea() != null && item.getScreenArea().getTotalCol() != null && item.getScreenArea().getTotalElement() != null && item.getScreenArea().getTblHeaderRow() != null) {
			limitOfTH = (item.getScreenArea().getTotalCol() * item.getScreenArea().getTblHeaderRow()) - 1;
		}
		// TH
		if(item.getItemSeqNo() != null && item.getItemSeqNo() < limitOfTH) {
			no1 = 0;
		}
		//TD
		if(item.getItemSeqNo() != null && item.getItemSeqNo() > limitOfTH) {
			no1 = 1;
		}
		return no1;
	}
}
