package org.terasoluna.qp.app.project;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.PermissionUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.FunctionMasterMessageConst;
import org.terasoluna.qp.app.message.ProjectMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.DataFormat;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.repository.project.ProjectCriteria;
import org.terasoluna.qp.domain.service.accountprofile.AccountProfileService;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Controller
@RequestMapping(value = "project")
@TransactionTokenCheck(value = "project")
@SessionAttributes(types = { ProjectSearchForm.class })
public class ProjectController {
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	private static final String PROJECT_FORM_NAME = "projectForm";
	private static final String PROJECT_SEARCH_FORM_NAME = "projectSearchForm";
	private static final String SEARCH_FORM_PATH = "project/searchForm";
	private static final String SEARCH_ACTION_PATH = "/project/search";
	private static final String REGISTER_FORM_PATH = "project/registerForm";
	private static final String VIEW_FORM_PATH = "project/viewForm";
	private static final String MODIFY_FORM_PATH = "project/modifyForm";

	private static final String SEARCH_REDIRECT_PATH = "redirect:/project/search";
	private static final String CONFIRM_PATH = "project/viewListAffectedChangeDesignForm";
	private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";

	@Inject
	ProjectService projectService;
	
	@Inject
	SystemService systemService;

	@Inject
	LanguageDesignService languageDesignService;
	
	@Inject
	AccountProfileService accountProfileService;
	
	@Inject
	Mapper beanMapper;
	
	@Inject
	@Named("CL_POSTGRESQL_CONFIG")
	private CodeList CL_POSTGRESQL_CONFIG;
	
	@Inject
	@Named("CL_ORACLE_CONFIG")
	private CodeList CL_ORACLE_CONFIG;

	@Inject
	ProjectValidator projectValidator;
	
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * 
	 * @param webDataBinder
	 *            WebDataBinder
	 */
	@InitBinder(PROJECT_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		webDataBinder.addValidators(projectValidator);
	}

	/**
	 * Identifies methods which initialize the project form object
	 * 
	 * @return projectForm
	 */
	@ModelAttribute
	public ProjectForm setUpProjectForm() {
		ProjectForm projectForm = new ProjectForm();
		logger.debug("Init form {0}", projectForm);

		return projectForm;
	}

	/**
	 * Identifies methods which initialize the search form object
	 * 
	 * @return projectSearchForm
	 */
	@ModelAttribute
	public ProjectSearchForm setUpProjectSearchForm() {
		ProjectSearchForm projectSearchForm = new ProjectSearchForm();
		logger.debug("Init form {0}", projectSearchForm);

		return projectSearchForm;
	}

	/**
	 * Initialize search project screen
	 * 
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute ProjectSearchForm projectSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if (init != null) {
			sessionStatus.setComplete();
			projectSearchForm = new ProjectSearchForm();
			model.addAttribute(PROJECT_SEARCH_FORM_NAME, projectSearchForm);
		}
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		ProjectCriteria projectCriteria = beanMapper.map(projectSearchForm, ProjectCriteria.class);

		projectCriteria.setAccountId(SessionUtils.getAccountId());
		Page<Project> page = projectService.searchProject(projectCriteria, pageable);
		model.addAttribute("page", page);

		return SEARCH_FORM_PATH;
	}

	/**
	 * Search project process
	 * 
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = { RequestMethod.POST })
	public String processSearch(@ModelAttribute(PROJECT_SEARCH_FORM_NAME) ProjectSearchForm projectSearchForm, @PageableDefault Pageable pageable, Model model) {
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());

		ProjectCriteria projectCriteria = beanMapper.map(projectSearchForm, ProjectCriteria.class);
		projectCriteria.setAccountId(SessionUtils.getAccountId());
		Page<Project> page = projectService.searchProject(projectCriteria, pageable);
		model.addAttribute("page", page);

		return SEARCH_FORM_PATH;
	}

	/**
	 * Initialize register project screen
	 * 
	 * @return REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(ProjectForm projectForm, Model model) {
		projectForm.setWebserviceFlg(false);
		
		projectForm.setDataFormat(prepareDataFormatForProject());
		if (!model.containsAttribute("message")) {
			projectForm.setDbHostName(CL_POSTGRESQL_CONFIG.asMap().get("dbHostName"));
			projectForm.setDbPort(CL_POSTGRESQL_CONFIG.asMap().get("dbPort"));
			projectForm.setDbUser(CL_POSTGRESQL_CONFIG.asMap().get("dbUser"));
			projectForm.setDbPassword(CL_POSTGRESQL_CONFIG.asMap().get("dbPassword"));
			projectForm.setDbDriver(CL_POSTGRESQL_CONFIG.asMap().get("dbDriver"));
			projectForm.setWebservicePattern("api");
		}
		
		return REGISTER_FORM_PATH;
	}

	private DataFormat prepareDataFormatForProject() {
		//Get account profile
		AccountProfile accountProfile = (AccountProfile) SessionUtils.get(SessionUtils.ACCOUNT_PROFILE);
		if (accountProfile == null) {
			accountProfile = accountProfileService.getAccountProfile(SessionUtils.getAccountId());
		}

		AccountProfile accountProfileDefault = systemService.getDefaultProfile();

		// Mapping data format 
		DataFormat dataFormat = new DataFormat();
		dataFormat.setIntegerFormat(StringUtils.isBlank(accountProfile.getIntegerFormat())? accountProfileDefault.getIntegerFormat(): accountProfile.getIntegerFormat());
		dataFormat.setDecimalFormat(StringUtils.isBlank(accountProfile.getFloatFormat())? accountProfileDefault.getFloatFormat(): accountProfile.getFloatFormat());
		dataFormat.setDateFormat(StringUtils.isBlank(accountProfile.getDateFormat())? accountProfileDefault.getDateFormat(): accountProfile.getDateFormat());
		dataFormat.setDateTimeFormat(StringUtils.isBlank(accountProfile.getDateTimeFormat())? accountProfileDefault.getDateTimeFormat(): accountProfile.getDateTimeFormat());
		dataFormat.setTimeFormat(StringUtils.isBlank(accountProfile.getTimeFormat())? accountProfileDefault.getTimeFormat(): accountProfile.getTimeFormat());
		dataFormat.setCurrencyFormat(StringUtils.isBlank(accountProfile.getCurrencyFormat())? accountProfileDefault.getCurrencyFormat(): accountProfile.getCurrencyFormat());
		dataFormat.setCurrencyCode(StringUtils.isBlank(accountProfile.getCurrencyCode())? accountProfileDefault.getCurrencyCode(): accountProfile.getCurrencyCode());
		dataFormat.setCurrencyCodePosition(StringUtils.isBlank(accountProfile.getCurrencyCodePosition())? accountProfileDefault.getCurrencyCodePosition(): accountProfile.getCurrencyCodePosition());

		return dataFormat;
	}
	
	
	/**
	 * Register project process
	 * 
	 * @param projectForm
	 *            Project information
	 * @param result
	 *            BindingResult
	 * @param redirectAttr
	 *            RedirectAttributes
	 * @param model
	 *            Model
	 * @return in case of success SEARCH_REDIRECT_PATH will be returned. Otherwise it will be returned to REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated ProjectForm projectForm, BindingResult result, RedirectAttributes redirectAttr, Model model, SessionStatus sessionStatus) {
		if (result.hasErrors()) {
			return REGISTER_FORM_PATH;
		}

		Project project = beanMapper.map(projectForm, Project.class);

		Long accountId = SessionUtils.getAccountId();
		project.setCreatedBy(accountId);
		project.setUpdatedBy(accountId);

		try {
			refreshCurrentProject(projectService.registerProject(project), DbDomainConst.YesNoFlg.NO);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return REGISTER_FORM_PATH;
		}

		
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0010)));
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * View project process
	 * 
	 * @param projectForm
	 *            ProjectForm
	 * @param redirectAttr
	 *            RedirectAttributes
	 * @param model
	 *            Model
	 * @return VIEW_FORM_PATH
	 */
	@RequestMapping(value = "view")
	public String displayView(ProjectForm projectForm, Model model, RedirectAttributes redirectAttr) {
		Project project = beanMapper.map(projectForm, Project.class);
		String destination = "";
		Integer openOwner = projectForm.getOpenOwner();

		try {
			project = projectService.getProjectInformation(project.getProjectId(), SessionUtils.getAccountId());
			// Finds all related module information to project
			project.setModules(projectService.findAllModuleOfProject(projectForm.getProjectId()));
			projectForm = beanMapper.map(project, ProjectForm.class);
			projectForm.setOpenOwner(openOwner);
			if (StringUtils.isNoneBlank(projectForm.getDbPassword()))
				projectForm.setDbPassword("******");
			model.addAttribute(PROJECT_FORM_NAME, projectForm);
			if (DbDomainConst.YesNoFlg.YES.equals(openOwner)) {
				model.addAttribute("checkDesignStatus", projectService.validateChangeStatusToFixed(project.getProjectId()));
			} else {
				model.addAttribute("checkDesignStatus", DbDomainConst.YesNoFlg.YES);
			}
			destination = VIEW_FORM_PATH;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			destination = SEARCH_REDIRECT_PATH;
		}

		return destination;
	}

	/**
	 * Initialize modify project screen
	 * 
	 * @param projectForm
	 *            ProjectForm
	 * @param redirectAttr
	 *            RedirectAttributes
	 * @param model
	 *            Model
	 * @return MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(ProjectForm projectForm, Model model, RedirectAttributes redirectAttr) {
		String destination = SEARCH_REDIRECT_PATH;
		Project project = beanMapper.map(projectForm, Project.class);

		try {
			project = projectService.getProjectInformation(project.getProjectId(), SessionUtils.getAccountId());

			if (DbDomainConst.DesignStatus.FIXED.equals(project.getStatus())) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0111, StringUtils.lowerCase(MessageUtils.getMessage(CommonMessageConst.SC_TQP_0011)), project.getProjectName()));
			}

			projectForm = beanMapper.map(project, ProjectForm.class);
			projectForm.setDbTypeOld(project.getDbType());
			// Finds all related module information to project
			projectForm.setModules(projectService.findAllModuleOfProject(project.getProjectId()));

			model.addAttribute(PROJECT_FORM_NAME, projectForm);
			destination = MODIFY_FORM_PATH;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			if (MODE_SEARCH.equals(projectForm.getMode())) {
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(projectForm.getMode())) {
				model.addAttribute("notExistFlg", 1);
				model.addAttribute("message", be.getResultMessages());
				destination = VIEW_FORM_PATH;
			}
		}
		return destination;
	}

	/**
	 * Modify project process
	 * 
	 * @param projectForm
	 *            ProjectForm
	 * @param redirectAttr
	 *            RedirectAttributes
	 * @param model
	 *            Model
	 * @return SEARCH_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modifyConfirm", method = RequestMethod.POST)
	public String processModifyConfirm(@Validated ProjectForm projectForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {

		if (result.hasErrors()) {
			projectForm.setModules(projectService.findAllModuleOfProject(projectForm.getProjectId()));
			return MODIFY_FORM_PATH;
		}
		String destination = "";
		Project project = beanMapper.map(projectForm, Project.class);
		
		try {
			boolean hasProblem = false;
			//if change datatype and change from PostgresSQL to oracle
			if (!DataTypeUtils.equals(projectForm.getDbType(), projectForm.getDbTypeOld())) {
				hasProblem = projectService.checkListAffected(project, false, systemService.getDefaultProfile().getMaxLengthOracleDBMS().intValue());
			}

			Long accountId = SessionUtils.getAccountId();
			project.setCreatedBy(accountId);
			project.setUpdatedBy(accountId);
			
			if (hasProblem) {
				projectForm.setModules(project.getModules());
				projectForm.setListOfTableDesign(project.getListOfTableDesign());
				projectForm.setListOfSqlDesign(project.getListOfSqlDesign());
				projectForm.setListOfAutocompleteDesign(project.getListOfAutocompleteDesign());
				model.addAttribute("formJson", DataTypeUtils.toJson(projectForm));
				destination = CONFIRM_PATH;
			} else {
				projectService.modifyProject(project);
				refreshCurrentProject(project, DbDomainConst.YesNoFlg.YES);
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0010)));
				destination = SEARCH_REDIRECT_PATH;
			}
		} catch (BusinessException be) {
			if (CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", 1);
			} else {
				projectForm.setModules(projectService.findAllModuleOfProject(project.getProjectId()));
			}
			model.addAttribute("message", be.getResultMessages());
			destination = MODIFY_FORM_PATH;
		}
		return destination;
	}

	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST, params = "jsonBack")
	public String displayModifyBack(RedirectAttributes redirectAttr, Model model, @RequestParam("formJson") String formJson) {
		ProjectForm projectForm = DataTypeUtils.toObject(formJson, ProjectForm.class);
		model.addAttribute(PROJECT_FORM_NAME, projectForm);
		return MODIFY_FORM_PATH;
	}

	@RequestMapping(value = "modify", method = RequestMethod.POST, params = "isJsonForm")
	public String processModify(ProjectForm projectForm, BindingResult result, Model model, RedirectAttributes redirectAttr, @Param("formJson") String formJson) {
		projectForm = DataTypeUtils.toObject(formJson, ProjectForm.class);
		Project project = beanMapper.map(projectForm, Project.class);
		try {
			Long accountId = SessionUtils.getAccountId();
			project.setCreatedBy(accountId);
			project.setUpdatedBy(accountId);
			
			projectService.checkListAffected(project, true, systemService.getDefaultProfile().getMaxLengthOracleDBMS().intValue());
			projectService.modifyProject(project);
			refreshCurrentProject(project, DbDomainConst.YesNoFlg.YES);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return MODIFY_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0010)));
		SessionUtils.remove("functionMasterModify");
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * Delete project process In case project is in-used Delete project process will not be success and forward to view including error message
	 * 
	 * @param projectForm
	 *            ProjectForm
	 * @param redirectAttr
	 *            RedirectAttributes
	 * @param model
	 *            Model
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(ProjectForm projectForm, Model model, RedirectAttributes redirectAttr) {
		Project project = beanMapper.map(projectForm, Project.class);

		Long accountId = SessionUtils.getAccountId();
		project.setCreatedBy(accountId);
		project.setUpdatedBy(accountId);

		if (projectForm.getActionDelete()) {
			try {
				if (PermissionUtils.hasRole(ProjectMessageConst.PM_SPECIAL_DELETE)) {
					projectService.deleteAssociatedProject(project);
				} else {
					projectService.deleteProject(project);
				}
				removeCurrentProject(project.getProjectId());
			} catch (BusinessException ex) {
				// if not exist or fixed design status
				/*String errMessageCode = StringUtils.defaultString(ex.getResultMessages().getList().get(0).getCode(), CommonMessageConst.ERR_SYS_0037);
				if (CommonMessageConst.ERR_SYS_0037.equals(errMessageCode) || CommonMessageConst.ERR_SYS_0111.equals(errMessageCode)) {
					model.addAttribute("notExistFlg", 1);
				} else {
					project = projectService.getProjectInformation(projectForm.getProjectId(), project.getUpdatedBy());
					projectForm = beanMapper.map(project, ProjectForm.class);
					projectForm.setModules(projectService.findAllModuleOfProject(project.getProjectId()));
					projectForm.setOpenOwner(DbDomainConst.YesNoFlg.YES);
					model.addAttribute(PROJECT_FORM_NAME, projectForm);
				}
				model.addAttribute("message", ex.getResultMessages());
				return VIEW_FORM_PATH;*/
				
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_PROJECT));
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
				
			}
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_PROJECT));
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0010)));
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
		} else {
			try {
				project = projectService.modifyDesignStatus(project);
				refreshCurrentProject(project, DbDomainConst.YesNoFlg.YES);
			} catch (BusinessException ex) {
				/*String errMessageCode = StringUtils.defaultString(ex.getResultMessages().getList().get(0).getCode(), CommonMessageConst.ERR_SYS_0037);
				if (CommonMessageConst.ERR_SYS_0037.equals(errMessageCode) || CommonMessageConst.ERR_SYS_0111.equals(errMessageCode)) {
					model.addAttribute("notExistFlg", 1);
				} else {
					project = projectService.getProjectInformation(projectForm.getProjectId(), project.getUpdatedBy());
					projectForm = beanMapper.map(project, ProjectForm.class);
					projectForm.setModules(projectService.findAllModuleOfProject(project.getProjectId()));
					projectForm.setOpenOwner(DbDomainConst.YesNoFlg.YES);
					model.addAttribute(PROJECT_FORM_NAME, projectForm);
				}

				model.addAttribute("message", ex.getResultMessages());
				return VIEW_FORM_PATH;*/
				
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_PROJECT));
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
				
			}
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(ProjectMessageConst.SC_PROJECT_0010)));
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_PROJECT));
			return DbDomainConst.REDIRECT_MODIFY_SUCCESS;
		}
	}

	private void refreshCurrentProject(Project project, Integer checkCurrentFlg) {
		if(project != null){
			if (DataTypeUtils.equals(DbDomainConst.DatabaseType.ORACLE, project.getDbType())) {
				project.setCodeMaxVal(systemService.getDefaultProfile().getMaxLengthOracleDBMS());
			} else if (DataTypeUtils.equals(DbDomainConst.DatabaseType.PostgreSQL, project.getDbType())) {
				project.setCodeMaxVal(systemService.getDefaultProfile().getMaxLengthPostgreDBMS());
			}
		}
		if (DbDomainConst.YesNoFlg.NO.equals(checkCurrentFlg) || DataTypeUtils.equals(SessionUtils.getCurrentProjectId(), project.getProjectId())) {
			SessionUtils.set(SessionUtils.CURRENT_PROJECT, project);
			AccountProfile accountProfile = (AccountProfile) SessionUtils.get(SessionUtils.ACCOUNT_PROFILE);

			if (accountProfile != null) {
				accountProfile.setCurrentProjectId(project.getProjectId());
				SessionUtils.set(SessionUtils.ACCOUNT_PROFILE, accountProfile);
				LanguageDesign languageDesign = languageDesignService.getLanguageDesignById(project.getDefaultLanguageId(), project.getProjectId());
				SessionUtils.set(SessionUtils.CURRENT_LANGUAGE_DESIGN, languageDesign);
			}
		}
	}

	private void removeCurrentProject(Long projectId) {
		if (DataTypeUtils.equals(SessionUtils.getCurrentProjectId(), projectId)) {
			SessionUtils.set(SessionUtils.CURRENT_PROJECT, null);
			SessionUtils.set(SessionUtils.CURRENT_LANGUAGE_DESIGN, null);
		}
	}
}
