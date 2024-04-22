package org.terasoluna.qp.app.businessdesign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
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
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.PermissionUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.BusinessDesignMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.InputBean;
import org.terasoluna.qp.domain.model.ObjectDefinition;
import org.terasoluna.qp.domain.model.OutputBean;
import org.terasoluna.qp.domain.model.PatternedComponent;
import org.terasoluna.qp.domain.model.SequenceConnector;
import org.terasoluna.qp.domain.model.SequenceLogic;
import org.terasoluna.qp.domain.repository.businessdesign.BusinessDesignCriteria;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignConst;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignHelper;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignService;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignShareService;
import org.terasoluna.qp.domain.service.businessdesign.CommonBusinessDesignShareService;
import org.terasoluna.qp.domain.service.businessdesign.ImpactChangeOfCommonBlogic;
import org.terasoluna.qp.domain.service.businessdesign.ImpactChangeOfStandardBlogic;

@Controller
@RequestMapping(value = "businessdesign")
@TransactionTokenCheck(value = "businessdesign")
@SessionAttributes(types = { BusinessDesignSearchForm.class })
public class BusinessDesignController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BusinessDesignController.class);

	private static final String BUSINESS_DESIGN_FORM_NAME = "businessDesignForm";

	private static final String SEARCH_FORM_PATH = "businessdesign/searchForm";

	private static final String BUSINESS_DESIGN_SEARCH_FORM_NAME = "businessDesignSearchForm";

	private static final String SEARCH_ACTION_PATH = "/businessdesign/search";

	private static final String REGISTER_FORM_PATH = "businessdesign/registerForm";

	private static final String REGISTER_WS_FORM_PATH = "businessdesign/registerWSForm";
	
	private static final String REGISTER_BATCH_FORM_PATH = "businessdesign/registerBatchForm";

	private static final String REGISTER_COMMON_FORM_PATH = "businessdesign/registerCommonForm";

	private static final String SEARCH_REDIRECT_PATH = "redirect:/businessdesign/search";

	private static final String MODIFY_FORM_PATH = "businessdesign/modifyForm";

	private static final String MODIFY_WS_FORM_PATH = "businessdesign/modifyWSForm";

	private static final String MODIFY_COMMON_FORM_PATH = "businessdesign/modifyCommonForm";
	
	private static final String MODIFY_BATCH_FORM_PATH = "businessdesign/modifyBatchForm";

	private static final String MODIFY_FORWARD = "forward:/businessdesign/modify";

	private static final String VIEW_FORM_PATH = "businessdesign/viewForm";

	private static final String VIEW_REDIRECT_PATH = "redirect:/businessdesign/view";

	private static final String DELETE_FORWARD = "forward:/businessdesign/delete";

	private static final String DELETE_FORM_AFFECTION = "businessdesign/viewFormDeleteAffection";

	private static final String MODIFY_FORM_AFFECTION = "businessdesign/modifyFormChangeAffection";

	private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";

	@Inject
	public BusinessDesignService businessDesignService;
	
	@Inject
	public CommonBusinessDesignShareService commonBusinessDesignShareService;
	
	@Inject
	public BusinessDesignShareService businessDesignShareService;

	@Inject
	Mapper beanMapper;

	@Inject
	BusinessDesignValidator businessDesignValidator;

	@InitBinder
	public void init() {
		moduleCode = CommonMessageConst.TQP_BUSINESSLOGICDESIGN;
	}

	/**
	 * Identifies methods which initialize the WebDataBinder which will be used
	 * for populating command and form object
	 *
	 * @param webDataBinder
	 *            WebDataBinder
	 */
	@InitBinder(BUSINESS_DESIGN_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(businessDesignValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * Identifies methods which initialize the business design form object
	 *
	 * @return businessDesginForm
	 */
	@ModelAttribute
	public BusinessDesignForm setUpBusinessDesignForm() {
		BusinessDesignForm businessDesignForm = new BusinessDesignForm();
		logger.debug("Init form {0}", businessDesignForm);
		return businessDesignForm;
	}
	
	/**
	 * Identifies methods which initialize the business design search form object
	 *
	 * @return businessDesginForm
	 */
	@ModelAttribute
	public BusinessDesignSearchForm setUpBusinessDesignSearchForm() {
		BusinessDesignSearchForm businessDesignSearchForm = new BusinessDesignSearchForm();
		logger.debug("Init form {0}", businessDesignSearchForm);
		return businessDesignSearchForm;
	}

	/**
	 * Identifies methods which initialize the search form object
	 *
	 * @return businessDesginSearchForm
	 */
	@ModelAttribute
	public BusinessDesignSearchForm setUpBusinessDesginSearchForm() {
		BusinessDesignSearchForm businessDesignSearchForm = new BusinessDesignSearchForm();
		logger.debug("Init form {0}", businessDesignSearchForm);
		return businessDesignSearchForm;
	}

	/**
	 * Initialize search business logic design screen
	 *
	 * @param form
	 *            BusinessDesignForm
	 * @param model
	 *            Model
	 * @param status
	 *            SessionStatus
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute BusinessDesignSearchForm businessDesignSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if (init != null) {
			
			/**
			 * DungNN - fixbug 1208
			 */
			Long moduleId = businessDesignSearchForm.getModuleId();
			String moduleName = businessDesignSearchForm.getModuleIdAutocomplete();
			
			sessionStatus.setComplete();
			businessDesignSearchForm = new BusinessDesignSearchForm();
			model.addAttribute(BUSINESS_DESIGN_SEARCH_FORM_NAME, businessDesignSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
			
			/**
			 * DungNN - fixbug 1208
			 */
			businessDesignSearchForm.setModuleId(moduleId);
			businessDesignSearchForm.setModuleIdAutocomplete(moduleName);
		}
		checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		BusinessDesignCriteria businessDesignCriteria = beanMapper.map(businessDesignSearchForm, BusinessDesignCriteria.class);
		CommonModel commonModel = this.initCommon();
		Page<BusinessDesign> page = businessDesignService.searchBusinessDesign(businessDesignCriteria, pageable, commonModel);
		model.addAttribute("page", page);
		return SEARCH_FORM_PATH;
	}

	/**
	 * Search business logic design process
	 *
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = { RequestMethod.POST })
	public String processSearch(@ModelAttribute(BUSINESS_DESIGN_SEARCH_FORM_NAME) BusinessDesignSearchForm businessDesignSearchForm, @PageableDefault Pageable pageable, Model model) {
		super.checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		BusinessDesignCriteria businessDesignCriteria = beanMapper.map(businessDesignSearchForm, BusinessDesignCriteria.class);
		CommonModel commonModel = this.initCommon();
		Page<BusinessDesign> page = businessDesignService.searchBusinessDesign(businessDesignCriteria, pageable, commonModel);
		model.addAttribute("page", page);
		return SEARCH_FORM_PATH;
	}

	private String displayRegister(BusinessDesignForm form, Model model){
		if (StringUtils.isNoneBlank(form.getModuleIdAutocomplete())) {
			this.setOldProjectId(SessionUtils.getCurrentProjectId());
		}
		super.checkChangeProject(true);
		CommonModel commonModel = this.initCommon();
		List<PatternedComponent> lstPatternedComponents = businessDesignService.findPatternedComponentOfProject(commonModel);
		model.addAttribute("lstPatternedComponents", lstPatternedComponents);
		model.addAttribute("advanceFlag", "0");
		initBusinessLogicForm(form);
		return form.getActionPath();
	}
	/**
	 * Initialize register business logic design screen
	 *
	 * @param form
	 *            BusinessDesignForm
	 * @return REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "registerWeb", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "registerWeb", method = { RequestMethod.GET })
	public String displayRegisterWeb(BusinessDesignForm form, Model model) {
		if(form != null)
			form.setBlogicType(BusinessDesignConst.BLOGIC_TYPE_STANDARD);
		return displayRegister(form,model);
	}
	
	@TransactionTokenCheck(value = "registerCommon", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "registerCommon", method = { RequestMethod.GET })
	public String displayRegisterCommon(BusinessDesignForm form, Model model) {
		if(form != null)
			form.setBlogicType(BusinessDesignConst.BLOGIC_TYPE_COMMON);
		return displayRegister(form,model);
	}
	
	@TransactionTokenCheck(value = "registerWS", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "registerWS", method = { RequestMethod.GET })
	public String displayRegisterWS(BusinessDesignForm form, Model model) {
		if(form != null)
			form.setBlogicType(BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE);
		return displayRegister(form,model);
	}
	
	@TransactionTokenCheck(value = "registerBatch", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "registerBatch", method = { RequestMethod.GET })
	public String displayRegisterBatch(BusinessDesignForm form, Model model) {
		if(form != null)
			form.setBlogicType(BusinessDesignConst.BLOGIC_TYPE_BATCH);
		return displayRegister(form,model);
	}
	
	private void initBusinessLogicForm(BusinessDesignForm form ){
		Integer blogicType = form.getBlogicType();
		if (FunctionCommon.equals(BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE, blogicType)) {
	        form.setReturnType(BusinessDesignConst.RETURN_TYPE_JSON);
	        form.setRequestMethod(BusinessDesignConst.REQUEST_METHOD_INITIAL);
	        form.setActionPath(REGISTER_WS_FORM_PATH);
        } else if (FunctionCommon.equals(BusinessDesignConst.BLOGIC_TYPE_COMMON, blogicType)) {
	        form.setPackageName(BusinessDesignHelper.generatePackageName(SessionUtils.getCurrentProject().getProjectCode(),SessionUtils.getCurrentProject().getPackageName()));
	        form.setActionPath(REGISTER_COMMON_FORM_PATH);
        } else if (FunctionCommon.equals(BusinessDesignConst.BLOGIC_TYPE_BATCH, blogicType)) {
	        form.setActionPath(REGISTER_BATCH_FORM_PATH);
	        form.setModuleType(1);
        } else {
	        form.setReturnType(1);
	        form.setRequestMethod(0);
	        form.setActionPath(REGISTER_FORM_PATH);
	        form.setAdvanceReturnTypeFlag(false);
	        form.setAdvanceRequestMethodFlag(false);
	        form.setModuleType(0);
        }
	}

	/**
	 * Process register business logic design
	 *
	 * @param businessDesignForm
	 *            BusinessDesignForm
	 * @param result
	 *            BindingResult
	 * @param redirectAttr
	 *            RedirectAttributes
	 * @param model
	 *            Model
	 * @return SEARCH_REDIRECT_PATH
	 */
	@TransactionTokenCheck(value = "registerWS", type = TransactionTokenType.IN)
	@RequestMapping(value = "registerWS", method = { RequestMethod.POST })
	public String processRegisterWS(@Validated BusinessDesignForm businessDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		return processRegister(businessDesignForm,result ,redirectAttr,model);
	}
	
	@TransactionTokenCheck(value = "registerWeb", type = TransactionTokenType.IN)
	@RequestMapping(value = "registerWeb", method = { RequestMethod.POST })
	public String processRegisterWeb(@Validated BusinessDesignForm businessDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		return processRegister(businessDesignForm,result ,redirectAttr,model);
	}
	
	@TransactionTokenCheck(value = "registerCommon", type = TransactionTokenType.IN)
	@RequestMapping(value = "registerCommon", method = { RequestMethod.POST })
	public String processRegisterCommon(@Validated BusinessDesignForm businessDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		return processRegister(businessDesignForm,result ,redirectAttr,model);
	}
	
	@TransactionTokenCheck(value = "registerBatch", type = TransactionTokenType.IN)
	@RequestMapping(value = "registerBatch", method = { RequestMethod.POST })
	public String processRegisterBatch(@Validated BusinessDesignForm businessDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		return processRegister(businessDesignForm,result ,redirectAttr,model);
	}
	
	private String processRegister(BusinessDesignForm businessDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model){
		super.checkChangeProject(true);
		if (result.hasErrors()) {
			BusinessDesignForm form = mappingInitRegister(businessDesignForm);
			model.addAttribute("businessDesignForm", form);
			CommonModel commonModel = this.initCommon();
			List<PatternedComponent> lstPatternedComponents = businessDesignService.findPatternedComponentOfProject(commonModel);
			model.addAttribute("lstPatternedComponents", lstPatternedComponents);
			return returnRegisterForm(form.getBlogicType());
		}
		BusinessDesign businessDesign = beanMapper.map(businessDesignForm, BusinessDesign.class);
		if (businessDesignForm.getFile() != null) {
			businessDesign.setFileName(businessDesignForm.getFile().getOriginalFilename());
		}
		try {
		    CommonModel commonModel = this.initCommon();
			businessDesignService.registerBusinessLogicDesign(businessDesign, SessionUtils.getCurrentLanguageId(), SessionUtils.getCurrentProjectId(), commonModel);
		} catch (BusinessException be) {
			BusinessDesignForm form = mappingInitRegister(businessDesignForm);
			model.addAttribute("businessDesignForm", form);
			model.addAttribute("message", be.getResultMessages());
			return returnRegisterForm(form.getBlogicType());
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010)));
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * Initialize modify business logic design screen
	 *
	 * @param form
	 *            BusinessDesignForm
	 * @return MODIFY_FORM_PATH
	 */
	//	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(BusinessDesignForm form, Model model, RedirectAttributes redirectAttr) {
		super.checkChangeProject(true);
		String destination = StringUtils.EMPTY;
		BusinessDesign businessDesign = beanMapper.map(form, BusinessDesign.class);
		Boolean isConvertWS = form.getConvertWSFlg();

		try {
		    CommonModel commonModel = this.initCommon();
			businessDesign = businessDesignService.findBusinessLogicInformation(businessDesign.getBusinessLogicId(), false, commonModel,true);
			businessDesignService.checkDesignStatus(businessDesign);
			form = beanMapper.map(businessDesign, BusinessDesignForm.class);

			if(Boolean.TRUE.equals(isConvertWS)){
				form.setBlogicType(BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE);
				form.setClientCheckFlg(false);
				form.setRequestMethod(BusinessDesignConst.RETURN_TYPE_JSON);
				form.setScreenId(null);
				form.setScreenFormId(null);
			}
			if (FunctionCommon.equals(form.getBlogicType(), BusinessDesignConst.BLOGIC_TYPE_COMMON) && Boolean.FALSE.equals(form.getCustomizeFlg())) {
				form.setPackageName(BusinessDesignHelper.generatePackageName(SessionUtils.getCurrentProject().getProjectCode(), SessionUtils.getCurrentProject().getPackageName()));
			}

			List<PatternedComponent> lstPatternedComponents = businessDesignService.findPatternedComponentOfProject(commonModel);

			model.addAttribute(BUSINESS_DESIGN_FORM_NAME, form);
			model.addAttribute("lstPatternedComponents", lstPatternedComponents);
			destination = returnModifyForm(form.getBlogicType());
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			if (MODE_SEARCH.equals(form.getMode())) {
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(form.getMode())) {
				form.setHasErrors(true);
				model.addAttribute("notExistFlg", 1);
				model.addAttribute("message", be.getResultMessages());
				destination = VIEW_FORM_PATH;
			}
		}
		return destination;
	}
	
	
	private String returnModifyForm(Integer blogicType){
		String destination;
		if(FunctionCommon.equals(blogicType, BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE)){
			destination = MODIFY_WS_FORM_PATH;
		}else if(FunctionCommon.equals(blogicType, BusinessDesignConst.BLOGIC_TYPE_COMMON)){
			destination = MODIFY_COMMON_FORM_PATH;
		}else if (FunctionCommon.equals(blogicType, BusinessDesignConst.BLOGIC_TYPE_BATCH)){
			destination = MODIFY_BATCH_FORM_PATH;
		}else{
			destination = MODIFY_FORM_PATH;
		}
		return destination;
	}
	
	private String returnRegisterForm(Integer blogicType){
		String destination;
		if(FunctionCommon.equals(blogicType, BusinessDesignConst.BLOGIC_TYPE_WEBSERVICE)){
			destination = REGISTER_WS_FORM_PATH;
		}else if(FunctionCommon.equals(blogicType, BusinessDesignConst.BLOGIC_TYPE_COMMON)){
			destination = REGISTER_COMMON_FORM_PATH;
		}else if (FunctionCommon.equals(blogicType, BusinessDesignConst.BLOGIC_TYPE_BATCH)){
			destination = REGISTER_BATCH_FORM_PATH;
		}else{
			destination = REGISTER_FORM_PATH;
		}
		return destination;
	}

	/**
	 * Initialize modify business logic design screen
	 *
	 * @param form
	 *            BusinessDesignForm
	 * @return MODIFY_FORM_PATH
	 */
	@RequestMapping(value = "modify", method = RequestMethod.POST, params = "jsonBack")
	//	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	public String displayModifyBack(RedirectAttributes redirectAttr, Model model, @RequestParam("formJson") String formJson) {
		this.checkChangeProject(true);
		String destination = null;
		BusinessDesign businessDesign = DataTypeUtils.toObject(formJson, BusinessDesign.class);
		BusinessDesignForm form = beanMapper.map(businessDesign, BusinessDesignForm.class);
		form = mappingInitRegister(form);
		model.addAttribute(BUSINESS_DESIGN_FORM_NAME, form);
		CommonModel commonModel = this.initCommon();
		List<PatternedComponent> lstPatternedComponents = businessDesignService.findPatternedComponentOfProject(commonModel);
		model.addAttribute("lstPatternedComponents", lstPatternedComponents);
		destination = returnModifyForm(form.getBlogicType());
		return destination;
	}

	/**
	 * Process modify business logic design
	 *
	 * @param businessDesignForm
	 *            BusinessDesignForm
	 * @param result
	 *            BindingResult
	 * @param redirectAttr
	 *            RedirectAttributes
	 * @param model
	 *            Model
	 * @return SEARCH_REDIRECT_PATH
	 */
	// @TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated BusinessDesignForm businessDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model, @RequestParam(value = "formJson", required = false) String formJson) {
		super.checkChangeProject(true);
		BusinessDesign businessDesign = new BusinessDesign();
		String destination= "";
		if (FunctionCommon.isNotEmpty(formJson)) {
			businessDesign = DataTypeUtils.toObject(formJson, BusinessDesign.class);
		} else {
			if (result.hasErrors()) {
				BusinessDesignForm form = mappingInitRegister(businessDesignForm);
				model.addAttribute("businessDesignForm", form);
				CommonModel commonModel = this.initCommon();
				List<PatternedComponent> lstPatternedComponents = businessDesignService.findPatternedComponentOfProject(commonModel);
				model.addAttribute("lstPatternedComponents", lstPatternedComponents);
				destination = returnModifyForm(form.getBlogicType());
				return destination;
			}
			businessDesign = beanMapper.map(businessDesignForm, BusinessDesign.class);
			if (businessDesignForm.getFile() != null && businessDesignForm.getFile().getSize() > 0) {
				businessDesign.setFileName(businessDesignForm.getFile().getOriginalFilename());
			}
		}

		try {
		    CommonModel commonModel = this.initCommon();
			businessDesignService.modifyBusinessDesignLogic(businessDesign, commonModel);
		} catch (BusinessException be) {
			if (FunctionCommon.isNotEmpty(formJson)) {
				businessDesignForm = beanMapper.map(businessDesign, BusinessDesignForm.class);
			}
			businessDesignForm = mappingInitRegister(businessDesignForm);
			model.addAttribute("businessDesignForm", businessDesignForm);
			model.addAttribute("message", be.getResultMessages());
			if (businessDesignForm != null) {
				model.addAttribute("notExistFlg", 0);
			} else {
				model.addAttribute("notExistFlg", 1);
			}
			destination = returnModifyForm(businessDesignForm.getBlogicType());
			return destination;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010)));
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * Process modify business logic design
	 *
	 * @param businessDesignForm
	 *            BusinessDesignForm
	 * @param result
	 *            BindingResult
	 * @param redirectAttr
	 *            RedirectAttributes
	 * @param model
	 *            Model
	 * @return SEARCH_REDIRECT_PATH
	 */
	//	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modifyConfirm", method = RequestMethod.POST)
	public String processModifyConfirm(@Validated BusinessDesignForm businessDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		super.checkChangeProject(true);
		String destination = null;
		CommonModel commonModel = this.initCommon();
		if (result.hasErrors()) {
			BusinessDesignForm form = mappingInitRegister(businessDesignForm);
			model.addAttribute("businessDesignForm", form);
			List<PatternedComponent> lstPatternedComponents = businessDesignService.findPatternedComponentOfProject(commonModel);
			model.addAttribute("lstPatternedComponents", lstPatternedComponents);
			destination = returnModifyForm(businessDesignForm.getBlogicType());
		} else {
			BusinessDesign businessDesign = beanMapper.map(businessDesignForm, BusinessDesign.class);
			if(Boolean.FALSE.equals(businessDesign.getShowImpactFlag())){
				destination = MODIFY_FORWARD;
			}else{
				if (BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(businessDesign.getBlogicType())) {
					// case of common blogic
					ImpactChangeOfCommonBlogic impact = commonBusinessDesignShareService.detectListAffectedWhenModify(businessDesign, commonModel, false);
					businessDesign.setLstAffectedBlogicCommon(impact.getLstUsedBusinessDesign());
					if (Boolean.TRUE.equals(businessDesign.getCustomizeFlg())) {
						if (businessDesignForm.getFile() != null && businessDesignForm.getFile().getSize() > 0) {
							String fileName = FilenameUtils.getName(businessDesignForm.getFile().getOriginalFilename());
							businessDesign.setFileName(fileName);
						}
					}
				} else if(BusinessDesignConst.BLOGIC_TYPE_STANDARD.equals(businessDesign.getBlogicType())){
					// case of normal blogic
					ImpactChangeOfStandardBlogic impact = businessDesignShareService.detectListAffectedWhenModify(businessDesign, commonModel, false);
					businessDesign.setLstAffectedBlogicNavigator(impact.getLstUsedBlogics());
					businessDesign.setLstAffectedScreenItems(impact.getLstUsedNavigatorScreenItems());
				}
				if (CollectionUtils.isEmpty(businessDesign.getLstAffectedBlogicCommon()) && CollectionUtils.isEmpty(businessDesign.getLstAffectedBlogicNavigator()) && CollectionUtils.isEmpty(businessDesign.getLstAffectedScreenItems())) {
					destination = MODIFY_FORWARD;
				} else {
					String json = DataTypeUtils.toJson(businessDesign);
					model.addAttribute("formJson", json);
					businessDesignForm.setLstAffectedBlogicCommon(businessDesign.getLstAffectedBlogicCommon());
					businessDesignForm.setLstAffectedBlogicNavigator(businessDesign.getLstAffectedBlogicNavigator());
					businessDesignForm.setLstAffectedScreenItems(businessDesign.getLstAffectedScreenItems());
					destination = MODIFY_FORM_AFFECTION;
				}
			}
		}
		return destination;
	}

	/**
	 * Initialize view business logic design screen
	 *
	 * @param form
	 *            BusinessDesignForm
	 * @return VIEW_FORM_PATH
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String displayView(BusinessDesignForm form, Model model, RedirectAttributes redirectAttr) {
		super.checkChangeProject(false);
		super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		BusinessDesign businessDesign = new BusinessDesign();
		String destination = "";
		Integer checkForm = form.getOpenOwner();
		Boolean showImpactFlag = form.getShowImpactFlag();
		try {
		    CommonModel commonModel = this.initCommon();
			businessDesign = businessDesignService.findBusinessLogicInformation(form.getBusinessLogicId(), true, commonModel,true);
			form = beanMapper.map(businessDesign, BusinessDesignForm.class);
			form.setOpenOwner(checkForm);
			form.setShowImpactFlag(showImpactFlag);
			model.addAttribute(BUSINESS_DESIGN_FORM_NAME, form);
			destination = VIEW_FORM_PATH;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			if (MODE_SEARCH.equals(form.getMode())) {
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(form.getMode())) {
				destination = VIEW_REDIRECT_PATH;
			}
		}
		return destination;
	}

	/**
	 * Delete or modify design status a business design
	 *
	 * @param BusinessDesignForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "deleteConfirm", method = RequestMethod.POST)
	public String processDeleteConfirm(@ModelAttribute BusinessDesignForm form, RedirectAttributes redirectAttr, Model model) {
		super.checkChangeProject(true);
		String destination = null;
		CommonModel commonModel = this.initCommon();
		// process when click button "delete"
		if (!form.getFlagAction()) {
			BusinessDesign businessDesign = beanMapper.map(form, BusinessDesign.class);
			String json = DataTypeUtils.toJson(businessDesign);
			if(Boolean.FALSE.equals(businessDesign.getShowImpactFlag())){
				destination = DELETE_FORWARD;
			}else{
				if (BusinessDesignConst.BLOGIC_TYPE_COMMON.equals(form.getBlogicType())) {
					// case of common blogic
					ImpactChangeOfCommonBlogic impact = commonBusinessDesignShareService.detectListAffectedWhenDelete(businessDesign, commonModel, false);
					businessDesign.setLstAffectedBlogicCommon(impact.getLstUsedBusinessDesign());
				} else {
					// case of normal blogic
					ImpactChangeOfStandardBlogic impact = businessDesignShareService.detectListAffectedWhenDelete(businessDesign, commonModel, false);
					businessDesign.setLstAffectedBlogicNavigator(impact.getLstUsedBlogics());
					businessDesign.setLstAffectedScreenItems(impact.getLstUsedNavigatorScreenItems());
				}
				
				if (CollectionUtils.isEmpty(businessDesign.getLstAffectedBlogicNavigator()) && CollectionUtils.isEmpty(businessDesign.getLstAffectedBlogicCommon()) && CollectionUtils.isEmpty(businessDesign.getLstAffectedScreenItems())) {
					destination = DELETE_FORWARD;
				} else {
					model.addAttribute("formJson", json);
					form.setLstAffectedBlogicCommon(businessDesign.getLstAffectedBlogicCommon());
					form.setLstAffectedBlogicNavigator(businessDesign.getLstAffectedBlogicNavigator());
					form.setLstAffectedScreenItems(businessDesign.getLstAffectedScreenItems());
					destination = DELETE_FORM_AFFECTION;
				}
			}
		}
		// process when click button "Fixed design" or "Under design"
		else {
			try {
				businessDesignService.modifyDesignStatus(form.getBusinessLogicId(),form.getUpdatedDate(), commonModel);
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010)));
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_BUSINESSLOGICDESIGN));
				destination = DbDomainConst.REDIRECT_MODIFY_SUCCESS;
			} catch (BusinessException be) {
				model.addAttribute("businessDesignForm", form);
				model.addAttribute("message", be.getResultMessages());
				model.addAttribute("notExistFlg", 1);
				return VIEW_FORM_PATH;
			}

		}
		return destination;
	}

	/**
	 * Delete business design
	 *
	 * @param BusinessDesignForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String processDelete(@ModelAttribute BusinessDesignForm form, RedirectAttributes redirectAttr, Model model, @RequestParam(value = "formJson", required = false) String formJson) {
		super.checkChangeProject(true);
		BusinessDesign businessDesign = new BusinessDesign();
		if (FunctionCommon.isNotEmpty(formJson)) {
			businessDesign = DataTypeUtils.toObject(formJson, BusinessDesign.class);
		} else {
			businessDesign = beanMapper.map(form, BusinessDesign.class);
		}
		try {
		    CommonModel commonModel = this.initCommon();
			businessDesignService.deleteBusinessDesignLogic(businessDesign.getBusinessLogicId(), PermissionUtils.deleteObjectHasFk(), form.getUpdatedDate(), commonModel);
		} catch (BusinessException be) {
			model.addAttribute("businessDesignForm", form);
			model.addAttribute("message", be.getResultMessages());
			model.addAttribute("notExistFlg", 1);
			return VIEW_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(BusinessDesignMessageConst.SC_BUSINESSLOGICDESIGN_0010)));
		return DbDomainConst.REDIRECT_DELETION_SUCCESS;
	}

	private BusinessDesignForm mappingInitRegister(BusinessDesignForm form) {
		List<InputBean> lstInputBean = form.getLstInputBean();
		List<OutputBean> lstOutputBean = form.getLstOutputBean();
		List<ObjectDefinition> lstObjectDefinition = form.getLstObjectDefinition();

		Map<String, String> mapTableIndex = new HashMap<String, String>();
		Map<String, Integer> mapSequence = new HashMap<String, Integer>();
		for (InputBean in : lstInputBean) {
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
		mapTableIndex.clear();
		mapSequence.clear();
		// process output bean
		for (OutputBean ou : lstOutputBean) {
			String currentGroup = "";
			if (ou.getParentOutputBeanId() != null) {
				currentGroup = mapTableIndex.get(ou.getParentOutputBeanId());
			}
			ou.setGroupId(currentGroup);
			int maxIndex = mapSequence.getOrDefault(ou.getGroupId(), 0);
			maxIndex++;
			if (ou.getParentOutputBeanId() == null) {
				ou.setTableIndex(String.valueOf(maxIndex));
			} else {
				ou.setTableIndex(currentGroup + "." + maxIndex);
			}
			mapTableIndex.put(ou.getOutputBeanId(), ou.getTableIndex());
			mapSequence.put(ou.getGroupId(), maxIndex);
		}
		// process output bean
		mapTableIndex.clear();
		mapSequence.clear();
		for (ObjectDefinition ob : lstObjectDefinition) {
			String currentGroup = "";
			if (ob.getParentObjectDefinitionId() != null) {
				currentGroup = mapTableIndex.get(ob.getParentObjectDefinitionId());
			}
			ob.setGroupId(currentGroup);
			int maxIndex = mapSequence.getOrDefault(ob.getGroupId(), 0);
			maxIndex++;
			if (ob.getParentObjectDefinitionId() == null) {
				ob.setTableIndex(String.valueOf(maxIndex));
			} else {
				ob.setTableIndex(currentGroup + "." + maxIndex);
			}
			mapTableIndex.put(ob.getObjectDefinitionId(), ob.getTableIndex());
			mapSequence.put(ob.getGroupId(), maxIndex);
		}
		List<SequenceConnector> lstSequenceConnectors = BusinessDesignHelper.parseSequenceConnector(form.getJsonConnector());
		List<SequenceLogic> lstSequenceLogics = BusinessDesignHelper.parseSequenceLogic(form.getJsonComponent());
		for (SequenceLogic objLogic : lstSequenceLogics) {
			BusinessDesignHelper.assignStyleComponent(objLogic, false);
		}

		form.setLstInputBean(lstInputBean);
		form.setLstOutputBean(lstOutputBean);
		form.setLstObjectDefinition(lstObjectDefinition);
		form.setLstSequenceConnectors(lstSequenceConnectors);
		form.setLstSequenceLogics(lstSequenceLogics);
		if(form.getFile() != null && form.getFile().getSize() >0){
			// add warning message : User choose file again.
			form.setFileName("");
			form.setIsShowWarningFile(true);
		}
		return form;
	}

	/**
	 * Initialize blogic design screen
	 *
	 * @param form
	 *            BusinessDesignForm
	 * @return REGISTER_FORM_PATH
	 */
	@RequestMapping(value = "designBlogic", method = { RequestMethod.GET })
	public String displayDesignBlogic(BusinessDesignForm businessDesignForm, @RequestParam(value = "sequenceLogicId", required = false) String sequenceLogicId, @RequestParam(value = "isOnlyView", required = false) boolean isOnlyView, Model model) {
		// NestedLogicComponent nestedLogicComponent =
		// businessDesignService.parseInformationOfNestedlogic(data,
		// isOnlyView);
		// nestedLogicComponentForm = beanMapper.map(nestedLogicComponent,
		// NestedLogicComponentForm.class);
		if(businessDesignForm.getBusinessLogicId() == null){
			businessDesignForm.setBusinessLogicId(-1L);
		}
		CommonModel commonModel = this.initCommon();
		List<PatternedComponent> lstPatternedComponents = businessDesignService.findPatternedComponentOfProject(commonModel);
		model.addAttribute("lstPatternedComponents", lstPatternedComponents);
		model.addAttribute("isOnlyView", isOnlyView);
		model.addAttribute("sequenceLogicId", sequenceLogicId);
		return "businessdesign/designLogicForm";
	}

}
