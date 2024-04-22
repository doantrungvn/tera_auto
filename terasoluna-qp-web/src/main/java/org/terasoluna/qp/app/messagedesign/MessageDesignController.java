package org.terasoluna.qp.app.messagedesign;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.apache.commons.lang3.ArrayUtils;
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
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.MessageDesignMessageConst;
import org.terasoluna.qp.app.messagedesign.MessageDesignForm.RegistrationForm;
import org.terasoluna.qp.app.messagedesign.MessageDesignSearchForm.ModificationMessageDesignForm;
import org.terasoluna.qp.app.messagedesign.MessageDesignSearchForm.SearchMessageDesignForm;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.model.LanguageDesign;
import org.terasoluna.qp.domain.model.MessageDesign;
import org.terasoluna.qp.domain.repository.messagedesign.MessageDesignCriteria;
import org.terasoluna.qp.domain.service.languagedesign.LanguageDesignService;
import org.terasoluna.qp.domain.service.messagedesign.MessageDesignService;

@Controller
@RequestMapping(value = "messagedesign")
@TransactionTokenCheck(value = "messagedesign")
@SessionAttributes(types = {MessageDesignSearchForm.class})
public class MessageDesignController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger( MessageDesignController.class);
	private static final String MESSAGE_DESIGN_FORM_NAME = "messageDesignForm";
	private static final String MESSAGE_DESIGN_SEARCH_FORM_NAME = "messageDesignSearchForm";
	private static final String SEARCH_FORM_PATH ="messagedesign/searchForm";
	private static final String SEARCH_ACTION_PATH ="/messagedesign/search";
	private static final String REGISTER_FORM_PATH = "messagedesign/registerForm";
	private static final String VIEW_FORM_PATH = "messagedesign/viewForm";
	private static final String SEARCH_REDIRECT_PATH = "redirect:/messagedesign/search";	
	//private static final String REDIRECT_DELETION_SUCCESS = "redirect:/complete";
	private static final String MODIFY_FORM_PATH = "messagedesign/modifyForm";	
	//private static final String VIEW_REDIRECT_PATH = "redirect:/messagedesign/view";
	//private static final String MODE_SEARCH = "0";
	private static final String MODE_VIEW = "1";
	
	//private static final String VIEW_LIST_AFFECTED_DELETED = "messagedesign/viewListAffectedChangeDesignDeleteForm";
	
	@Inject
	LanguageDesignService languageDesignService;
	
	@Inject
	MessageDesignService messageDesignService;
	
	@Inject
	Mapper beanMapper;
	
	@Inject
	MessageDesignValidator messageDesignValidator;
	
	@Inject
	ModifyMessageDesignValidator messageDesignSearchValidator;
	
	@InitBinder
	public void initService() {
		moduleCode = CommonMessageConst.TQP_MESSAGEDESIGN;
	}
	
	/**
	 * Identifies methods which initialize the WebDataBinder which will be used for populating command and form object
	 * @param webDataBinder WebDataBinder
	 */
	@InitBinder(value = {MESSAGE_DESIGN_FORM_NAME})
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(messageDesignValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@InitBinder(value = {MESSAGE_DESIGN_SEARCH_FORM_NAME})
	public void initSearchFormBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(messageDesignSearchValidator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute(MESSAGE_DESIGN_SEARCH_FORM_NAME)
	public MessageDesignSearchForm setUpMessageDesignSearchForm() {
		MessageDesignSearchForm messageDesignSearchForm = new MessageDesignSearchForm();
		logger.debug("Init form {0}", messageDesignSearchForm);
		return messageDesignSearchForm;
	}
	
	/**
	 * pre-initialization of form backed bean
	 * @return
	 */
	@ModelAttribute(MESSAGE_DESIGN_FORM_NAME)
	public MessageDesignForm setUpMessageDesignForm() {
		MessageDesignForm messageDesignForm = new MessageDesignForm();
		logger.debug("Init form {0}", messageDesignForm);
		return messageDesignForm;
	}
	
	/**
	 * return to search screen
	 * @param messageDesignSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search", method=RequestMethod.GET)
	public String displaySearch(@RequestParam(value = "init", required = false) String init, @ModelAttribute(MESSAGE_DESIGN_SEARCH_FORM_NAME) MessageDesignSearchForm messageDesignSearchForm,
		Model model, @PageableDefault Pageable pageable, SessionStatus sessionStatus) {
		LanguageDesign languageDesign;
		if(init != null || messageDesignSearchForm.getFromLanguageId() == null){
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
			sessionStatus.setComplete();
			messageDesignSearchForm = new MessageDesignSearchForm();
			model.addAttribute(MESSAGE_DESIGN_SEARCH_FORM_NAME, messageDesignSearchForm);
			// Setup base language base on current locale
			languageDesign = SessionUtils.getCurrentLanguageDesign();
			messageDesignSearchForm.setFromLanguageCode(languageDesign.getLanguageCode());
		} else {
			languageDesign = new LanguageDesign(messageDesignSearchForm.getFromLanguageCode(), null);
			languageDesign.setLanguageId(Long.parseLong(messageDesignSearchForm.getFromLanguageId()));
			languageDesign.setLanguageName(messageDesignSearchForm.getFromLanguageIdAutocomplete());
			languageDesign.setLanguageCode(messageDesignSearchForm.getFromLanguageCode());
		}
		/*
		checkChangeProject(false);
		try {
			param.setProjectId(messageDesignService.getWorkingProjectId());
			languageDesign = languageDesignService.findByLanguageDesign(param);
		} catch (BusinessException ex) {
			model.addAttribute(MESSAGE_DESIGN_SEARCH_FORM_NAME, messageDesignSearchForm);
			model.addAttribute("message", ex.getResultMessages());
			return SEARCH_FORM_PATH;
		}*/
		
		//Set base language
		messageDesignSearchForm.setFromLanguageCode(languageDesign.getLanguageCode());
		messageDesignSearchForm.setFromLanguageIdAutocomplete(languageDesign.getLanguageName());
		messageDesignSearchForm.setFromLanguageId(String.valueOf(languageDesign.getLanguageId()));
		messageDesignSearchForm.setProjectId(SessionUtils.getCurrentProjectId());

		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		MessageDesignCriteria messageDesignCriteria = beanMapper.map(messageDesignSearchForm, MessageDesignCriteria.class);
		Page<MessageDesign> page = messageDesignService.searchMessageDesign(messageDesignCriteria, pageable);
		ModifyMessageDesignForm[] modifyMessageDesignForm = null;
		
		if(page.getContent() != null && page.getContent().size() > 0) {
			for(int i = 0; i < page.getContent().size(); i++) {
				ModifyMessageDesignForm modifyForm = new ModifyMessageDesignForm();
				modifyForm.setFromMessageString(page.getContent().get(i).getFromMessageString());
				modifyForm.setToMessageString(page.getContent().get(i).getToMessageString());
				modifyMessageDesignForm = ArrayUtils.add(modifyMessageDesignForm, modifyForm);
			}
		}
		messageDesignSearchForm.setModifyMessageDesignForm(modifyMessageDesignForm);
		model.addAttribute("page", page);
		
		return SEARCH_FORM_PATH;
	}

	/**
	 * search message design
	 * @param messageDesignSearchForm
	 * @param model
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(@Validated({ SearchMessageDesignForm.class }) MessageDesignSearchForm messageDesignSearchForm, BindingResult result,Model model, @PageableDefault Pageable pageable, boolean errSaveSearch) {
		//checkChangeProject(false);
		if (result.hasErrors() && !errSaveSearch) {
			return SEARCH_FORM_PATH;
		}
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
		messageDesignSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
		MessageDesignCriteria messageDesignCriteria = beanMapper.map(messageDesignSearchForm, MessageDesignCriteria.class);
		Page<MessageDesign> page = messageDesignService.searchMessageDesign(messageDesignCriteria, pageable);
		ModifyMessageDesignForm[] modifyMessageDesignForm = null;
		
		if(errSaveSearch){
			for(int i = 0; i < messageDesignSearchForm.getModifyMessageDesignForm().length; i++) {
				page.getContent().get(i).setFromMessageString(messageDesignSearchForm.getModifyMessageDesignForm()[i].getFromMessageString());
				page.getContent().get(i).setFromSelected(messageDesignSearchForm.getModifyMessageDesignForm()[i].getFromSelected());
				page.getContent().get(i).setToMessageString(messageDesignSearchForm.getModifyMessageDesignForm()[i].getToMessageString());
				page.getContent().get(i).setToSelected(messageDesignSearchForm.getModifyMessageDesignForm()[i].getToSelected());
			}
		}else{
			for(int i = 0; i < page.getContent().size(); i++) {
				ModifyMessageDesignForm modifyForm = new ModifyMessageDesignForm();
				modifyForm.setFromMessageString(page.getContent().get(i).getFromMessageString());
				modifyForm.setToMessageString(page.getContent().get(i).getToMessageString());
				modifyMessageDesignForm = ArrayUtils.add(modifyMessageDesignForm, modifyForm);
			}
		}
		messageDesignSearchForm.setModifyMessageDesignForm(modifyMessageDesignForm);
		model.addAttribute("page", page);
		model.addAttribute("messageDesignSearchForm", messageDesignSearchForm);
		
		return SEARCH_FORM_PATH;
	}
	
	/**
	 * Initialize register business type screen
	 * @return REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(MessageDesignSearchForm messageDesignSearchForm, @ModelAttribute(MESSAGE_DESIGN_FORM_NAME) MessageDesignForm messageDesignForm, BindingResult result,Model model, @PageableDefault Pageable pageable) {
		try {
			checkChangeProject(true);
			messageDesignForm.setProjectId(SessionUtils.getCurrentProjectId());

			LanguageDesign languageDesign = SessionUtils.getCurrentLanguageDesign();
			messageDesignForm.setLanguageId(languageDesign.getLanguageId().toString());
			messageDesignForm.setLanguageIdAutocomplete(languageDesign.getLanguageName());
			messageDesignForm.setLanguageCode(languageDesign.getLanguageCode());
			messageDesignForm.setCountryCode(languageDesign.getCountryCode());

			model.addAttribute("messageDesignForm", messageDesignForm);
			return REGISTER_FORM_PATH;
		} catch (BusinessException be) {
			pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
			messageDesignSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
			MessageDesignCriteria messageDesignCriteria = beanMapper.map(messageDesignSearchForm, MessageDesignCriteria.class);
			Page<MessageDesign> page = messageDesignService.searchMessageDesign(messageDesignCriteria, pageable);
			ModifyMessageDesignForm[] modifyMessageDesignForm = new ModifyMessageDesignForm[page.getContent().size()];
			messageDesignSearchForm.setModifyMessageDesignForm(modifyMessageDesignForm);
			model.addAttribute("page", page);
			model.addAttribute("message", be.getResultMessages());
			return SEARCH_FORM_PATH;
		}
	}
	
	/**
	 * Register message design process
	 * @param MessageDesignForm business type information
	 * @param result BindingResult
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return in case of success SEARCH_REDIRECT_PATH will be returned.
	 *  Otherwise it will be returned to REGISTER_FORM_PATH
	 */
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.IN)
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(MessageDesignSearchForm messageDesignSearchForm, @Validated({ Default.class, RegistrationForm.class }) MessageDesignForm messageDesignForm, BindingResult result, Model model, RedirectAttributes redirectAttr, @PageableDefault Pageable pageable) {
		try {
			/*Long accountId = SessionUtils.getAccountId();*/
			checkChangeProject(true);
			
			if (result.hasErrors()) {
				return REGISTER_FORM_PATH;
			}
			Long accountId = SessionUtils.getAccountId();
			try {
				Long projectId = SessionUtils.getCurrentProjectId();
				MultipleMessageDesignForm[] multipleMessageDesignForms = messageDesignForm.getMultipleMessageDesignForm();
				List<MessageDesign> messageDesigns = new ArrayList<MessageDesign>();
				if (ArrayUtils.isNotEmpty(multipleMessageDesignForms)) {
					MessageDesign mesageDesign = null;
					ResultMessages resultMessages = ResultMessages.error();
					for(int i = 0; i< multipleMessageDesignForms.length; i++) {
						mesageDesign = new MessageDesign();
						mesageDesign.setMessageString(multipleMessageDesignForms[i].getMessageString());
						mesageDesign.setRemark(multipleMessageDesignForms[i].getRemark());
						mesageDesign.setCountryCode(messageDesignForm.getCountryCode());
						mesageDesign.setProjectId(projectId);
						mesageDesign.setLanguageCode(messageDesignForm.getLanguageCode());
						mesageDesign.setLanguageId(Long.parseLong( messageDesignForm.getLanguageId()));
						mesageDesign.setModuleCode(messageDesignForm.getModuleCode());
						mesageDesign.setModuleId(messageDesignForm.getModuleId());
						if(StringUtils.isBlank(mesageDesign.getModuleCode())) {
							mesageDesign.setMessageLevel(DbDomainConst.MessageLevel.PROJECT);
						} else {
							mesageDesign.setMessageLevel(DbDomainConst.MessageLevel.MODULE);
						}
						mesageDesign.setMessageType(multipleMessageDesignForms[i].getMessageType());
						mesageDesign.setAccountId(accountId);
						messageDesigns.add(mesageDesign);
						if (messageDesignService.checkMessageStringExist(mesageDesign)) {
							resultMessages.add(CommonMessageConst.ERR_SYS_0041, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0009), i+1);
						}
					}
					if (resultMessages.isNotEmpty()) {
						throw new BusinessException(resultMessages);
					}
					messageDesignService.registerMessageDesign(messageDesigns);
				}
				
			} catch (BusinessException be) {
				model.addAttribute("message", be.getResultMessages());
				return REGISTER_FORM_PATH;
			}
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0012)));
			
			return SEARCH_REDIRECT_PATH;
		} catch (BusinessException be) {
			pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction(SEARCH_ACTION_PATH), pageable.getSort());
			messageDesignSearchForm.setProjectId(SessionUtils.getCurrentProjectId());
			MessageDesignCriteria messageDesignCriteria = beanMapper.map(messageDesignSearchForm, MessageDesignCriteria.class);
			Page<MessageDesign> page = messageDesignService.searchMessageDesign(messageDesignCriteria, pageable);
			ModifyMessageDesignForm[] modifyMessageDesignForm = new ModifyMessageDesignForm[page.getContent().size()];
			messageDesignSearchForm.setModifyMessageDesignForm(modifyMessageDesignForm);
			model.addAttribute("page", page);
			model.addAttribute("message", be.getResultMessages());
			return SEARCH_FORM_PATH;
		}
	}
	
	/**
	 * Modify message design process on screen search
	 * @param messageForm MessageForm
	 * @param result BindingResult
	 * @param model Model
	 * @param redirectAttr RedirectAttributes
	 * @param pageable Pageable
	 * @return "redirect:search?init"
	 */
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify( @Validated({ ModificationMessageDesignForm.class }) @ModelAttribute MessageDesignSearchForm messageDesignSearchForm, BindingResult result, Model model, RedirectAttributes redirectAttr, @PageableDefault Pageable pageable) {
		
		if (result.hasErrors()) {
			return processSearch(messageDesignSearchForm,result, model, pageable, true);
		}
		try {
			checkChangeProject(true);
			Long accountId = SessionUtils.getAccountId();
			Long workingProjectId = SessionUtils.getCurrentProjectId();
			
			if (ArrayUtils.isNotEmpty(messageDesignSearchForm.getModifyMessageDesignForm())) {
				ModifyMessageDesignForm[] modifyMessageDesignForms = messageDesignSearchForm.getModifyMessageDesignForm();
				
				if (ArrayUtils.isNotEmpty(modifyMessageDesignForms)) {
					MessageDesign[] fromMessageDesigns = new MessageDesign[modifyMessageDesignForms.length];
					MessageDesign[] toMessageDesigns = new MessageDesign[modifyMessageDesignForms.length];
					
					int i = 0;
					for (ModifyMessageDesignForm form : modifyMessageDesignForms) {
						MessageDesign fromMessageDesign = new MessageDesign();
						fromMessageDesign.setMessageDesignId(form.getFromMessageDesignId());
						fromMessageDesign.setMessageString(form.getFromMessageString());
						fromMessageDesign.setUpdatedDate(form.getFromUpdatedDate());
						fromMessageDesign.setProjectId(workingProjectId);
//						if(form.getFromSelected()){
//							fromMessageDesign.setGeneratedStatus(DbDomainConst.MessageGeneratedStatus.MANUAL_TRANSLATE);
//						}else{
//							fromMessageDesign.setGeneratedStatus(DbDomainConst.MessageGeneratedStatus.Copy);
//						}
						fromMessageDesign.setSelected(form.getFromSelected());
						fromMessageDesign.setFromLanguageCode(form.getFromLanguageCode());
						fromMessageDesign.setMessageCode(form.getMessageCode());
						fromMessageDesign.setFromLanguageId(form.getFromLanguageId());
						fromMessageDesign.setAccountId(accountId);
						
						if (StringUtils.isNotBlank(messageDesignSearchForm.getToLanguageCode())) {
							MessageDesign toMessageDesign = new MessageDesign();
							toMessageDesign.setMessageDesignId(form.getToMessageDesignId());
							toMessageDesign.setMessageString(form.getToMessageString());
							toMessageDesign.setUpdatedDate(form.getToUpdatedDate());
//							if(form.getToSelected()){
//								toMessageDesign.setGeneratedStatus(DbDomainConst.MessageGeneratedStatus.MANUAL_TRANSLATE);
//							}else{
//								toMessageDesign.setGeneratedStatus(DbDomainConst.MessageGeneratedStatus.Copy);
//							}
							toMessageDesign.setSelected(form.getToSelected());
							toMessageDesign.setToLanguageCode(form.getToLanguageCode());
							toMessageDesign.setMessageCode(form.getMessageCode());
							toMessageDesign.setToLanguageId(form.getToLanguageId());
							toMessageDesign.setAccountId(accountId);
							toMessageDesign.setProjectId(workingProjectId);
							toMessageDesigns[i] = toMessageDesign;
						}
						fromMessageDesigns[i] = fromMessageDesign;
						i++;

						messageDesignSearchForm.setFromLanguageCode(form.getFromLanguageCode());
					}	
					MessageDesign[] wholeMessageDesign = null;
					if (StringUtils.isNotBlank(messageDesignSearchForm.getToLanguageCode())) {
						// Merge message design into one array
						wholeMessageDesign = ArrayUtils.addAll(fromMessageDesigns, toMessageDesigns);
					} else {
						wholeMessageDesign = ArrayUtils.addAll(fromMessageDesigns);
					}
					messageDesignService.modifyMessageDesign(accountId,wholeMessageDesign);
				}
			}
		} catch (BusinessException be) {
			model.addAttribute("message", be.getResultMessages());
			return processSearch(messageDesignSearchForm, result,model, pageable, false);
		}
		
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0012)));
		return SEARCH_REDIRECT_PATH;
	}
	
	/**
	 * View message design process
	 * @param messageDesignSearchForm MessageDesignSearchForm
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return VIEW_FORM_PATH
	 */
	@RequestMapping(value = "view")
	public String processView(MessageDesignForm messageDesignForm, Model model,RedirectAttributes redirectAttr) {
		checkChangeProject(false);
		MessageDesign messageDesign = beanMapper.map(messageDesignForm, MessageDesign.class);
		try {
			messageDesign.setProjectId(SessionUtils.getCurrentProjectId());
			CommonModel common = this.initCommon();
			common.setDesignStatus(false);
			messageDesign = messageDesignService.findAllMessageDesignByCode(messageDesign, DbDomainConst.NavigatorFrom.VIEW, common);
		} catch (BusinessException be) {
			messageDesignForm.setHasErrors(true);
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return SEARCH_REDIRECT_PATH;
		}
		model.addAttribute("messageDesign", messageDesign);
		return VIEW_FORM_PATH;
	}
	
	/**
	 * Delete message design process
	 * In case message design is in-used, Message design delete process will not be success
	 * @param messageDesignForm MessageDesignForm
	 * @param redirectAttr RedirectAttributes
	 * @param model Model
	 * @return SEARCH_FORM_PATH
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String processDelete(MessageDesignForm messageDesignForm, RedirectAttributes redirectAttr, Model model) {
		MessageDesign messageDesign = beanMapper.map(messageDesignForm, MessageDesign.class);
		try {
			checkChangeProject(true);
			
			//Long projectId = SessionUtils.getCurrentProjectId();
			messageDesign.setProjectId(SessionUtils.getCurrentProjectId());
			messageDesign.setAccountId(SessionUtils.getAccountId());
			messageDesignService.delete(messageDesign);
			
			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_MESSAGEDESIGN));
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0012)));
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
		} catch (BusinessException be) {
			/*model.addAttribute("message", be.getResultMessages());
			model.addAttribute("notExistFlg", "0");
			
			String errMessageCode = StringUtils.defaultString(be.getResultMessages().getList().get(0).getCode(), CommonMessageConst.ERR_SYS_0037);
			if(CommonMessageConst.ERR_SYS_0037.equals(errMessageCode)) {
				model.addAttribute("notExistFlg", "1");
			} else {
				messageDesign = messageDesignService.findAllMessageDesignByCode(messageDesign, DbDomainConst.NavigatorFrom.VIEW);
				model.addAttribute("messageDesign", messageDesign);	
			}

			return VIEW_FORM_PATH;*/

			redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_MESSAGEDESIGN));
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
			return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			
		}
	}
	
	/**
	 * Modify message design on screen modify
	 * @param messageDesignForm MessageDesignForm
	 * @param result BindingResult
	 * @param model Model
	 * @param redirectAttr RedirectAttributes
	 * @param pageable Pageable
	 * @return "redirect:search?init"
	 */
	@TransactionTokenCheck(value = "modifyByCode", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modifyByCode",  method = RequestMethod.GET)
	public String displayModify(@ModelAttribute(MESSAGE_DESIGN_FORM_NAME) MessageDesignForm messageDesignForm, Model model, RedirectAttributes redirectAttr) {
		String destination = StringUtils.EMPTY;
		MessageDesign messageDesign =  beanMapper.map(messageDesignForm, MessageDesign.class);
		try {
			checkChangeProject(true);
			messageDesign.setProjectId(SessionUtils.getCurrentProjectId());

			CommonModel common = this.initCommon();
			common.setDesignStatus(true);

			messageDesign = messageDesignService.findAllMessageDesignByCode(messageDesign, DbDomainConst.NavigatorFrom.MODIFY, common);
			messageDesignForm = beanMapper.map(messageDesign, MessageDesignForm.class);
			model.addAttribute("messageDesignForm", messageDesignForm);
			destination = MODIFY_FORM_PATH;

		} catch (BusinessException be) {
			/*messageDesign = messageDesignService.findAllMessageDesignByCode(messageDesign, DbDomainConst.NavigatorFrom.MODIFY);
			model.addAttribute("messageDesign", messageDesign);*/
			model.addAttribute("notExistFlg", 1);
			if (MODE_VIEW.equals(messageDesignForm.getMode())) {
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_MESSAGEDESIGN)); // message title
				redirectAttr.addFlashAttribute("message", be.getResultMessages());// message content
				return DbDomainConst.REDIRECT_DELETION_SUCCESS;
			}

			destination = SEARCH_REDIRECT_PATH;
			redirectAttr.addFlashAttribute("message", be.getResultMessages());
		}
		return destination;
	}
	
	/**
	 * Modify message design process on screen modify
	* @param messageDesignForm MessageDesignForm
	 * @param result BindingResult
	 * @param model Model
	 * @param redirectAttr RedirectAttributes
	 * @param pageable Pageable
	 * @return "redirect:search?init"
	 */
	@TransactionTokenCheck(value = "modifyByCode", type = TransactionTokenType.IN)
	@RequestMapping(value = "modifyByCode", method = RequestMethod.POST)
	public String processModify(MessageDesignSearchForm messageDesignSearchForm, @Validated MessageDesignForm messageDesignForm, BindingResult result, RedirectAttributes redirectAttr, Model model, @PageableDefault Pageable pageable) {
		// MessageDesign messageDesign = beanMapper.map(messageDesignForm, MessageDesign.class);

		if (result.hasErrors()) {
			return MODIFY_FORM_PATH;
		}
		try {
			checkChangeProject(true);
			Long accountId = SessionUtils.getAccountId();
			Long projectId = SessionUtils.getCurrentProjectId();

			if (DataTypeUtils.notEquals(projectId, messageDesignForm.getProjectId())) {
				throw new BusinessException(ResultMessages.error().add(CommonMessageConst.ERR_SYS_0074));
			}

			MultipleMessageDesignForm[] multipleMessageDesignForms = messageDesignForm.getMultipleMessageDesignForm();
			if (ArrayUtils.isNotEmpty(multipleMessageDesignForms)) {
				MessageDesign[] messageDesigns = new MessageDesign[multipleMessageDesignForms.length];
				int i = 0;
				ResultMessages resultMessages = ResultMessages.error();

				for (MultipleMessageDesignForm form : multipleMessageDesignForms) {
					form.setRemark(messageDesignForm.getRemark());
					form.setMessageType(messageDesignForm.getMessageType());
					form.setMessageLevel(messageDesignForm.getMessageLevel());
					form.setModuleId(messageDesignForm.getModuleId());
					form.setProjectId(projectId);
					/* form.setLanguageId(Long.valueOf(messageDesignForm.getLanguageId())); */

					MessageDesign mesageDesign = beanMapper.map(form, MessageDesign.class);
					mesageDesign.setGeneratedStatus(DbDomainConst.MessageGeneratedStatus.MANUAL_TRANSLATE);
					mesageDesign.setUpdatedBy(accountId);

					if (messageDesignService.checkMessageStringExist(mesageDesign)) {
						resultMessages.add(CommonMessageConst.ERR_SYS_0041, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0009), i + 1);
					}

					messageDesigns[i++] = mesageDesign;
				}

				if (resultMessages.isNotEmpty()) {
					throw new BusinessException(resultMessages);
				}

				messageDesignService.modifyMultipleMessage(messageDesigns);
			}
		} catch (BusinessException be) {
			/*
			 * if(CommonMessageConst.ERR_SYS_0037.equals(be.getResultMessages().getList().get(0).getCode())) { model.addAttribute("notExistFlg", 1); }
			 */
			model.addAttribute("message", be.getResultMessages());
			return MODIFY_FORM_PATH;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(MessageDesignMessageConst.SC_MESSAGEDESIGN_0012)));
		return SEARCH_REDIRECT_PATH;
	}
}
