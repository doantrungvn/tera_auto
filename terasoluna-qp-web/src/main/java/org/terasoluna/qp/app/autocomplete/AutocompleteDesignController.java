package org.terasoluna.qp.app.autocomplete;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.DataTypeUtils;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.PermissionUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.AutocompleteMessageConst;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.SqlDesignMessageConst;
import org.terasoluna.qp.domain.model.AutocompleteDesign;
import org.terasoluna.qp.domain.model.CommonModel;
import org.terasoluna.qp.domain.repository.autocomplete.AutocompleteSearchCriteria;
import org.terasoluna.qp.domain.service.autocomplete.AutocompleteDesignCompound;
import org.terasoluna.qp.domain.service.autocomplete.AutocompleteDesignService;
import org.terasoluna.qp.domain.service.autocomplete.AutocompleteDesignShareService;
import org.terasoluna.qp.domain.service.autocomplete.ImpactChangeOfAutocompleteDesign;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst.SQLDesignType;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignService;


/**
 * @author anlt
 * The Class AutocompleteDesignController.
 */
@Controller
@RequestMapping(value="autocomplete")
@TransactionTokenCheck(value = "autocomplete")
@SessionAttributes(types=AutocompleteSearchForm.class)
public class AutocompleteDesignController extends BaseController {

	private final String FORWARD_VIEW = "forward:/autocomplete/view";

	private final String FORWARD_VIEW_DELETE_AFFECTION = "forward:/autocomplete/deleteConfirm";
	
	private final String REDIRECT_SEARCH = "redirect:/autocomplete/search";
	
	private final String FORWARD_DELETE = "forward:/autocomplete/delete";
	
	private final String FORM_MODIFY = "autocomplete/modifyForm";
	
	private final String FORM_MODIFY_AFFECTION = "autocomplete/modifyFormChangeAffection"; 
	
	private final String FORM_MODIFY_ADVANCED = "autocomplete/modifyAdvancedForm";
	
	private final String FORM_MODIFY_ADVANCED_AFFECTION = "autocomplete/modifyAdvancedFormChangeAffection";
	
	private final String FORM_SEARCH = "autocomplete/searchForm";
	
	private final String FORM_REGISTER = "autocomplete/registerForm";
	
	private final String FORM_REGISTER_ADVANCED = "autocomplete/registerAdvancedForm";
	
	private final String FORM_VIEW = "autocomplete/viewForm";
	
	private final String FORM_VIEW_ADVANCED = "autocomplete/viewAdvancedForm";
	
	private final String FORM_VIEW_DELETE_AFFECTION = "autocomplete/viewFormDeleteAffection";
	
	private final String MODE_SEARCH = "0";
	
	private final String MODE_VIEW = "1";
	
	@Inject
	Mapper beanMapper;
	
	@Inject
	AutocompleteDesignService autocompleteDesignService;
	
	@Inject
	SqlDesignService sqlDesignService;
	
	@Inject 
	AutocompleteDesignFormValidator autocompleteDesignFormValidator;
	
	@Inject 
	AutocompleteAdvancedDesignFormValidator autocompleteAdvancedDesignFormValidator;
	
	@Inject
	AutocompleteDesignShareService autocompleteDesignShareService;
	
	private final String ADVANCED_DESIGN_FORM_NAME = "autocompleteAdvancedDesignForm";
	
	private final String SEARCH_FORM_NAME = "autocompleteSearchForm";
	
	
	private final String DESIGN_FORM_NAME = "autocompleteDesignForm";
	
	/**
	 * Delete autocomplete.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(AutocompleteDesignForm form, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		this.checkChangeProject(true);
		CommonModel common = this.initCommon();
		String destination = null;
		AutocompleteDesignCompound autocompleteDesignCompound = beanMapper.map(form, AutocompleteDesignCompound.class);
		boolean isDeleted=false;
		if(PermissionUtils.deleteObjectHasFk()){
			try{
				isDeleted = autocompleteDesignService.delete(autocompleteDesignCompound, common);
				if(isDeleted) {
					redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0001)));
					redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_AUTOCOMPLETE));
					destination = DbDomainConst.REDIRECT_DELETION_SUCCESS;
				}
				else {
					redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_AUTOCOMPLETE));
					redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.ERR_SYS_0073, MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0001)));
					/*redirectAttr.addAttribute("autocompleteForm.autocompleteId", form.getAutocompleteForm().getAutocompleteId());*/
					destination = DbDomainConst.REDIRECT_DELETION_SUCCESS;
				}
			} catch(BusinessException ex) {
				//model.addAttribute("message",ex.getResultMessages());
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_AUTOCOMPLETE));
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				destination = DbDomainConst.REDIRECT_DELETION_SUCCESS;
			}
		} else {
			destination = FORWARD_VIEW;
			autocompleteDesignService.findAllDeletionAffection(autocompleteDesignCompound, common);
			if(CollectionUtils.isEmpty(autocompleteDesignCompound.getAffectedScreenDesigns())){
				model.addAttribute("message", ResultMessages.error().add(CommonMessageConst.ERR_SYS_0097));
				
			} else {
				model.addAttribute("message", ResultMessages.error().add(CommonMessageConst.ERR_SYS_0097));
				destination = FORWARD_VIEW_DELETE_AFFECTION;
			}
		}
		
		return destination;
	}
	
	
	@RequestMapping(value = "deleteConfirm", method = {RequestMethod.GET,RequestMethod.POST})
	public String deleteConfirm(AutocompleteDesignForm form, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		this.checkChangeProject(true);
		CommonModel common = this.initCommon();
		AutocompleteDesignCompound sqlDesignCompound = beanMapper.map(form, AutocompleteDesignCompound.class);
		String destination = null;
		if(sqlDesignCompound.getActionDelete()) {
			try{
				sqlDesignCompound = autocompleteDesignService.findCompoundById(sqlDesignCompound.getAutocomplete().getAutocompleteId());
				//autocompleteDesignService.findAllDeletionAffection(sqlDesignCompound, common);
				boolean isNotShowImpact = form.getShowImpactFlag() == null || form.getShowImpactFlag() == false;
				if(isNotShowImpact){
					destination = FORWARD_DELETE;
				} else {
					ImpactChangeOfAutocompleteDesign impact = checkShowImpactWhenDeleteConfirm(sqlDesignCompound.getAutocomplete(), common);
					if(impact == null){
						return FORWARD_DELETE;
					}
					beanMapper.map(sqlDesignCompound, form);
					model.addAttribute("affectedScreenDesgins",impact.getScreenDesignsImpacted());
					model.addAttribute("affectedDomainDesigns",impact.getDomainDesignsImpacted());
					model.addAttribute("affectedTableDesigns",impact.getTableDesignsImpacted());
					destination = FORM_VIEW_DELETE_AFFECTION;
				}
			} catch(BusinessException ex){
				/*model.addAttribute("message",ex.getResultMessages());
				form.getAutocompleteForm().setAutocompleteId(null);
				return FORM_VIEW;*/
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_MESSAGEDESIGN)); //message title
                redirectAttr.addFlashAttribute("message", ex.getResultMessages());//message content
                return DbDomainConst.REDIRECT_DELETION_SUCCESS;

			}
		}
		else {
			try {
				sqlDesignCompound.getAutocomplete().setDesignStatus(form.getDesignStatus());
				autocompleteDesignService.modifyDesignStatus(sqlDesignCompound, common);
				
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003,
						MessageUtils.getMessage(CommonMessageConst.TQP_AUTOCOMPLETE)));
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_AUTOCOMPLETE));
				return DbDomainConst.REDIRECT_MODIFY_SUCCESS;
			} catch(BusinessException ex) {
				/*redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				redirectAttr.addAttribute("openOwner", 1);
				redirectAttr.addFlashAttribute(form);*/
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_AUTOCOMPLETE));
				return DbDomainConst.REDIRECT_MODIFY_SUCCESS;
			}
		}
		return destination;
	}
	
	private ImpactChangeOfAutocompleteDesign checkShowImpactWhenDeleteConfirm(AutocompleteDesign autocomplete, CommonModel common){
		ImpactChangeOfAutocompleteDesign impact = autocompleteDesignShareService.detectListAffectedWhenDelete(autocomplete, common, false);
		boolean checkShowImpact = CollectionUtils.isNotEmpty(impact.getDomainDesignsImpacted())
					|| CollectionUtils.isNotEmpty(impact.getScreenDesignsImpacted()) 
					|| CollectionUtils.isNotEmpty(impact.getTableDesignsImpacted());
		if(checkShowImpact){
			return impact;
		}
		return null;
	}
	
	private ImpactChangeOfAutocompleteDesign checkShowImpactWhenModifyConfirm(AutocompleteDesign autocomplete, CommonModel common){
		ImpactChangeOfAutocompleteDesign impact = autocompleteDesignShareService.detectListAffectedWhenModify(autocomplete.getAutocompleteId(), common, false);
		boolean checkShowImpact = CollectionUtils.isNotEmpty(impact.getDomainDesignsImpacted())
					|| CollectionUtils.isNotEmpty(impact.getScreenDesignsImpacted()) 
					|| CollectionUtils.isNotEmpty(impact.getTableDesignsImpacted());
		if(checkShowImpact){
			return impact;
		}
		return null;
	}
	
	/**
	 * Display modification screen.
	 *
	 * @param form the form
	 * @param model the model
	 * @param redirectAttr the redirect attr
	 * @return the string
	 */
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(AutocompleteDesignForm form, Model model, RedirectAttributes redirectAttr) {
		this.checkChangeProject(true);
		String destination = null;
		AutocompleteDesign autocompleteDesign = beanMapper.map(form.getAutocompleteForm(), AutocompleteDesign.class);
		AutocompleteDesignCompound autocompleteDesignCompound = null;
		try {
			autocompleteDesignCompound = autocompleteDesignService.findCompoundById(autocompleteDesign.getAutocompleteId());
			if(autocompleteDesignCompound.getSqlDesign().getDesignType()== SQLDesignType.AUTOCOMPLETE){
				form = beanMapper.map(autocompleteDesignCompound,AutocompleteDesignForm.class);
				model.addAttribute(DESIGN_FORM_NAME,form);
				destination = FORM_MODIFY;
			} else if(autocompleteDesignCompound.getSqlDesign().getDesignType()== SQLDesignType.ADVANCED_AUTOCOMPLETE){
				model.addAttribute(ADVANCED_DESIGN_FORM_NAME,beanMapper.map(autocompleteDesignCompound,AutocompleteAdvancedDesignForm.class));
				model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
				destination = FORM_MODIFY_ADVANCED;
			}
		} catch(BusinessException ex){
			if(MODE_SEARCH.equals(form.getMode())) {
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				destination = REDIRECT_SEARCH;
			} else if(MODE_VIEW.equals(form.getMode())) {
				model.addAttribute("message", ex.getResultMessages());
				model.addAttribute(DESIGN_FORM_NAME, null);
				destination = FORM_VIEW;
			}
		} 
		return destination;
	}
	
	/**
	 * Display autocomplete register screen
	 *
	 * @param form the form
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value="register")
	@TransactionTokenCheck(value = "register", type = TransactionTokenType.BEGIN)
	public String displayRegister(AutocompleteDesignForm form, Model model) {
		this.checkChangeProject(true);
		return FORM_REGISTER;
	}


	/**
	 * Display register advanced.
	 *
	 * @param form the form
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value="registerAdvanced")
	@TransactionTokenCheck(value = "registerAdvanced", type = TransactionTokenType.BEGIN)
	public String displayRegisterAdvanced(AutocompleteAdvancedDesignForm form, Model model) {
		this.checkChangeProject(true);
		model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
		return FORM_REGISTER_ADVANCED;
	}
	
	/**
	 * Display autocomplete search screen.
	 *
	 * @param form the form
	 * @param model the model
	 * @param sessionStatus the session status
	 * @param pageable the pageable
	 * @return the string
	 */
	@RequestMapping(value="search")
	public String displaySearch(@RequestParam(value="init",required=false)String init,AutocompleteSearchForm form,Model model,SessionStatus sessionStatus,@PageableDefault Pageable pageable) {
		if(init != null){
			sessionStatus.setComplete();
			form = new AutocompleteSearchForm();
			model.addAttribute(SEARCH_FORM_NAME, form);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		super.checkChangeProject(false);
		CommonModel common = this.initCommon();
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/autocomplete/search"), pageable.getSort());
		AutocompleteSearchCriteria criteria = beanMapper.map(form, AutocompleteSearchCriteria.class);
		Page<AutocompleteDesign> page=autocompleteDesignService.findPageByCriteria(criteria, pageable, common);
		//beanMapper.map(criteria, form);
		model.addAttribute("page", page);
		return FORM_SEARCH;
	}
	
	/**
	 * Display view screen.
	 *
	 * @param form the form
	 * @param model the model
	 * @param redirectAttr the redirect attr
	 * @return the string
	 */
	@TransactionTokenCheck(value = "view", type = TransactionTokenType.BEGIN)
	@RequestMapping(value = "view", method = {RequestMethod.GET,RequestMethod.POST})
	public String displayView(AutocompleteDesignForm form, Model model, RedirectAttributes redirectAttr) {
		this.checkChangeProject(false);
		String destination = null;
		AutocompleteDesignCompound autocompleteDesignCompound = beanMapper.map(form, AutocompleteDesignCompound.class);
		String openOwner = form.getOpenOwner();
		try {
			autocompleteDesignCompound = autocompleteDesignService.findCompoundById(autocompleteDesignCompound.getAutocomplete().getAutocompleteId());
			if(autocompleteDesignCompound.getSqlDesign().getDesignType() == SQLDesignType.AUTOCOMPLETE){
				beanMapper.map(autocompleteDesignCompound, form);
				form.setOpenOwner(openOwner);
				destination = FORM_VIEW;
			} else if(autocompleteDesignCompound.getSqlDesign().getDesignType() == SQLDesignType.ADVANCED_AUTOCOMPLETE) {
				AutocompleteAdvancedDesignForm advancedForm = beanMapper.map(autocompleteDesignCompound, AutocompleteAdvancedDesignForm.class);
				advancedForm.setShowImpactFlag(form.getShowImpactFlag());
				advancedForm.setOpenOwner(openOwner);
				model.addAttribute(ADVANCED_DESIGN_FORM_NAME,advancedForm);
				destination = FORM_VIEW_ADVANCED;
			}
		} catch(BusinessException ex){
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			destination = REDIRECT_SEARCH;
		}
		return destination;
	}
	@ModelAttribute
	public void init() {
		moduleCode = CommonMessageConst.TQP_AUTOCOMPLETE;
	}
	/**
	 * Inits the binder for {@link AutocompleteDesignController#DESIGN_FORM_NAME}
	 *
	 * @param webDataBinder the web data binder for form bean
	 */
	@InitBinder(DESIGN_FORM_NAME)
	public void initBinderDesignForm(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(autocompleteDesignFormValidator);
		// register characters trimmer for String-type field in form bean
	 	webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
	
	/**
	 * Inits the binder for {@link AutocompleteAdvancedDesignController#ADVANCED_DESIGN_FORM_NAME}
	 *
	 * @param webDataBinder the web data binder for form bean
	 */
	@InitBinder(ADVANCED_DESIGN_FORM_NAME)
	public void initBinderForAdvancedDesignForm(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(autocompleteAdvancedDesignFormValidator);
		// register characters trimmer for String-type field in form bean
	 	webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
	/**
	 * Process modify autocomplete.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value="modify",method={RequestMethod.POST})
	@TransactionTokenCheck(value="modify",type=TransactionTokenType.IN)
	public String processModify(@Validated AutocompleteDesignForm form, BindingResult result, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		this.checkChangeProject(true);
		CommonModel common = this.initCommon();
		String destination="";
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_MODIFY;
		}
		AutocompleteDesignCompound autocomplete = beanMapper.map(form, AutocompleteDesignCompound.class);
		if(BooleanUtils.isTrue(form.getSqlDesignForm().getIsConversion())){
			AutocompleteAdvancedDesignForm advancedForm = new AutocompleteAdvancedDesignForm();
			advancedForm.setAutocompleteForm(form.getAutocompleteForm());
			advancedForm.setSqlDesignForm(form.getSqlDesignForm());
			advancedForm.setInputForm(form.getInputForm());
			advancedForm.setOutputForm(form.getOutputForm());
			model.addAttribute("autocompleteAdvancedDesignForm", advancedForm);
			model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
			destination = FORM_MODIFY_ADVANCED;
		} else {
			try {
				autocompleteDesignService.modifyAutocomleteDesign(autocomplete, common);
			} catch(BusinessException ex){
				model.addAttribute("message",ex.getResultMessages());
				destination = FORM_MODIFY;
				return destination;
			}
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0001)));
			destination = REDIRECT_SEARCH;
		}
		return destination;
	}
	
	@RequestMapping(value = "modify", method = RequestMethod.POST, params = "isJsonForm")
	@TransactionTokenCheck(value="modify",type=TransactionTokenType.IN)
	public String processModify(RedirectAttributes redirectAttr, Model model,@RequestParam("formJson")String formJson) {
		AutocompleteDesignForm form = DataTypeUtils.toObject(formJson, AutocompleteDesignForm.class);
		this.checkChangeProject(true);
		String destination=null;
		AutocompleteDesignCompound autocomplete = beanMapper.map(form, AutocompleteDesignCompound.class);
		CommonModel common = this.initCommon();
		try {
			autocompleteDesignService.modifyAutocomleteDesign(autocomplete, common);
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			model.addAttribute("autocompleteDesignForm",form);
			destination = FORM_MODIFY;
			return destination;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));
		destination = REDIRECT_SEARCH;
		
		return destination;
	}
	
	@RequestMapping(value = {"modify","modifyAdvanced"}, method = RequestMethod.POST,params="jsonBack")
	@TransactionTokenCheck(value = "modify", type = TransactionTokenType.IN)
	public String displayModifyBack(RedirectAttributes redirectAttr, Model model,@RequestParam("formJson")String formJson,@RequestParam Boolean isAdvanced) {
		this.checkChangeProject(false);
		String destination = null;
		if(isAdvanced==null){
			return destination;
		}
		if(isAdvanced){
			model.addAttribute(ADVANCED_DESIGN_FORM_NAME,DataTypeUtils.toObject(formJson, AutocompleteAdvancedDesignForm.class));
			model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
			destination = FORM_MODIFY_ADVANCED;
		} else {
			model.addAttribute(DESIGN_FORM_NAME,DataTypeUtils.toObject(formJson, AutocompleteDesignForm.class));
			model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
			destination = FORM_MODIFY;
		}
		return destination;
	}
	
	/**
	 * Process modify.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value = "modifyConfirm", method = RequestMethod.POST)
	@TransactionTokenCheck(value="modify",type=TransactionTokenType.IN)
	public String processModifyConfirm(@Validated AutocompleteDesignForm form,BindingResult result,Model model, RedirectAttributes redirectAttr, SessionStatus sessionStatus) {
		this.checkChangeProject(true);
		CommonModel common = this.initCommon();
		String destination=null;
		if (result.hasErrors()) {
			return FORM_MODIFY;
		}
		AutocompleteDesignCompound autocomplete = beanMapper.map(form, AutocompleteDesignCompound.class);
		if(BooleanUtils.isTrue(form.getSqlDesignForm().getIsConversion())){
			AutocompleteAdvancedDesignForm advancedForm = new AutocompleteAdvancedDesignForm();
			advancedForm.setAutocompleteForm(form.getAutocompleteForm());
			advancedForm.setSqlDesignForm(form.getSqlDesignForm());
			advancedForm.setInputForm(form.getInputForm());
			advancedForm.setOutputForm(form.getOutputForm());
			model.addAttribute("autocompleteAdvancedDesignForm", advancedForm);
			model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
			destination = FORM_MODIFY_ADVANCED;
		} else {
			boolean isNotShowImpact = form.getShowImpactFlag() == null || form.getShowImpactFlag() == false;
			if(isNotShowImpact){
				destination = processModify(form, result, redirectAttr, model, sessionStatus);
			} else{
				ImpactChangeOfAutocompleteDesign impact = checkShowImpactWhenModifyConfirm(autocomplete.getAutocomplete(), common);
				if(impact == null){
					destination = processModify(form, result, redirectAttr, model, sessionStatus);
				}else{
					model.addAttribute("affectedScreenDesigns",impact.getScreenDesignsImpacted());
					model.addAttribute("formJson", DataTypeUtils.toJson(form));
					destination=FORM_MODIFY_AFFECTION;
				}
			}
		}
		return destination;
	}
	
	/**
	 * Process modify autocomplete.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value="modifyAdvanced",method={RequestMethod.POST})
	@TransactionTokenCheck(value="modify",type=TransactionTokenType.IN)
	public String processModifyAdvanced(@Validated AutocompleteAdvancedDesignForm form, BindingResult result, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		this.checkChangeProject(true);
		CommonModel common = this.initCommon();
		String destination="";
		model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_MODIFY_ADVANCED;
		}
		AutocompleteDesignCompound autocomplete = beanMapper.map(form, AutocompleteDesignCompound.class);

		try {
			autocompleteDesignService.modifyAdvancedAutocomleteDesign(autocomplete, common);
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			destination = FORM_MODIFY_ADVANCED;
			return destination;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0001)));
		destination = REDIRECT_SEARCH;
		return destination;
	}
	
	/**
	 * Process modify advanced.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value = "modifyAdvanced", method = RequestMethod.POST, params = "isJsonForm")
	@TransactionTokenCheck(value="modify",type=TransactionTokenType.IN)
	public String processModifyAdvanced(AutocompleteAdvancedDesignForm form,BindingResult result,@Param("formJson")String formJson, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		form = DataTypeUtils.toObject(formJson, AutocompleteAdvancedDesignForm.class);
		CommonModel common = this.initCommon();
		String destination="";
		model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
		this.checkChangeProject(true);
		AutocompleteDesignCompound autocomplete = beanMapper.map(form, AutocompleteDesignCompound.class);
		try {
			autocompleteDesignService.modifyAdvancedAutocomleteDesign(autocomplete, common);
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			model.addAttribute("autocompleteAdvancedDesignForm",form);
			destination = FORM_MODIFY_ADVANCED;
			return destination;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(SqlDesignMessageConst.SC_SQLDESIGN_SQL_DESIGN)));
		destination = REDIRECT_SEARCH;
		return destination;
	}
	
	@RequestMapping(value = "modifyAdvancedConfirm", method = RequestMethod.POST)
	@TransactionTokenCheck(value="modify",type=TransactionTokenType.IN)
	public String processModifyAdvancedConfirm(@Validated AutocompleteAdvancedDesignForm form,BindingResult result, Model model, RedirectAttributes redirectAttr, SessionStatus sessionStatus) {
		this.checkChangeProject(true);
		CommonModel common = this.initCommon();
		String destination="";
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_MODIFY_ADVANCED;
		}
		boolean isNotShowImpact = form.getShowImpactFlag() == null || form.getShowImpactFlag() == false;
		AutocompleteDesignCompound autocomplete = beanMapper.map(form, AutocompleteDesignCompound.class);
		if(isNotShowImpact){
			destination = processModifyAdvanced(form, result, redirectAttr, model, sessionStatus);
		} else{
			ImpactChangeOfAutocompleteDesign impact = this.checkShowImpactWhenModifyConfirm(autocomplete.getAutocomplete(), common);
			if(impact == null){
				destination = processModifyAdvanced(form, result, redirectAttr, model, sessionStatus);
			}else{
				model.addAttribute("affectedScreenDesigns",impact.getScreenDesignsImpacted());
				model.addAttribute("formJson", DataTypeUtils.toJson(form));
				destination=FORM_MODIFY_ADVANCED_AFFECTION;
			}
		}
		return destination;
	}
	
	/**
	 * Process register autocomplete.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value="register",method={RequestMethod.POST})
	@TransactionTokenCheck(value="register",type=TransactionTokenType.IN)
	public String processRegister(@Validated AutocompleteDesignForm form, BindingResult result, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		this.checkChangeProject(true);
		CommonModel common = this.initCommon();
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_REGISTER;
		}
		AutocompleteDesignCompound autocomplete = beanMapper.map(form, AutocompleteDesignCompound.class);

		try {
			autocompleteDesignService.registerAutocomleteDesign(autocomplete, common);
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			return FORM_REGISTER;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0001)));

		return REDIRECT_SEARCH;
		
	}
	
	/**
	 * Process register advanced.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value="registerAdvanced",method={RequestMethod.POST})
	@TransactionTokenCheck(value="registerAdvanced",type=TransactionTokenType.IN)
	public String processRegisterAdvanced(@Validated AutocompleteAdvancedDesignForm form, BindingResult result, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		this.checkChangeProject(true);
		CommonModel common = this.initCommon();
		model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_REGISTER_ADVANCED;
		}
		
		AutocompleteDesignCompound autocomplete = beanMapper.map(form, AutocompleteDesignCompound.class);

		try {
			autocompleteDesignService.registerAdvancedAutocomleteDesign(autocomplete, common);
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			return FORM_REGISTER_ADVANCED;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(AutocompleteMessageConst.SC_AUTOCOMPLETE_0001)));

		return REDIRECT_SEARCH;
	}
	
	/**
	 * Process search.
	 *
	 * @param form the form
	 * @param model the model
	 * @param pageable the pageable
	 * @return the string
	 */
	@RequestMapping(value="search",method={RequestMethod.POST})
	public String processSearch(AutocompleteSearchForm form, Model model, @PageableDefault Pageable pageable) {
		this.checkChangeProject(false);
		CommonModel common = this.initCommon();
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/autocomplete/search"), pageable.getSort());
		AutocompleteSearchCriteria criteria = beanMapper.map(form, AutocompleteSearchCriteria.class);
		Page<AutocompleteDesign> page=autocompleteDesignService.findPageByCriteria(criteria, pageable, common);
		model.addAttribute("page", page);
		return FORM_SEARCH;
	}
	
	@ModelAttribute
	public AutocompleteDesignForm setUpAutocompleteDesignForm() {
		return new AutocompleteDesignForm();
	}

}
