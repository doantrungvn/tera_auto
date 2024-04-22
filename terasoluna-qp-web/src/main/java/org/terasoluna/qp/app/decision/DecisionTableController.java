package org.terasoluna.qp.app.decision;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.PermissionUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.DecisionTableMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.DecisionTable;
import org.terasoluna.qp.domain.repository.decisiontable.DecisionTableSearchCriteria;
import org.terasoluna.qp.domain.service.decisiontable.DecisionTableService;
import org.terasoluna.qp.domain.service.decisiontable.DecisionTableShareService;
import org.terasoluna.qp.domain.service.decisiontable.ImpactChangeOfDecisionTable;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;

/**
 * @author HungHX
 *
 */
@Controller
@RequestMapping(value = "decisiontable")
@TransactionTokenCheck(value = "decisiontable")
@SessionAttributes(types = { DecisionTableSearchForm.class })
public class DecisionTableController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DecisionTableController.class);
	
	private static final String DECISION_TABLE_FORM_NAME = "decisionTableForm";
	private static final String DECISION_TABLE_SEARCH_FORM_NAME = "decisionTableSearchForm";
	private static final String SEARCH_FORM_PATH = "decisiontable/searchForm";
	private static final String SEARCH_ACTION_PATH = "/decisiontable/search";
	private static final String REGISTER_FORM_PATH = "decisiontable/registerForm";
	private static final String MODIFY_FORM_PATH = "decisiontable/modifyForm";
	private static final String VIEW_FORM_PATH = "decisiontable/viewForm";
	private static final String SEARCH_REDIRECT_PATH = "redirect:/decisiontable/search";
	private static final String VIEW_REDIRECT_PATH = "redirect:/decisiontable/view";
	/*private static final String REDIRECT_DELETECOMPLETE = "redirect:/complete";*/
	private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";
	
	private static final String VIEW_BLOGIC_MODIFY_LINK = "decisiontable/viewListAffectedChangeDesignForm";
	/*private static final String REDIRECT_VIEW_BLOGIC_MODIFY_LINK = "redirect:/decisiontable/viewListAffectedChangeDesignForm";*/
	
	@Inject
	public DecisionTableService decisionTableService;
	
	@Inject
	public DecisionTableShareService decisionTableShareService;
	
	@Inject
	ProjectService projectService;
	
	@Inject
	ModuleService moduleService;
	
	@Inject
    Mapper beanMapper;
	
	@Inject
	DecisionTableValidator decisionTableValidator;
	
	@InitBinder
	public void init() {
		moduleCode = CommonMessageConst.TQP_DECISIONTABLE;
	}
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used
	 * for populating command and form object
	 * 
	 * @param webDataBinder WebDataBinder
	 */
	@InitBinder(DECISION_TABLE_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(decisionTableValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/**
	 * Identifies methods which initialize the search form object
	 * 
	 * @return decisionTableSearchForm
	 */
	@ModelAttribute
	public DecisionTableSearchForm setUpDecisionTableSearchForm() {
		
		DecisionTableSearchForm decisionTableSearchForm = new DecisionTableSearchForm();
		logger.debug("Init form {0}", decisionTableSearchForm);
		
		return decisionTableSearchForm;
	}
	
	/**
	 * Initialize search decision table screen
	 * 
	 * @param form decisionTableSearchForm
	 * @param model Model
	 * @param status SessionStatus
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, 
			@ModelAttribute DecisionTableSearchForm decisionTableSearchForm, Model model, 
			@PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		
		if (init != null) {
			sessionStatus.setComplete();
			decisionTableSearchForm = new DecisionTableSearchForm();
			model.addAttribute(DECISION_TABLE_SEARCH_FORM_NAME, decisionTableSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		
		checkChangeProject(false);
		decisionTableSearchForm.setProjectId(String.valueOf(SessionUtils.getCurrentProjectId()));
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		DecisionTableSearchCriteria decisionTableSearchCriteria = beanMapper.map(decisionTableSearchForm, DecisionTableSearchCriteria.class);
		Page<DecisionTable> page = decisionTableService.searchDecisionTable(decisionTableSearchCriteria, pageable);
		model.addAttribute("page", page);
		
		return SEARCH_FORM_PATH;
	}
	
	
	/**
	 * Search decision table process
	 * 
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = { RequestMethod.POST })
	public String processSearch(@ModelAttribute(DECISION_TABLE_SEARCH_FORM_NAME) DecisionTableSearchForm decisionTableSearchForm,
			@PageableDefault Pageable pageable, Model model) {
		
		checkChangeProject(false);
		decisionTableSearchForm.setProjectId(String.valueOf(SessionUtils.getCurrentProjectId()));
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());

		DecisionTableSearchCriteria decisionTableSearchCriteria = beanMapper.map(decisionTableSearchForm, DecisionTableSearchCriteria.class);
		Page<DecisionTable> page = decisionTableService.searchDecisionTable(decisionTableSearchCriteria, pageable);
		model.addAttribute("page", page);

		return SEARCH_FORM_PATH;
	}
	
	/**
	 * Register decision table process
	 * 
	 * @param form
	 * @param model
	 * @param status
	 * @return REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register")
	public String displayRegister(DecisionTableForm decisionTableForm, Model model, SessionStatus status) {
		checkChangeProject(true);
		model.addAttribute(DECISION_TABLE_FORM_NAME, decisionTableForm);
		
		return REGISTER_FORM_PATH;
	}
	
	/**
	 * Process register decision table design
	 * 
	 * @param decisionTableForm DecisionTableForm
	 * @param result BindingResult
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return SEARCH_REDIRECT_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = { RequestMethod.POST })
	public String processRegister(@Validated DecisionTableForm decisionTableForm, BindingResult result,
			RedirectAttributes redirectAttr, Model model) {
		
		checkChangeProject(true);
		
		if (result.hasErrors()) {
			/*model.addAttribute("decisionTableForm", decisionTableForm);*/
			return REGISTER_FORM_PATH;
		}
		
		DecisionTable decisionTable = beanMapper.map(decisionTableForm, DecisionTable.class);
		decisionTable.setProjectId(SessionUtils.getCurrentProjectId());
		
		try {
			CommonModel common = this.initCommon();
			Long accountId = SessionUtils.getAccountId();
			decisionTable.setCreatedBy(accountId);
			decisionTable.setUpdatedBy(accountId);
			decisionTableService.registerDecisionTable(decisionTable, common);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			model.addAttribute("decisionTableForm", decisionTableForm);
			
			return REGISTER_FORM_PATH;
		}
		
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, 
				MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0007)));
		
		return SEARCH_REDIRECT_PATH;
	}
	
	/**
	 * Display modify decision table
	 * 
	 * @param decisionTableForm
	 * @param model
	 * @param status
	 * @return MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(DecisionTableForm decisionTableForm, Model model , RedirectAttributes redirectAttr, BindingResult result) {
		if(decisionTableForm.getMode() != null && decisionTableForm.getMode().equals("3")) {
			decisionTableForm = beanMapper.map(SessionUtils.get("decisionTableModify"), DecisionTableForm.class);
			model.addAttribute("decisionTableForm", decisionTableForm);
			return MODIFY_FORM_PATH;
		} else {
			DecisionTable decisionTable =  beanMapper.map(decisionTableForm, DecisionTable.class);
			try {
				checkChangeProject(true);
				decisionTable = decisionTableService.findOneByDecisionTbId(decisionTable.getDecisionTbId());
				// Adding check concurrence
				if (DbDomainConst.DesignStatus.FIXED.equals(decisionTable.getDesignStatus())) {
					throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, decisionTable.getDecisionTbName()));
				}
				CommonModel common = this.initCommon();
				if(decisionTable.getModuleId() == null) {
					common.setProjectId(decisionTable.getProjectId());
					projectService.validateProject(common);
				} else {
					moduleService.validateModule(decisionTable.getModuleId(), common);
				}
				decisionTableForm = beanMapper.map(decisionTable, DecisionTableForm.class);
				
			} catch (BusinessException ex) {
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				
				if (MODE_SEARCH.equals(decisionTableForm.getMode())) {
					return SEARCH_REDIRECT_PATH;
				} else if (MODE_VIEW.equals(decisionTableForm.getMode())) {
					try {
						model.addAttribute("message", ex.getResultMessages());
						DecisionTable dt = decisionTableService.findOneByDecisionTbId(decisionTable.getDecisionTbId());
						decisionTableForm = beanMapper.map(dt, DecisionTableForm.class);
						model.addAttribute("decisionTableForm", decisionTableForm);
					} catch (BusinessException be) {
						model.addAttribute("message", be.getResultMessages());
						model.addAttribute("decisionTableForm", null);
					}
					return VIEW_FORM_PATH;
				}
			}
				model.addAttribute("decisionTableForm", decisionTableForm);
				return MODIFY_FORM_PATH;
		}
	}

	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST,params="jsonBack")
	public String displayModifyBack(RedirectAttributes redirectAttr, Model model,@RequestParam("formJson")String formJson) {
		this.checkChangeProject(false);
		String destination = null;
		model.addAttribute(DECISION_TABLE_FORM_NAME,DataTypeUtils.toObject(formJson, DecisionTableForm.class));
		destination = MODIFY_FORM_PATH;
		return destination;
	}
	
	
	
	/**
	 * Process modify decision table
	 * 
	 * @param decisionTableForm
	 * @param result
	 * @param redirectAttr
	 * @param model
	 * @return MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modifyConfirm", method = RequestMethod.POST)
	public String displayViewBusinessDesignGet(@Validated DecisionTableForm decisionTableForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		DecisionTable decisionTable = beanMapper.map(decisionTableForm, DecisionTable.class);
		ImpactChangeOfDecisionTable impact = new ImpactChangeOfDecisionTable();
		if (result.hasErrors()) {
			return MODIFY_FORM_PATH;
		}
		
		super.checkChangeProject(true);
		CommonModel common = this.initCommon();		
		if(decisionTableForm.isShowImpactFlag()){
			impact = decisionTableShareService.detectListAffectedWhenModify(decisionTable, common,false);
		}
		
		if(Boolean.TRUE.equals(decisionTableForm.isShowImpactFlag()) && Boolean.TRUE.equals(decisionTableForm.isShowImpactFlag())){
			decisionTableForm.setListBD(impact.getLstUsedBusinessDesign());
			model.addAttribute("formJson", DataTypeUtils.toJson(decisionTableForm));
			model.addAttribute("decisionTableForm",decisionTableForm);
			return VIEW_BLOGIC_MODIFY_LINK;
		}else{
			try{
				decisionTableService.modifyDecisionTable(decisionTable, common);
			}catch(BusinessException be){
				if(CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
					model.addAttribute("isNotExist", "1");
				}
				model.addAttribute("message", be.getResultMessages());
				return MODIFY_FORM_PATH;
			}
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0007)));
			return SEARCH_REDIRECT_PATH;
		}
	}
	
	/**
	 * return viewListAffectedChangeDesignForm screen
	 * 
	 * @param tableDesignForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = {RequestMethod.POST}, params = "isJsonForm")
	public String displayViewBlogicGet(@Validated @ModelAttribute DecisionTableForm decisionTableForm, BindingResult result, RedirectAttributes redirectAttr, Model model, @Param("formJson")String formJson){
		
		decisionTableForm = DataTypeUtils.toObject(formJson, DecisionTableForm.class);
		DecisionTable decisionTable = beanMapper.map(decisionTableForm, DecisionTable.class);
		try {	
			CommonModel common = this.initCommon();
			common.setProjectId(SessionUtils.getCurrentProjectId());

			Long accountId = SessionUtils.getAccountId();
			decisionTable.setCreatedBy(accountId);
			decisionTable.setUpdatedBy(accountId);
			decisionTableService.modifyDecisionTable(decisionTable, common);
		} catch (BusinessException be) {

			if(CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("isNotExist", "1");
			}

			model.addAttribute("message", be.getResultMessages());
			return MODIFY_FORM_PATH;
		}
		
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, 
				MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0007)));
		
		return SEARCH_REDIRECT_PATH;
	}
	
	/**
	 * Process modify decision table
	 * 
	 * @param decisionTableForm
	 * @param result
	 * @param redirectAttr
	 * @param model
	 * @return MODIFY_FORM_PATH
	 *//*
	@TransactionTokenCheck(value = "modifyWithAffect", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated DecisionTableForm decisionTableForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		
		if (result.hasErrors()) {
			return MODIFY_FORM_PATH;
		}
		DecisionTableForm form = (DecisionTableForm) SessionUtils.get("decisionTableModify");
		
		try {	
			decisionTableService.insertProblemList(decisionTable,decisionTable.getListOfProblem());
			decisionTableService.modifyDecisionTable(decisionTable);
		} catch (BusinessException be) {

			if(CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("isNotExist", "1");
			}

			model.addAttribute("message", be.getResultMessages());
			return MODIFY_FORM_PATH;
		}
		
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, 
				MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0007)));
		
		return SEARCH_REDIRECT_PATH;
	}*/
	
	/**
	 * View decision table process
	 * 
	 * @param form
	 * @param model
	 * @param redirectAttr
	 * @return VIEW_FORM_PATH
	 */
	@TransactionTokenCheck(value = "delete", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "view")
	public String displayView(DecisionTableForm decisionTableForm, Model model, RedirectAttributes redirectAttr) {
		checkChangeProject(false);
		
		DecisionTable decisionTable =  beanMapper.map(decisionTableForm, DecisionTable.class);
		
		try {
			decisionTable = decisionTableService.findOneByDecisionTbId(decisionTable.getDecisionTbId());
			
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());

			if (MODE_SEARCH.equals(decisionTableForm.getMode())) {
				return SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(decisionTableForm.getMode())) {
				return VIEW_REDIRECT_PATH;
			}
		}
		decisionTableForm = beanMapper.map(decisionTable, DecisionTableForm.class);
		model.addAttribute(DECISION_TABLE_FORM_NAME, decisionTableForm);
		model.addAttribute("deleteObjectHasFk", PermissionUtils.deleteObjectHasFk());
		return VIEW_FORM_PATH;
	}
	
	/**
	 * View list affect when delete a decision table
	 * @param decisionTableForm
	 * @param model
	 * @return
	 */
	
	@TransactionTokenCheck(value = "delete", type = TransactionTokenType.IN)
	@RequestMapping(value = "viewListAffectedChangeDesignDeleteForm", method = RequestMethod.POST)
	public String displayViewListDeleteAffect(DecisionTableForm decisionTableForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		DecisionTable decisionTable = beanMapper.map(decisionTableForm, DecisionTable.class); 
		super.checkChangeProject(true);
		
		if (result.hasErrors()) {
			return VIEW_FORM_PATH;
		}
		
		CommonModel common = this.initCommon();
		// process when click button "delete" 
		if(!decisionTableForm.getActionDelete()) {
			try {	
				decisionTableForm = beanMapper.map(decisionTable, DecisionTableForm.class);
				model.addAttribute("decisionTableForm",decisionTableForm);
			} catch (BusinessException be) {
				if(CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
					model.addAttribute("isNotExist", "1");
				}
				model.addAttribute("message", be.getResultMessages());
				return VIEW_FORM_PATH;
			}
			
			try {
				Long accountId = SessionUtils.getAccountId();
				decisionTable.setCreatedBy(accountId);
				decisionTable.setUpdatedBy(accountId);
				decisionTableService.deleteDecisionTable(decisionTable, PermissionUtils.deleteObjectHasFk(), common);
			} catch (BusinessException ex) {
				model.addAttribute("message", ex.getResultMessages());
				return VIEW_FORM_PATH;
			}
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(CommonMessageConst.TQP_DECISIONTABLE)));
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
		} else {
			try {
				Long accountId = SessionUtils.getAccountId();
				decisionTable.setCreatedBy(accountId);
				decisionTable.setUpdatedBy(accountId);
				decisionTableService.modifyDesignStatus(decisionTable, common);
			} catch(BusinessException ex) {
				model.addAttribute("message", ex.getResultMessages());
				
				if(CommonMessageConst.ERR_SYS_0048.equals(ex.getResultMessages().getList().get(0).getCode())) {
					decisionTable = decisionTableService.findOneByDecisionTbId(decisionTable.getDecisionTbId());
					decisionTableForm = beanMapper.map(decisionTable, DecisionTableForm.class);
					model.addAttribute("decisionTableForm", decisionTableForm);
				} else {
					model.addAttribute("decisionTableForm", null);
				}
				
				return VIEW_FORM_PATH;
			}
			
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003,
					MessageUtils.getMessage(DecisionTableMessageConst.SC_DECISIONTABLE_0007)));
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
		}
	}

/*    @RequestMapping(value = "getDetailsExternalObjectDefinition", method = RequestMethod.GET)
    @ResponseBody
    public List<ExternalObjectAttribute> getDetailsExternalObjectDefinition(@RequestParam("externalObjectDefinitionId") Long externalObjectDefinitionId) {
        return decisionTableService.findExternalObjectAttributeByCommonObject(externalObjectDefinitionId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
    }

    @RequestMapping(value = "getDetailsCommonObjectDefinition", method = RequestMethod.GET)
    @ResponseBody
    public List<CommonObjectAttribute> getDetailsCommonObjectDefinition(@RequestParam("commonObjectDefinitionId") Long commonObjectDefinitionId) {
        return decisionTableService.findCommonObjectAttributeByCommonObject(commonObjectDefinitionId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
    }*/
}