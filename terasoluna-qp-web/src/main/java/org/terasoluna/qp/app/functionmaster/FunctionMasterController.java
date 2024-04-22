package org.terasoluna.qp.app.functionmaster;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.FunctionMasterMessageConst;
import org.terasoluna.qp.app.screenpermission.ScreenPemissionController;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.FunctionMaster;
import org.terasoluna.qp.domain.model.UploadFile;
import org.terasoluna.qp.domain.repository.functionmaster.FunctionMasterCriteria;
import org.terasoluna.qp.domain.repository.uploadfile.UploadFileRepository;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.functionmaster.FunctionMasterHelper;
import org.terasoluna.qp.domain.service.functionmaster.FunctionMasterService;
import org.terasoluna.qp.domain.service.functionmaster.FunctionMasterShareService;
import org.terasoluna.qp.domain.service.functionmaster.ImpactChangeOfFunctionMaster;

@Controller
@RequestMapping(value = "functionmaster")
@TransactionTokenCheck(value = "functionmaster")
@SessionAttributes(types = { FunctionMasterSearchForm.class })
public class FunctionMasterController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(ScreenPemissionController.class);
	private static final String FUNCTION_MASTER_FORM_NAME = "functionMasterForm";
	private static final String FUNCTION_MASTER_SEARCH_FORM_NAME = "functionMasterSearchForm";
	private static final String SEARCH_FORM_PATH = "functionmaster/searchForm";
	private static final String SEARCH_ACTION_PATH = "/functionmaster/search";
	private static final String REGISTER_FORM_PATH = "functionmaster/registerForm";
	private static final String VIEW_FORM_PATH = "functionmaster/viewForm";
	private static final String MODIFY_FORM_PATH = "functionmaster/modifyForm";
	private static final String REDIRECT_DELETION_SUCCESS = "redirect:/complete";
	private static final String SEARCH_REDIRECT_PATH = "redirect:/functionmaster/search";
	private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";
	private static final String VIEW_BLOGIC_MODIFY_LINK = "functionmaster/modifyFormChangeAffection";
	private static final String REDIRECT_VIEW_BLOGIC_DELETE_LINK = "functionmaster/viewFormDeleteAffection";
	
	@Inject
	FunctionMasterService functionMasterService;
	
	@Inject
	UploadFileRepository uploadFileRepository;
	
	@Inject
	FunctionMasterValidator functionMasterValidator;
	
	@Inject
	Mapper beanMapper;
	
	@Inject
	SystemService systemService;
	
	@Inject
	FunctionMasterShareService functionMasterShareService;
	
	@InitBinder
	public void init() {
		moduleCode = CommonMessageConst.TQP_FUNCTIONMASTER;
	}
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used
	 * for populating command and form object
	 * 
	 * @param webDataBinder WebDataBinder
	 */
	@InitBinder("functionMasterForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(functionMasterValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * Identifies methods which initialize the function master form object
	 * 
	 * @return functionMasterForm
	 */
	@ModelAttribute
	public FunctionMasterForm setUpFuncitonMasterForm() {
		FunctionMasterForm functionMasterForm = new FunctionMasterForm();
		logger.debug("Init form {0}", functionMasterForm);

		return functionMasterForm;
	}

	/**
	 * Identifies methods which initialize the function master search form
	 * object
	 * 
	 * @return functionMasterSearchForm
	 */
	@ModelAttribute
	public FunctionMasterSearchForm setUpFunctionMasterSearchForm() {
		FunctionMasterSearchForm functionMasterSearchForm = new FunctionMasterSearchForm();
		logger.debug("Init form {0}", functionMasterSearchForm);

		return functionMasterSearchForm;
	}

	/**
	 * Initialize search function master screen
	 * 
	 * @param init
	 * @param functionMasterSearchForm
	 * @param model
	 * @param pageable
	 * @param sessionStatus
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute FunctionMasterSearchForm functionMasterSearchForm, Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		if (init != null) {
			sessionStatus.setComplete();
			functionMasterSearchForm = new FunctionMasterSearchForm();
			model.addAttribute(FUNCTION_MASTER_SEARCH_FORM_NAME, functionMasterSearchForm);
		}
		checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		FunctionMasterCriteria functionMasterCriteria = beanMapper.map(functionMasterSearchForm, FunctionMasterCriteria.class);
		functionMasterCriteria.setProjectId(SessionUtils.getCurrentProjectId());
		Page<FunctionMaster> page = functionMasterService.searchFunctionMaster(functionMasterCriteria, pageable);
		model.addAttribute("page", page);

		return SEARCH_FORM_PATH;
	}

	/**
	 * Process search
	 * 
	 * @param functionMasterSearchForm
	 * @param pageable
	 * @param model
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(@ModelAttribute(FUNCTION_MASTER_SEARCH_FORM_NAME) FunctionMasterSearchForm functionMasterSearchForm, @PageableDefault Pageable pageable, Model model) {
		checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		
		FunctionMasterCriteria functionMasterCriteria = beanMapper.map(functionMasterSearchForm, FunctionMasterCriteria.class);
		functionMasterCriteria.setProjectId(SessionUtils.getCurrentProjectId());
		Page<FunctionMaster> page = functionMasterService.searchFunctionMaster(functionMasterCriteria, pageable);
		model.addAttribute("page", page);

		return SEARCH_FORM_PATH;
	}

	/**
	 * Initialize register function master screen
	 * 
	 * @param functionMasterForm
	 * @return REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(@ModelAttribute(FUNCTION_MASTER_FORM_NAME) FunctionMasterForm functionMasterForm, Model model, RedirectAttributes redirectAttr) {
		try {
			checkChangeProject(true);
			functionMasterForm.setPackageName(FunctionMasterHelper.generatePackageName(SessionUtils.getCurrentProject()));
			AccountProfile accountProfile = systemService.getDefaultProfile();
			functionMasterForm.setMaxSize(accountProfile.getMaxSizeUpload());
			return REGISTER_FORM_PATH;
		} catch (BusinessException be) {
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}
	}

	/**
	 * process register function master
	 * 
	 * @param functionMasterForm
	 * @param result
	 * @param model
	 * @param redirectAttr
	 * @return SEARCH_REDIRECT_PATH
	 * @throws IOException
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated @ModelAttribute FunctionMasterForm functionMasterForm, BindingResult result, Model model, RedirectAttributes redirectAttr) throws IOException {
		checkChangeProject(true);
		if (result.hasErrors()) {
			if (functionMasterForm.getFile() != null && functionMasterForm.getFile().getSize() >0) {
				// add warning message : User choose file again.
				functionMasterForm.setFileName("");
				functionMasterForm.setIsShowWarningFile(true);
			}
			
			model.addAttribute(FUNCTION_MASTER_FORM_NAME,functionMasterForm);
			return REGISTER_FORM_PATH;
		}
		FunctionMaster functionMaster = beanMapper.map(functionMasterForm, FunctionMaster.class);
		functionMaster.setProjectId(SessionUtils.getCurrentProjectId());
		if (functionMasterForm.getFile() != null && functionMasterForm.getFile().getSize() >0 ) {
			String fileName = FilenameUtils.getName(functionMasterForm.getFile().getOriginalFilename());
			functionMaster.setFileName(fileName);
		}
		try {
			CommonModel common = this.initCommon();
			common.setProjectId(SessionUtils.getCurrentProjectId());

			Long accountId = SessionUtils.getAccountId();
			functionMaster.setCreatedBy(accountId);
			functionMaster.setUpdatedBy(accountId);
			functionMasterService.registerFunctionMaster(functionMaster, common);
		} catch (BusinessException be) {
			if(functionMasterForm.getFile() != null && functionMasterForm.getFile().getSize() >0){
				// add warning message : User choose file again.
				functionMasterForm.setFileName("");
				functionMasterForm.setIsShowWarningFile(true);
			}
			model.addAttribute("message", be.getResultMessages());
			return REGISTER_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0010)));
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * Initialize modify function master screen.
	 * 
	 * @return MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(FunctionMasterForm functionMasterForm, Model model, RedirectAttributes redirectAttr) {
		String destination = StringUtils.EMPTY;
		FunctionMaster functionmaster = beanMapper.map(functionMasterForm, FunctionMaster.class); 
		checkChangeProject(true);
		try {
			functionmaster = functionMasterService.loadFunctionMaster(functionmaster.getFunctionMasterId());
			functionMasterService.checkFunctionMasterCommon(functionmaster);
			functionMasterForm = beanMapper.map(functionmaster, FunctionMasterForm.class);
			model.addAttribute(FUNCTION_MASTER_FORM_NAME, functionMasterForm);
			destination = MODIFY_FORM_PATH;
			AccountProfile accountProfile = systemService.getDefaultProfile();
			functionMasterForm.setMaxSize(accountProfile.getMaxSizeUpload());
		} catch (BusinessException be) {
			if (MODE_SEARCH.equals(functionMasterForm.getMode())) {
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(functionMasterForm.getMode())) {
				model.addAttribute("message", be.getResultMessages());
				model.addAttribute("notExistFlg", 1);
				destination = VIEW_FORM_PATH;
			}
		}
		return destination;
	}
	
	/**
	 * Initialize modify function master screen when User press back button from confirm screen.
	 * 
	 * @param functionMasterForm
	 * @param result
	 * @param model
	 * @param redirectAttr
	 * @return SEARCH_REDIRECT_PATH
	 * @throws IOException
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST,params="jsonBack")
	public String displayModifyBack(RedirectAttributes redirectAttr, Model model,@RequestParam("formJson")String formJson) {
		this.checkChangeProject(false);
		FunctionMaster functionMaster = DataTypeUtils.toObject(formJson, FunctionMaster.class);
		FunctionMasterForm functionMasterForm = beanMapper.map(functionMaster, FunctionMasterForm.class);
		// add warning message : User choose file again.
		if(functionMasterForm.getFlagChangeFile() && functionMasterForm.getFile() != null && functionMasterForm.getFile().getSize() >0){
			functionMasterForm.setFileName("");
			functionMasterForm.setIsShowWarningFile(true);
		}
		AccountProfile accountProfile = systemService.getDefaultProfile();
		functionMasterForm.setMaxSize(accountProfile.getMaxSizeUpload());
		model.addAttribute(FUNCTION_MASTER_FORM_NAME, functionMasterForm);
		return MODIFY_FORM_PATH;
	}
	
	/**
	 * process modify function master
	 * 
	 * @param functionMasterForm
	 * @param result
	 * @param model
	 * @param redirectAttr
	 * @return SEARCH_REDIRECT_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modifyConfirm", method = RequestMethod.POST)
	public String processModifyConfirm(@Validated FunctionMasterForm functionMasterForm, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		super.checkChangeProject(true);

		if (result.hasErrors()) {
			// add warning message : User choose file again.
			if(functionMasterForm.getFlagChangeFile() && functionMasterForm.getFile() != null && functionMasterForm.getFile().getSize() >0){
				functionMasterForm.setFileName("");
				functionMasterForm.setIsShowWarningFile(true);
			}
			return MODIFY_FORM_PATH;
		}

		FunctionMaster functionMaster = beanMapper.map(functionMasterForm, FunctionMaster.class);
		if(functionMasterForm.getFlagChangeFile()){
			if (functionMasterForm.getFile() != null && functionMasterForm.getFile().getSize() >0) {
				String fileName = FilenameUtils.getName(functionMasterForm.getFile().getOriginalFilename() );
				functionMaster.setFileName(fileName);
			}
		}
		try{
			CommonModel common = this.initCommon();
			common.setProjectId(SessionUtils.getCurrentProjectId());
			functionMaster.setCreatedBy(common.getCreatedBy());
			functionMaster.setUpdatedBy(common.getCreatedBy());
			ImpactChangeOfFunctionMaster impact = new ImpactChangeOfFunctionMaster();
			if(functionMaster.getShowImpactFlag()){
				impact = functionMasterShareService.detectListAffectedWhenModifyMaster(functionMaster, common, false);
			}
			if(impact.getImpactFlag()) {
				// redirect to Confirm Modify screen.
				functionMasterForm.setListOfBusinessDesign(impact.getLstUsedBusinessDesign());
				functionMasterForm.setListOfDecisionTable(impact.getLstUsedDecisionTable());
				model.addAttribute("formJson", DataTypeUtils.toJson(functionMaster));
				return VIEW_BLOGIC_MODIFY_LINK;
			} else {
				functionMasterService.modifyFunctionMaster(functionMaster, common);
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0010)));
				return SEARCH_REDIRECT_PATH;
			}
		}catch (BusinessException be) {
			if(CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", "1");
			}else{
				// add warning message : User choose file again.
				if(functionMasterForm.getFlagChangeFile() && functionMasterForm.getFile() != null && functionMasterForm.getFile().getSize() >0){
					functionMasterForm.setFileName("");
					functionMasterForm.setIsShowWarningFile(true);
				}
			}
			model.addAttribute("message", be.getResultMessages());
			return MODIFY_FORM_PATH;
		}
	}
	
	/**
	 * process modify function master
	 * 
	 * @param functionMasterForm
	 * @param result
	 * @param model
	 * @param redirectAttr
	 * @return SEARCH_REDIRECT_PATH
	 */
	@RequestMapping(value = "modify", method = RequestMethod.POST, params = "isJsonForm")
	public String processModify(FunctionMasterForm functionMasterForm, BindingResult result, Model model, RedirectAttributes redirectAttr, @Param("formJson")String formJson) {
		checkChangeProject(true);
		FunctionMaster functionMaster = DataTypeUtils.toObject(formJson, FunctionMaster.class);
		functionMaster.setProjectId(SessionUtils.getCurrentProjectId());
		try {
			CommonModel common = this.initCommon();
			common.setProjectId(SessionUtils.getCurrentProjectId());

			Long accountId = SessionUtils.getAccountId();
			functionMaster.setCreatedBy(accountId);
			functionMaster.setUpdatedBy(accountId);
			functionMasterService.modifyFunctionMaster(functionMaster, common);
		} catch (BusinessException be) {
			beanMapper.map(functionMaster, functionMasterForm);
			// add warning message : User choose file again.
			if(CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", "1");
			}else if(functionMaster.getFlagChangeFile() && functionMasterForm.getFile() != null && functionMasterForm.getFile().getSize() >0){
				functionMasterForm.setFileName("");
				functionMasterForm.setIsShowWarningFile(true);
			}
			model.addAttribute("message", be.getResultMessages());
			return MODIFY_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(FunctionMasterMessageConst.SC_FUNCTIONMASTER_0010)));
		SessionUtils.remove("functionMasterModify");
		return SEARCH_REDIRECT_PATH;
	}
	
	
	
	/**
	 * view function master
	 * 
	 * @param functionMasterForm
	 * @param model
	 * @return VIEW_FORM_PATH
	 */
	@TransactionTokenCheck(value = "delete", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String displayView(FunctionMasterForm functionMasterForm, Model model, RedirectAttributes redirectAttr) {
		checkChangeProject(false);
		String destination = "";
		try {
			FunctionMaster functionMaster = functionMasterService.loadFunctionMaster(functionMasterForm.getFunctionMasterId());
			functionMaster.setProjectId(SessionUtils.getCurrentProjectId());
			functionMasterForm = beanMapper.map(functionMaster, FunctionMasterForm.class);
			model.addAttribute(FUNCTION_MASTER_FORM_NAME, functionMasterForm);
			destination = VIEW_FORM_PATH;
		} catch (BusinessException ex) {
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			destination = SEARCH_REDIRECT_PATH;
		}
		return destination;
	}
	
	/**
	 * process delete function master
	 * @param functionMasterForm
	 * @param model
	 * @param redirectAttr
	 * @return REDIRECT_DELETION_SUCCESS
	 */
	@TransactionTokenCheck(value = "delete", type = TransactionTokenType.IN)
	@RequestMapping(value = "deleteConfirm", method = RequestMethod.POST)
	public String processDeleteConfirm(FunctionMasterForm functionMasterForm, Model model, RedirectAttributes redirectAttr) {
		FunctionMaster functionmaster = beanMapper.map(functionMasterForm, FunctionMaster.class);
		String destination = "";
		checkChangeProject(true);
		ImpactChangeOfFunctionMaster impact = new ImpactChangeOfFunctionMaster();
		CommonModel common = this.initCommon();
		try {
			if(Boolean.TRUE.equals(functionMasterForm.getShowImpactFlag())){
				impact = functionMasterShareService.detectListAffectedWhenDeleteMaster(functionmaster, common, false);
			}
			if(!impact.getImpactFlag()) {
				destination = processDelete(functionMasterForm, model, redirectAttr);
			} else {
				functionmaster.setListOfBusinessDesign(impact.getLstUsedBusinessDesign());
				functionmaster.setListOfDecisionTable(impact.getLstUsedDecisionTable());
				functionMasterForm = beanMapper.map(functionmaster, FunctionMasterForm.class);
				model.addAttribute("functionMasterForm",functionMasterForm);
				destination = REDIRECT_VIEW_BLOGIC_DELETE_LINK;
			}
		} catch(BusinessException ex){
			if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", "1");
			}
			model.addAttribute("message", ex.getResultMessages());
			destination = VIEW_FORM_PATH;
		}
		return destination;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String processDelete(FunctionMasterForm functionMasterForm, Model model, RedirectAttributes redirectAttr) {
		FunctionMaster functionmaster = beanMapper.map(functionMasterForm, FunctionMaster.class);
		checkChangeProject(true);
		try {
			CommonModel common = this.initCommon();
			common.setProjectId(SessionUtils.getCurrentProjectId());

			Long accountId = SessionUtils.getAccountId();
			functionmaster.setCreatedBy(accountId);
			functionmaster.setUpdatedBy(accountId);
			functionMasterService.delete(functionmaster, common);
		} catch(BusinessException ex){
			if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", "1");
			}
			model.addAttribute("message", ex.getResultMessages());
			return VIEW_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(CommonMessageConst.TQP_FUNCTIONMASTER)));
		return REDIRECT_DELETION_SUCCESS;
	}
	
	
	/**
	 * process download file
	 * 
	 * @param functionMasterForm
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "downloadfile", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> processDownloadFile(FunctionMasterForm functionMasterForm) {
		checkChangeProject(false);
		UploadFile uploadFile = uploadFileRepository.findOne(functionMasterForm.getUploadFileId());
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.set("Content-Disposition",  "attachment; filename=\"" + uploadFile.getFileName() + "\"");
	    return new ResponseEntity<byte[]>(uploadFile.getContent(), headers, HttpStatus.OK);
	}
}
