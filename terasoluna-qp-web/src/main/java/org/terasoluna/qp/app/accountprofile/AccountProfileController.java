package org.terasoluna.qp.app.accountprofile;

import java.net.Proxy;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.accounttheme.AccountThemeForm;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.message.AccountMessageConst;
import org.terasoluna.qp.app.message.AccountProfileMessageConst;
import org.terasoluna.qp.app.message.AccountRuleDefinitionMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.translator.microsoft.ProxyCommon;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.ChangePassword;
import org.terasoluna.qp.domain.service.account.AccountService;
import org.terasoluna.qp.domain.service.accountprofile.AccountProfileService;
import org.terasoluna.qp.domain.service.accounttheme.AccountThemeService;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.language.LanguageService;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;

@Controller
@RequestMapping("accountprofile")
@TransactionTokenCheck(value = "accountprofile")
public class AccountProfileController {
	private static final Logger logger = LoggerFactory.getLogger(AccountProfileController.class);
	private static final String VIEW_PROFILE_FORM_PATH ="accountprofile/profileForm";
	private static final String VIEW_PROFILE_REDIRECT_PATH = "redirect:/accountprofile/modifyUserSetting";
	private static final String MODIFY_PASSWORD_FORM_PATH ="accountprofile/passwordForm";
	private static final String MODIFY_SETTING_FORM_PATH ="accountprofile/settingForm";
	private static final String MODIFY_THEME_FORM_PATH ="accountprofile/themeForm";
	private static final String LOGIN_REDIRECT_PATH = "redirect:/login";
	private static final String MODIFY_PASSWORD_FORM_REDIRECT_FROM_LOGIN_PATH ="accountprofile/modifyPasswordForm";
	//private static final String REDIRECT_MODIFY_PASSWORD_FORM_REDIRECT_FROM_LOGIN_PATH ="redirect:/accountprofile/modifyPasswordRedirectFromLogin";
	private static final String REDIRECT_MODIFY_PASSWORD_FORM_REDIRECT_HOME_PATH ="redirect:/home";
	
	@Inject
	AccountService accountService;
	
	@Inject
	AccountProfileService accountProfileService;
	
	@Inject
	SystemService systemService;
	
	@Inject
	AccountThemeService accountThemeService;
	
	@Inject
	LanguageService languageService;
	
	@Inject
	Mapper beanMapper;
	
	@Inject
	LanguageDesignService languageDesignService;
	
	@Resource
	PasswordEncoder passwordEncoder;
	
	@Inject
	@Named("CL_THEME_COLOR")
	private CodeList clstTheme;
	
	@Inject
	PassEqualsValidator passwordEqualsValidator;
	
	@Inject
	AccountSettingValidator accountSettingValidator;
	
	@Inject
	PasswordRuleValidatorProfile passwordRuleValidatorProfile;
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used
	 * for populating command and form object
	 * 
	 * @param webDataBinder
	 *            WebDataBinder
	 */
	@InitBinder("passwordForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(passwordEqualsValidator);
		webDataBinder.addValidators(passwordRuleValidatorProfile);
	}
	
	@InitBinder("settingForm")
	public void initBinderSettingForm(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(accountSettingValidator);
	}
	
	/**
	 * Identifies methods which initialize the password form object
	 * @return projectForm
	 */
	@ModelAttribute
	public PasswordForm setUpPasswordForm() {
		PasswordForm passwordForm = new PasswordForm();
		logger.debug("Init form {0}", passwordForm);
		return passwordForm;
	}
	
	/**
	 * Identifies methods which initialize the setting form object
	 * @return projectForm
	 */
	@ModelAttribute
	public SettingForm setUpProjectForm() {
		SettingForm settingForm = new SettingForm();
		logger.debug("Init form {0}", settingForm);
		return settingForm;
	}
	

	/**
	 *  Identifies methods which initialize the theme form object
	 * 
	 * @return
	 */
	@ModelAttribute
	public AccountThemeForm setUpThemeForm() {
		AccountThemeForm themeForm = new AccountThemeForm();
		logger.debug("Init form {0}", themeForm);
		return themeForm;
	}
	/**
	 * Initialize view account profile screen
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "modifyUserSetting", method = { RequestMethod.GET })
	@TransactionTokenCheck(value = "modifyUserSetting", type = TransactionTokenType.BEGIN)
	public String displaymodifyUserSetting(SettingForm settingForm, Model model) {
		AccountProfile accountProfile = new AccountProfile();
		
		try {
			accountProfile = accountProfileService.getAccountProfile(SessionUtils.getAccountId());
		} catch(BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return MODIFY_PASSWORD_FORM_PATH;
		}
		beanMapper.map(accountProfile, settingForm);
		
		if (StringUtils.isNotEmpty(settingForm.getProxyHost())) {
			settingForm.setProxyLevel(DbDomainConst.ProxySetting.MANUAL_PROXY);
		}
		return VIEW_PROFILE_FORM_PATH;
	}

	/**
	 * Modify system setting process
	 * @param settingForm SettingForm
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return MODIFY_FORM_PATH
	 */
	@RequestMapping(value = "modifyUserSetting", method = { RequestMethod.POST })
	@TransactionTokenCheck(value = "modifyUserSetting", type = TransactionTokenType.IN)
	public String processModifyUserSetting(@Validated SettingForm form, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		if(result.hasErrors())
		{
			ValidationUtils.setBindingResult(result,model);
			return VIEW_PROFILE_FORM_PATH;
		}

		AccountProfile accountProfile = beanMapper.map(form, AccountProfile.class);
		try {
			// Check account exits
			accountService.findOneByAccountId(SessionUtils.getAccountId());
			
			// Set account profile information
			accountProfile.setCurrentProjectId(SessionUtils.getCurrentProjectId());
			accountProfile.setUpdatedBy(SessionUtils.getAccountId());
			
			accountProfileService.update(accountProfile);
			SessionUtils.set(SessionUtils.ACCOUNT_PROFILE, accountProfile);
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(CommonMessageConst.SC_TQP_0009)));
			return VIEW_PROFILE_REDIRECT_PATH;
		} catch(BusinessException ex) {
			// Check in case account does not exist
			if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				SecurityContextHolder.clearContext();
				return LOGIN_REDIRECT_PATH;
			}
			model.addAttribute("message", ex.getResultMessages());
			return VIEW_PROFILE_REDIRECT_PATH;
		}
	}
	
	@RequestMapping(value = "userTestConnection", method = { RequestMethod.POST })
	@TransactionTokenCheck(value = "modifyUserSetting", type = TransactionTokenType.IN)
	public String processUserTestConnection(@Validated SettingForm form, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		if (result.hasErrors()) {
			ValidationUtils.setBindingResult(result, model);
				return VIEW_PROFILE_FORM_PATH;
		}

		/*if (DbDomainConst.ProxySetting.NONE_PROXY.equals(form.getProxyLevel())
				|| DbDomainConst.ProxySetting.SYSTEM_PROXY.equals(form.getProxyLevel())) {
			form.setProxyHost(null);
			form.setProxyPort(null);
			form.setProxyUser(null);
			form.setProxyPass(null);
		}*/
		
		AccountProfile accountProfile = SessionUtils.getCurrentAccountProfile();
		accountProfile.setBingClientId(form.getBingClientId());
		accountProfile.setBingClientSecret(form.getBingClientSecret());

		//AccountProfile accountProfile = beanMapper.map(form, AccountProfile.class);

		try {
			languageService.testConnection(accountProfile);
			model.addAttribute("message", ResultMessages.info().add(AccountProfileMessageConst.INF_ACCOUNTPROFILE_0001));
			return VIEW_PROFILE_FORM_PATH;
		} catch (Exception ex) {
			model.addAttribute("message", ex.getMessage());
			return VIEW_PROFILE_FORM_PATH;
		}
	}
	
	/**
	 * Initialize modify password screen
	 * @param projectForm ProjectForm
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return MODIFY_PASSWORD_FORM_PATH
	 */
	@RequestMapping(value = "modifyPassword", method = { RequestMethod.GET })
	@TransactionTokenCheck(value = "modifyPassword", type = TransactionTokenType.BEGIN)
	public String displayModifyPassword(PasswordForm form) {
		ChangePassword changePassword = accountProfileService.getInformationForPasswordForm(SessionUtils.getAccountId());
		
		// Check if account is not exist
		if(changePassword == null){
			SecurityContextHolder.clearContext();
			return LOGIN_REDIRECT_PATH;
		}
		beanMapper.map(changePassword, form);
		return MODIFY_PASSWORD_FORM_PATH;
	}
	
	/**
	 * Initialize modify password screen redirect from login screen
	 * @param projectForm ProjectForm
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return MODIFY_PASSWORD_FORM_PATH
	 */
	@RequestMapping(value = "modifyPasswordRedirectFromLogin", method = { RequestMethod.GET })
	@TransactionTokenCheck(value = "modifyPassword", type = TransactionTokenType.BEGIN)
	public String displayModifyPasswordRedirectFromLogin(PasswordForm form) {
		ChangePassword changePassword = accountProfileService.getInformationForPasswordForm(SessionUtils.getAccountId());
		
		// Check if account is not exist
		if(changePassword == null){
			SecurityContextHolder.clearContext();
			return LOGIN_REDIRECT_PATH;
		}
		changePassword.setRedirectFromLogin(true);
		beanMapper.map(changePassword, form);
		
		return MODIFY_PASSWORD_FORM_REDIRECT_FROM_LOGIN_PATH;
	}

	/**
	 * Modify password process
	 * @param passwordform PasswordForm
	 * @param redirectAttr RedirectAttributes
	 * @param result BindingResult
	 * @param model Model
	 * @return MODIFY_PASSWORD_REDIRECT_PATH
	 */
	@RequestMapping(value = "modifyPassword", method = { RequestMethod.POST })
	@TransactionTokenCheck(value = "modifyPassword", type = TransactionTokenType.IN)
	public String processModifyPassword(@Validated PasswordForm passwordform,BindingResult result, RedirectAttributes redirectAttr,Model model) {
		String destinationUrl = "";
		if (passwordform.isRedirectFromLogin()) {
			destinationUrl = MODIFY_PASSWORD_FORM_REDIRECT_FROM_LOGIN_PATH;
		}
		else{
			destinationUrl = MODIFY_PASSWORD_FORM_PATH;
		}
		
		if(result.hasErrors())
		{
			//ValidationUtils.setBindingResult(result,model);
			return destinationUrl;
		}
		try{
			ChangePassword changePassword = beanMapper.map(passwordform, ChangePassword.class);
			// Get current account from database
			Account currentAccount = accountService.findOneByAccountId(SessionUtils.getAccountId());
			accountService.changePassword(changePassword, currentAccount);
			
			if (changePassword.isRedirectFromLogin()) {
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(AccountRuleDefinitionMessageConst.INF_ACCOUNTRULEDEFINITION_0051));
				destinationUrl = REDIRECT_MODIFY_PASSWORD_FORM_REDIRECT_HOME_PATH;
			} else {
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, 
						MessageUtils.getMessage(AccountMessageConst.SC_ACCOUNT_0003)));
				destinationUrl = VIEW_PROFILE_REDIRECT_PATH;
			}
			return destinationUrl;
		}catch(BusinessException ex)
		{
			// Check in case account does not exist
			if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				SecurityContextHolder.clearContext();
				return LOGIN_REDIRECT_PATH;
			}
			model.addAttribute("message", ex.getResultMessages());
			return destinationUrl;
		}
		
	}

	/**
	 * Initialize modify system setting screen
	 * @param settingForm SettingForm
	 * @param model Model
	 * @return MODIFY_SETTING_FORM_PATH
	 */
	@RequestMapping(value = "modifySystemSetting", method = { RequestMethod.GET })
	@TransactionTokenCheck(value = "modifySystemSetting", type = TransactionTokenType.BEGIN)
	public String displayModifySystemSetting(SettingForm settingForm,Model model) {
		AccountProfile accountProfile = null;
		try {
			accountProfile = systemService.getDefaultProfile();
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return MODIFY_PASSWORD_FORM_PATH;
		}
		beanMapper.map(accountProfile, settingForm);

		if (StringUtils.isNotBlank(settingForm.getProxyHost())) {
			settingForm.setProxyLevel(DbDomainConst.ProxySetting.MANUAL_PROXY);
		} else {
			settingForm.setProxyLevel(DbDomainConst.ProxySetting.NONE_PROXY);
			settingForm.setProxyPort("0");
		}
		
		if(StringUtils.isEmpty(settingForm.getBatchJobPath())){
			settingForm.setBatchDirectoryType(DbDomainConst.BatchDirectoryType.RELATIVE);
		}else{
			settingForm.setBatchDirectoryType(DbDomainConst.BatchDirectoryType.ABSOLUTE);
		}

		return MODIFY_SETTING_FORM_PATH;
	}

	/**
	 * Modify system setting process
	 * @param settingForm SettingForm
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return MODIFY_FORM_PATH
	 */
	@RequestMapping(value = "modifySystemSetting", method = { RequestMethod.POST })
	@TransactionTokenCheck(value = "modifySystemSetting", type = TransactionTokenType.IN)
	public String processModifySystemSetting(@Validated SettingForm form, BindingResult result, RedirectAttributes redirectAttr,Model model) {
		if(result.hasErrors())
		{
			ValidationUtils.setBindingResult(result,model);
			return MODIFY_SETTING_FORM_PATH;
		}

		if (!DbDomainConst.ProxySetting.MANUAL_PROXY.equals(form.getProxyLevel())){
			form.setProxyHost(null);
			form.setProxyPort(null);
			form.setProxyUser(null);
			form.setProxyPass(null);
		}

		if(form.getBatchDirectoryType() == 0){
			form.setBatchJobPath(SessionUtils.getBatchJobPath());
		}
		AccountProfile accountProfile = beanMapper.map(form, AccountProfile.class);
		try {
			accountProfile.setCurrentProjectId(SessionUtils.getCurrentProjectId());
			systemService.modifySystemSetting(accountProfile);
			model.addAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(CommonMessageConst.SC_TQP_0010)));
			SessionUtils.set(SessionUtils.ACCOUNT_PROFILE, accountProfile);
		} catch(BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
		}
		return MODIFY_SETTING_FORM_PATH;
	}

	@RequestMapping(value = "systemTestConnection", method = { RequestMethod.POST })
	@TransactionTokenCheck(value = "modifySystemSetting", type = TransactionTokenType.IN)
	public String processSystemTestConnection(@Validated SettingForm form, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		if (result.hasErrors()) {
			ValidationUtils.setBindingResult(result, model);
			return MODIFY_SETTING_FORM_PATH;
		}

		if (!DbDomainConst.ProxySetting.MANUAL_PROXY.equals(form.getProxyLevel())){
			form.setProxyHost(null);
			form.setProxyPort(null);
			form.setProxyUser(null);
			form.setProxyPass(null);
		}

		AccountProfile accountProfile = beanMapper.map(form, AccountProfile.class);

		try {
			if (Boolean.TRUE.equals(form.getTestBingFlag())) {
				languageService.testConnection(accountProfile);
				model.addAttribute("message", ResultMessages.info().add(AccountProfileMessageConst.INF_ACCOUNTPROFILE_0001));
			} else {
				URL url = new URL(accountProfile.getUrlTestProxy());
				Proxy proxy = null;
				if (DbDomainConst.ProxySetting.MANUAL_PROXY.equals(form.getProxyLevel())) {
					proxy = ProxyCommon.initProxySystemProxy(accountProfile.getProxyHost(), Integer.parseInt(accountProfile.getProxyPort()));
				}
				Boolean testInternetResult = languageDesignService.testInternetConnection(url, proxy, accountProfile.getProxyUser(), accountProfile.getProxyPass());
				if (testInternetResult) {
					model.addAttribute("message", ResultMessages.info().add(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0055));
				} else {
					model.addAttribute("message", MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0056, accountProfile.getUrlTestProxy()));
				}
			}
			return MODIFY_SETTING_FORM_PATH;
		} catch (UnknownHostException e) {
			model.addAttribute("message", MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0056, accountProfile.getUrlTestProxy()));
			return MODIFY_SETTING_FORM_PATH;
		} catch (Exception ex) {
			model.addAttribute("message", ex.getMessage());
			return MODIFY_SETTING_FORM_PATH;
		}
	}
	
	/**
	 * Initialize modify theme setting screen
	 * @param accountThemeForm AccountThemeForm
	 * @return MODIFY_THEME_FORM_PATH
	 */
	@RequestMapping(value = "modifyTheme", method = { RequestMethod.GET })
	@TransactionTokenCheck(value = "modifyTheme", type = TransactionTokenType.BEGIN)
	public String displayModifyTheme(@ModelAttribute AccountThemeForm accountThemeForm) {
		// get information of theme
		Account account = (Account) SessionUtils.get(SessionUtils.ACCOUNT_INFOR);
		if (account != null) {
			// HashMap<String, String> themes =
			// accountThemeService.getThemeSetting(account.getAccountId());
			@SuppressWarnings("unchecked")
			Map<String, String> themes = (Map<String, String>) SessionUtils.get(SessionUtils.THEME_INFOR);
			if (themes.size() == 0) {
				themes = clstTheme.asMap();
			}
			accountThemeForm.setMapTheme(themes);
			return MODIFY_THEME_FORM_PATH;
		}
		return LOGIN_REDIRECT_PATH;
	}

	/**
	 * Modify system setting process
	 * @param accountThemeForm AccountThemeForm
	 * @param redirectAttr RedirectAttributes
	 * @return MODIFY_FORM_PATH
	 */
	@RequestMapping(value = "/modifyTheme", method = { RequestMethod.POST })
	@TransactionTokenCheck(value = "modifyTheme", type = TransactionTokenType.IN)
	public String processModifyTheme(@ModelAttribute AccountThemeForm accountThemeForm, RedirectAttributes redirectAttr) {
		Account account = (Account) SessionUtils.get(SessionUtils.ACCOUNT_INFOR);
		if (account != null) {
			accountThemeService.saveSetting(accountThemeForm.getMapTheme(), account.getAccountId());
			SessionUtils.set(SessionUtils.THEME_INFOR, accountThemeForm.getMapTheme());
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, 
					MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0039)));
			return "redirect:modifyTheme";
		}
		return LOGIN_REDIRECT_PATH;
	}

	/**
	 * Register default theme process
	 * @param redirectAttr RedirectAttributes
	 * @return MODIFY_FORM_PATH
	 */
	@RequestMapping(value = "/setDefaultTheme", method = { RequestMethod.GET })
	public String processRegisterDefaultTheme(RedirectAttributes redirectAttr) {
		Account account = (Account) SessionUtils.get(SessionUtils.ACCOUNT_INFOR);
		if (account != null) {
			accountThemeService.saveSetting(clstTheme.asMap(), account.getAccountId());
			SessionUtils.set(SessionUtils.THEME_INFOR, clstTheme.asMap());
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, 
					MessageUtils.getMessage(AccountProfileMessageConst.SC_ACCOUNTPROFILE_0039)));
			return "redirect:modifyTheme";
		}
		return LOGIN_REDIRECT_PATH;
	}

}
