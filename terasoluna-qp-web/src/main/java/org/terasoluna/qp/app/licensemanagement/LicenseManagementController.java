package org.terasoluna.qp.app.licensemanagement;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.filter.CustomFilter;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ImportManagementMessageConst;
import org.terasoluna.qp.app.message.LicenseManagementMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.LicenseManagement;
import org.terasoluna.qp.domain.repository.licensemanagement.LicenseManagementCriteria;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.licensemanagement.LicenseManagementConst;
import org.terasoluna.qp.domain.service.licensemanagement.LicenseManagementService;
import org.terasoluna.qp.domain.service.message.MessageService;

@Controller
@RequestMapping(value = "licensemanagement")
@TransactionTokenCheck(value = "licensemanagement")
public class LicenseManagementController {
	private static final Logger logger = LoggerFactory.getLogger(LicenseManagementController.class);
	private static final String LICENSE_MANAGEMENT_FORM_NAME = "licenseManagementForm";
	private static final String LICENSE_MANAGEMENT_SEARCH_FORM_NAME = "licenseManagementSearchForm";
	private static final String REGISTER_FORM_PATH = "licensemanagement/registerForm";
	private static final String VIEW_FORM_PATH = "licensemanagement/viewForm";
	private static final String REDIRECT_DELETION_SUCCESS = "redirect:/complete";
	private static final String SEARCH_ACTION_PATH = "/licensemanagement/search";
	private static final String SEARCH_FORM_PATH = "licensemanagement/searchForm";
	private static final String REDIRECT_SEARCH = "redirect:/licensemanagement/search";

	private static final String VIEW_LICENSE_INFO_FORM_PATH = "licensemanagement/licenseInformationForm";

	
	@Inject
	LicenseManagementService licenseManagementService;

	@Inject
	MessageService messageService;

	@Inject
	Mapper beanMapper;

	@Inject
	SystemService systemService;

	@Inject
	LicenseManagementValidator licenseManagementValidator;

	@ModelAttribute("licenseManagementForm")
	public LicenseManagementForm setUpForm() {
		LicenseManagementForm form = new LicenseManagementForm();
		return form;
	}

	@ModelAttribute("licenseManagementSearchForm")
	public LicenseManagementSearchForm setUpSearchForm() {
		LicenseManagementSearchForm obj = new LicenseManagementSearchForm();
		return obj;
	}

	/**
	 * Identifies methods which initialize the project form object
	 * 
	 * @return projectForm
	 */
	@ModelAttribute
	public LicenseManagementSearchForm setUpLicenseManagementSearchForm() {
		LicenseManagementSearchForm licenseManagementSearchForm = new LicenseManagementSearchForm();
		logger.debug("Init form {0}", licenseManagementSearchForm);
		return licenseManagementSearchForm;
	}

	/**
	 * Identifies methods which initialize the project search form object
	 * 
	 * @return projectForm
	 */
	@ModelAttribute
	public LicenseManagementForm setUpLicenseManagementForm() {
		LicenseManagementForm licenseManagementForm = new LicenseManagementForm();
		logger.debug("Init form {0}", licenseManagementForm);
		return licenseManagementForm;
	}

	@InitBinder(LICENSE_MANAGEMENT_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(licenseManagementValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

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
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute(LICENSE_MANAGEMENT_SEARCH_FORM_NAME) LicenseManagementSearchForm licenseManagementSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if (init != null) {
			sessionStatus.setComplete();
			licenseManagementSearchForm = new LicenseManagementSearchForm();
			model.addAttribute(LICENSE_MANAGEMENT_SEARCH_FORM_NAME, licenseManagementSearchForm);
		}
		// checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		LicenseManagementCriteria liceneseManagementCriteria = beanMapper.map(licenseManagementSearchForm, LicenseManagementCriteria.class);
		Page<LicenseManagement> page = licenseManagementService.searchLicenseManagement(liceneseManagementCriteria, pageable);
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
	public String processSearch(@ModelAttribute(LICENSE_MANAGEMENT_SEARCH_FORM_NAME) LicenseManagementSearchForm licenseManagementSearchForm, Model model, @PageableDefault Pageable pageable) {
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		LicenseManagementCriteria liceneseManagementCriteria = beanMapper.map(licenseManagementSearchForm, LicenseManagementCriteria.class);
		Page<LicenseManagement> page = licenseManagementService.searchLicenseManagement(liceneseManagementCriteria, pageable);
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
	public String displayPopupRegister(@ModelAttribute(LICENSE_MANAGEMENT_FORM_NAME) LicenseManagementForm licenseManagementForm, Model model) {
		AccountProfile accountProfile = systemService.getDefaultProfile();
		licenseManagementForm.setMaxSize(accountProfile.getMaxSizeUpload());
		return REGISTER_FORM_PATH;
	}

	
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@ModelAttribute(LICENSE_MANAGEMENT_FORM_NAME) LicenseManagementForm licenseManagementForm, Model model, BindingResult result, RedirectAttributes redirectAttr, @RequestParam("status") Integer status) {
		try {
			LicenseManagement licenseManagement = beanMapper.map(licenseManagementForm, LicenseManagement.class);
			licenseManagement.setAppliedBy(SessionUtils.getAccountId());
			licenseManagementService.apply(licenseManagement);

			if (DbDomainConst.YesNoFlg.YES.compareTo(licenseManagement.getStatus()) == 0) {
				CustomFilter.activeTime = 0;
			}

		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return REGISTER_FORM_PATH;
		}

		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(LicenseManagementMessageConst.SC_LICENSEMANAGEMENT_0011)));
		redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_LICENSEMANAGEMENT));
		return DbDomainConst.REDIRECT_MODIFY_SUCCESS;
	}

	@RequestMapping(value = "import")
	public String processImport(@Validated @ModelAttribute(LICENSE_MANAGEMENT_FORM_NAME) LicenseManagementForm licenseManagementForm, BindingResult result, Model model, RedirectAttributes redirectAttr, @RequestParam("status") Integer status) throws UnsupportedEncodingException {
		if (result.hasErrors()) {
			licenseManagementForm.setResultFileName("");
			licenseManagementForm.setLicenseFileName("");
			return REGISTER_FORM_PATH;
		}
		
		LicenseManagement licensemanagement = beanMapper.map(licenseManagementForm, LicenseManagement.class);

		MultipartFile file = licenseManagementForm.getFileName();
		/*HttpServletRequest request = HttpServletRequestUtils.getRequest();
		HttpSession session = request.getSession(true);
		String path = session.getServletContext().getRealPath("/META-INF/" + "license");
		FileUtilsQP.createDirectory(path);*/

		String path = FileUtilsQP.getLicenseFolderPath();
		FileUtilsQP.createDirectory(path);
		
		//String licenseFileName = LicenseManagementConst.FILE_NAME;
		String filePath = path + LicenseManagementConst.FILE_NAME;
		try {
			file.transferTo(new File(filePath));
		} catch (IllegalStateException e) {
			model.addAttribute("message", e.getMessage());
			return REGISTER_FORM_PATH;
		} catch (IOException e) {
			model.addAttribute("message", e.getMessage());
			return REGISTER_FORM_PATH;
		}
		licensemanagement.setFilePath(filePath);

		int importCount = 0;
		int totalCount = 0;

		licenseManagementForm.setResultFileName(LicenseManagementConst.FILE_NAME);
		licenseManagementForm.setLicenseFileName(LicenseManagementConst.FILE_NAME);
		licensemanagement.setLicenseFileName(LicenseManagementConst.FILE_NAME);

		licenseManagementService.importLicense(licensemanagement);

		licenseManagementForm = beanMapper.map(licensemanagement, LicenseManagementForm.class);

		/*licenseManagementForm.setCustomerCode(licensemanagement.getCustomerCode());
		licenseManagementForm.setCustomerName(licensemanagement.getCustomerName());
		licenseManagementForm.setProjectId(licensemanagement.getProjectId());
		licenseManagementForm.setProjectCode(licensemanagement.getProjectCode());
		licenseManagementForm.setProjectName(licensemanagement.getProjectName());
		licenseManagementForm.setTel(licensemanagement.getTel());
		licenseManagementForm.setEmail(licensemanagement.getEmail());
		licenseManagementForm.setAddress(licensemanagement.getAddress());
		licenseManagementForm.setNum(String.valueOf(licensemanagement.getNum()));
		licenseManagementForm.setVersion(licensemanagement.getVersion());
		licenseManagementForm.setStartDate(DateUtils.formatDateTime(licensemanagement.getStartDate(), Date));
		licenseManagementForm.setExpiredDate(licensemanagement.getExpiredDate().toString());
		licenseManagementForm.setAppliedDate(licensemanagement.getAppliedDate().toString());
		licenseManagementForm.setStatus(licensemanagement.getStatus());*/

		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(ImportManagementMessageConst.INF_IMPORTMANAGEMENT_0001, importCount, totalCount));
		redirectAttr.addFlashAttribute("licenseManagementForm", licenseManagementForm);
		model.addAttribute(LICENSE_MANAGEMENT_FORM_NAME, licenseManagementForm);

		return VIEW_LICENSE_INFO_FORM_PATH;
	}

	/**
	 * return view screen
	 * 
	 * @param moduleForm
	 * @param model
	 * @return VIEW_FORM_PATH
	 */
	@RequestMapping(value = "view")
	public String processView(@ModelAttribute(LICENSE_MANAGEMENT_FORM_NAME) LicenseManagementForm licenseManagementForm, Model model, RedirectAttributes redirectAttr, @RequestParam("status") Integer status) {
		// checkChangeProject(false);
		LicenseManagement licenseManagement = null;
		try {
			licenseManagement = licenseManagementService.findLicenseManagementById(licenseManagementForm.getLicenseId());
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return REDIRECT_SEARCH;
		}

		licenseManagementForm = beanMapper.map(licenseManagement, LicenseManagementForm.class);
		model.addAttribute(LICENSE_MANAGEMENT_FORM_NAME, licenseManagementForm);

		return VIEW_FORM_PATH;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute LicenseManagementForm licenseManagementForm, Model model, RedirectAttributes redirectAttr) {
		LicenseManagement licenseManagement = beanMapper.map(licenseManagementForm, LicenseManagement.class);
		try {
			licenseManagementService.delete(licenseManagement);
		} catch (BusinessException be) {
			if (CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("message", be.getResultMessages());
				model.addAttribute("notExistFlg", 1);
				return VIEW_FORM_PATH;
			}
			if (CommonMessageConst.ERR_SYS_0097.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("message", be.getResultMessages());
				model.addAttribute("licenseManagementForm", licenseManagementForm);
				model.addAttribute("notExistFlg", 0);
				return VIEW_FORM_PATH;
			}

		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(LicenseManagementMessageConst.SC_LICENSEMANAGEMENT_0011)));
		return REDIRECT_DELETION_SUCCESS;
	}
}