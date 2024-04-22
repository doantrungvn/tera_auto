package org.terasoluna.qp.app.generate;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.common.ultils.ValidationUtils;
import org.terasoluna.qp.app.graphicdatabasedesign.GraphicDbDesignForm;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.GenerateMessageConst;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.Module;
import org.terasoluna.qp.domain.model.Project;
import org.terasoluna.qp.domain.model.ScreenDesign;
import org.terasoluna.qp.domain.repository.screendesign.ScreenDesignSearchCriteria;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GraphicDatabaseDesignService;
import org.terasoluna.qp.domain.service.graphicdatabasedesign.GraphicDbDesign;
import org.terasoluna.qp.domain.service.module.ModuleService;
import org.terasoluna.qp.domain.service.screendesign.ScreenDesignService;
import org.terasoluna.qp.domain.service.subjectarea.SubjectAreaService;

@Controller
@RequestMapping(value = "generation")
@TransactionTokenCheck(value = "generatedbblogic")
@SessionAttributes(types = { GenerateDbAndBlogicForm.class })
public class GenerateDbAndBlogicController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(GenerateDbAndBlogicController.class);

	@Inject
	ScreenDesignService screenDesignService;

	@Inject
	ModuleService moduleService;

	@Inject
	Mapper beanMapper;

	@Inject
	SubjectAreaService areaService;

	@Inject
	GraphicDatabaseDesignService graphicDatabaseDesignService;

	@Inject 
	SystemService systemService;
	
	private static final String CONFIRM_GENERATE_FORM = "generate/confirmGenerateForm";
	private static final String GENERATE_LINK = "generate/generateDbAndBlogic";
	private static final String SEARCH_REDIRECT_PATH = "redirect:/module/search";
	private static final String GENERATE_FORM_NAME = "generateDbAndBlogicForm";
	private static final String REDIRECT_CONFIRM_GENERATE_FORM = "redirect:/generation/confirmDbAndBlogic?valueOfForm=";
	private static final String REDIRECT_DISPLAY_SEARCH_FORM = "redirect:/generation/generatedbblogic?valueOfForm=";

	// private static final String REDIRECT_GENERATE_FORM = "redirect:/generation/generatedbblogic?init&moduleIdRedirect=";

	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * 
	 * @param webDataBinder
	 *            WebDataBinder
	 */

	@ModelAttribute
	public GenerateDbAndBlogicForm setUpGenerateBlogicSearchForm() {
		GenerateDbAndBlogicForm generateDbAndBlogicForm = new GenerateDbAndBlogicForm();
		logger.debug("Init form {}", generateDbAndBlogicForm);
		return generateDbAndBlogicForm;
	}

	@ModelAttribute
	public void init() {
//		moduleService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
//		moduleService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
//		moduleService.setAccountId(SessionUtils.getAccountId());
		moduleCode = CommonMessageConst.TQP_GENERATEDBBLOGIC;
	}

	@ModelAttribute("reservedWords") 
	public String[] initReservedWords() { 
		return  systemService.databaseReservedWords(SessionUtils.getCurrentDatabaseType()); 
	}
	
	/**
	 * Initialize search screen design
	 * 
	 * @return GENERATE_LINK
	 */
	@TransactionTokenCheck(value = "generatedbblogic", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "generatedbblogic", method = { RequestMethod.GET })
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @RequestParam(value = "valueOfForm", required = false) String valueOfForm, @RequestParam(value = "moduleIdRedirect", required = false) String moduleIdRedirect, @ModelAttribute GenerateDbAndBlogicForm generateDbAndBlogicForm, Model model, RedirectAttributes redirectAttr, SessionStatus sessionStatus) {
		
		try {
			checkChangeProject(true);
		} catch (BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}


		if (init != null) {
			Long moduleId = generateDbAndBlogicForm.getModuleId();
			if (moduleId == null && StringUtils.isNotEmpty(moduleIdRedirect)) {
				moduleId = Long.parseLong(moduleIdRedirect);
			}

			sessionStatus.setComplete();
			generateDbAndBlogicForm = new GenerateDbAndBlogicForm();
			generateDbAndBlogicForm.setModuleId(moduleId);
			if (StringUtils.isNotEmpty(moduleIdRedirect)) {
				model.addAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0009, MessageUtils.getMessage(GenerateMessageConst.CL_GENERATION_0006)));
			}
			model.addAttribute(GENERATE_FORM_NAME, generateDbAndBlogicForm);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		
		if (StringUtils.isNotEmpty(valueOfForm)) {
			generateDbAndBlogicForm = DataTypeUtils.toObject(valueOfForm, GenerateDbAndBlogicForm.class);
			model.addAttribute(GENERATE_FORM_NAME, generateDbAndBlogicForm);
			return prepareData(generateDbAndBlogicForm, model, redirectAttr, false);
		} else {
			return prepareData(generateDbAndBlogicForm, model, redirectAttr, true);
		}
	}

	/**
	 * 
	 * @param generateDbAndBlogicForm
	 * @param result
	 * @param model
	 * @param redirectAttr
	 * @param sessionStatus
	 * @return
	 */
	@TransactionTokenCheck(value = "generatedbblogic", type = TransactionTokenType.IN)
	@RequestMapping(value = "generatedbblogic", method = { RequestMethod.POST })
	public String processGenerate(@Validated GenerateDbAndBlogicForm generateDbAndBlogicForm, BindingResult result, Model model, RedirectAttributes redirectAttr, SessionStatus sessionStatus) {
		
		if (result.hasErrors()) {
			// need to reflector
			String strReturn = prepareData(generateDbAndBlogicForm, model, redirectAttr, false);
			ValidationUtils.setBindingResult(result, model);
			return strReturn;
		}

		String strReturn = "";
		try {
			checkChangeProject(true);
			Long projectId = SessionUtils.getCurrentProjectId();
			Long moduleId = generateDbAndBlogicForm.getModuleId();
			List<Long> listScreenIds = generateDbAndBlogicForm.getListScreenIds();
			if (DbDomainConst.GenerateMode.DATABASE_AND_BLOGIC.equals(generateDbAndBlogicForm.getGenerateMode())) {
				GraphicDbDesignForm dbDesignForm = new GraphicDbDesignForm();
				dbDesignForm.setProjectId(projectId);
				dbDesignForm.setModuleId(moduleId);
				dbDesignForm.setListScreenIds(listScreenIds);
				dbDesignForm.setGenerateMode(generateDbAndBlogicForm.getGenerateMode());
				String query = "";
				try {
					query = UriUtils.encodeQueryParam(DataTypeUtils.toJson(dbDesignForm), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				strReturn = REDIRECT_CONFIRM_GENERATE_FORM + query;
			} else {
				try {
					graphicDatabaseDesignService.generateBlogicFromScreen(moduleId, listScreenIds, SessionUtils.getCurrentLanguageDesign(), SessionUtils.getAccountId(), projectId);
					strReturn = redirectSearchModule(redirectAttr, DbDomainConst.GenerateMode.BUSINESS_LOGIC);
				} catch (Exception ex) {
					model.addAttribute("message", ex.getMessage());
					logger.error(ex.getMessage());
					strReturn = prepareData(generateDbAndBlogicForm, model, redirectAttr, false);
				}
			}
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
		}
		return strReturn;
	}

	private String redirectSearchModule(RedirectAttributes redirectAttr, Integer mode) {
		if (DbDomainConst.GenerateMode.BUSINESS_LOGIC.equals(mode)) {
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0009, MessageUtils.getMessage(GenerateMessageConst.CL_GENERATION_0007)));
		} else {
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0009, MessageUtils.getMessage(GenerateMessageConst.CL_GENERATION_0006)));
		}
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * 
	 * @param valueOfForm
	 * @param model
	 * @param sessionStatus
	 * @return
	 */
	@TransactionTokenCheck(value = "confirmDbAndBlogic", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "confirmDbAndBlogic", method = { RequestMethod.GET })
	public String confirmDbAndBlogic(@RequestParam(value = "valueOfForm", required = false) String valueOfForm, Model model, RedirectAttributes redirectAttr, SessionStatus sessionStatus) {
		String strReturn = "";
		try {
			checkChangeProject(true);
			GraphicDbDesignForm dbDesignForm = DataTypeUtils.toObject(valueOfForm, GraphicDbDesignForm.class);
			Long projectId = dbDesignForm.getProjectId();
			Long moduleId = dbDesignForm.getModuleId();
			Integer generateMode = dbDesignForm.getGenerateMode();
			List<Long> listScreenIds = dbDesignForm.getListScreenIds();
			Long subjectAreaId = DomainDatatypeConst.SEARCH_TABLE_DESIGN_NOT_IN_SUBJECT_AREA;
			GraphicDbDesign grpDbDesign = graphicDatabaseDesignService.displayConfirmGenerateDbAndBlogic(SessionUtils.getAccountId(), projectId, moduleId, subjectAreaId, listScreenIds, SessionUtils.getCurrentLanguageDesign());
			// keep value
			dbDesignForm = beanMapper.map(grpDbDesign, GraphicDbDesignForm.class);
			dbDesignForm.setLstBlogic(grpDbDesign.getLstBusinessDesigns());
			dbDesignForm.setListScreenIds(listScreenIds);
			dbDesignForm.setGenerateMode(generateMode);

			String jSonString = DataTypeUtils.toJson(dbDesignForm);
			dbDesignForm.setjSonString(jSonString);

			SessionUtils.remove("graphicDbDesignForm");
			model.addAttribute("graphicDbDesignForm", dbDesignForm);

			strReturn = CONFIRM_GENERATE_FORM;
			
			model.addAttribute("message", ResultMessages.info().add(GenerateMessageConst.INF_GENERATION_0002));
			
		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
		}
		return strReturn;
	}

	/**
	 * 
	 * @param dbDesignForm
	 * @param redirectAttr
	 * @param model
	 * @return
	 */
	@TransactionTokenCheck(value = "confirmDbAndBlogic", type = TransactionTokenType.IN)
	@RequestMapping(value = "generateConfirm", method = RequestMethod.POST, params = "jsonBack")
	public String displayModifyBack(@ModelAttribute GraphicDbDesignForm dbDesignForm, RedirectAttributes redirectAttr, Model model) {
		
		try {
			checkChangeProject(true);
		} catch (BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}

		String jSonString = dbDesignForm.getjSonString();
		GraphicDbDesignForm dto = DataTypeUtils.toObject(jSonString, GraphicDbDesignForm.class);
		GenerateDbAndBlogicForm generateDbAndBlogicForm = new GenerateDbAndBlogicForm();
		generateDbAndBlogicForm.setProjectId(dto.getProjectId());
		generateDbAndBlogicForm.setModuleId(dto.getModuleId());
		generateDbAndBlogicForm.setGenerateMode(dto.getGenerateMode());
		generateDbAndBlogicForm.setListScreenIds(dto.getListScreenIds());
		String query = "";
		try {
			query = UriUtils.encodeQueryParam(DataTypeUtils.toJson(generateDbAndBlogicForm), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return REDIRECT_DISPLAY_SEARCH_FORM + query;

	}

	/**
	 * 
	 * @param dbDesignForm
	 * @param result
	 * @param model
	 * @param redirectAttr
	 * @return
	 */
	@TransactionTokenCheck(value = "confirmDbAndBlogic", type = TransactionTokenType.IN)
	@RequestMapping(value = "generateConfirm", method = { RequestMethod.POST })
	public String generateConfirm(@Validated @ModelAttribute GraphicDbDesignForm dbDesignForm, BindingResult result, Model model, RedirectAttributes redirectAttr) {
		try {
			checkChangeProject(true);
			
			CommonModel common = this.initCommon();
			common.setDesignStatus(Boolean.TRUE);
			moduleService.validateModule(dbDesignForm.getModuleId(), common);
			
			Project project = SessionUtils.getCurrentProject();
			
			dbDesignForm.setProjectId(project.getProjectId());
			dbDesignForm.setProjectIdAutocomplete(project.getProjectName());
			GraphicDbDesign graphicDbDesign = beanMapper.map(dbDesignForm, GraphicDbDesign.class);

			String jSonString = dbDesignForm.getjSonString();

			GraphicDbDesignForm dto = DataTypeUtils.toObject(jSonString, GraphicDbDesignForm.class);
			graphicDbDesign.setProject(project);
			// generate DB & Blogic & update design mode
			graphicDatabaseDesignService.modifyTableByGraphicDesign(graphicDbDesign, false, dto.getLstBlogic(), dto.getListScreenIds(), true,
					SessionUtils.getAccountId(), SessionUtils.getCurrentLanguageDesign(), DbDomainConst.YesNoFlg.YES);
			// return search screen
			/*
			 * GenerateDbAndBlogicForm generateDbAndBlogicForm = new GenerateDbAndBlogicForm(); generateDbAndBlogicForm.setModuleId(dbDesignForm.getModuleId());
			 * 
			 * return REDIRECT_GENERATE_FORM + generateDbAndBlogicForm.getModuleId();
			 */

			return redirectSearchModule(redirectAttr, DbDomainConst.GenerateMode.DATABASE_AND_BLOGIC);

		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			return CONFIRM_GENERATE_FORM;
		}
	}

	/**
	 * 
	 * @param generateDbAndBlogicForm
	 * @param model
	 * @param initFlg
	 */
	private String prepareData(GenerateDbAndBlogicForm generateDbAndBlogicForm, Model model, RedirectAttributes redirectAttr, boolean initFlg) {
		Module module;
		
		try {
			CommonModel common = this.initCommon();
			common.setDesignStatus(Boolean.TRUE);
			module = moduleService.validateModule(generateDbAndBlogicForm.getModuleId(), common);
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}

		ScreenDesignSearchCriteria screenDesignSearchCriteria = new ScreenDesignSearchCriteria();
		int[] designMode = { DbDomainConst.DesignMode.PROTOTYPE };
		screenDesignSearchCriteria.setDesignMode(designMode);

		/*
		 * int[] screenParternType = {DbDomainConst.ScreenPatternType.REGISTER}; screenDesignSearchCriteria.setScreenParternTypes(screenParternType);
		 */

		int[] designStatus = { DbDomainConst.DesignStatus.UNDER_DESIGN };
		screenDesignSearchCriteria.setDesignStatus(designStatus);

		screenDesignSearchCriteria.setLanguageId(SessionUtils.getCurrentLanguageId());
		screenDesignSearchCriteria.setModuleId(generateDbAndBlogicForm.getModuleId());
		screenDesignSearchCriteria.setProjectId(SessionUtils.getCurrentProjectId());

		if (initFlg) {
			generateDbAndBlogicForm.setGenerateMode(DbDomainConst.GenerateMode.DATABASE_AND_BLOGIC);
		}

		List<ScreenDesign> listOfScreen = screenDesignService.findAllByCriteria(screenDesignSearchCriteria);
		model.addAttribute("listOfScreen", listOfScreen);
		model.addAttribute("module", module);
		return GENERATE_LINK;
	}

}
