package org.terasoluna.qp.app.languagedesign;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.apache.commons.lang3.ArrayUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.languagedesign.LanguageDesignForm.RegistrationForm;
import org.terasoluna.qp.app.languagedesign.LanguageDesignForm.TranslationForm;
import org.terasoluna.qp.app.message.AccountProfileMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LanguageDesignMessageConst;
import org.terasoluna.qp.app.messagedesign.MessageDesignController;
import org.terasoluna.qp.app.messagedesign.MessageDesignSearchForm;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.repository.languagedesign.LanguageDesignCriteria;
import org.terasoluna.qp.domain.service.accountprofile.AccountProfileService;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.language.LanguageService;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;
import org.terasoluna.qp.domain.service.messagedesign.MessageDesignService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Controller
@RequestMapping(value = "languagedesign")
@TransactionTokenCheck(value = "languagedesign")
@SessionAttributes(types = { LanguageDesignSearchForm.class })
public class LanguageDesignController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(MessageDesignController.class);
	private static final String MESSAGE_DESIGN_SEARCH_FORM_NAME = "messageDesignSearchForm";
	private static final String LANGUAGE_DESIGN_FORM_NAME = "languageDesignForm";
	private static final String REGISTER_FORM_PATH = "languagedesign/registerForm";
	private static final String VIEW_FORM_PATH = "languagedesign/viewForm";
	//private static final String REDIRECT_DELETION_SUCCESS = "redirect:/complete";
	private static final String MODIFY_FORM_PATH = "languagedesign/modifyForm";
	// private static final String SEARCH_REDIRECT_PATH = "redirect:/messagedesign/search?init";
	//private static final String REDIRECT_TRANSLATE_SUCCESS = "redirect:/languagedesign/translate";
	//private static final String TRANSLATE_SUCCESS = "languagedesign/translateCompleteForm";
	private static final String ACTION_SEARCH = "/languagedesign/search";
	private static final String SEARCH_LINK = "languagedesign/searchForm";
	private static final String REDIRECT_SEARCH = "redirect:/languagedesign/search";
	private static final String BING_TRANSLATE_SETTING_LINK = "languagedesign/bingSettingForm";
	private static final String REDIRECT_BING_TRANSLATE_SETTING_LINK = "redirect:/languagedesign/languagedesignBingsetting";
	/** Bing translate setting */
	public static final String SC_ACCOUNTPROFILE_0036 = "sc.accountprofile.0036";

	private static final String MODE_VIEW = "1";

	@Inject
	LanguageDesignService languageDesignService;

	@Inject
	MessageDesignService messageDesignService;

	@Inject
	ProjectService projectService;
	
	@Inject
	Mapper beanMapper;

	@Inject
	LanguageDesignValidator languageDesignValidator;

	@Inject
	BingTranslateSettingValidator bingTranslateSettingValidator;

	@Inject
	LanguageDesignDesignFormValidator languageDesignDesignFormValidator;

	@Inject
	SystemService systemService;

	@Inject
	LanguageService languageService;

	@Inject
	AccountProfileService accountProfileService;

	@InitBinder("languageDesignForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(languageDesignValidator);
	}

	@InitBinder("bingTranslateSettingForm")
	public void initBinderBingTranslateSetting(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(bingTranslateSettingValidator);
	}

	@InitBinder("languageDesignDesignForm")
	public void initDesignForm(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(languageDesignDesignFormValidator);
	}

	@ModelAttribute
	public void init() {
		moduleCode = CommonMessageConst.TQP_LANGUAGEDESIGN;
	}

	@ModelAttribute("languageDesignForm")
	public LanguageDesignForm setUpForm() {
		LanguageDesignForm form = new LanguageDesignForm();
		return form;
	}

	@ModelAttribute(MESSAGE_DESIGN_SEARCH_FORM_NAME)
	public MessageDesignSearchForm setUpMessageDesignSearchForm() {
		MessageDesignSearchForm messageDesignSearchForm = new MessageDesignSearchForm();
		logger.debug("Init form {0}", messageDesignSearchForm);
		return messageDesignSearchForm;
	}

	@ModelAttribute("languageDesignSearchForm")
	public LanguageDesignSearchForm setUpSearchForm() {
		LanguageDesignSearchForm obj = new LanguageDesignSearchForm();
		logger.debug("Init form {0}", obj);
		return obj;
	}

	/**
	 * Pre-initialization of language
	 * 
	 * @param model
	 * @return languages Collection
	 */
	@ModelAttribute("languages")
	public String setUpLanguage() {
		StringBuilder languages = new StringBuilder();
		Collection<LanguageDesign> languageDesigns = languageDesignService.findAllLanguageDesign();
		int idx = 0;
		for (LanguageDesign languageDesign : languageDesigns) {
			languages.append(languageDesign.getLanguageCode());
			languages.append(DbDomainConst.SEPARATE_LANGUAGE_COUNTRY);
			languages.append(languageDesign.getCountryCode());
			if (idx++ != languageDesigns.size()) {
				languages.append("\t");
			}
		}
		return languages.toString();
	}

	/*@RequestMapping(value = "/locale", method = { RequestMethod.GET })
	public String changeLocale(LanguageDesignForm form) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		Locale requestLocale = LocaleUtils.getRequestLocale();
		if (requestLocale != null) {
			currentLocale = requestLocale;
		}
		if (form != null && !currentLocale.getCountry().equals(form.getCountryCode()) && !currentLocale.getLanguage().equals(form.getLanguageCode())) {
			Locale newLocale = LocaleUtils.toLocale(form.getLanguageCode(), form.getCountryCode(), "");
			WebUtils.setSessionAttribute(HttpServletRequestUtils.getRequest(), LocaleUtils.LOCALE_SESSION_ATTRIBUTE_NAME, newLocale);
		}
		return "redirect:/";
	}*/

	/**
	 * register language design screen
	 * 
	 * @param languageDesignForm
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(LanguageDesignDesignForm languageDesignForm, RedirectAttributes redirectAttr, Model model) {
		try {
			checkChangeProject(true);

			Project project = SessionUtils.getCurrentProject();
			
			LanguageDesign[] languageDesigns = languageDesignService.findAllLanguageDesignByProject(project.getProjectId());
			LanguageDesignForm[] languageDesignForms = new LanguageDesignForm[languageDesigns.length];
			
			for (int i = 0; i < languageDesigns.length; i++) {
				languageDesignForms[i] = beanMapper.map(languageDesigns[i], LanguageDesignForm.class);
				if (DataTypeUtils.equals(project.getDefaultLanguageId(), languageDesigns[i].getLanguageId())) {
					languageDesignForm.setDefaultLanguage(DataTypeUtils.toString(languageDesigns[i].getItemSeqNo()));
				}
			}

			languageDesignForm.setProjectId(project.getProjectId());
			languageDesignForm.setLanguageDesignForms(languageDesignForms);
			return REGISTER_FORM_PATH;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return REDIRECT_SEARCH;
		}
	}

	/**
	 * register language design process
	 * 
	 * @param languageDesignForm
	 * @param model
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated({ Default.class, RegistrationForm.class }) LanguageDesignDesignForm languageDesignDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		
		if (result.hasErrors()) {
			return REGISTER_FORM_PATH;
		}
		try {
			checkChangeProject(true);
			if (ArrayUtils.isNotEmpty(languageDesignDesignForm.getLanguageDesignForms())) {
				LanguageDesign[] languageDesigns = new LanguageDesign[languageDesignDesignForm.getLanguageDesignForms().length];
				for (int i = 0; i < languageDesigns.length; i++) {
					languageDesigns[i] = beanMapper.map(languageDesignDesignForm.getLanguageDesignForms()[i], LanguageDesign.class);
				}

				Project curPro = SessionUtils.getCurrentProject();
				Long accountId = SessionUtils.getAccountId();
				projectService.validateProject(languageDesignDesignForm.getProjectId(), accountId, curPro.getProjectId(), true);
				LanguageDesign languageDesign = languageDesignService.registerLanguageDesign(languageDesigns, SessionUtils.getDefaultLanguage(), curPro, languageDesignDesignForm.getDefaultLanguage(), accountId);

				SessionUtils.set(SessionUtils.CURRENT_LANGUAGE_DESIGN, languageDesign);
				SessionUtils.set(SessionUtils.CURRENT_PROJECT, curPro);
			}

		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return REGISTER_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0009)));
		/* return "redirect:/languagedesign/register"; */
		return REDIRECT_SEARCH;
	}

	/**
	 * return view screen
	 * 
	 * @param messageDesignForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String view(@ModelAttribute(LANGUAGE_DESIGN_FORM_NAME) LanguageDesignForm languageDesignForm, RedirectAttributes redirectAttr, Model model) {
		checkChangeProject(false);
		LanguageDesign languageDesign = beanMapper.map(languageDesignForm, LanguageDesign.class);
		try {
			languageDesign = languageDesignService.findByLanguageDesign(languageDesign);
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return REDIRECT_SEARCH;
		}
		languageDesignForm = beanMapper.map(languageDesign, LanguageDesignForm.class);
		if (DataTypeUtils.equals(languageDesign.getLanguageId(), SessionUtils.getCurrentProject().getDefaultLanguageId())) {
			languageDesignForm.setIsDefault("isDefault");
		}
		model.addAttribute(LANGUAGE_DESIGN_FORM_NAME, languageDesignForm);
		return VIEW_FORM_PATH;
	}

	/**
	 * Initialize modify language design screen
	 * 
	 * @param languageDesignForm
	 * @param model
	 *            Model
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(LanguageDesignForm languageDesignForm, RedirectAttributes redirectAttr, Model model) {
		LanguageDesign param = beanMapper.map(languageDesignForm, LanguageDesign.class);

		try {
			checkChangeProject(true);
			LanguageDesign languageDesign = languageDesignService.findByLanguageDesign(param);

			validateLanguageDesign(languageDesign);

			languageDesignForm = beanMapper.map(languageDesign, LanguageDesignForm.class);
			languageDesignForm.setLanguageCode(languageDesign.getLanguageCode() + "_" + languageDesign.getCountryCode());
			List<LanguageDesign> listLanguageProject = languageDesignService.findLanguageDesignByProjectId(SessionUtils.getCurrentProjectId());
			StringBuilder listKeyLanguageProject = new StringBuilder();
			for (LanguageDesign temp : listLanguageProject) {
				if (!DataTypeUtils.equals(languageDesign.getLanguageId(), temp.getLanguageId())) {
					listKeyLanguageProject.append(temp.getLanguageCode());
					listKeyLanguageProject.append("_");
					listKeyLanguageProject.append(temp.getCountryCode());
					listKeyLanguageProject.append(" ");
				}
			}
			languageDesignForm.setCurrentLanguageProject(listKeyLanguageProject.toString());
			model.addAttribute("languageDesignForm", languageDesignForm);
			return MODIFY_FORM_PATH;
		} catch (BusinessException be) {
			if (MODE_VIEW.equals(languageDesignForm.getMode())) {
				/*languageDesignForm.setHasErrors(true);
				model.addAttribute("message", be.getResultMessages());
				return VIEW_FORM_PATH;*/
				
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_LANGUAGEDESIGN));
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			}

			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return REDIRECT_SEARCH;

		}
	}

	/**
	 * Modify language design process
	 * 
	 * @param languageDesignForm
	 * @param model
	 * @param result
	 * @param redirectAttr
	 * @return
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(LanguageDesignForm languageDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		/*checkChangeProject(true);*/
		if (result.hasErrors()) {
			return MODIFY_FORM_PATH;
		}
		LanguageDesign languageDesign = beanMapper.map(languageDesignForm, LanguageDesign.class);
		try {

			validateLanguageDesign(languageDesign);

			Project project = SessionUtils.getCurrentProject();
			languageDesign.setCreatedBy(SessionUtils.getAccountId());
			languageDesign.setProjectId(project.getProjectId());
			languageDesignService.updateLanguageDesign(languageDesign, project.getDefaultLanguageId(), this.initCommon());

		} catch (BusinessException be) {
			/*if (CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", 1);
			}*/
			model.addAttribute("message", be.getResultMessages());
			return MODIFY_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0009)));
		/*redirectAttr.addAttribute("messageDesignSearchForm", null);*/
		return REDIRECT_SEARCH;
	}

	/**
	 * delete language design
	 * 
	 * @param languageDesignForm
	 * @param model
	 * @param redirectAttr
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String processDelete(LanguageDesignForm languageDesignForm, Model model, RedirectAttributes redirectAttr) {
		try {
			checkChangeProject(true);
			LanguageDesign languageDesign = beanMapper.map(languageDesignForm, LanguageDesign.class);
			languageDesignService.deleteLanguageDesign(languageDesign, SessionUtils.getDefaultLanguageDesign(), this.initCommon());
		} catch (BusinessException be) {
			/*languageDesignForm.setHasErrors(true);
			model.addAttribute("message", be.getResultMessages());
			return VIEW_FORM_PATH;*/
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_LANGUAGEDESIGN));
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
		}

		redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_LANGUAGEDESIGN));
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0009)));
		return DbDomainConst.REDIRECT_DELETION_SUCCESS;
	}

	/**
	 * translate language design
	 * 
	 * @param languageDesignForm
	 * @param model
	 * @param redirectAttr
	 */
	@RequestMapping(value = "translate", method = RequestMethod.POST)
	public String processTranslate(@Validated({ Default.class, TranslationForm.class }) LanguageDesignForm languageDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		if (result.hasErrors()) {
			return VIEW_FORM_PATH;
		}
		try {
			checkChangeProject(true);
			LanguageDesign languageDesign = beanMapper.map(languageDesignForm, LanguageDesign.class);
			languageDesign.setProjectId(SessionUtils.getCurrentProjectId());
			languageDesignService.translateLanguageDesign(languageDesign, SessionUtils.getCurrentAccountProfile(), this.initCommon());
		} catch (BusinessException be) {
			/*model.addAttribute("message", be.getResultMessages());
			return VIEW_FORM_PATH;*/
			
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_LANGUAGEDESIGN));
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;

		}
		redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_LANGUAGEDESIGN));
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(LanguageDesignMessageConst.SC_LANGUAGEDESIGN_0016));
		return DbDomainConst.REDIRECT_DELETION_SUCCESS;
	}

	/*@RequestMapping(value = "translate", method = RequestMethod.GET)
	public String displayTranslateSuccess() {
		return TRANSLATE_SUCCESS;
	}*/

	/**
	 * displaySearch
	 * 
	 * @param init
	 * @param languageDesignSearchForm
	 * @param model
	 * @param pageable
	 * @param sessionStatus
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute LanguageDesignSearchForm languageDesignSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if (init != null) {
			sessionStatus.setComplete();
			languageDesignSearchForm = setUpSearchForm();
			model.addAttribute("languageDesignSearchForm", languageDesignSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		checkChangeProject(false);

		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		languageDesignSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		LanguageDesignCriteria languageDesignCriteria = beanMapper.map(languageDesignSearchForm, LanguageDesignCriteria.class);
		Page<LanguageDesign> pageLanguage = languageDesignService.findPageByCriteria(languageDesignCriteria, pageable);
		Project project = SessionUtils.getCurrentProject();
		for (LanguageDesign temp : pageLanguage) {
			if (DataTypeUtils.equals(project.getDefaultLanguageId(), temp.getLanguageId())) {
				temp.setDefaultFlg(true);
			}
		}
		model.addAttribute("page", pageLanguage);
		return SEARCH_LINK;
	}

	/**
	 * processSearch
	 * 
	 * @param languageDesignSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(@ModelAttribute LanguageDesignSearchForm languageDesignSearchForm, Model model, @PageableDefault Pageable pageable) {
		checkChangeProject(false);

		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH), pageable.getSort());
		
		Project project = SessionUtils.getCurrentProject();
		languageDesignSearchForm.setProjectId(project.getProjectId());
		LanguageDesignCriteria languageDesignCriteria = beanMapper.map(languageDesignSearchForm, LanguageDesignCriteria.class);
		Page<LanguageDesign> pageLanguage = languageDesignService.findPageByCriteria(languageDesignCriteria, pageable);
		
		for (LanguageDesign temp : pageLanguage) {
			if (DataTypeUtils.equals(project.getDefaultLanguageId(), temp.getLanguageId())) {
				temp.setDefaultFlg(true);
			}
		}
		model.addAttribute("page", pageLanguage);

		return SEARCH_LINK;
	}

	/**
	 * Bing translate setting
	 * 
	 * @param languageDesignForm
	 */
	@TransactionTokenCheck(value = "languagedesignBingsetting", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "languagedesignBingsetting", method = RequestMethod.GET)
	public String displayBingSetting(BingTranslateSettingForm form, Model model) {
		AccountProfile accountProfile = null;
		try {
			accountProfile = SessionUtils.getCurrentAccountProfile();
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return BING_TRANSLATE_SETTING_LINK;
		}
		beanMapper.map(accountProfile, form);
		return BING_TRANSLATE_SETTING_LINK;
	}

	/**
	 * Bing translate setting
	 * 
	 * @param languageDesignForm
	 */
	@RequestMapping(value = "languagedesignBingsetting", method = RequestMethod.POST)
	public String processBingSetting(@Validated({ Default.class, RegistrationForm.class }) BingTranslateSettingForm form, BindingResult result, RedirectAttributes redirectAttr, Model model) {

		if (result.hasErrors()) {
			return BING_TRANSLATE_SETTING_LINK;
		}
		if (Boolean.TRUE.equals(form.getTestFlag())) {
			AccountProfile accountProfile = SessionUtils.getCurrentAccountProfile();
			accountProfile.setBingClientId(form.getBingClientId());
			accountProfile.setBingClientSecret(form.getBingClientSecret());

			try {
				languageService.testConnection(accountProfile);
				model.addAttribute("message", ResultMessages.info().add(AccountProfileMessageConst.INF_ACCOUNTPROFILE_0001));
				return BING_TRANSLATE_SETTING_LINK;
			} catch (Exception ex) {
				model.addAttribute("message", ex.getMessage());
				return BING_TRANSLATE_SETTING_LINK;
			}
		} else {
			AccountProfile accountProfile = SessionUtils.getCurrentAccountProfile();
			accountProfile.setBingClientId(form.getBingClientId());
			accountProfile.setBingClientSecret(form.getBingClientSecret());
			accountProfileService.update(accountProfile);
			SessionUtils.remove(SessionUtils.ACCOUNT_PROFILE);
			SessionUtils.set(SessionUtils.ACCOUNT_PROFILE, accountProfile);
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(SC_ACCOUNTPROFILE_0036)));
			return REDIRECT_BING_TRANSLATE_SETTING_LINK;
		}
	}

	private void validateLanguageDesign(LanguageDesign languageDesign) {
		if (SessionUtils.getDefaultLanguageDesign().getLanguageCode().equals(languageDesign.getLanguageCode())) {
			throw new BusinessException(ResultMessages.error().add(LanguageDesignMessageConst.ERR_LANGUAGEDESIGN_0005));
		}
	}
}
