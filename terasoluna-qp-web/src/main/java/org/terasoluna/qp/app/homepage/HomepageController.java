package org.terasoluna.qp.app.homepage;

import java.security.Principal;
/*import java.text.DateFormat;
import java.util.Date;*/
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.DateUtils;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.app.common.ultils.HttpServletRequestUtils;
import org.terasoluna.qp.app.common.ultils.LocaleUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ProjectMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.service.account.AccountDetails;
import org.terasoluna.qp.domain.service.accountprofile.AccountProfileService;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;
import org.terasoluna.qp.domain.service.project.ProjectService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomepageController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomepageController.class);

	@Inject
	ProjectService projectService;
	
	@Inject
	AccountProfileService accountProfileService;
	
	@Inject 
	SystemService systemService;
	
	@Inject
	LanguageDesignService languageDesignService;
	
	@ModelAttribute
	public HomepageForm setUpHomepageForm() {
		HomepageForm form = new HomepageForm();
		logger.debug("Init form {}", form);
		return form;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/getUserDetailInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String getUserDetailInfo(Locale locale, Model model,
			Principal principal) {
		// get login user information
		Authentication authentication = (Authentication) principal;
		if (authentication != null) {
			// get UserDetails
			AccountDetails accDetails = (AccountDetails) authentication.getPrincipal();
			// get account object
			Account account = accDetails.getAccount();

			SessionUtils.set(SessionUtils.ACCOUNT_INFOR, account);

			String[] defaultLanguage = null;
			if (SessionUtils.get(SessionUtils.ACCOUNT_PROFILE) != null) {
				AccountProfile accountProfile = (AccountProfile) SessionUtils.get(SessionUtils.ACCOUNT_PROFILE);
				if (!StringUtils.isEmpty(accountProfile.getDefaultLanguage())) {
					defaultLanguage = accountProfile.getDefaultLanguage().split("_");
				}
			}
			if (defaultLanguage != null) {
				Locale userLocale = LocaleUtils.toLocale(defaultLanguage[0], defaultLanguage[1], "");
				WebUtils.setSessionAttribute(
						HttpServletRequestUtils.getRequest(),
						LocaleUtils.LOCALE_SESSION_ATTRIBUTE_NAME, userLocale);
			}

			//process get language id
			LanguageDesign languageDesign = languageDesignService.getLanguageDefaultForDesign();
			SessionUtils.set(SessionUtils.CURRENT_LANGUAGE_DESIGN, languageDesign);
			
			// KhanhTH: Check list permission, if only account profile, redirect change password page
			boolean changePassword = true;
//			List<GrantedAuthority> listPermission = account.getAuthorities();
			if (account.getAuthorities().size() == 0) {
				changePassword = false;
			}
			
			for (GrantedAuthority permission : account.getAuthorities()) {
				if (!permission.getAuthority().contains("accountprofile")) {
					changePassword = false;
					break;
				}
			}

			if (changePassword) {
				return "redirect:/accountprofile/modifyPasswordRedirectFromLogin";
			} else {
				return "redirect:/home";
			}
		} else {
			return "redirect:/login";
		}
	}

	/**
	 * return home page.
	 */
	@RequestMapping(value = "/home", method = { RequestMethod.GET})
	public String homePage(Model model, Principal principal) {

		AccountProfile ac = (AccountProfile) SessionUtils.get(SessionUtils.ACCOUNT_PROFILE);
		model.addAttribute("serverTime", DateUtils.formatDateTime(FunctionCommon.getCurrentTime(),DateUtils.getPatternDateTime(ac.getDateTimeFormat())));

		model.addAttribute("account", (Account) SessionUtils.get(SessionUtils.ACCOUNT_INFOR));

		model.addAttribute("msgWelcome", MessageUtils.getMessage("sc.homepage.0001", MessageUtils.getMessage("tqp.tqp")));
		
		List<Project> listProjectOfUser = systemService.getAllProjectByAccount(SessionUtils.getAccountId());

		model.addAttribute("listProjectOfUser", listProjectOfUser);

		//DungNN modify
		AccountProfile accountProfile = (AccountProfile) SessionUtils.get(SessionUtils.ACCOUNT_PROFILE);
		Long projectId = null;
		if ( accountProfile != null) {
			projectId = accountProfile.getCurrentProjectId();
		}
		
		Project projectDefault = null;
		if (listProjectOfUser != null && !listProjectOfUser.isEmpty()) {
			//if user not config default project, auto set first project
			if (projectId == null) {
				projectDefault = listProjectOfUser.get(0);
			} else {
				boolean notExist = true;
				for (Project temp : listProjectOfUser) {
					if (projectId.equals(temp.getProjectId())) {
						projectDefault = temp;
						notExist = false;
						break;
					}
				}
				if (notExist) {
					projectDefault = listProjectOfUser.get(0);
				}
			}
		}

		if(projectDefault != null){
			if (accountProfile != null) {
				accountProfile.setCurrentProjectId(projectDefault.getProjectId());
				SessionUtils.set(SessionUtils.ACCOUNT_PROFILE, accountProfile);
			}

			if (DataTypeUtils.equals(DbDomainConst.DatabaseType.ORACLE, projectDefault.getDbType())) {
				projectDefault.setCodeMaxVal(systemService.getDefaultProfile().getMaxLengthOracleDBMS());
			} else if (DataTypeUtils.equals(DbDomainConst.DatabaseType.PostgreSQL, projectDefault.getDbType())) {
				projectDefault.setCodeMaxVal(systemService.getDefaultProfile().getMaxLengthPostgreDBMS());
			}
			LanguageDesign obj = (LanguageDesign) SessionUtils.get(SessionUtils.CURRENT_LANGUAGE_DESIGN);
			
			if (obj == null || DataTypeUtils.notEquals(projectDefault.getDefaultLanguageId(), obj.getLanguageId())) {
				LanguageDesign languageDesign = languageDesignService.getLanguageDesignById(projectDefault.getDefaultLanguageId(), projectDefault.getProjectId());
				SessionUtils.set(SessionUtils.CURRENT_LANGUAGE_DESIGN, languageDesign);
			}
			SessionUtils.set(SessionUtils.CURRENT_PROJECT, projectDefault);
		} else {
			SessionUtils.remove(SessionUtils.CURRENT_PROJECT);
			SessionUtils.remove(SessionUtils.CURRENT_LANGUAGE_DESIGN);
		}
		return "homepage/home";
	}
	
	@RequestMapping(value = "/home", method = {RequestMethod.POST })
	public String homePage(@Validated HomepageForm form, BindingResult result, Locale locale, Model model, Principal principal) {

		try {
			
			AccountProfile ac = (AccountProfile) SessionUtils.get(SessionUtils.ACCOUNT_PROFILE);
			model.addAttribute("serverTime", DateUtils.formatDateTime(FunctionCommon.getCurrentTime(),DateUtils.getPatternDateTime(ac.getDateTimeFormat())));
			
			model.addAttribute("account", (Account) SessionUtils.get(SessionUtils.ACCOUNT_INFOR));
			model.addAttribute("msgWelcome", MessageUtils.getMessage("sc.homepage.0001", MessageUtils.getMessage("tqp.tqp")));
			
			List<Project> listProjectOfUser = systemService.getAllProjectByAccount(SessionUtils.getAccountId());
			model.addAttribute("listProjectOfUser", listProjectOfUser);

			if (form.getProjectId() == null ) {
				model.addAttribute("message", MessageUtils.getMessage("err.homepage.0001"));
			} else {
				Project project = null;
				if ( CollectionUtils.isNotEmpty(listProjectOfUser)) {
					for (Project temp : listProjectOfUser) {
						if (form.getProjectId().equals(temp.getProjectId())) {
							project = temp;
							break;
						}
					}
				}

				if (project == null ) {
					model.addAttribute("message", MessageUtils.getMessage(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0010))); 
				} else if (SessionUtils.get(SessionUtils.ACCOUNT_PROFILE) != null) {

					AccountProfile accountProfile = (AccountProfile) SessionUtils.get(SessionUtils.ACCOUNT_PROFILE);

					if (DataTypeUtils.notEquals(form.getProjectId(), accountProfile.getCurrentProjectId())) {
						accountProfile.setCurrentProjectId(form.getProjectId());
						accountProfileService.updatecurrentproject(accountProfile, project.getProjectName());
					}
					
					if (DataTypeUtils.notEquals(project.getDefaultLanguageId(), SessionUtils.getCurrentLanguageId())) {
						LanguageDesign languageDesign = languageDesignService.getLanguageDesignById(project.getDefaultLanguageId(), project.getProjectId());
						SessionUtils.set(SessionUtils.CURRENT_LANGUAGE_DESIGN, languageDesign);
					}
					SessionUtils.set(SessionUtils.CURRENT_PROJECT, project);
				} else {
					SessionUtils.remove(SessionUtils.CURRENT_PROJECT);
					SessionUtils.remove(SessionUtils.CURRENT_LANGUAGE_DESIGN);
				}
			}
		} catch(BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
		}
		return "homepage/home";

	}

	@RequestMapping(value = "/")
	public String login(Principal principal) {
		// get login user information
		Authentication authentication = (Authentication) principal;
		if (authentication != null) {
			try {
				// get UserDetails
				AccountDetails accDetails = (AccountDetails) authentication.getPrincipal();
				// get account object
				if (accDetails != null && accDetails.getAccount() != null) {
					return "redirect:/home";
				}
			} catch (Exception ex) {
				return "redirect:/login";
			}
		}
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/session-expired")
	public String sessionExpired(Principal principal) {
		return "common/error/sessionExpiredError";
	}
}
