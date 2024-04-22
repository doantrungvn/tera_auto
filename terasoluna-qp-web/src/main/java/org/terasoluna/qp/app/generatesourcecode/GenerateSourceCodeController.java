package org.terasoluna.qp.app.generatesourcecode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.codelist.SimpleMapCodeList;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.FileUtilsQP;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.GenerateSourceCodeMessageConst;
import org.terasoluna.qp.domain.model.BusinessDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.GenerateSourceCode;
import org.terasoluna.qp.domain.model.GenerateSourceCodeItem;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignService;
import org.terasoluna.qp.domain.service.decisiontable.DecisionTableService;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeConst;
import org.terasoluna.qp.domain.service.generatesourcecode.GenerateSourceCodeService;
import org.terasoluna.qp.domain.service.menudesign.MenuDesignService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;

import freemarker.template.TemplateException;

@Controller
@RequestMapping(value = "generatesourcecode")
@TransactionTokenCheck(value = "generatesourcecode")
public class GenerateSourceCodeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(GenerateSourceCodeController.class);
	private static final String GENERATE_SOURCECODE_PATH = "generatesourcecode/generateSourceCodeForm";
	private static final String GENERATE_SOURCECODE_MODULE_PATH = "generatesourcecode/generateSourceCodeModuleForm";
	private static final String GENERATE_SOURCECODE_FORM_NAME = "generateSourceCodeForm";

	private static final String REDIRECT_GENERATE_LINK_FORM = "redirect:/generatesourcecode/generatesourcecode";

	@Inject
	Mapper beanMapper;

	@Inject
	ModuleService moduleService;

	@Inject
	MenuDesignService menuDesignService;

	@Inject
	ScreenDesignService screenDesignService;

	@Inject
	BusinessDesignService businessDesignService;

	@Inject
	DecisionTableService decisionTableService;

	@Inject
	ProjectService projectService;
	
	@Inject
	@Named("CL_GENERATE_SOURCE_TYPE")
	SimpleMapCodeList generateSourceTypeCodeList;

	@Inject
	GenerateSourceCodeService generateSourceCodeService;

	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * 
	 * @param webDataBinder
	 *            WebDataBinder
	 */
	@InitBinder(GENERATE_SOURCECODE_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@InitBinder
	public void initService(WebDataBinder webDataBinder) {
		/*moduleService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
		moduleService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
		moduleService.setAccountId(SessionUtils.getAccountId());

		screenDesignService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
		screenDesignService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
		
		businessDesignService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
		businessDesignService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
		
		generateSourceCodeService.setAccountId(SessionUtils.getAccountId());
		generateSourceCodeService.setCurrentAccount(SessionUtils.getCurrentAccount());
		generateSourceCodeService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
		generateSourceCodeService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
		generateSourceCodeService.setWorkingProject(SessionUtils.getCurrentProject());*/
		
		moduleCode = CommonMessageConst.TQP_GENERATESOURCECODE;
	}

	/**
	 * Identifies methods which initialize the generate source
	 * 
	 * @return generateSourceCodeForm
	 */
	@ModelAttribute
	public GenerateSourceCodeForm setUpGenerateSourceCodeForm() {
		GenerateSourceCodeForm generateSourceCodeForm = new GenerateSourceCodeForm();
		logger.debug("Init form {0}", generateSourceCodeForm);

		return generateSourceCodeForm;
	}

	/**
	 * Display for generate source
	 * 
	 * @param init
	 * @param generateSourceCodeForm
	 * @param model
	 * @return path of folder view
	 */
	@RequestMapping(value = "generatesourcecode", method = RequestMethod.GET)
	public String displayGenerateSourceCode(@RequestParam(value = "init", required = false) String init, GenerateSourceCodeForm generateSourceCodeForm, Model model) {

		if (init != null) {
			generateSourceCodeForm = new GenerateSourceCodeForm();
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}

		checkChangeProject(false);

		// Get project information
		Project currentProject = SessionUtils.getCurrentProject();
		// Get list module information
		//List<Module> moduleList = moduleService.findAllModuleOfOnline(currentProject.getProjectId(), DbDomainConst.FunctionType.ONLINE);
		List<Module> moduleList = moduleService.findAllModule(currentProject.getProjectId(), null);

		generateSourceCodeForm.setProject(currentProject);
		generateSourceCodeForm.setModules(moduleList);

		initGenerateSourceCodeByCodeList(generateSourceCodeForm);
		
		model.addAttribute("generateSourceCodeForm", generateSourceCodeForm);
		return GENERATE_SOURCECODE_PATH;
	}

	/**
	 * Process for generate source code
	 * 
	 * @param generateSourceCodeForm
	 * @param model
	 * @param redirectAttr
	 * @return path of screen
	 */
	@RequestMapping(value = "generatesourcecode", method = RequestMethod.POST)
	public String processGenerateSourceCode(GenerateSourceCodeForm generateSourceCodeForm, Model model, RedirectAttributes redirectAttr) {
		String destination = "";

		checkChangeProject(false);
		GenerateSourceCode generateSourceCode = beanMapper.map(generateSourceCodeForm, GenerateSourceCode.class);
		CommonModel cm = this.initCommon();
		
		generateSourceCode.setProject(projectService.getProjectInformation(cm.getWorkingProjectId(), cm.getUpdatedBy()));
		generateSourceCode.setTempFolder(FileUtilsQP.getTempFolderForExtract());

		try {
			// FIXME Must DELETE
			if (generateSourceCode.getGenAll() != null && generateSourceCode.getGenAll() == true) {
				generateSourceCode.setScopeGenerateSource(GenerateSourceCodeConst.PROJECT_SCOPRE);
				generateSourceCode = generateSourceCodeService.processGenerateSourceCode(generateSourceCode, cm);
			} else {

				if (CollectionUtils.isEmpty(generateSourceCode.getModules())) {
					model.addAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0013));
					model.addAttribute("generateSourceCodeForm", generateSourceCodeForm);
					return GENERATE_SOURCECODE_PATH;
				}

				generateSourceCode.setScopeGenerateSource(GenerateSourceCodeConst.MODULE_SCOPRE);
				generateSourceCode = generateSourceCodeService.processGenerateSourceCode(generateSourceCode, cm);
			}
			generateSourceCodeForm = beanMapper.map(generateSourceCode, GenerateSourceCodeForm.class);

			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(GenerateSourceCodeMessageConst.SC_GENERATESOURCECODE_0007));
			redirectAttr.addFlashAttribute("fileName", generateSourceCodeForm.getFileName());
			destination = REDIRECT_GENERATE_LINK_FORM;
		} catch (BusinessException ex) {
			model.addAttribute("generateSourceCodeForm", generateSourceCodeForm);
			model.addAttribute("message", ex.getResultMessages());
			destination = GENERATE_SOURCECODE_PATH;
		}

		return destination;
	}

	@RequestMapping(value = "generatesourcecodemodule", method = RequestMethod.GET)
	public String displayGenerateSourceCodeModuleForm(@RequestParam(value = "moduleId", required = false) Long moduleId, GenerateSourceCodeForm generateSourceCodeForm, Model model) {

		checkChangeProject(false);
		Module module = moduleService.findModuleById(Long.valueOf(moduleId));
		CommonModel cm = this.initCommon();
		
		// Get list of screen design by module
		List<ScreenDesign> listOfScreenDesign = screenDesignService.getAllScreenInfoByModuleId(module.getModuleId(), cm.getWorkingLanguageId());
		// Get list of blogic by module
		List<BusinessDesign> listOfBusinessDesign = businessDesignService.findBlogicByModuleId(module.getModuleId(), cm);

		generateSourceCodeForm.setModule(module);
		generateSourceCodeForm.setListOfScreenDesign(listOfScreenDesign);
		generateSourceCodeForm.setListOfBusinessDesign(listOfBusinessDesign);
		// SQL Design

		model.addAttribute("generateSourceCodeForm", generateSourceCodeForm);
		return GENERATE_SOURCECODE_MODULE_PATH;
	}

	/**
	 * Display for generate document
	 * 
	 * @param init
	 * @param generateDocumentForm
	 * @param model
	 * @return path of folder view
	 * @throws TemplateException
	 * @throws IOException
	 */
	@RequestMapping(value = "generatesourcecodemodule", method = RequestMethod.POST)
	public String processGenerateSourceCodeModuleScope(GenerateSourceCodeForm generateSourceCodeForm, Model model, RedirectAttributes redirectAttr) {

		return REDIRECT_GENERATE_LINK_FORM;
	}

	/**
	 * Generate document item for generate source
	 * 
	 * @param generateSourceCodeForm
	 */
	private void initGenerateSourceCodeByCodeList(GenerateSourceCodeForm generateSourceCodeForm) {
		List<GenerateSourceCodeItem> generateDocumentItemLst = new ArrayList<GenerateSourceCodeItem>();

		for (String key : generateSourceTypeCodeList.asMap().keySet()) {
			GenerateSourceCodeItem item = new GenerateSourceCodeItem();
			item.setSourceCodeItemKey(Integer.valueOf(key));
			item.setSourceCodeItemTemplateName(generateSourceTypeCodeList.asMap().get(key));
			item.setIsChecked(false);
			generateDocumentItemLst.add(item);
		}

		generateSourceCodeForm.setGenerateSourceCodeItemLst(generateDocumentItemLst);
	}

	@RequestMapping(value = "confirmgeneratesourcecode", method = RequestMethod.POST)
	public String processRedirectGenerateDocument(@Validated GenerateSourceCodeForm generateSourceCodeForm, BindingResult result, Model model, RedirectAttributes redirectAttr) {
		String redirect = "";
		checkChangeProject(false);

		if (result.hasErrors()) {
			return GENERATE_SOURCECODE_PATH;
		}

		CommonModel cm = this.initCommon();
		GenerateSourceCode generateSourceCode = beanMapper.map(generateSourceCodeForm, GenerateSourceCode.class);
		generateSourceCode.setProject(SessionUtils.getCurrentProject());
		
		if (CollectionUtils.isEmpty(generateSourceCode.getModules())) {
			model.addAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0013));
			model.addAttribute("generateSourceCodeForm", generateSourceCodeForm);
			return GENERATE_SOURCECODE_PATH;
		}

		try {
			generateSourceCode.setTempFolder(FileUtilsQP.getTempFolderForExtract());
			generateSourceCode = generateSourceCodeService.processGenerateSourceCode(generateSourceCode, cm);
			generateSourceCodeForm = beanMapper.map(generateSourceCode, GenerateSourceCodeForm.class);
			// Backup data of form
			generateSourceCodeForm.setJsonBackup(DataTypeUtils.toJson(generateSourceCodeForm));
			model.addAttribute(GENERATE_SOURCECODE_FORM_NAME, generateSourceCodeForm);
		} catch (BusinessException ex) {
			generateSourceCodeForm.setFileName(null);
			model.addAttribute("generateSourceCodeForm", generateSourceCodeForm);
			model.addAttribute("message", ex.getResultMessages());
			return GENERATE_SOURCECODE_PATH;
		}

		redirect = GENERATE_SOURCECODE_MODULE_PATH;

		return redirect;
	}

}
