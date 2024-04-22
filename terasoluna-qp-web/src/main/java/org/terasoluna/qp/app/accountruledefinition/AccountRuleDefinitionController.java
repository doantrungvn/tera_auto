package org.terasoluna.qp.app.accountruledefinition;

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
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.message.AccountRuleDefinitionMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.AccountRuleDefinition;
import org.terasoluna.qp.domain.repository.accountruledefinition.AccountRuleDefinitionSearchCriteria;
import org.terasoluna.qp.domain.service.accountruledefinition.AccountRuleDefinitionService;

@Controller
@RequestMapping(value = "accountruledefinition")
@TransactionTokenCheck(value = "accountruledefinition")
@SessionAttributes(types = { AccountRuleDefinitionSearchForm.class })
public class AccountRuleDefinitionController {
	private static final Logger logger = LoggerFactory.getLogger(AccountRuleDefinitionController.class);
	private static final String ACTION_SEARCH = "/accountruledefinition/search";
	private static final String SEARCH_FORM_PATH = "accountruledefinition/searchForm";
	private static final String REGISTER_FORM_PATH = "accountruledefinition/registerForm";
	private static final String VIEW_FORM_PATH = "accountruledefinition/viewForm";
	private static final String MODIFY_FORM_PATH = "accountruledefinition/modifyForm";
	private static final String REDIRECT_DELETECOMPLETE = "redirect:/complete";
	private static final String REDIRECT_SEARCH = "redirect:/accountruledefinition/search";
	//private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";
	
	@Inject
	AccountRuleDefinitionService accountRuleDefinitionService;
	@Inject
	AccountRuleDefinitionValidator accountRuleDefinitionValidator;
	@Inject
	AccountRuleDefinitionFunction accountRuleDefinitionFunction;
	@Inject
	Mapper beanMapper;
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used
	 * for populating command and form object
	 * 
	 * @param webDataBinder
	 *            WebDataBinder
	 */
	@InitBinder("accountRuleDefinitionForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		webDataBinder.addValidators(accountRuleDefinitionValidator);
	}
	
	/**
	 * Identifies methods which initialize the search form object
	 * 
	 * @return accountRuleDefinitionSearchForm
	 */
	@ModelAttribute
	public AccountRuleDefinitionSearchForm setUpAccountRuleDefinitionSearchForm() {
		AccountRuleDefinitionSearchForm accountRuleDefinitionSearchForm = new AccountRuleDefinitionSearchForm();
		logger.debug("Init form {}", accountRuleDefinitionSearchForm);
		return accountRuleDefinitionSearchForm;
	}
	
	/**
	 * Identifies methods which initialize the search form object
	 * 
	 * @return accountRuleDefinitionForm
	 */
	@ModelAttribute
	public AccountRuleDefinitionForm setUpAccountRuleDefinitionForm() {
		AccountRuleDefinitionForm accountRuleDefinitionForm = new AccountRuleDefinitionForm();
		logger.debug("Init form {}", accountRuleDefinitionForm);
		return accountRuleDefinitionForm;
	}
	
	/**
	 * Initialize search account rule definition screen
	 * 
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute AccountRuleDefinitionSearchForm accountRuleDefinitionSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if(init != null) {
			sessionStatus.setComplete();
			accountRuleDefinitionSearchForm = setUpAccountRuleDefinitionSearchForm();
			model.addAttribute("accountRuleDefinitionSearchForm", accountRuleDefinitionSearchForm);
		}
		
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		AccountRuleDefinitionSearchCriteria accountRuleDefinitionCriteria = beanMapper.map(accountRuleDefinitionSearchForm, AccountRuleDefinitionSearchCriteria.class);
		Page<AccountRuleDefinition> pageAccountRuleDefinition = accountRuleDefinitionService.findPageByCriteria(accountRuleDefinitionCriteria, pageable);
		model.addAttribute("page", pageAccountRuleDefinition);
		return SEARCH_FORM_PATH;
	}
	
	/**
	 * Search account rule definition process
	 * 
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(@ModelAttribute AccountRuleDefinitionSearchForm accountRuleDefinitionSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus status) {
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		AccountRuleDefinitionSearchCriteria accountRuleDefinitionCriteria = beanMapper.map(accountRuleDefinitionSearchForm, AccountRuleDefinitionSearchCriteria.class);
		Page<AccountRuleDefinition> pageAccountRuleDefinition = accountRuleDefinitionService.findPageByCriteria(accountRuleDefinitionCriteria, pageable);
		model.addAttribute("page", pageAccountRuleDefinition);
		return SEARCH_FORM_PATH;
	}
	
	/**
	 * Initialize register account rule definition screen
	 * 
	 * @return REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(AccountRuleDefinitionForm accountRuleDefinitionForm) {
		return REGISTER_FORM_PATH;
	}
	
	/**
	 * Register account rule definition process
	 * 
	 * @return REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated AccountRuleDefinitionForm accountRuleDefinitionForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		if (result.hasErrors()) {
			return REGISTER_FORM_PATH;
		}
		
		try {
			AccountRuleDefinition accountRuleDefinition = accountRuleDefinitionFunction.mapFromFormToObject(accountRuleDefinitionForm);
			accountRuleDefinitionService.register(accountRuleDefinition);
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return REGISTER_FORM_PATH;
		}
		
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0039)));
		return REDIRECT_SEARCH;
	}
	
	/**
	 * Initialize view account rule definition screen
	 * 
	 * @return VIEW_FORM_PATH
	 */
	@RequestMapping(value = "view")
	public String displayView(AccountRuleDefinitionForm accountRuleDefinitionForm, Model model, RedirectAttributes redirectAttr) {
		AccountRuleDefinition accountRuleDefinition = new AccountRuleDefinition();
		
		try {
			accountRuleDefinition = accountRuleDefinitionService.getAccountRuleDefinitionByAccountRuleDefinitionId(accountRuleDefinitionForm.getAccountRuleDefinitionId());
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("notExistFlg", 1);
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			return REDIRECT_SEARCH;
		}
		
		accountRuleDefinitionForm = accountRuleDefinitionFunction.mapFromObjectToForm(accountRuleDefinition);
		model.addAttribute("accountRuleDefinitionForm", accountRuleDefinitionForm);
		model.addAttribute("notExistFlg", 0);
		return VIEW_FORM_PATH;
	}
	
	/**
	 * Initialize modify account rule definition screen
	 * 
	 * @return VIEW_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(AccountRuleDefinitionForm accountRuleDefinitionForm, Model model, RedirectAttributes redirectAttr) {
		AccountRuleDefinition accountRuleDefinition = new AccountRuleDefinition();
		
		try {
			accountRuleDefinition = accountRuleDefinitionService.getAccountRuleDefinitionByAccountRuleDefinitionId(accountRuleDefinitionForm.getAccountRuleDefinitionId());
		} catch (BusinessException ex) {
			
			/*if (MODE_SEARCH.equals(accountRuleDefinitionForm.getMode())) {
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				return REDIRECT_SEARCH;
			} else*/ if (MODE_VIEW.equals(accountRuleDefinitionForm.getMode())) {
				model.addAttribute("notExistFlg", 1);
				model.addAttribute("message", ex.getResultMessages());
				return VIEW_FORM_PATH;
			} else {
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				return REDIRECT_SEARCH;
			}
		}
		
		accountRuleDefinitionForm = accountRuleDefinitionFunction.mapFromObjectToForm(accountRuleDefinition);
		model.addAttribute("accountRuleDefinitionForm", accountRuleDefinitionForm);
		model.addAttribute("notExistFlg", 0);
		return MODIFY_FORM_PATH;
	}
	
	/**
	 * Modify account rule definition process
	 * 
	 * @return MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated AccountRuleDefinitionForm accountRuleDefinitionForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		if (result.hasErrors()) {
			return MODIFY_FORM_PATH;
		}
		
		AccountRuleDefinition accountRuleDefinition = accountRuleDefinitionFunction.mapFromFormToObject(accountRuleDefinitionForm);
		
		try {
			accountRuleDefinitionService.modify(accountRuleDefinition);
		} catch (BusinessException ex) {
			if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", 1);
			}
			model.addAttribute("message", ex.getResultMessages());
			return MODIFY_FORM_PATH;
		}
		
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0039)));
		return REDIRECT_SEARCH;
	}
	
	/**
	 * Delete account design process
	 * 
	 * @return DELETECOMPLETE_FORM_PATH
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(AccountRuleDefinitionForm accountRuleDefinitionForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		AccountRuleDefinition accountRuleDefinition = accountRuleDefinitionFunction.mapFromFormToObjectViewPage(accountRuleDefinitionForm);
		
		try {
			accountRuleDefinitionService.delete(accountRuleDefinition);
		} catch (BusinessException ex) {
			String errMessageCode = StringUtils.defaultString(ex.getResultMessages().getList().get(0).getCode(), CommonMessageConst.ERR_SYS_0037);
			if(CommonMessageConst.ERR_SYS_0037.equals(errMessageCode) || CommonMessageConst.ERR_SYS_0111.equals(errMessageCode)) {
				model.addAttribute("notExistFlg", "1");
			}else{
				model.addAttribute("notExistFlg", "0");
			}
			model.addAttribute("message", ex.getResultMessages());
			List<String> listAppliedUserAccount = accountRuleDefinitionService.getAppliedUserAccount(accountRuleDefinition.getAccountRuleDefinitionId());
			model.addAttribute("listAppliedUserAccount", listAppliedUserAccount);
			accountRuleDefinitionForm = accountRuleDefinitionFunction.mapFromObjectToForm(accountRuleDefinition);
			model.addAttribute("accountRuleDefinitionForm", accountRuleDefinitionForm);
			return VIEW_FORM_PATH;
		}
		
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(AccountRuleDefinitionMessageConst.SC_ACCOUNTRULEDEFINITION_0039)));
		return REDIRECT_DELETECOMPLETE;
	}
}
