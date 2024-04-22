package org.terasoluna.qp.app.accountrole;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.account.AccountForm;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.AccountRolePermissionConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountRole;
import org.terasoluna.qp.domain.model.Role;
import org.terasoluna.qp.domain.model.UserRoles;
import org.terasoluna.qp.domain.repository.accountrole.AccountRoleRepository;
import org.terasoluna.qp.domain.service.account.AccountService;
import org.terasoluna.qp.domain.service.accountrole.AccountRoleService;
import org.terasoluna.qp.domain.service.role.RoleService;

@Controller
@RequestMapping(value = "accountrole")
public class AccountRoleController {
	private static final Logger logger = LoggerFactory.getLogger(AccountRoleController.class);
	private static final String ACCOUNT_ROLE_FORM_NAME = "accountRoleForm";
	private static final String MODIFY_ACTION_PATH = "accountrole/modify";
	private static final String MODIFY_FORM_PATH = "accountrole/modifyForm";
	private static final String REDIRECT_ACCOUNT_SEARCH = "redirect:/account/search";

	@Inject
	Mapper beanMapper;

	@Inject
	AccountService accountService;

	@Inject
	RoleService roleService;

	@Inject
	AccountRoleService accountRoleService;

	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * 
	 * @param webDataBinder
	 *            WebDataBinder
	 */
	@InitBinder(ACCOUNT_ROLE_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/**
	 * Identifies methods which initialize the project form object
	 * 
	 * @return projectForm
	 */
	@ModelAttribute
	public AccountRoleForm setUpAccountRoleForm() {
		AccountRoleForm accountRoleForm = new AccountRoleForm();
		logger.debug("Init form {0}", accountRoleForm);

		return accountRoleForm;
	}

	@Inject
	AccountRoleRepository accountRoleRepository;

	/**
	 * Initialize modify role ' account screen
	 * 
	 * @param accountForm
	 *            AccountForm
	 * @param userroles
	 *            UserRoles
	 * @param model
	 *            Model
	 * @param status
	 *            SessionStatus
	 * @param wrapperRole
	 *            AccountRoleListWrapper
	 * @return MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "view")
	public String displayModify(AccountForm accountForm, @ModelAttribute UserRoles userroles, Model model, SessionStatus status, AccountRoleListWrapper wrapperRole, RedirectAttributes redirectAttr) {
		String destination = StringUtils.EMPTY;
		status.setComplete();
		try {
			Account accountInfor = accountRoleService.findOneByAccountId(accountForm.getAccountId());
			if (accountInfor == null) {
				return REDIRECT_ACCOUNT_SEARCH;
			} else {
				beanMapper.map(accountInfor, accountForm);
				model.addAttribute("accountForm", accountForm);
				
				// get account ' role
				List<Role> lstRole = accountRoleService.getRoleByAccount(accountInfor.getAccountId());
				List<AccountRoleForm> lstAccRoleForm = new ArrayList<AccountRoleForm>();
				beanMapper.map(lstRole, lstAccRoleForm);
				wrapperRole.setAccRoleList(lstAccRoleForm);
				wrapperRole.setAccountId(accountInfor.getAccountId());
			}
			destination = MODIFY_FORM_PATH;
		} catch (BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			destination = REDIRECT_ACCOUNT_SEARCH;
		}
		return destination;
	}

	/**
	 * Modify account ' role process
	 * 
	 * @param accRoleWrapper
	 *            AccountRoleListWrapper
	 * @param result
	 *            BindingResult
	 * @param model
	 *            Model
	 * @param redirectAttr
	 *            RedirectAttributes
	 * @return REDIRECT_ACCOUNT_SEARCH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModifyAccountRole(AccountForm accountForm, AccountRoleListWrapper accRoleWrapper, BindingResult result, Model model, RedirectAttributes redirectAttr) {
		if (result.hasErrors()) {
			ValidationUtils.setBindingResult(result, model);
			return MODIFY_ACTION_PATH;
		}
		Long accountId = accRoleWrapper.getAccountId();
		List<AccountRoleForm> lstRole = accRoleWrapper.getAccRoleList();
		List<AccountRole> lstAccountRole = new ArrayList<AccountRole>();
		for (AccountRoleForm obj : lstRole) {
			if (obj.getRoleId() != null && obj.isSelected()) {
				AccountRole accRole = new AccountRole();
				accRole.setAccountId(accountId);
				accRole.setRoleId(obj.getRoleId());
				accRole.setUpdatedDate(obj.getUpdatedDate());
				lstAccountRole.add(accRole);
			}
		}
		try {
			Account account = beanMapper.map(accountForm, Account.class);
			accountRoleService.modifyAccountRole(accountId, lstAccountRole, account);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			model.addAttribute("notExistFlg", "0");
			// if not exist or fixed design status
			String errMessageCode = StringUtils.defaultString(be.getResultMessages().getList().get(0).getCode(), CommonMessageConst.ERR_SYS_0037);
			if (CommonMessageConst.ERR_SYS_0037.equals(errMessageCode)) {
				model.addAttribute("notExistFlg", "1");
			} else {
				List<Role> lstRoleDB = accountRoleService.getRoleByAccount(accountId);
				List<AccountRoleForm> lstAccRoleForm = new ArrayList<AccountRoleForm>();
				beanMapper.map(lstRoleDB, lstAccRoleForm);
				accRoleWrapper.setAccRoleList(lstAccRoleForm);
				accRoleWrapper.setAccountId(accountId);
				model.addAttribute("accountForm", accountForm);
			}

			return MODIFY_FORM_PATH;
		}

		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(AccountRolePermissionConst.SC_ACCOUNTROLEPERMISSION_0019)));
		return REDIRECT_ACCOUNT_SEARCH;
	}

}
