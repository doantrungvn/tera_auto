package org.terasoluna.qp.app.account;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import org.terasoluna.qp.app.accountproject.AccountProjectForm;
import org.terasoluna.qp.app.accountrole.AccountRoleForm;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.AccountMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountRole;
import org.terasoluna.qp.domain.model.AccountRuleDefinition;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.Role;
import org.terasoluna.qp.domain.repository.account.AccountSearchCriteria;
import org.terasoluna.qp.domain.service.account.AccountDetails;
import org.terasoluna.qp.domain.service.account.AccountService;
import org.terasoluna.qp.domain.service.accountrole.AccountRoleService;
import org.terasoluna.qp.domain.service.accountruledefinition.AccountRuleDefinitionService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Controller
@RequestMapping(value = "account")
@TransactionTokenCheck(value = "account")
@SessionAttributes(types = { AccountSearchForm.class })
public class AccountController {
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	private static final String ACTION_SEARCH = "/account/search";
	private static final String SEARCH_FORM_PATH = "account/searchForm";
	private static final String REGISTER_FORM_PATH = "account/registerForm";
	private static final String VIEW_FORM_PATH = "account/viewForm";
	private static final String MODIFY_FORM_PATH = "account/modifyForm";
	private static final String REDIRECT_DELETECOMPLETE = "redirect:/complete";
	private static final String REDIRECT_SEARCH = "redirect:/account/search";
	private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";
	@Inject
	AccountService accountService;
	@Inject
	ProjectService projectService;
	@Inject
	AccountRuleDefinitionService accountRuleDefinitionService;
	@Inject
	Mapper beanMapper;
	@Inject
	AccountRoleService accountRoleService;
	@Inject
	PasswordGeneration passwordGeneration;
	@Inject
	AccountPassEqualsValidator accountPassEqualsValidator;
	@Inject
	PasswordRuleValidator passwordRuleValidator;
	@Inject
	AccountUsernameValidator accountUsernameValidator;

	@Autowired
	private SessionRegistry sessionRegistry;
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * 
	 * @param webDataBinder
	 *            WebDataBinder
	 */
	@InitBinder("accountForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(accountPassEqualsValidator);
		webDataBinder.addValidators(passwordRuleValidator);
		webDataBinder.addValidators(accountUsernameValidator);
	}

	/**
	 * Identifies methods which initialize the account form object
	 * 
	 * @return accountForm
	 */
	@ModelAttribute
	public AccountForm setUpAccountForm() {
		AccountForm form = new AccountForm();
		logger.debug("Init form {}", form);
		return form;
	}

	/**
	 * Identifies methods which initialize the search form object
	 * 
	 * @return accountSearchForm
	 */
	@ModelAttribute
	public AccountSearchForm setUpAccountSearchForm() {
		AccountSearchForm form = new AccountSearchForm();
		logger.debug("Init form {}", form);
		return form;
	}

	@InitBinder("accountForm")
	public void initBinderForm(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * Initialize search account screen
	 * 
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute AccountSearchForm accountSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if (init != null) {
			sessionStatus.setComplete();
			accountSearchForm = setUpAccountSearchForm();
			model.addAttribute("accountSearchForm", accountSearchForm);
		}
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		AccountSearchCriteria accountCriteria = beanMapper.map(accountSearchForm, AccountSearchCriteria.class);
		Page<Account> pageAccount = accountService.findPageByCriteria(accountCriteria, pageable);
		model.addAttribute("page", pageAccount);
		return SEARCH_FORM_PATH;
	}

	/**
	 * Search account process
	 * 
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(@ModelAttribute AccountSearchForm accountSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus status) {
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		AccountSearchCriteria accountCriteria = beanMapper.map(accountSearchForm, AccountSearchCriteria.class);
		Page<Account> pageSample = accountService.findPageByCriteria(accountCriteria, pageable);
		model.addAttribute("page", pageSample);
		return SEARCH_FORM_PATH;
	}

	/**
	 * Initialize register account screen
	 * 
	 * @return REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(AccountForm form, Model model) {
		List<Role> listRole = accountService.getListActiveRolesForAccountRegisterAndModify();
		List<AccountRoleForm> listAccountRoleForm = new ArrayList<AccountRoleForm>();
		beanMapper.map(listRole, listAccountRoleForm);
		form.setAccRoleList(listAccountRoleForm);
		return REGISTER_FORM_PATH;
	}

	/**
	 * Register account process
	 * 
	 * @return REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated AccountForm form, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		StringBuilder generatedPassword = new StringBuilder();

		if (result.hasErrors()) {
			return REGISTER_FORM_PATH;
		}
		try {
			// if rule definition has initial password, use initial password instead of input password
			AccountRuleDefinition accountRuleDefinition = accountRuleDefinitionService.getAccountRuleDefinitionWhenAuthentication(form.getAccountRuleDefinitionId());
			boolean forceChangePassword = false;
			if (accountRuleDefinition != null && accountRuleDefinition.isInitialPassword()) {
				generatedPassword = passwordGeneration.generatePassword(form.getAccountRuleDefinitionId());
				form.setPassword(generatedPassword.toString());
				// QuyND: process in case initial password force change is true
				if(accountRuleDefinition.isInitialPasswordForceChange()){
					forceChangePassword = true;
				}
			}

			Account account = beanMapper.map(form, Account.class);
			account.setCreatedBy(SessionUtils.getAccountId());
			account.setForceChangePassword(forceChangePassword);

			accountService.register(account);
			long accountId = accountService.getCurrentValueAccountSequence();
			List<AccountRoleForm> listAccountRoleForm = form.getAccRoleList();
			List<AccountRole> listAccountRole = new ArrayList<AccountRole>();

			for (AccountRoleForm obj : listAccountRoleForm) {
				if (obj.getRoleId() != null && obj.isSelected()) {
					AccountRole accountRole = new AccountRole();
					accountRole.setAccountId(accountId);
					accountRole.setRoleId(obj.getRoleId());
					listAccountRole.add(accountRole);
				}
			}

			accountRoleService.modifyAccountRole(accountId, listAccountRole, account);
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return REGISTER_FORM_PATH;
		}

		if (generatedPassword.length() == 0) {
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0001)));
		} else {
//			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(AccountRuleDefinitionMessageConst.INF_ACCOUNTRULEDEFINITION_0048, form.getUsername(), generatedPassword));
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0001)));
			redirectAttr.addFlashAttribute("initialPassword", generatedPassword);
			return "redirect:/account/initialPassword";
		}

		return REDIRECT_SEARCH;
	}

	/**
	 * Initialize view account screen
	 * 
	 * @return VIEW_FORM_PATH
	 */
	@RequestMapping(value = "view")
	public String displayView(AccountForm accountForm, Model model, RedirectAttributes redirectAttr) {
		Account accountInfor = new Account();
		try {
			accountInfor = accountService.findOneByAccountId(accountForm.getAccountId());
			List<AccountProjectForm> lstAccProjectForm = new ArrayList<AccountProjectForm>();
			for (Project objProject : accountInfor.getLstProject()) {
				AccountProjectForm accProjectForm = new AccountProjectForm();
				accProjectForm.setAccountId(accountForm.getAccountId());
				accProjectForm.setProjectId(objProject.getProjectId());
				accProjectForm.setProjectName(objProject.getProjectName());
				accProjectForm.setProjectCode(objProject.getProjectCode());
				accProjectForm.setStatus(objProject.getStatus());
				if (objProject.getSelected() != null) {
					accProjectForm.setSelected(true);
				} else {
					accProjectForm.setSelected(false);
				}

				lstAccProjectForm.add(accProjectForm);
			}
			model.addAttribute("lstProject", lstAccProjectForm);
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("notExistFlg", 1);
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			if (MODE_SEARCH.equals(accountForm.getMode())) {
				return VIEW_FORM_PATH;
			} else if (MODE_VIEW.equals(accountForm.getMode())) {
				return REDIRECT_SEARCH;
			}
		}
		accountForm = beanMapper.map(accountInfor, AccountForm.class);
		model.addAttribute("accountForm", accountForm);
		model.addAttribute("lstRole", accountInfor.getLstRole());
		model.addAttribute("lstPermission", accountInfor.getLstPermission());
		model.addAttribute("lstModuleCode", accountInfor.getLstModuleCode());
		model.addAttribute("notExistFlg", 0);
		return VIEW_FORM_PATH;
	}

	/**
	 * Initialize modify account screen
	 * 
	 * @return VIEW_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(AccountForm form, Model model, RedirectAttributes redirectAttr) {
		String destination = StringUtils.EMPTY;
		Account accountInfor = new Account();
		try {
			accountInfor = accountService.findOneByAccountId(form.getAccountId());
			AccountRuleDefinition accountRuleDefinition = accountRuleDefinitionService.getAccountRuleDefinitionByAccountId(form.getAccountId());
			if (accountRuleDefinition != null && accountRuleDefinition.getAccountRuleDefinitionId() != null) {
				accountInfor.setAccountRuleDefinitionId(accountRuleDefinition.getAccountRuleDefinitionId());
				if(accountRuleDefinition.isInitialPassword()) {
					model.addAttribute("initialPassword", true);
				}
			} else {
				model.addAttribute("initialPassword", false);
			}
			
			form = beanMapper.map(accountInfor, AccountForm.class);
			model.addAttribute("accountForm", form);
			destination = MODIFY_FORM_PATH;
		} catch (BusinessException ex) {
			/*if (MODE_SEARCH.equals(form.getMode())) {
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				destination = REDIRECT_SEARCH;
			} else*/ if (MODE_VIEW.equals(form.getMode())) {
				model.addAttribute("message", ex.getResultMessages());
				model.addAttribute("notExistFlg", 1);
				destination = VIEW_FORM_PATH;
			} else {
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				destination = REDIRECT_SEARCH;
			}
		}
		form = beanMapper.map(accountInfor, AccountForm.class);
		form.setPassword("");
		return destination;
	}

	/**
	 * Modify account process
	 * 
	 * @return MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated AccountForm form, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		StringBuilder generatedPassword = new StringBuilder();

		if (result.hasErrors()) {
			//QuyND: add case for validate when account information does not exist
			for (ObjectError item :result.getAllErrors()){
				String errMessageCode = StringUtils.defaultString(item.getCode(), CommonMessageConst.ERR_SYS_0037);
				if(CommonMessageConst.ERR_SYS_0037.equals(errMessageCode)){
					model.addAttribute("notExistFlg", 1);
					model.addAttribute("message", ResultMessages.error().add(item.getCode(),item.getDefaultMessage()));
				}
			}
			ValidationUtils.setBindingResult(result, model);
			return MODIFY_FORM_PATH;
		}

		boolean forceChangePassword = false;
		if (form.isSelectedChangePass()) {
			// if rule definition has initial password, use initial password instead of input password
			AccountRuleDefinition accountRuleDefinition = accountRuleDefinitionService.getAccountRuleDefinitionWhenAuthentication(form.getAccountRuleDefinitionId());
			if (accountRuleDefinition != null && accountRuleDefinition.isInitialPassword()) {
				generatedPassword = passwordGeneration.generatePassword(form.getAccountRuleDefinitionId());
				form.setPassword(generatedPassword.toString());
				// QuyND: process in case initial password force change is true
				if(accountRuleDefinition.isInitialPasswordForceChange()){
					forceChangePassword = true;
				}
			}
		}

		Account account = beanMapper.map(form, Account.class);
		account.setForceChangePassword(forceChangePassword);
		try {
			account.setUpdatedBy(SessionUtils.getAccountId());

			accountService.modify(account);
		} catch (BusinessException ex) {
			if (CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", 1);
			}
			model.addAttribute("message", ex.getResultMessages());
			return MODIFY_FORM_PATH;
		}

		if (generatedPassword.length() == 0) {
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0001)));
		} else {
//			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(AccountRuleDefinitionMessageConst.INF_ACCOUNTRULEDEFINITION_0049, account.getUsername(), generatedPassword));
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0001)));
			redirectAttr.addFlashAttribute("initialPassword", generatedPassword);
			return "redirect:/account/initialPassword";
		}

		return REDIRECT_SEARCH;
	}

	/**
	 * Delete account process
	 * 
	 * @return DELETECOMPLETE_FORM_PATH
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(AccountForm form, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		Account account = beanMapper.map(form, Account.class);
		try {
			List<Object> principals = sessionRegistry.getAllPrincipals();
			if (CollectionUtils.isEmpty(principals)) {
				SecurityContextHolder.clearContext();
			}
			//delete count
			accountService.delete(account);

			//clear sesssion
			Long deleteAccountId = account.getAccountId();
			for (Object principal : principals) {
				AccountDetails accDetail = (AccountDetails) principal;
				if (DataTypeUtils.equals(accDetail.getAccount().getAccountId(), deleteAccountId)) {
					for (SessionInformation sessionInformation : sessionRegistry.getAllSessions(principal, false)) {
						sessionInformation.expireNow();
					}
					break;
				}
			}

		} catch (BusinessException ex) {
			model.addAttribute("notExistFlg", 1);
			model.addAttribute("message", ex.getResultMessages());
			return VIEW_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0001)));
		return REDIRECT_DELETECOMPLETE;
	}
	
	@RequestMapping(value = "initialPassword", method = RequestMethod.GET)
	public String displayInitialPasswordForm(Model model) {
		return "account/initialPasswordForm";
	}
}
