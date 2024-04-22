package org.terasoluna.qp.app.librarymanagement;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
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
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.LibraryManagementMessageConst;
import org.terasoluna.qp.domain.model.AccountProfile;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.LibraryManagement;
import org.terasoluna.qp.domain.repository.librarymanagement.LibraryManagementSearchCriteria;
import org.terasoluna.qp.domain.service.common.SystemService;
import org.terasoluna.qp.domain.service.librarymanagement.LibraryManagementService;

@Controller
@RequestMapping(value = "librarymanagement")
@TransactionTokenCheck(value = "librarymanagement")
@SessionAttributes(types = { LibraryManagementSearchForm.class })
public class LibraryManagementController extends BaseController {

	private static final String SEARCH_FORM_PATH = "librarymanagement/searchForm";

	private static final String REGISTER_FORM_PATH = "librarymanagement/registerForm";

	private static final String MODIFY_FORM_PATH = "librarymanagement/modifyForm";
	
	private static final String VIEW_FORM_PATH = "librarymanagement/viewForm";
	
	private static final String SEARCH_REDIRECT_PATH = "redirect:/librarymanagement/search";

	private static final String SEARCH_ACTION_PATH = "/librarymanagement/search";
	
	private static final String LIBRARY_MANAGEMENT_SEARCH_FORM_NAME = "libraryManagementSearchForm";

	private static final String LIBRARY_MANAGEMENT_FORM_NAME = "libraryManagementForm";
	
	private static final String REDIRECT_DELETECOMPLETE = "redirect:/complete";
	
	private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";
	
	@Inject
	SystemService systemService;

	@Inject
	Mapper beanMapper;
	
	@Inject
	LibraryManagementService libraryManagementService;
	
	@Inject
	LibraryManagementValidator libraryManagementValidator;
	
	@InitBinder(LIBRARY_MANAGEMENT_FORM_NAME)
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		webDataBinder.addValidators(libraryManagementValidator);
	}
	
	@ModelAttribute
	public void init(){
		moduleCode = CommonMessageConst.TQP_LIBRARYMANAGEMENT;
	}
	
	@ModelAttribute
	public LibraryManagementSearchForm setUpFormCriteria() {
		LibraryManagementSearchForm form = new LibraryManagementSearchForm();
		return form;
	}
	
	/**
	 * Initialize search library management
	 * 
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String displaySearch(
			@RequestParam(value = "init", required = false) String init,
			@ModelAttribute LibraryManagementSearchForm libraryManagementSearchForm,
			Model model, @PageableDefault Pageable pageable,
			SessionStatus sessionStatus) {
		Long projectId = SessionUtils.getCurrentProjectId();
		if (init != null) {
			sessionStatus.setComplete();
			libraryManagementSearchForm = new LibraryManagementSearchForm();
			model.addAttribute(LIBRARY_MANAGEMENT_SEARCH_FORM_NAME,
					libraryManagementSearchForm);
			super.setOldProjectId(projectId);
		}
		checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		libraryManagementSearchForm.setProjectId(projectId);
		LibraryManagementSearchCriteria librarySearchCriteria = beanMapper.map( libraryManagementSearchForm,  LibraryManagementSearchCriteria.class);
		Page<LibraryManagement> page = libraryManagementService.findPageByCriteria(librarySearchCriteria, pageable);
		model.addAttribute("page", page);
		
		return SEARCH_FORM_PATH;
	}

	/**
	 * Search library management process
	 * 
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "search", method = { RequestMethod.POST })
	public String processSearch(LibraryManagementSearchForm libraryManagementSearchForm, Model model, SessionStatus sessionStatus,@PageableDefault Pageable pageable) {
		
		this.checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		libraryManagementSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		LibraryManagementSearchCriteria librarySearchCriteria = beanMapper.map( libraryManagementSearchForm,  LibraryManagementSearchCriteria.class);
		Page<LibraryManagement> page = libraryManagementService.findPageByCriteria(librarySearchCriteria, pageable);
		beanMapper.map(librarySearchCriteria, libraryManagementSearchForm);
		model.addAttribute("page", page);

		return SEARCH_FORM_PATH;
	}

	/**
	 * Initialize register common object definition
	 *
	 * @return COMMON_REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = { RequestMethod.GET })
	public String displayRegister(
			@ModelAttribute(LIBRARY_MANAGEMENT_FORM_NAME) LibraryManagementForm libraryManagementForm,
			Model model) {
		super.checkChangeProject(true);
		AccountProfile accountProfile = systemService.getDefaultProfile();
		libraryManagementForm.setMaxSize(accountProfile.getMaxSizeUpload());
		return REGISTER_FORM_PATH;
	}

	/**
	 * Process register common object definition
	 *
	 * @return SEARCH_REDIRECT_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = { RequestMethod.POST })
	public String processRegister(
			@Validated LibraryManagementForm libraryManagementForm,
			BindingResult bindingResult, RedirectAttributes redirectAttr, Model model) {
		
		super.checkChangeProject(true);
		libraryManagementForm.setProjectId(SessionUtils.getCurrentProjectId());
		
		LibraryManagement library = beanMapper.map(libraryManagementForm, LibraryManagement.class);
		if(libraryManagementForm.getUploadFileContent()!=null ){
			String fileName = libraryManagementForm.getUploadFileContent().getOriginalFilename();
			if (StringUtils.isNotEmpty(fileName)) {
				int idx = fileName.replace("\\", "/").lastIndexOf("/");
				fileName = idx >= 0 ? fileName.substring(idx + 1) : fileName;
			}
			library.setUploadFileName(fileName);
		}
		library.setCreatedBy(SessionUtils.getAccountId());
		if (bindingResult.hasErrors()) {
			if (libraryManagementForm.getUploadFileContent() != null && libraryManagementForm.getUploadFileContent().getSize() >0) {
				// add warning message : User choose file again.
				libraryManagementForm.setUploadFileName("");
				model.addAttribute("libraryManagementForm", libraryManagementForm);
			}
			//ValidationUtils.setBindingResult(bindingResult, model);
			return REGISTER_FORM_PATH;
		}
		if (libraryManagementForm.getUploadFileContent() != null && libraryManagementForm.getUploadFileContent().getSize() >0 ) {
			String fileName = FilenameUtils.getName(libraryManagementForm.getUploadFileContent().getOriginalFilename());
			library.setUploadFileName(fileName);
		}
		try {
			CommonModel common = this.initCommon();
			
			Long accountId = SessionUtils.getAccountId();
			library.setCreatedBy(accountId);
			library.setUpdatedBy(accountId);
			libraryManagementService.registerLibrary(library,common);
		} catch (BusinessException ex) {
			if(libraryManagementForm.getUploadFileContent() != null && libraryManagementForm.getUploadFileContent().getSize() >0){
				// add warning message : User choose file again.
				libraryManagementForm.setUploadFileName("");
			}
			model.addAttribute("message", ex.getResultMessages());
			return REGISTER_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message",ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0013)));
		return SEARCH_REDIRECT_PATH;
	}

	/**
	 * Initialize modify common object definition
	 *
	 * @return COMMON_MODIFY_FORM_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(@RequestParam(value = "mode", required = false) String mode, LibraryManagementForm libraryManagementForm, Model model,
			RedirectAttributes redirectAttr) {
		super.checkChangeProject(true);
		String destination = StringUtils.EMPTY;

		try {			
			LibraryManagement libraryManagement = libraryManagementService.findLibraryManagement(libraryManagementForm.getLibraryId());
			libraryManagementForm = beanMapper.map(libraryManagement, LibraryManagementForm.class);
			model.addAttribute(LIBRARY_MANAGEMENT_FORM_NAME, libraryManagementForm);
			destination = MODIFY_FORM_PATH;
			AccountProfile accountProfile = systemService.getDefaultProfile();
			libraryManagementForm.setMaxSize(accountProfile.getMaxSizeUpload());
		} catch (BusinessException be) {
			model.addAttribute("notExistFlg", 1);
			if (MODE_SEARCH.equals(mode)) {
				redirectAttr.addFlashAttribute("message", be.getResultMessages());
				destination = SEARCH_REDIRECT_PATH;
			} else if (MODE_VIEW.equals(mode)) {
				model.addAttribute("message", be.getResultMessages());
				destination = VIEW_FORM_PATH;
			}

		}
		return destination;
	}

	/**
	 * Process modify common object definition
	 *
	 * @return SEARCH_REDIRECT_PATH
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processSaveLibrary(
			@Validated @ModelAttribute LibraryManagementForm libraryManagemenForm,
			BindingResult bindingResult, RedirectAttributes redirectAttr,
			Model model) {

		checkChangeProject(true);
		if (bindingResult.hasErrors()) {
			if (libraryManagemenForm.getUploadFileContent() != null && libraryManagemenForm.getUploadFileContent().getSize() >0) {
				// add warning message : User choose file again.
					libraryManagemenForm.setUploadFileName("");
				model.addAttribute("libraryManagementForm", libraryManagemenForm);
			}
			return MODIFY_FORM_PATH;
		}
		libraryManagemenForm.setProjectId(SessionUtils.getCurrentProjectId());
		LibraryManagement libraryManagement = beanMapper.map(libraryManagemenForm, LibraryManagement.class);
		if(libraryManagemenForm.getUploadFileContentChange()){
			if(libraryManagemenForm.getUploadFileContent()!=null){	
				String fileName = FilenameUtils.getName(libraryManagemenForm.getUploadFileContent().getOriginalFilename());
				libraryManagement.setUploadFileName(fileName);
			}
		} 
		libraryManagement.setUpdatedBy(SessionUtils.getAccountId());
		
		try{
			CommonModel common = this.initCommon();
			Long accountId = SessionUtils.getAccountId();
			libraryManagement.setUpdatedBy(accountId);
			
			libraryManagementService.modifyLibrary(libraryManagement,common);
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0013)));
			return SEARCH_REDIRECT_PATH;
		} catch(BusinessException ex){

			if(CommonMessageConst.ERR_SYS_0037.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("notExistFlg", "1");
			}
			if(libraryManagemenForm.getUploadFileContent() != null && libraryManagemenForm.getUploadFileContent().getSize() >0){
				libraryManagemenForm.setUploadFileName("");
			}
			if(CommonMessageConst.ERR_SYS_0048.equals(ex.getResultMessages().getList().get(0).getCode())) {
				model.addAttribute("message", ex.getResultMessages());
				return MODIFY_FORM_PATH;
			}
			else {
				model.addAttribute("message", ex.getResultMessages());
				return MODIFY_FORM_PATH;
			}
		}
	}

	
	/**
	 * return view screen
	 * 
	 * @return
	 */
	@RequestMapping(value = "view")
	public String displayView(LibraryManagementForm libraryManagementForm, Model model, RedirectAttributes redirectAttr) {
		
		String destination = "";
		try {
			LibraryManagement libraryManagement = libraryManagementService.findLibraryManagement(libraryManagementForm.getLibraryId());
			libraryManagementForm = beanMapper.map(libraryManagement, LibraryManagementForm.class);
			model.addAttribute(LIBRARY_MANAGEMENT_FORM_NAME, libraryManagementForm);
			super.checkChangeProject(false);
			destination = VIEW_FORM_PATH;
		} catch (BusinessException ex) {
			model.addAttribute("notExistFlg", 1);
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			destination =  SEARCH_REDIRECT_PATH;			
		}
		return destination;
	}
	
	@RequestMapping(value = "delete")
	public String processDelete(LibraryManagementForm libraryManagementForm, RedirectAttributes redirectAttr, Model model) {
		checkChangeProject(true); 

		LibraryManagement libraryManagement = beanMapper.map(libraryManagementForm, LibraryManagement.class);
		try {
			CommonModel common = this.initCommon();
			Long accountId = SessionUtils.getAccountId();
			libraryManagement.setUpdatedBy(accountId);
			
			libraryManagement = libraryManagementService.deleteLibrary(libraryManagement,common);
			if (libraryManagement == null) {
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004,  MessageUtils.getMessage(LibraryManagementMessageConst.SC_LIBRARYMANAGEMENT_0013)));
				return REDIRECT_DELETECOMPLETE;
			}

		} catch (BusinessException ex) {
			model.addAttribute("message", ex.getResultMessages());
			model.addAttribute("notExistFlg", "0");
			String errMessageCode = StringUtils.defaultString(ex.getResultMessages().getList().get(0).getCode(), CommonMessageConst.ERR_SYS_0037);
			if(CommonMessageConst.ERR_SYS_0037.equals(errMessageCode) || CommonMessageConst.ERR_SYS_0111.equals(errMessageCode) ) {
				model.addAttribute("notExistFlg", "1");
			}
		}

		return VIEW_FORM_PATH;
	}
}
