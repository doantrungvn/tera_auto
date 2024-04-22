package org.terasoluna.qp.app.accountpermission;

import java.util.ArrayList;
import java.util.Collection;
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
import org.terasoluna.qp.app.accountrole.AccountRoleForm;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.AccountRolePermissionConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountPermission;
import org.terasoluna.qp.domain.model.Permission;
import org.terasoluna.qp.domain.model.Role;
import org.terasoluna.qp.domain.model.UserRoles;
import org.terasoluna.qp.domain.service.account.AccountService;
import org.terasoluna.qp.domain.service.accountpermission.AccountPermissionService;
import org.terasoluna.qp.domain.service.accountrole.AccountRoleService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.permission.PermissionService;
import org.terasoluna.qp.domain.service.role.RoleService;

@Controller
@RequestMapping(value = "accountpermission")
@TransactionTokenCheck(value = "accountpermission")
public class AccountPermissionController {
	private static final Logger logger = LoggerFactory.getLogger(AccountPermissionController.class);
	private static final String ACCOUNTPERMISSION_FORM_NAME = "accountPermissionForm";
	private static final String MODIFY_ACTION_PATH =  "accountpermission/modify";
	private static final String REDIRECT_ACCOUNT_SEARCH = "redirect:/account/search";
	private static final String MODIFY_FORM_PATH =  "accountpermission/modifyForm";

	/**
	 * Identifies methods which initialize the account permission form object
	 * @return accountpermissionform
	 */
	@ModelAttribute
	public AccountPermissionListWrapper setUpForm() {
		AccountPermissionListWrapper accountpermissionform = new AccountPermissionListWrapper();
		logger.debug("Init form {0}", accountpermissionform);
		return accountpermissionform;
	}

	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * @param webDataBinder WebDataBinder
	 */
	@InitBinder(ACCOUNTPERMISSION_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Inject
	RoleService roleService;

	@Inject
	PermissionService permissionService;

	@Inject
	AccountService accountService;

	@Inject
	ModuleService moduleService;

	@Inject
	AccountPermissionService accountPermissionService;

	@Inject
	AccountRoleService accountRoleService;

	@Inject
	Mapper beanMapper;

	/**
	 * Display information to prepare modify account ' permission
	 * @param accountForm AccountForm
	 * @param userroles UserRoles
	 * @param model Model
	 * @param status SessionStatus
	 * @param wrapperPermission AccountPermissionListWrapper
	 * @return MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "view")
	public String displayAccountPermissionView(AccountForm accountForm, @ModelAttribute UserRoles userroles, Model model, SessionStatus status, AccountPermissionListWrapper wrapperPermission, RedirectAttributes redirectAttr) {
		String destination = StringUtils.EMPTY;
		try {
			/*status.setComplete();*/

			Account accountInfor = accountPermissionService.findOneByAccountId(accountForm.getAccountId());
			if (accountInfor == null) {
				return "redirect:/account/search";
			} else {
				beanMapper.map(accountInfor, accountForm);
				model.addAttribute("accountForm", accountForm);
				// get account ' role
				List<Role> lstRole = roleService.findRoleOfAccount(accountInfor.getAccountId());
				List<Long> lstRoleId = new ArrayList<Long>();
				for (Role obj : lstRole) {
					lstRoleId.add(obj.getRoleId());
				}
				List<AccountRoleForm> lstAccRoleForm = new ArrayList<AccountRoleForm>();
				beanMapper.map(lstRole, lstAccRoleForm);
				wrapperPermission.setAccRoleList(lstAccRoleForm);
				model.addAttribute("lstAccRole", lstAccRoleForm);
				// get account ' permission
				Collection<Permission> listModul = permissionService.findAllModuleCode();
				model.addAttribute("listModule", listModul);
				model.addAttribute("listparent", listModul);
				List<Permission> lstPermission = permissionService.getAuthorityInformation(accountInfor.getAccountId());
				List<AccountPermissionForm> lstChild = new ArrayList<AccountPermissionForm>();
				List<Role> lstRolePermission = roleService.getRolePermission();

				for (Permission objModel : lstPermission) {
					AccountPermissionForm objForm = new AccountPermissionForm();
					beanMapper.map(objModel, objForm);
					// get role ' permission
					List<Role> lstRoleOfThisPermission = new ArrayList<Role>();
					for(Role role : lstRolePermission) {
						if(role.getPermissionId().equals(objForm.getPermissionId())) {
							lstRoleOfThisPermission.add(role);
						}
					}
					StringBuilder roleNames = new StringBuilder();
					boolean hiddenHaveSameRole = false;
					if (lstRoleOfThisPermission != null && lstRoleOfThisPermission.size() > 0) {
						for (int i = 0; i < lstRoleOfThisPermission.size(); i++) {
							Role roleObj = lstRoleOfThisPermission.get(i);
							if (i < lstRoleOfThisPermission.size() - 1) {
								roleNames.append(roleObj.getRoleName()).append(" ; ");
							} else {
								roleNames.append(roleObj.getRoleName());
							}
							if (lstRoleId.contains(roleObj.getRoleId()) && DbDomainConst.ROLE.STATUS_ACTIVE.equals(roleObj.getStatus())) {
								hiddenHaveSameRole = true;
							}
						}
					}
					objForm.setHiddenHaveSameRole(hiddenHaveSameRole);
					objForm.setRolesName(roleNames.toString());
					lstChild.add(objForm);
				}
				wrapperPermission.setAccPermissionList(lstChild);
				wrapperPermission.setAccountId(accountInfor.getAccountId());
			}
			destination = MODIFY_FORM_PATH;
		}catch (BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			destination = REDIRECT_ACCOUNT_SEARCH;
		}
		return destination;
	}

	/**
	 * Modify account ' permission process
	 * @param arraySelected AccountPermissionListWrapper
	 * @param model Model
	 * @param result BindingResult
	 * @param redirectAttr RedirectAttributes
	 * @return REDIRECT_ACCOUNT_SEARCH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(AccountForm accountForm, AccountPermissionListWrapper arraySelected, Model model, BindingResult result, RedirectAttributes redirectAttr) {
		if (result.hasErrors()) {
			ValidationUtils.setBindingResult(result, model);
			return MODIFY_ACTION_PATH;
		}
		Long accountId = arraySelected.getAccountId();
		List<AccountPermissionForm> lstPermission = arraySelected.getAccPermissionList();
		List<AccountPermission> lstAccountPermission = new ArrayList<AccountPermission>();
		for (AccountPermissionForm objPermission : lstPermission) {
			if (objPermission.getPermissionId() != null && objPermission.isSelected()) {
				AccountPermission objAcc = new AccountPermission();
				objAcc.setAccountId(accountId);
				objAcc.setPermissionId(objPermission.getPermissionId());
				lstAccountPermission.add(objAcc);
			}
		}
		try {
			Account account = beanMapper.map(accountForm, Account.class);				
			accountPermissionService.modify(accountId, lstAccountPermission,account);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			model.addAttribute("notExistFlg", "0");
			// if not exist or fixed design status
			String errMessageCode = StringUtils.defaultString(be.getResultMessages().getList().get(0).getCode(), CommonMessageConst.ERR_SYS_0037);
			if (CommonMessageConst.ERR_SYS_0037.equals(errMessageCode)) {
				model.addAttribute("notExistFlg", "1");
			} else {
				model.addAttribute("accountForm", accountForm);
				// get account ' role
				List<Role> lstRole = roleService.findRoleOfAccount(accountId);
				List<Long> lstRoleId = new ArrayList<Long>();
				for (Role obj : lstRole) {
					lstRoleId.add(obj.getRoleId());
				}
				List<AccountRoleForm> lstAccRoleForm = new ArrayList<AccountRoleForm>();
				beanMapper.map(lstRole, lstAccRoleForm);
				arraySelected.setAccRoleList(lstAccRoleForm);
				model.addAttribute("lstAccRole", lstAccRoleForm);
				// get account ' permission
				Collection<Permission> listModul = permissionService.findAllModuleCode();
				model.addAttribute("listModule", listModul);
				model.addAttribute("listparent", listModul);
				List<Permission> lstPermissionDB = permissionService.getAuthorityInformation(accountId);
				List<AccountPermissionForm> lstChild = new ArrayList<AccountPermissionForm>();
				List<Role> lstRolePermission = roleService.getRolePermission();

				for (Permission objModel : lstPermissionDB) {
					AccountPermissionForm objForm = new AccountPermissionForm();
					beanMapper.map(objModel, objForm);
					// get role ' permission
					List<Role> lstRoleOfThisPermission = new ArrayList<Role>();
					for(Role role : lstRolePermission) {
						if(role.getPermissionId().equals(objForm.getPermissionId())) {
							lstRoleOfThisPermission.add(role);
						}
					}
					StringBuilder roleNames = new StringBuilder();
					boolean hiddenHaveSameRole = false;
					if (lstRoleOfThisPermission != null && lstRoleOfThisPermission.size() > 0) {
						for (int i = 0; i < lstRoleOfThisPermission.size(); i++) {
							Role roleObj = lstRoleOfThisPermission.get(i);
							if (i < lstRoleOfThisPermission.size() - 1) {
								roleNames.append(roleObj.getRoleName()).append(" ; ");
							} else {
								roleNames.append(roleObj.getRoleName());
							}
							if (lstRoleId.contains(roleObj.getRoleId()) && DbDomainConst.ROLE.STATUS_ACTIVE.equals(roleObj.getStatus())) {
								hiddenHaveSameRole = true;
							}
						}
					}
					objForm.setHiddenHaveSameRole(hiddenHaveSameRole);
					objForm.setRolesName(roleNames.toString());
					lstChild.add(objForm);
				}
				arraySelected.setAccPermissionList(lstChild);
				arraySelected.setAccountId(accountId);
			}
			return MODIFY_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(AccountRolePermissionConst.SC_ACCOUNTROLEPERMISSION_0024)));
		return REDIRECT_ACCOUNT_SEARCH;
	}

}
