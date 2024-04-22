package org.terasoluna.qp.app.generatemanagement;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.GenerateManagementMessageConst;
import org.terasoluna.qp.domain.model.GenerateHistory;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.service.generatemanagement.GenerateManagementService;

@Controller
@RequestMapping(value = "generatemanagement")
@TransactionTokenCheck(value = "generatemanagement")
@SessionAttributes(types = { GenerateManagementForm.class })
public class GenerateManagementController extends BaseController {

	private static final String SEARCH_FORM_PATH = "generatemanagement/searchForm";

	private static final String GENERATE_MANAGEMENT_FORM_NAME = "generateManagementForm";

	private static final String SEARCH_REDIRECT_PATH = "redirect:/generatemanagement/generatemanagement";

	private static final String SEARCH_ACTION = "/generatemanagement/generatemanagement";

	@Inject
	Mapper beanMapper;

	@Inject
	GenerateManagementService generateManagementService;

	@ModelAttribute
	public GenerateManagementForm setUpForm() {
		GenerateManagementForm form = new GenerateManagementForm();
		return form;
	}

	@InitBinder
	public void init() {
		moduleCode = CommonMessageConst.TQP_GENERATEMANAGEMENT;
	}
	
	/**
	 * Initialize search library management
	 * 
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "generatemanagement", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute GenerateManagementForm generateManagementForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		Project project = SessionUtils.getCurrentProject();
		if (init != null) {
			sessionStatus.setComplete();
			generateManagementForm = setUpForm();
			generateManagementForm.setLanguageId(SessionUtils.getCurrentLanguageId());
			generateManagementForm.setLanguageIdAutocomplete(SessionUtils.getCurrentLanguageDesign().getLanguageName());
			model.addAttribute(GENERATE_MANAGEMENT_FORM_NAME, generateManagementForm);
			super.setOldProjectId(project.getProjectId());
			
		}
		checkChangeProject(false);
		if (generateManagementForm.getGenerateMode() == null) {
			generateManagementForm.setGenerateMode(Integer.parseInt(DbDomainConst.GenerateHistoryMode.DOCUMENT));
		}
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION), pageable.getSort());
		generateManagementForm.setProjectId(project.getProjectId());
		Page<GenerateHistory> page = generateManagementService.findPageByCriteria(pageable, project.getProjectId());
		// Project project = generateManagementService.getProjectInformation(SessionUtils.getCurrentProjectId());
		model.addAttribute("page", page);
		model.addAttribute("project", project);
		return SEARCH_FORM_PATH;
	}

	@RequestMapping(value = "generatemanagement", method = RequestMethod.POST)
	public String processSearch(@ModelAttribute GenerateManagementForm generateManagementForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		checkChangeProject(false);
		if (generateManagementForm.getGenerateMode() == null) {
			generateManagementForm.setGenerateMode(Integer.parseInt(DbDomainConst.GenerateHistoryMode.DOCUMENT));
		}
		
		Project project = SessionUtils.getCurrentProject();
		
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION), pageable.getSort());
		generateManagementForm.setProjectId(project.getProjectId());
		Page<GenerateHistory> page = generateManagementService.findPageByCriteria(pageable, project.getProjectId());
		// Project project = generateManagementService.getProjectInformation(SessionUtils.getCurrentProjectId());
		model.addAttribute("page", page);
		model.addAttribute("project", project);
		return SEARCH_FORM_PATH;
	}

	@RequestMapping(value = "generate", method = RequestMethod.POST)
	public String processGenerate(@ModelAttribute GenerateManagementForm generateManagementForm, Model model, 
			@PageableDefault Pageable pageable, SessionStatus sessionStatus, RedirectAttributes attributes) {

		checkChangeProject(false);

		generateManagementForm.setProjectId(SessionUtils.getCurrentProjectId());
		generateManagementForm.setGenerateBy(SessionUtils.getAccountId());
		GenerateHistory generateHistory = beanMapper.map(generateManagementForm, GenerateHistory.class);
		try {
			generateManagementService.registerGenerateHistory(generateHistory);
			attributes.addFlashAttribute("message", ResultMessages.info().add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0019));
		} catch (BusinessException ex) {
			attributes.addFlashAttribute("message", ex.getResultMessages());
		}
		return SEARCH_REDIRECT_PATH;

	}
	
	@RequestMapping(value = "regenerate", method = RequestMethod.GET)
	public String processRegenerate(@ModelAttribute GenerateManagementForm generateManagementForm, Model model, 
			@PageableDefault Pageable pageable, SessionStatus sessionStatus, RedirectAttributes attributes) {

		checkChangeProject(false);

		generateManagementForm.setProjectId(SessionUtils.getCurrentProjectId());
		generateManagementForm.setGenerateBy(SessionUtils.getAccountId());
		GenerateHistory generateHistory = beanMapper.map(generateManagementForm, GenerateHistory.class);
		try {
			generateManagementService.reGenerateHistory(generateHistory);
			attributes.addFlashAttribute("message", ResultMessages.info().add(GenerateManagementMessageConst.SC_GENERATEMANAGEMENT_0019));
		} catch (BusinessException ex) {
			attributes.addFlashAttribute("message", ex.getResultMessages());
		}
		return SEARCH_REDIRECT_PATH;

	}

}
