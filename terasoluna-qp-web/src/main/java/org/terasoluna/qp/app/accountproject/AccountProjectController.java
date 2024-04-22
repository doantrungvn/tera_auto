package org.terasoluna.qp.app.accountproject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.account.AccountForm;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.AccountProjectConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.AccountProject;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.service.accountproject.AccountProjectService;
import org.terasoluna.qp.domain.service.accountrole.AccountRoleService;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Controller
@RequestMapping(value = "accountproject")
public class AccountProjectController {

	@Inject
	Mapper beanMapper;

	@Inject
	AccountRoleService accountRoleService;

	@Inject
	AccountProjectService accountProjectService;

	@Inject
	ProjectService projectService;

	@Inject
	LanguageDesignService languageDesignService;

	private static final String REDIRECT_ACCOUNT_SEARCH = "redirect:/account/search";
	private static final String MODIFY_FORM_PATH = "accountproject/modifyForm";
	private static final String MODIFY_ACCOUNT_PATH = "accountproject/modify";

	/**
	 * Initialize modify account ' project screen
	 * 
	 * @param accountForm
	 *            AccountForm
	 * @param model
	 *            Model
	 * @return MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "view")
	public String displayModify(AccountForm accountForm, Model model, AccountProjectListWrapper accountProjectListWrapper, RedirectAttributes redirectAttr) {
		String destination = StringUtils.EMPTY;
		try {
			Account accountInfor = accountRoleService.findOneByAccountId(accountForm.getAccountId());

			if (accountInfor == null) {
				return REDIRECT_ACCOUNT_SEARCH;
			} else {
				beanMapper.map(accountInfor, accountForm);
				model.addAttribute("accountForm", accountForm);
			}
			List<Project> listProject = projectService.getAllProjectAssignToAccount(accountForm.getAccountId());
			List<AccountProjectForm> lstAccProjectForm = new ArrayList<AccountProjectForm>();

			for (Project objProject : listProject) {
				AccountProjectForm accProjectForm = new AccountProjectForm();
				accProjectForm.setAccountId(accountForm.getAccountId());
				accProjectForm.setProjectId(objProject.getProjectId());
				accProjectForm.setProjectName(objProject.getProjectName());
				accProjectForm.setProjectCode(objProject.getProjectCode());
				accProjectForm.setStatus(objProject.getStatus());
				if (objProject.getSelected() != null) {
					accProjectForm.setSelected(true);
					accProjectForm.setOwerProject(true);
				} else {
					accProjectForm.setSelected(false);
					accProjectForm.setOwerProject(false);
				}

				lstAccProjectForm.add(accProjectForm);
			}
			accountProjectListWrapper.setAccProjectList(lstAccProjectForm);
			model.addAttribute("lstProject", lstAccProjectForm);
			model.addAttribute("notExistFlg", 0);
			destination = MODIFY_FORM_PATH;
		} catch (BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			destination = REDIRECT_ACCOUNT_SEARCH;
		}

		return destination;
	}

	/**
	 * Initialize modify account ' project screen
	 * 
	 * @param accountForm
	 *            AccountForm
	 * @param model
	 *            Model
	 * @return MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(AccountForm accountForm, AccountProjectListWrapper arraySelected, Model model, BindingResult result, RedirectAttributes redirectAttr) {

		if (result.hasErrors()) {
			ValidationUtils.setBindingResult(result, model);
			return MODIFY_ACCOUNT_PATH;
		}

		List<AccountProjectForm> lstProject = arraySelected.getAccProjectList();
		Long accountId = arraySelected.getAccountId();
		List<AccountProject> lstAccountProject = new ArrayList<AccountProject>();
		
		for (AccountProjectForm objProject : lstProject) {
			if (objProject.getAccountId() != null && objProject.isSelected()) {
				AccountProject objAcc = new AccountProject();
				objAcc.setAccountId(accountId);
				objAcc.setProjectId(objProject.getProjectId());
				lstAccountProject.add(objAcc);
			}
		}

		try {
			
			Long currentProjectId = SessionUtils.getCurrentProjectId();
			Account account = new Account();
			account = beanMapper.map(accountForm, Account.class);
			account.setAccountId(accountId);
			account.setCreatedBy(SessionUtils.getAccountId());
			account.setWorkingProjectId(currentProjectId);

			Long projectId = accountProjectService.registerAccountProject(account, lstAccountProject);
			
			if (projectId == null) {
				SessionUtils.set(SessionUtils.CURRENT_PROJECT, null);
				SessionUtils.set(SessionUtils.CURRENT_LANGUAGE_DESIGN, null);
			} else if (!DataTypeUtils.equals(currentProjectId, projectId)) {
				try {
					//account = beanMapper.map(accountForm, Account.class);
					Project project = projectService.getProjectInformation(projectId, accountId);
					SessionUtils.set(SessionUtils.CURRENT_PROJECT, project);

					LanguageDesign languageDesign = languageDesignService.getLanguageDesignById(project.getDefaultLanguageId(), projectId);
					SessionUtils.set(SessionUtils.CURRENT_LANGUAGE_DESIGN, languageDesign);

					AccountProfile accountProfile = (AccountProfile) SessionUtils.get(SessionUtils.ACCOUNT_PROFILE);
					if (accountProfile != null) {
						accountProfile.setCurrentProjectId(projectId);
						SessionUtils.set(SessionUtils.ACCOUNT_PROFILE, accountProfile);
					}
				} catch (Exception e) {
					SessionUtils.set(SessionUtils.CURRENT_PROJECT, null);
					SessionUtils.set(SessionUtils.CURRENT_LANGUAGE_DESIGN, null);
				}
			}
			
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			model.addAttribute("notExistFlg", "0");
			// if not exist or fixed design status
			String errMessageCode = StringUtils.defaultString(be.getResultMessages().getList().get(0).getCode(), CommonMessageConst.ERR_SYS_0037);
			if (CommonMessageConst.ERR_SYS_0037.equals(errMessageCode)) {
				model.addAttribute("notExistFlg", "1");
			} else {
				List<Project> listProject = projectService.getAllProjectAssignToAccount(accountForm.getAccountId());
				List<AccountProjectForm> lstAccProjectForm = new ArrayList<AccountProjectForm>();
				for (Project objProject : listProject) {
					AccountProjectForm accProjectForm = new AccountProjectForm();
					accProjectForm.setAccountId(accountForm.getAccountId());
					accProjectForm.setProjectId(objProject.getProjectId());
					accProjectForm.setProjectName(objProject.getProjectName());
					accProjectForm.setProjectCode(objProject.getProjectCode());
					accProjectForm.setStatus(objProject.getStatus());
					if (objProject.getSelected() != null) {
						accProjectForm.setSelected(true);
						accProjectForm.setOwerProject(true);
					} else {
						accProjectForm.setSelected(false);
						accProjectForm.setOwerProject(false);
					}

					lstAccProjectForm.add(accProjectForm);
				}
				arraySelected.setAccProjectList(lstAccProjectForm);
				model.addAttribute("lstProject", lstAccProjectForm);
				model.addAttribute("notExistFlg", 0);
				model.addAttribute("accountForm", accountForm);
			}
			return MODIFY_FORM_PATH;
		}

		AccountForm form = new AccountForm();
		form.setAccountId(arraySelected.getAccountId());
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0006, MessageUtils.getMessage(AccountProjectConst.SC_ACCOUNTPROJECT_0001)));
		return REDIRECT_ACCOUNT_SEARCH;
	}

}
