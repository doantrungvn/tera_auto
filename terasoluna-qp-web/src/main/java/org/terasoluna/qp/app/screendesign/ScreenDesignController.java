package org.terasoluna.qp.app.screendesign;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.groups.Default;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.PermissionUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.generate.GenerateHTMLForm;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.GenerateMessageConst;
import org.terasoluna.qp.app.screendesign.ScreenRegisterForm.RegistrationScreenDesignForm;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.InfoModuleForScreenDesign;
import org.terasoluna.qp.domain.model.MenuDesign;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenArea;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.model.ScreenForm;
import org.terasoluna.qp.domain.model.ScreenFormTabGroup;
import org.terasoluna.qp.domain.model.ScreenFormTabs;
import org.terasoluna.qp.domain.model.ScreenGroupItem;
import org.terasoluna.qp.domain.model.ScreenItem;
import org.terasoluna.qp.domain.model.ScreenItemValidation;
import org.terasoluna.qp.domain.model.ScreenTransition;
import org.terasoluna.qp.domain.model.ScreenTransitionBranch;
import org.terasoluna.qp.domain.model.ScreenTransitionBranchDetail;
import org.terasoluna.qp.domain.model.TempScreenDesign;
import org.terasoluna.qp.domain.model.ValidationRule;
import org.terasoluna.qp.domain.repository.menudesign.MenuDesignRepository;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignRegister;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignSearchCriteria;
import org.terasoluna.qp.domain.repository.screentransition.ScreenTransitionRepository;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignService;
import org.terasoluna.qp.domain.service.codelist.CodeListService;
import org.terasoluna.qp.domain.service.domaindesign.DomainDesignService;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentConst;
import org.terasoluna.qp.domain.service.generatescreen.ScreenDesignGeneratorService;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;
import org.terasoluna.qp.domain.service.menudesign.MenuDesignService;
import org.terasoluna.qp.domain.service.messagedesign.MessageDesignService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.screendesign.FormTab;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst.ScreenElementConst;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignOutput;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;
import org.terasoluna.qp.domain.service.screendesign.ScreenGenerateHandler;
import org.terasoluna.qp.domain.service.screendesign.Tab;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.TemplateException;

@Controller
@RequestMapping(value = "screendesign")
@TransactionTokenCheck(value = "screendesign")
@SessionAttributes(types = { ScreenDesignSearchForm.class, ScreenTransitionForm.class, ScreenItemForm.class})
public class ScreenDesignController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(ScreenDesignController.class);

	@Inject
	ScreenDesignValidator screenDesignValidator;
	
	@Inject
	ScreenItemValidator screenItemValidator;
	
	@Inject
	ScreenDesignEditValidator screenDesignEditValidator;
	
	@Inject
	private ScreenDesignService screenDesignService;
	
	@Inject
	MenuDesignRepository menuDesignRepository;
	
	@Inject
	ScreenTransitionRepository screenTransitionRepository;
	
	@Inject
	CodeListService codeListService;
	
	@InitBinder
	public void init() {
		
		screenGenerateHandler.importData(SessionUtils.getCurrentAccount(), SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		
		moduleCode = CommonMessageConst.TQP_SCREENDESIGN;
	}
	
	@Inject
	ModuleService moduleService;
	
	@Inject
	ProjectService  projectService;
	
	@Inject
	MenuDesignService menuDesignService;
	
	@Inject
	Mapper beanMapper;

	@Inject
	ScreenDesignGeneratorService generateScreenService;
	
	@Inject
	LanguageDesignService languageDesignService;
	
	@Inject
	BusinessDesignService businessDesignService;
	
	@Inject
	MessageDesignService messageDesignService;
	
	@Inject
	DomainDesignService domainDesignService;
	
	@Inject
	ScreenGenerateHandler screenGenerateHandler;
	
	private static final String ACTION_SEARCH = "/screendesign/search";
	private static final String SEARCH_LINK = "screendesign/searchForm";
	private static final String VIEW_LINK = "screendesign/viewForm";
	private static final String TRANSITION_LINK = "screendesign/transitionForm";
	private static final String DESIGN_LINK = "screendesign/designForm";
	private static final String REGISTER_LINK = "screendesign/registerForm";
	private static final String SCREEN_REGISTER_FORM = "screenRegisterForm";
	private static final String SEARCH_REDIRECT_PATH = "redirect:/screendesign/search";
	private static final String PROTOTYPE_VIEW_PATH = "screendesign/prototypeForm";
	private static final String PREVIEW_PATH = "screendesign/previewForm";
	private static final String PROTOTYPE_VIEW_LIST_ITEMS = "screendesign/itemList";
	private static final String CONTAIN_SCREEN_ITEM_FORM = "screenDesignItemForm";
	
	private static final String SCREEN_DESIGN_FORM = "screenDesignForm";

	private static final String REDIRECT_VIEW_LINK_ERROR = "redirect:/screendesign/view?openOwner=1&error=true";
	private static final String REDIRECT_TRANSITION_LINK = "redirect:/screendesign/transition";
	
	private static final String GENERATE_LINK_FORM = "generate/generateHTML";
	private static final String REDIRECT_GENERATE_LINK_FORM = "redirect:/screendesign/generateHTML";
	
	private static final int MODULE_SELECTED_GENERATE = 1;
	
	private static final String FROM_MODULE = "9";
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * @param webDataBinder WebDataBinder
	 */
	@InitBinder(SCREEN_REGISTER_FORM)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(screenDesignValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@InitBinder(CONTAIN_SCREEN_ITEM_FORM)
	public void initBinderS(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(screenItemValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@InitBinder(SCREEN_DESIGN_FORM)
	public void initBinderD(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(screenDesignEditValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute
	public ShowScreenDesignForm setUpShowScreenDesignForm() {
		ShowScreenDesignForm showForm = new ShowScreenDesignForm();
		logger.debug("populate form {}", showForm);

		return showForm;
	}

	@ModelAttribute
	public ScreenDesignSearchForm setUpScreenDesignSearchForm() {
		ScreenDesignSearchForm screenDesignSearchForm = new ScreenDesignSearchForm();
		logger.debug("populate form {}", screenDesignSearchForm);
		return screenDesignSearchForm;
	}
	
	@ModelAttribute
	public ScreenItemForm setUpScreenItemListForm() {
		ScreenItemForm screenItemListForm = new ScreenItemForm();
		logger.debug("ScreenItemForm form {}", screenItemListForm);
		return screenItemListForm;
	}
	
	@ModelAttribute
	public ScreenTransitionForm setUpScreenTransitionForm() {
		ScreenTransitionForm screenTransitionForm = new ScreenTransitionForm();
		logger.debug("populate form {}", screenTransitionForm);

		return screenTransitionForm;
	}
	
	@ModelAttribute("CL_QP_VALIDATION")
	public List<ValidationRule> getRule() {
		return domainDesignService.findAllValidationRuleByStatys(DbDomainConst.DisplayType.USED);
	}

	/**
	 * Initialize search screen design
	 * 
	 * @return SEARCH_LINK
	 */
	@RequestMapping(value = "search", method = { RequestMethod.GET })
	public String displaySearch(
			@RequestParam(value = "init", required = false) String init, 
			@ModelAttribute ScreenDesignSearchForm screenDesignSearchForm,
			Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		
		
		if(init != null){
			/**
			 * DungNN - fixbug 1208
			 */
			Long moduleId = screenDesignSearchForm.getModuleId();
			String moduleName = screenDesignSearchForm.getModuleIdAutocomplete();
			
			sessionStatus.setComplete();
			screenDesignSearchForm = new ScreenDesignSearchForm();
			model.addAttribute("screenDesignSearchForm", screenDesignSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
			
			/**
			 * DungNN - fixbug 1208
			 */
			screenDesignSearchForm.setModuleId(moduleId);
			screenDesignSearchForm.setModuleIdAutocomplete(moduleName);

		}
		checkChangeProject(false);
		
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		ScreenDesignSearchCriteria screenDesignSearchCriteria = beanMapper.map(screenDesignSearchForm, ScreenDesignSearchCriteria.class);
		screenDesignSearchCriteria.setProjectId(SessionUtils.getCurrentProjectId());
		Page<ScreenDesign> pageArea = screenDesignService.findPageByCriteria(screenDesignSearchCriteria, pageable, SessionUtils.getCurrentLanguageId());
		model.addAttribute("page", pageArea);

		return SEARCH_LINK;
	}

	/**
	 * Search screen design process
	 * 
	 * @return SEARCH_LINK
	 */
	@RequestMapping(value = "search", method = { RequestMethod.POST })
	public String processSearch(
			@ModelAttribute ScreenDesignSearchForm screenDesignSearchForm,
			Model model, @PageableDefault Pageable pageable,
			SessionStatus status) {
		
		checkChangeProject(false);
		
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		ScreenDesignSearchCriteria screenDesignSearchCriteria = beanMapper.map(screenDesignSearchForm, ScreenDesignSearchCriteria.class);
		screenDesignSearchCriteria.setProjectId(SessionUtils.getCurrentProjectId());
		Page<ScreenDesign> pageArea = screenDesignService.findPageByCriteria(screenDesignSearchCriteria, pageable, SessionUtils.getCurrentLanguageId());

		model.addAttribute("page", pageArea);

		return SEARCH_LINK;
	}

	/**
	 * Initialize view screen design screen
	 * 
	 * @return VIEW_LINK
	 */
	@RequestMapping(value = "view")
	public String displayView(ScreenDesignForm screenDesignForm,@RequestParam("openOwner") Integer openOwner, Model model
			, RedirectAttributes redirectAttr) {
		
		checkChangeProject(false);
		//String destination = "";
		
		if (screenDesignForm.getError() != null && screenDesignForm.getError()) {
			if (screenDesignForm.getScreenId() == null) {
				redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
				model.addAttribute("screenDesignForm", screenDesignForm);
				return VIEW_LINK;
			}
		}
		if (screenDesignForm.getScreenId() == null) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			return SEARCH_REDIRECT_PATH;
		}
		
		ScreenDesign screenDesign = screenDesignService.getScreenDesignByIdForPreview(screenDesignForm.getScreenId(),1, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		
		
		if (screenDesign == null) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			return SEARCH_REDIRECT_PATH;
		}
		try {
			screenDesignForm = mappingView(screenDesign, screenDesign.getScreenForms(), screenDesign.getScreenAreas(), screenDesign.getScreenGroupItems(), screenDesign.getScreenItems());
			Module module = null;//moduleService.validateModule(screenDesign.getModuleId());
			/*Check design status of Module*/
			try{
				if (DbDomainConst.YesNoFlg.YES.equals(openOwner)) {
					module = moduleService.validateModule(screenDesign.getModuleId(), SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
				} else {
					module = moduleService.findModuleById(screenDesign.getModuleId());
				}
			}catch( BusinessException be){
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				return REDIRECT_VIEW_LINK_ERROR;
			}
			model.addAttribute("module", module);
			screenDesignForm.setOpenOwner(openOwner);
			model.addAttribute("screenDesignForm", screenDesignForm);
			model.addAttribute("projectStyle", screenDesignService.getProjectStyle(SessionUtils.getCurrentProjectId()));
		} catch(BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			return REDIRECT_VIEW_LINK_ERROR;
		}
		
		return VIEW_LINK;
	}
	
	/**
	 * Initialize search screen transition
	 * 
	 * @return TRANSITION_LINK
	 */
	@RequestMapping(value = "transition",  method = RequestMethod.GET)
	public String displayTransition(@RequestParam(value = "init", required = false) String init,
			@ModelAttribute ScreenTransitionForm screenTransitionForm, Model model, SessionStatus sessionStatus, RedirectAttributes redirectAttr) {
		if(FROM_MODULE.equals(screenTransitionForm.getMode())){
			return showTransitionByModule(screenTransitionForm, redirectAttr, model);
		}else{
			return showAllTransition(init, screenTransitionForm, model, sessionStatus);
		}
	}

	/**
	 * Search screen transition process
	 * 
	 * @return TRANSITION_LINK
	 */
	@RequestMapping(value = "transition", method = RequestMethod.POST)
	public String processTransition(@Validated ScreenTransitionForm screenTransitionForm,BindingResult result, SessionStatus sessionStatus, RedirectAttributes redirectAttr, Model model) {
		if(screenTransitionForm.getModuleId() != null){
			return showTransitionByModule(screenTransitionForm, redirectAttr, model);
		}else{
			return showAllTransition(StringUtils.EMPTY, screenTransitionForm, model, sessionStatus);
		}
	}
	
	/**
	 * @param init
	 * @param screenTransitionForm
	 * @param model
	 * @param sessionStatus
	 * @return
	 */
	private String showAllTransition(String init,
			ScreenTransitionForm screenTransitionForm, Model model,
			SessionStatus sessionStatus) {
		if (init != null) {
			sessionStatus.setComplete();
			screenTransitionForm = new ScreenTransitionForm();
			/*model.addAttribute("screenTransitionForm", screenTransitionForm);*/
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		checkChangeProject(false);
		CommonModel common = this.initCommon();
		//screenTransitionForm = new ScreenTransitionForm();

		List<ScreenDesign> scrDesigns = screenDesignService.getAllScreenInfoByProjectId(common.getWorkingProjectId(), common.getWorkingLanguageId());
//		List<ScreenAction> scrConnects = screenDesignService.findAllActionByScreenId(scrDesigns);
		
		List<ScreenTransition> scrTransition = screenDesignService.findAllTransitionByModuleId(null, common.getWorkingProjectId(), SessionUtils.getCurrentLanguageId(), scrDesigns);
		
		List<ScreenTransitionBranch> scrBranch = this.getAllTransitionBranchByModule(null, common.getWorkingProjectId());
		
		model.addAttribute("scrDesigns", scrDesigns);
		model.addAttribute("scrConnects", scrTransition);
		model.addAttribute("scrBranch", scrBranch);
		
		// Init mode
		screenTransitionForm.setMode(null);
		model.addAttribute("screenTransitionForm", screenTransitionForm);
		
		return TRANSITION_LINK;
	}
	

	/**
	 * @param screenTransitionForm
	 * @param redirectAttr
	 * @param model
	 * @return
	 * @throws MappingException
	 */
	private String showTransitionByModule(ScreenTransitionForm screenTransitionForm, RedirectAttributes redirectAttr, Model model) throws MappingException {
		
		checkChangeProject(true);
		
		try {
			List<ScreenDesign> scrDesigns = new ArrayList<ScreenDesign>();
			// Initial variable store all screen transition connector
			ScreenDesign screenDesign = beanMapper.map(screenTransitionForm, ScreenDesign.class);
			CommonModel common = this.initCommon(); 
			
			if (screenDesign.getModuleId() == null) {
				screenDesignService.getAllScreenInfoByProjectId(common.getWorkingProjectId(), common.getWorkingLanguageId());
			} else {
				scrDesigns = screenDesignService.getAllScreenInfoByModuleId(screenDesign.getModuleId(), common.getWorkingLanguageId());
			}
//			List<ScreenAction> scrConnects = screenDesignService.findAllActionByScreenId(scrDesigns);
			
			List<ScreenTransition> scrTransition = screenDesignService.findAllTransitionByModuleId(screenTransitionForm.getModuleId(), common.getWorkingProjectId(), common.getWorkingLanguageId(), scrDesigns);
			
			List<ScreenTransitionBranch> scrBranch = this.getAllTransitionBranchByModule(screenTransitionForm.getModuleId(), common.getWorkingProjectId());
			
			model.addAttribute("scrDesigns", scrDesigns);
			model.addAttribute("scrConnects", scrTransition);
			model.addAttribute("scrBranch", scrBranch);
			
			/*Check design status of Module*/
			  
			try{
				Module module = moduleService.validateModule(screenDesign.getModuleId(), common);
				String jsonInfo = "{\"moduleId\":"+screenDesign.getModuleId()+", \"projectId\": "+module.getProjectId()+", \"moduleName\": \""+module.getModuleName()+"\", \"moduleCode\": \""+module.getModuleCode()+"\"}";
				model.addAttribute("jsonInfo", jsonInfo);
			}catch( BusinessException be){
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				return REDIRECT_VIEW_LINK_ERROR;
			}

			// Init mode
			screenTransitionForm.setMode(null);
		} catch(BusinessException ex) {
			//redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("sc.screendesign.0322")));
			return REDIRECT_TRANSITION_LINK;
		}
		
		return TRANSITION_LINK;
	}
	
	private List<ScreenTransitionBranch> getAllTransitionBranchByModule(Long moduleId, Long projectId){
		
		List<ScreenTransitionBranch> transitionBranchs = screenTransitionRepository.findAllTransitionBranchByModuleId(moduleId, projectId);
		List<ScreenTransitionBranchDetail> transitionBranchDetails = screenTransitionRepository.findAllTransitionBranchDetailByModuleId(moduleId, projectId);
		
		List<ScreenTransitionBranchDetail> branchDetails = null;
		for (ScreenTransitionBranch screenTransitionBranch : transitionBranchs) {
			branchDetails = new ArrayList<ScreenTransitionBranchDetail>();
			for (ScreenTransitionBranchDetail screenTransitionBranchDetail : transitionBranchDetails) {
				if(screenTransitionBranch.getBranchId().equals(screenTransitionBranchDetail.getBranchId())){
					branchDetails.add(screenTransitionBranchDetail);
				}
			}
			screenTransitionBranch.setObjNavigatorInfoDetail(branchDetails);
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				String value = objectMapper.writeValueAsString(screenTransitionBranch);
				screenTransitionBranch.setScreenDesignElement(value);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		
		return transitionBranchs;
	}

	/***
	 * Display design form
	 * 
	 * @param showForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "design")
	public String displayDesignById(@RequestParam("screenId") Long screenId, @RequestParam("screenId") Long projectId, ScreenDesignForm screenDesignForm, RedirectAttributes redirectAttr, Model model) {
		
		checkChangeProject(true);
		
		logger.debug(DESIGN_LINK + ": display design");

		if (screenId == null) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			if (screenDesignForm.getSourceForm() == 1) {
				return REDIRECT_VIEW_LINK_ERROR;
			} else {
				return SEARCH_REDIRECT_PATH;
			}
			
		}
		
		ScreenDesign screenDesign = screenDesignService.getScreenDesignById(screenId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		
		if (screenDesign == null) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			if (screenDesignForm.getSourceForm() == 1) {
				return REDIRECT_VIEW_LINK_ERROR;
			} else {
				return SEARCH_REDIRECT_PATH;
			}
		}
		
		if (DbDomainConst.DesignStatus.FIXED.equals(screenDesign.getDesignStatus())) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add(CommonMessageConst.ERR_SYS_0109, screenDesign.getMessageDesign().getMessageString()));
			return SEARCH_REDIRECT_PATH;
		}
		
		if (screenDesign.getDesignMode().equals(ScreenDesignConst.DesignMode.PROTOTYPE)) {
			
			if (screenDesignForm.getSourceForm() == 1) {
				redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
				return REDIRECT_VIEW_LINK_ERROR;
			} else {
				return "redirect:/screendesign/prototype?screenId=" + screenId;
			}
		}
		
		Module module = null;
		try {
			module = moduleService.validateModule(screenDesign.getModuleId(), SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
			projectService.validateProject(module.getProjectId(), SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId(), true);
			model.addAttribute("module", module);
		} catch( BusinessException be){
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			if (screenDesignForm.getSourceForm() == 1) {
				return REDIRECT_VIEW_LINK_ERROR;
			} else {
				return SEARCH_REDIRECT_PATH;
			}
		}
			if (!screenDesignForm.getError()) {
				screenDesignForm = mapping(screenDesign, screenDesign.getScreenForms(), screenDesign.getScreenAreas(), screenDesign.getScreenGroupItems(), screenDesign.getScreenItems());				
			}
			
			if (module != null) {
				screenDesignForm.setModuleId(module.getModuleId());
				screenDesignForm.setProjectId(module.getProjectId());
				screenDesignForm.setModuleName(module.getModuleName());
				
				screenDesignForm.setDesignMode(screenDesign.getDesignMode());
				screenDesignForm.setDesignStatus(screenDesign.getDesignStatus());
			}
			
			List<BusinessDesign> businessDesigns = businessDesignService.findBusinessLoginsByScreenId(screenId);
			if (businessDesigns != null && businessDesigns.size() > 0) model.addAttribute("blogics", businessDesigns);
			
			model.addAttribute("screenDesignForm", screenDesignForm);
			model.addAttribute("projectStyle", screenDesignService.getProjectStyle(SessionUtils.getCurrentProjectId()));
		
		return DESIGN_LINK;
	}
	
	
	/***
	 * Display design form
	 * 
	 * @param showForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "modifysettingitemlist", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modifysettingitemlist", method = RequestMethod.GET)
	public String displayScreenItemList(@RequestParam("screenId") Long screenId,@ModelAttribute ScreenDesignItemForm screenDesignItemForm, RedirectAttributes redirectAttr, Model model) {
		
		checkChangeProject(true);
		
		logger.debug(PROTOTYPE_VIEW_LIST_ITEMS + ": display design");
		List<ScreenItemForm> screenItemListForms = new ArrayList<ScreenItemForm>();
		//check ScreenId
		if (screenId == null) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			return SEARCH_REDIRECT_PATH;
		}
		ScreenDesign screenDesignCheck = screenDesignService.getScreenDesignById(screenId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		if (screenDesignCheck == null) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			return SEARCH_REDIRECT_PATH;
		}
		
		try {
			List<ScreenItem> lstScreenItems = screenDesignService.getAllItemsByScreenId(screenId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
			for(ScreenItem item : lstScreenItems){
				if(item.getLogicalDataType() != null){
					if(!(item.getLogicalDataType().equals(new Integer(20)) && item.getMessageDesign() == null)){
						item.setCodelistItemInfor(screenDesignService.getCodelistByScreenItem(item));
						ScreenItemForm form = beanMapper.map(item, ScreenItemForm.class);
						if(null != form.getDefaultValue()){
							form.setDefaultValue(form.getDefaultValue().trim());
						}
						if(null != form.getDefaultValueTo()){
							form.setDefaultValueTo(form.getDefaultValueTo().trim());
						}
						if(null != form.getMessageDesign()){
							form.setMessageCode(form.getMessageDesign().getMessageCode());
							if (!StringUtils.isEmpty((form.getMessageDesign().getMessageCode()))) {
								form.setHasSelectItemName(true);
							} else {
								form.setHasSelectItemName(false);
							}
						} else {
							form.setHasSelectItemName(false);
						}
						screenItemListForms.add(form);
					}
				}
			}
			ScreenItemForm [] arr = new ScreenItemForm[screenItemListForms.size()];	
			screenDesignItemForm.setScreenItemForms(screenItemListForms.toArray(arr));
			
			//get Infomatio by screenId
			ScreenDesign screenDesign = screenDesignService.findScreenById(screenId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());

			model.addAttribute("screenDesign", screenDesign);
			model.addAttribute("screenDesignItemForm", screenDesignItemForm);
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return PROTOTYPE_VIEW_LIST_ITEMS;
		}
		return PROTOTYPE_VIEW_LIST_ITEMS;
	}

	/***
	 * save setting screen items list
	 * @param screenId
	 * @param screenItemListForm
	 * @param redirectAttr
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "modifysettingitemlist", type = TransactionTokenType.IN)
	@RequestMapping(value = "modifysettingitemlist", method = RequestMethod.POST)
	public String processSaveSettingItemList(@Validated @ModelAttribute ScreenDesignItemForm screenDesignItemForm, BindingResult bindingResult, RedirectAttributes redirectAttr, Model model) {
		
		checkChangeProject(true);
		
		logger.debug(PROTOTYPE_VIEW_LIST_ITEMS + ": display design");
		List<ScreenItem> lstScreenItems = new ArrayList<ScreenItem>();
		
		//get moduleId by screenId
		ScreenDesign screenDesign = screenDesignService.findScreenById(screenDesignItemForm.getScreenId(), SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		//get Infomation
		model.addAttribute("screenDesign", screenDesign);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("screenDesignItemForm", screenDesignItemForm);
			ValidationUtils.setBindingResult(bindingResult, model);
			return PROTOTYPE_VIEW_LIST_ITEMS;
		}
		try{
			lstScreenItems = createMessageCode(screenDesignItemForm);
			screenDesignService.updateScreenItem(lstScreenItems, SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
		}catch(BusinessException be){
			model.addAttribute("message", be.getResultMessages());
			model.addAttribute("screenDesignItemForm", screenDesignItemForm);
			return PROTOTYPE_VIEW_LIST_ITEMS;
		}
		redirectAttr.addFlashAttribute("message",ResultMessages.info().add("inf.sys.0003", MessageUtils.getMessage("tqp.screendesign")));
		return SEARCH_REDIRECT_PATH;
	}
	
	/***
	 * save form
	 * 
	 * @return
	 */
	@RequestMapping(value = "design", method = RequestMethod.POST)
	public String processDesign(HttpServletRequest request,
			@Validated @ModelAttribute ScreenDesignForm screenDesignForm,
			BindingResult result, RedirectAttributes redirectAttr, Model model) {
		
		checkChangeProject(true);
		
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		screenDesignForm.setFunctionDesignName(screenDesignForm.getFunctionDesignIdAutocomplete());
		
		ScreenDesign screenDesign = beanMapper.map(screenDesignForm,
				ScreenDesign.class);
		screenDesign.setCreatedBy(SessionUtils.getAccountId());
		screenDesign.setCreatedDate(currentTime);
		screenDesign.setUpdatedBy(SessionUtils.getAccountId());
		screenDesign.setUpdatedDate(screenDesignForm.getUpdatedDate());
		
		screenDesign.getMessageDesign().setMessageLevel(2);
		screenDesign.getMessageDesign().setMessageType("sc");
		screenDesign.getMessageDesign().setMessageString(screenDesignForm.getScreenName());
		AccountProfile accountProfile = SessionUtils.getCurrentAccountProfile();
		
		Map<Integer, Object> screen = ScreenDesignFunction.getScreenElement(request, screenDesign, currentTime);
		
		ScreenForm[] screenForms = (ScreenForm[])screen.get(ScreenElementConst.FORM);
		ScreenArea[] screenAreas = (ScreenArea[])screen.get(ScreenElementConst.AREA);
		ScreenGroupItem[] screenGroupItems = (ScreenGroupItem[])screen.get(ScreenElementConst.GROUP_ITEM);
		ScreenItem[] screenItems = (ScreenItem[])screen.get(ScreenElementConst.ITEM);

		if (result.hasErrors()) {
			
			ScreenDesignForm form = mappingInit(screenDesign, screenForms, screenAreas, screenGroupItems, screenItems);	
			form.setProjectId(screenDesignForm.getProjectId());
			form.setModuleName(screenDesignForm.getModuleName());
			form.setError(true);
			model.addAttribute("screenDesignForm", form);
			List<BusinessDesign> businessDesigns = businessDesignService.findBusinessLoginsByScreenId(form.getScreenId());
			if (businessDesigns != null && businessDesigns.size() > 0) model.addAttribute("blogics", businessDesigns);
			ValidationUtils.setBindingResult(result, model);
			return DESIGN_LINK;
		}
		
		try{
			/*Check design status of Module*/
			moduleService.validateModule(screenDesign.getModuleId(), SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
			projectService.validateProject(screenDesign.getProjectId(), SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId(), true);
			screenDesignService.design(screenDesign, screenForms, screenAreas,screenGroupItems, screenItems, SessionUtils.getAccountId(), SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId(), accountProfile);
		}catch(BusinessException be){
			ScreenDesignForm form = mappingInit(screenDesign, screenForms, screenAreas, screenGroupItems, screenItems);
			form.setModuleName(screenDesignForm.getModuleName());
			form.setProjectId(screenDesignForm.getProjectId());
			form.setEnableHomePage(screenDesignForm.getEnableHomePage());
			form.setError(true);
			model.addAttribute("screenDesignForm", form);
			model.addAttribute("message", be.getResultMessages());
			return DESIGN_LINK;
		}
		

		redirectAttr.addFlashAttribute("message",ResultMessages.info().add("inf.sys.0003", MessageUtils.getMessage("tqp.screendesign")));
		return "redirect:search";
	}
	
	private ScreenDesignForm mappingInit(ScreenDesign screenDesign,
			ScreenForm[] screenForms, ScreenArea[] screenAreas,
			ScreenGroupItem[] screenGroupItems, ScreenItem[] screenItems) {
		ScreenDesignForm form = new ScreenDesignForm();
		
		form.setScreenId(screenDesign.getScreenId());
		form.setScreenCode(screenDesign.getScreenCode());
		form.setMessageDesign(screenDesign.getMessageDesign());
		form.setScreenUrlCode(screenDesign.getScreenUrlCode());
		form.setScreenPatternType(screenDesign.getScreenPatternType());
		form.setModuleId(screenDesign.getModuleId());
		form.setScreenName(screenDesign.getMessageDesign().getMessageString());
		form.setRemark(screenDesign.getRemark());
		form.setUpdatedDate(screenDesign.getUpdatedDate());
		form.setDesignMode(screenDesign.getDesignMode());
		form.setDesignStatus(screenDesign.getDesignStatus());
		form.setFunctionDesignId(screenDesign.getFunctionDesignId().toString());
		form.setFunctionDesignName(screenDesign.getFunctionDesignName());
		
		screenAreas = ScreenDesignFunction.addItemToArea(screenAreas, screenGroupItems, screenItems);
		screenAreas = ScreenDesignFunction.addGroupArea(screenAreas, screenGroupItems);
		screenAreas = ScreenDesignFunction.addItemToGroup(screenAreas);
		
		for (int i = 0; i < screenForms.length; i++) {
			screenForms[i].setScreenId(screenDesign.getScreenId());
			screenForms[i].setAreas(new ArrayList<ScreenArea>());
			screenForms[i].setScreenFormId(Long.parseLong(i + ""));
		}
		
		screenDesign.setScreenForms(screenForms);
		
		ScreenForm[] forms = screenForms;
		
		//set screen parameters
		
		for (int i = 0; i < screenDesign.getArrScreenParameter().length; i++) {
			if (screenDesign.getArrScreenParameter()[i] != null) {
				screenDesign.getArrScreenParameter()[i].setScreenId(screenDesign.getScreenId());
			}
		}
		screenDesign.setArrScreenParameter(screenDesign.getArrScreenParameter());
		
		ScreenArea[] areas = screenAreas;
		
		ScreenGroupItem[] groups = new ScreenGroupItem[screenGroupItems.length]; 
		
		ScreenItem[] items = new ScreenItem[screenItems.length];
		
		int startGroupIndex = 0;
		int itemIndex = 0;
		for (int i = 0; i < areas.length; i++) {
			ScreenArea area = areas[i];
			if (areas[i].getAreaType() == -1 || areas[i].getAreaType() == 2 || areas[i].getAreaType() == 3 || areas[i].getTotalGroup() == 0) {
				if (area.getItems() != null) {
					for (int k = 0; k < area.getItems().size(); k++) {
						ScreenItem item = areas[i].getItems().get(k);
						items[itemIndex] = item;					
						itemIndex++;
					}
				}
			} else {
				if (areas[i].getGroups() != null) {
					for (int j = 0; j < areas[i].getGroups().size(); j++) {
						ScreenGroupItem group = areas[i].getGroups().get(j);
						groups[startGroupIndex] = group;
						startGroupIndex++;
						if (group.getItems() != null) {
							for (int k = 0; k < group.getItems().size(); k++) {
								ScreenItem item = group.getItems().get(k);
								item.setGroupItemId(group.getGroupItemId());
								items[itemIndex] = item;					
								itemIndex++;
							}
						}
					}
				}
			}
		}
		
		//set total element for screen group id
		for (int i = 0; i < groups.length; i++) {
			
			ScreenGroupItem group = groups[i];
			int startItemIndex = 0;
			int totalElement = groups[i].getTotalElement();
			
			for (int j = 0; j < items.length; j++) {
				ScreenItem item = items[j];
				
				if (item.getGroupItemId() == null || !item.getGroupItemId().equals(group.getGroupItemId())) continue;
				
				if (totalElement == 0) break;
				
				if (startItemIndex == 0) {
					groups[i].setElementStart(item.getItemSeqNo());
					
				}
				
				if (totalElement == 1) {
					groups[i].setElementEnd(item.getItemSeqNo());
					
				}
				startItemIndex++;
				totalElement--;
			}
			startItemIndex = 0;
			
		}
		
		form.setAreaNonGroup(new ArrayList<ScreenArea>());
		for (int i = 0; i < screenAreas.length; i++) {			
			//get area type link header
			if (screenAreas[i].getAreaType().equals(-1)) 
			{
				form.getAreaNonGroup().add(screenAreas[i]);
			}
		}
		

		
		form.setHeaderLinkItems(new ArrayList<ScreenItem>());
		// 
		for (int i = 0; i < screenItems.length; i++) {
			for (int j = 0; j < form.getAreaNonGroup().size(); j++) {
				if (screenItems[i].getScreenAreaId().equals(form.getAreaNonGroup().get(j).getScreenAreaId())) {
					form.getHeaderLinkItems().add(screenItems[i]);
				}
			}
			
			screenItems[i].setValue(ScreenDesignFunction.getJsonScreenItem(screenItems[i]));
		}
		
		//add screen item to area
		for (int i = 0; i < screenAreas.length; i++) {
			List<ScreenItem> itemElements = new ArrayList<ScreenItem>();
			
			for (int j = 0; j < screenItems.length; j++) {
				if (screenAreas[i].getScreenAreaId().equals(screenItems[j].getScreenAreaId())) {
					itemElements.add(screenItems[j]);
				}
			}
			screenAreas[i].setItems(itemElements);
		}
		
		screenGroupItems = ScreenDesignFunction.checkColRowSpan(screenAreas, screenGroupItems, screenItems);
		
		for (int i = 0; i < screenGroupItems.length; i++) {
			form.getScreenGroups().put(screenGroupItems[i].getGroupItemId(), screenGroupItems[i]);
		}
		
		List<ScreenFormTabGroup> screenFormTabGroups = new ArrayList<ScreenFormTabGroup>();
		List<ScreenFormTabs> screenFormTabs =  new ArrayList<ScreenFormTabs>();
		
		for (int i = 0; i < screenForms.length; i++) {
			if (screenFormTabs != null) {
				if (screenForms[i].getScreenFormTabs() != null && screenForms[i].getScreenFormTabs().length > 0) {
					screenFormTabs.addAll( Arrays.asList(screenForms[i].getScreenFormTabs()));
				}
			}
		}
		
		if (screenFormTabs.size() > 0) {
			List<String> groupTabs = new ArrayList<String>();
			groupTabs.add(screenFormTabs.get(0).getTabCode() + "-" + screenFormTabs.get(0).getScreenFormId());
			
			for (int i = 1; i < screenFormTabs.size(); i++) {
				if (groupTabs.indexOf(screenFormTabs.get(i).getTabCode() + "-" + screenFormTabs.get(i).getScreenFormId()) == -1) {
					groupTabs.add(screenFormTabs.get(i).getTabCode() + "-" + screenFormTabs.get(i).getScreenFormId());
				}
			}
			
			for (int i = 0; i < groupTabs.size(); i++) {
				String[] tabKey = groupTabs.get(i).split("-");
				
				if (tabKey.length == 2) {
					ScreenFormTabGroup group = new ScreenFormTabGroup();
					
					group.setTabCode(tabKey[0]);
					group.setScreenFormId(Long.parseLong(tabKey[1]));
					group.setScreenFormTabs(new ArrayList<ScreenFormTabs>());
					
					for (int j = 0; j < screenFormTabs.size(); j++) {
						if (screenFormTabs.get(j).getScreenFormId().equals(group.getScreenFormId()) 
								&& screenFormTabs.get(j).getTabCode().equals(group.getTabCode())) {
							
							group.setTabDirection(screenFormTabs.get(j).getTabDirection());
							group.getScreenFormTabs().add(screenFormTabs.get(j));
						}
					}
					screenFormTabGroups.add(group);
				}
				
			}
		}
		
		for (int i = 0; i < screenForms.length; i++) {
			
			//add group tab
			screenForms[i].setScreenFormTabGroups(new ArrayList<ScreenFormTabGroup>());
			
			for (ScreenFormTabGroup group : screenFormTabGroups) {
				if (group.getScreenFormId().equals(screenForms[i].getScreenFormId())) {
					String formCode = screenForms[i].getFormCode() + "-" + group.getTabCode();
					formCode = formCode.replace(" ", "");
					//setting template
					String startHtml = "<div id=\""+ formCode +"\" style='width: 100%; float:left;' class='area-tab'>";
					String endHtml = "";
					if (group.getTabDirection().equals(1)) {
						startHtml += "<div class=\"menu-tab\" style=\"float: left; width: 20%; margin: 0px; padding: 0px; margin-left: 4px;\"><ul id=\""+formCode+"\"-tab\" class=\"nav nav-tabs tabs-left\">";
					} else if (group.getTabDirection().equals(0)) {
						startHtml += "<ul style=\"margin-left: 4px; margin-right: 4px;\" id=\""+formCode+"-tab\" class=\"nav nav-tabs\">";
					} else {
						startHtml += "<div style=\"margin-left: 4px; margin-right: 4px;\" class=\"panel-group-accordion\" id=\""+formCode+"-tab\">";
					}
					
					if (group.getTabDirection().equals(1) || group.getTabDirection().equals(0)) {
						for (int j = 0; j < group.getScreenFormTabs().size(); j++) {
							ScreenFormTabs tab = group.getScreenFormTabs().get(j);
							String tabTitle = "<div class=\"form-inline\">" +
									"	<span style=\"cursor: move;\" class=\".ui-state-dark qp-glyphicon glyphicon glyphicon-sort sortable srcgenTableSort\"></span>" +
									" <span style=\"cursor: pointer;\" onclick=\"openTabSetting(this)\" class=\".ui-state-dark qp-glyphicon glyphicon glyphicon-cog\"></span>&nbsp;" +
									tab.getTabTitle() +
									"</div>";
					
							if (j == 0) {
								startHtml += "<li class=\"active\"><a data-toggle='tab' href='#"+formCode+"tab-"+j+"'>"+tabTitle+"</a></li>";
							} else {
								startHtml += "<li><a data-toggle='tab' href='#"+formCode+"tab-"+j+"'>"+tab.getTabTitle()+"</a></li>";
							}
						}
					}
					
					
					if (group.getTabDirection().equals(1)) {
						startHtml += "</ul></div><div class=\"contain-tab-content\" style=\"float: left; width: 79%;margin: 0px; padding: 0px;\"><div id=\""+formCode + "-tab-content\" style=\"border: 1px solid #ddd;\" class=\"tab-content\">";
						endHtml = "</div></div></div>";
					} else if(group.getTabDirection().equals(0)) {
						startHtml += "</ul><div style=\"margin-left: 4px; margin-right: 4px; height: auto;\" id=\""+formCode + "-tab-content\" class=\"tab-content\">";
						endHtml = "</div></div>";
					} else {
						endHtml = "</div></div>";
					}
					
					group.setStartHtml(startHtml);
					group.setEndHtml(endHtml);
					screenForms[i].getScreenFormTabGroups().add(group);
				}
			}
			
			
			//set value
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
				arrFormTabs =  formTabs.toArray(arrFormTabs);
				screenForms[i].setScreenFormTabs(arrFormTabs);
				
				Tab[] arrTabs = new Tab[tabs.size()];
				arrTabs = tabs.toArray(arrTabs);
				
				formTab.setTabs(arrTabs);
				JsonFactory json = new JsonFactory();
				ObjectMapper mapper = new ObjectMapper(json);
				/*TypeReference<FormTab> typeRef = new TypeReference<FormTab>() {
				};*/

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
		
		//set tab
		for (int i = 0; i < screenAreas.length; i++) {
			screenAreas[i] = ScreenDesignFunction.setAreaPosition(screenForms, screenAreas[i], false);
		}
		
		form.setScreenForms(forms);
		form.setScreenAreas(areas);
		form.setScreenGroupItems(groups);
		form.setScreenItems(items);
		
		return form;
	}
	
	private ScreenDesignForm mapping(ScreenDesign screenDesign,
			ScreenForm[] screenForms, ScreenArea[] screenAreas,
			ScreenGroupItem[] screenGroupItems, ScreenItem[] screenItems) {
		ScreenDesignForm form = new ScreenDesignForm();
		
		form.setScreenId(screenDesign.getScreenId());
		form.setScreenCode(screenDesign.getScreenCode());
		form.setScreenUrlCode(screenDesign.getScreenUrlCode());
		form.setScreenPatternType(screenDesign.getScreenPatternType());
		form.setTemplateType(screenDesign.getTemplateType());
		form.setRemark(screenDesign.getRemark());
		form.setUpdatedDate(screenDesign.getUpdatedDate());
		form.setOpenOwner(screenDesign.getOpenOwner());
		form.setDesignMode(screenDesign.getDesignMode());
		form.setDesignStatus(screenDesign.getDesignStatus());
		form.setEnableHomePage(screenDesign.getEnableHomePage());
		
		if (screenDesign.getFunctionDesignId() != null) {
			form.setFunctionDesignId(screenDesign.getFunctionDesignId() + "");
		}
		form.setFunctionDesignName(screenDesign.getFunctionDesignName());
		
		if (screenDesign.getMessageDesign() != null) {
			form.setScreenName(screenDesign.getMessageDesign().getMessageString());
		}
		
		if (screenDesign.getMessageDesign() != null) {
			form.setMessageDesign(screenDesign.getMessageDesign());
			form.setScreenName(screenDesign.getMessageDesign().getMessageString());
		}
		
		form.setAreaNonGroup(new ArrayList<ScreenArea>());
		for (int i = 0; i < screenAreas.length; i++) {			
			//get area type link header
			if (screenAreas[i].getAreaType().equals(-1)) 
			{
				form.getAreaNonGroup().add(screenAreas[i]);
			}
		}
		
		
		form.setHeaderLinkItems(new ArrayList<ScreenItem>());
		// 
		for (int i = 0; i < screenItems.length; i++) {
			for (int j = 0; j < form.getAreaNonGroup().size(); j++) {
				if (screenItems[i].getScreenAreaId().equals(form.getAreaNonGroup().get(j).getScreenAreaId())) {
					form.getHeaderLinkItems().add(screenItems[i]);
				}
			}
		}
		
		//add screen item to area
		for (int i = 0; i < screenAreas.length; i++) {
			List<ScreenItem> items = new ArrayList<ScreenItem>();
			
			for (int j = 0; j < screenItems.length; j++) {
				if (screenAreas[i].getScreenAreaId().equals(screenItems[j].getScreenAreaId())) {
					items.add(screenItems[j]);
				}
			}
			screenAreas[i].setItems(items);
		}
		
		screenGroupItems = ScreenDesignFunction.checkColRowSpan(screenAreas, screenGroupItems, screenItems);
		
		for (int i = 0; i < screenGroupItems.length; i++) {
			form.getScreenGroups().put(screenGroupItems[i].getGroupItemId(), screenGroupItems[i]);
		}
		
		for (int i = 0; i < screenAreas.length; i++) {
			screenAreas[i] = ScreenDesignFunction.setAreaPosition(screenForms, screenAreas[i], false);
		}
		
		form.setScreenForms(screenForms);
		form.setScreenAreas(screenAreas);
		form.setScreenGroupItems(screenGroupItems);
		form.setScreenItems(screenItems);
		form.setScreenParameters(screenDesign.getScreenParameters());
		return form;
	}
	
	public ScreenDesignForm mappingView(ScreenDesign screenDesign,
			ScreenForm[] screenForms, ScreenArea[] screenAreas,
			ScreenGroupItem[] screenGroupItems, ScreenItem[] screenItems) {
		ScreenDesignForm form = new ScreenDesignForm();
		
		form.setScreenId(screenDesign.getScreenId());
		form.setScreenCode(screenDesign.getScreenCode());
		form.setScreenUrlCode(screenDesign.getScreenUrlCode());
		form.setScreenPatternType(screenDesign.getScreenPatternType());
		form.setTemplateType(screenDesign.getTemplateType());
		form.setRemark(screenDesign.getRemark());
		form.setUpdatedDate(screenDesign.getUpdatedDate());
		
		form.setDesignMode(screenDesign.getDesignMode());
		form.setDesignStatus(screenDesign.getDesignStatus());
		
		if (screenDesign.getFunctionDesignId() != null) {
			form.setFunctionDesignId(screenDesign.getFunctionDesignId() + "");
		}
		form.setFunctionDesignName(screenDesign.getFunctionDesignName());
		
		if (screenDesign.getMessageDesign() != null) {
			form.setScreenName(screenDesign.getMessageDesign().getMessageString());
		}
		
		if (screenDesign.getMessageDesign() != null) {
			form.setMessageDesign(screenDesign.getMessageDesign());
			form.setScreenName(screenDesign.getMessageDesign().getMessageString());
		}
		
		form.setAreaNonGroup(new ArrayList<ScreenArea>());
		for (int i = 0; i < screenAreas.length; i++) {			
			//get area type link header
			if (screenAreas[i].getAreaType().equals(-1)) 
			{
				form.getAreaNonGroup().add(screenAreas[i]);
			}
		}
		
		
		form.setHeaderLinkItems(new ArrayList<ScreenItem>());
		// 
		for (int i = 0; i < screenItems.length; i++) {
			for (int j = 0; j < form.getAreaNonGroup().size(); j++) {
				if (screenItems[i].getScreenAreaId().equals(form.getAreaNonGroup().get(j).getScreenAreaId())) {
					form.getHeaderLinkItems().add(screenItems[i]);
				}
			}
		}
		
		//add screen item to area
		for (int i = 0; i < screenAreas.length; i++) {
			List<ScreenItem> items = new ArrayList<ScreenItem>();
			
			for (int j = 0; j < screenItems.length; j++) {
				if (screenAreas[i].getScreenAreaId().equals(screenItems[j].getScreenAreaId())) {
					items.add(screenItems[j]);
				}
			}
			screenAreas[i].setItems(items);
		}
		
		screenGroupItems = ScreenDesignFunction.checkColRowSpan(screenAreas, screenGroupItems, screenItems);
		
		for (int i = 0; i < screenGroupItems.length; i++) {
			form.getScreenGroups().put(screenGroupItems[i].getGroupItemId(), screenGroupItems[i]);
		}
		
		for (int i = 0; i < screenAreas.length; i++) {
			screenAreas[i] = ScreenDesignFunction.setAreaPosition(screenForms, screenAreas[i], true);
		}
		
		form.setScreenForms(screenForms);
		form.setScreenAreas(screenAreas);
		form.setScreenGroupItems(screenGroupItems);
		form.setScreenItems(screenItems);
		form.setScreenParameters(screenDesign.getScreenParameters());
		return form;
	}
	
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)	
	@RequestMapping(value = "register", method=RequestMethod.GET)
	public String displayRegister(@ModelAttribute(SCREEN_REGISTER_FORM) ScreenRegisterForm screenRegisterForm, Model model) {
		
		Long currentProjectId = SessionUtils.getCurrentProjectId();
		if (StringUtils.isNoneBlank(screenRegisterForm.getModuleIdAutocomplete())) {
			this.setOldProjectId(currentProjectId);
		}
		
		checkChangeProject(true);
		
		if(null != currentProjectId) {
			screenRegisterForm.setProjectId(String.valueOf(currentProjectId));
		}

		screenRegisterForm.setCompletionType(ScreenDesignConst.CompleteType.MESSAGE);
		screenRegisterForm.setConfirmationType(ScreenDesignConst.ConfirmType.MESSAGE);
		screenRegisterForm.setDesignMode(ScreenDesignConst.DesignMode.PROTOTYPE);

		return REGISTER_LINK;
	}
	
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method=RequestMethod.POST)
	public String processRegister(@Validated({ Default.class, RegistrationScreenDesignForm.class }) ScreenRegisterForm screenRegisterForm, BindingResult result,  RedirectAttributes redirectAttr, Model model) {
		
		checkChangeProject(true);
		if (result.hasErrors()) {
			screenRegisterForm.setProjectId(SessionUtils.getCurrentProjectId().toString());
			ValidationUtils.setBindingResult(result, model);
			return REGISTER_LINK;
		}
		try {
			ScreenDesignRegister screenDesignRegister = beanMapper.map(screenRegisterForm, ScreenDesignRegister.class);
			try{
				moduleService.validateModule(screenDesignRegister.getModuleId(), SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
			}catch( BusinessException be){
				model.addAttribute("message", be.getResultMessages());
				return REGISTER_LINK;
			}
			screenDesignRegister.setProjectId(SessionUtils.getCurrentProjectId());
			screenDesignRegister.setProject(SessionUtils.getCurrentProject());
			screenDesignService.register(screenDesignRegister, false, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId(), SessionUtils.getAccountId());
		} catch (BusinessException be) {
			screenRegisterForm.setProjectId(SessionUtils.getCurrentProjectId().toString());
			model.addAttribute("message", be.getResultMessages());
			return REGISTER_LINK;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add("inf.sys.0002", MessageUtils.getMessage("sc.screendesign.0004")));
		return SEARCH_REDIRECT_PATH;
	}
	
	@RequestMapping(value="delete")
	public String processDelete( RedirectAttributes redirectAttr, Model model, ScreenDesignForm screenDesignForm) {
		
		checkChangeProject(true);
		
		ScreenDesign screen =  screenDesignService.getScreenDesignById(screenDesignForm.getScreenId(), SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		
		/*String screenName = "";*/
		if (screen != null) {
			screen.setOpenOwner(screenDesignForm.getOpenOwner());
			/*Check design status of Module*/
			
			try{
				moduleService.validateModule(screen.getModuleId(), SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
			}catch( BusinessException ex){
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				return REDIRECT_VIEW_LINK_ERROR;
			}
			
			try{
				screenDesignService.deleteScreenDesign(screen,PermissionUtils.deleteObjectHasFk(), SessionUtils.getAccountId(), SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
			}catch( BusinessException be){
				ScreenDesignForm screenDesignFormError = beanMapper.map(screen, ScreenDesignForm.class);
				screenDesignFormError = mapping(screen, screen.getScreenForms(), screen.getScreenAreas(), screen.getScreenGroupItems(), screen.getScreenItems());
				screenDesignFormError.setListScreenChangeParameter(screenDesignService.getAllScreenActionChangeParameter(screen.getScreenId(), SessionUtils.getCurrentLanguageId()));
				Module module = moduleService.validateModule(screen.getModuleId(), SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
				model.addAttribute("module", module);
				model.addAttribute("screenDesignForm", screenDesignFormError);
				model.addAttribute("message", be.getResultMessages());
				return VIEW_LINK;
			}
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add("inf.sys.0004", MessageUtils.getMessage("tqp.screendesign")));
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
		} else {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			return REDIRECT_VIEW_LINK_ERROR;
		}
	}
	
	@RequestMapping(value="fixdesign")
	public String processFixDesign(@RequestParam("screenId") Long screenId, RedirectAttributes redirectAttr, Model model) {
		
		checkChangeProject(true);
		
		ScreenDesign screen =  screenDesignService.getScreenDesignById(screenId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		/*String screenName = "";*/
		if (screen != null) {
			/*try {
			screenName = screen.getMessageDesign().getMessageString();
			} catch (Exception ex) {
				//message not exists
			}*/
			/*Check Module Design status*/
			try{
				moduleService.validateModule(screen.getModuleId(), SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
			}catch( BusinessException be){
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				return REDIRECT_VIEW_LINK_ERROR;
			}
			screen.setDesignStatus(2);
			screenDesignService.screenDesignChangeStatus(screen, SessionUtils.getAccountId());
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage("tqp.screendesign")));
		} else {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			model.addAttribute("screenDesignForm", new ScreenDesignForm());
			return REDIRECT_VIEW_LINK_ERROR;
		}
		
		return DbDomainConst.REDIRECT_DELETION_SUCCESS;
	}
	
	@RequestMapping(value="underdesign")
	public String processUnderDesign(@RequestParam("screenId") Long screenId, RedirectAttributes redirectAttr, Model model) {
		
		checkChangeProject(true);
		
		ScreenDesign screen =  screenDesignService.getScreenDesignById(screenId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		/*String screenName = "";*/
		if (screen != null) {
			/*try {
			screenName = screen.getMessageDesign().getMessageString();
			} catch (Exception ex) {
				//message not exists
			}*/
			
			/*Check design status of Module*/
			try{
				moduleService.validateModule(screen.getModuleId(), SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
			}catch( BusinessException be){
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				return REDIRECT_VIEW_LINK_ERROR;
			}
			
			screen.setDesignStatus(1);
			screenDesignService.screenDesignChangeStatus(screen, SessionUtils.getAccountId());
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage("tqp.screendesign")));
		} else {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			model.addAttribute("screenDesignForm", new ScreenDesignForm());
			return REDIRECT_VIEW_LINK_ERROR;
		}
		
		return DbDomainConst.REDIRECT_DELETION_SUCCESS;
	}
	
	/***
	 * Display prototype mode
	 * 
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "prototype")
	public String displayPrototype(@RequestParam("screenId") Long screenId, ScreenDesignForm screenDesignForm, RedirectAttributes redirectAttr, Model model) {
		
		checkChangeProject(false);
		
		logger.debug(DESIGN_LINK + ": display design");

		if (screenId == null) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			if (screenDesignForm.getSourceForm() == 1) {
				return REDIRECT_VIEW_LINK_ERROR;
			} else {
				return SEARCH_REDIRECT_PATH;
			}
		}
		
		ScreenDesign screenDesign = screenDesignService.getScreenDesignById(screenId, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
		
		if (screenDesign == null) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			if (screenDesignForm.getSourceForm() == 1) {
				return REDIRECT_VIEW_LINK_ERROR;
			} else {
				return SEARCH_REDIRECT_PATH;
			}
		}
		
		if (!screenDesign.getDesignMode().equals(ScreenDesignConst.DesignMode.PROTOTYPE)) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add("err.sys.0037", MessageUtils.getMessage("tqp.screendesign")));
			if (screenDesignForm.getSourceForm() == 1) {
				return REDIRECT_VIEW_LINK_ERROR;
			} else {
				return SEARCH_REDIRECT_PATH;
			}
		}
		
		Module module = null;
		try {
			module = moduleService.validateModule(screenDesign.getModuleId(), SessionUtils.getAccountId(), SessionUtils.getCurrentProjectId());
			model.addAttribute("module", module);
		} catch( BusinessException be){
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			if (screenDesignForm.getSourceForm() == 1) {
				return REDIRECT_VIEW_LINK_ERROR;
			} else {
				return SEARCH_REDIRECT_PATH;
			}
		}
		if (!screenDesignForm.getError()) {
			screenDesignForm = mapping(screenDesign, screenDesign.getScreenForms(), screenDesign.getScreenAreas(), screenDesign.getScreenGroupItems(), screenDesign.getScreenItems());
		}
		
		if (module != null) {
			screenDesignForm.setModuleId(module.getModuleId());
			screenDesignForm.setProjectId(module.getProjectId());
			screenDesignForm.setModuleName(module.getModuleName());
			
			screenDesignForm.setDesignMode(screenDesign.getDesignMode());
			screenDesignForm.setDesignStatus(screenDesign.getDesignStatus());
		}
		model.addAttribute("screenDesignForm", screenDesignForm);
		model.addAttribute("projectStyle", screenDesignService.getProjectStyle(SessionUtils.getCurrentProjectId()));

		return PROTOTYPE_VIEW_PATH;
	}
	
	@RequestMapping(value = "prototype", method = RequestMethod.POST)
	public String processPrototype(HttpServletRequest request,
			@Validated @ModelAttribute ScreenDesignForm screenDesignForm,
			BindingResult result, RedirectAttributes redirectAttr, Model model) {
		
		checkChangeProject(true);
		
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		
		ScreenDesign screenDesign = beanMapper.map(screenDesignForm,
				ScreenDesign.class);
		screenDesign.setCreatedBy(SessionUtils.getAccountId());
		screenDesign.setCreatedDate(currentTime);
		screenDesign.setUpdatedBy(SessionUtils.getAccountId());
		screenDesign.setUpdatedDate(screenDesignForm.getUpdatedDate());
		
		screenDesign.getMessageDesign().setMessageLevel(2);
		screenDesign.getMessageDesign().setMessageType("sc");
		screenDesign.getMessageDesign().setMessageString(screenDesignForm.getScreenName());
		AccountProfile accountProfile = SessionUtils.getCurrentAccountProfile();
		
		Map<Integer, Object> screen = ScreenDesignFunction.getScreenElement(request, screenDesign, currentTime);
		
		ScreenForm[] screenForms = (ScreenForm[])screen.get(ScreenElementConst.FORM);
		ScreenArea[] screenAreas = (ScreenArea[])screen.get(ScreenElementConst.AREA);
		ScreenGroupItem[] screenGroupItems = (ScreenGroupItem[])screen.get(ScreenElementConst.GROUP_ITEM);
		ScreenItem[] screenItems = (ScreenItem[])screen.get(ScreenElementConst.ITEM);

		if (result.hasErrors()) {
			
			ScreenDesignForm form = mappingInit(screenDesign, screenForms, screenAreas, screenGroupItems, screenItems);	
			form.setProjectId(screenDesignForm.getProjectId());
			form.setModuleName(screenDesignForm.getModuleName());
			form.setError(true);
			model.addAttribute("screenDesignForm", form);
			ValidationUtils.setBindingResult(result, model);
			return DESIGN_LINK;
		}
		
		try{
			screenDesignService.design(screenDesign, screenForms, screenAreas,screenGroupItems, screenItems, SessionUtils.getAccountId(), SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId(), accountProfile);
		}catch(BusinessException be){
			ScreenDesignForm form = mappingInit(screenDesign, screenForms, screenAreas, screenGroupItems, screenItems);
			form.setModuleName(screenDesignForm.getModuleName());
			form.setProjectId(screenDesignForm.getProjectId());
			form.setError(true);
			model.addAttribute("screenDesignForm", form);
			model.addAttribute("message", be.getResultMessages());
			return DESIGN_LINK;
		}
		

		redirectAttr.addFlashAttribute("message",ResultMessages.info().add("inf.sys.0003", MessageUtils.getMessage("tqp.screendesign")));
		return "redirect:search";
	}
	
	@RequestMapping(value = "preview")
	public String displayPreview(HttpSession session, Model model, RedirectAttributes redirectAttr) {
		
		checkChangeProject(false);
		
		ScreenDesignForm form = new ScreenDesignForm();	
		
		if (session.getAttribute("screenPreview") != null) {
			form = (ScreenDesignForm) session.getAttribute("screenPreview");
		}

		model.addAttribute("screenDesignForm", form);
		model.addAttribute("screenName", form.getScreenName());
		model.addAttribute("projectStyle", screenDesignService.getProjectStyle(SessionUtils.getCurrentProjectId()));
		
		session.removeAttribute("screenPreview");
		return PREVIEW_PATH;
	}
	
	//TungHT
	@RequestMapping(value = "generateHTML" , method = RequestMethod.GET)
	public String displayGenTemp(GenerateHTMLForm generateHTMLForm, Model model, RedirectAttributes redirectAttr, @RequestParam(value = "init", required = false) String init,  SessionStatus sessionStatus){
		String destination = StringUtils.EMPTY;
		
		if(init != null){
			sessionStatus.setComplete();
			generateHTMLForm = new GenerateHTMLForm();
			model.addAttribute("generateHTMLForm", generateHTMLForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		checkChangeProject(false);
		
		try {
			// Finds all related module information to project
			Project currentProject = SessionUtils.getCurrentProject();
			currentProject.setModules(moduleService.findAllModuleOfOnline(currentProject.getProjectId(), DbDomainConst.FunctionType.ONLINE));
			
			generateHTMLForm = beanMapper.map(currentProject, GenerateHTMLForm.class);
			model.addAttribute("generateHTMLForm", generateHTMLForm);
			destination = GENERATE_LINK_FORM;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			destination = GENERATE_LINK_FORM;
		}
		return destination;
	}
	
	//TungHT
	@RequestMapping(value = "generateHTML" , method = RequestMethod.POST)
	/*public String processGenTemp(HttpSession session, ScreenDesignForm screenDesignForm, RedirectAttributes redirectAttr, Model model,ModuleForm moduleForm,GenerateHTMLForm generateHTMLForm) throws IOException, TemplateException{*/
		
	public String processGenTemp(GenerateHTMLForm generateHTMLForm, Model model, RedirectAttributes redirectAttr) throws IOException, TemplateException{

		super.checkChangeProject(false);
		// Prepare Modules
		List<Module> listOfModules = new ArrayList<Module>();
		
		CommonModel common = this.initCommon();
		
		JsonFactory json = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(json);
		TypeReference<ScreenDesignOutput> typeRef = new TypeReference<ScreenDesignOutput>() {
		};
		
		/*MenuDesign menuDesign = menuDesignRepository.findMenuDesignByProjectId(SessionUtils.getCurrentProjectId());*/
		if(generateHTMLForm.getScopeGenerate().equals(GenerateDocumentConst.GENERATE_MODULE)){
			for (Module module : generateHTMLForm.getModules()) {
				if(MODULE_SELECTED_GENERATE == module.getSelectedGenerate()){
					listOfModules.add(module);
				}
			}
		} else if(generateHTMLForm.getScopeGenerate().equals(GenerateDocumentConst.GENERATE_PROJECT)){
			listOfModules = generateHTMLForm.getModules();
		}
		
		if (FunctionCommon.isEmpty(listOfModules)) {
			model.addAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0013));
			return GENERATE_LINK_FORM;
		}
		int index = 0;
		String exportFath = FileUtilsQP.getExportFolder();
		Project project = SessionUtils.getCurrentProject();
//		ScreenGenerateHandler screenGenerateHandler = new ScreenGenerateHandler();
		String fileID = screenGenerateHandler.init(exportFath, project.getProjectCode(), SessionUtils.generateTempFolderPath());
		ScreenDesignForm screenDesignForm = new ScreenDesignForm();
		try {
			/*create menu content*/
			String menuContentOfHome = StringUtils.EMPTY;
			String menuContent = StringUtils.EMPTY;	

			MenuDesign menuDesign = menuDesignService.getMenuDesignForPreview(project.getProjectId(), common.getWorkingLanguageId());

			if(menuDesign != null){
				menuDesign.setProjectId(project.getProjectId());
				String urlMainAction = menuDesign.getUrlMainAction();
				if(StringUtils.isNotBlank(urlMainAction)) {
					menuDesign.setUrlMainAction(urlMainAction + ".html");
				} else {
					menuDesign.setUrlMainAction("home.html");
				}
				
				menuContentOfHome = menuDesignService.buildMenu(menuDesign, DbDomainConst.MenuType.PROTOTYPE);
				if(StringUtils.isBlank(urlMainAction)) {
					menuDesign.setUrlMainAction("home.html");
				} 
				menuDesign.setUrlRoot("../");
				menuContent = menuDesignService.buildMenu(menuDesign, DbDomainConst.MenuType.PROTOTYPE);
			} else {
				menuDesign = new MenuDesign();
				menuDesign.setUrlRoot("../");
				menuDesign.setProjectId(project.getProjectId());
				menuDesign.setHeaderMenuName(generateHTMLForm.getProjectName());
				menuDesign.setMenuType(DbDomainConst.MenuDirection.HORIZONTAL);
				menuContent = menuDesignService.buildMenu(menuDesign, DbDomainConst.MenuType.PROTOTYPE);
				menuContentOfHome = menuContent;
			}

			screenGenerateHandler.generateProjectInfo(project, listOfModules, menuContentOfHome, menuDesign, common.getWorkingLanguageId(), SessionUtils.getCurrentAccount());
			menuDesign.setGenOneTime(true);
			for(Module item : listOfModules) {
				List<ScreenDesign> listOfScreenDesign = screenDesignService.getAllScreenInfoByModuleId(item.getModuleId(), SessionUtils.getCurrentLanguageId());

				if (FunctionCommon.isEmpty(listOfScreenDesign)) {
					continue;
				}

				List<ScreenDesign> listToGenerate = new ArrayList<ScreenDesign>();
				for(int j = 0; j < listOfScreenDesign.size(); j++) {
					ScreenDesign screenDesign = new ScreenDesign();
					screenDesign = screenDesignService.getScreenDesignByIdForPreview(listOfScreenDesign.get(j).getScreenId(), 0, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
					listToGenerate.add(screenDesign);
				}
				String pathModule = screenGenerateHandler.createModuleFolderHTML(item.getModuleCode(),1);
				String pathModuleHTML = screenGenerateHandler.createModuleFolderHTML(item.getModuleCode(),0);
				TempScreenDesign tempScreenDesign = new TempScreenDesign();
				try {
					for(int k = 0; k < listToGenerate.size(); k++) {
						screenDesignForm = mappingView(listToGenerate.get(k), listToGenerate.get(k).getScreenForms(), listToGenerate.get(k).getScreenAreas(), listToGenerate.get(k).getScreenGroupItems(), listToGenerate.get(k).getScreenItems());
						screenDesignForm.setModuleId(item.getModuleId());
						
						for(ScreenItem screenitem : screenDesignForm.getScreenItems()) {
							if((screenitem != null && screenitem.getLogicalDataType() != null) && 
									(screenitem.getLogicalDataType().equals(DbDomainConst.LogicDataType.BUTTON) || screenitem.getLogicalDataType().equals(DbDomainConst.LogicDataType.LINK) || screenitem.getLogicalDataType().equals(DbDomainConst.LogicDataType.LINK_DYNAMIC))){
								ScreenDesignOutput screenDesignOutput = new ScreenDesignOutput();
								String jsonString = "";
								if(screenitem.getValue() != null) {
									jsonString = FunctionCommon.getStringJson(screenitem.getValue());
								}
								try {
									screenDesignOutput = mapper.readValue(jsonString, typeRef);
								} catch (JsonParseException e) {
									e.printStackTrace();
								} catch (JsonMappingException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
								if (StringUtils.isEmpty(screenDesignOutput.getDatatype())) {
									return "";
								}
								if(screenDesignOutput.getNavigateToBlogic() != "" && screenDesignOutput.getNavigateToBlogic() != null) {
									screenitem.setNavigateToBlogicId(Long.parseLong(screenDesignOutput.getNavigateToBlogic()));
								}
							}
						}
						tempScreenDesign = beanMapper.map(screenDesignForm, TempScreenDesign.class);
						//tempScreenDesign.setConfirmationType(listToGenerate.get(k).getConfirmationType());
						screenGenerateHandler.generateHTML(tempScreenDesign, pathModule, generateHTMLForm.getProjectName(), listOfModules, menuContent, menuDesign.getMenuType(), project.getProjectId(),
														   common.getWorkingLanguageId(), SessionUtils.getCurrentAccount(),index,pathModuleHTML, menuDesign,item);
						/*screenGenerateHandler.generateJSP(tempScreenDesign, pathModule);*/
					}
				} catch(BusinessException ex) {
					model.addAttribute("generateHTMLForm", generateHTMLForm);
					model.addAttribute("message", ex.getResultMessages());
					return GENERATE_LINK_FORM;
				}
			}

			// Path of Zip folder
			StringBuilder fileName = new StringBuilder(generateHTMLForm.getProjectCode());
			fileName.append(fileID);
			fileName.append(".zip");
			try{
				screenGenerateHandler.zipPrototype(exportFath, fileName.toString(),1);
			} catch(BusinessException ex) {
				fileName.delete(0, fileName.length());
				model.addAttribute("generateHTMLForm", generateHTMLForm);
				model.addAttribute("message", ex.getResultMessages());
				return GENERATE_LINK_FORM;
			}

			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(GenerateMessageConst.INF_GENERATION_0001));
			redirectAttr.addFlashAttribute("fileName", fileName.toString());
			return REDIRECT_GENERATE_LINK_FORM;
		} finally {
			//after zip then delete folder
			FileUtilsQP.deleteQuietly(new File(screenGenerateHandler.getRootDir()));
		}
	}

	@RequestMapping(value = "previewTemp")
	public String displayPreviewTemp(HttpServletRequest request, Model model, RedirectAttributes redirectAttr) {
		
		checkChangeProject(false);
		
		ScreenDesignForm screenDesignForm = new ScreenDesignForm();
		 
		Timestamp currentTime = FunctionCommon.getCurrentTime();
		ScreenDesign screenDesign = beanMapper.map(screenDesignForm,
				ScreenDesign.class);
		
		Map<Integer, Object> screen = ScreenDesignFunction.getScreenElement(request, screenDesign, currentTime);
		
		ScreenForm[] screenForms = (ScreenForm[])screen.get(ScreenElementConst.FORM);
		ScreenArea[] screenAreas = (ScreenArea[])screen.get(ScreenElementConst.AREA);
		ScreenGroupItem[] screenGroupItems = (ScreenGroupItem[])screen.get(ScreenElementConst.GROUP_ITEM);
		ScreenItem[] screenItems = (ScreenItem[])screen.get(ScreenElementConst.ITEM);
	
		ScreenDesignForm form = ScreenDesignFunction.mappingPreview(screenDesign, screenForms, screenAreas, screenGroupItems, screenItems);
		
		if (request.getParameter("screenPatternType") != null) {
			form.setScreenPatternType( Integer.parseInt(request.getParameter("screenPatternType")));
		}
		
		if (request.getParameter("screenName") != null) {
			
			form.setScreenName(request.getParameter("screenName"));
		}
		
		form.setProjectId(screenDesignForm.getProjectId());
		form.setModuleName(screenDesignForm.getModuleName());
		
		if (request.getParameter("screenId") != null) {
			form.setScreenId(Long.parseLong(request.getParameter("screenId")));
		}
		
		request.getSession().setAttribute("screenPreview", form);
		model.addAttribute("screenDesignForm", form);
		
		return PREVIEW_PATH;
	}
	
	/**
	 * mapp Form -> Model
	 * @param screenDesignForm
	 * @return List of Model
	 */
	private List<ScreenItem> createMessageCode(ScreenDesignItemForm screenDesignItemForm){
		List<ScreenItem> listScreenItem = new ArrayList<ScreenItem>();
		if(null != screenDesignItemForm.getModuleId()){
			//get languageId by languageCode and languageContrycode
			/*String languageCode = LocaleUtils.getDesaultLanguage().getLanguageCode();
			String countryCode  =  LocaleUtils.getDesaultLanguage().getCountryCode();
			LanguageDesign param = new LanguageDesign(languageCode, countryCode);
			LanguageDesign languageDesign = languageDesignService.findByLanguageDesign(param);*/
			
			for(ScreenItemForm item : screenDesignItemForm.getScreenItemForms()){
				if(!StringUtils.isBlank(item.getMessageCode())){
					
					MessageDesign messageDesign = new MessageDesign();
					if (item.getHasSelectItemName()) {
						messageDesign.setMessageCode(item.getMessageCode());
					} else {
						messageDesign.setMessageString(item.getMessageCode());
					}
						
					messageDesign.setModuleId(screenDesignItemForm.getModuleId());
					messageDesign.setProjectId(SessionUtils.getCurrentProjectId());
					messageDesign.setAccountId(SessionUtils.getAccountId());
					messageDesign.setMessageType("sc");
					messageDesign.setLanguageId(SessionUtils.getCurrentLanguageId());
					item.setMessageDesign(messageDesign);
				}
			}
			//mapp form to model
			ScreenItemValidation itemValidation = null;
			for(ScreenItemForm item : screenDesignItemForm.getScreenItemForms()){
				if(item.getHasMandatoryFlgCheck() != null){
					if(item.getScreenItemValidation() != null){
						if(item.getHasMandatoryFlgCheck()){
							item.getScreenItemValidation().setMandatoryFlg(1);
						}else{
							item.getScreenItemValidation().setMandatoryFlg(0);
						}
					}else{
						itemValidation = new ScreenItemValidation();
						if(item.getHasMandatoryFlgCheck()){
							itemValidation.setMandatoryFlg(1);
						}else{
							itemValidation.setMandatoryFlg(0);
						}
						item.setScreenItemValidation(itemValidation);
					}
				}
				
				if(item.getItemCode() == null){
					item.setItemCode(item.getItemCodeH());
				}
				
				ScreenItem screenItem = beanMapper.map(item, ScreenItem.class);
				listScreenItem.add(screenItem);
			}
			
		}
		return listScreenItem;
	}
	
	@RequestMapping(value="saveTransition", method=RequestMethod.POST)
	public String processTransition(HttpServletRequest request, ScreenTransitionForm screenTransitionForm, 
			BindingResult result, SessionStatus sessionStatus, RedirectAttributes redirectAttr, Model model) {
		String parameters = "";
		String jsonConnector = "";
		String jsonInfo = "";
		String jsonBranch = "";
		
		if (request.getParameter("parameters") != null) {
			parameters = request.getParameter("parameters");
		}
		
		if (request.getParameter("jsonConnector") != null) {
			jsonConnector = request.getParameter("jsonConnector");
		}
		
		if (request.getParameter("jsonInfo") != null) {
			jsonInfo = request.getParameter("jsonInfo");
		}
		
		if (request.getParameter("jsonBranch") != null) {
			jsonBranch = request.getParameter("jsonBranch");
		}
		
		if (!parameters.isEmpty()) {
			// convert string json to object
			JsonFactory json = new JsonFactory();
			ObjectMapper mapper = new ObjectMapper(json);
			TypeReference<ScreenDesign> typeRef = new TypeReference<ScreenDesign>() {};
			List<ScreenDesign> sdLstUpdt = null;
			List<ScreenTransition> lstConnect = new ArrayList<ScreenTransition>();
			List<ScreenTransition> lstUpdateConnect = new ArrayList<ScreenTransition>();
			List<ScreenTransition> lstNewConnect = new ArrayList<ScreenTransition>();
			List<ScreenTransition> lstDelete = new ArrayList<ScreenTransition>();
			
			List<ScreenTransitionBranch> lstBranchs = new ArrayList<ScreenTransitionBranch>();
			
			/*LanguageDesign languageDesign = new LanguageDesign();*/
			ScreenDesign sd = new ScreenDesign();
			try {
				lstConnect = ScreenDesignFunction.readJson(jsonConnector);
				if(null != lstConnect){
					for(ScreenTransition transition : lstConnect){
						transition.setModuleId(screenTransitionForm.getModuleId());
						if(transition.getStatus() == 0){
							lstDelete.add(transition);
						} else if(transition.getStatus() == 2){
							lstNewConnect.add(transition);
						}else {
							lstUpdateConnect.add(transition);
						}
					}
				}
				
				lstBranchs = ScreenDesignFunction.readJsonScreenTransitionBranch(jsonBranch);
				
				
				// Get list parameter by regex
				parameters = parameters.replaceAll("\\\\", "");
				
				// Split to array list object
				String [] params = parameters.split(";");
				// Update position follow screen
				if(params.length > 0){
					sdLstUpdt = new ArrayList<ScreenDesign>();
					for(int index = 0 ; index < params.length; index++){
						sd = mapper.readValue(params[index], typeRef);
						sdLstUpdt.add(sd);
					}
					
					List<InfoModuleForScreenDesign> listInfo = ScreenDesignFunction.parseJson(jsonInfo);
					screenDesignService.saveTransition(sdLstUpdt, lstBranchs, lstConnect, lstUpdateConnect, lstNewConnect, lstDelete, listInfo, SessionUtils.getAccountId(), SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId());
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "err.screendesign.0026";
			}
		}
		
		return processTransition(screenTransitionForm, result, sessionStatus, redirectAttr, model);
	}

}
