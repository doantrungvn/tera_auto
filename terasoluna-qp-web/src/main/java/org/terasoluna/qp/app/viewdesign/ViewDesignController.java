package org.terasoluna.qp.app.viewdesign;

import javax.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.qp.app.common.BaseController;
import org.terasoluna.qp.app.common.constants.DbDomainConst;
import org.terasoluna.qp.app.common.ultils.MessageUtils;
import org.terasoluna.qp.app.common.ultils.PageSizeUtils;
import org.terasoluna.qp.app.common.ultils.SessionUtils;
import org.terasoluna.qp.app.message.CommonMessageConst;
import org.terasoluna.qp.app.message.ViewDesignMessageConst;
import org.terasoluna.qp.app.sqldesign.SqlDesignForm.ViewDesignValidationGroup;
import org.terasoluna.qp.domain.model.SqlDesign;
import org.terasoluna.qp.domain.repository.sqldesign.SqlDesignSearchCriteria;
import org.terasoluna.qp.domain.service.domaindatatype.DomainDatatypeConst.SQLDesignType;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignCompound;
import org.terasoluna.qp.domain.service.sqldesign.SqlDesignService;
import org.terasoluna.qp.domain.service.viewdesign.ViewDesignService;

@Controller
@RequestMapping(value = "viewdesign")
@TransactionTokenCheck(value = "viewdesign")
@SessionAttributes(types=ViewDesignSearchForm.class)
public class ViewDesignController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(ViewDesignController.class);
	
	private final String REDIRECT_VIEW = "redirect:/viewdesign/view";
	
	private final String REDIRECT_SEARCH = "redirect:/viewdesign/search";
	
	private final String FORM_MODIFY = "viewdesign/modifyForm";
	
	private final String FORM_MODIFY_ADVANCED = "viewdesign/modifyAdvancedForm";
	
	private final String FORM_SEARCH = "viewdesign/searchForm";
	
	private final String FORM_REGISTER = "viewdesign/registerForm";
	
	private final String FORM_REGISTER_ADVANCED = "viewdesign/registerAdvancedForm";
	
	private final String FORM_VIEW = "viewdesign/viewForm";
	
	private final String FORM_VIEW_ADVANCED = "viewdesign/viewAdvancedForm";
	
	private final String MODE_SEARCH = "0";
	
	private final String MODE_VIEW = "1";
	
	private final String DESIGN_FORM_NAME = "viewDesignDesignForm";
	
	private final String ADVANCED_DESIGN_FORM_NAME = "viewDesignAdvancedDesignForm";
	
	private final String SEARCH_FORM_NAME = "viewDesignSearchForm";
	
	@Inject
	Mapper beanMapper;
	
	@Inject
	ViewDesignService viewDesignService;
	
	@Inject
	SqlDesignService sqlDesignService;
	
	@Inject
	ViewDesignDesignFormValidator viewDesignDesignFormValidator;
	
	@Inject
	ViewDesignAdvancedDesignFormValidator viewDesignAdvancedDesignFormValidator;
	
	@InitBinder
	public void init() {
		moduleCode = CommonMessageConst.TQP_VIEWDESIGN;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String delete(ViewDesignDesignForm form, BindingResult result, RedirectAttributes redirectAttr, Model model) {
		this.checkChangeProject(true);
		SqlDesignCompound viewDesignCompound = beanMapper.map(form, SqlDesignCompound.class);
		String destination = null;
		if(form.getActionDelete()) {
			boolean isDeleted=false;
			try{
				viewDesignCompound.getSqlDesign().setDesignType(SQLDesignType.VIEW);
				isDeleted = sqlDesignService.delete(viewDesignCompound, this.initCommon());
				if(isDeleted) {
					redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_VIEWDESIGN));
					redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0004, MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN)));
					destination = DbDomainConst.REDIRECT_DELETION_SUCCESS;;
				}
				else {
					redirectAttr.addFlashAttribute("message", ResultMessages.error().add(CommonMessageConst.ERR_SYS_0073, MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN)));
					redirectAttr.addAttribute("sqlDesignForm.sqlDesignId", form.getSqlDesignForm().getSqlDesignId());
					destination = REDIRECT_VIEW;
				}
			} catch(BusinessException ex) {
				/*model.addAttribute("message",ex.getResultMessages());
				form.getSqlDesignForm().setSqlDesignId(null);
				destination = FORM_VIEW;*/
				redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_VIEWDESIGN));
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
				destination = DbDomainConst.REDIRECT_DELETION_SUCCESS;
			}
		} else {
			try {
				sqlDesignService.modifyDesignStatus(viewDesignCompound, this.initCommon());
				redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003,
						MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN)));
							redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_VIEWDESIGN));
							destination = DbDomainConst.REDIRECT_MODIFY_SUCCESS;
			} catch(BusinessException ex) {
				redirectAttr.addFlashAttribute("message", ex.getResultMessages());
							redirectAttr.addFlashAttribute("msgHeader", MessageUtils.getMessage(CommonMessageConst.TQP_VIEWDESIGN));
							destination = DbDomainConst.REDIRECT_MODIFY_SUCCESS;
			}
		}
		return destination;
	}
	/**
	 * Display modify.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String displayModify(ViewDesignDesignForm form, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		this.checkChangeProject(true);
		String destination = null;
		SqlDesign viewDesign = beanMapper.map(form.getSqlDesignForm(), SqlDesign.class);
		SqlDesignCompound viewDesignCompound = null;
		try {
			viewDesignCompound = sqlDesignService.findCompoundById(viewDesign.getSqlDesignId());
			if(viewDesignCompound.getSqlDesign().getDesignType()== SQLDesignType.VIEW){
				form = beanMapper.map(viewDesignCompound,ViewDesignDesignForm.class);
				model.addAttribute(DESIGN_FORM_NAME,form);
				destination = FORM_MODIFY;
			} else if(viewDesignCompound.getSqlDesign().getDesignType()== SQLDesignType.ADVANCED_VIEW){
				model.addAttribute(ADVANCED_DESIGN_FORM_NAME,beanMapper.map(viewDesignCompound,ViewDesignAdvancedDesignForm.class));
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
	 * Display modify advanced.
	 *
	 * @param form the form
	 * @param model the model
	 * @param sessionStatus the session status
	 * @param pageable the pageable
	 * @return the string
	 */
	@RequestMapping(value = "modifyAdvanced", method = RequestMethod.GET)
	public String displayModifyAdvanced(ViewDesignAdvancedDesignForm form, Model model) {
		model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
		return FORM_MODIFY_ADVANCED;
	}
	
	/**
	 * Display register.
	 *
	 * @param form the form
	 * @param model the model
	 * @param redirectAttr the redirect attr
	 * @return the string
	 */
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String displayRegister(ViewDesignDesignForm form, Model model, RedirectAttributes redirectAttr) {
		this.checkChangeProject(true);
		return FORM_REGISTER;
	}
	
	/**
	 * Display register advanced.
	 *
	 * @param form the form
	 * @param model the model
	 * @param redirectAttr the redirect attr
	 * @return the string
	 */
	@RequestMapping(value = "registerAdvanced", method = RequestMethod.GET)
	public String displayRegisterAdvanced(ViewDesignAdvancedDesignForm form, Model model, RedirectAttributes redirectAttr) {
		model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode("1"));
		return FORM_REGISTER_ADVANCED;
	}
	
	/**
	 * Display search.
	 *
	 * @param init the init
	 * @param form the form
	 * @param model the model
	 * @param sessionStatus the session status
	 * @param pageable the pageable
	 * @return the string
	 */
	@RequestMapping(value = "search")
	public String displaySearch(@RequestParam(value="init",required=false)String init,ViewDesignSearchForm form, Model model,SessionStatus sessionStatus,@PageableDefault Pageable pageable) {
		if(init != null){
			sessionStatus.setComplete();
			form = new ViewDesignSearchForm();
			model.addAttribute(SEARCH_FORM_NAME, form);
			super.setOldProjectId(SessionUtils.getCurrentProject().getProjectId());
		}
		super.checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/viewdesign/search"), pageable.getSort());
		SqlDesignSearchCriteria criteria = beanMapper.map(form, SqlDesignSearchCriteria.class);
		Page<SqlDesign> page=viewDesignService.findPageByCriteria(criteria, pageable, this.initCommon());
		beanMapper.map(criteria, form);
		model.addAttribute("page", page);
		
		return FORM_SEARCH;
	}
	
	/**
	 * Display view.
	 *
	 * @param form the form
	 * @param model the model
	 * @param redirectAttr the redirect attr
	 * @return the string
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String displayView(ViewDesignDesignForm form, Model model, RedirectAttributes redirectAttr) {
		this.checkChangeProject(false);
		String destination = null;
		SqlDesignCompound viewDesignCompound = beanMapper.map(form, SqlDesignCompound.class);
		try {
			viewDesignCompound = sqlDesignService.findCompoundById(viewDesignCompound.getSqlDesign().getSqlDesignId());
			if(viewDesignCompound.getSqlDesign().getDesignType() == SQLDesignType.VIEW){
				beanMapper.map(viewDesignCompound, form);
				destination = FORM_VIEW;
			} else if(viewDesignCompound.getSqlDesign().getDesignType() == SQLDesignType.ADVANCED_VIEW) {
				model.addAttribute(ADVANCED_DESIGN_FORM_NAME,beanMapper.map(viewDesignCompound, ViewDesignAdvancedDesignForm.class));
				destination = FORM_VIEW_ADVANCED;
			}
		} catch(BusinessException ex){
			redirectAttr.addFlashAttribute("message", ex.getResultMessages());
			destination = REDIRECT_SEARCH;
		}
		return destination;
	}
	
	/*@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		sqlDesignService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
		sqlDesignService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
		sqlDesignService.setAccountId(SessionUtils.getAccountId());
		
		viewDesignService.setWorkingProjectId(SessionUtils.getCurrentProjectId());
		viewDesignService.setWorkingLanguageId(SessionUtils.getCurrentLanguageId());
		viewDesignService.setAccountId(SessionUtils.getAccountId());
	}*/
	
	/**
	 * Inits the binder design form.
	 *
	 * @param webDataBinder the web data binder
	 */
	@InitBinder(DESIGN_FORM_NAME)
	public void initBinderDesignForm(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(viewDesignDesignFormValidator);
		// register characters trimmer for String-type field in form bean
	 	webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
	
	/**
	 * Inits the binder for advanced design form.
	 *
	 * @param webDataBinder the web data binder
	 */
	@InitBinder(ADVANCED_DESIGN_FORM_NAME)
	public void initBinderForAdvancedDesignForm(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(viewDesignAdvancedDesignFormValidator);
		// register characters trimmer for String-type field in form bean
	 	webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
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
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String processModify(@Validated(value=ViewDesignValidationGroup.class) ViewDesignDesignForm form,BindingResult result, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		String destination=null;
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_MODIFY;
		}
		this.checkChangeProject(true);
		SqlDesignCompound viewDesignCompound = beanMapper.map(form, SqlDesignCompound.class);
		if(BooleanUtils.isTrue(form.getSqlDesignForm().getIsConversion())){
			ViewDesignAdvancedDesignForm advancedForm = new ViewDesignAdvancedDesignForm();
			advancedForm.setSqlDesignForm(form.getSqlDesignForm());
			advancedForm.setInputForm(form.getInputForm());
			advancedForm.setOutputForm(form.getOutputForm());
			model.addAttribute("viewDesignAdvancedDesignForm", advancedForm);
			model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode("1"));
			destination = FORM_MODIFY_ADVANCED;
		} else {
			try {
				viewDesignService.modifyViewDesign(viewDesignCompound, this.initCommon());
			} catch(BusinessException ex){
				model.addAttribute("message",ex.getResultMessages());
				destination = FORM_MODIFY;
				return destination;
			}
			redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN)));
			destination = REDIRECT_SEARCH;
		}
		
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
	@RequestMapping(value = "modifyAdvanced", method = RequestMethod.POST)
	public String processModifyAdvanced(@Validated(value=ViewDesignValidationGroup.class) ViewDesignAdvancedDesignForm form,BindingResult result, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		String destination="";
		model.addAttribute("sqlDesignFunctionGroups",sqlDesignService.findAllFunctionCode(SessionUtils.getCurrentDatabaseType()));
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_MODIFY_ADVANCED;
		}
		this.checkChangeProject(true);
		SqlDesignCompound viewDesignCompound = beanMapper.map(form, SqlDesignCompound.class);
		try {
			viewDesignService.modifyAdvancedViewDesign(viewDesignCompound, this.initCommon());
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			destination = FORM_MODIFY_ADVANCED;
			return destination;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0003, MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN)));
		destination = REDIRECT_SEARCH;
		return destination;
	}
	
	/**
	 * Process register.
	 *
	 * @param form the form
	 * @param result the result
	 * @param redirectAttr the redirect attr
	 * @param model the model
	 * @param sessionStatus the session status
	 * @return the string
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String processRegister(@Validated(value=ViewDesignValidationGroup.class) ViewDesignDesignForm form,BindingResult result, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		this.checkChangeProject(true);
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_REGISTER;
		}
		SqlDesignCompound viewDesignCompound = beanMapper.map(form, SqlDesignCompound.class);

		try {
			viewDesignService.registerViewDesign(viewDesignCompound, this.initCommon());
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			return FORM_REGISTER;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN)));

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
	@RequestMapping(value = "registerAdvanced", method = RequestMethod.POST)
	public String processRegisterAdvanced(@Validated(value=ViewDesignValidationGroup.class) ViewDesignAdvancedDesignForm form,BindingResult result, RedirectAttributes redirectAttr, Model model,SessionStatus sessionStatus) {
		model.addAttribute("viewDesignFunctionGroups",sqlDesignService.findAllFunctionCode("1"));
		if (result.hasErrors()) {
			// ValidationUtils.setBindingResult(result,model);
			return FORM_REGISTER_ADVANCED;
		}
		this.checkChangeProject(true);
		SqlDesignCompound viewDesignCompound = beanMapper.map(form, SqlDesignCompound.class);

		try {
			viewDesignService.registerAdvancedViewDesign(viewDesignCompound, this.initCommon());
		} catch(BusinessException ex){
			model.addAttribute("message",ex.getResultMessages());
			return FORM_REGISTER_ADVANCED;
		}
		redirectAttr.addFlashAttribute("message", ResultMessages.info().add(CommonMessageConst.INF_SYS_0002, MessageUtils.getMessage(ViewDesignMessageConst.SC_VIEWDESIGN_VIEW_DESIGN)));

		return REDIRECT_SEARCH;
	}
	
	/**
	 * Process search.
	 *
	 * @param form the form
	 * @param model the model
	 * @param sessionStatus the session status
	 * @param pageable the pageable
	 * @return the string
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String processSearch(ViewDesignSearchForm form, Model model, SessionStatus sessionStatus,@PageableDefault Pageable pageable) {
		this.checkChangeProject(false);
		pageable = new PageRequest(pageable.getPageNumber(), PageSizeUtils.getPageSizeOfAction("/viewdesign/search"), pageable.getSort());
		SqlDesignSearchCriteria criteria = beanMapper.map(form, SqlDesignSearchCriteria.class);
		Page<SqlDesign> page=viewDesignService.findPageByCriteria(criteria, pageable,this.initCommon());
		beanMapper.map(criteria, form);
		model.addAttribute("page", page);

		return FORM_SEARCH;
	}

}
