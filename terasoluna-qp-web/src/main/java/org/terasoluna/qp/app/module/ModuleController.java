/*
 * @(#)ModuleController.java
 *
 * Copyright (c) 2015 NTTDATA Vietnam.
 */
package org.terasoluna.qp.app.module;

import java.util.List;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.apache.commons.collections.CollectionUtils;
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
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.PermissionUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ModuleMessageConst;
import org.terasoluna.qp.app.module.ModuleForm.RegistrationForm;
import org.terasoluna.qp.domain.model.BusinessType;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.repository.module.ModuleCriteria;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignService;
import org.terasoluna.qp.domain.service.businesstype.BusinessTypeService;
import org.terasoluna.qp.domain.service.functiondesign.FunctionDesignService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignConst;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;

@Controller
@RequestMapping(value = "module")
@TransactionTokenCheck(value = "module")
@SessionAttributes(types = { ModuleSearchForm.class })
public class ModuleController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);
	private static final String MODULE_FORM_NAME = "moduleForm";
	private static final String MODULE_SEARCH_FORM_NAME = "moduleSearchForm";
	private static final String SEARCH_FORM_PATH = "module/searchForm";
	private static final String SEARCH_ACTION_PATH = "/module/search";
	private static final String REGISTER_FORM_PATH = "module/registerForm";
	private static final String VIEW_FORM_PATH = "module/viewForm";
	private static final String FORM_VIEW_DELETE_AFFECTION = "module/viewFormDeleteAffection";
	private static final String MODIFY_FORM_PATH = "module/modifyForm";
	//private static final String REDIRECT_DELETION_SUCCESS = "redirect:/complete";
	private static final String SEARCH_REDIRECT_PATH = "redirect:/module/search";
	// private static final String REGISTER_REDIRECT_PATH = "redirect:/module/register";
	//private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";

	@Inject
	ModuleValidator moduleValidator;

	@Inject
	ProjectService projectService;

	@Inject
	ModuleService moduleService;

	@Inject
	BusinessTypeService businessTypeService;

	@Inject
	Mapper beanMapper;

	@Inject
	BusinessDesignService businessDesignService;

	@Inject
	ScreenDesignService screenDesignService;

	@Inject
	FunctionDesignService functionDesignService;

	@ModelAttribute
	public void initService() {
//		moduleService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
//		moduleService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
//		moduleService.setAccountId(SessionUtils.getAccountId());

//		screenDesignService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
//		screenDesignService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
		
//		businessDesignService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
//		businessDesignService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
		moduleCode = CommonMessageConst.TQP_MODULE;
	}
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * 
	 * @param webDataBinder
	 *            WebDataBinder
	 */
	@InitBinder(MODULE_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(moduleValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * Identifies methods which initialize the project form object
	 * 
	 * @return projectForm
	 */
	@ModelAttribute
	public ModuleSearchForm setUpModuleSearchForm() {
		ModuleSearchForm moduleSearchForm = new ModuleSearchForm();
		logger.debug("Init form {0}", moduleSearchForm);
		return moduleSearchForm;
	}

	/**
	 * Identifies methods which initialize the project search form object
	 * 
	 * @return projectForm
	 */
	@ModelAttribute
	public ModuleForm setUpModuleForm() {
		ModuleForm moduleForm = new ModuleForm();
		logger.debug("Init form {0}", moduleForm);
		return moduleForm;
	}

	/**
	 * pre-initialization of form backed bean
	 * 
	 * @return
	 */
	@ModelAttribute("businessTypes")
	List<BusinessType> setUpBusinessTypes() {
		List<BusinessType> businessTypes = businessTypeService.findAllBusinessTypeTree(SessionUtils.getCurrentProjectId());
		return businessTypes;
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
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute(MODULE_SEARCH_FORM_NAME) ModuleSearchForm moduleSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if (init != null) {
			sessionStatus.setComplete();
			moduleSearchForm = new ModuleSearchForm();
			model.addAttribute(MODULE_SEARCH_FORM_NAME, moduleSearchForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		moduleSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		ModuleCriteria moduleCriteria = beanMapper.map(moduleSearchForm, ModuleCriteria.class);
		Page<Module> page = moduleService.searchModule(moduleCriteria, pageable);
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
	public String processSearch(@ModelAttribute(MODULE_SEARCH_FORM_NAME) ModuleSearchForm moduleSearchForm, Model model, @PageableDefault Pageable pageable) {
		checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		moduleSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		ModuleCriteria moduleCriteria = beanMapper.map(moduleSearchForm, ModuleCriteria.class);
		Page<Module> page = moduleService.searchModule(moduleCriteria, pageable);
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
	public String displayRegister(@ModelAttribute(MODULE_FORM_NAME) ModuleForm moduleForm, RedirectAttributes redirectAttr, Model model) {
		try {
			checkChangeProject(true);
			ModuleTableMappingForm[] moduleTableMappings = new ModuleTableMappingForm[] { new ModuleTableMappingForm() };
			moduleForm.setModuleTableMappings(moduleTableMappings);
			moduleForm.setProjectId(SessionUtils.getCurrentProjectId());
			// set default
			moduleForm.setCompletionType(ScreenDesignConst.CompleteType.MESSAGE);
			moduleForm.setConfirmationType(ScreenDesignConst.ConfirmType.MESSAGE);

			/* moduleForm.setProjectName(SessionUtils.getCurrentProject().getProjectName()); */
			return REGISTER_FORM_PATH;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}
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
	public String processRegister(@Validated({ Default.class, RegistrationForm.class }) ModuleForm moduleForm, BindingResult result, RedirectAttributes redirectAttr, Model model, SessionStatus sessionStatus) {

		try {
			CommonModel common = this.initCommon();
			checkChangeProject(true);

			if (result.hasErrors()) {
				return REGISTER_FORM_PATH;
			}

			Module module = beanMapper.map(moduleForm, Module.class);
			/*module.setProject(SessionUtils.getCurrentProject());
			module.setProjectId(module.getProject().getProjectId());
			module.setProjectName(module.getProject().getProjectName());*/
			module.setLanguageDesign(SessionUtils.getCurrentLanguageDesign());
			module.setAccountId(SessionUtils.getAccountId());
			module.setCreatedBy(module.getAccountId());
			module.setUpdatedBy(module.getAccountId());

			moduleService.registerModule(module, common);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return REGISTER_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0005)));
		// Modify by HungHX follow ticket #769
		// sessionStatus.setComplete();
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * return view screen
	 * 
	 * @param moduleForm
	 * @param model
	 * @return VIEW_FORM_PATH
	 */
	@RequestMapping(value = "view")
	public String displayView(@ModelAttribute(MODULE_FORM_NAME) ModuleForm moduleForm, Model model, RedirectAttributes redirectAttr) {
		checkChangeProject(false);

		Module module = null;
		Integer openOwner = moduleForm.getOpenOwner();
		try {
			module = moduleService.findModuleById(moduleForm.getModuleId());
			module.setModuleTableMappings(moduleService.findAllTableInModule(moduleForm.getModuleId()));
		} catch (BusinessException be) {
			moduleForm.setHasErrors(true);
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}

		moduleForm = beanMapper.map(module, ModuleForm.class);

		CommonModel commonModel = this.initCommon();
		moduleForm.setListBusinessDesign(businessDesignService.findBlogicByModuleId(moduleForm.getModuleId(), commonModel));
		moduleForm.setListScreenDesign(screenDesignService.getAllScreenInfoByModuleId(moduleForm.getModuleId(), commonModel.getWorkingLanguageId()));
		moduleForm.setListFunctionDesign(functionDesignService.findAllFunctionDesignByModuleId(moduleForm.getModuleId()));

		moduleForm.setOpenOwner(openOwner);
		model.addAttribute(MODULE_FORM_NAME, moduleForm);
		if (DbDomainConst.YesNoFlg.YES.equals(openOwner)) {
			model.addAttribute("checkDesignStatus", moduleService.validateChangeStatusToFixed(moduleForm.getModuleId()));
		}

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
	public String displayModify(@ModelAttribute(MODULE_FORM_NAME) ModuleForm moduleForm, Model model, RedirectAttributes redirectAttr) {

		String destination = StringUtils.EMPTY;
		// Module module = beanMapper.map(moduleForm, Module.class);
		try {
			checkChangeProject(true);

			CommonModel common = this.initCommon();
			common.setDesignStatus(true);
			Module module = moduleService.validateModule(moduleForm.getModuleId(), common);
			module.setModuleTableMappings(moduleService.findAllTableInModule(module.getModuleId()));

			moduleForm = beanMapper.map(module, ModuleForm.class);

			moduleForm.setListBusinessDesign(businessDesignService.findBlogicByModuleId(moduleForm.getModuleId(), common));
			moduleForm.setListScreenDesign(screenDesignService.getAllScreenInfoByModuleId(moduleForm.getModuleId(), common.getWorkingLanguageId()));
			moduleForm.setListFunctionDesign(functionDesignService.findAllFunctionDesignByModuleId(moduleForm.getModuleId()));

			model.addAttribute("moduleForm", moduleForm);
			destination = MODIFY_FORM_PATH;
		} catch (BusinessException be) {
			/*if (MODE_SEARCH.equals(moduleForm.getMode())) {
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(moduleForm.getMode())) {
				model.addAttribute("notExistFlg", 1);
				model.addAttribute("message", be.getResultMessages());
				destination = VIEW_FORM_PATH;
			}*/

			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_MODULE)); // message title
			if (MODE_VIEW.equals(moduleForm.getMode())) {
				redirectAttr.addFlashAttribute("message", be.getResultMessages());// message content
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			}

			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;

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
	public String processModify(@Validated({ Default.class }) ModuleForm moduleForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		CommonModel common = this.initCommon();

		if (result.hasErrors()) {
			moduleForm.setListFunctionDesign(functionDesignService.findAllFunctionDesignByModuleId(moduleForm.getModuleId()));
			moduleForm.setListBusinessDesign(businessDesignService.findBlogicByModuleId(moduleForm.getModuleId(), common));
			moduleForm.setListScreenDesign(screenDesignService.getAllScreenInfoByModuleId(moduleForm.getModuleId(), common.getWorkingLanguageId()));

			return MODIFY_FORM_PATH;
		}

		Module module = beanMapper.map(moduleForm, Module.class);
		try {
			checkChangeProject(true);
			moduleService.modifyModule(module, common);
		} catch (BusinessException be) {
			/*if (CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", 1);
			}*/
			
			moduleForm.setListFunctionDesign(functionDesignService.findAllFunctionDesignByModuleId(moduleForm.getModuleId()));
			moduleForm.setListBusinessDesign(businessDesignService.findBlogicByModuleId(moduleForm.getModuleId(), common));
			moduleForm.setListScreenDesign(screenDesignService.getAllScreenInfoByModuleId(moduleForm.getModuleId(), common.getWorkingLanguageId()));
			
			model.addAttribute("message", be.getResultMessages());
			return MODIFY_FORM_PATH;
		}

		redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_MODULE)); // message title
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0005)));
		return SEARCH_REDIRECT_PATH;
	}
	
	@RequestMapping(value = "deleteConfirm")
	public String processDeleteConfirm(ModuleForm moduleForm, RedirectAttributes redirectAttr, Model model) {
		String destination = "";
		try {
			checkChangeProject(true);
			Module module = beanMapper.map(moduleForm, Module.class);
			module.setAccountId(SessionUtils.getAccountId());
			module.setCreatedBy(module.getAccountId());
			module.setUpdatedBy(module.getAccountId());
			CommonModel common = this.initCommon();
			if (moduleForm.getActionDelete()) {

				module = moduleService.findModuleById(moduleForm.getModuleId());
				module.setModuleTableMappings(moduleService.findAllTableInModule(moduleForm.getModuleId()));
				moduleService.findAllDeletionAffection(module, common);
				if (CollectionUtils.isEmpty(module.getAffectedScreenDesigns()) && CollectionUtils.isEmpty(module.getAffectedTableDesigns()) 
						&& CollectionUtils.isEmpty(module.getAffectedDomainDesigns()) && CollectionUtils.isEmpty(module.getAffectedBusinessDesigns()) 
						&& CollectionUtils.isEmpty(module.getAffectedSqlDesigns())) {
					destination = processDelete(moduleForm, redirectAttr, model);
				} else {
					model.addAttribute("affectedScreenDesigns", module.getAffectedScreenDesigns());
					model.addAttribute("affectedTableDesigns", module.getAffectedTableDesigns());
					model.addAttribute("affectedDomainDesigns", module.getAffectedDomainDesigns());
					model.addAttribute("affectedBusinessDesigns", module.getAffectedBusinessDesigns());
					model.addAttribute("affectedSqlDesigns", module.getAffectedSqlDesigns());
					moduleForm = beanMapper.map(module, ModuleForm.class);
					model.addAttribute(MODULE_FORM_NAME, moduleForm);
					destination = FORM_VIEW_DELETE_AFFECTION;
				}
			} else {
				moduleService.modifyDesignStatus(module, common);
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0005)));
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_MODULE));
				return DbDomainConst.REDIRECT_MODIFY_SUCCESS;
			}
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_MODULE)); // message
			redirectAttr.addFlashAttribute("message", be.getResultMessages());// message content
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
		}
		return destination;
	}
	
	/**
	 * Delete a Module
	 * 
	 * @param moduleForm
	 * @param modelR
	 * @return
	 */
	@RequestMapping(value = "delete")
	public String processDelete(ModuleForm moduleForm, RedirectAttributes redirectAttr, Model model) {

		try {
			checkChangeProject(true);
			Module module = beanMapper.map(moduleForm, Module.class);
			module.setAccountId(SessionUtils.getAccountId());
			module.setCreatedBy(module.getAccountId());
			module.setUpdatedBy(module.getAccountId());
			CommonModel common = this.initCommon();

			if (PermissionUtils.hasRole(ModuleMessageConst.PM_SPECIAL_DELETE)) {
				moduleService.deleteAssociatedModule(module, common);
			} else {
				moduleService.deleteModule(module, common);
			}
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_MODULE)); // message
			redirectAttr.addFlashAttribute("message", be.getResultMessages());// message content
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
		}
		redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_MODULE)); // message
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0005)));
		return DbDomainConst.REDIRECT_DELETION_SUCCESS;
	}

}