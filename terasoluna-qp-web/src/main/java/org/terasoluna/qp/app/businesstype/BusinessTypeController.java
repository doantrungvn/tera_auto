package org.terasoluna.qp.app.businesstype;

import java.util.List;

import javax.inject.Inject;

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
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.BusinessTypeMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.BusinessType;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.repository.businesstype.BusinessTypeCriteria;
import org.terasoluna.qp.domain.service.businesstype.BusinessTypeService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Controller
@RequestMapping(value = "businesstype")
@SessionAttributes(types = { BusinessTypeSearchForm.class })
public class BusinessTypeController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BusinessTypeController.class);
	private static final String BUSINESSTYPE_SEARCH_FORM_NAME = "businessTypeSearchForm";
	private static final String BUSINESSTYPE_FORM_NAME = "businessTypeForm";
	
	private static final String SEARCH_FORM_PATH ="businesstype/searchForm";
	private static final String SEARCH_ACTION_PATH ="/businesstype/search";
	private static final String REGISTER_FORM_PATH = "businesstype/registerForm";
	private static final String VIEW_FORM_PATH = "businesstype/viewForm";
	private static final String MODIFY_FORM_PATH = "businesstype/modifyForm";
	//private static final String REDIRECT_DELETION_SUCCESS = "redirect:/complete";
	private static final String SEARCH_REDIRECT_PATH = "redirect:/businesstype/search";
	/*private static final String VIEW_REDIRECT_PATH = "redirect:/businesstype/view";
	private static final String MODE_SEARCH = "0";*/
	private static final String MODE_VIEW = "1";
	
	@Inject
	Mapper beanMapper;

	@Inject
	BusinessTypeService businessTypeService;
	
	@Inject
	ProjectService projectService;

	@InitBinder(BUSINESSTYPE_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@InitBinder
	public void initService(WebDataBinder webDataBinder) {
		moduleCode = CommonMessageConst.TQP_BUSINESSTYPE;
	}
	
	/**
	 * Identifies methods which initialize the business type form object
	 * @return businessTypeForm business type form
	 */
	@ModelAttribute
	public BusinessTypeForm setUpBusinessTypeForm() {
		BusinessTypeForm businessTypeForm = new BusinessTypeForm();
		logger.debug("Init form {0}", businessTypeForm);
		return businessTypeForm;
	}
	
	/**
	 * Identifies methods which initialize the search form object
	 * @return businessTypeSearchForm search business type form
	 */
	@ModelAttribute
	public BusinessTypeSearchForm setUpBusinessTypeSearchForm() {
		BusinessTypeSearchForm businessTypeSearchForm = new BusinessTypeSearchForm();
		logger.debug("Init form {0}", businessTypeSearchForm);
		return businessTypeSearchForm;
	}

	/**
	 * pre-initialization of form backed bean
	 * @return
	 */
	@ModelAttribute("businessTypes")
	List<BusinessType> setUpBusinessTypes() {
		List<BusinessType> businessTypes = businessTypeService.findAllBusinessTypeTree(SessionUtils.getCurrentProjectId());
		return businessTypes;
	}
	
	/**
	 * Initialize search business type screen
	 * @param businessTypeSearchForm
	 * @param model
	 * @param pageable
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute (BUSINESSTYPE_SEARCH_FORM_NAME) BusinessTypeSearchForm businessTypeSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if(init != null){
			sessionStatus.setComplete();
			businessTypeSearchForm = new BusinessTypeSearchForm();
			model.addAttribute(BUSINESSTYPE_SEARCH_FORM_NAME, businessTypeSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}

		checkChangeProject(false);
		
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		BusinessTypeCriteria businessTypeCriteria = beanMapper.map(businessTypeSearchForm, BusinessTypeCriteria.class);
		businessTypeCriteria.setProjectId(SessionUtils.getCurrentProject().getProjectId());
		Page<BusinessType> page = businessTypeService.searchBusinessType(businessTypeCriteria, pageable);
		model.addAttribute("page", page);
		
		return SEARCH_FORM_PATH;
	}

	/**
	 * Search business type process
	 * @param businessTypeSearchForm
	 * @param model
	 * @param pageable
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(@ModelAttribute(BUSINESSTYPE_SEARCH_FORM_NAME) BusinessTypeSearchForm businessTypeSearchForm, Model model, @PageableDefault Pageable pageable) {

		checkChangeProject(false);
		
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		BusinessTypeCriteria businessTypeCriteria = beanMapper.map(businessTypeSearchForm, BusinessTypeCriteria.class);
		businessTypeCriteria.setProjectId(SessionUtils.getCurrentProject().getProjectId());
		Page<BusinessType> page = businessTypeService.searchBusinessType(businessTypeCriteria, pageable);
		model.addAttribute("page", page);
		
		return SEARCH_FORM_PATH;
	}

	/**
	 * Initialize register business type screen
	 * @return REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(BusinessTypeForm businessTypeForm,Model model,RedirectAttributes redirectAttr) {
		try {
			checkChangeProject(true);
			return REGISTER_FORM_PATH;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}
	}
	
	/**
	 * Register business type process
	 * @param projectForm business type information
	 * @param result BindingResult
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return in case of success SEARCH_REDIRECT_PATH will be returned.
	 *  Otherwise it will be returned to REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated BusinessTypeForm businessTypeForm, BindingResult result, Model model, RedirectAttributes redirectAttr) {

		if (result.hasErrors()) {
			return REGISTER_FORM_PATH;
		}

		try {
			checkChangeProject(true);
			BusinessType businessType = beanMapper.map(businessTypeForm, BusinessType.class);
			businessType.setAccountId(SessionUtils.getAccountId());
			businessType.setProjectId(SessionUtils.getCurrentProjectId());
			businessTypeService.registerBusinessType(businessType);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return REGISTER_FORM_PATH;
		}

		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(BusinessTypeMessageConst.SC_BUSINESSTYPE_0004)));
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * View business type process
	 * @param projectForm ProjectForm
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return VIEW_FORM_PATH
	 */
	@RequestMapping(value = "view")
	public String processView(BusinessTypeForm businessTypeForm, Model model,RedirectAttributes redirectAttr) {
		
		/*checkChangeProject(false);*/
		
		BusinessType businessType = beanMapper.map(businessTypeForm, BusinessType.class);
		
		try {
			CommonModel commonModel = new CommonModel();
			businessType = businessTypeService.findBusinessTypeById(businessType, commonModel);
			businessType.setModules(businessTypeService.findListModule(businessType.getBusinessTypeId()));
		} catch (BusinessException be) {
			businessTypeForm.setHasErrors(true);
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}
		
		businessTypeForm = beanMapper.map(businessType, BusinessTypeForm.class);
		model.addAttribute(BUSINESSTYPE_FORM_NAME, businessTypeForm);
		return VIEW_FORM_PATH;
	}

	/**
	 * Initialize modify business type screen
	 * @param projectForm ProjectForm
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(BusinessTypeForm businessTypeForm, Model model, RedirectAttributes redirectAttr) {

		String destination = StringUtils.EMPTY;

		try {
			
			checkChangeProject(true);
			
			BusinessType businessType = beanMapper.map(businessTypeForm, BusinessType.class);
			CommonModel commonModel = new CommonModel();
			businessType = businessTypeService.findBusinessTypeById(businessType, commonModel);
			
			businessTypeForm = beanMapper.map(businessType, BusinessTypeForm.class);
			model.addAttribute(BUSINESSTYPE_FORM_NAME, businessTypeForm);
			List<BusinessType> businessTypes = businessTypeService.findAllBusinessTypeTreeNotThis(businessType.getBusinessTypeId(), SessionUtils.getCurrentProjectId());
			model.addAttribute("businessTypes", businessTypes);
			destination = MODIFY_FORM_PATH;
		} catch (BusinessException be) {
			
			if (MODE_VIEW.equals(businessTypeForm.getMode())) {
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_BUSINESSTYPE)); // message title
				redirectAttr.addFlashAttribute("message", be.getResultMessages());// message content
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			}
			
			/*if (MODE_SEARCH.equals(businessTypeForm.getMode())) {
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(businessTypeForm.getMode())) {
				model.addAttribute("notExistFlg", 1);
				model.addAttribute("message",be.getResultMessages());
				destination = VIEW_FORM_PATH;
			}*/
			
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}
		
		return destination;
	}
	
	/**
	 * Modify business type process
	 * @param projectForm ProjectForm
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return SEARCH_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated BusinessTypeForm businessTypeForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {

		if (result.hasErrors()) {
			return MODIFY_FORM_PATH;
		}

		try {
			checkChangeProject(true);
			BusinessType businessType = beanMapper.map(businessTypeForm, BusinessType.class);
			businessType.setAccountId(SessionUtils.getAccountId());
			businessType.setProjectId(SessionUtils.getCurrentProjectId());
			businessTypeService.modifyBusinessType(businessType);
		} catch (BusinessException be) {
			/*if(CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", 1);
			}*/
			model.addAttribute("message", be.getResultMessages());
			return MODIFY_FORM_PATH;
		}
		
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(BusinessTypeMessageConst.SC_BUSINESSTYPE_0004)));
		return SEARCH_REDIRECT_PATH;		
	}
	/**
	 * Delete business type process
	 * In case business type is in-used, Business type delete process will not be success
	 * @param projectForm ProjectForm
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(BusinessTypeForm businessTypeForm, Model model, RedirectAttributes redirectAttr) {

		try {
			checkChangeProject(true);
			BusinessType businessType = beanMapper.map(businessTypeForm, BusinessType.class);
			businessTypeService.deleteBusinessType(businessType);
		} catch (BusinessException be) {
			/*if(CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", 1);
				model.addAttribute("message",be.getResultMessages());
				return VIEW_FORM_PATH;
			}
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			redirectAttr.addAttribute("businessTypeId", businessType.getBusinessTypeId());
			return VIEW_REDIRECT_PATH;*/

			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_BUSINESSTYPE)); // message title
			redirectAttr.addFlashAttribute("message", be.getResultMessages());// message content
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
		}
		
		redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_BUSINESSTYPE)); // message title
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(BusinessTypeMessageConst.SC_BUSINESSTYPE_0004)));
		return DbDomainConst.REDIRECT_DELETION_SUCCESS;
	}
}
