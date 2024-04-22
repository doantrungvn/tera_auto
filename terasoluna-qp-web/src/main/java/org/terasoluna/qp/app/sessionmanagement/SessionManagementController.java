package org.terasoluna.qp.app.sessionmanagement;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
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
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.SessionManagement;
import org.terasoluna.qp.domain.repository.sessionmanagement.SessionManagementSearchCriteria;
import org.terasoluna.qp.domain.service.sessionmanagement.SessionManagementService;

@Controller
@RequestMapping(value = "sessionmanagement")
@TransactionTokenCheck(value = "sessionmanagement")
@SessionAttributes(types = { SessionManagementSearchForm.class })
public class SessionManagementController extends BaseController{
	private static final String ACTION_SEARCH = "/sessionmanagement/search";
	private static final String LINK_SEARCH = "sessionmanagement/searchForm";
	private static final String SESSION_MANAGEMENT_FORM_NAME = "sessionManagementForm";
	private static final String LINK_REGISTER = "sessionmanagement/registerForm";
	private static final String LINK_VIEW = "sessionmanagement/viewForm";
	private static final String LINK_MODIFY = "sessionmanagement/modifyForm";
	private static final String SEARCH_REDIRECT_PATH = "redirect:/sessionmanagement/search";
	private static final String REDIRECT_DELETECOMPLETE = "redirect:/complete";
	/*private static final String VIEW_REDIRECT_PATH = "redirect:/SessionManagement/view";*/
	
	private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";

	@Inject
	SessionManagementService sessionManagementService;

	@Inject
	Mapper beanMapper;
	
	@Inject
	SessionManagementValidator sessionManagementValidator;

	@InitBinder
	public void initService() {
		moduleCode = CommonMessageConst.TQP_SESSIONMANAGEMENT;
	}
	
	@InitBinder("sessionManagementForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(sessionManagementValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute
	public SessionManagementSearchForm setUpFormCriteria() {
		SessionManagementSearchForm form = new SessionManagementSearchForm();
		return form;
	}

	@ModelAttribute
	public SessionManagementForm setUpFormSessionManagement() {
		SessionManagementForm form = new SessionManagementForm();
		return form;
	}

	/**
	 * Initialize search session management screen
	 * 
	 * @param sessionManagementSearchForm
	 * @param model
	 * @param pageable
	 * @return SEARCH_FORM_PATH
	 */

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, 
			@ModelAttribute SessionManagementSearchForm sessionManagementSearchForm, Model model,@PageableDefault Pageable pageable,SessionStatus sessionStatus) {
	
		if(init != null){
			sessionStatus.setComplete();
			sessionManagementSearchForm = setUpFormCriteria();
			model.addAttribute("sessionManagementSearchForm", sessionManagementSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		
		checkChangeProject(false); 
		
		pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
		sessionManagementSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		SessionManagementSearchCriteria criteria = beanMapper.map(sessionManagementSearchForm,SessionManagementSearchCriteria.class);
		CommonModel common = this.initCommon();
		Page<SessionManagement> page = sessionManagementService.findSessionManagementBySearchCriteria(criteria,pageable, common);
		model.addAttribute("page", page);
		return LINK_SEARCH;
	}
	/**
	 * Search session management process
	 * 
	 * @param sessionManagementSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(@ModelAttribute SessionManagementSearchForm sessionManagementSearchForm, Model model,@PageableDefault Pageable pageable, SessionStatus status) {
		checkChangeProject(false); 
		sessionManagementSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
		SessionManagementSearchCriteria criteria = beanMapper.map(sessionManagementSearchForm,SessionManagementSearchCriteria.class);
		CommonModel common = this.initCommon();
		Page<SessionManagement> pageSessionManagement = sessionManagementService.findSessionManagementBySearchCriteria(criteria, pageable, common);
		model.addAttribute("page", pageSessionManagement);
		return LINK_SEARCH;
	}

	/**
	 * Initialize register session management screen
	 * 
	 * @param sessionManagementForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(@ModelAttribute(SESSION_MANAGEMENT_FORM_NAME) SessionManagementForm sessionManagementForm,Model model) {
		checkChangeProject(true); 
		sessionManagementForm.setProjectId(SessionUtils.getCurrentProjectId());
		return LINK_REGISTER;
	}

	/**
	 * Register session management process
	 * 
	 * @param sessionManagementForm
	 * @param model
	 * @return SEARCH_REDIRECT_PATH in case of success. Otherwise return to
	 *         Initialize register session management screen
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated SessionManagementForm sessionManagementForm,BindingResult bindingResult, Model model,RedirectAttributes redirectAttr) {
		checkChangeProject(true); 
		sessionManagementForm.setProjectId(SessionUtils.getCurrentProjectId());
		if (bindingResult.hasErrors()) {
			return LINK_REGISTER;
		}
		SessionManagement sessionManagement = beanMapper.map(sessionManagementForm, SessionManagement.class);
		
		try {
			CommonModel common = this.initCommon();

			Long accountId = SessionUtils.getAccountId();
			sessionManagement.setCreatedBy(accountId);
			sessionManagement.setUpdatedBy(accountId);
			sessionManagementService.registerSessionManagement(sessionManagement, common);
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return LINK_REGISTER;
		}
		redirectAttr.addFlashAttribute("message",ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(CommonMessageConst.TQP_SESSIONMANAGEMENT)));
		return SEARCH_REDIRECT_PATH;
	}

	@RequestMapping(value = "view")
	public String displayView(@ModelAttribute(SESSION_MANAGEMENT_FORM_NAME) SessionManagementForm sessionManagementForm, Model model, RedirectAttributes redirectAttr) {
		SessionManagement sessionManagement = null;
		try {
			checkChangeProject(false);
			sessionManagement = sessionManagementService.findSessionManagementById(sessionManagementForm.getSessionManagementId());
		} catch (BusinessException ex) {
			//notExistFlg = 1: show 
			//notExistFlg = 0: hidden
			model.addAttribute("notExistFlg", 1);
			/*model.addAttribute("sessionManagementForm", null);*/
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}

		sessionManagementForm = beanMapper.map(sessionManagement, SessionManagementForm.class);
		model.addAttribute("sessionManagementForm", sessionManagementForm);
		model.addAttribute("notExistFlg", 0);
		return LINK_VIEW;
	}

	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify")
	public String displayModify(@ModelAttribute(SESSION_MANAGEMENT_FORM_NAME) SessionManagementForm sessionManagementForm, Model model, RedirectAttributes redirectAttr) {
		if (sessionManagementForm.getSessionManagementType() == null || !"1".equals(sessionManagementForm.getSessionManagementType())){
			return SEARCH_REDIRECT_PATH;
		}
		
		String destination = StringUtils.EMPTY;
		try{
			checkChangeProject(true); 
			SessionManagement sessionManagement = sessionManagementService.findSessionManagementById(sessionManagementForm.getSessionManagementId());
			sessionManagementForm = beanMapper.map(sessionManagement, SessionManagementForm.class);
			model.addAttribute("sessionManagementForm", sessionManagementForm);
			destination = LINK_MODIFY;
		} catch (BusinessException be) {
			model.addAttribute("notExistFlg", 1);
			if (MODE_SEARCH.equals(sessionManagementForm.getMode())) {
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(sessionManagementForm.getMode())) {
				model.addAttribute("message", be.getResultMessages());
				destination = LINK_VIEW;
			}
		}
		return destination;
	}

	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated SessionManagementForm form,BindingResult bindingResult, Model model,RedirectAttributes attributes) {
		checkChangeProject(true); 
		
		if (form.getSessionManagementType() == null || !"1".equals(form.getSessionManagementType())){
			return SEARCH_REDIRECT_PATH;
		}
		
		if (bindingResult.hasErrors()) {
		    List<BusinessDesign> lstBusinessDesign = sessionManagementService.getBusinessDesign(form.getSessionManagementId());
		    form.setListBusinessDesign(lstBusinessDesign);
			return LINK_MODIFY;
		}

		SessionManagement sessionManagement = beanMapper.map(form, SessionManagement.class);
		try{
			CommonModel common = this.initCommon();

			Long accountId = SessionUtils.getAccountId();
			sessionManagement.setCreatedBy(accountId);
			sessionManagement.setUpdatedBy(accountId);
			sessionManagementService.modifySessionManagement(sessionManagement, common);
			attributes.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(CommonMessageConst.TQP_SESSIONMANAGEMENT)));
			return SEARCH_REDIRECT_PATH;
		} catch(BusinessException ex){

			if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", "1");
			}

			model.addAttribute("message", ex.getResultMessages());
			return LINK_MODIFY;
		}
	}
	
	@RequestMapping(value = "delete")
	public String processDelete(SessionManagementForm sessionManagementForm, RedirectAttributes redirectAttr, Model model) {
		checkChangeProject(true); 

		if (sessionManagementForm.getSessionManagementType() == null || !"1".equals(sessionManagementForm.getSessionManagementType())){
			return SEARCH_REDIRECT_PATH;
		}
		
		SessionManagement sessionManagement = beanMapper.map(sessionManagementForm, SessionManagement.class);
		try {
			CommonModel common = this.initCommon();

			Long accountId = SessionUtils.getAccountId();
			sessionManagement.setCreatedBy(accountId);
			sessionManagement.setUpdatedBy(accountId);
			sessionManagementService.deleteSessionManagement(sessionManagement, common);
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004,  MessageUtils.getMessage(CommonMessageConst.TQP_SESSIONMANAGEMENT)));
			return REDIRECT_DELETECOMPLETE;
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("notExistFlg", "0");
		}

		return LINK_VIEW;
	}
}
