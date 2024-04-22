package org.terasoluna.qp.app.licensedesign;

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
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LicenseDesignMessageConst;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.model.LicenseDesign;
import org.terasoluna.qp.domain.repository.licensedesign.LicenseDesignCriteria;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.licensedesign.LicenseDesignService;
import org.terasoluna.qp.domain.service.message.MessageService;

@Controller
@RequestMapping(value = "licensedesign")
@TransactionTokenCheck(value = "licensedesign")
@SessionAttributes(types = { LicenseDesignSearchForm.class })
public class LicenseDesignController {
	private static final Logger logger = LoggerFactory.getLogger(LicenseDesignController.class);
	private static final String LICENSE_DESIGN_FORM_NAME = "licenseDesignForm";
	private static final String LICENSE_DESIGN_SEARCH_FORM_NAME = "licenseDesignSearchForm";
	private static final String REGISTER_FORM_PATH = "licensedesign/registerForm";
	private static final String VIEW_FORM_PATH = "licensedesign/viewForm";
	private static final String MODIFY_FORM_PATH = "licensedesign/modifyForm";
	private static final String REDIRECT_DELETION_SUCCESS = "redirect:/complete";
	private static final String SEARCH_ACTION_PATH = "/licensedesign/search";
	private static final String SEARCH_FORM_PATH = "licensedesign/searchForm";
	private static final String REDIRECT_SEARCH = "redirect:/licensedesign/search";
	private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";

	@Inject
	LicenseDesignService licenseDesignService;

	@Inject
	MessageService messageService;

	@Inject
	Mapper beanMapper;

	@Inject
	SystemService systemService;

	@Inject
	LicenseDesignValidator licenseDesignValidator;

	@ModelAttribute("licensedesignForm")
	public LicenseDesignForm setUpForm() {
		LicenseDesignForm form = new LicenseDesignForm();
		return form;
	}

	@ModelAttribute("licensedesignSearchForm")
	public LicenseDesignSearchForm setUpSearchForm() {
		LicenseDesignSearchForm obj = new LicenseDesignSearchForm();
		return obj;
	}

	/**
	 * Identifies methods which initialize the project form object
	 * 
	 * @return projectForm
	 */
	@ModelAttribute
	public LicenseDesignSearchForm setUpLicenseDesignSearchForm() {
		LicenseDesignSearchForm licenseDesignSearchForm = new LicenseDesignSearchForm();
		logger.debug("Init form {0}", licenseDesignSearchForm);
		return licenseDesignSearchForm;
	}

	/**
	 * Identifies methods which initialize the project search form object
	 * 
	 * @return projectForm
	 */
	@ModelAttribute
	public LicenseDesignForm setUpLicenseDesignForm() {
		LicenseDesignForm licenseDesignForm = new LicenseDesignForm();
		logger.debug("Init form {0}", licenseDesignForm);
		return licenseDesignForm;
	}

	@InitBinder(LICENSE_DESIGN_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		webDataBinder.addValidators(licenseDesignValidator);
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
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute(LICENSE_DESIGN_SEARCH_FORM_NAME) LicenseDesignSearchForm licenseDesignSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if (init != null) {
			sessionStatus.setComplete();
			licenseDesignSearchForm = new LicenseDesignSearchForm();
			model.addAttribute(LICENSE_DESIGN_SEARCH_FORM_NAME, licenseDesignSearchForm);
			
		}
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		LicenseDesignCriteria licenseDesignCriteria = beanMapper.map(licenseDesignSearchForm, LicenseDesignCriteria.class);
		Page<LicenseDesign> page = licenseDesignService.searchLicenseDesign(licenseDesignCriteria, pageable);
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
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(@ModelAttribute(LICENSE_DESIGN_SEARCH_FORM_NAME) LicenseDesignSearchForm licenseDesignSearchForm, Model model, @PageableDefault Pageable pageable) {
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		LicenseDesignCriteria licenseDesignCriteria = beanMapper.map(licenseDesignSearchForm, LicenseDesignCriteria.class);
		Page<LicenseDesign> page = licenseDesignService.searchLicenseDesign(licenseDesignCriteria, pageable);
		model.addAttribute("page", page);

		return SEARCH_FORM_PATH;
	}

	/**
	 * Initialize register module screen
	 * 
	 * @param moduleForm
	 *            ModuleForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(@ModelAttribute(LICENSE_DESIGN_FORM_NAME) LicenseDesignForm licenseDesignForm, Model model, Account account) {
		// checkChangeProject(true);
		licenseDesignForm.setUpdatedBy(account.getAccountId());
		return REGISTER_FORM_PATH;
	}

	/**
	 * Register module process
	 * 
	 * @param moduleForm
	 * @param model
	 * @return SEARCH_REDIRECT_PATH in case of success. Otherwise return to Initialize register module screen
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated LicenseDesignForm licenseDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model, SessionStatus sessionStatus) {
		// checkChangeProject(true);
		if (result.hasErrors()) {
			return REGISTER_FORM_PATH;
		}

		LicenseDesign licenseDesign = beanMapper.map(licenseDesignForm, LicenseDesign.class);
		try {
			licenseDesignService.register(licenseDesign);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return REGISTER_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0011)));
		return REDIRECT_SEARCH;
	}

	/**
	 * return view screen
	 * 
	 * @param moduleForm
	 * @param model
	 * @return VIEW_FORM_PATH
	 */
	@RequestMapping(value = "view")
	public String processView(@ModelAttribute(LICENSE_DESIGN_FORM_NAME) LicenseDesignForm licenseDesignForm, Model model, RedirectAttributes redirectAttr) {
		// checkChangeProject(false);
		LicenseDesign licenseDesign = null;
		try {
			licenseDesign = licenseDesignService.findLicenseDesignById(licenseDesignForm.getLicenseId());
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return REDIRECT_SEARCH;
		}

		licenseDesignForm = beanMapper.map(licenseDesign, LicenseDesignForm.class);
		model.addAttribute(LICENSE_DESIGN_FORM_NAME, licenseDesignForm);

		return VIEW_FORM_PATH;
	}

	/**
	 * Initialize modify module screen
	 * 
	 * @param moduleForm
	 *            ModuleForm
	 * @param model
	 *            Model
	 * @return
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(@ModelAttribute(LICENSE_DESIGN_FORM_NAME) LicenseDesignForm licenseDesignForm, Model model, RedirectAttributes redirectAttr, Account account) {
		licenseDesignForm.setUpdatedBy(account.getAccountId());
		String destination = StringUtils.EMPTY;
		LicenseDesign licenseDesign = beanMapper.map(licenseDesignForm, LicenseDesign.class);
		try {
			licenseDesign = licenseDesignService.findLicenseDesignById(licenseDesign.getLicenseId());
			licenseDesignForm = beanMapper.map(licenseDesign, LicenseDesignForm.class);
			model.addAttribute("licenseDesignForm", licenseDesignForm);
			destination = MODIFY_FORM_PATH;
		} catch (BusinessException be) {
			if (MODE_SEARCH.equals(licenseDesignForm.getMode())) {
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				destination = REDIRECT_SEARCH;
			} else if (MODE_VIEW.equals(licenseDesignForm.getMode())) {
				model.addAttribute("notExistFlg", 1);
				model.addAttribute("message", be.getResultMessages());
				destination = VIEW_FORM_PATH;
			}
		}
		return destination;
	}

	/**
	 * Modify module process Edit a module
	 * 
	 * @param moduleForm
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated @ModelAttribute LicenseDesignForm licenseDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		if (result.hasErrors()) {
			return MODIFY_FORM_PATH;
		}
		LicenseDesign licenseDesign = beanMapper.map(licenseDesignForm, LicenseDesign.class);
		try {
			licenseDesignService.modify(licenseDesign);
		} catch (BusinessException be) {
			if (CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("message", be.getResultMessages());
				model.addAttribute("notExistFlg", 1);
				return MODIFY_FORM_PATH;
			}
			model.addAttribute("message", be.getResultMessages());
			return MODIFY_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0011)));
		return REDIRECT_SEARCH;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute LicenseDesignForm licenseDesignForm, Model model, RedirectAttributes redirectAttr) {
		LicenseDesign licenseDesign = beanMapper.map(licenseDesignForm, LicenseDesign.class);
		try {
			licenseDesignService.delete(licenseDesign);
		} catch (BusinessException be) {
			if (CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("message", be.getResultMessages());
				model.addAttribute("notExistFlg", 1);
				return VIEW_FORM_PATH;
			}
			if (CommonMessageConst.ERR_SYS_0097.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("message", be.getResultMessages());
				model.addAttribute("licenseDesignForm", licenseDesignForm);
				model.addAttribute("notExistFlg", 0);
				return VIEW_FORM_PATH;
			}

		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(LicenseDesignMessageConst.SC_LICENSEDESIGN_0011)));
		return REDIRECT_DELETION_SUCCESS;
	}

	@RequestMapping(value = "generate", method = RequestMethod.GET)
	public String processGenerate(@ModelAttribute(LICENSE_DESIGN_FORM_NAME) LicenseDesignForm licenseDesignForm, Model model, RedirectAttributes redirectAttr) {
		try {
			String fileName = licenseDesignService.generateLicense(licenseDesignForm.getLicenseId());
			redirectAttr.addFlashAttribute("fileName", fileName);
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return REDIRECT_SEARCH;
		}

		return REDIRECT_SEARCH;
	}
}
