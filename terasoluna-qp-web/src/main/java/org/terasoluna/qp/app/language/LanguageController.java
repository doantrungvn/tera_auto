package org.terasoluna.qp.app.language;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;
import org.springframework.context.i18n.LocaleContextHolder;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;
import org.terasoluna.gfw.common.codelist.ReloadableCodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.ultils.HttpServletRequestUtils;
import org.terasoluna.qp.app.common.ultils.LocaleUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.language.LanguageForm.TranslationSystemForm;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LanguageMessageConst;
import org.terasoluna.qp.app.message.MessageForm;
import org.terasoluna.qp.domain.model.Language;

import org.terasoluna.qp.domain.repository.language.LanguageCriteria;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.language.LanguageService;
import org.terasoluna.qp.domain.service.message.MessageService;

@Controller
@RequestMapping(value = "language")
@TransactionTokenCheck(value = "language")
public class LanguageController {

	private static final String LANGUAGE_FORM_NAME = "languageForm";
	private static final String REGISTER_FORM_PATH = "language/registerForm";
	private static final String VIEW_FORM_PATH = "language/viewForm";
	/* private static final String REFRESH_FORM = "refreshForm"; */
	private static final String MODIFY_FORM_PATH = "language/modifyForm";
	//private static final String SEARCH_REDIRECT_PATH = "redirect:/message/search";
	private static final String REDIRECT_TRANSLATE_SUCCESS = "redirect:/language/translate";
	private static final String TRANSLATE_SUCCESS = "language/translateCompleteForm";
	private static final String REDIRECT_DELETION_SUCCESS = "redirect:/complete";
	private static final String ACTION_SEARCH = "/language/search";
	private static final String SEARCH_LINK = "language/searchForm";
	private static final String REDIRECT_SEARCH ="redirect:/language/search";

	@Inject
	LanguageService languageService;

	@Inject
	MessageService messageService;

	@Inject
	Mapper beanMapper;

	@Inject
	LanguageValidator languageValidator;

	@Inject
	@Named(value = "CL_LANGUAGE_LIST")
	ReloadableCodeList codeListLanguage;

	@Inject
	SystemService systemService;

	@InitBinder("languageForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(languageValidator);
	}

	@ModelAttribute("languageForm")
	public LanguageForm setUpForm() {
		LanguageForm form = new LanguageForm();
		return form;
	}

	@ModelAttribute("languageSearchForm")
	public LanguageSearchForm setUpSearchForm() {
		LanguageSearchForm obj = new LanguageSearchForm();
		return obj;
	}

	@ModelAttribute("languagecodes")
	public String setUpLanguageCodes() {
		StringBuilder strLanguageCodes = new StringBuilder();

		Map<String, String> clLanguage = codeListLanguage.asMap();
		/* Collection<Language> languages = languageService.findAllLanguage(); */

		for (Map.Entry<String, String> language : clLanguage.entrySet()) {
			strLanguageCodes.append(language.getKey());
			strLanguageCodes.append("\t");
		}

		/*
		 * clLanguage.forEach((key, value) -> { strLanguageCodes.append(key); strLanguageCodes.append("\t"); });
		 */

		return strLanguageCodes.toString();
	}
	

	@RequestMapping(value = "/locale", method = { RequestMethod.GET })
	public String changeLocale(LanguageForm form, RedirectAttributes redirectAttr) {

		Locale currentLocale = LocaleContextHolder.getLocale();

		Locale requestLocale = LocaleUtils.getRequestLocale();
		if (requestLocale != null) {
			currentLocale = requestLocale;
		}

		// Check if exist messages for new locale
		Language newLanguage = new Language(form.getLanguageCode(), form.getCountryCode());
		Integer langCount = messageService.countByLanguage(newLanguage);
		if (langCount == null) {
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add(CommonMessageConst.ERR_SYS_0037, MessageUtils.getMessage("sc.message.0007")));
		} else {
			if (form != null && !currentLocale.getCountry().equals(form.getCountryCode()) && !currentLocale.getLanguage().equals(form.getLanguageCode())) {
				Locale newLocale = LocaleUtils.toLocale(form.getLanguageCode(), form.getCountryCode(), "");
				WebUtils.setSessionAttribute(HttpServletRequestUtils.getRequest(), LocaleUtils.LOCALE_SESSION_ATTRIBUTE_NAME, newLocale);
			}
		}

		// process get language id
//		LanguageDesign languageDesign = languageDesignService.getLanguageDefaultForDesign();
//		SessionUtils.set(SessionUtils.CURRENT_LANGUAGE_DESIGN, languageDesign);

		return "redirect:/home";
	}

	/**
	 * register language screen
	 * 
	 * @param languageForm
	 * @param model
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(@ModelAttribute(LANGUAGE_FORM_NAME) LanguageForm languageForm, Model model) {
		return REGISTER_FORM_PATH;
	}

	/**
	 * register language process
	 * 
	 * @param languageForm
	 * @param model
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated LanguageForm languageForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		if (result.hasErrors()) {
			ValidationUtils.setBindingResult(result, model);
			return REGISTER_FORM_PATH;
		}
		Language language = beanMapper.map(languageForm, Language.class);
		
		try{
			languageService.register(language, languageForm.isTranslateFlg(), SessionUtils.getAccountId());
		}catch(BusinessException ex){
			model.addAttribute("message", ex.getResultMessages());
			return REGISTER_FORM_PATH;
		}
	
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add("inf.sys.0002", MessageUtils.getMessage("sc.message.0005")));
		MessageUtils.reloadMessage = true;
		return REDIRECT_SEARCH;
	}

	/**
	 * return view screen
	 * 
	 * @param languageForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String view(@ModelAttribute MessageForm messageForm, Model model, RedirectAttributes redirectAttr, @PageableDefault Pageable pageable) {
		try {
			Language param = new Language(messageForm.getLanguageCode(), messageForm.getCountryCode());
			Language language = languageService.findLanguage(param);
			LanguageForm languageForm = beanMapper.map(language, LanguageForm.class);

			model.addAttribute("languageForm", languageForm);
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return REDIRECT_SEARCH;
		}
		return VIEW_FORM_PATH;
	}

	/**
	 * Initialize modify language screen
	 * 
	 * @param languageForm
	 * @param model
	 *			Model
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(LanguageForm languageForm, Model model) {

		Language param = beanMapper.map(languageForm, Language.class);

		Language language = languageService.findLanguage(param);

		languageForm = beanMapper.map(language, LanguageForm.class);
		model.addAttribute("languageForm", languageForm);
		return MODIFY_FORM_PATH;
	}

	/**
	 * Modify language process
	 * 
	 * @param languageForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated LanguageForm languageForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		if (result.hasErrors()) {
			ValidationUtils.setBindingResult(result, model);
			return MODIFY_FORM_PATH;
		}
		Language language = beanMapper.map(languageForm, Language.class);
		int rowCount = languageService.modify(language);
		if (rowCount > 0) {
			MessageUtils.reloadMessage = true;
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add("inf.sys.0003", MessageUtils.getMessage("sc.message.0005")));
			return REDIRECT_SEARCH;
		} else {
			model.addAttribute("message", ResultMessages.error().add("err.sys.0072", MessageUtils.getMessage("sc.message.0005")));
			return MODIFY_FORM_PATH;
		}
	}

	/**
	 * delete language
	 * 
	 * @param languageForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String progressDelete(LanguageForm languageForm, Model model, RedirectAttributes redirectAttr) {
		/*
		 * Language language = beanMapper.map(languageForm, Language.class); languageService.delete(language); return REFRESH_FORM;
		 */

		Language language = beanMapper.map(languageForm, Language.class);
		try {
			languageService.delete(language);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			model.addAttribute("notExistFlg", 1);
			return VIEW_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage("sc.language.0012")));
		return REDIRECT_DELETION_SUCCESS;
	}

	/**
	 * translate language
	 * 
	 * @param languageDesignForm
	 * @param model
	 * @param redirectAttr
	 */
	@RequestMapping(value = "translate", method = RequestMethod.POST)
	public String processTranslate(@Validated({TranslationSystemForm.class }) LanguageForm languageForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		if (result.hasErrors()) {
			//ValidationUtils.setBindingResult(result, model);
			return VIEW_FORM_PATH;
		}
		Language language = beanMapper.map(languageForm, Language.class);
		try {
			languageService.translateLanguage(language, SessionUtils.getCurrentAccountProfile());
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return VIEW_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(LanguageMessageConst.SC_LANGUAGE_0064));
		return REDIRECT_TRANSLATE_SUCCESS;
	}

	@RequestMapping(value = "translate", method=RequestMethod.GET)
	public String displayTranslateSuccess() {
		return TRANSLATE_SUCCESS;
	}
	/**
	 * displaySearch
	 * @param init
	 * @param languageDesignSearchForm
	 * @param model
	 * @param pageable
	 * @param sessionStatus
	 * @return
	 */
	@RequestMapping(value = "search", method=RequestMethod.GET )
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute LanguageSearchForm languageSearchForm,
			Model model,@PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if(init != null){
			sessionStatus.setComplete();
			languageSearchForm = setUpSearchForm();
			model.addAttribute("languageSearchForm", languageSearchForm);
		}
		
		pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
		LanguageCriteria languageCriteria = beanMapper.map(languageSearchForm, LanguageCriteria.class);
		Page<Language> pageLanguage = languageService.findPageByCriteria(languageCriteria, pageable);
		model.addAttribute("page", pageLanguage);
		return SEARCH_LINK;
	}

	/**
	 * processSearch
	 * @param languageDesignSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search", method =RequestMethod.POST)
	public String processSearch(@ModelAttribute LanguageSearchForm languageSearchForm,
			Model model,@PageableDefault Pageable pageable) {
		
		pageable = new PageRequest(pageable.getPageNumber(),PageSizeUtils.getPageSizeOfAction(ACTION_SEARCH),pageable.getSort());
		LanguageCriteria languageCriteria = beanMapper.map(languageSearchForm, LanguageCriteria.class);
		Page<Language> pageLanguage = languageService.findPageByCriteria(languageCriteria, pageable);
		model.addAttribute("page", pageLanguage);
		
		return SEARCH_LINK;
	}
}
