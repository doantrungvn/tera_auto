/*
 * @(#)ModuleController.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.app.functiondesign;

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
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.FunctionDesignMessageConst;
import org.terasoluna.qp.domain.model.FunctionDesign;
import org.terasoluna.qp.domain.repository.functiondesign.FunctionDesignCriteria;
import org.terasoluna.qp.domain.service.functiondesign.FunctionDesignService;

@Controller
@RequestMapping(value = "functiondesign")
@TransactionTokenCheck(value = "functiondesign")
@SessionAttributes(types = { FunctionDesignSearchForm.class })
public class FunctionDesignController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(FunctionDesignController.class);
	private static final String FUNCTION_DESIGN_FORM_NAME = "functionDesignForm";
	private static final String FUNCTION_DESIGN_SEARCH_FORM_NAME = "functionDesignSearchForm";
	private static final String SEARCH_FORM_PATH = "functiondesign/searchForm";
	private static final String SEARCH_ACTION_PATH ="/functiondesign/search";
	private static final String REGISTER_FORM_PATH = "functiondesign/registerForm";
	private static final String VIEW_FORM_PATH = "functiondesign/viewForm";
	private static final String MODIFY_FORM_PATH = "functiondesign/modifyForm";
	//private static final String REDIRECT_DELETION_SUCCESS = "redirect:/complete";
	private static final String SEARCH_REDIRECT_PATH = "redirect:/functiondesign/search";
	//private static final String VIEW_REDIRECT_PATH = "redirect:/functiondesign/view";
	//private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";

	@Inject
	FunctionDesignService functionDesignService;
	
	@Inject
	Mapper beanMapper;
	
	@InitBinder
	public void init() {
		moduleCode = CommonMessageConst.TQP_FUNCTIONDESIGN;
	}
	
	/**
	 * Identifies methods which initialize the project form object
	 * @return projectForm
	 */
	@ModelAttribute
	public FunctionDesignSearchForm setUpFunctionDesignSearchForm() {
		FunctionDesignSearchForm functionDesignSearchForm = new FunctionDesignSearchForm();
		logger.debug("Init form {0}", functionDesignSearchForm);
		return functionDesignSearchForm;
	}
	
	/**
	 * Identifies methods which initialize the project search form object
	 * @return projectForm
	 */
	@ModelAttribute
	public FunctionDesignForm setUpFunctionDesignForm() {
		FunctionDesignForm functionDesignForm = new FunctionDesignForm();
		logger.debug("Init form {0}", functionDesignForm);
		return functionDesignForm;
	}
	
	@InitBinder(FUNCTION_DESIGN_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/**
	 * Initialize search module screen
	 * 
	 * @param moduleSearchForm
	 * @param model
	 * @param pageable
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute(FUNCTION_DESIGN_SEARCH_FORM_NAME) FunctionDesignSearchForm functionDesignSearchForm, 
			Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if(init != null){
			sessionStatus.setComplete();
			functionDesignSearchForm = new FunctionDesignSearchForm();
			model.addAttribute(FUNCTION_DESIGN_SEARCH_FORM_NAME, functionDesignSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		FunctionDesignCriteria functionDesignCriteria = beanMapper.map(functionDesignSearchForm, FunctionDesignCriteria.class);
		Page<FunctionDesign> page = functionDesignService.searchFunctionDesign(functionDesignCriteria, pageable, SessionUtils.getCurrentProjectId());
		model.addAttribute("page", page);
		return SEARCH_FORM_PATH;
	}

	/**
	 * Search module process
	 * 
	 * @param moduleForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search",  method = RequestMethod.POST)
	public String processSearch(@ModelAttribute(FUNCTION_DESIGN_SEARCH_FORM_NAME) FunctionDesignSearchForm functionDesignSearchForm, Model model, @PageableDefault Pageable pageable) {
		checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		FunctionDesignCriteria functionDesignCriteria = beanMapper.map(functionDesignSearchForm, FunctionDesignCriteria.class);
		Page<FunctionDesign> page = functionDesignService.searchFunctionDesign(functionDesignCriteria, pageable, SessionUtils.getCurrentProjectId());
		model.addAttribute("page", page);

		return SEARCH_FORM_PATH;
	}
	
	/**
	 * Initialize register module screen
	 * @param moduleForm ModuleForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(@ModelAttribute(FUNCTION_DESIGN_FORM_NAME) FunctionDesignForm functionDesignForm, RedirectAttributes redirectAttr, Model model) {
		try {
			checkChangeProject(true);
			return REGISTER_FORM_PATH;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_FORM_PATH;
		}
	}

	/**
	 * Register module process
	 * @param moduleForm
	 * @param model
	 * @return SEARCH_REDIRECT_PATH in case of success. 
	 * Otherwise return to Initialize register module screen
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated FunctionDesignForm functionDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model, SessionStatus sessionStatus) {
		
		if (result.hasErrors()) {
			return REGISTER_FORM_PATH;
		}

		try {
			checkChangeProject(true);
			FunctionDesign functionDesign = beanMapper.map(functionDesignForm, FunctionDesign.class);
			functionDesign.setAccountId(SessionUtils.getAccountId());
			functionDesign.setProjectId(SessionUtils.getCurrentProjectId());
			functionDesignService.register(functionDesign);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return REGISTER_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0006)));
		return SEARCH_REDIRECT_PATH;
	}
	
	/**
	 * return view screen
	 * @param moduleForm
	 * @param model
	 * @return VIEW_FORM_PATH
	 */
	@RequestMapping(value = "view")
	public String processView(@ModelAttribute(FUNCTION_DESIGN_FORM_NAME) FunctionDesignForm functionDesignForm, Model model,RedirectAttributes redirectAttr,
								@RequestParam("status") Integer status) {
		checkChangeProject(false);
		FunctionDesign functionDesign = null;
		try {
			functionDesign = functionDesignService.findFunctionDesignById(functionDesignForm.getFunctionId());
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}
		
		functionDesignForm = beanMapper.map(functionDesign, FunctionDesignForm.class);
		functionDesignForm.setStatus(status);
		model.addAttribute(FUNCTION_DESIGN_FORM_NAME, functionDesignForm);
		
		return VIEW_FORM_PATH;
	}
	
	/**
	 * Initialize modify module screen
	 * @param moduleForm ModuleForm
	 * @param model Model
	 * @return
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify",  method = RequestMethod.GET)
	public String displayModify(@ModelAttribute(FUNCTION_DESIGN_FORM_NAME) FunctionDesignForm functionDesignForm, Model model, RedirectAttributes redirectAttr) {
		String destination = StringUtils.EMPTY;
		FunctionDesign functionDesign = beanMapper.map(functionDesignForm, FunctionDesign.class);
		try {
			checkChangeProject(true);
			functionDesign = functionDesignService.findFunctionDesignById(functionDesign.getFunctionId());
			functionDesignForm = beanMapper.map(functionDesign, FunctionDesignForm.class);
			model.addAttribute("functionDesignForm", functionDesignForm);
			destination = MODIFY_FORM_PATH;
		} catch (BusinessException be) {
			/*if (MODE_SEARCH.equals(functionDesignForm.getMode())) {
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(functionDesignForm.getMode())) {
				model.addAttribute("notExistFlg", 1);
				model.addAttribute("message", be.getResultMessages());
				destination = VIEW_FORM_PATH;
			}*/
			
			if (MODE_VIEW.equals(functionDesignForm.getMode())) {
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_FUNCTIONDESIGN)); // message title
				redirectAttr.addFlashAttribute("message", be.getResultMessages());// message content
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			}

			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
			
			
		}
		return destination;
	}
	
	/**Modify module process
	 * Edit a module
	 * @param moduleForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated @ModelAttribute FunctionDesignForm functionDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		
		if (result.hasErrors()) {
			return MODIFY_FORM_PATH;
		}
		
		try {
			checkChangeProject(true);
			FunctionDesign functionDesign = beanMapper.map(functionDesignForm, FunctionDesign.class);
			functionDesign.setProjectId(SessionUtils.getCurrentProjectId());
			functionDesign.setAccountId(SessionUtils.getAccountId());
			functionDesignService.modify(functionDesign);
		} catch (BusinessException be) {
			/*if(CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("message", be.getResultMessages());
				model.addAttribute("notExistFlg", 1);
				return MODIFY_FORM_PATH;
			}*/
			model.addAttribute("message", be.getResultMessages());
			return MODIFY_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0006)));
		return SEARCH_REDIRECT_PATH;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute FunctionDesignForm functionDesignForm, Model model, RedirectAttributes redirectAttr) {
		
		try {
			checkChangeProject(true);
			FunctionDesign functionDesign = beanMapper.map(functionDesignForm, FunctionDesign.class);
			functionDesign.setProjectId(SessionUtils.getCurrentProjectId());
			functionDesignService.delete(functionDesign);
		} catch (BusinessException be) {
			/*if(CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("message", be.getResultMessages());
				model.addAttribute("notExistFlg", 1);
				return VIEW_FORM_PATH;
			}

			model.addAttribute("message", be.getResultMessages());
			model.addAttribute("functionDesignForm", functionDesignForm);
			model.addAttribute("notExistFlg", 0);
			return VIEW_FORM_PATH;*/
			
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_FUNCTIONDESIGN)); // message title
			redirectAttr.addFlashAttribute("message", be.getResultMessages());// message content
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			

		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(FunctionDesignMessageConst.SC_FUNCTIONDESIGN_0006)));
		return DbDomainConst.REDIRECT_DELETION_SUCCESS;
	}
}