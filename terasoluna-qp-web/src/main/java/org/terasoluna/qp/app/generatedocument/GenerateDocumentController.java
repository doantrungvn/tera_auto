package org.terasoluna.qp.app.generatedocument;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.repository.query.Param;
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
import org.terasoluna.qp.app.common.ultils.GenerateDocumentUtilsQP;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.GenerateDocumentMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.GenerateDocument;
import org.terasoluna.qp.domain.model.GenerateDocumentItem;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.service.businessdesign.BusinessDesignService;
import org.terasoluna.qp.domain.service.generatedocument.GenerateDocumentService;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.project.ProjectService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;

@Controller
@RequestMapping(value = "generatedocument")
@TransactionTokenCheck(value = "generatedocument")
public class GenerateDocumentController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(GenerateDocumentController.class);
	private static final String GENERATE_DOCUMENT_PROJECT_PATH = "generatedocument/generateDocumentProjectForm";
	private static final String GENERATE_DOCUMENT_MODULE_PATH = "generatedocument/generateDocumentModuleForm";
	private static final String GENERATE_DOCUMENT_FORM_NAME = "generateDocumentForm";
	private static final String REDIRECT_GENERATE_DOCUMENT_PROJECT_PATH = "redirect:/generatedocument/generatedocument";
	private static final String REDIRECT_TYPE_NEXT = "next";
	private static final String REDIRECT_TYPE_PREV = "prev";
	
	@Inject
	GenerateDocumentService generateDocumentService;
	
	@Inject
	ProjectService projectService;

	@Inject
	ModuleService moduleService;
	
	@Inject
	ScreenDesignService screenDesignService;

	@Inject
	BusinessDesignService businessDesignService;

	@Inject
	Mapper beanMapper;
	
	@Inject
	@Named("CL_GENERATE_DOCUMENT_PROJECT_RD_TYPE")
	SimpleMapCodeList generateDocumentProjectRDCodeList;
	
	@Inject
	@Named("CL_GENERATE_DOCUMENT_PROJECT_ED_TYPE")
	SimpleMapCodeList generateDocumentProjectEDCodeList;
	
	@Inject
	@Named("CL_GENERATE_DOCUMENT_MODULE_ED_TYPE")
	SimpleMapCodeList generateDocumentModuleEDCodeList;
	
	@Inject
	GenerateDocumentValidator generateDocumentValidator;
	
	@InitBinder
	public void init() {
	    //Refactor fix current project 20160613
//		generateDocumentService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
//		generateDocumentService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
//		generateDocumentService.setAccountId(SessionUtils.getCurrentAccount().getAccountId());
//		generateDocumentService.setWorkingLanguageDesign(SessionUtils.getCurrentLanguageDesign());
//		generateDocumentService.setWorkingProject(SessionUtils.getCurrentProject());
//		generateDocumentService.setCurrentAccounProfile(SessionUtils.getCurrentAccountProfile());
//		generateDocumentService.setCurrentAccount(SessionUtils.getCurrentAccount());
		
		moduleCode = CommonMessageConst.TQP_GENERATEDOCUMENT;
	}
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used
	 * for populating command and form object
	 * 
	 * @param webDataBinder WebDataBinder
	 */
	@InitBinder(GENERATE_DOCUMENT_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(generateDocumentValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * Identifies methods which initialize the generate document
	 * 
	 * @return generateDocumentForm
	 */
	@ModelAttribute
	public GenerateDocumentForm setUpGenerateDocumentForm() {
		GenerateDocumentForm generateDocumentForm = new GenerateDocumentForm();
		logger.debug("Init form {0}", generateDocumentForm);

		return generateDocumentForm;
	}
	
	/**
	 * Display for generate document
	 * 
	 * @param init
	 * @param generateDocumentForm
	 * @param model
	 * @return path of folder view
	 */
	@RequestMapping(value = "generatedocument", method = RequestMethod.GET)
	public String displayGenerateDocument(@RequestParam(value = "init", required = false) String init, 
			GenerateDocumentForm generateDocumentForm, Model model) {

		Long workingProject = SessionUtils.getCurrentProjectId();
		if (init != null) {
			generateDocumentForm = new GenerateDocumentForm();
			super.setOldProjectId(workingProject);
		}

		checkChangeProject(false);

		// Get project information
		Project project = projectService.getProjectInformation(workingProject, SessionUtils.getAccountId());
		// Get list module information
		List<Module> moduleList = projectService.findAllModuleOfProject(workingProject);

		generateDocumentForm.setProject(project);
		generateDocumentForm.setModuleList(moduleList);
		initGenerateDocumentByCodeList(generateDocumentForm);

		model.addAttribute(GENERATE_DOCUMENT_FORM_NAME, generateDocumentForm);

		return GENERATE_DOCUMENT_PROJECT_PATH;
	}

	/**
	 * Process for transfer to process generate document
	 * 
	 * @param generateDocumentForm
	 * @param model
	 * @param redirectAttr
	 * @return path of folder jsp
	 */
	@RequestMapping(value = "confirmgeneratedocument", method = RequestMethod.POST)
	public String processRedirectGenerateDocument(@Validated GenerateDocumentForm generateDocumentForm, BindingResult result, 
			Model model, RedirectAttributes redirectAttr) {
		String redirect = "";
		checkChangeProject(false);
		
		if (result.hasErrors()) {
			return GENERATE_DOCUMENT_PROJECT_PATH;
		}
		
		GenerateDocument generateDocument =  beanMapper.map(generateDocumentForm, GenerateDocument.class);
		
		/*if (CollectionUtils.isEmpty(generateDocument.getModuleList())) {
			model.addAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0013));
			model.addAttribute("generateDocumentForm", generateDocumentForm);
			return GENERATE_DOCUMENT_PROJECT_PATH;
		}*/
		
		try {
		    //Refactor fix current project 20160613
		    CommonModel common = this.initCommon();
			generateDocument = generateDocumentService.getDataForGenerateDocument(generateDocument, common);
			
			generateDocumentForm = beanMapper.map(generateDocument, GenerateDocumentForm.class);
			// Backup data of form
			generateDocumentForm.setJsonBackup(DataTypeUtils.toJson(generateDocumentForm));
			model.addAttribute(GENERATE_DOCUMENT_FORM_NAME, generateDocumentForm);
		} catch(BusinessException ex) {
			generateDocumentForm.setFileName(null);
			model.addAttribute("generateDocumentForm", generateDocumentForm);
			model.addAttribute("message", ex.getResultMessages());
			return GENERATE_DOCUMENT_PROJECT_PATH;
		}
	
		// Check item is single document
		if(!GenerateDocumentUtilsQP.isMultipleDocumentType(generateDocumentForm.getGenerateDocumentItem())){
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0007));
			redirectAttr.addFlashAttribute("fileName", generateDocumentForm.getFileName());
			redirect = REDIRECT_GENERATE_DOCUMENT_PROJECT_PATH;
		} else {
			redirect = GENERATE_DOCUMENT_MODULE_PATH;
		}

		return redirect;
	}

	/**
	 * Process for generate document
	 * 
	 * @param generateDocumentForm
	 * @param model
	 * @param redirectAttr
	 * @return path of folder jsp
	 */
	@RequestMapping(value = "generatedocument", method = RequestMethod.POST)
	public String processGenerateDocument(GenerateDocumentForm generateDocumentForm, Model model, @Param("direct")String direct, 
			RedirectAttributes redirectAttr) {

		String redirect = "";
		checkChangeProject(false);
		GenerateDocument generateDocument =  beanMapper.map(generateDocumentForm, GenerateDocument.class);
		
		if(REDIRECT_TYPE_NEXT.equals(direct.split(",")[0])) {
			try {
			    //Refactor fix current project 20160613
	            CommonModel common = this.initCommon();
				generateDocument = generateDocumentService.processGenerateDocumentExcelFile(generateDocument, common);
				generateDocumentForm = beanMapper.map(generateDocument, GenerateDocumentForm.class);
			} catch (BusinessException ex) {
				model.addAttribute("generateDocumentForm", generateDocumentForm);
				model.addAttribute("message", ex.getResultMessages());
				return GENERATE_DOCUMENT_MODULE_PATH;
			}

			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(GenerateDocumentMessageConst.SC_GENERATEDOCUMENT_0007));
			redirectAttr.addFlashAttribute("fileName", generateDocumentForm.getFileName());

			redirect = REDIRECT_GENERATE_DOCUMENT_PROJECT_PATH;
		} else if(REDIRECT_TYPE_PREV.equals(direct.split(",")[0])) {
			model.addAttribute(GENERATE_DOCUMENT_FORM_NAME, DataTypeUtils.toObject(generateDocumentForm.getJsonBackup(), GenerateDocumentForm.class));
			redirect = GENERATE_DOCUMENT_PROJECT_PATH;
		}

		return redirect;
	}
	
	private void initGenerateDocumentByCodeList(GenerateDocumentForm generateDocumentForm) {
		List<GenerateDocumentItem> generateDocumentItemLst = new ArrayList<GenerateDocumentItem>();
		
		for(String key : generateDocumentProjectRDCodeList.asMap().keySet()) {
			GenerateDocumentItem item = new GenerateDocumentItem();
			item.setDocumentItemScopeItemType("project");
			item.setDocumentItemParenItemType("rd");
			item.setDocumentItemType(Integer.valueOf(key));
			item.setDocumentItemTemplateName(generateDocumentProjectRDCodeList.asMap().get(key));
			item.setIsChecked(false);
			generateDocumentItemLst.add(item);
		}
		generateDocumentForm.setGenerateDocumentProjectTypeRDLst(generateDocumentItemLst);
		generateDocumentItemLst = new ArrayList<GenerateDocumentItem>();
		for(String key : generateDocumentProjectEDCodeList.asMap().keySet()) {
			GenerateDocumentItem item = new GenerateDocumentItem();
			item.setDocumentItemScopeItemType("project");
			item.setDocumentItemParenItemType("ed");
			item.setDocumentItemType(Integer.valueOf(key));
			item.setDocumentItemTemplateName(generateDocumentProjectEDCodeList.asMap().get(key));
			item.setIsChecked(false);
			generateDocumentItemLst.add(item);
		}
		generateDocumentForm.setGenerateDocumentProjectTypeEDLst(generateDocumentItemLst);
		generateDocumentItemLst = new ArrayList<GenerateDocumentItem>();
		for(String key : generateDocumentModuleEDCodeList.asMap().keySet()) {
			GenerateDocumentItem item = new GenerateDocumentItem();
			item.setDocumentItemScopeItemType("module");
			item.setDocumentItemParenItemType("ed");
			item.setDocumentItemType(Integer.valueOf(key));
			item.setDocumentItemTemplateName(generateDocumentModuleEDCodeList.asMap().get(key));
			item.setIsChecked(false);
			generateDocumentItemLst.add(item);
		}
		generateDocumentForm.setGenerateDocumentModuleTypeEDLst(generateDocumentItemLst);
	}
}
