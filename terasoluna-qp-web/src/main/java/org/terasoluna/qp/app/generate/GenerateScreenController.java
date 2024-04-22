package org.terasoluna.qp.app.generate;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ModuleMessageConst;
import org.terasoluna.qp.app.module.ModuleTableMappingForm;
import org.terasoluna.qp.app.screendesign.ScreenRegisterForm.RegistrationScreenDesignForm;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.service.generatescreen.ScreenDesignGeneratorService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Controller
@RequestMapping(value = "generation")
@TransactionTokenCheck(value = "generationScreenAndBlogic")
public class GenerateScreenController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(GenerateScreenController.class);
	private static final String GENERATE_SCREEN_FORM_NAME = "generateScreenForm";
	private static final String GENERATE_SCREEN_FORM_PATH = "generate/generateScreen";
	private static final String SEARCH_REDIRECT_PATH = "redirect:/module/search";

	@Inject
	GenerateScreenValidator generateScreenValidator;
	
	@Inject
	ProjectService projectService;
	
	@Inject
	Mapper beanMapper;
	
	@Inject
	ModuleService moduleService;
	
	@Inject
	ScreenDesignGeneratorService screenDesignGeneratorService;
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * @param webDataBinder WebDataBinder
	 */
	@InitBinder(GENERATE_SCREEN_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(generateScreenValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@InitBinder
	public void init() {
//		projectService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
//		projectService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
//		projectService.setAccountId(SessionUtils.getCurrentAccount().getAccountId());
//		
//		moduleService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
//		moduleService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
//		moduleService.setAccountId(SessionUtils.getCurrentAccount().getAccountId());

		moduleCode = CommonMessageConst.TQP_GENERATESCREEN;
	}
	
	/**
	 * Identifies methods which initialize the generate form object
	 * @return generateScreenForm
	 */
	@ModelAttribute
	public GenerateScreenForm setUpProjectForm() {
		GenerateScreenForm generateScreenForm = new GenerateScreenForm();
		logger.debug("Init form {0}", generateScreenForm);
		return generateScreenForm;
	}
	
	/**
	 * Initialize generate screen
	 * @return GENERATE_SCREEN_FORM_PATH
	 */
	@TransactionTokenCheck(value = "generationScreenAndBlogic", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "generatescreen", method = RequestMethod.GET)
	public String displayGenerate(@RequestParam(value="init",required=false) String init, GenerateScreenForm generateScreenForm, Model model, RedirectAttributes redirectAttr, SessionStatus sessionStatus) {
		if (init != null) {
			Long moduleId = generateScreenForm.getModuleId();
			sessionStatus.setComplete();
			generateScreenForm = new GenerateScreenForm();
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
			generateScreenForm.setModuleId(moduleId);
		}
		
		CommonModel common = this.initCommon();
		Module module = null;

		try {
			checkChangeProject(true);
			module = moduleService.validateModule(generateScreenForm.getModuleId(), common);
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}

		generateScreenForm = beanMapper.map(module, GenerateScreenForm.class);
		ModuleTableMappingForm[] moduleTableMappings = new ModuleTableMappingForm[] { new ModuleTableMappingForm() };
		generateScreenForm.setModuleTableMappings(moduleTableMappings);

		model.addAttribute(GENERATE_SCREEN_FORM_NAME, generateScreenForm);
		return GENERATE_SCREEN_FORM_PATH;
	}
	
	/**
	 * Initialize generate screen
	 * @return GENERATE_FORM_PATH
	 */
	@TransactionTokenCheck(value = "generationScreenAndBlogic", type = TransactionTokenType.IN)
	@RequestMapping(value = "generatescreen", method = RequestMethod.POST)
	public String processGenerate(@Validated({ Default.class, RegistrationScreenDesignForm.class }) GenerateScreenForm generateScreenForm, 
			BindingResult result, Model model, RedirectAttributes redirectAttr, SessionStatus sessionStatus) {

		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result, model);
			return GENERATE_SCREEN_FORM_PATH;
		}

		try {
			CommonModel common = this.initCommon();
			common.setDesignStatus(Boolean.TRUE);
			moduleService.validateModule(generateScreenForm.getModuleId(), common);

			Module generateScreen = beanMapper.map(generateScreenForm, Module.class);

			Project project = SessionUtils.getCurrentProject();

			generateScreen.setModuleName(generateScreenForm.getBusinessGenerateName());
			generateScreen.setModuleCode(generateScreenForm.getBusinessGenerateCode());
			generateScreen.setProjectId(project.getProjectId());
			generateScreen.setLanguageDesign(SessionUtils.getCurrentLanguageDesign());
			generateScreen.setProject(project);

			screenDesignGeneratorService.generateSceenAndBlogic(generateScreen, common);

		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return GENERATE_SCREEN_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0009, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0035)));
		return SEARCH_REDIRECT_PATH;
	}
}
