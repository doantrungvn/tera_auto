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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.qp.app.module.ModuleForm.RegistrationForm;
import org.terasoluna.qp.domain.service.generatescreen.ScreenDesignGeneratorService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;

@Controller
@RequestMapping(value = "generation")
@TransactionTokenCheck(value = "generation")
public class DependencyModuleController {
	private static final Logger logger = LoggerFactory.getLogger(DependencyModuleController.class);
	private static final String MODULE_DEPENDENCY_FORM_NAME = "dependencyModuleForm";
	/*private static final String MODULE_DEPENDENCY_FORM_PATH = "generate/dependencyModule";*/
	private static final String REDIRECT_MODULE_DEPENDENCY_FORM_PATH = "redirect:/generation/dependencymodule";

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
	@InitBinder(MODULE_DEPENDENCY_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
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
	 *//*
	@RequestMapping(value = "dependencymodule", method = RequestMethod.GET)
	public String displayGenerate(@RequestParam(value="init",required=false) String init, DependencyModuleForm dependencyModuleForm, SessionStatus sessionStatus, Model model) {
		if (init != null) {
			sessionStatus.setComplete();
			dependencyModuleForm = new DependencyModuleForm();
			model.addAttribute(MODULE_DEPENDENCY_FORM_PATH, dependencyModuleForm);
		}
		Module module = new Module();
		if(dependencyModuleForm.getModuleId() != null){
			module = moduleService.findModuleById(Long.parseLong(dependencyModuleForm.getModuleId()));
		} else {
			module = moduleService.findModuleById(Long.parseLong((String) SessionUtils.get("returnModuleIdFromGenerateScreen")));
		}
		List<Module> listModuleDependency = moduleService.findListModuleDependency(Long.parseLong(dependencyModuleForm.getModuleId()), SessionUtils.getCurrentProjectId());
		dependencyModuleForm.setListModuleDependency(listModuleDependency);
		dependencyModuleForm.setModuleName(module.getModuleName());
		dependencyModuleForm.setModuleCode(module.getModuleCode());
		dependencyModuleForm.setStatus(module.getStatus());
		dependencyModuleForm.setBusinessTypeName(module.getBusinessTypeName());
		dependencyModuleForm.setProjectId(SessionUtils.getCurrentProjectId());
		dependencyModuleForm.setProjectName(SessionUtils.getCurrentProject().getProjectName());
		return MODULE_DEPENDENCY_FORM_PATH;
	}*/
	
	/**
	 * Initialize generate screen
	 * @return GENERATE_FORM_PATH
	 */
	@RequestMapping(value = "dependencymodule", method = RequestMethod.POST)
	public String processGenerate(@Validated({ Default.class, RegistrationForm.class }) GenerateScreenForm generateScreenForm, BindingResult result,Model model, RedirectAttributes redirectAttr, SessionStatus sessionStatus) {
		/*if (result.hasErrors()) {
			ValidationUtils.setBindingResult(result, model);
			return GENERATE_SCREEN_FORM_PATH;
		}
		Module generateScreen = beanMapper.map(generateScreenForm, Module.class);
		if(generateScreen.getModuleCode().equals(generateScreenForm.getBusinessGenerateCode())){
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(GenerateMessageConst.SC_GENERATION_0010)));
			SessionUtils.set("returnModuleIdFromGenerateScreen" , generateScreenForm.getModuleId());
			return REDIRECT_GENERATE_SCREEN_FORM_PATH;
		}
		if(generateScreen.getModuleName().equals(generateScreenForm.getBusinessGenerateName())){
			redirectAttr.addFlashAttribute("message", ResultMessages.error().add(CommonMessageConst.ERR_SYS_0036, MessageUtils.getMessage(GenerateMessageConst.SC_GENERATION_0011)));
			SessionUtils.set("returnModuleIdFromGenerateScreen" , generateScreenForm.getModuleId());
			return REDIRECT_GENERATE_SCREEN_FORM_PATH;
		}
		generateScreen.setModuleName(generateScreenForm.getBusinessGenerateName());
		generateScreen.setModuleCode(generateScreenForm.getBusinessGenerateCode());
		generateScreen.setProjectId(SessionUtils.getCurrentProjectId());
		generateScreen.setProjectName(SessionUtils.getCurrentProject().getProjectName());
		try {
			screenDesignGeneratorService.generateSceen(generateScreen);
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return GENERATE_SCREEN_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(ModuleMessageConst.SC_MODULE_0031)));
		SessionUtils.set("returnModuleIdFromGenerateScreen" , generateScreenForm.getModuleId());*/
		return REDIRECT_MODULE_DEPENDENCY_FORM_PATH;
	}
}
